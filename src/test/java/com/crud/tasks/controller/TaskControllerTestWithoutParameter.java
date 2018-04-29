package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestWithoutParameter {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Test1", "Test Task1"));
        taskDtoList.add(new TaskDto(2L, "Test2", "Test Task2"));
        when(taskController.getTasks()).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test1")))
                .andExpect(jsonPath("$[0].content", is("Test Task1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test2")))
                .andExpect(jsonPath("$[1].content", is("Test Task2")));
    }
    @Test
    public void shouldFetchUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test Task")));
    }
    @Test
    public void shouldFetchCreateTask() throws Exception {
        //Given & When & Then
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        taskController.createTask(taskDto);
        verify(taskController, times(1)).createTask(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk());
    }
}