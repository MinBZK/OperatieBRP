/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;

/**
 * Het stuf bericht verzoek.
 */
public final class StufBerichtVerzoek extends VerzoekBasis {

    private StufBericht stufBericht;

    private Parameters parameters = new Parameters();

    /**
     * Gets parameters.
     * @return the parameters
     */
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Gets stuf bericht.
     * @return the stuf bericht
     */
    public StufBericht getStufBericht() {
        return stufBericht;
    }

    /**
     * Sets stuf bericht.
     * @param stufBericht the stuf bericht
     */
    public void setStufBericht(final StufBericht stufBericht) {
        this.stufBericht = stufBericht;
    }

    /**
     * Parameters binnen een stuf Bericht verzoek.
     */
    public static class Parameters extends Verzoek.BerichtGegevens {
        private String vertalingBerichtsoortBRP;
        private String versieStufbg;
        private String leveringsAutorisatieId;

        /**
         * Gets vertaling berichtsoort brp.
         * @return the vertaling berichtsoort brp
         */
        public String getVertalingBerichtsoortBRP() {
            return vertalingBerichtsoortBRP;
        }

        /**
         * Sets vertaling berichtsoort brp.
         * @param vertalingBerichtsoortBRP the vertaling berichtsoort brp
         */
        public void setVertalingBerichtsoortBRP(String vertalingBerichtsoortBRP) {
            this.vertalingBerichtsoortBRP = vertalingBerichtsoortBRP;
        }

        /**
         * Gets versie stufbg.
         * @return the versie stufbg
         */
        public String getVersieStufbg() {
            return versieStufbg;
        }

        /**
         * Sets versie stufbg.
         * @param versieStufbg the versie stufbg
         */
        public void setVersieStufbg(String versieStufbg) {
            this.versieStufbg = versieStufbg;
        }

        /**
         * Gets leverings autorisatie id.
         * @return the leverings autorisatie id
         */
        public String getLeveringsAutorisatieId() {
            return leveringsAutorisatieId;
        }

        /**
         * Sets leverings autorisatie id.
         * @param leveringsAutorisatieId the leverings autorisatie id
         */
        public void setLeveringsAutorisatieId(String leveringsAutorisatieId) {
            this.leveringsAutorisatieId = leveringsAutorisatieId;
        }
    }
}
