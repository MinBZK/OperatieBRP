/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig;

/**
 * HisVolledig klasse voor Administratieve handeling \ Gedeblokkeerde melding.
 *
 */
@Entity
@Table(schema = "Kern", name = "AdmHndGedeblokkeerdeMelding")
public class AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl extends AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl
        implements HisVolledigImpl, AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Gedeblokkeerde melding.
     * @param gedeblokkeerdeMelding gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl(
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMelding)
    {
        super(administratieveHandeling, gedeblokkeerdeMelding);
    }

}
