/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType.UniekGevondenPersoon;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakIf01BerichtBActionTest {

    private static final String TECHNISCHE_SLEUTEL = "9874512051";
    private static final String BRON_GEMEENTE = "1234";
    private static final String DOEL_GEMEENTE = "5678";

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakIf01BerichtBAction subject = new MaakIf01BerichtBAction(berichtenDao);

    @Test
    public void test() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode(BRON_GEMEENTE);
        ii01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setGemeenteNaar(DOEL_GEMEENTE);
        blokkeringAntwoordType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringAntwoordType.setStatus(StatusType.GEBLOKKEERD);
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht(blokkeringAntwoordType);

        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoordType.setUniekGevondenPersoon(new UniekGevondenPersoon());
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setPersoonId(1);
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setActueelANummer(TECHNISCHE_SLEUTEL);
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setBijhoudingsgemeente("0399");

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        parameters.put("blokkeringAntwoordBericht", berichtenDao.bewaarBericht(blokkeringAntwoordBericht));
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);

        Assert.assertEquals(DOEL_GEMEENTE, if01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("1234", if01Bericht.getDoelPartijCode());
        Assert.assertEquals("5678", if01Bericht.getBronPartijCode());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("B", if01Bericht.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals(DOEL_GEMEENTE, if01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals(TECHNISCHE_SLEUTEL, if01Bericht.getHeaderWaarde(Lo3HeaderVeld.A_NUMMER));
    }
}
