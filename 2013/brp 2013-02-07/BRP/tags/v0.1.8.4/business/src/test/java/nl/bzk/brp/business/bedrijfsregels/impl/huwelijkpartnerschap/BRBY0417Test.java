/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0417Test {

    private BRBY0417          brby0417;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieRepository relatieRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0417 = new BRBY0417();

        ReflectionTestUtils.setField(brby0417, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(brby0417, "relatieRepository", relatieRepository);
    }

    @Test
    public void testNietGehuwdPersoon() {
        Datum datumAanvang = new Datum(20120303);

        RelatieBericht relatieBericht = maakRelatie("123456789", "123456780", datumAanvang);

        PersoonModel persoon1 = new PersoonModel(relatieBericht.getBetrokkenheden().get(0).getBetrokkene());
        ReflectionTestUtils.setField(persoon1, "id", 1);

        PersoonModel persoon2 = new PersoonModel(relatieBericht.getBetrokkenheden().get(1).getBetrokkene());
        ReflectionTestUtils.setField(persoon2, "id", 2);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                persoon1);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456780"))).thenReturn(
                persoon2);

        Mockito.when(relatieRepository.heeftPartners(1, datumAanvang)).thenReturn(false);
        Mockito.when(relatieRepository.heeftPartners(2, datumAanvang)).thenReturn(false);

        List<Melding> meldingen = brby0417.executeer(null, relatieBericht, null);
        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                new Burgerservicenummer("123456789"));
        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                new Burgerservicenummer("123456780"));

        Mockito.verify(relatieRepository, Mockito.times(1)).heeftPartners(1, datumAanvang);
        Mockito.verify(relatieRepository, Mockito.times(1)).heeftPartners(2, datumAanvang);
    }

    @Test
    public void testAlGehuwdPersoon() {
        Datum datumAanvang = new Datum(20120303);

        RelatieBericht relatieBericht = maakRelatie("123456789", "123456780", datumAanvang);

        PersoonModel persoon1 = new PersoonModel(relatieBericht.getBetrokkenheden().get(0).getBetrokkene());
        ReflectionTestUtils.setField(persoon1, "id", 1);

        PersoonModel persoon2 = new PersoonModel(relatieBericht.getBetrokkenheden().get(1).getBetrokkene());
        ReflectionTestUtils.setField(persoon2, "id", 2);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                persoon1);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456780"))).thenReturn(
                persoon2);

        Mockito.when(relatieRepository.heeftPartners(1, datumAanvang)).thenReturn(true);
        Mockito.when(relatieRepository.heeftPartners(2, datumAanvang)).thenReturn(false);

        List<Melding> meldingen = brby0417.executeer(null, relatieBericht, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0417, meldingen.get(0).getCode());

        Mockito.verify(persoonRepository, Mockito.times(2)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));

        Mockito.verify(relatieRepository, Mockito.times(2))
                .heeftPartners(Matchers.anyInt(), Matchers.any(Datum.class));
    }

    @Test
    public void testDatumAanvangInDeToekomst() {
        // Datum 1 dag in de toekomst
        Datum vandaag = DatumUtil.vandaag();
        Datum datumAanvang = new Datum(vandaag.getWaarde() + 1);

        RelatieBericht relatieBericht = maakRelatie("123456789", "123456780", datumAanvang);

        PersoonModel persoon1 = new PersoonModel(relatieBericht.getBetrokkenheden().get(0).getBetrokkene());
        ReflectionTestUtils.setField(persoon1, "id", 1);

        PersoonModel persoon2 = new PersoonModel(relatieBericht.getBetrokkenheden().get(1).getBetrokkene());
        ReflectionTestUtils.setField(persoon2, "id", 2);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                persoon1);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456780"))).thenReturn(
                persoon2);

        Mockito.when(relatieRepository.heeftPartners(1, vandaag)).thenReturn(true);
        Mockito.when(relatieRepository.heeftPartners(2, datumAanvang)).thenReturn(false);

        List<Melding> meldingen = brby0417.executeer(null, relatieBericht, null);
        Assert.assertTrue(meldingen.size() > 0);
        Assert.assertEquals(MeldingCode.BRBY0417, meldingen.get(0).getCode());

        Mockito.verify(persoonRepository, Mockito.times(2)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));

        Mockito.verify(relatieRepository, Mockito.times(1)).heeftPartners(1, vandaag);
        Mockito.verify(relatieRepository, Mockito.times(1)).heeftPartners(2, vandaag);
    }

    private RelatieBericht maakRelatie(final String bsn1, final String bsn2, final Datum datumAanvang) {
        PersoonIdentificatienummersGroepBericht idGroep1 = new PersoonIdentificatienummersGroepBericht();
        idGroep1.setBurgerservicenummer(new Burgerservicenummer(bsn1));

        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setIdentificatienummers(idGroep1);

        PersoonIdentificatienummersGroepBericht idGroep2 = new PersoonIdentificatienummersGroepBericht();
        idGroep2.setBurgerservicenummer(new Burgerservicenummer(bsn2));

        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setIdentificatienummers(idGroep2);

        BetrokkenheidBericht partner1 = new BetrokkenheidBericht();
        partner1.setRol(SoortBetrokkenheid.PARTNER);
        partner1.setBetrokkene(persoon1);

        BetrokkenheidBericht partner2 = new BetrokkenheidBericht();
        partner2.setRol(SoortBetrokkenheid.PARTNER);
        partner2.setBetrokkene(persoon2);

        List<BetrokkenheidBericht> partners = new ArrayList<BetrokkenheidBericht>();
        partners.add(partner1);
        partners.add(partner2);

        RelatieStandaardGroepBericht relatieGegevens = new RelatieStandaardGroepBericht();
        relatieGegevens.setDatumAanvang(datumAanvang);

        RelatieBericht relatieBericht = new RelatieBericht();
        relatieBericht.setGegevens(relatieGegevens);
        relatieBericht.setBetrokkenheden(partners);

        return relatieBericht;
    }
}
