/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * MaakBijgehoudenPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijgehoudenPersoonFactoryImplTest {

    @InjectMocks
    private BijgehoudenPersoonFactoryImpl maakBijgehoudenPersoonService = new BijgehoudenPersoonFactoryImpl();
    @Mock
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;


    @Test
    public void testMaakBijgehoudenPersonen() {

        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon().build();

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());

        final List<BijgehoudenPersoon> bijgehoudenPersonen = maakBijgehoudenPersoonService.maakBijgehoudenPersonen(Lists.newArrayList(berichtgegevens));

        Assert.assertTrue(bijgehoudenPersonen.size() == 1);
    }
}
