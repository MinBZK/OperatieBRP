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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;


public class BRBY0033Test extends AbstractBedrijfsregelsTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private KandidaatVader kandidaatVader;

    @InjectMocks
    private BRBY0033 brby0033;

    @Test
    public void testRelatieNull() {
        List<Melding> meldingen = brby0033.executeer(null, null, null);

        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(getLogger())
               .error("De nieuwe situatie zou niet null mogen zijn, kan {} niet valideren.", "BRBY0033");
    }

    @Test
    public void testMoederHeeftGeenBsn() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid(null, Ja.J), null);

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(getLogger()).warn(
            "Persoon moeder niet aanwezig of heeft geen BSN. Bedrijfsregel {} wordt dus niet uitgevoerd.", "BRBY0033");
    }

    @Test
    public void testMoederNietGevondenMetBsn() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid("123", Ja.J), null);

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
            null);

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
            Matchers.any(Burgerservicenummer.class));

        Assert.assertEquals(0, meldingen.size());
        Mockito.verify(getLogger()).warn(
            "Persoon moeder kan niet gevonden worden met BSN {}. Bedrijfsregel {} wordt dus niet uitgevoerd.",
            new Object[]{ "000000123", "BRBY0033" });
    }

    @Test
    public void testVaderGevondenMaarNietOpgegeven() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid("123", Ja.J), maakKindBetrokkenheid());

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
    }

    @Test
    public void testOpgegevenVaderGeenBsn() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid(null, null), maakOuderBetrokkenheid("123", Ja.J),
                maakKindBetrokkenheid());

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
        Mockito.verify(getLogger()).warn(
            "Persoon vader niet aanwezig of heeft geen BSN. Bedrijfsregel {} wordt dus niet uitgevoerd.", "BRBY0033");
    }

    @Test
    public void testOpgegevenVaderGeenKandidaat() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid("456", null), maakOuderBetrokkenheid("123", Ja.J),
                maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
            new PersoonModel(new PersoonBericht()));

        PersoonBericht gevondenVader = maakOuderBetrokkenheid("999", null).getPersoon();

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
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
    }

    @Test
    public void testOpgegevenVaderIsKandidaat() {
        FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid("456", null), maakOuderBetrokkenheid("123", Ja.J),
                maakKindBetrokkenheid());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
            new PersoonModel(new PersoonBericht()));

        PersoonBericht gevondenVader1 = maakOuderBetrokkenheid("789", null).getPersoon();
        PersoonBericht gevondenVader2 = maakOuderBetrokkenheid("456", null).getPersoon();

        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
               .thenReturn(Arrays.asList(new PersoonModel(gevondenVader1), new PersoonModel(gevondenVader2)));

        List<Melding> meldingen = brby0033.executeer(null, relatie, null);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
            Matchers.any(Burgerservicenummer.class));
        Mockito.verify(kandidaatVader, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonModel.class),
            Matchers.any(Datum.class));

        Assert.assertEquals(0, meldingen.size());
    }

    private BetrokkenheidBericht maakOuderBetrokkenheid(final String bsn, final Ja adresgevend) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        if (bsn != null) {
            persoonBericht.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        }

        OuderBericht betrokkenheid = new OuderBericht();
        betrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        betrokkenheid.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(adresgevend);
        betrokkenheid.setPersoon(persoonBericht);

        return betrokkenheid;
    }

    private KindBericht maakKindBetrokkenheid() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());

        KindBericht betrokkenheid = new KindBericht();
        betrokkenheid.setPersoon(persoonBericht);
        return betrokkenheid;
    }

    private FamilierechtelijkeBetrekkingBericht maakFamilieRelatie(final BetrokkenheidBericht vader,
        final BetrokkenheidBericht moeder, final BetrokkenheidBericht kind)
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

        FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        // relatieBericht.setStandaard(new RelatieStandaardGroepBericht());
        relatieBericht.setBetrokkenheden(betrokkenheden);

        return relatieBericht;
    }
}
