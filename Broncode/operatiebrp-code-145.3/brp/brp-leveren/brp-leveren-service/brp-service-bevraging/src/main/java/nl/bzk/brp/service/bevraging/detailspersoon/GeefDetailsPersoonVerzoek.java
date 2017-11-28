/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * Het {@link BevragingVerzoek} specifiek voor Geef Details Persoon.
 */
public final class GeefDetailsPersoonVerzoek extends VerzoekBasis implements BevragingVerzoek {

    private GeefDetailsPersoonParameters parameters;
    private ScopingElementen scopingElementen;
    private Identificatiecriteria identificatiecriteria;

    /**
     * Geeft de parameters binnen een bevragingsverzoek.
     * @return de parameters
     */
    @Override
    public GeefDetailsPersoonParameters getParameters() {
        if (parameters == null) {
            parameters = new GeefDetailsPersoonParameters();
        }
        return parameters;
    }

    /**
     * @return de scoping elementen
     */
    public ScopingElementen getScopingElementen() {
        if (scopingElementen == null) {
            scopingElementen = new ScopingElementen();
        }
        return scopingElementen;
    }

    /**
     * @return de identificatiecriteria
     */
    public Identificatiecriteria getIdentificatiecriteria() {
        if (identificatiecriteria == null) {
            identificatiecriteria = new Identificatiecriteria();
        }
        return identificatiecriteria;
    }

    /**
     * GeefDetailsPersoonParameters.
     */
    public static final class GeefDetailsPersoonParameters extends Parameters {

        private HistorieFilterParameters historieFilterParameters;

        /**
         * @param peilMomentFormeelResultaat peilMomentFormeelResultaat
         */
        public void setPeilMomentFormeelResultaat(final String peilMomentFormeelResultaat) {
            maakHistorieFilterParameters();
            historieFilterParameters.peilMomentFormeelResultaat = peilMomentFormeelResultaat;
        }

        /**
         * @param peilMomentMaterieelResultaat peilMomentMaterieelResultaat
         */
        public void setPeilMomentMaterieelResultaat(final String peilMomentMaterieelResultaat) {
            maakHistorieFilterParameters();
            historieFilterParameters.peilMomentMaterieelResultaat = peilMomentMaterieelResultaat;
        }

        /**
         * @param historieVorm historieVorm
         */
        public void setHistorieVorm(final HistorieVorm historieVorm) {
            maakHistorieFilterParameters();
            historieFilterParameters.historieVorm = historieVorm;
        }

        /**
         * @param verantwoording verantwoording
         */
        public void setVerantwoording(final String verantwoording) {
            maakHistorieFilterParameters();
            historieFilterParameters.verantwoording = verantwoording;
        }

        public HistorieFilterParameters getHistorieFilterParameters() {
            return historieFilterParameters;
        }

        private void maakHistorieFilterParameters() {
            if (historieFilterParameters == null) {
                historieFilterParameters = new HistorieFilterParameters();
            }
        }
    }

    /**
     * HistorieFilterParameters.
     */
    public static final class HistorieFilterParameters {
        private String peilMomentFormeelResultaat;
        private String peilMomentMaterieelResultaat;
        private HistorieVorm historieVorm;
        private String verantwoording;

        public HistorieVorm getHistorieVorm() {
            return historieVorm;
        }

        public String getVerantwoording() {
            return verantwoording;
        }

        public String getPeilMomentFormeelResultaat() {
            return peilMomentFormeelResultaat;
        }

        public String getPeilMomentMaterieelResultaat() {
            return peilMomentMaterieelResultaat;
        }
    }

    /**
     * Identificatiecriteria.
     */
    public static final class Identificatiecriteria extends BerichtGegevens {
        private String bsn;
        private String anr;
        private String objectSleutel;

        public String getAnr() {
            return anr;
        }

        public void setAnr(final String anr) {
            this.anr = anr;
        }

        public String getObjectSleutel() {
            return objectSleutel;
        }

        public void setObjectSleutel(final String objectSleutel) {
            this.objectSleutel = objectSleutel;
        }

        public String getBsn() {
            return bsn;
        }

        public void setBsn(final String bsn) {
            this.bsn = bsn;
        }
    }

    /**
     * ScopingElementen.
     */
    public static final class ScopingElementen extends BerichtGegevens {
        private Set<String> elementen = new HashSet<>();

        public Set<String> getElementen() {
            return elementen;
        }
    }
}
