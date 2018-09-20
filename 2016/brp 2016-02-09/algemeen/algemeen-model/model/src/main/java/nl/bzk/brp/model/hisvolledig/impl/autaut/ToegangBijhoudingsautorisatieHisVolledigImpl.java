/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;

/**
 * HisVolledig klasse voor Toegang bijhoudingsautorisatie.
 *
 */
@Entity
@Table(schema = "AutAut", name = "ToegangBijhautorisatie")
@Access(value = AccessType.FIELD)
public class ToegangBijhoudingsautorisatieHisVolledigImpl extends AbstractToegangBijhoudingsautorisatieHisVolledigImpl implements HisVolledigImpl,
        ToegangBijhoudingsautorisatieHisVolledig, ALaagAfleidbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected ToegangBijhoudingsautorisatieHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     */
    public ToegangBijhoudingsautorisatieHisVolledigImpl(
        final PartijRolAttribuut geautoriseerde,
        final PartijAttribuut ondertekenaar,
        final PartijAttribuut transporteur)
    {
        super(geautoriseerde, ondertekenaar, transporteur);
    }

}
