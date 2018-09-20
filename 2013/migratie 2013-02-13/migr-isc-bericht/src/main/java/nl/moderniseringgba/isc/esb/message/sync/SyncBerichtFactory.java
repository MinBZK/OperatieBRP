/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3AntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3VerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.LeesUitBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.LeesUitBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchronisatieStrategieAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchronisatieStrategieVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.xml.SimpleLSInput;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 * Vertaal een binnengekomen Sync bericht naar een ESB Snc Bericht object.
 */
// CHECKSTYLE:OFF - Class fan-out is hoog door alle verschillende berichten.
public enum SyncBerichtFactory implements BerichtFactory {
    // CHECKSTYLE:ON

    /**
     * We willen een singleton object hebben hiervan.
     */
    SINGLETON;
    private final Schema schema;
    private final JAXBContext jaxbContext;

    private SyncBerichtFactory() {
        try {
            jaxbContext = JAXBContext.newInstance("nl.moderniseringgba.isc.esb.message.sync.generated");
            final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
            schemaFactory.setResourceResolver(maakResolver());

            schema = schemaFactory.newSchema(new Source[] { new StreamSource(SyncBerichtFactory.class
                            .getResourceAsStream("/xsd/SYNC_Berichten.xsd")), });
        } catch (final JAXBException e) {
            throw new RuntimeException("Fout tijdens het initialiseren van de SyncBerichtFactory (JAXBException).", e);
        } catch (final SAXException e) {
            throw new RuntimeException("Fout tijdens het initialiseren van de SyncBerichtFactory (SAXException).", e);
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
     * {@inheritDoc}
     */
    @Override
    public SyncBericht getBericht(final String bericht) {
        try {
            final JAXBElement<?> element =
                    (JAXBElement<?>) createUnmarchaller().unmarshal(new StreamSource(new StringReader(bericht)));
            return maakBericht(element.getValue());

        } catch (final JAXBException je) {
            return new OngeldigBericht(bericht, je.getMessage());
        }
    }

    /**
     * Converteert een JAXB element naar een String.
     * 
     * @param element
     *            Het JAXB element dat geconverteerd dient te worden.
     * @return De string representatie van het JAXB element.
     */
    public String elementToString(final JAXBElement<?> element) {
        final StringWriter resultWriter = new StringWriter();
        try {
            createMarchaller().marshal(element, resultWriter);
            return resultWriter.toString();
        } catch (final JAXBException e) {
            throw new RuntimeException("Fout tijdens het marshallen van een jaxb element.", e);
        }
    }

    /**
     * Geeft de unmarshaller terug.
     * 
     * @return De unmarshaller.
     * 
     * @throws JAXBException
     *             als er een fout optreedt bij het creeren van de Unmarchaller.
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
    // CHECKSTYLE:OFF - Cyclomatic complexity is te hoog doordat alle berichten hierin staan.
    private SyncBericht maakBericht(final Object value) {
        // CHECKSTYLE:ON

        final SyncBericht bericht;
        if (value instanceof BlokkeringVerzoekType) {
            bericht = new BlokkeringVerzoekBericht((BlokkeringVerzoekType) value);
        } else if (value instanceof BlokkeringAntwoordType) {
            bericht = new BlokkeringAntwoordBericht((BlokkeringAntwoordType) value);
        } else if (value instanceof BlokkeringInfoVerzoekType) {
            bericht = new BlokkeringInfoVerzoekBericht((BlokkeringInfoVerzoekType) value);
        } else if (value instanceof BlokkeringInfoAntwoordType) {
            bericht = new BlokkeringInfoAntwoordBericht((BlokkeringInfoAntwoordType) value);
        } else if (value instanceof DeblokkeringVerzoekType) {
            bericht = new DeblokkeringVerzoekBericht((DeblokkeringVerzoekType) value);
        } else if (value instanceof DeblokkeringAntwoordType) {
            bericht = new DeblokkeringAntwoordBericht((DeblokkeringAntwoordType) value);
        } else if (value instanceof SynchroniseerNaarBrpVerzoekType) {
            bericht = new SynchroniseerNaarBrpVerzoekBericht((SynchroniseerNaarBrpVerzoekType) value);
        } else if (value instanceof SynchroniseerNaarBrpAntwoordType) {
            bericht = new SynchroniseerNaarBrpAntwoordBericht((SynchroniseerNaarBrpAntwoordType) value);
        } else if (value instanceof LeesUitBrpVerzoekType) {
            bericht = new LeesUitBrpVerzoekBericht((LeesUitBrpVerzoekType) value);
        } else if (value instanceof LeesUitBrpAntwoordType) {
            bericht = new LeesUitBrpAntwoordBericht((LeesUitBrpAntwoordType) value);
        } else if (value instanceof SynchronisatieStrategieVerzoekType) {
            bericht = new SynchronisatieStrategieVerzoekBericht((SynchronisatieStrategieVerzoekType) value);
        } else if (value instanceof SynchronisatieStrategieAntwoordType) {
            bericht = new SynchronisatieStrategieAntwoordBericht((SynchronisatieStrategieAntwoordType) value);
        } else if (value instanceof ConverteerNaarBrpVerzoekType) {
            bericht = new ConverteerNaarBrpVerzoekBericht((ConverteerNaarBrpVerzoekType) value);
        } else if (value instanceof ConverteerNaarBrpAntwoordType) {
            bericht = new ConverteerNaarBrpAntwoordBericht((ConverteerNaarBrpAntwoordType) value);
        } else if (value instanceof ConverteerNaarLo3VerzoekType) {
            bericht = new ConverteerNaarLo3VerzoekBericht((ConverteerNaarLo3VerzoekType) value);
        } else if (value instanceof ConverteerNaarLo3AntwoordType) {
            bericht = new ConverteerNaarLo3AntwoordBericht((ConverteerNaarLo3AntwoordType) value);
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een SyncBericht. Type: "
                    + value.getClass());
        }
        return bericht;
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
                return new SimpleLSInput(publicId, systemId, SyncBerichtFactory.class.getResourceAsStream("/xsd/"
                        + systemId));
            }
        };
    }
}
