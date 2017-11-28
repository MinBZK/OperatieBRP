/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Service voor het parsen en controleren van datums.
 */
public interface DatumService {

    /**
     * Parsed de datum String. Datum moet voldoen aan het DateTimeFormatter.ISO_DATE pattern. Daarnaat moet de datum geldig zijn in de Gregoriaanse
     * kalender.
     * @param dateString datum string,
     * @return een LocalDate
     * @throws StapMeldingException indien de datum niet geparsed kan worden of niet voorkomt in de gregoriaanse kalender.
     */
    LocalDate parseDate(final String dateString) throws StapMeldingException;

    /**
     * Parsed de datumtijd String. Datum moet voldoen aan het DateTimeFormatter.ISO_DATE_TIME pattern. Daarnaat moet de datum geldig zijn in de
     * Gregoriaanse kalender.
     * @param dateString datumtijd string,
     * @return een ZonedDateTime
     * @throws StapMeldingException indien de datum niet geparsed kan worden of niet voorkomt in de gregoriaanse kalender.
     */
    ZonedDateTime parseDateTime(final String dateString) throws StapMeldingException;
}
