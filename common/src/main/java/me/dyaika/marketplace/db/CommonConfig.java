package me.dyaika.marketplace.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CommonConfig {
    public NamedParameterJdbcTemplate template(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
