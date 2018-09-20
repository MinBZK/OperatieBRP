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
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    String getBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt de persId op behorende by bsn
     * 
     * @param bsn
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    
    Long getPersIdByBsn(Long bsn);

    
    /**
     * Haalt een uniek adres op.
     *
     * @return uniek adres
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    Map<String, String> getAdres() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een vrijgezel bsn op.
     *
     * @return vrijgezel bsn
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    BsnPers getVrijgezelBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een nieuwe bsn op.
     *
     * @return nieuwe bsn
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    String getNieuweBsn() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een nieuw a nummer op.
     *
     * @return nieuw a nummer
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    String getNieuwANummer() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een huwelijk op.
     *
     * @return huwelijk
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    Map<String, String> getHuwelijk() throws SQLException, ClassNotFoundException;

    /**
     * Haalt een geregistreerd partnerschap op.
     *
     * @return geregistreerd partnerschap
     * @throws SQLException bij problemen met ophalen van gegevens uit de database.
     * @throws ClassNotFoundException bij problemen met omzetten van uit database afkomstige data naar juiste klasse.
     */
    Map<String, String> getGeregistreerdPartnerschap() throws SQLException, ClassNotFoundException;

    /**
     * Haal een lijst op met relatie id's en kind bsns voor adoptie.
     *
     * @return adoptie kind met relatie
     * @throws SQLException SQL exception
     * @throws ClassNotFoundException class not found exception
     */
    Map<String, String> getAdoptieKind() throws SQLException, ClassNotFoundException;


    /**
     * Haalt een bsn lijst grootte op.
     *
     * @return bsn lijst grootte
     */
    int getBsnLijstGrootte();
}
