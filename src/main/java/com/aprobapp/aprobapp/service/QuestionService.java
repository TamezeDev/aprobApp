package com.aprobapp.aprobapp.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final JdbcTemplate jdbcTemplate;

    public QuestionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Integer getErrorQuestions(String email) {
        String sql = "SELECT COUNT(e.id_question) AS preguntas_falladas FROM users u LEFT JOIN errors e ON u.id_user = e.id_user WHERE u.email = ? GROUP BY u.email";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }

    }

}
