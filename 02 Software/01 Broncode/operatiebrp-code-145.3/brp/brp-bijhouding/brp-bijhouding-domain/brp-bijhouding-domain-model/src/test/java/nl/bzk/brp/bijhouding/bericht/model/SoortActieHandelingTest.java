/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.*;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import org.junit.Test;

public class SoortActieHandelingTest {

    @Test
    public void testSoortActieHandeling(){
        SoortActieHandeling soort = new SoortActieHandeling(SoortActie.REGISTRATIE_GEBOORTE,AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND,AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM);
        assertEquals(SoortActie.REGISTRATIE_GEBOORTE,soort.getSoortActie());
        assertEquals(2,soort.getAdministratieveHandelingElementSoort().size());
    }
    @Test
    public void testSoortActieHandelingZonderHandelingen(){
        SoortActieHandeling soort = new SoortActieHandeling(SoortActie.REGISTRATIE_BIJHOUDING);
        assertEquals(SoortActie.REGISTRATIE_BIJHOUDING,soort.getSoortActie());
        assertEquals(0,soort.getAdministratieveHandelingElementSoort().size());
    }
}