/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;

/**
 * Deze enum type representeert de LO3 waarde voor indicatie curateleregister (33.10).
 * 
 */
public enum Lo3IndicatieCurateleregisterEnum {

    /** belemmering verstrekking Nederlands reisdocument. */
    CURATOR_AANGESTELD(1);

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<Integer, Lo3IndicatieCurateleregisterEnum> CODE_MAP =
            new HashMap<Integer, Lo3IndicatieCurateleregisterEnum>() {
                private static final long serialVersionUID = 1L;
                {
                    for (final Lo3IndicatieCurateleregisterEnum value : Lo3IndicatieCurateleregisterEnum.values()) {
                        put(value.getCode(), value);
                    }
                }
            };

    private final Integer code;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3IndicatieCurateleregisterEnum(final Integer code) {
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
    public static boolean containsCode(final Integer code) {
        return CODE_MAP.containsKey(code);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven code.
     * 
     * @param code
     *            code
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3IndicatieCurateleregisterEnum getByCode(final Integer code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * 
     * @param element
     *            element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3IndicatieCurateleregisterEnum getByElement(final Lo3IndicatieCurateleregister element) {
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
    public Integer getCode() {
        return code;
    }

    /**
     * Geef de enumeratie als element.
     * 
     * @return element
     */
    public Lo3IndicatieCurateleregister asElement() {
        return new Lo3IndicatieCurateleregister(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * 
     * @param element
     *            element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3IndicatieCurateleregister element) {
        return element == null ? false : code.equals(element.getCode());
    }

}
