/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.bijhouding.business.excepties.BijhoudingExceptie;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik.NaamgebruikAfleiding;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.bijhouding.business.util.Geldigheidsperiode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class SamengesteldeNaamAfleidingTest {

    private final ActieModel actie1;
    private final ActieModel actie2;

    public SamengesteldeNaamAfleidingTest() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        actie1 = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GESLACHTSNAAM),
            null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, nu, null);
        actie2 = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_VOORNAAM),
            null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, nu, null);
    }

    @Test
    public void testLeidAf() {
        final PersoonHisVolledig persoon = maakBeginSituatiePersoonHisVolledig();

        //We veranderen niks aan de voornaam en geslachtsnaam en roepen de afleiding aan:
        final List<Afleidingsregel> resultaat = new SamengesteldeNaamAfleiding(persoon, actie1).leidAf().getVervolgAfleidingen();

        assertEquals(1, resultaat.size());
        assertTrue(resultaat.get(0) instanceof NaamgebruikAfleiding);
    }

    @Test
    public void testLeidAfActieRaaktVoornaamEnGeslachtsnaamNiet() {
        final PersoonHisVolledig persoon = maakBeginSituatiePersoonHisVolledig();

        //We veranderen niks aan de voornaam en geslachtsnaam en roepen de afleiding aan:
        new SamengesteldeNaamAfleiding(persoon, actie1).leidAf();

        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorie().getAantal());
        assertNotNull(persoon.getPersoonSamengesteldeNaamHistorie().getActueleRecord());
    }

    @Test
    //TODO
    public void testLeidAfActieRaaktVoornaam() {

    }

    @Test
    //TODO
    public void testLeidAfActieRaaktGeslachtsnaam() {

    }

    @Test
    //TODO
    public void testLeidAfActieRaaktVoornaamEnGeslachtsnaam() {

    }

    /**
     * Test of de samengestelde naam afleiding wel draait als er alleen maar een indicatie namenreeks op 'nee' wordt gezet, zonder in het bericht nieuwe
     * voornamen en/of geslachtsnaamcomponenten mee te geven. Dit wordt gesimuleert door een samengestelde naam record in de historie toe te voegen aan de
     * hand van een actie, waarbij alleen de indicatie namenreeks op 'nee' staat. De afleiding zou hieruit moeten concluderen dat er gedraaid moet worden.
     */
    @Test
    public void testLeidAfAlleenIndicatie() {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));
        final ActieModel actieModel = new ActieModel(actieBericht, null);

        final PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwSamengesteldeNaamRecord(20120101, 20130101, 20120101)
            .indicatieNamenreeks(true)
            .geslachtsnaamstam("Jansen")
            .eindeRecord()
                // Dit record is zojuist door het bericht toegevoegd, alleen indicatie op nee gezet.
            .nieuwSamengesteldeNaamRecord(actieModel)
            .indicatieNamenreeks(false)
            .eindeRecord()
            .build();
        persoonHisVolledig.getGeslachtsnaamcomponenten().add(
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20120101, null, 20120101)
                .stam("Pietersen")
                .eindeRecord()
                .build());

        new SamengesteldeNaamAfleiding(persoonHisVolledig, actieModel).leidAf();

        assertEquals(2, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie().size());
        final HisPersoonSamengesteldeNaamModel actueleRecord = persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getActueleRecord();
        assertEquals(20130101, actueleRecord.getDatumAanvangGeldigheid().getWaarde().intValue());
        assertEquals(null, actueleRecord.getDatumEindeGeldigheid());
        assertEquals(false, actueleRecord.getIndicatieNamenreeks().getWaarde());
        assertEquals(null, actueleRecord.getVoornamen());
        assertNotNull(actueleRecord.getGeslachtsnaamstam());
        assertEquals("Pietersen", actueleRecord.getGeslachtsnaamstam().getWaarde());
    }

    @Test
    public void testLeidAf2ActiesMetDezelfdeDagDegRakenVoornaam() {
        final PersoonHisVolledig persoonHisVolledig = maakBeginSituatiePersoonHisVolledig();
        final PersoonVoornaamHisVolledig voornaamHisVolledig = persoonHisVolledig.getVoornamen().iterator().next();

        //Actie 1 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam1 = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam1.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        nieuweVoornaam1.setNaam(new VoornaamAttribuut("NIEUW_ACTIE_1"));
        voornaamHisVolledig.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(voornaamHisVolledig, nieuweVoornaam1, nieuweVoornaam1, actie1)
        );

        //Actie 2 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam2 = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam2.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        nieuweVoornaam2.setNaam(new VoornaamAttribuut("NIEUW_ACTIE_2"));
        voornaamHisVolledig.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(voornaamHisVolledig, nieuweVoornaam2, nieuweVoornaam2, actie2)
        );

        //De afleiding gebeurt voor beide acties!
        //De afleidings regel afvuren voor actie 1
        new SamengesteldeNaamAfleiding(persoonHisVolledig, actie1).leidAf();
        //De afleidings regel afvuren voor actie 2
        new SamengesteldeNaamAfleiding(persoonHisVolledig, actie2).leidAf();

        //Verwachte resultaat:
        //Er zijn nu in totaal 3 historie records samengestelde naam, de actuele record heeft als voornaam
        //NIEUW_ACTIE_2, want actie 2 schrijft actie 1 over. Dit is normaal.
        assertEquals(3, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getAantal());
        assertEquals(
            3, persoonHisVolledig.getVoornamen().iterator().next().getPersoonVoornaamHistorie().getAantal());

        final HisPersoonSamengesteldeNaamModel actueleSamegesteldeNaamRecord =
            persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getActueleRecord();
        assertEquals("NIEUW_ACTIE_2", actueleSamegesteldeNaamRecord.getVoornamen().getWaarde());
    }

    @Test
    public void testLeidAf2ActiesMetVerschillendeDagDegRakenVoornaam() {
        final PersoonHisVolledig persoonHisVolledig = maakBeginSituatiePersoonHisVolledig();
        final PersoonVoornaamHisVolledig voornaamHisVolledig = persoonHisVolledig.getVoornamen().iterator().next();

        // De verwerking en afleiding gebeurt voor elke actie apart want de datum aanvang geldigheid van de acties is
        // verschillend!

        //Actie 1 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam1 = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam1.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        nieuweVoornaam1.setNaam(new VoornaamAttribuut("NIEUW_ACTIE_1"));
        voornaamHisVolledig.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(voornaamHisVolledig, nieuweVoornaam1, nieuweVoornaam1, actie1)
        );

        //Afleiding actie 1:
        //De afleidings regel afvuren voor actie 1
        new SamengesteldeNaamAfleiding(persoonHisVolledig, actie1).leidAf();

        //Actie 2 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam2 = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam2.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()));
        ReflectionTestUtils.setField(actie2, "datumAanvangGeldigheid", new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()));
        nieuweVoornaam2.setNaam(new VoornaamAttribuut("NIEUW_ACTIE_2"));
        voornaamHisVolledig.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(voornaamHisVolledig, nieuweVoornaam2, nieuweVoornaam2, actie2)
        );

        //De afleidingsregel afvuren voor actie 2
        new SamengesteldeNaamAfleiding(persoonHisVolledig, actie2).leidAf();

        //Verwachte resultaat:
        // Er zijn nu in totaal 4 historie records samengestelde naam en voornaam.
        // Er ontstaat weliswaar een tussensituatie, maar die
        // vervalt direct op tijdstipregistratie. Een record waar tijdstipregistratie en tijdstipverval gelijk is, is
        // onzin en niet relevant voor de historie.
        assertEquals(4, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getAantal());
        assertEquals(4, persoonHisVolledig.getVoornamen().iterator().next().getPersoonVoornaamHistorie().getAantal());

        final HisPersoonSamengesteldeNaamModel actueleSamengesteldeNaamRecord =
            persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getActueleRecord();

        // LET OP: Omdat Actie 2 morgen pas ingaat, datumaanvanggeldigheid = morgen, is de actuele samengestelde naam
        // voornaam 'NIEUW_ACTIE_1'
        assertEquals("NIEUW_ACTIE_1", actueleSamengesteldeNaamRecord.getVoornamen().getWaarde());
    }

    /**
     * __ _ _ _ _ _ _ _ _ _ _ ____________|__________G___________ ----> Scenario: Correctie voornaam Voornaam historie
     * |_______A________|_________B________ Geslnaam historie              |___X___|________Y__________|____Z__ 1-1-'12  30-6-'12  1-1-'13    1-5-'13
     * ___________________________________ Samengestelde naam historie    |__AX___|___AY___|____BY____|___BZ__ 1-1-'12  30-6-'12  1-1-'13    1-5-'13
     * <p/>
     * Het SCENARIO:    Per 30-9-2012 Was de voornaam G en NIET A. De samengestelde naam moet dus met terugwerkende krach her-afgeleid worden vanaf
     * 30-9-2012.
     * <p/>
     * Het Resultaat:                  _____________________________________ Samengestelde naam historie    |__AX___|_AY_|______GY______|______GZ_ 1-1-'12
     * 30-6-'12             1-5-'13 30-9-'12
     */
    @Test
    public void testTerugwerkendeKrachtAfleidingBijCorrectieVoornaam()
        throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        //We creeeren eerste de begin situatie voor de voornaam.
        final PersoonVoornaamHisVolledigImpl persoonVoornaam = new PersoonVoornaamHisVolledigImpl(
            persoon, new VolgnummerAttribuut(1));
        final Set<HisPersoonVoornaamModel> voornaamHistorie = new HashSet<>();
        ReflectionTestUtils.setField(persoonVoornaam, "hisPersoonVoornaamLijst", voornaamHistorie);
        final SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = new TreeSet<>(new VolgnummerComparator());
        voornamen.add(persoonVoornaam);
        persoon.setVoornamen(voornamen);

        //Record voornaam A:
        final HisPersoonVoornaamModel voornaamHisRecordA =
            maakHisPersoonVoornaamModel("A", new DatumEvtDeelsOnbekendAttribuut(20120101), new DatumEvtDeelsOnbekendAttribuut(20130101),
                DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1));
        voornaamHistorie.add(voornaamHisRecordA);

        //Record voornaam B:
        final HisPersoonVoornaamModel voornaamHisRecordB =
            maakHisPersoonVoornaamModel("B", new DatumEvtDeelsOnbekendAttribuut(20130101), null, DatumTijdAttribuut.bouwDatumTijd(2013, 1, 1));
        voornaamHistorie.add(voornaamHisRecordB);


        //Beginsituatie voor de geslachtsnaamcomponent:
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslnaamComponent =
            new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoon, new VolgnummerAttribuut(1));
        final Set<HisPersoonGeslachtsnaamcomponentModel> geslnaamcompHistorie = new HashSet<>();
        ReflectionTestUtils.setField(geslnaamComponent, "hisPersoonGeslachtsnaamcomponentLijst", geslnaamcompHistorie);
        final SortedSet<PersoonGeslachtsnaamcomponentHisVolledigImpl> geslachtsnaamComponenten = new TreeSet<>(new VolgnummerComparator());
        geslachtsnaamComponenten.add(geslnaamComponent);
        persoon.setGeslachtsnaamcomponenten(geslachtsnaamComponenten);


        //Record geslachtsnaam X:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordX =
            maakHisPersoonGeslachtsnaamcomponentModel("X", new DatumEvtDeelsOnbekendAttribuut(20120101), new DatumEvtDeelsOnbekendAttribuut(20120630),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        geslnaamcompHistorie.add(geslNaamHisRecordX);

        //Record geslachtsnaam Y:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordY =
            maakHisPersoonGeslachtsnaamcomponentModel("Y", new DatumEvtDeelsOnbekendAttribuut(20120630), new DatumEvtDeelsOnbekendAttribuut(20130501),
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        geslnaamcompHistorie.add(geslNaamHisRecordY);

        //Record geslachtsnaam Z:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordZ =
            maakHisPersoonGeslachtsnaamcomponentModel("Z", new DatumEvtDeelsOnbekendAttribuut(20130501), null,
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        geslnaamcompHistorie.add(geslNaamHisRecordZ);

        //Beginsituatie voor de samengestelde naam:
        final Set<HisPersoonSamengesteldeNaamModel> samengesteldeNaamHistorie =
            new HashSet<HisPersoonSamengesteldeNaamModel>();
        ReflectionTestUtils.setField(persoon, "hisPersoonSamengesteldeNaamLijst", samengesteldeNaamHistorie);

        //Record samengesteldenaam AX
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordAX =
            maakHisPersoonSamengesteldeNaamModel("A", "X", new DatumEvtDeelsOnbekendAttribuut(20120101), new DatumEvtDeelsOnbekendAttribuut(20120630),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordAX);

        //Record samengesteldenaam AY
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordAY =
            maakHisPersoonSamengesteldeNaamModel("A", "Y", new DatumEvtDeelsOnbekendAttribuut(20120630), new DatumEvtDeelsOnbekendAttribuut(20130101),
                DatumTijdAttribuut.bouwDatumTijd(2013, 1, 1));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordAY);

        //Record samengesteldenaam BY
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordBY =
            maakHisPersoonSamengesteldeNaamModel("B", "Y", new DatumEvtDeelsOnbekendAttribuut(20130101), new DatumEvtDeelsOnbekendAttribuut(20130501),
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordBY);

        //Record samengesteldenaam BZ
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordBZ =
            maakHisPersoonSamengesteldeNaamModel("B", "Z", new DatumEvtDeelsOnbekendAttribuut(20130501), null,
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordBZ);


        //Het scenario: Per 30-9-2012 Was de voornaam G en NIET A.
        //Actie 1 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120930));
        nieuweVoornaam.setNaam(new VoornaamAttribuut("G"));
        ReflectionTestUtils.setField(actie1, "datumAanvangGeldigheid", new DatumEvtDeelsOnbekendAttribuut(20120930));
        persoonVoornaam.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(persoonVoornaam, nieuweVoornaam, nieuweVoornaam, actie1)
        );

        //Controleer nieuwe voornaam historie, Er zijn nu 2 C laag records aanwezig.
        assertEquals(2, persoonVoornaam.getPersoonVoornaamHistorie().getNietVervallenHistorie().size());
        assertEquals("G", persoonVoornaam.getPersoonVoornaamHistorie().getActueleRecord().getNaam().getWaarde());

        //Nu gaan we de afleiding doen
        new SamengesteldeNaamAfleiding(persoon, actie1).leidAf();

        //Controleer de nieuwe samengestelde naam historie:
        //Er zijn nu nog steeds 4 c laag records samengestelde naam
        final Set<HisPersoonSamengesteldeNaamModel> nietVervallenHistorie =
            persoon.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie();
        assertEquals(4, nietVervallenHistorie.size());

        final List<Geldigheidsperiode> verwachteGeldigheidsperiodes = new ArrayList<>();
        //AX
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20120101), new DatumEvtDeelsOnbekendAttribuut(20120630)));
        //AY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20120630), new DatumEvtDeelsOnbekendAttribuut(20120930)));
        //GY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20120930), new DatumEvtDeelsOnbekendAttribuut(20130501)));
        //GZ
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20130501), null));

        for (final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamModel : nietVervallenHistorie) {
            if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20120101))) {
                assertEquals("A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("X", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20120630))) {
                assertEquals("A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20120930))) {
                assertEquals("G", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20130501))) {
                assertEquals("G", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Z", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else {
                Assert.fail("Wat is hier aan de hand?");
            }
            verwachteGeldigheidsperiodes.remove(
                new Geldigheidsperiode(hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid(),
                    hisPersoonSamengesteldeNaamModel.getDatumEindeGeldigheid()));
        }

        assertTrue(verwachteGeldigheidsperiodes.isEmpty());
    }

    /**
     * 1-7-'11 __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ____________________|_____________________O________________________ --> Scenario Voornaam 1
     * historie            |___K____________|______________L______________|____________M_______ Voornaam 2 historie
     * |___________A_____________|_______________B____________|______C_____ Geslnaam historie
     * |___X___|____________Y________________|________________Z____________ 1-1-'11  1-3-'11  1-6-'11    1-9-'11    1-1-'13  1-4-'13  1-6-'13
     * ___________________________________________________________________ Samengestelde naam historie    |__KAX__|___KAY__|___LAY__|___LBY_____|__LBZ___|__MBZ__|___MCZ______
     * 1-1-'11  1-3-'11  1-6-'11    1-9-'11    1-1-'13  1-4-'13  1-6-'13
     * <p/>
     * Het SCENARIO:    Per 1-7-2011 Was voornaam 1 O en NIET L, en het is nooit M geweest. De samengestelde naam moet dus met terugwerkende kracht
     * her-afgeleid worden vanaf 1-7-2011.
     * <p/>
     * Het Resultaat:                  ___________________________________________________________________ Samengestelde naam historie
     * |__KAX__|__KAY___|LAY|_OAY|___OBY_____|____OBZ_________|___OCZ______
     */
    @Test
    public void testTerugwerkendeKrachtAfleidingBijCorrectieVoornaamBijMeerdereVoornamen()
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        //We creeeren de begin situatie voor voornaam 1.
        final PersoonVoornaamHisVolledigImpl persoonVoornaam =
            new PersoonVoornaamHisVolledigImpl(persoon, new VolgnummerAttribuut(1));
        final Set<HisPersoonVoornaamModel> voornaamHistorie = new HashSet<>();
        ReflectionTestUtils.setField(persoonVoornaam, "hisPersoonVoornaamLijst", voornaamHistorie);
        final SortedSet<PersoonVoornaamHisVolledigImpl> voornamen =
            new TreeSet<PersoonVoornaamHisVolledigImpl>(new VolgnummerComparator());
        voornamen.add(persoonVoornaam);
        persoon.setVoornamen(voornamen);

        //Record voornaam 1 K:
        final HisPersoonVoornaamModel voornaamHisRecordK =
            maakHisPersoonVoornaamModel("K", new DatumEvtDeelsOnbekendAttribuut(20110101), new DatumEvtDeelsOnbekendAttribuut(20110601),
                DatumTijdAttribuut.bouwDatumTijd(2011, 6, 1));
        voornaamHistorie.add(voornaamHisRecordK);

        //Record voornaam 1 L:
        final HisPersoonVoornaamModel voornaamHisRecordL =
            maakHisPersoonVoornaamModel("L", new DatumEvtDeelsOnbekendAttribuut(20110601), new DatumEvtDeelsOnbekendAttribuut(20130401),
                DatumTijdAttribuut.bouwDatumTijd(2013, 4, 1));
        voornaamHistorie.add(voornaamHisRecordL);

        //Record voornaam 1 M:
        final HisPersoonVoornaamModel voornaamHisRecordM =
            maakHisPersoonVoornaamModel("M", new DatumEvtDeelsOnbekendAttribuut(20130401), null, DatumTijdAttribuut.bouwDatumTijd(2013, 4, 1));
        voornaamHistorie.add(voornaamHisRecordM);

        //We creeeren de begin situatie voor voornaam 2.
        final PersoonVoornaamHisVolledigImpl persoonVoornaam2 =
            new PersoonVoornaamHisVolledigImpl(persoon, new VolgnummerAttribuut(2));
        final Set<HisPersoonVoornaamModel> voornaamHistorie2 = new HashSet<>();
        ReflectionTestUtils.setField(persoonVoornaam2, "hisPersoonVoornaamLijst", voornaamHistorie2);
        voornamen.add(persoonVoornaam2);

        //Record voornaam 2 A:
        final HisPersoonVoornaamModel voornaam2HisRecordA =
            maakHisPersoonVoornaamModel("A", new DatumEvtDeelsOnbekendAttribuut(20110101), new DatumEvtDeelsOnbekendAttribuut(20110901),
                DatumTijdAttribuut.bouwDatumTijd(2011, 6, 1));
        voornaamHistorie2.add(voornaam2HisRecordA);

        //Record voornaam 2 B:
        final HisPersoonVoornaamModel voornaam2HisRecordB =
            maakHisPersoonVoornaamModel("B", new DatumEvtDeelsOnbekendAttribuut(20110901), new DatumEvtDeelsOnbekendAttribuut(20130601),
                DatumTijdAttribuut.bouwDatumTijd(2013, 4, 1));
        voornaamHistorie2.add(voornaam2HisRecordB);

        //Record voornaam 2 C:
        final HisPersoonVoornaamModel voornaam2HisRecordC =
            maakHisPersoonVoornaamModel("C", new DatumEvtDeelsOnbekendAttribuut(20130601), null, DatumTijdAttribuut.bouwDatumTijd(2013, 4, 1));
        voornaamHistorie2.add(voornaam2HisRecordC);

        //Beginsituatie voor de geslachtsnaamcomponent:
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslnaamComponent =
            new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoon, new VolgnummerAttribuut(1));
        final Set<HisPersoonGeslachtsnaamcomponentModel> geslnaamcompHistorie =
            new HashSet<HisPersoonGeslachtsnaamcomponentModel>();
        ReflectionTestUtils.setField(geslnaamComponent, "hisPersoonGeslachtsnaamcomponentLijst", geslnaamcompHistorie);
        final SortedSet<PersoonGeslachtsnaamcomponentHisVolledigImpl> geslachtsnaamComponenten =
            new TreeSet<PersoonGeslachtsnaamcomponentHisVolledigImpl>(new VolgnummerComparator());
        geslachtsnaamComponenten.add(geslnaamComponent);
        persoon.setGeslachtsnaamcomponenten(geslachtsnaamComponenten);

        //Record geslachtsnaam X:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordX =
            maakHisPersoonGeslachtsnaamcomponentModel("X", new DatumEvtDeelsOnbekendAttribuut(20110101), new DatumEvtDeelsOnbekendAttribuut(20110301),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        geslnaamcompHistorie.add(geslNaamHisRecordX);

        //Record geslachtsnaam Y:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordY =
            maakHisPersoonGeslachtsnaamcomponentModel("Y", new DatumEvtDeelsOnbekendAttribuut(20110301), new DatumEvtDeelsOnbekendAttribuut(20130101),
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        geslnaamcompHistorie.add(geslNaamHisRecordY);

        //Record geslachtsnaam Z:
        final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecordZ =
            maakHisPersoonGeslachtsnaamcomponentModel("Z", new DatumEvtDeelsOnbekendAttribuut(20130101), null,
                DatumTijdAttribuut.bouwDatumTijd(2013, 5, 1));
        geslnaamcompHistorie.add(geslNaamHisRecordZ);

        //Beginsituatie voor de samengestelde naam:
        final Set<HisPersoonSamengesteldeNaamModel> samengesteldeNaamHistorie =
            new HashSet<HisPersoonSamengesteldeNaamModel>();
        ReflectionTestUtils.setField(persoon, "hisPersoonSamengesteldeNaamLijst", samengesteldeNaamHistorie);

        final Constructor<HisPersoonSamengesteldeNaamModel> samengesteldeNaamHisConstructor =
            HisPersoonSamengesteldeNaamModel.class.getDeclaredConstructor();
        samengesteldeNaamHisConstructor.setAccessible(true);

        //Record samengesteldenaam KAX
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordKAX =
            maakHisPersoonSamengesteldeNaamModel("K A", "X", new DatumEvtDeelsOnbekendAttribuut(20110101), new DatumEvtDeelsOnbekendAttribuut(20110301),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordKAX);

        //Record samengesteldenaam KAY
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordKAY =
            maakHisPersoonSamengesteldeNaamModel("K A", "Y", new DatumEvtDeelsOnbekendAttribuut(20110301), new DatumEvtDeelsOnbekendAttribuut(20110601),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordKAY);

        //Record samengesteldenaam LAY
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordLAY =
            maakHisPersoonSamengesteldeNaamModel("L A", "Y", new DatumEvtDeelsOnbekendAttribuut(20110601), new DatumEvtDeelsOnbekendAttribuut(20110901),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordLAY);

        //Record samengesteldenaam LBY
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordLBY =
            maakHisPersoonSamengesteldeNaamModel("L B", "Y", new DatumEvtDeelsOnbekendAttribuut(20110901), new DatumEvtDeelsOnbekendAttribuut(20130101),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordLBY);

        //Record samengesteldenaam LBZ
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordLBZ =
            maakHisPersoonSamengesteldeNaamModel("L B", "Z", new DatumEvtDeelsOnbekendAttribuut(20130101), new DatumEvtDeelsOnbekendAttribuut(20130401),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordLBZ);

        //Record samengesteldenaam MBZ
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordMBZ =
            maakHisPersoonSamengesteldeNaamModel("M B", "Z", new DatumEvtDeelsOnbekendAttribuut(20130401), new DatumEvtDeelsOnbekendAttribuut(20130601),
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordMBZ);

        //Record samengesteldenaam MCZ
        final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecordMCZ =
            maakHisPersoonSamengesteldeNaamModel("M C", "Z", new DatumEvtDeelsOnbekendAttribuut(20130601), null,
                DatumTijdAttribuut.bouwDatumTijd(2012, 6, 30));
        samengesteldeNaamHistorie.add(hisPersoonSamengesteldeNaamRecordMCZ);

        //Het scenario: Per 1-7-2011 Was voornaam 1 O en NIET L, en het is nooit M geweest.
        //Actie 1 past de voornaam aan:
        final PersoonVoornaamStandaardGroepBericht nieuweVoornaam = new PersoonVoornaamStandaardGroepBericht();
        nieuweVoornaam.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110701));
        nieuweVoornaam.setNaam(new VoornaamAttribuut("O"));
        ReflectionTestUtils.setField(actie1, "datumAanvangGeldigheid", new DatumEvtDeelsOnbekendAttribuut(20110701));
        persoonVoornaam.getPersoonVoornaamHistorie().voegToe(
            new HisPersoonVoornaamModel(persoonVoornaam, nieuweVoornaam, nieuweVoornaam, actie1)
        );

        //Nu gaan we de afleiding doen
        new SamengesteldeNaamAfleiding(persoon, actie1).leidAf();

        //Controleer de nieuwe samengestelde naam historie:
        //Er zijn nu nog steeds 4 c laag records samengestelde naam
        final Set<HisPersoonSamengesteldeNaamModel> nietVervallenHistorie =
            persoon.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie();
        assertEquals(7, nietVervallenHistorie.size());

        final List<Geldigheidsperiode> verwachteGeldigheidsperiodes = new ArrayList<>();
        //KAX
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20110101), new DatumEvtDeelsOnbekendAttribuut(20110301)));
        //KAY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20110301), new DatumEvtDeelsOnbekendAttribuut(20110601)));
        //LAY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20110601), new DatumEvtDeelsOnbekendAttribuut(20110701)));
        //OAY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20110701), new DatumEvtDeelsOnbekendAttribuut(20110901)));
        //OBY
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20110901), new DatumEvtDeelsOnbekendAttribuut(20130101)));
        //OBZ
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20130101), new DatumEvtDeelsOnbekendAttribuut(20130601)));
        //OCZ
        verwachteGeldigheidsperiodes.add(new Geldigheidsperiode(new DatumEvtDeelsOnbekendAttribuut(20130601), null));

        for (final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamModel : nietVervallenHistorie) {
            if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20110101))) {
                assertEquals("K A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("X", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20110301))) {
                assertEquals("K A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20110601))) {
                assertEquals("L A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20110701))) {
                assertEquals("O A", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20110901))) {
                assertEquals("O B", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Y", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20130101))) {
                assertEquals("O B", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Z", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else if (hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid().equals(new DatumEvtDeelsOnbekendAttribuut(20130601))) {
                assertEquals("O C", hisPersoonSamengesteldeNaamModel.getVoornamen().getWaarde());
                assertEquals("Z", hisPersoonSamengesteldeNaamModel.getGeslachtsnaamstam().getWaarde());
            } else {
                Assert.fail("Wat is hier aan de hand?");
            }
            verwachteGeldigheidsperiodes.remove(
                new Geldigheidsperiode(hisPersoonSamengesteldeNaamModel.getDatumAanvangGeldigheid(),
                    hisPersoonSamengesteldeNaamModel.getDatumEindeGeldigheid()));
        }
        assertTrue(verwachteGeldigheidsperiodes.isEmpty());
    }

    @Test
    public void testLeidAfBeeindigdeVoornaam() {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19000101));
        final ActieModel actieModel = new ActieModel(actieBericht, null);

        final PersoonHisVolledig persoonHisVolledig = maakBeginSituatiePersoonHisVolledig();
        // Beeindig alle voornamen per heel lang geleden (effectief: geen geldige voornaam historie meer).
        for (final PersoonVoornaamHisVolledig voornaam : persoonHisVolledig.getVoornamen()) {
            voornaam.getPersoonVoornaamHistorie().beeindig(actieModel, actieModel);
        }

        new SamengesteldeNaamAfleiding(persoonHisVolledig, actieModel).leidAf();

        // Er is nog maar 1 actueel record over.
        assertEquals(1, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie().size());
        // En de voornamen is null.
        assertEquals(null, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie().iterator().next().getVoornamen());
    }

    @Test
    public void testLeidAfEenActueleEenBeeindigdeVoornaam() {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19000101));
        final ActieModel actieModel = new ActieModel(actieBericht, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakBeginSituatiePersoonHisVolledig();
        final String actueleVoornaam = "Pietje";
        voegVoornaamToeMetHistorie(persoonHisVolledig, actueleVoornaam);
        voegVoornaamToeMetHistorie(persoonHisVolledig, "VervallenNaam");
        // Beëindig de niet actuele voornamen per heel lang geleden.
        for (final PersoonVoornaamHisVolledig voornaam : persoonHisVolledig.getVoornamen()) {
            if (!voornaam.getPersoonVoornaamHistorie().iterator().next().getNaam().getWaarde().equals(actueleVoornaam)) {
                voornaam.getPersoonVoornaamHistorie().beeindig(actieModel, actieModel);
            }
        }

        new SamengesteldeNaamAfleiding(persoonHisVolledig, actieModel).leidAf();

        assertEquals(1, persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie().size());
        // Alleen de actuele voornaam staat in 'voornamen'.
        assertEquals(actueleVoornaam,
            persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getNietVervallenHistorie().iterator().next().getVoornamen().getWaarde());
    }

    // TODO: (meer) tests die rekening houden met beëindiging!


    private MaterieleHistorieImpl nieuwMaterieleHistorie(final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final DatumTijdAttribuut datumTijdReg)
    {
        final MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();
        materieleHistorie.setDatumAanvangGeldigheid(datumAanvang);
        materieleHistorie.setDatumEindeGeldigheid(datumEinde);
        materieleHistorie.setDatumTijdRegistratie(datumTijdReg);
        return materieleHistorie;
    }

    private HisPersoonVoornaamModel maakHisPersoonVoornaamModel(final String voorNaam, final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final DatumTijdAttribuut datumTijdReg)
    {
        //Voor het gemak maken we historie records aan via Reflection.
        final Constructor<HisPersoonVoornaamModel> voornaamHisConstructor;
        try {
            voornaamHisConstructor = HisPersoonVoornaamModel.class.getDeclaredConstructor();
            voornaamHisConstructor.setAccessible(true);

            final HisPersoonVoornaamModel voornaamHisRecord =
                voornaamHisConstructor.newInstance();
            ReflectionTestUtils.setField(voornaamHisRecord, "naam", new VoornaamAttribuut(voorNaam));
            ReflectionTestUtils.setField(voornaamHisRecord, "materieleHistorie",
                nieuwMaterieleHistorie(datumAanvang,
                    datumEinde,
                    datumTijdReg));
            return voornaamHisRecord;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e)
        {
            throw new BijhoudingExceptie(e);
        }
    }

    private HisPersoonGeslachtsnaamcomponentModel maakHisPersoonGeslachtsnaamcomponentModel(final String geslNaam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final DatumTijdAttribuut datumTijdReg)
    {
        try {
            final Constructor<HisPersoonGeslachtsnaamcomponentModel> geslachtsnaamHisConstructor =
                HisPersoonGeslachtsnaamcomponentModel.class.getDeclaredConstructor();
            geslachtsnaamHisConstructor.setAccessible(true);

            final HisPersoonGeslachtsnaamcomponentModel geslNaamHisRecord = geslachtsnaamHisConstructor.newInstance();
            ReflectionTestUtils.setField(geslNaamHisRecord, "stam", new GeslachtsnaamstamAttribuut(geslNaam));
            ReflectionTestUtils.setField(geslNaamHisRecord, "materieleHistorie",
                nieuwMaterieleHistorie(datumAanvang, datumEinde,
                    datumTijdReg));
            return geslNaamHisRecord;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e)
        {
            throw new BijhoudingExceptie(e);
        }
    }

    private HisPersoonSamengesteldeNaamModel maakHisPersoonSamengesteldeNaamModel(final String voorNaam, final String geslNaam,
                                                                                  final DatumEvtDeelsOnbekendAttribuut datumAanvang,
                                                                                  final DatumEvtDeelsOnbekendAttribuut datumEinde,
                                                                                  final DatumTijdAttribuut datumTijdReg)
    {
        try {

            final Constructor<HisPersoonSamengesteldeNaamModel> samengesteldeNaamHisConstructor =
                HisPersoonSamengesteldeNaamModel.class.getDeclaredConstructor();
            samengesteldeNaamHisConstructor.setAccessible(true);

            final HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaamRecord =
                samengesteldeNaamHisConstructor.newInstance();
            ReflectionTestUtils.setField(hisPersoonSamengesteldeNaamRecord, "voornamen", new VoornamenAttribuut(voorNaam));
            ReflectionTestUtils
                .setField(hisPersoonSamengesteldeNaamRecord, "geslachtsnaamstam", new GeslachtsnaamstamAttribuut(geslNaam));
            ReflectionTestUtils.setField(hisPersoonSamengesteldeNaamRecord, "materieleHistorie",
                nieuwMaterieleHistorie(datumAanvang, datumEinde, datumTijdReg));
            return hisPersoonSamengesteldeNaamRecord;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e)
        {
            throw new BijhoudingExceptie(e);
        }
    }

    /**
     * Creeer een begin situatie waarbij de persoon een voornaam heeft met historie en een geslachtsnaamcomponent met historie. En een samengestelde naam
     * met historie.
     *
     * @return persoon
     */
    private PersoonHisVolledigImpl maakBeginSituatiePersoonHisVolledig() {

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final String voornaam = "HIS_RECORD_VOORNAAM_1";
        final String geslachtsnaam = "HIS_RECORD_GESLACHTSNAAM_1";

        voegGeslachtsnaamComponentToeMetHistorie(persoon, geslachtsnaam);

        voegVoornaamToeMetHistorie(persoon, voornaam);

        voegSamengesteldeNaamToeMetHistorie(persoon, voornaam, geslachtsnaam);

        return persoon;
    }

    private void voegSamengesteldeNaamToeMetHistorie(final PersoonHisVolledigImpl persoon, final String voornaam, final String achternaam) {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGroep = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGroep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));
        samengesteldeNaamGroep.setIndicatieAfgeleid(JaNeeAttribuut.JA);
        samengesteldeNaamGroep.setVoornamen(new VoornamenAttribuut(voornaam));
        samengesteldeNaamGroep.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut(achternaam));
        persoon.getPersoonSamengesteldeNaamHistorie().voegToe(
            new HisPersoonSamengesteldeNaamModel(persoon, samengesteldeNaamGroep, samengesteldeNaamGroep,
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                    new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                    DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null))
        );
    }

    private void voegVoornaamToeMetHistorie(final PersoonHisVolledigImpl persoon, final String voornaam) {
        final int volgnummer = persoon.getVoornamen().size() + 1;
        final PersoonVoornaamHisVolledigImpl persoonVoornaam = new PersoonVoornaamHisVolledigImpl(persoon, new VolgnummerAttribuut(volgnummer));
        persoon.getVoornamen().add(persoonVoornaam);

        final PersoonVoornaamStandaardGroepBericht voornaamStandaardGroepBericht = new PersoonVoornaamStandaardGroepBericht();
        voornaamStandaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));
        voornaamStandaardGroepBericht.setDatumTijdRegistratie(DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1));
        voornaamStandaardGroepBericht.setNaam(new VoornaamAttribuut(voornaam));
        persoonVoornaam.getPersoonVoornaamHistorie().voegToe(new HisPersoonVoornaamModel(persoonVoornaam, voornaamStandaardGroepBericht,
            voornaamStandaardGroepBericht, new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
            DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null)));
    }

    private void voegGeslachtsnaamComponentToeMetHistorie(final PersoonHisVolledigImpl persoon, final String geslachtsnaam) {
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslnaamComponent =
            new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoon, new VolgnummerAttribuut(1));
        persoon.getGeslachtsnaamcomponenten().add(geslnaamComponent);

        final PersoonGeslachtsnaamcomponentStandaardGroepBericht geslachtsnaamcomponentBericht = new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        geslachtsnaamcomponentBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));
        geslachtsnaamcomponentBericht.setDatumTijdRegistratie(DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1));
        geslachtsnaamcomponentBericht.setStam(new GeslachtsnaamstamAttribuut(geslachtsnaam));
        geslnaamComponent.getPersoonGeslachtsnaamcomponentHistorie().voegToe(
            new HisPersoonGeslachtsnaamcomponentModel(geslnaamComponent, geslachtsnaamcomponentBericht,
                geslachtsnaamcomponentBericht,
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null,
                    null, new DatumEvtDeelsOnbekendAttribuut(20120101),
                    null, DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null)));
    }
}
