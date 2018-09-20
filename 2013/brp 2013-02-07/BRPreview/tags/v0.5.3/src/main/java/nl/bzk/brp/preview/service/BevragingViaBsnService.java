/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.List;

import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persnation;


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
}
