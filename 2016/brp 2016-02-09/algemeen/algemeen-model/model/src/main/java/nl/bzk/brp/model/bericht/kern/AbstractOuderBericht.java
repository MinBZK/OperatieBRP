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
import nl.bzk.brp.model.logisch.kern.OuderBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Ouder_" in een OT:"Familierechtelijke Betrekking".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractOuderBericht extends BetrokkenheidBericht implements BrpObject, OuderBasis {

    private OuderOuderschapGroepBericht ouderschap;
    private OuderOuderlijkGezagGroepBericht ouderlijkGezag;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractOuderBericht() {
        super(new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.OUDER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderschapGroepBericht getOuderschap() {
        return ouderschap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderlijkGezagGroepBericht getOuderlijkGezag() {
        return ouderlijkGezag;
    }

    /**
     * Zet Ouderschap van Ouder.
     *
     * @param ouderschap Ouderschap.
     */
    public void setOuderschap(final OuderOuderschapGroepBericht ouderschap) {
        this.ouderschap = ouderschap;
    }

    /**
     * Zet Ouderlijk gezag van Ouder.
     *
     * @param ouderlijkGezag Ouderlijk gezag.
     */
    public void setOuderlijkGezag(final OuderOuderlijkGezagGroepBericht ouderlijkGezag) {
        this.ouderlijkGezag = ouderlijkGezag;
    }

}
