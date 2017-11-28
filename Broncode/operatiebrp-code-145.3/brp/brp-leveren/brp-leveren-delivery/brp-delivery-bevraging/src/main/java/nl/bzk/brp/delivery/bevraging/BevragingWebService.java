/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.AlgemeneFoutHandler;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import org.apache.cxf.interceptor.Fault;
import org.xml.sax.InputSource;


/**
 * Implementatie van BRP bevragingsservice tbv leveringen.
 */
@WebService(wsdlLocation = "wsdl/bevraging.wsdl",
        serviceName = "LeveringBevragingService",
        portName = "lvgBevraging")
@ServiceMode(value = Service.Mode.PAYLOAD)
public final class BevragingWebService implements Provider<DOMSource> {

    /**
     * Het {@link Schema} tbv request validatie.
     */
    public static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgBevraging_Berichten.xsd");

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private OinResolver oinResolver;
    private SchemaValidatorService schemaValidatorService;
    private Map<SoortDienst, BevragingVerzoekVerwerker<BevragingVerzoek>> bevragingVerzoekVerwerkerMap;

    /**
     * Default constructor.
     */
    public BevragingWebService() {
        // Default constructor is nodig binnen web service implementaties.
    }

    /**
     * Constructor.
     * @param oinResolver de OIN resolver
     * @param schemaValidatorService de schemavalidator service
     * @param bevragingVerzoekVerwerkerMap de map met bevraging verzoek verwerkers per dienst
     */
    @Inject
    public BevragingWebService(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
                               final Map<SoortDienst, BevragingVerzoekVerwerker<BevragingVerzoek>> bevragingVerzoekVerwerkerMap) {
        this.oinResolver = oinResolver;
        this.schemaValidatorService = schemaValidatorService;
        this.bevragingVerzoekVerwerkerMap = bevragingVerzoekVerwerkerMap;
    }

    @Override
    public DOMSource invoke(final DOMSource request) {
        Thread.currentThread().setName("Bevraging voor Levering");
        LOGGER.debug("Bevraging voor levering aangeroepen");
        try {
            schemaValidatorService.valideer(request, SCHEMA);
        } catch (SchemaValidatorService.SchemaValidatieException schemaValidatieException) {
            LOGGER.debug("Bevraging aangeroepen met invalide xml", schemaValidatieException);
            throw new Fault(schemaValidatieException.getCause());
        }
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        return AlgemeneFoutHandler.doeBijFout(e -> {
            LOGGER.error("Algemene fout", e);
            throw new WebServiceException("Er is iets fout gegaan bij het verwerken van het verzoek.");
        }).voerUit(() -> maakResponse(request));
    }

    private DOMSource maakResponse(final DOMSource request) {
        try {
            final BevragingVerzoek bevragingVerzoek = new LeveringBevragingBerichtParser().parse(request);
            bevragingVerzoek.setOin(oinResolver.get());
            bevragingVerzoek.setBrpKoppelvlakVerzoek(true);

            final Map<String, String> mdcMap = Maps.newHashMap();
            mdcMap.put(BevragingVeld.MDC_AANGEROEPEN_DIENST.getVeld(), bevragingVerzoek.getSoortDienst().getNaam());
            mdcMap.put(BevragingVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), bevragingVerzoek.getParameters().getLeveringsAutorisatieId());
            mdcMap.put(BevragingVeld.MDC_REFERENTIE_NUMMER.getVeld(), bevragingVerzoek.getStuurgegevens().getReferentieNummer());
            mdcMap.put(BevragingVeld.MDC_PARTIJ_CODE.getVeld(), bevragingVerzoek.getStuurgegevens().getZendendePartijCode());

            if (!bevragingVerzoekVerwerkerMap.containsKey(bevragingVerzoek.getSoortDienst())) {
                throw new UnsupportedOperationException();
            }

            final BevragingVerzoekVerwerker<BevragingVerzoek> verzoekVerwerker = bevragingVerzoekVerwerkerMap.get(bevragingVerzoek.getSoortDienst());
            final BevragingCallbackImpl bevragingCallback = new BevragingCallbackImpl();
            MDC.voerUit(mdcMap, () -> verzoekVerwerker.verwerk(bevragingVerzoek, bevragingCallback));
            return XmlUtils.toDOMSource(new InputSource(new StringReader(bevragingCallback.getResultaat())));

        } catch (final VerzoekParseException | MaakDomSourceException | TransformerException e) {
            throw new WebServiceException(e);
        }
    }
}
