/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.sql.Timestamp;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test blokkeringsinformatie.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlControleGevondenBlokkeringssituatieIsJuistTest {

    @Mock
    private BrpDalService brpDalService;

    private PlControleGevondenBlokkeringssituatieIsJuist subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new PlControleGevondenBlokkeringssituatieIsJuist(brpDalService);
    }

    @Test
    public void testControleerGeenBlokkeringsInfo() {
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(null);
        final BrpPersoonslijst dbPersoonslijst = maakPersoonsLijst(null);
        Assert.assertTrue("Er mag geen blokkeringsinformatie zijn", subject.controleer(new VerwerkingsContext(null, null, null, null), dbPersoonslijst));
    }

    @Test
    public void testControleerCorrecteBlokkeringssituatieGBAGemeente() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));
        blokkering.setGemeenteCodeNaar("100001");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("100001"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("200001"));
        final Partij aangebodenPartij = new Partij("aangeboden", "100001");
        final Partij dbPartij = new Partij("database", "200001");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("100001"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("200001"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("100001");
        Assert.assertTrue(
                "Blokkeringssituatie zou juist moeten zijn want GBA gemeente",
                subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testControleerVerkeerdeBlokkeringssituatieGBAGemeente() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));
        blokkering.setGemeenteCodeNaar("1010");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        Assert.assertFalse(
                "Blokkeringssituatie zou onjuist moeten zijn want GBA gemeente is niet de gemeente van blokkering",
                subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testControleerCorrecteBlokkeringssituatieGBAGemeenteOvergangdatumInToekomst() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));
        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        aangebodenPartij.setDatumOvergangNaarBrp(30000101);
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        Assert.assertTrue(
                "Blokkeringssituatie zou juist moeten zijn want GBA gemeente",
                subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testControleerCorrecteBlokkeringssituatieRNI() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("199901"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "199901");
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("199901"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        Assert.assertTrue(
                "Blokkeringssituatie zou juist moeten zijn want RN gemeente",
                subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testControleerOnjuisteBlokkeringssituatieGeenGbaOfRNI() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        aangebodenPartij.setDatumOvergangNaarBrp(19000101);
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        final boolean result = subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst);
        Assert.assertFalse("Blokkeringssituatie zou onjuist moeten zijn, want brpgemeente", result);
    }

    @Test
    public void testControleerOnjuisteBlokkeringssituatueGeenBRPGemeente() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        final Partij dbPartij = new Partij("database", "002000");
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        Assert.assertFalse(
                "Blokkeringssituatie zou onjuist moeten zijn, want geen brpgemeente",
                subject.controleer(new VerwerkingsContext(null, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testControleerOnjuisteBlokkeringssituatueGeenBRPGemeenteDatumOvergangInToekomst() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(30000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        Assert.assertFalse(
                "Blokkeringssituatie zou onjuist moeten zijn, want geen brpgemeente",
                subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst));
    }

    @Test
    public void testPersonenZonderBijhoudingsstapelBrpGemeente() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(new BrpPartijCode("001000"));
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(null);
        final Partij aangebodenPartij = new Partij("aangeboden", "001000");
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(aangebodenPartij);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(null);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        final boolean result = subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst);
        Assert.assertFalse("Blokkeringssituatie zou onjuist moeten zijn, want geen bijhoudingsstapel brpgemeente", result);
    }

    @Test
    public void testPersonenZonderBijhoudingsstapelGbaGemeente() {
        final Blokkering blokkering = new Blokkering("1234567890", new Timestamp(System.currentTimeMillis()));

        blokkering.setGemeenteCodeNaar("1000");
        Mockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final BrpPersoonslijst aangebodenLijst = maakPersoonsLijst(null);
        final BrpPersoonslijst dbLijst = maakPersoonsLijst(new BrpPartijCode("002000"));
        final Partij dbPartij = new Partij("database", "002000");
        dbPartij.setDatumOvergangNaarBrp(19000101);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("001000"))).thenReturn(null);
        Mockito.when(brpDalService.geefPartij(new BrpPartijCode("002000"))).thenReturn(dbPartij);
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setVerzendendeGemeente("1000");
        final boolean result = subject.controleer(new VerwerkingsContext(verzoek, null, null, aangebodenLijst), dbLijst);
        Assert.assertFalse("Blokkeringssituatie zou onjuist moeten zijn, want geen bijhoudingsstapel gbagemeente", result);
    }

    private BrpPersoonslijst maakPersoonsLijst(final BrpPartijCode code) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpString("1234567890"), new BrpString("123456789"));
        final BrpGroep<BrpIdentificatienummersInhoud> groep =
                new BrpGroep<>(inhoud, new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null), null, null, null);
        final BrpStapel<BrpIdentificatienummersInhoud> stapel = new BrpStapel<>(Collections.singletonList(groep));
        builder.identificatienummersStapel(stapel);
        if (code != null) {
            final BrpBijhoudingInhoud bijhoudingsInhoud =
                    new BrpBijhoudingInhoud(code, BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL);
            final BrpGroep<BrpBijhoudingInhoud> bijhoudingsGroep =
                    new BrpGroep<>(bijhoudingsInhoud, new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null), null, null, null);
            final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = new BrpStapel<>(Collections.singletonList(bijhoudingsGroep));
            builder.bijhoudingStapel(bijhoudingStapel);
        }
        return builder.build();
    }

}
