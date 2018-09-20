/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.model.logisch.Persoon;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link BijhoudingResultaat} klasse. */
public class BijhoudingResultaatTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn, dient de lijst met bijgehouden personen
     * ook niet <code>null</code> te zijn en dient de resultaat code initieel op "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertNotNull(resultaat.getBijgehoudenPersonen());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, resultaat.getBijhoudingCode());
    }

    @Test
    public void testToevoegenEnkeleBijgehoudenPersoon() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        resultaat.voegPersoonToe(new Persoon());
        Assert.assertEquals(1, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersoonToe(new Persoon());
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenPersonen() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        resultaat.voegPersonenToe(Arrays.asList(new Persoon(), new Persoon()));
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersonenToe(Arrays.asList(new Persoon(), new Persoon()));
        Assert.assertEquals(4, resultaat.getBijgehoudenPersonen().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienPersonenLijstWordtAangepastVanBuiten() {
        List<Persoon> personen = new ArrayList<Persoon>();
        personen.add(new Persoon());
        personen.add(new Persoon());

        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        resultaat.voegPersonenToe(personen);
        resultaat.getBijgehoudenPersonen().add(new Persoon());
    }

    @Test
    public void testGettersEnSetters() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Calendar now = Calendar.getInstance();
        resultaat.setTijdstipRegistratie(now.getTime());
        resultaat.setBijhoudingCode(BijhoudingCode.DIRECT_VERWERKT);
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, resultaat.getBijhoudingCode());

        now.add(Calendar.MONTH, 2);
        resultaat.setTijdstipRegistratie(now.getTime());
        resultaat.setBijhoudingCode(BijhoudingCode.UITGESTELD);
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());
        Assert.assertEquals(BijhoudingCode.UITGESTELD, resultaat.getBijhoudingCode());

        resultaat.setTijdstipRegistratie(null);
        Assert.assertNull(resultaat.getTijdstipRegistratie());
    }
}
