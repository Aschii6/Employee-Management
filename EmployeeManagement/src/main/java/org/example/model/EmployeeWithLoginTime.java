package org.example.model;

import java.time.LocalTime;

public class EmployeeWithLoginTime {
    private Employee employee;
    private LocalTime loginTime;

    public EmployeeWithLoginTime(Employee employee, LocalTime loginTime) {
        this.employee = employee;
        this.loginTime = loginTime;
    }

    public Employee getWorker() {
        return employee;
    }

    public LocalTime getLoginTime() {
        return loginTime;
    }
}
