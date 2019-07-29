package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AccountDao extends JdbcCrudDao<Account,Integer> {
    private static  final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    private static  final String TABLE_NAME = "account";
    private static final String ID_NAME = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
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
    Class getEntityClass() { return Account.class;}


    @Override
    public Account save(Account entity) { return super.save(entity); }

    public Account findByTraderId(Integer traderId) {
        return super.findById("trader_id",traderId,false,getEntityClass());
    }

    public Account findByTraderIdForUpdate(Integer traderId) {
        return super.findById("trader_id",traderId,true,getEntityClass());
    }

    public Account updateAmountById(Integer id,Double amount) {
        if (super.existsById(id)) {
            String str = "UPDATE" + getTableName() + "SET amount = ? WHERE id = ?";
            int row = jdbcTemplate.update(str,amount,id);
            logger.debug("Update amount row =" + row);
            if (row != 1) {
                throw new IncorrectResultSizeDataAccessException(1,row);
            }
            return findById(id);
        }
        return null;
    }
}
