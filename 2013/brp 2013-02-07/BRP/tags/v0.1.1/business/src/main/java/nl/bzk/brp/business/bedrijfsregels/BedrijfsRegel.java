/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.validatie.Melding;

/**
 * Interface waaraan elke bedrijfsregel implementatie moet voldoen. Een bedrijfsregel executeert op de nieuwe
 * situatie (zoals wordt beweerd in het binnenkomende BRP bericht) en de huidige situatie zoals die bekend is in de
 * BRP database.
 *
 * @param <Y> Type voor de huidige situatie zoals bekend in de BRP database.
 * @param <T> Type voor de nieuwe situatie, zoals wordt beweerd in het bericht.
 */
public interface BedrijfsRegel<Y extends PersistentRootObject, T extends RootObject> {

    /**
     * Retourneert de code waarmee de bedrijfsregel kan worden geidentificeerd.
     *
     * @return Bedrijfsregel code.
     */
    String getCode();

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param huidigeSituatie De huidige situatie zoals bekend in de BRP database.
     * @param nieuweSituatie De nieuwe situatie die wordt beweerd in het binnenkomende BRP bericht.
     * @return Melding die de bedrijfsregel kan retourneren indien niet aan de bedrijfsregel wordt voldaan.
     */
    Melding executeer(final Y huidigeSituatie, final T nieuweSituatie);
}
