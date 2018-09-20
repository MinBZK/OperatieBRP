/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0194.
 */
public class BRBY0194Test {

    private BRBY0194 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0194();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0194, bedrijfsregel.getRegel());
    }

    @Test
    public void testGeenVerstrekkingsbeperkingen() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenGemeenteVerordening() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGemeenteVerordeningIsWelGemeente() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGemeenteVerordeningIsGeenGemeente() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false), null, null);
        Assert.assertEquals(1, meldingen.size());
    }

    private PersoonBericht bouwPersoon(final Boolean gemeenteVerordeningIsGemeente) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
        PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking = new PersoonVerstrekkingsbeperkingBericht();
        if (gemeenteVerordeningIsGemeente != null) {
            verstrekkingsbeperking.setGemeenteVerordening(new PartijAttribuut(bouwPartij(gemeenteVerordeningIsGemeente)));
        }
        persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        return persoon;
    }

    private Partij bouwPartij(final boolean gemeenteVerordeningIsGemeente) {
        SoortPartij soortPartij = SoortPartij.GEMEENTE;
        if (!gemeenteVerordeningIsGemeente) {
            soortPartij = SoortPartij.DERDE;
        }
        return TestPartijBuilder.maker().metSoort(soortPartij).maak();
    }
}
