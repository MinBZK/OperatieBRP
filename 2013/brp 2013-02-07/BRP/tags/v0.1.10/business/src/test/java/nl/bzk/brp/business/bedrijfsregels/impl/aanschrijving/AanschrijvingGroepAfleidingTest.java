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
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
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
    private Predikaat hoogheid;

    private PersoonModel partner;
    private PersoonModel hoofdPersoon;

    private static final Datum AANVANG_DATUM = new Datum(20150205);
    private static final String VOORNAAM_EIGEN = "Jan-Pieter-Eigen";
    private static final String VOORNAAM_PARTNER = "May-June-Partner";
    private static final String ACHTERNAAM_EIGEN = "EigenSamen";
    private static final String ACHTERNAAM_PARTNER = "PartnerSamen";

    private static final String VOORVOEG_EIGEN = "vdSamen";
    private static final String VOORVOEG_PARTNER = "vdPs";
    private static final String SCHEIDING_EIGEN = "m";
    private static final String SCHEIDING_PARTNER = "v";

    @Before
    public void init() {
        aanschrijvingGroepAfleiding = new AanschrijvingGroepAfleiding();
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(aanschrijvingGroepAfleiding, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(aanschrijvingGroepAfleiding, "relatieRepository", relatieRepository);
        baron = new AdellijkeTitel();
        baron.setAdellijkeTitelCode(new AdellijkeTitelCode("B"));
        hoogheid = new Predikaat();
        hoogheid.setCode(new PredikaatCode("H"));
        PersoonBericht p = maakStandaardPersoon();
        p.setAanschrijving(maakAanschrijvingBericht(
                baron, hoogheid, JaNee.Nee, JaNee.Nee, WijzeGebruikGeslachtsnaam.DUMMY,
                "vdP", "k", "Partners"));
        p.setSamengesteldeNaam(maakSamengesteldeNaam(VOORNAAM_PARTNER, ACHTERNAAM_PARTNER, VOORVOEG_PARTNER, null,
                hoogheid, SCHEIDING_PARTNER));
        partner = new PersoonModel(p);
        partner.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(
                maakPersoonGeslachtsnaamcomponent("Partner", "del", baron, hoogheid, "-"), partner));

        p = maakStandaardPersoon();
        p.setAanschrijving(maakAanschrijvingBericht(
                baron, hoogheid, JaNee.Ja, JaNee.Ja, WijzeGebruikGeslachtsnaam.EIGEN,
                "vdE", "e", "Eigens"));
        p.setSamengesteldeNaam(maakSamengesteldeNaam(VOORNAAM_EIGEN, ACHTERNAAM_EIGEN, VOORVOEG_EIGEN, null,
                hoogheid, SCHEIDING_EIGEN));
        hoofdPersoon = new PersoonModel(p);
        hoofdPersoon.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(
                maakPersoonGeslachtsnaamcomponent("Eigen", "mar", null, null, "+"), hoofdPersoon));
    }

    @Test
    public void testIndicatieE() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Ja, JaNee.Nee,
            WijzeGebruikGeslachtsnaam.EIGEN, null, null, null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN, persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitel());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaat());
    }

    @Test
    public void testIndicatieEMetPredikaat() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Ja, JaNee.Ja,
            WijzeGebruikGeslachtsnaam.EIGEN, null, null, null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN, persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitel());
        Assert.assertEquals(hoogheid, persoon.getAanschrijving().getPredikaat());
    }

    @Test
    public void testIndicatieP() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Ja, JaNee.Nee,
            WijzeGebruikGeslachtsnaam.PARTNER, null, null, null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_PARTNER, persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals(SCHEIDING_PARTNER, persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals(VOORVOEG_PARTNER, persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaat());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitel());
    }

    @Test
    public void testIndicatieV() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Ja, JaNee.Ja,
            WijzeGebruikGeslachtsnaam.PARTNER_EIGEN, null, null, null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_PARTNER + "-" + VOORVOEG_EIGEN + SCHEIDING_EIGEN + ACHTERNAAM_EIGEN,
                persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals(SCHEIDING_PARTNER, persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals(VOORVOEG_PARTNER, persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(hoogheid, persoon.getAanschrijving().getPredikaat());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitel());
    }

    @Test
    public void testIndicatieN() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Ja, JaNee.Nee,
            WijzeGebruikGeslachtsnaam.EIGEN_PARTNER, null, null, null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(ACHTERNAAM_EIGEN + "-" + VOORVOEG_PARTNER + SCHEIDING_PARTNER + ACHTERNAAM_PARTNER,
                persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(VOORNAAM_EIGEN, persoon.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals(SCHEIDING_EIGEN, persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals(VOORVOEG_EIGEN, persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaat());
        Assert.assertEquals(null, persoon.getAanschrijving().getAdellijkeTitel());
    }


    @Test
    public void testVolledigOpgegeven() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Nee, JaNee.Nee, null,
                "voorv", "s", "achternaam"));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals("achternaam", persoon.getAanschrijving().getGeslachtsnaam().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getVoornamen());
        Assert.assertEquals("s", persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals("voorv", persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaat());
        Assert.assertEquals(baron, persoon.getAanschrijving().getAdellijkeTitel());
    }

    @Test
    public void testVolledigOpgegevenZonderNaam() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Nee, JaNee.Nee, null,
                "voorv", "s", null));

        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        Assert.assertEquals(true, meldingen.isEmpty());

        Assert.assertEquals(null, persoon.getAanschrijving().getGeslachtsnaam());
        Assert.assertEquals(null, persoon.getAanschrijving().getVoornamen());
        Assert.assertEquals("s", persoon.getAanschrijving().getScheidingsteken().getWaarde());
        Assert.assertEquals("voorv", persoon.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoon.getAanschrijving().getPredikaat());
        Assert.assertEquals(baron, persoon.getAanschrijving().getAdellijkeTitel());
    }

    @Test
    public void testVolledigOpgegevenZonderNaamPersistentZonderSamengesteldeNaam() {
        Mockito.when(relatieRepository.haalopPartners(Matchers.anyInt(), Matchers.any(Datum.class)))
            .thenReturn(Arrays.asList(new Integer(1)));
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.anyInt())).thenReturn(partner);
        PersoonBericht persoon = maakStandaardPersoon();

        persoon.setAanschrijving(maakAanschrijvingBericht(baron, null, JaNee.Nee, JaNee.Nee, null,
                "voorv", "s", null));

        PersoonSamengesteldeNaamGroepModel samenNaam = hoofdPersoon.getSamengesteldeNaam();
        ReflectionTestUtils.setField(hoofdPersoon, "samengesteldeNaam", null);
        Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.WIJZIGING_NAAMGEBRUIK)
                                         .setDatumAanvang(AANVANG_DATUM).getActie();
        List<Melding> meldingen = aanschrijvingGroepAfleiding.executeer(hoofdPersoon, persoon, actie);
        ReflectionTestUtils.setField(hoofdPersoon, "samengesteldeNaam", samenNaam);
        // wordt nu niet meer gecopierd it samengesteldenaam. =>> geen error (pas bij validatie fout),
        // geslachtsnaam blijft null value.
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
        Assert.assertEquals(null, persoon.getAanschrijving().getGeslachtsnaam());
    }

    private PersoonBericht maakStandaardPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoonBericht.setPersoonVoornaam(new ArrayList<PersoonVoornaamBericht>());
        persoonBericht.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        return persoonBericht;
    }

    private PersoonGeslachtsnaamcomponentBericht maakPersoonGeslachtsnaamcomponent(final String naam,
                                                                            final String voorvoegsel,
                                                                            final AdellijkeTitel adellijkeTitel,
                                                                            final Predikaat predikaat,
                                                                            final String scheidingsteken)
    {
        final PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht
            standaardGroepBericht = new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepBericht.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent(naam));
        standaardGroepBericht.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        standaardGroepBericht.setAdellijkeTitel(adellijkeTitel);
        standaardGroepBericht.setPredikaat(predikaat);
        standaardGroepBericht.setScheidingsteken(new Scheidingsteken(scheidingsteken));
        comp.setGegevens(standaardGroepBericht);
        return comp;
    }

//    private PersoonVoornaamBericht maakPersoonVoorNaam(final String naam, final Integer volgnummer) {
//        final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
//        PersoonVoornaamStandaardGroepBericht standaardGroepBericht = new PersoonVoornaamStandaardGroepBericht();
//        standaardGroepBericht.setVoornaam(new Voornaam(naam));
//        voornaam.setGegevens(standaardGroepBericht);
//        voornaam.setVolgnummer(new Volgnummer(volgnummer));
//        return voornaam;
//    }

    private PersoonSamengesteldeNaamGroepBericht maakSamengesteldeNaam(
            final String voornamen,
            final String naam,
            final String voorvoegsel,
            final AdellijkeTitel adellijkeTitel,
            final Predikaat predikaat,
            final String scheidingsteken)
    {
        PersoonSamengesteldeNaamGroepBericht samenNaam = new PersoonSamengesteldeNaamGroepBericht();
        if (voornamen != null) {
            samenNaam.setVoornamen(new Voornaam(voornamen));
        }
        if (naam != null) {
            samenNaam.setGeslachtsnaam(new Geslachtsnaamcomponent(naam));
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

    private PersoonAanschrijvingGroepBericht maakAanschrijvingBericht(
            final AdellijkeTitel adellijkeTitel, final Predikaat predikaat,
            final JaNee indAlgorithm, final JaNee indPredikaat, final WijzeGebruikGeslachtsnaam gebruik,
            final String voorvoegsel, final String scheidingsteken, final String naam)
    {
        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setAdellijkeTitel(adellijkeTitel);
        aanschrijving.setPredikaat(predikaat);
        aanschrijving.setIndAanschrijvingAlgorthmischAfgeleid(indAlgorithm);
        aanschrijving.setIndAanschrijvenMetAdellijkeTitel(indPredikaat);
        aanschrijving.setGebruikGeslachtsnaam(gebruik);
        if (null != scheidingsteken) {
            aanschrijving.setScheidingsteken(new Scheidingsteken(scheidingsteken));
        }
        if (null != voorvoegsel) {
            aanschrijving.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        }
        if (null != naam) {
            aanschrijving.setGeslachtsnaam(new Geslachtsnaamcomponent(naam));
        }
        return aanschrijving;
    }
}
