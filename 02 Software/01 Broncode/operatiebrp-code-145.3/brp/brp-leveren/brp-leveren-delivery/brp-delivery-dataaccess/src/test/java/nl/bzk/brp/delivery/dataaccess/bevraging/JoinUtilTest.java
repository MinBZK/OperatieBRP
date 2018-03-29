/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * JoinUtilTest.
 */
public class JoinUtilTest {

    @Test
    public void bepaalSoortRelatie() throws Exception {
        Assert.assertEquals(SoortRelatie.HUWELIJK, JoinUtil.isRelatie(ElementHelper.getObjectElement(Element.HUWELIJK)));
        Assert
                .assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, JoinUtil.isRelatie(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP)));
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING,
                JoinUtil.isRelatie(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING)));
    }

    @Test
    public void bepaalSoortGerelateerdeBetrokkenheid() throws Exception {
        //kind betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.KIND, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element.GERELATEERDEKIND)));
        Assert.assertEquals(SoortBetrokkenheid.KIND, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element
                .GERELATEERDEKIND_PERSOON)));

        //ouder betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.OUDER, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER)));
        Assert.assertEquals(SoortBetrokkenheid.OUDER,
                JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON)));

        //partner betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element
                .GERELATEERDEGEREGISTREERDEPARTNER)));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element
                .GERELATEERDEHUWELIJKSPARTNER)));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element
                .GERELATEERDEGEREGISTREERDEPARTNER)));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, JoinUtil.getGerelateerdeBetrokkenheid(ElementHelper.getObjectElement(Element
                .GERELATEERDEHUWELIJKSPARTNER_PERSOON)));
    }

    @Test
    public void bepaalSoortBetrokkenheid() throws Exception {
        //kind betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.KIND, JoinUtil.getBetrokkenheid(ElementHelper.getObjectElement(Element.PERSOON_KIND)));

        //ouder betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.OUDER, JoinUtil.getBetrokkenheid(ElementHelper.getObjectElement(Element.PERSOON_OUDER)));

        //partner betrokkenheid
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, JoinUtil.getBetrokkenheid(ElementHelper.getObjectElement(Element.PERSOON_PARTNER)));
    }

}
