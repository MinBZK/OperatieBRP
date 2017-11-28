/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import java.util.stream.Collectors;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStufTransformatieResultaat;
import nl.bzk.brp.domain.berichtmodel.BerichtStufVertaling;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Component;

/**
 * StufBerichtBerichtFactoryImpl.
 */
@Component
final class StufBerichtBerichtFactoryImpl implements StufBerichtBerichtFactory {

    private StufBerichtBerichtFactoryImpl() {
    }

    @Override
    public StufAntwoordBericht maakAntwoordBericht(final StufBerichtResultaat resultaat) {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                .metReferentienummer(resultaat.getReferentienummerAntwoordbericht())
                .metCrossReferentienummer(resultaat.getVerzoek().getStuurgegevens().getReferentieNummer())
                .metZendendePartij(resultaat.getBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metTijdstipVerzending(resultaat.getTijdstipVerzending())
            .eindeStuurgegevens()
            .metMeldingen(resultaat.getMeldingen())
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                .metVerwerking(MeldingUtil.bepaalVerwerking(resultaat.getMeldingen()))
                .metHoogsteMeldingsniveau(MeldingUtil.bepaalHoogsteMeldingNiveau(resultaat.getMeldingen()).getNaam())
                .build()
            ).build();
        final BerichtStufTransformatieResultaat berichtStufTransformatieResultaat = new BerichtStufTransformatieResultaat(
                resultaat.getStufVertalingen().stream().map(StufBerichtBerichtFactoryImpl::mapVertaling).collect(Collectors.toList()));
        return new StufAntwoordBericht(basisBerichtGegevens, berichtStufTransformatieResultaat );
        //@formatter:on
    }

    private static BerichtStufVertaling mapVertaling(StufTransformatieVertaling stufTransformatieVertaling) {
        return new BerichtStufVertaling(stufTransformatieVertaling.getVertaling(), stufTransformatieVertaling.getCommunicatieId());
    }
}
