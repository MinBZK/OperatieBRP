/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import org.springframework.stereotype.Service;

/**
 * De implementatie van {@link Verzending.ProtocolleringService}.
 */
@Service
final class ProtocolleringServiceVerzendingImpl implements Verzending.ProtocolleringService {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ProtocolleringService protocolleringService;

    private ProtocolleringServiceVerzendingImpl() {
    }

    @Override
    public void verwerkProtocollering(final SynchronisatieBerichtGegevens berichtGegevens) {
        final Protocolleringsniveau protocolleringNiveau = berichtGegevens.getProtocolleringsniveau() == null
                ? Protocolleringsniveau.GEEN_BEPERKINGEN : berichtGegevens.getProtocolleringsniveau();
        final ProtocolleringData protocolleringData = new ProtocolleringData(protocolleringNiveau, berichtGegevens
                .getArchiveringOpdracht().getLeveringsAutorisatieId().toString(),
                String.valueOf(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));

        LOGGER.debug("Procotolleer verzending");
        try (MDC.MDCCloser closer = MDC.putData(zetMDCMDCVeld(protocolleringData, berichtGegevens))) {
            if (erMoetGeprotocolleerdWorden(protocolleringData.getProtocolleringNiveau(), berichtGegevens)) {
                protocolleerLevering(berichtGegevens);
                LOGGER.debug("Protocolleer de verzending van bericht referentienummer {}", berichtGegevens.getArchiveringOpdracht().getReferentienummer());
            } else {
                LOGGER.info("Het bericht met referentienummer {} zal niet geprotocolleerd worden.",
                        berichtGegevens.getArchiveringOpdracht().getReferentienummer());
            }
        } finally {
            verwijderMDCVelden();
        }
    }

    private boolean erMoetGeprotocolleerdWorden(final Protocolleringsniveau protocolleringNiveau,
                                                final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens) {
        return synchronisatieBerichtGegevens.getArchiveringOpdracht().getRolId() == Rol.AFNEMER.getId() && (protocolleringNiveau == null
                || !Protocolleringsniveau.GEHEIM
                .equals(protocolleringNiveau));
    }

    /**
     * Doe de protocollering.
     * @param berichtGegevens De berichtgegevens/metadata
     */
    private void protocolleerLevering(final SynchronisatieBerichtGegevens berichtGegevens) {
        final ProtocolleringOpdracht protocolleringOpdracht = berichtGegevens.getProtocolleringOpdracht();

        if (berichtGegevens.getSoortDienst() != null) {
            protocolleringOpdracht.setSoortDienst(berichtGegevens.getSoortDienst());
        }

        protocolleringService.protocolleer(protocolleringOpdracht);
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     * @param protocolleringData de protocolleringdata
     * @param synchronisatieBerichtGegevens synchronisatieberichtgegevens
     */
    private Map<String, String> zetMDCMDCVeld(final ProtocolleringData protocolleringData, final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens) {
        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), protocolleringData.getLeveringsautorisatieId());
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), protocolleringData.getPartijCode());
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(),
                String.valueOf(synchronisatieBerichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId()));
        return mdcMap;
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(LeveringVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(LeveringVeld.MDC_PARTIJ_ID);
        MDC.remove(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }

}
