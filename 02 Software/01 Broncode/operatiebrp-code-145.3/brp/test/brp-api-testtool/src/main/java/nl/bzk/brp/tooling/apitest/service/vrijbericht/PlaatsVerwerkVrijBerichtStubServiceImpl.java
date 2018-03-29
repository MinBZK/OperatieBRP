/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.vrijbericht;

import java.io.StringReader;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.vrijbericht.VrijBerichtWebService;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.vrijbericht.PlaatsVerwerkVrijBerichtService;
import org.springframework.stereotype.Service;

/**
 */
@Service
final class PlaatsVerwerkVrijBerichtStubServiceImpl implements PlaatsVerwerkVrijBerichtService, VrijBerichtStubService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SchemaValidatorService schemaValidatorService;

    private VrijBerichtGegevens vrijBerichtGegevens;

    @Override
    public void plaatsVrijBericht(final VrijBerichtGegevens vrijBerichtGegevens) {
        this.vrijBerichtGegevens = vrijBerichtGegevens;

        controleerBericht(vrijBerichtGegevens);
    }

    private void controleerBericht(final VrijBerichtGegevens vrijBerichtGegevens) {
        final String bericht = vrijBerichtGegevens.getArchiveringOpdracht().getData();
        try {
            schemaValidatorService.valideer(new StreamSource(new StringReader(bericht)), VrijBerichtWebService.SCHEMA);
        } catch (SchemaValidatorService.SchemaValidatieException se) {
            LOGGER.error("Bericht niet XSD valide:\n{}", bericht);
            throw new BrpServiceRuntimeException(se);
        }
    }

    @Override
    public boolean heeftVrijBerichtOntvangen() {
        return vrijBerichtGegevens != null;
    }

    @Override
    public VrijBerichtGegevens geefVrijBericht() {
        return vrijBerichtGegevens;
    }

    /**
     * reset de state
     */
    @Override
    public void reset() {
        vrijBerichtGegevens = null;
    }
}
