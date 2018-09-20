/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.ANummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;
import nl.bzk.brp.model.validatie.constraint.Anummer;
import nl.bzk.brp.model.validatie.constraint.Bsn;


/**
 * @author boen
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatienummersGroepBericht extends AbstractGroepBericht implements
    PersoonIdentificatienummersGroepBasis
{

    @Bsn
    private Burgerservicenummer burgerservicenummer;
    @Anummer
    private ANummer administratienummer;

    /**
     * .
     *
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getBurgerservicenummer()
     * @return .
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
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getANummer()
     * @return .
     */
    @Override
    public ANummer getANummer() {
        return administratienummer;
    }

    public void setAdministratienummer(final ANummer ANummer) {
        administratienummer = administratienummer;
    }

}
