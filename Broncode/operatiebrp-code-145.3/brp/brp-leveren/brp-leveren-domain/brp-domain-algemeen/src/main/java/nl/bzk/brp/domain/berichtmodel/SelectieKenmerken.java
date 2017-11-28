/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;

/**
 * SelectieKenmerken.
 */
public final class SelectieKenmerken {
    private SoortSelectie soortSelectie;
    private String soortSelectieresultaatSet;
    private Integer soortSelectieresultaatVolgnummer;
    private Dienst dienst;
    private Leveringsautorisatie leveringsautorisatie;
    private Integer selectietaakId;
    private Integer datumUitvoer;
    private Integer peilmomentMaterieelResultaat;
    private Timestamp peilmomentFormeelResultaat;

    private SelectieKenmerken() {
    }

    /**
     * Maak de builder.
     * @return de builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public SoortSelectie getSoortSelectie() {
        return soortSelectie;
    }

    public String getSoortSelectieresultaatSet() {
        return soortSelectieresultaatSet;
    }

    public Integer getSoortSelectieresultaatVolgnummer() {
        return soortSelectieresultaatVolgnummer;
    }

    public Dienst getDienst() {
        return dienst;
    }

    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    public Integer getSelectietaakId() {
        return selectietaakId;
    }

    public Integer getDatumUitvoer() {
        return datumUitvoer;
    }

    public Integer getPeilmomentMaterieelResultaat() {
        return peilmomentMaterieelResultaat;
    }

    public Timestamp getPeilmomentFormeelResultaat() {
        return peilmomentFormeelResultaat;
    }

    /**
     * De builder voor SelectieKenmerken.
     */
    public static final class Builder {
        private SoortSelectie soortSelectie;
        private Dienst dienst;
        private Leveringsautorisatie leveringsautorisatie;
        private Integer selectietaakId;
        private Integer datumUitvoer;
        private Integer peilmomentMaterieelResultaat;
        private Timestamp peilmomentFormeelResultaat;
        private String soortSelectieresultaatSet;
        private Integer soortSelectieresultaatVolgnummer;

        private Builder() {
        }

        /**
         * @param soortSelectie soortSelectie
         * @return builder
         */
        public Builder metSoortSelectie(final SoortSelectie soortSelectie) {
            this.soortSelectie = soortSelectie;
            return this;
        }

        /**
         * @param dienst dienst
         * @return builder
         */
        public Builder metDienst(final Dienst dienst) {
            this.dienst = dienst;
            return this;
        }

        /**
         * @param leveringsautorisatie leveringsautorisatie
         * @return builder
         */
        public Builder metLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
            this.leveringsautorisatie = leveringsautorisatie;
            return this;
        }

        /**
         * @param selectietaakId selectietaakId
         * @return builder
         */
        public Builder metSelectietaakId(final Integer selectietaakId) {
            this.selectietaakId = selectietaakId;
            return this;
        }

        /**
         * @param datumUitvoer datumUitvoer
         * @return builder
         */
        public Builder metDatumUitvoer(final Integer datumUitvoer) {
            this.datumUitvoer = datumUitvoer;
            return this;
        }

        /**
         * @param peilmomentMaterieelResultaat peilmomentMaterieelResultaat
         * @return builder
         */
        public Builder metPeilmomentMaterieelResultaat(final Integer peilmomentMaterieelResultaat) {
            this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
            return this;
        }

        /**
         * @param peilmomentFormeelResultaat peilmomentFormeelResultaat
         * @return builder
         */
        public Builder metPeilmomentFormeelResultaat(final Timestamp peilmomentFormeelResultaat) {
            this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
            return this;
        }

        /**
         * @param soortSelectieresultaatSet soortSelectieresultaatSet
         * @return builder
         */
        public Builder metSoortSelectieresultaatSet(final String soortSelectieresultaatSet) {
            this.soortSelectieresultaatSet = soortSelectieresultaatSet;
            return this;
        }

        /**
         * @param soortSelectieresultaatVolgnummer soortSelectieresultaatVolgnummer
         * @return builder
         */
        public Builder metSoortSelectieresultaatVolgnummer(final Integer soortSelectieresultaatVolgnummer) {
            this.soortSelectieresultaatVolgnummer = soortSelectieresultaatVolgnummer;
            return this;
        }

        /**
         * Bouw de selectie kenmerken.
         * @return de selectie kenmerken
         */
        public SelectieKenmerken build() {
            final SelectieKenmerken selectieKenmerken = new SelectieKenmerken();
            selectieKenmerken.soortSelectie = this.soortSelectie;
            selectieKenmerken.soortSelectieresultaatSet = this.soortSelectieresultaatSet;
            selectieKenmerken.soortSelectieresultaatVolgnummer = this.soortSelectieresultaatVolgnummer;
            selectieKenmerken.dienst = this.dienst;
            selectieKenmerken.leveringsautorisatie = this.leveringsautorisatie;
            selectieKenmerken.selectietaakId = selectietaakId;
            selectieKenmerken.datumUitvoer = datumUitvoer;
            selectieKenmerken.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
            selectieKenmerken.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
            return selectieKenmerken;
        }


    }
}
