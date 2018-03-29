/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingAntwoordElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.IdentificatienummersElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.ResultaatElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.bericht.writer.BijhoudingAntwoordBerichtWriter;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import org.springframework.stereotype.Service;

/**
 * Implementatie voor de {@link BijhoudingAntwoordBerichtService}.
 */
@Service
@Bedrijfsregel(Regel.R1266)
public final class BijhoudingAntwoordBerichtServiceImpl implements BijhoudingAntwoordBerichtService {

    private static final String ZENDENDE_PARTIJ = "199901";
    private static final String ZENDENDE_SYSTEEM = "BRP";
    private final ValidatieService validatieService;

    /**
     * constructor;
     * @param validatieService {@link ValidatieService}
     */
    @Inject
    public BijhoudingAntwoordBerichtServiceImpl(ValidatieService validatieService) {
        this.validatieService = validatieService;
    }

    /**
     * Maakt het {@link BijhoudingAntwoordBericht}.
     * @param verzoekBericht het verzoek bericht
     * @param meldingen de gevonden meldingen
     * @param administratieveHandeling de administratieve handeling die uit het verzoek is ontstaan
     * @param bijhoudingsplanContext het bijhoudingsplan
     * @return een gevuld antwoordbericht
     */
    @Override
    public BijhoudingAntwoordBericht maakAntwoordBericht(
            final BijhoudingVerzoekBericht verzoekBericht,
            final List<MeldingElement> meldingen,
            final AdministratieveHandeling administratieveHandeling,
            final BijhoudingsplanContext bijhoudingsplanContext) {
        final SoortMelding meldingHoogsteNiveau = validatieService.bepaalHoogsteMeldingNiveau(meldingen);

        final VerwerkingsResultaat verwerkingResultaat = bepaalVerwerkingsResultaat(meldingHoogsteNiveau);

        return maakBijhoudingAntwoordBericht(
                verzoekBericht,
                meldingen,
                bijhoudingsplanContext,
                meldingHoogsteNiveau,
                verwerkingResultaat,
                maakAdministratieveHandelingAntwoordElement(verzoekBericht, bijhoudingsplanContext),
                administratieveHandeling);
    }

    private VerwerkingsResultaat bepaalVerwerkingsResultaat(final SoortMelding meldingHoogsteNiveau) {
        if (meldingHoogsteNiveau.getMeldingNiveau() > SoortMelding.WAARSCHUWING.getMeldingNiveau()) {
            return VerwerkingsResultaat.FOUTIEF;
        } else {
            return VerwerkingsResultaat.GESLAAGD;
        }
    }

    private BijhoudingAntwoordBericht maakBijhoudingAntwoordBericht(
            final BijhoudingVerzoekBericht verzoekBericht,
            final List<MeldingElement> meldingen,
            final BijhoudingsplanContext bijhoudingsplanContext,
            final SoortMelding meldingHoogsteNiveau,
            final VerwerkingsResultaat verwerkingResultaat,
            final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord,
            final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingAntwoordBericht result =
                BijhoudingAntwoordBericht.getInstance(
                        verzoekBericht.getSoort().getAntwoordBijhoudingBerichtSoort(),
                        StuurgegevensElement.getInstance(
                                ZENDENDE_PARTIJ,
                                ZENDENDE_SYSTEEM,
                                null,
                                verzoekBericht.getStuurgegevens().getReferentienummer().getWaarde(),
                                DatumUtil.nuAlsZonedDateTime()),
                        ResultaatElement.getInstance(
                                verwerkingResultaat,
                                bijhoudingsplanContext != null ? bijhoudingsplanContext.bepaalBijhoudingResultaat() : null,
                                meldingHoogsteNiveau),
                        meldingen,
                        administratieveHandelingAntwoord);
        if (administratieveHandeling != null) {
            result.setAdministratieveHandelingID(administratieveHandeling.getId());
        }

        result.setXml(transformeerAntwoordBerichtNaarXml(result));
        return result;
    }

    private AdministratieveHandelingAntwoordElement maakAdministratieveHandelingAntwoordElement(
            final BijhoudingVerzoekBericht verzoekBericht,
            final BijhoudingsplanContext bijhoudingsplanContext) {
        return AdministratieveHandelingAntwoordElement.getInstance(
                verzoekBericht.getAdministratieveHandeling(),
                verzoekBericht.getTijdstipOntvangst().getWaarde(),
                maakBezienVanuitPersonenAntwoord(verzoekBericht),
                maakGedeblokkeerdeMeldingenAntwoord(verzoekBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen()),
                bijhoudingsplanContext != null ? maakPersoonElementenVoorAntwoord(
                        bijhoudingsplanContext.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden()) : null,
                bijhoudingsplanContext != null ? bijhoudingsplanContext.maakBijhoudingsplanElementVoorBijhoudingAntwoordBericht() : null);
    }

    private List<PersoonGegevensElement> maakPersoonElementenVoorAntwoord(final List<BijhoudingPersoon> personen) {
        final List<PersoonGegevensElement> results = new ArrayList<>();
        for (final BijhoudingPersoon bijhoudingPersoon : personen) {
            final String actueelBurgerservicenummer = bijhoudingPersoon.getActueelBurgerservicenummer();
            if (actueelBurgerservicenummer != null) {
                results.add(PersoonGegevensElement.getInstance(IdentificatienummersElement.getInstance(actueelBurgerservicenummer, null)));
            }
        }
        return results;
    }

    private List<GedeblokkeerdeMeldingElement> maakGedeblokkeerdeMeldingenAntwoord(final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen) {
        return gedeblokkeerdeMeldingen.stream().map(GedeblokkeerdeMeldingElement::getInstanceVoorAntwoord).collect(Collectors.toList());
    }

    private List<PersoonGegevensElement> maakBezienVanuitPersonenAntwoord(final BijhoudingVerzoekBericht verzoekBericht) {
        final List<PersoonGegevensElement> bezienVanuitPersonen = verzoekBericht.getAdministratieveHandeling().getBezienVanuitPersonen();
        final List<PersoonGegevensElement> bzvuAntwoord = new ArrayList<>();
        for (final PersoonElement bzvuPersoon : bezienVanuitPersonen) {
            try {
                final long persoonId =
                        ApplicationContextProvider.getObjectSleutelService()
                                .maakPersoonObjectSleutel(bzvuPersoon.getObjectSleutel()).getDatabaseId();
                final Persoon persoon = ApplicationContextProvider.getPersoonRepository().findById(persoonId);
                final PersoonIDHistorie idHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonIDHistorieSet());
                bzvuAntwoord.add(
                        PersoonGegevensElement.getInstance(IdentificatienummersElement.getInstance(String.valueOf(idHistorie.getBurgerservicenummer()), null)));
            } catch (OngeldigeObjectSleutelException e) {
                throw new IllegalStateException(e);
            }
        }
        return bzvuAntwoord;
    }

    private String transformeerAntwoordBerichtNaarXml(final BijhoudingAntwoordBericht antwoordBericht) {
        try {
            final BijhoudingAntwoordBerichtWriter writer = new BijhoudingAntwoordBerichtWriter();
            final StringWriter stringWriter = new StringWriter();
            writer.write(antwoordBericht, stringWriter);
            return stringWriter.toString();
        } catch (WriteException e) {
            throw new IllegalStateException("Het antwoordbericht kan niet worden omgezet in XML.", e);
        }
    }
}
