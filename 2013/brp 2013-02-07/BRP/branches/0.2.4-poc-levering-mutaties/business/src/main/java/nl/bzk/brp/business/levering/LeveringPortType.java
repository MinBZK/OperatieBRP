/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.levering;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.stereotype.Component;

@WebService(wsdlLocation = "xsd/levering.wsdl", serviceName = "LeveringService", portName = "LeveringPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LeveringPortType {

    LEVLeveringBijgehoudenPersoonLvAntwoord mutatiePersoon(final LEVLeveringBijgehoudenPersoonLv mutatiePersoon) ;
}
