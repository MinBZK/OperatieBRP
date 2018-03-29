/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.io.IOException;
import java.io.StringWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * BerichtPersoonslijstWriterTest.
 */
public class BerichtPersoonslijstWriterTest extends BerichtTestUtil {

    @Test
    public void testWriteBerichtPersoonslijst() throws TransformerException, XMLStreamException, IOException, SAXException {
        final BerichtElement berichtElem = maakBerichtElement(true, true, true);
        BerichtPersoonslijstWriter.write(berichtElem, new BerichtWriter(new StringWriter()));
        final String output = geefOutput(
                writer -> BerichtPersoonslijstWriter.write(berichtElem, writer));

        assertGelijk("berichtpersoonslijst1.xml".replaceAll("\\s+", ""), output);
    }

    @Test
    public void testWriteBerichtPersoonslijstGeenAttribuut() throws TransformerException, XMLStreamException, IOException, SAXException {
        final BerichtElement berichtElem = maakBerichtElement(true, true, false);
        BerichtPersoonslijstWriter.write(berichtElem, new BerichtWriter(new StringWriter()));
        final String output = geefOutput(
                writer -> BerichtPersoonslijstWriter.write(berichtElem, writer));

        assertGelijk("berichtpersoonslijst2.xml".replaceAll("\\s+", ""), output);
    }

    @Test
    public void testWriteBerichtPersoonslijstGeenAttribuutGeenKindElement() throws TransformerException, XMLStreamException, IOException, SAXException {
        final BerichtElement berichtElem = maakBerichtElement(true, false, false);
        BerichtPersoonslijstWriter.write(berichtElem, new BerichtWriter(new StringWriter()));
        final String output = geefOutput(
                writer -> BerichtPersoonslijstWriter.write(berichtElem, writer));

        assertGelijk("berichtpersoonslijst3.xml".replaceAll("\\s+", ""), output);
    }


    public BerichtElement maakBerichtElement(final boolean waardeElement, final boolean kindElement, final boolean attribuutElement) {
        final BerichtElement.Builder builder = BerichtElement.builder();

        if (waardeElement) {
            final String naam = "xmlnaam";
            final String waarde = "xmlwaarde";
            builder.metNaam(naam);
            builder.metWaarde(waarde);
        }

        if (kindElement) {
            final String naamKind = "xmlnaamkind";
            final String waardeKind = "xmlwaardekind";
            final BerichtElement.Builder builderKind = BerichtElement.builder();
            builderKind.metNaam(naamKind);
            builderKind.metWaarde(waardeKind);
            builder.metBerichtElement(builderKind);
        }

        if (attribuutElement) {
            final BerichtElementAttribuut.Builder berichtAttr1 = BerichtElementAttribuut.builder();
            final String attrnaam = "attrnaam";
            final String attrwaarde = "attrwaarde";
            berichtAttr1.metNaam(attrnaam);
            berichtAttr1.metWaarde(attrwaarde);
            builder.metBerichtElementAttribuut(berichtAttr1);
        }

        return builder.build();
    }
}