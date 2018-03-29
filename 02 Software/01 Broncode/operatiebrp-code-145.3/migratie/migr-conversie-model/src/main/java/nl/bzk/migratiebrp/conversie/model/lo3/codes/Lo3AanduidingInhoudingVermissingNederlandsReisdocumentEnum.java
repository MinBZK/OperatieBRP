/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze enum type representeert de mogelijke aanduidingen voor inhouding dan wel vermissing Nederlands reisdocument
 * (35.70) in LO3.
 */
public enum Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum {

    /**
     * ingehouden, ingeleverd.
     */
    INGEHOUDEN("I", "Ingehouden"),
    /**
     * vermist.
     */
    VERMIST("V", "Vermist"),
    /**
     * onbekend.
     */
    ONBEKEND(".", "Onbekend"),
    /**
     * Van rechtswege vervallen.
     */
    RECHTSWEGE("R", "Van rechtswege vervallen");

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final Map<String, Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum> CODE_MAP = new HashMap<>();

    static {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum[] values = Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.values();
        for (final Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum value : values) {
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

    Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum(final String code, final String label) {
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
    public static Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * @param element element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum getByElement(
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument element) {
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
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument asElement() {
        return new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * @param element element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument element) {
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
