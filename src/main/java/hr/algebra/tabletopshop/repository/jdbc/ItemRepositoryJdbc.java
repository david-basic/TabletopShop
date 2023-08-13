package hr.algebra.tabletopshop.repository.jdbc;

import hr.algebra.tabletopshop.domain.items.Category;
import hr.algebra.tabletopshop.domain.items.Item;
import hr.algebra.tabletopshop.repository.ItemRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Deprecated
@Repository
@Primary
public class ItemRepositoryJdbc implements ItemRepository {
    
    private static final String ITEMS_TABLE = "ITEMS";
    private static final String ITEMS_TABLE_ID = "ID";
    private static final String ITEMS_TABLE_NAME = "NAME";
    private static final String ITEMS_TABLE_CATEGORY = "CATEGORY";
    private static final String ITEMS_TABLE_DESCRIPTION = "DESCRIPTION";
    private static final String ITEMS_TABLE_QUANTITY = "QUANTITY";
    private static final String ITEMS_TABLE_PRICE = "PRICE";
    private static final String SELECT_ALL_ITEMS = "select * from ITEMS where 1=1 ";
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    public ItemRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ITEMS_TABLE)
                .usingGeneratedKeyColumns(ITEMS_TABLE_ID);
    }
    
    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SELECT_ALL_ITEMS, this::mapDbRowToItem);
    }
    
    private Item mapDbRowToItem(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getInt(ITEMS_TABLE_ID),
                rs.getString(ITEMS_TABLE_NAME),
                Category.valueOf(rs.getString(ITEMS_TABLE_CATEGORY)),
                rs.getString(ITEMS_TABLE_DESCRIPTION),
                rs.getInt(ITEMS_TABLE_QUANTITY),
                rs.getDouble(ITEMS_TABLE_PRICE)
        );
    }
    
    @Override
    public void saveNewItem(Item item) {
        saveItemDetails(item);
    }
    
    private void saveItemDetails(Item item) {
        Map<String, Object> itemDetails = new HashMap<>();
        
        itemDetails.put(ITEMS_TABLE_ID, item.getId());
        itemDetails.put(ITEMS_TABLE_NAME, item.getName());
        itemDetails.put(ITEMS_TABLE_CATEGORY, item.getCategory());
        itemDetails.put(ITEMS_TABLE_DESCRIPTION, item.getDescription());
        itemDetails.put(ITEMS_TABLE_QUANTITY, item.getQuantity());
        itemDetails.put(ITEMS_TABLE_PRICE, item.getPrice());

//        int id = simpleJdbcInsert.executeAndReturnKey(itemDetails).intValue(); // ovako ako zelis dobit id novo generiranog bg-a
        simpleJdbcInsert.executeAndReturnKey(itemDetails);
    }
    
    @Override
    public List<Item> filterItems(Item filter) {
        String query = SELECT_ALL_ITEMS;
        
        if (Optional.ofNullable(filter.getId()).isPresent()) {
            query += "and ID = " + filter.getId();
        }
        if (Optional.ofNullable(filter.getName()).isPresent()) {
            query += "and NAME = " + filter.getName();
        }
        if (Optional.ofNullable(filter.getCategory()).isPresent()) {
            query += "and CATEGORY = " + filter.getCategory();
        }
        if (Optional.ofNullable(filter.getDescription()).isPresent()) {
            query += "and DESCRIPTION = " + filter.getDescription();
        }
        if (Optional.ofNullable(filter.getQuantity()).isPresent()) {
            query += "and QUANTITY = " + filter.getQuantity();
        }
        if (Optional.ofNullable(filter.getPrice()).isPresent()) {
            query += "and PRICE = " + filter.getPrice();
        }
        
        return jdbcTemplate.query(query, this::mapDbRowToItem);
    }
}
