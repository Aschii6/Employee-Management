package org.example.utils.observer;

import org.example.model.Employee;
import org.example.model.EmployeeWithLoginTime;

public interface BossObserver extends Observer {
    void updateLogin(EmployeeWithLoginTime employee);
    void updateLogout(Employee employee);
}
