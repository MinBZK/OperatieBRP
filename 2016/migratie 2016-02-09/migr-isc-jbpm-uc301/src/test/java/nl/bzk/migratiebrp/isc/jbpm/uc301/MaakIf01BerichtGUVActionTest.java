/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakIf01BerichtGUVActionTest {

    private MaakIf01BerichtGUVAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakIf01BerichtGUVAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testNietGevonden() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final ZoekPersoonAntwoordBericht antwoord = new ZoekPersoonAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(ZoekPersoonResultaatType.GEEN);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("0000", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }

    @Test
    public void testVerhuisd() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final ZoekPersoonAntwoordBericht antwoord = new ZoekPersoonAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        antwoord.setPersoonId(1);
        antwoord.setAnummer("8172387435");
        antwoord.setGemeente("1900");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("V", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("8172387435", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("1900", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }

    @Test
    public void testNietUniek() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final ZoekPersoonAntwoordBericht antwoord = new ZoekPersoonAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(ZoekPersoonResultaatType.MEERDERE);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("U", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("0000", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }
}
