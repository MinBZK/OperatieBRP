/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonOpActueleGegevensVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Zoek persoon op actuele gegevens.
 */
public final class ZoekPersoonOpActueleGegevensVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final ZoekPersoonOpActueleGegevensVerzoekType zoekPersoonOpActueleGegevensVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ZoekPersoonOpActueleGegevensVerzoekBericht() {
        this(new ZoekPersoonOpActueleGegevensVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param zoekPersoonOpActueleGegevensVerzoekType het zoekPersoonOpActueleGegevensVerzoek type
     */
    public ZoekPersoonOpActueleGegevensVerzoekBericht(final ZoekPersoonOpActueleGegevensVerzoekType zoekPersoonOpActueleGegevensVerzoekType) {
        super("ZoekPersoonOpActueleGegevensVerzoek");
        this.zoekPersoonOpActueleGegevensVerzoekType = zoekPersoonOpActueleGegevensVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createZoekPersoonOpActueleGegevensVerzoek(zoekPersoonOpActueleGegevensVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer op het bericht terug.
     * @return Het A-nummer op het bericht.
     */
    public String getANummer() {
        return zoekPersoonOpActueleGegevensVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * @param aNummer Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        zoekPersoonOpActueleGegevensVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het BSN op het bericht terug.
     * @return Het BSN op het bericht.
     */
    public String getBsn() {
        return zoekPersoonOpActueleGegevensVerzoekType.getBurgerservicenummer();
    }

    /**
     * Zet het BSN op het bericht.
     * @param bsn Het te zetten BSN.
     */
    public void setBsn(final String bsn) {
        zoekPersoonOpActueleGegevensVerzoekType.setBurgerservicenummer(bsn);
    }

    /**
     * Geeft de geslachtsnaam op het bericht terug.
     * @return De geslachtsnaam op het bericht.
     */
    public String getGeslachtsnaam() {
        return zoekPersoonOpActueleGegevensVerzoekType.getGeslachtsnaam();
    }

    /**
     * Zet de geslachtsnaam op het bericht.
     * @param geslachtsnaam De te zetten geslachtsnaamstam.
     */
    public void setGeslachtsnaam(final String geslachtsnaam) {
        zoekPersoonOpActueleGegevensVerzoekType.setGeslachtsnaam(geslachtsnaam);
    }

    /**
     * Geeft de postcode op het bericht terug.
     * @return De postcode op het bericht.
     */
    public String getPostcode() {
        return zoekPersoonOpActueleGegevensVerzoekType.getPostcode();
    }

    /**
     * Zet de postcode op het bericht.
     * @param postcode De te zetten postcode.
     */
    public void setPostcode(final String postcode) {
        zoekPersoonOpActueleGegevensVerzoekType.setPostcode(postcode);
    }

    /**
     * Geeft de aanvullende zoekcriteria op het bericht terug.
     * @return De aanvullende zoekcriteria op het bericht.
     */
    public String getAanvullendeZoekcriteria() {
        return zoekPersoonOpActueleGegevensVerzoekType.getAanvullendeZoekcriteria();
    }

    /**
     * Zet de aanvullende zoekcriteria op het bericht.
     * @param aanvullendeZoekcriteria De te zetten aanvullende zoekcriteria.
     */
    public void setAanvullendeZoekcriteria(final String aanvullendeZoekcriteria) {
        zoekPersoonOpActueleGegevensVerzoekType.setAanvullendeZoekcriteria(aanvullendeZoekcriteria);
    }
}
