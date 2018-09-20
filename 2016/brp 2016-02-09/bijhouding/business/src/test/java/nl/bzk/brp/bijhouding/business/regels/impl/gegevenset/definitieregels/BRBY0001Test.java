/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0001Test {

    private static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID = new DatumEvtDeelsOnbekendAttribuut(20120101);
    private static final String ID = "iD";
    private final BRBY0001 brby0001 = new BRBY0001();

    @Test
    public void testVerwantschapBroerKrijgtKindMetZus() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);
        final PersoonHisVolledigImpl vader = maakPersoon(1);
        final PersoonHisVolledigImpl moeder = maakPersoon(2);

        final PersoonHisVolledigImpl kind = maakPersoon(3);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie1 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        ReflectionTestUtils.setField(relatie1, ID, 1);

        final PersoonHisVolledigImpl kind2 = maakPersoon(4);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind2, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie2 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        ReflectionTestUtils.setField(relatie2, ID, 2);

        final boolean resultaat = brby0001.isErVerwantschap(
                new PersoonView(kind), new PersoonView(kind2));

        Assert.assertTrue(resultaat);
    }

    @Test
    public void testVerwantschapKindKrijgtKindMetOma() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);
        final PersoonHisVolledigImpl vader = maakPersoon(1);
        final PersoonHisVolledigImpl moeder = maakPersoon(2);

        final PersoonHisVolledigImpl opaVader = maakPersoon(3);
        final PersoonHisVolledigImpl omaVader = maakPersoon(4);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(opaVader, omaVader, vader, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie1 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(vader);
        ReflectionTestUtils.setField(relatie1, ID, 1);

        final PersoonHisVolledigImpl opaMoeder = maakPersoon(5);
        final PersoonHisVolledigImpl omaMoeder = maakPersoon(6);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(opaMoeder, omaMoeder, moeder, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie2 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(moeder);
        ReflectionTestUtils.setField(relatie2, ID, 2);

        final PersoonHisVolledigImpl kind = maakPersoon(7);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie3 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        ReflectionTestUtils.setField(relatie3, ID, 3);

        final boolean resultaat = brby0001.isErVerwantschap(
                new PersoonView(kind), new PersoonView(omaMoeder));

        Assert.assertTrue(resultaat);
    }

    @Test
    public void testVerwantschapKindKrijgtKindMetMoeder() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl vader = maakPersoon(1);
        final PersoonHisVolledigImpl moeder = maakPersoon(2);

        final PersoonHisVolledigImpl opaVader = maakPersoon(3);
        final PersoonHisVolledigImpl omaVader = maakPersoon(4);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(opaVader, omaVader, vader, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie1 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(vader);
        ReflectionTestUtils.setField(relatie1, ID, 1);

        final PersoonHisVolledigImpl opaMoeder = maakPersoon(5);
        final PersoonHisVolledigImpl omaMoeder = maakPersoon(6);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(opaMoeder, omaMoeder, moeder, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie2 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(moeder);
        ReflectionTestUtils.setField(relatie2, ID, 2);

        final PersoonHisVolledigImpl kind = maakPersoon(7);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie3 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        ReflectionTestUtils.setField(relatie3, ID, 3);

        final boolean resultaat = brby0001.isErVerwantschap(
                new PersoonView(kind), new PersoonView(moeder));

        Assert.assertTrue(resultaat);
    }

    @Test
    public void testZonderVerwantschap() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl vaderA = maakPersoon(1);
        final PersoonHisVolledigImpl moederB = maakPersoon(2);

        final PersoonHisVolledigImpl vaderC = maakPersoon(3);
        final PersoonHisVolledigImpl moederD = maakPersoon(4);

        final PersoonHisVolledigImpl kindAB = maakPersoon(5);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vaderA, moederB, kindAB, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie1 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kindAB);
        ReflectionTestUtils.setField(relatie1, ID, 1);

        final PersoonHisVolledigImpl kindCD = maakPersoon(6);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vaderC, moederD, kindCD, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie2 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kindCD);
        ReflectionTestUtils.setField(relatie2, ID, 2);

        final boolean resultaat = brby0001.isErVerwantschap(
                new PersoonView(kindAB), new PersoonView(kindCD));

        Assert.assertFalse(resultaat);
    }

    @Test
    public void testZonderVerwantschapDoorEerderKind() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl vader = maakPersoon(1);
        final PersoonHisVolledigImpl moeder = maakPersoon(2);

        final PersoonHisVolledigImpl kind = maakPersoon(3);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, DatumAttribuut.vandaag().getWaarde(), actie);
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie1 =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        ReflectionTestUtils.setField(relatie1, ID, 1);

        final boolean resultaat = brby0001.isErVerwantschap(
                new PersoonView(vader), new PersoonView(moeder));

        Assert.assertFalse(resultaat);
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                                      null, null), null, DATUM_AANVANG_GELDIGHEID, null, DatumTijdAttribuut.nu(), null);
    }

    /**
     * Maakt een moeder.
     *
     * @return de moeder als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakPersoon(final Integer bsn) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(maakActie())
                .voornamen("Pietje")
                .geslachtsnaamstam("sjdhf")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, ID, bsn);
        return persoon;
    }
}
