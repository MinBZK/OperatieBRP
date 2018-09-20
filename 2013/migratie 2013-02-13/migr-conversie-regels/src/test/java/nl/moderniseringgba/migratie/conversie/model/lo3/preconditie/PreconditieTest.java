/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.preconditie;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Lo3PersoonslijstPrecondities;

import org.junit.Test;

public class PreconditieTest extends AbstractComponentTest {

    @Inject
    Lo3PersoonslijstPrecondities precondities;

    @Test
    public void test() {
        Logging.initContext();

        precondities.controleerPersoonslijst(persoonslijst());
        final Logging logging = Logging.getLogging();
        printLogging(logging);

        Logging.destroyContext();
    }

    private Lo3Persoonslijst persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonStapel());

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(1234567892L, "Piet",
                "Pietersen", 19770505, "0518", "6030", "M"), Lo3StapelHelper.lo3His(19770505), Lo3StapelHelper
                .lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));
    }

    private void printLogging(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            System.out.println(logRegel);
        }
    }
}
