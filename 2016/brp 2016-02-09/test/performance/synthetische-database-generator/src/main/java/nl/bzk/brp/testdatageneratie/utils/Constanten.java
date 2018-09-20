/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;


import java.util.Arrays;
import java.util.List;

/**
 * Constanten die in het hele project gebruikt worden.
 */
public final class Constanten {

    /** 0 */
    public static final int NUL = 0;
    /** 1 */
    public static final int EEN = 1;
    /** 2 */
    public static final int TWEE = 2;
    /** 3 */
    public static final int DRIE = 3;
    /** 4 */
    public static final int VIER = 4;
    /** 5 */
    public static final int VIJF = 5;
    /** 6 */
    public static final int ZES = 6;
    /** 7 */
    public static final int ZEVEN = 7;
    /** 8 */
    public static final int ACHT = 8;
    /** 9 */
    public static final int NEGEN = 9;
    /** 10 */
    public static final int TIEN = 10;
    /** 11 */
    public static final int ELF = 11;
    /** 20 */
    public static final int TWINTIG = 20;
    /** 22 */
    public static final int TWEEENTWINTIG = 22;
    /** 25 */
    public static final int VIJFENTWINTIG = 25;
    /** 35 */
    public static final int VIJFENDERTIG = 35;
    /** 40 */
    public static final int VEERTIG = 40;
    /** 50 */
    public static final int VIJFTIG = 50;
    /** 90 */
    public static final int NEGENTIG = 90;
    /** 100 */
    public static final int HONDERD = 100;
    /** 1000 */
    public static final int DUIZEND = 1000;
    /** 1024 */
    public static final int DUIZEND_VIERENTWINTIG = 1024;
    /** 2347 */
    public static final int TWEEDUIZEND_DRIEHONDERD_ZEVENENVEERTIG = 2347;
    /** 10000 */
    public static final int TIENDUIZEND = 10000;
    /** 100000 */
    public static final int HONDERDDUIZEND = 100000;

    /**
     * Aanvang relatie van ADAM en EVA.
     */
    public static final int ADAM_EVA_DATUM_AANVANG_RELATIE = 19950101;

    /**
     * De logische persoonids van niet inegzetenen. Deze krijgen bijvoorbeeld geen adam en eva ouders.
     */
    public static final List<Integer> NIET_INGEZETEN_LOGISCHE_PERSOON_IDS = Arrays.asList(12040);

    /**
     * Instantieert Constanten, private constructor ivm utility klasse.
     */
    private Constanten() {
    }
}
