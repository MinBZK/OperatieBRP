/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.logisch.basis;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.objecttype.logisch.Persoon;

/**
 * Interface object type Persoon Geslachtsnaam component.
 */
public interface PersoonGeslachtsnaamcomponentBasis extends ObjectType  {

    /**
     * Retourneert de Persoon waar het gelsachtsnaam toebehoort.
     * @return Geslachtsnaam
     */
    Persoon getPersoon();

    /**
     * Retourneert volgnummer van dit geslachtsnaam.
     * @return volgnummer geslachtsnaam.
     */
    Volgnummer getVolgnummer();

    /**
     * Retourneert standaard groep geslachtsnaam component.
     * @return Standaard groep geslachtsnaam component.
     */
    PersoonGeslachtsnaamcomponentStandaardGroep getGegevens();

}
