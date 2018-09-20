/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
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
 * Unit test voor het testen van de bedrijfsregel BRBY0179.
 */
public class BRBY0179Test {

    private BRBY0179 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0179();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0179, bedrijfsregel.getRegel());
    }

    @Test
    public void testGeenVerstrekkingsbeperkingen() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenOmschrijvingDerdeGeenGemeenteVerordening() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false, false), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWelOmschrijvingDerdeWelGemeenteVerordening() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true, true), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWelOmschrijvingDerdeGeenGemeenteVerordening() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(true, false), null, null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeenOmschrijvingDerdeWelGemeenteVerordening() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(false, true), null, null);
        Assert.assertEquals(1, meldingen.size());
    }

    private PersoonBericht bouwPersoon(final boolean omschrijvingDerde, final boolean gemeenteVerordening) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
        PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking = new PersoonVerstrekkingsbeperkingBericht();
        if (omschrijvingDerde) {
            verstrekkingsbeperking.setOmschrijvingDerde(new OmschrijvingEnumeratiewaardeAttribuut(""));
        }
        if (gemeenteVerordening) {
            verstrekkingsbeperking.setGemeenteVerordening(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        }
        persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        return persoon;
    }

}
