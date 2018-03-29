/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * MeldingBepalerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class MeldingBepalerServiceImplTest {

    @BeforeClass
    public static void init() {
        BrpNu.set();
    }

    @InjectMocks
    private MeldingBepalerServiceImpl meldingBepalerService;

    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;


    @Test
    public void testMeldingVerstrekkingsbeperking() {
        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(true);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon);

        Assert.assertFalse(meldingen.isEmpty());
        final Melding melding = meldingen.stream().findFirst().orElseThrow(AssertionError::new);
        Assert.assertEquals(Regel.R1340, melding.getRegel());
    }



    @Test
    public void testMeldingVerstrekkingsbeperking_LijstMetPersonen() {
        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(true);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();
        final List<BijgehoudenPersoon> bijgehoudenPersonen = Lists.newArrayList(bijgehoudenpersoon, bijgehoudenpersoon);

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenPersonen);

        Assert.assertEquals(2, meldingen.size());
        meldingen.stream().forEach(
                m -> Assert.assertEquals(Regel.R1340, m.getRegel())
        );
    }


    @Test
    public void testGeenMelding() {
        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(false);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon);

        Assert.assertTrue(meldingen.isEmpty());
    }


    @Test
    public void testBuitenMeldingAfnemerindicatie() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();
        final Populatie populatie = Populatie.BUITEN;

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon, populatie,
                maakAutorisatieBundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE));

        Assert.assertFalse(meldingen.isEmpty());
        final Melding melding = meldingen.stream().findFirst().get();
        Assert.assertEquals(Regel.R1315, melding.getRegel());
    }


    @Test
    public void testVerlaatNietMeldingAfnemerindicatie() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();
        final Populatie populatie = Populatie.BETREEDT;

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon, populatie,
                maakAutorisatieBundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE));

        Assert.assertTrue(meldingen.isEmpty());
    }


    @Test
    public void testVerlaatMeldingDienstMutLev() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();
        final Populatie populatie = Populatie.VERLAAT;

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon, populatie,
                maakAutorisatieBundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING));

        Assert.assertFalse(meldingen.isEmpty());
        final Melding melding = meldingen.stream().findFirst().get();
        Assert.assertEquals(Regel.R1316, melding.getRegel());
    }


    @Test
    public void testMeldingVerstrekkingsbeperkingDienstMutLev() {
        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(true);
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final BerichtElement berichtelement = BerichtElement.builder().build();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, berichtelement).metCommunicatieId(1).build();
        final Populatie populatie = Populatie.BINNEN;
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonslijst, autorisatiebundel.getPartij())).thenReturn(true);

        final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenpersoon, populatie, autorisatiebundel);

        Assert.assertFalse(meldingen.isEmpty());
        final Melding melding = meldingen.stream().findFirst().get();
        Assert.assertEquals(Regel.R2586, melding.getRegel());
    }

    private Autorisatiebundel maakAutorisatieBundel(final SoortDienst soortDienst) {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }


    private Persoonslijst maakPersoonMetVolledigeVerstrekkingsbeperking(boolean verstrekkingsbeperkingWaarde) {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(ElementConstants.PERSOON)
            .metId(1)
            .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING)
                .metGroep()
                    .metGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD)
                        .metRecord()
                            .metId(1)
                            .metAttribuut()
                                .metType(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE)
                                .metWaarde(verstrekkingsbeperkingWaarde)
                            .eindeAttribuut()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.from(BrpNu.get().getDatum().toInstant().atZone(ZoneId.of ("UTC")))))
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }
}
