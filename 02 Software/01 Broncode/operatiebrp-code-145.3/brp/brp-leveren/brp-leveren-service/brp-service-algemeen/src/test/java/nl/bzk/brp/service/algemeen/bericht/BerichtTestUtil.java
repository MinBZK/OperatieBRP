/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

/**
 */
public class BerichtTestUtil {

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
    protected Actie actieInhoud = TestVerantwoording.maakActie(1, getVasteDatum());
    protected Actie actieVerval = TestVerantwoording.maakActie(2, getVasteDatum());


    public void assertGelijk(final String expectedPath, final String output) throws IOException, SAXException {

        final InputStream resourceAsStream = BerichtTestUtil.class.getResourceAsStream("/synchronisatiebericht/" + expectedPath);
        final String expected = IOUtils.toString(resourceAsStream);
        final Diff diff = DiffBuilder.compare(expected).withTest(output).ignoreComments().ignoreWhitespace().build();

        if (diff.hasDifferences()) {
            System.err.println("Expected:");
            System.err.println(expected);
            System.err.println();
            System.err.println("Actual:");
            System.err.println(output);
            System.err.println();
            System.err.println("Verschillen:");
            for (Difference difference : diff.getDifferences()) {
                System.err.println(difference);
            }
        }

        Assert.assertFalse(diff.hasDifferences());
    }

    public String geefOutput(BerichtWriterDelegate delegate) throws XMLStreamException, TransformerException {
        final StringWriter outputWriter = new StringWriter();
        final BerichtWriter bw = new BerichtWriter(outputWriter);
        bw.writeStartDocument();
        bw.startElement("test");
        bw.writeNamespace();

        delegate.write(bw);

        bw.endElement();
        bw.flush();
        return indent(outputWriter.toString());
    }

    public String indent(String input) throws TransformerException {
        // Instantiate transformer input
        Source xmlInput = new StreamSource(new StringReader(input));
        final StringWriter outputWriter = new StringWriter();
        final StreamResult xmlOutput = new StreamResult(outputWriter);

        // Configure transformer
        final Transformer transformer = TRANSFORMER_FACTORY
                .newTransformer(); // An identity transformer
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(xmlInput, xmlOutput);
        return outputWriter.toString();
    }

    public ZonedDateTime getVasteDatum() {
        return getVasteDatum(0);
    }

    public ZonedDateTime getVasteDatum(int dagOffset) {
        return ZonedDateTime.of(2010, 3, 22, 4, 10, 30, 17000000, DatumUtil.BRP_ZONE_ID).plus(dagOffset, ChronoUnit.DAYS);
    }

    public interface BerichtWriterDelegate {
        void write(BerichtWriter writer) throws XMLStreamException;
    }
}
