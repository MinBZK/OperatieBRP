/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;

/**
 * Klasse voor constanten binnen de expressie taal.
 */
public final class ExpressieTaalConstanten {

    /**
     * Identifier voor het standaard object waarover een expressie gaat.
     */
    public static final String CONTEXT_PERSOON = "persoon";

    /**
     * Identifier voor het oude persoonsbeeld.
     */
    public static final String CONTEXT_PERSOON_OUD = "oud";

    /**
     * Identifier voor het nieuwe persoonsbeeld.
     */
    public static final String CONTEXT_PERSOON_NIEUW = "nieuw";

    /**
     * Identifier voor de ModelIndex.
     */
    public static final String CONTEXT_PROPERTY_PERSOONSLIJST = "root_model_index";

    /**
     * Key van de Context-property voor de waarde van de actuele selectiedatum.
     */
    public static final String PROPERTY_DATUM_START_SELECTIE = "SELECTIE_DATUM_WAARDE";

    /**
     * Context definitie ter indicatie dat enkel enkel actuele gegevens relevant zijn,
     * default {@link BooleanLiteral#WAAR}.
     */
    public static final String TOON_ACTUELE_GEGEVENS = "toonActueleGegevens";

    /**
     * Context key, ter indicatie dat bij het aanwijzen van een {@link MetaAttribuut} de waarde
     * terugggeven wordt ipv het attribuut zelf ({@link MetaAttribuutLiteral}).
     * default {@link BooleanLiteral#WAAR}.
     */
    public static final String ATTRIBUUTALSWAARDE = "attribuutalswaarde";

    /**
     * Key van de Context-property voor de waarde van de dienst-selectielijst mapping.
     */
    public static final String CONTEXT_PROPERTY_SELECTIE_LIJST = "SELECTIE_LIJST";


    private ExpressieTaalConstanten() {
    }
}
