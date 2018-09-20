/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering;

import nl.bzk.brp.business.regels.context.HuidigeSituatieRegelContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRLV0022Test {

    @Test
    public void testValideerHuidigeSituatieOntbreekt() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(null);
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        Assert.assertTrue(resultaat);
    }

    @Test
    public void testValideerPersoonIsNietIngeschrevene() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(maakHuidigeSituatie(
                SoortPersoon.NIET_INGESCHREVENE, NadereBijhoudingsaard.ACTUEEL));
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        Assert.assertTrue(resultaat);
    }

    @Test
    public void testValideerNaderebijhoudingsaardOnbekend() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(maakHuidigeSituatie(
                SoortPersoon.INGESCHREVENE, NadereBijhoudingsaard.ONBEKEND));
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        Assert.assertFalse(resultaat);
    }

    @Test
    public void testValideerNaderebijhoudingsaardOntbrekend() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(maakHuidigeSituatie(
            SoortPersoon.INGESCHREVENE, null));
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        Assert.assertTrue(resultaat);
    }

    @Test
    public void testValideerNaderebijhoudingsaardFout() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(maakHuidigeSituatie(
                SoortPersoon.INGESCHREVENE, NadereBijhoudingsaard.FOUT));
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        Assert.assertFalse(resultaat);
    }

    @Test
    public void testValideerNaderebijhoudingsaardActueel() {
        final HuidigeSituatieRegelContext regelContext = new HuidigeSituatieRegelContext(maakHuidigeSituatie(
                SoortPersoon.INGESCHREVENE, NadereBijhoudingsaard.ACTUEEL));
        final boolean resultaat = new BRLV0022().valideer(regelContext);
        System.out.println(resultaat);
        Assert.assertTrue(resultaat);
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(HuidigeSituatieRegelContext.class, new BRLV0022().getContextType());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0022, new BRLV0022().getRegel());
    }

    private PersoonView maakHuidigeSituatie(final SoortPersoon soortPersoon,
                                            final NadereBijhoudingsaard nadereBijhoudingsaard)
    {
        final PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(
                soortPersoon)
                .nieuwBijhoudingRecord(20100101, null, 20100101).nadereBijhoudingsaard(nadereBijhoudingsaard)
                .eindeRecord()
                .build();
        return new PersoonView(persoonHisVolledig);
    }
}
