package com.crud.tasks.controller;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super("The task with the given id does not exist in the database!!!");
    }
}
