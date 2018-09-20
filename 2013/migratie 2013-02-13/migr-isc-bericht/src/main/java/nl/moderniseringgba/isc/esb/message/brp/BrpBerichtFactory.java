/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtFactory;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningNotarieelAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningNotarieelVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVernietigingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVernietigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeboorteVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GerechtelijkeVaststellingVaderschapAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GerechtelijkeVaststellingVaderschapVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsnaamwijzigingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsnaamwijzigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvVerhuizingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvVerhuizingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapDoorMoederAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapDoorMoederVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OverlijdenAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.OverlijdenVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.SynchronisatieSignaalType;
import nl.moderniseringgba.isc.esb.message.brp.generated.TransseksualiteitAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.TransseksualiteitVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VoornaamswijzigingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VoornaamswijzigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.WijzigingANummerSignaalType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningNotarieelAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningNotarieelVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVernietigingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVernietigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GerechtelijkeVaststellingVaderschapAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GerechtelijkeVaststellingVaderschapVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeslachtsnaamwijzigingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeslachtsnaamwijzigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvVerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvVerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapDoorMoederAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapDoorMoederVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OverlijdenAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OverlijdenVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.SynchronisatieSignaalBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.TransseksualiteitAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.TransseksualiteitVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VoornaamswijzigingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VoornaamswijzigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.xml.SimpleLSInput;
import nl.moderniseringgba.isc.esb.message.xml.SimpleValidationErrorHandler;

import org.w3c.dom.Document;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Vertaal een binnengekomen BRP bericht naar een ESB BRP Bericht object.
 */
// CHECKSTYLE:OFF - Class fan-out is hoog door alle verschillende berichten.
public enum BrpBerichtFactory implements BerichtFactory {
    // CHECKSTYLE:ON

    /**
     * Singleton object.
     */
    SINGLETON;

    private static final String BRP_XSD_PATH = "/xsd/brp/";
    private static final String MIGRATIE_XSD_PATH = "/xsd/";
    private final DocumentBuilder builder;
    private final JAXBContext jaxbContext;
    private final Schema schema;

    /**
     * Constructor.
     */
    private BrpBerichtFactory() {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            jaxbContext = JAXBContext.newInstance("nl.moderniseringgba.isc.esb.message.brp.generated");
            final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
            schemaFactory.setResourceResolver(maakResolver());

            factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                    .getResourceAsStream("/xsd/BRP_Berichten.xsd")), }));
            schema = factory.getSchema();

            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleValidationErrorHandler());

        } catch (final JAXBException e) {
            throw new RuntimeException("JAXB Fout tijdens het initialiseren van de BrpBerichtFactory.", e);
        } catch (final SAXException e) {
            throw new RuntimeException("SAX Fout tijdens het initialiseren van de BrpBerichtFactory.", e);
        } catch (final ParserConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Marshaller createMarchaller() throws JAXBException {
        return jaxbContext.createMarshaller();
    }

    private Unmarshaller createUnmarchaller() throws JAXBException {
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        return unmarshaller;
    }

    /**
     * Vertaal een binnengekomen BRP bericht naar een ESB BRP Bericht object.
     * 
     * @param berichtAlsString
     *            binnengekomen BRP bericht
     * @return ESB BRP Bericht
     */
    @Override
    public BrpBericht getBericht(final String berichtAlsString) {

        try {
            final Document document = builder.parse(new InputSource(new StringReader(berichtAlsString)));

            final JAXBElement<?> element =
                    (JAXBElement<?>) createUnmarchaller().unmarshal(
                            new StreamSource(new StringReader(berichtAlsString)));

            final BrpBericht bericht = maakBericht(element.getValue());
            bericht.parse(document);

            return bericht;

            // CHECKSTYLE:OFF Omdat we hier een flinke lijst excepties zouden moeten afvangen, is ervoor gekozen om
            // throwable te vangen. Dit is tegen de checkstyle regels.
        } catch (final Throwable t) {
            // ChECKSTYLE:ON
            return new OngeldigBericht(berichtAlsString, t.getMessage());
        }
    }

    /**
     * Converteert een JAXB element naar een String.
     * 
     * @param element
     *            Het JAXB element dat geconverteerd dient te worden.
     * @return De string representatie van het JAXB element.
     * @throws BerichtInhoudException
     *             In het geval van een probleem bij het unmarshallen.
     */
    public String elementToString(final JAXBElement<?> element) throws BerichtInhoudException {
        final StringWriter resultWriter = new StringWriter();
        try {
            createMarchaller().marshal(element, resultWriter);
            return resultWriter.toString();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException("Fout tijdens het marshallen van een jaxb element.", e);
        }
    }

    /**
     * Geeft de unmarshaller terug.
     * 
     * @return De unmarshaller.
     * @throws JAXBException
     *             In het geval er een JAXB probleem optreedt.
     */
    public Unmarshaller getUnmarshaller() throws JAXBException {
        return createUnmarchaller();
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     * 
     * @param value
     *            De meegegeven value.
     * @return Het bericht.
     */
    private BrpBericht maakBericht(final Object value) {

        final BrpBericht result;

        if (value instanceof ErkenningAntwoordType) {
            result = new ErkenningAntwoordBericht((ErkenningAntwoordType) value);
        } else if (value instanceof ErkenningVerzoekType) {
            result = new ErkenningVerzoekBericht((ErkenningVerzoekType) value);
        } else if (value instanceof ErkenningNotarieelAntwoordType) {
            result = new ErkenningNotarieelAntwoordBericht((ErkenningNotarieelAntwoordType) value);
        } else if (value instanceof ErkenningNotarieelVerzoekType) {
            result = new ErkenningNotarieelVerzoekBericht((ErkenningNotarieelVerzoekType) value);
        } else if (value instanceof ErkenningVernietigingAntwoordType) {
            result = new ErkenningVernietigingAntwoordBericht((ErkenningVernietigingAntwoordType) value);
        } else if (value instanceof ErkenningVernietigingVerzoekType) {
            result = new ErkenningVernietigingVerzoekBericht((ErkenningVernietigingVerzoekType) value);
        } else if (value instanceof OntkenningVaderschapAntwoordType) {
            result = new OntkenningVaderschapAntwoordBericht((OntkenningVaderschapAntwoordType) value);
        } else if (value instanceof OntkenningVaderschapVerzoekType) {
            result = new OntkenningVaderschapVerzoekBericht((OntkenningVaderschapVerzoekType) value);
        } else if (value instanceof OntkenningVaderschapDoorMoederAntwoordType) {
            result =
                    new OntkenningVaderschapDoorMoederAntwoordBericht(
                            (OntkenningVaderschapDoorMoederAntwoordType) value);
        } else if (value instanceof OntkenningVaderschapDoorMoederVerzoekType) {
            result =
                    new OntkenningVaderschapDoorMoederVerzoekBericht(
                            (OntkenningVaderschapDoorMoederVerzoekType) value);
        } else if (value instanceof GeboorteVerzoekType) {
            result = new GeboorteVerzoekBericht((GeboorteVerzoekType) value);
        } else if (value instanceof GeboorteAntwoordType) {
            result = new GeboorteAntwoordBericht((GeboorteAntwoordType) value);
        } else if (value instanceof MvGeboorteVerzoekType) {
            result = new MvGeboorteVerzoekBericht((MvGeboorteVerzoekType) value);
        } else if (value instanceof MvGeboorteAntwoordType) {
            result = new MvGeboorteAntwoordBericht((MvGeboorteAntwoordType) value);
        } else if (value instanceof MvVerhuizingVerzoekType) {
            result = new MvVerhuizingVerzoekBericht((MvVerhuizingVerzoekType) value);
        } else if (value instanceof MvVerhuizingAntwoordType) {
            result = new MvVerhuizingAntwoordBericht((MvVerhuizingAntwoordType) value);
        } else if (value instanceof NotificatieVerzoekType) {
            result = new NotificatieVerzoekBericht((NotificatieVerzoekType) value);
        } else if (value instanceof NotificatieAntwoordType) {
            result = new NotificatieAntwoordBericht((NotificatieAntwoordType) value);
        } else if (value instanceof SynchronisatieSignaalType) {
            result = new SynchronisatieSignaalBericht((SynchronisatieSignaalType) value);
        } else if (value instanceof VerhuizingVerzoekType) {
            result = new VerhuizingVerzoekBericht((VerhuizingVerzoekType) value);
        } else if (value instanceof VerhuizingAntwoordType) {
            result = new VerhuizingAntwoordBericht((VerhuizingAntwoordType) value);
        } else if (value instanceof WijzigingANummerSignaalType) {
            result = new WijzigingANummerSignaalBericht((WijzigingANummerSignaalType) value);
        } else if (value instanceof ZoekPersoonVerzoekType) {
            result = new ZoekPersoonVerzoekBericht((ZoekPersoonVerzoekType) value);
        } else if (value instanceof ZoekPersoonAntwoordType) {
            result = new ZoekPersoonAntwoordBericht((ZoekPersoonAntwoordType) value);
        } else if (value instanceof GeslachtsnaamwijzigingVerzoekType) {
            result = new GeslachtsnaamwijzigingVerzoekBericht((GeslachtsnaamwijzigingVerzoekType) value);
        } else if (value instanceof GeslachtsnaamwijzigingAntwoordType) {
            result = new GeslachtsnaamwijzigingAntwoordBericht((GeslachtsnaamwijzigingAntwoordType) value);
        } else if (value instanceof VoornaamswijzigingVerzoekType) {
            result = new VoornaamswijzigingVerzoekBericht((VoornaamswijzigingVerzoekType) value);
        } else if (value instanceof VoornaamswijzigingAntwoordType) {
            result = new VoornaamswijzigingAntwoordBericht((VoornaamswijzigingAntwoordType) value);
        } else if (value instanceof TransseksualiteitVerzoekType) {
            result = new TransseksualiteitVerzoekBericht((TransseksualiteitVerzoekType) value);
        } else if (value instanceof TransseksualiteitAntwoordType) {
            result = new TransseksualiteitAntwoordBericht((TransseksualiteitAntwoordType) value);
        } else if (value instanceof OverlijdenVerzoekType) {
            result = new OverlijdenVerzoekBericht((OverlijdenVerzoekType) value);
        } else if (value instanceof OverlijdenAntwoordType) {
            result = new OverlijdenAntwoordBericht((OverlijdenAntwoordType) value);
        } else if (value instanceof GerechtelijkeVaststellingVaderschapVerzoekType) {
            result =
                    new GerechtelijkeVaststellingVaderschapVerzoekBericht(
                            (GerechtelijkeVaststellingVaderschapVerzoekType) value);
        } else if (value instanceof GerechtelijkeVaststellingVaderschapAntwoordType) {
            result =
                    new GerechtelijkeVaststellingVaderschapAntwoordBericht(
                            (GerechtelijkeVaststellingVaderschapAntwoordType) value);
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een BrpBericht. Type: "
                    + value.getClass());
        }
        return result;
    }

    /**
     * Maakte een resolver om recursief alle XSD's te resolven.
     * 
     * @return De gemaakte resolver.
     */
    private LSResourceResolver maakResolver() {
        return new LSResourceResolver() {
            @Override
            public LSInput resolveResource(
                    final String type,
                    final String namespaceURI,
                    final String publicId,
                    final String systemId,
                    final String baseURI) {

                InputStream resourceAsStream =
                        BrpBerichtFactory.class.getResourceAsStream(MIGRATIE_XSD_PATH + systemId);
                if (resourceAsStream == null) {
                    resourceAsStream = BrpBerichtFactory.class.getResourceAsStream(BRP_XSD_PATH + systemId);
                }
                return new SimpleLSInput(publicId, systemId, resourceAsStream);
            }
        };
    }

}
