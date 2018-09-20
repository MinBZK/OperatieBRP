/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

public class Lo3PersoonslijstOpschonerTest {
    @Test
    public void testOpschonenSchoon() throws Exception {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst();
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenWarning() throws Exception {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst();
        Logging.log(pl.getOuder2Stapels().get(0).getMeestRecenteElement().getLo3Herkomst(), LogSeverity.WARNING,
                LogType.VERWERKING, "TEST", "TEST");
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenError() throws Exception {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst();
        Logging.log(pl.getOuder2Stapels().get(0).getMeestRecenteElement().getLo3Herkomst(), LogSeverity.ERROR,
                LogType.VERWERKING, "TEST", "TEST");
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenErrorOnjuist() throws Exception {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst();
        Logging.log(pl.getOuder2Stapels().get(0).getMeestRecenteElement().getLo3Herkomst(), LogSeverity.ERROR,
                LogType.VERWERKING, "TEST", "TEST");
        // pl.getOuder1Stapels().get(0).getMeestRecenteElement().
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    private static Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuderStapel());
        builder.ouder2Stapel(VerplichteStapel.createOuderStapel());

        return builder.build();
    }
}
