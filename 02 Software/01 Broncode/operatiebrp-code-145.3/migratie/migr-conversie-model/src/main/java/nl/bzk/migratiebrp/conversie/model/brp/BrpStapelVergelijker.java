/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.List;
import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Utility class voor het inhoudelijk kunnen vergelijken van {@link BrpStapel} objecten.
 */
public final class BrpStapelVergelijker {

    private static final String LABEL_EXPECTED = "expected";
    private static final String LABEL_ACTUAL = "actual";
    private static final String FORMAT_EXPECTED_ACTUAL = "%n\t%1$8s=%3$s%n\t%2$8s=%4$s%n";

    private BrpStapelVergelijker() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     * @param verschillenLog de verschillen log
     * @param expected De referentie stapel.
     * @param actual De input stapel.
     * @param <T> Subklassen van BrpGroepInhoud.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(final StringBuilder verschillenLog, final BrpStapel<T> expected,
                                                                      final BrpStapel<T> actual) {
        return BrpStapelVergelijker.vergelijkStapels(verschillenLog, expected, actual, true, true);
    }

    /**
     * Vergelijk twee stapels inhoudelijk.
     * @param verschillenLog de verschillen log
     * @param expected De referentie stapel.
     * @param actual De input stapel.
     * @param <T> Subklassen van BrpGroepInhoud.
     * @param controleerFormeleHistorie Indicator voor het controleren van de formele historie.
     * @param controleerMaterieleHistorie Indicator voor het controleren van de materiele historie.
     * @return True indien de stapels inhoudelijk gelijk zijn, false in andere gevallen.
     */
    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(final StringBuilder verschillenLog, final BrpStapel<T> expected,
                                                                      final BrpStapel<T> actual, final boolean controleerFormeleHistorie,
                                                                      final boolean controleerMaterieleHistorie) {
        boolean isGelijk = true;
        final StringBuilder tijdelijkVerschillenLog = new StringBuilder();
        final String mesg = "vergelijk stapels:" + FORMAT_EXPECTED_ACTUAL;
        tijdelijkVerschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                tijdelijkVerschillenLog.append("vergelijk stapels: Een van de stapels is null\n");
                isGelijk = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                tijdelijkVerschillenLog.append(
                        String.format("vergelijk stapels: stapels bevatten niet even veel groepen (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                isGelijk = false;
            }

            final List<BrpGroep<T>> expectedCategorieen = expected.getGroepen();
            final List<BrpGroep<T>> actualCategorieen = actual.getGroepen();

            for (int index = 0; index < expectedCategorieen.size(); index++) {
                if (index >= actualCategorieen.size()) {
                    break;
                }

                if (!BrpStapelVergelijker
                        .vergelijkGroepen(tijdelijkVerschillenLog, expectedCategorieen.get(index), actualCategorieen.get(index), controleerFormeleHistorie,
                                controleerMaterieleHistorie)) {
                    isGelijk = false;
                }
            }
        }

        if (!isGelijk) {
            verschillenLog.append(tijdelijkVerschillenLog);
        }
        return isGelijk;
    }

    private static <T extends BrpGroepInhoud> boolean vergelijkGroepen(final StringBuilder verschillenLog, final BrpGroep<T> expected, final BrpGroep<T> actual,
                                                                       final boolean controleerFormeleHistorie, final boolean controleerMaterieleHistorie) {
        boolean isGelijk = true;
        final StringBuilder tijdelijkVerschillenLog = new StringBuilder();
        String mesg = "vergelijk groepen:" + FORMAT_EXPECTED_ACTUAL;
        tijdelijkVerschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                tijdelijkVerschillenLog.append("vergelijk groepen: Een van de groepen is null\n");
                isGelijk = false;
            }
        } else {
            if (!Objects.equals(expected.getInhoud(), actual.getInhoud())) {
                mesg = "vergelijk groepen: inhoud ongelijk" + FORMAT_EXPECTED_ACTUAL;
                tijdelijkVerschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getInhoud(), actual.getInhoud()));
                isGelijk = false;
            }

            final boolean controleerHistorie = controleerFormeleHistorie || controleerMaterieleHistorie;
            isGelijk = controleerHistorie(tijdelijkVerschillenLog, expected, actual, controleerHistorie, controleerMaterieleHistorie, isGelijk);
            isGelijk = controleerActies(tijdelijkVerschillenLog, expected, actual, controleerHistorie, controleerMaterieleHistorie, isGelijk);
        }

        if (!isGelijk) {
            verschillenLog.append(tijdelijkVerschillenLog);
        }
        return isGelijk;
    }

    private static <T extends BrpGroepInhoud> boolean controleerHistorie(final StringBuilder verschillenLog, final BrpGroep<T> expected,
                                                                         final BrpGroep<T> actual, final boolean controleerHistorie,
                                                                         final boolean controleerMaterieleHistorie, final boolean wasGelijk) {
        String mesg;
        boolean isGelijk = wasGelijk;
        final BrpHistorie expectedHistorie = expected.getHistorie();
        final BrpHistorie actualHistorie = actual.getHistorie();
        if (controleerHistorie
                && (!isDatumTijdRegistratieGelijk(expectedHistorie, actualHistorie)
                || !isDatumTijdVervalGelijk(expectedHistorie, actualHistorie)
                || !isNadereAanduidingVervalGelijk(expectedHistorie, actualHistorie))) {
            mesg = "vergelijk groepen: formele historie ongelijk" + FORMAT_EXPECTED_ACTUAL;
            verschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expectedHistorie, actualHistorie));
            isGelijk = false;
        }

        if (controleerMaterieleHistorie
                && (!isDatumAanvangGeldigheidGelijk(expectedHistorie, actualHistorie)
                || !isDatumEindeGeldigheidGelijk(expectedHistorie, actualHistorie))) {
            mesg = "vergelijk groepen: materiele historie ongelijk" + FORMAT_EXPECTED_ACTUAL;
            verschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expectedHistorie, actualHistorie));
            isGelijk = false;
        }
        return isGelijk;
    }

    private static <T extends BrpGroepInhoud> boolean controleerActies(final StringBuilder verschillenLog,
                                                                       final BrpGroep<T> expected,
                                                                       final BrpGroep<T> actual,
                                                                       final boolean controleerHistorie,
                                                                       final boolean controleerMaterieleHistorie,
                                                                       final boolean wasGelijk) {
        boolean isGelijk = wasGelijk;
        String mesg;
        if (controleerHistorie) {
            if (!BrpStapelVergelijker.isActieGelijk(expected.getActieInhoud(), actual.getActieInhoud())) {
                mesg = "vergelijk groepen: actie inhoud ongelijk" + FORMAT_EXPECTED_ACTUAL;
                verschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieInhoud(), actual.getActieInhoud()));
                isGelijk = false;
            }
            if (!BrpStapelVergelijker.isActieGelijk(expected.getActieVerval(), actual.getActieVerval())) {
                mesg = "vergelijk groepen: actie verval ongelijk" + FORMAT_EXPECTED_ACTUAL;
                verschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieVerval(), actual.getActieVerval()));
                isGelijk = false;
            }
        }

        if (controleerMaterieleHistorie && !BrpStapelVergelijker.isActieGelijk(expected.getActieGeldigheid(), actual.getActieGeldigheid())) {
            mesg = "vergelijk groepen: actie geldigheid ongelijk" + FORMAT_EXPECTED_ACTUAL;
            verschillenLog.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getActieGeldigheid(), actual.getActieGeldigheid()));
            isGelijk = false;
        }
        return isGelijk;
    }

    private static boolean isDatumTijdRegistratieGelijk(final BrpHistorie historie1, final BrpHistorie historie2) {
        return isDatumTijdRegistratieGelijk(historie1.getDatumTijdRegistratie(), historie2.getDatumTijdRegistratie());
    }

    private static boolean isDatumTijdRegistratieGelijk(final BrpDatumTijd tsReg1, final BrpDatumTijd tsReg2) {
        final BrpDatumTijd teNegerenDatumTijd = BrpDatumTijd.fromDatum(9999_01_01, null);
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

    private static boolean isActieGelijk(final BrpActie expected, final BrpActie actual) {
        final boolean result;
        if (expected == null || actual == null) {
            result = expected == null && actual == null;
        } else {
            result = expected.vergelijk(actual, false) && isDatumTijdRegistratieGelijk(expected.getDatumTijdRegistratie(), actual.getDatumTijdRegistratie());
        }
        return result;
    }
}
