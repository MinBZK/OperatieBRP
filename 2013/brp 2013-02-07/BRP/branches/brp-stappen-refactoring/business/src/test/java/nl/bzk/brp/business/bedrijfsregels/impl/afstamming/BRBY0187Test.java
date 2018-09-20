/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0187Test {

    @Mock
    private RelatieRepository   relatieRepository;

    @Mock
    private PersoonRepository   persoonRepository;

    private BRBY0187            brby0187;

    private static final String MOEDER_BSN  = "111111111";
    private PersoonModel        moeder;
    private PersoonModel        vader;
    private PersoonModel        broer;
    private PersoonModel        zus;
    private final List<Integer> kinderenIds = Arrays.asList(new Integer(5001), new Integer(6001));

    private PersoonBericht      berichtMoeder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0187 = new BRBY0187();
        ReflectionTestUtils.setField(brby0187, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(brby0187, "relatieRepository", relatieRepository);

        berichtMoeder =
            PersoonBuilder.bouwPersoon(MOEDER_BSN, Geslachtsaanduiding.VROUW, 19360408,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "moeder", "van", "Houten");
        berichtMoeder.setCommunicatieID("id.moeder.pers");
        berichtMoeder.getIdentificatienummers().setCommunicatieID("id.moeder.pers.ids");
        berichtMoeder.getGeboorte().setCommunicatieID("id.moeder.pers.geboorte");

        PersoonBericht v =
            PersoonBuilder.bouwPersoon("222222222", Geslachtsaanduiding.MAN, 19261225,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "vader", "van", "Houten");
        PersoonBericht b =
            PersoonBuilder.bouwPersoon("333333333", Geslachtsaanduiding.MAN, 20060409,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "broer", "van", "Houten");
        PersoonBericht z =
            PersoonBuilder.bouwPersoon("555555555", Geslachtsaanduiding.MAN, 20090730,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "zus", "van", "Houten");

        // bouw nu de PersoonModels om teerug te geven door de Mock
        moeder = new PersoonModel(berichtMoeder);
        vader = new PersoonModel(v);
        broer = new PersoonModel(b);
        zus = new PersoonModel(z);

        ReflectionTestUtils.setField(moeder, "iD", new Integer(3001));
        ReflectionTestUtils.setField(vader, "iD", new Integer(4001));
        ReflectionTestUtils.setField(broer, "iD", new Integer(5001));
        ReflectionTestUtils.setField(zus, "iD", new Integer(6001));

        when(persoonRepository.findByBurgerservicenummer(Matchers.eq(new Burgerservicenummer(MOEDER_BSN)))).thenReturn(
                moeder);

        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(3001)))).thenReturn(moeder);
        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(4001)))).thenReturn(vader);
        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(5001)))).thenReturn(broer);
        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(6001)))).thenReturn(zus);

        when(relatieRepository.haalopKinderen((Matchers.eq(new Integer(3001))))).thenReturn(kinderenIds);
    }

    @Test
    public void testKindExactJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20120730
        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20090730,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();

        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(0, meldingen.size());

    }

    @Test
    public void testKind9MaandenJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20120730
        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20120730,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();

        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testKindExact9MaandenJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20100601 (== exact 306 dgn)
        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20100601,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();

        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(0, meldingen.size());

    }

    @Test
    public void testKind9MaandenMinEenDagJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20100531 (== 305 dgn)
        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20100531,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        k.setCommunicatieID("id.kind.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();

        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0187, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, meldingen.get(0).getSoort());
        Assert.assertEquals("id.kind.pers.geboorte", meldingen.get(0).getCommunicatieID());
    }

    @Test
    public void testRelatieZonderModer() throws Exception {
        // Er is WEL een ouder, maar deze ouder is GEEN adresgevend. ==> geen 'Moeder'

        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20100531,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        k.setCommunicatieID("id.kind.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        // zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();
        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuigEnNieuwNull() throws Exception {
        // Er is WEL een ouder, maar deze ouder is GEEN adresgevend. ==> geen 'Moeder'

        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20100531,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        k.setCommunicatieID("id.kind.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        // zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();
        List<Melding> meldingen = brby0187.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testKindGeenGeboorteDatum() throws Exception {
        // jongste kind is 20090730, kind is 20100531 (== 305 dgn)
        PersoonBericht k =
            PersoonBuilder.bouwPersoon("666666666", Geslachtsaanduiding.MAN, 20100531,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "kind", "van", "Houten");
        k.setCommunicatieID("id.kind.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.setGeboorte(null);
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder).voegKindToe(k)
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        ActieBericht actie = maakStandaardActie();

        List<Melding> meldingen = brby0187.executeer(null, nieuweSituatie, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    private void zetOuderMoederAlsIndicatieAdresHoudend(final FamilierechtelijkeBetrekkingBericht nieuweSituatie) {
        // zoek binnen deze relatie, en zet de ouder met bsn: MOEDER_BSN and indicatieAdresHouden.
        for (OuderBericht betr : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer().getWaarde().toString().equals(MOEDER_BSN)) {
                betr.setOuderschap(new OuderOuderschapGroepBericht());
                betr.getOuderschap().setIndicatieOuderUitWieKindIsVoortgekomen(Ja.J);
            }
        }

    }

    private ActieBericht maakStandaardActie() {
        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer("123"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

}
