/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpHistorischeGegevensVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen.VerwijderenAfnIndTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakZoekPersoonBerichtActionTest {

    private static final String ZOEK_PERSOON_VERZOEK = "zoekPersoonVerzoek";
    private static final String REFERENTIE_A_NUMMER = "1234567890";
    private static final String INPUT = "input";
    private static final String POSTCODE = "1234AA";
    private static final String ACHTERNAAM = "Jansen";
    private static final int BSN = 987654321;
    private static final long A_NUMMER = 1234567890L;
    private static final String AFNEMER = "580001";
    private MaakZoekPersoonBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakZoekPersoonBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testGevuldActueel() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoekBericht =
                (ZoekPersoonOpActueleGegevensVerzoekBericht) berichtenDao.leesBericht((Long) result.get(ZOEK_PERSOON_VERZOEK));
        Assert.assertNotNull(zoekPersoonVerzoekBericht);
        Assert.assertEquals(REFERENTIE_A_NUMMER, zoekPersoonVerzoekBericht.getANummer());
        Assert.assertEquals("987654321", zoekPersoonVerzoekBericht.getBsn());
        Assert.assertEquals(ACHTERNAAM, zoekPersoonVerzoekBericht.getGeslachtsnaam());
        Assert.assertEquals("1234AA", zoekPersoonVerzoekBericht.getPostcode());
    }

    @Test
    public void testGevuldHistorisch() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0110, Long.toString(A_NUMMER));
        cat51.addElement(Lo3ElementEnum.ELEMENT_0120, Integer.toString(BSN));
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, ACHTERNAAM);
        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat51);
        ap01Bericht.setCategorieen(categorieen);
        Assert.assertFalse(ap01Bericht.bevatActueleZoekGegevens());
        Assert.assertTrue(ap01Bericht.bevatHistorischeZoekGegevens());

        System.out.println("Historisch: " + ap01Bericht.format());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final ZoekPersoonOpHistorischeGegevensVerzoekBericht zoekPersoonVerzoekBericht =
                (ZoekPersoonOpHistorischeGegevensVerzoekBericht) berichtenDao.leesBericht((Long) result.get(ZOEK_PERSOON_VERZOEK));
        Assert.assertNotNull(zoekPersoonVerzoekBericht);
        Assert.assertEquals(REFERENTIE_A_NUMMER, zoekPersoonVerzoekBericht.getANummer());
        Assert.assertEquals("987654321", zoekPersoonVerzoekBericht.getBsn());
        Assert.assertEquals(ACHTERNAAM, zoekPersoonVerzoekBericht.getGeslachtsnaam());
    }

    @Test
    public void testGevuldVerwijderen() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER, REFERENTIE_A_NUMMER);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoekBericht =
                (ZoekPersoonOpActueleGegevensVerzoekBericht) berichtenDao.leesBericht((Long) result.get(ZOEK_PERSOON_VERZOEK));
        Assert.assertNotNull(zoekPersoonVerzoekBericht);
        Assert.assertEquals(REFERENTIE_A_NUMMER, zoekPersoonVerzoekBericht.getANummer());
    }
}
