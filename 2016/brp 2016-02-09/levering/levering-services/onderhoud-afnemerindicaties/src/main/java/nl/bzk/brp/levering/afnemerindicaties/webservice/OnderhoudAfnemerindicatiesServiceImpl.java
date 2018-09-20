/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.webservice;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContextImpl;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtVerwerker;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractLeveringsautorisatieWebService;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Implementatie van BRP levering synchronisatie web webservice ten behoeve van het synchroniseren van BRP data met eigen systemen van afnemers en
 * gemeenten.
 *
 * @brp.bedrijfsregel R1984
 */
@Regels(Regel.R1984)
@WebService(wsdlLocation = "wsdl/levering-afnemerindicaties.wsdl",
    serviceName = "AfnemerindicatiesService",
    portName = "lvgAfnemerindicaties")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class OnderhoudAfnemerindicatiesServiceImpl extends AbstractLeveringsautorisatieWebService<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat> implements OnderhoudAfnemerindicatiesService
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("onderhoudAfnemerindicatiesBerichtVerwerker")
    private OnderhoudAfnemerindicatiesBerichtVerwerker onderhoudAfnemerindicatiesBerichtVerwerker;

    @Regels(Regel.R1984)
    @Override
    @WebMethod(operationName = "registreerAfnemerindicatie")
    public final RegistreerAfnemerindicatieAntwoordBericht registreerAfnemerindicatie(
        final RegistreerAfnemerindicatieBericht bericht)
    {
        Thread.currentThread().setName("OnderhoudAfnemerindicatie");
        zetMDCVelden(bericht);
        final OnderhoudAfnemerindicatiesResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerAfnemerindicatieAntwoordBericht) antwoordBericht;
    }

    @Override
    protected final OnderhoudAfnemerindicatiesResultaat verwerkBericht(final RegistreerAfnemerindicatieBericht bericht,
        final OnderhoudAfnemerindicatiesBerichtContext context)
    {
        final OnderhoudAfnemerindicatiesResultaat berichtVerwerkingsResultaat;
        if (SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE == bericht.getSoort().getWaarde()) {
            berichtVerwerkingsResultaat = onderhoudAfnemerindicatiesBerichtVerwerker.verwerkBericht(bericht, context);
        } else {
            LOGGER.error("Fout soort bericht aanroepen: {}", bericht.getSoort().getWaarde());
            throw new IllegalArgumentException("Soort bericht wordt niet ondersteund door de onderhoud afnemerindicaties service: "
                                                   + bericht.getSoort().getWaarde());
        }
        LOGGER.debug("Onderhoud afnemerindicaties bericht verwerkt resultaat succesvol: {}", berichtVerwerkingsResultaat.isSuccesvol());
        return berichtVerwerkingsResultaat;
    }

    @Override
    protected final OnderhoudAfnemerindicatiesBerichtContext bouwBerichtContext(
        final ReferentienummerAttribuut berichtReferentieNummer,
        final BerichtenIds berichtenIds,
        final Partij geautoriseerde,
        final CommunicatieIdMap identificeerbareObjecten)
    {
        return new OnderhoudAfnemerindicatiesBerichtContextImpl(berichtenIds, geautoriseerde,
                                                                berichtReferentieNummer.getWaarde(), identificeerbareObjecten);
    }

    @Override
    protected final OnderhoudAfnemerindicatiesResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new OnderhoudAfnemerindicatiesResultaat(meldingen);
    }

    /**
     * Vul waardes in voor MDC logging.
     *
     * @param bericht inkomend bericht
     */
    private void zetMDCVelden(final RegistreerAfnemerindicatieBericht bericht) {
        final BerichtRootObject rootObject = bericht.getAdministratieveHandeling().getHoofdActie().getRootObject();
        if (rootObject instanceof PersoonBericht) {
            final PersoonBericht persoonBericht = (PersoonBericht) rootObject;
            MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, bericht.getParameters().getLeveringsautorisatieID());
        }
        if(bericht.getAdministratieveHandeling().getSoort().getWaarde() == SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE) {
            MDC.put(MDCVeld.MDC_AANGEROEPEN_DIENST, SoortDienst.PLAATSEN_AFNEMERINDICATIE.getNaam());
        } else if (bericht.getAdministratieveHandeling().getSoort().getWaarde() == SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE) {
            MDC.put(MDCVeld.MDC_AANGEROEPEN_DIENST, SoortDienst.VERWIJDEREN_AFNEMERINDICATIE.getNaam());
        }
        MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, bericht.getStuurgegevens().getReferentienummer().getWaarde());
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, bericht.getStuurgegevens().getZendendePartijCode());
    }
}
