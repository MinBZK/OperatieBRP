/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.validation.Valid;

import nl.bzk.brp.model.bericht.kern.basis.AbstractBetrokkenheidBericht;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.3.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-10 16:16:22.
 * Gegenereerd op: Mon Dec 10 16:17:11 CET 2012.
 */
public abstract class BetrokkenheidBericht extends AbstractBetrokkenheidBericht implements Betrokkenheid {

    @Valid
    @Override
    public PersoonBericht getPersoon() {
        return super.getPersoon();
    }

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param rol de waarde van het discriminator attribuut
     */
    public BetrokkenheidBericht(final SoortBetrokkenheid rol) {
        super(rol);
    }

}
