package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("http://cloud.iexapis.com/v1/")
    private String IEX_HOST;

    //@Bean
    //public PlatformTransactionManager txManager(DataSource dataSource) {
    //}
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MarketDataConfig marketDataConfig() {
        if (StringUtils.isEmpty(System.getenv("IEX_PUB_TOKEN")) || StringUtils.isEmpty(IEX_HOST)) {
            throw new IllegalArgumentException("IEX_PUB_TOKEN OR IEX_HOST IS EMPTY.");
        }

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("IEX_PUB_token"));
        marketDataConfig.setHost(IEX_HOST);
        return marketDataConfig;
    }

    /**
     * @Bean public DataSource dataSource() {
    }
     *
     *
     */
    /**
     * @Bean public DataSource dataSource() {
     * }
     */


    //http://bit.ly/2tWTmzQ connectionPool
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

    @Bean
    public DataSource dataSource() {
        String jdbcUrl;
        String user;
        String password;

        if (!StringUtil.isEmpty(System.getenv("RDS_HOSTNAME"))) {
            throw new IllegalArgumentException("HOSTNAME doesn't exist!");
        } else {
            jdbcUrl = System.getenv("PSQL_URL");
            user = System.getenv(("PSQL_USER"));
            password = System.getenv("PSQL_PASSWORD");
        }
        logger.error(("jdbc:" + jdbcUrl));
        if (StringUtil.isEmpty(jdbcUrl, user, password)) {
            throw new IllegalArgumentException(("Missing source configenv vars"));
        }
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setPassword(password);
        basicDataSource.setUsername(user);
        return basicDataSource;
    }
}

