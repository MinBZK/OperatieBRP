/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.DatumtijdLiteral;

/**
 * Hulpklasse voor datumvergelijkingen.
 */
final class DatumVergelijkHelper {

    private final DatumLiteral datumLinks;
    private final DatumLiteral datumRechts;
    private final boolean datumsBeideVolledigBekend;
    private final DatumdeelVergelijkResultaat[] deelvergelijking;

    /**
     * Constructor.
     *
     * @param lh de linker expressie
     * @param rh de rechter expressie
     */
    DatumVergelijkHelper(final Expressie lh, final Expressie rh) {
        datumLinks = (DatumLiteral) lh;
        datumRechts = (DatumLiteral) rh;
        datumsBeideVolledigBekend = datumLinks.isVolledigBekend2() && datumRechts.isVolledigBekend2();
        deelvergelijking = new DatumdeelVergelijkResultaat[]{
                maakDeelvergelijk(datumLinks.getJaar(), datumRechts.getJaar()),
                maakDeelvergelijk(datumLinks.getMaand(), datumRechts.getMaand()),
                maakDeelvergelijk(datumLinks.getDag(), datumRechts.getDag()),
        };
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#KLEINER groter } vergelijking.
     */
    Expressie bepaalKleinerDan() {

        BooleanLiteral resultaat;
        if (isVolledigIrrelevant()) {
            resultaat = BooleanLiteral.WAAR;
        } else if (datumsBeideVolledigBekend) {
            resultaat = BooleanLiteral.valueOf(datumCompare() == -1);
        } else {
            resultaat = bepaalKleinerDanSub();
        }
        return resultaat;
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#GROTER groter } vergelijking.
     */
    Expressie bepaalGroterDan() {
        BooleanLiteral resultaat;
        if (isVolledigIrrelevant()) {
            resultaat = BooleanLiteral.WAAR;
        } else if (datumsBeideVolledigBekend) {
            resultaat = BooleanLiteral.valueOf(datumCompare() == 1);
        } else {
            resultaat = bepaalGroterDanSub();
        }
        return resultaat;
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#KLEINER_OF_GELIJK groter } vergelijking.
     */
    Expressie bepaalKleinerDanOfGelijk() {
        return BooleanLiteral.valueOf(bepaalGelijk().alsBoolean() || bepaalKleinerDan().alsBoolean());
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#GROTER_OF_GELIJK groter } vergelijking.
     */
    Expressie bepaalGroterDanOfGelijk() {
        return BooleanLiteral.valueOf(bepaalGelijk().alsBoolean() || bepaalGroterDan().alsBoolean());
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#GELIJK groter } vergelijking.
     */
    Expressie bepaalGelijk() {
        if (datumsBeideVolledigBekend) {
            return BooleanLiteral.valueOf(datumCompare() == 0);
        }
        final boolean resultaat = is(deelvergelijking[0], DatumdeelVergelijkResultaat.GELIJK, DatumdeelVergelijkResultaat.IRRELEVANT)
                && is(deelvergelijking[1], DatumdeelVergelijkResultaat.GELIJK, DatumdeelVergelijkResultaat.IRRELEVANT)
                && is(deelvergelijking[2], DatumdeelVergelijkResultaat.GELIJK, DatumdeelVergelijkResultaat.IRRELEVANT);
        return BooleanLiteral.valueOf(resultaat);
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#ONGELIJK groter } vergelijking.
     */
    Expressie bepaalOngelijk() {
        return BooleanLiteral.valueOf(!bepaalGelijk().alsBoolean());
    }

    /**
     * @return bepaalt het resultaat van een {@link OperatorType#WILDCARD groter } vergelijking.
     */
    Expressie bepaalWildcardGelijk() {
        return bepaalGelijk();
    }

    private BooleanLiteral bepaalGroterDanSub() {
        for (int i = 0; i < deelvergelijking.length; i++) {
            final DatumdeelVergelijkResultaat datumdeelVergelijkResultaat = deelvergelijking[i];
            if (datumdeelVergelijkResultaat == DatumdeelVergelijkResultaat.GROTER) {
                boolean kleinerErvoor = isKleinerErvoor(i);
                if (!kleinerErvoor) {
                    return BooleanLiteral.WAAR;
                }
            }
        }
        return BooleanLiteral.ONWAAR;
    }

    private BooleanLiteral bepaalKleinerDanSub() {
        for (int i = 0; i < deelvergelijking.length; i++) {
            final DatumdeelVergelijkResultaat datumdeelVergelijkResultaat = deelvergelijking[i];
            if (datumdeelVergelijkResultaat == DatumdeelVergelijkResultaat.KLEINER) {
                boolean groterErvoor = isGroterErvoor(i);
                if (!groterErvoor) {
                    return BooleanLiteral.WAAR;
                }
            }
        }
        return BooleanLiteral.ONWAAR;
    }

    private boolean isKleinerErvoor(final int i) {
        boolean kleinerErvoor = false;
        for (int j = 0; i > 0 && j < i; j++) {
            final DatumdeelVergelijkResultaat vergelijkResultaat = deelvergelijking[j];
            if (vergelijkResultaat == DatumdeelVergelijkResultaat.KLEINER) {
                kleinerErvoor = true;
            }
        }
        return kleinerErvoor;
    }

    private boolean isGroterErvoor(final int i) {
        boolean groterErvoor = false;
        for (int j = 0; i > 0 && j < i; j++) {
            final DatumdeelVergelijkResultaat vergelijkResultaat = deelvergelijking[j];
            if (vergelijkResultaat == DatumdeelVergelijkResultaat.GROTER) {
                groterErvoor = true;
            }
        }
        return groterErvoor;
    }

    private DatumdeelVergelijkResultaat maakDeelvergelijk(final Datumdeel dl, final Datumdeel dr) {
        if (dl.isWaarde() && dr.isWaarde()) {
            final int compareTo = dl.compareTo(dr);
            return compareTo == 0 ? DatumdeelVergelijkResultaat.GELIJK
                    : compareTo < 0
                    ? DatumdeelVergelijkResultaat.KLEINER
                    : DatumdeelVergelijkResultaat.GROTER;
        }
        return DatumdeelVergelijkResultaat.IRRELEVANT;
    }

    private boolean is(final DatumdeelVergelijkResultaat resultaat, final DatumdeelVergelijkResultaat... mogelijkheden) {
        for (DatumdeelVergelijkResultaat resultaat1 : mogelijkheden) {
            if (resultaat == resultaat1) {
                return true;
            }
        }
        return false;
    }

    private boolean isVolledigIrrelevant() {
        boolean volledigIrrelevant = true;
        for (DatumdeelVergelijkResultaat datumdeelVergelijkResultaat : deelvergelijking) {
            if (datumdeelVergelijkResultaat != DatumdeelVergelijkResultaat.IRRELEVANT) {
                volledigIrrelevant = false;
                break;
            }
        }
        return volledigIrrelevant;
    }

    private int datumCompare() {
        final int resultaat;
        if (datumLinks instanceof DatumtijdLiteral && datumRechts instanceof DatumtijdLiteral) {
            final int stringVergelijk = alsVergelijkbareString((DatumtijdLiteral) this.datumLinks)
                    .compareTo(alsVergelijkbareString((DatumtijdLiteral) this.datumRechts));
            // dit is nodig omdat de operators verkeerde vergelijkingen doen (enkel -1, 0 en 1...)
            if (stringVergelijk < 0) {
                resultaat = -1;
            } else if (stringVergelijk > 0) {
                resultaat = 1;
            } else {
                resultaat = 0;
            }
        } else {
            resultaat = Integer.valueOf(datumLinks.alsInteger()).compareTo(datumRechts.alsInteger());
        }
        return resultaat;
    }

    private String alsVergelijkbareString(final DatumtijdLiteral datumtijdLiteral) {
        return String.format("%04d%02d%02d%02d%02d%02d",
                datumtijdLiteral.getJaar().getWaarde(),
                datumtijdLiteral.getMaand().getWaarde(),
                datumtijdLiteral.getDag().getWaarde(),
                datumtijdLiteral.getUur(),
                datumtijdLiteral.getMinuut(),
                datumtijdLiteral.getSeconde());
    }
}
