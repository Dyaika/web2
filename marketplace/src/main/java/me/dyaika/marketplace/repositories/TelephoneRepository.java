package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.Telephone;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TelephoneRepository {


    private final NamedParameterJdbcTemplate template;


    public TelephoneRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Long createTelephone(Telephone book) {
        String sql = "INSERT INTO telephone (vendor, sellernumber, type, price, title, battery) VALUES (:vendor, :sellernumber, :type, :price, :title, :battery) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("vendor", book.getVendor());
        map.put("sellernumber", book.getSellernumber());
        map.put("type", book.getType());
        map.put("price", book.getPrice());
        map.put("title", book.getTitle());
        map.put("battery", book.getBattery());

        return template.queryForObject(sql, map, Long.class);
    }

    public Telephone getTelephone(Long id) {
        String sql = "SELECT * FROM telephone WHERE telephone.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Telephone book = new Telephone();
            book.setId(rs.getLong("id"));
            book.setVendor(rs.getString("vendor"));
            book.setSellernumber(rs.getString("sellernumber"));
            book.setType(rs.getString("type"));
            book.setPrice(rs.getDouble("price"));
            book.setTitle(rs.getString("title"));
            book.setBattery(rs.getInt("battery"));
            return book;
        });
    }

    public void updateTelephone(Telephone book) {
        String sql = "UPDATE telephone SET vendor = :vendor, sellernumber = :sellernumber, type = :type, price = :price, title = :title, battery = :battery WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("vendor", book.getVendor())
                .addValue("sellernumber", book.getSellernumber())
                .addValue("title", book.getTitle())
                .addValue("type", book.getType())
                .addValue("price", book.getPrice())
                .addValue("battery", book.getBattery());
        template.update(sql, parameterSource);
    }

    public void deleteTelephone(long id) {
        String sql = "DELETE FROM telephone WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }
}
