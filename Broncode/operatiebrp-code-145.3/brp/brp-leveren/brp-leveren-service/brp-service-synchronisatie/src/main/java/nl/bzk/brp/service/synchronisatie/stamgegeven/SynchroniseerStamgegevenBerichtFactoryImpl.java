/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import java.time.ZonedDateTime;
import java.util.UUID;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStamgegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Component;

/**
 * SynchroniseerPersoonBerichtFactoryImpl.
 */
@Component
public final class SynchroniseerStamgegevenBerichtFactoryImpl implements SynchroniseerStamgegevenBerichtFactory {

    @Override
    public SynchroniseerStamgegevenBericht maakBericht(final BepaalStamgegevenResultaat resultaat) {
        final ZonedDateTime datumVerzending = DatumUtil.nuAlsZonedDateTime();
        final String referentienummerAttribuutAntwoord = UUID.randomUUID().toString();

        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                        .metStuurgegevens()
                        .metZendendePartij(resultaat.getBrpPartij())
                        .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                        .metReferentienummer(referentienummerAttribuutAntwoord)
                        .metCrossReferentienummer(resultaat.getVerzoek().getStuurgegevens().getReferentieNummer())
                        .metTijdstipVerzending(datumVerzending)
                        .eindeStuurgegevens()
                        .metMeldingen(resultaat.getMeldingList())
                        .metResultaat(BerichtVerwerkingsResultaat.builder()
                                .metHoogsteMeldingsniveau(MeldingUtil.bepaalHoogsteMeldingNiveau(resultaat.getMeldingList()).getNaam())
                                .metVerwerking(MeldingUtil.bepaalVerwerking(resultaat.getMeldingList()))
                                .build()
                        ).build();
        return new SynchroniseerStamgegevenBericht(basisBerichtGegevens, new BerichtStamgegevens(resultaat.getStamgegevens()));
        //@formatter:on
    }
}
