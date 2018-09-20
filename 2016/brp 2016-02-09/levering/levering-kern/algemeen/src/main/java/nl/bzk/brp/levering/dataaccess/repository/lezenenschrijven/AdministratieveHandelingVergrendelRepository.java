/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven;

/**
 * Deze interface verzorgt het locken van de administratieve handelingen.
 */
public interface AdministratieveHandelingVergrendelRepository {

    /**
     * Deze methode probeert een (pessimistic write) lock te leggen op een administratieve handeling, controleert of
     * de handeling niet al verwerkt is en als aan deze condities wordt voldaan wordt een boolean true ge-returned.
     *
     * @param administratieveHandelingId De administratieve handeling id om te locken.
     * @return boolean true als de administratieve handeling nog niet gelockt is én nog niet verwerkt is.
     */
    boolean vergrendelAlsNogNietIsVerwerkt(Long administratieveHandelingId);

}
