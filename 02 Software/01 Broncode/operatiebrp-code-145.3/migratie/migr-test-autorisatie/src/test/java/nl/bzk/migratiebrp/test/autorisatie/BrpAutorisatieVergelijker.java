/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapelVergelijker;

/**
 * Vergleijk autorisaties.
 */
public final class BrpAutorisatieVergelijker {

    private static final String AUTORISATIE_PARTIJ_CODE = "partijCode";
    private static final String AUTORISATIE_PARTIJ_NAAM = "naam";

    private BrpAutorisatieVergelijker() {
    }

    /**
     * Vergelijk autorisaties.
     * @param verschillenLog log
     * @param expected verwacht
     * @param actual gevonden
     * @param skipPartij sla partijen over in de vergelijking
     * @return false, als verschillen zijn gevonden, anders true
     */
    public static boolean vergelijkAutorisaties(
            final StringBuilder verschillenLog,
            final BrpAutorisatie expected,
            final BrpAutorisatie actual,
            final boolean skipPartij) {
        boolean result = true;

        // Partij
        if (!skipPartij && !BrpAutorisatieVergelijker.equals(verschillenLog, "partij", expected.getPartij(), actual.getPartij())) {
            result = false;
        }

        // Leveringsautorisatie
        if (!BrpAutorisatieVergelijker.vergelijkLeveringsautorisaties(
                verschillenLog,
                expected.getLeveringsAutorisatieLijst(),
                actual.getLeveringsAutorisatieLijst())) {
            result = false;
        }

        return result;
    }

    /**
     * Vergelijk partij.
     * @param verschillenLog log
     * @param expected verwacht
     * @param actual gevonden
     * @return false, als verschillen zijn gevonden, anders true
     */
    public static boolean vergelijkPartij(final StringBuilder verschillenLog, final BrpPartij expected, final BrpPartij actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk partijen:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de partijen is null\n");
                equal = false;
            }
        } else {
            if (!BrpAutorisatieVergelijker.equals(lokaalVerschillenLog, AUTORISATIE_PARTIJ_NAAM, expected.getNaam(), actual.getNaam())
                    || !BrpAutorisatieVergelijker.equals(
                    lokaalVerschillenLog,
                    AUTORISATIE_PARTIJ_CODE,
                    expected.getPartijCode().getWaarde(),
                    actual.getPartijCode().getWaarde())
                    || !BrpStapelVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getPartijStapel(), actual.getPartijStapel(), false, false)) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /**
     * Vergelijk autorisatie besluiten.
     * @param verschillenLog log
     * @param expected verwacht
     * @param actual gevonden
     * @return false, als verschillen zijn gevonden, anders true
     */
    public static boolean vergelijkLeveringsautorisaties(
            final StringBuilder verschillenLog,
            final List<BrpLeveringsautorisatie> expected,
            final List<BrpLeveringsautorisatie> actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk leveringsautorisaties:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de leveringsautorisaties is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(
                        String.format("Lijsten bevatten niet even veel leveringsautorisaties (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final BrpLeveringsautorisatie expectedItem = expected.get(index);
                final BrpLeveringsautorisatie actualItem = actual.get(index);

                if (!BrpAutorisatieVergelijker.equals(lokaalVerschillenLog, "stelsel", expectedItem.getStelsel(), actualItem.getStelsel())
                        || !BrpAutorisatieVergelijker.equals(
                        lokaalVerschillenLog,
                        "indicatieModelautorisatie",
                        expectedItem.getIndicatieModelautorisatie(),
                        actualItem.getIndicatieModelautorisatie())
                        || !BrpStapelVergelijker.vergelijkStapels(
                        lokaalVerschillenLog,
                        expectedItem.getLeveringsautorisatieStapel(),
                        actualItem.getLeveringsautorisatieStapel())
                        || !BrpAutorisatieVergelijker.vergelijkDienstbundels(verschillenLog, expectedItem.getDienstbundels(), actualItem.getDienstbundels())) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    private static boolean vergelijkDienstbundels(
            final StringBuilder verschillenLog,
            final List<BrpDienstbundel> expected,
            final List<BrpDienstbundel> actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk dienstbundels:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de lijsten is null\n  ");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(
                        String.format("Lijsten bevatten niet even veel dienstbundels (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final BrpDienstbundel expectedItem = expected.get(index);
                final BrpDienstbundel actualItem = actual.get(index);

                if (!BrpAutorisatieVergelijker.vergelijkDienstbundel(lokaalVerschillenLog, index, expectedItem, actualItem)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean vergelijkDienstbundel(
            final StringBuilder verschillenLog,
            final int index,
            final BrpDienstbundel expected,
            final BrpDienstbundel actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk dienstbundel (").append(index).append("):\n    ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de dienstbundels is null\n");
                equal = false;
            }
        } else {
            if (!BrpAutorisatieVergelijker.vergelijkDiensten(lokaalVerschillenLog, expected.getDiensten(), actual.getDiensten())
                    || !BrpAutorisatieVergelijker.vergelijkDienstbundelLo3Rubrieken(lokaalVerschillenLog, expected.getLo3Rubrieken(), actual.getLo3Rubrieken())
                    || !BrpStapelVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getDienstbundelStapel(), actual.getDienstbundelStapel())) {
                equal = false;
            }

        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    private static boolean vergelijkDiensten(final StringBuilder verschillenLog, final List<BrpDienst> expected, final List<BrpDienst> actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk diensten:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de lijsten is null\n ");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(
                        String.format("Lijsten bevatten niet even veel diensten (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final BrpDienst expectedItem = expected.get(index);
                final BrpDienst actualItem = actual.get(index);

                if (!BrpAutorisatieVergelijker.vergelijkDienst(lokaalVerschillenLog, index, expectedItem, actualItem)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean vergelijkDienst(final StringBuilder verschillenLog, final int index, final BrpDienst expected, final BrpDienst actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk diensten (").append(index).append("):\n  ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de diensten is null\n");
                equal = false;
            }
        } else {
            if (!BrpAutorisatieVergelijker.equals(
                    lokaalVerschillenLog,
                    "effectAfnemersindicatie",
                    expected.getEffectAfnemersindicatie(),
                    actual.getEffectAfnemersindicatie())
                    || !BrpAutorisatieVergelijker.equals(lokaalVerschillenLog, "soort", expected.getSoortDienstCode(), actual.getSoortDienstCode())
                    || !BrpStapelVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getDienstStapel(), actual.getDienstStapel())
                    || !BrpStapelVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getDienstAttenderingStapel(), actual.getDienstAttenderingStapel())
                    || !BrpStapelVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getDienstSelectieStapel(), actual.getDienstSelectieStapel())) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    private static boolean vergelijkDienstbundelLo3Rubrieken(
            final StringBuilder verschillenLog,
            final List<BrpDienstbundelLo3Rubriek> expected,
            final List<BrpDienstbundelLo3Rubriek> actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk dienstbundello3rubrieken:\n ");

        if (isRubriekenLijstIsNullOfLeeg(expected) || actual == null || actual.isEmpty()) {
            if (isRubriekenLijstIsNullOfLeeg(expected) != isRubriekenLijstIsNullOfLeeg(actual)) {
                lokaalVerschillenLog.append("Een van de lijsten is null\n    ");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(
                        String.format("Lijsten bevatten niet even veel dienstbundello3rubrieken (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final BrpDienstbundelLo3Rubriek expectedItem = expected.get(index);
                final BrpDienstbundelLo3Rubriek actualItem = actual.get(index);

                if (!BrpAutorisatieVergelijker.vergelijkDienstbundelLo3Rubriek(lokaalVerschillenLog, index, expectedItem, actualItem)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean isRubriekenLijstIsNullOfLeeg(final List<BrpDienstbundelLo3Rubriek> rubriekenLijst) {
        return rubriekenLijst == null || rubriekenLijst.isEmpty();
    }

    private static boolean vergelijkDienstbundelLo3Rubriek(
            final StringBuilder verschillenLog,
            final int index,
            final BrpDienstbundelLo3Rubriek expected,
            final BrpDienstbundelLo3Rubriek actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk dienstbundello3rubriek (").append(index).append("):\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de dienstbundello3rubrieken is null\n");
                equal = false;
            }
        } else {
            if (!equals(lokaalVerschillenLog, "conversieRubriek", expected.getConversieRubriek(), actual.getConversieRubriek())) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T> boolean equals(final StringBuilder verschillenLog, final String naam, final T expected, final T actual) {
        boolean equal = true;

        if (!(expected == null && actual == null || expected != null && expected.equals(actual))) {
            verschillenLog.append(String.format("vergelijk %s: waarden zijn niet gelijk (expected=%s, actual=%s)%n", naam, expected, actual));
            equal = false;
        }

        return equal;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    //
    // /**
    // * Inhoud vergelijker voor Dienstbundel (geen vergelijking op naam).
    // */
    // public static final class DienstbundelInhoudVergelijker implements BrpInhoudVergelijker<BrpDienstbundelInhoud> {
    // @Override
    // public boolean isGelijk(final BrpDienstbundelInhoud expected, final BrpDienstbundelInhoud actual) {
    // boolean equal = true;
    // if (!Objects.equals(expected.getDatumIngang(), actual.getDatumIngang())) {
    // equal = false;
    // }
    // if (!Objects.equals(expected.getDatumEinde(), actual.getDatumEinde())) {
    // equal = false;
    // }
    // if (!Objects.equals(expected.getNaderePopulatiebeperking(), actual.getNaderePopulatiebeperking())) {
    // equal = false;
    // }
    // if (!Objects.equals(
    // expected.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(),
    // actual.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd()))
    // {
    // equal = false;
    // }
    // if (!Objects.equals(expected.getToelichting(), actual.getToelichting())) {
    // equal = false;
    // }
    // if (!Objects.equals(expected.getGeblokkeerd(), actual.getGeblokkeerd())) {
    // equal = false;
    // }
    //
    // return equal;
    // }
    // }

}
