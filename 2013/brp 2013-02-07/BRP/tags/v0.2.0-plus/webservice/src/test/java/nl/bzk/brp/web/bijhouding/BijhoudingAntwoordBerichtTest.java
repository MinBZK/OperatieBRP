/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Test;

/** Unit test klasse voor de methodes in de {@link BijhoudingAntwoordBericht} klasse. */
public class BijhoudingAntwoordBerichtTest {

    @Test
    public void testConstructor() {
        BijhoudingAntwoordBericht antwoordBericht =
            new BijhoudingAntwoordBericht(bouwBijhoudingResultaat());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
        Assert.assertEquals(Long.valueOf(2006032510072412L), antwoordBericht.getTijdstipRegistratie());
        Assert.assertNull(antwoordBericht.getBijgehoudenPersonen());
    }

    @Test
    public void testStandaardGettersEnSetters() {
        BijhoudingAntwoordBericht antwoordBericht =
            new BijhoudingAntwoordBericht(new BijhoudingResultaat(null));

        antwoordBericht.setTijdstipRegistratie(12L);
        Assert.assertEquals(Long.valueOf(12L), antwoordBericht.getTijdstipRegistratie());
    }

    @Test
    public void testSetTijdstipRegistratieMetDatum()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method methode =
            BijhoudingAntwoordBericht.class.getDeclaredMethod("setTijdstipRegistratieMetDatum", Date.class);
        methode.setAccessible(true);

        BijhoudingAntwoordBericht antwoordBericht =
            new BijhoudingAntwoordBericht(bouwBijhoudingResultaat());

        methode.invoke(antwoordBericht, bouwTijdstip(2008, 6, 28, 7, 10, 55, 456));
        Assert.assertEquals(Long.valueOf(2008072807105545L), antwoordBericht.getTijdstipRegistratie());

        methode.invoke(antwoordBericht, (Date) null);
        Assert.assertNull(antwoordBericht.getTijdstipRegistratie());
    }

    @Test
    public void testBijhoudingCodeGoedVerwerktBericht() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertNotNull(antwoordBericht.getBijhoudingCode());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
    }

    @Test
    public void testBijhoudingCodeFoutVerwerktBericht() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        resultaat.markeerVerwerkingAlsFoutief();
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertNull(antwoordBericht.getBijhoudingCode());
    }

    @Test
    public void testBijhoudingCodePrevalidatieZonderMeldingen() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertNotNull(antwoordBericht.getBijhoudingCode());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
    }

    @Test
    public void testBijhoudingCodePrevalidatieMetWaarschuwingen() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertNotNull(antwoordBericht.getBijhoudingCode());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
    }

    @Test
    public void testBijhoudingCodePrevalidatieMetOverrulebareFout() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001));
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertNotNull(antwoordBericht.getBijhoudingCode());
        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
    }

    @Test
    public void testBijhoudingCodePrevalidatieMetOnoverrulebareFout() {
        BijhoudingResultaat resultaat = bouwBijhoudingResultaat();
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001));
        BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBericht(resultaat);

        Assert.assertEquals(BijhoudingCode.DIRECT_VERWERKT, antwoordBericht.getBijhoudingCode());
    }


    /** Instantieert en retourneert een gevulde {@link BijhoudingResultaat} instantie. */
    private BijhoudingResultaat bouwBijhoudingResultaat() {
        BijhoudingResultaat bijhoudingResultaat = new BijhoudingResultaat(null);
        bijhoudingResultaat.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bijhoudingResultaat.setTijdstipRegistratie(bouwTijdstip(2006, 2, 25, 10, 7, 24, 123));
        bijhoudingResultaat.setBijhoudingCode(BijhoudingCode.DIRECT_VERWERKT);
        return bijhoudingResultaat;
    }

    private Date bouwTijdstip(final int jaar, final int maand, final int dag, final int uur, final int minuut,
        final int seconde, final int milliseconde)
    {
        Calendar tijdstip = new GregorianCalendar();
        tijdstip.set(Calendar.YEAR, jaar);
        tijdstip.set(Calendar.MONTH, maand);
        tijdstip.set(Calendar.DAY_OF_MONTH, dag);
        tijdstip.set(Calendar.HOUR, uur);
        tijdstip.set(Calendar.MINUTE, minuut);
        tijdstip.set(Calendar.SECOND, seconde);
        tijdstip.set(Calendar.MILLISECOND, milliseconde);
        tijdstip.set(Calendar.AM_PM, Calendar.AM);
        return tijdstip.getTime();
    }

}
