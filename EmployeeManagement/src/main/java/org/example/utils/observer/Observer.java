package org.example.utils.observer;

import org.example.model.Employee;
import org.example.model.Task;
import org.example.model.Worker;

import java.time.LocalTime;

public interface Observer {
   Integer getId();
   boolean isBoss();
   Worker getWorker();
}
