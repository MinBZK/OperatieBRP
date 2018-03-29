/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Root;

/**
 * Deze class representeert de inhoud van de BRP groep Afgeleid Administratief voor objecttype Persoon.
 *
 * Deze class is immutable en threadsafe.
 */
@Root(strict = false)
public final class BrpPersoonAfgeleidAdministratiefInhoud extends AbstractBrpGroepInhoud {

    /**
     * Maak een BrpAfgeleidAdministratiefInhoud object.
     */
    public BrpPersoonAfgeleidAdministratiefInhoud() {
        // leeg object
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        return other instanceof BrpPersoonAfgeleidAdministratiefInhoud;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

}
