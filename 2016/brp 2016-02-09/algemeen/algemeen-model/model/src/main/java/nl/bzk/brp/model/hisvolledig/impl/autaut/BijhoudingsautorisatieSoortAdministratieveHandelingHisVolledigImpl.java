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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig;

/**
 * HisVolledig klasse voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
@Entity
@Table(schema = "AutAut", name = "BijhautorisatieSrtAdmHnd")
@Access(value = AccessType.FIELD)
public class BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl extends
        AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl implements HisVolledigImpl,
        BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl(
        final ToegangBijhoudingsautorisatieHisVolledigImpl toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling)
    {
        super(toegangBijhoudingsautorisatie, soortAdministratieveHandeling);
    }

}
