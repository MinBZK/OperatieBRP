/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.gezagderde;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY2015Test {

    private final BRBY2015 brby2015 = new BRBY2015();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby2015, "brby0003", new BRBY0003());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2015, new BRBY2015().getRegel());
    }

    @Test
    public void testPersoonGezagDerdeMinderjarig() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20010101);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2015.voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonGezagDerdeMeerderjarig() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(19510101);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2015.voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testPersoonVerstrekkingsBeperking() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120102);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        // Hacky manier om een ander soort indicatie te testen
        ReflectionTestUtils.setField(persoonBericht.getIndicaties().iterator().next(),
            "soort", new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING));

        ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2015.voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonGeenIndicatiesInBericht() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120102);
        final PersoonBericht persoonBericht = maakNieuweSituatie(false);
        ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2015.voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    private ActieModel maakActie(final int datumAanvangGeldigHeid) {
        return new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigHeid), null, null, null);
    }

    private PersoonBericht maakNieuweSituatie(final boolean derdeHeeftGezag) {
        PersoonBericht persoonBericht = new PersoonBericht();
        if (derdeHeeftGezag) {
            persoonBericht.setIndicaties(new ArrayList<PersoonIndicatieBericht>());
            PersoonIndicatieBericht derdeHeeftGezagIndicatie =
                new PersoonIndicatieBericht(new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG));
            persoonBericht.getIndicaties().add(derdeHeeftGezagIndicatie);
        }
        return persoonBericht;
    }

    private PersoonHisVolledigImpl maakHuidigePersoon(final int datumGeboorte) {
        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(datumGeboorte)
            .datumGeboorte(datumGeboorte)
            .eindeRecord().build();
    }

}
