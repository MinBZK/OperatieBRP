/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link BRBY0178} bedrijfsregel.
 */
public class BRBY0178Test {

    private static final int DATUM_AANVANG_GELDIGHEID = 20100101;

    private BRBY0178 brby0178 = new BRBY0178();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0178, brby0178.getRegel());
    }

    @Test
    public void testHuidigeSituatieNull() {
        List<BerichtEntiteit> berichtEntiteiten = brby0178.voerRegelUit(null, null, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testZonderKind() {
        List<BerichtEntiteit> berichtEntiteiten = brby0178.voerRegelUit(bouwHuidigeSituatie(false, false), null, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testAndereBetrokkenheid() {
        PersoonView persoon = bouwHuidigeSituatie(true, true);
        // Hacky manier om een andere betrokkenheid te testen ipv hele nieuwe set testdata.
        ReflectionTestUtils.setField(ReflectionTestUtils.getField(persoon.getBetrokkenheden().iterator().next(), "betrokkenheid"), "rol", new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.PARTNER));
        List<BerichtEntiteit> berichtEntiteiten = brby0178.voerRegelUit(persoon, null, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testMetKindZonderOuderschap() {
        List<BerichtEntiteit> berichtEntiteiten = brby0178.voerRegelUit(bouwHuidigeSituatie(true, false), null, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testMetKindMetOuderschap() {
        List<BerichtEntiteit> berichtEntiteiten = brby0178.voerRegelUit(bouwHuidigeSituatie(true, true), null, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    private PersoonView bouwHuidigeSituatie(final boolean heeftKind, final boolean heeftOuderschap) {
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        if (heeftKind) {
            FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            OuderHisVolledigImplBuilder ouderBuilder = new OuderHisVolledigImplBuilder(familie, persoon);

            Integer datumEindeGeldigheid = null;
            if (!heeftOuderschap) {
                // Geen ouderschap simuleren door einde geldigheid gelijk aan aanvang.
                datumEindeGeldigheid = DATUM_AANVANG_GELDIGHEID;
            }
            OuderHisVolledigImpl ouder = ouderBuilder.nieuwOuderschapRecord(DATUM_AANVANG_GELDIGHEID, datumEindeGeldigheid, DATUM_AANVANG_GELDIGHEID).eindeRecord().build();
            PersoonHisVolledigImpl kindPersoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
            KindHisVolledigImpl kind = new KindHisVolledigImpl(familie, kindPersoon);
            familie.getBetrokkenheden().add(ouder);
            familie.getBetrokkenheden().add(kind);
            kindPersoon.getBetrokkenheden().add(kind);
            persoon.getBetrokkenheden().add(ouder);
        }

        return new PersoonView(persoon);
    }
}
