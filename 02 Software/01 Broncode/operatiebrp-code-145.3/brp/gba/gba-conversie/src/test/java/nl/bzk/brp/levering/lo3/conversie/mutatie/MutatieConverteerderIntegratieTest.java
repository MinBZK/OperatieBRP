/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;
import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class MutatieConverteerderIntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGeenWijzigingen() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final Persoonslijst persoon = new Persoonslijst(maakBasisPersoonBuilder(idTeller.getAndIncrement()).build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGroep81Akte() {
        final ZonedDateTime tsReg = ZonedDateTime.of(1940, 1, 2, 0, 0, 0,
            0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(
            TestVerantwoording.maakAdministratieveHandeling(idTeller.getAndIncrement(), "000034", tsReg,
                SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
            .metObject(TestVerantwoording.maakActieBuilder(idTeller.getAndIncrement(), SoortActie.CONVERSIE_GBA, tsReg, "000001", 19470307)
            .metObject(TestVerantwoording.maakActiebronBuilder(1, null, null)
            .metObject(TestVerantwoording.maakDocumentBuilder(1, "1", "TBX0001", null, "043301")))
            ).build());

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoonBuilder(100).build());
        final MetaObject.Builder builder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON.getId()))
                          .metId(100);
        voegIdentificatieNummersToe(builder, administratieveHandeling.getActies().iterator().next(), 19400101, "1234567890", "123123123");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(),
                        0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8110,
            "0433",
            Lo3ElementEnum.ELEMENT_8120,
            "TBX0001",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8110,
            "",
            Lo3ElementEnum.ELEMENT_8120,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep82Document() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1940, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(idTeller.getAndIncrement(), "000034", tsReg, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
            .metObject(TestVerantwoording.maakActieBuilder(idTeller.getAndIncrement(), SoortActie.CONVERSIE_GBA, tsReg, "000001", 19400101)
            .metObject(TestVerantwoording.maakActiebronBuilder(1, null, null)
            .metObject(TestVerantwoording.maakDocumentBuilder(1, "1", null, "Documentomschrijving", "043301")
        ))).build());

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoonBuilder(100).build());
        final MetaObject.Builder builder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON.getId()))
                          .metId(100);
        voegIdentificatieNummersToe(builder, administratieveHandeling.getActies().iterator().next(), 19400101, "1234567890", "123123123");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();
        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(),
                        0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8210,
            "0433",
            Lo3ElementEnum.ELEMENT_8220,
            "19400101",
            Lo3ElementEnum.ELEMENT_8230,
            "Documentomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8210,
            "",
            Lo3ElementEnum.ELEMENT_8220,
            "",
            Lo3ElementEnum.ELEMENT_8230,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep83Onderzoek() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoonBuilder(100).build());

        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .metRecord()
                          .metId(333455L)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567890")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), "123123123")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaObject.Builder gegevenInOnderzoekBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.GEGEVENINONDERZOEK.getId()))
                          .metId(idTeller.getAndIncrement())
                          .metGroep()
                          .metGroepElement(
                              ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId()),
                              "Persoon.Identificatienummers.Burgerservicenummer")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId()), 4587348L)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId()), 333455L)
                          .eindeRecord()
                          .eindeGroep();
        final MetaRecord gegevenInOnderzoekRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK.getId()))
                          .metId(20)
                          .metObject(gegevenInOnderzoekBuilder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.GEGEVENINONDERZOEK.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaObject.Builder onderzoekIdentiteitBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK.getId()))
                          .metId(20)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .eindeRecord()
                          .eindeGroep();
        final MetaRecord onderzoekIdentiteitRecord =
                onderzoekIdentiteitBuilder.build()
                                          .getGroep(
                                              ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK_IDENTITEIT.getId()))
                                          .getRecords()
                                          .iterator()
                                          .next();

        final MetaObject.Builder onderzoekStandaardBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK.getId()))
                          .metId(20)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK_STANDAARD.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG.getId()), 19340505)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING.getId()), "Conversie GBA: 010120")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.ONDERZOEK_STATUSNAAM.getId()), "In uitvoering")
                          .eindeRecord()
                          .eindeGroep();

        final MetaRecord onderzoekStandaardRecord =
                onderzoekStandaardBuilder.build()
                                         .getGroep(
                                             (ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.ONDERZOEK_STANDAARD.getId())))
                                         .getRecords()
                                         .iterator()
                                         .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(onderzoekIdentiteitRecord)
                                .voegPersoonMutatieToe(onderzoekStandaardRecord)
                                .voegPersoonMutatieToe(gegevenInOnderzoekRecord)
                                .voegPersoonMutatieToe(mutatieRecord)
                                .build(),
                        0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8310,
            "010120",
            Lo3ElementEnum.ELEMENT_8320,
            "19340505",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8310,
            "",
            Lo3ElementEnum.ELEMENT_8320,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep84Onjuist() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling(1960, 1, 2);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoonBuilder(100).build());

        final MetaObject.Builder builder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON.getId()))
                          .metId(100);
        voegIdentificatieNummersToe(builder, administratieveHandeling.getActies().iterator().next(), 19200101, "1234567890", "123123123");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonCorrectieToe(mutatieRecord, "O").build(),
                        0L);

        logMetaObject(persoon.getMetaObject());

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8410,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8410,
            "O",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep88RniDeelnemer() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1940, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(idTeller.getAndIncrement(), "891401", tsReg, SoortAdministratieveHandeling
                .GBA_BIJHOUDING_ACTUEEL)
            .metObject(TestVerantwoording.maakActieBuilder(idTeller.getAndIncrement(), SoortActie.CONVERSIE_GBA, tsReg, "891401", 19400101)
            .metObject(TestVerantwoording.maakActiebronBuilder(1, null, "rechtsgrondomschrijving")))
        .build());

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoonBuilder(100).build());
        final MetaObject.Builder builder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON.getId()))
                          .metId(100);
        voegIdentificatieNummersToe(builder, administratieveHandeling.getActies().iterator().next(), 19400101, "1234567890", "123123123");
        final MetaRecord mutatieRecord =
                builder.build()
                       .getGroep(ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                       .getRecords()
                       .iterator()
                       .next();
        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(),
                        0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8810,
            "0601",
            Lo3ElementEnum.ELEMENT_8820,
            "rechtsgrondomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8810,
            "",
            Lo3ElementEnum.ELEMENT_8820,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

}
