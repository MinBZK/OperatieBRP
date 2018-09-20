/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

public class ControleerOpschortingDecisionTest {

    private final ControleerOpschortingDecision subject = new ControleerOpschortingDecision();

    @Test
    @SuppressWarnings("unchecked")
    public void testOk() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));

        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNok() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, null, "O", 19700101, "0053", 5, 1, 19700101124500000L, true),
                Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));

        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        Assert.assertEquals("10a. Opgeschort", subject.execute(parameters));
    }
}
