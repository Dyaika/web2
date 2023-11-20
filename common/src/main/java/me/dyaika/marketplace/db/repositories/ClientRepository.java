package me.dyaika.marketplace.db.repositories;

import me.dyaika.marketplace.db.model.User;
import me.dyaika.marketplace.db.model.UserRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientRepository {
    private final NamedParameterJdbcTemplate template;

    public ClientRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Long createClient(User user){
        String sql = "INSERT INTO client (login, password, name, email, role) VALUES (:login, :password, :name, :email, :role) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("login", user.getLogin());
        map.put("password", user.getPassword());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("role", user.getRole());

        return template.queryForObject(sql, map, Long.class);
    }

    public User getClientById(Long id){
        String sql = "SELECT * FROM client WHERE client.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            UserRole userRole = UserRole.valueOf(rs.getString("role"));
            user.setRole(userRole);
            return user;
        });
    }

    public List<User> findAll(){
        String sql = "SELECT * FROM client";
        return template.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            UserRole userRole = UserRole.valueOf(rs.getString("role"));
            user.setRole(userRole);
            return user;
        });
    }

    public User findByLogin(String login){
        String sql = "SELECT * FROM client WHERE client.login = :login";
        SqlParameterSource parameterSource = new MapSqlParameterSource("login", login);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            UserRole userRole = UserRole.valueOf(rs.getString("role"));
            user.setRole(userRole);
            return user;
        });
    }

    public void updateClient(User user){
        String sql = "UPDATE client SET login = :login, password = :password, name = :name, email = :email, role = :role WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("login", user.getLogin())
                .addValue("password", user.getPassword())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("role", user.getRole());
        template.update(sql, parameterSource);
    }

    public void deleteClient(long id){
        String sql = "DELETE FROM client WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }
}
