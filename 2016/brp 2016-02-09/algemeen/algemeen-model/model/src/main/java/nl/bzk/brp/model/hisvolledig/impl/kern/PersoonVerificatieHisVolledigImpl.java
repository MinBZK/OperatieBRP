/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;


/**
 * HisVolledig klasse voor Persoon \ Verificatie.
 */
@Entity
@Table(schema = "Kern", name = "PersVerificatie")
public class PersoonVerificatieHisVolledigImpl extends AbstractPersoonVerificatieHisVolledigImpl implements
    HisVolledigImpl, PersoonVerificatieHisVolledig, ALaagAfleidbaar,
    ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected PersoonVerificatieHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param partij        partij van Persoon \ Verificatie.
     * @param soort         soort van Persoon \ Verificatie.
     */
    public PersoonVerificatieHisVolledigImpl(final PersoonHisVolledigImpl geverifieerde, final PartijAttribuut partij,
        final NaamEnumeratiewaardeAttribuut soort)
    {
        super(geverifieerde, partij, soort);
    }

}
