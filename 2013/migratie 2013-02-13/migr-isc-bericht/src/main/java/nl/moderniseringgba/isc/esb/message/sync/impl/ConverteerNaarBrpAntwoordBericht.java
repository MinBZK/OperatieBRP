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
import nl.moderniseringgba.isc.esb.message.sync.generated.BrpPlType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
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
 * ConverteerNaarBrpAntwoordBericht. Antwoord op een ConverteerNaarBrpVerzoekBericht.
 */
// CHECKSTYLE:OFF - Class fan-out is hoog door het parsen van het any element (= de BRP persoonslijst).
public class ConverteerNaarBrpAntwoordBericht extends AbstractSyncBericht {
    // CHECKSTYLE:ON

    private static final long serialVersionUID = 1L;
    private static final String PARSE_ERROR =
            "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst.";

    private ConverteerNaarBrpAntwoordType converteerNaarBrpAntwoordType;

    /**
     * default constructor. Sets Status to StatusType.OK.
     */
    public ConverteerNaarBrpAntwoordBericht() {
        converteerNaarBrpAntwoordType = new ConverteerNaarBrpAntwoordType();
        converteerNaarBrpAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Convenience constructor.
     * 
     * @param type
     *            {@link ConverteerNaarBrpAntwoordType}
     */
    public ConverteerNaarBrpAntwoordBericht(final ConverteerNaarBrpAntwoordType type) {
        converteerNaarBrpAntwoordType = type;
    }

    /**
     * Convenience constructor.
     * 
     * @param correlationId
     *            {@link String}
     * @param foutMelding
     *            {@link String}
     */
    public ConverteerNaarBrpAntwoordBericht(final String correlationId, final String foutMelding) {
        this();
        setCorrelationId(correlationId);
        converteerNaarBrpAntwoordType.setStatus(StatusType.FOUT);
        converteerNaarBrpAntwoordType.setFoutmelding(foutMelding);
    }

    /**
     * Convinience constructor.
     * 
     * @param correlationId
     *            {@link String}
     * @param brpPl
     *            {@link String}
     */
    public ConverteerNaarBrpAntwoordBericht(final String correlationId, final BrpPersoonslijst brpPl) {
        this();
        setCorrelationId(correlationId);
        setBrpPersoonslijst(brpPl);
    }

    @Override
    public final void parse(final Document document) throws BerichtInhoudException {
        try {
            converteerNaarBrpAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, ConverteerNaarBrpAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ConverteerNaarBrpAntwoordBericht bericht.", e);
        }
    }

    @Override
    public final String getBerichtType() {
        return "ConverteerNaarBrpAntwoord";
    }

    @Override
    public final String getStartCyclus() {
        return null;
    }

    @Override
    public final String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createConverteerNaarBrpAntwoord(converteerNaarBrpAntwoordType));
    }

    /**
     * @return {@link BrpPersoonslijst}
     */
    public final BrpPersoonslijst getBrpPersoonslijst() {
        if (converteerNaarBrpAntwoordType.getBrpPl() == null
                || converteerNaarBrpAntwoordType.getBrpPl().getAny() == null
                || converteerNaarBrpAntwoordType.getBrpPl().getAny().isEmpty()) {
            return null;
        } else {
            try {
                final Element anyElement = (Element) converteerNaarBrpAntwoordType.getBrpPl().getAny().get(0);
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

    /**
     * @param brpPersoonslijst
     *            {@ link BrpPersoonslijst}
     */
    public final void setBrpPersoonslijst(final BrpPersoonslijst brpPersoonslijst) {
        if (converteerNaarBrpAntwoordType.getBrpPl() != null) {
            converteerNaarBrpAntwoordType.getBrpPl().getAny().clear();
        }
        final BrpPlType brpPl = new BrpPlType();

        if (brpPersoonslijst != null) {
            try {
                final ByteArrayOutputStream outputXmlStream = new ByteArrayOutputStream();
                PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, outputXmlStream);

                final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                final Document document =
                        documentBuilder
                                .parse(new InputSource(new ByteArrayInputStream(outputXmlStream.toByteArray())));

                brpPl.getAny().add(document.getDocumentElement());

            } catch (final ParserConfigurationException e) {
                throw new IllegalStateException(PARSE_ERROR, e);
            } catch (final SAXException e) {
                throw new IllegalStateException(PARSE_ERROR, e);
            } catch (final IOException e) {
                throw new IllegalStateException(PARSE_ERROR, e);
            }
        } else {
            throw new IllegalStateException(PARSE_ERROR);
        }
        converteerNaarBrpAntwoordType.setBrpPl(brpPl);
    }

    /**
     * Geeft de status terug van het bericht.
     * 
     * @return De status van het bericht.
     */
    public final StatusType getStatus() {
        return converteerNaarBrpAntwoordType != null ? converteerNaarBrpAntwoordType.getStatus() : null;
    }

    /**
     * Geeft de foutmelding terug.
     * 
     * @return De foutmelding op het het bericht.
     */
    public final String getFoutmelding() {
        return converteerNaarBrpAntwoordType != null ? converteerNaarBrpAntwoordType.getFoutmelding() : null;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConverteerNaarBrpAntwoordBericht)) {
            return false;
        }
        final ConverteerNaarBrpAntwoordBericht castOther = (ConverteerNaarBrpAntwoordBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(converteerNaarBrpAntwoordType.getStatus(),
                        castOther.converteerNaarBrpAntwoordType.getStatus())
                .append(converteerNaarBrpAntwoordType.getFoutmelding(),
                        castOther.converteerNaarBrpAntwoordType.getFoutmelding()).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(converteerNaarBrpAntwoordType.getStatus())
                .append(converteerNaarBrpAntwoordType.getFoutmelding()).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(converteerNaarBrpAntwoordType.getStatus())
                .append(converteerNaarBrpAntwoordType.getFoutmelding()).toString();
    }
}
