/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Assert;
import org.junit.Test;


public class R2056Test {

    private static final String POPULATIE_BEPERKING_WAAR = "WAAR";

    private final R2056 regel = new R2056();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.R2056, regel.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AutorisatieRegelContext.class, regel.getContextType());
    }

    @Test
    public void geenDienst() {
        final Dienst dienst = null;
        final AutorisatieRegelContext regelContext =
                new AutorisatieRegelContext(null, dienst,
                        null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    @Test
    public void dienstbundelNietGeblokkeerd() {
        final Dienst dienst = TestDienstBuilder.dummy();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metIndicatieGeblokkeerd(false).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(null, dienst,
                null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    @Test
    public void dienstbundelGeblokkeerd() {
        final Dienst dienst = TestDienstBuilder.dummy();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metIndicatieGeblokkeerd(true).maak();
        final AutorisatieRegelContext regelContext =
                new AutorisatieRegelContext(null, dienst,
                        null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertFalse(regel.valideer(regelContext));
    }

}
