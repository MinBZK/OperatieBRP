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
 * Nationaliteit.
 */
public class MutatieCategorie04IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep04NationaliteitVerkrijging() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakBasisPersoon(idTeller.getAndIncrement());
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(96)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), "0052")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final GroepElement groepElement2 = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId());
        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(96)
                        .metGroep()
                        .metGroepElement(groepElement2)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement2)
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_04,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "0052",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_54,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04NationaliteitBeeindiging() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakBasisPersoon(idTeller.getAndIncrement());
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final AdministratieveHandeling administratieveHandeling2 = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(administratieveHandeling2);

        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(97)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), "0052")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(97)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metActieVerval(administratieveHandeling.getActies().iterator().next())
                        .metActieVervalTbvLeveringMutaties(administratieveHandeling2.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final MetaRecord mutatieRecord3 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(97)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metActieAanpassingGeldigheid(administratieveHandeling2.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metDatumEindeGeldigheid(19600101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId()), "114")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord3);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling2, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_04,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "",
                Lo3ElementEnum.ELEMENT_6410,
                "114",
                Lo3ElementEnum.ELEMENT_8510,
                "19600101",
                Lo3ElementEnum.ELEMENT_8610,
                "19600102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_54,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "0052",
                Lo3ElementEnum.ELEMENT_6410,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep63VerkrijgingNL() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakBasisPersoon(idTeller.getAndIncrement());
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(98)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), "0001")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final GroepElement groepElement2 = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId());
        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(98)
                        .metGroep()
                        .metGroepElement(groepElement2)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId()), "001")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement2)
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_04,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "0001",
                Lo3ElementEnum.ELEMENT_6310,
                "001",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_54,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "",
                Lo3ElementEnum.ELEMENT_6310,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep63VerliesNL() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakBasisPersoon(idTeller.getAndIncrement());
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final AdministratieveHandeling administratieveHandeling2 = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(administratieveHandeling2);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(99)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), "0001")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(99)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metActieVerval(administratieveHandeling.getActies().iterator().next())
                        .metActieVervalTbvLeveringMutaties(administratieveHandeling2.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId()), "020")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final MetaRecord mutatieRecord3 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(99)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metActieAanpassingGeldigheid(administratieveHandeling2.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metDatumEindeGeldigheid(19600101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId()), "034")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord3);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling2, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_04,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "",
                Lo3ElementEnum.ELEMENT_6310,
                "",
                Lo3ElementEnum.ELEMENT_6410,
                "034",
                Lo3ElementEnum.ELEMENT_8510,
                "19600101",
                Lo3ElementEnum.ELEMENT_8610,
                "19600102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_54,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "0001",
                Lo3ElementEnum.ELEMENT_6310,
                "020",
                Lo3ElementEnum.ELEMENT_6410,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04NationaliteitVerkrijgingBuitenlandsPersoonsnummer() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakBasisPersoon(idTeller.getAndIncrement());
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(96)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), "0052")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                        .metId(96)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord mutatieRecord3 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId()))
                        .metId(667)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId()), "0052")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId()), "DAN-NR-1234567890")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId()))
                        .getRecords().iterator().next();

        final MetaRecord mutatieRecord4 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId()))
                        .metId(667)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId()))
                        .getRecords().iterator().next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord).voegPersoonMutatieToe(mutatieRecord2).voegPersoonMutatieToe(mutatieRecord3)
                .voegPersoonMutatieToe(mutatieRecord4);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_04,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "0052",
                Lo3ElementEnum.ELEMENT_7310,
                "DAN-NR-1234567890",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_54,
                true,
                Lo3ElementEnum.ELEMENT_0510,
                "",
                Lo3ElementEnum.ELEMENT_7310,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }
}
