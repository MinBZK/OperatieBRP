/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.HashSet;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRPUC00120Test {

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    private PersistentPersoonAdres adresMoeder;
    private BRPUC00120 brpuc00120;

    private Relatie nieuweSituatie;
    private Persoon kind;
    private Persoon moeder;
    private Betrokkenheid moederBetr;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        adresMoeder = new PersistentPersoonAdres();
        adresMoeder.setDatumAanvangAdreshouding(20120501);
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(Mockito.anyString())).thenReturn(adresMoeder);
        brpuc00120 = new BRPUC00120();
        ReflectionTestUtils.setField(brpuc00120, "persoonAdresRepository", persoonAdresRepository);

        nieuweSituatie = new Relatie();
        nieuweSituatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
        moederBetr = new Betrokkenheid();
        moederBetr.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ReflectionTestUtils.setField(moederBetr, "indAdresGevend", Boolean.TRUE);
        moeder = new Persoon();
        moeder.setIdentificatienummers(new PersoonIdentificatienummers());
        moeder.getIdentificatienummers().setBurgerservicenummer("123456789");
        moeder.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        moederBetr.setBetrokkene(moeder);
        nieuweSituatie.getBetrokkenheden().add(moederBetr);

        Betrokkenheid kindBetr = new Betrokkenheid();
        kindBetr.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind = new Persoon();
        kind.setGeboorte(new PersoonGeboorte());
        kind.getGeboorte().setDatumGeboorte(20120601);
        kindBetr.setBetrokkene(kind);
        nieuweSituatie.getBetrokkenheden().add(kindBetr);
    }

    @Test
    public void testGeboorteDatumKindNaAanvAdreshoudingMoeder() {
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testGeboorteDatumKindVoorAanvAdreshoudingMoeder() {
        kind.getGeboorte().setDatumGeboorte(20120401);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.BRPUC00120, melding.getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding.getSoort());
        Assert.assertEquals("De adresgevende ouder is verhuisd na de geboorte. Het kind dient wellicht ook te worden verhuisd.", melding.getOmschrijving());
    }

    @Test
    public void testGeboorteDatumKindOpAanvAdreshoudingMoeder() {
        kind.getGeboorte().setDatumGeboorte(20120501);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalen() {
        ReflectionTestUtils.setField(moederBetr, "indAdresGevend", Boolean.FALSE);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalenIndicatieIsNull() {
        ReflectionTestUtils.setField(moederBetr, "indAdresGevend", null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testIdentificatieNummersMoederNietOpgegeven() {
        moeder.setIdentificatienummers(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testDatumAanvAdresHoudingOnbekend() {
        adresMoeder.setDatumAanvangAdreshouding(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNull(melding);
    }
}
