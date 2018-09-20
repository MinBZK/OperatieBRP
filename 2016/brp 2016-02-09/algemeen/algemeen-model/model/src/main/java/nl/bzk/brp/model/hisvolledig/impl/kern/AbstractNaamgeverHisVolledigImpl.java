/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledigBasis;

/**
 * HisVolledig klasse voor Naamgever.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractNaamgeverHisVolledigImpl extends BetrokkenheidHisVolledigImpl implements NaamgeverHisVolledigBasis, ElementIdentificeerbaar {

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractNaamgeverHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Naamgever.
     * @param persoon persoon van Naamgever.
     */
    public AbstractNaamgeverHisVolledigImpl(final RelatieHisVolledigImpl relatie, final PersoonHisVolledigImpl persoon) {
        super(relatie, new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.NAAMGEVER), persoon);
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.NAAMGEVER;
    }

}
