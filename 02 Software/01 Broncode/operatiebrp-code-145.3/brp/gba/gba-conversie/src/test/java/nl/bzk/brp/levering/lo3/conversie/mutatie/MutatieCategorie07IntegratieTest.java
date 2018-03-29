/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;
import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
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
 * Inschrijving.
 */
public class MutatieCategorie07IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void testGroep67Opschorting() {
        LOG.info("testGroep67Opschorting");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(basisAdministratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19200101)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "051801")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElement)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final GroepElement groepElement2 = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
        final MetaRecord mutatieRecord2 =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement2)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metDatumAanvangGeldigheid(19400101)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "051801")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()), "M")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElement2)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6710, "19400101", Lo3ElementEnum.ELEMENT_6720, "M");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6710, "", Lo3ElementEnum.ELEMENT_6720, "");
        Assert.assertEquals(4, resultaat.size());
    }

    @Test
    public void testGroep68Opname() {
        LOG.info("testGroep68Opname");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_INSCHRIJVING.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUM.getId()), 19950304)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId()), 1)
                .metAttribuut(
                    ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL.getId()),
                    ZonedDateTime.of(1920, 1, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID))
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

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6810, "19950304");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6810, "19200101");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep69GemeentePk() {
        LOG.info("testGroep69GemeentePk");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_PERSOONSKAART.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getId()), "059901")
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

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6910, "0599");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6910, "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep70Geheim() {
        LOG.info("testGroep70Geheim");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final MetaRecord identiteitRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING.getId()))
                .metId(95)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                    ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_SOORTNAAM.getId()),
                    SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.toString())
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT.getId()))
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(identiteitRecord);

        final MetaRecord standaardRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING.getId()))
                .metId(95)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE.getId()), true)
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD.getId()))
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(standaardRecord);

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        logMetaObject(persoon.getMetaObject());

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_7010, "7");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_7010, "0");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep71Verificatie() {
        LOG.info("testGroep71Verificatie");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_VERIFICATIE.getId()))
                .metId(95)
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId()), "999999")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_SOORT.getId()), "verificatienaam")
                .eindeRecord()
                .eindeGroep()
                .build()
                .getGroep(groepElement)
                .getRecords()
                .iterator()
                .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord);

        final GroepElement groepElement2 = ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId());
        final MetaRecord mutatieRecord2 =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_VERIFICATIE.getId()))
                .metId(95)
                .metGroep()
                .metGroepElement(groepElement2)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_DATUM.getId()), 19500203)
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
            Lo3CategorieEnum.CATEGORIE_07,
            true,
            Lo3ElementEnum.ELEMENT_7110,
            "19500203",
            Lo3ElementEnum.ELEMENT_7120,
            "verificatienaam",
            Lo3ElementEnum.ELEMENT_8810,
            "9999");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_57,
            true,
            Lo3ElementEnum.ELEMENT_7110,
            "",
            Lo3ElementEnum.ELEMENT_7120,
            "",
            Lo3ElementEnum.ELEMENT_8810,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep80Synchroniciteit() {
        LOG.info("testGroep80Synchroniciteit");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_INSCHRIJVING.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUM.getId()), 19200101)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL.getId()),
                    DatumUtil.vanDateNaarZonedDateTime(new Date(2336239723L)))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId()), 2)
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
            Lo3CategorieEnum.CATEGORIE_07,
            true,
            Lo3ElementEnum.ELEMENT_8010,
            "0002",
            Lo3ElementEnum.ELEMENT_8020,
            "19700128005719723");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_57,
            true,
            Lo3ElementEnum.ELEMENT_8010,
            "0001",
            Lo3ElementEnum.ELEMENT_8020,
            "19200101000000000");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep87PkConversie() {
        LOG.info("testGroep87PkConversie");
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(idTeller.getAndIncrement()));

        final GroepElement groepElement = ElementHelper.getGroepElement(Element.PERSOON_PERSOONSKAART.getId());
        final MetaRecord mutatieRecord =
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                .metGroepElement(groepElement)
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(administratieveHandeling.getActies().iterator().next())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD.getId()), true)
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

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_8710, "P");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_8710, "");
        Assert.assertEquals(2, resultaat.size());
    }
}
