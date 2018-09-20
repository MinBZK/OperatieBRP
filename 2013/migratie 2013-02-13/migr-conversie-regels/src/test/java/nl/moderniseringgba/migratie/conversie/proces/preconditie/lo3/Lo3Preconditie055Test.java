/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Lo3Preconditie055Test {

    private static final Lo3PreconditiesTester TESTER = new Lo3PreconditiesTester();

    private final Lo3Historie lo3Historie0 = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20130101));
    private final Lo3Historie lo3Historie1 = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20130102));
    private final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20130103));
    private final Lo3Historie lo3Historie3 = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(20120101),
            new Lo3Datum(20130104));
    private final Lo3Historie lo3Historie4 = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20130104));

    private final Lo3Herkomst lo3Herkomst0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 3);
    private final Lo3Herkomst lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 2);
    private final Lo3Herkomst lo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 1);
    private final Lo3Herkomst lo3Herkomst3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);

    private final Lo3GezagsverhoudingInhoud inhoud = new Lo3GezagsverhoudingInhoud(new Lo3IndicatieGezagMinderjarige(
            "1"), new Lo3IndicatieCurateleregister(1));

    @Before
    public void setup() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testControleerPreconditie055Sortering1() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering1());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering2() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering2());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering3() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering3());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering4() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering4());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering5() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering5());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering6() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering6());
        assertDatLoggingIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering7() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering7());
        assertDatLoggingNietIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055Sortering8() {
        TESTER.controleerPreconditie055(maakStapelMetOnjuistMetSortering8());
        assertDatLoggingNietIsAangemaakt(Logging.getLogging());
    }

    @Test
    public void testControleerPreconditie055ZonderOnjuist() {
        TESTER.controleerPreconditie055(maakStapelMetSortering());
        assertDatLoggingNietIsAangemaakt(Logging.getLogging());
    }

    private void assertDatLoggingIsAangemaakt(final Logging logging) {
        assertEquals(1, logging.getRegels().size());
        final LogRegel logRegel = logging.getRegels().iterator().next();
        assertEquals(LogType.PRECONDITIE, logRegel.getType());
        assertEquals(Precondities.PRE055.name(), logRegel.getCode());
        assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
    }

    private void assertDatLoggingNietIsAangemaakt(final Logging logging) {
        assertTrue(logging.getRegels().isEmpty());
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering1() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering2() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering3() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering4() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering5() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering6() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering7() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie4, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetOnjuistMetSortering8() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie4, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie3, lo3Herkomst3));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> maakStapelMetSortering() {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie0, lo3Herkomst0));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie1, lo3Herkomst1));
        categorieen.add(new Lo3Categorie<Lo3GezagsverhoudingInhoud>(inhoud, null, lo3Historie2, lo3Herkomst2));
        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieen);
    }

    private static final class Lo3PreconditiesTester extends Lo3Precondities {
    }
}
