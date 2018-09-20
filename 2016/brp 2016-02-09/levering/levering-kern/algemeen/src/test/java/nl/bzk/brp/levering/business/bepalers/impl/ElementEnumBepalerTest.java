/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.impl.kern.ErkennerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ErkenningOngeborenVruchtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.InstemmerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.NaamgeverHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.NaamskeuzeOngeborenVruchtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ElementEnumBepalerTest {

    private ElementEnumBepaler elementEnumBepaler = new ElementEnumBepaler();

    @Test
    public void dientElementEnumVoorPersonenTeBepalen() {
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(new OuderHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEOUDER_PERSOON));
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(new KindHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEKIND_PERSOON));
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(new ErkennerHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEERKENNER_PERSOON));
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(new InstemmerHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEINSTEMMER_PERSOON));
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(new NaamgeverHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDENAAMGEVER_PERSOON));

        PartnerHisVolledig partner = new PartnerHisVolledigImpl(new HuwelijkHisVolledigImpl(), null);
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(partner), is(ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON));

        partner = new PartnerHisVolledigImpl(new GeregistreerdPartnerschapHisVolledigImpl(), null);
        assertThat(elementEnumBepaler.bepaalBetrokkenPersoonObjectType(partner), is(ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON));
    }

    @Test
    public void dientElementEnumVoorBetrokkenBetrokkenheidObjectTypeTeBepalen() {
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(new OuderHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEOUDER));
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(new KindHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEKIND));
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(new ErkennerHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEERKENNER));
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(new InstemmerHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDEINSTEMMER));
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(new NaamgeverHisVolledigImpl(null, null)), is(ElementEnum.GERELATEERDENAAMGEVER));

        PartnerHisVolledig partner = new PartnerHisVolledigImpl(new HuwelijkHisVolledigImpl(), null);
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(partner), is(ElementEnum.GERELATEERDEHUWELIJKSPARTNER));

        partner = new PartnerHisVolledigImpl(new GeregistreerdPartnerschapHisVolledigImpl(), null);
        assertThat(elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(partner), is(ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER));
    }

    @Test
    public void dientElementEnumVoorRelatieObjectTypeTeBepalen() {
        assertThat(elementEnumBepaler.bepaalRelatieObjectType(new HuwelijkHisVolledigImpl()), is(ElementEnum.HUWELIJK));
        assertThat(elementEnumBepaler.bepaalRelatieObjectType(new GeregistreerdPartnerschapHisVolledigImpl()), is(ElementEnum.HUWELIJKGEREGISTREERDPARTNERSCHAP));
        assertThat(elementEnumBepaler.bepaalRelatieObjectType(new ErkenningOngeborenVruchtHisVolledigImpl()), is(ElementEnum.ERKENNINGONGEBORENVRUCHT));
        assertThat(elementEnumBepaler.bepaalRelatieObjectType(new NaamskeuzeOngeborenVruchtHisVolledigImpl()), is(ElementEnum.NAAMSKEUZEONGEBORENVRUCHT));
        assertThat(elementEnumBepaler.bepaalRelatieObjectType(new FamilierechtelijkeBetrekkingHisVolledigImpl()), is(ElementEnum.FAMILIERECHTELIJKEBETREKKING));
    }
}
