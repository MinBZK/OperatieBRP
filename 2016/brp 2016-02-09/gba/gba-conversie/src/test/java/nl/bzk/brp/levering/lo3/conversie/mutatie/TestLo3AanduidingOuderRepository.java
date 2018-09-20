/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.gba.dataaccess.Lo3AanduidingOuderRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;

public class TestLo3AanduidingOuderRepository implements Lo3AanduidingOuderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<Integer, LO3SoortAanduidingOuder> aanduidingen = new HashMap<>();

    @Override
    public LO3SoortAanduidingOuder getOuderIdentificatie(final Integer betrokkenheidId) {
        final LO3SoortAanduidingOuder resultaat = aanduidingen.get(betrokkenheidId);
        LOGGER.debug("getOuderIdentificatie(betrokkenheidId={}) -> {}", betrokkenheidId, resultaat);
        return resultaat;
    }

    @Override
    public void setAanduidingOuderBijOuderBetrokkenheid(final Integer betrokkenheidId, final LO3SoortAanduidingOuder aanduiding) {
        aanduidingen.put(betrokkenheidId, aanduiding);
    }

    public void reset() {
        aanduidingen.clear();
    }

}
