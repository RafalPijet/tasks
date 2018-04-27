package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
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
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            taskList.add(new Task((long) i, "Test" + i, "Test Task " + i));
        }
        when(taskRepository.findAll()).thenReturn(taskList);
        //When
        List<Task> result = dbService.getAllTasks();
        //Then
        assertEquals(taskList, result);
    }
    @Test
    public void testGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(2L, "Next Test", "Next Test Task");
        when(taskRepository.findById(1L)).thenReturn(ofNullable(task));
        //When
        Optional<Task> result = dbService.getTask(1L);
        //Then
        assertEquals(task.getId(), result.orElseThrow(TaskNotFoundException::new).getId());
        assertEquals(task.getTitle(), result.orElseThrow(TaskNotFoundException::new).getTitle());
        assertEquals(task.getContent(), result.orElseThrow(TaskNotFoundException::new).getContent());

    }
    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(2L, "Next Test", "Next Test Task");
        when(taskRepository.save(task)).thenReturn(task);
        //When
        Task result = dbService.saveTask(task);
        //Then
        assertEquals(task, result);
    }
}
