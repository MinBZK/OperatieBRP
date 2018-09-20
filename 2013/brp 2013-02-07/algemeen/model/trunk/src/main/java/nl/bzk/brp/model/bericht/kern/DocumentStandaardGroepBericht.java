/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractDocumentStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;


/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt. RvdP 17 jan 2012.
 *
 *
 *
 */
public class DocumentStandaardGroepBericht extends AbstractDocumentStandaardGroepBericht implements
        DocumentStandaardGroep
{

}
