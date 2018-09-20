/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Reden einde relatie code.
 */
@Embeddable
public class RedenEindeRelatieCodeAttribuut extends AbstractRedenEindeRelatieCodeAttribuut {

    /**
     * Constante voor reden einde relatie overlijden code.
     */
    public static final String REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING = "O";

    /**
     * Constante voor reden einde relatie rechtsvermoeden overlijden code.
     */
    public static final String REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE_STRING = "R";

    /**
     * Constante voor reden einde relatie nietigverklaring code.
     */
    public static final String REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING = "N";

    /**
     * Constante voor reden einde relatie omzetting soort verbintenis code.
     */
    public static final String REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE_STRING = "Z";

    /**
     * Constante voor reden einde relatie andere verbintenis code.
     */
    public static final String REDEN_EINDE_RELATIE_ANDERE_VERBINTENIS_CODE_STRING = "A";

    /**
     * Constante voor reden einde relatie echtscheiding code.
     */
    public static final String REDEN_EINDE_RELATIE_ECHTSCHEIDING_CODE_STRING = "S";

    /**
     * Constante voor reden einde relatie vreemd recht code.
     */
    public static final String REDEN_EINDE_RELATIE_VREEMD_RECHT_CODE_STRING = "V";

    /**
     * Constante voor reden einde relatie onbekend code.
     */
    public static final String REDEN_EINDE_RELATIE_ONBEKEND_CODE_STRING = "?";

    /**
     * Constante voor reden einde relatie overlijden code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_OVERLIJDEN_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING);

    /**
     * Constante voor reden einde relatie overlijden code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE_STRING);

    /**
     * Constante voor reden einde relatie nietigverklaring code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING);

    /**
     * Constante voor reden einde relatie omzetting soort verbintenis code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE_STRING);

    /**
     * Constante voor reden einde relatie omzetting soort verbintenis code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_ANDERE_VERBINTENIS_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_ANDERE_VERBINTENIS_CODE_STRING);

    /**
     * Constante voor reden einde relatie echtscheiding code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_ECHTSCHEIDING_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_ECHTSCHEIDING_CODE_STRING);

    /**
     * Constante voor reden einde relatie vreemd recht code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_VREEMD_RECHT_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_VREEMD_RECHT_CODE_STRING);

    /**
     * Constante voor reden einde relatie onbekend code.
     */
    public static final RedenEindeRelatieCodeAttribuut REDEN_EINDE_RELATIE_ONBEKEND_CODE = new RedenEindeRelatieCodeAttribuut(
        REDEN_EINDE_RELATIE_ONBEKEND_CODE_STRING);

    /**
     * Lege private constructor voor RedenEindeRelatieCodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private RedenEindeRelatieCodeAttribuut() {
        super();
    }

    /**
     * Constructor voor RedenEindeRelatieCodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public RedenEindeRelatieCodeAttribuut(final String waarde) {
        super(waarde);
    }

}
