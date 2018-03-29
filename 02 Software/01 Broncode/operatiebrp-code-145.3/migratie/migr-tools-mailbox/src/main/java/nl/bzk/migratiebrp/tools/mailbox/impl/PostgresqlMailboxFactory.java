/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Postgresql Mailbox factory.
 */
public final class PostgresqlMailboxFactory extends AbstractDatabaseMailboxFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public PostgresqlMailboxFactory() {
        LOG.info("Starting PostgreSQL mailbox factory...");
    }

    @Override
    public void destroy() {
        // Niets doen.
    }
}
