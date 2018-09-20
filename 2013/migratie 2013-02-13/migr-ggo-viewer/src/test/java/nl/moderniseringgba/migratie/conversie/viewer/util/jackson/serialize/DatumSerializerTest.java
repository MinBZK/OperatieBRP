/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.TokenBuffer;

public class DatumSerializerTest {
    private static int TESTDATUM = 19800925;
    private static long TESTDATUM2 = 19800925000000L;
    private static long TESTDATUMTIJD = 19800925014533L;

    private final Lo3DatumSerializer lo3DatumSerializer = new Lo3DatumSerializer();
    private final Lo3DatumtijdstempelSerializer lo3DatumTijdSerializer = new Lo3DatumtijdstempelSerializer();
    private final BrpDatumSerializer brpDatumSerializer = new BrpDatumSerializer();
    private final BrpDatumTijdSerializer brpDatumTijdSerializer = new BrpDatumTijdSerializer();

    private String result;

    private final JsonGenerator jgen = new TokenBuffer(null) {
        @Override
        public void writeString(final String text) throws IOException, JsonGenerationException {
            result = text;
        };
    };

    @Test
    public void testDatumSerializers() throws JsonProcessingException, IOException {
        lo3DatumSerializer.serialize(Lo3Datum.NULL_DATUM, jgen, null);
        assertEquals("00000000", result);
        brpDatumSerializer.serialize(BrpDatum.ONBEKEND, jgen, null);
        assertEquals("00000000", result);
        lo3DatumTijdSerializer.serialize(new Lo3Datumtijdstempel(0L), jgen, null);
        assertEquals("00000000000000", result);
        brpDatumTijdSerializer.serialize(BrpDatumTijd.fromDatumTijd(0L), jgen, null);
        assertEquals("00000000000000", result);

        lo3DatumSerializer.serialize(new Lo3Datum(TESTDATUM), jgen, null);
        assertEquals("19800925", result);
        brpDatumSerializer.serialize(new BrpDatum(TESTDATUM), jgen, null);
        assertEquals("19800925", result);
        lo3DatumTijdSerializer.serialize(new Lo3Datumtijdstempel(TESTDATUM), jgen, null);
        assertEquals("19800925", result);
        brpDatumTijdSerializer.serialize(BrpDatumTijd.fromDatumTijd(TESTDATUM), jgen, null);
        assertEquals("19800925", result);

        lo3DatumTijdSerializer.serialize(new Lo3Datumtijdstempel(TESTDATUM2), jgen, null);
        assertEquals("19800925000000", result);
        brpDatumTijdSerializer.serialize(BrpDatumTijd.fromDatumTijd(TESTDATUM2), jgen, null);
        assertEquals("19800925000000", result);

        lo3DatumTijdSerializer.serialize(new Lo3Datumtijdstempel(TESTDATUMTIJD), jgen, null);
        assertEquals("19800925014533", result);
        brpDatumTijdSerializer.serialize(BrpDatumTijd.fromDatumTijd(TESTDATUMTIJD), jgen, null);
        assertEquals("19800925014533", result);
    }
}
