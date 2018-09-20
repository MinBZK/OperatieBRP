/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0192.
 */
public class BRBY0192Test {

    private BRBY0192 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0192();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0192, bedrijfsregel.getRegel());
    }

    @Test
    public void testGeenVerstrekkingsbeperkingen() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenPartij() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWelVerstrekkingsbeperkingMogelijk() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenVerstrekkingsbeperkingMogelijk() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false), null, null);
        Assert.assertEquals(1, meldingen.size());
    }

    private PersoonBericht bouwPersoon(final Boolean verstrekkingsbeperkingMogelijk) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
        PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking = new PersoonVerstrekkingsbeperkingBericht();
        if (verstrekkingsbeperkingMogelijk != null) {
            verstrekkingsbeperking.setPartij(new PartijAttribuut(bouwPartij(verstrekkingsbeperkingMogelijk)));
        }
        persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        return persoon;
    }

    private Partij bouwPartij(final boolean verstrekkingsbeperkingMogelijk) {
        return TestPartijBuilder.maker()
            .metIndicatieVerstrekkingsbeperkingMogelijk(new JaNeeAttribuut(verstrekkingsbeperkingMogelijk))
            .maak();
    }
}
