/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit enum type representeert de mogelijk waarden van het LO3 element 61.10 Aanduiding naamgebruik.
 */
public enum Lo3AanduidingNaamgebruikCodeEnum {

    /**
     * Eigen geslachtsnaam.
     */
    EIGEN_GESLACHTSNAAM("E", "Eigen geslachtsnaam"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner.
     */
    GESLACHTSNAAM_PARTNER("P", "Geslachtsnaam partner"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam.
     */
    GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM("V", "Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam.
     */
    GESLACHTNAAM_PARTER_NA_EIGEN_GESLACHTSNAAM("N", "Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3AanduidingNaamgebruikCodeEnum> CODE_MAP = new HashMap<>();

    static {
        for (final Lo3AanduidingNaamgebruikCodeEnum value : Lo3AanduidingNaamgebruikCodeEnum.values()) {
            CODE_MAP.put(value.getCode(), value);
        }
    }


    private final String code;
    private final String label;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    Lo3AanduidingNaamgebruikCodeEnum(final String code, final String label) {
        this.code = code;
        this.label = label;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bestaat de gegeven code in de enumeratie.
     * @param code code
     * @return true, als de code bestaat, anders false
     */
    public static boolean containsCode(final String code) {
        return CODE_MAP.containsKey(code);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven code.
     * @param code code
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3AanduidingNaamgebruikCodeEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * @param element element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3AanduidingNaamgebruikCodeEnum getByElement(final Lo3AanduidingNaamgebruikCode element) {
        return element == null ? null : getByCode(element.getWaarde());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de enumeratie als element.
     * @return element
     */
    public Lo3AanduidingNaamgebruikCode asElement() {
        return new Lo3AanduidingNaamgebruikCode(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * @param element element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3AanduidingNaamgebruikCode element) {
        return Lo3Validatie.isElementGevuld(element) && code.equals(element.getWaarde());
    }

    /**
     * Geef de waarde van label.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

}
