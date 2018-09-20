/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.text.DateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class JsonObjectMapperWithDateFormat.
 */
public class JsonObjectMapperWithDateFormat extends ObjectMapper {

    private static Logger logger = LoggerFactory.getLogger(JsonObjectMapperWithDateFormat.class);

    /**
     * Instantiates a new json object mapper with date format.
     */
    public JsonObjectMapperWithDateFormat() {
        super.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        super.configure(Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        DateFormat dateFormat = StdDateFormat.getBlueprintISO8601Format();
        setDateFormat(dateFormat);
        logger.debug("Dateformat set in JsonObjectMapperWithDateFormat: {}",
                getSerializationConfig().getDateFormat().format(new Date()));
    }

    /* (non-Javadoc)
     * @see org.codehaus.jackson.map.ObjectMapper#writer(java.text.DateFormat)
     */
    @Override
    public final ObjectWriter writer(final DateFormat df) {
        if (df == null) {
            logger.warn("dateformat empty");
        }
        return super.writer(df);
    }
}
