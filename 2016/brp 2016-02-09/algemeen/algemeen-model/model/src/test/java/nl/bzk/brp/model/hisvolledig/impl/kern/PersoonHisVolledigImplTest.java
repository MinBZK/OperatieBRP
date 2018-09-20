/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;

public class PersoonHisVolledigImplTest {

    @Test
    public void testVulAanMetAdministratieveHandelingenBegintMetLegeLijst() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

        final List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingHisVolledigs = new ArrayList<>();
        administratieveHandelingHisVolledigs.add(new AdministratieveHandelingHisVolledigImpl());
        persoonHisVolledig.vulAanMetAdministratieveHandelingen(administratieveHandelingHisVolledigs);

        final List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingHisVolledigs2 = new ArrayList<>();
        administratieveHandelingHisVolledigs2.add(new AdministratieveHandelingHisVolledigImpl());
        persoonHisVolledig.vulAanMetAdministratieveHandelingen(administratieveHandelingHisVolledigs);

        assertEquals(1, persoonHisVolledig.getAdministratieveHandelingen().size());
    }

}
