/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Enumeratie voor indicatie gezag minderjarige.
 */
public enum Lo3IndicatieGezagMinderjarigeEnum {

    /**
     * Ouder 1 heeft het gezag.
     */
    OUDER_1("1", "Ouder1"),
    /**
     * Ouder 2 heeft het gezag.
     */
    OUDER_2("2", "Ouder2"),
    /**
     * Een of meer derden hebben het gezag.
     */
    DERDE("D", "Derde"),
    /**
     * Ouder 1 en een derde hebben het gezag.
     */
    OUDER_1_EN_DERDE("1D", "Ouder1 en derde"),
    /**
     * Ouder 2 en een derde hebben het gezag.
     */
    OUDER_2_EN_DERDE("2D", "Ouder2 en derde"),
    /**
     * Ouder1 en Ouder2 hebben het gezag.
     */
    OUDERS_1_EN_2("12", "Ouders 1 en 2");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3IndicatieGezagMinderjarigeEnum> CODE_MAP = new HashMap<String, Lo3IndicatieGezagMinderjarigeEnum>() {
        private static final long serialVersionUID = 1L;
        {
            for (final Lo3IndicatieGezagMinderjarigeEnum value : Lo3IndicatieGezagMinderjarigeEnum.values()) {
                put(value.getCode(), value);
            }
        }
    };

    private final String code;
    private final String label;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3IndicatieGezagMinderjarigeEnum(final String code, final String label) {
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
    public static Lo3IndicatieGezagMinderjarigeEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * 
     * @param element
     *            element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3IndicatieGezagMinderjarigeEnum getByElement(final Lo3IndicatieGezagMinderjarige element) {
        return element == null ? null : getByCode(element.getWaarde());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van code.
     *
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
    public Lo3IndicatieGezagMinderjarige asElement() {
        return new Lo3IndicatieGezagMinderjarige(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * 
     * @param element
     *            element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3IndicatieGezagMinderjarige element) {
        return Validatie.isElementGevuld(element) && code.equals(element.getWaarde());
    }

    /**
     * Geef de waarde van label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

}
