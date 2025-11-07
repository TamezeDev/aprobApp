--CREATE TABLES
CREATE TABLE IF NOT EXISTS users(
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    surname VARCHAR(150) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    email_token VARCHAR(200) UNIQUE, --Not valid for free database testing
    email_valid TINYINT (1) DEFAULT 1, --default 1 for free database testing
    password VARCHAR(100) NOT NULL,
    registration_day DATE NOT NULL,
    study VARCHAR(5),
    root TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS topics(
    id_topic INT AUTO_INCREMENT PRIMARY KEY,
    topic_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS choose(
    id_user INT,
    id_topic INT,
    PRIMARY KEY(id_user, id_topic),
    FOREIGN KEY(id_user) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY(id_topic) REFERENCES topics(id_topic) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;  --greater compatibility with sql

CREATE TABLE IF NOT EXISTS tests(
    id_test INT AUTO_INCREMENT PRIMARY KEY,
    test_date DATE NOT NULL,
    id_topic INT NOT NULL,
    FOREIGN KEY(id_topic) REFERENCES topics(id_topic) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS performs(
    id_test INT,
    id_user INT,
    test_grade DOUBLE NOT NULL,
    PRIMARY KEY(id_user, id_test),
    FOREIGN KEY(id_test) REFERENCES tests(id_test),
    FOREIGN KEY(id_user) REFERENCES users(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS questions(
    id_question INT AUTO_INCREMENT PRIMARY KEY,
    question_result TINYINT(1) DEFAULT 0 NOT NULL,
    question_name VARCHAR(400)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS errors(
    id_user INT,
    id_question INT,
    PRIMARY KEY(id_user, id_question),
    FOREIGN KEY(id_user) REFERENCES users(id_user),
    FOREIGN KEY(id_question) REFERENCES questions(id_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS contains(
    id_question INT,
    id_test INT,
    PRIMARY KEY(id_question, id_test),
    FOREIGN KEY(id_question) REFERENCES questions(id_question) ON DELETE CASCADE,
    FOREIGN KEY(id_test) REFERENCES tests(id_test) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS answers(
    id_answer INT AUTO_INCREMENT PRIMARY KEY,
    answer_name VARCHAR(400),
    correct_answer TINYINT(1) DEFAULT 0 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS answers_questions(
    id_answer INT,
    id_question INT,
    PRIMARY KEY(id_question, id_answer),
    FOREIGN KEY(id_answer) REFERENCES answers(id_answer) ON DELETE CASCADE,
    FOREIGN KEY(id_question) REFERENCES questions(id_question) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
