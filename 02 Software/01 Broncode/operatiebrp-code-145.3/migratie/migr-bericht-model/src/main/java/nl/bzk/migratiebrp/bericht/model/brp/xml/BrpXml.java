/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp.xml;

import java.io.InputStream;
import nl.bzk.migratiebrp.bericht.model.xml.AbstractXml;
import nl.bzk.migratiebrp.bericht.model.xml.SimpleLSInput;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Brp XML support.
 */
public final class BrpXml extends AbstractXml {
    /**
     * Singleton.
     */
    public static final BrpXml SINGLETON = new BrpXml();

    private static final String BRP_XSD_PATH = "/xsd/BRP0200/";

    /**
     * Constructor.
     */
    private BrpXml() {
        super("/xsd/brp/BRP_Berichten.xsd", "nl.bzk.migratiebrp.bericht.model.brp.generated");
    }

    @Override
    protected LSResourceResolver maakResolver() {
        return new LocalLSResourceResolver();
    }

    /**
     * Resource resolver.
     */
    public static final class LocalLSResourceResolver implements LSResourceResolver {
        @Override
        public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId, final String baseURI) {
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + systemId);

            if (resourceAsStream == null) {
                resourceAsStream = getClass().getResourceAsStream(BRP_XSD_PATH + systemId);
            }
            if (resourceAsStream == null) {
                throw new IllegalArgumentException("Kan XSD resource '" + systemId + "' niet vinden.");
            }

            return new SimpleLSInput(publicId, systemId, resourceAsStream);
        }
    }
}
