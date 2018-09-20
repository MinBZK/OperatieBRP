/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Utility class voor het inhoudelijk kunnen vergelijken van persoonslijsten of delen hiervan.
 */
public final class BrpVergelijker {

    private static final String LABEL_EXPECTED = "expected";
    private static final String LABEL_ACTUAL = "actual";
    private static final String FORMAT_EXPECTED_ACTUAL = "%n\t%1$8s=%3$s%n\t%2$8s=%4$s%n";
    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpVergelijker() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Vergelijk twee persoonslijsten inhoudelijk met elkaar.
     *
     * @param stringBuilder
     *            Stringbuilder voor eventuele logging van de vergelijking.
     * @param inputExpected
     *            De referentue persoonslijst.
     * @param inputActual
     *            De input persoonslijst.
     * @param vergelijkRelaties
     *            Indicatie of relaties ook vergeleken dienen te worden of niet.
     * @param vergelijkAfgeleidAdministratief
     *            indicatie of afgeleid administratief vergeleken dient te worden
     * @return True indien de persoonslijsten inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static boolean vergelijkPersoonslijsten(
        final StringBuilder stringBuilder,
        final BrpPersoonslijst inputExpected,
        final BrpPersoonslijst inputActual,
        final boolean vergelijkRelaties,
        final boolean vergelijkAfgeleidAdministratief)
    {
        return BrpVergelijker.vergelijkPersoonslijsten(
            stringBuilder,
            inputExpected,
            inputActual,
            vergelijkRelaties,
            false,
            vergelijkAfgeleidAdministratief);
    }

    /**
     * Vergelijk twee persoonslijsten inhoudelijk met elkaar.
     *
     * @param stringBuilder
     *            Stringbuilder voor eventuele logging van de vergelijking.
     * @param inputExpected
     *            De referentue persoonslijst.
     * @param inputActual
     *            De input persoonslijst.
     * @param vergelijkRelaties
     *            Indicatie of relaties ook vergeleken dienen te worden of niet.
     * @param vergelijkAlleenIstRelaties
     *            Indicatie of relaties ook vergeleken dienen te worden of niet; zo ja, dan wordt alleen op IST
     *            gecontroleerd. Controle wordt niet uitgevoerd indien vergelijkRelaties op true staat.
     * @param vergelijkAfgeleidAdministratief
     *            indicatie of afgeleid administratief vergeleken dient te worden
     * @return True indien de persoonslijsten inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static boolean vergelijkPersoonslijsten(
        final StringBuilder stringBuilder,
        final BrpPersoonslijst inputExpected,
        final BrpPersoonslijst inputActual,
        final boolean vergelijkRelaties,
        final boolean vergelijkAlleenIstRelaties,
        final boolean vergelijkAfgeleidAdministratief)
    {
        boolean equal;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk persoonslijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, inputExpected, inputActual));

        final BrpPersoonslijst expected = BrpStapelSorter.sorteerPersoonslijst(inputExpected);
        final BrpPersoonslijst actual = BrpStapelSorter.sorteerPersoonslijst(inputActual);

        // LET OP: gewone OR (|) en NIET de shortcircuit OR (||)
        equal = vergelijkPersoonStapels(localStringBuilder, expected, actual);

        equal &= vergelijkAdministratieveStapels(localStringBuilder, expected, actual, vergelijkAfgeleidAdministratief);

        equal &= vergelijkNationatiteitsStapels(localStringBuilder, expected, actual);

        equal &= vergelijkGezagStapels(localStringBuilder, expected, actual);

        equal &= vergelijkIstStapels(localStringBuilder, expected, actual);

        equal &= vergelijkRelaties(vergelijkRelaties, vergelijkAlleenIstRelaties, localStringBuilder, expected, actual);

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /**
     * vergelijk relaties.
     * @param vergelijkRelaties boolean
     * @param vergelijkAlleenIstRelaties boolean
     * @param localStringBuilder result
     * @param expected exp
     * @param actual act
     * @return True if equal
     */
    static boolean vergelijkRelaties(
        final boolean vergelijkRelaties,
        final boolean vergelijkAlleenIstRelaties,
        final StringBuilder localStringBuilder,
        final BrpPersoonslijst expected,
        final BrpPersoonslijst actual)
    {
        boolean equal = true;

        if (vergelijkRelaties && !BrpVergelijker.vergelijkRelaties(localStringBuilder, expected.getRelaties(), actual.getRelaties())) {
            equal = false;

        } else if (vergelijkAlleenIstRelaties && !BrpVergelijker.vergelijkIstRelaties(localStringBuilder, expected.getRelaties(), actual.getRelaties())) {

            equal = false;
        }
        return equal;
    }

    private static boolean vergelijkAdministratieveStapels(
        final StringBuilder localStringBuilder,
        final BrpPersoonslijst expected,
        final BrpPersoonslijst actual,
        final boolean vergelijkAfgeleidAdministratief)
    {
        boolean equal = true;
        if (vergelijkAfgeleidAdministratief) {
            equal =
                    BrpVergelijker.vergelijkStapels(
                        localStringBuilder,
                        expected.getPersoonAfgeleidAdministratiefStapel(),
                        actual.getPersoonAfgeleidAdministratiefStapel(),
                        false,
                        false);
        }
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getVerstrekkingsbeperkingIndicatieStapel(),
                    actual.getVerstrekkingsbeperkingIndicatieStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIdentificatienummerStapel(), actual.getIdentificatienummerStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getInschrijvingStapel(), actual.getInschrijvingStapel(), true, false);
        equal &=
                BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getNummerverwijzingStapel(), actual.getNummerverwijzingStapel(), true, false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getBijhoudingStapel(), actual.getBijhoudingStapel());

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getPersoonskaartStapel(), actual.getPersoonskaartStapel(), true, false);

        return equal;
    }

    private static boolean vergelijkNationatiteitsStapels(
        final StringBuilder localStringBuilder,
        final BrpPersoonslijst expected,
        final BrpPersoonslijst actual)
    {
        boolean equal;
        equal =
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getVastgesteldNietNederlanderIndicatieStapel(),
                    actual.getVastgesteldNietNederlanderIndicatieStapel());

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getMigratieStapel(), actual.getMigratieStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getNationaliteitStapels(), actual.getNationaliteitStapels());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getReisdocumentStapels(), actual.getReisdocumentStapels(), true, false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getStaatloosIndicatieStapel(), actual.getStaatloosIndicatieStapel());
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(),
                    actual.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());

        return equal;
    }

    private static boolean vergelijkGezagStapels(final StringBuilder localStringBuilder, final BrpPersoonslijst expected, final BrpPersoonslijst actual) {
        boolean equal;

        equal =
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getDerdeHeeftGezagIndicatieStapel(),
                    actual.getDerdeHeeftGezagIndicatieStapel());
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getDeelnameEuVerkiezingenStapel(),
                    actual.getDeelnameEuVerkiezingenStapel(),
                    true,
                    false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getOnderCurateleIndicatieStapel(), actual.getOnderCurateleIndicatieStapel());

        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getUitsluitingKiesrechtStapel(),
                    actual.getUitsluitingKiesrechtStapel(),
                    true,
                    false);

        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getBehandeldAlsNederlanderIndicatieStapel(),
                    actual.getBehandeldAlsNederlanderIndicatieStapel());

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getVerblijfsrechtStapel(), actual.getVerblijfsrechtStapel());

        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel(),
                    actual.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel(),
                    true,
                    false);
        return equal;
    }

    private static boolean vergelijkPersoonStapels(
        final StringBuilder localStringBuilder,
        final BrpPersoonslijst expected,
        final BrpPersoonslijst actual)
    {
        boolean equal = BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getNaamgebruikStapel(), actual.getNaamgebruikStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getAdresStapel(), actual.getAdresStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getGeslachtsaanduidingStapel(), actual.getGeslachtsaanduidingStapel());
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getGeslachtsnaamcomponentStapels(),
                    actual.getGeslachtsnaamcomponentStapels());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getGeboorteStapel(), actual.getGeboorteStapel(), true, false);
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getSamengesteldeNaamStapel(), actual.getSamengesteldeNaamStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getOverlijdenStapel(), actual.getOverlijdenStapel(), true, false);
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getVoornaamStapels(), actual.getVoornaamStapels());

        return equal;
    }

    private static boolean vergelijkIstStapels(final StringBuilder localStringBuilder, final BrpPersoonslijst expected, final BrpPersoonslijst actual) {
        boolean equal = BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstOuder1Stapel(), actual.getIstOuder1Stapel(), false, false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstOuder2Stapel(), actual.getIstOuder2Stapel(), false, false);
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getIstHuwelijkOfGpStapels(),
                    actual.getIstHuwelijkOfGpStapels(),
                    false,
                    false);
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstKindStapels(), actual.getIstKindStapels(), false, false);
        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getIstGezagsverhoudingsStapel(),
                    actual.getIstGezagsverhoudingsStapel(),
                    false,
                    false);
        return equal;
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     *
     * @param expected
     *            De referentie stapel.
     * @param actual
     *            De input stapel.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(final BrpStapel<T> expected, final BrpStapel<T> actual) {
        return BrpVergelijker.vergelijkStapels(expected, actual, true, true);
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     *
     * @param stringBuilder
     *            Stringbuilder voor logging.
     * @param expected
     *            De referentie stapel.
     * @param actual
     *            De input stapel.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final StringBuilder stringBuilder,
        final BrpStapel<T> expected,
        final BrpStapel<T> actual)
    {
        return BrpVergelijker.vergelijkStapels(stringBuilder, expected, actual, true, true);
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     *
     * @param expected
     *            De referentie stapel.
     * @param actual
     *            De input stapel.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @param controleerFormeleHistorie
     *            Indicator voor het controleren van de formele historie.
     * @param controleerMaterieleHistorie
     *            Indicator voor het controleren van de materiele historie.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final BrpStapel<T> expected,
        final BrpStapel<T> actual,
        final boolean controleerFormeleHistorie,
        final boolean controleerMaterieleHistorie)
    {
        final StringBuilder log = new StringBuilder();
        final boolean result = BrpVergelijker.vergelijkStapels(log, expected, actual, controleerFormeleHistorie, controleerMaterieleHistorie);
        LOG.debug(log.toString());
        return result;
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     *
     * @param stringBuilder
     *            Stringbuilder voor logging.
     * @param expected
     *            De referentie stapel.
     * @param actual
     *            De input stapel.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @param controleerFormeleHistorie
     *            Indicator voor het controleren van de formele historie.
     * @param controleerMaterieleHistorie
     *            Indicator voor het controleren van de materiele historie.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final StringBuilder stringBuilder,
        final BrpStapel<T> expected,
        final BrpStapel<T> actual,
        final boolean controleerFormeleHistorie,
        final boolean controleerMaterieleHistorie)
    {
        return vergelijkStapels(
            stringBuilder,
            expected,
            actual,
            new StandaardBrpInhoudVergelijker<T>(),
            controleerFormeleHistorie,
            controleerMaterieleHistorie);
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     *
     * @param stringBuilder
     *            Stringbuilder voor logging.
     * @param expected
     *            De referentie stapel.
     * @param actual
     *            De input stapel.
     * @param inhoudVergelijker
     *            inhoud vergelijker
     * @param controleerFormeleHistorie
     *            Indicator voor het controleren van de formele historie.
     * @param controleerMaterieleHistorie
     *            Indicator voor het controleren van de materiele historie.
     * @param <T>
     *            groep inhoud type
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final StringBuilder stringBuilder,
        final BrpStapel<T> expected,
        final BrpStapel<T> actual,
        final BrpInhoudVergelijker<T> inhoudVergelijker,
        final boolean controleerFormeleHistorie,
        final boolean controleerMaterieleHistorie)
    {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk stapels:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk stapels: Een van de stapels is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                    String.format(
                        "vergelijk stapels: stapels bevatten niet even veel groepen (expected=%s, actual=%s)%n",
                        expected.size(),
                        actual.size()));
                equal = false;
            }

            final List<BrpGroep<T>> expectedCategorieen = BrpVergelijker.getGesorteerdeGroepen(BrpStapelSorter.sorteerStapel(expected));
            final List<BrpGroep<T>> actualCategorieen = BrpVergelijker.getGesorteerdeGroepen(BrpStapelSorter.sorteerStapel(actual));

            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!BrpVergelijker.vergelijkGroepen(
                    localStringBuilder,
                    expectedCategorieen.get(index),
                    actualCategorieen.get(index),
                    inhoudVergelijker,
                    controleerFormeleHistorie,
                    controleerMaterieleHistorie))
                {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /**
     * Vergelijk twee lijsten van stapels inhoudelijk.
     *
     * @param stringBuilder
     *            Stringbuilder voor logging.
     * @param expected
     *            De referentie lijst van stapels.
     * @param actual
     *            De input lijst van stapels.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @return True indien de lijsten van stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final StringBuilder stringBuilder,
        final List<BrpStapel<T>> expected,
        final List<BrpStapel<T>> actual)
    {
        return BrpVergelijker.vergelijkStapels(stringBuilder, expected, actual, true, true);
    }

    /**
     * Vergelijk twee lijsten van stapels inhoudelijk.
     *
     * @param stringBuilder
     *            Stringbuilder voor logging.
     * @param expected
     *            De referentie lijst van stapels.
     * @param actual
     *            De input lijst van stapels.
     * @param <T>
     *            Subklassen van BrpGroepInhoud.
     * @param controleerFormeleHistorie
     *            Indicator voor het controleren van de formele historie.
     * @param controleerMaterieleHistorie
     *            Indicator voor het controleren van de materiele historie.
     * @return True indien de lijsten van stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
        final StringBuilder stringBuilder,
        final List<BrpStapel<T>> expected,
        final List<BrpStapel<T>> actual,
        final boolean controleerFormeleHistorie,
        final boolean controleerMaterieleHistorie)
    {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk stapel lijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk stapel lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            BrpVergelijker.sortList(expected);
            BrpVergelijker.sortList(actual);

            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                    String.format(
                        "vergelijk stapel lijsten: lijsten bevatten niet even veel stapels (expected=%s, actual=%s)%n",
                        expected.size(),
                        actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.get(index),
                    actual.get(index),
                    controleerFormeleHistorie,
                    controleerMaterieleHistorie))
                {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /**
     * vergelijk relaties.
     * @param stringBuilder result
     * @param expected exp
     * @param actual act
     * @return True if equal
     */
    static boolean vergelijkIstRelaties(final StringBuilder stringBuilder, final List<BrpRelatie> expected, final List<BrpRelatie> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk IST relatie lijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk IST relatie lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                    String.format(
                        "vergelijk IST relatie lijsten: lijsten bevatten niet even veel relaties (expected=%s, actual=%s)%n",
                        expected.size(),
                        actual.size()));
                equal = false;
            }

            final List<BrpRelatie> expectedGesorteerd = BrpStapelSorter.sorteerRelaties(expected);
            final List<BrpRelatie> actualGesorteerd = BrpStapelSorter.sorteerRelaties(actual);

            for (int index = 0; index < expectedGesorteerd.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkIstStapelsRelaties(expectedGesorteerd.get(index), actualGesorteerd.get(index), localStringBuilder)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /**
     * vergelijkt relaties.
     * @param stringBuilder resultaat
     * @param expected exp
     * @param actual act
     * @return true als gelijk
     */
    static boolean vergelijkRelaties(final StringBuilder stringBuilder, final List<BrpRelatie> expected, final List<BrpRelatie> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk relatie lijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk relatie lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                    String.format(
                        "vergelijk relatie lijsten: lijsten bevatten niet even veel relaties (expected=%s, actual=%s)%n",
                        expected.size(),
                        actual.size()));
                equal = false;
            }

            final List<BrpRelatie> expectedGesorteerd = BrpStapelSorter.sorteerRelaties(expected);
            final List<BrpRelatie> actualGesorteerd = BrpStapelSorter.sorteerRelaties(actual);

            for (int index = 0; index < expectedGesorteerd.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!BrpVergelijker.vergelijkRelatie(localStringBuilder, expectedGesorteerd.get(index), actualGesorteerd.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /**
     * vergelijkt relaties.
     * @param stringBuilder result
     * @param expected exp
     * @param actual act
     * @return true if equal
     */
    static boolean vergelijkRelatie(final StringBuilder stringBuilder, final BrpRelatie expected, final BrpRelatie actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        String mesg = "vergelijk relaties" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (!Objects.equals(expected.getRolCode(), actual.getRolCode())) {
            mesg = "vergelijk relaties: rol ongelijk" + FORMAT_EXPECTED_ACTUAL;
            localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getRolCode(), actual.getRolCode()));
            equal = false;
        }

        if (!Objects.equals(expected.getSoortRelatieCode(), actual.getSoortRelatieCode())) {
            mesg = "vergelijk relaties: soort relatie ongelijk" + FORMAT_EXPECTED_ACTUAL;
            localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getSoortRelatieCode(), actual.getSoortRelatieCode()));
            equal = false;
        }

        equal &= BrpVergelijker.vergelijkStapels(expected.getRelatieStapel(), actual.getRelatieStapel());

        equal &= BrpVergelijker.vergelijkBetrokkenheden(localStringBuilder, expected.getBetrokkenheden(), actual.getBetrokkenheden());

        equal &= vergelijkIstStapelsRelaties(expected, actual, localStringBuilder);

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkIstStapelsRelaties(final BrpRelatie expected, final BrpRelatie actual, final StringBuilder localStringBuilder) {

        boolean equal = BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstOuder1Stapel(), actual.getIstOuder1Stapel(), false, false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstOuder2Stapel(), actual.getIstOuder2Stapel(), false, false);

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstHuwelijkOfGpStapel(), actual.getIstHuwelijkOfGpStapel(), false, false);
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIstKindStapel(), actual.getIstKindStapel(), false, false);

        equal &=
                BrpVergelijker.vergelijkStapels(
                    localStringBuilder,
                    expected.getIstGezagsverhoudingStapel(),
                    actual.getIstGezagsverhoudingStapel(),
                    false,
                    false);
        return equal;
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> getGesorteerdeGroepen(final BrpStapel<T> stapel) {
        final List<BrpGroep<T>> groepen = stapel.getGroepen();

        BrpStapelSorter.sorteerGroepLijst(groepen);

        return groepen;
    }

    private static <T extends BrpGroepInhoud> boolean vergelijkGroepen(
        final StringBuilder stringBuilder,
        final BrpGroep<T> expected,
        final BrpGroep<T> actual,
        final BrpInhoudVergelijker<T> inhoudVergelijker,
        final boolean controleerFormeleHistorie,
        final boolean controleerMaterieleHistorie)
    {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        String mesg = "vergelijk groepen:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk groepen: Een van de groepen is null\n");
                equal = false;
            }
        } else {
            if (!inhoudVergelijker.isGelijk(expected.getInhoud(), actual.getInhoud())) {
                mesg = "vergelijk groepen: inhoud ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getInhoud(), actual.getInhoud()));
                equal = false;
            }

            final boolean controleerHistorie = controleerFormeleHistorie || controleerMaterieleHistorie;
            if (controleerHistorie
                && (!isDatumTijdRegistratieGelijk(expected.getHistorie(), actual.getHistorie())
                    || !isDatumTijdVervalGelijk(expected.getHistorie(), actual.getHistorie())
                    || !isNadereAanduidingVervalGelijk(expected.getHistorie(), actual.getHistorie())))
            {
                mesg = "vergelijk groepen: formele historie ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getHistorie(), actual.getHistorie()));
                equal = false;
            }

            if (controleerMaterieleHistorie
                && (!isDatumAanvangGeldigheidGelijk(expected.getHistorie(), actual.getHistorie())
                    || !isDatumEindeGeldigheidGelijk(expected.getHistorie(), actual.getHistorie())))
            {
                mesg = "vergelijk groepen: materiele historie ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getHistorie(), actual.getHistorie()));
                equal = false;
            }

            if (controleerHistorie) {
                if (!BrpVergelijker.isGelijk(expected.getActieInhoud(), actual.getActieInhoud())) {
                    mesg = "vergelijk groepen: actie inhoud ongelijk" + FORMAT_EXPECTED_ACTUAL;
                    localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieInhoud(), actual.getActieInhoud()));
                    equal = false;
                }
                if (!BrpVergelijker.isGelijk(expected.getActieVerval(), actual.getActieVerval())) {
                    mesg = "vergelijk groepen: actie verval ongelijk" + FORMAT_EXPECTED_ACTUAL;
                    localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieVerval(), actual.getActieVerval()));
                    equal = false;
                }
            }

            if (controleerMaterieleHistorie && !BrpVergelijker.isGelijk(expected.getActieGeldigheid(), actual.getActieGeldigheid())) {
                mesg = "vergelijk groepen: actie geldigheid ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieGeldigheid(), actual.getActieGeldigheid()));
                equal = false;
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean isDatumTijdRegistratieGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return isDatumTijdRegistratieGelijk(historie1.getDatumTijdRegistratie(), historie2.getDatumTijdRegistratie());
    }

    private static boolean isDatumTijdRegistratieGelijk(final BrpDatumTijd tsReg1, final BrpDatumTijd tsReg2) {
        final BrpDatumTijd teNegerenDatumTijd = BrpDatumTijd.fromDatum(99990101, null);
        return teNegerenDatumTijd.equals(tsReg1) || teNegerenDatumTijd.equals(tsReg2) || Objects.equals(tsReg1, tsReg2);
    }

    private static boolean isDatumTijdVervalGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return Objects.equals(historie1.getDatumTijdVerval(), historie2.getDatumTijdVerval());
    }

    private static boolean isDatumAanvangGeldigheidGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return Objects.equals(historie1.getDatumAanvangGeldigheid(), historie2.getDatumAanvangGeldigheid());
    }

    private static boolean isDatumEindeGeldigheidGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return Objects.equals(historie1.getDatumEindeGeldigheid(), historie2.getDatumEindeGeldigheid());
    }

    private static boolean isNadereAanduidingVervalGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return Objects.equals(historie1.getNadereAanduidingVerval(), historie2.getNadereAanduidingVerval());
    }

    private static boolean isGelijk(final BrpActie expected, final BrpActie actual) {
        final boolean result;
        if (expected == null || actual == null) {
            result = expected == null && actual == null;
        } else {
            final BrpActie expectedGesorteerd = BrpVergelijker.soorteerActie(expected);
            final BrpActie actualGesorteerd = BrpVergelijker.soorteerActie(actual);

            result =
                    expectedGesorteerd.equals(actualGesorteerd, false)
                     && isDatumTijdRegistratieGelijk(expectedGesorteerd.getDatumTijdRegistratie(), actualGesorteerd.getDatumTijdRegistratie());
        }
        return result;
    }

    /**
     * Sorteer de actie.
     *
     * @param actie
     *            De te sorteren actie.
     * @return De gesorteerde actie.
     */
    public static BrpActie soorteerActie(final BrpActie actie) {
        return new BrpActie.Builder(actie).actieBronnen(BrpStapelSorter.sorteerActieBronnen(actie.getActieBronnen())).build();
    }

    /**
     * vergelijk betrokkenheden.
     * @param stringBuilder resultaat
     * @param expected verwacht
     * @param actual aangetroffen
     * @return true als gelijk
     */
    static boolean vergelijkBetrokkenheden(
        final StringBuilder stringBuilder,
        final List<BrpBetrokkenheid> expected,
        final List<BrpBetrokkenheid> actual)
    {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "vergelijk betrokkenheid lijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk betrokkenheid lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                    String.format(
                        "vergelijk betrokkenheid lijsten: lijsten bevatten niet even veel betrokkenheden (expected=%s, actual=%s)%n",
                        expected.size(),
                        actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!BrpVergelijker.vergelijkBetrokkenheid(localStringBuilder, expected.get(index), actual.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkBetrokkenheid(final StringBuilder stringBuilder, final BrpBetrokkenheid expected, final BrpBetrokkenheid actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        String mesg = "vergelijk betrokkenheden:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (!Objects.equals(expected.getRol(), actual.getRol())) {
            mesg = "vergelijk betrokkenheden: rol ongelijk" + FORMAT_EXPECTED_ACTUAL;
            localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getRol(), actual.getRol()));
            equal = false;
        }

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getIdentificatienummersStapel(), actual.getIdentificatienummersStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getGeslachtsaanduidingStapel(), actual.getGeslachtsaanduidingStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getGeboorteStapel(), actual.getGeboorteStapel());
        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getSamengesteldeNaamStapel(), actual.getSamengesteldeNaamStapel());
        equal &= vergelijkOuderlijkeBetrokkenheid(expected, actual, localStringBuilder);

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkOuderlijkeBetrokkenheid(
        final BrpBetrokkenheid expected,
        final BrpBetrokkenheid actual,
        final StringBuilder localStringBuilder)
    {
        boolean equal = BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getOuderlijkGezagStapel(), actual.getOuderlijkGezagStapel());

        equal &= BrpVergelijker.vergelijkStapels(localStringBuilder, expected.getOuderStapel(), actual.getOuderStapel());

        return equal;
    }

    private static <T extends BrpGroepInhoud> void sortList(final List<BrpStapel<T>> list) {
        Collections.sort(list, new Comparator<Object>() {

            @Override
            public int compare(final Object o1, final Object o2) {
                final int result;
                if (o1 == null && o2 == null) {
                    result = 0;
                } else if (o1 == null) {
                    result = -1;
                } else if (o2 == null) {
                    result = 1;
                } else {
                    result = o1.toString().compareTo(o2.toString());
                }
                return result;
            }
        });
    }

    /**
     * Inhoud vergelijker.
     *
     * @param <T>
     *            type inhoud
     */
    public interface BrpInhoudVergelijker<T extends BrpGroepInhoud> {
        /**
         * Vergelijk inhoud.
         *
         * @param expected
         *            verwacht
         * @param actual
         *            actueel
         * @return true, als actueel voldoet aan verwacht
         */
        boolean isGelijk(final T expected, final T actual);
    }

    /**
     * Standaard inhoud vergelijker.
     *
     * @param <T>
     *            type inhoud
     */
    public static final class StandaardBrpInhoudVergelijker<T extends BrpGroepInhoud> implements BrpInhoudVergelijker<T> {

        @Override
        public boolean isGelijk(final BrpGroepInhoud expected, final BrpGroepInhoud actual) {
            return Objects.equals(expected, actual);
        }

    }
}
