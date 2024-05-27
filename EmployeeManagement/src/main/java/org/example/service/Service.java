package org.example.service;

import org.example.model.Employee;
import org.example.model.EmployeeWithLoginTime;
import org.example.model.Task;
import org.example.model.Worker;
import org.example.repository.WorkerRepository;
import org.example.utils.observer.BossObserver;
import org.example.utils.observer.EmployeeObserver;
import org.example.utils.observer.Observable;
import org.example.utils.observer.Observer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service implements Observable {
    private final WorkerRepository workerRepository;

    List<EmployeeObserver> employeeObservers = new ArrayList<>();
    BossObserver bossObserver = null;

    public Service(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker login(String name, String password) {
        Optional<Worker> opt = workerRepository.login(name, password);

        if (opt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        return opt.get();
    }

    public void logout(Observer observer) {
        removeObserver(observer);
    }

    public Iterable<Worker> findAllEmployees() {
        return workerRepository.findAllEmployees();
    }

    public void sendTask(Integer employeeId, String taskDescription) {
        notifyEmployee(employeeId, new Task(taskDescription));
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer.isBoss()) {
            if (bossObserver == null) {
                bossObserver = (BossObserver) observer;

                if (!employeeObservers.isEmpty()) {
                    employeeObservers.forEach(eO -> notifyBossOfLogin((Employee) eO.getWorker()));
                }
            }
        } else {
            employeeObservers.add((EmployeeObserver) observer);

            notifyBossOfLogin((Employee) observer.getWorker());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer.isBoss()) {
            bossObserver = null;
        } else {
            employeeObservers.remove((EmployeeObserver) observer);

            notifyBossOfLogout((Employee) observer.getWorker());
        }
    }

    @Override
    public void notifyEmployee(Integer employeeId, Task task) {
        employeeObservers.forEach(o -> {
            if (o.getId().equals(employeeId)) o.update(task);
        });
    }

    @Override
    public void notifyBossOfLogin(Employee employee) {
        if (bossObserver != null)
            bossObserver.updateLogin(new EmployeeWithLoginTime(employee,
                    LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS)));
    }

    @Override
    public void notifyBossOfLogout(Employee employee) {
        if (bossObserver != null)
            bossObserver.updateLogout(employee);
    }
}
