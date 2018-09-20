/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Interface voor de BRP Bedrijfsregelmanager. Deze manager biedt methodes voor het ophalen van de regels specifiek naar
 * situatie.
 */
public interface BedrijfsregelManager {

    /**
     * Haal de configureerbare parameters op die bij een bedrijfsregel horen. Dit zijn:
     * - Melding tekst
     * - Meldingsniveau; fout, deblokkeerbaar, waarschuwing etc.
     * - RegelCode
     *
     * @param bedrijfsregel de bedrijfsregel
     * @return regel parameters zoals hierboven beschreven
     */
    RegelParameters getRegelParametersVoorRegel(final RegelInterface bedrijfsregel);

    /**
     * Haal de configureerbare parameters op die bij een regel horen. Dit zijn:
     * - Melding tekst
     * - Meldingsniveau; fout, deblokkeerbaar, waarschuwing etc.
     * - RegelCode
     *
     * @param regel de regel
     * @return regel parameters zoals hierboven beschreven
     */
    RegelParameters getRegelParametersVoorRegel(final Regel regel);

}
