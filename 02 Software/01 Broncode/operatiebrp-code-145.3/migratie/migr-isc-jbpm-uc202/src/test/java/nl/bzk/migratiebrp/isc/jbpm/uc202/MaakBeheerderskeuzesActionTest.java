/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;


import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import org.junit.Assert;
import org.junit.Test;

public class MaakBeheerderskeuzesActionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao = new InMemoryBerichtenDao();

    private final MaakBeheerderskeuzesAction subject = new MaakBeheerderskeuzesAction(berichtenDao);



    @Test
    public void testToevoegen() {
        String aangeboden = maakPl("0000000001", "000000001");
        String kandidaat1 = maakPl("0000000002", "000000002");
        String kandidaat2 = maakPl("0000000003", "000000003");

        final Map<String, Object> result = execute(aangeboden, kandidaat1, kandidaat2);

        FoutafhandelingPaden paden = (FoutafhandelingPaden) result.get("foutafhandelingPaden");
        Collection<String> padNamen = paden.getSelectItems().values();
        LOGGER.info("Paden: {}", padNamen);
        Assert.assertTrue(padNamen.contains("toevoegen"));
        Assert.assertTrue(padNamen.contains("vervang0"));
        Assert.assertTrue(padNamen.contains("vervang1"));

        Assert.assertEquals("foutafhandelingIndicatieBeheerder", true, result.get("foutafhandelingIndicatieBeheerder"));
        Assert.assertEquals("foutafhandelingFout", "uc202.syncnaarbrp.onduidelijk", result.get("foutafhandelingFout"));
        Assert.assertEquals("foutafhandelingFoutmelding", "onduidelijk", result.get("foutafhandelingFoutmelding"));
        Assert.assertEquals("foutafhandelingType", "uc808", result.get("foutafhandelingType"));
        Assert.assertNotNull("foutafhandelingPaden", result.get("foutafhandelingPaden"));
        Assert.assertNotNull("persoonslijstOverzicht", result.get("persoonslijstOverzicht"));

    }
    @Test
    public void testAnummerBestaatAl() {
        String aangeboden = maakPl("0000000001", "000000001");
        String kandidaat1 = maakPl("0000000001", "000000002");
        String kandidaat2 = maakPl("0000000002", "000000003");

        final Map<String, Object> result = execute(aangeboden, kandidaat1, kandidaat2);

        FoutafhandelingPaden paden = (FoutafhandelingPaden) result.get("foutafhandelingPaden");
        Collection<String> padNamen = paden.getSelectItems().values();
        LOGGER.info("Paden: {}", padNamen);
        Assert.assertFalse(padNamen.contains("toevoegen"));
        Assert.assertTrue(padNamen.contains("vervang0"));
        Assert.assertFalse(padNamen.contains("vervang1"));
    }

    @Test
    public void testBsnBestaatAl() {
        String aangeboden = maakPl("0000000001", "000000001");
        String kandidaat1 = maakPl("0000000002", "000000002");
        String kandidaat2 = maakPl("0000000003", "000000001");

        final Map<String, Object> result = execute(aangeboden, kandidaat1, kandidaat2);

        FoutafhandelingPaden paden = (FoutafhandelingPaden) result.get("foutafhandelingPaden");
        Collection<String> padNamen = paden.getSelectItems().values();
        LOGGER.info("Paden: {}", padNamen);
        Assert.assertFalse(padNamen.contains("toevoegen"));
        Assert.assertFalse(padNamen.contains("vervang0"));
        Assert.assertTrue(padNamen.contains("vervang1"));
    }

    private Map<String, Object> execute(String aangeboden, String... kandidaten) {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setLo3PersoonslijstAlsTeletexString(aangeboden);

        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setMelding("onduidelijk");
        for (int i = 0; i < kandidaten.length; i++) {
            SynchroniseerNaarBrpAntwoordType.Kandidaat kandidaat = new SynchroniseerNaarBrpAntwoordType.Kandidaat();
            kandidaat.setPersoonId(i);
            kandidaat.setLo3PersoonslijstAlsTeletexString(kandidaten[i]);
            antwoord.getKandidaten().add(kandidaat);
        }

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("synchroniseerNaarBrpVerzoekBericht", berichtenDao.bewaarBericht(verzoek));
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        return subject.execute(parameters);
    }

    private String maakPl(final String anummer, final String bsn) {
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, anummer);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0120, bsn);

        return Lo3Inhoud.formatInhoud(Collections.singletonList(categorie01));
    }

}
