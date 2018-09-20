/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTest;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

/**
 * Alle spontaan berichten zijn slechts wrappers om de lo3 categorieen heen.
 */
public class SpontaanBerichtenTest extends AbstractLo3BerichtTest {

    @Test
    public void ag11() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Ag11Bericht bericht = new Ag11Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parse("00000000Ag11 00000000" + Lo3Inhoud.formatInhoud(maakData()));

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void ag21() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Ag21Bericht bericht = new Ag21Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parse("00000000Ag21 00000000" + Lo3Inhoud.formatInhoud(maakData()));

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void ag31() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Ag31Bericht bericht = new Ag31Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parse("00000000Ag31 00000000" + Lo3Inhoud.formatInhoud(maakData()));

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void ng01() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ng01Bericht bericht = new Ng01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setCategorieen(maakData());

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void gv01() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Gv01Bericht bericht = new Gv01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parse("00000000Gv01123123123400021070118010004000157000");

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void gv02() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Gv02Bericht bericht = new Gv02Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parse("00000000Gv02123123123400021070118010004000157000");

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void wa11() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Wa11Bericht bericht = new Wa11Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.parseInhoud(maakData());

        testFormatAndParseBericht(bericht);
    }

    private List<Lo3CategorieWaarde> maakData() {
        final List<Lo3CategorieWaarde> result = new ArrayList<>();
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0);
        cat01.addElement(Lo3ElementEnum.ANUMMER, "1234567890");

        result.add(cat01);
        return result;
    }
}
