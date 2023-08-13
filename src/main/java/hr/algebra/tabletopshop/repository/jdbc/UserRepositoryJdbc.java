package hr.algebra.tabletopshop.repository.jdbc;

import hr.algebra.tabletopshop.domain.users.User;
import hr.algebra.tabletopshop.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Deprecated
@Repository
public class UserRepositoryJdbc implements UserRepository {
    private static final String USERS_TABLE = "USERS";
    private static final String USERS_TABLE_ID = "ID";
    private static final String USERS_TABLE_USERNAME = "USERNAME";
    private static final String USERS_TABLE_PASSWORD = "PASSWORD";
    private static final String SELECT_ALL_USERS = "select * from USERS where 1=1 ";
    
    private static final String AUTHORITIES_TABLE_USERNAME = "USERNAME";
    private static final String AUTHORITIES_TABLE_AUTHORITY = "AUTHORITY";
    private static final String AUTHORITIES_TABLE_USER_ID = "USER_ID";
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(USERS_TABLE)
                .usingGeneratedKeyColumns(USERS_TABLE_ID);
    }
    
    @Override
    public void saveNewUser(User user) {
        Map<Integer, String> newUserDetails = saveUserDetails(user);
        
        Map<String, Object> authoritiesDetails = new HashMap<>();
        authoritiesDetails.put(AUTHORITIES_TABLE_USERNAME, newUserDetails.values().iterator().next());
        authoritiesDetails.put(AUTHORITIES_TABLE_AUTHORITY, "ROLE_USER");
        authoritiesDetails.put(AUTHORITIES_TABLE_USER_ID, newUserDetails.keySet().iterator().next());
        
        simpleJdbcInsert.execute(authoritiesDetails);
    }
    
    private Map<Integer, String> saveUserDetails(User user) {
        Map<String, Object> userDetails = new HashMap<>();
        
        userDetails.put(USERS_TABLE_ID, user.getId());
        userDetails.put(USERS_TABLE_USERNAME, user.getUsername());
        userDetails.put(USERS_TABLE_PASSWORD, user.getPassword());
        
        Integer id = simpleJdbcInsert.executeAndReturnKey(userDetails).intValue();
        Map<Integer, String> createdUser = new HashMap<>();
        createdUser.put(id, user.getUsername());
        
        return createdUser;
    }
    
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_USERS, this::mapDbRowToUser);
    }
    
    private User mapDbRowToUser(ResultSet rs, int rowNum) {
        return new User();
    }
    
    @Override
    public List<User> filterUsers(User filter) {
        String query = SELECT_ALL_USERS;
        
        if (Optional.ofNullable(filter.getUsername()).isPresent()) {
            query += "and USERNAME = " + filter.getUsername();
        }
        if (Optional.ofNullable(filter.getPassword()).isPresent()) {
            query += "and PASSWORD = " + filter.getPassword();
        }
        
        return jdbcTemplate.query(query, this::mapDbRowToUser);
    }
}
