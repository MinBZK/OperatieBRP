/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType.UniekGevondenPersoon;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Zoek Persoon bericht: ga na of de persoon uniek kan worden geidentificeerd in BRP.
 */
public final class ZoekPersoonAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final ZoekPersoonAntwoordType zoekPersoonAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ZoekPersoonAntwoordBericht() {
        this(new ZoekPersoonAntwoordType());
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param zoekPersoonAntwoordType
     *            het zoekPersoonAntwoord type
     */
    public ZoekPersoonAntwoordBericht(final ZoekPersoonAntwoordType zoekPersoonAntwoordType) {
        super("ZoekPersoonAntwoord");
        this.zoekPersoonAntwoordType = zoekPersoonAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createZoekPersoonAntwoord(zoekPersoonAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Zet de status {@link StatusType} op het bericht.
     *
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        zoekPersoonAntwoordType.setStatus(status);
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     *
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return zoekPersoonAntwoordType.getStatus();
    }

    /**
     * Geeft het resultaat {@link ZoekPersoonResultaatType} van het bericht terug.
     *
     * @return Het resultaat {@link ZoekPersoonResultaatType} van het bericht.
     */
    public ZoekPersoonResultaatType getResultaat() {
        return zoekPersoonAntwoordType.getResultaat();
    }

    /**
     * Zet het resultaat {@link ZoekPersoonResultaatType} op het bericht.
     *
     * @param resultaat
     *            Het te zetten resultaat {@link ZoekPersoonResultaatType}.
     */
    public void setResultaat(final ZoekPersoonResultaatType resultaat) {
        zoekPersoonAntwoordType.setResultaat(resultaat);
    }

    /**
     * Zet de waarde van persoon id.
     *
     * @param persoonId
     *            het id van de uniek gevonden persoon
     */
    public void setPersoonId(final Integer persoonId) {
        if (zoekPersoonAntwoordType.getUniekGevondenPersoon() == null) {
            zoekPersoonAntwoordType.setUniekGevondenPersoon(new UniekGevondenPersoon());
        }
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setPersoonId(persoonId);
    }

    /**
     * Geef de waarde van persoon id.
     *
     * @return het id van de uniek gevonden persoon
     */
    public Integer getPersoonId() {
        return zoekPersoonAntwoordType.getUniekGevondenPersoon() == null ? null : zoekPersoonAntwoordType.getUniekGevondenPersoon().getPersoonId();
    }

    /**
     * Zet de waarde van anummer.
     *
     * @param anummer
     *            het anummer van de uniek gevonden persoon
     */
    public void setAnummer(final String anummer) {
        if (zoekPersoonAntwoordType.getUniekGevondenPersoon() == null) {
            zoekPersoonAntwoordType.setUniekGevondenPersoon(new UniekGevondenPersoon());
        }
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setActueelANummer(anummer);
    }

    /**
     * Geef de waarde van anummer.
     *
     * @return het anummer van de uniek gevonden persoon
     */
    public String getAnummer() {
        return zoekPersoonAntwoordType.getUniekGevondenPersoon() == null ? null : zoekPersoonAntwoordType.getUniekGevondenPersoon().getActueelANummer();

    }

    /**
     * Zet de waarde van gemeente.
     *
     * @param gemeente
     *            de bijhoudingsgemeente van de uniek gevonden persoon
     */
    public void setGemeente(final String gemeente) {
        if (zoekPersoonAntwoordType.getUniekGevondenPersoon() == null) {
            zoekPersoonAntwoordType.setUniekGevondenPersoon(new UniekGevondenPersoon());
        }
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setBijhoudingsgemeente(gemeente);
    }

    /**
     * Geef de waarde van gemeente.
     *
     * @return de gemeente van de uniek gevonden persoon
     */
    public String getGemeente() {
        return zoekPersoonAntwoordType.getUniekGevondenPersoon() == null ? null : zoekPersoonAntwoordType.getUniekGevondenPersoon()
                                                                                                         .getBijhoudingsgemeente();
    }
}
