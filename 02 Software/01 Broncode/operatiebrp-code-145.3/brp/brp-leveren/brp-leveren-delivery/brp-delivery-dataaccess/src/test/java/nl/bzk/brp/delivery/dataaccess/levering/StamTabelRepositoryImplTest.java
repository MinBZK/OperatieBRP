/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import org.junit.Test;
import org.springframework.util.Assert;


/**
 * Stam tabel repository test.
 */
public class StamTabelRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private StamTabelRepository stamTabelRepository;

    @Test
    public final void vindAlleStamgegevensVoorTabel() {

        final ObjectElement object = ElementHelper.getObjectElement(Element.GEMEENTE.getId());
        final List<AttribuutElement> stamgegevenAttributenInBericht = new ArrayList<>();
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();

        for (GroepElement groepElement : object.getGroepen()) {
            for (AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                stamgegevenAttributenInBericht.add(attribuutElement);
                stamgegevenAttributen.add(attribuutElement);
            }
        }
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(object, stamgegevenAttributenInBericht, stamgegevenAttributen);

        final List<Map<String, Object>> lijst = stamTabelRepository.vindAlleStamgegevensVoorTabel(stamgegevenTabel);
        Assert.notNull(lijst);
    }

    @Test
    public final void vindAlleStamgegevensVoorTabelVersieStuf() {

        final ObjectElement object = ElementHelper.getObjectElement(Element.VERTALINGBERICHTSOORTBRP.getId());
        final List<AttribuutElement> stamgegevenAttributenInBericht = new ArrayList<>();
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();

        for (GroepElement groepElement : object.getGroepen()) {
            for (AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                stamgegevenAttributenInBericht.add(attribuutElement);
                stamgegevenAttributen.add(attribuutElement);
            }
        }
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(object, stamgegevenAttributenInBericht, stamgegevenAttributen);

        final List<Map<String, Object>> lijst = stamTabelRepository.vindAlleStamgegevensVoorTabel(stamgegevenTabel);
        Assert.notNull(lijst);
    }
}
