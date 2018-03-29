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
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

/**
 * Kind.
 */
public class MutatieCategorie09IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
    final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
    {
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);
    }

    private MetaObjectAdder maakKind() {
        final MetaObject.Builder persoonBuilder = maakBasisPersoonBuilder(idTeller.getAndIncrement());
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonBuilder.build());

        Actie actie = administratieveHandeling.getActies().iterator().next();

        final MetaRecord ouder1Record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(1)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(actie)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OUDER_ROLCODE.getId()), "OUDER")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(ouder1Record);

        MetaObject.Builder familierechterlijkeBetrekking1Builder =
                new MetaObject.Builder().metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                                        .metId(10)
                                        .metGroep()
                                        .metGroepElement(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                                        .metRecord()
                                        .metId(idTeller.getAndIncrement())
                                        .metAttribuut(ElementHelper.getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId()), "F")
                                        .eindeRecord()
                                        .eindeGroep();

        final MetaRecord mutatieRecord1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(1)
                          .metObject(familierechterlijkeBetrekking1Builder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord1);

        final MetaObject.Builder kind1MetaObjectBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SOORTCODE.getId()), "O")
                          .eindeRecord()
                          .eindeGroep();

        voegGerelateerdeKindGeboorteToe(kind1MetaObjectBuilder, actie, 19400101, "0518", "6030");
        voegGerelateerdeKindIdentificatieNummersToe(kind1MetaObjectBuilder, actie, 19400101, "1231231234", "345345345");
        voegGerelateerdeKindSamengesteldeNaamToe(kind1MetaObjectBuilder, actie, 19400101, "Pimmetje", "van", " ", "Trommelen");

        MetaObject.Builder kind1GerelateerdeKindBuilder = MetaObject.maakBuilder();
        kind1GerelateerdeKindBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                                    .metId(20)
                                    .metObject(kind1MetaObjectBuilder)
                                    .metGroep()
                                    .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                                    .metRecord()
                                    .metId(idTeller.getAndIncrement())
                                    .metActieInhoud(actie)
                                    .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_ROLCODE.getId()), "KIND")
                                    .eindeRecord()
                                    .eindeGroep();

        final MetaRecord mutatieRecord4 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .metId(10)
                          .metObject(kind1GerelateerdeKindBuilder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord4);

        MetaObject.maakBuilder()
                  .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                  .metId(20)
                  .metObject(kind1MetaObjectBuilder)
                  .build()
                  .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                  .iterator()
                  .next()
                  .getGroepen()
                  .forEach(g -> g.getRecords().stream().forEach(persoonAdder::voegPersoonMutatieToe));

        return persoonAdder;
    }

    @Test
    public void testGeboorteKind() {
        final Persoonslijst persoon = new Persoonslijst(maakKind().build(), 0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "1231231234",
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_0310,
            "19400101",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
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
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep01Identificatienummers() {
        final AdministratieveHandeling nieuwHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(nieuwHandeling);
        Actie nieuwActie = nieuwHandeling.getActies().iterator().next();

        MetaObject.Builder builder =
                MetaObject.maakBuilder().metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId())).metId(100);
        voegGerelateerdeKindIdentificatieNummersToe(builder, nieuwActie, 19600101, "1231231234", "543543543");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();

        final Persoonslijst persoon = new Persoonslijst(maakKind().voegPersoonMutatieToe(mutatieRecord).build(), 0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, nieuwHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
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
        final AdministratieveHandeling nieuwHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(nieuwHandeling);
        Actie nieuwActie = nieuwHandeling.getActies().iterator().next();

        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(nieuwActie)
                          .metDatumAanvangGeldigheid(19600101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                              "achternaam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), " ")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                              "Voornaam3")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), "het")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                              "P")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon = new Persoonslijst(maakKind().voegPersoonMutatieToe(mutatieRecord).build(), 0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, nieuwHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
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
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        final AdministratieveHandeling nieuwHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(nieuwHandeling);
        Actie nieuwActie = nieuwHandeling.getActies().iterator().next();

        MetaObject.Builder builder =
                MetaObject.maakBuilder().metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId())).metId(100);
        voegGerelateerdeKindGeboorteToe(builder, nieuwActie, 19590101, "0222", "6030");
        final MetaRecord mutatieRecord =
                builder.build().getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId())).getRecords().iterator().next();

        final Persoonslijst persoon = new Persoonslijst(maakKind().voegPersoonMutatieToe(mutatieRecord).build(), 0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, nieuwHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
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
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0310,
            "19400101",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboortenBuiteland() {
        final AdministratieveHandeling nieuwHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(nieuwHandeling);
        Actie nieuwActie = nieuwHandeling.getActies().iterator().next();

        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(nieuwActie)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM.getId()), 19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()), "Brussel")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "5010")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon = new Persoonslijst(maakKind().voegPersoonMutatieToe(mutatieRecord).build(), 0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, nieuwHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
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
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final AdministratieveHandeling nieuwHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);
        administratieveHandelingen.add(nieuwHandeling);
        Actie nieuwActie = nieuwHandeling.getActies().iterator().next();

        MetaObject.Builder builder =
                MetaObject.maakBuilder().metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId())).metId(100);
        voegGerelateerdeKindIdentificatieNummersToe(builder, nieuwActie, 19600101, "1231231234", "543543543");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();

        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(nieuwActie)
                          .metDatumAanvangGeldigheid(19600101)
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                              "achternaam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), " ")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                              "Voornaam3")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), "het")
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                              "P")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord mutatieRecord3 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(nieuwActie)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM.getId()), 19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()), "Brussel")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "5010")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    maakKind().voegPersoonMutatieToe(mutatieRecord).voegPersoonMutatieToe(mutatieRecord2).voegPersoonMutatieToe(mutatieRecord3).build(),
                        0L);
        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, nieuwHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam3",
            Lo3ElementEnum.ELEMENT_0220,
            "P",
            Lo3ElementEnum.ELEMENT_0230,
            "het",
            Lo3ElementEnum.ELEMENT_0240,
            "achternaam",
            Lo3ElementEnum.ELEMENT_0320,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0330,
            "5010",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
    }

}
