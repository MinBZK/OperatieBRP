/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtResultaatTest.
 */
public class BerichtResultaatTest {

    private static final String HOOG = "hoog";
    private static final String LAAG = "laag";

    @Test
    public void testBerichtResultaat() {
        final BerichtVerwerkingsResultaat berichtResultaat = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(HOOG).metVerwerking(VerwerkingsResultaat
                .FOUTIEF).build();
        Assert.assertEquals(HOOG, berichtResultaat.getHoogsteMeldingsniveau());
        Assert.assertEquals(VerwerkingsResultaat.FOUTIEF.getNaam(), berichtResultaat.getVerwerking());
    }

    @Test
    public void testGelijkheidBerichtResultaat() {
        final BerichtVerwerkingsResultaat berichtResultaat1 = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(HOOG).metVerwerking(VerwerkingsResultaat
                .FOUTIEF).build();
        final BerichtVerwerkingsResultaat berichtResultaat2 = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(HOOG).metVerwerking(VerwerkingsResultaat
                .FOUTIEF).build();
        final BerichtVerwerkingsResultaat berichtResultaat3 = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(LAAG).metVerwerking(VerwerkingsResultaat
                .FOUTIEF).build();

        Assert.assertTrue(berichtResultaat1.equals(berichtResultaat1));
        Assert.assertTrue(berichtResultaat1.equals(berichtResultaat2));
        Assert.assertFalse(berichtResultaat1.equals(berichtResultaat3));
        Assert.assertFalse(berichtResultaat1.equals(null));
        Assert.assertFalse(berichtResultaat1.equals(VerwerkingsResultaat.FOUTIEF));
    }
}
