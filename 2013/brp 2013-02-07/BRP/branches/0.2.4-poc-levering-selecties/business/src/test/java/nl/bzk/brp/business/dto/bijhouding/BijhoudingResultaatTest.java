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

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
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
        Assert.assertTrue(resultaat.getVerwerkingsResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertNotNull(resultaat.getBijgehoudenPersonen());
    }

    @Test
    public void testToevoegenEnkeleBijgehoudenPersoonBericht() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersoonToe(new PersoonBericht());
        Assert.assertEquals(1, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersoonToe(new PersoonBericht());
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenPersonen() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersonenToe(Arrays.asList((Persoon) new PersoonBericht(), (Persoon) new PersoonBericht()));
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersonenToe(Arrays.asList((Persoon) new PersoonBericht(), (Persoon) new PersoonBericht()));
        Assert.assertEquals(4, resultaat.getBijgehoudenPersonen().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenPersonenSortering() {
        // Zou eigenlijk een persoonModel in moeten
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setVerzendendId("Persoon1");
        PersoonIdentificatienummersGroepBericht pid1 = new PersoonIdentificatienummersGroepBericht();
        pid1.setBurgerservicenummer(new Burgerservicenummer("1234"));
        persoon1.setIdentificatienummers(pid1);

        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setVerzendendId("Persoon2");
        PersoonIdentificatienummersGroepBericht pid2 = new PersoonIdentificatienummersGroepBericht();
        pid2.setBurgerservicenummer(new Burgerservicenummer("1230"));
        persoon2.setIdentificatienummers(pid2);

        PersoonBericht persoon3 = new PersoonBericht();
        persoon3.setVerzendendId("Persoon3");
        PersoonIdentificatienummersGroepBericht pid3 = new PersoonIdentificatienummersGroepBericht();
        persoon3.setIdentificatienummers(pid3);

        PersoonBericht persoon4 = new PersoonBericht();
        persoon4.setVerzendendId("Persoon4");


        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersonenToe(Arrays.asList((Persoon) persoon4, (Persoon) persoon3, (Persoon) persoon1, (Persoon) persoon2));


        Object[] personen = resultaat.getBijgehoudenPersonen().toArray();
        Assert.assertEquals("Persoon2", ((PersoonBericht) personen[0]).getVerzendendId());
        Assert.assertEquals("Persoon1", ((PersoonBericht) personen[1]).getVerzendendId());
        Assert.assertEquals("Persoon3", ((PersoonBericht) personen[2]).getVerzendendId());
        Assert.assertEquals("Persoon4", ((PersoonBericht) personen[3]).getVerzendendId());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienPersonenLijstWordtAangepastVanBuiten() {
        List<Persoon> personen = new ArrayList<Persoon>();
        personen.add(new PersoonBericht());
        personen.add(new PersoonBericht());

        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        resultaat.voegPersonenToe(personen);
        resultaat.getBijgehoudenPersonen().add(new PersoonBericht());
    }

    @Test
    public void testGettersEnSetters() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Calendar now = Calendar.getInstance();
        resultaat.setTijdstipRegistratie(now.getTime());
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());

        now.add(Calendar.MONTH, 2);
        resultaat.setTijdstipRegistratie(now.getTime());
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());

        resultaat.setTijdstipRegistratie(null);
        Assert.assertNull(resultaat.getTijdstipRegistratie());
    }
}
