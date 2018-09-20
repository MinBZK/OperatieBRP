/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.Arrays;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BRBY0118Test {

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    private BRBY0118               brby0118;

    @Before
    public void init() {
        PersistentPersoonAdres adres = new PersistentPersoonAdres();
        adres.setPostcode("1000AB");

        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon("abcd")).thenReturn(adres);
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon("def")).thenReturn(null);

        brby0118 = new BRBY0118();
        ReflectionTestUtils.setField(brby0118, "persoonAdresRepository", persoonAdresRepository);
    }

    @Test
    public void testGeenAdresgevendAdres() {
        Betrokkenheid ouderBetrokkenheid = new Betrokkenheid();
        ouderBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        Relatie nieuweSituatie = new Relatie();
        nieuweSituatie.voegOuderBetrokkenhedenToe(Arrays.asList(ouderBetrokkenheid));

        Melding melding = brby0118.executeer(null, nieuweSituatie, 20120101);

        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals("Kan adres gevende ouder van nieuwgeborene niet bepalen in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testAdresgevendPersoonHeeftGeenAdres() {
        Betrokkenheid ouderBetrokkenheid = new Betrokkenheid();
        ouderBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);

        Relatie nieuweSituatie = new Relatie();
        nieuweSituatie.voegOuderBetrokkenhedenToe(Arrays.asList(ouderBetrokkenheid));

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("def");

        nieuweSituatie.getOuderBetrokkenheden().get(0).setBetrokkene(persoon);
        ReflectionTestUtils.setField(nieuweSituatie.getOuderBetrokkenheden().get(0), "indAdresGevend", Boolean.TRUE);

        Melding melding = brby0118.executeer(null, nieuweSituatie, 20120101);

        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals("Kan adres gevende ouder van nieuwgeborene niet bepalen in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testAdresgevendPersoonMetAdres() {
        Betrokkenheid ouderBetrokkenheid = new Betrokkenheid();
        ouderBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);

        Persoon kind = new Persoon();
        Betrokkenheid kindBetrokkenheid = new Betrokkenheid();
        kindBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kindBetrokkenheid.setBetrokkene(kind);

        Relatie nieuweSituatie = new Relatie();
        nieuweSituatie.voegOuderBetrokkenhedenToe(Arrays.asList(ouderBetrokkenheid, kindBetrokkenheid));

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("abcd");

        nieuweSituatie.getOuderBetrokkenheden().get(0).setBetrokkene(persoon);
        ReflectionTestUtils.setField(nieuweSituatie.getOuderBetrokkenheden().get(0), "indAdresGevend", Boolean.TRUE);

        Melding melding = brby0118.executeer(null, nieuweSituatie, 20120101);

        Assert.assertNull(melding);
        Assert.assertEquals(1, nieuweSituatie.getKindBetrokkenheid().getBetrokkene().getAdressen().size());
        PersoonAdres persoonAdres =
            nieuweSituatie.getKindBetrokkenheid().getBetrokkene().getAdressen().iterator().next();
        Assert.assertEquals(RedenWijzigingAdres.AMBTSHALVE, persoonAdres.getRedenWijziging());
        Assert.assertNull(persoonAdres.getAangeverAdreshouding());
        Assert.assertEquals(Integer.valueOf(20120101), persoonAdres.getDatumAanvangAdreshouding());
    }
}
