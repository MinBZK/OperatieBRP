/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class ConversieCacheTest {

    @Test
    public void test() {
        final ConversieCache subject = new ConversieCache();
        Assert.assertNull(subject.getVolledigCategorien());
        Assert.assertNull(subject.getMutatieCategorien());

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        subject.setVolledigCategorien(categorieen);
        Assert.assertEquals(categorieen, subject.getVolledigCategorien());
        Assert.assertNull(subject.getMutatieCategorien());

        subject.setVolledigCategorien(null);
        Assert.assertNull(subject.getVolledigCategorien());
        Assert.assertNull(subject.getMutatieCategorien());

        subject.setMutatieCategorien(categorieen);
        Assert.assertNull(subject.getVolledigCategorien());
        Assert.assertEquals(categorieen, subject.getMutatieCategorien());

        subject.setMutatieCategorien(null);
        Assert.assertNull(subject.getVolledigCategorien());
        Assert.assertNull(subject.getMutatieCategorien());

    }
}
