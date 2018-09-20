/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;

public class VerstrekkingsbeperkingRegelContextTest {

    private static final Partij PARTIJ = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde();

    @Test
    public void testGetHuidigeSituatie() {
        final PersoonView persoon = maakHuidigeSituatie();
        final VerstrekkingsbeperkingRegelContext context =
            new VerstrekkingsbeperkingRegelContext(persoon, PARTIJ);
        final PersoonView resultaat = context.getHuidigeSituatie();

        Assert.assertEquals(persoon, resultaat);
        Assert.assertEquals(persoon, context.getSituatie());
    }

    @Test
    public void testGetPartij() {
        final PersoonView persoon = maakHuidigeSituatie();
        final VerstrekkingsbeperkingRegelContext context =
            new VerstrekkingsbeperkingRegelContext(persoon, PARTIJ);
        final Partij resultaat = context.getPartij();

        Assert.assertEquals(PARTIJ, resultaat);
    }

    /**
     * Maakt een huidige situatie..
     *
     * @return persoon view
     */
    private PersoonView maakHuidigeSituatie() {
        return new PersoonView(TestPersoonJohnnyJordaan.maak());
    }
}
