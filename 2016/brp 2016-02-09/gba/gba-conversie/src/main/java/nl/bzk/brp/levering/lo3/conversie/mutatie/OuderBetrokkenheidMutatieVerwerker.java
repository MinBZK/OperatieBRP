/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.conversie.mutatie.OuderBetrokkenheidMutatieVerwerker.BrpBetrokkenheidInhoud;
import nl.bzk.brp.levering.lo3.mapper.AbstractFormeelMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Betrokkenheid mutatie verwerker.
 */
@Component
public class OuderBetrokkenheidMutatieVerwerker extends AbstractFormeelMutatieVerwerker<Lo3OuderInhoud, BrpBetrokkenheidInhoud, HisBetrokkenheidModel> {
    /**
     * Constructor.
     *
     * @param mapper mapper
     * @param converteerder converteerder
     */
    @Autowired
    protected OuderBetrokkenheidMutatieVerwerker(final BetrokkenheidMapper mapper, final BetrokkenheidConverteerder converteerder) {
        super(mapper, converteerder, null, ElementEnum.GERELATEERDEOUDER_BETROKKENHEID);
    }

    /**
     * Betrokkenheid inhoud (dummy).
     */
    public static final class BrpBetrokkenheidInhoud implements BrpGroepInhoud {
        @Override
        public boolean isLeeg() {
            return false;
        }

        @Override
        public void valideer() {
        }
    }

    /**
     * Betrokkenheid mapper (no-op).
     */
    @Component
    public static final class BetrokkenheidMapper extends AbstractFormeelMapper<BetrokkenheidHisVolledig, HisBetrokkenheidModel, BrpBetrokkenheidInhoud> {

        /**
         * Constructor.
         */
        protected BetrokkenheidMapper() {
            super(ElementEnum.BETROKKENHEID_TIJDSTIPREGISTRATIE, ElementEnum.BETROKKENHEID_TIJDSTIPVERVAL);
        }

        @Override
        protected Iterable<HisBetrokkenheidModel> getHistorieIterable(final BetrokkenheidHisVolledig volledig) {
            return volledig.getBetrokkenheidHistorie();
        }

        @Override
        protected BrpBetrokkenheidInhoud mapInhoud(final HisBetrokkenheidModel historie, final OnderzoekMapper onderzoekMapper) {
            return new BrpBetrokkenheidInhoud();
        }
    }

    /**
     * Betrokkenheid converteerder (no-op).
     */
    @Component
    public static final class BetrokkenheidConverteerder extends BrpGroepConverteerder<BrpBetrokkenheidInhoud, Lo3OuderInhoud> {

        private static final Logger LOGGER = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        public Lo3OuderInhoud maakNieuweInhoud() {
            return new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
        }

        @Override
        public Lo3OuderInhoud vulInhoud(final Lo3OuderInhoud lo3Inhoud, final BrpBetrokkenheidInhoud brpInhoud, final BrpBetrokkenheidInhoud brpVorige) {
            return lo3Inhoud;
        }

    }
}
