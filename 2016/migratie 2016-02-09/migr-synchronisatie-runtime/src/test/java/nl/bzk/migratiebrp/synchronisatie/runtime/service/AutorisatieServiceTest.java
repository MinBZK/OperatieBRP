/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.math.BigInteger;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeAutorisatieException;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3CategorieMelding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Melding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Severity;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3SoortMelding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class AutorisatieServiceTest {

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConverteerLo3NaarBrpService conversieService;
    @Mock
    private PreconditiesService preconditieService;

    @InjectMocks
    private AutorisatieService subject;

    @Test
    public void testProperties() {
        Assert.assertEquals(AutorisatieBericht.class, subject.getVerzoekType());
        Assert.assertEquals("AutorisatieService", subject.getServiceNaam());
    }

    @Test
    public void test() throws Lo3SyntaxException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(12345, 20120101, null);

        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenReturn(maakBrpAutorisatie());

        final AutorisatieAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertNull(antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        try {
            Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());
        } catch (final OngeldigeAutorisatieException e) {
            Assert.fail("Er mogen geen precondities falen");
        }

        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerAutorisatie(Matchers.<BrpAutorisatie>any());
        final ArgumentCaptor<Lo3Bericht> berichtLogCaptor = ArgumentCaptor.forClass(Lo3Bericht.class);
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerLo3Bericht(berichtLogCaptor.capture());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);

        final Lo3Bericht bericht = berichtLogCaptor.getValue();
        Assert.assertNull(bericht.getAnummer());
        Assert.assertNotNull(bericht.getBerichtdata());
        Assert.assertNotNull(bericht.getChecksum());
        Assert.assertEquals(Lo3BerichtenBron.INITIELE_VULLING, bericht.getBerichtenBron());
        Assert.assertNull(bericht.getFoutcode());
        Assert.assertNull(bericht.getVerwerkingsmelding());
        Assert.assertNull(bericht.getPersoon());
        Assert.assertEquals("MSG-ID", bericht.getReferentie());
        Assert.assertNotNull(bericht.getTijdstipConversie());
        Assert.assertEquals(0, bericht.getVoorkomens().size());
    }

    @Test
    public void testWithLogging() throws Lo3SyntaxException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(1234567, 20130101, null);

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
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertNull(antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        try {
            Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());
        } catch (final OngeldigeAutorisatieException e) {
            Assert.fail("Er mogen geen precondities falen");
        }

        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerAutorisatie(Matchers.<BrpAutorisatie>any());
        final ArgumentCaptor<Lo3Bericht> berichtLogCaptor = ArgumentCaptor.forClass(Lo3Bericht.class);
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerLo3Bericht(berichtLogCaptor.capture());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);

        final Lo3Bericht bericht = berichtLogCaptor.getValue();
        Assert.assertNull(bericht.getAnummer());
        Assert.assertNotNull(bericht.getBerichtdata());
        Assert.assertNotNull(bericht.getChecksum());
        Assert.assertEquals(Lo3BerichtenBron.INITIELE_VULLING, bericht.getBerichtenBron());
        Assert.assertNull(bericht.getFoutcode());
        Assert.assertNull(bericht.getVerwerkingsmelding());
        Assert.assertNull(bericht.getPersoon());
        Assert.assertEquals("MSG-ID", bericht.getReferentie());
        Assert.assertNotNull(bericht.getTijdstipConversie());
        Assert.assertEquals(1, bericht.getVoorkomens().size());

        final Lo3Voorkomen voorkomen = bericht.getVoorkomens().iterator().next();
        final Lo3Melding melding = voorkomen.getMeldingen().iterator().next();
        Assert.assertEquals(Lo3Severity.WARNING, melding.getLogSeverity());
        Assert.assertEquals(Lo3CategorieMelding.BIJZONDERE_SITUATIE, melding.getSoortMelding().getCategorieMelding());
        Assert.assertEquals(Lo3SoortMelding.BIJZ_CONV_LB001, melding.getSoortMelding());
        Assert.assertEquals("Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied.", melding.getSoortMelding()
                                                                                                                          .getOmschrijving());
    }

    @Test
    public void testFout() throws Lo3SyntaxException {
        final AutorisatieBericht verzoek = maakAutorisatieBericht(1, 20130101, null);

        Mockito.when(conversieService.converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any())).thenThrow(new RuntimeException("Foutje"));

        final AutorisatieAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertEquals("Foutje", antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        try {
            Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Autorisatie>any());
        } catch (final OngeldigeAutorisatieException e) {
            Assert.fail("Er mogen geen precondities falen");
        }

        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Autorisatie(Matchers.<Lo3Autorisatie>any());
        final ArgumentCaptor<Lo3Bericht> berichtLogCaptor = ArgumentCaptor.forClass(Lo3Bericht.class);
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerLo3Bericht(berichtLogCaptor.capture());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);

        final Lo3Bericht bericht = berichtLogCaptor.getValue();
        Assert.assertNull(bericht.getAnummer());
        Assert.assertNotNull(bericht.getBerichtdata());
        Assert.assertNotNull(bericht.getChecksum());
        Assert.assertEquals(Lo3BerichtenBron.INITIELE_VULLING, bericht.getBerichtenBron());
        Assert.assertEquals("java.lang.RuntimeException", bericht.getFoutcode());
        Assert.assertEquals("Foutje", bericht.getVerwerkingsmelding());
        Assert.assertNull(bericht.getPersoon());
        Assert.assertEquals("MSG-ID", bericht.getReferentie());
        Assert.assertNotNull(bericht.getTijdstipConversie());
        Assert.assertEquals(0, bericht.getVoorkomens().size());
    }

    private BrpAutorisatie maakBrpAutorisatie() {
        return new BrpAutorisatie(null, null);
    }

    /**
     * Maak een AutorisatieBericht aan voor gegeven afnemer.
     *
     * @param afnemerCode
     * @return AutorisatieBericht
     */
    private AutorisatieBericht maakAutorisatieBericht(final Integer afnemerCode, final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieType type = new AutorisatieType();
        type.setAfnemerCode(String.valueOf(afnemerCode));
        type.getAutorisatieTabelRegels().add(maakBerichtInhoud(datumIngang, datumEinde));
        final AutorisatieBericht bericht = new AutorisatieBericht(type);
        bericht.setMessageId("MSG-ID");
        return bericht;
    }

    /**
     * Maak een AutorisatieRecordType aan met verplichte velden en een startdatum en evt einddatum.
     *
     * @return AutorisatieRecordType
     */
    private AutorisatieRecordType maakBerichtInhoud(final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieRecordType record = new AutorisatieRecordType();
        record.setGeheimhoudingInd(Short.valueOf("0"));
        record.setVerstrekkingsBeperking(Short.valueOf("0"));
        record.setTabelRegelStartDatum(new BigInteger(datumIngang.toString()));
        record.setTabelRegelEindDatum(datumEinde == null ? null : new BigInteger(datumEinde.toString()));
        return record;
    }

}
