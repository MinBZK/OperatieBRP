/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Test;

public class MaakSynchroniseerNaarBrpVerzoekActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakSynchroniseerNaarBrpVerzoekAction subject = new MaakSynchroniseerNaarBrpVerzoekAction(berichtenDao);

    @Test
    public void test() {
        final La01Bericht la01Bericht = new La01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Persoon(
                                        "1234567892",
                                        "123456789",
                                        "Jaap",
                                        null,
                                        null,
                                        "Pietersen",
                                        19770101,
                                        "0512",
                                        "6030",
                                        "M",
                                        "1231231232",
                                        "4564564564",
                                        "E"),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(20040101),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        la01Bericht.setLo3Persoonslijst(pl);
        la01Bericht.setBronPartijCode("0626");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        assertEquals(TypeSynchronisatieBericht.LA_01, synchroniseerNaarBrpVerzoekBericht.getTypeBericht());
        assertEquals("verzendende gemeente moet zijn opgenomen in bericht", "0626", synchroniseerNaarBrpVerzoekBericht.getVerzendendeGemeente());
    }

}
