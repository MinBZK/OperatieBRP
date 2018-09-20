/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app.support;

import java.util.List;

/**
 * Een afnemer.
 */
public class Afnemer {

    private final String           naam;
    private final List<Abonnement> abonnementen;

    /**
     * De constructor.
     *
     * @param naam de naam van de afnemer
     * @param abonnementen de abonnementen van de afnemer
     */
    public Afnemer(final String naam, final List<Abonnement> abonnementen) {
        this.naam = naam;
        this.abonnementen = abonnementen;
    }

    /**
     * Geeft de naam van de afnemer.
     * @return de naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft de abonnementen.
     * @return de lijst van abonnementen
     */
    public List<Abonnement> getAbonnementen() {
        return abonnementen;
    }
}
