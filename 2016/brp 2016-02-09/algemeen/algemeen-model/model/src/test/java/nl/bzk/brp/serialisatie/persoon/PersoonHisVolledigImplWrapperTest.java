/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Collections;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Test;

public class PersoonHisVolledigImplWrapperTest {
    @Test
    public void testUnwrap() {
        // Bootst een deserialisatie na, en controleert of 'unwrap' het juiste resultaat levert (of de juiste referenties worden opgebouwd)

        // given
        PersoonHisVolledigImplWrapper wrapper = new PersoonHisVolledigImplWrapper();

        // given persoon
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.DUMMY).build();
        Integer persoonId = 1234;
        setField(persoon, "iD", persoonId);
        setField(wrapper, "persoon", persoon);

        // given betrokkenheden
        BetrokkenheidHisVolledigImpl betrokkenheid = new BetrokkenheidHisVolledigImpl() {
        };
        Integer betrokkenheidId = 2345;
        setField(betrokkenheid, "iD", betrokkenheidId);
        setField(betrokkenheid, "persoon", persoon);
        setField(wrapper, "betrokkenheden", Collections.singletonList(betrokkenheid));

        // given relaties
        RelatieHisVolledigImpl relatie = new RelatieHisVolledigImpl() {
        };
        Integer relatieId = 3456;
        setField(relatie, "iD", relatieId);
        relatie.getBetrokkenheden().add(betrokkenheid);
        setField(wrapper, "relaties", Collections.singletonList(relatie));

        // given persoononderzoeken
        OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImpl();
        setField(onderzoek, "iD", 98457);
        PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImpl(persoon, onderzoek);
        setField(wrapper, "persoonOnderzoeken", Collections.singletonList(persoonOnderzoek));
        PersoonOnderzoekHisVolledigImpl gerelateerdPersoonOnderzoek = new PersoonOnderzoekHisVolledigImpl(persoon, onderzoek);
        setField(wrapper, "gerelateerdePersoonOnderzoeken", Collections.singletonList(gerelateerdPersoonOnderzoek));

        // when
        PersoonHisVolledigImpl result = wrapper.unwrap();

        // then
        assertNotNull(result);
        assertEquals(1, result.getBetrokkenheden().size());
        assertEquals(relatieId, result.getBetrokkenheden().iterator().next().getRelatie().getID());
        assertEquals(1, result.getOnderzoeken().size());
        OnderzoekHisVolledigImpl gerelateerdOnderzoek = result.getOnderzoeken().iterator().next().getOnderzoek();
        assertNotNull(gerelateerdOnderzoek);
        assertEquals(1, gerelateerdOnderzoek.getPersonenInOnderzoek().size());

    }
}
