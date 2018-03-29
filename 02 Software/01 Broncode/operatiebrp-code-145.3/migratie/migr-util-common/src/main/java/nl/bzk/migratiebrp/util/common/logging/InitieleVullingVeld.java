/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

/**
 * MDC velden voor .
 */
public enum InitieleVullingVeld implements nl.bzk.algemeenbrp.util.common.logging.MDCVeld{

    /** Veld waarin het administratienummer wordt gecommuniceerd t.b.v. MDC logging. */
    MDC_ADMINISTRATIE_NUMMER ("administratienummer"),

    /** Veld waarin de afnemersindicatie wordt gecommuniceerd t.b.v. MDC logging. */
    MDC_AFNEMERSINDICATIE ("afnemersindicatie");

    private final String veld;


    InitieleVullingVeld(final String veld) {
        this.veld = veld;
    }

    @Override
    public String getVeld() {
        return veld;
    }
}
