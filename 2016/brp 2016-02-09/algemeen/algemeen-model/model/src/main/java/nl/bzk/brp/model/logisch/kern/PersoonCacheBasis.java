/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De geserialiseerde opslag van actuele en historische gegevens van een persoon.
 *
 * Naast de relationele vastlegging van persoonsgegevens - waarin naast actuele gegevens ook materiële en formele
 * historie worden bewaard - is er ook een vastlegging met behulp van 'BLOBS', in casu JSON (JavaSimpleObjectNotation)
 * format. Deze representatie van dezelfde gegevens dient voor het snel kunnen leveren van gegevens.
 *
 * Zowel de Persoon historie volledig gegevens als de Afnemerindicatie gegevens zijn optioneel gedefinieerd. Het blijkt
 * namelijk, in een uitzonderlijk geval, voor te komen dat er geen Persoon blob is, maar wel een Afnemerindicatie blob.
 * Daarom zijn beide clusters optioneel.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonCacheBasis extends BrpObject {

    /**
     * Retourneert Persoon van Persoon cache.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Standaard van Persoon cache.
     *
     * @return Standaard.
     */
    PersoonCacheStandaardGroep getStandaard();

}
