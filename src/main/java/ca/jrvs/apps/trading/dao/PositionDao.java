package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private final static String TABLE_NAME = "position";
    private final static String ID_NAME = "account_id";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Position> findByAccountId(Integer accountId) {
        String selectSql = "SELECT position FROM" + TABLE_NAME + "WHERE account_id =";
        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Position.class),accountId);

    }

    public Long findByIdAndTicker(Integer accountId,String ticker) {
        String selectSql = "SELECT position FROM" + TABLE_NAME + "WHERE account_id =? AND ticker =?";
        Long position = 0L;
        try {
            position = jdbcTemplate.queryForObject(selectSql,Long.class,accountId,ticker);
        } catch (EmptyResultDataAccessException e){
            logger.debug(String.
                    format("select position from position accountId=%s and ticker=%s",accountId,ticker));
        }
        return position;
    }
}
