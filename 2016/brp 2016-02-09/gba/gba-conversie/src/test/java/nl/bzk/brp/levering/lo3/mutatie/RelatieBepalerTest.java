/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mutatie;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.levering.lo3.conversie.mutatie.RelatieBepaler;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

public class RelatieBepalerTest {

    private final RelatieBepaler subject = new RelatieBepaler();

    HuwelijkGeregistreerdPartnerschapHisVolledig relatie2;
    HuwelijkGeregistreerdPartnerschapHisVolledig relatie3;
    HuwelijkGeregistreerdPartnerschapHisVolledig relatie6;

    @Before
    public void setup() {
        final Partij partij = PersoonHisVolledigUtil.maakPartij();
        final ActieModel actie2 =
                PersoonHisVolledigUtil.maakActie(
                    1002L,
                    SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                    SoortActie.CONVERSIE_G_B_A,
                    19020101,
                    partij);
        final ActieModel actie23 =
                PersoonHisVolledigUtil.maakActie(
                    1003L,
                    SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                    SoortActie.CONVERSIE_G_B_A,
                    19030101,
                    partij);

        final ActieModel actie36 =
                PersoonHisVolledigUtil.maakActie(
                    1006L,
                    SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                    SoortActie.CONVERSIE_G_B_A,
                    19060101,
                    partij);

        relatie2 = maakRelatie(2, actie2, actie23);
        relatie3 = maakRelatie(3, actie23, actie36);
        relatie6 = maakRelatie(6, actie36, null);
    }

    private HuwelijkGeregistreerdPartnerschapHisVolledig maakRelatie(final int id, final ActieModel actieAanvang, final ActieModel actieEinde) {
        final HuwelijkHisVolledigImplBuilder relatieBuilder = new HuwelijkHisVolledigImplBuilder();
        ReflectionTestUtils.setField(ReflectionTestUtils.getField(relatieBuilder, "hisVolledigImpl"), "iD", id);

        relatieBuilder.nieuwStandaardRecord(actieAanvang)
                      .datumAanvang(actieAanvang.getDatumAanvangGeldigheid())
                      .gemeenteAanvang((short) 433)
                      .landGebiedAanvang((short) 6030)
                      .eindeRecord();

        if (actieEinde != null) {
            relatieBuilder.nieuwStandaardRecord(actieEinde)
                          .datumAanvang(actieAanvang.getDatumAanvangGeldigheid())
                          .gemeenteAanvang((short) 433)
                          .landGebiedAanvang((short) 6030)
                          .datumEinde(actieEinde.getDatumAanvangGeldigheid())
                          .gemeenteEinde((short) 433)
                          .landGebiedEinde((short) 6030)
                          .redenEinde("Z")
                          .eindeRecord();
        }

        return relatieBuilder.build();
    }

    @Test
    public void test236() {
        test(relatie2, relatie3, relatie6);
    }

    @Test
    public void test263() {
        test(relatie2, relatie6, relatie3);
    }

    @Test
    public void test326() {
        test(relatie3, relatie2, relatie6);
    }

    @Test
    public void test362() {
        test(relatie3, relatie6, relatie2);
    }

    @Test
    public void test623() {
        test(relatie6, relatie2, relatie3);
    }

    @Test
    public void test632() {
        test(relatie6, relatie3, relatie2);
    }

    private void test(
        final HuwelijkGeregistreerdPartnerschapHisVolledig relatie1,
        final HuwelijkGeregistreerdPartnerschapHisVolledig relatie2,
        final HuwelijkGeregistreerdPartnerschapHisVolledig relatie3)
    {
        final Set<HuwelijkGeregistreerdPartnerschapHisVolledig> relaties = new LinkedHashSet<>();
        relaties.add(relatie1);
        relaties.add(relatie2);
        relaties.add(relatie3);

        final Map<Integer, String> resultaat = subject.bepaalRelatieMapping(relaties);
        Assert.assertEquals(3, resultaat.size());

        Assert.assertEquals("2+3+6", resultaat.get(2));
        Assert.assertEquals("2+3+6", resultaat.get(3));
        Assert.assertEquals("2+3+6", resultaat.get(6));
    }

}
