/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.web.bevraging.BevragingAntwoordBericht;

/** Implementatie van BRP bevragingsservice. */
@WebService(wsdlLocation = "WEB-INF/wsdl/bevraging.wsdl", serviceName = "BevragingService",
    portName = "BevragingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BevragingServiceImpl extends AbstractWebService<OpvragenPersoonBericht> implements BevragingService {

    @Inject
    private BerichtVerwerker bevragingsBerichtVerwerker;

    /** {@inheritDoc} */
    @Override
    @WebMethod(operationName = "bevraging")
    public BevragingAntwoordBericht opvragenPersoon(@WebParam(name = "OpvragenPersoonBericht",
        partName = "OpvragenPersoonBericht") final OpvragenPersoonBericht opvragenPersoonBericht)
    {
        OpvragenPersoonResultaat berichtResultaat =
            (OpvragenPersoonResultaat) voerBerichtUit(bevragingsBerichtVerwerker, opvragenPersoonBericht);
        return new BevragingAntwoordBericht(berichtResultaat);
    }

}
