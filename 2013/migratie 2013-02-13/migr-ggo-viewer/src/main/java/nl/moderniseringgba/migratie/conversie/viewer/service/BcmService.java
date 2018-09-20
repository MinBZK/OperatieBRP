/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;

/**
 * Zorgt voor koppeling met BCM.
 */
public interface BcmService {

    /**
     * Controleer de Lo3Persoonslijst door de BCM.
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoonslijst
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return bcmSignaleringen List<FoutRegel>
     */
    List<FoutRegel> controleerDoorBCM(Lo3Persoonslijst lo3Persoonslijst, FoutMelder foutMelder);

}
