/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.verwerker;

import com.google.common.collect.Lists;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieMaakFragmentBerichtServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieMaakFragmentBerichtServiceImplTest {

    @InjectMocks
    private SelectieMaakFragmentBerichtServiceImpl maakSelectiePersoonFragmentService;

    @Test
    public void testHappyFlow() {

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        BerichtElement berichtelement = new BerichtElement("naam");
        BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).build();
        final String bericht = maakSelectiePersoonFragmentService.maakPersoonFragment(bijgehoudenPersoon, Lists.newArrayList());
    }
}
