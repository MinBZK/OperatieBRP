/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;

import nl.bzk.brp.dataaccess.repository.PersoonAdresMdlRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsAanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRPUC00120Test {

    @Mock
    private PersoonAdresMdlRepository persoonAdresRepository;

    private PersoonAdresBericht adresMoeder;
    private BRPUC00120 brpuc00120;

    private RelatieBericht nieuweSituatie;
    private PersoonBericht kind;
    private PersoonBericht moeder;
    private BetrokkenheidBericht moederBetr;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        moeder = new PersoonBericht();
        adresMoeder = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht moederAdresGegevens = new PersoonAdresStandaardGroepBericht();
        moederAdresGegevens.setDatumAanvangAdreshouding(new Datum(20120501));
        adresMoeder.setGegevens(moederAdresGegevens);
        PersoonModel moederModel = new PersoonModel(moeder);
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(new PersoonAdresModel(adresMoeder, moederModel));
        brpuc00120 = new BRPUC00120();
        ReflectionTestUtils.setField(brpuc00120, "persoonAdresRepository", persoonAdresRepository);

        nieuweSituatie = new RelatieBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        moederBetr = new BetrokkenheidBericht();
        moederBetr.setRol(SoortBetrokkenheid.OUDER);
        moederBetr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
        moeder = new PersoonBericht();
        moeder.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        moeder.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer("123456789"));
        moeder.setGeslachtsAanduiding(new PersoonGeslachtsAanduidingGroepBericht());
        moederBetr.setBetrokkene(moeder);
        nieuweSituatie.getBetrokkenheden().add(moederBetr);

        BetrokkenheidBericht kindBetr = new BetrokkenheidBericht();
        kindBetr.setRol(SoortBetrokkenheid.KIND);
        kind = new PersoonBericht();
        kind.setGeboorte(new PersoonGeboorteGroepBericht());
        kind.getGeboorte().setDatumGeboorte(new Datum(20120601));
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
        kind.getGeboorte().setDatumGeboorte(new Datum(20120401));
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.BRPUC00120, melding.getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding.getSoort());
        Assert.assertEquals("De adresgevende ouder is verhuisd na de geboorte. Het kind dient wellicht ook te worden verhuisd.", melding.getOmschrijving());
    }

    @Test
    public void testGeboorteDatumKindOpAanvAdreshoudingMoeder() {
        kind.getGeboorte().setDatumGeboorte(new Datum(20120501));
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalen() {
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalenIndicatieIsNull() {
        moederBetr.getBetrokkenheidOuderschap().setIndAdresGevend(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testIdentificatieNummersMoederNietOpgegeven() {
        moeder.setIdentificatieNummers(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testDatumAanvAdresHoudingOnbekend() {
        adresMoeder.getGegevens().setDatumAanvangAdreshouding(null);
        Melding melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNull(melding);
    }
}
