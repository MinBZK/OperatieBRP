/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import javax.inject.Inject;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.impl.levering.afnemerindicatie.BRLV0001;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("RegelManagerIntegratieTest-context.xml")
public class BedrijfsregelManagerImplTest {

    @Inject
    private BedrijfsregelManager bedrijfsregelManager;

    @Test
    public final void testGetRegelParametersVoorRegel() {
        final RegelParameters resultaat = bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRLV0001);
        Assert.assertEquals(Regel.BRLV0001, resultaat.getRegelCode());
    }

    @Test
    public final void testGetRegelParametersVoorRegelInterface() {
        final RegelInterface regel = new BRLV0001();
        final RegelParameters resultaat = bedrijfsregelManager.getRegelParametersVoorRegel(regel);
        Assert.assertEquals(Regel.BRLV0001, resultaat.getRegelCode());
    }
}
