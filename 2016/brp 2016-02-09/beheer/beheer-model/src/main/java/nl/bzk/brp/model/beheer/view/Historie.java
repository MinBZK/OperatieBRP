/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.Set;


/**
 * Interface voor de historie.
 *
 * @param <T> Type waarvan de materiële historie is.
 */
public interface Historie<T> {

    /**
     * Verzameling met historische inhoud.
     *
     * @return de historische inhoud.
     */
    Set<T> getInhoud();

    /**
     * Verzameling met verval waarden.
     *
     * @return De verval waarden.
     */
    Set<T> getVerval();

    /**
     * Verzameling met de geldigheden.
     *
     * @return De geldigheden.
     */
    Set<T> getGeldigheid();

    /**
     * Verzameling met verval waarden t.b.v. mutatie (MUTS).
     *
     * @return De verval waarden t.b.v. mutatie.
     */
    Set<T> getVervalTbvMutatie();
}
