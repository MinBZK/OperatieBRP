/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test klasse waarin de functionaliteit van de {@link FormeleHistorieEntiteitComparator} wordt getest.
 */
public class FormeleHistorieEntiteitComparatorTest {

    private static final DateFormat DATUM_FORMATTER            = new SimpleDateFormat("yyMMdd");
    private static final String     REGISTRATIE_5_MEI_2005     = "050505";
    private static final String     REGISTRATIE_2_FEB_2002     = "020202";
    private static final String     REGISTRATIE_3_MAART_2003   = "030303";
    private static final String     REGISTRATIE_1_JANUARI_2001 = "010101";
    private static final String     REGISTRATIE_1_JANUARI_0    = "000101";
    private static final String     REGISTRATIE_4_APRIL_2004   = "040404";

    private int testIndex;

    private final FormeleHistorieEntiteitComparator<TestEntiteit> comparator = new FormeleHistorieEntiteitComparator<>();

    @Before
    public void init() {
        testIndex = 1;
    }

    /**
     * Indien beide een verschillende Verwerkingssoort hebben, dienen de entiteiten op volgorde van de {@link Verwerkingssoort} enumeratie te worden
     * gesorteerd.
     */
    @Test
    public void testVerschillendeVerwerkingssoortSortering() throws ParseException {
        final List<TestEntiteit> collectie =
            Arrays.asList(new TestEntiteit(Verwerkingssoort.TOEVOEGING), new TestEntiteit(Verwerkingssoort.VERVAL),
                new TestEntiteit(Verwerkingssoort.VERWIJDERING), new TestEntiteit(Verwerkingssoort.WIJZIGING),
                new TestEntiteit(Verwerkingssoort.IDENTIFICATIE));
        Collections.sort(collectie, comparator);

        Assert.assertEquals(Verwerkingssoort.IDENTIFICATIE, collectie.get(0).getVerwerkingssoort());
        Assert.assertEquals(Verwerkingssoort.TOEVOEGING, collectie.get(1).getVerwerkingssoort());
        Assert.assertEquals(Verwerkingssoort.WIJZIGING, collectie.get(2).getVerwerkingssoort());
        Assert.assertEquals(Verwerkingssoort.VERVAL, collectie.get(3).getVerwerkingssoort());
        Assert.assertEquals(Verwerkingssoort.VERWIJDERING, collectie.get(4).getVerwerkingssoort());
    }

    /**
     * Indien beide een gelijke Verwerkingssoort hebben, dienen de entiteiten op volgorde van het tijdstip van registratie gesorteerd te worden.
     */
    @Test
    public void testGelijkeVerwerkingssoortVerschillendeTijdstipRegistratieSortering() throws ParseException {
        final List<TestEntiteit> collectie =
            Arrays.asList(new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_5_MEI_2005), new TestEntiteit(
                Verwerkingssoort.TOEVOEGING, REGISTRATIE_2_FEB_2002),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_3_MAART_2003),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_4_APRIL_2004));
        Collections.sort(collectie, comparator);

        Assert.assertEquals(4, collectie.get(4).getIndex());
        Assert.assertEquals(2, collectie.get(3).getIndex());
        Assert.assertEquals(3, collectie.get(2).getIndex());
        Assert.assertEquals(5, collectie.get(1).getIndex());
        Assert.assertEquals(1, collectie.get(0).getIndex());
    }

    /**
     * Indien beide een gelijke Verwerkingssoort hebben en een gelijke tijdstipregistratie, dan dienen de entiteiten op volgorde van het tijdstip vervallen
     * gesorteerd te worden.
     */
    @Test
    public void testGelijkeVerwerkingssoortEnRegistratieVerschillendeTijdstipVervallenSortering()
        throws ParseException
    {
        final List<TestEntiteit> collectie =
            Arrays.asList(new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_1_JANUARI_0, REGISTRATIE_4_APRIL_2004), new TestEntiteit(
                Verwerkingssoort.TOEVOEGING, REGISTRATIE_1_JANUARI_0, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING,
                    REGISTRATIE_1_JANUARI_0, REGISTRATIE_5_MEI_2005),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_1_JANUARI_0, REGISTRATIE_3_MAART_2003),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_1_JANUARI_0, REGISTRATIE_2_FEB_2002));
        Collections.sort(collectie, comparator);

        Assert.assertEquals(2, collectie.get(4).getIndex());
        Assert.assertEquals(5, collectie.get(3).getIndex());
        Assert.assertEquals(4, collectie.get(2).getIndex());
        Assert.assertEquals(1, collectie.get(1).getIndex());
        Assert.assertEquals(3, collectie.get(0).getIndex());
    }

    @Test
    public void testCompleet() throws ParseException {
        final List<TestEntiteit> collectie =
            Arrays.asList(new TestEntiteit(Verwerkingssoort.VERVAL, REGISTRATIE_1_JANUARI_2001), new TestEntiteit(
                Verwerkingssoort.TOEVOEGING, REGISTRATIE_3_MAART_2003),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_2_FEB_2002,
                    REGISTRATIE_5_MEI_2005), new TestEntiteit(Verwerkingssoort.IDENTIFICATIE, REGISTRATIE_2_FEB_2002, REGISTRATIE_5_MEI_2005),
                new TestEntiteit(
                    Verwerkingssoort.WIJZIGING, REGISTRATIE_3_MAART_2003, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.WIJZIGING, null,
                    REGISTRATIE_1_JANUARI_2001), new TestEntiteit(Verwerkingssoort.WIJZIGING, REGISTRATIE_3_MAART_2003, REGISTRATIE_5_MEI_2005),
                new TestEntiteit(
                    Verwerkingssoort.WIJZIGING, REGISTRATIE_3_MAART_2003, REGISTRATIE_2_FEB_2002),
                new TestEntiteit(Verwerkingssoort.WIJZIGING,
                    REGISTRATIE_1_JANUARI_2001, REGISTRATIE_2_FEB_2002),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_2_FEB_2002, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.TOEVOEGING, REGISTRATIE_3_MAART_2003, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.WIJZIGING, REGISTRATIE_1_JANUARI_2001, REGISTRATIE_1_JANUARI_2001),
                new TestEntiteit(Verwerkingssoort.WIJZIGING, REGISTRATIE_1_JANUARI_2001, null),
                new TestEntiteit(Verwerkingssoort.WIJZIGING,
                    REGISTRATIE_1_JANUARI_2001, REGISTRATIE_4_APRIL_2004),
                new TestEntiteit(Verwerkingssoort.VERVAL, REGISTRATIE_2_FEB_2002, REGISTRATIE_2_FEB_2002),
                new TestEntiteit(Verwerkingssoort.IDENTIFICATIE, REGISTRATIE_1_JANUARI_2001, REGISTRATIE_1_JANUARI_2001));
        Collections.sort(collectie, comparator);

        Assert.assertEquals(4, collectie.get(0).getIndex());
        Assert.assertEquals(16, collectie.get(1).getIndex());
        Assert.assertEquals(2, collectie.get(2).getIndex());
        Assert.assertEquals(11, collectie.get(3).getIndex());
        Assert.assertEquals(3, collectie.get(4).getIndex());
        Assert.assertEquals(10, collectie.get(5).getIndex());
        Assert.assertEquals(6, collectie.get(6).getIndex());
        Assert.assertEquals(7, collectie.get(7).getIndex());
        Assert.assertEquals(8, collectie.get(8).getIndex());
        Assert.assertEquals(5, collectie.get(9).getIndex());
        Assert.assertEquals(13, collectie.get(10).getIndex());
        Assert.assertEquals(14, collectie.get(11).getIndex());
        Assert.assertEquals(9, collectie.get(12).getIndex());
        Assert.assertEquals(12, collectie.get(13).getIndex());
        Assert.assertEquals(15, collectie.get(14).getIndex());
        Assert.assertEquals(1, collectie.get(15).getIndex());
    }

    /**
     * Retourneert een {@link DatumTijdAttribuut} instantie op basis van opgegeven datum/tijd string.
     *
     * @param tijdstipString de tekstuele vorm van het tijdstip dat moet worden geretourneerd.
     * @return een DatumTijd instantie.
     */
    private DatumTijdAttribuut bouwDatumTijd(final String tijdstipString) throws ParseException {
        final Date tijdstip;
        if (tijdstipString != null) {
            tijdstip = DATUM_FORMATTER.parse(tijdstipString);
        } else {
            tijdstip = null;
        }
        return new DatumTijdAttribuut(tijdstip);
    }

    /**
     * Helper klasse die de {@link AbstractFormeelHistorischMetActieVerantwoording} klasse extend en hulp methodes en constructors biedt om eenvoudig
     * {@link AbstractFormeelHistorischMetActieVerantwoording} instanties aan te maken om te testen.
     */
    class TestEntiteit extends AbstractFormeelHistorischMetActieVerantwoording {

        private int index;

        /**
         * Standaard constructor die alleen de Verwerkingssoort zet.
         *
         * @param soort de Verwerkingssoort
         */
        public TestEntiteit(final Verwerkingssoort soort) throws ParseException {
            this(soort, null, null);
        }

        /**
         * Standaard constructor die zowel de Verwerkingssoort zet als het tijdstip van registratie.
         *
         * @param soort       de Verwerkingssoort.
         * @param registratie het tijdstip van registratie.
         */
        public TestEntiteit(final Verwerkingssoort soort, final String registratie) throws ParseException {
            this(soort, registratie, null);
        }

        /**
         * Standaard constructor die zowel de Verwerkingssoort zet, als het tijdstip van registratie en vervallen.
         *
         * @param soort       de Verwerkingssoort.
         * @param registratie het tijdstip van registratie.
         * @param vervallen   het tijdstip van vervallen.
         */
        public TestEntiteit(final Verwerkingssoort soort, final String registratie, final String vervallen)
            throws ParseException
        {
            this.index = testIndex++;
            this.setVerwerkingssoort(soort);
            this.getFormeleHistorie().setDatumTijdRegistratie(bouwDatumTijd(registratie));
            this.getFormeleHistorie().setDatumTijdVerval(bouwDatumTijd(vervallen));
        }

        /**
         * Retourneert de index van deze entiteit (is test specifiek).
         */
        public int getIndex() {
            return index;
        }
    }
}
