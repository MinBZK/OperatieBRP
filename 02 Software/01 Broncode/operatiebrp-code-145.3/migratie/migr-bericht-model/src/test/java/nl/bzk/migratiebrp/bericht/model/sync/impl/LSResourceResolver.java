/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.InputStream;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.xml.SimpleLSInput;
import org.w3c.dom.ls.LSInput;

public class LSResourceResolver implements org.w3c.dom.ls.LSResourceResolver {

    private static final String SYNC_XSD_PATH = "/xsd/sync/";
    private static final String MIGRATIE_XSD_PATH = "/xsd/";

    @Override
    public final LSInput resolveResource(
            final String type,
            final String namespaceURI,
            final String publicId,
            final String systemId,
            final String baseURI) {

        InputStream resourceAsStream = SyncBerichtFactory.class.getResourceAsStream(MIGRATIE_XSD_PATH + systemId);
        if (resourceAsStream == null) {
            resourceAsStream = SyncBerichtFactory.class.getResourceAsStream(SYNC_XSD_PATH + systemId);
        }
        return new SimpleLSInput(publicId, systemId, resourceAsStream);
    }

}
