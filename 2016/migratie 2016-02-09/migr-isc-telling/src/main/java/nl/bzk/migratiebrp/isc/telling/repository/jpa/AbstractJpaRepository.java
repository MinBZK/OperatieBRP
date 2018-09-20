/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

/**
 * Abstracte klasse voor gedeelde onderdelen.
 */
public abstract class AbstractJpaRepository {

    /**
     * Maximale grootte van batches voor 'IN'-queries.
     */
    protected static final Integer MAX_BATCH_SIZE = 1000;

}
