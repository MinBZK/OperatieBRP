/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakSynchroniseerNaarBrpVerzoekActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakSynchroniseerNaarBrpVerzoekAction subject = new MaakSynchroniseerNaarBrpVerzoekAction(berichtenDao);

    @Test
    public void testToevoegen() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
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
                                        1977_01_01,
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
        lg01Bericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
    }

    @Test
    public void testBeheerderKeuzeNieuw() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
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
                                        1977_01_01,
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
        lg01Bericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));
        parameters.put(VerwerkBeheerderkeuzeAction.VARIABELE_BEHEERDER_KEUZE, MaakBeheerderskeuzesAction.KEUZE_NIEUW);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze());
        Assert.assertEquals(BeheerdersKeuzeType.TOEVOEGEN, synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze().getKeuze());
        Assert.assertNull(synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze().getTeVervangenPersoonId());
    }

    @Test
    public void testBeheerderKeuzeVervang() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
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
                                        1977_01_01,
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
        lg01Bericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));
        parameters.put(VerwerkBeheerderkeuzeAction.VARIABELE_BEHEERDER_KEUZE, MaakBeheerderskeuzesAction.KEUZE_VERVANGEN_PREFIX + "1321645");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze());
        Assert.assertEquals(BeheerdersKeuzeType.VERVANGEN, synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze().getKeuze());
        Assert.assertEquals(Long.valueOf("1321645"), synchroniseerNaarBrpVerzoekBericht.getBeheerderKeuze().getTeVervangenPersoonId());
    }

}
