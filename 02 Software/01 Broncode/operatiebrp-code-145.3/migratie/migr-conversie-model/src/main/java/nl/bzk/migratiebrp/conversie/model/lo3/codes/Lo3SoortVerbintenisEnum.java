/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze enum type identificeert de LO3 Soort verbintenis gebruikt in element 15.10.
 */
public enum Lo3SoortVerbintenisEnum {

    /**
     * Onbekend.
     */
    ONBEKEND(".", "Onbekend"),

    /**
     * Huwelijk.
     */
    HUWELIJK("H", "Huwelijk"),

    /**
     * Geregistreerd partnerschap.
     */
    GEREGISTREERD_PARTNERSCHAP("P", "Geregistreerd partnerschap");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3SoortVerbintenisEnum> CODE_MAP = new HashMap<>();

    static {
        for (final Lo3SoortVerbintenisEnum value : Lo3SoortVerbintenisEnum.values()) {
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

    Lo3SoortVerbintenisEnum(final String code, final String label) {
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
    public static Lo3SoortVerbintenisEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * @param element element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3SoortVerbintenisEnum getByElement(final Lo3SoortVerbintenis element) {
        return element == null ? null : getByCode(element.getWaarde());
    }

    /**
     * Geeft terug of de verbintenis een 'partnerschap' is.
     * @param lo3SoortVerbintenis de soort verbintenis
     * @return True indien partnerschap, false in overige gevallen.
     */
    public static boolean isPartnerschap(final Lo3SoortVerbintenis lo3SoortVerbintenis) {
        return GEREGISTREERD_PARTNERSCHAP.getCode().equals(lo3SoortVerbintenis.getWaarde());
    }

    /**
     * Geeft terug of de verbintenis een 'huwelijk' is.
     * @param lo3SoortVerbintenis de soort verbintenis
     * @return True indien huwelijk, false in overige gevallen.
     */
    public static boolean isHuwelijk(final Lo3SoortVerbintenis lo3SoortVerbintenis) {
        return HUWELIJK.getCode().equals(lo3SoortVerbintenis.getWaarde());
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
    public Lo3SoortVerbintenis asElement() {
        return new Lo3SoortVerbintenis(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * @param element element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3SoortVerbintenis element) {
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
