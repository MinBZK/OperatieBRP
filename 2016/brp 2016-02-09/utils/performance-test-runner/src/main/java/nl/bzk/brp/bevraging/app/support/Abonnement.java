/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app.support;

/**
 * Een abonnement.
 */
public class Abonnement {

    private final String omschrijving;
    private final String expressie;

    /**
     * De constructor.
     * @param omschrijving de omschrijving van een abonnement
     * @param expressie de expressie bij het abonnement
     */
    public Abonnement(final String omschrijving, final String expressie) {
        this.omschrijving = omschrijving;
        this.expressie = expressie;
    }

    /**
     * Geeft de omschrijving.
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geeft de expressie.
     * @return De expressie string
     */
    public String getExpressie() {
        return expressie;
    }
}
