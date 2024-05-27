package org.example.utils.observer;

import org.example.model.Task;

public interface EmployeeObserver extends Observer {
    void update(Task task);
}
