/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import com.google.common.collect.Maps;
import java.io.StringReader;
import java.util.Map;
import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.AlgemeneFoutHandler;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.apache.cxf.interceptor.Fault;
import org.xml.sax.InputSource;


/**
 * Implementatie van BRP levering synchronisatie web webservice ten behoeve van het synchroniseren van BRP data met eigen systemen van afnemers en
 * gemeenten.
 */
@Bedrijfsregel(Regel.R1984)
@WebServiceProvider(wsdlLocation = "wsdl/afnemerindicatie.wsdl",
        serviceName = "AfnemerindicatiesService",
        portName = "lvgAfnemerindicaties")
@ServiceMode(value = Service.Mode.PAYLOAD)
public class OnderhoudAfnemerindicatiesWebServiceImpl implements Provider<DOMSource> {

    /**
     * Het {@link Schema} tbv request validatie.
     */
    public static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgAfnemerindicatie_Berichten.xsd");

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private OnderhoudAfnemerindicatieService onderhoudAfnemerindicatieService;
    private OinResolver oinResolver;
    private SchemaValidatorService schemaValidatorService;

    /**
     * Default constuctor.
     */
    public OnderhoudAfnemerindicatiesWebServiceImpl() {
        // Default constructor is nodig binnen web service implementaties.
    }

    /**
     * Constructor.
     * @param onderhoudAfnemerindicatieService de onderhoud afnemerindicatie service
     * @param oinResolver de OIN resolver
     * @param schemaValidatorService de schemavalidator service
     */
    @Inject
    public OnderhoudAfnemerindicatiesWebServiceImpl(final OnderhoudAfnemerindicatieService onderhoudAfnemerindicatieService,
                                                    final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService) {
        this.onderhoudAfnemerindicatieService = onderhoudAfnemerindicatieService;
        this.oinResolver = oinResolver;
        this.schemaValidatorService = schemaValidatorService;
    }

    /**
     * Voert het verzoek uit.
     * @param request de ingaande request
     * @return het response
     */
    @Bedrijfsregel(Regel.R1978)
    @Bedrijfsregel(Regel.R1979)
    @Bedrijfsregel(Regel.R1984)
    @Override
    public final DOMSource invoke(final DOMSource request) {
        Thread.currentThread().setName("OnderhoudAfnemerindicatie");
        LOGGER.debug("AfnemerindicatiesService aangeroepen");
        try {
            schemaValidatorService.valideer(request, OnderhoudAfnemerindicatiesWebServiceImpl.SCHEMA);
        } catch (SchemaValidatorService.SchemaValidatieException schemaValidatieException) {
            LOGGER.debug("AfnemerindicatiesService aangeroepen met invalide xml", schemaValidatieException);
            throw new Fault(schemaValidatieException.getCause());
        }
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        return AlgemeneFoutHandler.doeBijFout(
                e1 -> {
                    LOGGER.error("Algemene fout", e1);
                    throw new WebServiceException("Er is iets fout gegaan bij het verwerken van het verzoek.");
                }
        ).voerUit(() -> getDomSource(request));
    }

    private DOMSource getDomSource(final DOMSource request) throws VerzoekParseException, MaakDomSourceException, TransformerException {
        final AfnemerindicatieVerzoek verzoek = new OnderhoudAfnemerindicatiesBerichtParser().parse(TransformerUtil.initializeNode(request));
        verzoek.setOin(oinResolver.get());
        verzoek.setBrpKoppelvlakVerzoek(true);

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), verzoek.getParameters().getLeveringsAutorisatieId());
        mdcMap.put(LeveringVeld.MDC_AANGEROEPEN_DIENST.getVeld(), verzoek.getSoortDienst().getNaam());
        mdcMap.put(LeveringVeld.MDC_REFERENTIE_NUMMER.getVeld(), verzoek.getStuurgegevens()
                .getReferentieNummer());
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), verzoek.getStuurgegevens().getZendendePartijCode());
        final RegistreerAfnemerindicatieCallbackImpl registreerAfnemerindicatieCallback = new RegistreerAfnemerindicatieCallbackImpl();
        MDC.voerUit(mdcMap, () -> onderhoudAfnemerindicatieService.onderhoudAfnemerindicatie(verzoek, registreerAfnemerindicatieCallback));
        return XmlUtils.toDOMSource(new InputSource(new StringReader(registreerAfnemerindicatieCallback.getResultaat())));
    }
}
