package com.crud.tasks.task.controller;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static java.util.Optional.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MyTaskControllerTest {

    @InjectMocks
    private TaskController taskController;
    @Mock
    private DbService dbService;
    @Mock
    private TaskMapper taskMapper;

    @Test
    public void testGetTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            taskList.add(new Task((long) i, "Test" + i, "Test Task " + i));
        }
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            taskDtoList.add(new TaskDto((long) i, "Test" + i, "Test Task " + i));
        }
        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.taskListToTaskDtoList(taskList)).thenReturn(taskDtoList);
        //When
        List<TaskDto> result = taskController.getTasks();
        //Then
        assertEquals(5, result.size());
    }
    @Test
    public void testGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Test", "Test Task");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        when(dbService.getTask(1L)).thenReturn(ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When
        TaskDto result = taskController.getTask(1L);
        //Then
        assertEquals(of(1L), of(result.getId()));
        assertEquals("Test", result.getTitle());
        assertEquals("Test Task", result.getContent());
    }
    @Test
    public void testDeleteTask() throws TaskNotFoundException {
        //Given
        //When
        taskController.deleteTask(1L);
        //Then
        verify(dbService, times(1)).deleteTask(1L);
    }
    @Test
    public void testUpdateTask() {
        //Given
        Task task = new Task(1L, "Test", "Test Task");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When
        TaskDto result = taskController.updateTask(taskDto);
        //Then
        assertEquals(taskDto, result);
    }
    @Test
    public void testCreateTask() {
        //Given
        Task task = new Task(1L, "Test", "Test Task");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        //When
        taskController.createTask(taskDto);
        //Then
        verify(dbService, times(1)).saveTask(task);
    }
}
