/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.web;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.pocmotor.model.Bericht;
import nl.bzk.brp.pocmotor.model.BerichtResultaat;

/**
 * Interface van de bijhouding service.
 */
@WebService(serviceName = "BijhoudingService",
            name = "BijhoudingService",
            portName = "BijhoudingPort",
            wsdlLocation = "/WEB-INF/wsdl/bijhouding.wsdl",
            targetNamespace = "http://www.brp.bzk.nl/bijhouding/ws/service")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface BijhoudingService {

    @WebMethod(operationName = "bijhouden")
    @WebResult(name = "BerichtResultaat",
               partName = "berichtResultaat")
    BerichtResultaat bijhouden(@WebParam(partName = "bericht",
                                         name = "Bijhouding")
    Bericht bericht);

}
