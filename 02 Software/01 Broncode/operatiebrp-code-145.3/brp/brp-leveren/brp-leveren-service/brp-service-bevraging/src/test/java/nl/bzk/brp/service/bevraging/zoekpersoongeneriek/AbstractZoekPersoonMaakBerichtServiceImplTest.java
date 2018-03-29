/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractZoekPersoonMaakBerichtServiceImplTest {

    private final Set<Melding> meldingen = Sets.newHashSet();
    @Mock
    private ZoekPersoon.OphalenPersoonService<ZoekPersoonVerzoek> ophalenPersoonService;
    @Mock
    private ZoekPersoonBerichtFactory zoekPersoonBerichtFactory;
    @Mock
    private PeilmomentValidatieService peilmomentValidatieService;
    @Mock
    private ValideerZoekCriteriaService valideerZoekCriteriaService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieValidatieService;
    @Mock
    private PartijService partijService;
    @InjectMocks
    private final TestZoekPersoonMaakBerichtServiceImpl
            maakZoekPersoonBerichtService =
            new TestZoekPersoonMaakBerichtServiceImpl(leveringsautorisatieValidatieService, partijService);

    @Before
    public void voorTest() {
        Mockito.when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build());

        meldingen.clear();
    }

    @Test
    public void testHappyFlow() throws StapException, AutorisatieException {
        final ZoekPersoonVerzoek zoekPersoonVerzoek = maakZoekPersoonVerzoek();
        final BevragingResultaat resultaat = new BevragingResultaat();
        resultaat.setAutorisatiebundel(maakAutorisatiebundel(Stelsel.BRP));
        maakZoekPersoonBerichtService.voerDienstSpecifiekeStappenUit(zoekPersoonVerzoek, resultaat);

        Mockito.inOrder(valideerZoekCriteriaService, ophalenPersoonService, zoekPersoonBerichtFactory);
        Mockito.verify(valideerZoekCriteriaService).valideerZoekCriteria(Matchers.any(), Matchers.any());
        Mockito.verify(ophalenPersoonService).voerStapUit(Matchers.any(), Matchers.any());
        Mockito.verify(zoekPersoonBerichtFactory).maakZoekPersoonBericht(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any());
    }

    @Test(expected = StapMeldingException.class)
    public void testFoutNaValidatieZoekCriteria() throws StapException, AutorisatieException {
        Mockito.when(valideerZoekCriteriaService.valideerZoekCriteria(Matchers.any(), Matchers.any()))
                .thenReturn(Sets.newHashSet(new Melding(SoortMelding.FOUT, Regel.R1244)));

        final ZoekPersoonVerzoek zoekPersoonVerzoek = maakZoekPersoonVerzoek();
        maakZoekPersoonBerichtService.voerDienstSpecifiekeStappenUit(zoekPersoonVerzoek, new BevragingResultaat());

        Mockito.inOrder(valideerZoekCriteriaService);
        Mockito.verify(valideerZoekCriteriaService).valideerZoekCriteria(Matchers.any(), Matchers.any());
        Mockito.verifyZeroInteractions(ophalenPersoonService);
        Mockito.verifyZeroInteractions(zoekPersoonBerichtFactory);
    }


    @Test(expected = StapMeldingException.class)
    public void testFoutNaValidatieZoekPersoonSpecifiekeZoekCriteria() throws StapException, AutorisatieException {
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.R2288));

        final ZoekPersoonVerzoek zoekPersoonVerzoek = maakZoekPersoonVerzoek();
        maakZoekPersoonBerichtService.voerDienstSpecifiekeStappenUit(zoekPersoonVerzoek, new BevragingResultaat());

        Mockito.inOrder(valideerZoekCriteriaService);
        Mockito.verify(valideerZoekCriteriaService).valideerZoekCriteria(Matchers.any(), Matchers.any());
        Mockito.verifyZeroInteractions(ophalenPersoonService);
        Mockito.verifyZeroInteractions(zoekPersoonBerichtFactory);
    }

    private ZoekPersoonVerzoek maakZoekPersoonVerzoek() {
        final ZoekPersoonVerzoek verzoek = new ZoekPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        verzoek.setOin(new OIN("OinOndertekenaar", "OinTransporteur"));
        verzoek.setXmlBericht("xmlBerichtDummy");

        verzoek.getStuurgegevens().setZendendePartijCode("1");

        verzoek.getParameters().setLeveringsAutorisatieId("2");
        verzoek.getParameters().setDienstIdentificatie("3");
        verzoek.getParameters().setRolNaam("rolNaam");

        return verzoek;
    }

    private Autorisatiebundel maakAutorisatiebundel(final Stelsel stelsel) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partij.addPartijRol(partijRol);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setStelsel(stelsel);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }


    private final class TestZoekPersoonMaakBerichtServiceImpl extends AbstractZoekPersoonMaakBerichtServiceImpl<ZoekPersoonVerzoek> {

        public TestZoekPersoonMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService,
                                                     final PartijService partijService) {
            super(leveringsautorisatieService, partijService);
        }

        @Override
        protected ZoekPersoon.OphalenPersoonService<ZoekPersoonVerzoek> getOphalenPersoonService() {
            return ophalenPersoonService;
        }

        @Override
        protected Set<Melding> valideerDienstSpecifiek(final ZoekPersoonVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
            return meldingen;
        }

        @Override
        protected String getDienstSpecifiekeLoggingString() {
            return "test";
        }
    }
}
