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
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;


/**
 * HisVolledig klasse voor Onderzoek.
 */
@Entity
@Table(schema = "Kern", name = "Onderzoek")
public class OnderzoekHisVolledigImpl extends AbstractOnderzoekHisVolledigImpl implements HisVolledigImpl,
    OnderzoekHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    public OnderzoekHisVolledigImpl() {
        super();
    }

}
