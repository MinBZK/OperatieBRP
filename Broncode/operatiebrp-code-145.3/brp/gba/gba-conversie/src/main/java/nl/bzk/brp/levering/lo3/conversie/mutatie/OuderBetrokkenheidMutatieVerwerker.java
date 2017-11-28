/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.conversie.mutatie.OuderBetrokkenheidMutatieVerwerker.BrpBetrokkenheidInhoud;
import nl.bzk.brp.levering.lo3.mapper.AbstractMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpGroepConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * Betrokkenheid mutatie verwerker.
 */
@Component
public class OuderBetrokkenheidMutatieVerwerker extends AbstractFormeelMutatieVerwerker<Lo3OuderInhoud, BrpBetrokkenheidInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected OuderBetrokkenheidMutatieVerwerker(final BetrokkenheidMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new BetrokkenheidConverteerder(attribuutConverteerder), attribuutConverteerder, null, BetrokkenheidMapper.GROEP_ELEMENT, LOGGER);
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
            // Betrokkenheid inhoud wordt niet gevalideerd
        }
    }

    /**
     * Betrokkenheid mapper (no-op).
     */
    @Component
    public static final class BetrokkenheidMapper extends AbstractMapper<BrpBetrokkenheidInhoud> {

        /**
         * Groep element.
         */
        public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId());

        /**
         * Constructor.
         */
        protected BetrokkenheidMapper() {
            super(GROEP_ELEMENT,
                    GROEP_ELEMENT,
                    null,
                    null,
                    ElementHelper.getAttribuutElement(Element.BETROKKENHEID_TIJDSTIPREGISTRATIE.getId()),
                    ElementHelper.getAttribuutElement(Element.BETROKKENHEID_TIJDSTIPVERVAL.getId()));
        }

        @Override
        protected BrpBetrokkenheidInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord historie, final OnderzoekMapper onderzoekMapper) {
            return new BrpBetrokkenheidInhoud();
        }
    }

    /**
     * Betrokkenheid converteerder (no-op).
     */
    @Component
    public static final class BetrokkenheidConverteerder extends AbstractBrpGroepConverteerder<BrpBetrokkenheidInhoud, Lo3OuderInhoud> {

        private static final Logger LOGGER = LoggerFactory.getLogger();

        @Inject
        BetrokkenheidConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

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
