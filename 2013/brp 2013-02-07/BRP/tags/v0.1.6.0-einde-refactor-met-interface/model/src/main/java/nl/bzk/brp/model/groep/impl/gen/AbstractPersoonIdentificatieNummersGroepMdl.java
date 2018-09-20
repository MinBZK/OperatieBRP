/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * .
 */
package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis;

/**
 * @author boen
 *
 */
public abstract class AbstractPersoonIdentificatieNummersGroepMdl extends AbstractGroep implements PersoonIdentificatieNummersGroepBasis {
    private Burgerservicenummer burgerServiceNummer;
    private Administratienummer administratieNummer;

    /**
     * .
     * @see nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis#getBurgerServiceNummer()
     * @return .
     */
    @Override
    public Burgerservicenummer getBurgerServiceNummer() {
        return this.burgerServiceNummer;
    }

    /**
     * .
     * @see nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis#getAdministratieNummer()
     * @return .
     */
    @Override
    public Administratienummer getAdministratieNummer() {
        return this.administratieNummer;
    }

}
