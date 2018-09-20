/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

/**
 * Deze class is een abtract basis class voor de IST gegevens.
 * 
 */
public abstract class AbstractBrpIstGroepInhoud extends AbstractBrpGroepInhoud implements BrpIstGroepInhoud {

    /**
     * Geef de leeg.
     *
     * @return Er wordt een IllegalStateException gegooid, omdat er geen historie conversie plaats vindt op een
     *         IST-stapel.
     */
    @Override
    public final boolean isLeeg() {
        throw new IllegalStateException("Er kan geen historie conversie plaats vinden op een IST-stapel");
    }
}
