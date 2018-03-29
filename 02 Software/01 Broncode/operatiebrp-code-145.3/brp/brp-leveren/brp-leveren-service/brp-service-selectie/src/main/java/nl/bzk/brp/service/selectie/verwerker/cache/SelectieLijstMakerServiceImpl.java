/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import com.google.common.collect.Sets;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import org.springframework.stereotype.Service;

/**
 * SelectieLijstMakerServiceImpl.
 */
@Service
public final class SelectieLijstMakerServiceImpl implements SelectieLijstMakerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String SELECTIE_BESTAND_NAAM = "selectielijst.csv";
    private static final String SELECTIELIJST_ANR_HEADER = "administratienummer";
    private static final String SELECTIELIJST_BSN_HEADER = "burgerservicenummer";
    private static final Set<String> GELDIGE_SELECTIELIJST_HEADERS =
            Sets.newHashSet(SELECTIELIJST_ANR_HEADER, SELECTIELIJST_BSN_HEADER);

    @Inject
    private ConfiguratieService configuratieService;

    private SelectieLijstMakerServiceImpl() {
    }

    @Override
    public SelectieLijst maak(final Integer dienstId, final Integer selectieTaakId) {
        LOGGER.info("Start laden selectiebestanden voor selectietaak : " + selectieTaakId);
        final Path
                selectiebestandFolder =
                Paths.get(configuratieService.getSelectiebestandFolder(), String.valueOf(dienstId), String.valueOf(selectieTaakId), SELECTIE_BESTAND_NAAM);
        final SelectieLijst selectieLijst = leesSelectiebestand(selectiebestandFolder, dienstId, selectieTaakId);
        LOGGER.info("Einde laden selectiebestand voor dienstId={}, selectietaakId={}, header={}, aantalWaarden={} ", dienstId, selectieTaakId,
                selectieLijst.getWaardeType(),
                selectieLijst.getWaarden().size());
        return selectieLijst;
    }

    private SelectieLijst leesSelectiebestand(final Path path, final Integer dienstId, final Integer selectieTaakId) {
        if (path.toFile().exists()) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                final AttribuutElement soortIdentificatieNr = leesHeader(reader.readLine(), selectieTaakId);
                String line;
                Set<String> identificatieNrSet = new HashSet<>();
                while ((line = reader.readLine()) != null) {
                    identificatieNrSet.add(line.trim());
                }
                return new SelectieLijst(dienstId, soortIdentificatieNr, identificatieNrSet);
            } catch (IOException e) {
                LOGGER.error("fout bij lezen selectie lijst bestand", e);
            }
        }
        LOGGER.info("Selectiebestand bestaat niet: {}", path);
        return SelectieLijst.GEEN_LIJST;
    }


    private AttribuutElement leesHeader(final String header, final Integer selectieTaakId) throws IOException {
        if (GELDIGE_SELECTIELIJST_HEADERS.contains(header.toLowerCase())) {
            return SELECTIELIJST_ANR_HEADER.equalsIgnoreCase(header) ?
                    ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER) :
                    ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        }
        throw new IOException(String.format("Selectielijst voor dienst %d wordt niet geevalueerd, geen geldige header : %s", selectieTaakId, header));
    }
}
