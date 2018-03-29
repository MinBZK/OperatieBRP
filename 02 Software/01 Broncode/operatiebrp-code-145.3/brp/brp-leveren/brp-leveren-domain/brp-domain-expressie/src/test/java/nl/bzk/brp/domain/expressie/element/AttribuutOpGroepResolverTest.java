/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Before;
import org.junit.Test;

public class AttribuutOpGroepResolverTest {

    private MetaObject metaObject;
    private MetaGroep metaGroep;
    private ModelIndex index;
    private final AttribuutOpGroepResolver resolver = new AttribuutOpGroepResolver(ElementHelper.getAttribuutElement(Element
            .PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK));

    @Before
    public void voorTest() {
        metaObject = maakPersoon();
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 1L);
        index = persoonslijst.getModelIndex();
        metaGroep = Iterables.getOnlyElement(index.geefGroepen());;
    }

    @Test
    public void apply() throws Exception {
        final Collection<? extends MetaModel> resultaatBinnenGroep = resolver.apply(index, metaGroep);
    }

    @Test(expected = ClassCastException.class)
    public void applyOpObject() {
        final Collection<? extends MetaModel> resultaatBinnenObject = resolver.apply(index, metaObject);
    }

    @Test
    public void matchContext() throws Exception {
        assertTrue(resolver.matchContext(metaGroep));
        assertFalse(resolver.matchContext(metaObject));
    }

    @Test
    public void prioriteit() throws Exception {
        assertEquals(Prioriteit.MIDDEL, resolver.prioriteit());
    }

    private MetaObject maakPersoon() {
        final ZonedDateTime tijdstipLaatsteWijzigingGBASystematiek = ZonedDateTime.now();
        final Actie actieInhoud = TestVerantwoording.maakActie(1, tijdstipLaatsteWijzigingGBASystematiek);
        //@formatter:off
        MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(),
                            tijdstipLaatsteWijzigingGBASystematiek)
                    .eindeRecord()
            .eindeGroep()
            .build();
        //@formatter:on
        return persoon;
    }
}
