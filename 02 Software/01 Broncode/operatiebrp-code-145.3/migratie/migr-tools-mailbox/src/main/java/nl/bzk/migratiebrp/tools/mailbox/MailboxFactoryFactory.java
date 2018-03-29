/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import javax.inject.Inject;
import nl.bzk.migratiebrp.tools.mailbox.impl.FileMailboxFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.HsqldbMailboxFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.MemoryMailboxFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.PostgresqlMailboxFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory om de juiste mailbox factory te verkrijgen.
 */
public final class MailboxFactoryFactory implements FactoryBean<MailboxFactory> {

    /**
     * In-memory (Map based) mailbox factory.
     */
    public static final String MEMORY_MAILBOX_FACTORY = "memory";
    /**
     * HSQLDB mailbox factory.
     */
    public static final String HSQLDB_MAILBOX_FACTORY = "hsqldb";
    /**
     * Postgresql mailbox factory.
     */
    public static final String POSTGRESQL_MAILBOX_FACTORY = "postgresql";
    /**
     * File (CSV) mailbox factory.
     */
    public static final String FILE_MAILBOX_FACTORY = "file";

    private Boolean inMemoryMailboxFactory;
    private String mailboxType;

    private PostgresqlMailboxFactory postgresqlMailboxFactory;

    /**
     * Constructor.
     * @param postgresqlMailboxFactory postgresqlMailboxFactory
     */
    @Inject
    public MailboxFactoryFactory(final PostgresqlMailboxFactory postgresqlMailboxFactory) {
        this.postgresqlMailboxFactory = postgresqlMailboxFactory;
    }

    /**
     * Gebruik de in-memory mailbox (oude setting).
     * @param inMemoryMailboxFactory true of false
     */
    public void setInMemoryMailboxFactory(final boolean inMemoryMailboxFactory) {
        this.inMemoryMailboxFactory = inMemoryMailboxFactory;
    }

    /**
     * Definieer het te gebruiken mailbox type.
     * @param mailboxType "memory", "hsqldb" of "file"
     */
    public void setMailboxType(final String mailboxType) {
        this.mailboxType = mailboxType;
    }

    @Override
    public MailboxFactory getObject() throws MailboxException {
        if (Boolean.TRUE.equals(inMemoryMailboxFactory)) {
            mailboxType = MEMORY_MAILBOX_FACTORY;
        }

        final MailboxFactory result;
        switch (mailboxType.toLowerCase()) {
            case "inmemory":
            case MEMORY_MAILBOX_FACTORY:
                result = new MemoryMailboxFactory();
                break;
            case "database":
            case "sql":
            case POSTGRESQL_MAILBOX_FACTORY:
                result = postgresqlMailboxFactory;
                break;
            case HSQLDB_MAILBOX_FACTORY:
            case "hsql":
                result = new HsqldbMailboxFactory();
                break;
            default:
                result = new FileMailboxFactory();
                break;
        }
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return MailboxFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
