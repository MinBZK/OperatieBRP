/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.testdata;

import java.sql.SQLException;
import java.util.Map;


/**
 * De Interface TestdataService.
 */
public interface TestdataService {

    /**
     * Haalt een unieke bsn op.
     *
     * @return unieke bsn
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    String getBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een uniek adres op.
     *
     * @return uniek adres
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Map<String, String> getAdres() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een vrijgezel bsn op.
     *
     * @return vrijgezel bsn
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    String getVrijgezelBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een nieuwe bsn op.
     *
     * @return nieuwe bsn
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    String getNieuweBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een nieuw a nummer op.
     *
     * @return nieuw a nummer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    String getNieuwANummer() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een huwelijk op.
     *
     * @return huwelijk
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Map<String, String> getHuwelijk() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een bsn list size op.
     *
     * @return bsn list size
     */
    int getBsnListSize();
}
