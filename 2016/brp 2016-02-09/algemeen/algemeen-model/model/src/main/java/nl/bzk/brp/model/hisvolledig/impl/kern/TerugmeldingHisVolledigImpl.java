/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;


/**
 * HisVolledig klasse voor Terugmelding.
 */
@Entity
@Table(schema = "Kern", name = "Terugmelding")
public class TerugmeldingHisVolledigImpl extends AbstractTerugmeldingHisVolledigImpl implements HisVolledigImpl,
    TerugmeldingHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected TerugmeldingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmeldendePartij terugmeldendePartij van Terugmelding.
     * @param persoon             persoon van Terugmelding.
     * @param bijhoudingsgemeente bijhoudingsgemeente van Terugmelding.
     * @param tijdstipRegistratie tijdstipRegistratie van Terugmelding.
     */
    public TerugmeldingHisVolledigImpl(final PartijAttribuut terugmeldendePartij, final PersoonHisVolledigImpl persoon,
        final PartijAttribuut bijhoudingsgemeente, final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(terugmeldendePartij, persoon, bijhoudingsgemeente, tijdstipRegistratie);
    }

}
