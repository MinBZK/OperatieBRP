/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ArchiveerInBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SysteemType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZenderType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Archivering verzoek.
 */
public final class ArchiveringVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final ArchiveerInBrpVerzoekType archiveerInBrpVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ArchiveringVerzoekBericht() {
        this(new ArchiveerInBrpVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param archiveerInBrpVerzoekType
     *            het archiveerInBrpVerzoekType type
     */
    public ArchiveringVerzoekBericht(final ArchiveerInBrpVerzoekType archiveerInBrpVerzoekType) {
        super("ArchiveerInBrpVerzoek");
        this.archiveerInBrpVerzoekType = archiveerInBrpVerzoekType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van soort bericht.
     *
     * @return soort bericht
     */
    public String getSoortBericht() {
        return archiveerInBrpVerzoekType.getSoortBericht();
    }

    /**
     * Zet de waarde van soort bericht.
     *
     * @param soortBericht
     *            soort bericht
     */
    public void setSoortBericht(final String soortBericht) {
        archiveerInBrpVerzoekType.setSoortBericht(soortBericht);
    }

    /**
     * Geef de waarde van richting.
     *
     * @return richting
     */
    public RichtingType getRichting() {
        return archiveerInBrpVerzoekType.getRichting();
    }

    /**
     * Zet de waarde van richting.
     *
     * @param richting
     *            richting
     */
    public void setRichting(final RichtingType richting) {
        archiveerInBrpVerzoekType.setRichting(richting);
    }

    /**
     * Geef de waarde van zendende afnemer code.
     *
     * @return zendende afnemer code
     */
    public String getZendendeAfnemerCode() {
        return archiveerInBrpVerzoekType.getZender() == null ? null : archiveerInBrpVerzoekType.getZender().getAfnemer();
    }

    /**
     * Zet de waarde van zendende afnemer code.
     *
     * @param afnemerCode
     *            zendende afnemer code
     */
    public void setZendendeAfnemerCode(final String afnemerCode) {
        if (archiveerInBrpVerzoekType.getZender() == null) {
            archiveerInBrpVerzoekType.setZender(new ZenderType());
        }
        archiveerInBrpVerzoekType.getZender().setAfnemer(afnemerCode);
        archiveerInBrpVerzoekType.getZender().setGemeente(null);
    }

    /**
     * Geef de waarde van zendende gemeente code.
     *
     * @return zendende gemeente code
     */
    public String getZendendeGemeenteCode() {
        return archiveerInBrpVerzoekType.getZender() == null ? null : archiveerInBrpVerzoekType.getZender().getGemeente();
    }

    /**
     * Zet de waarde van zendende gemeente code.
     *
     * @param gemeenteCode
     *            zendende gemeente code
     */
    public void setZendendeGemeenteCode(final String gemeenteCode) {
        if (archiveerInBrpVerzoekType.getZender() == null) {
            archiveerInBrpVerzoekType.setZender(new ZenderType());
        }
        archiveerInBrpVerzoekType.getZender().setAfnemer(null);
        archiveerInBrpVerzoekType.getZender().setGemeente(gemeenteCode);
    }

    /**
     * Geef de waarde van zendende systeem.
     *
     * @return zendende systeem
     */
    public SysteemType getZendendeSysteem() {
        return archiveerInBrpVerzoekType.getZender() == null ? null : archiveerInBrpVerzoekType.getZender().getSysteem();
    }

    /**
     * Zet de waarde van zendende systeem.
     *
     * @param systeem
     *            zendende systeem
     */
    public void setZendendeSysteem(final SysteemType systeem) {
        if (archiveerInBrpVerzoekType.getZender() == null) {
            archiveerInBrpVerzoekType.setZender(new ZenderType());
        }
        archiveerInBrpVerzoekType.getZender().setSysteem(systeem);
    }

    /**
     * Geef de waarde van ontvangende afnemer code.
     *
     * @return ontvangende afnemer code
     */
    public String getOntvangendeAfnemerCode() {
        return archiveerInBrpVerzoekType.getOntvanger() == null ? null : archiveerInBrpVerzoekType.getOntvanger().getAfnemer();
    }

    /**
     * Zet de waarde van ontvangende afnemer code.
     *
     * @param afnemerCode
     *            ontvangende afnemer code
     */
    public void setOntvangendeAfnemerCode(final String afnemerCode) {
        if (archiveerInBrpVerzoekType.getOntvanger() == null) {
            archiveerInBrpVerzoekType.setOntvanger(new ZenderType());
        }
        archiveerInBrpVerzoekType.getOntvanger().setAfnemer(afnemerCode);
        archiveerInBrpVerzoekType.getOntvanger().setGemeente(null);
    }

    /**
     * Geef de waarde van ontvangende gemeente code.
     *
     * @return ontvangende gemeente code
     */
    public String getOntvangendeGemeenteCode() {
        return archiveerInBrpVerzoekType.getOntvanger() == null ? null : archiveerInBrpVerzoekType.getOntvanger().getGemeente();
    }

    /**
     * Zet de waarde van ontvangende gemeente code.
     *
     * @param gemeenteCode
     *            ontvangende gemeente code
     */
    public void setOntvangendeGemeenteCode(final String gemeenteCode) {
        if (archiveerInBrpVerzoekType.getOntvanger() == null) {
            archiveerInBrpVerzoekType.setOntvanger(new ZenderType());
        }
        archiveerInBrpVerzoekType.getOntvanger().setAfnemer(null);
        archiveerInBrpVerzoekType.getOntvanger().setGemeente(gemeenteCode);
    }

    /**
     * Geef de waarde van ontvangende systeem.
     *
     * @return ontvangende systeem
     */
    public SysteemType getOntvangendeSysteem() {
        return archiveerInBrpVerzoekType.getOntvanger() == null ? null : archiveerInBrpVerzoekType.getOntvanger().getSysteem();
    }

    /**
     * Zet de waarde van ontvangende systeem.
     *
     * @param systeem
     *            ontvangende systeem
     */
    public void setOntvangendeSysteem(final SysteemType systeem) {
        if (archiveerInBrpVerzoekType.getOntvanger() == null) {
            archiveerInBrpVerzoekType.setOntvanger(new ZenderType());
        }
        archiveerInBrpVerzoekType.getOntvanger().setSysteem(systeem);
    }

    /**
     * Geef de waarde van referentienummer.
     *
     * @return referentienummer
     */
    public String getReferentienummer() {
        return archiveerInBrpVerzoekType.getReferentienummer();
    }

    /**
     * Zet de waarde van referentienummer.
     *
     * @param referentienummer
     *            referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        archiveerInBrpVerzoekType.setReferentienummer(referentienummer);
    }

    /**
     * Geef de waarde van cross referentienummer.
     *
     * @return cross referentienummer
     */
    public String getCrossReferentienummer() {
        return archiveerInBrpVerzoekType.getCrossReferentienummer();
    }

    /**
     * Zet de waarde van cross referentienummer.
     *
     * @param crossReferentienummer
     *            cross referentienummer
     */
    public void setCrossReferentienummer(final String crossReferentienummer) {
        archiveerInBrpVerzoekType.setCrossReferentienummer(crossReferentienummer);
    }

    /**
     * Geef de waarde van tijdstip verzending.
     *
     * @return tijdstip verzending
     */
    public Date getTijdstipVerzending() {
        return converteerXmlNaarDate(archiveerInBrpVerzoekType.getTijdstipVerzending());
    }

    /**
     * Zet de waarde van tijdstip verzending.
     *
     * @param tijdstipVerzending
     *            tijdstip verzending
     */
    public void setTijdstipVerzending(final Date tijdstipVerzending) {
        archiveerInBrpVerzoekType.setTijdstipVerzending(converteerDateNaarXml(tijdstipVerzending));
    }

    /**
     * Geef de waarde van tijdstip ontvangst.
     *
     * @return tijdstip ontvangst
     */
    public Date getTijdstipOntvangst() {
        return converteerXmlNaarDate(archiveerInBrpVerzoekType.getTijdstipOntvangst());
    }

    /**
     * Zet de waarde van tijdstip ontvangst.
     *
     * @param tijdstipOntvangst
     *            tijdstip ontvangst
     */
    public void setTijdstipOntvangst(final Date tijdstipOntvangst) {
        archiveerInBrpVerzoekType.setTijdstipOntvangst(converteerDateNaarXml(tijdstipOntvangst));
    }

    /**
     * Geef de waarde van data.
     *
     * @return data
     */
    public String getData() {
        return archiveerInBrpVerzoekType.getData();
    }

    /**
     * Zet de waarde van data.
     *
     * @param data
     *            data
     */
    public void setData(final String data) {
        archiveerInBrpVerzoekType.setData(data);
    }

    /**
     * Converteer xml naar date.
     *
     * @param xml
     *            xml
     * @return date
     */
    private Date converteerXmlNaarDate(final XMLGregorianCalendar xml) {
        return xml == null ? null : xml.toGregorianCalendar().getTime();
    }

    /**
     * Converteer date naar xml.
     *
     * @param date
     *            date
     * @return XML gregorian calendar
     */
    private XMLGregorianCalendar converteerDateNaarXml(final Date date) {
        if (date == null) {
            return null;
        }
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (final DatatypeConfigurationException e) {
            throw new IllegalArgumentException("Kon XML Datatype factory niet ophalen", e);
        }

    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createArchiveerInBrpVerzoek(archiveerInBrpVerzoekType));
    }

}
