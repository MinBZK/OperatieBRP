/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.mutatie.AbstractMutatieConverteerderIntegratieTest;
import nl.bzk.brp.levering.lo3.conversie.mutatie.RelatieBepaler;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatieBepalerTest extends AbstractMutatieConverteerderIntegratieTest {

    private final RelatieBepaler subject = new RelatieBepaler();

    private MetaObject persoon;

    @Before
    public void setup() {

        final AdministratieveHandeling handelingAanvang = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(12L, "000034", null, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
                .metObject(TestVerantwoording.maakActieBuilder(1002L, SoortActie.CONVERSIE_GBA, ZonedDateTime.of(1962, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", 19620101)
                ).build());

        final AdministratieveHandeling handelingEinde = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(13L, "000034", null, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
                .metObject(TestVerantwoording.maakActieBuilder(1003L, SoortActie.CONVERSIE_GBA, ZonedDateTime.of(1963, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", 19630101)
                ).build());

        persoon = maakPersoon(2, handelingAanvang, handelingEinde);
    }

    private MetaObject maakPersoon(final int id, final AdministratieveHandeling handelingAanvang, final AdministratieveHandeling handelingEinde) {
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(maakBasisPersoon(100));

        // Partner toevoegen
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                        .metId(11)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(handelingAanvang.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_PARTNER_ROLCODE.getId()), "PARTNER")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        // Partnerschap
        final MetaObject.Builder metaObject2Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .metId(id)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_SOORTCODE.getId()), "H")
                        .eindeRecord()
                        .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                        .metId(11)
                        .metObject(metaObject2Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject3Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .metId(id)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(handelingAanvang.getActies().iterator().next())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG.getId()),
                                handelingAanvang.getActies().iterator().next().getDatumOntlening())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE.getId()), 433)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE.getId()), 6030)
                        .eindeRecord()
                        .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                        .metId(11)
                        .metObject(metaObject3Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject4Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER.getId()))
                        .metId(130)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(handelingAanvang.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_ROLCODE.getId()), "PARTNER")
                        .eindeRecord()
                        .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .metId(id)
                        .metObject(metaObject4Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject5Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON.getId()))
                        .metId(idTeller.getAndIncrement())
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SOORTCODE.getId()), "O")
                        .eindeRecord()
                        .eindeGroep();

        voegGerelateerdeGeregistreerdePartnerIdentificatieNummersToe(
                metaObject5Builder,
                handelingAanvang.getActies().iterator().next(),
                19400101,
                "1231231234",
                "345345345");
        voegGerelateerdeGeregistreerdePartnerGeboorteToe(metaObject5Builder, handelingAanvang.getActies().iterator().next(), 19200202, "0599", "6030");
        voegGerelateerdeGeregistreerdePartnerGeslachtsaanduidingToe(metaObject5Builder, handelingAanvang.getActies().iterator().next(), 19400101, "V");
        voegGerelateerdeGeregistreerdePartnerSamengesteldeNaamToe(
                metaObject5Builder,
                handelingAanvang.getActies().iterator().next(),
                19400101,
                "Maria",
                "los",
                " ",
                "Pallo");

        // Huwelijk
        final MetaObject.Builder metaObject20Builder = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                .metId(id + 1)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.HUWELIJK_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.HUWELIJK_SOORTCODE.getId()), "H")
                .eindeRecord()
                .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId()))
                        .metId(11)
                        .metObject(metaObject20Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.HUWELIJK_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject30Builder = MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                .metId(id + 1)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(handelingEinde.getActies().iterator().next())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMAANVANG.getId()),
                        handelingAanvang.getActies().iterator().next().getDatumOntlening())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE.getId()),
                        433)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDAANVANGCODE.getId()),
                        6030)
                .eindeRecord()
                .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(Element.PERSOON_PARTNER)
                        .metId(11)
                        .metObject(metaObject30Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject40Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                        .metId(130)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(handelingEinde.getActies().iterator().next())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_ROLCODE.getId()), "PARTNER")
                        .eindeRecord()
                        .eindeGroep();
        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.HUWELIJK.getId()))
                        .metId(id + 1)
                        .metObject(metaObject40Builder)
                        .build()
                        .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                        .iterator()
                        .next()
                        .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        final MetaObject.Builder metaObject50Builder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                        .metId(idTeller.getAndIncrement())
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE.getId()), "O")
                        .eindeRecord()
                        .eindeGroep();

        voegGerelateerdeHuwelijkspartnerIdentificatieNummersToe(
                metaObject50Builder,
                handelingEinde.getActies().iterator().next(),
                19400101,
                "1231231234",
                "345345345");
        voegGerelateerdeHuwelijkspartnerGeboorteToe(metaObject50Builder, handelingEinde.getActies().iterator().next(), 19200202, "0599", "6030");
        voegGerelateerdeHuwelijkspartnerGeslachtsaanduidingToe(metaObject50Builder, handelingEinde.getActies().iterator().next(), 19400101, "V");
        voegGerelateerdeHuwelijkspartnerSamengesteldeNaamToe(
                metaObject50Builder,
                handelingEinde.getActies().iterator().next(),
                19400101,
                "Maria",
                "los",
                " ",
                "Pallo");

        persoonAdder.voegPersoonMutatieToe(
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()))
                        .metId(id)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(handelingEinde.getActies().iterator().next())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE.getId()),
                                handelingEinde.getActies().iterator().next().getDatumOntlening())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE.getId()), 433)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE.getId()), 6030)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE.getId()), "Z")
                        .eindeRecord()
                        .eindeGroep()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next());

        MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                .metId(130)
                .metObject(metaObject50Builder)
                .build()
                .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                .iterator()
                .next()
                .getGroepen()
                .forEach(g -> g.getRecords().forEach(persoonAdder::voegPersoonMutatieToe));

        MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER.getId()))
                .metId(13)
                .metObject(metaObject5Builder)
                .build()
                .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON.getId()))
                .iterator()
                .next()
                .getGroepen()
                .forEach(g -> g.getRecords().forEach(persoonAdder::voegPersoonMutatieToe));

        logMetaObject(persoonAdder.build());

        return persoonAdder.build();
    }

    @Test
    public void testOmzetting() {
        final Collection<MetaObject> huwelijken = new ArrayList<>();
        final Collection<MetaObject> partnerschappen = new ArrayList<>();

        // Eerst alle huwelijken en partnerschappen verzamelen (verschillende ik-betrokkenheden)
        for (final MetaObject mijnPartnerBetrokkenheid : MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.PARTNER_ELEMENT)) {
            huwelijken.addAll(MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.HUWELIJK_ELEMENT));
            partnerschappen.addAll(MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.GEREGISTREERD_PARTNERSCHAP_ELEMENT));
        }

        final Map<Long, String> resultaat = subject.bepaalRelatieMapping(huwelijken, partnerschappen);
        Assert.assertEquals(2, resultaat.size());
        Assert.assertEquals("2+3", resultaat.get(2L));
    }
}
