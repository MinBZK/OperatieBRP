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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class VR00104Test extends AbstractBedrijfsregelsTest {

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @InjectMocks
    private final VR00104 vr00104 = new VR00104();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht adresGegevens = new PersoonAdresStandaardGroepBericht();
        adresGegevens.setPostcode(new Postcode("1000AB"));
        adres.setStandaard(adresGegevens);

        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("1234")))
               .thenReturn(new PersoonAdresModel(adres, null));
        Mockito.when(persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("567")))
               .thenThrow(new NoResultException());

        RedenWijzigingAdres redenWijzigingAdres = new RedenWijzigingAdres(new RedenWijzigingAdresCode("P"), null);
        Mockito.when(referentieDataRepository.vindRedenWijzingAdresOpCode(Matchers.any(RedenWijzigingAdresCode.class)))
               .thenReturn(redenWijzigingAdres);
    }

    @Test
    public void testGeenAdresgevendAdres() {
        OuderBericht ouderBetrokkenheid = new OuderBericht();
        ouderBetrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        FamilierechtelijkeBetrekkingBericht nieuweSituatie = new FamilierechtelijkeBetrekkingBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertNotNull(meldingen);
        Assert.assertEquals(0, meldingen.size());
        Mockito.verify(getLogger()).error("Het is niet gelukt om het adres af te leiden.", "VR00104");
    }

    @Test
    public void testAdresgevendPersoonHeeftGeenAdres() {
        OuderBericht ouderBetrokkenheid = new OuderBericht();

        FamilierechtelijkeBetrekkingBericht nieuweSituatie = new FamilierechtelijkeBetrekkingBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("567"));

        nieuweSituatie.getOuderBetrokkenheden().iterator().next().setPersoon(persoon);
        ouderBetrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        ouderBetrokkenheid.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertNotNull(meldingen);
        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(getLogger()).error("Geen adres gevonden voor adresgevende ouder (bsn: 567). Mogelijk vanwege data inconsistentie.");
        Mockito.verify(getLogger()).error("Het is niet gelukt om het adres af te leiden.", "VR00104");
    }

    @Test
    public void testAdresgevendPersoonMetAdres() {
        OuderBericht ouderBetrokkenheid = new OuderBericht();
        ouderBetrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());

        PersoonBericht kind = new PersoonBericht();
        KindBericht kindBetrokkenheid = new KindBericht();
        kindBetrokkenheid.setPersoon(kind);

        FamilierechtelijkeBetrekkingBericht nieuweSituatie = new FamilierechtelijkeBetrekkingBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        nieuweSituatie.getBetrokkenheden().add(ouderBetrokkenheid);
        nieuweSituatie.getBetrokkenheden().add(kindBetrokkenheid);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("1234"));

        nieuweSituatie.getOuderBetrokkenheden().iterator().next().setPersoon(persoon);
        ouderBetrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        ouderBetrokkenheid.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);

        List<Melding> meldingen = vr00104.executeer(null, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120101)).getActie());

        Assert.assertEquals(0, meldingen.size());
        Assert.assertEquals(1, nieuweSituatie.getKindBetrokkenheid().getPersoon().getAdressen().size());
        PersoonAdresBericht persoonAdres =
            nieuweSituatie.getKindBetrokkenheid().getPersoon().getAdressen().iterator().next();
        Assert
            .assertEquals("P", persoonAdres.getStandaard().getRedenWijziging().getCode().getWaarde());
        Assert.assertNull(persoonAdres.getStandaard().getAangeverAdreshouding());
        Assert.assertEquals(new Datum(20120101), persoonAdres.getStandaard().getDatumAanvangAdreshouding());
    }
}
