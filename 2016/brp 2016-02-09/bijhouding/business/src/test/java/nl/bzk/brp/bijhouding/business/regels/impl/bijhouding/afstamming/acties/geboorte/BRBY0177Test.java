/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0177Test {

    private BRBY0177 brby0177;

    @Before
    public void init() {
        brby0177 = new BRBY0177();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0177, brby0177.getRegel());
    }

    @Test
    public void testRegelGaatAfDatumGeboorteNietGelijkAanDatumAanvangGeldigheid() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegKindToe(maakKind(20101010)).getRelatie();
        final List<BerichtEntiteit> objecten =
            brby0177.voerRegelUit(null, nieuweSituatie, maakStandaardActie(20111111), null);
        Assert.assertEquals(1, objecten.size());
    }

    @Test
    public void testRegelGaatNietAfDatumGeboorteGelijkAanDatumAanvangGeldigheid() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegKindToe(maakKind(20101010)).getRelatie();
        final List<BerichtEntiteit> objecten =
            brby0177.voerRegelUit(null, nieuweSituatie, maakStandaardActie(20101010), null);
        Assert.assertEquals(0, objecten.size());
    }

    @Test
    public void testGeenKind() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().getRelatie();
        final List<BerichtEntiteit> objecten =
            brby0177.voerRegelUit(null, nieuweSituatie, maakStandaardActie(20101010), null);
        Assert.assertEquals(0, objecten.size());
    }

    @Test
    public void testGeenGeboorte() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegKindToe(maakKind(null)).getRelatie();
        final List<BerichtEntiteit> objecten =
            brby0177.voerRegelUit(null, nieuweSituatie, maakStandaardActie(20101010), null);
        Assert.assertEquals(0, objecten.size());
    }

    @Test
    public void testGeenGeboorteDatum() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
            new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegKindToe(maakKind(0)).getRelatie();
        final List<BerichtEntiteit> objecten =
            brby0177.voerRegelUit(null, nieuweSituatie, maakStandaardActie(20101010), null);
        Assert.assertEquals(0, objecten.size());
    }

    private ActieBericht maakStandaardActie(final Integer datumAanvangGeldigheid) {
        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));

        return actie;
    }

    private PersoonBericht maakKind(final Integer geboorteDatum) {
        final PersoonBericht kind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666, Geslachtsaanduiding.MAN, 20120730,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), "kind", "van", "Houten");
        if (null != geboorteDatum) {
            if (geboorteDatum.equals(0)) {
                kind.getGeboorte().setDatumGeboorte(null);
            } else {
                kind.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(geboorteDatum));
            }
        } else {
            kind.setGeboorte(null);
        }
        return kind;
    }
}
