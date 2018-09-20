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
 * Met deze OT:"Administratieve handeling" wordt de geboorte van een OT:Kind geregistreerd op basis van een Nederlandse
 * SD:Geboorteakte. Het OT:Kind wordt als Ingezetene ingeschreven op het OT:"Persoon \ Adres" van de (verplichte)
 * adresgevende OT:Ouder. De (optionele) niet-adresgevende OT:Ouder van het OT:Kind moet voldoen aan de zogenaamde
 * R:R1281.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHandelingGeboorteInNederlandBericht extends AdministratieveHandelingBericht {

    /**
     * Default constructor instantieert met de juiste SoortAdministratieveHandeling.
     *
     */
    public AbstractHandelingGeboorteInNederlandBericht() {
        super(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND));
    }

}
