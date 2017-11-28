/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * De bijgehouden persoon zoals deze is gemaakt van een Berichtgegevens object wat gemaakt is door de maak bericht module.
 */
public final class BijgehoudenPersoon {

    private BerichtElement berichtElement;
    private Persoonslijst persoonslijst;
    private Integer communicatieId;
    private boolean volledigBericht;


    public BerichtElement getBerichtElement() {
        return berichtElement;
    }

    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    public Integer getCommunicatieId() {
        return communicatieId;
    }

    public boolean isVolledigBericht() {
        return volledigBericht;
    }

    /**
     * De bijgehouden persoon builder.
     */
    public static final class Builder {

        private final Persoonslijst persoonslijst;
        private final BerichtElement berichtElement;
        private Integer communicatieId;
        private boolean volledigBericht;

        /**
         * De bijgehouden persoon builder.
         *
         * @param persoonslijst  de leverpersoon
         * @param berichtElement berichtElement
         */
        public Builder(final Persoonslijst persoonslijst, final BerichtElement berichtElement) {
            this.persoonslijst = persoonslijst;
            this.berichtElement = berichtElement;
        }

        /**
         * Voeg het referentienummer toe aan de builder.
         *
         * @param communicatieIdBuild de communicatieId
         * @return de builder
         */
        public Builder metCommunicatieId(final Integer communicatieIdBuild) {
            this.communicatieId = communicatieIdBuild;
            return this;
        }

        /**
         * Voeg het referentienummer toe aan de builder.
         *
         * @param volledigBerichtBuild de soortSynchronisatie
         * @return de builder
         */
        public Builder metVolledigBericht(final boolean volledigBerichtBuild) {
            this.volledigBericht = volledigBerichtBuild;
            return this;
        }

        /**
         * Bouw de bijgehouden persoon.
         *
         * @return de bijgehouden persoon
         */
        public BijgehoudenPersoon build() {
            final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon();
            bijgehoudenPersoon.persoonslijst = persoonslijst;
            bijgehoudenPersoon.communicatieId = communicatieId;
            bijgehoudenPersoon.berichtElement = berichtElement;
            bijgehoudenPersoon.volledigBericht = volledigBericht;
            return bijgehoudenPersoon;
        }
    }
}
