/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.basis.BetrokkenheidBasis;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 *
 *
 *
 */
public abstract class AbstractBetrokkenheidBericht extends AbstractObjectTypeBericht implements BetrokkenheidBasis {

    private RelatieBericht     relatie;
    private SoortBetrokkenheid rol;
    private PersoonBericht     persoon;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param rol de waarde van het discriminator attribuut
     */
    public AbstractBetrokkenheidBericht(final SoortBetrokkenheid rol) {
        this.rol = rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieBericht getRelatie() {
        return relatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Zet Relatie van Betrokkenheid.
     *
     * @param relatie Relatie.
     */
    public void setRelatie(final RelatieBericht relatie) {
        this.relatie = relatie;
    }

    /**
     * Zet Persoon van Betrokkenheid.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

}
