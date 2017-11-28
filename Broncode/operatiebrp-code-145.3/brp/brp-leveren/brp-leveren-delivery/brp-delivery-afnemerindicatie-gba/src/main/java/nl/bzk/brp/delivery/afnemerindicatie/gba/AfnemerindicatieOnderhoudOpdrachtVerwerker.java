/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.afnemerindicatie.Afnemerindicatie;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.gba.autorisatie.AutorisatieHandler;
import nl.bzk.brp.service.gba.autorisatie.GbaAutorisaties;
import org.springframework.stereotype.Component;

/**
 * Verwerker voor afnemer indicatie onderhoud opdrachten.
 */
@Component
final class AfnemerindicatieOnderhoudOpdrachtVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final GbaAutorisaties gbaAutorisaties;
    private final OnderhoudAfnemerindicatieService afnemerindicatieService;
    private final RegelcodeVertaler<Character> regelcodeVertaler;

    /**
     * Constructor.
     * @param afnemerindicatieService BRP afnemer indicatie service
     * @param regelcodeVertaler vertaler van BRP regelcodes naar Lo3 foutcodes
     * @param gbaAutorisaties utility voor bepalen autorisaties
     */
    @Inject
    AfnemerindicatieOnderhoudOpdrachtVerwerker(
            final OnderhoudAfnemerindicatieService afnemerindicatieService,
            final RegelcodeVertaler<Character> regelcodeVertaler,
            final GbaAutorisaties gbaAutorisaties) {
        this.afnemerindicatieService = afnemerindicatieService;
        this.regelcodeVertaler = regelcodeVertaler;
        this.gbaAutorisaties = gbaAutorisaties;
    }

    /**
     * Verwerk opdracht.
     * @param opdracht opdracht om te verwerken
     * @return antwoord
     */
    AfnemerindicatieOnderhoudAntwoord verwerk(final AfnemerindicatieOnderhoudOpdracht opdracht) {
        final AutorisatieHandler<AfnemerindicatieOnderhoudAntwoord> autorisatieHandler = new AutorisatieHandler<>(gbaAutorisaties);
        if (opdracht.getEffectAfnemerindicatie() == EffectAfnemerindicaties.PLAATSING) {
            return autorisatieHandler.verwerkMetAutorisatie(
                    opdracht.getPartijCode(), null, SoortDienst.PLAATSING_AFNEMERINDICATIE,
                    autorisatiebundel -> bepaalAntwoord(opdracht, autorisatiebundel),
                    () -> maakAntwoord(opdracht.getReferentienummer(), 'X')
            );
        } else {
            return autorisatieHandler.verwerkMetAutorisatie(
                    opdracht.getPartijCode(), null, SoortDienst.VERWIJDERING_AFNEMERINDICATIE,
                    autorisatiebundel -> bepaalAntwoord(opdracht, autorisatiebundel),
                    () -> { throw new IllegalStateException("Geen autorisatie gevonden voor verwijderen afnemerindicatie"); }
            );
        }
    }


    private AfnemerindicatieOnderhoudAntwoord maakAntwoord(final String referentienummer, final Character foutcode) {
        final AfnemerindicatieOnderhoudAntwoord antwoord = new AfnemerindicatieOnderhoudAntwoord();
        antwoord.setReferentienummer(referentienummer);
        antwoord.setFoutcode(foutcode);
        return antwoord;
    }

    /**
     * Bepaal het antwoord aan de hand van de opdracht.
     * @param opdracht afnemer indicatie onderhoud opdracht
     * @param autorisatiebundel de autorisatiebundel
     * @return het antwoord
     */
    private AfnemerindicatieOnderhoudAntwoord bepaalAntwoord(
            final AfnemerindicatieOnderhoudOpdracht opdracht,
            final Autorisatiebundel autorisatiebundel) {

        final AfnemerindicatieOnderhoudAntwoord antwoord;

        if (autorisatiebundel.getToegangLeveringsautorisatie() == null) {
            LOGGER.info("[Bericht {}] - Toegang leveringsautorisatie niet gevonden", opdracht.getReferentienummer());
            antwoord = maakAntwoord(opdracht.getReferentienummer(), 'X');
        } else if (opdracht.getBsn() == null) {
            LOGGER.info("[Bericht {}] - Persoonsgegevens niet gevonden", opdracht.getReferentienummer());
            antwoord = maakAntwoord(opdracht.getReferentienummer(), 'G');
        } else {
            LOGGER.info("[Bericht {}] - Verwerk plaatsing", opdracht.getReferentienummer());

            final Dienst dienstSpontaan;
            if (autorisatiebundel.getToegangLeveringsautorisatie() == null) {
                dienstSpontaan = null;
            } else {
                dienstSpontaan = AutAutUtil.zoekDienst(autorisatiebundel.getLeveringsautorisatie(), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
            }

            if (opdracht.getEffectAfnemerindicatie() == EffectAfnemerindicaties.PLAATSING && dienstSpontaan == null) {
                // Geen spontaan bij plaatsen
                LOGGER.debug("[Bericht {}] - Geen spontaan (mutatielevering obv afnemersindicatie) dienst bij autorisatie ", opdracht.getReferentienummer());
                antwoord = maakAntwoord(opdracht.getReferentienummer(), 'R');
            } else {
                antwoord = new AfnemerindicatieOnderhoudAntwoord();
                antwoord.setReferentienummer(opdracht.getReferentienummer());
                final Afnemerindicatie afnemerindicatie = maakAfnemerindicatie(opdracht, autorisatiebundel.getToegangLeveringsautorisatie());
                final AfnemerindicatieVerzoek
                        afnemerindicatieVerzoek =
                        maakAfnemerindicatieVerzoek(opdracht, opdracht.getReferentienummer(), afnemerindicatie,
                                autorisatiebundel.getToegangLeveringsautorisatie());

                final AfnemerindicatieCallbackImpl callback = new AfnemerindicatieCallbackImpl();
                afnemerindicatieService.onderhoudAfnemerindicatie(afnemerindicatieVerzoek, callback);
                logRegels(opdracht.getReferentienummer(), callback.getMeldingen());
                regelcodeVertaler.bepaalFoutcode(callback.getMeldingen()).ifPresent(antwoord::setFoutcode);
            }
        }

        return antwoord;
    }

    /**
     * Voor de functionele test is het handig om te weten welke BRP regels zijn getriggered.
     * @param meldingen meldingen
     */
    private void logRegels(final String referentie, final List<Melding> meldingen) {
        if (meldingen != null) {
            for (final Melding melding : meldingen) {
                if (melding.getRegel() != null) {
                    LOGGER.info("[Bericht {}] - Regel getriggered: {}", referentie, melding.getRegel());
                }
            }
        }
    }

    private Afnemerindicatie maakAfnemerindicatie(final AfnemerindicatieOnderhoudOpdracht opdracht, final ToegangLeveringsAutorisatie autorisatie) {
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setBsn(opdracht.getBsn());
        afnemerindicatie.setPartijCode(String.valueOf(autorisatie.getGeautoriseerde().getPartij().getCode()));
        return afnemerindicatie;
    }

    private AfnemerindicatieVerzoek maakAfnemerindicatieVerzoek(final AfnemerindicatieOnderhoudOpdracht opdracht,
                                                                final String berichtReferentie,
                                                                final Afnemerindicatie afnemerindicatie,
                                                                final ToegangLeveringsAutorisatie autorisatie) {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);
        if (opdracht.getEffectAfnemerindicatie() == EffectAfnemerindicaties.PLAATSING) {
            afnemerindicatieVerzoek.setSoortDienst(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        } else if (opdracht.getEffectAfnemerindicatie() == EffectAfnemerindicaties.VERWIJDERING) {
            afnemerindicatieVerzoek.setSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        }
        afnemerindicatieVerzoek.getParameters().setLeveringsAutorisatieId(autorisatie.getLeveringsautorisatie().getId().toString());
        String oinOndertekenaar;
        if (autorisatie.getOndertekenaar() == null || autorisatie.getOndertekenaar().getOin() == null) {
            oinOndertekenaar = autorisatie.getGeautoriseerde().getPartij().getOin();
        } else {
            oinOndertekenaar = autorisatie.getOndertekenaar().getOin();
        }
        String oinTransporteur;
        if (autorisatie.getTransporteur() == null || autorisatie.getTransporteur().getOin() == null) {
            oinTransporteur = autorisatie.getGeautoriseerde().getPartij().getOin();
        } else {
            oinTransporteur = autorisatie.getTransporteur().getOin();
        }

        afnemerindicatieVerzoek.setOin(new OIN(oinOndertekenaar, oinTransporteur));

        afnemerindicatieVerzoek.getStuurgegevens().setReferentieNummer(berichtReferentie);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(autorisatie.getGeautoriseerde().getPartij().getCode()));
        afnemerindicatieVerzoek.getStuurgegevens().setZendendSysteem(Stelsel.GBA.getNaam());

        return afnemerindicatieVerzoek;
    }
}
