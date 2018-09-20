/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;

/**
 * Verzorgt het inlezen van de ondersteunde bestandsformaten.
 */
public interface LeesService {

    /**
     * Leest de Lo3Persoonslijst in uit een geupload bestand. Bestandstype is "Lg01 of AM" (Alternatieve Media; lo3.7
     * blz. 612) Volgens mij moet het inlezen van dit formaat nog vanaf 0 worden geschreven. Voor nu kunnen we ook het
     * test Excel formaat ondersteunen, dan hebben we alvast iets.
     * 
     * @param filename
     *            De naam van het bestand.
     * @param file
     *            De file zelf in een byte array.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De lijst met Lo3Persoonslijsten.
     */
    List<Lo3Persoonslijst> leesLo3Persoonslijst(String filename, byte[] file, FoutMelder foutMelder);
}
