/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.algemeen.cache.StamTabelCache;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de stam tabel service {@link nl.bzk.brp.levering.algemeen.service.StamTabelService}.
 */
@Service
public class StamTabelServiceImpl implements StamTabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private StamTabelCache stamTabelCache;

    @Override
    public final Collection<Element> geefAlleElementen() {
        return stamTabelCache.geefAlleElementen();
    }

    @Override
    public final Map<String, ParserResultaat> geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie() {
        return this.stamTabelCache.geefAlleExpressieParserResultatenVanAttributeElementen();
    }

    @Override
    public final Element geefElementById(final int id) {
        return this.stamTabelCache.geefElementById(id);
    }

}
