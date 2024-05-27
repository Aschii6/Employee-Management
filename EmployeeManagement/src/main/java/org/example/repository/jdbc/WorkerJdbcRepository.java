package org.example.repository.jdbc;

import org.example.model.Boss;
import org.example.model.Employee;
import org.example.model.Worker;
import org.example.repository.WorkerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class WorkerJdbcRepository implements WorkerRepository {
    JdbcUtils jdbcUtils;
    public WorkerJdbcRepository(Properties dbProps) {
        jdbcUtils = new JdbcUtils(dbProps);
    }

    @Override
    public Optional<Worker> findOne(Integer id) {
        Connection con = jdbcUtils.getConnection();

        try (
                PreparedStatement ps = con.prepareStatement("select * from workers where id_worker = ?")
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                boolean isBoss = rs.getBoolean("is_boss");

                Worker worker;

                if (isBoss) {
                    worker = new Boss(name, password, true);
                } else {
                    worker = new Employee(name, password, false);
                }

                worker.setId(rs.getInt("id_worker"));

                return Optional.of(worker);
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Worker> login(String name, String password) {
        Connection con = jdbcUtils.getConnection();

        try (
                PreparedStatement ps = con.prepareStatement("select * from workers where name = ? and password = ?")
        ) {
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean isBoss = rs.getBoolean("is_boss");

                Worker worker;

                if (isBoss) {
                    worker = new Boss(name, "", true);
                } else {
                    worker = new Employee(name, "", false);
                }

                worker.setId(rs.getInt("id_worker"));

                return Optional.of(worker);
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Worker> findAllEmployees() {
        List<Worker> employees = new ArrayList<>();

        Connection con = jdbcUtils.getConnection();

        try (
                PreparedStatement ps = con.prepareStatement("select * from workers where is_boss = false")
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");

                Employee employee = new Employee(name, password, false);
                employee.setId(rs.getInt("id_worker"));

                employees.add(employee);
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        return employees;
    }

    @Override
    public Iterable<Worker> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Worker> save(Worker worker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Worker> delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Worker> update(Worker worker) {
        throw new UnsupportedOperationException();
    }
}
