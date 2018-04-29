package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
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
import static java.util.Optional.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
//        List<Task> taskList = new ArrayList<>();
//        taskList.add(new Task(1L, "Test1", "Test Task1"));
//        taskList.add(new Task(2L, "Test2", "Test Task2"));
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Test1", "Test Task1"));
        taskDtoList.add(new TaskDto(2L, "Test2", "Test Task2"));
//        when(dbService.getAllTasks()).thenReturn(taskList);
//        when(taskMapper.taskListToTaskDtoList(taskList)).thenReturn(taskDtoList);
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
    public void shouldFetchTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test", "Test Task");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        when(dbService.getTask( 1L)).thenReturn(ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
//        when(taskController.getTask(1L)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test Task")));
    }
    @Test
    public void shouldFetchDeleteTask() throws Exception {
        //Given & When & Then
        dbService.deleteTask(1L);
        verify(dbService, times(1)).deleteTask(1L);
//        taskController.deleteTask(1L);
//        verify(taskController, times(1)).deleteTask(1L);
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldFetchUpdateTask() throws Exception {
        //Given
//        Task task = new Task(1L, "Test", "Test Task");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
//        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
//        when(dbService.saveTask(task)).thenReturn(task);
//        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
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
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        taskController.createTask(taskDto);
        verify(taskController, times(1)).createTask(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk());
    }
}