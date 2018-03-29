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
 * Reisdocument.
 */
public class MutatieCategorie12IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep35NederlandsReisdocument() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement reisdocumentIdentiteitGroep = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT.getId());
        final MetaRecord reisdocumentIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId()))
                        .metGroep()
                        .metGroepElement(reisdocumentIdentiteitGroep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_SOORTCODE.getId()), "PN")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(reisdocumentIdentiteitGroep)
                        .getRecords()
                        .iterator()
                        .next();

        final GroepElement reisdocumentStandaardGroep = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId());
        final MetaRecord reisdocumentStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId()))
                        .metGroep()
                        .metGroepElement(reisdocumentStandaardGroep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE.getId()), "1234")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT.getId()), 19550101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId()), 19400202)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE.getId()), 19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_NUMMER.getId()), "123")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(reisdocumentStandaardGroep)
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                        persoonAdder.voegPersoonMutatieToe(reisdocumentIdentiteitRecord).voegPersoonMutatieToe(reisdocumentStandaardRecord).build(),
                        0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_12,
                true,
                Lo3ElementEnum.ELEMENT_3510,
                "PN",
                Lo3ElementEnum.ELEMENT_3520,
                "123",
                Lo3ElementEnum.ELEMENT_3530,
                "19400101",
                Lo3ElementEnum.ELEMENT_3540,
                "1234",
                Lo3ElementEnum.ELEMENT_3550,
                "19550101",
                Lo3ElementEnum.ELEMENT_8510,
                "19400202",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_62,
                true,
                Lo3ElementEnum.ELEMENT_3510,
                "",
                Lo3ElementEnum.ELEMENT_3520,
                "",
                Lo3ElementEnum.ELEMENT_3530,
                "",
                Lo3ElementEnum.ELEMENT_3540,
                "",
                Lo3ElementEnum.ELEMENT_3550,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep36Signalering() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final MetaRecord indicatieIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(
                                ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_SOORTNAAM.getId()),
                                SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(
                                ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE.getId()),
                                true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon =
                new Persoonslijst(
                        persoonAdder.voegPersoonMutatieToe(indicatieIdentiteitRecord).voegPersoonMutatieToe(indicatieStandaardRecord).build(),
                        0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_12,
                true,
                Lo3ElementEnum.ELEMENT_3610,
                "1",
                Lo3ElementEnum.ELEMENT_8510,
                "19400102",
                Lo3ElementEnum.ELEMENT_8610,
                "19400102");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_62,
                true,
                Lo3ElementEnum.ELEMENT_3610,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement reisdocumentIdentiteitGroep = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT.getId());
        final MetaRecord reisdocumentIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId()))
                        .metGroep()
                        .metGroepElement(reisdocumentIdentiteitGroep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_SOORTCODE.getId()), "PN")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(reisdocumentIdentiteitGroep)
                        .getRecords()
                        .iterator()
                        .next();

        final GroepElement reisdocumentStandaardGroep = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId());
        final MetaRecord reisdocumentStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId()))
                        .metGroep()
                        .metGroepElement(reisdocumentStandaardGroep)
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE.getId()), "1234")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT.getId()), 19550101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId()), 19400202)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE.getId()), 19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_NUMMER.getId()), "123")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(reisdocumentStandaardGroep)
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieIdentiteitRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(
                                ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_SOORTNAAM.getId()),
                                SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT.toString())
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final MetaRecord indicatieStandaardRecord =
                MetaObject.maakBuilder()
                        .metObjectElement(
                                ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId()))
                        .metId(95)
                        .metGroep()
                        .metGroepElement(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE.getId()),
                                true)
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(
                                ElementHelper.getGroepElement(
                                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        final Persoonslijst persoon = new Persoonslijst(
                persoonAdder.voegPersoonMutatieToe(indicatieIdentiteitRecord)
                        .voegPersoonMutatieToe(indicatieStandaardRecord)
                        .voegPersoonMutatieToe(reisdocumentIdentiteitRecord)
                        .voegPersoonMutatieToe(reisdocumentStandaardRecord)
                        .build(),
                0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        Assert.assertEquals(4, resultaat.size());
    }
}
