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
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.service.BijhoudingsBerichtVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bijhouding.ActualiseerAfstammingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.ActualiseerAfstammingBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresAntwoordBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerKiesrechtAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerKiesrechtBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractBijhoudingsautorisatieWebService;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Implementatie van BRP bijhoudingsservice.
 */
@WebService(wsdlLocation = "wsdl/bijhouding.wsdl", serviceName = "BijhoudingService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public final class BijhoudingServiceImpl extends
    AbstractBijhoudingsautorisatieWebService<BijhoudingsBericht, BijhoudingBerichtContext, BijhoudingResultaat> implements
    BijhoudingService
{

    private static final Logger LOGGER     = LoggerFactory.getLogger();
    private static final String BIJHOUDING = "Bijhouding";
    private static final String BERICHT    = "bericht";

    @Inject
    private BijhoudingsBerichtVerwerker bijhoudingsBerichtVerwerker;

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerVerhuizingAntwoordBericht registreerVerhuizing(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerVerhuizingBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht verhuizingAntwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);
        return (RegistreerVerhuizingAntwoordBericht) verhuizingAntwoordBericht;
    }

    @Override
    public CorrigeerAdresAntwoordBericht corrigeerAdres(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final CorrigeerAdresBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (CorrigeerAdresAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerGeboorteAntwoordBericht registreerGeboorte(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerGeboorteBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerGeboorteAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerGBAGeboorteAntwoordBericht registreerGBAGeboorte(
        @WebParam(name = "Bijhouding", partName = "bericht") final RegistreerGBAGeboorteBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerGBAGeboorteAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerAdoptieAntwoordBericht registreerAdoptie(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerAdoptieBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerAdoptieAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht registreerHuwelijkPartnerschap(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerHuwelijkGeregistreerdPartnerschapBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht) antwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht registreerGBAHuwelijkGeregistreerdPartnerschap(
        @WebParam(name = "Bijhouding", partName = "bericht") final RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht) antwoordBericht;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistreerOverlijdenAntwoordBericht registreerOverlijden(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerOverlijdenBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerOverlijdenAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerErkenningAntwoordBericht registreerErkenning(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerErkenningBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);
        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);
        return (RegistreerErkenningAntwoordBericht) antwoordBericht;
    }

    @Override
    public ActualiseerAfstammingAntwoordBericht actualiseerAfstamming(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final ActualiseerAfstammingBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (ActualiseerAfstammingAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerNaamGeslachtAntwoordBericht registreerNaamGeslacht(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerNaamGeslachtBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerNaamGeslachtAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerNationaliteitAntwoordBericht registreerNationaliteit(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerNationaliteitBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerNationaliteitAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerReisdocumentAntwoordBericht registreerReisdocument(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerReisdocumentBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerReisdocumentAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerMededelingVerzoekAntwoordBericht registreerMededelingVerzoek(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerMededelingVerzoekBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerMededelingVerzoekAntwoordBericht) antwoordBericht;
    }

    @Override
    public RegistreerKiesrechtAntwoordBericht registreerKiesrecht(
        @WebParam(name = BIJHOUDING, partName = BERICHT) final RegistreerKiesrechtBericht bericht)
    {
        final BijhoudingResultaat berichtResultaat = voerBerichtUit(bericht);

        final Bericht antwoordBericht =
            stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(bericht, berichtResultaat);

        return (RegistreerKiesrechtAntwoordBericht) antwoordBericht;
    }

    /**
     * Verwerkt het bijhoudingsbericht.
     *
     * @param bericht het bericht
     * @param context de context
     * @return het bijhouding resultaat
     * @brp.bedrijfsregel VR00055
     */
    @Regels(Regel.VR00055)
    @Override
    protected BijhoudingResultaat verwerkBericht(final BijhoudingsBericht bericht,
        final BijhoudingBerichtContext context)
    {
        final BijhoudingResultaat bijhoudingResultaat = bijhoudingsBerichtVerwerker.verwerkBericht(bericht, context);
        // Wanneer de verwerking succesvol was, de personen en documenten toevoegen aan het resultaat
        if (bijhoudingResultaat.isSuccesvol()) {
            for (final PersoonHisVolledig persoonHisVolledig : context.getBijgehoudenPersonen()) {
                bijhoudingResultaat.voegPersoonToe(new PersoonView(persoonHisVolledig));

                // Voeg persoon Id toe aan het resultaat voor archivering
                if (persoonHisVolledig.getID() != null) {
                    bijhoudingResultaat.getTeArchiverenPersonenUitgaandBericht().add(persoonHisVolledig.getID());
                }
            }
            bijhoudingResultaat.voegDocumentenToe(context.getBijgehoudenDocumenten());
            MDC.put(MDCVeld.MDC_PERSONEN_BIJGEHOUDEN,
                String.valueOf(bijhoudingResultaat.getBijgehoudenPersonen().size()));
            LOGGER.info(FunctioneleMelding.BIJHOUDING_AANTAL_BIJGEHOUDEN_PERSONEN, "Aantal bijgehouden personen {}",
                bijhoudingResultaat.getBijgehoudenPersonen().size());
            MDC.remove(MDCVeld.MDC_PERSONEN_BIJGEHOUDEN);
        }

        return bijhoudingResultaat;
    }

    @Override
    protected BijhoudingBerichtContext bouwBerichtContext(final ReferentienummerAttribuut berichtReferentieNummer,
        final BerichtenIds berichtenIds, final Partij geautoriseerde,
        final CommunicatieIdMap identificeerbareObjecten)
    {
        return new BijhoudingBerichtContext(berichtenIds, geautoriseerde,
            berichtReferentieNummer.getWaarde(), identificeerbareObjecten);
    }

    @Override
    protected BijhoudingResultaat getResultaatInstantie(final List<Melding> meldingen) {
        return new BijhoudingResultaat(meldingen);
    }

}
