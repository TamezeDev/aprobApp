package com.aprobapp.aprobapp.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final JdbcTemplate jdbcTemplate;

    public TestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Integer getApprovedTests(String email) {
        String sql = "SELECT COUNT(p.id_test) FROM users u JOIN performs p ON u.id_user = p.id_user WHERE u.email = ? AND p.test_grade >= 5";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public Double getAverageGrade(String email) {
        String sql = "SELECT COALESCE(AVG(p.test_grade), 0) AS nota_media FROM users u JOIN performs p ON u.id_user = p.id_user WHERE u.email = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, Double.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null; // o 0.0 si prefieres
        }
    }

}


