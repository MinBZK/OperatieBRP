/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Administratienummer;
import nl.bzk.copy.model.attribuuttype.Burgerservicenummer;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;
import nl.bzk.copy.model.validatie.constraint.Anummer;
import nl.bzk.copy.model.validatie.constraint.Bsn;


/**
 * @author boen
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatienummersGroepBericht extends AbstractGroepBericht implements
        PersoonIdentificatienummersGroepBasis
{

    @Bsn
    private Burgerservicenummer burgerservicenummer;
    @Anummer
    private Administratienummer administratienummer;

    /**
     * .
     *
     * @return .
     * @see nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getBurgerservicenummer()
     */
    @Override
    public Burgerservicenummer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * .
     *
     * @return .
     * @see nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getAdministratienummer()
     */
    @Override
    public Administratienummer getAdministratienummer() {
        return administratienummer;
    }

    public void setAdministratienummer(final Administratienummer administratienummer) {
        this.administratienummer = administratienummer;
    }

}
