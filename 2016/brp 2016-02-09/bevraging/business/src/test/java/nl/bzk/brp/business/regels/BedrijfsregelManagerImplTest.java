/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.business.regels.bevraging.BRBV0001;
import nl.bzk.brp.dataaccess.repository.jpa.RegelRepositoryImpl;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BedrijfsregelManagerImplTest {

    @Inject
    private BedrijfsregelManagerImpl bedrijfsregelManager;

    @Before
    public final void init() throws Exception {
        ReflectionTestUtils.setField(bedrijfsregelManager, "regelRepository", new RegelRepositoryImpl());
    }
    @Ignore
    public final void testGetUitTeVoerenRegelsVoorBericht() throws Exception {
        Assert.assertEquals(1, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON).size());
        Assert.assertEquals(1, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON).size());

        Assert.assertEquals(BRBV0001.class, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON).get(0).getClass());

        Assert.assertEquals(0, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_BEPAAL_KANDIDAAT_VADER).size());
    }
    @Ignore
    public final void testGetUitTeVoerenRegelsVoorVerwerking() throws Exception {
        Assert.assertEquals(2, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON).size());
        Assert.assertEquals(2, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON).size());
        Assert.assertEquals(0, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_BEPAAL_KANDIDAAT_VADER).size());
    }
    @Ignore
    public final void testGetUitTeVoerenRegelsNaVerwerking() throws Exception {
        // nog geen na verwerking regels aanwezig, dus geen regels geconfigureerd
        Assert.assertEquals(0, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON).size());
        Assert.assertEquals(0, bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(
            SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON).size());
    }

    @Test
    public final void testGetRegelParametersVoorRegel() throws Exception {
        final RegelParameters regelParametersVoorRegel = bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012);
        Assert.assertNotNull(regelParametersVoorRegel);
        Assert.assertEquals(Regel.BRAL0012, regelParametersVoorRegel.getRegelCode());
    }

    @Test
    public final void testGetRegelParametersVoorRegelInterface() throws Exception {
        final RegelInterface regelInterface = new VoorBerichtRegel<GeefDetailsPersoonBericht>() {
            @Override
            public final Regel getRegel() {
                return Regel.BRAL0012;
            }

            @Override
            public List<BerichtIdentificeerbaar> voerRegelUit(final GeefDetailsPersoonBericht bericht) {
                return null;
            }
        };

        final RegelParameters regelParametersVoorRegel = bedrijfsregelManager.getRegelParametersVoorRegel(regelInterface);
        Assert.assertNotNull(regelParametersVoorRegel);
        Assert.assertEquals(Regel.BRAL0012, regelParametersVoorRegel.getRegelCode());
    }
}
