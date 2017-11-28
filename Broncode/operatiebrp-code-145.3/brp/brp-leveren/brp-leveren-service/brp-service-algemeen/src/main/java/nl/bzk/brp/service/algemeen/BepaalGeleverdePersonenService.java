/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;

/**
 * Deze service bepaalt geleverde personen op basis van een persoonsgegevens lijst. Het {@link Resultaat} wordt verrijkt met
 * protocolleringsgegevens ({@link nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon} referenties). Als er een datumAanvangMaterielePeriode
 * wordt meegegeven, dan zal die geprotocollerd worden. Zo niet dan wordt deze uit de afnemerindicaties in de lijst van {@link Persoonslijst} gehaald
 * worden. Indien daar maar één unieke datum aanvang materiele periode resultaat uit gehaald kan worden, zal die toegevoegd worden. Als er meerdere
 * verschillende datum aanvang materiele periode waarden gevonden worden voor verschillende personen,
 * worden deze aan de {@link nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon} objecten toegevoegd.
 */
@Bedrijfsregel(Regel.R1617)
@Bedrijfsregel(Regel.R2236)
@FunctionalInterface
public interface BepaalGeleverdePersonenService {

    /**
     * Bepaal de geleverde personen.
     * @param soortDienst de {@link SoortDienst}
     * @param leveringsautorisatieId het leveringsautorisatie ID
     * @param persoonslijstList de lijst met {@link Persoonslijst}
     * @param datumAanvangMaterielePeriode de datum aanvang materiele periode (wordt bij plaatsen afnemerindicatie meegegeven)
     * @return Het resultaat van de geleverde personen bepaling
     */
    Resultaat bepaal(SoortDienst soortDienst, int leveringsautorisatieId, List<Persoonslijst> persoonslijstList,
                     final Integer datumAanvangMaterielePeriode);

    /**
     * Het resultaat van de geleverde personen bepaling.
     */
    final class Resultaat {

        private final Integer datumAanvangMaterielePersiodeResultaat;
        private final List<LeveringPersoon> leveringPersonen;
        private final List<Long> geleverdePersoonIds;

        /**
         * Maak een nieuwe Resultaat object.
         * @param datumAanvangMaterielePersiodeResultaat datumAanvangMaterielePersiodeResultaat
         * @param leveringPersonen leveringPersonen
         * @param geleverdePersoonIds geleverdePersoonIds
         */
        public Resultaat(final Integer datumAanvangMaterielePersiodeResultaat, final List<LeveringPersoon> leveringPersonen,
                         final List<Long> geleverdePersoonIds) {
            this.datumAanvangMaterielePersiodeResultaat = datumAanvangMaterielePersiodeResultaat;
            this.leveringPersonen = leveringPersonen;
            this.geleverdePersoonIds = geleverdePersoonIds;
        }

        public Integer getDatumAanvangMaterielePeriodeResultaat() {
            return datumAanvangMaterielePersiodeResultaat;
        }

        public List<LeveringPersoon> getLeveringPersonen() {
            return leveringPersonen;
        }

        public List<Long> getGeleverdePersoonIds() {
            return geleverdePersoonIds;
        }
    }
}
