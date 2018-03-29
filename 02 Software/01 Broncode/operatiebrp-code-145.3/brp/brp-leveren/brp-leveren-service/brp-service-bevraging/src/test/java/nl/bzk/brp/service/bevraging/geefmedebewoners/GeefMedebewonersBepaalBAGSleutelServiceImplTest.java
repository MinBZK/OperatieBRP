/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.collect.Iterables;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GeefMedebewonersBepaalBAGSleutelServiceImplTest {

    private final Actie actieInhoud_20140101 = TestVerantwoording.maakActie(1, TestDatumUtil.van(2015, 1, 1));
    private final Actie actieInhoud_20130101 = TestVerantwoording.maakActie(2, TestDatumUtil.van(2016, 1, 1));
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @InjectMocks
    private GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService = new GeefMedebewonersBepaalBAGSleutelServiceImpl();
    @Mock
    private DatumService datumService;

    @Before
    public void before() throws Exception {
        Mockito.when(datumService.parseDate(Mockito.anyString())).thenReturn(LocalDate.of(2014, 1, 1));
    }

    @Test
    public void bepaalBAGSleutelHappyFlow() throws Exception {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(), "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on

        final String BAGSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
        Assert.assertEquals("BAG", BAGSleutel);
    }


    @Test
    public void bepaalBAGSleutelGeenNLAdresVerkeerdeLandgebied() throws Exception {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6032")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on
        thrown.expect(new ExceptionRegelMatcher(Regel.R2383));

        bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
    }

    @Test
    public void bepaalBAGSleutelGeenNLAdresBuitenlandsadresgevuld() throws Exception {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), "g")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), "e")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), "v")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), "u")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), "l")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), "d")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on
        thrown.expect(new ExceptionRegelMatcher(Regel.R2383));

        bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
    }

    @Test
    public void bepaalBAGSleutel_GeenAdres() throws Exception {
        thrown.expect(StapMeldingException.class);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2383));

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metDatumAanvangGeldigheid(20150101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on

        final String BAGSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
        Assert.assertNull("BAG", BAGSleutel);
    }

    @Test
    public void bepaalBAGSleutel_MeerdereAdressen() throws Exception {

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling handelingNu = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000034", nu, SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, nu, "000001", null)
                ).build());

        final AdministratieveHandeling handelingToen = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000034", nu.minusYears(1), SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, nu.minusYears(1), "000001", null)
                ).build());

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(Iterables.getOnlyElement(handelingToen.getActies()))
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metId(2)
                            .metActieInhoud(Iterables.getOnlyElement(handelingNu.getActies()))
                            .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(handelingNu.getTijdstipRegistratie().minusDays(1).toLocalDate()))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG1")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                        .metRecord()
                            .metId(1)
                            .metActieInhoud(Iterables.getOnlyElement(handelingToen.getActies()))
                            .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(handelingToen.getTijdstipRegistratie().minusDays(1).toLocalDate()))
                            .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(handelingNu.getTijdstipRegistratie().minusDays(1).toLocalDate()))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG2")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on

        final String BAGSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L),
                DatumUtil.vanDatumNaarInteger(handelingToen.getTijdstipRegistratie().toLocalDate()));
        Assert.assertNotNull("BAG", BAGSleutel);
    }

    @Test
    public void bepaalBAGSleutelPersoonOverleden() throws Exception {
        thrown.expect(StapMeldingException.class);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2404));

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "O")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  6030)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on

        bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
    }


    @Test
    public void bepaalBAGSleutelGeenBAG() throws Exception {
        thrown.expect(StapMeldingException.class);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2383));
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud_20140101)
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud_20140101)
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  "6030")
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on

        final String BAGSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(new Persoonslijst(persoon, 0L), 20140101);
        Assert.assertEquals("BAG", BAGSleutel);
    }
}
