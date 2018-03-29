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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
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
 * Gezagsverhouding.
 */
public class MutatieCategorie11IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    private MetaObject maakOudersMetKind() {
        MetaObject.Builder persoonBuilder = maakBasisPersoonBuilder(idTeller.getAndIncrement());

        MetaObject.Builder ouder1MetaObjectBuilder = MetaObject.maakBuilder();
        ouder1MetaObjectBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).metId(10);
        voegGerelateerdeOuderIndentiteitToe(ouder1MetaObjectBuilder, "O");
        voegGerelateerdeOuderGeboorteToe(ouder1MetaObjectBuilder, actie, 18800505, "0588", "6030");
        voegGerelateerdeOuderGeslachtsaanduidingToe(ouder1MetaObjectBuilder, actie, 19200101, "V");
        voegGerelateerdeOuderIdentificatieNummersToe(ouder1MetaObjectBuilder, actie, 19200101, "8888888888", "888888888");
        voegGerelateerdeOuderSamengesteldeNaamToe(ouder1MetaObjectBuilder, actie, 19200101, "Mama", "los", " ", "Pallo");

        MetaObject.Builder ouder2MetaObjectBuilder = MetaObject.maakBuilder();
        ouder2MetaObjectBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).metId(20);
        voegGerelateerdeOuderIndentiteitToe(ouder2MetaObjectBuilder, "O");
        voegGerelateerdeOuderGeboorteToe(ouder2MetaObjectBuilder, actie, 18800606, "0577", "6030");
        voegGerelateerdeOuderGeslachtsaanduidingToe(ouder2MetaObjectBuilder, actie, 19200101, "M");
        voegGerelateerdeOuderIdentificatieNummersToe(ouder2MetaObjectBuilder, actie, 19200101, "7777777777", "777777777");
        voegGerelateerdeOuderSamengesteldeNaamToe(ouder2MetaObjectBuilder, actie, 19200101, "Papa", "la", " ", "Pippos");

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
                .metId(1)
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
                .metId(2)
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
    public void testGroep32GezagMinderjarige1() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(1)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
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
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "1",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige2() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(2)
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
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
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "2",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarigeD() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final MetaRecord identiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM.getId()),
                                SoortIndicatie.DERDE_HEEFT_GEZAG.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord standaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metDatumAanvangGeldigheid(19400101)
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                        persoonAdder.voegPersoonMutatieToe(identiteitRecord).voegPersoonMutatieToe(standaardRecord).build(),
                        0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "D",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige12D() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement gerelateerdeOuder1Groep = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord gerelateerdeOuder1Record =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(1)
                        .metGroep()
                        .metGroepElement(gerelateerdeOuder1Groep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(gerelateerdeOuder1Groep)
                        .getRecords()
                        .iterator()
                        .next();

        final GroepElement gerelateerdeOuder2Groep = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord gerelateerdeOuder2Record =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(2)
                        .metGroep()
                        .metGroepElement(gerelateerdeOuder2Groep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(gerelateerdeOuder2Groep)
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM.getId()),
                                SoortIndicatie.DERDE_HEEFT_GEZAG.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metDatumAanvangGeldigheid(19400101)
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon = new Persoonslijst(
                persoonAdder.voegPersoonMutatieToe(gerelateerdeOuder1Record)
                        .voegPersoonMutatieToe(gerelateerdeOuder2Record)
                        .voegPersoonMutatieToe(indicatieStandaardRecord)
                        .voegPersoonMutatieToe(indicatieIdentiteitRecord)
                        .build(),
                0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "12D",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige2naar1D() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        logMetaObject(persoonMetaObject);
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final GroepElement gerelateerdeOuder2Groep = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord gerelateerdeOuder2Record =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(2)
                        .metGroep()
                        .metGroepElement(gerelateerdeOuder2Groep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(actie)
                        .metDatumAanvangGeldigheid(19200101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(gerelateerdeOuder2Groep)
                        .getRecords()
                        .iterator()
                        .next();

        final GroepElement gerelateerdeOuder2Groep1 = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord gerelateerdeOuder2Record1 =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(2)
                        .metGroep()
                        .metGroepElement(gerelateerdeOuder2Groep1)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()),
                                false)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(gerelateerdeOuder2Groep1)
                        .getRecords()
                        .iterator()
                        .next();

        final GroepElement gerelateerdeOuder1Groep = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());
        final MetaRecord gerelateerdeOuder1Record =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metId(1)
                        .metGroep()
                        .metGroepElement(gerelateerdeOuder1Groep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(gerelateerdeOuder1Groep)
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM.getId()),
                                SoortIndicatie.DERDE_HEEFT_GEZAG.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metDatumAanvangGeldigheid(19400101)
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon = new Persoonslijst(
                persoonAdder.voegPersoonMutatieToe(gerelateerdeOuder2Record)
                        .voegPersoonMutatieToe(gerelateerdeOuder2Record1)
                        .voegPersoonMutatieToe(gerelateerdeOuder1Record)
                        .voegPersoonMutatieToe(indicatieStandaardRecord)
                        .voegPersoonMutatieToe(indicatieIdentiteitRecord)
                        .build(),
                0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "1D",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3210,
                "2",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep33Curatele() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject persoonMetaObject = maakOudersMetKind();
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonMetaObject);

        final MetaRecord identiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM.getId()),
                                SoortIndicatie.ONDER_CURATELE.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord standaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metDatumAanvangGeldigheid(19400101)
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE.getId()), true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                        persoonAdder.voegPersoonMutatieToe(identiteitRecord).voegPersoonMutatieToe(standaardRecord).build(),
                        0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_11,
                true,
                Lo3ElementEnum.ELEMENT_3310,
                "1",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_61,
                true,
                Lo3ElementEnum.ELEMENT_3310,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }
}
