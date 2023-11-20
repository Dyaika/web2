package me.dyaika.marketplace.db.repositories;

import me.dyaika.marketplace.db.entities.CartItem;
import me.dyaika.marketplace.db.entities.Item;
import me.dyaika.marketplace.db.model.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private final NamedParameterJdbcTemplate template;

    public ItemRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Item> findAll(){
        String sql = "select * from item";
        return template.query(sql, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setPrice(rs.getInt("price"));
            item.setSellernumber(rs.getLong("sellernumber"));
            item.setType(rs.getString("type"));
            item.setTitle(rs.getString("title"));
            return item;
        });
    }
    public Item getById(Long id){
        String sql = "select * from item where item.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setPrice(rs.getInt("price"));
            item.setSellernumber(rs.getLong("sellernumber"));
            item.setType(rs.getString("type"));
            item.setTitle(rs.getString("title"));
            return item;
        });
    }

    public void remove(Long id){
        String sql = "DELETE FROM item WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        template.update(sql, parameters);
    }

    public long createItem(Item item){
        String sql = "INSERT INTO item (price, sellernumber, type, title) VALUES (:price, :sellernumber, :type, :title) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("price", item.getPrice());
        map.put("sellernumber", item.getSellernumber());
        map.put("type", item.getType());
        map.put("title", item.getTitle());

        return template.queryForObject(sql, map, Long.class);
    }

    public void update(Item item){
        String sql = "UPDATE item SET price = :price, sellernumber = :sellernumber, type = :type, title = :title WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", item.getId())
                .addValue("price", item.getPrice())
                .addValue("sellernumber", item.getSellernumber())
                .addValue("type", item.getType())
                .addValue("title", item.getTitle());
        template.update(sql, parameterSource);
    }
}
