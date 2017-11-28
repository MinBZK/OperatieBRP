/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Interface voor het selecteren van personen.
 */
public interface SelecteerPersoonService {

    /**
     * Selecteert een persoon obv een bsn.
     * @param bsn het bsn
     * @param autorisatiebundel de autorisatiebundel
     * @return de persoon
     * @throws StapMeldingException StapMeldingException fout bij selecteren persoon
     */
    Persoonslijst selecteerPersoonMetBsn(String bsn, Autorisatiebundel autorisatiebundel) throws StapMeldingException;

    /**
     * Selecteert een persoon obv een asn.
     * @param anr anr
     * @param autorisatiebundel de autorisatiebundel
     * @return de persoon
     * @throws StapMeldingException StapMeldingException fout bij selecteren persoon
     */
    Persoonslijst selecteerPersoonMetANummer(String anr, Autorisatiebundel autorisatiebundel) throws StapMeldingException;

    /**
     * Selecteert een persoon obv een id.
     * @param id id
     * @param autorisatiebundel de autorisatiebundel
     * @return de persoon
     * @throws StapMeldingException StapMeldingException fout bij selecteren persoon
     */
    Persoonslijst selecteerPersoonMetId(Long id, Autorisatiebundel autorisatiebundel)
            throws StapMeldingException;
}
