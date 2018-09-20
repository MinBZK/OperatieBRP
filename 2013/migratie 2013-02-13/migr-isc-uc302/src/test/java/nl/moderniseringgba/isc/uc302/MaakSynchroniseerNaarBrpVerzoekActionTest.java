/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;

import org.junit.Assert;
import org.junit.Test;

public class MaakSynchroniseerNaarBrpVerzoekActionTest {
    private final MaakSynchroniseerNaarBrpVerzoekAction subject = new MaakSynchroniseerNaarBrpVerzoekAction();

    @Test
    public void test() {
        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstBuilder().build();
        ib01Bericht.setLo3Persoonslijst(lo3Persoonlijst);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ib01Bericht", ib01Bericht);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht syncVerzoek =
                (SynchroniseerNaarBrpVerzoekBericht) result.get("synchroniseerNaarBrpVerzoekBericht");
        Assert.assertEquals(lo3Persoonlijst, syncVerzoek.getLo3Persoonslijst());
    }
}
