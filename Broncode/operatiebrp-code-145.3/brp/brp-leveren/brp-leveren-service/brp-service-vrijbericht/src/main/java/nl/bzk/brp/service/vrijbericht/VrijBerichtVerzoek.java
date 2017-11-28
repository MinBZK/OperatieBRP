/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;

/**
 * Het vrij bericht verzoek.
 */
public final class VrijBerichtVerzoek extends VerzoekBasis {

    private VrijBerichtBericht vrijBericht;

    private Parameters parameters = new Parameters();

    public Parameters getParameters() {
        return parameters;
    }

    public VrijBerichtBericht getVrijBericht() {
        return vrijBericht;
    }

    public void setVrijBericht(final VrijBerichtBericht vrijBericht) {
        this.vrijBericht = vrijBericht;
    }

    /**
     * Parameters binnen een Vrij Bericht verzoek.
     */
    public static class Parameters extends Verzoek.BerichtGegevens {
        private String zenderVrijBericht;
        private String ontvangerVrijBericht;

        public String getZenderVrijBericht() {
            return zenderVrijBericht;
        }

        public void setZenderVrijBericht(final String zenderVrijBericht) {
            this.zenderVrijBericht = zenderVrijBericht;
        }

        public String getOntvangerVrijBericht() {
            return ontvangerVrijBericht;
        }

        public void setOntvangerVrijBericht(final String ontvangerVrijBericht) {
            this.ontvangerVrijBericht = ontvangerVrijBericht;
        }
    }
}
