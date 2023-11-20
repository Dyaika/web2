package me.dyaika.marketplace.db.repository;

import me.dyaika.marketplace.db.model.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ItemsRepository {
    private final NamedParameterJdbcTemplate template;

    public ItemsRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Book getBook(Long id){
        String sql = "select * from item join book on item.id = book.id where book.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setPrice(rs.getInt("price"));
            return book;
        });
    }
}
