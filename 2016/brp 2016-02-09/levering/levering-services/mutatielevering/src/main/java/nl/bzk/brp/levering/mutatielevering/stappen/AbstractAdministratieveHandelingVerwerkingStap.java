/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;

/**
 * Dit is de abstracte klasse voor de administratieve handeling verwerking stappen.
 */
public abstract class AbstractAdministratieveHandelingVerwerkingStap
        extends AbstractStap<AdministratieveHandelingMutatie, AdministratieveHandelingVerwerkingContext,
        AdministratieveHandelingVerwerkingResultaat> implements
        AdministratieveHandelingVerwerkingStap
{

}
