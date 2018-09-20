/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface klasse voor Extractie DAO.
 */
public interface ExtractieDao {

    /**
     * Haalt de id's op van processen op basis van proces extracties waarvoor geldt: indicatiebeeindigdgeteld = true en
     * einddatum is voor de datumTot. LET OP: deze methode geeft slechts de eerste 100 resultaten terug!
     * 
     * @param datumTot
     *            De datum tot wanneer proces extracties worden meegenomen.
     * @return Lijst met de id's van beeindigde processen.
     */
    List<Long> haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(Timestamp datumTot);

    /**
     * Past de verwachte verwijderdatum aan voor een proces.
     * 
     * @param procesId
     *            Het id van het proces dat aangepast wordt.
     * @param verwachteVerwijderDatum
     *            De verwachte verwijder datum; het proces wordt niet eerder meegenomen voor het opschonen.
     */
    void updateVerwachteVerwijderDatum(Long procesId, Timestamp verwachteVerwijderDatum);

}
