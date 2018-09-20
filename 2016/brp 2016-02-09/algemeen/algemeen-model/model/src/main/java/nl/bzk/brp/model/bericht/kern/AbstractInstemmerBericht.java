/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.InstemmerBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Instemmer_" in een OT:"Erkenning ongeboren vrucht" of in een
 * OT:"Naamskeuze ongeboren vrucht".
 *
 * Zowel bij een erkenning ongeboren vrucht als bij een naamskeuze ongeboren vrucht is er naast de betrokkenheid van een
 * persoon als erkenner of naamgever ook een (toekomstige) ouder die hiermee instemt: in dat geval is er sprake van een
 * betrokkenheid in de rol van instemmer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractInstemmerBericht extends BetrokkenheidBericht implements BrpObject, InstemmerBasis {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractInstemmerBericht() {
        super(new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.INSTEMMER));
    }

}
