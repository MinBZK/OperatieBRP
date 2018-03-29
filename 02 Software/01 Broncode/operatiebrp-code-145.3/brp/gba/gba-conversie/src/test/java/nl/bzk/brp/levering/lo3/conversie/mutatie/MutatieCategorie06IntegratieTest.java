/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

/**
 * Overlijden.
 */
public class MutatieCategorie06IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep08Overlijden() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId()), 19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId()), "0052")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId()), "6030")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_06,
                true,
                Lo3ElementEnum.ELEMENT_0810,
                "19400101",
                Lo3ElementEnum.ELEMENT_0820,
                "0052",
                Lo3ElementEnum.ELEMENT_0830,
                "6030",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_56,
                true,
                Lo3ElementEnum.ELEMENT_0810,
                "",
                Lo3ElementEnum.ELEMENT_0820,
                "",
                Lo3ElementEnum.ELEMENT_0830,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

}
