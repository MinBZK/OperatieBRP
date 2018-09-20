/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.afnemerservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.levering.Antwoord;
import nl.bzk.brp.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.levering.LEVLeveringBijgehoudenPersoonLvAntwoord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Ten behoeve van WS-Addressing is @Addressing toegevoegd

@Component("LeveringPortTypeImpl")
@WebService(wsdlLocation = "xsd/levering.wsdl", serviceName = "LeveringService", portName = "LeveringPort")
//@Addressing(enabled = true, required = true)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class LeveringPortTypeImpl implements LeveringPortType {

    private static final Logger  LOGGER = LoggerFactory.getLogger(LeveringPortTypeImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see nl.bprbzk.brp._0002.LeveringPortType#mutatiePersoon(nl.bprbzk.brp._0002.LEVLeveringBijgehoudenPersoonLv
     * mutatiePersoon )*
     */
    @Override
    @WebMethod(operationName = "mutatiePersoon")
    public LEVLeveringBijgehoudenPersoonLvAntwoord mutatiePersoon(@WebParam(name = "MutatiePersoon", partName = "bericht") final LEVLeveringBijgehoudenPersoonLv bericht)
    {
        LOGGER.info("********************************************************************************");
        LOGGER.info("*");
        LOGGER.info("* Executing operation mutatiePersoon");
        LOGGER.info("*");
        LOGGER.info("********************************************************************************");

        // Ten behoeve van WS-Addressing
        //final AddressingProperties addressProp = (AddressingProperties) webServiceContext.getMessageContext().get(org.apache.cxf.ws.addressing.JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_INBOUND);
       // final String messageId = addressProp.getMessageID().getValue();

        // TODO: weghalen log
       // LOGGER.debug("messageId: " + messageId + ", persoon: " + bericht.getAdministratieveHandeling().getLeveringen().getLevering().getIdentificatie().getPersoon().getIdentificatienummers().getBurgerservicenummer().getBurgerservicenummer());

        LEVLeveringBijgehoudenPersoonLvAntwoord antwoordBericht = new LEVLeveringBijgehoudenPersoonLvAntwoord();
        Antwoord antwoord = new Antwoord();
        antwoord.setAntwoord(Antwoord.AntwoordInner.GOED);
        antwoordBericht.setAntwoord(antwoord);
        return antwoordBericht;
    }

}
