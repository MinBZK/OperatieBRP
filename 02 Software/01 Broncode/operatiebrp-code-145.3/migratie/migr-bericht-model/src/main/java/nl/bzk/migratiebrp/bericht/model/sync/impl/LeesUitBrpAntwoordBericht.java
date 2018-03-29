/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
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
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;

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
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
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
     * @param leesUitBrpAntwoordType Het lees uit brp antwoord type {@link LeesUitBrpAntwoordType}
     */
    public LeesUitBrpAntwoordBericht(final LeesUitBrpAntwoordType leesUitBrpAntwoordType) {
        super("LeesUitBrpAntwoord");
        this.leesUitBrpAntwoordType = leesUitBrpAntwoordType;
    }

    /**
     * Convenience constructor (status = OK).
     * @param correlationId correlatieId
     * @param lo3Persoonslijst lo3persoonslijst
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final Lo3Persoonslijst lo3Persoonslijst) {
        this();
        setLo3Persoonslijst(lo3Persoonslijst);
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = OK).
     * @param correlationId correlatieId
     * @param lo3Persoonslijst lo3persoonslijst
     * @param antwoordFormaatType Geeft aan in welk formaat het antwoord gegeven moet worden
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final Lo3Persoonslijst lo3Persoonslijst, final AntwoordFormaatType antwoordFormaatType) {
        this();
        setLo3Persoonslijst(lo3Persoonslijst, antwoordFormaatType);
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = OK).
     * @param correlationId correlatieId
     * @param brpPersoonslijst Brp persoonslijst.
     */
    public LeesUitBrpAntwoordBericht(final String correlationId, final BrpPersoonslijst brpPersoonslijst) {
        this();
        setBrpPersoonslijst(brpPersoonslijst);
        setCorrelationId(correlationId);
    }

    /**
     * Zet de waarde van lo3 persoonslijst.
     * @param lo3Persoonslijst LO3 persoonslijst
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        setLo3Persoonslijst(lo3Persoonslijst, AntwoordFormaatType.LO_3);
    }

    /**
     * @param lo3Persoonslijst LO3 persoonslijst
     * @param antwoordFormaatType geeft aan in welke formaat het antwoord bericht moet zijn
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst, final AntwoordFormaatType antwoordFormaatType) {
        if (antwoordFormaatType == AntwoordFormaatType.LO_3_XML) {
            final Document document = maakDocument(lo3Persoonslijst);
            if (leesUitBrpAntwoordType.getLo3PlXml() == null) {
                leesUitBrpAntwoordType.setLo3PlXml(new Lo3PlXmlType());
            }
            leesUitBrpAntwoordType.getLo3PlXml().getAny().add(document.getDocumentElement());

        } else {
            leesUitBrpAntwoordType.setLo3Pl(XmlTeletexEncoding.codeer(asString(lo3Persoonslijst)));

        }
    }

    /**
     * Geef de waarde van lo3 persoonslijst.
     * @return De Lo3 Persoonslijst
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(XmlTeletexEncoding.decodeer(leesUitBrpAntwoordType.getLo3Pl()));
    }

    /**
     * Geef de waarde van lo3 persoonslijst from xml.
     * @return De Lo3 Persoonslijst from XML
     */
    public Lo3Persoonslijst getLo3PersoonslijstFromXml() {
        return asLo3Persoonslijst(leesUitBrpAntwoordType.getLo3PlXml());
    }

    /**
     * Geef de waarde van lo3 persoonslijst from xml.
     * @return De String representatie van de xml
     */
    public String getStringFromXml() {
        return asString(leesUitBrpAntwoordType.getLo3PlXml());
    }

    /**
     * Zet de waarde van brp persoonslijst.
     * @param brpPersoonslijst BRP persoonslijst
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
            try (Writer writer = new OutputStreamWriter(outputXmlStream, UTF_8)) {
                MigratieXml.encode(persoonsLijst, writer);
            }

            final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder.parse(new InputSource(new ByteArrayInputStream(outputXmlStream.toByteArray())));
        } catch (final
        ParserConfigurationException
                | IOException
                | XmlException
                | SAXException e) {
            throw new IllegalStateException(PARSE_ERROR, e);
        }
    }

    /**
     * Geef de waarde van brp persoonslijst.
     * @return brp persoonslijst
     */
    public BrpPersoonslijst getBrpPersoonslijst() {
        return asBrpPersoonslijst(leesUitBrpAntwoordType.getBrpPl());
    }

    /**
     * Converteert het brpPl type naar een BrpPersoonslijst.
     * @param brpPl Het brpPl type waarin de benodigde gegevens staan.
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

                final ByteArrayInputStream bais = new ByteArrayInputStream(xmlOutputStream.toByteArray());
                try (Reader reader = new InputStreamReader(bais, UTF_8)) {
                    return MigratieXml.decode(BrpPersoonslijst.class, reader);
                }
            } catch (final
            TransformerException
                    | IOException
                    | XmlException e) {
                throw new IllegalStateException("De BRP persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
            }
        }
    }

    private String asString(final Lo3PlXmlType lo3PlXmlType) {
        if (lo3PlXmlType == null || lo3PlXmlType.getAny().isEmpty()) {
            throw new IllegalArgumentException("Inhoud lo3Pl mag niet leeg zijn.");
        }
        try {
            final Element anyElement = (Element) lo3PlXmlType.getAny().get(0);
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            final ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(anyElement), new StreamResult(xmlOutputStream));
            return xmlOutputStream.toString(UTF_8);
        } catch (final TransformerException | UnsupportedEncodingException e) {
            throw new IllegalStateException("De LO3 persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
        }
    }

    /**
     * Converteert het brpPl type naar een BrpPersoonslijst.
     * @param lo3Pl Het brpPl type waarin de benodigde gegevens staan.
     * @return De samengestelde BrpPersoonslijst.
     */
    private Lo3Persoonslijst asLo3Persoonslijst(final Lo3PlXmlType lo3Pl) {
        if (lo3Pl == null || lo3Pl.getAny().isEmpty()) {
            return null;
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(asString(lo3Pl).getBytes(UTF_8)); Reader reader = new InputStreamReader(bais, UTF_8)) {
            return MigratieXml.decode(Lo3Persoonslijst.class, reader);
        } catch (final IOException | XmlException e) {
            throw new IllegalStateException("De LO3 persoonslijst van het LeesUitBrpAntwoordBericht kan niet worden geparsed.", e);
        }
    }

    /**
     * Geeft de status {@link StatusType} op het bericht terug.
     * @return De status {@link StatusType} op het bericht.
     */
    public StatusType getStatus() {
        return leesUitBrpAntwoordType.getStatus();
    }

    /**
     * Zet status.
     * @param status status
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        leesUitBrpAntwoordType.setStatus(status);
    }

    /**
     * Geeft de melding.
     * @return De melding
     */
    public String getMelding() {
        return leesUitBrpAntwoordType.getMelding();
    }

    /**
     * Zet melding.
     * @param melding melding
     */
    public void setMelding(String melding) {
        leesUitBrpAntwoordType.setMelding(melding);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesUitBrpAntwoord(leesUitBrpAntwoordType));
    }

}
