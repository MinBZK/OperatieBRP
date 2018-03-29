/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PredicaatRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class PredicaatRepositoryTest {

    private final PredicaatRepository subject = new PredicaatRepository();

    @Test
    public void test() {
        final Page<Predicaat> result = subject.findAll(null, new PageRequest(0, 10));
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getContent().isEmpty());
        Assert.assertEquals(nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat.class, result.getContent().get(0).getClass());
        Assert.assertNotNull(result.getContent().get(0).getId());
        Assert.assertNotNull(result.getContent().get(0).getCode());
    }
}
