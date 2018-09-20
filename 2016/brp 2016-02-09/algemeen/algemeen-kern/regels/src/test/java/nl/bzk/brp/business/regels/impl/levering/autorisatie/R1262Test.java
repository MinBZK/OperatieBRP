/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Assert;
import org.junit.Test;


public class R1262Test {

    private final R1262 regel = new R1262();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.R1262, regel.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AutorisatieRegelContext.class, regel.getContextType());
    }

    @Test
    public void geenDienst() {
        final Dienst dienst = TestDienstBuilder.maker().metDatumIngang(DatumAttribuut.gisteren()).maak();
        final AutorisatieRegelContext regelContext =
                new AutorisatieRegelContext(null, dienst,
                        null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    @Test
    public void geldigeDienst() {
        final Dienst dienst = TestDienstBuilder.maker().metDatumIngang(DatumAttribuut.gisteren()).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(null, dienst,
                null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    @Test
    public void geldigeOngeldigeDienst() {
        final Dienst dienst = TestDienstBuilder.maker().metDatumIngang(DatumAttribuut.morgen()).maak();
        final AutorisatieRegelContext regelContext =
                new AutorisatieRegelContext(null, dienst,
                        null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        final boolean resultaat = regel.valideer(regelContext);
        Assert.assertFalse(resultaat);
    }
}
