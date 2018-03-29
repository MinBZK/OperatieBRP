/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieBerichtFactoryImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieBerichtFactoryImplTest {

    @Mock
    private VerwerkPersoonBerichtFactory verwerkPersoonBerichtFactory;

    @Mock
    private PartijService partijService;

    @InjectMocks
    private SelectieResultaatBerichtFactoryImpl selectieBerichtFactory;

    @Test
    public void testMaakBerichtVoorSelectie() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final List<VerwerkPersoonBericht> verwerkPersoonBerichten = new ArrayList<>();

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test"))
                .build();
        List<BijgehoudenPersoon> bijgehoudenPersonen = Lists.newArrayList(bijgehoudenPersoon);
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metParameters()
                    .metDienst(dienst)
                    .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                .eindeParameters()
            .build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, bijgehoudenPersonen);
        verwerkPersoonBerichten.add(bericht);
        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(Mockito.any())).thenReturn(verwerkPersoonBerichten);

        //test
        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        SelectieAutorisatiebundel selectieAutorisatiebundel = new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht);
        final List<VerwerkPersoonBericht>
                verwerkPersoonBerichtenReturned =
                selectieBerichtFactory.maakBerichten(Lists.newArrayList(selectieAutorisatiebundel), persoonslijst);
        Assert.assertEquals(verwerkPersoonBerichten, verwerkPersoonBerichtenReturned);
    }
}
