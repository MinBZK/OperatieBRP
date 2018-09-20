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


public class R1264Test {

    private static final String POPULATIE_BEPERKING_WAAR = "WAAR";

    private final R1264 regel = new R1264();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.R1264, regel.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AutorisatieRegelContext.class, regel.getContextType());
    }


    @Test
    public void dienstNietGeblokkeerd() {
        final Dienst dienst = TestDienstBuilder.maker().metIndicatieGeblokkeerd(false).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(null, dienst,
                null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    @Test
    public void dienstGeblokkeerd() {
        final Dienst dienst = TestDienstBuilder.maker().metIndicatieGeblokkeerd(true).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(null, dienst,
                null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertFalse(regel.valideer(regelContext));
    }

    @Test
    public void dienstVerlopenMaarNietGeblokkeerd() {
        final Dienst dienst = TestDienstBuilder.maker().metIndicatieGeblokkeerd(false).metDatumIngang(DatumAttribuut.morgen()).maak();
        final AutorisatieRegelContext regelContext =
                new AutorisatieRegelContext(null, dienst,
                        null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(regel.valideer(regelContext));
    }
}
