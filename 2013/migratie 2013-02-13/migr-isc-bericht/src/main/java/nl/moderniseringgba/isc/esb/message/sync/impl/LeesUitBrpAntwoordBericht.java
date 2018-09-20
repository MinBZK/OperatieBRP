/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

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
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.BrpPlType;
import nl.moderniseringgba.isc.esb.message.sync.generated.LeesUitBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
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
 * LeesUitBrpAntwoordbericht.
 */
// CHECKSTYLE:OFF Aangezien we voor de convenience constructor de BrpPersoonslijst uit moeten lezen, hebben we te veel
// import statements waardoor de class fan-out complexity > 20 is.
public final class LeesUitBrpAntwoordBericht extends AbstractSyncBericht implements SyncBericht, Serializable {
    // CHECKSTYLE:ON

    private static final long serialVersionUID = 1L;
    private static final String PARSE_ERROR =
            "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst.";

    private LeesUitBrpAntwoordType leesUitBrpAntwoordType;

    /**
     * Default constructor (status = OK).
     */
    public LeesUitBrpAntwoordBericht() {
        leesUitBrpAntwoordType = new LeesUitBrpAntwoordType();
        setStatus(StatusType.OK);
    }

    /**
     * Convenience constructor.
     * 
     * @param leesUitBrpAntwoordType
     *            Het lees uit brp antwoord type {@link LeesUitBrpAntwoordType}
     */
    public LeesUitBrpAntwoordBericht(final LeesUitBrpAntwoordType leesUitBrpAntwoordType) {
        this.leesUitBrpAntwoordType = leesUitBrpAntwoordType;
    }

    /**
     * Convenience constructor (status = OK).
     * 
     * @param correlationId
     *            correlatieId
     * @param lo3Persoonslijst
     *            lo3persoonslijst
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final Lo3Persoonslijst lo3Persoonslijst) {
        this();
        leesUitBrpAntwoordType.setLo3Pl(asString(lo3Persoonslijst));
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = OK).
     * 
     * @param verzoekBericht
     *            Het verzoekBericht waaraan gecorreleerd moet worden.
     * @param brpPersoonslijst
     *            De BrpPersoonslijst.
     */
    public LeesUitBrpAntwoordBericht(
            final LeesUitBrpVerzoekBericht verzoekBericht,
            final BrpPersoonslijst brpPersoonslijst) {
        this();
        setCorrelationId(verzoekBericht.getMessageId());

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
        leesUitBrpAntwoordType.setBrpPl(brpPl);
    }

    /**
     * Convenience constructor (status = FOUT).
     * 
     * @param correlationId
     *            correlatieId
     * @param foutmelding
     *            foutmelding
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final String foutmelding) {
        this();
        leesUitBrpAntwoordType.setStatus(StatusType.FOUT);
        leesUitBrpAntwoordType.setFoutmelding(foutmelding);
        setCorrelationId(correlationId);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de foutmelding op het bericht terug.
     * 
     * @return De foutmelding op het bericht.
     */
    public String getFoutmelding() {
        return leesUitBrpAntwoordType.getFoutmelding();
    }

    /**
     * Geeft de Lo3Persoonslijst terug.
     * 
     * @return De Lo3Persoonslijst
     * 
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(leesUitBrpAntwoordType.getLo3Pl());
    }

    /**
     * Geeft de brp persoonslijst terug die als XML op het bericht staat.
     * 
     * @return De brp persoonslijst.
     */
    public BrpPersoonslijst getBrpPersoonslijst() {
        return asBrpPersoonslijst(leesUitBrpAntwoordType.getBrpPl());
    }

    /**
     * Converteert het brpPl type naar een BrpPersoonslijst.
     * 
     * @param brpPl
     *            Het brpPl type waarin de benodigde gegevens staan.
     * @return De samengestelde BrpPersoonslijst.
     */
    private BrpPersoonslijst asBrpPersoonslijst(final BrpPlType brpPl) {
        if (brpPl == null || brpPl.getAny() == null || brpPl.getAny().isEmpty()) {
            return null;
        } else {
            try {
                final Element anyElement = (Element) brpPl.getAny().get(0);
                final Transformer transformer = TransformerFactory.newInstance().newTransformer();
                final ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(anyElement), new StreamResult(xmlOutputStream));

                return PersoonslijstDecoder.decodeBrpPersoonslijst(new ByteArrayInputStream(xmlOutputStream
                        .toByteArray()));
            } catch (final TransformerException e) {
                throw new IllegalStateException(
                        "De BRP persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
            }
        }
    }

    /**
     * Geeft de status {@link StatusType} op het bericht terug.
     * 
     * @return De status {@link StatusType} op het bericht.
     */
    public StatusType getStatus() {
        return leesUitBrpAntwoordType.getStatus();
    }

    /**
     * Zet status.
     * 
     * @param status
     *            status
     * @throws NullPointerException
     *             als status null is
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        leesUitBrpAntwoordType.setStatus(status);
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "LeesUitBrpAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createLeesUitBrpAntwoord(leesUitBrpAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            leesUitBrpAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, LeesUitBrpAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een LeesUitBrpAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LeesUitBrpAntwoordBericht)) {
            return false;
        }
        final LeesUitBrpAntwoordBericht castOther = (LeesUitBrpAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(leesUitBrpAntwoordType.getStatus(), castOther.leesUitBrpAntwoordType.getStatus())
                .append(leesUitBrpAntwoordType.getFoutmelding(), castOther.leesUitBrpAntwoordType.getFoutmelding())
                .append(leesUitBrpAntwoordType.getLo3Pl(), castOther.leesUitBrpAntwoordType.getLo3Pl()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(leesUitBrpAntwoordType.getStatus())
                .append(leesUitBrpAntwoordType.getFoutmelding()).append(leesUitBrpAntwoordType.getLo3Pl())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(leesUitBrpAntwoordType.getStatus()).append(leesUitBrpAntwoordType.getFoutmelding())
                .append(leesUitBrpAntwoordType.getLo3Pl()).toString();
    }

}
