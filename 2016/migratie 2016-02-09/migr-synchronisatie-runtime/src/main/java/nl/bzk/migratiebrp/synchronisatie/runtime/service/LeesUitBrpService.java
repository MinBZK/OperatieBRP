/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deze service biedt functionaliteit voor het lezen van persoonslijsten uit de BRP.
 */
@Service
public final class LeesUitBrpService implements SynchronisatieBerichtService<LeesUitBrpVerzoekBericht, LeesUitBrpAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;

    @Inject
    private BrpDalService brpDalService;

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<LeesUitBrpVerzoekBericht> getVerzoekType() {
        return LeesUitBrpVerzoekBericht.class;
    }

    /**
     * Zoekt o.b.v. het a-nummer in het LeesUitBrpVerzoek bericht de persoon op in de BRP. Deze wordt vervolgens
     * geconverteerd naar een LO3 persoonslijst en geretourneerd in het antwoordbericht.
     * 
     * @param leesUitBrpVerzoekBericht
     *            het lees verzoek met daarin het a-nummer
     * @return het antwoordbericht met daarin de LO3 persoonslijst
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
    public LeesUitBrpAntwoordBericht verwerkBericht(final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht) {
        LOG.debug("Query BRP (anummer={}, technischesleutel={}) ...", new Object[] {leesUitBrpVerzoekBericht.getANummer(),
                                                                                    leesUitBrpVerzoekBericht.getTechnischeSleutel(), });
        final LeesUitBrpAntwoordBericht result;
        final BrpPersoonslijst brpPersoonslijst;
        if (leesUitBrpVerzoekBericht.getANummer() != null) {
            brpPersoonslijst = brpDalService.bevraagPersoonslijst(leesUitBrpVerzoekBericht.getANummer());

        } else if (leesUitBrpVerzoekBericht.getTechnischeSleutel() != null) {
            brpPersoonslijst =
                    brpDalService.bevraagPersoonslijstOpTechnischeSleutel(Long.parseLong(leesUitBrpVerzoekBericht.getTechnischeSleutel()));
        } else {
            throw new IllegalArgumentException("Lees uit BRP verzoek moet a-nummer of technische sleutel bevatten.");
        }

        final AntwoordFormaatType antwoordFormaat = leesUitBrpVerzoekBericht.getAntwoordFormaat();
        switch (antwoordFormaat) {
            case BRP:
                result = new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), brpPersoonslijst);
                break;
            default:
                // LO_3 en LO_3_XML
                result =
                        new LeesUitBrpAntwoordBericht(
                            leesUitBrpVerzoekBericht.getMessageId(),
                            converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst),
                            antwoordFormaat);
                break;
        }
        result.setMessageId(MessageId.generateSyncMessageId());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }
}
