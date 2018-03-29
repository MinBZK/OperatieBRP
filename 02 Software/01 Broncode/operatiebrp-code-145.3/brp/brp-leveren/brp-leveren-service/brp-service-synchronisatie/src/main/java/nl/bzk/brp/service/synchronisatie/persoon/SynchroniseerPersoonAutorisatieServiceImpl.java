/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.springframework.stereotype.Component;

/**
 * Autorisatieservice voor synchroniseer persoon. Voert naast generieke leveringautorisatie nog dienst-specifieke autorisatie uit : De afnemer moet naast de
 * dienst Synchronisatie Persoon ook een leveringsautorisatie hebben voor de dienst 'Mutatielevering op basis van doelbinding' of
 * 'Mutatielevering op basis van afnemerindicatie'.
 */
@Bedrijfsregel(Regel.R1347)
@Component
final class SynchroniseerPersoonAutorisatieServiceImpl implements SynchroniseerPersoonAutorisatieService {

    @Inject
    private LeveringsautorisatieValidatieService leveringsautorisatieService;

    private SynchroniseerPersoonAutorisatieServiceImpl() {
    }

    @Override
    public Autorisatiebundel controleerAutorisatie(final SynchronisatieVerzoek verzoek) throws AutorisatieException {
        final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
        final int leveringautorisatieId = Integer.parseInt(verzoek.getParameters().getLeveringsAutorisatieId());
        final AutorisatieParams autorisatieParams = AutorisatieParams.maakBuilder()
                .metZendendePartijCode(zendendePartijCode)
                .metLeveringautorisatieId(leveringautorisatieId)
                .metOIN(verzoek.getOin())
                .metSoortDienst(verzoek.getSoortDienst())
                .metVerzoekViaKoppelvlak(verzoek.isBrpKoppelvlakVerzoek())
                .build();

        final Autorisatiebundel autorisatiebundel = this.leveringsautorisatieService.controleerAutorisatie(autorisatieParams);
        //dienst specifieke autorisatie
        controleerAutorisatieVoorMutatielevering(autorisatiebundel);
        return autorisatiebundel;
    }

    private void controleerAutorisatieVoorMutatielevering(final Autorisatiebundel autorisatiebundel) throws AutorisatieException {
        final Leveringsautorisatie leveringsautorisatie = autorisatiebundel.getLeveringsautorisatie();
        final Collection<Dienst> diensten = AutAutUtil.zoekDiensten(leveringsautorisatie, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
                SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        // verwijder geblokkeerde en ongeldige diensten
        diensten.removeIf(dienst -> !AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, BrpNu.get().alsIntegerDatumNederland()));
        if (diensten.isEmpty()) {
            throw new AutorisatieException(new Melding(Regel.R1347));
        }
    }
}
