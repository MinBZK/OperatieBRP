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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0182.
 */
public class BRBY0182Test {

    private BRBY0182 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0182();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0182, bedrijfsregel.getRegel());
    }

    @Test
    public void testGeenVolledigeGeenSpecifieke() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false, false), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenVolledigeWelSpecifieke() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false, true), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWelVolledigeGeenSpecifieke() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true, false), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWelVolledigeWelSpecifieke() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true, true), null, null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testWelVolledigeCollectieSpecifiekeNotNull() {
        List<BerichtEntiteit> meldingen;

        PersoonBericht persoon = bouwPersoon(true, true);
        // Kan volgens huidige config niet voorkomen, maar voor de volledigheid.
        persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testAndereIndicatie() {
        List<BerichtEntiteit> meldingen;

        PersoonBericht persoon = bouwPersoon(true, true);
        ReflectionTestUtils.setField(persoon.getIndicaties().iterator().next(), "soort", new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_ONDER_CURATELE));
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    private PersoonBericht bouwPersoon(final boolean volledige, final boolean specifieke) {
        final PersoonBericht persoon = new PersoonBericht();
        if (volledige) {
            PersoonIndicatieBericht indicatie = new PersoonIndicatieBericht(new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING));
            persoon.setIndicaties(new ArrayList<PersoonIndicatieBericht>());
            persoon.getIndicaties().add(indicatie);
        }
        if (specifieke) {
            persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
            PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking = new PersoonVerstrekkingsbeperkingBericht();
            verstrekkingsbeperking.setPartij(new PartijAttribuut(bouwPartij()));
            persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        }
        return persoon;
    }

    private Partij bouwPartij() {
        return TestPartijBuilder.maker().maak();
    }
}
