/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.notificatie.xml;

import java.io.InputStream;
import nl.bzk.migratiebrp.bericht.model.xml.AbstractXml;
import nl.bzk.migratiebrp.bericht.model.xml.SimpleLSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Sync XML Support.
 */
public final class NotificatieXml extends AbstractXml {
    /**
     * Singleton.
     */
    public static final NotificatieXml SINGLETON = new NotificatieXml();

    private NotificatieXml() {
        super("/xsd/notificatie/NOTIFICATIE_Berichten.xsd", "nl.bzk.migratiebrp.bericht.model.notificatie.generated");
    }

    @Override
    protected LSResourceResolver maakResolver() {
        return (type, namespaceURI, publicId, systemId, baseURI) -> {
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + systemId);

            if (resourceAsStream == null) {
                resourceAsStream = getClass().getResourceAsStream("/xsd/BRP0200/" + systemId);
            }
            if (resourceAsStream == null) {
                throw new IllegalArgumentException("Kan XSD resource '" + systemId + "' niet vinden.");
            }

            return new SimpleLSInput(publicId, systemId, resourceAsStream);
        };
    }
}
