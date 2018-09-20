/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoord;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord;


/** Implementatie van BRP bevragingsservice. */
@WebService(wsdlLocation = "WEB-INF/wsdl/bevraging.wsdl", serviceName = "BevragingService", portName = "BevragingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BevragingServiceImpl
        extends AbstractWebService<AbstractBevragingsBericht, OpvragenPersoonResultaat> implements BevragingService
{

    @Inject
    private BerichtVerwerker bevragingsBerichtVerwerker;



    /** {@inheritDoc} */
    @Override
    @WebMethod(operationName = "vraagDetailsPersoon")
    public VraagDetailsPersoonAntwoord opvragenDetailPersoon(
            @WebParam(name = "VraagDetailsPersoonBericht", partName = "VraagDetailsPersoonBericht")
            final VraagDetailsPersoonBericht vraagDetailsPersoonBericht)
    {

        OpvragenPersoonResultaat berichtResultaat = voerBerichtUit(vraagDetailsPersoonBericht);

        return (VraagDetailsPersoonAntwoord)
                stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(vraagDetailsPersoonBericht, berichtResultaat);
    }

    /** {@inheritDoc} */
    @Override
    @WebMethod(operationName = "vraagPersonenOpAdresInclusiefBetrokkenheden")
    public VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord opvragenPersonenOpAdresInclusiefBetrokkenheden(
            @WebParam(name = "VraagPersonenOpAdresInclusiefBetrokkenhedenBericht",
            partName = "VraagPersonenOpAdresInclusiefBetrokkenhedenBericht")
            final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht vraagPersonenOpAdresInclusiefBetrokkenhedenBericht)
    {
        OpvragenPersoonResultaat berichtResultaat =
            voerBerichtUit(vraagPersonenOpAdresInclusiefBetrokkenhedenBericht);

        return (VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord)
                stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(
                        vraagPersonenOpAdresInclusiefBetrokkenhedenBericht, berichtResultaat);
    }

    /** {@inheritDoc} */
    @Override
    @WebMethod(operationName = "vraagKandidaatVader")
    public VraagKandidaatVaderAntwoord opvragenKandidaatVader(
            final VraagKandidaatVaderBericht vraagKandidaatVaderBericht)
    {
        OpvragenPersoonResultaat berichtResultaat = voerBerichtUit(vraagKandidaatVaderBericht);

        return (VraagKandidaatVaderAntwoord)
                stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(vraagKandidaatVaderBericht, berichtResultaat);
    }

    @Override
    protected OpvragenPersoonResultaat verwerkBericht(final AbstractBevragingsBericht bericht,
                                                      final BerichtContext context)
    {
        return bevragingsBerichtVerwerker.verwerkBericht(bericht, context);
    }

    @Override
    protected OpvragenPersoonResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new OpvragenPersoonResultaat(meldingen);
    }
}
