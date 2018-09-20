/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.AbstractBedrijfsregelsTest;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class BRPUC00120Test extends AbstractBedrijfsregelsTest {

    @Mock
    private PersoonAdresRepository              persoonAdresRepository;

    private PersoonAdresBericht                 adresMoeder;

    @InjectMocks
    private final BRPUC00120                    brpuc00120 = new BRPUC00120();

    private FamilierechtelijkeBetrekkingBericht nieuweSituatie;
    private PersoonBericht                      kind;
    private PersoonBericht                      moeder;
    private OuderBericht                        moederBetr;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        moeder = new PersoonBericht();
        adresMoeder = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht moederAdresGegevens = new PersoonAdresStandaardGroepBericht();
        moederAdresGegevens.setDatumAanvangAdreshouding(new Datum(20120501));
        adresMoeder.setStandaard(moederAdresGegevens);
        PersoonModel moederModel = new PersoonModel(moeder);
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(new PersoonAdresModel(adresMoeder, moederModel));

        nieuweSituatie = new FamilierechtelijkeBetrekkingBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        moederBetr = new OuderBericht();
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);
        moeder = new PersoonBericht();
        moeder.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        moeder.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456789"));
        moeder.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        moederBetr.setPersoon(moeder);
        nieuweSituatie.getBetrokkenheden().add(moederBetr);

        BetrokkenheidBericht kindBetr = new KindBericht();
        kind = new PersoonBericht();
        kind.setGeboorte(new PersoonGeboorteGroepBericht());
        kind.getGeboorte().setDatumGeboorte(new Datum(20120601));
        kindBetr.setPersoon(kind);
        nieuweSituatie.getBetrokkenheden().add(kindBetr);
    }

    @Test
    public void testGeboorteDatumKindNaAanvAdreshoudingMoeder() {
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void testGeboorteDatumKindVoorAanvAdreshoudingMoeder() {
        kind.getGeboorte().setDatumGeboorte(new Datum(20120401));
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.BRPUC00120, melding.get(0).getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding.get(0).getSoort());
        Assert.assertEquals(
                "De adresgevende ouder is verhuisd na de geboorte. Het kind dient wellicht ook te worden verhuisd.",
                melding.get(0).getOmschrijving());
    }

    @Test
    public void testGeboorteDatumKindOpAanvAdreshoudingMoeder() {
        kind.getGeboorte().setDatumGeboorte(new Datum(20120501));
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalen() {
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(null);
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);

        Assert.assertNotNull(melding);
        Mockito.verify(getLogger()).error("De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
    }

    @Test
    public void testKanAdresGevendeOuderNietBepalenIndicatieIsNull() {
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(null);
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);

        Assert.assertNotNull(melding);
        Mockito.verify(getLogger()).error("De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
    }

    @Test
    public void testIdentificatienummersMoederNietOpgegeven() {
        moeder.setIdentificatienummers(null);
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(0, melding.size());
        Mockito.verify(getLogger()).error(
                "Groep persoonidentificatienummers van adresgevende ouder niet opgegeven in het bericht.");
    }

    @Test
    public void testDatumAanvAdresHoudingOnbekend() {
        adresMoeder.getStandaard().setDatumAanvangAdreshouding(null);
        List<Melding> melding = brpuc00120.executeer(null, nieuweSituatie, null);
        Assert.assertTrue(melding.size() == 0);
    }
}
