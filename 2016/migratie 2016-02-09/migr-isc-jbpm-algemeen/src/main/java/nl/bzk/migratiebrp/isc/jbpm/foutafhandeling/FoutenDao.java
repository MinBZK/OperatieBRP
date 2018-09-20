/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

/**
 * Registreren van fouten.
 */
public interface FoutenDao {

    /**
     * Registreer een fout.
     * 
     * @param foutcode
     *            foutcode
     * @param foutmelding
     *            melding
     * @param proces
     *            proces
     * @param processId
     *            process instance id
     * @param bronGemeente
     *            bron gemeente
     * @param doelGemeente
     *            doel gemeente
     * @return id
     */
    long registreerFout(String foutcode, String foutmelding, String proces, long processId, String bronGemeente, String doelGemeente);

    /**
     * Voeg de beheerders actie toe aan een fout registratie.
     * 
     * @param id
     *            id
     * @param resolutie
     *            beheerders actie
     */
    void voegResolutieToe(long id, String resolutie);

    /**
     * Haalt op basis van het foutid de foutcode op die is opgeslagen bij een fout.
     * 
     * @param foutId
     *            Het id van de fout waarvoor de foutcode wordt opgehaald.
     * @return De foutcode behorend bij de fout waarvan het id is meegegeven.
     */
    String haalFoutcodeOp(long foutId);

}
