/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws.service.brp;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.dto.verzoek.Bijhouding;
import org.apache.cxf.annotations.DataBinding;

@WebService(serviceName = "BijhoudingService",
            name = "BijhoudingService",
            portName = "BijhoudingPort",
            targetNamespace = "http://www.brp.bzk.nl/bijhouding/ws/service",
            wsdlLocation = "D:\\mGBA BRP\\Sources\\bevraging\\branches\\poc_branch-v0.3.0\\webservice\\src\\main\\resources\\bijhouding.wsdl")
public interface BijhoudingService {

    @WebMethod(operationName = "Bijhouding")
    @WebResult(name = "BijhoudingResultaat",
               partName = "bijhoudingResultaat",
               targetNamespace = "http://www.brp.bzk.nl/bijhouding/ws/service/model")
    BijhoudingResultaat bijhouden(@WebParam(partName = "bijhouding",
                                            name = "Bijhouding",
                                            targetNamespace = "http://www.brp.bzk.nl/bijhouding/ws/service/model")
                                  Bijhouding bijhouding);
}
