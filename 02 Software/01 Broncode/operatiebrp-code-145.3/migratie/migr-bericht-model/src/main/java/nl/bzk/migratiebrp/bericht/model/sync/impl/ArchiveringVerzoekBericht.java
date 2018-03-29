/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.Date;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ArchiveerInBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.bericht.model.xml.XmlConversieUtils;

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
     * @param archiveerInBrpVerzoekType het archiveerInBrpVerzoekType type
     */
    public ArchiveringVerzoekBericht(final ArchiveerInBrpVerzoekType archiveerInBrpVerzoekType) {
        super("ArchiveerInBrpVerzoek");
        this.archiveerInBrpVerzoekType = archiveerInBrpVerzoekType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van soort bericht.
     * @return soort bericht
     */
    public String getSoortBericht() {
        return archiveerInBrpVerzoekType.getSoortBericht();
    }

    /**
     * Zet de waarde van soort bericht.
     * @param soortBericht soort bericht
     */
    public void setSoortBericht(final String soortBericht) {
        archiveerInBrpVerzoekType.setSoortBericht(soortBericht);
    }

    /**
     * Geef de waarde van richting.
     * @return richting
     */
    public RichtingType getRichting() {
        return archiveerInBrpVerzoekType.getRichting();
    }

    /**
     * Zet de waarde van richting.
     * @param richting richting
     */
    public void setRichting(final RichtingType richting) {
        archiveerInBrpVerzoekType.setRichting(richting);
    }

    /**
     * Geef de waarde van zendende afnemer code.
     * @return zendende afnemer code
     */
    public String getZendendePartij() {
        return archiveerInBrpVerzoekType.getZendendePartij();
    }

    /**
     * Zet de waarde van zendende partij code.
     * @param partijcode zendende partij code
     */
    public void setZendendePartij(final String partijcode) {
        archiveerInBrpVerzoekType.setZendendePartij(partijcode);
    }

    /**
     * Geef de waarde van ontvangende partij code.
     * @return ontvangende partij code
     */
    public String getOntvangendePartij() {
        return archiveerInBrpVerzoekType.getOntvangendePartij();
    }

    /**
     * Zet de waarde van ontvangende partij code.
     * @param partijcode ontvangende partij code
     */
    public void setOntvangendePartij(final String partijcode) {
        archiveerInBrpVerzoekType.setOntvangendePartij(partijcode);
    }

    /**
     * Geef de waarde van referentienummer.
     * @return referentienummer
     */
    public String getReferentienummer() {
        return archiveerInBrpVerzoekType.getReferentienummer();
    }

    /**
     * Zet de waarde van referentienummer.
     * @param referentienummer referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        archiveerInBrpVerzoekType.setReferentienummer(referentienummer);
    }

    /**
     * Geef de waarde van cross referentienummer.
     * @return cross referentienummer
     */
    public String getCrossReferentienummer() {
        return archiveerInBrpVerzoekType.getCrossReferentienummer();
    }

    /**
     * Zet de waarde van cross referentienummer.
     * @param crossReferentienummer cross referentienummer
     */
    public void setCrossReferentienummer(final String crossReferentienummer) {
        archiveerInBrpVerzoekType.setCrossReferentienummer(crossReferentienummer);
    }

    /**
     * Geef de waarde van tijdstip verzending.
     * @return tijdstip verzending
     */
    public Date getTijdstipVerzending() {
        return XmlConversieUtils.converteerXmlNaarDate(archiveerInBrpVerzoekType.getTijdstipVerzending());
    }

    /**
     * Zet de waarde van tijdstip verzending.
     * @param tijdstipVerzending tijdstip verzending
     */
    public void setTijdstipVerzending(final Date tijdstipVerzending) {
        archiveerInBrpVerzoekType.setTijdstipVerzending(XmlConversieUtils.converteerDateNaarXml(tijdstipVerzending));
    }

    /**
     * Geef de waarde van tijdstip ontvangst.
     * @return tijdstip ontvangst
     */
    public Date getTijdstipOntvangst() {
        return XmlConversieUtils.converteerXmlNaarDate(archiveerInBrpVerzoekType.getTijdstipOntvangst());
    }

    /**
     * Zet de waarde van tijdstip ontvangst.
     * @param tijdstipOntvangst tijdstip ontvangst
     */
    public void setTijdstipOntvangst(final Date tijdstipOntvangst) {
        archiveerInBrpVerzoekType.setTijdstipOntvangst(XmlConversieUtils.converteerDateNaarXml(tijdstipOntvangst));
    }

    /**
     * Geef de waarde van data.
     * @return data
     */
    public String getData() {
        return archiveerInBrpVerzoekType.getData();
    }

    /**
     * Zet de waarde van data.
     * @param data data
     */
    public void setData(final String data) {
        archiveerInBrpVerzoekType.setData(data);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createArchiveerInBrpVerzoek(archiveerInBrpVerzoekType));
    }

}
