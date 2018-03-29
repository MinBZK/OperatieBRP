/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link StufBerichtInhoudControleServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StufBerichtInhoudControleServiceImplTest {

    private static final String NR_NIETBESTAAND_VERSIE_NR = "niet bestaand";
    private static final String NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE = "geldig met einde";
    private static final String NR_GELDIG_VERSIE_NR_START_VANDAAG_ZONDER_EINDE = "geldig zonder einde";
    private static final String NR_ONGELDIG_VERSIE_NR_VANAF_MORGEN = "ongeldig start morgen";
    private static final String NR_ONGELDIG_VERSIE_NR_VERLOPEN = "ongeldig verlopen";
    private static final String BERICHT_NIETBESTAAND_BERICHT = "niet bestaand";
    private static final String BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE = "geldig met einde";
    private static final String BERICHT_GELDIG_BERICHT_START_VANDAAG_ZONDER_EINDE = "geldig zonder einde";
    private static final String BERICHT_ONGELDIG_BERICHT_VANAF_MORGEN = "ongeldig start morgen";
    private static final String BERICHT_ONGELDIG_BERICHT_VERLOPEN = "ongeldig verlopen";


    @InjectMocks
    private StufBerichtInhoudControleServiceImpl stufBerichtInhoudControleService;
    @Mock
    private StamTabelService stamTabelService;
    @Mock
    private SchemaValidatorService schemaValidatorService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void voorTest() {
        List<Map<String, Object>> stamgegevensVersieStuf = new ArrayList<>();
        stamgegevensVersieStuf.add(ImmutableMap.<String, Object>builder()
                .put("nr", NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE)
                .put("dateindegel", 99991231)
                .build());
        stamgegevensVersieStuf.add(ImmutableMap.<String, Object>builder()
                .put("nr", NR_GELDIG_VERSIE_NR_START_VANDAAG_ZONDER_EINDE)
                .put("dataanvgel", DatumUtil.vandaag())
                .build());
        stamgegevensVersieStuf.add(ImmutableMap.<String, Object>builder()
                .put("nr", NR_ONGELDIG_VERSIE_NR_VANAF_MORGEN)
                .put("dataanvgel", DatumUtil.morgen())
                .build());
        stamgegevensVersieStuf.add(ImmutableMap.<String, Object>builder()
                .put("nr", NR_ONGELDIG_VERSIE_NR_VERLOPEN)
                .put("dataanvgel", DatumUtil.gisteren())
                .put("dateindegel", DatumUtil.gisteren())
                .build());

        List<Map<String, Object>> stamgegevensSoortBericht = new ArrayList<>();
        stamgegevensSoortBericht.add(ImmutableMap.<String, Object>builder()
                .put("naam", BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE)
                .put("dateindegel", 99991231)
                .build());
        stamgegevensSoortBericht.add(ImmutableMap.<String, Object>builder()
                .put("naam", BERICHT_GELDIG_BERICHT_START_VANDAAG_ZONDER_EINDE)
                .put("dataanvgel", DatumUtil.vandaag())
                .build());
        stamgegevensSoortBericht.add(ImmutableMap.<String, Object>builder()
                .put("naam", BERICHT_ONGELDIG_BERICHT_VANAF_MORGEN)
                .put("dataanvgel", DatumUtil.morgen())
                .build());
        stamgegevensSoortBericht.add(ImmutableMap.<String, Object>builder()
                .put("naam", BERICHT_ONGELDIG_BERICHT_VERLOPEN)
                .put("dataanvgel", DatumUtil.gisteren())
                .put("dateindegel", DatumUtil.gisteren())
                .build());

        final StamtabelGegevens gegevensStuf = new StamtabelGegevens(null, stamgegevensVersieStuf);
        when(stamTabelService.geefStamgegevens(Element.VERSIESTUFBG.getNaam() + StamtabelGegevens.TABEL_POSTFIX)).thenReturn(gegevensStuf);
        final StamtabelGegevens gegevensSoortBericht = new StamtabelGegevens(null, stamgegevensSoortBericht);
        when(stamTabelService.geefStamgegevens(Element.VERTALINGBERICHTSOORTBRP.getNaam() + StamtabelGegevens.TABEL_POSTFIX)).thenReturn(gegevensSoortBericht);
    }


    @Test
    public void geenXmlBrpBericht() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2443));
        final StufBerichtVerzoek
                verzoek =
                maakVerzoek(BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE, "MutatieBericht", null);
        stufBerichtInhoudControleService.controleerInhoud(verzoek);
    }

    @Test
    public void geenInvalideXmlBrpBericht() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2443));
        final StufBerichtVerzoek verzoek = maakVerzoek(BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE);
        Mockito.doThrow(SchemaValidatorService.SchemaValidatieException.class).when(schemaValidatorService).valideer(Mockito.any(), Mockito.any());
        stufBerichtInhoudControleService.controleerInhoud(verzoek);
    }

    @Test
    public void bestaandEnGeldigStartGisterenMetEinde() throws StapMeldingException {
        stufBerichtInhoudControleService
                .controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE));
    }

    @Test
    public void bestaandEnGeldigStartVandaagZonderEinde() throws StapMeldingException {
        stufBerichtInhoudControleService
                .controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_VANDAAG_ZONDER_EINDE, NR_GELDIG_VERSIE_NR_START_VANDAAG_ZONDER_EINDE));
    }

    @Test
    public void nietBestaandVersie() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2439));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE, NR_NIETBESTAAND_VERSIE_NR));
    }

    @Test
    public void nietBestaandBerichtSoort() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2441));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_NIETBESTAAND_BERICHT, NR_GELDIG_VERSIE_NR_START_VANDAAG_ZONDER_EINDE));
    }

    @Test
    public void bestaandEnOngeldigStartMorgenVersie() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2440));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_VANDAAG_ZONDER_EINDE, NR_ONGELDIG_VERSIE_NR_VANAF_MORGEN));
    }

    @Test
    public void bestaandEnOngeldigStartMorgenSoortBericht() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2442));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_ONGELDIG_BERICHT_VANAF_MORGEN, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE));
    }


    @Test
    public void bestaandOngeldigVerlopenVersie() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2440));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_VANDAAG_ZONDER_EINDE, NR_ONGELDIG_VERSIE_NR_VERLOPEN));
    }

    @Test
    public void bestaandOngeldigVerlopenSoortBericht() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2442));
        stufBerichtInhoudControleService.controleerInhoud(maakVerzoek(BERICHT_ONGELDIG_BERICHT_VERLOPEN, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE));
    }

    private StufBerichtVerzoek maakVerzoek(final String berichtNaam, final String versieNr) {
        return maakVerzoek(berichtNaam, versieNr, berichtNaam, "inhoud");
    }


    @Test
    public void bestaandOngeldigGeldigSoortBerichtInVerzoek() throws StapMeldingException {
        expectedException.expect(new ExceptionRegelMatcher(Regel.R2444));
        stufBerichtInhoudControleService
                .controleerInhoud(maakVerzoek(BERICHT_GELDIG_BERICHT_START_LEEG_MET_EINDE, NR_GELDIG_VERSIE_NR_START_LEEG_MET_EINDE, "ongelijk", "inhouf"));
    }

    private StufBerichtVerzoek maakVerzoek(final String berichtNaam, final String versieNr, final String afwijkendeBerichtnaam, final String inhoud) {
        final StufBerichtVerzoek bericht = new StufBerichtVerzoek();
        bericht.getParameters().setVertalingBerichtsoortBRP(berichtNaam);
        bericht.getParameters().setVersieStufbg(versieNr);
        bericht.setStufBericht(new StufBericht(inhoud, afwijkendeBerichtnaam));
        return bericht;
    }
}
