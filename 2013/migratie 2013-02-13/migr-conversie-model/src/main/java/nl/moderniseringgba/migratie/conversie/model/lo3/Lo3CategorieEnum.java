/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Enumeratie van alle LO3 categorieen.
 * 
 * 
 * 
 */
public enum Lo3CategorieEnum {

    /**
     * Persoon.
     */
    CATEGORIE_01("01", new String[] { "01", "02", "03", "04", "20", "61", "81", "82", "83", "85", "86", "88" }),
    /**
     * Ouder1.
     */
    CATEGORIE_02("02", new String[] { "01", "02", "03", "04", "62", "81", "82", "83", "85", "86" }),
    /**
     * Ouder2.
     */
    CATEGORIE_03("03", new String[] { "01", "02", "03", "04", "62", "81", "82", "83", "85", "86" }),
    /**
     * Nationaliteit.
     */
    CATEGORIE_04("04", new String[] { "05", "63", "64", "65", "82", "83", "85", "86", "88" }),
    /**
     * Huwelijk/geregistreerd partnerschap.
     */
    CATEGORIE_05("05", new String[] { "01", "02", "03", "04", "06", "07", "15", "81", "82", "83", "85", "86" }),
    /**
     * Overlijden.
     */
    CATEGORIE_06("06", new String[] { "08", "81", "82", "83", "85", "86" }),
    /**
     * Inschrijving.
     */
    CATEGORIE_07("07", new String[] { "66", "67", "68", "69", "70", "71", "80", "87", "88" }),
    /**
     * Verblijfplaats.
     */
    CATEGORIE_08("08", new String[] { "09", "10", "11", "12", "13", "14", "72", "75", "83", "85", "86", "88" }),
    /**
     * Kind.
     */
    CATEGORIE_09("09", new String[] { "01", "02", "03", "81", "82", "83", "85", "86" }),
    /**
     * Verblijfstitel.
     */
    CATEGORIE_10("10", new String[] { "39", "83", "85", "86" }),
    /**
     * Gezagsverhouding.
     */
    CATEGORIE_11("11", new String[] { "32", "33", "82", "83", "85", "86" }),
    /**
     * Reisdocument.
     */
    CATEGORIE_12("12", new String[] { "35", "36", "37", "82", "83", "85", "86" }),
    /**
     * Kiesrecht.
     */
    CATEGORIE_13("13", new String[] { "31", "38", "82" }),
    /**
     * Afnemersindicatie persoonslijst.
     */
    CATEGORIE_14("14", new String[] { "40", "85" }),
    /**
     * Aantekening.
     */
    CATEGORIE_15("15", new String[] { "42", }),
    /**
     * Verwijzing. TODO: In LO 3.8 zijn groepen 11 en 12 niet meer toegestaan in categorie 21/71
     */
    CATEGORIE_21("21", new String[] { "01", "02", "03", "09", "11", "12", "70", "83", "85", "86" }),
    /**
     * Persoon (historisch).
     */
    CATEGORIE_51("51", new String[] { "01", "02", "03", "04", "20", "61", "81", "82", "83", "84", "85", "86", "88" }),
    /**
     * Ouder1 (historisch).
     */
    CATEGORIE_52("52", new String[] { "01", "02", "03", "04", "62", "81", "82", "83", "84", "85", "86" }),
    /**
     * Ouder2 (historisch).
     */
    CATEGORIE_53("53", new String[] { "01", "02", "03", "04", "62", "81", "82", "83", "84", "85", "86" }),
    /**
     * Nationaliteit (historisch).
     */
    CATEGORIE_54("54", new String[] { "05", "63", "64", "65", "82", "83", "84", "85", "86", "88" }),
    /**
     * Huwelijk/geregistreerd partnerschap (historisch).
     */
    CATEGORIE_55("55", new String[] { "01", "02", "03", "04", "06", "07", "15", "81", "82", "83", "84", "85", "86" }),
    /**
     * Overlijden (historisch).
     */
    CATEGORIE_56("56", new String[] { "08", "81", "82", "83", "84", "85", "86" }),
    /**
     * Verblijfplaats (historisch).
     */
    CATEGORIE_58("58", new String[] { "09", "10", "11", "12", "13", "14", "72", "75", "83", "84", "85", "86", "88" }),
    /**
     * Kind (historisch).
     */
    CATEGORIE_59("59", new String[] { "01", "02", "03", "81", "82", "83", "84", "85", "86" }),
    /**
     * Verblijfstitel (historisch).
     */
    CATEGORIE_60("60", new String[] { "39", "83", "84", "85", "86" }),
    /**
     * Gezagsverhouding (historisch).
     */
    CATEGORIE_61("61", new String[] { "32", "33", "82", "83", "84", "85", "86" }),
    /**
     * Afnemersindicatie persoonslijst (historisch).
     */
    CATEGORIE_64("64", new String[] { "40", "85" }),
    /**
     * Verwijzing (historisch).
     */
    CATEGORIE_71("71", new String[] { "01", "02", "03", "09", "11", "12", "70", "83", "84", "85", "86" });

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** LEESBARE NAMEN *************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** Persoon. */
    public static final Lo3CategorieEnum PERSOON = CATEGORIE_01;
    /** Ouder 1. */
    public static final Lo3CategorieEnum OUDER_1 = CATEGORIE_02;
    /** Ouder 2. */
    public static final Lo3CategorieEnum OUDER_2 = CATEGORIE_03;
    /** Nationaliteit. */
    public static final Lo3CategorieEnum NATIONALITEIT = CATEGORIE_04;
    /** Huwelijk. */
    public static final Lo3CategorieEnum HUWELIJK = CATEGORIE_05;
    /** Overlijden. */
    public static final Lo3CategorieEnum OVERLIJDEN = CATEGORIE_06;
    /** Inschrijving. */
    public static final Lo3CategorieEnum INSCHRIJVING = CATEGORIE_07;
    /** Verblijfplaats. */
    public static final Lo3CategorieEnum VERBLIJFPLAATS = CATEGORIE_08;
    /** Kind. */
    public static final Lo3CategorieEnum KIND = CATEGORIE_09;
    /** Verblijfstitel. */
    public static final Lo3CategorieEnum VERBLIJFSTITEL = CATEGORIE_10;
    /** Gezagsverhouding. */
    public static final Lo3CategorieEnum GEZAGSVERHOUDING = CATEGORIE_11;
    /** Reisdocument. */
    public static final Lo3CategorieEnum REISDOCUMENT = CATEGORIE_12;
    /** Kiesrecht. */
    public static final Lo3CategorieEnum KIESRECHT = CATEGORIE_13;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final int HISTORIE_OFFSET = 50;
    /**
     * Categorie naam.
     */
    private final String categorie;
    private final List<String> groepen;

    /**
     * Constructor.
     * 
     * @param categorie
     *            Het categorienummer
     */
    private Lo3CategorieEnum(final String categorie, final String[] groepen) {
        this.categorie = categorie;
        this.groepen = Collections.unmodifiableList(Arrays.asList(groepen));
    }

    /**
     * @return the categorie
     */
    public final String getCategorie() {
        return categorie;
    }

    /**
     * @return the categorie
     */
    public final int getCategorieAsInt() {
        return Integer.parseInt(getCategorie());
    }

    public final List<String> getGroepen() {
        return groepen;
    }

    @Override
    public final String toString() {
        return categorie;
    }

    /**
     * @param categorie
     *            de categorie naam
     * @return de coressponderende LO3 categorie
     */
    public static final Lo3CategorieEnum getLO3Categorie(final String categorie) {
        return Lo3CategorieEnum.valueOf("CATEGORIE_" + categorie);
    }

    /**
     * Geeft aan of het meegegeven categorienummer een geldige categorie is.
     * 
     * @param categorie
     *            de categorie code als String
     * @return true als er bij de code een bijbehorende categorie is gedefinieerd
     */
    public static boolean isValidCategorie(final String categorie) {
        final Lo3CategorieEnum[] values = Lo3CategorieEnum.values();
        for (final Lo3CategorieEnum lo3Categorie : values) {
            if (lo3Categorie.categorie.equals(categorie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Geeft aan of het meegegeven categorienummer een geldige categorie is.
     * 
     * @param categorie
     *            de categorie code als int
     * @return true als er bij de code een bijbehorende categorie is gedefinieerd
     */
    public static boolean isValidCategorie(final int categorie) {
        return isValidCategorie(categorieAsIntToString(categorie));
    }

    /**
     * Geef op basis van een actuele categorie zijn historische categorie terug, of null als deze niet bestaat.
     * 
     * @param categorie
     *            de actuele categorie
     * @return de historische categorie of null
     */
    public static Lo3CategorieEnum bepaalHistorischeCategorie(final Lo3CategorieEnum categorie) {
        final String catString = categorie.getCategorie();
        final int historisch = Integer.valueOf(catString) + HISTORIE_OFFSET;
        if (isValidCategorie("" + historisch)) {
            return getLO3Categorie("" + historisch);
        } else {
            return null;
        }
    }

    /**
     * Geef op basis van een categorie zijn actuele categorie terug. Als de gegeven categorie al actueel is dan wordt de
     * categorie teruggegeven.
     * 
     * @param categorie
     *            de categorie
     * @return de actuele categorie
     */
    public static Lo3CategorieEnum bepaalActueleCategorie(final Lo3CategorieEnum categorie) {
        if (categorie.isActueel()) {
            return categorie;
        }

        final String catString = categorie.getCategorie();
        final int actueel = Integer.valueOf(catString) - HISTORIE_OFFSET;

        return getLO3Categorie(Integer.toString(actueel));
    }

    /**
     * @return true als de categorie actueel is, false als de categorie historisch is
     */
    public boolean isActueel() {
        return Integer.valueOf(getCategorie()) < HISTORIE_OFFSET;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3CategorieEnum> CODE_MAP = new HashMap<String, Lo3CategorieEnum>() {
        private static final long serialVersionUID = 1L;

        {
            final Lo3CategorieEnum[] values = Lo3CategorieEnum.values();
            for (final Lo3CategorieEnum value : values) {
                put(value.getCategorie(), value);
            }
        }
    };

    /**
     * Geef de enumeratie waarde voor de gegeven severity.
     * 
     * @param categorie
     *            de categorie
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3CategorieEnum valueOfCategorie(final String categorie) {
        return CODE_MAP.get(categorie);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven severity.
     * 
     * @param categorie
     *            de categorie
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3CategorieEnum valueOfCategorie(final int categorie) {
        return Lo3CategorieEnum.valueOfCategorie(categorieAsIntToString(categorie));
    }

    private static String categorieAsIntToString(final int categorie) {
        return String.valueOf(StringUtils.leftPad(String.valueOf(categorie), 2, '0'));
    }

}
