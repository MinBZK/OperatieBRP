/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.springframework.stereotype.Service;

/**
 * DatumService implementatie.
 */
@Service
@Bedrijfsregel(Regel.R1274)
final class DatumServiceImpl implements DatumService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public LocalDate parseDate(final String dateString) throws StapMeldingException {
        try {
            return DatumFormatterUtil.vanXsdDatumNaarLocalDate(dateString);
        } catch (final DateTimeParseException pe) {
            LOGGER.warn("Datum kan niet geparsed worden: {}", dateString);
            throw new StapMeldingException(Regel.R1274, pe);
        }
    }

    @Override
    public ZonedDateTime parseDateTime(final String datumTijdString) throws StapMeldingException {
        try {
            return DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime(datumTijdString);
        } catch (final DateTimeParseException dpe) {
            LOGGER.warn("Datumtijd kan niet geparsed worden: {}", datumTijdString);
            throw new StapMeldingException(Regel.R1274, dpe);
        }
    }
}
