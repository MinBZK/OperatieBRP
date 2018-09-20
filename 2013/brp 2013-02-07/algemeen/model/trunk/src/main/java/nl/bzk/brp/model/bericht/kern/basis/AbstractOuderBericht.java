/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.OuderBasis;


/**
 * De betrokkenheid van een persoon in de rol van Ouder in een Familierechtelijke betrekking.
 *
 *
 *
 */
public abstract class AbstractOuderBericht extends BetrokkenheidBericht implements OuderBasis {

    private OuderOuderschapGroepBericht     ouderschap;
    private OuderOuderlijkGezagGroepBericht ouderlijkGezag;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractOuderBericht() {
        super(SoortBetrokkenheid.OUDER);
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
