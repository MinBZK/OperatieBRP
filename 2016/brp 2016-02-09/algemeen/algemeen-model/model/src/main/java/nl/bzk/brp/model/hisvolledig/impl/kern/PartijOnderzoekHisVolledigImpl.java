/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;


/**
 * HisVolledig klasse voor Partij \ Onderzoek.
 */
@Entity
@Table(schema = "Kern", name = "PartijOnderzoek")
public class PartijOnderzoekHisVolledigImpl extends AbstractPartijOnderzoekHisVolledigImpl implements HisVolledigImpl,
    PartijOnderzoekHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected PartijOnderzoekHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij    partij van Partij \ Onderzoek.
     * @param onderzoek onderzoek van Partij \ Onderzoek.
     */
    public PartijOnderzoekHisVolledigImpl(final PartijAttribuut partij, final OnderzoekHisVolledigImpl onderzoek) {
        super(partij, onderzoek);
    }

}
