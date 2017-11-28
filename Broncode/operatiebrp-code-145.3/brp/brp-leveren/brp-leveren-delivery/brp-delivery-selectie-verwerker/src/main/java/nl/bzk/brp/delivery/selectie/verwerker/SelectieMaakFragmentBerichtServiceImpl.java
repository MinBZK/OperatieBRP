/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.verwerker;

import java.io.StringWriter;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtMeldingWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtPersoonslijstWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.selectie.verwerker.SelectieMaakFragmentBerichtService;
import org.springframework.stereotype.Service;

/**
 * SelectieMaakBerichtServiceImpl.
 */
@Service
public final class SelectieMaakFragmentBerichtServiceImpl implements SelectieMaakFragmentBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private SelectieMaakFragmentBerichtServiceImpl() {
    }

    @Override
    public String maakPersoonFragment(final BijgehoudenPersoon bijgehoudenPersoon, List<Melding> meldingen) {
        try {
            return write(bijgehoudenPersoon, meldingen);
        } catch (BerichtException e) {
            LOGGER.error("fout bij maken xml bericht");
            throw new BrpServiceRuntimeException(e);
        }
    }

    /**
     * Schrijft xml fragment voor {@link BijgehoudenPersoon}.
     * @param bijgehoudenPersoon bijgehouden persoon
     * @param meldingen meldingen
     * @return xml fragment
     */
    private static String write(final BijgehoudenPersoon bijgehoudenPersoon, List<Melding> meldingen) {
        final StringWriter outputWriter = new StringWriter();
        final BerichtWriter writer = new BerichtWriter(outputWriter);
        try {
            writer.startElement("geselecteerdePersoon");
            BerichtMeldingWriter.write(meldingen, writer);
            BerichtPersoonslijstWriter.write(bijgehoudenPersoon.getBerichtElement(), writer);
            writer.endElement();
            writer.flush();
            return outputWriter.toString();
        } finally {
            writer.close();
        }
    }
}
