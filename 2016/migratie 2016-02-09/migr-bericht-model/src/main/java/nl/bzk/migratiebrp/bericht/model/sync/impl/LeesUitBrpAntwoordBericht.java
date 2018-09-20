/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BrpPlType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.Lo3PlXmlType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.bericht.model.xml.XmlTeletexEncoding;
import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstDecoder;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstEncoder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * LeesUitBrpAntwoordbericht.
 */
public final class LeesUitBrpAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;
    private static final String PARSE_ERROR = "Fout tijdens het parsen van xml respresentatie van een BRP persoonslijst.";

    private final LeesUitBrpAntwoordType leesUitBrpAntwoordType;

    /**
     * Default constructor (status = OK).
     */
    public LeesUitBrpAntwoordBericht() {
        this(new LeesUitBrpAntwoordType());
        setStatus(StatusType.OK);
    }

    /**
     * JAXB constructor.
     *
     * @param leesUitBrpAntwoordType
     *            Het lees uit brp antwoord type {@link LeesUitBrpAntwoordType}
     */
    public LeesUitBrpAntwoordBericht(final LeesUitBrpAntwoordType leesUitBrpAntwoordType) {
        super("LeesUitBrpAntwoord");
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
        setLo3Persoonslijst(lo3Persoonslijst);
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = OK).
     *
     * @param correlationId
     *            correlatieId
     * @param lo3Persoonslijst
     *            lo3persoonslijst
     * @param antwoordFormaatType
     *            Geeft aan in welk formaat het antwoord gegeven moet worden
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final Lo3Persoonslijst lo3Persoonslijst, final AntwoordFormaatType antwoordFormaatType) {
        this();
        setLo3Persoonslijst(lo3Persoonslijst, antwoordFormaatType);
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = OK).
     *
     * @param correlationId
     *            correlatieId
     * @param brpPersoonslijst
     *            Brp persoonslijst.
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final BrpPersoonslijst brpPersoonslijst) {
        this();
        setBrpPersoonslijst(brpPersoonslijst);
        setCorrelationId(correlationId);
    }

    /* ************************************************************************************************************* */

    /**
     * Zet de waarde van lo3 persoonslijst.
     *
     * @param lo3Persoonslijst
     *            LO3 persoonslijst
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        setLo3Persoonslijst(lo3Persoonslijst, AntwoordFormaatType.LO_3);
    }

    /**
     * @param lo3Persoonslijst
     *            LO3 persoonslijst
     * @param antwoordFormaatType
     *            geeft aan in welke formaat het antwoord bericht moet zijn
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst, final AntwoordFormaatType antwoordFormaatType) {
        switch (antwoordFormaatType) {
            case LO_3_XML:
                final Document document = maakDocument(lo3Persoonslijst);
                if (leesUitBrpAntwoordType.getLo3PlXml() == null) {
                    leesUitBrpAntwoordType.setLo3PlXml(new Lo3PlXmlType());
                }
                leesUitBrpAntwoordType.getLo3PlXml().getAny().add(document.getDocumentElement());
                break;
            default:
                leesUitBrpAntwoordType.setLo3Pl(XmlTeletexEncoding.codeer(asString(lo3Persoonslijst)));
                break;
        }
    }

    /**
     * Geef de waarde van lo3 persoonslijst.
     *
     * @return De Lo3 Persoonslijst
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(XmlTeletexEncoding.decodeer(leesUitBrpAntwoordType.getLo3Pl()));
    }

    /**
     * Geef de waarde van lo3 persoonslijst from xml.
     *
     * @return De Lo3 Persoonslijst from XML
     */
    public Lo3Persoonslijst getLo3PersoonslijstFromXml() {
        return asLo3Persoonslijst(leesUitBrpAntwoordType.getLo3PlXml());
    }

    /**
     * Zet de waarde van brp persoonslijst.
     *
     * @param brpPersoonslijst
     *            BRP persoonslijst
     */
    public void setBrpPersoonslijst(final BrpPersoonslijst brpPersoonslijst) {
        final Document document = maakDocument(brpPersoonslijst);
        if (leesUitBrpAntwoordType.getBrpPl() == null) {
            leesUitBrpAntwoordType.setBrpPl(new BrpPlType());
        }
        leesUitBrpAntwoordType.getBrpPl().getAny().add(document.getDocumentElement());
    }

    private Document maakDocument(final Persoonslijst persoonsLijst) {
        try {
            final ByteArrayOutputStream outputXmlStream = new ByteArrayOutputStream();
            PersoonslijstEncoder.encodePersoonslijst(persoonsLijst, outputXmlStream);

            final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(outputXmlStream.toByteArray())));

            return document;
        } catch (final
            ParserConfigurationException
            | IOException
            | SAXException e)
        {
            throw new IllegalStateException(PARSE_ERROR, e);
        }
    }

    /**
     * Geef de waarde van brp persoonslijst.
     *
     * @return brp persoonslijst
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
        if (brpPl == null || brpPl.getAny().isEmpty()) {
            return null;
        } else {
            try {
                final Element anyElement = (Element) brpPl.getAny().get(0);
                final Transformer transformer = TransformerFactory.newInstance().newTransformer();
                final ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(anyElement), new StreamResult(xmlOutputStream));

                return PersoonslijstDecoder.decodeBrpPersoonslijst(new ByteArrayInputStream(xmlOutputStream.toByteArray()));
            } catch (final TransformerException e) {
                throw new IllegalStateException("De BRP persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
            }
        }
    }

    /**
     * Converteert het brpPl type naar een BrpPersoonslijst.
     *
     * @param lo3Pl
     *            Het brpPl type waarin de benodigde gegevens staan.
     * @return De samengestelde BrpPersoonslijst.
     */
    private Lo3Persoonslijst asLo3Persoonslijst(final Lo3PlXmlType lo3Pl) {
        if (lo3Pl == null || lo3Pl.getAny().isEmpty()) {
            return null;
        } else {
            try {
                final Element anyElement = (Element) lo3Pl.getAny().get(0);
                final Transformer transformer = TransformerFactory.newInstance().newTransformer();
                final ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(anyElement), new StreamResult(xmlOutputStream));

                return PersoonslijstDecoder.decodeLo3Persoonslijst(new ByteArrayInputStream(xmlOutputStream.toByteArray()));
            } catch (final TransformerException e) {
                throw new IllegalStateException("De LO3 persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
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
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        leesUitBrpAntwoordType.setStatus(status);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesUitBrpAntwoord(leesUitBrpAntwoordType));
    }

}
