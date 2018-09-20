/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleHistorieAnummerGelijkTest {

    private final PlControleHistorieAnummerGelijk subject = new PlControleHistorieAnummerGelijk();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(maakContext(1L), maakPl(1L)));
        Assert.assertTrue(subject.controleer(maakContext(1L), maakPl(1L, 1L, 1L)));

        Assert.assertTrue(subject.controleer(maakContext(1L, 2L), maakPl(1L, 2L)));
        Assert.assertTrue(subject.controleer(maakContext(1L, 2L), maakPl(1L, 1L, 1L, 2L, 2L, 2L)));

        Assert.assertFalse(subject.controleer(maakContext(1L, 2L, 3L), maakPl(1L, 2L)));
        Assert.assertFalse(subject.controleer(maakContext(1L, 2L), maakPl(1L, 1L, 1L, 2L, 2L, 2L, 3L)));
    }

    private VerwerkingsContext maakContext(final Long... anummers) {
        return new VerwerkingsContext(null, null, null, maakPl(anummers));
    }

    private BrpPersoonslijst maakPl(final Long... anummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < anummers.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(anummers[i]), null);
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        builder.identificatienummersStapel(new BrpStapel<>(groepen));

        return builder.build();

    }

}
