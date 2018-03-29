/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.gba.domain.bevraging.Basisvraag;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * Mapt instanties van Basisvraag op instanties van ZoekPersoonGbaVerzoek
 */
public interface VerzoekMapper {
    /**
     * Mapt instanties van Basisvraag op instanties van ZoekPersoonGbaVerzoek.
     * @param verzoekClass subclass van ZoekPersoonGbaVerzoek
     * @param vraag vraag
     * @param autorisatiebundel gba autorisatiebundel
     * @param berichtReferentie bericht referentie
     * @param <T> type van de vraag
     * @param <U> type van het verzoek
     * @return geconverteerde verzoek
     */
    static <T extends Basisvraag, U extends ZoekPersoonGbaVerzoek> U mapZoekPersoonVerzoek(final Class<U> verzoekClass,
                                                                                           final T vraag,
                                                                                           final Autorisatiebundel autorisatiebundel,
                                                                                           final String berichtReferentie) {
        U verzoek;
        try {
            verzoek = mapAlgemeen(verzoekClass.newInstance(), autorisatiebundel, berichtReferentie, vraag.getSoortDienst());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        if (vraag.getGevraagdeRubrieken() != null) {
            verzoek.setGevraagdeRubrieken(vraag.getGevraagdeRubrieken());
        }

        if (vraag.getZoekRubrieken() != null) {
            verzoek.setZoekRubrieken(vraag.getZoekRubrieken());
        }
        verzoek.getZoekCriteriaPersoon().addAll(
                vraag.getZoekCriteria().stream()
                        .map(vraag.isSlimZoekenStandaard() ? ZoekCriteriaMapper::mapInSlimZoekenContext : ZoekCriteriaMapper::map)
                        .collect(Collectors.toList()));

        if (vraag.isZoekenInHistorie()) {
            verzoek.getParameters().setPeilmomentMaterieel(null);
            verzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);
        }
        return verzoek;
    }

    /**
     * Mapt algemene informatie van instanties van Basisvraag op instanties van BevragingVerzoek.
     * @param verzoek instantie van (een subclass van) BevragingVerzoek
     * @param autorisatiebundel gba autorisatiebundel
     * @param berichtReferentie bericht referentie
     * @param soortDienst soort dienst
     * @param <U> type van het verzoek
     * @return geconverteerde verzoek
     */
    static <U extends BevragingVerzoek> U mapAlgemeen(final U verzoek,
                                                      final Autorisatiebundel autorisatiebundel,
                                                      final String berichtReferentie,
                                                      final SoortDienst soortDienst) {
        String oinOndertekenaar = null;
        if (autorisatiebundel.getToegangLeveringsautorisatie().getOndertekenaar() != null) {
            oinOndertekenaar = autorisatiebundel.getToegangLeveringsautorisatie().getOndertekenaar().getOin();
        }
        String oinTransporteur = null;
        if (autorisatiebundel.getToegangLeveringsautorisatie().getTransporteur() != null) {
            oinTransporteur = autorisatiebundel.getToegangLeveringsautorisatie().getTransporteur().getOin();
        }
        verzoek.setOin(new OIN(oinOndertekenaar, oinTransporteur));

        verzoek.setSoortDienst(soortDienst);
        verzoek.getParameters().setCommunicatieId(berichtReferentie);
        verzoek.getParameters().setDienstIdentificatie(autorisatiebundel.getDienst().getId().toString());
        verzoek.getParameters()
                .setLeveringsAutorisatieId(Integer.toString(autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId()));
        verzoek.getParameters().setRolNaam(autorisatiebundel.getRol().getNaam());

        verzoek.getStuurgegevens().setCommunicatieId(berichtReferentie);
        verzoek.getStuurgegevens().setReferentieNummer(berichtReferentie);
        verzoek.getStuurgegevens()
                .setZendendePartijCode(String.valueOf(autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getCode()));

        return verzoek;
    }
}
