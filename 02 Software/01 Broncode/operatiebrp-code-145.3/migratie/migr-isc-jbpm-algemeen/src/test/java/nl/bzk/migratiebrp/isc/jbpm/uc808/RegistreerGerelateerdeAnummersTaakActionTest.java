/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc808;


import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

public class RegistreerGerelateerdeAnummersTaakActionTest {

    @Test
    public void test() {
        Lo3Persoonslijst persoonslijst = new Lo3PersoonslijstBuilder()
                .persoonStapel(Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Persoon("1231231234", "Jaap", "Jansen", 19700101, "0518", "6030", "M"),
                                Lo3CategorieEnum.CATEGORIE_01
                        ),
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Persoon(null, "Jaap", "Jansen", 19700101, "0518", "6030", "M"),
                                Lo3CategorieEnum.CATEGORIE_51
                        )
                )).build();

        final List<String> gerelateerdeAnummers = RegistreerGerelateerdeAnummersTaakAction.bepaalGerelateerdeAnummers(persoonslijst);
        Assert.assertEquals(Arrays.asList("1231231234"), gerelateerdeAnummers);

    }
}
