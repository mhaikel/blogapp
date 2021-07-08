package com.afam.backendapistest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {
    private final Map<String, String> dataSourceJndis = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DatasourceConfig.class);

    @Value("${datasource.jndi-name}")
    private String[] dataSourceDefs;

    @Autowired
    public DatasourceConfig(Environment environment) {
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Lazy()
    public JdbcTemplate jdbcTemplate() throws NamingException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(jndiDataSource());

        CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);

        return jdbcTemplate;
    }

    private void populateJndiMap() {
        for (String jndiEntry : dataSourceDefs) {
            logger.info("Populating JNDI wih entry: " + jndiEntry);
            dataSourceJndis.put(jndiEntry.split(":")[0].trim(), jndiEntry.split(":")[1].trim());
        }
    }

    @Bean(destroyMethod = "")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        logger.info(" ++++ Initializing Datasource ++++ ");

        if (dataSourceJndis.isEmpty()) {
            logger.info(" ++++ dataSourceJndis is empty ++++ ");
            populateJndiMap();
        }

        logger.info(" ++++ Fetching SADCWARRI Jndi ++++ ");

        DataSource dataSource = dataSourceLookup.getDataSource(dataSourceJndis.get("sadcwarri"));

        logger.info(" ++++ SADCWARRI Jndi Initialized ++++ ");

        return dataSource;
    }

    /*@Bean(destroyMethod = "")
    public AffiliateDataSources getAffiliateDataSources() throws IllegalArgumentException, NamingException {
        AffiliateDataSources dataSources = new AffiliateDataSources();
        if (dataSourceJndis.isEmpty()) {
            populateJndiMap();
        }
        for (String key : dataSourceJndis.keySet()) {
            if (key.equals("sadcwarri")) {
                continue;
            }
            JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

            logger.info(String.format("JNDI Key: %s", dataSourceJndis.get(key)));

            DataSource dataSource = dataSourceLookup.getDataSource(dataSourceJndis.get(key));
            dataSources.addDataSource(key, dataSource);

            logger.info(String.format("Initialized Data Source for '%s' ...", key));
        }
        return dataSources;
    }*/

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws NamingException {
        return new DataSourceTransactionManager(jndiDataSource());
    }

}
