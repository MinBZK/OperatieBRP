/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRBY0141} bedrijfsregel.
 */
public class BRBY0141Test {

    private BRBY0141 brby0141 = new BRBY0141();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0141, brby0141.getRegel());
    }

    @Test
    public void geenHuidigeSituatie() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde());

        List<BerichtEntiteit> meldingen = brby0141.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void huidigeSituatieHeeftAlDeNLNationaliteit() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde());

        PersoonView bestaandeSituatie = bouwHuidigePersoonMetNationaliteit(
                StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde(),
                StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        List<BerichtEntiteit> meldingen = brby0141.voerRegelUit(bestaandeSituatie, nieuweSituatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(nieuweSituatie.getNationaliteiten().iterator().next(), meldingen.get(0));
    }

    @Test
    public void huidigeSituatieHeeftAlDeNLNationaliteitMetNieuweSituatieTweeKeerAlVoorkomend() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde(),
                        StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        PersoonView bestaandeSituatie = bouwHuidigePersoonMetNationaliteit(
                StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde(),
                StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        List<BerichtEntiteit> meldingen = brby0141.voerRegelUit(bestaandeSituatie, nieuweSituatie, null, null);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals(nieuweSituatie.getNationaliteiten().iterator().next(), meldingen.get(0));
    }

    @Test
    public void huidigeSituatieHeeftGeenNLNationaliteit() {
        PersoonBericht nieuweSituatie =
                bouwPersoonMetNationaliteit(987654321, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde());

        PersoonView bestaandeSituatie =
                bouwHuidigePersoonMetNationaliteit(
                        StatischeObjecttypeBuilder.NATIONALITEIT_TURKS.getWaarde(),
                        StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        List<BerichtEntiteit> meldingen = brby0141.voerRegelUit(bestaandeSituatie, nieuweSituatie, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    private PersoonBericht bouwPersoonMetNationaliteit(final Integer bsn, final Nationaliteit... nations) {
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, bsn, Geslachtsaanduiding.MAN,
                19900308,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "voornaam", "voorvoegsel", "geslachtsnaam");
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        for (Nationaliteit nation : nations) {
            persoon.getNationaliteiten().add(
                    PersoonBuilder.bouwPersoonNationaliteit(nation));
        }
        return persoon;
    }

    private PersoonView bouwHuidigePersoonMetNationaliteit(final Nationaliteit... nations) {
        PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        for (Nationaliteit nation : nations) {
            PersoonNationaliteitHisVolledigImpl persoonNation =
                    new PersoonNationaliteitHisVolledigImplBuilder(persoonHisVolledig, nation).
                    nieuwStandaardRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
                        .eindeRecord().build();

            persoonHisVolledig.getNationaliteiten().add(persoonNation);
        }

        return new PersoonView(persoonHisVolledig);
    }
}
