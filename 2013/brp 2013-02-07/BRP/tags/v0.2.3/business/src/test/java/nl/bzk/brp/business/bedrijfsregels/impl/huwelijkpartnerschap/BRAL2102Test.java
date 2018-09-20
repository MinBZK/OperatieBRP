/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL2102Test {

    private BRAL2102 bral2102;

    @Before
    public void init() {
        bral2102 = new BRAL2102();
    }

    @Test
    public void testDatumHuwelijkVolledig() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().setDatumAanvang(20080404).getRelatie();
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumHuwelijkOnVolledigBuitenland() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().setDatumAanvang(20080400).getRelatie();
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumHuwelijkOnVolledigNL() {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .setDatumAanvang(20080400)
            .getRelatie();
        huwelijk.getGegevens().setLandAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2102, meldingen.get(0).getCode());
    }

    @Test
    public void testDatumHuwelijkOnVolledigNL2() {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .setDatumAanvang(20080016)
            .getRelatie();
        huwelijk.getGegevens().setLandAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2102, meldingen.get(0).getCode());
    }

    @Test
    public void testDatumHuwelijkOnVolledigNL3() {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .setDatumAanvang(0)
            .getRelatie();
        huwelijk.getGegevens().setLandAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2102, meldingen.get(0).getCode());
    }
    @Test
    public void testDatumHuwelijkOnVolledigNL4() {
        RelatieBericht huwelijk = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .setDatumAanvang(00001203)
            .getRelatie();
        huwelijk.getGegevens().setLandAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        final List<Melding> meldingen = bral2102.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2102, meldingen.get(0).getCode());
    }
}
