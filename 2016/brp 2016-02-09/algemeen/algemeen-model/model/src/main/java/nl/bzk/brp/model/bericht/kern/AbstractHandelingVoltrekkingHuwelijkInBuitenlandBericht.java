/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;

/**
 * Met deze OT:"Administratieve handeling" wordt een in het buitenland voltrokken OT:Huwelijk geregistreerd, eventueel
 * met wijziging van de G:Persoon.Naamgebruik en/of G:"Persoon \ Geslachtsnaamcomponent.Identiteit". OT:"Naamskeuze
 * ongeboren vrucht" bij gelegenheid van het OT:Huwelijk, wordt middels een afzonderlijke bijhouding verwerkt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHandelingVoltrekkingHuwelijkInBuitenlandBericht extends AdministratieveHandelingBericht {

    /**
     * Default constructor instantieert met de juiste SoortAdministratieveHandeling.
     *
     */
    public AbstractHandelingVoltrekkingHuwelijkInBuitenlandBericht() {
        super(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND));
    }

}
