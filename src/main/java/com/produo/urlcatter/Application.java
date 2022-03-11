package com.produo.urlcatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    public DataSource dataSource;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            dataSource = getDataSource();
            System.out.println("> Server started...");

            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            String addEventQuery = "CREATE EVENT IF NOT EXISTS delete_link_event ON SCHEDULE EVERY 1 DAY DO DELETE FROM link WHERE last_use < (NOW() - INTERVAL 60 DAY);";
            jdbcTemplate.setDataSource(dataSource);
            jdbcTemplate.execute(addEventQuery);
        };
    }

    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(pass);
        return dataSource;
    }
}
