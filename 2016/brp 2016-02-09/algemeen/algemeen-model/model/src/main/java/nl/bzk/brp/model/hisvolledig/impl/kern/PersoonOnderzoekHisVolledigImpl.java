/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;


/**
 * HisVolledig klasse voor Persoon \ Onderzoek.
 */
@Entity
@Table(schema = "Kern", name = "PersOnderzoek")
public class PersoonOnderzoekHisVolledigImpl extends AbstractPersoonOnderzoekHisVolledigImpl implements
    HisVolledigImpl, PersoonOnderzoekHisVolledig, ALaagAfleidbaar,
    ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected PersoonOnderzoekHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon   persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     */
    public PersoonOnderzoekHisVolledigImpl(final PersoonHisVolledigImpl persoon,
        final OnderzoekHisVolledigImpl onderzoek)
    {
        super(persoon, onderzoek);
    }

}
