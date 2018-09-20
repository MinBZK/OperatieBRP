/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Stamtabel service.
 */
public interface StamtabelRepository {

    /**
     * Geef een lijst van alle geldige partij codes.
     * 
     * @return lijst van alle geldige partij codes
     */
    Collection<BigDecimal> findAllPartijCodes();

    /**
     * Geef een lijst van alle geldige gemeente codes.
     * 
     * @return lijst van alle geldige gemeente codes
     */
    Collection<BigDecimal> findAllGemeenteCodes();

    /**
     * Geef een lijst van alle geldige land codes.
     * 
     * @return lijst van alle geldige land codes
     */
    Collection<BigDecimal> findAllLandCodes();

    /**
     * Geef een lijst van alle geldige nationaliteit codes.
     * 
     * @return lijst van alle geldige nationaliteit codes
     */
    Collection<BigDecimal> findAllNationaliteitCodes();

    /**
     * Geef een lijst van alle geldige plaatsnamen.
     * 
     * @return lijst van alle geldige plaatsnamen
     */
    Collection<String> findAllPlaatsnamen();

    /**
     * Geef een lijst van alle geldige namen openbare ruimte.
     * 
     * @return lijst van alle geldige namen openbare ruimte
     */
    Collection<String> findAllNamenOpenbareRuimte();

}
