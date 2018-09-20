/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.bericht.ber.basis.AbstractBerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;


/**
 *
 *
 */
public class BerichtStuurgegevensGroepBericht extends AbstractBerichtStuurgegevensGroepBericht implements
        BerichtStuurgegevensGroep
{

    // TODO: BOLIE, toegevoegd, generator heeft deze nog niet.
    private String             functie;

    /**
     * Retourneert functie van Stuurgegevens.
     *
     * @return functie.
     */
    public String getFunctie() {
        return functie;
    }

    /**
     * Zet functie van Stuurgegevens.
     *
     * @param functie Functie.
     */
    public void setFunctie(final String functie) {
        this.functie = functie;
    }

}
