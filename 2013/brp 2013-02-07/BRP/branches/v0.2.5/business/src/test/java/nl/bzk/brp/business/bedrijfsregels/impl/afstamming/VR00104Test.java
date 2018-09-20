/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import nl.bzk.brp.business.bedrijfsregels.AbstractBedrijfsregelsTest;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class VR00104Test extends AbstractBedrijfsregelsTest {

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private VR00104 vr00104;

    @Before
    public void init() {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht adresGegevens = new PersoonAdresStandaardGroepBericht();
        adresGegevens.setPostcode(new Postcode("1000AB"));
        adres.setGegevens(adresGegevens);

        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("abcd")))
               .thenReturn(new PersoonAdresModel(adres, null));
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("def")))
               .thenThrow(new NoResultException());

        RedenWijzigingAdres redenWijzigingAdres = new RedenWijzigingAdres();
        ReflectionTestUtils.setField(redenWijzigingAdres, "redenWijzigingAdresCode", new RedenWijzigingAdresCode("P"));
        Mockito.when(referentieDataRepository.vindRedenWijzingAdresOpCode(Matchers.any(RedenWijzigingAdresCode.class)))
               .thenReturn(redenWijzigingAdres);

        vr00104 = new VR00104();
        ReflectionTestUtils.setField(vr00104, "persoonAdresRepository", persoonAdresRepository);
        ReflectionTestUtils.setField(vr00104, "referentieDataRepository", referentieDataRepository);
    }

    @Test
    public void testGeenAdresgevendAdres() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        BetrokkenheidBericht ouderBetrokkenheid = new BetrokkenheidBericht();
        ouderBetrokkenheid.setRol(SoortBetrokkenheid.OUDER);
        ouderBetrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        RelatieBericht nieuweSituatie = new RelatieBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertNotNull(meldingen);
        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(1, getLogAppender().size());
        Assert.assertEquals("Het is niet gelukt om het adres af te leiden.", getLogAppender().get(0));
    }

    @Test
    public void testAdresgevendPersoonHeeftGeenAdres() {
        getLogAppender().clear();
        Assert.assertTrue(getLogAppender().isEmpty());

        BetrokkenheidBericht ouderBetrokkenheid = new BetrokkenheidBericht();
        ouderBetrokkenheid.setRol(SoortBetrokkenheid.OUDER);

        RelatieBericht nieuweSituatie = new RelatieBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("def"));

        nieuweSituatie.getOuderBetrokkenheden().iterator().next().setBetrokkene(persoon);
        ouderBetrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        ouderBetrokkenheid.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertNotNull(meldingen);
        Assert.assertEquals(0, meldingen.size());
        Assert.assertFalse(getLogAppender().isEmpty());
        Assert.assertEquals(2, getLogAppender().size());
        Assert.assertEquals("Geen adres gevonden voor adresgevende ouder (bsn: def). Mogelijk vanwege data inconsistentie.", getLogAppender().get(0));
        Assert.assertEquals("Het is niet gelukt om het adres af te leiden.", getLogAppender().get(1));
    }

    @Test
    public void testAdresgevendPersoonMetAdres() {
        BetrokkenheidBericht ouderBetrokkenheid = new BetrokkenheidBericht();
        ouderBetrokkenheid.setRol(SoortBetrokkenheid.OUDER);
        ouderBetrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());

        PersoonBericht kind = new PersoonBericht();
        BetrokkenheidBericht kindBetrokkenheid = new BetrokkenheidBericht();
        kindBetrokkenheid.setRol(SoortBetrokkenheid.KIND);
        kindBetrokkenheid.setBetrokkene(kind);

        RelatieBericht nieuweSituatie = new RelatieBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);
        nieuweSituatie.getBetrokkenheden().add(kindBetrokkenheid);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("abcd"));

        nieuweSituatie.getOuderBetrokkenheden().iterator().next().setBetrokkene(persoon);
        ouderBetrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        ouderBetrokkenheid.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertEquals(0, meldingen.size());
        Assert.assertEquals(1, nieuweSituatie.getKindBetrokkenheid().getBetrokkene().getAdressen().size());
        PersoonAdresBericht persoonAdres =
            nieuweSituatie.getKindBetrokkenheid().getBetrokkene().getAdressen().iterator().next();
        Assert
            .assertEquals("P", persoonAdres.getGegevens().getRedenWijziging().getRedenWijzigingAdresCode().getWaarde());
        Assert.assertNull(persoonAdres.getGegevens().getAangeverAdreshouding());
        Assert.assertEquals(new Datum(20120101), persoonAdres.getGegevens().getDatumAanvangAdreshouding());
    }

    @Override
    protected Class getTestClass() {
        return VR00104.class;
    }
}
