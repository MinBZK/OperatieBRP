/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVrucht;

/**
 * De relatie tussen twee ouders-in-spe waarmee zij aangeven welke naamskeuze zij wensen voor het kind/de kinderen
 * waarvan ��n van beide zwanger is.
 *
 * Reeds voor de geboorte kunnen de toekomstig ouders afspraken maken welke geslachtsnaam het kind/de kinderen gaan
 * krijgen. De ouder wiens geslachtsnaam zal overgaan op het kind, heeft hier de rol Naamgever; de andere ouder - die
 * hiermee instemt - heeft in deze relatie dan de betrokkenheid in de rol van Instemmer.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "6")
public class NaamskeuzeOngeborenVruchtModel extends AbstractNaamskeuzeOngeborenVruchtModel implements NaamskeuzeOngeborenVrucht {

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public NaamskeuzeOngeborenVruchtModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param naamskeuzeOngeborenVrucht Te kopieren object type.
     */
    public NaamskeuzeOngeborenVruchtModel(final NaamskeuzeOngeborenVrucht naamskeuzeOngeborenVrucht) {
        super(naamskeuzeOngeborenVrucht);
    }

}
