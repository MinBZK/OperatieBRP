/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonIndicatieBasis;


/**
 * Indicaties bij een persoon.
 *
 *
 *
 */
public abstract class AbstractPersoonIndicatieBericht extends AbstractObjectTypeBericht implements
        PersoonIndicatieBasis
{

    private PersoonBericht                        persoon;
    private SoortIndicatie                        soort;
    private PersoonIndicatieStandaardGroepBericht standaard;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractPersoonIndicatieBericht(final SoortIndicatie soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortIndicatie getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Persoon van Persoon \ Indicatie.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Standaard van Persoon \ Indicatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonIndicatieStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

}
