/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

public class AttribuutOpMetaRecordResolverTest {

    private static final AttribuutElement ATTRIBUUT_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    private final AttribuutOpMetaRecordResolver resolver = new AttribuutOpMetaRecordResolver(ATTRIBUUT_ELEMENT);
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
        Assert.assertTrue(resolver.matchContext(metaRecord));
        final Collection<? extends MetaModel> result = resolver.apply(persoonslijst.getModelIndex(), metaRecord);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(metaRecord.getAttribuut(ATTRIBUUT_ELEMENT) == result.stream().findAny().get());
    }

    @Test
    public void matchContext() throws Exception {
        org.junit.Assert.assertTrue(resolver.matchContext(metaRecord));
        assertFalse(resolver.matchContext(metaRecord.getParent()));
        assertFalse(resolver.matchContext(metaRecord.getParent().getParent()));
    }

    @Test
    public void prioriteit() throws Exception {
        assertEquals(Prioriteit.MIDDEL, resolver.prioriteit());
    }


    @Test
    public void postFilter() throws Exception {
        Assert.assertFalse(resolver.postFilter());
    }
}
