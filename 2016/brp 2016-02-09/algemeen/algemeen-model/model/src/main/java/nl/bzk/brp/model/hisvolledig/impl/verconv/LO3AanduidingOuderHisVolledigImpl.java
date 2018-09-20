/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3AanduidingOuderHisVolledig;

/**
 * HisVolledig klasse voor LO3 Aanduiding Ouder.
 *
 */
@Entity
@Table(schema = "VerConv", name = "LO3AandOuder")
@Access(value = AccessType.FIELD)
public class LO3AanduidingOuderHisVolledigImpl extends AbstractLO3AanduidingOuderHisVolledigImpl implements HisVolledigImpl, LO3AanduidingOuderHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LO3AanduidingOuderHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param aanduidingOuder aanduidingOuder van LO3 Aanduiding Ouder.
     */
    public LO3AanduidingOuderHisVolledigImpl(final OuderHisVolledigImpl ouder, final LO3SoortAanduidingOuderAttribuut aanduidingOuder) {
        super(ouder, aanduidingOuder);
    }

}
