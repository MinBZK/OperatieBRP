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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleAnummerHistorischGelijkTest {
    private final PlControleAnummerHistorischGelijk subject = new PlControleAnummerHistorischGelijk();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("1")), null));
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("1", "1", "1")), null));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("1", "2")), null));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("1", "2", "1", "1")), null));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("1", "1", "1", "2")), null));
    }

    private BrpPersoonslijst maakPl(final String... anummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < anummers.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpString(anummers[i]), null);
            final Integer datumAanvang = (2010 - i) * 10000 + 101;
            final Integer datumEinde = i == 0 ? null : (2011 - i) * 10000 + 101;
            final BrpHistorie historie = BrpStapelHelper.his(datumAanvang, datumEinde, datumAanvang, datumEinde);
            final BrpActie actieInhoud = BrpStapelHelper.act(1, datumAanvang);

            groepen.add(new BrpGroep<>(inhoud, historie, actieInhoud, null, null));
        }

        builder.identificatienummersStapel(new BrpStapel<>(groepen));

        return builder.build();
    }
}
