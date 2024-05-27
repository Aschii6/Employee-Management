package org.example.repository;

import org.example.model.Worker;

import java.util.Optional;

public interface WorkerRepository extends Repository<Integer, Worker> {
    Optional<Worker> login(String name, String password);
    Iterable<Worker> findAllEmployees();
}
