/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakDeblokkerenBerichtActionTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private MaakDeblokkerenBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakDeblokkerenBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    @Test
    public void test() throws Exception {
        final La01Bericht la01Bericht = new La01Bericht();
        la01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());

        final BlokkeringInfoVerzoekBericht blokkeringInfo = new BlokkeringInfoVerzoekBericht();
        blokkeringInfo.setANummer("1234567891");

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setProcessId("4234833");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01Bericht));
        parameters.put("blokkeringInfoBericht", berichtenDao.bewaarBericht(blokkeringInfo));
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(blokkeringInfoAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final DeblokkeringVerzoekBericht deblokkering =
                (DeblokkeringVerzoekBericht) berichtenDao.leesBericht((Long) result.get("deblokkeringBericht"));
        Assert.assertNotNull(deblokkering);
        Assert.assertEquals("1234567891", deblokkering.getANummer());
        Assert.assertEquals("4234833", deblokkering.getProcessId());
        Assert.assertEquals("0599", deblokkering.getGemeenteRegistratie());
    }
}
