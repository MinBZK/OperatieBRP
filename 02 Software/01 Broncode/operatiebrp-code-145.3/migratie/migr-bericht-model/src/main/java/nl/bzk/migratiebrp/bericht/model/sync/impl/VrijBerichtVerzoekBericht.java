/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VrijBerichtVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Vrijbericht.
 */
public class VrijBerichtVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VrijBerichtVerzoekType vrijBerichtVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VrijBerichtVerzoekBericht() {
        this(new VrijBerichtVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param vrijBerichtVerzoekType het vrijBerichtVerzoek type
     */
    public VrijBerichtVerzoekBericht(final VrijBerichtVerzoekType vrijBerichtVerzoekType) {
        super("vrijBerichtVerzoek");
        this.vrijBerichtVerzoekType = vrijBerichtVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public final String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVrijBerichtVerzoek(vrijBerichtVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef verzendende partij
     * @return de verzendende partij
     */
    public String getVerzendendePartij() {
        return vrijBerichtVerzoekType.getVerzendendePartij();
    }

    /**
     * Zet de verzendende partij.
     * @param verzendendePartij de partij welke het vrijbericht verstuurd
     */
    public void setVerzendendePartij(String verzendendePartij) {
        vrijBerichtVerzoekType.setVerzendendePartij(verzendendePartij);
    }

    /**
     * Geef ontvangende partij.
     * @return de partij voor wie bericht is bestemd
     */
    public String getOntvangendePartij() {
        return vrijBerichtVerzoekType.getOntvangendePartij();
    }

    /**
     * Zet de ontvangende partij
     * @param ontvangendePartij partij voor wie bericht is bestemd
     */
    public void setOntvangendePartij(String ontvangendePartij) {
        vrijBerichtVerzoekType.setOntvangendePartij(ontvangendePartij);
    }

    /**
     * Geef het bericht.
     * @return de inhoud van het bericht
     */
    public String getBericht() {
        return vrijBerichtVerzoekType.getBericht();
    }

    /**
     * Zet de inhoud van het bericht.
     * @param bericht het vrije bericht
     */
    public void setBericht(String bericht) {
        vrijBerichtVerzoekType.setBericht(bericht);
    }

    /**
     * Geef het referentienummer van dit vrijbericht.
     * @return het referentienummer
     */
    public String getReferentienummer() {
        return vrijBerichtVerzoekType.getReferentienummer();
    }

    /**
     * Zet het referentienummer voor dit bericht.
     * @param referentienummer referentienummer voor dit bericht
     */
    public void setReferentienummer(String referentienummer) {
        vrijBerichtVerzoekType.setReferentienummer(referentienummer);
    }
}
