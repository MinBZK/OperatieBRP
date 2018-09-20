/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContext;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractBijhoudingsautorisatieWebService;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Implementatie van BRP bevragingsservice.
 */
@WebService(wsdlLocation = "wsdl/bijhouding-bevraging.wsdl",
    serviceName = "BijhoudingBevragingService",
    portName = "bhgBevraging")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingBevragingServiceImpl
    extends AbstractBijhoudingsautorisatieWebService<BevragingsBericht, BevragingBerichtContext, BevragingResultaat>
    implements BijhoudingBevragingService
{

    @Inject
    @Named("bijhoudingBevragingsBerichtVerwerker")
    private BerichtVerwerker bevragingsBerichtVerwerker;


    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(operationName = "geefDetailsPersoon")
    public final GeefDetailsPersoonAntwoordBericht geefDetailsPersoon(
        @WebParam(name = "bhg_bvgGeefDetailsPersoon", partName = "bhg_bvgGeefDetailsPersoon")
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht)
    {

        final BevragingResultaat berichtResultaat = voerBerichtUit(geefDetailsPersoonBericht);

        return (GeefDetailsPersoonAntwoordBericht)
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(geefDetailsPersoonBericht, berichtResultaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(operationName = "geefPersonenOpAdresMetBetrokkenheden")
    public final GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht geefPersonenOpAdresMetBetrokkenheden(
        @WebParam(name = "bhg_bvgGeefPersonenOpAdresMetBetrokkenheden",
            partName = "bhg_bvgGeefPersonenOpAdresMetBetrokkenheden")
        final GeefPersonenOpAdresMetBetrokkenhedenBericht geefPersonenOpAdresMetBetrokkenhedenBericht)
    {
        final BevragingResultaat berichtResultaat =
            voerBerichtUit(geefPersonenOpAdresMetBetrokkenhedenBericht);

        return (GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht)
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(
                geefPersonenOpAdresMetBetrokkenhedenBericht, berichtResultaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(operationName = "bepaalKandidaatVader")
    public final BepaalKandidaatVaderAntwoordBericht bepaalKandidaatVader(
        @WebParam(name = "bhg_bvgBepaalKandidaatVader", partName = "bhg_bvgBepaalKandidaatVader")
        final BepaalKandidaatVaderBericht bepaalKandidaatVaderBericht)
    {
        final BevragingResultaat berichtResultaat = voerBerichtUit(bepaalKandidaatVaderBericht);

        return (BepaalKandidaatVaderAntwoordBericht)
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bepaalKandidaatVaderBericht, berichtResultaat);
    }

    @Override
    protected BevragingResultaat verwerkBericht(final BevragingsBericht bericht,
        final BevragingBerichtContext context)
    {
        return (BevragingResultaat) bevragingsBerichtVerwerker.verwerkBericht(bericht, context);
    }

    @Override
    protected BevragingBerichtContext bouwBerichtContext(final ReferentienummerAttribuut berichtReferentieNummer,
        final BerichtenIds berichtenIds,
        final Partij partij,
        final CommunicatieIdMap identificeerbareObjecten)
    {
        return new BevragingBerichtContextBasis(berichtenIds, partij,
            berichtReferentieNummer.getWaarde(), identificeerbareObjecten);

    }

    @Override
    protected BevragingResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new BevragingResultaat(meldingen);
    }
}
