/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

public class MaakStoreActionTest {

    private final MaakSynchroniseerNaarBrpVerzoekAction subject = new MaakSynchroniseerNaarBrpVerzoekAction();

    @Test
    @SuppressWarnings("unchecked")
    public void testToevoegen() {
        final PlSyncBericht plSyncBericht = new PlSyncBericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                1234567892L, 123456789L, "Jaap", null, null, "Pietersen", 19770101, "0512", "6030", "M", "E",
                1231231232L, 4564564564L), Lo3StapelHelper.lo3His(20040101), Lo3StapelHelper.lo3Akt(1),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        plSyncBericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", plSyncBericht);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) result.get("synchroniseerNaarBrpVerzoekBericht");
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
    }
}
