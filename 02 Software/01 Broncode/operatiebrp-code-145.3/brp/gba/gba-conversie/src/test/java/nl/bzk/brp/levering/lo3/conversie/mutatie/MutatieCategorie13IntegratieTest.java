/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;
import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

/**
 * Kiesrecht.
 */
public class MutatieCategorie13IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep31EuropeesKiesrecht() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN.getId());
        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING.getId()),
                              19400101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING.getId()),
                              19450101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME.getId()), false)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(record).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_13,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "1",
            Lo3ElementEnum.ELEMENT_3120,
            "19400101",
            Lo3ElementEnum.ELEMENT_3130,
            "19450101");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_63,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "",
            Lo3ElementEnum.ELEMENT_3120,
            "",
            Lo3ElementEnum.ELEMENT_3130,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep38UitsluitingKiesrecht() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_UITSLUITINGKIESRECHT.getId());
        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE.getId()), 19450101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE.getId()), true)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(record).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_13, true, Lo3ElementEnum.ELEMENT_3810, "A", Lo3ElementEnum.ELEMENT_3820, "19450101");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_63, true, Lo3ElementEnum.ELEMENT_3810, "", Lo3ElementEnum.ELEMENT_3820, "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN.getId());
        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING.getId()),
                              19400101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING.getId()),
                              19450101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME.getId()), false)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        final GroepElement uitsluitingElement = ElementHelper.getGroepElement(Element.PERSOON_UITSLUITINGKIESRECHT.getId());
        final MetaRecord uitsluitingRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(uitsluitingElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE.getId()), 19450101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE.getId()), true)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(uitsluitingElement)
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(record).voegPersoonMutatieToe(uitsluitingRecord).build(),
                        0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_13,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "1",
            Lo3ElementEnum.ELEMENT_3120,
            "19400101",
            Lo3ElementEnum.ELEMENT_3130,
            "19450101",
            Lo3ElementEnum.ELEMENT_3810,
            "A",
            Lo3ElementEnum.ELEMENT_3820,
            "19450101");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_63,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "",
            Lo3ElementEnum.ELEMENT_3120,
            "",
            Lo3ElementEnum.ELEMENT_3130,
            "",
            Lo3ElementEnum.ELEMENT_3810,
            "",
            Lo3ElementEnum.ELEMENT_3820,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

}
