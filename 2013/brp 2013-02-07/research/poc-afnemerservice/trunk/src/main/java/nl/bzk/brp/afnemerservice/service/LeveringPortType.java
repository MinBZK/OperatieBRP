/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.afnemerservice.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.levering.LEVLeveringBijgehoudenPersoonLvAntwoord;
import org.apache.cxf.annotations.DataBinding;
import org.springframework.stereotype.Component;

@Component("LeveringPortType")
@WebService(wsdlLocation = "xsd/levering.wsdl", serviceName = "LeveringService", portName = "LeveringPort", targetNamespace = "http://www.bprbzk.nl/BRP/levering/service")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@DataBinding(org.apache.cxf.jibx.JibxDataBinding.class)
public interface LeveringPortType {

    LEVLeveringBijgehoudenPersoonLvAntwoord mutatiePersoon(final LEVLeveringBijgehoudenPersoonLv mutatiePersoon) ;
}
