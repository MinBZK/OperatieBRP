/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.mvi;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.mvi.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.xml.SimpleLSInput;
import nl.moderniseringgba.isc.esb.message.xml.SimpleValidationErrorHandler;
import nl.moderniseringgba.isc.esb.message.xml.XmlValidatieExceptie;

import org.w3c.dom.Document;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Vertaal een binnengekomen MVI bericht naar een ESB MVI Bericht object.
 */
// CHECKSTYLE:OFF - Class fan-out is hoog door alle verschillende excepties
public final class MviBerichtFactory implements BerichtFactory {
    // CHECKSTYLE:ON
    private final DocumentBuilder builder;

    /**
     * Constructor.
     */
    public MviBerichtFactory() {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(maakResolver());
        try {
            factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                    .getResourceAsStream("/xsd/MVI_Berichten.xsd")), }));
        } catch (final SAXException e) {
            throw new XmlValidatieExceptie("Kan xsd niet laden", e);
        }

        try {
            builder = factory.newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
        builder.setErrorHandler(new SimpleValidationErrorHandler());
    }

    private LSResourceResolver maakResolver() {
        return new LSResourceResolver() {
            @Override
            public LSInput resolveResource(
                    final String type,
                    final String namespaceURI,
                    final String publicId,
                    final String systemId,
                    final String baseURI) {
                return new SimpleLSInput(publicId, systemId, BrpBerichtFactory.class.getResourceAsStream("/xsd/"
                        + systemId));
            }
        };
    }

    /**
     * Vertaal een binnengekomen MVI bericht naar een ESB MVI Bericht object.
     * 
     * @param inhoud
     *            binnengekomen MVI bericht
     * @return ESB MVI Bericht
     */
    @Override
    public MviBericht getBericht(final String inhoud) {

        try {
            final Document document = builder.parse(new InputSource(new StringReader(inhoud)));

            final BerichtType berichtType =
                    BerichtType.valueOf(document.getDocumentElement().getLocalName().toUpperCase());

            final MviBericht bericht = berichtType.getBericht();
            bericht.parse(document);

            return bericht;

            // CHECKSTYLE:OFF Omdat we hier een flinke lijst excepties zouden moeten afvangen, is ervoor gekozen om
            // throwable te vangen. Dit is tegen de checkstyle regels.
        } catch (final Throwable t) {
            // ChECKSTYLE:ON
            return new OngeldigBericht(inhoud, t.getMessage());
        }
    }

    /**
     * Berichttypen.
     */
    private static enum BerichtType {
        // @formatter:off
        PL_SYNC { @Override MviBericht getBericht() { return new PlSyncBericht(); }  };
        // @formatter:on

        /**
         * Geef een ESB MVI bericht instantie voor dit bericht type.
         * 
         * @return ESB MVI bericht object
         */
        abstract MviBericht getBericht();
    }
}
