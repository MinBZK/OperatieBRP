/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Container interface voor generieke interfaces ter ondersteuning van herbruikbare afnemerindicatie stappen.
 */
public interface GeneriekeOnderhoudAfnemerindicatieStappen {


    /**
     * Interface voor het plaatsen van de indicatie.
     */
    @FunctionalInterface
    interface PlaatsAfnemerindicatie {
        /**
         * @param support support
         * @throws StapException stap fout
         */
        void voerStapUit(PlaatsAfnemerindicatieParams support) throws StapException;
    }


    /**
     * Interface voor het verwijderen van de indicatie.
     */
    @FunctionalInterface
    interface VerwijderAfnemerindicatie {
        /**
         * @param params params
         * @throws StapException stap fout
         */
        void voerStapUit(VerwijderAfnemerindicatieParams params) throws StapException;
    }


    /**
     * Deze interface definieert hoe de input die nodig is om de validaties uit te voeren voordat het plaatsen afnemerindicate doorgang kan vinden.
     */
    final class ValideerPlaatsAfnemerindicatieParams {

        private final ToegangLeveringsAutorisatie toegangLeveringsautorisatie;
        private Persoonslijst persoonslijst;
        private final Integer datumAanvangMaterielePeriode;
        private final Integer datumEindeVolgen;

        /**
         * Constructor voor de input paramters.
         * @param toegangLeveringsautorisatie de autorisatie
         * @param persoonslijst de persoon voor wie de indicatie geplaatst gaat worden
         * @param datumAanvangMaterielePeriode de datum waarop de indicatie moet gaan gelden
         * @param datumEindeVolgen de datum waarop de indicatie verloopt
         */
        public ValideerPlaatsAfnemerindicatieParams(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Persoonslijst persoonslijst,
                                                    final Integer datumAanvangMaterielePeriode, final Integer datumEindeVolgen) {
            this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
            this.persoonslijst = persoonslijst;
            this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
            this.datumEindeVolgen = datumEindeVolgen;
        }

        public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
            return toegangLeveringsautorisatie;
        }

        public Persoonslijst getPersoonslijst() {
            return persoonslijst;
        }

        public Integer getDatumAanvangMaterielePeriode() {
            return datumAanvangMaterielePeriode;
        }

        public Integer getDatumEindeVolgen() {
            return datumEindeVolgen;
        }

        public void setPersoonslijst(final Persoonslijst persoonslijst) {
            this.persoonslijst = persoonslijst;
        }
    }

    /**
     * Interface voor het uitvoeren van de regels voorafgaand aan het plaatsen van de indicatie.
     */
    @FunctionalInterface
    interface ValideerRegelsPlaatsing {
        /**
         * @param support support
         * @throws StapMeldingException als een validatie stap faalt
         */
        void valideer(ValideerPlaatsAfnemerindicatieParams support) throws StapMeldingException;
    }

    /**
     * Interface voor het uitvoeren van de regels voorafgaand aan het verwijderen van de indicatie.
     */
    @FunctionalInterface
    interface ValideerRegelsVerwijderen {
        /**
         * @param toegangLeveringsAutorisatie de toegang leveringsautorisatie
         * @param persoonslijst de persoonslijst
         * @throws StapMeldingException als een validatie stap faalt
         */
        void valideer(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Persoonslijst persoonslijst) throws StapMeldingException;
    }
}
