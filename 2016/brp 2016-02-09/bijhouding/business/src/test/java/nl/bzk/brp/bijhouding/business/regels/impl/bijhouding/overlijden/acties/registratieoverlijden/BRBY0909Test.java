/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0909Test {

    private final BRBY0909 brby0909 = new BRBY0909();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby0909, "brby0003", new BRBY0003());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0909, brby0909.getRegel());
    }

    @Test
    public void testGeenKinderen() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(false),
                maakNieuwePersoon(20130101), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenKindOverleden() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(true, 20100101),
                maakNieuwePersoon(20130101), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenKindMeerderjarig() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(false, 19800101),
                maakNieuwePersoon(20130101), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenKindMinderjarig() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(false, 20100101),
                maakNieuwePersoon(20130101), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testMeedereKinderen() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(false, 20100101, 20000101, 19900101, 19800101),
                maakNieuwePersoon(20130101), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testKindWasNogNietGeborenOpDatumOverlijden() {
        final List<BerichtEntiteit> overtreders = brby0909.voerRegelUit(maakHuidigePersoon(false, 20100101),
                maakNieuwePersoon(20090101), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    private PersoonBericht maakNieuwePersoon(final int datumOverlijden) {
        final PersoonBericht persoon = new PersoonBericht();
        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
        persoon.setOverlijden(overlijden);
        return persoon;
    }

    private PersoonView maakHuidigePersoon(final boolean overleden, final int ... datumGeboorteKinderen) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        for (final int datumGeboorteKind : datumGeboorteKinderen) {
            final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwGeboorteRecord(datumGeboorteKind)
                        .datumGeboorte(datumGeboorteKind)
                    .eindeRecord();
            if (overleden) {
                builder.nieuwOverlijdenRecord(datumGeboorteKind).datumOverlijden(datumGeboorteKind).eindeRecord();
            }
            final PersoonHisVolledigImpl kindPersoon = builder.build();
            final FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            final KindHisVolledigImpl kind = new KindHisVolledigImpl(familie, kindPersoon);
            familie.getBetrokkenheden().add(kind);
            final OuderHisVolledigImpl ouder = new OuderHisVolledigImplBuilder(familie, persoon)
                    .nieuwOuderschapRecord(20010101, null, 20010101)
                        .indicatieOuder(Ja.J)
                    .eindeRecord().build();
            persoon.getBetrokkenheden().add(ouder);
        }
        return new PersoonView(persoon);
    }

}
