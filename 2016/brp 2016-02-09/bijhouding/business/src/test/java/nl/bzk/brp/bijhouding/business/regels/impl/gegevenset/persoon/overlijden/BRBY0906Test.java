/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0906Test {

    private BRBY0906            brby0906;

    private static final Short BUITENLAND = 1234;

    @Before
    public void init() {
        brby0906 = new BRBY0906();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0906, brby0906.getRegel());
    }

    @Test
    public void testVoerRegelUitMetLandOverlijdenCodeNederland() {
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT);
        final List<BerichtEntiteit> resultaat = brby0906.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetLandOverlijdenCodeAndersDanNederland() {
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(BUITENLAND);
        final List<BerichtEntiteit> resultaat = brby0906.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitZonderLandOverlijdencode() {
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(null);
        final List<BerichtEntiteit> resultaat = brby0906.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitZonderOverlijden() {
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(null);
        nieuweSituatie.setOverlijden(null);
        final List<BerichtEntiteit> resultaat = brby0906.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt de nieuwe situatie: een persoonbericht met (indien bekend) landOverlijdenCode.
     *
     * @return persoon bericht
     */
    private PersoonBericht maakNieuweSituatie(final Short landOverlijdenCode) {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        if (landOverlijdenCode != null) {
            overlijden.setLandGebiedOverlijden(new LandGebiedAttribuut(
                TestLandGebiedBuilder.maker().metCode(landOverlijdenCode).maak()));
        }
        nieuweSituatie.setOverlijden(overlijden);

        return nieuweSituatie;
    }
}
