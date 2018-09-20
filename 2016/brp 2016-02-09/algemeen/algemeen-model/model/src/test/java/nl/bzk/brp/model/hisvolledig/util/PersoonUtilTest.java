/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonUtilTest {

    @Test
    public void testHeeftActueleNederlandseNationaliteitJa() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Persoon persoon = new PersoonView(persoonHisVolledig);

        final boolean resultaat = PersoonUtil.heeftActueleNederlandseNationaliteit(persoon);

        assertTrue(resultaat);
    }

    @Test
    public void testHeeftActueleNederlandseNationaliteitNee() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Persoon persoon = new PersoonView(persoonHisVolledig);

        final Set<PersoonNationaliteitHisVolledigImpl> nationaliteiten = new HashSet<>();
        final PersoonNationaliteitHisVolledigImplBuilder persoonNationaliteitHisVolledigImplBuilder =
            new PersoonNationaliteitHisVolledigImplBuilder(StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde())
                .nieuwStandaardRecord(persoonHisVolledig.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerantwoordingInhoud())
                .eindeRecord();
        nationaliteiten.add(persoonNationaliteitHisVolledigImplBuilder.build());

        ReflectionTestUtils.setField(persoonHisVolledig, "nationaliteiten", nationaliteiten);

        final boolean resultaat = PersoonUtil.heeftActueleNederlandseNationaliteit(persoon);

        assertFalse(resultaat);
    }

    @Test
    public void testIsLeeftijdOfOuderOpDatumJa() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Persoon persoon = new PersoonView(persoonHisVolledig);

        final boolean resultaat = PersoonUtil.isLeeftijdOfOuderOpDatum(persoon, 1, new DatumAttribuut(19790101));

        assertTrue(resultaat);
    }

    @Test
    public void testIsLeeftijdOfOuderOpDatumNee() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Persoon persoon = new PersoonView(persoonHisVolledig);

        final boolean resultaat = PersoonUtil.isLeeftijdOfOuderOpDatum(persoon, 1, new DatumAttribuut(19770101));

        assertFalse(resultaat);
    }

    @Test
    public void testGetHGPs() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Persoon persoon = new PersoonView(persoonHisVolledig);

        final List<HuwelijkGeregistreerdPartnerschap> hgPs = PersoonUtil.getHGPs(persoon);

        assertEquals(1, hgPs.size());
    }

    @Test
    public void testGetHGPsGeenBetrokkenheden() throws Exception {
        final Persoon persoon = mock(Persoon.class);

        final List<HuwelijkGeregistreerdPartnerschap> hgPs = PersoonUtil.getHGPs(persoon);

        assertEquals(0, hgPs.size());
    }

}
