/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class AfnemersindicatieServiceTest {

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConverteerLo3NaarBrpService conversieService;
    @Mock
    private PreconditiesService preconditieService;

    @InjectMocks
    private AfnemersindicatieService subject;

    @Test
    public void testProperties() {
        Assert.assertEquals(AfnemersindicatiesBericht.class, subject.getVerzoekType());
        Assert.assertEquals("AfnemersindicatieService", subject.getServiceNaam());
    }

    @Test
    public void test() {
        final AfnemersindicatiesBericht verzoek = maakVerzoek();

        Mockito.when(conversieService.converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any())).thenReturn(maakBrpAfnemersindicaties());

        final AfnemersindicatiesAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertNull(antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerAfnemersindicaties(Matchers.<BrpAfnemersindicaties>any());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);
    }

    @Test
    public void testWithLogging() {
        final AfnemersindicatiesBericht verzoek = maakVerzoek();

        Mockito.when(conversieService.converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any()))
               .thenAnswer(new Answer<BrpAfnemersindicaties>()
        {
                   @Override
                   public BrpAfnemersindicaties answer(final InvocationOnMock invocation) throws Throwable {
                       Logging.log(
                           new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, 0, 0),
                           LogSeverity.WARNING,
                           SoortMeldingCode.BIJZ_CONV_LB001,
                           Lo3ElementEnum.ELEMENT_1410);

                       return maakBrpAfnemersindicaties();
                   }
               });

        final AfnemersindicatiesAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertNull(antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        final LogRegel melding = antwoord.getLogging().iterator().next();
        Assert.assertEquals(LogSeverity.WARNING, melding.getSeverity());
        Assert.assertEquals(SoortMeldingCode.BIJZ_CONV_LB001, melding.getSoortMeldingCode());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verify(brpDalService, Mockito.times(1)).persisteerAfnemersindicaties(Matchers.<BrpAfnemersindicaties>any());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);
    }

    @Test
    public void testFout() {
        final AfnemersindicatiesBericht verzoek = maakVerzoek();

        Mockito.when(conversieService.converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any())).thenThrow(new RuntimeException("Foutje"));

        final AfnemersindicatiesAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertEquals("Foutje", antwoord.getFoutmelding());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());

        Mockito.verify(preconditieService, Mockito.times(1)).verwerk(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verify(conversieService, Mockito.times(1)).converteerLo3Afnemersindicaties(Matchers.<Lo3Afnemersindicatie>any());
        Mockito.verifyNoMoreInteractions(brpDalService, conversieService);
    }

    private BrpAfnemersindicaties maakBrpAfnemersindicaties() {
        return new BrpAfnemersindicaties(1231231234L, null);
    }

    private AfnemersindicatiesBericht maakVerzoek() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels = maakStapels();
        final Lo3Afnemersindicatie lo3Afnemersindicatie = new Lo3Afnemersindicatie(1231231234L, afnemersindicatieStapels);
        final AfnemersindicatiesBericht verzoek = new AfnemersindicatiesBericht();
        verzoek.setAfnemersindicaties(lo3Afnemersindicatie);
        verzoek.setMessageId("MSG-ID");

        return verzoek;
    }

    private List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> maakStapels() {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(1234567);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, 0, 0);

        final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel =
                new Lo3Stapel<>(Collections.singletonList(new Lo3Categorie<>(inhoud, null, historie, herkomst)));

        return Collections.singletonList(stapel);
    }

}
