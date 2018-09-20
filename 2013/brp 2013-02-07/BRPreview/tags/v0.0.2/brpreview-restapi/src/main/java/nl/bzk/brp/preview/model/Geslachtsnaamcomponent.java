/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;

public class Geslachtsnaamcomponent {

    private String voorvoegsel;

    private String naam;

    public Geslachtsnaamcomponent() {
    }

    public Geslachtsnaamcomponent(final String naam) {
        this.naam = naam;
    }

    public Geslachtsnaamcomponent(final String voorvoegsel, final String naam) {
        this.voorvoegsel = voorvoegsel;
        this.naam = naam;
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

    @JsonIgnore
    public String getVolledigeNaam() {
        if (StringUtils.hasText(voorvoegsel)) {
            return voorvoegsel + " " + naam;
        } else {
            return naam;
        }
    }

}
