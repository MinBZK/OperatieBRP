/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

/**
 */
public class Afnemerbericht {

    private String partijCode;
    private String abonnement;
    private String bericht;

    public Afnemerbericht(final String partijCode, final String abonnement, final String bericht) {
        this.partijCode = partijCode;
        this.abonnement = abonnement;
        this.bericht = bericht;
    }

    public String getPartijCode() {
        return partijCode;
    }

    public String getAbonnement() {
        return abonnement;
    }

    public String getBericht() {
        return bericht;
    }

    @Override
    public String toString() {
        return "Afnemerbericht{" +
            "partijCode='" + partijCode + '\'' +
            ", abonnement='" + abonnement + '\'' +
            ", bericht='" + bericht + '\'' +
            '}';
    }
}
