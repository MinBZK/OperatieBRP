/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.AbstractSamengesteldeNaam;
import nl.bzk.brp.model.gedeeld.WijzeGebruikGeslachtsnaam;

/**
 * Samengestelde naam groep van een persoon.
 */
public class PersoonSamengesteldeAanschrijving extends AbstractSamengesteldeNaam {
    private Boolean indAanschrijvingMetAdellijkeTitels;
    private Boolean indAlgoritmischAfgeleid;
    private WijzeGebruikGeslachtsnaam wijzeGebruikGeslachtsnaam;
    /**
     * @return the indAanschrijvingMetAdellijkeTitels
     */
    public Boolean getIndAanschrijvingMetAdellijkeTitels() {
        return indAanschrijvingMetAdellijkeTitels;
    }

    /**
     * @param indAanschrijvingMetAdellijkeTitels the indAanschrijvingMetAdellijkeTitels to set
     */
    public void setIndAanschrijvingMetAdellijkeTitels(final Boolean indAanschrijvingMetAdellijkeTitels) {
        this.indAanschrijvingMetAdellijkeTitels = indAanschrijvingMetAdellijkeTitels;
    }

    /**
     * @return the indAlgoritmischAfgeleid
     */
    public Boolean getIndAlgoritmischAfgeleid() {
        return indAlgoritmischAfgeleid;
    }

    /**
     * @param indAlgoritmischAfgeleid the indAlgoritmischAfgeleid to set
     */

    public void setIndAlgoritmischAfgeleid(final Boolean indAlgoritmischAfgeleid) {
        this.indAlgoritmischAfgeleid = indAlgoritmischAfgeleid;
    }

    /**
     * @return the wijzeGebruikGeslachtsnaam
     */
    public WijzeGebruikGeslachtsnaam getWijzeGebruikGeslachtsnaam() {
        return wijzeGebruikGeslachtsnaam;
    }

    /**
     * @param wijzeGebruikGeslachtsnaam the wijzeGebruikGeslachtsnaam to set
     */
    public void setWijzeGebruikGeslachtsnaam(final WijzeGebruikGeslachtsnaam wijzeGebruikGeslachtsnaam) {
        this.wijzeGebruikGeslachtsnaam = wijzeGebruikGeslachtsnaam;
    }


}
