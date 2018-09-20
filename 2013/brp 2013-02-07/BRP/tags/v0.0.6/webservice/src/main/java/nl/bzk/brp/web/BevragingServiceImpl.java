/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.service.BerichtVerwerker;

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
    public BerichtResultaat opvragenPersoon(@WebParam(name = "OpvragenPersoonBericht",
        partName = "OpvragenPersoonBericht") final OpvragenPersoonBericht opvragenPersoonBericht)
    {
        OpvragenPersoonResultaat berichtResultaat = new OpvragenPersoonResultaat(null);
        return voerBerichtUit(bevragingsBerichtVerwerker, opvragenPersoonBericht, berichtResultaat);
    }

}
