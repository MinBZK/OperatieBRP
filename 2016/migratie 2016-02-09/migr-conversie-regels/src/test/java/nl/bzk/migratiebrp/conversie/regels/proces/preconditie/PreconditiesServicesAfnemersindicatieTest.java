/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeAfnemersindicatieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Test;

public class PreconditiesServicesAfnemersindicatieTest extends AbstractLoggingTest {

    @Inject
    private PreconditiesService subject;

    @Test
    public void testAFN001() {
        final Lo3Afnemersindicatie input = new Lo3Afnemersindicatie(null, new ArrayList<Lo3Stapel<Lo3AfnemersindicatieInhoud>>());

        // try {
        subject.verwerk(input);
        // // fail if no exception thrown
        // Assert.fail("OngeldigeAfnemersindicatieException verwacht");
        // } catch (final OngeldigeAfnemersindicatieException e) {
        // final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        // Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
        // Assert.assertEquals("Verwacht AFN001", "AFN001",
        // logRegels.iterator().next().getSoortMeldingCode().toString());
        // }
    }

    @Test
    public void testAFN002() throws OngeldigeAfnemersindicatieException {
        Lo3Afnemersindicatie input =
                new Lo3Afnemersindicatie(2543534L, Collections.singletonList(stapel(maak(null, 19900101, 0, 0), maak(null, 19920101, 0, 0))));

        input = subject.verwerk(input);

        // Verwacht dat stapel is verwijderd
        Assert.assertEquals(0, input.getAfnemersindicatieStapels().size());

        // En logging
        final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
        Assert.assertEquals("Verwacht AFN002", "AFN002", logRegels.iterator().next().getSoortMeldingCode().toString());
    }

    @Test
    public void testAFN005() throws OngeldigeAfnemersindicatieException {
        Lo3Afnemersindicatie input =
                new Lo3Afnemersindicatie(2543534L, Collections.singletonList(stapel(maak(7, 19900101, 0, 0), maak(8, 19920101, 0, 0))));

        input = subject.verwerk(input);

        // Verwacht dat stapel is verwijderd
        Assert.assertEquals(0, input.getAfnemersindicatieStapels().size());

        // En logging
        final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
        Assert.assertEquals("Verwacht AFN005", "AFN005", logRegels.iterator().next().getSoortMeldingCode().toString());
    }

    @Test
    public void testAFN003() throws OngeldigeAfnemersindicatieException {
        Lo3Afnemersindicatie input = new Lo3Afnemersindicatie(2543534L, Collections.singletonList(stapel(maak(7, 19900000, 0, 0))));

        input = subject.verwerk(input);

        // Verwacht dat stapel is verwijderd
        Assert.assertEquals(0, input.getAfnemersindicatieStapels().size());

        // En logging
        final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
        Assert.assertEquals("Verwacht AFN003", "AFN003", logRegels.iterator().next().getSoortMeldingCode().toString());
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> stapel(final Lo3Categorie<T>... categorieen) {
        return new Lo3Stapel<>(Arrays.<Lo3Categorie<T>>asList(categorieen));
    }

    private Lo3Categorie<Lo3AfnemersindicatieInhoud> maak(
        final Integer afnemersindicatie,
        final Integer datumIngang,
        final int stapel,
        final int voorkomen)
    {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(afnemersindicatie);
        final Lo3Herkomst herkomst = new Lo3Herkomst(voorkomen == 0 ? Lo3CategorieEnum.CATEGORIE_14 : Lo3CategorieEnum.CATEGORIE_64, stapel, voorkomen);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(datumIngang), new Lo3Datum(datumIngang));

        return new Lo3Categorie(inhoud, null, historie, herkomst);
    }
}
