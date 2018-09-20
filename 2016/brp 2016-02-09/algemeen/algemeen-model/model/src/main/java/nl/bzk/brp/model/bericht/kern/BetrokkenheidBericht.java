/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.validation.Valid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 * <p/>
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is anderzijds. De koppeling van een
 * Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 */
public abstract class BetrokkenheidBericht extends AbstractBetrokkenheidBericht implements Betrokkenheid {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param rol de waarde van het discriminator attribuut
     */
    public BetrokkenheidBericht(final SoortBetrokkenheidAttribuut rol) {
        super(rol);
    }

    @Valid
    @Override
    public PersoonBericht getPersoon() {
        return super.getPersoon();
    }

}
