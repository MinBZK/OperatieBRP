/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Map;

/**
 * Dit element identificeert een andere groep.
 *
 * @param <T> het type van de groep waar deze referentie naar verwijst.
 */
public interface BmrGroepReferentie<T extends BmrGroep> extends BmrGroep {

    /**
     * Geef de waarde van referentieId.
     *
     * @return de referentieId
     */
    String getReferentieId();

    /**
     * Geef de groep waar deze referentie naar verwijst.
     *
     * @return groep
     */
    T getReferentie();

    /**
     * geeft aan of het een referentie naar nestaand element is en van het juiste type is.
     *
     * @return boolean
     */
    boolean verwijstNaarBestaandEnJuisteType();

    /**
     * Zet de waarde van de groep waarnaar deze referentie verwijst.
     *
     * @param communicatieIdGroepMap de map van communicatie ids met daarbij horenden BMR groep
     */
    void initialiseer(Map<String, BmrGroep> communicatieIdGroepMap);
}
