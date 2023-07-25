package hr.algebra.tabletopshop.repository.jdbc;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;
import hr.algebra.tabletopshop.repository.BoardgameRepository;
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

@Repository
@Primary
public class BoardgameRepositoryJdbc implements BoardgameRepository {
    
    private static final String BOARDGAMES_TABLE = "BOARDGAMES";
    private static final String BOARDGAMES_TABLE_ID = "ID";
    private static final String BOARDGAMES_TABLE_NAME = "NAME";
    private static final String BOARDGAMES_TABLE_PUBLISHED = "PUBLISHED";
    private static final String BOARDGAMES_TABLE_RATING = "RATING";
    private static final String BOARDGAMES_TABLE_WEIGHT = "WEIGHT";
    private static final String BOARDGAMES_TABLE_DURATION = "DURATION";
    private static final String BOARDGAMES_TABLE_PRICE = "PRICE";
    private static final String SELECT_ALL_BGS = "select * from BOARDGAMES where 1=1 ";
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    public BoardgameRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(BOARDGAMES_TABLE)
                .usingGeneratedKeyColumns(BOARDGAMES_TABLE_ID);
    }
    
    @Override
    public List<Boardgame> getAllBoardgames() {
        return jdbcTemplate.query(SELECT_ALL_BGS, this::mapDbRowToBoardgame);
    }
    
    private Boardgame mapDbRowToBoardgame(ResultSet rs, int rowNum) throws SQLException {
        return new Boardgame(
                rs.getInt(BOARDGAMES_TABLE_ID),
                rs.getString(BOARDGAMES_TABLE_NAME),
                rs.getInt(BOARDGAMES_TABLE_PUBLISHED),
                rs.getDouble(BOARDGAMES_TABLE_RATING),
                rs.getDouble(BOARDGAMES_TABLE_WEIGHT),
                rs.getInt(BOARDGAMES_TABLE_DURATION),
                rs.getDouble(BOARDGAMES_TABLE_PRICE)
        );
    }
    
    @Override
    public void saveNewBoardgame(Boardgame boardgame) {
        saveBoardgameDetails(boardgame);
    }
    
    private void saveBoardgameDetails(Boardgame boardgame) {
        Map<String, Object> boardgameDetails = new HashMap<>();
        
        boardgameDetails.put(BOARDGAMES_TABLE_ID, boardgame.getId());
        boardgameDetails.put(BOARDGAMES_TABLE_NAME, boardgame.getName());
        boardgameDetails.put(BOARDGAMES_TABLE_PUBLISHED, boardgame.getPublished());
        boardgameDetails.put(BOARDGAMES_TABLE_RATING, boardgame.getRating());
        boardgameDetails.put(BOARDGAMES_TABLE_WEIGHT, boardgame.getWeight());
        boardgameDetails.put(BOARDGAMES_TABLE_DURATION, boardgame.getDuration());
        boardgameDetails.put(BOARDGAMES_TABLE_PRICE, boardgame.getPrice());

//        int id = simpleJdbcInsert.executeAndReturnKey(boardgameDetails).intValue(); // ovako ako zelis dobit id novo generiranog bg-a
        simpleJdbcInsert.executeAndReturnKey(boardgameDetails);
    }
    
    @Override
    public List<Boardgame> filterBoardgames(Boardgame filter) {
        String query = SELECT_ALL_BGS;
        
        if (Optional.ofNullable(filter.getId()).isPresent()) {
            query += "and ID = " + filter.getId();
        }
        if (Optional.ofNullable(filter.getName()).isPresent()) {
            query += "and NAME = " + filter.getName();
        }
        if (Optional.ofNullable(filter.getPublished()).isPresent()) {
            query += "and PUBLISHED = " + filter.getPublished();
        }
        if (Optional.ofNullable(filter.getRating()).isPresent()) {
            query += "and RATING = " + filter.getRating();
        }
        if (Optional.ofNullable(filter.getWeight()).isPresent()) {
            query += "and WEIGHT = " + filter.getWeight();
        }
        if (Optional.ofNullable(filter.getDuration()).isPresent()) {
            query += "and DURATION = " + filter.getDuration();
        }
        if (Optional.ofNullable(filter.getPrice()).isPresent()) {
            query += "and PRICE = " + filter.getPrice();
        }
        
        return jdbcTemplate.query(query, this::mapDbRowToBoardgame);
    }
}
