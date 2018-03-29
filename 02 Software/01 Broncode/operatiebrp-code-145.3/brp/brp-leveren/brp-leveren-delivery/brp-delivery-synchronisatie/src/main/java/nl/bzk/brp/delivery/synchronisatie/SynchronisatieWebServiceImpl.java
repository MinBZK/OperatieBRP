/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import com.google.common.collect.Maps;
import java.io.StringReader;
import java.util.Map;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.AlgemeneFoutHandler;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import nl.bzk.brp.service.synchronisatie.persoon.SynchroniseerPersoonService;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenService;
import org.apache.cxf.interceptor.Fault;
import org.xml.sax.InputSource;


/**
 * Implementatie van BRP levering synchronisatie web webservice ten behoeve van het synchroniseren van BRP data met eigen systemen van afnemers en gemeenten.
 */
@WebService(wsdlLocation = "wsdl/synchronisatie.wsdl",
        serviceName = "SynchronisatieService",
        portName = "lvgSynchronisatie")
@ServiceMode(value = Service.Mode.PAYLOAD)
public class SynchronisatieWebServiceImpl implements Provider<DOMSource> {

    /**
     * Het {@link Schema} tbv request validatie.
     */
    public static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgSynchronisatie_Berichten.xsd");

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private SynchroniseerPersoonService synchroniseerPersoonService;
    private SynchroniseerStamgegevenService synchroniseerStamgegevenService;
    private SchemaValidatorService schemaValidatorService;
    private OinResolver oinResolver;

    /**
     * Default constuctor.
     */
    public SynchronisatieWebServiceImpl() {
        // Default constructor is nodig binnen web service implementaties.
    }

    /**
     * Constructor.
     * @param synchroniseerPersoonService de synchroniseer persoon service
     * @param synchroniseerStamgegevenService de synchroniseer stamgegeven service
     * @param schemaValidatorService de schemavalidator service
     * @param oinResolver de OIN resolver
     */
    @Inject
    public SynchronisatieWebServiceImpl(final SynchroniseerPersoonService synchroniseerPersoonService,
                                        final SynchroniseerStamgegevenService synchroniseerStamgegevenService,
                                        final SchemaValidatorService schemaValidatorService, final OinResolver oinResolver) {
        this.synchroniseerPersoonService = synchroniseerPersoonService;
        this.synchroniseerStamgegevenService = synchroniseerStamgegevenService;
        this.schemaValidatorService = schemaValidatorService;
        this.oinResolver = oinResolver;
    }

    /**
     * Handelt het verzoek af.
     * @param request het verzoek
     * @return het response.
     */
    @Bedrijfsregel(Regel.R1978)
    @Bedrijfsregel(Regel.R1979)
    @Override
    public final DOMSource invoke(final DOMSource request) {
        Thread.currentThread().setName("Synchronisatie Service");
        LOGGER.debug("SynchronisatieService aangeroepen");
        try {
            schemaValidatorService.valideer(request, SCHEMA);
        } catch (SchemaValidatorService.SchemaValidatieException schemaValidatieException) {
            LOGGER.debug("SynchronisatieService aangeroepen met invalide xml", schemaValidatieException);
            throw new Fault(schemaValidatieException.getCause());
        }
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        return AlgemeneFoutHandler
                .doeBijFout(e1 -> {
                    LOGGER.error("Algemene fout", e1);
                    throw new WebServiceException("Er is iets fout gegaan bij het verwerken van het verzoek.");
                }).voerUit(() -> maakResponse(request));
    }

    private DOMSource maakResponse(final DOMSource request) {
        try {
            final SynchronisatieVerzoek verzoek = new SynchronisatieBerichtParser().parse(TransformerUtil.initializeNode(request));
            verzoek.setOin(oinResolver.get());
            verzoek.setBrpKoppelvlakVerzoek(true);

            final Map<String, String> mdcMap = Maps.newHashMap();
            mdcMap.put(LeveringVeld.MDC_AANGEROEPEN_DIENST.getVeld(), String.valueOf(verzoek.getSoortDienst()));
            mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), verzoek.getParameters().getLeveringsAutorisatieId());
            mdcMap.put(LeveringVeld.MDC_REFERENTIE_NUMMER.getVeld(), verzoek.getStuurgegevens().getReferentieNummer());
            mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), verzoek.getStuurgegevens().getZendendePartijCode());

            if (SoortDienst.SYNCHRONISATIE_PERSOON == verzoek.getSoortDienst()) {
                final SynchroniseerPersoonCallbackImpl synchronisatieCallback = new SynchroniseerPersoonCallbackImpl();
                MDC.voerUit(mdcMap, () -> synchroniseerPersoonService.synchroniseer(verzoek, synchronisatieCallback));
                return XmlUtils.toDOMSource(new InputSource(new StringReader(synchronisatieCallback.getResultaat())));
            } else {
                final SynchroniseerStamgegevenCallbackImpl stamgegevenCallback = new SynchroniseerStamgegevenCallbackImpl();
                MDC.voerUit(mdcMap, () -> synchroniseerStamgegevenService.maakResponse(verzoek, stamgegevenCallback));
                return XmlUtils.toDOMSource(new InputSource(new StringReader(stamgegevenCallback.getResultaat())));
            }
        } catch (final VerzoekParseException | MaakDomSourceException | TransformerException e) {
            throw new WebServiceException(e);
        }
    }

}
