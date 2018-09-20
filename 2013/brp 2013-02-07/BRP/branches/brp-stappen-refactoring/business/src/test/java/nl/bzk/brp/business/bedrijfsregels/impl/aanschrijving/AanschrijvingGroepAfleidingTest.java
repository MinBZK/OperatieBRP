/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.aanschrijving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class AanschrijvingGroepAfleidingTest {

    private AanschrijvingGroepAfleiding aanschrijvingGroepAfleiding;

    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private RelatieRepository relatieRepository;

    private AdellijkeTitel baron;
    private Predikaat      hoogheid;

    private PersoonModel partner;
    private PersoonModel hoofdPersoon;

    private static final Datum  AANVANG_DATUM      = new Datum(20150205);
    private static final String VOORNAAM_EIGEN     = "Jan-Pieter-Eigen";
    private static final String VOORNAAM_PARTNER   = "May-June-Partner";
    private static final String ACHTERNAAM_EIGEN   = "EigenSamen";
    private static final String ACHTERNAAM_PARTNER = "PartnerSamen";

    private static final String VOORVOEG_EIGEN    = "vdSamen";
    private static final String VOORVOEG_PARTNER  = "vdPs";
    private static final String SCHEIDING_EIGEN   = "m";
    private static final String SCHEIDING_PARTNER = "v";

    @Before
    public void init() {
        aanschrijvingGroepAfleiding = new AanschrijvingGroepAfleiding();
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(aanschrijvingGroepAfleiding, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(aanschrijvingGroepAfleiding, "relatieRepository", relatieRepository);
        baron = new AdellijkeTitel(new AdellijkeTitelCode("B"), null, null);
        hoogheid = new Predikaat(new PredikaatCode("H"), null, null);
        PersoonBericht p = maakStandaardPersoon();
        p.setAanschrijving(maakAanschrijvingBericht(baron, hoogheid, JaNee.NEE, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.DUMMY, "vdP", "k", "Partners"));
        p.setSamengesteldeNaam(maakSamengesteldeNaam(VOORNAAM_PARTNER, ACHTERNAAM_PARTNER, VOORVOEG_PARTNER, null,
            hoogheid, SCHEIDING_PARTNER));
        partner = new PersoonModel(p);
        partner.getGeslachtsnaamcomponenten().add(
            new PersoonGeslachtsnaamcomponentModel(maakPersoonGeslachtsnaamcomponent("Partner", "del", baron,
                hoogheid, "-"), partner));

        p = maakStandaardPersoon();
        p.setAanschrijving(maakAanschrijvingBericht(baron, hoogheid, JaNee.JA, JaNee.JA,
            WijzeGebruikGeslachtsnaam.EIGEN, "vdE", "e", "Eigens"));
        p.setSamengesteldeNaam(maakSamengesteldeNaam(VOORNAAM_EIGEN, ACHTERNAAM_EIGEN, VOORVOEG_EIGEN, null, hoogheid,
            SCHEIDING_EIGEN));
        hoofdPersoon = new PersoonModel(p);
        hoofdPersoon.getGeslachtsnaamcomponenten().add(
            new PersoonGeslachtsnaamcomponentModel(maakPersoonGeslachtsnaamcomponent("Eigen", "mar", null, null,
                "+"), hoofdPersoon));
    }

    @Test
    public void testIndicatieE() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.EIGEN, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN, persoon.getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamenAanschrijving().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaatAanschrijving());
    }

    @Test
    public void testIndicatieEMetPredikaat() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.JA,
            WijzeGebruikGeslachtsnaam.EIGEN, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN, persoon.getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamenAanschrijving().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
        Assert.assertEquals(hoogheid, persoon.getAanschrijving().getPredikaatAanschrijving());
    }

    @Test
    public void testIndicatieP() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.PARTNER, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_PARTNER, persoon.getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamenAanschrijving().getWaarde());
        Assert.assertEquals(SCHEIDING_PARTNER, persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals(VOORVOEG_PARTNER, persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaatAanschrijving());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
    }

    @Test
    public void testIndicatiePPartnerNietGevonden() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
               .thenReturn(new ArrayList<Integer>());
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.PARTNER, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(false, meldingen.isEmpty());
        Assert.assertEquals("Partner niet gevonden.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testIndicatieV() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.JA,
            WijzeGebruikGeslachtsnaam.PARTNER_EIGEN, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_PARTNER + "-" + VOORVOEG_EIGEN + SCHEIDING_EIGEN + ACHTERNAAM_EIGEN, persoon
            .getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamenAanschrijving().getWaarde());
        Assert.assertEquals(SCHEIDING_PARTNER, persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals(VOORVOEG_PARTNER, persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(hoogheid, persoon.getAanschrijving().getPredikaatAanschrijving());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
    }

    @Test
    public void testIndicatieN() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.EIGEN_PARTNER, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN + "-" + VOORVOEG_PARTNER + SCHEIDING_PARTNER + ACHTERNAAM_PARTNER, persoon
            .getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamenAanschrijving().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaatAanschrijving());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
    }

    @Test
    public void testVolledigOpgegeven() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.NEE, JaNee.NEE, null, "voorv", "s",
            "achternaam"));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals("achternaam", persoon.getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getVoornamenAanschrijving());
        Assert.assertEquals("s", persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals("voorv", persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaatAanschrijving());
        Assert.assertEquals(baron, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
    }

    @Test
    public void testVolledigOpgegevenZonderNaam() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.NEE, JaNee.NEE, null, "voorv", "s", null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(null, persoon.getAanschrijving().getGeslachtsnaamAanschrijving());
        Assert.assertEquals(null, persoon.getAanschrijving().getVoornamenAanschrijving());
        Assert.assertEquals("s", persoon.getAanschrijving().getScheidingstekenAanschrijving().getWaarde());
        Assert.assertEquals("voorv", persoon.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaatAanschrijving());
        Assert.assertEquals(baron, persoon.getAanschrijving().getAdellijkeTitelAanschrijving());
    }

    @Test
    public void testVolledigOpgegevenZonderNaamPersistentZonderSamengesteldeNaam() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.NEE, JaNee.NEE, null, "voorv", "s", null));

        PersoonSamengesteldeNaamGroepModel samenNaam = hoofdPersoon.getSamengesteldeNaam();
        ReflectionTestUtils.setField(hoofdPersoon, "samengesteldeNaam", null);
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        ReflectionTestUtils.setField(hoofdPersoon, "samengesteldeNaam", samenNaam);
        // wordt nu niet meer gecopierd it samengesteldenaam. =>> geen error (pas bij validatie fout),
        // geslachtsnaam blijft null value.
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
        Assert.assertEquals(null, persoon.getAanschrijving().getGeslachtsnaamAanschrijving());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigRootObject() {
        RelatieBericht b = new HuwelijkBericht();
        aanschrijvingGroepAfleiding.executeer(b, b, new ActieRegistratieAanschrijvingBericht());
    }

    @Test(expected = RuntimeException.class)
    public void testIndicatiePTeveelPartners() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class))).thenReturn(
            Arrays.asList(new Integer(1), new Integer(2)));
        //Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.JA, JaNee.NEE,
            WijzeGebruikGeslachtsnaam.PARTNER, null, null, null));

        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_AANSCHRIJVING).setDatumAanvang(AANVANG_DATUM)
                               .getActie();

        aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
    }

    private PersoonBericht maakStandaardPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoonBericht.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        persoonBericht.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        return persoonBericht;
    }

    private PersoonGeslachtsnaamcomponentBericht maakPersoonGeslachtsnaamcomponent(final String naam,
        final String voorvoegsel, final AdellijkeTitel adellijkeTitel, final Predikaat predikaat,
        final String scheidingsteken)
    {
        final PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepBericht =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepBericht.setNaam(new Geslachtsnaamcomponent(naam));
        standaardGroepBericht.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        standaardGroepBericht.setAdellijkeTitel(adellijkeTitel);
        standaardGroepBericht.setPredikaat(predikaat);
        standaardGroepBericht.setScheidingsteken(new Scheidingsteken(scheidingsteken));
        comp.setStandaard(standaardGroepBericht);
        return comp;
    }

    // private PersoonVoornaamBericht maakPersoonVoorNaam(final String naam, final Integer volgnummer) {
    // final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
    // PersoonVoornaamStandaardGroepBericht standaardGroepBericht = new PersoonVoornaamStandaardGroepBericht();
    // standaardGroepBericht.setVoornaam(new Voornaam(naam));
    // voornaam.setStandaard(standaardGroepBericht);
    // voornaam.setVolgnummer(new Volgnummer(volgnummer));
    // return voornaam;
    // }

    private PersoonSamengesteldeNaamGroepBericht maakSamengesteldeNaam(final String voornamen, final String naam,
        final String voorvoegsel, final AdellijkeTitel adellijkeTitel, final Predikaat predikaat,
        final String scheidingsteken)
    {
        PersoonSamengesteldeNaamGroepBericht samenNaam = new PersoonSamengesteldeNaamGroepBericht();
        if (voornamen != null) {
            samenNaam.setVoornamen(new Voornamen(voornamen));
        }
        if (naam != null) {
            samenNaam.setGeslachtsnaam(new Geslachtsnaam(naam));
        }
        if (voorvoegsel != null) {
            samenNaam.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        }
        if (scheidingsteken != null) {
            samenNaam.setScheidingsteken(new Scheidingsteken(scheidingsteken));
        }
        samenNaam.setAdellijkeTitel(adellijkeTitel);
        samenNaam.setPredikaat(predikaat);
        return samenNaam;
    }

    private PersoonAanschrijvingGroepBericht maakAanschrijvingBericht(final AdellijkeTitel adellijkeTitel,
        final Predikaat predikaat, final JaNee indAlgorithm, final JaNee indPredikaat,
        final WijzeGebruikGeslachtsnaam gebruik, final String voorvoegsel, final String scheidingsteken,
        final String naam)
    {
        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setAdellijkeTitelAanschrijving(adellijkeTitel);
        aanschrijving.setPredikaatAanschrijving(predikaat);
        aanschrijving.setIndicatieAanschrijvingAlgoritmischAfgeleid(indAlgorithm);
        aanschrijving.setIndicatieTitelsPredikatenBijAanschrijven(indPredikaat);
        aanschrijving.setNaamgebruik(gebruik);
        if (null != scheidingsteken) {
            aanschrijving.setScheidingstekenAanschrijving(new Scheidingsteken(scheidingsteken));
        }
        if (null != voorvoegsel) {
            aanschrijving.setVoorvoegselAanschrijving(new Voorvoegsel(voorvoegsel));
        }
        if (null != naam) {
            aanschrijving.setGeslachtsnaamAanschrijving(new Geslachtsnaam(naam));
        }
        return aanschrijving;
    }
}
