package nl.bzk.brp.funqmachine

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean
import javax.sql.DataSource
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.configuratie.DatabaseConfig
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.migratiebrp.util.common.UniqueName
import org.apache.commons.dbcp.BasicDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
/**
 * Spring configuratie klasse voor de datasource.
 * Deze is de koppeling tussen spring configuratie en het door de
 * FunqMachine gebruikte {@link Environment}. Deze koppeling is nodig
 * voor de data-taal, die deze configuratie nodig heeft om personen te
 * kunnen opslaan in de database die door de FunqMachine wordt gebruikt.
 *
 * @see Environment
 * @see config/one-datasource-beans.xml
 */
@Configuration
class DataSourceConfiguration {

    private static UniqueName uniqueName = new UniqueName()

    static {
        uniqueName.baseName = 'syncDalDatabase'
    }

    @Bean(destroyMethod = 'close', name = 'lezenSchrijvenDataSource')
    public DataSource lezenSchrijvenDataSource() {

        DatabaseConfig dbConfig = Environment.instance().getGetDatabaseConfig(Database.KERN)

        DataSource ds = new BasicDataSource()
        ds.driverClassName = (dbConfig.driverClassName)
        ds.username = (dbConfig.username)
        ds.password = (dbConfig.password)
        ds.url = (dbConfig.url)

        ds.defaultTransactionIsolation = 2

        ds.initialSize = 1
        ds.maxWait = 3000
        ds.maxActive = 30
        ds.testWhileIdle = true
        ds.validationQuery = 'SELECT 41+1'

        ds
    }

    @Bean(destroyMethod = 'close', name = 'berlezenSchrijvenDataSource')
    public DataSource berlezenSchrijvenDataSource() {

        DatabaseConfig dbConfig = Environment.instance().getGetDatabaseConfig(Database.BER)

        DataSource ds = new BasicDataSource()
        ds.driverClassName = (dbConfig.driverClassName)
        ds.username = (dbConfig.username)
        ds.password = (dbConfig.password)
        ds.url = (dbConfig.url)

        ds.defaultTransactionIsolation = 2

        ds.initialSize = 1
        ds.maxWait = 3000
        ds.maxActive = 30
        ds.testWhileIdle = true
        ds.validationQuery = 'SELECT 41+1'

        ds
    }

    @Bean(initMethod = 'init',  destroyMethod = 'close', name = 'syncDalDataSource')
    public DataSource migratieDataSource() {
        DatabaseConfig dbConfig = Environment.instance().getGetDatabaseConfig(Database.KERN)

        AtomikosNonXADataSourceBean ds =new AtomikosNonXADataSourceBean();
        ds.setUniqueResourceName(uniqueName.getObject())

        String host = dbConfig.url.substring(dbConfig.url.indexOf('//') + 2, dbConfig.url.lastIndexOf('/'))
        int poort = 5432
        String dbName = dbConfig.url.substring(dbConfig.url.lastIndexOf('/') + 1);

        ds.setUrl(String.format("jdbc:postgresql://%s:%d/%s", host, poort, dbName))
        ds.setDriverClassName('org.postgresql.Driver')
        ds.setUser(dbConfig.username)
        ds.setPassword(dbConfig.password)

        ds.setMaxPoolSize(20)
        ds.setMinPoolSize(10)

        ds

/*        <bean id="syncDalDataSource" class="com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean">
        <property name="uniqueResourceName">
            &lt;!&ndash;
        | Tijdens jUnit testen kan de ApplicationContext meerdere keren worden geinstantieerd.
            | Elke keer dient een unique naam gebruikt te worden, anders gaat Atomikos fout.
        &ndash;&gt;
        <bean class="nl.bzk.migratiebrp.util.common.UniqueName">
        <property name="baseName" value="syncDatabase"/>
        </bean>
        </property>
        <property name="url" value="jdbc:postgresql://${db.ServerName}:${db.PortNumber}/brp"/>
        <property name="driverClassName" value="org.postgresql.Driver"/>
        <property name="user" value="brp"/>
        <property name="password" value="brp"/>
        <property name="maxPoolSize" value="20"/>
        <property name="minPoolSize" value="10"/>*/

//        AtomikosDataSourceBean ds = new AtomikosDataSourceBean()
//
//        uniqueName.baseName =
//        ds.uniqueResourceName = uniqueName.getObject()
//
//        ds.xaDataSourceClassName = 'org.postgresql.xa.PGXADataSource'
//        ds.xaProperties.setProperty('ServerName', dbConfig.url.substring(dbConfig.url.indexOf('//') + 2, dbConfig.url.lastIndexOf('/')))
//        ds.xaProperties.setProperty('PortNumber', '5432')
//        ds.xaProperties.setProperty('DatabaseName', dbConfig.url.substring(dbConfig.url.lastIndexOf('/') + 1))
//        ds.xaProperties.setProperty('User', dbConfig.username)
//        ds.xaProperties.setProperty('Password', dbConfig.password)
//
//        ds.maxPoolSize = 20
//        ds.minPoolSize = 3
//        ds.testQuery = 'SELECT 41+1'
//
//        ds

    }

    @Bean(destroyMethod = 'close', name = 'alleenLezenDataSource')
    public DataSource alleenLezenDataSource() {
        DatabaseConfig dbConfig = Environment.instance().getGetDatabaseConfig(Database.KERN)

        DataSource ds = new BasicDataSource()
        ds.driverClassName = (dbConfig.driverClassName)
        ds.username = (dbConfig.username)
        ds.password = (dbConfig.password)
        ds.url = (dbConfig.url)

        ds.defaultTransactionIsolation = 2

        ds.initialSize = 1
        ds.maxWait = 3000
        ds.testWhileIdle = true
        ds.validationQuery = 'SELECT 41+1'

        ds
    }

}
