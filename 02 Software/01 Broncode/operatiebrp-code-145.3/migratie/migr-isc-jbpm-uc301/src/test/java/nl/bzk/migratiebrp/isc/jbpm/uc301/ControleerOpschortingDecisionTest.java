/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class ControleerOpschortingDecisionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private ControleerOpschortingDecision subject = new ControleerOpschortingDecision(berichtenDao);

    @Test
    public void testOk() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19700101, "0053", 5, 1, 19700101124500000L, true),
                                null,
                                new Lo3Historie(null, null, null),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));

        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testNok() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Inschrijving(null, null, "O", 19700101, "0053", 5, 1, 19700101124500000L, true),
                                null,
                                new Lo3Historie(null, null, null),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));

        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        Assert.assertEquals("10a. Opgeschort", subject.execute(parameters));
    }
}
