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
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractLeveringsautorisatieWebService;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Implementatie van BRP bevragingsservice tbv leveringen.
 */
@WebService(wsdlLocation = "wsdl/levering-bevraging.wsdl",
            serviceName = "LeveringBevragingService",
            portName = "lvgBevraging")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class LeveringBevragingServiceImpl
        extends AbstractLeveringsautorisatieWebService<BevragingsBericht, BevragingBerichtContext, BevragingResultaat>
        implements LeveringBevragingService
{

    @Inject
    @Named("leveringBevragingsBerichtVerwerker")
    private BerichtVerwerker bevragingsBerichtVerwerker;

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(operationName = "geefDetailsPersoon")
    public final GeefDetailsPersoonAntwoordBericht geefDetailsPersoon(
            @WebParam(name = "lvg_bvgGeefDetailsPersoon", partName = "lvg_bvgGeefDetailsPersoon")
            final GeefDetailsPersoonBericht geefDetailsPersoonBericht)
    {
        Thread.currentThread().setName("Bevraging");
        final BevragingResultaat berichtResultaat = voerBerichtUit(geefDetailsPersoonBericht);

        return (GeefDetailsPersoonAntwoordBericht)
                stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(geefDetailsPersoonBericht, berichtResultaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(operationName = "zoekPersoon")
    public final ZoekPersoonAntwoordBericht zoekPersoon(
            @WebParam(name = "lvg_bvgZoekPersoon", partName = "lvg_bvgZoekPersoon")
            final ZoekPersoonBericht zoekPersoonBericht)
    {
        Thread.currentThread().setName("Bevraging");
        final BevragingResultaat berichtResultaat = voerBerichtUit(zoekPersoonBericht);

        return (ZoekPersoonAntwoordBericht)
                stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(zoekPersoonBericht, berichtResultaat);
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
                                                         final Partij geautoriseerde,
                                                         final CommunicatieIdMap identificeerbareObjecten)
    {
        return new BevragingBerichtContextBasis(berichtenIds, geautoriseerde,
                                                berichtReferentieNummer.getWaarde(), identificeerbareObjecten);
    }

    @Override
    protected BevragingResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new BevragingResultaat(meldingen);
    }
}
