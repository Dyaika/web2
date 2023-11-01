package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.Client;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

public class ClientRepository {
    private final NamedParameterJdbcTemplate template;


    public ClientRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    public Long createClient(Client client){
        String sql = "INSERT INTO client (login, password, name, email) VALUES (:login, :password, :name, :email) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("login", client.getLogin());
        map.put("password", client.getPassword());
        map.put("name", client.getName());
        map.put("email", client.getEmail());

        return template.queryForObject(sql, map, Long.class);
    }

    public Client getClient(Long id){
        String sql = "SELECT * FROM client WHERE client.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Client client = new Client();
            client.setId(rs.getLong("id"));
            client.setLogin(rs.getString("login"));
            client.setPassword(rs.getString("password"));
            client.setName(rs.getString("name"));
            client.setEmail(rs.getString("email"));
            return client;
        });
    }

    public void updateClient(Client client){
        String sql = "UPDATE client SET login = :login, password = :password, name = :name, email = :email WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", client.getId())
                .addValue("login", client.getLogin())
                .addValue("password", client.getPassword())
                .addValue("name", client.getName())
                .addValue("email", client.getEmail());
        template.update(sql, parameterSource);
    }

    public void deleteClient(long id){
        String sql = "DELETE FROM client WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }
}
