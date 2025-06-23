package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceNoMockTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void testCreateTaskWithRealDatabase() {
        Task task = new Task();
        task.setTitle("Integration Unit Task");
        task.setDescription("Testing non-mocked unit logic");
        task.setCompleted(false);

        Task saved = taskService.createTask(task);

        assertNotNull(saved.getId());
        assertEquals("Integration Unit Task", saved.getTitle());
        assertFalse(saved.isCompleted());

        List<Task> all = taskRepository.findAll();
        assertEquals(1, all.size());
    }
}
