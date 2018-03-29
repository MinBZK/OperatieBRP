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
 * Verblijfplaats.
 */
public class MutatieCategorie08IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep09Gemeente() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "059901")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0910,
            "0599",
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0910,
            "0518",
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep10Adreshouding() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19391228)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "links")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "naam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1010,
            "B",
            Lo3ElementEnum.ELEMENT_1020,
            "links",
            Lo3ElementEnum.ELEMENT_1030,
            "19391228",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1010,
            "W",
            Lo3ElementEnum.ELEMENT_1020,
            "deel vd gemeente",
            Lo3ElementEnum.ELEMENT_1030,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep11Adres() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "V")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 123)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "Y")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES.getId()), "by")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId()), "straatnaam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "openbareruimte")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "6643WE")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotjeknor")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT.getId()), "IDENT-001")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId()), "IDENT-002")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1110,
            "straatnaam",
            Lo3ElementEnum.ELEMENT_1115,
            "openbareruimte",
            Lo3ElementEnum.ELEMENT_1120,
            "123",
            Lo3ElementEnum.ELEMENT_1130,
            "V",
            Lo3ElementEnum.ELEMENT_1140,
            "Y",
            Lo3ElementEnum.ELEMENT_1150,
            "by",
            Lo3ElementEnum.ELEMENT_1160,
            "6643WE",
            Lo3ElementEnum.ELEMENT_1170,
            "Rotjeknor",
            Lo3ElementEnum.ELEMENT_1180,
            "IDENT-001",
            Lo3ElementEnum.ELEMENT_1190,
            "IDENT-002",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1110,
            "",
            Lo3ElementEnum.ELEMENT_1115,
            "naam",
            Lo3ElementEnum.ELEMENT_1120,
            "10",
            Lo3ElementEnum.ELEMENT_1130,
            "A",
            Lo3ElementEnum.ELEMENT_1140,
            "B",
            Lo3ElementEnum.ELEMENT_1150,
            "",
            Lo3ElementEnum.ELEMENT_1160,
            "2245HJ",
            Lo3ElementEnum.ELEMENT_1170,
            "Rotterdam",
            Lo3ElementEnum.ELEMENT_1180,
            "",
            Lo3ElementEnum.ELEMENT_1190,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep12Locatie() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "naam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getId()), "locatieomschrijving")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1210,
            "locatieomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep13Emigratie() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE.getId()), "E")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId()), "5002")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1.getId()), "regel1")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2.getId()), "regel2")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3.getId()), "regel3")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1310,
            "5002",
            Lo3ElementEnum.ELEMENT_1320,
            "19400101",
            Lo3ElementEnum.ELEMENT_1330,
            "regel1",
            Lo3ElementEnum.ELEMENT_1340,
            "regel2",
            Lo3ElementEnum.ELEMENT_1350,
            "regel3",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1310,
            "",
            Lo3ElementEnum.ELEMENT_1320,
            "",
            Lo3ElementEnum.ELEMENT_1330,
            "",
            Lo3ElementEnum.ELEMENT_1340,
            "",
            Lo3ElementEnum.ELEMENT_1350,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep14Immigratie() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE.getId()), "I")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId()), "5002")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1410,
            "5002",
            Lo3ElementEnum.ELEMENT_1420,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1410,
            "",
            Lo3ElementEnum.ELEMENT_1420,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep72AdresaangifteAangever() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "P")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "naam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "P",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "I",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep72AdresaangifteReden() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metDatumAanvangGeldigheid(19400101)
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "naam")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "B")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "T",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "I",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep75DocumentindicatieTrue() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "051801")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(groepElement)
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord indicatieIdentiteitRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG.getId()))
                          .metId(95)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metAttribuut(
                              ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_SOORTNAAM.getId()),
                              SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG.toString())
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord indicatieStandaardRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG.getId()))
                          .metId(95)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE.getId()), true)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                    persoonAdder.voegPersoonMutatieToe(indicatieIdentiteitRecord)
                                .voegPersoonMutatieToe(indicatieStandaardRecord)
                                .voegPersoonMutatieToe(mutatieRecord)
                                .build(),
                        0L);

        logMetaObject(persoon.getMetaObject());

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_7510,
            "1",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_7510,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep75DocumentindicatieFalse() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
        final MetaRecord mutatieRecord =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(groepElement)
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                          .metDatumAanvangGeldigheid(19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "051801")
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
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }
}
