/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import nl.bzk.brp.service.algemeen.request.Verzoek;

/**
 * De DTO voor bevragingsverzoeken.
 */
public interface BevragingVerzoek extends Verzoek {

    /**
     * Geef de parameters van dit bevragingverzoek.
     * @return de parameters
     */
    Parameters getParameters();

    /**
     * De parameters binnen een bevragingsverzoek.
     */
    class Parameters extends Verzoek.BerichtGegevens {
        private String rolNaam;
        private String leveringsAutorisatieId;
        private String dienstIdentificatie;

        public final String getRolNaam() {
            return rolNaam;
        }

        public final void setRolNaam(final String rolNaam) {
            this.rolNaam = rolNaam;
        }

        public final String getLeveringsAutorisatieId() {
            return leveringsAutorisatieId;
        }

        public final void setLeveringsAutorisatieId(final String leveringsAutorisatieId) {
            this.leveringsAutorisatieId = leveringsAutorisatieId;
        }

        public final String getDienstIdentificatie() {
            return dienstIdentificatie;
        }

        public final void setDienstIdentificatie(final String dienstIdentificatie) {
            this.dienstIdentificatie = dienstIdentificatie;
        }
    }
}
