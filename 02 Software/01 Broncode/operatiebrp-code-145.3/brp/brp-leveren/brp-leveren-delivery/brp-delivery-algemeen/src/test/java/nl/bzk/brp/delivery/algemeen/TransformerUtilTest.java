/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import javax.xml.transform.Source;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class TransformerUtilTest {

    @Test
    public void initializeNode() throws Exception {
        final Source source = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("./synchronisatiebericht/verwerkpersoonbericht1.xml")
                .getInputStream()));
        final Node node = TransformerUtil.initializeNode(source);
        Assert.assertNotNull(node);
    }

}
