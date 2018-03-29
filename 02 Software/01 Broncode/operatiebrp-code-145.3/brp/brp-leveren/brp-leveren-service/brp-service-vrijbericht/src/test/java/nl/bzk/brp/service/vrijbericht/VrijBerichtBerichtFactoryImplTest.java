/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link VrijBerichtBerichtFactoryImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtBerichtFactoryImplTest {

    @InjectMocks
    private VrijBerichtBerichtFactoryImpl berichtFactory;

    @Mock
    private BeheerRepository beheerRepository;

    @Test
    public void maakAntwoordBericht() throws Exception {
        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        verzoek.getStuurgegevens().setReferentieNummer("refnummer");
        final VrijBerichtResultaat resultaat = new VrijBerichtResultaat(verzoek);
        ReflectionTestUtils.setField(resultaat, "referentienummerAntwoordbericht", "refnummerAntwoordbericht");
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        ReflectionTestUtils.setField(resultaat, "tijdstipVerzending", nu);
        resultaat.getMeldingen().add(new Melding(Regel.ALG0001));

        final VrijBerichtAntwoordBericht antwoordBericht = berichtFactory.maakAntwoordBericht(resultaat);

        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer(), is("refnummer"));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij(), is(nullValue()));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij(), is(resultaat.getBrpPartij()));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem(), is(BasisBerichtGegevens.BRP_SYSTEEM));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer(), is("refnummerAntwoordbericht"));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(), is(nu));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getParameters(), is(nullValue()));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getAdministratieveHandelingId(), is(nullValue()));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getMeldingen(), is(resultaat.getMeldingen()));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getResultaat().getHoogsteMeldingsniveau(), is("Fout"));
        assertThat(antwoordBericht.getBasisBerichtGegevens().getResultaat().getVerwerking(), is("Foutief"));
    }

    @Test
    public void maakVerwerkBericht() throws Exception {
        Mockito.when(beheerRepository.haalSoortVrijBerichtOp("soortNaam")).thenReturn(new SoortVrijBericht("soortNaam"));
        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        final VrijBerichtBericht vrijBericht = new VrijBerichtBericht();
        vrijBericht.setInhoud("inhoud");
        vrijBericht.setSoortNaam("soortNaam");
        verzoek.setVrijBericht(vrijBericht);
        final VrijBerichtResultaat resultaat = new VrijBerichtResultaat(verzoek);
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        ReflectionTestUtils.setField(resultaat, "tijdstipVerzending", nu);
        final Partij ontvangendePartij = TestPartijBuilder.maakBuilder().metCode("000123").build();
        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metCode("000123").build();

        final VrijBerichtVerwerkBericht verwerkBericht = berichtFactory.maakVerwerkBericht(resultaat, ontvangendePartij, zendendePartij);

        Mockito.verify(beheerRepository).haalSoortVrijBerichtOp("soortNaam");
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer(), is(nullValue()));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij(), is(ontvangendePartij));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij(), is(resultaat.getBrpPartij()));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem(), is(BasisBerichtGegevens.BRP_SYSTEEM));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer()
                .matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"), is(true));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(), is(nu));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getParameters(), is(nullValue()));
        assertThat(verwerkBericht.getBasisBerichtGegevens().getAdministratieveHandelingId(), is(nullValue()));
        assertThat(verwerkBericht.getVrijBerichtParameters().getOntvangerVrijBericht(), is(ontvangendePartij));
        assertThat(verwerkBericht.getVrijBerichtParameters().getZenderVrijBericht(), is(zendendePartij));
        assertThat(verwerkBericht.getBerichtVrijBericht().getVrijBericht().getInhoud(), is(vrijBericht.getInhoud()));
        assertThat(verwerkBericht.getBerichtVrijBericht().getVrijBericht().getSoortVrijBericht().getNaam(), is(vrijBericht.getSoortNaam()));
    }

}
