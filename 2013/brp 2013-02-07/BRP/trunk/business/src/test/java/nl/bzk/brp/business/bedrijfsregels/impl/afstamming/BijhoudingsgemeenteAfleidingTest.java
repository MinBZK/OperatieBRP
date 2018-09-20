/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 *
 */
public class BijhoudingsgemeenteAfleidingTest {

    private BijhoudingsgemeenteAfleiding bijhoudingsgemeenteAfleiding;

    @Mock
    private PersoonRepository            persoonRepository;

    @Before
    public void init() {
        bijhoudingsgemeenteAfleiding = new BijhoudingsgemeenteAfleiding();
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(bijhoudingsgemeenteAfleiding, "persoonRepository", persoonRepository);
    }

    @Test
    public void testGeenAdresGevendeOuder() {
        FamilierechtelijkeBetrekkingBericht familie = bouwRelatie();
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("1111")));
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("2222")));
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("33333")));

        Assert.assertTrue(bijhoudingsgemeenteAfleiding.executeer(null, familie, null).size() == 0);
    }

    @Test
    public void testAdresGevendeMoederZonderIdentificatieNummers() {
        FamilierechtelijkeBetrekkingBericht familie = bouwRelatie();
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("1111")));
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("2222")));

        OuderBericht moederBetr = (OuderBericht) maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, null);
        moederBetr.getPersoon().setIdentificatienummers(null);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);
        familie.getBetrokkenheden().add(moederBetr);

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
        Assert.assertEquals("Het BSN van de ouder is niet opgegeven.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testAdresGevendeMoederKanNietGevondenWorden() {
        FamilierechtelijkeBetrekkingBericht familie = bouwRelatie();
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("1111")));
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("2222")));

        OuderBericht moederBetr =
            (OuderBericht) maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("33333"));
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);
        familie.getBetrokkenheden().add(moederBetr);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("33333"))).thenReturn(null);

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
        Assert.assertEquals("Kan ouder niet vinden met BSN: 33333", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testBijhoudingsGemeenteAfleidingNormaleFlow() {
        FamilierechtelijkeBetrekkingBericht familie = bouwRelatie();
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("1111")));
        familie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("2222")));

        OuderBericht moederBetr =
            (OuderBericht) maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("33333"));
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);
        PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeenteGroepMoeder =
            new PersoonBijhoudingsgemeenteGroepBericht();
        Partij gemeente =
            new Partij(new NaamEnumeratiewaarde("gem"), SoortPartij.GEMEENTE, new GemeenteCode((short) 34), null,
                       null, null, null, null, StatusHistorie.A, null);

        bijhoudingsgemeenteGroepMoeder.setBijhoudingsgemeente(gemeente);
        moederBetr.getPersoon().setBijhoudingsgemeente(bijhoudingsgemeenteGroepMoeder);
        familie.getBetrokkenheden().add(moederBetr);

        PersoonModel moederModel = new PersoonModel(moederBetr.getPersoon());
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("33333"))).thenReturn(
                moederModel);

        Assert.assertNull(familie.getKindBetrokkenheid().getPersoon().getBijhoudingsgemeente()
                .getBijhoudingsgemeente());

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie,
            new ActieRegistratieAanschrijvingBericht());
        Assert.assertEquals(0, meldingen.size());

        Assert.assertEquals("0034", familie.getKindBetrokkenheid().getPersoon()
                .getBijhoudingsgemeente().getBijhoudingsgemeente().getCode().toString());
    }

    private FamilierechtelijkeBetrekkingBericht bouwRelatie() {
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return relatie;
    }

    private BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
            final Burgerservicenummer bsn)
    {
        BetrokkenheidBericht betr;

        if (SoortBetrokkenheid.KIND == soort) {
            betr = new KindBericht();
        } else if (SoortBetrokkenheid.OUDER == soort) {
            betr = new OuderBericht();
        } else {
            throw new IllegalArgumentException("Ondersteund alleen soort Kind en Ouder");
        }

        betr.setRelatie(relatie);

        Integer bsnInt = null;
        if (bsn != null) {
            bsnInt = bsn.getWaarde();
        }
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsnInt, null, null, null, "test", null, null);
        betr.setPersoon(persoon);
        return betr;
    }
}
