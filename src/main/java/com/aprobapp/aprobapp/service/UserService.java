package com.aprobapp.aprobapp.service;

import com.aprobapp.aprobapp.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SELECT ALL USERS
    public List<User> getAllUsers() {
        String sql = "SELECT id_user, first_name, surname, email, password, registration_day, study, root FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("id_user"),
                rs.getString("first_name"),
                rs.getString("surname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDate("registration_day"),
                rs.getString("study"),
                rs.getInt("root")
        ));
    }

    // INSERT NEW USER
    public void addUser(String firstName, String surName, String email, String password, String study) {
        if (!validPassword(password)) {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos de seguridad establecidos");
        }

        String hashedPassword = passwordEncoder.encode(password);// Encoding valid password

        String sql = """
        INSERT INTO users (first_name, surname, email, password, registration_day, study, root)
        VALUES (?, ?, ?, ?, CURDATE(), ? , 0)
    """;
        jdbcTemplate.update(sql, firstName, surName, email, hashedPassword, study);
    }


    // VALIDATE USER LOGIN
    public boolean validateLogin(String email, String rawPassword) {
        String sql = "SELECT password FROM users WHERE email = ?";
        List<String> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("password"), email);

        if (result.isEmpty()) return false;

        String storedHash = result.get(0);
        return passwordEncoder.matches(rawPassword, storedHash);
    }

    // VALIDATE PASSWORD SECURE
    public boolean validPassword(String password) {
        // Al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#()\\-])[A-Za-z\\d@$!%*?&._#()\\-]{8,}$";
        return Pattern.matches(regex, password);
    }
    // GET ROOT VALUE (devuelve el valor numérico del campo root)
    public int getRootValue(String email) {
        String sql = "SELECT root FROM users WHERE email = ?";
        List<Integer> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("root"), email);
        if (result.isEmpty()) return 0;
        return result.get(0);
    }

    //GET SINGLE USER DATA BY EMAIL
    public Map<String, Object> getUserData(String email) {
        String sql = "SELECT first_name, surname, email, study FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForMap(sql, email);
        } catch (EmptyResultDataAccessException e) {
            return null; // no se encontró usuario
        }
    }

    // CHECK IF USER IS ROOT
    public boolean isRootUser(String email) {
        int rootValue = getRootValue(email);
        return rootValue == 1; // 1 significa que el usuario es root
    }
}
