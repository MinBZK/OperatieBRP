/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Onderzoek;


/**
 * Onderzoek naar gegevens in de BRP.
 * <p/>
 * Normaliter is er geen reden om te twijfelen aan de in de BRP geregistreerde gegevens. Soms echter is dat wel aan de orde. Vanuit verschillende hoeken
 * kan een signaal komen dat bepaalde gegevens niet correct zijn. Dit kan om zowel actuele gegevens als om (materieel) historische gegevens gaan. Met het
 * objecttype Onderzoek wordt vastgelegd dat gegevens in onderzoek zijn, en welke gegevens het betreft.
 * <p/>
 * Nog onderzoeken: Relatie met TMV/TMF.
 */
public final class OnderzoekBericht extends AbstractOnderzoekBericht implements BrpObject, BerichtEntiteit,
    MetaIdentificeerbaar, Onderzoek
{

}
