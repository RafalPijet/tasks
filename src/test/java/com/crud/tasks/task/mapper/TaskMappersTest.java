package com.crud.tasks.task.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMappersTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapTaskDtoToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test Task");
        //When
        Task result = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(taskDto.getId(), result.getId());
        assertEquals(taskDto.getTitle(), result.getTitle());
        assertEquals(taskDto.getContent(), result.getContent());
    }
    @Test
    public void testMapTaskToTaskDto() {
        //Given
        Task task = new Task(2L, "Next Test", "Next Test Task");
        //When
        TaskDto result = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getContent(), result.getContent());
    }
    @Test
    public void testMapTaskListToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            taskList.add(new Task((long) i, "Test" + i, "Test Task " + i));
        }
        //When
        List<TaskDto> result = taskMapper.taskListToTaskDtoList(taskList);
        //Then
        assertEquals(taskList.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(taskList.get(i).getId(), result.get(i).getId());
            assertEquals(taskList.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(taskList.get(i).getContent(), result.get(i).getContent());
        }

    }
}
