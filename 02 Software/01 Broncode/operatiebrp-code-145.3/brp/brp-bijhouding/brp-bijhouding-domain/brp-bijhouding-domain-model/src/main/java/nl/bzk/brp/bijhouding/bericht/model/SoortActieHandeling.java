/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;

/**
 * wordt gebruikt om handelingen en acties specifiek te koppelen.
 * er zijn regels die alleen in een bepaalde combinatie van handeling en actie wel/niet moeten afgaan.
 */
public class SoortActieHandeling {

    SoortActie soortActie;
    List<AdministratieveHandelingElementSoort> administratieveHandelingElementSoort;

    /**
     * constructor.
     * @param soort {@link SoortActie}
     * @param handelingen lijst van {@link AdministratieveHandelingElementSoort}
     */
    SoortActieHandeling(final SoortActie soort, final AdministratieveHandelingElementSoort... handelingen) {
        this.soortActie = soort;
        this.administratieveHandelingElementSoort = Arrays.asList(handelingen);
    }

    public SoortActie getSoortActie() {
        return soortActie;
    }

    public List<AdministratieveHandelingElementSoort> getAdministratieveHandelingElementSoort() {
        return administratieveHandelingElementSoort;
    }
}
