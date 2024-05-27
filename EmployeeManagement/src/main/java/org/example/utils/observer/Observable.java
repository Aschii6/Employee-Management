package org.example.utils.observer;

import org.example.model.Employee;
import org.example.model.Task;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyEmployee(Integer employeeId, Task task);
    void notifyBossOfLogin(Employee employee);
    void notifyBossOfLogout(Employee employee);
}
