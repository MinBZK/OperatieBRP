/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import java.io.StringReader;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 */
public class XmlUtilsTest {

    @Test
    public void test() throws MaakDomSourceException {
        XmlUtils.toDOMSource(new InputSource(new StringReader("<bla></bla>")));
    }
}
