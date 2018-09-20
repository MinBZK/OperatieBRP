/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
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
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY2012Test {

    @Test
    public void testPersoonOnderCurateleZonderLatereRechtsfeiten() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120101);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        final ActieModel actie = maakActie(20120102);
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY2012().voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonOnderCurateleMetLatereRechtsfeiten() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120102);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        final ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY2012().voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testPersoonVerstrekkingsBeperkingMetLatereRechtsfeiten() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120102);
        final PersoonBericht persoonBericht = maakNieuweSituatie(true);
        // Hacky manier om een ander soort indicatie te testen
        ReflectionTestUtils.setField((PersoonIndicatieBericht) persoonBericht.getIndicaties().iterator().next(),
                             "soort", new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING));

        final ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY2012().voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonGeenIndicatiesInBericht() {
        final PersoonHisVolledigImpl huidigePersoon = maakHuidigePersoon(20120102);
        final PersoonBericht persoonBericht = maakNieuweSituatie(false);
        final ActieModel actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY2012().voerRegelUit(new PersoonView(huidigePersoon), persoonBericht, actie, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    private ActieModel maakActie(final int datumAanvangGeldigHeid) {
        return new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigHeid), null, null, null);
    }

    private PersoonBericht maakNieuweSituatie(final boolean onderCuratele) {
        final PersoonBericht persoonBericht = new PersoonBericht();
        if (onderCuratele) {
            persoonBericht.setIndicaties(new ArrayList<PersoonIndicatieBericht>());
            final PersoonIndicatieBericht onderCurateleIndicatie = new PersoonIndicatieBericht(new SoortIndicatieAttribuut(
                    SoortIndicatie.INDICATIE_ONDER_CURATELE));
            persoonBericht.getIndicaties().add(onderCurateleIndicatie);
        }
        return persoonBericht;
    }

    private PersoonHisVolledigImpl maakHuidigePersoon(final int tijdstipLaatsteWijziging) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwAfgeleidAdministratiefRecord(tijdstipLaatsteWijziging)
                .tijdstipLaatsteWijziging(new DatumAttribuut(tijdstipLaatsteWijziging).toDate())
                .eindeRecord().build();
        return persoon;
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2012, new BRBY2012().getRegel());
    }
}
