/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.ArrayList;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;

import org.junit.Assert;
import org.junit.Test;

public class BRBY0003Test {

    private final BRBY0003 brby0003 = new BRBY0003();

    @Test
    public void testMinderjarigLeeftijd() {
        final Persoon minderjarig = maakPersoon(DatumAttribuut.gisteren().getWaarde());
        Assert.assertTrue(brby0003.isMinderjarig(minderjarig));
        Assert.assertFalse(brby0003.isMeerderjarig(minderjarig));
    }

    @Test
    public void testMeerderjarigLeeftijd() {
        final Persoon meerderjarig = maakPersoon(19900101);
        Assert.assertFalse(brby0003.isMinderjarig(meerderjarig));
        Assert.assertTrue(brby0003.isMeerderjarig(meerderjarig));
    }

    @Test
    public void testMeerderjarigHuwelijk() {
        final Persoon meerderjarig = maakPersoon(19900101, DatumAttribuut.gisteren().getWaarde());
        Assert.assertFalse(brby0003.isMinderjarig(meerderjarig));
        Assert.assertTrue(brby0003.isMeerderjarig(meerderjarig));
    }

    @Test
    public void testMeerderjarigHuwelijkDatumAanvangNull() {
        final Persoon minderjarig = maakPersoon(DatumAttribuut.gisteren().getWaarde(), new Integer[] {null});
        Assert.assertTrue(brby0003.isMinderjarig(minderjarig));
        Assert.assertFalse(brby0003.isMeerderjarig(minderjarig));
    }

    @Test
    public void testMinderjarigHuwelijkEnDatum() {
        final Persoon minderjarig = maakPersoon(19900101, DatumAttribuut.gisteren().getWaarde());
        Assert.assertTrue(brby0003.isMinderjarig(minderjarig, new DatumAttribuut(20050101)));
        Assert.assertFalse(brby0003.isMeerderjarig(minderjarig, new DatumAttribuut(20050101)));
    }

    private Persoon maakPersoon(final int geboortedatum, final Integer ... trouwDatums) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(geboortedatum));
        persoon.setGeboorte(geboorte);
        for (final Integer trouwDatum : trouwDatums) {
            final PartnerBericht eigenPartner = new PartnerBericht();
            persoon.getBetrokkenheden().add(eigenPartner);
            final HuwelijkBericht huwelijk = new HuwelijkBericht();
            eigenPartner.setRelatie(huwelijk);

            final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
            if (trouwDatum != null) {
                standaard.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(trouwDatum));
            }
            huwelijk.setStandaard(standaard);
        }
        return persoon;
    }
}
