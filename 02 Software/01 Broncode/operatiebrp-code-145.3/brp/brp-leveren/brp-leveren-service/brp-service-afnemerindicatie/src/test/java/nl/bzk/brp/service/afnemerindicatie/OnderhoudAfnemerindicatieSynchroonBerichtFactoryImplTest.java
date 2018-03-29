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
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * MaakAntwoordBerichtServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatieSynchroonBerichtFactoryImplTest {

    @Mock
    private PartijService partijService;

    @InjectMocks
    private OnderhoudAfnemerindicatieSynchroonBerichtFactoryImpl maakAntwoordBerichtService;

    @Before
    public void setUp() {
        BrpNu.set();
    }

    @Test
    public void testHappyFlow() throws StapException {
        OnderhoudResultaat verzoekResultaat = maakVerzoekResultaat();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metCode("000001").build());

        OnderhoudAfnemerindicatieAntwoordBericht antwoordBericht = maakAntwoordBerichtService.maakAntwoordBericht(verzoekResultaat);
        Assert.assertNotNull(antwoordBericht);
    }

    private OnderhoudResultaat maakVerzoekResultaat() {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        afnemerindicatieVerzoek.setSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        afnemerindicatieVerzoek.getStuurgegevens().setReferentieNummer("1");
        final int leveringsautorisatieId = 123;
        afnemerindicatieVerzoek.getParameters().setLeveringsAutorisatieId(String.valueOf(leveringsautorisatieId));

        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);
        final int afnemercode = 789;
        afnemerindicatie.setPartijCode(String.valueOf(afnemercode));
        afnemerindicatie.setBsn("123411111");
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(afnemercode));

        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final SoortDienst soortDienst = afnemerindicatieVerzoek.getSoortDienst();
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final OnderhoudResultaat resultaat = new OnderhoudResultaat(afnemerindicatieVerzoek);
        resultaat.setPersoonslijst(persoonslijst);
        resultaat.setAutorisatiebundel(autorisatiebundel);
        return resultaat;
    }
}
