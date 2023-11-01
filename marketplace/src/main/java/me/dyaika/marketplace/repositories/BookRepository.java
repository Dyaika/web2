package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.Book;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {


    private final NamedParameterJdbcTemplate template;


    public BookRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    public Long createBook(Book book){
        String sql = "INSERT INTO book (author, sellernumber, type, price, title) VALUES (:author, :sellernumber, :type, :price, :title) RETURNING ID";
        Map<String, Object> map = new HashMap<>();
        map.put("author", book.getAuthor());
        map.put("sellernumber", book.getSellernumber());
        map.put("type", book.getType());
        map.put("price", book.getPrice());
        map.put("title", book.getTitle());

        System.out.println(book.getAuthor());
        System.out.println(book.getSellernumber());
        System.out.println(book.getType());
        System.out.println(book.getPrice());
        System.out.println(book.getTitle());

        return template.queryForObject(sql, map, Long.class);
    }

    public Book getBook(Long id){
        String sql = "SELECT * FROM book WHERE book.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setType(rs.getString("type"));
            book.setSellernumber(rs.getString("sellernumber"));
            book.setPrice(rs.getDouble("price"));
            return book;
        });
    }

    public void editBook(Book book){
        String sql = "UPDATE book SET author = :author, sellernumber = :sellernumber, type = :type, price = :price, title = :title WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("author", book.getAuthor())
                .addValue("sellernumber", book.getSellernumber())
                .addValue("title", book.getTitle())
                .addValue("type", book.getType())
                .addValue("price", book.getPrice());
        template.update(sql, parameterSource);
    }

    public void deleteBook(long id){
        String sql = "DELETE FROM book WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }
}