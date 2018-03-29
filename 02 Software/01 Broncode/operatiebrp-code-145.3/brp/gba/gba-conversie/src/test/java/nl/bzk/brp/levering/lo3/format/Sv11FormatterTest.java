/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link Sv11Formatter}
 */
public class Sv11FormatterTest {

    private static final String A_NUMMER = "3450924321";
    private final VulBerichtFilter filter = new VulBerichtFilter();
    private final Sv11Formatter subject = new Sv11Formatter();

    @Test
    public void test() {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(buildLo3Persoonslijst());

        final List<Lo3CategorieWaarde> gefilterdeCategorieen =
                filter.filter(
                        null,
                        null,
                        null,
                        null,
                        categorieen,
                        Arrays.asList("01.01.10"));

        final String ng01 = subject.maakPlatteTekst(null, null, categorieen, gefilterdeCategorieen);

        final StringBuilder verwachteSv11 = new StringBuilder();
        // Header
        verwachteSv11.append("00000000Sv11");
        // Bericht lengte
        verwachteSv11.append("00022");

        // Categorie 01 + lengte (totale lengte: 22)
        verwachteSv11.append("01").append("017");
        // Element 01.10 + lengte + waarde (totale lengte: 17)
        verwachteSv11.append("0110").append("010").append("3450924321");

        Assert.assertEquals(verwachteSv11.toString(), ng01);
    }

    private Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());

        return builder.build();
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon(A_NUMMER, "Scarlet", "Simpspoon", 19770101, "0599", "6030", "V"),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(19770101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }
}
