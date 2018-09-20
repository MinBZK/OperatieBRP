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
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
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
    private PersoonRepository persoonRepository;

    @Before
    public void init() {
        bijhoudingsgemeenteAfleiding = new BijhoudingsgemeenteAfleiding();
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(bijhoudingsgemeenteAfleiding, "persoonRepository", persoonRepository);
    }

    @Test
    public void testGeenAdresGevendeOuder() {
        RelatieBericht familie = bouwRelatie();
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("kind")));
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("vader")));
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("moeder")));

        Assert.assertTrue(bijhoudingsgemeenteAfleiding.executeer(null, familie, null).size() == 0);
    }

    @Test
    public void testAdresGevendeMoederZonderIdentificatieNummers() {
        RelatieBericht familie = bouwRelatie();
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("kind")));
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("vader")));

        BetrokkenheidBericht moederBetr = maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, null);
        moederBetr.getBetrokkene().setIdentificatienummers(null);
        moederBetr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
        familie.getBetrokkenheden().add(moederBetr);

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("Het BSN van de ouder is niet opgegeven.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testAdresGevendeMoederKanNietGevondenWorden() {
        RelatieBericht familie = bouwRelatie();
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("kind")));
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("vader")));

        BetrokkenheidBericht moederBetr =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("moeder"));
        moederBetr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
        familie.getBetrokkenheden().add(moederBetr);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder"))).thenReturn(null);

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("Kan ouder niet vinden met BSN: moeder", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testBijhoudingsGemeenteAfleidingNormaleFlow() {
        RelatieBericht familie = bouwRelatie();
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, new Burgerservicenummer("kind")));
        familie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("vader")));

        BetrokkenheidBericht moederBetr =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, new Burgerservicenummer("moeder"));
        moederBetr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
        PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeenteGroepMoeder =
            new PersoonBijhoudingsgemeenteGroepBericht();
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 34));
        bijhoudingsgemeenteGroepMoeder.setBijhoudingsgemeente(gemeente);
        moederBetr.getBetrokkene().setBijhoudingsgemeente(bijhoudingsgemeenteGroepMoeder);
        familie.getBetrokkenheden().add(moederBetr);

        PersoonModel moederModel = new PersoonModel(moederBetr.getBetrokkene());
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")))
               .thenReturn(moederModel);

        Assert.assertNull(
            familie.getKindBetrokkenheid().getBetrokkene().getBijhoudingsgemeente().getBijhoudingsgemeente());

        List<Melding> meldingen = bijhoudingsgemeenteAfleiding.executeer(null, familie, new ActieBericht());
        Assert.assertEquals(0, meldingen.size());

        Assert.assertEquals(Short.valueOf((short) 34), familie.getKindBetrokkenheid().getBetrokkene()
                                                              .getBijhoudingsgemeente().getBijhoudingsgemeente()
                                                              .getGemeentecode().getWaarde());
    }

    private RelatieBericht bouwRelatie() {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return relatie;
    }

    private BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
        final Burgerservicenummer bsn)
    {
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(soort);
        betr.setRelatie(relatie);

        String bsnAlsString = null;
        if (bsn != null) {
            bsnAlsString = bsn.getWaarde();
        }
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsnAlsString, null, null, null, "test", null, null);
        betr.setBetrokkene(persoon);
        return betr;
    }
}
