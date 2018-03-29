/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link VrijBerichtInhoudControleServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtInhoudControleServiceImplTest {

    private static final String NAAM_NIETBESTAAND_SOORTVRIJBERICHT = "niet bestaand";
    private static final String NAAM_GELDIG_SOORTVRIJBERICHT_START_GISTEREN_MET_EINDE = "geldig met einde";
    private static final String NAAM_GELDIG_SOORTVRIJBERICHT_START_VANDAAG_ZONDER_EINDE = "geldig zonder einde";
    private static final String NAAM_ONGELDIG_SOORTVRIJBERICHT_VANAF_MORGEN = "ongeldig start morgen";
    private static final String NAAM_ONGELDIG_SOORTVRIJBERICHT_VERLOPEN = "ongeldig verlopen";
    private static final String NAAM_GELDIG_SOORTVRIJBERICHT_ZONDER_START = "ongeldig zonder start";
    private static final String NAAM_GELDIG_SOORTVRIJBERICHT_LEEG = "geldig leeg";

    @InjectMocks
    private VrijBerichtInhoudControleServiceImpl vrijBerichtInhoudControleService;
    @Mock
    private StamTabelService stamTabelService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void voorTest() {
        List<Map<String, Object>> stamgegevens = new ArrayList<>();
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_GELDIG_SOORTVRIJBERICHT_START_GISTEREN_MET_EINDE)
                .put("dataanvgel", DatumUtil.gisteren())
                .put("dateindegel", DatumUtil.morgen())
                .build());
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_GELDIG_SOORTVRIJBERICHT_START_VANDAAG_ZONDER_EINDE)
                .put("dataanvgel", DatumUtil.vandaag())
                .build());
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_ONGELDIG_SOORTVRIJBERICHT_VANAF_MORGEN)
                .put("dataanvgel", DatumUtil.morgen())
                .build());
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_ONGELDIG_SOORTVRIJBERICHT_VERLOPEN)
                .put("dataanvgel", DatumUtil.gisteren())
                .put("dateindegel", DatumUtil.gisteren())
                .build());
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_GELDIG_SOORTVRIJBERICHT_ZONDER_START)
                .put("dateindegel", DatumUtil.morgen())
                .build());
        stamgegevens.add(ImmutableMap.<String, Object>builder()
                .put("naam", NAAM_GELDIG_SOORTVRIJBERICHT_LEEG)
                .build());

        final StamtabelGegevens gegevens = new StamtabelGegevens(null, stamgegevens);
        when(stamTabelService.geefStamgegevens(any())).thenReturn(gegevens);
    }

    @Test
    public void bestaandEnGeldigStartGisterenMetEinde() throws StapMeldingException {
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_GELDIG_SOORTVRIJBERICHT_START_GISTEREN_MET_EINDE));
    }

    @Test
    public void bestaandEnGeldigStartVandaagZonderEinde() throws StapMeldingException {
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_GELDIG_SOORTVRIJBERICHT_START_VANDAAG_ZONDER_EINDE));
    }

    @Test
    public void nietBestaand() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2472));
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_NIETBESTAAND_SOORTVRIJBERICHT));
    }

    @Test
    public void bestaandEnOngeldigStartMorgen() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2473));
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_ONGELDIG_SOORTVRIJBERICHT_VANAF_MORGEN));
    }

    @Test
    public void bestaandOngeldigVerlopen() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2473));
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_ONGELDIG_SOORTVRIJBERICHT_VERLOPEN));
    }

    @Test
    public void bestaandGeldigZonderStart() throws StapMeldingException {
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_GELDIG_SOORTVRIJBERICHT_ZONDER_START));
    }

    @Test
    public void bestaandGeldigLeeg() throws StapMeldingException {
        vrijBerichtInhoudControleService.controleerInhoud(maakVerzoek(NAAM_GELDIG_SOORTVRIJBERICHT_LEEG));
    }

    private VrijBerichtVerzoek maakVerzoek(final String soortNaam) {
        final VrijBerichtBericht bericht = new VrijBerichtBericht();
        bericht.setSoortNaam(soortNaam);
        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        verzoek.setVrijBericht(bericht);
        return verzoek;
    }

}
