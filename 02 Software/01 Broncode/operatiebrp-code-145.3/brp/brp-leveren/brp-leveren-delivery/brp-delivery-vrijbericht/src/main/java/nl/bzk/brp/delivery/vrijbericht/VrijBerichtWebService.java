/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import java.io.StringReader;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.AlgemeneFoutHandler;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerwerker;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.apache.cxf.interceptor.Fault;
import org.xml.sax.InputSource;


/**
 * Implementatie van BRP VrijBerichtService websservice.
 */
@WebService(wsdlLocation = "wsdl/vrijbericht.wsdl",
        serviceName = "VrijBerichtService",
        portName = "vrbStuurVrijBericht")
@ServiceMode(value = Service.Mode.PAYLOAD)
public final class VrijBerichtWebService implements Provider<DOMSource> {

    /**
     * Het {@link Schema} tbv request validatie.
     */
    public static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_vrbVrijBericht_Berichten.xsd");

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private OinResolver oinResolver;
    private SchemaValidatorService schemaValidatorService;
    private VrijBerichtVerwerker vrijBerichtService;

    /**
     * Default constuctor.
     */
    public VrijBerichtWebService() {
        // Default constructor is nodig binnen web service implementaties.
    }

    /**
     * Constructor.
     * @param oinResolver de OIN resolver.
     * @param schemaValidatorService de schemavalidator service
     * @param vrijBerichtService de vrij bericht service
     */
    @Inject
    public VrijBerichtWebService(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
                                 final VrijBerichtVerwerker vrijBerichtService) {
        this.oinResolver = oinResolver;
        this.schemaValidatorService = schemaValidatorService;
        this.vrijBerichtService = vrijBerichtService;
    }

    @Override
    public DOMSource invoke(final DOMSource request) {
        Thread.currentThread().setName("Vrij bericht ontvanger");
        LOGGER.debug("Vrij bericht ontvanger aangeroepen");
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        try {
            schemaValidatorService.valideer(request, VrijBerichtWebService.SCHEMA);
        } catch (SchemaValidatorService.SchemaValidatieException schemaValidatieException) {
            LOGGER.debug("Vrij bericht ontvanger aangeroepen met invalide xml", schemaValidatieException);
            throw new Fault(schemaValidatieException.getCause());
        }
        return AlgemeneFoutHandler.doeBijFout(e -> {
            LOGGER.error("Algemene fout", e);
            throw new WebServiceException("Er is iets fout gegaan bij het verwerken van het vrij bericht verzoek.");
        }).voerUit(() -> maakResponse(request));
    }

    private DOMSource maakResponse(final DOMSource request) throws VerzoekParseException, MaakDomSourceException, TransformerException {
        final VrijBerichtVerzoek verzoek = new VrijBerichtParser().parse(TransformerUtil.initializeNode(request));
        verzoek.setOin(oinResolver.get());
        verzoek.setBrpKoppelvlakVerzoek(true);
        final VrijBerichtCallbackImpl callback = new VrijBerichtCallbackImpl();
        vrijBerichtService.stuurVrijBericht(verzoek, callback);
        return XmlUtils.toDOMSource(new InputSource(new StringReader(callback.getBerichtResultaat())));
    }
}
