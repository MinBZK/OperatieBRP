/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BerichtVrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtParameters;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.springframework.stereotype.Component;

/**
 * VrijBerichtBerichtFactoryImpl.
 */
@Component
final class VrijBerichtBerichtFactoryImpl implements VrijBerichtBerichtFactory {

    @Inject
    private BeheerRepository beheerRepository;

    private VrijBerichtBerichtFactoryImpl() {
    }

    @Override
    public VrijBerichtAntwoordBericht maakAntwoordBericht(final VrijBerichtResultaat resultaat) {
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
        //@formatter:on
        return new VrijBerichtAntwoordBericht(basisBerichtGegevens);
    }

    @Override
    public VrijBerichtVerwerkBericht maakVerwerkBericht(final VrijBerichtResultaat resultaat, final Partij ontvangendePartij, final Partij zenderVrijBericht) {
        final VrijBericht vrijBericht = new VrijBericht(
                resultaat.getVerzoek().getVrijBericht().getInhoud(),
                beheerRepository.haalSoortVrijBerichtOp(resultaat.getVerzoek().getVrijBericht().getSoortNaam())
        );
        final BerichtVrijBericht berichtVrijBericht = new BerichtVrijBericht(vrijBericht);
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metZendendePartij(resultaat.getBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
                .metTijdstipVerzending(resultaat.getTijdstipVerzending())
            .eindeStuurgegevens()
            .build();
         //@formatter:on
        return new VrijBerichtVerwerkBericht(basisBerichtGegevens, berichtVrijBericht, new VrijBerichtParameters(zenderVrijBericht, ontvangendePartij));
    }
}
