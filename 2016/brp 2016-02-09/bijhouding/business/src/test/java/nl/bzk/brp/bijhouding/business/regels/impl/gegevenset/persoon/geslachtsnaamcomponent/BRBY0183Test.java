/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0183Test {

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0183, new BRBY0183().getRegel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVreemdRootobject() {
        new BRBY0183().voerRegelUit(Mockito.mock(ModelRootObject.class), null, null, null);
    }

    @Test
    public void testHuidigeSituatieNullNieuweSituatiePersoon() {
        final List<BerichtEntiteit> berichtEntiteiten =
            new BRBY0183().voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetWijzigingGeslachtsnaam() {
        final PersoonBericht persoonBericht = maakPersoon("van", "-", "Brom", null, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        //Geef de persoon een ouderschap over een kind.
        final OuderHisVolledigImpl ouderBetr = new OuderHisVolledigImplBuilder(null, persoonHisVolledig)
            .nieuwOuderschapRecord(20120101, null, 20120101)
            .eindeRecord()
            .build();
        persoonHisVolledig.getBetrokkenheden().add(ouderBetr);


        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testPersoonMetWijzigingGeslachtsnaamMaarNietIngeschrevene() {
        final PersoonBericht persoonBericht = maakPersoon("van", "-", "Brom", null, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.NIET_INGESCHREVENE, null);

        //Geef de persoon een ouderschap over een kind.
        final OuderHisVolledigImpl ouderBetr = new OuderHisVolledigImplBuilder(null, persoonHisVolledig)
            .nieuwOuderschapRecord(20120101, null, 20120101)
            .eindeRecord()
            .build();
        persoonHisVolledig.getBetrokkenheden().add(ouderBetr);


        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonMetWijzigingGeslachtsnaamMaarGeenOuderschapOverKind() {
        final PersoonBericht persoonBericht = maakPersoon("van", "-", "Brom", null, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonMetWijzigingGeslachtsnaamAlleenTitulatuurWijzigingMetKindMetDezelfdeNaam() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20110101), null, new DatumTijdAttribuut(new Date()), null);
        final PersoonBericht persoonBericht = maakPersoon(null, null, null,
            StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        final PersoonHisVolledigImpl kindHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        //Geef het kind dezelfde geslachtsnaam
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(persoonHisVolledig, null, kindHisVolledig, actieModel);

        //Geef het kind dezelfde geslachtsnaam als de ouder
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompOuder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        persoonHisVolledig.getGeslachtsnaamcomponenten().add(geslCompOuder);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompKind =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(kindHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        kindHisVolledig.getGeslachtsnaamcomponenten().add(geslCompKind);


        //Geslachtsaanduiding man.
        final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(
            new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));
        geslachtsaanduidingGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
            new HisPersoonGeslachtsaanduidingModel(
                persoonHisVolledig, geslachtsaanduidingGroepBericht, geslachtsaanduidingGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null));
        persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);


        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testPersoonMetWijzigingGeslachtsnaamAlleenTitulatuurWijzigingMetKindMetAndereNaam() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonBericht persoonBericht = maakPersoon(null, null, null,
            StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        final PersoonHisVolledigImpl kindHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        //Geef het kind dezelfde geslachtsnaam
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(persoonHisVolledig, null, kindHisVolledig, actieModel);

        //Geef het kind dezelfde geslachtsnaam als de ouder
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompOuder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        persoonHisVolledig.getGeslachtsnaamcomponenten().add(geslCompOuder);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompKind =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(kindHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .stam("Brom")
                .eindeRecord()
                .build();
        kindHisVolledig.getGeslachtsnaamcomponenten().add(geslCompKind);


        //Geslachtsaanduiding man.
        final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(
            new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));
        geslachtsaanduidingGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
            new HisPersoonGeslachtsaanduidingModel(
                persoonHisVolledig, geslachtsaanduidingGroepBericht, geslachtsaanduidingGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null));
        persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);


        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void
    testPersoonMetWijzigingGeslachtsnaamAlleenTitulatuurWijzigingMetKindMetDezelfdeNaamMaarPersoonIsVrouw() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonBericht persoonBericht = maakPersoon(null, null, null,
            StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null);

        final PersoonHisVolledigImpl persoonHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        final PersoonHisVolledigImpl kindHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        //Geef het kind dezelfde geslachtsnaam
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(persoonHisVolledig, null, kindHisVolledig, actieModel);

        //Geef het kind dezelfde geslachtsnaam als de ouder
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompOuder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        persoonHisVolledig.getGeslachtsnaamcomponenten().add(geslCompOuder);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompKind =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(kindHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        kindHisVolledig.getGeslachtsnaamcomponenten().add(geslCompKind);


        //Geslachtsaanduiding vrouw.
        final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(
            new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.VROUW));
        geslachtsaanduidingGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
            new HisPersoonGeslachtsaanduidingModel(
                persoonHisVolledig, geslachtsaanduidingGroepBericht, geslachtsaanduidingGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null));
        persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);


        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                new PersoonView(persoonHisVolledig),
                persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testHuidigeSituatieIsNull() {
        final PersoonBericht kind = maakPersoon(null, null, null,
            StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null);
        kind.setIdentificerendeSleutel("456");

        final PersoonBericht ouder1 = new PersoonBericht();
        ouder1.setIdentificerendeSleutel("123");

        //Maak de nieuwe situatie aan
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final OuderBericht ouderBericht = new OuderBericht();
        ouderBericht.setPersoon(ouder1);
        familie.getBetrokkenheden().add(ouderBericht);

        final KindBericht kindBericht = new KindBericht();
        kindBericht.setPersoon(kind);
        familie.getBetrokkenheden().add(kindBericht);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        final PersoonHisVolledigImpl ouderHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        bestaandeBetrokkenen.put(ouder1.getIdentificerendeSleutel(), new PersoonView(ouderHisVolledig));

        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                null,
                familie, null, bestaandeBetrokkenen);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testNieuweSituatieHuwelijkPartnerMetGeslachtsnaamComponentenMetOuderschapOverKind() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        // Nieuwe situatie huwelijk
        final PersoonBericht partner1 = maakPersoon("van", "-", "Brom", null, null);
        partner1.setIdentificerendeSleutel("partner1");
        final PersoonBericht partner2 = new PersoonBericht();
        partner2.setIdentificerendeSleutel("partner2");
        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final PartnerBericht partner1Betr = new PartnerBericht();
        partner1Betr.setPersoon(partner1);
        final PartnerBericht partner2Betr = new PartnerBericht();
        partner2Betr.setPersoon(partner2);
        huwelijkBericht.getBetrokkenheden().add(partner1Betr);
        huwelijkBericht.getBetrokkenheden().add(partner2Betr);

        // De personen waarbij ze een al een kind hebben met dezelfde geslachtsnaam als de vader.
        final PersoonHisVolledigImpl persoon1HisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        final PersoonHisVolledigImpl persoon2HisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);
        final PersoonHisVolledigImpl kindHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(persoon1HisVolledig, persoon2HisVolledig, kindHisVolledig, actieModel);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompOuder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoon1HisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        persoon1HisVolledig.getGeslachtsnaamcomponenten().add(geslCompOuder);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompKind =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(kindHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        kindHisVolledig.getGeslachtsnaamcomponenten().add(geslCompKind);

        //Geslachtsaanduiding man partner1.
        final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(
            new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));
        geslachtsaanduidingGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
            new HisPersoonGeslachtsaanduidingModel(
                persoon1HisVolledig, geslachtsaanduidingGroepBericht, geslachtsaanduidingGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null));
        persoon1HisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);

        //Bestaande betrokkenen
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(partner1.getIdentificerendeSleutel(), new PersoonView(persoon1HisVolledig));
        bestaandeBetrokkenen.put(partner2.getIdentificerendeSleutel(), new PersoonView(persoon2HisVolledig));

        //Huidige situatie is er niet!

        //Evalueer regel
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183()
            .voerRegelUit(
                null,
                huwelijkBericht, null, bestaandeBetrokkenen);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(partner1, berichtEntiteiten.get(0));
    }

    @Test
    public void testAfstammingVaderMetGeslachtsnaamcomponentenMetOuderschapOverKind() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        // Nieuwe situatie familierechtelijkeBetrekking
        final PersoonBericht ouder1 = maakPersoon("van", "-", "Brom", null, null);
        ouder1.setIdentificerendeSleutel("partner1");
        final PersoonBericht ouder2 = new PersoonBericht();
        ouder2.setIdentificerendeSleutel("partner2");
        final PersoonBericht kind = new PersoonBericht();
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final OuderBericht ouder1Betr = new OuderBericht();
        ouder1Betr.setPersoon(ouder1);
        final OuderBericht ouder2Betr = new OuderBericht();
        ouder2Betr.setPersoon(ouder2);
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setPersoon(kind);
        familie.getBetrokkenheden().add(ouder1Betr);
        familie.getBetrokkenheden().add(ouder2Betr);
        familie.getBetrokkenheden().add(kindBetr);

        //Huidige situatie
        final PersoonHisVolledigImpl ouder1HisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, 123);
        ouder1.setObjectSleutelDatabaseID(123);
        final PersoonHisVolledigImpl ouder2HisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, 456);
        ouder2.setObjectSleutelDatabaseID(456);
        final PersoonHisVolledigImpl kindHisVolledig = maakHuidigePersoon(SoortPersoon.INGESCHREVENE, 789);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1HisVolledig, ouder2HisVolledig, kindHisVolledig, actieModel);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompOuder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(ouder1HisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        ouder1HisVolledig.getGeslachtsnaamcomponenten().add(geslCompOuder);

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslCompKind =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(kindHisVolledig, new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .voorvoegsel("van")
                .scheidingsteken("-")
                .stam("Brom")
                .eindeRecord()
                .build();
        kindHisVolledig.getGeslachtsnaamcomponenten().add(geslCompKind);

        //Geslachtsaanduiding man partner1.
        final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(
            new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));
        geslachtsaanduidingGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
            new HisPersoonGeslachtsaanduidingModel(
                ouder1HisVolledig, geslachtsaanduidingGroepBericht, geslachtsaanduidingGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null));
        ouder1HisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);

        //Bestaande betrokkenen
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(ouder1.getIdentificerendeSleutel(), new PersoonView(ouder1HisVolledig));
        bestaandeBetrokkenen.put(ouder2.getIdentificerendeSleutel(), new PersoonView(ouder2HisVolledig));

        //Evalueer regel
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY0183().voerRegelUit(
            new FamilierechtelijkeBetrekkingView(RelatieTestUtil
                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kindHisVolledig),
                DatumTijdAttribuut.nu(), DatumAttribuut.vandaag()), familie, null, bestaandeBetrokkenen);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(ouder1, berichtEntiteiten.get(0));

    }

    private PersoonBericht maakPersoon(final String voorvoegsel, final String scheidingsTeken, final String naam,
        final PredicaatAttribuut predikaat, final AdellijkeTitel adellijkeTitel)
    {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final PersoonGeslachtsnaamcomponentBericht geslComp = new PersoonGeslachtsnaamcomponentBericht();
        geslComp.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        if (voorvoegsel != null) {
            geslComp.getStandaard().setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
        }
        if (scheidingsTeken != null) {
            geslComp.getStandaard().setScheidingsteken(new ScheidingstekenAttribuut(scheidingsTeken));
        }
        if (naam != null) {
            geslComp.getStandaard().setStam(new GeslachtsnaamstamAttribuut(naam));
        }
        if (predikaat != null) {
            geslComp.getStandaard().setPredicaat(predikaat);
        }
        if (adellijkeTitel != null) {
            geslComp.getStandaard().setAdellijkeTitel(new AdellijkeTitelAttribuut(adellijkeTitel));
        }

        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoonBericht.getGeslachtsnaamcomponenten().add(geslComp);
        return persoonBericht;
    }

    private PersoonHisVolledigImpl maakHuidigePersoon(final SoortPersoon soortPersoon, final Integer databaseId) {
        final PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImplBuilder(soortPersoon).build();
        ReflectionTestUtils.setField(persoonHisVolledigImpl, "iD", databaseId);
        return persoonHisVolledigImpl;
    }
}
