/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtAfnemerindicatie;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Component;

/**
 * OnderhoudAfnemerindicatieSynchroonBerichtFactoryImpl.
 */
@Component
final class OnderhoudAfnemerindicatieSynchroonBerichtFactoryImpl implements OnderhoudAfnemerindicatieSynchroonBerichtFactory {

    @Override
    public OnderhoudAfnemerindicatieAntwoordBericht maakAntwoordBericht(final OnderhoudResultaat resultaat) {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                .metReferentienummer(resultaat.getReferentienummerAntwoordbericht())
                .metCrossReferentienummer(resultaat.getVerzoek().getStuurgegevens().getReferentieNummer())
                .metZendendePartij(resultaat.getBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metTijdstipVerzending(resultaat.getTijdstipVerzending())
            .eindeStuurgegevens()
            .metMeldingen(resultaat.getMeldingList())
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                .metVerwerking(MeldingUtil.bepaalVerwerking(resultaat.getMeldingList()))
                .metHoogsteMeldingsniveau(MeldingUtil.bepaalHoogsteMeldingNiveau(resultaat.getMeldingList()).getNaam())
                .build()
            ).build();

        final BerichtAfnemerindicatie berichtAfnemerindicatie = BerichtAfnemerindicatie.builder()
                .metBsn(resultaat.getVerzoek().getAfnemerindicatie().getBsn())
                .metPartijCode(resultaat.getVerzoek().getAfnemerindicatie().getPartijCode())
                .metTijdstipRegistratie(resultaat.getTijdstipRegistratie())
                .build();
        //@formatter:on
        return new OnderhoudAfnemerindicatieAntwoordBericht(basisBerichtGegevens, berichtAfnemerindicatie);
    }
}
