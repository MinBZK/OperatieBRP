/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface klasse voor Mig DAO.
 */
public interface MigDao {

    /**
     * Bepaalt op basis van het id en de einddatum van het proces de datum van het laatste bericht dat is binnengekomen
     * voor het meegegeven proces.
     * @param procesId Het id van het proces dat is beeindigd.
     * @return Het aantal berichten dat is binnengekomen na deze datum.
     */
    Timestamp bepaalDatumLaatsteBerichtOntvangenVoorProces(Long procesId);

    /**
     * Haalt op basis van het id van het proces de gerelateerde processen op.
     * @param procesId Het proces id.
     * @return Lijst met gerelateerdeProcessen.
     */
    List<Long> selecteerGerelateerdeProcessenVoorProces(Long procesId);

    /**
     * Verwijdert alle berichten die behoren bij het proces met het meegegeven id.
     * @param procesId Het id van het proces.
     */
    void verwijderBerichtenVanProces(Long procesId);

    /**
     * Verwijdert de verwijziging tussen de twee meegegeven processen.
     * @param procesId Het 'hoofd' proces id.
     * @param gerelateerdProcesId Het 'gerelateerde' proces id.
     */
    void verwijderGerelateerdProcesVerwijzingVoorProces(Long procesId, Long gerelateerdProcesId);

}
