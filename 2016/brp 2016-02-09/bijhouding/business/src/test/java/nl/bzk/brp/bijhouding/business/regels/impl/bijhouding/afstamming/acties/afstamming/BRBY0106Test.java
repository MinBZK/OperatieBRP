/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The type BRBY 0106 test.
 */
public class BRBY0106Test {

    private Nationaliteit nederlandseNationaliteit;
    private Nationaliteit vreemdeNationaliteit;

    @Before
    public void init() {
        nederlandseNationaliteit = new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, null, null, null);
        vreemdeNationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut("0009"), null, null, null);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0106, new BRBY0106().getRegel());
    }

    @Test
    public void testPreconditieMoetNederlandseNationaliteitHebben() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "van", "Valk", JaNeeAttribuut.NEE);

        //Geen Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, vreemdeNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPreconditieKindMoetIngeschreveneZijn() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.NIET_INGESCHREVENE, "-", "van", "Valk", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPreconditieKindHeeftIndicatieNamenReeks() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "van", "Valk", JaNeeAttribuut.JA);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testKindHeeftAndereNaamDanBeideOuders() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "van", "Valk", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals("99", berichtEntiteiten.get(0).getCommunicatieID());
    }

    @Test
    public void testKindHeeftDezelfdeNaamAlsMoeder() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testKindHeeftDezelfdeNaamAlsMoederEnVader() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnverwachteBetrokkenheid() {
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
                new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();

        familierechtelijkeBetrekkingHisVolledig.setBetrokkenheden(new HashSet<BetrokkenheidHisVolledigImpl>());

        // Geef kind een familierechtelijke betrekking met Partner betrokkenheid, dit is uiteraard 'onverwacht'.
        final BetrokkenheidHisVolledigImpl partnerBetr
            = new PartnerHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, kind);
        familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(partnerBetr);

        final FamilierechtelijkeBetrekkingView familieView =
                new FamilierechtelijkeBetrekkingView(familierechtelijkeBetrekkingHisVolledig, null, null);

        new BRBY0106().voerRegelUit(familieView, maakFamilieRechtelijkeBetrekkingBericht());
    }

    @Test
    public void testKindZonderOuders() {
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigViewMetNullWaarden(null,
                                                                              null,
                                                                              kind,
                                                                              kind,
                                                                              SoortBetrokkenheid.KIND);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testKindIsNull() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigViewMetNullWaarden(vader,
                                                                              moeder,
                                                                              null,
                                                                              vader,
                                                                              SoortBetrokkenheid.OUDER);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testGeslachtsnaamMatchNietEenOuder() {
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "ten", "Cate", JaNeeAttribuut.NEE);
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigViewMetNullWaarden(vader,
                                                                              null,
                                                                              kind,
                                                                              kind,
                                                                              SoortBetrokkenheid.KIND);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals("99", berichtEntiteiten.get(0).getCommunicatieID());
    }

    @Test
    public void testKindZonderSamengesteldeNaam() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testOudersZonderSamengesteldeNaam() {
        //Vader: Ten - Cate
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        //Moeder der - Horst
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        //Kind moet de regels overtreden om de precondities te kunnen checken.
        final PersoonHisVolledigImpl kind = maakPersoon(SoortPersoon.INGESCHREVENE, "-", "der", "Horst", JaNeeAttribuut.NEE);

        //Nederlandse nationaliteit
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                bouwFamilieRechtelijkeBetrekkingHisVolledigView(vader, moeder, kind);

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0106().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                            maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    /**
     * Bouwt een familie rechtelijke betrekking his volledig view met behulp van ouders en kind.
     *
     * @param vader  vader
     * @param moeder moeder
     * @param kind   kind
     * @return familierechtelijke betrekking his volledig view
     */
    private FamilierechtelijkeBetrekkingView bouwFamilieRechtelijkeBetrekkingHisVolledigView(
            final PersoonHisVolledigImpl vader,
            final PersoonHisVolledigImpl moeder,
            final PersoonHisVolledigImpl kind)
    {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);
        return new FamilierechtelijkeBetrekkingView(RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                                                    DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    /**
     * Bouwt een familie rechtelijke betrekking his volledig view met behulp van ouders en kind.
     *
     * @param vader vader
     * @param moeder moeder
     * @param kind kind
     * @param persoonInRelatie persoon in relatie
     * @param soortBetrokkenheid soort betrokkenheid
     * @return familierechtelijke betrekking his volledig view
     */
    private FamilierechtelijkeBetrekkingView bouwFamilieRechtelijkeBetrekkingHisVolledigViewMetNullWaarden(
            final PersoonHisVolledigImpl vader,
            final PersoonHisVolledigImpl moeder,
            final PersoonHisVolledigImpl kind,
            final PersoonHisVolledigImpl persoonInRelatie,
            final SoortBetrokkenheid soortBetrokkenheid)
    {
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekkingMetNullWaarden(vader, moeder, kind, 20120101);
        return new FamilierechtelijkeBetrekkingView(
                RelatieTestUtil
                        .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonRolInHeeft(persoonInRelatie,
                                                                                                       soortBetrokkenheid),
                DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    /**
     * Maakt een 'standaard' persoon.
     *
     * @param soortPersoon        soort persoon
     * @param scheidingsTeken     scheidings teken
     * @param voorvoegsel         voorvoegsel
     * @param geslachtsnaam       geslachtsnaam
     * @param indicatieNamenreeks indicatie namenreeks
     * @return persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakPersoon(final SoortPersoon soortPersoon,
                                               final String scheidingsTeken,
                                               final String voorvoegsel,
                                               final String geslachtsnaam,
                                               final JaNeeAttribuut indicatieNamenreeks)
    {
        return new PersoonHisVolledigImplBuilder(soortPersoon)
                .nieuwSamengesteldeNaamRecord(20120101, null, 20120101)
                .indicatieNamenreeks(indicatieNamenreeks)
                .voorvoegsel(voorvoegsel)
                .scheidingsteken(scheidingsTeken)
                .geslachtsnaamstam(geslachtsnaam)
                .eindeRecord()
                .build();
    }

    /**
     * Maakt een 'standaard' familie rechtelijke betrekking bericht.
     *
     * @return familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakFamilieRechtelijkeBetrekkingBericht() {
        final PersoonBericht kindBericht = new PersoonBericht();
        kindBericht.setCommunicatieID("99");
        return new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>()
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegKindToe(kindBericht)
                .getRelatie();
    }

}
