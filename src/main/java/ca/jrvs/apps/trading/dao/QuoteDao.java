package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * public interface CrudRepository<E,ID> {
 *     E save(E entity);
 *     E findById(ID id);
 *     boolean existsById(ID id);
 *     void deleteById(ID id);
 * }
 */
@Repository
public class QuoteDao implements CrudRepository<Quote,Integer> {
    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
    private final String TABLE_NAME = "quote";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public Quote save(Quote entity) {
        return null;
    }

    @Override
    public Quote findById(Integer integer) {
        return null;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public List<Quote> findAll() {
        return null;
    }

    public void update(List<Quote> quoteList){

    }

    public boolean exitsById(String id) {
        return false;
    }
}
