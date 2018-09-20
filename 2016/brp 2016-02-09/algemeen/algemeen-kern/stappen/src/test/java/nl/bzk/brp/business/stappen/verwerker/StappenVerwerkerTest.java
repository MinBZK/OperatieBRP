/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.verwerker;

import static nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding.FOUT;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.AbstractStappenTestBasis;
import nl.bzk.brp.business.stappen.support.BrpStap;
import nl.bzk.brp.business.stappen.support.BrpStapContext;
import nl.bzk.brp.business.stappen.support.BrpStapOnderwerp;
import nl.bzk.brp.business.stappen.support.BrpStapResultaat;
import nl.bzk.brp.business.stappen.support.BrpStappenVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor een stappenverwerker
 */
public class StappenVerwerkerTest extends AbstractStappenTestBasis {

    private BrpStappenVerwerker stappenVerwerker;

    private BrpStapContext stapContext;

    private BrpStapOnderwerp stapOnderwerp;

    private List<BrpStap> stappen;

    @Before
    public void setUp() {
        stappenVerwerker = new BrpStappenVerwerker();
        Assert.assertTrue(stappenVerwerker.getStappen() == null);
        stapContext = bouwStapContext();
        stapOnderwerp = new BrpStapOnderwerp();
        stappen = new ArrayList<>();
    }

    @Test
    public void testVerwerkingZonderStappen() {
        stappenVerwerker.setStappen(null);
        final BrpStapResultaat resultaat = stappenVerwerker.verwerk(stapOnderwerp, stapContext);
        Assert.assertTrue(resultaat.isSuccesvol());
    }

    @Test
    public void testSuccesvolleStap() {
        stappen.add(new SuccesvolleStap());
        stappenVerwerker.setStappen(stappen);
        final BrpStapResultaat resultaat = stappenVerwerker.verwerk(stapOnderwerp, stapContext);
        Assert.assertTrue(resultaat.isSuccesvol());
    }

    @Test
    public void testStapMetFoutMeldingInResultaat() {
        stappen.add(new TestStapMetFoutMelding());
        stappenVerwerker.setStappen(stappen);
        Assert.assertTrue(stappenVerwerker.getStappen() != null);
        final BrpStapResultaat resultaat = stappenVerwerker.verwerk(stapOnderwerp, stapContext);
        Assert.assertTrue(resultaat.isFoutief());
    }

    @Test
    public void testExceptieInStap() {
        stappen.add(new ExceptieInStap());
        stappenVerwerker.setStappen(stappen);
        Assert.assertTrue(stappenVerwerker.getStappen() != null);
        final BrpStapResultaat resultaat = stappenVerwerker.verwerk(stapOnderwerp, stapContext);
        Assert.assertTrue(resultaat.isFoutief());
    }

    @Test
    public void testExceptieInNabewerking() {
        stappen.add(new ExceptieInNabewerkingStap());
        stappenVerwerker.setStappen(stappen);
        Assert.assertTrue(stappenVerwerker.getStappen() != null);
        final BrpStapResultaat resultaat = stappenVerwerker.verwerk(stapOnderwerp, stapContext);
        Assert.assertTrue(resultaat.isFoutief());
    }

    private class SuccesvolleStap extends AbstractStap<BrpStapOnderwerp, BrpStapContext, BrpStapResultaat>
            implements BrpStap
    {

        @Override
        public boolean voerStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {
            return true;
        }

    }


    private class TestStapMetFoutMelding extends AbstractStap<BrpStapOnderwerp, BrpStapContext, BrpStapResultaat>
            implements BrpStap
    {

        @Override
        public boolean voerStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {
            resultaat.voegMeldingToe(new Melding(FOUT, Regel.ALG0001, "Fout in stap"));

            return true;
        }

        @Override
        public void voerNabewerkingStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {

        }
    }


    private class ExceptieInNabewerkingStap extends AbstractStap<BrpStapOnderwerp, BrpStapContext, BrpStapResultaat>
            implements BrpStap
    {

        @Override
        public boolean voerStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {
            return true;
        }

        @Override
        public void voerNabewerkingStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {
            throw new IllegalArgumentException("Onderwerp bevat iets vreemds, stap moet fout gaan in naverwerking!");
        }
    }


    private class ExceptieInStap extends AbstractStap<BrpStapOnderwerp, BrpStapContext, BrpStapResultaat>
            implements BrpStap
    {

        @Override
        public boolean voerStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {
            throw new IllegalArgumentException("Onderwerp bevat iets vreemds, stap moet fout gaan in de verwerking!");
        }

        @Override
        public void voerNabewerkingStapUit(final BrpStapOnderwerp onderwerp, final BrpStapContext context,
                final BrpStapResultaat resultaat)
        {

        }
    }

}
