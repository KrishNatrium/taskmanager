package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task1, task2;

    @BeforeEach
    public void setup() {
        task1 = new Task(1L, "Test Task 1", "Description 1", false);
        task2 = new Task(2L, "Test Task 2", "Description 2", true);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Task 1"));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task newTask = new Task(null, "New Task", "New Desc", false);
        Task savedTask = new Task(3L, "New Task", "New Desc", false);

        Mockito.when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task updated = new Task(null, "Updated Task", "Updated Desc", true);
        Task result = new Task(1L, "Updated Task", "Updated Desc", true);

        Mockito.when(taskService.updateTask(eq(1L), any(Task.class)))
                .thenReturn(Optional.of(result));

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(taskService).deleteTask(1L);
    }
}
