/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

public class Tb01BerichtTest extends AbstractLo3BerichtTestBasis {

    @Test
    public void testLeeg() {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setMessageId(MessageIdGenerator.generateId());
        assertEquals("Tb01", tb01Bericht.getBerichtType());
        assertEquals("uc307", tb01Bericht.getStartCyclus());
    }

    @Test(expected = IllegalStateException.class)
    public void testLegePersoonslijstFormat() {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.formatInhoud();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testLegePersoonslijstParse() throws BerichtInhoudException {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.parseCategorieen(null);
    }

    @Test
    public void test() throws IOException, BerichtInhoudException, ClassNotFoundException {

        Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(maakPersoonslijst(null));
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setLo3Persoonslijst(builder.build());
        // Format en parse om 'overbodige' elementen uit de persoonslijst te halen; standaard 'testFormatEnParseBericht' gaat niet goed aangezien we de
        // 'overbodige' elementen dan kwijtraken, maar er wel nog op controleren in de standaard controle.
        List<Lo3CategorieWaarde> overcompleteLijstCategorieWaarden = tb01Bericht.formatInhoud();
        tb01Bericht.parseCategorieen(overcompleteLijstCategorieWaarden);
        tb01Bericht.setBronPartijCode("3333");
        tb01Bericht.setDoelPartijCode("5555");
        tb01Bericht.setMessageId(MessageIdGenerator.generateId());
        List<Lo3CategorieWaarde> categorieWaardeLijst = tb01Bericht.formatInhoud();

        Tb01Bericht controleBerichtViaParse = new Tb01Bericht();
        controleBerichtViaParse.parseCategorieen(categorieWaardeLijst);

        assertEquals("Tb01", tb01Bericht.getBerichtType());
        assertEquals("uc307", tb01Bericht.getStartCyclus());
        assertEquals(Collections.emptyList(), tb01Bericht.getGerelateerdeAnummers());
        assertEquals(tb01Bericht.getLo3Persoonslijst(), controleBerichtViaParse.getLo3Persoonslijst());

        Tb01Bericht controleBerichtViaPL = new Tb01Bericht();
        controleBerichtViaPL.setLo3Persoonslijst(tb01Bericht.getLo3Persoonslijst());

        assertEquals(Collections.emptyList(), tb01Bericht.getGerelateerdeAnummers());
        assertEquals(tb01Bericht.formatInhoud(), controleBerichtViaPL.formatInhoud());
        testSerialisatie(tb01Bericht);
    }

    @Test
    public void testGerelateerdAnummer() throws IOException, BerichtInhoudException, ClassNotFoundException {

        Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(maakPersoonslijst("1234567890"));
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setLo3Persoonslijst(builder.build());
        assertEquals("Tb01", tb01Bericht.getBerichtType());
        assertEquals("uc307", tb01Bericht.getStartCyclus());
        assertEquals(Collections.singletonList("1234567890"), tb01Bericht.getGerelateerdeAnummers());
    }

    protected Lo3Persoonslijst maakPersoonslijst(final String aNummerInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                null,
                "Jan",
                null,
                null,
                "Jansen",
                19700101,
                "0518",
                "6030",
                "M",
                null,
                null,
                null),
                Lo3StapelHelper.lo3Documentatie(0L, "0518", "1B2524","0518", null, null),
                Lo3StapelHelper.lo3His(20120101),
                new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0)),
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                null,
                "Jan",
                null,
                null,
                "Janssen",
                19700101,
                "0518",
                "6030",
                "M",
                null,
                null,
                null),
                Lo3StapelHelper.lo3Documentatie(0L, "0518", "1B2524","0518", null, null),
                Lo3StapelHelper.lo3His(19700101),
                new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));
        builder.ouder1Stapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Ouder("42354324", "Karel", "Janssen", 19501201, "0352", "0630", "M", 19700101)
                , Lo3CategorieEnum.CATEGORIE_02)));
        builder.ouder2Stapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Ouder("42354324", "Truus", "Dijksma", 19600405, "0626", "0630", "V", 19700101)
                , Lo3CategorieEnum.CATEGORIE_03)));

        return builder.build();
        }
}
