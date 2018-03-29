/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link GeefMedebewonersOphalenPersoonServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeefMedebewonersOphalenPersoonServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService;
    @Mock
    private DatumService datumService;
    @InjectMocks
    private GeefMedebewonersOphalenPersoonServiceImpl service;

    @Before
    public void before() throws Exception {
        when(datumService.parseDate("2010-01-01")).thenReturn(LocalDate.of(2010, 1, 1));
    }

    @Test
    public void verwijdertPersoonAlsBAGNietBepaaldKanWorden() throws Exception {
        final List<Persoonslijst> persoonsgegevens = Lists.newArrayList();
        persoonsgegevens.add(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
        final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters parameters = new ZoekPersoonGeneriekVerzoek.ZoekBereikParameters();
        doThrow(new StapMeldingException(Regel.R2383)).when(bepaalBAGSleutelService).bepaalBAGSleutel(any(), any());

        service.valideerAantalZoekResultaten(persoonsgegevens, null, parameters);

        assertThat(persoonsgegevens.isEmpty(), is(true));
    }

    @Test
    public void gooitStapMeldingExceptionBijMeerdereBAGs() throws Exception {
        final List<Persoonslijst> persoonsgegevens = Lists.newArrayList();
        final Persoonslijst persoonslijst1 = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final Persoonslijst persoonslijst2 = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        persoonsgegevens.add(persoonslijst1);
        persoonsgegevens.add(persoonslijst2);
        final ZoekPersoonGeneriekVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-01");

        when(bepaalBAGSleutelService.bepaalBAGSleutel(persoonslijst1, 20100101)).thenReturn("abc");
        when(bepaalBAGSleutelService.bepaalBAGSleutel(persoonslijst2, 20100101)).thenReturn("def");
        thrown.expect(new ExceptionRegelMatcher(Regel.R2392));

        service.valideerAantalZoekResultaten(persoonsgegevens, null, verzoek.getParameters().getZoekBereikParameters());
    }

    @Test
    public void goedpadZonderPeilmomentMaterieel() throws Exception {
        final List<Persoonslijst> persoonsgegevens = Lists.newArrayList();
        final Persoonslijst persoonslijst1 = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final Persoonslijst persoonslijst2 = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        persoonsgegevens.add(persoonslijst1);
        persoonsgegevens.add(persoonslijst2);
        final ZoekPersoonGeneriekVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        BrpNu.set(ZonedDateTime.of(2016, 11, 30, 11, 11, 11, 0, ZoneId.of("UTC")));
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-01");

        when(bepaalBAGSleutelService.bepaalBAGSleutel(persoonslijst1, 20161130)).thenReturn("abc");

        service.valideerAantalZoekResultaten(persoonsgegevens, null, verzoek.getParameters().getZoekBereikParameters());

        assertThat(persoonsgegevens, contains(persoonslijst1, persoonslijst2));
    }

}
