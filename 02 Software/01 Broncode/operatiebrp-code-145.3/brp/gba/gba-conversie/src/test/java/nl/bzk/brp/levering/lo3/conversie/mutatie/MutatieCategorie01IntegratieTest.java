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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
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
 * Persoon.
 */
public class MutatieCategorie01IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGeenWijzigingen() {
        LOG.info("testGeenWijzigingen");
        final MetaObject persoonMetaObject = maakBasisPersoon(5001);

        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);
        final Persoonslijst persoon = new Persoonslijst(persoonMetaObject, 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGroep01Identificatienummers() {
        LOG.info("testGroep01Identificatienummers");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567890")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), "123123123")
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
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0120,
                "123123123",
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
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep02Naam() {
        LOG.info("testGroep02Naam");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), "Voornaam3")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()), "P")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), "het")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), " ")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), "achternaam")
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
                Lo3CategorieEnum.CATEGORIE_01,
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
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam1 Voornaam2",
                Lo3ElementEnum.ELEMENT_0220,
                "",
                Lo3ElementEnum.ELEMENT_0230,
                "de",
                Lo3ElementEnum.ELEMENT_0240,
                "geslachtsnaam",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        LOG.info("testGroep03Geboorte");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 19330202)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId()), "0222")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "6030")
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
                Lo3CategorieEnum.CATEGORIE_01,
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
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_0310,
                "19200101",
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboorteBuiteland() {
        LOG.info("testGroep03GeboorteBuiteland");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 19200101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()), "Brussel")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), "5010")
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
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "Brussel",
                Lo3ElementEnum.ELEMENT_0330,
                "5010",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04Geslacht() {
        LOG.info("testGroep04Geslacht");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE.getId()), "V")
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
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0410,
                "V",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_0410,
                "M",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep20ANummerVerwijzingen() {
        LOG.info("testGroep20ANummerVerwijzingen");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId()), "1234512345")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()),
                                "1234123412")
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
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_2010,
                "1234512345",
                Lo3ElementEnum.ELEMENT_2020,
                "1234123412",
                Lo3ElementEnum.ELEMENT_8510,
                "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_2010,
                "",
                Lo3ElementEnum.ELEMENT_2020,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep20BsnVerwijzingen() {
        LOG.info("testGroep20BsnVerwijzingen");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER.getId()), "123451234")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER.getId()), "123412341")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(groepElement)
                        .getRecords()
                        .iterator()
                        .next();
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.voegPersoonMutatieToe(mutatieRecord).build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_01, true, Lo3ElementEnum.ELEMENT_8510, "19400101", Lo3ElementEnum.ELEMENT_8610, "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_51, true, Lo3ElementEnum.ELEMENT_8510, "19200101", Lo3ElementEnum.ELEMENT_8610, "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep61Naamgebruik() {
        LOG.info("testGroep61Naamgebruik");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metGroep()
                        .metGroepElement(groepElement)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_CODE.getId()), "P")
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
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_6110,
                "P",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19400101",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_51,
                true,
                Lo3ElementEnum.ELEMENT_6110,
                "E",
                // Lo3ElementEnum.ELEMENT_8510,
                // "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        LOG.info("testAlleInhoudelijkeGroepen");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElementIdentificatie = ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId());
        final MetaRecord mutatieRecordIdentificatie = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementIdentificatie)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19400101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                        "1234567890")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()),
                        "123123123")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementIdentificatie)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordIdentificatie);

        final GroepElement groepElementSamengesteldeNaam = ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId());
        final MetaRecord mutatieRecordSamengesteldeNaam = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementSamengesteldeNaam)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19400101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                        "Voornaam3")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                        "P")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()),
                        "het")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                        " ")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                        "achternaam")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementSamengesteldeNaam)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordSamengesteldeNaam);

        final GroepElement groepElementGeboorte = ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId());
        final MetaRecord mutatieRecordGeboorte = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementGeboorte)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()),
                        19200101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()),
                        "Brussel")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()),
                        "5010")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementGeboorte)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordGeboorte);

        final GroepElement groepElementGeslachtsaanduiding = ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId());
        final MetaRecord mutatieRecordGeslachtsaanduiding = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementGeslachtsaanduiding)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19400101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_GESLACHTSAANDUIDING_CODE.getId()),
                        "V")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementGeslachtsaanduiding)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordGeslachtsaanduiding);

        final GroepElement groepElementNummerwijziging = ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId());
        final MetaRecord mutatieRecordNummerwijziging = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementNummerwijziging)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19400101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId()),
                        "1234512345")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()),
                        "1234123412")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementNummerwijziging)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordNummerwijziging);

        final GroepElement groepElementNaamgebruik = ElementHelper.getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId());
        final MetaRecord mutatieRecordNaamgebruik = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElementNaamgebruik)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_CODE.getId()),
                        "P")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElementNaamgebruik)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecordNaamgebruik);
        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0120,
                "123123123",
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam3",
                Lo3ElementEnum.ELEMENT_0220,
                "PS",
                Lo3ElementEnum.ELEMENT_0230,
                "het",
                Lo3ElementEnum.ELEMENT_0240,
                "achternaam",
                Lo3ElementEnum.ELEMENT_0320,
                "Brussel",
                Lo3ElementEnum.ELEMENT_0330,
                "5010",
                Lo3ElementEnum.ELEMENT_0410,
                "V",
                Lo3ElementEnum.ELEMENT_2010,
                "1234512345",
                Lo3ElementEnum.ELEMENT_2020,
                "1234123412",
                Lo3ElementEnum.ELEMENT_6110,
                "P",
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
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam1 Voornaam2",
                Lo3ElementEnum.ELEMENT_0220,
                "",
                Lo3ElementEnum.ELEMENT_0230,
                "de",
                Lo3ElementEnum.ELEMENT_0240,
                "geslachtsnaam",
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                Lo3ElementEnum.ELEMENT_0410,
                "M",
                Lo3ElementEnum.ELEMENT_2010,
                "",
                Lo3ElementEnum.ELEMENT_2020,
                "",
                Lo3ElementEnum.ELEMENT_6110,
                "E",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }
}
