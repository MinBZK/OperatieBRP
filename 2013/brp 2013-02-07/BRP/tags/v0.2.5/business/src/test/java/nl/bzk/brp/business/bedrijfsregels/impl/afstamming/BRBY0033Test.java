/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.AbstractBedrijfsregelsTest;
import nl.bzk.brp.business.definities.impl.afstamming.KandidaatVader;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BRBY0033Test extends AbstractBedrijfsregelsTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private KandidaatVader    kandidaatVader;

    @InjectMocks
    private BRBY0033          brby0033;

    @Test
    public void testRelatieNull() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        List<Melding> meldingen = brby0033.executeer(null, null, null);

        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(1, getLogAppender().size());
        Assert.assertEquals("De nieuwe situatie zou niet null mogen zijn, kan BRBY0033 niet valideren.",
                getLogAppender().get(0));
    }

    @Test
    public void testMoederHeeftGeenBsn() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie = maakFamilieRelatie(null, maakOuderBetrokkenheid(null, Ja.Ja), null);

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(1, getLogAppender().size());
        Assert.assertEquals(
                "Persoon moeder niet aanwezig of heeft geen BSN. Bedrijfsregel BRBY0033 wordt dus niet uitgevoerd.",
                getLogAppender().get(0));
    }

    @Test
    public void testMoederNietGevondenMetBsn() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie = maakFamilieRelatie(null, maakOuderBetrokkenheid("123", Ja.Ja), null);

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                null);

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));

        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(1, getLogAppender().size());
        Assert.assertEquals(
                "Persoon moeder kan niet gevonden worden met BSN 123. Bedrijfsregel BRBY0033 wordt dus niet uitgevoerd.",
                getLogAppender().get(0));
    }

    @Test
    public void testVaderGevondenMaarNietOpgegeven() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid("123", Ja.Ja), maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));
        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
                .thenReturn(Arrays.asList(new PersoonModel(new PersoonBericht())));

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));
        Mockito.verify(kandidaatVader, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonModel.class),
                Matchers.any(Datum.class));

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0033, meldingen.get(0).getCode());
        Assert.assertEquals(MeldingCode.BRBY0033_1.getOmschrijving(), meldingen.get(0).getOmschrijving());
        Assert.assertTrue(getLogAppender().isEmpty());
    }

    @Test
    public void testOpgegevenVaderGeenBsn() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid(null, null), maakOuderBetrokkenheid("123", Ja.Ja), maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));
        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
                .thenReturn(Arrays.asList(new PersoonModel(new PersoonBericht())));

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));
        Mockito.verify(kandidaatVader, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonModel.class),
                Matchers.any(Datum.class));

        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(1, getLogAppender().size());
        Assert.assertEquals(
                "Persoon vader niet aanwezig of heeft geen BSN. Bedrijfsregel BRBY0033 wordt dus niet uitgevoerd.",
                getLogAppender().get(0));
    }

    @Test
    public void testOpgegevenVaderGeenKandidaat() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid("456", null), maakOuderBetrokkenheid("123", Ja.Ja),
                    maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));

        PersoonBericht gevondenVader = maakOuderBetrokkenheid("999", null).getBetrokkene();

        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
                .thenReturn(Arrays.asList(new PersoonModel(gevondenVader)));

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));
        Mockito.verify(kandidaatVader, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonModel.class),
                Matchers.any(Datum.class));

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0033, meldingen.get(0).getCode());
        Assert.assertEquals(MeldingCode.BRBY0033.getOmschrijving(), meldingen.get(0).getOmschrijving());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertTrue(getLogAppender().isEmpty());
    }

    @Test
    public void testOpgegevenVaderIsKandidaat() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        RelatieBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid("456", null), maakOuderBetrokkenheid("123", Ja.Ja),
                    maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));

        PersoonBericht gevondenVader1 = maakOuderBetrokkenheid("789", null).getBetrokkene();
        PersoonBericht gevondenVader2 = maakOuderBetrokkenheid("456", null).getBetrokkene();

        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
                .thenReturn(Arrays.asList(new PersoonModel(gevondenVader1), new PersoonModel(gevondenVader2)));

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));
        Mockito.verify(kandidaatVader, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonModel.class),
                Matchers.any(Datum.class));

        Assert.assertEquals(0, meldingen.size());
        Assert.assertTrue(getLogAppender().isEmpty());
    }

    private BetrokkenheidBericht maakOuderBetrokkenheid(final String bsn, final Ja adresgevend) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        if (bsn != null) {
            persoonBericht.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        }

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        betrokkenheid.getBetrokkenheidOuderschap().setIndAdresGevend(adresgevend);
        betrokkenheid.setBetrokkene(persoonBericht);
        betrokkenheid.setRol(SoortBetrokkenheid.OUDER);

        return betrokkenheid;
    }

    private BetrokkenheidBericht maakKindBetrokkenheid() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setBetrokkene(persoonBericht);
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        return betrokkenheid;
    }

    private RelatieBericht maakFamilieRelatie(final BetrokkenheidBericht vader, final BetrokkenheidBericht moeder,
            final BetrokkenheidBericht kind)
    {
        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        if (vader != null) {
            betrokkenheden.add(vader);
        }
        if (moeder != null) {
            betrokkenheden.add(moeder);
        }
        if (kind != null) {
            betrokkenheden.add(kind);
        }

        RelatieBericht relatieBericht = new RelatieBericht();
        // relatieBericht.setGegevens(new RelatieStandaardGroepBericht());
        relatieBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieBericht.setBetrokkenheden(betrokkenheden);

        return relatieBericht;
    }

    @Override
    protected Class getTestClass() {
        return BRBY0033.class;
    }
}
