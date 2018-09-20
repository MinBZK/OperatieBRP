/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVruchtBasis;

/**
 * De relatie tussen twee ouders-in-spe waarmee zij aangeven welke naamskeuze zij wensen voor het kind/de kinderen
 * waarvan één van beide zwanger is.
 *
 * Reeds voor de geboorte kunnen de toekomstig ouders afspraken maken welke geslachtsnaam het kind/de kinderen gaan
 * krijgen. De ouder wiens geslachtsnaam zal overgaan op het kind, heeft hier de rol Naamgever; de andere ouder - die
 * hiermee instemt - heeft in deze relatie dan de betrokkenheid in de rol van Instemmer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractNaamskeuzeOngeborenVruchtBericht extends RelatieBericht implements BrpObject, NaamskeuzeOngeborenVruchtBasis {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractNaamskeuzeOngeborenVruchtBericht() {
        super(new SoortRelatieAttribuut(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT));
    }

}
