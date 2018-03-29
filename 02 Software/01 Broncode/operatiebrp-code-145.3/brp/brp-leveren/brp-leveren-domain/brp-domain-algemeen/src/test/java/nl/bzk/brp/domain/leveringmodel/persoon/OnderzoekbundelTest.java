/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Test;

/**
 * GegevenInOnderzoekTest.
 */
public class OnderzoekbundelTest {

    private static final Long VOORKOMEN_SLEUTEL = 13L;
    private static final Long OBJECT_SLEUTEL = 14L;
    private static final Long GEGEVEN_OBJECT_SLEUTEL = 7L;
    private static final Long OBJECT_SLEUTEL_ONDERZOEK = 100L;

    @Test
    public void testIsOntbrekend() throws Exception {

        assertTrue(geefOntbrekendOnderzoeksgegeven().isOntbrekend());
        assertFalse(geefOnderzoeksgegeven().isOntbrekend());
    }

    @Test
    public void testEquals() throws Exception {

        final Onderzoekbundel onderzoekbundel1 = geefOnderzoeksgegeven();
        final Onderzoekbundel onderzoekbundel2 = geefOntbrekendOnderzoeksgegeven();

        assertTrue(onderzoekbundel1.equals(onderzoekbundel1));
        assertFalse(onderzoekbundel1.equals(null));
        assertFalse(onderzoekbundel1.equals(onderzoekbundel2.getOnderzoek()));
        assertFalse(onderzoekbundel1.equals(onderzoekbundel2));
        assertFalse(onderzoekbundel1.equals(null));
    }

    private Onderzoekbundel geefOntbrekendOnderzoeksgegeven() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()

                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(1)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 20140101)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK.getId()))
                    .metId(OBJECT_SLEUTEL_ONDERZOEK)
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD.getId()))
                        .metRecord().eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                            .metRecord()
                                .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON.getNaam())
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject()
        .build();
        //@formatter:on
        return new OnderzoekIndex(new ModelIndex(metaObject)).getGegevensInOnderzoek().keySet().iterator().next();
    }

    private Onderzoekbundel geefOnderzoeksgegeven() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()

                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(1)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 20100101)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK.getId()))
                    .metId(OBJECT_SLEUTEL_ONDERZOEK)
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD.getId()))
                        .metRecord().eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                            .metRecord()
                                .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON.getNaam())
                                .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 1)
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject()
        .build();
        //@formatter:on
        return new OnderzoekIndex(new ModelIndex(metaObject)).getGegevensInOnderzoek().keySet().iterator().next();
    }
}
