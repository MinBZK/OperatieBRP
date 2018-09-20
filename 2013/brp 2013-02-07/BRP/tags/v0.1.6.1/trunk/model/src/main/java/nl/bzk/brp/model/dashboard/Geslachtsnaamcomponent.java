/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;


/**
 * Achternaam met tussenviegsels.
 */
public class Geslachtsnaamcomponent {

    private String voorvoegsel;

    private String naam;

    /**
     * Constructor voor een dashboard Geslachtsnaamcomponent op basis van een logische PersoonSamengesteldeNaam.
     * @param geslachtsnaamcomponent naam uit logisch model
     */
    public Geslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent) {
        voorvoegsel = geslachtsnaamcomponent.getVoorvoegsel();
        naam = geslachtsnaamcomponent.getNaam();
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

}
