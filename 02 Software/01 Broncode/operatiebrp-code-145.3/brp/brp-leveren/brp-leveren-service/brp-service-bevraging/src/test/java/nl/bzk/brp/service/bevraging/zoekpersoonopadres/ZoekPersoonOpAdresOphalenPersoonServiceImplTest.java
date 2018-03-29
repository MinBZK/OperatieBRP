/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoonopadres;

import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ZoekPersoonOpAdresOphalenZoekPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonOpAdresOphalenPersoonServiceImplTest {
    //@formatter:off
    private static final List<Persoonslijst> TWEE_PERSONEN_VERSCHILLENDE_BAG =  Lists.newArrayList(
        new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
        new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
    //@formatter:on

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @InjectMocks
    private ZoekPersoonOpAdresOphalenPersoonServiceImpl ophalenZoekPersoonPersoonService = new ZoekPersoonOpAdresOphalenPersoonServiceImpl();
    @Mock
    private Dienst dienst;

    @Test
    public void geeftR2289MeldingBijOverschrijdingMaxAantal() throws StapMeldingException {
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(0);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(TWEE_PERSONEN_VERSCHILLENDE_BAG, new Autorisatiebundel(null, dienst),
                null);
    }

    @Test
    public void geeftGeenMeldingBijCorrectAantalResultatenEnGeenBAGMatch() throws StapMeldingException {
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(3);

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(TWEE_PERSONEN_VERSCHILLENDE_BAG, new Autorisatiebundel(null, dienst),
                null);
    }

    @Test
    public void geeftGeenMeldingBijCorrectAantalResultatenEnGeenBAGMatchEnDefaultMax() throws StapMeldingException {
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(null);

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(TWEE_PERSONEN_VERSCHILLENDE_BAG, new Autorisatiebundel(null, dienst),
                null);
    }

    @Test
    public void geeftGeenMeldingBijBAGMatchMeerderePersonen() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftGeenMeldingBijBAGMatchEnkelPersoon() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                    .metObjectElement(Element.PERSOON.getId())
                    .metObject()
                        .metObjectElement(Element.PERSOON_ADRES.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                            .metRecord()
                                .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                                .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(0);

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftMeldingBijGeenAdresGroepEnOverschrijdingMax() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList(TestBuilders.PERSOON_MET_HANDELINGEN);
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(0);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftMeldingBijGeenRecordEnOverschrijdingMax() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                    .metObjectElement(Element.PERSOON.getId())
                    .metObject()
                        .metObjectElement(Element.PERSOON_ADRES.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .eindeGroep()
                    .eindeObject()
                .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(0);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftMeldingBijGeenBAGAttribuutEnOverschrijdingMax() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                    .metObjectElement(Element.PERSOON.getId())
                    .metObject()
                        .metObjectElement(Element.PERSOON_ADRES.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                            .metRecord()
                                .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 1)
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(0);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftMeldingBijCombinatieWelEnGeenBAGAttribuut() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 1)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftGeenMeldingBijVerzoekMetPeilmomentEnHistorischeBAGMatch() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1,ZonedDateTime.now().minusDays(1)))
                            .metDatumAanvangGeldigheid(20100201)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1,ZonedDateTime.
                                of(LocalDate.of(2010, 1, 1), LocalTime.now(), DatumUtil.BRP_ZONE_ID)))
                            .metDatumAanvangGeldigheid(20100101)
                            .metDatumEindeGeldigheid(20100201)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now() .minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);

        final ZoekPersoonOpAdresVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-15");

        ophalenZoekPersoonPersoonService
                .valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), verzoek.getParameters().getZoekBereikParameters());
    }

    @Test
    public void geeftMeldingBijVerzoekMetPeilmomentEnGeenHistorischeBAGMatch() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metDatumAanvangGeldigheid(20100111)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.of(LocalDate.of (2010, 1, 1),
                                LocalTime.now(), DatumUtil.BRP_ZONE_ID)))
                            .metDatumAanvangGeldigheid(20100101)
                            .metDatumEindeGeldigheid(20100111)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        final ZoekPersoonOpAdresVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-15");

        ophalenZoekPersoonPersoonService
                .valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), verzoek.getParameters().getZoekBereikParameters());
    }

    @Test
    public void geeftMeldingBijVerzoekMaterielePeriodeMetBagMatchEnOverscheidingMax() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metDatumAanvangGeldigheid(20100111)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        final ZoekPersoonOpAdresVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-15");
        verzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);

        ophalenZoekPersoonPersoonService
                .valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), verzoek.getParameters().getZoekBereikParameters());
    }

    @Test
    public void geeftMeldingBijVerzoekBereikZonderPeilmomentEnHistorischeBAGMatch() throws StapMeldingException {
        //@formatter:off
        final List<Persoonslijst> persoonslijstList =  Lists.newArrayList(
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.now().minusDays(1)))
                            .metDatumAanvangGeldigheid(20100111)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "123")
                        .eindeRecord()
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1,
                                ZonedDateTime.of(LocalDate.of(2010, 1, 1), LocalTime.now(), DatumUtil.BRP_ZONE_ID)))
                            .metDatumAanvangGeldigheid(20100101)
                            .metDatumEindeGeldigheid(20100201)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L),
            new Persoonslijst(MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(2, ZonedDateTime.now().minusDays(1)))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "456")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject().build(), 0L)
        );
        //@formatter:on
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(1);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        final ZoekPersoonOpAdresVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        verzoek.getParameters().setZoekBereik(Zoekbereik.PEILMOMENT);

        ophalenZoekPersoonPersoonService
                .valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), verzoek.getParameters().getZoekBereikParameters());
    }
}
