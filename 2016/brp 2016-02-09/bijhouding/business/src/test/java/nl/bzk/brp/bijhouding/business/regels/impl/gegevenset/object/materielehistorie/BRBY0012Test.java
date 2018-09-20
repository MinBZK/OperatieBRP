/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0012Test {

    private BRBY0012 brby0012;

    @Before
    public void init() {
        brby0012 = new BRBY0012();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0012, brby0012.getRegel());
    }

    @Test
    public void testDatumEindeInDeToekomst() {
        final List<BerichtEntiteit> meldingen = brby0012.voerRegelUit(null, null, ActieBerichtBuilder.bouwNieuweActie(
                SoortActie.REGISTRATIE_NATIONALITEIT).setDatumEinde(
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen())).getActie(), null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testDatumEindeVandaag() {
        final List<BerichtEntiteit> meldingen = brby0012.voerRegelUit(null, null, ActieBerichtBuilder.bouwNieuweActie(
                SoortActie.REGISTRATIE_NATIONALITEIT).setDatumEinde(
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag())).getActie(), null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumEindeInHetVerleden() {
        final List<BerichtEntiteit> meldingen = brby0012.voerRegelUit(null, null, ActieBerichtBuilder.bouwNieuweActie(
                SoortActie.REGISTRATIE_NATIONALITEIT).setDatumEinde(
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren())).getActie(), null);
        Assert.assertEquals(0, meldingen.size());
    }
}
