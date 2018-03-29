/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link GeefDetailsPersoonBerichtFactoryImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeefDetailsPersoonBerichtFactoryImplTest {

    @InjectMocks
    private GeefDetailsPersoonBerichtFactoryImpl geefDetailsPersoonBerichtFactory;
    @Mock
    private VerwerkPersoonBerichtFactory verwerkPersoonBerichtFactory;
    @Mock
    private DatumService datumService;
    @Mock
    private PartijService partijService;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    @Captor
    private ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor;

    private Autorisatiebundel autorisatiebundel;
    private Persoonslijst persoonslijst;

    @Before
    public void setUp() throws Exception {
        BrpNu.set();
        persoonslijst = TestBuilders.PERSOON_MET_HANDELINGEN;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("test", "000001"), Rol.AFNEMER), leveringsautorisatie);
        autorisatiebundel = new Autorisatiebundel(tla, null);

        when(datumService.parseDate(Mockito.anyString())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return LocalDate.parse((String) args[0],
                    DateTimeFormatter
                            .ISO_DATE);
        });

        when(datumService.parseDateTime(Mockito.anyString())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return ZonedDateTime.parse((String) args[0],
                    DateTimeFormatter
                            .ISO_DATE_TIME);
        });

        final Partij brpPartij = TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build();
        when(partijService.geefBrpPartij()).thenReturn(brpPartij);

        when(meldingBepalerService.geefWaarschuwingen(Mockito.any(BijgehoudenPersoon.class))).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void maakGeefDetailsPersoonBericht() throws Exception {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getParameters().setVerantwoording(null);

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(Mockito.any())).thenReturn(Lists.newArrayList(
                maakBericht()
        ));
        geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoonslijst, autorisatiebundel, verzoek, null);

        verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        assertThat(maakBerichtParametersArgumentCaptor.getValue().isVerantwoordingLeveren(), is(true));
        assertThat(maakBerichtParametersArgumentCaptor.getValue().getAutorisatiebundels().get(0), is(autorisatiebundel));
        assertNull(maakBerichtParametersArgumentCaptor.getValue().getAdministratieveHandeling());
    }

    @Test
    public void maakGeefDetailsPersoonBerichtGeenVerantwoording() throws Exception {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getParameters().setVerantwoording("Geen");

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(Mockito.any())).thenReturn(Lists.newArrayList(
                maakBericht()
        ));

        geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoonslijst, autorisatiebundel, verzoek, null);

        verify(verwerkPersoonBerichtFactory)
                .maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        assertThat(maakBerichtParametersArgumentCaptor.getValue().isVerantwoordingLeveren(), is(false));
        assertThat(maakBerichtParametersArgumentCaptor.getValue().getAutorisatiebundels().get(0), is(autorisatiebundel));
        assertNull(maakBerichtParametersArgumentCaptor.getValue().getAdministratieveHandeling());
    }

    @Test
    public void maakGeefDetailsPersoonBerichtMetHistorie() throws Exception {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getParameters().setPeilMomentMaterieelResultaat("2010-01-01");

        final ZonedDateTime peilMomentFormeelResultaat = ZonedDateTime.of(2010, 1, 1, 1, 1, 0, 0, DatumUtil.BRP_ZONE_ID);

        verzoek.getParameters().setPeilMomentFormeelResultaat(peilMomentFormeelResultaat.format(DateTimeFormatter.ISO_DATE_TIME));

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(Mockito.any())).thenReturn(Lists.newArrayList(
                maakBericht()
        ));

        geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoonslijst, autorisatiebundel, verzoek, null);

        verify(verwerkPersoonBerichtFactory)
                .maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        assertThat(maakBerichtParametersArgumentCaptor.getValue().getAutorisatiebundels().get(0), is(autorisatiebundel));
        assertNull(maakBerichtParametersArgumentCaptor.getValue().getAdministratieveHandeling());

        Assert.assertEquals(maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst)
                .getHistorieFilterInformatie().getPeilmomentMaterieel().intValue(), 20100101);
        Assert.assertEquals(maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst)
                .getHistorieFilterInformatie().getPeilmomentFormeel(), peilMomentFormeelResultaat);
        Assert.assertEquals(
                maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst)
                        .getHistorieFilterInformatie().getHistorieVorm(), HistorieVorm.GEEN);
    }

    @Test(expected = StapMeldingException.class)
    public void maakLeegGeefDetailsPersoonBericht() throws Exception {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getParameters().setVerantwoording(null);

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(Mockito.any())).thenReturn(Lists.newArrayList(
                new VerwerkPersoonBericht(null, null, Lists.newArrayList())
        ));
        geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoonslijst, autorisatiebundel, verzoek, null);
    }

    private VerwerkPersoonBericht maakBericht() {
        return new VerwerkPersoonBericht(null, null, Lists.newArrayList(new BijgehoudenPersoon()));
    }
}
