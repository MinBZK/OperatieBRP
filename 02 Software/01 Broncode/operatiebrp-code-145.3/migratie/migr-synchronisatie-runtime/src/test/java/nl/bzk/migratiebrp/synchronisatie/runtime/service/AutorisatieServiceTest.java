/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.math.BigInteger;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordsType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PartijNietGevondenException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class AutorisatieServiceTest {

    @Mock
    private BrpAutorisatieService autorisatieService;
    @Mock
    private ConverteerLo3NaarBrpService conversieService;
    @Mock
    private PreconditiesService preconditieService;

    private AutorisatieService subject;

    @Before
    public void setup() {
        subject = new AutorisatieService(autorisatieService, conversieService, preconditieService);
    }

    @Test
    public void testProperties() {
        Assert.assertEquals(AutorisatieBericht.class, subject.getVerzoekType());
        Assert.assertEquals("AutorisatieService", subject.getServiceNaam());
    }

    @Test
    public void test() throws PartijNietGevondenException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(12345, 20120101, null);

        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Autorisatie>any())).thenAnswer(invocation -> { return invocation.getArguments()[0]; });
        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenReturn(maakBrpAutorisatie());

        final AutorisatieAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(1, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(145333L, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertNull(antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());

        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(autorisatieService, Mockito.times(1)).persisteerAutorisatie(Matchers.<BrpAutorisatie>any());
        Mockito.verifyNoMoreInteractions(autorisatieService, conversieService);

    }

    @Test
    public void testWithLoggingOk() throws PartijNietGevondenException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(1234567, 20130101, null);

        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Autorisatie>any())).thenAnswer(invocation -> { return invocation.getArguments()[0]; });
        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenAnswer(new Answer<BrpAutorisatie>() {
            @Override
            public BrpAutorisatie answer(final InvocationOnMock invocation) throws Throwable {
                Logging.log(
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, 0, 0),
                        LogSeverity.WARNING,
                        SoortMeldingCode.BIJZ_CONV_LB001,
                        Lo3ElementEnum.ELEMENT_3510);

                return maakBrpAutorisatie();
            }
        });

        final AutorisatieAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(1, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(145333L, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals("BIJZ_CONV_LB001", antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());

        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());

        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(autorisatieService, Mockito.times(1)).persisteerAutorisatie(Matchers.<BrpAutorisatie>any());
        Mockito.verifyNoMoreInteractions(autorisatieService, conversieService);
    }

    @Test
    public void testWithLoggingFout() throws PartijNietGevondenException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(1234567, 20130101, null);

        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Autorisatie>any())).thenAnswer(invocation -> { return invocation.getArguments()[0]; });
        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenAnswer(new Answer<BrpAutorisatie>() {
            @Override
            public BrpAutorisatie answer(final InvocationOnMock invocation) throws Throwable {
                Logging.log(
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, 0, 0),
                        LogSeverity.ERROR,
                        SoortMeldingCode.BIJZ_CONV_LB001,
                        Lo3ElementEnum.ELEMENT_3510);

                return maakBrpAutorisatie();
            }
        });

        final AutorisatieAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(1, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(145333L, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.FOUT, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals("BIJZ_CONV_LB001", antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());

        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(autorisatieService, Mockito.times(1)).persisteerAutorisatie(Matchers.<BrpAutorisatie>any());
        Mockito.verifyNoMoreInteractions(autorisatieService, conversieService);
    }

    @Test(expected = RuntimeException.class)
    public void testFout() {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(1, 20130101, null);

        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Autorisatie>any())).thenAnswer(invocation -> { return invocation.getArguments()[0]; });
        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenThrow(new RuntimeException("Foutje"));

        subject.verwerkBericht(verzoek);
    }

    private BrpAutorisatie maakBrpAutorisatie() {
        return new BrpAutorisatie(null, null, null);
    }

    /**
     * Maak een AutorisatieBericht aan voor gegeven afnemer.
     * @return AutorisatieBericht
     */
    private AutorisatieBericht maakAutorisatieBericht(final Integer afnemerCode, final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieType type = new AutorisatieType();
        type.setAfnemerCode(String.valueOf(afnemerCode));
        type.setAutorisatieTabelRegels(new AutorisatieRecordsType());
        type.getAutorisatieTabelRegels().getAutorisatieTabelRegel().add(maakBerichtInhoud(datumIngang, datumEinde));
        final AutorisatieBericht bericht = new AutorisatieBericht(type);
        bericht.setMessageId("MSG-ID");
        return bericht;
    }

    /**
     * Maak een AutorisatieRecordType aan met verplichte velden en een startdatum en evt einddatum.
     * @return AutorisatieRecordType
     */
    private AutorisatieRecordType maakBerichtInhoud(final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieRecordType record = new AutorisatieRecordType();
        record.setAutorisatieId(145333L);
        record.setGeheimhoudingInd(Short.valueOf("0"));
        record.setVerstrekkingsBeperking(Short.valueOf("0"));
        record.setTabelRegelStartDatum(new BigInteger(datumIngang.toString()));
        record.setTabelRegelEindDatum(datumEinde == null ? null : new BigInteger(datumEinde.toString()));
        return record;
    }

}
