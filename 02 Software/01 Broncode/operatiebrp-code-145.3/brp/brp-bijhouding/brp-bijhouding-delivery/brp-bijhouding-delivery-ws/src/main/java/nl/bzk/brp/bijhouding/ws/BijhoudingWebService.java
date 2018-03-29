/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import java.io.IOException;
import java.util.Collections;
import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.validation.SchemaValidationHelper;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import nl.bzk.brp.bijhouding.business.BijhoudingAntwoordBerichtService;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.xml.sax.SAXException;

/**
 * Implementatie van BRP bijhoudingsservice.
 */
@WebServiceProvider(wsdlLocation = "wsdl/bijhouding.wsdl", serviceName = "BijhoudingService")
@ServiceMode()
public final class BijhoudingWebService implements Provider<DOMSource> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final BerichtAuthenticatieInfoService berichtAuthenticatieInfoService;
    private final BijhoudingService bijhoudingService;
    private final BerichtTransformatieService berichtTransformatieService;
    private final BijhoudingAntwoordBerichtService bijhoudingAntwoordBerichtService;

    /**
     * constructor creating new Bijhoudings web service.
     * @param berichtAuthenticatieInfoService autenticatie info service
     * @param bijhoudingService bijhoudings service
     * @param berichtTransformatieService
     * @param bijhoudingAntwoordBerichtService
     */
    @Inject
    public BijhoudingWebService(BerichtAuthenticatieInfoService berichtAuthenticatieInfoService,
                                BijhoudingService bijhoudingService,
                                BerichtTransformatieService berichtTransformatieService,
                                BijhoudingAntwoordBerichtService bijhoudingAntwoordBerichtService) {
        this.berichtAuthenticatieInfoService = berichtAuthenticatieInfoService;
        this.bijhoudingService = bijhoudingService;
        this.berichtTransformatieService = berichtTransformatieService;
        this.bijhoudingAntwoordBerichtService = bijhoudingAntwoordBerichtService;
    }

    @Override
    public DOMSource invoke(final DOMSource bericht) {
        LOGGER.debug("BijhoudingWebService aangeroepen");

        valideer(bericht);
        final BijhoudingVerzoekBericht verzoekBericht =
                berichtAuthenticatieInfoService.vulBerichtAanMetOinGegevensUitHttpHeader(PhaseInterceptorChain.getCurrentMessage(), parse(bericht));

        BijhoudingAntwoordBericht bijhoudingAntwoordBericht;
        try {
            bijhoudingAntwoordBericht = bijhoudingService.verwerkBrpBericht(verzoekBericht);
        } catch (Exception e) {
            LOGGER.error("Algemene fout bij verwerking van bijhoudingsbericht.", e);
            bijhoudingAntwoordBericht =
                    bijhoudingAntwoordBerichtService.maakAntwoordBericht(
                        verzoekBericht,
                        Collections.singletonList(MeldingElement.getInstance(Regel.ALG0001, verzoekBericht.getAdministratieveHandeling())),
                        null,
                        null);
        }
        return transformeerNaarDOMSource(bijhoudingAntwoordBericht);
    }

    private void valideer(final DOMSource bericht) {
        try {
            final Schema schema = SchemaValidationHelper.getSchema();
            final Validator validator = schema.newValidator();
            validator.validate(bericht);
        } catch (final
            SAXException
            | IOException e){
            throw new Fault(e);
        }
    }

    private BijhoudingVerzoekBericht parse(final DOMSource bericht) {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        try {
            final BijhoudingVerzoekBericht verzoekBericht = parser.parse(bericht);
            verzoekBericht.setXml(transformeerNaarXml(bericht));
            return verzoekBericht;
        } catch (ParseException e) {
            LOGGER.error("BijhoudingWebService fout bij het parsen van binnenkomend bericht: ", e);
            throw new WebServiceException(e);
        }
    }

    private String transformeerNaarXml(final DOMSource bericht) {
        try {
            return berichtTransformatieService.transformeerNaarString(bericht);
        } catch ( TransformerException e) {
            LOGGER.error("BijhoudingWebService fout bij het archiveren van het verzoek bericht: ", e);
            throw new WebServiceException(e);
        }
    }

    private DOMSource transformeerNaarDOMSource(final BijhoudingAntwoordBericht antwoordBericht) {
        try {
            return berichtTransformatieService.transformeerNaarDOMSource(antwoordBericht.getXml());
        } catch (WriteException e) {
            LOGGER.error("BijhoudingWebService fout bij omzetten van String naar DOMSource: ", e);
            throw new WebServiceException(e);
        }
    }
}
