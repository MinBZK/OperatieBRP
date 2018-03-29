/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link StufBerichtBerichtFactoryImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StufBerichtBerichtFactoryImplTest {

    @InjectMocks
    private StufBerichtBerichtFactoryImpl berichtFactory;


    @Test
    public void maakAntwoordBericht() throws Exception {
        final StufBerichtVerzoek verzoek = new StufBerichtVerzoek();
        verzoek.getStuurgegevens().setReferentieNummer("refnummer");
        final StufBerichtResultaat resultaat = new StufBerichtResultaat(verzoek);
        ReflectionTestUtils.setField(resultaat, "referentienummerAntwoordbericht", "refnummerAntwoordbericht");
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        ReflectionTestUtils.setField(resultaat, "tijdstipVerzending", nu);
        resultaat.getMeldingen().add(new Melding(Regel.ALG0001));

        final StufAntwoordBericht antwoordBericht = berichtFactory.maakAntwoordBericht(resultaat);

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
}
