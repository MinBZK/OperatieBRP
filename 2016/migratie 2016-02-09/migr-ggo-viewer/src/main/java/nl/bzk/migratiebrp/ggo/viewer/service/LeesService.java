/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;

/**
 * Verzorgt het inlezen van de ondersteunde bestandsformaten.
 */
public interface LeesService {

    /**
     * Leest een Lo3Persoonslijst (of meerdere) in uit een geupload bestand. Bestandstype is Excel (mGBA Test format),
     * Lg01 of AM (Alternatieve Media; lo3.7 blz. 612).
     * 
     * @param filename
     *            De naam van het bestand.
     * @param file
     *            De file zelf in een byte array.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De lijst met Lo3Persoonslijsten in de vorm van List<Lo3CategorieWaarde>.
     */
    List<List<Lo3CategorieWaarde>> leesBestand(final String filename, final byte[] file, final FoutMelder foutMelder);

    /**
     * Parse een lijst van Lo3CategorieWaarde naar een Lo3Persoonslijst en voert direct syntax controle uit. Logt
     * eventuele fouten in de meegegeven foutMelder en retourneert de Lo3Persoonslijst mits valide.
     * 
     * @param lo3CategorieWaarden
     *            List<Lo3CategorieWaarde>
     * @param foutMelder
     *            FoutMelder
     * @return de Lo3Persoonslijst
     */
    Lo3Persoonslijst parsePersoonslijstMetSyntaxControle(final List<Lo3CategorieWaarde> lo3CategorieWaarden, final FoutMelder foutMelder);
}
