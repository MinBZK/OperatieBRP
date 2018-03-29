/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3VerwijzingFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

public class Iv01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Iv01Bericht iv01Bericht = new Iv01Bericht();
        iv01Bericht.setVerwijzing(maakVerwijzingInhoud());
        testFormatAndParseBericht(iv01Bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lo3Datum ingangsdatum = new Lo3Datum(20160101);
        final Iv01Bericht iv01Bericht = new Iv01Bericht();
        iv01Bericht.setVerwijzing(maakVerwijzingInhoud());
        iv01Bericht.setBronPartijCode(BRON_GEMEENTE);
        iv01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        iv01Bericht.setMessageId(MessageIdGenerator.generateId());
        iv01Bericht.setIngangsdatumGeldigheid(ingangsdatum);
        testFormatAndParseBericht(iv01Bericht);

        Assert.assertNull(iv01Bericht.getStartCyclus());

        final Iv01Bericht controleBericht = new Iv01Bericht();
        controleBericht.setVerwijzing(iv01Bericht.getVerwijzing());
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(iv01Bericht.getMessageId());
        controleBericht.setIngangsdatumGeldigheid(ingangsdatum);

        Assert.assertEquals(iv01Bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(iv01Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(iv01Bericht.equals(iv01Bericht));
        Assert.assertTrue(controleBericht.equals(iv01Bericht));
        Assert.assertEquals(Collections.singletonList("2349326344"), iv01Bericht.getGerelateerdeAnummers());
        Assert.assertEquals(iv01Bericht.getIngangsdatumGeldigheid(), controleBericht.getIngangsdatumGeldigheid());
        Assert.assertFalse(iv01Bericht.equals(new Lq01Bericht()));
    }

    @Test(expected = BerichtInhoudException.class)
    public void testLegeCategorieen() throws BerichtInhoudException {
        Iv01Bericht iv01Bericht = new Iv01Bericht();
        iv01Bericht.parseCategorieen(Collections.emptyList());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testGeenVerwijzingStapel() throws BerichtInhoudException {
        Iv01Bericht iv01Bericht = new Iv01Bericht();
        Lo3Stapel<Lo3PersoonInhoud>
                persoonStapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon("1234567890", null, null, null, null, null, null), Lo3CategorieEnum.CATEGORIE_01));
        Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        Lo3PersoonFormatter persoonFormatter = new Lo3PersoonFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        persoonFormatter.format(persoonStapel.get(0).getInhoud(), formatter);
        iv01Bericht.parseCategorieen(formatter.getList());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testMeerdereVerwijzingStapel() throws BerichtInhoudException {
        Iv01Bericht iv01Bericht = new Iv01Bericht();
        Lo3Stapel<Lo3VerwijzingInhoud>
                verwijzingStapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verwijzing(1234567890L, null, null, null, null, null, null, null, null, null, null, null),
                                Lo3CategorieEnum.CATEGORIE_21),
                        Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verwijzing(1234567890L, null, null, null, null, null, null, null, null, null, null, null),
                                Lo3CategorieEnum.CATEGORIE_71));
        Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        Lo3VerwijzingFormatter verwijzingFormatter = new Lo3VerwijzingFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_21);
        verwijzingFormatter.format(verwijzingStapel.get(0).getInhoud(), formatter);
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_71);
        verwijzingFormatter.format(verwijzingStapel.get(0).getInhoud(), formatter);
        iv01Bericht.parseCategorieen(formatter.getList());
    }

    private Lo3VerwijzingInhoud maakVerwijzingInhoud() {
        return Lo3StapelHelper.lo3Verwijzing(2349326344L,
                546589734,
                "Jaap",
                null,
                null,
                "Appelenberg",
                19540307,
                "0518",
                "0630",
                "0518",
                19540309,
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.getCode());
    }

}
