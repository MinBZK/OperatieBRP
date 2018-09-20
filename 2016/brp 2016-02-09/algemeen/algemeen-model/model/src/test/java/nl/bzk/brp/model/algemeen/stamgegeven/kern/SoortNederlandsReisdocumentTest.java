/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test klasse voor de methodes in {@link SoortNederlandsReisdocument}.
 */
public class SoortNederlandsReisdocumentTest {

    @Test
    public void testCompare() {
        final SoortNederlandsReisdocument document = bouwDocument("2", null, null, null);

        Assert.assertTrue(document.compareTo(document) == 0);
        Assert.assertTrue(document.compareTo(bouwDocument("1", null, null, null)) > 0);
        Assert.assertTrue(document.compareTo(bouwDocument("3", null, null, null)) < 0);
        Assert.assertTrue(document.compareTo(bouwDocument("2", null, null, null)) == 0);
        Assert.assertTrue(document.compareTo(bouwDocument("2", "test", 20100101, null)) == 0);
        Assert.assertTrue(document.compareTo(bouwDocument("2", "test", 20100101, 20130603)) == 0);
    }

    @Test
    public void testEquals() {
        final SoortNederlandsReisdocument document = bouwDocument("2", null, null, null);

        Assert.assertNotEquals(document, null);
        Assert.assertFalse(document.equals(new Object()));
        Assert.assertTrue(document.equals(document));
        Assert.assertFalse(document.equals(bouwDocument("1", null, null, null)));
        Assert.assertFalse(document.equals(bouwDocument("3", null, null, null)));
        Assert.assertTrue(document.equals(bouwDocument("2", null, null, null)));
        Assert.assertTrue(document.equals(bouwDocument("2", "test", 20100101, null)));
        Assert.assertTrue(document.equals(bouwDocument("2", "test", 20100101, 20130603)));
    }

    @Test
    public void testHashCode() {
        final SoortNederlandsReisdocument document = bouwDocument("2", null, null, null);

        Assert.assertEquals(document.hashCode(), document.hashCode());
        Assert.assertEquals(document.hashCode(), bouwDocument("2", null, null, null).hashCode());
        Assert.assertEquals(document.hashCode(), bouwDocument("2", "test", 20100101, null).hashCode());
        Assert.assertNotEquals(document.hashCode(), bouwDocument("3", null, null, null).hashCode());
    }

    /**
     * Retourneert een {@link SoortNederlandsReisdocument} instantie met opgegeven waardes.
     *
     * @param code    de code van het document.
     * @param omschr  de omschrijving voor het document (optioneel).
     * @param datAanv de datum aanvang als integer (optioneel).
     * @param datEind de datum eind als integer (optioneel).
     * @return een SoortNederlandsReisdocument instantie.
     */
    private SoortNederlandsReisdocument bouwDocument(final String code, final String omschr, final Integer datAanv,
            final Integer datEind)
    {
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving;
        if (omschr != null) {
            omschrijving = new OmschrijvingEnumeratiewaardeAttribuut(omschr);
        } else {
            omschrijving = null;
        }
        return new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut(code), omschrijving,
                bouwDatum(datAanv), bouwDatum(datEind));
    }

    /**
     * Retourneert een {@link DatumEvtDeelsOnbekendAttribuut} instantie met de opgegeven datum of <code>null</code> als
     * de opgegeven datum ook <code>null</code> is.
     *
     * @param dat de datum als integer.
     * @return een Datum instantie.
     */
    private DatumEvtDeelsOnbekendAttribuut bouwDatum(final Integer dat) {
        final DatumEvtDeelsOnbekendAttribuut datum;
        if (dat != null) {
            datum = new DatumEvtDeelsOnbekendAttribuut(dat);
        } else {
            datum = null;
        }
        return datum;
    }
}
