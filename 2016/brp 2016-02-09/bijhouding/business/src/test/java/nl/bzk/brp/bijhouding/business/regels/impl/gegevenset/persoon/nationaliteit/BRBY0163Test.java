/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRBY0163} bedrijfsregel.
 */
public class BRBY0163Test {

    private BRBY0163 brby0163 = new BRBY0163();

    @Test
    public void testNieuweSituatieMetNederlandseNationaliteit() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde());

        List<BerichtEntiteit> berichtEntiteiten = brby0163.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testNieuweSituatieMetVreemdeNationaliteit() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        List<BerichtEntiteit> berichtEntiteiten = brby0163.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(nieuweSituatie.getNationaliteiten().iterator().next(), berichtEntiteiten.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0163, brby0163.getRegel());
    }

    private PersoonBericht bouwPersoonMetNationaliteit(final Integer bsn, final Nationaliteit... nations) {
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, bsn, Geslachtsaanduiding.MAN,
                                                            19900308,
                                                            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                                                            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM
                                                                    .getWaarde(),
                                                            "voornaam", "voorvoegsel", "geslachtsnaam");
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        for (Nationaliteit nation : nations) {
            persoon.getNationaliteiten().add(
                    PersoonBuilder.bouwPersoonNationaliteit(nation));
        }
        return persoon;
    }
}
