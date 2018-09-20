/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.domein.ber.Richting;


/**
 * Specifieke logger ten behoeve van het archiveren/loggen van berichten. Deze logger schrijft de te loggen berichten
 * weg via de archiveringsservice (zie {@link ArchiveringService}.
 */
public class UitgaandeArchiveringServiceLogger extends Logger {

    @Inject
    private ArchiveringService archiveringService;

    /**
     * Standaard constructor die de logger initialiseert en een lokale handler zet die naar de archivering service
     * logt.
     */
    public UitgaandeArchiveringServiceLogger() {
        super("uitgaandeArchiveringServiceLogger", null);

        setUseParentHandlers(false);

        addHandler(new Handler() {

            @Override
            public void publish(final LogRecord record) {
                archiveringService.archiveer(record.getMessage(), Richting.UITGAAND);
            }

            @Override
            public void flush() {
                // Do Nothing
            }

            @Override
            public void close() {
                // Do Nothing
            }

        });
    }
}
