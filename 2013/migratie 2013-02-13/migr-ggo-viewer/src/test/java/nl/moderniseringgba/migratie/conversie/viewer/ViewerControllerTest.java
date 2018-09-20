/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterException;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;
import nl.moderniseringgba.migratie.conversie.viewer.service.BcmService;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;
import nl.moderniseringgba.migratie.conversie.viewer.service.LeesService;
import nl.moderniseringgba.migratie.conversie.viewer.service.PermissionService;
import nl.moderniseringgba.migratie.conversie.viewer.service.ViewerService;
import nl.moderniseringgba.migratie.conversie.viewer.service.impl.LeesServiceImpl;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.StapelVergelijking;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewerControllerTest {

    @Mock
    private ViewerService viewerService;
    @Mock
    private DbService dbService;
    @Mock
    private LeesService leesService;
    @Mock
    private BcmService bcmService;
    @Mock
    private PermissionService permissionService;
    @Mock
    private DemoMode demoMode;
    @InjectMocks
    private ViewerController viewerController;

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testGetIndex() {
        assertEquals("viewer", viewerController.getIndex().getViewName());
    }

    @Test
    public void testZoekOpAnummer() throws IOException {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst(null);
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijsten().get(0);
        final BerichtLog berichtLog =
                new BerichtLog("ggo_viewer_test", "ggo_viewer_test", new Timestamp(new Date().getTime()));
        final List<LogRegel> logRegels = new ArrayList<LogRegel>();

        final Long aNr = new Long(42);
        when(dbService.zoekBrpPersoonsLijst(aNr)).thenReturn(brpPersoonslijst);
        when(dbService.zoekBerichtLog(aNr)).thenReturn(berichtLog);
        when(dbService.haalLo3PersoonslijstUitBerichtLog(berichtLog)).thenReturn(lo3Persoonslijst);
        when(dbService.haalLogRegelsUitBerichtLog(berichtLog)).thenReturn(logRegels);
        when(bcmService.controleerDoorBCM(eq(lo3Persoonslijst), any(FoutMelder.class))).thenReturn(
                new ArrayList<FoutRegel>());
        when(viewerService.converteerTerug(eq(brpPersoonslijst), any(FoutMelder.class))).thenReturn(lo3Persoonslijst);
        when(permissionService.hasPermissionOnPl(brpPersoonslijst)).thenReturn(Boolean.TRUE);

        final String result = viewerController.zoekOpAnummer(aNr).getBody();

        // gebruiker heeft permissie dus zal hier langs moeten komen
        verify(dbService, VerificationModeFactory.times(1)).zoekBerichtLog(aNr);
        assertStructuurResultaat(result);
    }

    @Test
    public void testNoPermissionZoekOpAnummer() throws IOException {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst(null);
        final Long aNr = new Long(42);
        when(dbService.zoekBrpPersoonsLijst(aNr)).thenReturn(brpPersoonslijst);
        when(permissionService.hasPermissionOnPl(brpPersoonslijst)).thenReturn(Boolean.FALSE);

        final String result = viewerController.zoekOpAnummer(aNr).getBody();

        // mag niet worden aangeroepen omdat gebruiker geen permissie heeft
        verify(dbService, VerificationModeFactory.times(0)).zoekBerichtLog(aNr);

        assertTrue(result.contains("Persoonslijst niet gevonden of geen permissie"));
    }

    @Test
    public void testNotFoundZoekOpAnummer() throws IOException {
        final Long aNr = new Long(42);
        when(dbService.zoekBrpPersoonsLijst(aNr)).thenReturn(null);
        final String result = viewerController.zoekOpAnummer(aNr).getBody();

        // mag niet worden aangeroepen omdat gebruiker geen permissie heeft
        verify(dbService, VerificationModeFactory.times(0)).zoekBerichtLog(aNr);

        assertTrue(result.contains("Persoonslijst niet gevonden of geen permissie"));
    }

    @Test
    public void testVerwerkFileUploadSucces() throws IOException, ExcelAdapterException {
        final List<Lo3Persoonslijst> lo3Persoonslijsten = getValidLo3Persoonslijsten();
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst(null);
        final List<LogRegel> logRegels = new ArrayList<LogRegel>();
        final List<StapelVergelijking> matches = new ArrayList<StapelVergelijking>();

        when(leesService.leesLo3Persoonslijst(anyString(), any(byte[].class), any(FoutMelder.class))).thenReturn(
                lo3Persoonslijsten);
        when(viewerService.verwerkPrecondities(any(Lo3Persoonslijst.class), any(FoutMelder.class))).thenReturn(
                logRegels);
        when(viewerService.converteerNaarBrp(any(Lo3Persoonslijst.class), any(FoutMelder.class))).thenReturn(
                brpPersoonslijst);
        when(viewerService.converteerTerug(any(BrpPersoonslijst.class), any(FoutMelder.class))).thenReturn(
                lo3Persoonslijsten.get(0));
        when(bcmService.controleerDoorBCM(any(Lo3Persoonslijst.class), any(FoutMelder.class))).thenReturn(
                new ArrayList<FoutRegel>());
        when(
                viewerService.vergelijkLo3OrigineelMetTerugconversie(any(Lo3Persoonslijst.class),
                        any(Lo3Persoonslijst.class), any(FoutMelder.class))).thenReturn(matches);

        String result = viewerController.uploadRequest("asdf", new byte[] {}).getBody();
        assertStructuurResultaat(result);

        // just IE
        final MultipartFile multipartFile = new MockMultipartFile("asdf", new byte[] {});
        result = viewerController.uploadRequestMSIE(multipartFile).getBody();
        assertStructuurResultaat(result);

        // in demoMode
        result = viewerController.uploadRequestFileName("asdf").getBody();
        assertStructuurResultaat(result);
    }

    @Test
    public void testVerwerkFileUploadGeenPl() {
        try {
            final String filename = "Omzetting01.xls";
            final byte[] file = IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(filename));
            when(leesService.leesLo3Persoonslijst(eq(filename), eq(file), any(FoutMelder.class))).thenReturn(null);

            viewerController.uploadRequest(filename, file);
            fail("Er zou een nullpointer terug moeten komen.");
        } catch (final IOException e) {
            fail("Er zou geen IOException gegooid moeten worden.");
        } catch (final NullPointerException npe) {
            assertNotNull("Nullpointer verwacht.", npe);
        }
    }

    @Test
    public void testVerwerkFileUploadConverteerFail() {
        try {
            final String filename = "Omzetting01.xls";
            final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

            // Lees excel
            final List<ExcelData> excelDatas =
                    excelAdapter.leesExcelBestand(this.getClass().getClassLoader().getResourceAsStream(filename));

            // Parsen input *ZONDER* syntax en precondite controles
            final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<Lo3Persoonslijst>();
            for (final ExcelData excelData : excelDatas) {
                lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
            }

            final byte[] file = IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(filename));
            when(leesService.leesLo3Persoonslijst(eq(filename), eq(file), any(FoutMelder.class))).thenReturn(
                    lo3Persoonslijsten);
            when(viewerService.converteerNaarBrp(any(Lo3Persoonslijst.class), foutMelder)).thenThrow(
                    new RuntimeException());

            viewerController.uploadRequest(filename, file);
            fail("Er kwam een nullpointer terug.");
        } catch (final IOException e) {
            fail("Er zou geen IOException gegooid moeten worden.");
        } catch (final ExcelAdapterException e) {
            fail("Er zou geen ExcelAdapterException gegooid moeten worden.");
        } catch (final RuntimeException re) {
            assertNotNull("RuntimeException verwacht.", re);
        }
    }

    private List<Lo3Persoonslijst> getValidLo3Persoonslijsten() throws IOException {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        return new LeesServiceImpl().leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
    }

    private BrpPersoonslijst createBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        BrpGemeenteCode brpGemeenteCode = gemeenteCode;
        if (brpGemeenteCode == null) {
            brpGemeenteCode = new BrpGemeenteCode(new BigDecimal(599));
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingsgemeenteInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsgemeenteInhoud>>();
        groepen.add(new BrpGroep<BrpBijhoudingsgemeenteInhoud>(new BrpBijhoudingsgemeenteInhoud(brpGemeenteCode,
                BrpDatum.ONBEKEND, false), new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101),
                new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date())), null, null, null));
        builder.bijhoudingsgemeenteStapel(new BrpStapel<BrpBijhoudingsgemeenteInhoud>(groepen));
        // final BrpRelatie relatie = new BrpRelatie(new BrpSoortRelatieCode(), new BrpSoortBetrokkenheidCode, new
        // List<BrpBetrokkenheid>(), new BrpStapel<BrpRelatieInhoud>());
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<BrpBetrokkenheid>();
        final BrpIdentificatienummersInhoud inhoud =
                new BrpIdentificatienummersInhoud(Long.valueOf(43), Long.valueOf(43));
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()),
                        new BrpDatumTijd(new Date()));
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(inhoud, historie, null, null, null);

        idBrpGroep.add(brpIdGroep);
        betrokkenheid.add(new BrpBetrokkenheid(null, new BrpStapel<BrpIdentificatienummersInhoud>(idBrpGroep), null,
                null, null, null, null));
        final BrpRelatie relatie =
                new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER,
                        betrokkenheid, null);

        builder.relatie(relatie);

        return builder.build();
    }

    private void assertStructuurResultaat(final String result) {
        assertTrue(result.contains("\"lo3Persoonslijst\" : {"));
        assertTrue(result.contains("\"brpPersoonslijst\" : {"));
        assertTrue(result.contains("\"bcmSignaleringen\" : ["));
        assertTrue(result.contains("\"precondities\" : ["));
        assertTrue(result.contains("\"teruggeconverteerd\" : {"));
        assertTrue(result.contains("\"vergelijking\" : ["));
    }
}
