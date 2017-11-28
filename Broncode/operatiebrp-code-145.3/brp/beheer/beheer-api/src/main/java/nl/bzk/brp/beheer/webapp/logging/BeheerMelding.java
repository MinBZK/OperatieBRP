/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.logging;

import nl.bzk.algemeenbrp.util.common.logging.FunctioneleMelding;

/**
 * Beheer meldingen.
 */
public enum BeheerMelding implements FunctioneleMelding {

    /**
     * Beheer: Verzoek ontvangen.
     */
    BEHEER_VERZOEK_ONTVANGEN("BEH001", "Verzoek ontvangen."),

    /**
     * Beheer: Verzoek verwerken.
     */
    BEHEER_VERZOEK_VERWERKEN("BEH002", "Verzoek wordt verwerkt."),

    /**
     * Beheer: Verzoek verwerkt.
     */
    BEHEER_VERZOEK_VERWERKT("BEH003", "Verzoek verwerkt."),

    /**
     * Beheer: Onverwachte fout.
     */
    BEHEER_ONVERWACHTE_FOUT("BEH004", "Onverwachte fout opgetreden bij verwerken verzoek.");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code
     *            De code
     * @param omschrijving
     *            De omschrijving
     */
    BeheerMelding(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }
}
