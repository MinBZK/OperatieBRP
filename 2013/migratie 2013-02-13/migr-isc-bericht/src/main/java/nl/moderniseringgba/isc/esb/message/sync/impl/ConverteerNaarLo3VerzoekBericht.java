/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3VerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Verzoek bericht voor het converteren van gegevens van BRP naar LO3.
 */
// CHECKSTYLE:OFF - Class fan-out is hoog door het parsen van het any element (= de BRP persoonslijst).
public final class ConverteerNaarLo3VerzoekBericht extends AbstractSyncBericht {
    // CHECKSTYLE:ON

    private static final long serialVersionUID = 1L;

    private ConverteerNaarLo3VerzoekType type;

    /**
     * Default constructor.
     */
    public ConverteerNaarLo3VerzoekBericht() {
        type = new ConverteerNaarLo3VerzoekType();
    }

    /**
     * Convenient constructor.
     * 
     * @param type
     *            Het ConverteerNaarLo3VerzoekType met daarin de informatie.
     */
    public ConverteerNaarLo3VerzoekBericht(final ConverteerNaarLo3VerzoekType type) {
        this.type = type;
    }

    /**
     * Convenient constructor.
     * 
     * @param brpPl
     *            De BRP persoonslijst die gevonverteerd dient te worden.
     */
    public ConverteerNaarLo3VerzoekBericht(final BrpPersoonslijst brpPl) {
        this();
        setBrpPersoonslijst(brpPl);
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            type =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, ConverteerNaarLo3VerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ConverteerNaarLo3VerzoekBericht bericht.", e);
        }
    }

    @Override
    public String getBerichtType() {
        return "ConverteerNaarLo3Verzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory().createConverteerNaarLo3Verzoek(type));
    }

    /**
     * Geeft de BRP persoonslijst terug.
     * 
     * @return De BRP persoonslijst.
     */
    public BrpPersoonslijst getBrpPersoonslijst() {
        if (type.getAny().isEmpty()) {
            return null;
        } else {
            try {
                final Element anyElement = (Element) type.getAny().get(0);
                final Transformer transformer = TransformerFactory.newInstance().newTransformer();
                final ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(anyElement), new StreamResult(xmlOutputStream));

                return PersoonslijstDecoder.decodeBrpPersoonslijst(new ByteArrayInputStream(xmlOutputStream
                        .toByteArray()));
            } catch (final TransformerException e) {
                throw new IllegalStateException(
                        "De BRP persoonslijst van het converteerbericht kan niet worden geparsed.", e);
            }
        }
    }

    private void setBrpPersoonslijst(final BrpPersoonslijst brpPersoonslijst) {
        type.getAny().clear();
        if (brpPersoonslijst != null) {
            try {
                final ByteArrayOutputStream outputXmlStream = new ByteArrayOutputStream();
                PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, outputXmlStream);

                final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                final Document document =
                        documentBuilder
                                .parse(new InputSource(new ByteArrayInputStream(outputXmlStream.toByteArray())));
                type.getAny().add(document.getDocumentElement());
            } catch (final ParserConfigurationException e) {
                throw new IllegalStateException(
                        "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst "
                                + "(ParserConfigurationException).", e);
            } catch (final SAXException e) {
                throw new IllegalStateException(
                        "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst (SAXException).", e);
            } catch (final IOException e) {
                throw new IllegalStateException(
                        "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst (IOException).", e);
            }
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConverteerNaarLo3VerzoekBericht)) {
            return false;
        }
        final ConverteerNaarLo3VerzoekBericht castOther = (ConverteerNaarLo3VerzoekBericht) other;

        // TODO: implementeer ... inclusief type
        return new EqualsBuilder().appendSuper(super.equals(castOther)).isEquals();
    }

    @Override
    public int hashCode() {
        // TODO: implementeer ... inclusief type
        return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append(type)
                .toString();
    }
}
