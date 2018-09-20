/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;


import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0105MTest {

    private Nationaliteit nederlandseNationaliteit;
    private Nationaliteit vreemdeNationaliteit;

    @Before
    public void init() {
        nederlandseNationaliteit = new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, null, null, null);
        vreemdeNationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut("0009"), null, null, null);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0105M, new BRBY0105M().getRegel());
    }

    @Test
    public void testKindHeeftNlNationaliteit() {
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, nederlandseNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0105M().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                             maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testKindHeeftGeenNLNationaliteitEnOokGeenRechtOp() {
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, vreemdeNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        //Vader geboren buiten NL
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte((short) 222)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        //Moeder geboren buiten NL
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte((short) 333)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0105M().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                             maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testKindHeeftGeenNLNationaliteitWelRechtOpDoorOnbekendeGeboorteDatumOuders() {
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, vreemdeNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        //Vader geboren buiten NL
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(0)
                .landGebiedGeboorte((short) 222)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        //Moeder geboren buiten NL
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(0)
                .landGebiedGeboorte((short) 333)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0105M().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                             maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals("99", berichtEntiteiten.get(0).getCommunicatieID());
    }


    @Test
    public void testKindHeeftGeenNLNationaliteitMaarWelRechtOpOmdatOuderInNederlandGeboren() {
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, vreemdeNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        //Vader geboren in NL
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte(LandGebiedCodeAttribuut.NEDERLAND)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        //Moeder geboren buiten NL
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte((short) 333)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0105M().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                             maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals("99", berichtEntiteiten.get(0).getCommunicatieID());
    }

    @Test
    public void testKindHeeftGeenNLNationaliteitMaarWelRechtOpOmdatOuderInNederlandVoor18deGevestigd() {
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonNationaliteitHisVolledigImpl kindNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(kind, vreemdeNationaliteit)
                        .nieuwStandaardRecord(20120101, null, 20120101)
                        .eindeRecord()
                        .build();
        kind.getNationaliteiten().add(kindNationaliteit);

        //Vader geboren in NL
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte((short) 222)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(20120101)
                .eindeRecord()
                .build();

        //Moeder geboren buiten NL, voor haar 18de gevestigd in NL
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(20120101)
                .datumGeboorte(19600101)
                .landGebiedGeboorte((short) 333)
                .eindeRecord()
                .nieuwInschrijvingRecord(20120101)
                .datumInschrijving(19770101)
                .eindeRecord()
                .build();

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20120101, actieModel);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0105M().voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                             maakFamilieRechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals("99", berichtEntiteiten.get(0).getCommunicatieID());
    }

    /**
     * Puur voor het maken van een bericht variant van de relatie, de regel gebruikt enkel het kind als return waarde in
     * de objectenDieDeRegelOvertreden lijst.
     * @return FamilieRechtelijkeBetrekkingBericht
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
