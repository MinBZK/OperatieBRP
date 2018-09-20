/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.List;

import nl.bzk.brp.model.data.kern.Betr;
import nl.bzk.brp.model.data.kern.HisPersadres;
import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persadres;
import nl.bzk.brp.model.data.kern.Persnation;


// TODO: Auto-generated Javadoc
/**
 * Interface voor het zoeken naar BSN.
 *
 */
public interface BevragingViaBsnService {

    /**
     * Zoek naar een persoon uit de BRP database door middel van het burger service nummer (bsn).
     *
     * @param bsn het bsn waar we de persoon van zoeken
     * @return het pers object van de gevonden persoon
     */
    Pers findPersonByBsn(String bsn);

    /**
     * Zoek nationaliteiten op persoon.
     *
     * @param persoon de persoon waarvoor we de nationaliteiten zoeken
     * @return de lijst van nationaliteiten
     */
    List<Persnation> zoekNationaliteitenOpPersoon(Pers persoon);


    /**
     * Zoek het huidige adres op persoon.
     *
     * @param persoon de persoon
     * @return het persadres
     */
    Persadres findAdresByPers(Pers persoon);

    /**
     * Zoek historische adressen op, op basis van persoonadres.
     *
     * @param huidigAdres de huidig adres
     * @return de list
     */
    List<HisPersadres> findHistorischeAdressenByPers(Persadres huidigAdres);

    /**
     * Zoek ouders op basis van persoon.
     *
     * @param persoon de persoon
     * @return de list
     */
    List<Betr> findOuders(Pers persoon);

    /**
     * Zoek kinderen op basis van persoon.
     *
     * @param persoon de persoon
     * @return de list
     */
    List<Betr> findKinderen(Pers persoon);

    /**
     * Zoek partner op basis van persoon.
     *
     * @param persoon de persoon
     * @return de betr
     */
    Betr findPartner(Pers persoon);

    /**
     * Zoek indicatie op basis van persoon en soort.
     *
     * @param persoon de persoon
     * @param soort de soort
     * @return de boolean
     */
    Boolean findIndicatie(final Pers persoon, int soort);

}
