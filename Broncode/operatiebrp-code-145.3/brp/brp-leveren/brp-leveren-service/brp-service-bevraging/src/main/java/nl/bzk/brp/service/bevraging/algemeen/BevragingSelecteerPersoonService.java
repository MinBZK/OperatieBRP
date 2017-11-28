/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Service waarmee een {@link Persoonslijst} geselecteerd kan worden.
 */
@FunctionalInterface
public interface BevragingSelecteerPersoonService {

    /**
     * @param bsn bsn
     * @param anummer anummer
     * @param persoonId persoonId
     * @param partijCode partijCode
     * @param autorisatiebundel autorisatiebundel
     * @return een persoonslijst
     * @throws StapMeldingException fout bij selecteren persoon
     */
    Persoonslijst selecteerPersoon(String bsn, String anummer, String persoonId, String partijCode, Autorisatiebundel autorisatiebundel)
            throws StapMeldingException;
}
