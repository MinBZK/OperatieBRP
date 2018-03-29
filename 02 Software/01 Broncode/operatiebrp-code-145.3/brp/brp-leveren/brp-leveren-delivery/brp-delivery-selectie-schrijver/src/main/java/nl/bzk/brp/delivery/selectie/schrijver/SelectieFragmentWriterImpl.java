/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import nl.bzk.brp.service.selectie.schrijver.SelectieFragmentWriter;
import org.springframework.stereotype.Component;

/**
 * Schrijft een reeks XML berichten welke behoren tot een selectietaak naar bestand (een part).
 * Concat eventueel deze parts tot een groter geheel (fragment) om zodoende het aantal files te beperken.
 * <p>
 * Deze writer is impliciet threadsafe omdat het deel uit maakt van een JMS messagegroup.
 * Alle writes mbt een gegeven selectietaak zijn serieel.
 */
@Component
public final class SelectieFragmentWriterImpl implements SelectieFragmentWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    private SelectieFileService selectieFileService;

    private SelectieFragmentWriterImpl() {
    }

    @Override
    public void verwerk(SelectieFragmentSchrijfBericht selectieBerichtSchrijfTaak) throws SelectieException {
        LOGGER.debug("Start schrijf fragmenten weg voor batch voor dienst [{}] en berichten aantal [{}]",
                selectieBerichtSchrijfTaak.getDienstId(),
                selectieBerichtSchrijfTaak.getBerichten().size());
        if (selectieBerichtSchrijfTaak.getBerichten().stream().filter(Objects::nonNull).count() == 0) {
            LOGGER.debug("Geen berichten voor taak [{}]", selectieBerichtSchrijfTaak.getSelectietaakId());
            return;
        }
        //dit doen we nu nog te vaak
        selectieFileService.initSchrijfOpslag(selectieBerichtSchrijfTaak);
        try {
            LOGGER.debug("Start schrijf part");
            LOGGER.debug("Start encode fragmenten");
            final List<byte[]> berichtenEncoded = encode(selectieBerichtSchrijfTaak);
            LOGGER.debug("Einde encode fragmenten");
            selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieBerichtSchrijfTaak);
            LOGGER.debug("Einde schrijf part");
            LOGGER.debug("Start concat parts tot fragment");
            selectieFileService.concatDeelFragmenten(selectieBerichtSchrijfTaak);
            LOGGER.debug("Einde concat parts tot fragment");
            LOGGER.debug("Einde schrijf part voor dienst: " + selectieBerichtSchrijfTaak.getDienstId());
            if (!selectieBerichtSchrijfTaak.getProtocolleringPersonen().isEmpty()) {
                final List<LeveringPersoon>
                        leveringPersoonList =
                        Lists.newArrayListWithExpectedSize(selectieBerichtSchrijfTaak.getProtocolleringPersonen().size());
                for (Map.Entry<Long, ZonedDateTime> entry : selectieBerichtSchrijfTaak.getProtocolleringPersonen().entrySet()) {
                    final LeveringPersoon leveringPersoon = new LeveringPersoon(entry.getKey(), entry.getValue());
                    leveringPersoonList.add(leveringPersoon);
                }
                selectieFileService.schijfProtocolleringPersonen(selectieBerichtSchrijfTaak,
                        leveringPersoonList.stream().map(serializer::serialiseerNaarString).collect(Collectors.toList()));
            }
            LOGGER.debug("Einde schrijf protocollering");

        } catch (IOException e) {
            throw new SelectieException(e);
        }
    }

    private List<byte[]> encode(SelectieFragmentSchrijfBericht berichtSchrijfTaak) {
        final List<byte[]> berichtenEncoded = new ArrayList<>();
        for (String berichtStr : berichtSchrijfTaak.getBerichten()) {
            berichtenEncoded.add(Base64.getEncoder().encode(berichtStr.getBytes(StandardCharsets.UTF_8)));
        }
        return berichtenEncoded;
    }
}
