/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.junit.Assert;
import org.junit.Test;

public class MaakIf01BerichtBActionTest {

    private static final String A_NUMMER = "9874512051";
    private static final String BRON_GEMEENTE = "1234";
    private static final String DOEL_GEMEENTE = "5678";

    private final MaakIf01BerichtBAction subject = new MaakIf01BerichtBAction();

    @Test
    public void test() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente(BRON_GEMEENTE);
        ii01Bericht.setDoelGemeente(DOEL_GEMEENTE);
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);

        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setGemeenteNaar(DOEL_GEMEENTE);
        blokkeringAntwoordType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringAntwoordType.setStatus(StatusType.GEBLOKKEERD);
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                new BlokkeringAntwoordBericht(blokkeringAntwoordType);

        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        final List<GevondenPersoonType> gevondenPersoonTypeLijst = gevondenPersonenType.getGevondenPersoon();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        gevondenPersoon1.setANummer(A_NUMMER);
        gevondenPersoon1.setBijhoudingsgemeente("0399");
        gevondenPersoonTypeLijst.add(gevondenPersoon1);
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        parameters.put("blokkeringAntwoordBericht", blokkeringAntwoordBericht);
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", zoekPersoonAntwoord);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);

        Assert.assertEquals(DOEL_GEMEENTE, if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("B", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals(DOEL_GEMEENTE, if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals(A_NUMMER, if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }
}
