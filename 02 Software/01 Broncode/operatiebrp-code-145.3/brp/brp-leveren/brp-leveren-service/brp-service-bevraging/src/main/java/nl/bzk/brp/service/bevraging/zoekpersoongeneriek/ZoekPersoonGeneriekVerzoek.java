/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * ZoekPersoonVerzoek.
 */
public interface ZoekPersoonGeneriekVerzoek extends BevragingVerzoek {
    /**
     * @return zoekcriteria
     */
    Set<ZoekCriteria> getZoekCriteriaPersoon();

    /**
     * Geef de parameters van dit bevragingverzoek.
     * @return de parameters
     */
    @Override
    ZoekPersoonParameters getParameters();

    /**
     * ZoekPersoonParameters.
     */
    final class ZoekPersoonParameters extends Parameters {
        private ZoekBereikParameters zoekBereikParameters = new ZoekBereikParameters();

        public ZoekBereikParameters getZoekBereikParameters() {
            return zoekBereikParameters;
        }

        /**
         * @param zoekBereik zoekBereik
         */
        public void setZoekBereik(final Zoekbereik zoekBereik) {
            zoekBereikParameters.zoekBereik = zoekBereik;
        }

        /**
         * @param peilmomentMaterieel peilmomentMaterieel
         */
        public void setPeilmomentMaterieel(final String peilmomentMaterieel) {
            zoekBereikParameters.peilmomentMaterieel = peilmomentMaterieel;
        }
    }

    /**
     * ZoekBereikParameters.
     */
    final class ZoekBereikParameters {
        private Zoekbereik zoekBereik;
        private String peilmomentMaterieel;

        public Zoekbereik getZoekBereik() {
            return zoekBereik;
        }

        public String getPeilmomentMaterieel() {
            return peilmomentMaterieel;
        }
    }

    /**
     * De criteria voor het zoeken naar een persoon binnen een bevragingsverzoek.
     */
    final class ZoekCriteria extends Verzoek.BerichtGegevens {
        private String elementNaam;
        private String waarde;
        private Zoekoptie zoekOptie;
        private ZoekCriteria of;

        public String getElementNaam() {
            return elementNaam;
        }

        public void setElementNaam(final String elementNaam) {
            this.elementNaam = elementNaam;
        }

        public String getWaarde() {
            return waarde;
        }

        public void setWaarde(final String waarde) {
            this.waarde = waarde;
        }

        public Zoekoptie getZoekOptie() {
            return zoekOptie;
        }

        public void setZoekOptie(final Zoekoptie zoekOptie) {
            this.zoekOptie = zoekOptie;
        }

        public ZoekCriteria getOf() {
            return of;
        }

        public void setOf(final ZoekCriteria of) {
            this.of = of;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final ZoekCriteria that = (ZoekCriteria) o;

            return !(elementNaam != null ? !elementNaam.equals(that.elementNaam) : that.elementNaam != null);
        }

        @Override
        public int hashCode() {
            return elementNaam != null ? elementNaam.hashCode() : 0;
        }
    }
}
