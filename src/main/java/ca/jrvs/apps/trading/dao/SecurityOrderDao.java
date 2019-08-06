package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
    private static final String TABLE_NAME = "security_order";
    private static final String ID_NAME = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate((dataSource));
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    Class getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    public SecurityOrder save(SecurityOrder entity) {
        return super.save(entity);
    }


    public SecurityOrder findByAccountIdForUpdate(Integer accountId) {
        return super.findById("account_id", accountId, true, getEntityClass());
    }


    public SecurityOrder findByAccountId(Integer accountId) {
        return super.findById("account_id", accountId, false, getEntityClass());
    }

    public void deletebyAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException(("ID is not valid"));
        }
        String deleteSql = "DELETE FROM" + getTableName() + "WHERE account_id = ?";
        logger.info(deleteSql);
        getJdbcTemplate().update(deleteSql, accountId);
    }
}
