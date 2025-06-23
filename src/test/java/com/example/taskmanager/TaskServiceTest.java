package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Test 1", false),
                new Task(2L, "Task 2", "Test 2", true)
        );

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(null, "New Task", "desc", false);
        Task savedTask = new Task(1L, "New Task", "desc", false);

        when(taskRepository.save(task)).thenReturn(savedTask);

        Task result = taskService.createTask(task);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Long id = 1L;
        Task oldTask = new Task(id, "Old", "Old desc", false);
        Task updatedTask = new Task(null, "Updated", "Updated desc", true);

        when(taskRepository.findById(id)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateTask(id, updatedTask);

        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getTitle());
        assertTrue(result.get().isCompleted());
    }

    @Test
    public void testDeleteTask() {
        Long id = 1L;

        doNothing().when(taskRepository).deleteById(id);

        taskService.deleteTask(id);

        verify(taskRepository, times(1)).deleteById(id);
    }
}
