/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.webservice.business.service.ObjectSleutelPartijInvalideExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVerlopenExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVersleutelingFoutExceptie;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Stap voor verificatie van objectSleutel van alle personen in het bericht. Na verificatie worden de persoonIds
 * toegevoegd aan de context zodat deze kunnen worden gebruikt in de lockstap.
 */
@Component
public class BijhoudingObjectSleutelVerificatieStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ObjectSleutelService objectSleutelService;

    /**
     * Voert de stap uit.
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return de lijst van meldingen
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();

        if (bericht.getAdministratieveHandeling() != null) {
            for (final ActieBericht actieBericht : bericht.getAdministratieveHandeling().getActies()) {
                final RootObject rootObject = actieBericht.getRootObject();
                if (rootObject instanceof RelatieBericht) {
                    resultaatMeldingen.addAll(verifieerObjectSleutelsVanPersonenBinnenRelatie(
                        bericht, context, (RelatieBericht) rootObject));
                } else if (rootObject instanceof PersoonBericht) {
                    resultaatMeldingen.addAll(verifieerObjectSleutelVanPersoon(
                        bericht, context, (PersoonBericht) rootObject));
                }
            }
        }

        return Resultaat.builder().withMeldingen(resultaatMeldingen).build();
    }

    /**
     * Verifieer de objectSleutel van persoon en voeg de bijbehorende persoonId toe aan de context.
     *
     * @param bericht        Het bericht.
     * @param context        de bericht context waaraan het object moet worden toegevoegd.
     * @param persoonBericht Persoon uit het bericht wat de technische sleutel bevat.
     * @return de lijst van meldingen
     */
    private List<ResultaatMelding> verifieerObjectSleutelVanPersoon(final BijhoudingsBericht bericht,
                                                  final BijhoudingBerichtContext context,
                                                  final PersoonBericht persoonBericht)
    {
        final List<ResultaatMelding> resultaatMeldingen = new ArrayList<>();
        if (StringUtils.isNotBlank(persoonBericht.getObjectSleutel())) {
            try {

                final String objectSleutelString = persoonBericht.getObjectSleutel();
                final Integer persoonId = objectSleutelService.bepaalPersoonId(
                    objectSleutelString, context.getPartij().getWaarde().getCode().getWaarde());
                context.getLockingIds().add(persoonId);
            } catch (final ObjectSleutelPartijInvalideExceptie ongeldigeObjectSleutelExceptie) {
                LOGGER.error(ongeldigeObjectSleutelExceptie.getMessage(), ongeldigeObjectSleutelExceptie);
                resultaatMeldingen.add(maakMelding(persoonBericht, Regel.BRAL0015));
            } catch (final ObjectSleutelVerlopenExceptie ongeldigeObjectSleutelExceptie) {
                LOGGER.error(ongeldigeObjectSleutelExceptie.getMessage(), ongeldigeObjectSleutelExceptie);
                resultaatMeldingen.add(maakMelding(persoonBericht, Regel.BRAL0014));
            } catch (final ObjectSleutelVersleutelingFoutExceptie ongeldigeObjectSleutelExceptie) {
                LOGGER.error(ongeldigeObjectSleutelExceptie.getMessage(), ongeldigeObjectSleutelExceptie);
                resultaatMeldingen.add(maakMelding(persoonBericht, Regel.BRAL0016));
            } catch (OngeldigeObjectSleutelExceptie ongeldigeObjectSleutelExceptie) {
                LOGGER.error(ongeldigeObjectSleutelExceptie.getMessage(), ongeldigeObjectSleutelExceptie);
            }

            // Check voor betrokkenheden en personen en haal deze ook op.
            resultaatMeldingen.addAll(verifieerObjectSleutelsVanPersoonBetrokkenheden(bericht, context, persoonBericht));
        }
        return resultaatMeldingen;
    }

    /**
     * Onder een root object persoon kunnen betrokkenheden zitten, bijvoorbeeld bij de actie Registratie gezag.
     * Deze betrokkenheden kunnen weer personen bevatten met objectsleutels die we moeten ophalen. Maar de
     * betrokkenheid kan ook weer een relatie bevatten met betrokkenheden en personen.
     * Ook de objectSleutels van deze personen worden geverifieerd.
     *
     * @param bericht        Het bericht.
     * @param context        de bericht context waaraan het object moet worden toegevoegd.
     * @param persoonBericht Persoon uit het bericht wat de technische sleutel bevat.
     * @return de lijst van meldingen
     */
    private List<ResultaatMelding> verifieerObjectSleutelsVanPersoonBetrokkenheden(final BijhoudingsBericht bericht,
                                                                 final BijhoudingBerichtContext context,
                                                                 final PersoonBericht persoonBericht)
    {
        final List<ResultaatMelding> resultaatMeldingen = new ArrayList<>();
        // In sommige berichten (bijv. registratie gezag) kan een persoon nog betrokkenheden hebben met personen
        // met objectSleutels:
        if (persoonBericht.getBetrokkenheden() != null) {
            for (final BetrokkenheidBericht betrokkenheidOnderRootObjectPersoon : persoonBericht.getBetrokkenheden()) {
                if (betrokkenheidOnderRootObjectPersoon.getPersoon() != null) {
                    resultaatMeldingen.addAll(
                        verifieerObjectSleutelVanPersoon(
                            bericht, context, betrokkenheidOnderRootObjectPersoon.getPersoon()));
                }

                // Er kan ook nog een relatie in zitten met daarin weer betrokkenheden en personen.
                if (betrokkenheidOnderRootObjectPersoon.getRelatie() != null
                    && betrokkenheidOnderRootObjectPersoon.getRelatie().getBetrokkenheden() != null)
                {
                    for (final BetrokkenheidBericht betrokkenheidOnderRelatie : betrokkenheidOnderRootObjectPersoon
                        .getRelatie().getBetrokkenheden())
                    {
                        if (betrokkenheidOnderRelatie.getPersoon() != null) {
                            resultaatMeldingen.addAll(
                                verifieerObjectSleutelVanPersoon(bericht, context, betrokkenheidOnderRelatie.getPersoon()));
                        }
                    }
                }
            }
        }
        return resultaatMeldingen;
    }

     /**
     * Verifieer de objectSleutel van personen binnen de relatie en voer de bijbehorende persoonId toe aan de context.
     *
     * @param bericht        Het bericht.
     * @param context        de context waaraan persoonIds moeten worden toegevoegd.
     * @param relatieBericht relatie uit het bericht.
     * @return de lijst van meldingen
     */
    private List<ResultaatMelding> verifieerObjectSleutelsVanPersonenBinnenRelatie(final BijhoudingsBericht bericht,
                                                                 final BijhoudingBerichtContext context,
                                                                 final RelatieBericht relatieBericht)
    {
        final List<ResultaatMelding> resultaatMeldingen = new ArrayList<>();
        //Haal de Personen op die betrokken zijn bij de relatie, voor zover die bekend zijn in de BRP.
        for (final BetrokkenheidBericht betrokkenheidBericht : relatieBericht.getBetrokkenheden()) {
            if (betrokkenheidBericht.getPersoon() != null) {
                resultaatMeldingen.addAll(verifieerObjectSleutelVanPersoon(
                    bericht, context, betrokkenheidBericht.getPersoon()));
            }
        }
        return resultaatMeldingen;
    }

    private ResultaatMelding maakMelding(final PersoonBericht persoonBericht, final Regel regel) {
        return ResultaatMelding.builder()
            .withRegel(regel)
            .withMeldingTekst(regel.getOmschrijving().replace("%s", persoonBericht.getObjectSleutel()))
            .withReferentieID(persoonBericht.getCommunicatieID()).build();
    }
}
