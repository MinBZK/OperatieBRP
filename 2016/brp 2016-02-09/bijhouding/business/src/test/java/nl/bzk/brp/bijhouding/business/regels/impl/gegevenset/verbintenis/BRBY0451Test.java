/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0451Test {

    private Gemeente geldigeGemeente;
    private Gemeente nietGeldigeGemeente;
    private BRBY0451 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0451();

        geldigeGemeente = new Gemeente(null, null, null, null,
                                       new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()),
                                       new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()));
        nietGeldigeGemeente = new Gemeente(null, null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()),
                                           new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0451, bedrijfsregel.getRegel());
    }

    @Test
    public void testGemeenteEindeGeldigOpDatumEinde() {
        final List<BerichtEntiteit> berichtEntiteits =
            bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(
                    new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), geldigeGemeente), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGemeenteEindeOnGeldigOpDatumEinde() {
        final List<BerichtEntiteit> berichtEntiteits =
                bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), nietGeldigeGemeente), null, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(
            final DatumEvtDeelsOnbekendAttribuut datumEindeRelatie,
            final Gemeente gemeenteEinde)
    {
        HuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkBericht.getStandaard().setDatumEinde(datumEindeRelatie);
        huwelijkBericht.getStandaard().setGemeenteEinde(new GemeenteAttribuut(gemeenteEinde));
        return huwelijkBericht;
    }
}
