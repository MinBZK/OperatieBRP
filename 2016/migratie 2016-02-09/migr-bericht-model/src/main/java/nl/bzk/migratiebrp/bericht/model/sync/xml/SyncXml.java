/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.xml;

import java.io.InputStream;
import nl.bzk.migratiebrp.bericht.model.xml.AbstractXml;
import nl.bzk.migratiebrp.bericht.model.xml.SimpleLSInput;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Sync XML Support.
 */
public final class SyncXml extends AbstractXml {
    /**
     * Singleton.
     */
    public static final SyncXml SINGLETON = new SyncXml();

    private SyncXml() {
        super("/xsd/sync/SYNC_Berichten.xsd", "nl.bzk.migratiebrp.bericht.model.sync.generated");
    }

    @Override
    protected LSResourceResolver maakResolver() {
        return new LSResourceResolver() {
            @Override
            public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId, final String baseURI)
            {
                final InputStream resourceAsStream = getClass().getResourceAsStream("/xsd/sync/" + systemId);
                return new SimpleLSInput(publicId, systemId, resourceAsStream);
            }
        };
    }
}
