/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.impl.levering.afnemerindicatie.BRLV0001;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
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

    private BedrijfsregelManagerImpl getBedrijfsregelManagerImpl() {
        return (BedrijfsregelManagerImpl) bedrijfsregelManager;
    }

    @Test
    public final void testGetUitTeVoerenRegelsVoorBericht() {
        final List<RegelInterface> uitTeVoerenRegelsVoorBerichtSynPersoon =
            getBedrijfsregelManagerImpl()
                .getUitTeVoerenRegelsVoorBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON);
        Assert.assertTrue(uitTeVoerenRegelsVoorBerichtSynPersoon.isEmpty());


        final List<RegelInterface> uitTeVoerenRegelsVoorBerichtSynStamgegeven =
            getBedrijfsregelManagerImpl()
                .getUitTeVoerenRegelsVoorBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN);
        Assert.assertFalse(uitTeVoerenRegelsVoorBerichtSynStamgegeven.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testGetUitTeVoerenRegelsNaBericht() {
        getBedrijfsregelManagerImpl().getUitTeVoerenRegelsNaBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testGetUitTeVoerenRegelsVoorActie() {
        getBedrijfsregelManagerImpl().getUitTeVoerenRegelsVoorActie(SoortAdministratieveHandeling.DUMMY,
            SoortActie.DUMMY);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testGetUitTeVoerenRegelsNaActie() {
        getBedrijfsregelManagerImpl().getUitTeVoerenRegelsNaActie(SoortAdministratieveHandeling.DUMMY,
            SoortActie.DUMMY);
    }

    @Test
    public final void testGetUitTeVoerenRegelsVoorVerwerking() {
        final List<RegelInterface> uitTeVoerenRegelsVoorVerwerking =
            getBedrijfsregelManagerImpl()
                .getUitTeVoerenRegelsVoorVerwerking(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE);

        Assert.assertNotNull(uitTeVoerenRegelsVoorVerwerking);
    }

    @Test
    public final void testGetUitTeVoerenRegelsNaVerwerking() {
        final List<RegelInterface> uitTeVoerenRegelsNaVerwerking =
            getBedrijfsregelManagerImpl()
                .getUitTeVoerenRegelsNaVerwerking(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE);

        Assert.assertNotNull(uitTeVoerenRegelsNaVerwerking);
    }

    @Test
    public final void testGetRegelParametersVoorRegel() {
        final RegelParameters resultaat = getBedrijfsregelManagerImpl().getRegelParametersVoorRegel(Regel.BRLV0001);
        Assert.assertEquals(Regel.BRLV0001, resultaat.getRegelCode());
    }

    @Test
    public final void testGetRegelParametersVoorRegelInterface() {
        final RegelInterface regel = new BRLV0001();
        final RegelParameters resultaat = getBedrijfsregelManagerImpl().getRegelParametersVoorRegel(regel);
        Assert.assertEquals(Regel.BRLV0001, resultaat.getRegelCode());
    }
}
