/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal.impl;

import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ExpressieServiceImplIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String HAAKJE_OPEN = "{";
    private static final String HAAKJE_SLUITEN = "}";
    private static final String SPATIE = " ";
    private static final String KOMMA = ",";

    @Inject
    private ExpressieService expressieService;

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Test
    public final void testSuccesvolleExpressies() {
        final TypedQuery<String> query =
                entityManager.createQuery("SELECT expressie.waarde FROM Element "
                        + "WHERE autorisatie IS NOT nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie.NIET_VERSTREKKEN "
                        + "AND autorisatie IS NOT nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie.BIJHOUDINGSGEGEVENS"
                        , String.class);
        final List<String> resultList = query.getResultList();

        LOGGER.info("Aantal ElementExpressies: {}", resultList.size());

        int foutieveParsings = 0;
        for (final String elementExpressie : resultList) {

            if (elementExpressie == null || "".equals(elementExpressie)) {
                continue;
            }
            final ParserResultaat parse = BRPExpressies.parse(HAAKJE_OPEN + elementExpressie + HAAKJE_SLUITEN);

            if (!parse.succes()) {
                LOGGER.error("ElementExpressie foutief geparsed: {}", elementExpressie);
                foutieveParsings++;
            }
        }
        Assert.assertEquals(0, foutieveParsings);
    }
}

