/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0166.
 */
public class BRBY0166Test {

    private BRBY0166 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0166();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0166, bedrijfsregel.getRegel());
    }

    @Test
    public void testNieuweSituatieNull() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, null, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testKindNull() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGeboorteGroepNull() {
        PersoonBericht kind = maakKind(null, null);
        ReflectionTestUtils.setField(kind, "geboorte", null);
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(kind), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testDatumGeboorteNull() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(maakKind(null, null)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testLandGeboorteNull() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(maakKind(20010101, null)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testDatumGeboorteVolledigLandNL() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(maakKind(20010101, LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testDatumGeboorteVolledigLandBuitenland() {
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(maakKind(20010101, (short) (1 + LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT))), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testDatumGeboorteOnvolledigLandNL() {
        PersoonBericht kind = maakKind(20010000, LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT);
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(kind), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(kind, overtreders.get(0));
    }

    @Test
    public void testDatumGeboorteOnvolledigLandBuitenland() {
        PersoonBericht kind = maakKind(20010100, (short) (1 + LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT));
        List<BerichtEntiteit> overtreders = bedrijfsregel.voerRegelUit(null, maakNieuweSituatie(kind), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    private PersoonBericht maakKind(final Integer datumGeboorte, final Short landGeboorteCode) {
        PersoonBericht persoonBericht = new PersoonBericht();
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        if (datumGeboorte != null) {
            geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
        }
        if (landGeboorteCode != null) {
            geboorte.setLandGebiedGeboorte(new LandGebiedAttribuut(
                    TestLandGebiedBuilder.maker().metCode(landGeboorteCode).maak()));
        }
        persoonBericht.setGeboorte(geboorte);
        return persoonBericht;
    }

    private FamilierechtelijkeBetrekkingBericht maakNieuweSituatie(final PersoonBericht kindPersoon) {
        FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        KindBericht kind = new KindBericht();
        kind.setPersoon(kindPersoon);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(kind);
        return familie;
    }


}
