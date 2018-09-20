/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;

/**
 * Deze enum type representeert de LO3 waarde voor indicatie onjuist.
 * 
 */
public enum Lo3IndicatieOnjuistEnum {

    /** Onjuist. */
    ONJUIST("O"),

    /** Strijdig met de openbare order. */
    STRIJDIG("S");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3IndicatieOnjuistEnum> CODE_MAP =
            new HashMap<String, Lo3IndicatieOnjuistEnum>() {
                private static final long serialVersionUID = 1L;
                {
                    for (final Lo3IndicatieOnjuistEnum value : Lo3IndicatieOnjuistEnum.values()) {
                        put(value.getCode(), value);
                    }
                }
            };

    private final String code;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3IndicatieOnjuistEnum(final String code) {
        this.code = code;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bestaat de gegeven code in de enumeratie.
     * 
     * @param code
     *            code
     * @return true, als de code bestaat, anders false
     */
    public static boolean containsCode(final String code) {
        return CODE_MAP.containsKey(code);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven code.
     * 
     * @param code
     *            code
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3IndicatieOnjuistEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * 
     * @param element
     *            element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3IndicatieOnjuistEnum getByElement(final Lo3IndicatieOnjuist element) {
        return element == null ? null : getByCode(element.getCode());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de enumeratie als element.
     * 
     * @return element
     */
    public Lo3IndicatieOnjuist asElement() {
        return new Lo3IndicatieOnjuist(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * 
     * @param element
     *            element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3IndicatieOnjuist element) {
        return element == null ? false : code.equals(element.getCode());
    }

}
