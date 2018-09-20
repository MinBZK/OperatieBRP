/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatieNummersGroepBasis;
import nl.bzk.brp.model.validatie.constraint.Bsn;
import nl.bzk.brp.model.validatie.constraint.Anummer;


/**
 * @author boen
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatieNummersGroepBericht extends AbstractGroepBericht implements
        PersoonIdentificatieNummersGroepBasis
{

    @Bsn
    private Burgerservicenummer burgerServiceNummer;
    @Anummer
    private Administratienummer administratieNummer;

    /**
     * .
     *
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatieNummersGroepBasis#getBurgerServiceNummer()
     * @return .
     */
    @Override
    public Burgerservicenummer getBurgerServiceNummer() {
        return burgerServiceNummer;
    }

    public void setBurgerServiceNummer(final Burgerservicenummer burgerServiceNummer) {
        this.burgerServiceNummer = burgerServiceNummer;
    }

    /**
     * .
     *
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatieNummersGroepBasis#getAdministratieNummer()
     * @return .
     */
    @Override
    public Administratienummer getAdministratieNummer() {
        return administratieNummer;
    }

    public void setAdministratieNummer(final Administratienummer administratieNummer) {
        this.administratieNummer = administratieNummer;
    }

}
