/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.locking.AbstractBsnLockStap;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingStap;

/**
 * Dit is de lock stap voor de administratieve handeling verwerking. Deze zorgt ervoor dat de betrokken personen
 * gelockt worden.
 */
public class BetrokkenBsnLockStap extends
        AbstractBsnLockStap<AdministratieveHandelingMutatie, AdministratieveHandelingVerwerkingContext,
                AdministratieveHandelingVerwerkingResultaat>
        implements AdministratieveHandelingVerwerkingStap
{

}
