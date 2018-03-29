/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;

/**
 * Class waarin de resultaten komen van het opslaan van een persoonslijst.
 */
public final class PersoonslijstPersisteerResultaat {

    private Persoon persoon;
    private Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();

    /**
     * Constructor voor de resultaat van het persisteren van een persoonslijst.
     * @param persoon de persoon die opgeslagen is.
     * @param administratieveHandelingen de set met administratieve handelingen die nieuw zijn voor de persoonslijst.
     */
    public PersoonslijstPersisteerResultaat(final Persoon persoon, final Set<AdministratieveHandeling> administratieveHandelingen) {
        this.persoon = persoon;
        for (final AdministratieveHandeling administratieveHandeling : administratieveHandelingen) {
            this.administratieveHandelingen.add(administratieveHandeling);
        }
    }

    /**
     * Geef de waarde van persoon.
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Geef de waarde van administratieve handelingen.
     * @return administratieve handelingen
     */
    public Set<AdministratieveHandeling> getAdministratieveHandelingen() {
        return Collections.unmodifiableSet(administratieveHandelingen);
    }
}
