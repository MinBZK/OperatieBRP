/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.brp.levering.synchronisatie.webservice;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.synchronisatie.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractLeveringsautorisatieWebService;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Implementatie van BRP levering synchronisatie web webservice ten behoeve van het synchroniseren van BRP data met eigen systemen van afnemers en
 * gemeenten.
 *
 * @brp.bedrijfsregel R1978
 * @brp.bedrijfsregel R1979
 */
@Regels({Regel.R1978, Regel.R1979})
@WebService(wsdlLocation = "wsdl/levering-synchronisatie.wsdl",
    serviceName = "SynchronisatieService",
    portName = "lvgSynchronisatie")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class SynchronisatieServiceImpl extends AbstractLeveringsautorisatieWebService<AbstractSynchronisatieBericht, SynchronisatieBerichtContext,
    SynchronisatieResultaat> implements SynchronisatieService
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("synchronisatiePersoonBerichtVerwerker")
    private BerichtVerwerker<AbstractSynchronisatieBericht, SynchronisatieBerichtContext, SynchronisatieResultaat> synchronisatiePersoonBerichtVerwerker;

    @Inject
    @Named("synchronisatieStamgegevenBerichtVerwerker")
    private BerichtVerwerker<AbstractSynchronisatieBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
        synchronisatieStamgegevenBerichtVerwerker;

    @Regels(Regel.R1978)
    @Override
    @WebMethod(operationName = "geefSynchronisatiePersoon")
    public final GeefSynchronisatiePersoonAntwoordBericht geefSynchronisatiePersoon(final GeefSynchronisatiePersoonBericht
        geefSynchronisatiePersoonBericht)
    {
        Thread.currentThread().setName("Synchronisatie");
        zetMDCVelden(geefSynchronisatiePersoonBericht, SoortDienst.SYNCHRONISATIE_PERSOON);
        LOGGER.debug("geefSynchronisatiePersoon: aangeroepen");

        final SynchronisatieResultaat berichtResultaat = voerBerichtUit(geefSynchronisatiePersoonBericht);

        LOGGER.debug("geefSynchronisatiePersoon: berichtresultaat gemaakt");

        return (GeefSynchronisatiePersoonAntwoordBericht) stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(geefSynchronisatiePersoonBericht,
                berichtResultaat);
    }

    @Regels(Regel.R1979)
    @Override
    @WebMethod(operationName = "geefSynchronisatieStamgegeven")
    public final GeefSynchronisatieStamgegevenAntwoordBericht geefSynchronisatieStamgegeven(final GeefSynchronisatieStamgegevenBericht
                                                                                                    geefSynchronisatieStamgegevenBericht)
    {
        Thread.currentThread().setName("Synchronisatie");
        zetMDCVelden(geefSynchronisatieStamgegevenBericht, SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
        LOGGER.debug("geefSynchronisatieStamgegeven: aangeroepen");

        final SynchronisatieResultaat berichtResultaat = voerBerichtUit(geefSynchronisatieStamgegevenBericht);

        LOGGER.debug("geefSynchronisatieStamgegeven: berichtresultaat gemaakt");

        return (GeefSynchronisatieStamgegevenAntwoordBericht)
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(geefSynchronisatieStamgegevenBericht, berichtResultaat);
    }

    @Override
    protected final SynchronisatieResultaat verwerkBericht(final AbstractSynchronisatieBericht bericht, final SynchronisatieBerichtContext context) {
        final SynchronisatieResultaat berichtVerwerkingsResultaat;
        if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON == bericht.getSoort().getWaarde()) {
            berichtVerwerkingsResultaat = synchronisatiePersoonBerichtVerwerker.verwerkBericht(bericht, context);
        } else if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN == bericht.getSoort().getWaarde()) {
            berichtVerwerkingsResultaat = synchronisatieStamgegevenBerichtVerwerker.verwerkBericht(bericht, context);
        } else {
            throw new IllegalArgumentException("Soort bericht wordt niet ondersteund door de synchronisatie service: "
                + bericht.getSoort().getWaarde());
        }
        return berichtVerwerkingsResultaat;
    }

    @Override
    protected final SynchronisatieBerichtContext bouwBerichtContext(final ReferentienummerAttribuut berichtReferentieNummer,
                                                                    final BerichtenIds berichtenIds, final Partij geautoriseerde,
                                                                    final CommunicatieIdMap identificeerbareObjecten)
    {
        return new SynchronisatieBerichtContext(berichtenIds, geautoriseerde, berichtReferentieNummer.getWaarde(),
                                                identificeerbareObjecten);
    }

    @Override
    protected final SynchronisatieResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new SynchronisatieResultaat(meldingen);
    }

    /**
     * Vul waardes in voor MDC logging.
     *
     * @param bericht           inkomend bericht
     * @param soortDienst de aangeroepen dienst
     */
    private void zetMDCVelden(final BerichtBericht bericht, final SoortDienst soortDienst) {
        MDC.put(MDCVeld.MDC_AANGEROEPEN_DIENST, soortDienst.name());
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, bericht.getParameters().getLeveringsautorisatieID());
        MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, bericht.getStuurgegevens().getReferentienummer().getWaarde());
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, bericht.getStuurgegevens().getZendendePartijCode());
    }
}
