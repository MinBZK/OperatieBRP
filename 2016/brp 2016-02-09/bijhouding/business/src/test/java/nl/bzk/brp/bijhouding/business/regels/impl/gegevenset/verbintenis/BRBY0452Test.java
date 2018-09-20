/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class BRBY0452Test {

    private LandGebied geldigLand;
    private LandGebied nietGeldigLand;
    private BRBY0452 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0452();

        geldigLand = new LandGebied(null, null, null,
                                    new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()),
                                    new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()));
        nietGeldigLand = new LandGebied(null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()),
                                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0452, bedrijfsregel.getRegel());
    }

    @Test
    public void testLandEindeGeldigOpDatumEinde() {
        final List<BerichtEntiteit> berichtEntiteits =
            bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(
                    new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), geldigLand), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testLandEindeOnGeldigOpDatumEinde() {
        final List<BerichtEntiteit> berichtEntiteits =
                bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), nietGeldigLand), null, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(
            final DatumEvtDeelsOnbekendAttribuut datumEindeRelatie, final LandGebied landEinde)
    {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkBericht.getStandaard().setDatumEinde(datumEindeRelatie);
        huwelijkBericht.getStandaard().setLandGebiedEinde(new LandGebiedAttribuut(landEinde));
        return huwelijkBericht;
    }
}
