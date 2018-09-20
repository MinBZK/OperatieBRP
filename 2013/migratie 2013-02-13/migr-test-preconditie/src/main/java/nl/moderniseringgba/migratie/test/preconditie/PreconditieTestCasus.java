/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.preconditie;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.PreconditiesService;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.resultaat.Foutmelding;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;
import nl.moderniseringgba.migratie.test.resultaat.TestStatus;
import nl.moderniseringgba.migratie.test.util.HashCodeComparator;

/**
 * Test casus: precondities.
 */
// CHECKSTYLE:OFF - Fan out complexity
public final class PreconditieTestCasus extends TestCasus {
    // CHECKSTYLE:ON

    private static final String STAP_LO3_LOGGING = "lo3-logging";

    private static final String SUFFIX_VERSCHILLEN = "verschillen";

    private static final String STAP_LO3 = "lo3";

    private static final Comparator<Object> HERKOMST_COMPARATOR = new HashCodeComparator();

    private final List<Lo3CategorieWaarde> categorieen;

    @Inject
    private Lo3SyntaxControle syntaxControle;

    private final Lo3PersoonslijstParser lo3PersoonslijstParser = new Lo3PersoonslijstParser();

    @Inject
    private PreconditiesService preconditieService;

    @Inject
    private ConversieService conversieService;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param categorieen
     *            categorieen
     */
    protected PreconditieTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final List<Lo3CategorieWaarde> categorieen) {
        super(thema, naam, outputFolder, expectedFolder);
        this.categorieen = categorieen;
    }

    @Override
    public PreconditieTestResultaat run() {
        // Logging
        Logging.initContext();

        final PreconditieTestResultaat resultaat = new PreconditieTestResultaat(getThema(), getNaam());

        // Stap: precondities
        final Lo3Persoonslijst lo3 = stapPrecondities(resultaat, categorieen);

        // Stap: conversie
        stapConverteerNaarBrp(resultaat, lo3);

        // Logging
        Logging.destroyContext();

        return resultaat;
    }

    private Lo3Persoonslijst stapPrecondities(
            final PreconditieTestResultaat resultaat,
            final List<Lo3CategorieWaarde> input) {
        if (input == null) {
            return null;
        }

        try {
            Lo3Persoonslijst result = null;
            try {
                // Syntax
                final List<Lo3CategorieWaarde> syntax = syntaxControle.controleer(categorieen);

                // Parse
                final Lo3Persoonslijst lo3 = lo3PersoonslijstParser.parse(syntax);

                // Precondities
                result = preconditieService.verwerk(lo3);
                final String lo3Html = debugOutputXmlEnHtml(result, STAP_LO3);
                resultaat.setBron(lo3Html);
                debugOutputLg01(result, STAP_LO3);

                final Lo3Persoonslijst verwachteLo3 = leesVerwachteLo3Persoonslijst(STAP_LO3);

                if (verwachteLo3 != null) {
                    final StringBuilder verschillen = new StringBuilder();
                    final boolean equal =
                            Lo3StapelHelper.vergelijkPersoonslijst(verschillen, verwachteLo3, lo3, true);

                    if (!equal) {
                        final Foutmelding foutmelding =
                                new Foutmelding("Verschillen in LO3 en verwachte LO3", null, verschillen.toString());
                        final String verschillenHtml =
                                debugOutputXmlEnHtml(foutmelding, STAP_LO3, SUFFIX_VERSCHILLEN);

                        resultaat.setPreconditie(new TestStap(TestStatus.NOK,
                                "Er zijn verschillen geconstateerd (tussen de lo3 persoonslijsten)", lo3Html,
                                verschillenHtml));
                        return null;
                    }
                }
            } catch (final OngeldigePersoonslijstException e) {
                // Deze is hier verwacht
                result = null;
            }

            final Logging logging = Logging.getLogging();
            final String loggingHtml = debugOutputXmlEnHtml(logging, STAP_LO3_LOGGING);

            // Controleer verwachting logging
            final Logging verwachteLogging = leesVerwachteLogging(STAP_LO3_LOGGING);
            if (vergelijkLog(verwachteLogging, logging)) {
                resultaat.setPreconditie(new TestStap(TestStatus.OK, null, loggingHtml, null));
            } else {
                resultaat.setPreconditie(new TestStap(TestStatus.NOK,
                        "Er zijn verschillen geconstateerd (tussen de logregels)", loggingHtml, null));
            }

            return result;
            // CHECKSTYLE:OFF - Catch exception - robustheid testtooling
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding foutmelding = new Foutmelding("Fout bij controleren precondities", e);
            final String foutmeldingHtml = debugOutputXmlEnHtml(foutmelding, STAP_LO3);

            resultaat.setPreconditie(new TestStap(TestStatus.EXCEPTIE,
                    "Er is een exceptie opgetreden (controleren precondities)", foutmeldingHtml, null));
            return null;
        }

    }

    private BrpPersoonslijst stapConverteerNaarBrp(
            final PreconditieTestResultaat resultaat,
            final Lo3Persoonslijst input) {
        if (input != null) {
            try {
                final BrpPersoonslijst brp = conversieService.converteerLo3Persoonslijst(input);

                debugOutputXmlEnHtml(brp, "brp");

                final Logging logging = Logging.getLogging();
                final String loggingHtml = debugOutputXmlEnHtml(logging, "brp-logging");

                resultaat.setConversie(new TestStap(TestStatus.OK, null, loggingHtml, null));
                return brp;
                // CHECKSTYLE:OFF - Catch exception - robustheid testtooling
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                final Foutmelding foutmelding = new Foutmelding("Fout bij converteren LO3 naar BRP", e);
                final String foutmeldingHtml = debugOutputXmlEnHtml(foutmelding, STAP_LO3);

                resultaat.setConversie(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden",
                        foutmeldingHtml, null));
            }
        }
        return null;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private boolean vergelijkLog(final Logging expectedLogging, final Logging actualLogging) {
        final SortedMap<Lo3Herkomst, SortedSet<LogRegel>> expected = reworkLoggingToMap(expectedLogging);
        final SortedMap<Lo3Herkomst, SortedSet<LogRegel>> actual = reworkLoggingToMap(actualLogging);

        boolean result = true;
        if (expected.size() != actual.size()) {
            result = false;
        } else {
            final Iterator<Map.Entry<Lo3Herkomst, SortedSet<LogRegel>>> expectedIterator =
                    expected.entrySet().iterator();
            final Iterator<Map.Entry<Lo3Herkomst, SortedSet<LogRegel>>> actualIterator =
                    expected.entrySet().iterator();

            while (expectedIterator.hasNext()) {
                final Map.Entry<Lo3Herkomst, SortedSet<LogRegel>> expectedEntry = expectedIterator.next();
                final Map.Entry<Lo3Herkomst, SortedSet<LogRegel>> actualEntry = actualIterator.next();

                if (!isEqual(expectedEntry.getKey(), actualEntry.getKey())
                        || !vergelijkLogRegels(expectedEntry.getValue(), actualEntry.getValue())) {
                    result = false;
                }
            }
        }

        return result;
    }

    /* LogRegels zijn al van dezelfde herkomst. */
    private boolean vergelijkLogRegels(final SortedSet<LogRegel> expected, final SortedSet<LogRegel> actual) {

        boolean result = true;
        if (expected.size() != actual.size()) {
            result = false;
        } else {
            final Iterator<LogRegel> expectedIterator = expected.iterator();
            final Iterator<LogRegel> actualIterator = actual.iterator();

            while (expectedIterator.hasNext()) {
                final LogRegel expectedRegel = expectedIterator.next();
                final LogRegel actualRegel = actualIterator.next();

                if (!isEqual(expectedRegel.getSeverity(), actualRegel.getSeverity())
                        || !isEqual(expectedRegel.getType(), actualRegel.getType())
                        || !isEqual(expectedRegel.getCode(), actualRegel.getCode())) {
                    result = false;
                }
            }
        }

        return result;
    }

    private boolean isEqual(final Object expected, final Object actual) {
        if (expected == null) {
            return actual == null;
        } else {
            return expected.equals(actual);
        }
    }

    private SortedMap<Lo3Herkomst, SortedSet<LogRegel>> reworkLoggingToMap(final Logging logging) {
        final SortedMap<Lo3Herkomst, SortedSet<LogRegel>> result =
                new TreeMap<Lo3Herkomst, SortedSet<LogRegel>>(HERKOMST_COMPARATOR);

        if (logging != null) {
            for (final LogRegel regel : logging.getRegels()) {
                if (regel.getSeverity().compareTo(LogSeverity.WARNING) < 0) {
                    continue;
                }

                final Lo3Herkomst lo3Herkomst = regel.getLo3Herkomst();

                if (!result.containsKey(lo3Herkomst)) {
                    result.put(lo3Herkomst, new TreeSet<LogRegel>(HERKOMST_COMPARATOR));
                }

                result.get(lo3Herkomst).add(regel);
            }
        }

        return result;
    }

}
