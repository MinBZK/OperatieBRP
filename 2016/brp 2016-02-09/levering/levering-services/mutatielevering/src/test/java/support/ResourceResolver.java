/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import java.io.InputStream;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class ResourceResolver implements LSResourceResolver {

    public final LSInput resolveResource(final String type, final String namespaceURI, final String publicId,
        final String systemId, final String baseURI)
    {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("xsd/BRP0200/" + systemId);
        return new Input(publicId, systemId, resourceAsStream);
    }
}
