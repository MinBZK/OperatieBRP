/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.logisch.kern.basis.InstemmerBasis;


/**
 * De betrokkenheid van de rol instemmer in een erkenning ongeboren vrucht of in een naamskeuze ongeboren vrucht.
 *
 * Bij de relatiesoorten Erkenning ongeboren vrucht en Naamskeuze ongeboren vrucht is er enerzijds de betrokkenheid in
 * de rol van Erkenner (in geval van Erkenning) of de Naamgever (in geval van Naamskeuze), anderzijds is er de
 * betrokkenheid van de andere (toekomstig) ouder, die hetzij met de erkenning, hetzij met de naamskeuze instemt.
 *
 *
 *
 */
public abstract class AbstractInstemmerBericht extends BetrokkenheidBericht implements InstemmerBasis {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractInstemmerBericht() {
        super(SoortBetrokkenheid.INSTEMMER);
    }

}
