/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.AbstractSamengesteldeNaam;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;

/**
 * Samengestelde naam groep van een persoon.
 */
public class PersoonSamengesteldeNaam extends AbstractSamengesteldeNaam {
    private AdellijkeTitel adellijkeTitel;
    private Boolean indNamenreeksAlsGeslachtsnaam;
    private Boolean indAlgoritmischAfgeleid;

    /**
     * @return het adellijkeTitel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * @param adellijkeTitel het adellijkeTitel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }


    /**
     * @return the indNamenreeksAlsGeslachtsnaam
     */
    public Boolean getIndNamenreeksAlsGeslachtsnaam() {
        return indNamenreeksAlsGeslachtsnaam;
    }

    /**
     * @param indNamenreeksAlsGeslachtsnaam the indNamenreeksAlsGeslachtsnaam to set
     */
    public void setIndNamenreeksAlsGeslachtsnaam(final Boolean indNamenreeksAlsGeslachtsnaam) {
        this.indNamenreeksAlsGeslachtsnaam = indNamenreeksAlsGeslachtsnaam;
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


}
