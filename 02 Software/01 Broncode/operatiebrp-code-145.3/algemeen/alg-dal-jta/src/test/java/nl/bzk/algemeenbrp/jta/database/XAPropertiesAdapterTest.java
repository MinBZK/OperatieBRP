/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta.database;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


public class XAPropertiesAdapterTest {

    @Test
    public void test() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        Assert.assertEquals(Properties.class, subject.getObjectType());
        Assert.assertEquals(true, subject.isSingleton());
    }

    @Test
    public void testPostgreSQL() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        subject.setDriver("org.postgresql.xa.PGXADataSource");
        subject.setHost("host");
        subject.setPort(5432);
        subject.setName("naam");
        subject.setUser("user");
        subject.setPassword("pass");

        subject.afterPropertiesSet();
        final Properties result = subject.getObject();
        Assert.assertEquals("host", result.getProperty("ServerName"));
        Assert.assertEquals("naam", result.getProperty("DatabaseName"));
        Assert.assertEquals("5432", result.getProperty("PortNumber"));
        Assert.assertEquals("user", result.getProperty("User"));
        Assert.assertEquals("pass", result.getProperty("Password"));
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void testPostgreSQLMinimal() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        subject.setDriver("org.postgresql.xa.PGXADataSource");
        subject.setHost("host");
        subject.setName("naam");

        subject.afterPropertiesSet();
        final Properties result = subject.getObject();
        Assert.assertEquals("host", result.getProperty("ServerName"));
        Assert.assertEquals("naam", result.getProperty("DatabaseName"));
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testHsqlDB() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        subject.setDriver("org.hsqldb.jdbc.pool.JDBCXADataSource");
        subject.setHost("host");
        subject.setPort(9001);
        subject.setName("naam");
        subject.setUser("user");
        subject.setPassword("pass");

        subject.afterPropertiesSet();
        final Properties result = subject.getObject();
        Assert.assertEquals("jdbc:hsqldb:hsql://host:9001/naam", result.getProperty("Url"));
        Assert.assertEquals("user", result.getProperty("User"));
        Assert.assertEquals("pass", result.getProperty("Password"));
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void testHsqlDBMinimal() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        subject.setDriver("org.hsqldb.jdbc.pool.JDBCXADataSource");
        subject.setHost("host");
        subject.setName("naam");

        subject.afterPropertiesSet();
        final Properties result = subject.getObject();
        Assert.assertEquals("jdbc:hsqldb:hsql://host/naam", result.getProperty("Url"));
        Assert.assertEquals(1, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupported() {
        final XAPropertiesAdapter subject = new XAPropertiesAdapter();
        subject.setDriver("org.unknown.jdbc.xa.DataSource");
        subject.setHost("host");
        subject.setName("naam");

        subject.afterPropertiesSet();
    }
}
