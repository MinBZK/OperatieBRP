/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import static org.junit.Assert.*;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import org.junit.Test;

/**
 * Testen voor {@link BerichtTransformatieService}.
 */
public class BerichtTransformatieServiceTest {

    private final static String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><iets1>1</iets1><iets2><iets3>2</iets3></iets2></xml>";
    private final static String XML_FOUT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><iets1>1<iets2><iets3>2</iets3></iets2></xml>";
    private final BerichtTransformatieService service = new BerichtTransformatieService();

    @Test
    public void test() throws WriteException, TransformerException {
        final DOMSource domSource = service.transformeerNaarDOMSource(XML);
        String string = service.transformeerNaarString(domSource).replace(" standalone=\"no\"", "");
        assertEquals(XML, string);
        string = service.transformeerNaarString(domSource).replace(" standalone=\"no\"", "");
        assertEquals(XML, string);
    }

    @Test(expected = WriteException.class)
    public void testWriteException() throws WriteException, TransformerException {
        service.transformeerNaarDOMSource(XML_FOUT);
        fail();
    }
}
