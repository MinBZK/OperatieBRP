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
 * Ouder 1.
 */
public class MutatieCategorie02IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    private MetaObject maakPersoon(final int ouder1Id, final int ouder2id) {
        MetaObject.Builder persoonBuilder = maakBasisPersoonBuilder(idTeller.getAndIncrement());
        MetaObject.Builder ouder1MetaObjectBuilder = MetaObject.maakBuilder();

        ouder1MetaObjectBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).metId(ouder1Id);
        voegGerelateerdeOuderIndentiteitToe(ouder1MetaObjectBuilder, "O");
        voegGerelateerdeOuderGeboorteToe(ouder1MetaObjectBuilder, actie, 18800505, "0588", "6030");
        voegGerelateerdeOuderGeslachtsaanduidingToe(ouder1MetaObjectBuilder, actie, 18800505, "V");
        voegGerelateerdeOuderIdentificatieNummersToe(ouder1MetaObjectBuilder, actie, 18800506, "8888888888", "888888888");
        voegGerelateerdeOuderSamengesteldeNaamToe(ouder1MetaObjectBuilder, actie, 18800505, "Mama", "los", " ", "Pallo");

        MetaObject.Builder ouder2MetaObjectBuilder = MetaObject.maakBuilder();
        ouder2MetaObjectBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).metId(ouder2id);
        voegGerelateerdeOuderIndentiteitToe(ouder2MetaObjectBuilder, "O");
        voegGerelateerdeOuderGeboorteToe(ouder2MetaObjectBuilder, actie, 18800606, "0577", "6030");
        voegGerelateerdeOuderGeslachtsaanduidingToe(ouder2MetaObjectBuilder, actie, 18800606, "M");
        voegGerelateerdeOuderIdentificatieNummersToe(ouder2MetaObjectBuilder, actie, 18800606, "7777777777", "777777777");
        voegGerelateerdeOuderSamengesteldeNaamToe(ouder2MetaObjectBuilder, actie, 18800606, "Papa", "la", " ", "Pippos");

        MetaObject.Builder kindBuilder =
                new MetaObject.Builder().metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_KIND.getId()))
                        .metId(idTeller.getAndIncrement())
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(actie)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_KIND_ROLCODE.getId()), "KIND")
                        .eindeRecord()
                        .eindeGroep();
        persoonBuilder.metObject(kindBuilder);

        MetaObject.Builder familierechterlijkeBetrekkingBuilder =
                new MetaObject.Builder().metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                        .metId(idTeller.getAndIncrement())
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId()), "F")
                        .eindeRecord()
                        .eindeGroep();
        kindBuilder.metObject(familierechterlijkeBetrekkingBuilder);

        MetaObject.Builder ouder1GerelateerdeOuderBuilder = new MetaObject.Builder();
        ouder1GerelateerdeOuderBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                .metId(idTeller.getAndIncrement())
                .metObject(ouder1MetaObjectBuilder)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_ROLCODE.getId()), "OUDER")
                .eindeRecord()
                .eindeGroep();
        familierechterlijkeBetrekkingBuilder.metObject(ouder1GerelateerdeOuderBuilder);

        MetaObject.Builder ouder2GerelateerdeOuderBuilder = new MetaObject.Builder();
        ouder2GerelateerdeOuderBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                .metId(idTeller.getAndIncrement())
                .metObject(ouder2MetaObjectBuilder)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_ROLCODE.getId()), "OUDER")
                .eindeRecord()
                .eindeGroep();
        familierechterlijkeBetrekkingBuilder.metObject(ouder2GerelateerdeOuderBuilder);

        return persoonBuilder.build();
    }

    @Test
    public void testGroep01Identificatienummers() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakPersoon(10, 20);
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                        .metId(10)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                                "8888888888")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()),
                                "123123123")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_02,
                true,
                Lo3ElementEnum.ELEMENT_0120,
                "123123123",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_52,
                true,
                Lo3ElementEnum.ELEMENT_0120,
                "888888888",
                Lo3ElementEnum.ELEMENT_8510,
                "18800506",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep02Naam() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakPersoon(11, 21);
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                        .metId(11)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().stream().findFirst().get())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                                "P")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                                "achternaam")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                                " ")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                                "Voornaam3")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), "het")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_02,
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
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_52,
                true,
                Lo3ElementEnum.ELEMENT_0210,
                "Mama",
                Lo3ElementEnum.ELEMENT_0220,
                "",
                Lo3ElementEnum.ELEMENT_0230,
                "los",
                Lo3ElementEnum.ELEMENT_0240,
                "Pallo",
                Lo3ElementEnum.ELEMENT_8510,
                "18800505",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakPersoon(12, 22);
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                        .metId(12)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().stream().findFirst().get())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM.getId()), 19330202)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE.getId()), "0222")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "6030")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_02,
                true,
                Lo3ElementEnum.ELEMENT_0310,
                "19330202",
                Lo3ElementEnum.ELEMENT_0320,
                "0222",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_52,
                true,
                Lo3ElementEnum.ELEMENT_0310,
                "18800505",
                Lo3ElementEnum.ELEMENT_0320,
                "0588",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboorteBuitenland() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakPersoon(13, 23);
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                        .metId(13)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().stream().findFirst().get())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM.getId()), 18800505)
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()),
                                "Brussel")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "5010")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_02,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "Brussel",
                Lo3ElementEnum.ELEMENT_0330,
                "5010",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_52,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "0588",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04Geslacht() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakPersoon(14, 24);
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                        .metId(14)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().stream().findFirst().get())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()), "M")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_02,
                true,
                Lo3ElementEnum.ELEMENT_0410,
                "M",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_52,
                true,
                Lo3ElementEnum.ELEMENT_0410,
                "V",
                Lo3ElementEnum.ELEMENT_8510,
                "18800505",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

}
