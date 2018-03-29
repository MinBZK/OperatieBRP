/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;

/**
 * De DTO voor afnemerindicatieverzoeken.
 */
public class AfnemerindicatieVerzoek extends VerzoekBasis {
    private Afnemerindicatie afnemerindicatie;
    private String dummyAfnemerCode;
    private Parameters parameters;

    /**
     * Geef de parameters binnen een afnemerindicatieverzoek.
     * @return de parameters
     */
    public final Parameters getParameters() {
        if (parameters == null) {
            parameters = new Parameters();
        }
        return parameters;
    }

    public final Afnemerindicatie getAfnemerindicatie() {
        return afnemerindicatie;
    }

    public final void setAfnemerindicatie(final Afnemerindicatie afnemerindicatie) {
        this.afnemerindicatie = afnemerindicatie;
    }

    public final String getDummyAfnemerCode() {
        return dummyAfnemerCode;
    }

    public final void setDummyAfnemerCode(final String dummyAfnemerCode) {
        this.dummyAfnemerCode = dummyAfnemerCode;
    }

    /**
     * Parameters.
     */
    public static final class Parameters extends Verzoek.BerichtGegevens {

        private String leveringsAutorisatieId;

        public String getLeveringsAutorisatieId() {
            return leveringsAutorisatieId;
        }

        public void setLeveringsAutorisatieId(final String leveringsAutorisatieId) {
            this.leveringsAutorisatieId = leveringsAutorisatieId;
        }
    }
}
