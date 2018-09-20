/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * JUnit voorbeeld waarin getoond wordt hoe Persoon objecten uit een bestaande database gehaald kunnen worden t.b.v.
 * het testen van de parser.
 */
@ContextConfiguration(locations = { "/dbtest-context.xml" })
@Ignore
public class DBVoorbeeldTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager       em;

    @Test
    public void testOphalenPersoonMiddelsJPA() {
        // PersoonModel persoon = em.find(PersoonModel.class, 1);
        // Assert.assertNotNull(persoon);

        // Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());
    }

    @Test
    public void testExpressie() {
        final TypedQuery<PersoonModel> query = em.createQuery("select p from PersoonModel p", PersoonModel.class);
        final List<PersoonModel> personen = query.getResultList();
        LOGGER.info("resultlist size = " + personen.size());

        testTotalCount(personen, "geslachtsaanduiding = \"M\"");
        testTotalCount(personen, "geboorte.datum > #1940/10/10#");
        testTotalCount(personen, "NIET(geboorte.datum < #1924/DEC/4#)");
        testTotalCount(personen, "IS_NULL(voornamen[1].naam)");
        testTotalCount(personen, "voornamen[1].naam = \"Jack\"");
        testTotalCount(personen, "voornamen[1].naam >= \"Inge\"");
        testTotalCount(personen, "voornamen[1].naam <> \"Henk\"");
        testTotalCount(personen, "IS_NULL(overlijden.datum) OF geboorte.datum < overlijden.datum");
        testTotalCount(personen, "aantal(kinderen()) = 2");
        testTotalCount(personen, "aantal(kinderen()) > 0");
        testTotalCount(personen, "alle(kinderen(), k, TRUE)");
        testTotalCount(personen, "alle(kinderen(), k, k.geboorte.datum > #1970/01/01#)");
        testTotalCount(personen, "niet alle(kinderen(), k, k.geboorte.datum < #1970/01/01#)");
        testTotalCount(personen, "niet alle(kinderen(), k, k.geboorte.datum = #1990/01/01#)");
        testTotalCount(personen, "alle(kinderen(), k, k.geboorte.datum < #2000/01/01#)");
        testTotalCount(personen, "er_is(kinderen(), k, k.bsn = 302533928)");
        testTotalCount(personen, "niet er_is(kinderen(), k, k.bsn = 302533928)");
        testTotalCount(personen,
                "er_is(kinderen(), k, k.geboorte.datum > #1968/01/01# en k.geboorte.datum < #1971/01/01#)");
        testTotalCount(personen, "er_is(kinderen(), k, k.geslachtsaanduiding = \"M\" en er_is(kinderen(), l, "
            + "l.geslachtsaanduiding = \"V\" en l.bsn <> k.bsn))");
        testTotalCount(personen, "niet er_is(kinderen(), k, k.geslachtsaanduiding = \"M\" en er_is(kinderen(), l, "
            + "l.geslachtsaanduiding = \"M\" en l.bsn <> k.bsn))");
        testTotalCount(personen, "er_is(kinderen(), k, aantal(kinderen(k)) > 0)");
        testTotalCount(personen, "niet er_is(kinderen(), k, aantal(kinderen(k)) > 1)");
        testTotalCount(personen, "alle(kinderen(), k, er_is(ouders(k), o, o.geboorte.datum < #1950/JAN/01#))");
        testTotalCount(personen, "alle(kinderen(), k, alle(ouders(k), o, o.geboorte.datum < k.geboorte.datum))");
        testTotalCount(personen, "niet er_is(kinderen(), k, alle(ouders(k), o, o.geboorte.datum >= k.geboorte.datum))");
        testTotalCount(personen, "er_is(partners(), p, p.bsn = 302533928)");
        testTotalCount(personen, "er_is(kinderen(), k, er_is(partners(k), p, p.bsn = 302533928))");
        // persoon.getVoornamen().iterator().next();
    }

    private boolean testTotalCount(final List<PersoonModel> personen, final String expressieString) {
        final int count = count(personen, expressieString);
        final String inverseExpressieString = "NIET(" + expressieString + ")";
        final int inverseCount = count(personen, inverseExpressieString);

        if (personen.size() != count + inverseCount) {
            LOGGER.info(expressieString + " #" + count);
            LOGGER.info(inverseExpressieString + " #" + inverseCount);
            LOGGER.info("count + inverseCount = " + (count + inverseCount) + "/" + personen.size());
        }

        return count + inverseCount == personen.size();
    }

    private int count(final List<PersoonModel> personen, final String expressieString) {
        final ParserResultaat pr = BRPExpressies.parse(expressieString);
        Assert.assertTrue("Fout in expressie: " + pr.getFoutmelding(), pr.succes());
        final Expressie expressie = pr.getExpressie();
        int count = 0;
        for (final PersoonModel p : personen) {
            final Expressie er = BRPExpressies.evalueer(expressie, p);
            if (er.isConstanteWaarde(ExpressieType.BOOLEAN) && er.alsBoolean()) {
                count++;
            }
        }

        return count;
    }
}
