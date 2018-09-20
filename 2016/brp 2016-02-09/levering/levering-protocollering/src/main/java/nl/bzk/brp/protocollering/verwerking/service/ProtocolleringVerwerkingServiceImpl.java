/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.service;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.verwerking.exceptie.ProtocolleringFout;
import nl.bzk.brp.protocollering.verwerking.repository.LeveringPersoonRepository;
import nl.bzk.brp.protocollering.verwerking.repository.LeveringRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * De service die de protocollering verwerkt.
 * @brp.bedrijfsregel R1996
 */
@Regels(Regel.R1996)
@Service
@Transactional
public class ProtocolleringVerwerkingServiceImpl implements ProtocolleringVerwerkingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringPersoonRepository leveringPersoonRepository;

    @Inject
    private LeveringRepository        leveringRepository;

    @Override
    public final void slaProtocolleringOp(final ProtocolleringOpdracht protocolleringOpdracht) {
        try {
            final LeveringModel opgeslagenLevering = leveringRepository.opslaanNieuweLevering(protocolleringOpdracht.getLevering());
            protocolleerPersonen(protocolleringOpdracht.getPersonen(), opgeslagenLevering);
            LOGGER.info("Protocollering van levering {} met bijbehorende personen en communicatie geslaagd.", opgeslagenLevering.getID());
        } catch (final Exception ex) {
            LOGGER.error("Fout bij het protocolleren, {}", ex.getMessage(), ex);
            throw new ProtocolleringFout(ex);
        }
    }

    /**
     * Protocolleer personen bij een levering.
     *
     * @param leveringPersonen Een Set van leveringpersonen.
     * @param opgeslagenLevering De gepersisteerde levering.
     */
    private void protocolleerPersonen(final Set<LeveringPersoonModel> leveringPersonen, final LeveringModel opgeslagenLevering) {
        for (final LeveringPersoonModel leveringPersoon : leveringPersonen) {
            final LeveringPersoonModel nieuweLeveringPersoon = new LeveringPersoonModel(opgeslagenLevering, leveringPersoon.getPersoonId());
            LOGGER.debug("Protocolleer persoon {} voor levering {}", leveringPersoon.getPersoonId(), opgeslagenLevering.getID());
            leveringPersoonRepository.opslaanNieuweLeveringPersoon(nieuweLeveringPersoon);
        }
    }

}
