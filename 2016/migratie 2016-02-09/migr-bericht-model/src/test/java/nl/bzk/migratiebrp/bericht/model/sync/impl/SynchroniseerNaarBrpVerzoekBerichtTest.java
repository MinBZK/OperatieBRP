/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

public class SynchroniseerNaarBrpVerzoekBerichtTest extends AbstractSyncBerichtTest {

    private static final Long ANUMMER = Long.valueOf(1352456245L);
    private static final String LO3_BERICHT =
            "00000000LG010028001148011001000000012340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E8110004051881200071-X000185"
                    + "100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876AA721"
                    + "0001I851000819700101861000819700102";
    private static final String PL_ALS_TELETEXT =
            "0028001148011001000000012340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E81"
                    + "10004051881200071-X000185100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876A"
                    + "A7210001I851000819700101861000819700102";

    @Test
    public void testAnummerWijziging() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setANummerTeVervangenPl(ANUMMER);
        bericht.setAnummerWijziging(true);
        bericht.setLo3BerichtAsTeletexString(LO3_BERICHT);
        bericht.setLo3Persoonslijst(maakPersoonslijst());
        bericht.setOpnemenAlsNieuwePl(false);
        bericht.setGezaghebbendBericht(null);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals(ANUMMER, bericht.getANummerTeVervangenPl());
        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertTrue(bericht.isAnummerWijziging());
        Assert.assertFalse(bericht.isGezaghebbendBericht());
        Assert.assertFalse(bericht.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht.getOpnemenAlsNieuwePl());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3BerichtAsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

    @Test
    public void testNieuwOpnemen() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setANummerTeVervangenPl(null);
        bericht.setAnummerWijziging(false);
        bericht.setAnummerWijziging(null);
        bericht.setLo3BerichtAsTeletexString(LO3_BERICHT);
        bericht.setLo3Persoonslijst(maakPersoonslijst());
        bericht.setOpnemenAlsNieuwePl(true);
        bericht.setGezaghebbendBericht(true);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNull(bericht.getANummerTeVervangenPl());
        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertFalse(bericht.isAnummerWijziging());
        Assert.assertTrue(bericht.isGezaghebbendBericht());
        Assert.assertTrue(bericht.isOpnemenAlsNieuwePl());
        Assert.assertTrue(bericht.getOpnemenAlsNieuwePl());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3BerichtAsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

    @Test
    public void testConvenienceLo3Teletext() {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht(PL_ALS_TELETEXT);
        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertFalse(bericht.isAnummerWijziging());
        Assert.assertFalse(bericht.isGezaghebbendBericht());
        Assert.assertFalse(bericht.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht.getOpnemenAlsNieuwePl());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3BerichtAsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

    @Test
    public void testConveniencePersoonslijst() {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht(maakPersoonslijst(), ANUMMER);
        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertFalse(bericht.isAnummerWijziging());
        Assert.assertFalse(bericht.isGezaghebbendBericht());
        Assert.assertFalse(bericht.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht.getOpnemenAlsNieuwePl());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3BerichtAsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

    @Test
    public void testConvenienceTeletext() {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht(PL_ALS_TELETEXT, ANUMMER);
        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertFalse(bericht.isAnummerWijziging());
        Assert.assertFalse(bericht.isGezaghebbendBericht());
        Assert.assertFalse(bericht.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht.getOpnemenAlsNieuwePl());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3BerichtAsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

    @Test
    public void testGettersEnSetters() {
        final SynchroniseerNaarBrpVerzoekBericht bericht1 = new SynchroniseerNaarBrpVerzoekBericht();
        Assert.assertNull(bericht1.getANummerTeVervangenPl());
        bericht1.setOpnemenAlsNieuwePl(true);
        Assert.assertFalse(bericht1.isAnummerWijziging());
        Assert.assertFalse(bericht1.isGezaghebbendBericht());
        Assert.assertTrue(bericht1.isOpnemenAlsNieuwePl());
        Assert.assertTrue(bericht1.getOpnemenAlsNieuwePl());

        final SynchroniseerNaarBrpVerzoekBericht bericht2 = new SynchroniseerNaarBrpVerzoekBericht();
        bericht2.setANummerTeVervangenPl(ANUMMER);
        bericht2.setOpnemenAlsNieuwePl(null);
        Assert.assertFalse(bericht2.isAnummerWijziging());
        Assert.assertFalse(bericht2.isGezaghebbendBericht());
        Assert.assertFalse(bericht2.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht2.getOpnemenAlsNieuwePl());
        Assert.assertEquals(ANUMMER, bericht2.getANummerTeVervangenPl());

        final SynchroniseerNaarBrpVerzoekBericht bericht3 = new SynchroniseerNaarBrpVerzoekBericht();
        bericht3.setGezaghebbendBericht(true);
        Assert.assertFalse(bericht3.isAnummerWijziging());
        Assert.assertTrue(bericht3.isGezaghebbendBericht());
        Assert.assertFalse(bericht3.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht3.getOpnemenAlsNieuwePl());

        final SynchroniseerNaarBrpVerzoekBericht bericht4 = new SynchroniseerNaarBrpVerzoekBericht();
        bericht4.setAnummerWijziging(true);
        bericht4.setANummerTeVervangenPl(ANUMMER);
        Assert.assertTrue(bericht4.isAnummerWijziging());
        Assert.assertFalse(bericht4.isGezaghebbendBericht());
        Assert.assertFalse(bericht4.isOpnemenAlsNieuwePl());
        Assert.assertNull(bericht4.getOpnemenAlsNieuwePl());
        Assert.assertEquals(ANUMMER, bericht4.getANummerTeVervangenPl());
    }

    protected Lo3Persoonslijst maakPersoonslijst() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(1234L,
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
                "E"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19700101),
                new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0599",
            1970101,
            1970101,
            "Straat",
            15,
            "9876AA",
                "I"),
                null,
                Lo3StapelHelper.lo3His(19700101),
                new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

}
