/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import static java.util.Arrays.asList;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * SynchroniseerStamgegevenBerichtTest.
 */
public class SynchroniseerStamgegevenBerichtTest {

    @Test
    public void testSynchroniseerStamgegevenBericht() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().build();
        //@formatter:off
        final BerichtStamgegevens berichtStamgegevens = new BerichtStamgegevens(
                new StamtabelGegevens(new StamgegevenTabel(
                        ElementHelper.getObjectElement(Element.PERSOON_ADRES),
                        Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE)),
                        Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE))
                ), asList(
                        ImmutableMap.<String, Object>builder().put("gem", "Amsterdam").build(),
                        ImmutableMap.<String, Object>builder().put("gem", "Rotterdam").build(),
                        ImmutableMap.<String, Object>builder().put("gem", "Utrecht").build()
                ))
        );
        //@formatter:on
        final SynchroniseerStamgegevenBericht bericht = new SynchroniseerStamgegevenBericht(basisBerichtGegevens, berichtStamgegevens);
        Assert.assertEquals(basisBerichtGegevens, bericht.getBasisBerichtGegevens());
        Assert.assertEquals(berichtStamgegevens, bericht.getBerichtStamgegevens());
    }
}
