/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.Collections;
import nl.bzk.brp.beheer.webapp.configuratie.jpa.CustomPageImpl;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CustomPageSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void test() throws IOException {
        final Pageable pageable = new PageRequest(2, 20);
        final CustomPageImpl<?> page = new CustomPageImpl<Object>(Collections.emptyList(), pageable, 10000, "Blaat");
        Assert.assertNotNull(page.getWarning());
        Assert.assertEquals(
            "{\"first\":false,\"hasNext\":true,\"hasPrevious\":true,\"last\":false,\"number\":2,\"numberOfElements\":0,\"size\":20,\"totalElements\":10000,\"totalPages\":500,\"warning\":\"Blaat\"}",
            write(page));
    }

    private Object write(final CustomPageImpl<?> object) throws JsonProcessingException {
        final ObjectWriter writer = subject.writer();
        return writer.writeValueAsString(object);
    }
}
