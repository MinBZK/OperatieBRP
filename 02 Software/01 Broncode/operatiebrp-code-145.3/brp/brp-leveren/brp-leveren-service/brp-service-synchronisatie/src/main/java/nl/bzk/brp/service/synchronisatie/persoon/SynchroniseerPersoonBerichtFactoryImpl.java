/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Component;

/**
 * SynchroniseerPersoonBerichtFactoryImpl.
 */
@Component
final class SynchroniseerPersoonBerichtFactoryImpl implements SynchroniseerPersoonBerichtFactory {

    @Override
    public SynchroniseerPersoonAntwoordBericht apply(final MaakAntwoordBerichtResultaat resultaat) {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                    .metStuurgegevens()
                    .metReferentienummer(resultaat.getReferentienummerAttribuutAntwoord())
                    .metCrossReferentienummer(resultaat.getReferentieNummer())
                    .metZendendePartij(resultaat.getPartijBrpvoorziening())
                    .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                    .metTijdstipVerzending(resultaat.getDatumVerzending())
                    .eindeStuurgegevens()
                    .metMeldingen(resultaat.getMeldingList())
                    .metResultaat(BerichtVerwerkingsResultaat.builder()
                        .metVerwerking(MeldingUtil.bepaalVerwerking(resultaat.getMeldingList()))
                        .metHoogsteMeldingsniveau(resultaat.getHoogsteMeldingNiveau().getNaam())
                        .build()
                    )
        .build();
        //@formatter:on
        return new SynchroniseerPersoonAntwoordBericht(basisBerichtGegevens);
    }
}
