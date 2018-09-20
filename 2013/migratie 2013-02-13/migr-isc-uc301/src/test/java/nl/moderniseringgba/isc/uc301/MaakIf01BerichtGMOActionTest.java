/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

public class MaakIf01BerichtGMOActionTest {

    private final MaakIf01BerichtGMOAction subject = new MaakIf01BerichtGMOAction();

    @Test
    @SuppressWarnings("unchecked")
    public void testOverlijden() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"), Lo3StapelHelper.lo3His(19700101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "O", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("O", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEmigratie() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"), Lo3StapelHelper.lo3His(19700101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "E", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("E", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFout() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"), Lo3StapelHelper.lo3His(19700101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "F", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRni() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"), Lo3StapelHelper.lo3His(19700101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "R", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMinistrieelBesluit() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"), Lo3StapelHelper.lo3His(19700101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "M", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("M", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }
}
