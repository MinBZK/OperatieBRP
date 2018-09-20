/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0126Test {

    private PersoonHisVolledigImpl moeder;
    private PersoonHisVolledigImpl vader;
    private PersoonHisVolledigImpl kind1;
    private PersoonHisVolledigImpl kind2;
    private PersoonHisVolledigImpl kind3;

    private ActieModel actieModel;


    @Before
    public void setUp() {
        actieModel = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, DatumTijdAttribuut.nu(), null);

        moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19860101).eindeRecord().build();
        vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();

        kind1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20100101, null, 20100101)
                .geslachtsnaamstam("PIET")
                .voornamen("JAN")
                .scheidingsteken("-")
                .voorvoegsel("DER").eindeRecord()
                .nieuwGeboorteRecord(20100101).datumGeboorte(20100101).eindeRecord()
                .build();

        kind2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20110101, null, 20110101)
                .geslachtsnaamstam("HENDRIK")
                .voornamen("AMBACHT")
                .voorvoegsel("IDO").eindeRecord()
                .nieuwGeboorteRecord(20110101).datumGeboorte(20110101).eindeRecord()
                .build();

        kind3 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20120101, null, 20120101)
                .geslachtsnaamstam("HENDRIK")
                .voornamen("FLOOR")
                .scheidingsteken("-")
                .voorvoegsel("LA").eindeRecord()
                .nieuwGeboorteRecord(20120101).datumGeboorte(20120101).eindeRecord()
                .build();


        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind1, 20100101, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind2, 20110101, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind3, 20120101, actieModel);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0126, new BRBY0126().getRegel());
    }

    @Test
    public void testHappyFlowKindHeeftAnderePersonaliaDanEerdereKinderen() {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGeborene = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGeborene.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut("pietje"));
        samengesteldeNaamGeborene.setVoornamen(new VoornamenAttribuut("jantje"));
        samengesteldeNaamGeborene.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        samengesteldeNaamGeborene.setVoorvoegsel(new VoorvoegselAttribuut("der"));
        samengesteldeNaamGeborene.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(20120101, samengesteldeNaamGeborene);

        //Maak het nieuwe kind aan met geboorte groep.
        final PersoonHisVolledigImpl nieuwKind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101).datumGeboorte(20120101).eindeRecord().build();

        //Voeg de samengestelde naam toe.
        nieuwKind.getPersoonSamengesteldeNaamHistorie().voegToe(
                new HisPersoonSamengesteldeNaamModel(nieuwKind, samengesteldeNaamGeborene, samengesteldeNaamGeborene,
                                                     actieModel)
        );

        //Maak een nieuwe familierechtelijke betrekking tussen het nieuwe kind en de kersverse ouders.
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, nieuwKind, 20120101, actieModel);

        //Haal de familie rechtelijke betrekking eruit via het kind.
        final FamilierechtelijkeBetrekkingView aangemaakteRelatie =
                haalFamilieRechtelijkeBetrekkingOpWaarPersoonKindInIs(nieuwKind);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0126().voerRegelUit(aangemaakteRelatie, familierechtelijkeBetrekkingBericht);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testNieuwKindHeeftDezelfdePersonaliaAlsKind2() {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGeborene = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGeborene.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut("HENDRIK"));
        samengesteldeNaamGeborene.setVoornamen(new VoornamenAttribuut("AMBACHT"));
        samengesteldeNaamGeborene.setVoorvoegsel(new VoorvoegselAttribuut("IDO"));
        samengesteldeNaamGeborene.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(20110101, samengesteldeNaamGeborene);

        //Maak het nieuwe kind aan met geboorte groep.
        final PersoonHisVolledigImpl nieuwKind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20110101).datumGeboorte(20110101).eindeRecord().build();

        //Voeg de samengestelde naam toe.
        nieuwKind.getPersoonSamengesteldeNaamHistorie().voegToe(
                new HisPersoonSamengesteldeNaamModel(nieuwKind, samengesteldeNaamGeborene, samengesteldeNaamGeborene,
                                                     actieModel)
        );

        //Maak een nieuwe familierechtelijke betrekking tussen het nieuwe kind en de kersverse ouders.
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, nieuwKind, 20110101, actieModel);

        //Haal de familie rechtelijke betrekking eruit via het kind.
        final FamilierechtelijkeBetrekkingView aangemaakteRelatie =
                haalFamilieRechtelijkeBetrekkingOpWaarPersoonKindInIs(nieuwKind);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0126().voerRegelUit(aangemaakteRelatie, familierechtelijkeBetrekkingBericht);

        Assert.assertEquals(1, berichtEntiteits.size());
        Assert.assertEquals("99", berichtEntiteits.get(0).getObjectSleutel());
    }

    @Test
    public void testNieuwKindHeeftDezelfdeSamengesteldeNaamAlsKind3EnDezelfdeDatumGeboorteAlsKind1() {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGeborene = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGeborene.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut("HENDRIK"));
        samengesteldeNaamGeborene.setVoornamen(new VoornamenAttribuut("FLOOR"));
        samengesteldeNaamGeborene.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        samengesteldeNaamGeborene.setVoorvoegsel(new VoorvoegselAttribuut("LA"));
        samengesteldeNaamGeborene.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(20120101, samengesteldeNaamGeborene);

        //Maak het nieuwe kind aan met geboorte groep.
        final PersoonHisVolledigImpl nieuwKind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20100101).datumGeboorte(20100101).eindeRecord().build();

        //Voeg de samengestelde naam toe.
        nieuwKind.getPersoonSamengesteldeNaamHistorie().voegToe(
                new HisPersoonSamengesteldeNaamModel(nieuwKind, samengesteldeNaamGeborene, samengesteldeNaamGeborene,
                                                     actieModel)
        );

        //Maak een nieuwe familierechtelijke betrekking tussen het nieuwe kind en de kersverse ouders.
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, nieuwKind, 20100101, actieModel);

        //Haal de familie rechtelijke betrekking eruit via het kind.
        final FamilierechtelijkeBetrekkingView aangemaakteRelatie =
                haalFamilieRechtelijkeBetrekkingOpWaarPersoonKindInIs(nieuwKind);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0126().voerRegelUit(aangemaakteRelatie, familierechtelijkeBetrekkingBericht);

        Assert.assertTrue(berichtEntiteits.isEmpty());

    }

    private FamilierechtelijkeBetrekkingView haalFamilieRechtelijkeBetrekkingOpWaarPersoonKindInIs(
            final PersoonHisVolledigImpl nieuwKind)
    {
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : nieuwKind.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheidHisVolledig.getRol().getWaarde()) {
                return new FamilierechtelijkeBetrekkingView(
                        (FamilierechtelijkeBetrekkingHisVolledig) betrokkenheidHisVolledig.getRelatie(),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
            }
        }
        return null;
    }

    private FamilierechtelijkeBetrekkingBericht maakFamilieRechtelijkeBetrekkingBericht(
            final int geboorteDatumKind, final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGroepBericht)
    {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
            new FamilierechtelijkeBetrekkingBericht();
        final PersoonBericht kindBericht = new PersoonBericht();
        kindBericht.setObjectSleutel("99");
        kindBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        kindBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(geboorteDatumKind));
        kindBericht.setSamengesteldeNaam(samengesteldeNaamGroepBericht);
        final KindBericht kindBerichtBetr = new KindBericht();
        kindBerichtBetr.setPersoon(kindBericht);
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBerichtBetr);

        final PersoonBericht moederBericht = new PersoonBericht();
        moederBericht.setObjectSleutel("1");
        moederBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        moederBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20110101));
        final OuderBericht moederBerichtBetr = new OuderBericht();
        moederBerichtBetr.setPersoon(moederBericht);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(moederBerichtBetr);

        final PersoonBericht vaderBericht = new PersoonBericht();
        vaderBericht.setObjectSleutel("2");
        vaderBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        vaderBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20110101));
        final OuderBericht vaderBerichtBetr = new OuderBericht();
        vaderBerichtBetr.setPersoon(vaderBericht);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(vaderBerichtBetr);
        return familierechtelijkeBetrekkingBericht;
    }

}
