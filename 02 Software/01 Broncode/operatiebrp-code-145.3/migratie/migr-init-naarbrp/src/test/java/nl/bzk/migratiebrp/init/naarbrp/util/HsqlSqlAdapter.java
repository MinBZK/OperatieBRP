/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class HsqlSqlAdapter implements FactoryBean<Resource>, InitializingBean {

    private Resource sqlResource;
    private Resource hsqlSqlResource;

    @Required
    public void setSqlResource(final Resource sqlResource) {
        this.sqlResource = sqlResource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Fully read resource
        final String sql = IOUtils.toString(sqlResource.getInputStream());

        // Adapt SQL to Hsql SQL
        final String hsqlSql = adaptSql(sql);

        System.out.println("ADAPTED SQL:\n" + hsqlSql);

        // Prepare resource
        hsqlSqlResource = new ByteArrayResource(hsqlSql.getBytes());
    }

    /**
     * Adapt SQL naar Hsql SQL.
     *
     * - Verwijder UNLOGGED (CREATE UNLOGGED TABLE)
     * @return hsql sql
     */
    private String adaptSql(final String sql) {
        // Remove 'UNLOGGED' from CREATE TABLE statements
        String result = sql.replaceAll("(?i)(\\sUNLOGGED\\s)", " /* REMOVED FOR HSQLDB:$1*/ ");
        // Remove INDEXES (HSQLDB cannot use partial indexes)
        result = result.replaceAll("(?is)(CREATE\\s+(UNIQUE\\s+)?INDEX.*?;)", "/* REMOVED FOR HSQLDB: $1 */");
        // Remove WITH (autovacuum_vacuum_scale_factor=0.05)
        result = result.replaceAll("(?is)(WITH \\(autovacuum_vacuum_scale_factor=0\\.05\\))", "/* REMOVED FOR HSQLDB: $1 */");
        return result;
    }

    @Override
    public Resource getObject() throws Exception {
        return hsqlSqlResource;
    }

    @Override
    public Class<?> getObjectType() {
        return Resource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
