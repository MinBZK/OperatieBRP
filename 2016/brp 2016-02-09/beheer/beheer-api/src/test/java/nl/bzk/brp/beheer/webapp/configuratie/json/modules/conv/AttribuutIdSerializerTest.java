/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules.conv;

import nl.bzk.brp.beheer.webapp.configuratie.json.modules.ObjectUtils;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AttribuutIdSerializerTest {

    @Test
    public void test() {
        final AanduidingInhoudingVermissingReisdocument aanduiding = new AanduidingInhoudingVermissingReisdocument(null, null);
        ReflectionTestUtils.setField(aanduiding, "iD", (short) 42);
        final AanduidingInhoudingVermissingReisdocumentAttribuut value = new AanduidingInhoudingVermissingReisdocumentAttribuut(aanduiding);

        System.out.println("value: " + value);
        System.out.println("value.waarde: " + value.getWaarde());

        System.out.println("value, waarde: " + value + " -> " + ObjectUtils.getWaarde(value, "waarde"));
        System.out.println("value, waarde.iD: " + value + " -> " + ObjectUtils.getWaarde(value, "waarde.iD"));

    }
}
