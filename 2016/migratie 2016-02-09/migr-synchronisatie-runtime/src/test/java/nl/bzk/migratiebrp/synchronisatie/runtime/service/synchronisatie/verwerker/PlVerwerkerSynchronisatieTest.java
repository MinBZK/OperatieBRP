/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlVerwerkerSynchronisatieTest {
    @Mock
    @Named("controleReguliereWijziging")
    private Controle controleReguliereWijziging;
    @Mock
    @Named("controleReguliereVerhuizing")
    private Controle controleReguliereVerhuizing;
    @Mock
    @Named("controleEmigratie")
    private Controle controleEmigratie;
    @Mock
    @Named("controleAnummerWijziging")
    private Controle controleAnummerWijziging;
    @Mock
    @Named("controleNieuwePersoonslijst")
    private Controle controleNieuwePersoonslijst;
    @Mock
    @Named("controlePersoonslijstIsOuder")
    private Controle controlePersoonslijstIsOuder;
    @Mock
    @Named("controleBevatBlokkeringsinformatie")
    private Controle controleBevatBlokkeringsinformatie;
    @Mock
    @Named("controlePersoonslijstIsGelijk")
    private Controle controlePersoonslijstIsGelijk;

    @Mock
    private PlService plService;

    @InjectMocks
    private PlVerwerkerSynchronisatie subject;

    @Mock
    private Lo3Bericht loggingBericht;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        Logging.initContext();
    }

    @After
    public void destroyLoggin() {
        Logging.destroyContext();
    }

    @Test
    public void testReguliereWijziging() throws SynchronisatieVerwerkerException {
        setup(true, false, false, false, false, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(verwerkingsContext);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Mockito.verify(plService).persisteerPersoonslijst(brpPersoonslijst, 12345L, false, loggingBericht);
    }

    @Test
    public void testReguliereVerhuizing() throws SynchronisatieVerwerkerException {
        setup(false, true, false, false, false, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(verwerkingsContext);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Mockito.verify(plService).persisteerPersoonslijst(brpPersoonslijst, 12345L, false, loggingBericht);
    }

    @Test
    public void testAnummerWijziging() throws SynchronisatieVerwerkerException {
        setup(false, false, true, false, false, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(verwerkingsContext);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Mockito.verify(plService).persisteerPersoonslijst(brpPersoonslijst, 55667L, true, loggingBericht);
    }

    @Test
    public void testToevoegen() throws SynchronisatieVerwerkerException {
        setup(false, false, false, true, false, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(verwerkingsContext);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getStatus());

        Mockito.verify(plService).persisteerPersoonslijst(brpPersoonslijst, loggingBericht);
    }

    @Test
    public void testPersoonslijstIsOuder() {
        setup(false, false, false, false, true, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        try {
            subject.verwerk(verwerkingsContext);
            Assert.fail("SynchronisatieVerwerkerException verwacht");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.GENEGEERD, e.getStatus());
        }
    }

    @Test
    public void testBevatBlokkeringsinformatie() {
        setup(false, false, false, false, false, true, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        try {
            subject.verwerk(verwerkingsContext);
            Assert.fail("SynchronisatieVerwerkerException verwacht");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.GENEGEERD, e.getStatus());
        }
    }

    @Test
    public void testPersoonslijstIsGelijk() {
        setup(false, false, false, false, false, false, true);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        try {
            subject.verwerk(verwerkingsContext);
            Assert.fail("SynchronisatieVerwerkerException verwacht");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.GENEGEERD, e.getStatus());
        }
    }

    @Test
    public void testOnduidelijk() {
        setup(false, false, false, false, false, false, false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        final BrpPersoonslijst brpPersoonslijst = maakPl();
        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);

        try {
            subject.verwerk(verwerkingsContext);
            Assert.fail("SynchronisatieVerwerkerException verwacht");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.ONDUIDELIJK, e.getStatus());
        }
    }

    public void setup(
        final boolean controleReguliereWijzigingResult,
        final boolean controleReguliereVerhuizingResult,
        final boolean controleAnummerWijzigingResult,
        final boolean controleNieuwePersoonslijstResult,
        final boolean controlePersoonslijstIsOuderResult,
        final boolean controleBevatBlokkeringsinformatieResult,
        final boolean controlePersoonslijstIsGelijkResult)
    {
        Mockito.when(controleReguliereWijziging.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controleReguliereWijzigingResult);
        Mockito.when(controleReguliereVerhuizing.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controleReguliereVerhuizingResult);
        Mockito.when(controleAnummerWijziging.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controleAnummerWijzigingResult);
        Mockito.when(controleNieuwePersoonslijst.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controleNieuwePersoonslijstResult);
        Mockito.when(controlePersoonslijstIsOuder.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controlePersoonslijstIsOuderResult);
        Mockito.when(controleBevatBlokkeringsinformatie.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(
            controleBevatBlokkeringsinformatieResult);
        Mockito.when(controlePersoonslijstIsGelijk.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(controlePersoonslijstIsGelijkResult);
    }

    private BrpPersoonslijst maakPl() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.identificatienummersStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            new BrpIdentificatienummersInhoud(new BrpLong(12345L), null),
            BrpStapelHelper.his(20000131),
            BrpStapelHelper.act(1, 20000131))));

        builder.nummerverwijzingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.nummerverwijzing(55667L, null, null, null),
            BrpStapelHelper.his(20000131),
            BrpStapelHelper.act(1, 20000131))));

        return builder.build();
    }

}
