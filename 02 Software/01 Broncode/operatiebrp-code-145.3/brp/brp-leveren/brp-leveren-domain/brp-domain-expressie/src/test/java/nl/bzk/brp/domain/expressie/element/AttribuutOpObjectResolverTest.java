/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttribuutOpObjectResolverTest {

    private static final AttribuutElement ATTRIBUUT_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    private final AttribuutOpObjectResolver resolver = new AttribuutOpObjectResolver(ATTRIBUUT_ELEMENT, Prioriteit.MIDDEL);
    private MetaRecord metaRecord;
    private Persoonslijst persoonslijst;

    @Before
    public void before() {
        persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);
        metaRecord = persoonslijst.getModelIndex().geefAttributen(ATTRIBUUT_ELEMENT)
                .stream().findAny().map(MetaAttribuut::getParentRecord).orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void happyFlow() throws Exception {
        final MetaModel context = metaRecord.getParent().getParent();
        Assert.assertTrue(resolver.matchContext(context));
        final Collection<? extends MetaModel> result = resolver.apply(persoonslijst.getModelIndex(), context);
        Assert.assertFalse(result.isEmpty());

        for (MetaModel metaModel : result) {
            MetaAttribuut metaAttribuut = (MetaAttribuut) metaModel;
            Assert.assertTrue(metaAttribuut.getAttribuutElement() == ATTRIBUUT_ELEMENT);
        }
    }

    @Test
    public void matchContext() throws Exception {
        Assert.assertFalse(resolver.matchContext(metaRecord));
        Assert.assertFalse(resolver.matchContext(metaRecord.getParent()));
        Assert.assertTrue(resolver.matchContext(metaRecord.getParent().getParent()));
    }

    @Test
    public void prioriteit() throws Exception {
        assertEquals(Prioriteit.LAAG, new AttribuutOpObjectResolver(ATTRIBUUT_ELEMENT, Prioriteit.LAAG).prioriteit());
        assertEquals(Prioriteit.HOOG, new AttribuutOpObjectResolver(ATTRIBUUT_ELEMENT, Prioriteit.HOOG).prioriteit());
    }


    @Test
    public void postFilter() throws Exception {
        assertTrue(resolver.postFilter());
    }
}
