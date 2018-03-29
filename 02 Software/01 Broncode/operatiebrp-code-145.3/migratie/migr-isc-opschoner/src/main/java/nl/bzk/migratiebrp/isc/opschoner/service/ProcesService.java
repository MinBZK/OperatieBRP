/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service;

import java.sql.Timestamp;
import java.util.List;
import nl.bzk.migratiebrp.isc.opschoner.exception.NietVerwijderbareProcesInstantieException;

/**
 * Interface voor de service op processen.
 */
public interface ProcesService {

    /**
     * Selecteert de id's van de op te schonen processen.
     * @param datumTot De datum tot wanneer op te schonen processen worden meegenomen.
     * @return Lijst van id's van de op te schonen processen.
     */
    List<Long> selecteerIdsVanOpTeSchonenProcessen(Timestamp datumTot);

    /**
     * Controleert of het meegegeven proces verwijderbaar is. Een proces is verwijderbaar indien alle sub- en
     * gerelateerde processen zijn beeindigd, de wachtperiode sinds het einde van het proces verlopen is en er nadien
     * geen nieuw bericht meer is binnengekomen gerelateerd aan dit proces of een van zijn sub- en gerelateerde
     * processen.
     * @param procesId Het id van het proces waarvoor we willen bepalen of deze verwijderbaar is.
     * @param verwerkteProcesIds Lijst met reeds verwerkte processen.
     * @param verwijderdeProcesIds Lijst met reeds verwijderde processen.
     * @throws NietVerwijderbareProcesInstantieException Exceptie die wordt gegooid wanneer het proces niet verwijderbaar is.
     */
    void controleerProcesVerwijderbaar(Long procesId, final List<Long> verwerkteProcesIds, final List<Long> verwijderdeProcesIds)
            throws NietVerwijderbareProcesInstantieException;

    /**
     * Verwijdert het proces en alle gerelateerde informatie uit de database.
     * @param procesId Het id van het proces dat we willen verwijderen.
     * @param verwerkteProcesIds Lijst met reeds verwerkte processen.
     * @param verwijderdeProcesIds Lijst met reeds verwijderde processen.
     */
    void verwijderProces(Long procesId, List<Long> verwerkteProcesIds, List<Long> verwijderdeProcesIds);

    /**
     * Update de verwachte verwijder datum voor een proces. Indien een proces niet verwijderbaar is, dan update deze
     * methode het proces zodat de verwachteVerwijderDatum gelijk is aan de datum van het laatste bericht/einde met
     * daarbij de wachtperiode opgeteld.
     * @param procesId Het proces dat geupdate dient te worden.
     * @param verwachteVerwijderDatum De te zetten verwachte verwijder datum.
     */
    void updateVerwachteVerwijderDatumProces(Long procesId, Timestamp verwachteVerwijderDatum);
}
