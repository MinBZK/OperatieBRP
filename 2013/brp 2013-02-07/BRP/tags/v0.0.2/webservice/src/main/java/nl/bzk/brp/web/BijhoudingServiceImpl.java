/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.business.service.BerichtVerwerker;

/**
 * Implementatie van BRP bijhoudingsservice.
 */
//
@WebService(
        wsdlLocation = "WEB-INF/wsdl/bijhouding.wsdl",
        serviceName = "BijhoudingService",
        portName = "BijhoudingPort"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingServiceImpl implements BijhoudingService {

    @Inject
    private BerichtVerwerker berichtVerwerker;

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtResultaat bijhouden(@WebParam(name = "Bijhouding", partName = "bericht") final BRPBericht bericht) {
        return berichtVerwerker.verwerkBericht(bericht);
    }

}
