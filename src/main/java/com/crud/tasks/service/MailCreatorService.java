package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/trello_frontend");
        context.setVariable("button", "Visit WebSite");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Greetings from your Tasks Application");
        context.setVariable("company", adminConfig.getCompanyName() + ", " + adminConfig.getCompanyEmail() + ", phone: " + adminConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDayReportOfTasksQuantityEmail(String message) {
        List<Task> taskList = dbService.getAllTasks();
        List<TaskDto> result = new ArrayList<>();
        boolean check = false;
        if (taskList.size() >= 3) {
            for (int i = 1; i < 4; i++) {
                result.add(taskMapper.mapToTaskDto(taskList.get(taskList.size() - i)));
                check = true;
            }
        }
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/trello_frontend");
        context.setVariable("button", "Check the WebSite");
        context.setVariable("goodbye", "Greetings from your Tasks Application");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("is_three", check);
        context.setVariable("last_tasks", result);
        return templateEngine.process("mail/day-report-of-tasks-mail", context);
    }
}
