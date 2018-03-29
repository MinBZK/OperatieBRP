/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen;
import nl.bzk.brp.service.algemeen.afnemerindicatie.VerwijderAfnemerindicatieParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerwijderAfnemerIndicatieServiceImplTest {

    @InjectMocks
    private VerwijderAfnemerIndicatieServiceImpl service;

    @Mock
    private GeneriekeOnderhoudAfnemerindicatieStappen.VerwijderAfnemerindicatie verwijderAfnemerindicatieAfnemerindicatie;

    @Test
    public void testHapplyflow() throws StapException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        final SoortDienst soortDienst = SoortDienst.VERWIJDERING_AFNEMERINDICATIE;

        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(afnemerindicatieVerzoek);
        verzoekResultaat.setPersoonslijst(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
        verzoekResultaat.setAutorisatiebundel(autorisatiebundel);
        service.verwijderAfnemerindicatie(verzoekResultaat);

        ArgumentCaptor<VerwijderAfnemerindicatieParams> argumentCaptor = ArgumentCaptor.forClass(VerwijderAfnemerindicatieParams.class);
        Mockito.verify(verwijderAfnemerindicatieAfnemerindicatie).voerStapUit(argumentCaptor.capture());

        final VerwijderAfnemerindicatieParams value = argumentCaptor.getValue();
        Assert.assertEquals(tla, value.getToegangLeveringsautorisatie());
        Assert.assertEquals(verzoekResultaat.getPersoonslijst(), value.getPersoonslijst());
        Assert.assertEquals(verzoekResultaat.getAutorisatiebundel().getDienst().getId().intValue(), value.getVerantwoordingDienstId());
    }
}
