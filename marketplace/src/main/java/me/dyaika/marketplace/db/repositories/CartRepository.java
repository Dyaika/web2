package me.dyaika.marketplace.db.repositories;

import me.dyaika.marketplace.db.entities.CartItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {


    private final NamedParameterJdbcTemplate template;


    public CartRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public boolean addToCart(Long clientId, Long itemId) {
        int existingAmountInCart = getAmountInCart(clientId, itemId);
        int newAmount = existingAmountInCart + 1;

        // Проверяем наличие товара в корзине и на складе
        if (!checkAvailability(itemId, newAmount)) {
            return false;
        }

        String sql;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        parameters.put("itemId", itemId);
        parameters.put("amount", newAmount);

        if (existingAmountInCart == 0) {
            // Если товар еще не в корзине, выполняем операцию INSERT
            sql = "INSERT INTO cart_item (clientid, itemid, amount) VALUES (:clientId, :itemId, :amount)";
        } else {
            // Если товар уже в корзине, выполняем операцию UPDATE
            sql = "UPDATE cart_item SET amount = :amount WHERE clientid = :clientId AND itemid = :itemId";
        }

        template.update(sql, parameters);
        return true;
    }

    public boolean updateAmountInCart(Long clientId, Long itemId, Integer amount) {
        int existingAmountInCart = getAmountInCart(clientId, itemId);
        int newAmount = amount;
        if (newAmount == 0){
            removeFromCart(clientId, itemId);
            return true;
        }
        // Проверяем наличие товара в корзине и на складе
        if (!checkAvailability(itemId, newAmount)) {
            return false;
        }

        String sql;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        parameters.put("itemId", itemId);
        parameters.put("amount", newAmount);

        if (existingAmountInCart == 0) {
            // Если товар еще не в корзине, выполняем операцию INSERT
            sql = "INSERT INTO cart_item (clientid, itemid, amount) VALUES (:clientId, :itemId, :amount)";
        } else {
            // Если товар уже в корзине, выполняем операцию UPDATE
            sql = "UPDATE cart_item SET amount = :amount WHERE clientid = :clientId AND itemid = :itemId";
        }

        template.update(sql, parameters);
        return true;
    }

    public void removeFromCart(Long clientId, Long itemId) {
        String sql = "DELETE FROM cart_item WHERE clientid = :clientId AND itemid = :itemId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        parameters.put("itemId", itemId);

        template.update(sql, parameters);
    }

    public List<CartItem> getCart(Long clientId) {
        String sql = "SELECT ci.itemid, i.price, i.sellernumber, i.type, i.title, ci.amount " +
                "FROM cart_item ci " +
                "INNER JOIN item i ON i.id = ci.itemid " +
                "WHERE ci.clientid = :clientId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        return template.query(sql, parameters, (rs, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(rs.getLong("itemid"));
            cartItem.setPrice(rs.getInt("price"));
            cartItem.setSellernumber(rs.getString("sellernumber"));
            cartItem.setType(rs.getString("type"));
            cartItem.setTitle(rs.getString("title"));
            cartItem.setAmount(rs.getInt("amount"));
            return cartItem;
        });
    }

    public Integer makeOrder(Long clientId) {
        int sum = 0;
        var cart = getCart(clientId);
        for (CartItem it : cart) {
            if (!checkAvailability(it.getId(), it.getAmount())){
                return null;
            }
            sum += it.getAmount() * it.getPrice();
        }
        if (sum == 0){
            return 0;
        }
        for (CartItem it: cart) {
            updateAmountInStock(it.getId(), it.getAmount());
        }
        String sql = "DELETE FROM cart_item WHERE clientid = :clientId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);

        template.update(sql, parameters);
        return sum;
    }

    public boolean checkAvailability(Long itemId, Integer amount) {
        String sql = "SELECT s.amount FROM stock s WHERE s.id = :itemId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("itemId", itemId);

        Integer availableAmount = template.queryForObject(sql, parameters, Integer.class);

        // Если количество товара в наличии больше или равно запрошенному количеству, возвращаем true, иначе false
        return availableAmount != null && availableAmount >= amount;
    }

    private int getAmountInCart(Long clientId, Long itemId) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM cart_item WHERE clientid = :clientId AND itemid = :itemId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        parameters.put("itemId", itemId);

        return template.queryForObject(sql, parameters, Integer.class);
    }

    public boolean updateAmountInStock(Long itemId, Integer soldAmount) {

        String sql = "UPDATE stock SET amount = amount - :amount WHERE id = :itemId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("itemId", itemId);
        parameters.put("amount", soldAmount);

        template.update(sql, parameters);
        return true;
    }
}
