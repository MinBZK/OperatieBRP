/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bijhouding.HuwelijkEnGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;


/** Implementatie van BRP bijhoudingsservice. */
@WebService(wsdlLocation = "WEB-INF/wsdl/bijhouding.wsdl", serviceName = "BijhoudingService",
            portName = "BijhoudingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingServiceImpl extends AbstractWebService<AbstractBijhoudingsBericht, BijhoudingResultaat>
        implements BijhoudingService
{

    @Inject
    private BerichtVerwerker bijhoudingsBerichtVerwerker;

    /** {@inheritDoc} */
    @Override
    public VerhuizingAntwoordBericht verhuizing(
            @WebParam(name = "Bijhouding", partName = "bericht") final VerhuizingBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        return new VerhuizingAntwoordBericht(berichtResultaat);
    }

    /** {@inheritDoc} */
    @Override
    public InschrijvingGeboorteAntwoordBericht inschrijvingGeboorte(
            @WebParam(name = "Bijhouding", partName = "bericht") final InschrijvingGeboorteBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        return new InschrijvingGeboorteAntwoordBericht(berichtResultaat);
    }

    /** {@inheritDoc} */
    @Override
    public HuwelijkEnGeregistreerdPartnerschapAntwoordBericht registreerHuwelijkEnPartnerschap(
            @WebParam(name = "Bijhouding", partName = "bericht")
            final HuwelijkEnGeregistreerdPartnerschapBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        return new HuwelijkEnGeregistreerdPartnerschapAntwoordBericht(berichtResultaat);
    }

    @Override
    protected BijhoudingResultaat verwerkBericht(final BRPBericht bericht, final BerichtContext context) {
        return bijhoudingsBerichtVerwerker.verwerkBericht(bericht, context);
    }

    @Override
    protected BijhoudingResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new BijhoudingResultaat(meldingen);
    }

}
