/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.util.StdDateFormat;


/**
 * De Class JsonCalendarSerializer.
 */
public class JsonCalendarSerializer extends JsonSerializer<Calendar> {

    /*
     * (non-Javadoc)
     *
     * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator,
     * org.codehaus.jackson.map.SerializerProvider)
     */
    @Override
    public void serialize(final Calendar value, final JsonGenerator jgen, final SerializerProvider provider)
        throws IOException
    {
        DateFormat dateFormat = StdDateFormat.getBlueprintISO8601Format();
        // Must create a clone since Formats are not thread-safe:
        DateFormat df = (DateFormat) dateFormat.clone();
        jgen.writeStartObject();
        jgen.writeStringField("type", "Date");
        jgen.writeFieldName("value");
        // If standard date format is ok, use this
        // provider.defaultSerializeDateValue(value.getTime(), jgen);
        jgen.writeString(df.format(value.getTime()));
        // (if not, just use whatever converter you want, output result using JsonGenerator)
        jgen.writeEndObject();
    }

}
