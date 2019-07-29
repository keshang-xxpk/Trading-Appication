package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

;

/**
 * public interface CrudRepository<E,ID> {
 *     E save(E entity);
 *     E findById(ID id);
 *     boolean existsById(ID id);
 *     void deleteById(ID id);
 * }
 */
@Repository
public class QuoteDao extends JdbcCrudDao<Quote,String> {

    private final String TABLE_NAME = "quote";
    private final String ID_NAME = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate;}

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() { return simpleJdbcInsert;}

    @Override
    public String getTableName() { return TABLE_NAME;}

    @Override
    public String getIdName() { return ID_NAME;}

    @Override
    Class getEntityClass() { return Quote.class;}
    //finished
    @Override
    public Quote save(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = getSimpleJdbcInsert().execute(parameterSource);
        if(row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert!",1,row);
        }
        return quote;
    }
//finished
    public List<Quote> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Quote.class ));
    }
    //finished
    public void deleteById(String idName, ID id) {
        if (id ==null) {
            throw new IllegalArgumentException("Invaild ID!");
        }
        String sql = "DELETE FROM " + getTableName() + "WHERE" + idName + "= ?";
        getJdbcTemplate().update(sql,id);
    }

    //public boolean existsById(String id) {
        //return false;
    //}

    /**public Quote findById(String idName, ID id,boolean forUpdate,Class clazz) {
        if (id ==null) {
            throw new IllegalArgumentException("Invaild ID!");
        }
        String sql = "DELETE FROM " + getTableName() + "WHERE" + idName + "= ?";
        getJdbcTemplate().update(sql,id);
        return null;
    }
     */

    //finished
    public void update(List<Quote> quotes){
        /**
         * "askPrice",1
         *         "askSize",1
         *         "bidPrice",1
         *         "bidSize",1
         *         "id",
         *         "lastPrice",1
         *         "ticker"
         */
        String updateSql = "UPDATE quote " +
                "SET last_price=?," +
                "bid_price=?," +
                "bid_size=?," +
                "ask_size=?," +
                "ask_price WHERE ticker=?";
        List<Object[]> batch = new ArrayList<>();
        quotes.forEach(quote -> {
            if(!existsById(quote.getTicker())) {
                throw new ResourceNotFoundException("Ticker not found" + quote.getTicker());
            }
            Object[] values = new Object[] {
                    quote.getLastPrice(),
                    quote.getAskSize(),
                    quote.getAskPrice(),
                    quote.getBidPrice(),
                    quote.getBidSize(),
                    quote.getTicker()};
            batch.add(values);
        });

        int[] rows = jdbcTemplate.batchUpdate(updateSql,batch);
        int totalRow = Arrays.stream(rows).sum();
        if(totalRow != quotes.size()) {
            throw new IncorrectResultSizeDataAccessException("number of rows",quotes.size(),totalRow);
        }
    }
}
