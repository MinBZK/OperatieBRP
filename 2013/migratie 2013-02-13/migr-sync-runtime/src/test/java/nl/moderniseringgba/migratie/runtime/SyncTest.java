/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:synchronisatie-beans-test-datasource.xml",
        "classpath:synchronisatie-runtime-beans-test.xml" })
public abstract class SyncTest extends AbstractDatabaseTest {

    @Inject
    private SynchronisatieMessageHandler handler;

    protected SyncBericht handleBericht(final SyncBericht bericht) {
        try {
            return handler.verwerkBericht(bericht);
        } catch (final BerichtOnbekendException exception) {
            return null;
        }
    }
}
