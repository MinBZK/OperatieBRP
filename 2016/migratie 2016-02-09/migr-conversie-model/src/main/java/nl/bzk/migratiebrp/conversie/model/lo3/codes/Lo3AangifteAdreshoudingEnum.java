/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Deze enum type representeert de LO3 waarde voor omschrijving van de aangifte adreshouding.
 */
public enum Lo3AangifteAdreshoudingEnum {

    /** ambtshalve. */
    AMBSTHALVE("A", "Ambtshalve"),
    /** ministrieel besluit. */
    MINISTRIEEL_BESLUIT("B", "Ministerieel besluit"),
    /** gezaghouder. */
    GEZAGHOUDER("G", "Gezaghouder"),
    /** hoofd instelling. */
    HOOFD_INSTELLING("H", "Hoofd instelling"),
    /** ingeschrevene. */
    INGESCHREVENE("I", "Ingeschrevene"),
    /** meerderjarig inwonend kind voor ouder. */
    KIND("K", "Kind"),
    /** meerderjarige gemachtigde. */
    GEMACHTIGDE("M", "Meerderjarige gemachtigde"),
    /** inwonende ouder voor meerderjarig kind. */
    OUDER("O", "Ouder"),
    /** echtgenoot/geregistreerd partner. */
    PARTNER("P", "Partner"),
    /** technische wijziging i.v.m. BAG. */
    TECHNISCHE_WIJZIGING("T", "Technische wijziging"),
    /** infrastructurele wijziging. */
    INFRASTRUCTURELE_WIJZIGING("W", "Infrastructurele wijziging"),
    /** Standaardwaarde indien onbekend. */
    ONBEKEND(".", "Onbekend");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3AangifteAdreshoudingEnum> CODE_MAP = new HashMap<String, Lo3AangifteAdreshoudingEnum>() {
        private static final long serialVersionUID = 1L;
        {
            for (final Lo3AangifteAdreshoudingEnum value : Lo3AangifteAdreshoudingEnum.values()) {
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

    private Lo3AangifteAdreshoudingEnum(final String code, final String label) {
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
    public static Lo3AangifteAdreshoudingEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * 
     * @param element
     *            element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3AangifteAdreshoudingEnum getByElement(final Lo3AangifteAdreshouding element) {
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
    public Lo3AangifteAdreshouding asElement() {
        return new Lo3AangifteAdreshouding(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * 
     * @param element
     *            element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3AangifteAdreshouding element) {
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
