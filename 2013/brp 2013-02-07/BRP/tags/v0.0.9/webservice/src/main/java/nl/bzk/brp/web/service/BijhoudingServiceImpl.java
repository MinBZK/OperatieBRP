/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;


/** Implementatie van BRP bijhoudingsservice. */
@WebService(wsdlLocation = "WEB-INF/wsdl/bijhouding.wsdl", serviceName = "BijhoudingService",
    portName = "BijhoudingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingServiceImpl extends AbstractWebService<BijhoudingsBericht> implements BijhoudingService {

    @Inject
    private BerichtVerwerker bijhoudingsBerichtVerwerker;

    /** {@inheritDoc} */
    @Override
    public VerhuizingAntwoordBericht verhuizing(
        @WebParam(name = "Bijhouding", partName = "bericht") final BijhoudingsBericht bericht)
    {
        BerichtResultaat berichtResultaat = voerBerichtUit(bijhoudingsBerichtVerwerker, bericht);

        return new VerhuizingAntwoordBericht(berichtResultaat);
    }

    /** {@inheritDoc} */
    @Override
    public InschrijvingGeboorteAntwoordBericht inschrijvingGeboorte(
        @WebParam(name = "Bijhouding", partName = "bericht") final BijhoudingsBericht bericht)
    {
        BerichtResultaat berichtResultaat = voerBerichtUit(bijhoudingsBerichtVerwerker, bericht);

        return new InschrijvingGeboorteAntwoordBericht(berichtResultaat);
    }
}
