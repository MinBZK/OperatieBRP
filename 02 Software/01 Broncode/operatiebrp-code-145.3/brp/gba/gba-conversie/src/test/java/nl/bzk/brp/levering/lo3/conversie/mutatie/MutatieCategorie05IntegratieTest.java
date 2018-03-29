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
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

/**
 * Huwelijk.
 */
public class MutatieCategorie05IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    private AdministratieveHandeling administratieveHandeling;
    private Set<AdministratieveHandeling> administratieveHandelingen;

    private MetaObject maakPersoonMetHuwelijk() {
        administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        // Partner toevoegen
        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                          .metId(11)
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_PARTNER_ROLCODE.getId()), "PARTNER")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        // Huwelijk Identiteit toevoegen
        final GroepElement groepElement2 = ElementHelper.getGroepElement(Element.HUWELIJK_IDENTITEIT.getId());
        final ObjectElement objectElement2 = ElementHelper.getObjectElement(Element.HUWELIJK.getId());
        final MetaObject.Builder metaObject2Builder = MetaObject.maakBuilder()
                                                                .metObjectElement(objectElement2)
                                                                .metId(12)
                                                                .metGroep()
                                                                .metGroepElement(groepElement2)
                                                                .metRecord()
                                                                .metId(idTeller.getAndIncrement())
                                                                .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_SOORTCODE.getId()), "H")
                                                                .eindeRecord()
                                                                .eindeGroep();
        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                          .metId(11)
                          .metObject(metaObject2Builder)
                          .build()
                          .getObjecten(objectElement2)
                          .iterator()
                          .next()
                          .getGroep(groepElement2)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        // Huwelijk Standaard toevoegen
        final GroepElement groepElement3 = ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId());
        final ObjectElement objectElement3 = ElementHelper.getObjectElement(Element.HUWELIJK.getId());
        final MetaObject.Builder metaObject3Builder = MetaObject.maakBuilder()
                                                                .metObjectElement(objectElement3)
                                                                .metId(12)
                                                                .metGroep()
                                                                .metGroepElement(groepElement3)
                                                                .metRecord()
                                                                .metId(idTeller.getAndIncrement())
                                                                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                                                                .metAttribuut(
                                                                    ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMAANVANG.getId()),
                                                                    19400101)
                                                                .metAttribuut(
                                                                    ElementHelper.getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE.getId()),
                                                                    "0433")
                                                                .metAttribuut(
                                                                    ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDAANVANGCODE.getId()),
                                                                    "6030")
                                                                .eindeRecord()
                                                                .eindeGroep();
        final MetaRecord mutatieRecord3 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                          .metId(11)
                          .metObject(metaObject3Builder)
                          .build()
                          .getObjecten(objectElement3)
                          .iterator()
                          .next()
                          .getGroep(groepElement3)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord3);

        // Voeg gerelateerdeHuwelijkspartner toe
        final GroepElement groepElement4 = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId());
        final ObjectElement objectElement4 = ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId());
        final MetaObject.Builder metaObject4Builder = MetaObject.maakBuilder()
                                                                .metObjectElement(objectElement4)
                                                                .metId(13)
                                                                .metGroep()
                                                                .metGroepElement(groepElement4)
                                                                .metRecord()
                                                                .metId(idTeller.getAndIncrement())
                                                                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                                                                .metAttribuut(
                                                                    ElementHelper.getAttribuutElement(
                                                                        Element.GERELATEERDEHUWELIJKSPARTNER_ROLCODE.getId()),
                                                                    "PARTNER")
                                                                .eindeRecord()
                                                                .eindeGroep();
        final MetaRecord mutatieRecord4 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                          .metId(12)
                          .metObject(metaObject4Builder)
                          .build()
                          .getObjecten(objectElement4)
                          .iterator()
                          .next()
                          .getGroep(groepElement4)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord4);

        // Voeg gerelateerdeHuwelijkspartner.Persoon toe
        final GroepElement groepElement5 = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT.getId());
        final ObjectElement objectElement5 = ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId());
        final MetaObject.Builder metaObject5Builder = MetaObject.maakBuilder()
                                                                .metObjectElement(objectElement5)
                                                                .metId(idTeller.getAndIncrement())
                                                                .metGroep()
                                                                .metGroepElement(groepElement5)
                                                                .metRecord()
                                                                .metId(idTeller.getAndIncrement())
                                                                .metAttribuut(
                                                                    ElementHelper.getAttribuutElement(
                                                                        Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE.getId()),
                                                                    "O")
                                                                .eindeRecord()
                                                                .eindeGroep();
        voegGerelateerdeHuwelijkspartnerIdentificatieNummersToe(
            metaObject5Builder,
            administratieveHandeling.getActies().iterator().next(),
            19400101,
            "1231231234",
            "345345345");
        voegGerelateerdeHuwelijkspartnerGeboorteToe(metaObject5Builder, administratieveHandeling.getActies().iterator().next(), 19200202, "0599", "6030");
        voegGerelateerdeHuwelijkspartnerGeslachtsaanduidingToe(metaObject5Builder, administratieveHandeling.getActies().iterator().next(), 19400101, "V");
        voegGerelateerdeHuwelijkspartnerSamengesteldeNaamToe(
            metaObject5Builder,
            administratieveHandeling.getActies().iterator().next(),
            19400101,
            "Maria",
            "los",
            " ",
            "Pallo");

        MetaObject.maakBuilder()
                  .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                  .metId(13)
                  .metObject(metaObject5Builder)
                  .build()
                  .getObjecten(objectElement5)
                  .iterator()
                  .next()
                  .getGroepen()
                  .forEach(g -> g.getRecords().forEach(persoonAdder::voegPersoonMutatieToe));

        return persoonAdder.build();
    }

    @Test
    public void testSluitingHuwelijk() {

        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        logMetaObject(persoonObject);

        final Persoonslijst persoon = new Persoonslijst(persoonObject, 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "1231231234",
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Maria",
            Lo3ElementEnum.ELEMENT_0230,
            "los",
            Lo3ElementEnum.ELEMENT_0240,
            "Pallo",
            Lo3ElementEnum.ELEMENT_0310,
            "19200202",
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_1510,
            "H",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "",
            Lo3ElementEnum.ELEMENT_0120,
            "",
            Lo3ElementEnum.ELEMENT_0210,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "",
            Lo3ElementEnum.ELEMENT_0240,
            "",
            Lo3ElementEnum.ELEMENT_0310,
            "",
            Lo3ElementEnum.ELEMENT_0320,
            "",
            Lo3ElementEnum.ELEMENT_0330,
            "",
            Lo3ElementEnum.ELEMENT_0410,
            "",
            Lo3ElementEnum.ELEMENT_0610,
            "",
            Lo3ElementEnum.ELEMENT_0620,
            "",
            Lo3ElementEnum.ELEMENT_0630,
            "",
            Lo3ElementEnum.ELEMENT_1510,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep01Identificatienummers() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19600101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(
                                  Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                              "1231231234")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(
                                  Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()),
                              "543543543")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep02Naam() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().stream().findFirst().get())
                          .metDatumAanvangGeldigheid(19600101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                              "Voornaam3")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                              "P")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()),
                              "het")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                              " ")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                              "achternaam")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam3",
            Lo3ElementEnum.ELEMENT_0220,
            "P",
            Lo3ElementEnum.ELEMENT_0230,
            "het",
            Lo3ElementEnum.ELEMENT_0240,
            "achternaam",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Maria",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "los",
            Lo3ElementEnum.ELEMENT_0240,
            "Pallo",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().stream().findFirst().get())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM.getId()), 19590101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE.getId()), "0222")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()),
                              "6030")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0310,
            "19590101",
            Lo3ElementEnum.ELEMENT_0320,
            "0222",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0310,
            "19200202",
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboorteBuiteland() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().stream().findFirst().get())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM.getId()), 19200202)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()),
                              "Brussel")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()),
                              "5010")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0320,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0330,
            "5010",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04Geslachtsaanduiding() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19600101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()),
                              "O")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "O",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep06Sluiting() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMAANVANG.getId()), 19600101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG.getId()), "Brussel")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDAANVANGCODE.getId()), "5002")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19600101",
            Lo3ElementEnum.ELEMENT_0620,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0630,
            "5002",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep07Ontbinding() {
        final MetaObject persoonObject = maakPersoonMetHuwelijk();
        AdministratieveHandeling bijhoudingsAdministratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(bijhoudingsAdministratieveHandeling);

        logMetaObject(persoonObject);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(bijhoudingsAdministratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMEINDE.getId()), 19600101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEPLAATSEINDE.getId()), "Brussel")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDEINDECODE.getId()), "5002")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, bijhoudingsAdministratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "",
            Lo3ElementEnum.ELEMENT_0620,
            "",
            Lo3ElementEnum.ELEMENT_0630,
            "",
            Lo3ElementEnum.ELEMENT_0710,
            "19600101",
            Lo3ElementEnum.ELEMENT_0720,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0730,
            "5002",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_0710,
            "",
            Lo3ElementEnum.ELEMENT_0720,
            "",
            Lo3ElementEnum.ELEMENT_0730,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }
}
