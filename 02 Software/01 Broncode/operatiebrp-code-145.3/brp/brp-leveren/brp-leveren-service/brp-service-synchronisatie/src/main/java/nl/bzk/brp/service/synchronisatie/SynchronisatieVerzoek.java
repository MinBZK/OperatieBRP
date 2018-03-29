/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie;

import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;

/**
 * SynchronisatieVerzoek.
 */
public class SynchronisatieVerzoek extends VerzoekBasis {
    private Parameters parameters;
    private ZoekCriteriaPersoon zoekCriteriaPersoon;

    /**
     * Geeft de parameters binnen een synchronisatieverzoek.
     * @return de parameters
     */
    public final Parameters getParameters() {
        if (parameters == null) {
            parameters = new Parameters();
        }
        return parameters;
    }

    /**
     * Geeft de criteria voor het zoeken naar een persoon binnen een synchronisatieverzoek.
     * @return de criteria voor het zoeken naar een persoon
     */
    public final ZoekCriteriaPersoon getZoekCriteriaPersoon() {
        if (zoekCriteriaPersoon == null) {
            zoekCriteriaPersoon = new ZoekCriteriaPersoon();
        }
        return zoekCriteriaPersoon;
    }

    /**
     * Parameters binnen een synchronisatieverzoek.
     */
    public static final class Parameters extends Verzoek.BerichtGegevens {
        private String leveringsAutorisatieId;
        private String stamgegeven;

        public String getLeveringsAutorisatieId() {
            return leveringsAutorisatieId;
        }

        public void setLeveringsAutorisatieId(final String leveringsAutorisatieId) {
            this.leveringsAutorisatieId = leveringsAutorisatieId;
        }

        public void setStamgegeven(final String stamgegeven) {
            this.stamgegeven = stamgegeven;
        }

        public String getStamgegeven() {
            return stamgegeven;
        }
    }

    /**
     * De criteria voor het zoeken naar een persoon.
     */
    public static final class ZoekCriteriaPersoon extends Verzoek.BerichtGegevens {
        private String bsn;

        public String getBsn() {
            return bsn;
        }

        public void setBsn(final String bsn) {
            this.bsn = bsn;
        }
    }
}
