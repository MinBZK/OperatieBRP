/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.expressietaal;

import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class VerifieerExpressieTaalIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BlobifierService blobifierService;

    private Map<String, Boolean> getTestExpressiesDeel1() {
        final Map<String, Boolean> exps = new HashMap<>();

        exps.put("persoon.geboorte.datum >= 1950/01/01", true);
        exps.put("persoon.geboorte.datum < 1950/01/01 EN AANTAL(FILTER(persoon.voornamen, v, v.naam = \"Karel\")) = 0", false);
        exps.put("JAAR(persoon.geboorte.datum) = 2000", false);
        exps.put("MAAND(persoon.geboorte.datum) = 2", false);
        exps.put("persoon.geboorte.datum = 1950/01/01 OF persoon.geboorte.datum = 1960/07/05 OF persoon.geboorte.datum = 1970/11/07", false);
        exps.put("JAAR(VANDAAG()) - JAAR(persoon.geboorte.datum) > 25", true);

        return exps;
    }

    private Map<String, Boolean> getAlleTestExpressies() {
        final Map<String, Boolean> exps = new HashMap<>();

        exps.putAll(getTestExpressiesDeel1());

        exps.put("WAAR", true);
        exps.put("ER_IS(KINDEREN(), k, AANTAL(KINDEREN(k)) = 0)", false);
        exps.put("ER_IS(HUWELIJKEN(), h, h.datum_aanvang > 1960/JAN/1)", false);

        exps.put("AANTAL(FILTER(persoon.voornamen, v, v.naam = \"Johanna\")) = 1 "
            + "EN AANTAL(FILTER(persoon.geslachtsnaamcomponenten, g, g.stam = \"Hoop\")) = 1", true);
        exps.put("AANTAL(FILTER(persoon.adressen, a, a.datum_aanvang_adreshouding < 2010/OKT/01)) = 1", true);
        exps.put("AANTAL(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 2)) = 1", false);
        exps.put("AANTAL(FILTER(persoon.adressen, a, a.postcode = \"3521TK\")) = 1", false);
        exps.put("AANTAL(FILTER(persoon.adressen, a, a.huisnummer = 12)) = 1", false);
        exps.put(
            "AANTAL(FILTER(persoon.adressen, a, a.huisnummer = 12)) = 1 "
                + "EN AANTAL(FILTER(persoon.voornamen, v, v.naam = \"Henk\")) = 1 "
                + "OF AANTAL(FILTER(persoon.voornamen, v2, v2.naam = \"Anita\")) = 1", false);

        exps.put("persoon.bijhouding.bijhoudingspartij = 34301", true);

        return exps;
    }

    @Test
    public final void testExpressies() {
        final PersoonHisVolledig persoon = blobifierService.leesBlob(49);

        final PersoonView persoonView = new PersoonView(persoon);
        for (final Map.Entry<String, Boolean> paar : getAlleTestExpressies().entrySet()) {
            final Boolean evaluatieResultaat = evalueer(persoonView, paar.getKey());
            Assert.assertEquals("expressie: " + paar.getKey(), paar.getValue(), evaluatieResultaat);
        }
    }

    @Test
    public final void testNullExpressies() {
        final Set<String> nullExpressies = getTestExpressiesDeel1().keySet();

        final PersoonHisVolledig persoon = blobifierService.leesBlob(49);
        ReflectionTestUtils.setField(persoon, "nationaliteiten", Collections.EMPTY_SET);
        ReflectionTestUtils.setField(persoon, "hisPersoonGeboorteLijst", Collections.EMPTY_SET);
        ReflectionTestUtils.setField(persoon, "adressen", Collections.EMPTY_SET);
        ReflectionTestUtils.setField(persoon, "hisPersoonBijhoudingLijst", Collections.EMPTY_SET);

        final PersoonView persoonView = new PersoonView(persoon);

        for (final String expressie : nullExpressies) {
            final boolean geeftNullWaarde = geeftNullWaarde(persoonView, expressie);
            assertTrue("Geen null: " + expressie, geeftNullWaarde);
        }
    }

    @Test
    public final void testFoutExpressies() {
        final List<String> foutExpressies = new ArrayList<>();
        foutExpressies.add("AANTAL(FILTER(persoon.adressen, a, a.huisnummer = 12)) > a");

        final PersoonHisVolledig persoon = blobifierService.leesBlob(49);

        final PersoonView persoonView = new PersoonView(persoon);

        for (final String expressie : foutExpressies) {
            final boolean geeftEenFout = geeftFout(persoonView, expressie);
            assertTrue("Geen fout: " + expressie, geeftEenFout);
        }
    }

    private Boolean evalueer(final Persoon persoon, final String expressieString) {
        final ParserResultaat parserResultaat = BRPExpressies.parse(expressieString);

        if (parserResultaat.succes()) {
            return BRPExpressies.evalueer(parserResultaat.getExpressie(), persoon).alsBoolean();
        } else {
            LOGGER.error("fout in {}: {}", expressieString, parserResultaat.getFoutmelding());
            return null;
        }

    }

    private boolean geeftNullWaarde(final Persoon persoon, final String expressie) {
        final ParserResultaat parserResultaat = BRPExpressies.parse(expressie);
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(parserResultaat.getExpressie(), persoon);
        return !evaluatieResultaat.isConstanteWaarde(ExpressieType.BOOLEAN) && evaluatieResultaat.isNull(null);
    }

    private boolean geeftFout(final Persoon persoon, final String expressie) {
        final ParserResultaat parserResultaat = BRPExpressies.parse(expressie);
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(parserResultaat.getExpressie(), persoon);
        if (evaluatieResultaat.isFout()) {
            LOGGER.info("Foutcode {}: {}", evaluatieResultaat.alsString(),
                evaluatieResultaat.alsString());
            return true;
        }
        return false;
    }
}
