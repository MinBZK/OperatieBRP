/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRAL0106Test {

    private BRAL0106 bral0106;

    @Before
    public void init() {
        bral0106 = new BRAL0106();
        ReflectionTestUtils.setField(bral0106, "bral0107", new BRAL0107());
    }

    @Test
    public void testIsWelIngezeteneBericht() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingGroepBericht bijhoudingsaard = new PersoonBijhoudingGroepBericht();
        bijhoudingsaard.setBijhoudingsaard(new BijhoudingsaardAttribuut(Bijhoudingsaard.INGEZETENE));
        persoon.setBijhouding(bijhoudingsaard);
        Assert.assertTrue(bral0106.isIngezetene(persoon));
    }

    @Test
    public void testIsNietIngezeteneBericht() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingGroepBericht bijhoudingsaard = new PersoonBijhoudingGroepBericht();
        bijhoudingsaard.setBijhoudingsaard(new BijhoudingsaardAttribuut(Bijhoudingsaard.NIET_INGEZETENE));
        persoon.setBijhouding(bijhoudingsaard);
        Assert.assertFalse(bral0106.isIngezetene(persoon));
    }

    @Test
    public void testBijhoudingsaardNullBericht() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        Assert.assertFalse(bral0106.isIngezetene(persoon));
    }

    @Test
    public void testIsIngezeteneIngeschreveneView() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwBijhoudingRecord(20120101, null, 20120101)
                .bijhoudingsaard(Bijhoudingsaard.INGEZETENE).eindeRecord().build();
        Assert.assertTrue(bral0106.isIngezetene(new PersoonView(persoon)));
    }

    @Test
    public void testIsIngezeteneNietIngeschreveneView() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE)
                .nieuwBijhoudingRecord(20120101, null, 20120101)
                .bijhoudingsaard(Bijhoudingsaard.INGEZETENE).eindeRecord().build();
        Assert.assertFalse(bral0106.isIngezetene(new PersoonView(persoon)));
    }

    @Test
    public void testIsNietIngezeteneNietIngeschreveneView() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE)
                .nieuwBijhoudingRecord(20120101, null, 20120101)
                .bijhoudingsaard(Bijhoudingsaard.NIET_INGEZETENE).eindeRecord().build();
        Assert.assertFalse(bral0106.isIngezetene(new PersoonView(persoon)));
    }




}
