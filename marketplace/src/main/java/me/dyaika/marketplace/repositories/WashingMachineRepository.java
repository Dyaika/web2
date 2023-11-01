package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.WashingMachine;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WashingMachineRepository {

    private final NamedParameterJdbcTemplate template;


    public WashingMachineRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Long createWashingMachine(WashingMachine book) {
        String sql = "INSERT INTO washing_machine (vendor, sellernumber, type, price, title, volume) VALUES (:vendor, :sellernumber, :type, :price, :title, :volume) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("vendor", book.getVendor());
        map.put("sellernumber", book.getSellernumber());
        map.put("type", book.getType());
        map.put("price", book.getPrice());
        map.put("title", book.getTitle());
        map.put("volume", book.getVolume());

        return template.queryForObject(sql, map, Long.class);
    }

    public WashingMachine getWashingMachine(Long id) {
        String sql = "SELECT * FROM washing_machine WHERE washing_machine.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            WashingMachine book = new WashingMachine();
            book.setId(rs.getLong("id"));
            book.setVendor(rs.getString("vendor"));
            book.setSellernumber(rs.getString("sellernumber"));
            book.setType(rs.getString("type"));
            book.setPrice(rs.getDouble("price"));
            book.setTitle(rs.getString("title"));
            book.setVolume(rs.getInt("volume"));
            return book;
        });
    }

    public void updateWashingMachine(WashingMachine book) {
        String sql = "UPDATE washing_machine SET vendor = :vendor, sellernumber = :sellernumber, type = :type, price = :price, title = :title, volume = :volume WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("vendor", book.getVendor())
                .addValue("sellernumber", book.getSellernumber())
                .addValue("title", book.getTitle())
                .addValue("type", book.getType())
                .addValue("price", book.getPrice())
                .addValue("battery", book.getVolume());
        template.update(sql, parameterSource);
    }

    public void deleteWashingMachine(long id) {
        String sql = "DELETE FROM washing_machine WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }
}
