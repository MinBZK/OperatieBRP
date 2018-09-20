/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;

/**
 * De Class Geslachtsnaamcomponent.
 */
public class Geslachtsnaamcomponent {

    /** De voorvoegsel. */
    private String voorvoegsel;

    /** De naam. */
    private String naam;

    /**
     * Instantieert een nieuwe geslachtsnaamcomponent.
     */
    public Geslachtsnaamcomponent() {
    }

    /**
     * Instantieert een nieuwe geslachtsnaamcomponent.
     *
     * @param naam de naam
     */
    public Geslachtsnaamcomponent(final String naam) {
        this.naam = naam;
    }

    /**
     * Instantieert een nieuwe geslachtsnaamcomponent.
     *
     * @param voorvoegsel de voorvoegsel
     * @param naam de naam
     */
    public Geslachtsnaamcomponent(final String voorvoegsel, final String naam) {
        this.voorvoegsel = voorvoegsel;
        this.naam = naam;
    }

    /**
     * Haalt een voorvoegsel op.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Instellen van voorvoegsel.
     *
     * @param voorvoegsel de nieuwe voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Haalt een naam op.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Instellen van naam.
     *
     * @param naam de nieuwe naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Haalt een volledige naam op.
     *
     * @return volledige naam
     */
    @JsonIgnore
    public String getVolledigeNaam() {
        if (StringUtils.hasText(voorvoegsel)) {
            return voorvoegsel + " " + naam;
        } else {
            return naam;
        }
    }

}
