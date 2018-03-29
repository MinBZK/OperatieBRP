/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test op opgeschort reden O.
 */
public class PlControleGevondenPersoonOpgeschortRedenOTest {

    private PlControleGevondenPersoonOpgeschortRedenO subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new PlControleGevondenPersoonOpgeschortRedenO();
    }

    @Test
    public void testControleerOk() {
        BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpBijhoudingInhoud bijhoudingsInhoud =
                new BrpBijhoudingInhoud(null, BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.OVERLEDEN);
        final BrpGroep<BrpBijhoudingInhoud> bijhoudingsGroep =
                new BrpGroep<>(bijhoudingsInhoud, new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null), null, null, null);
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = new BrpStapel<>(Collections.singletonList(bijhoudingsGroep));
        builder.bijhoudingStapel(bijhoudingStapel);
        Assert.assertTrue("Moet true zijn, persoon is overleden", subject.controleer(new VerwerkingsContext(null, null, null, null), builder.build()));
    }

    @Test
    public void testControleerNOk() {
        BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpBijhoudingInhoud bijhoudingsInhoud =
                new BrpBijhoudingInhoud(null, BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL);
        final BrpGroep<BrpBijhoudingInhoud> bijhoudingsGroep =
                new BrpGroep<>(bijhoudingsInhoud, new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null), null, null, null);
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = new BrpStapel<>(Collections.singletonList(bijhoudingsGroep));
        builder.bijhoudingStapel(bijhoudingStapel);
        Assert.assertFalse(
                "Moet false zijn, persoon is niet opgeschort",
                subject.controleer(new VerwerkingsContext(null, null, null, null), builder.build()));
    }

}
