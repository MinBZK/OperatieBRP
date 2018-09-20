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

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.RegistratieOverlijdenBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.AbstractAntwoordBericht;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.RegistratieOverlijdenAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;


/**
 * Implementatie van BRP bijhoudingsservice.
 */
@WebService(wsdlLocation = "WEB-INF/wsdl/bijhouding.wsdl", serviceName = "BijhoudingService",
            portName = "BijhoudingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingServiceImpl extends AbstractWebService<AbstractBijhoudingsBericht, BijhoudingResultaat>
        implements BijhoudingService
{

    @Inject
    private BerichtVerwerker<AbstractBijhoudingsBericht,BerichtContext,BijhoudingResultaat> bijhoudingsBerichtVerwerker;

    /**
     * {@inheritDoc}
     */
    @Override
    public VerhuizingAntwoordBericht verhuizing(
            @WebParam(name = "Bijhouding", partName = "bericht") final VerhuizingBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        AbstractAntwoordBericht verhuizingAntwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht,
                                                                                                  berichtResultaat);
        verrijkAntwoordBericht(verhuizingAntwoordBericht, bericht, berichtResultaat);
        return (VerhuizingAntwoordBericht) verhuizingAntwoordBericht;
    }


    @Override
    public CorrectieAdresNLAntwoordBericht correctieAdresNL(
            @WebParam(name = "Bijhouding", partName = "bericht") final CorrectieAdresBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        AbstractAntwoordBericht antwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht,
                                                                                                         berichtResultaat);
        verrijkAntwoordBericht(antwoordBericht, bericht, berichtResultaat);

        return (CorrectieAdresNLAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InschrijvingGeboorteAntwoordBericht inschrijvingGeboorte(
            @WebParam(name = "Bijhouding", partName = "bericht") final InschrijvingGeboorteBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);


        AbstractAntwoordBericht antwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);
        verrijkAntwoordBericht(antwoordBericht, bericht, berichtResultaat);

        return (InschrijvingGeboorteAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuwelijkAntwoordBericht registreerHuwelijkEnPartnerschap(
            @WebParam(name = "Bijhouding", partName = "bericht")
            final HuwelijkBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        AbstractAntwoordBericht antwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);
        verrijkAntwoordBericht(antwoordBericht, bericht, berichtResultaat);

        return (HuwelijkAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistratieOverlijdenAntwoordBericht registreerOverlijden(
            @WebParam(name = "Bijhouding", partName = "bericht") final RegistratieOverlijdenBericht bericht)
    {
        BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        AbstractAntwoordBericht antwoordBericht = stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);
        verrijkAntwoordBericht(antwoordBericht, bericht, berichtResultaat);

        return (RegistratieOverlijdenAntwoordBericht) antwoordBericht;
    }

    @Override
    protected BijhoudingResultaat
    verwerkBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context)
    {
        return bijhoudingsBerichtVerwerker.verwerkBericht(bericht, context);
    }

    @Override
    protected BijhoudingResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new BijhoudingResultaat(meldingen);
    }

    /**
     * Voegt technische sleutel toe indien resultaat. In de toekomst moet dit eigenlijk direct gedaan worden door
     * bevraging en bijhouding antwoordberichten apart te behandelen
     * @param antwoordBericht
     * @param bericht
     * @param berichtResultaat
     */
    private void verrijkAntwoordBericht(final AbstractAntwoordBericht antwoordBericht,
                                        final BerichtBericht bericht, final BijhoudingResultaat berichtResultaat) {
        if (berichtResultaat.getVerwerkingsResultaat()) {
            antwoordBericht.getAdministratieveHandeling().setTechnischeSleutel(bericht.getAdministratieveHandeling().getTechnischeSleutel());
        } else {
            // moet gespecced worden, zie SIERRA-464
        }


    }
}
