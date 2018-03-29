/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

/**
 * Test conversie van blob mbt compleetheid van mapping + correctheid datatypering en attribuutwaarden.
 */
@RunWith(MockitoJUnitRunner.class)
public class BlobTerugConversieTest {


    private static final Set<AttribuutElement> NIET_VERWACHTE_ATTRIBUTEN = Sets.newHashSet(
            Element.PERSOON_GESLACHTSNAAMCOMPONENT_PERSOONGESLACHTSNAAMCOMPONENT,
            Element.PERSOON_INDICATIE_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_ONDERCURATELE_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_STAATLOOS_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_PERSOONINDICATIE,
            Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_PERSOONINDICATIE,
            Element.PERSOON_NATIONALITEIT_PERSOONNATIONALITEIT,
            Element.PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT,
            Element.PERSOON_VOORNAAM_PERSOONVOORNAAM,
            Element.PERSOON_ADRES_PERSOONADRES,
            Element.PERSOON_VERSTREKKINGSBEPERKING_PERSOONVERSTREKKINGSBEPERKING,
            Element.GERELATEERDEKIND_BETROKKENHEID,
            Element.GERELATEERDEKIND_INDICATIEACTUEELENGELDIG,
            Element.GERELATEERDEKIND_INDICATIEACTUEELENGELDIGOUDERSCHAP,
            Element.GERELATEERDEKIND_PERSOON_INDICATIEACTUEELENGELDIGIDENTIFICATIENUMMERS,
            Element.GERELATEERDEKIND_PERSOON_INDICATIEACTUEELENGELDIGSAMENGESTELDENAAM,
            Element.GERELATEERDEKIND_PERSOON_INDICATIEACTUEELENGELDIGGEBOORTE,
            Element.GERELATEERDEHUWELIJKSPARTNER_BETROKKENHEID,
            Element.GERELATEERDEHUWELIJKSPARTNER_INDICATIEACTUEELENGELDIG,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_INDICATIEACTUEELENGELDIGGEBOORTE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_INDICATIEACTUEELENGELDIGGESLACHTSAANDUIDING,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_INDICATIEACTUEELENGELDIGIDENTIFICATIENUMMERS,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_INDICATIEACTUEELENGELDIGSAMENGESTELDENAAM,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_BETROKKENHEID,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_INDICATIEACTUEELENGELDIG,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_INDICATIEACTUEELENGELDIGGEBOORTE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_INDICATIEACTUEELENGELDIGGESLACHTSAANDUIDING,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_INDICATIEACTUEELENGELDIGIDENTIFICATIENUMMERS,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_INDICATIEACTUEELENGELDIGSAMENGESTELDENAAM,
            Element.OUDER_OUDERSCHAP_BETROKKENHEID,
            Element.OUDER_OUDERLIJKGEZAG_BETROKKENHEID,
            Element.PERSOON_OUDER_BETROKKENHEID,
            Element.PERSOON_OUDER_OUDERSCHAP_BETROKKENHEID,
            Element.GERELATEERDEOUDER_BETROKKENHEID,
            Element.GERELATEERDEOUDER_INDICATIEACTUEELENGELDIG,
            Element.GERELATEERDEOUDER_INDICATIEACTUEELENGELDIGOUDERSCHAP,
            Element.GERELATEERDEOUDER_INDICATIEACTUEELENGELDIGOUDERLIJKGEZAG,
            Element.GERELATEERDEOUDER_PERSOON_INDICATIEACTUEELENGELDIGIDENTIFICATIENUMMERS,
            Element.GERELATEERDEOUDER_PERSOON_INDICATIEACTUEELENGELDIGSAMENGESTELDENAAM,
            Element.GERELATEERDEOUDER_PERSOON_INDICATIEACTUEELENGELDIGGESLACHTSAANDUIDING,
            Element.GERELATEERDEOUDER_PERSOON_INDICATIEACTUEELENGELDIGGEBOORTE,
            Element.PERSOON_PARTNER_BETROKKENHEID,
            Element.PERSOON_PARTNER_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_KIND_BETROKKENHEID,
            Element.KIND_RELATIE,
            Element.OUDER_RELATIE,
            Element.PARTNER_RELATIE,
            Element.FAMILIERECHTELIJKEBETREKKING_RELATIE,
            Element.GEREGISTREERDPARTNERSCHAP_RELATIE,
            Element.HUWELIJK_RELATIE,
            Element.ONDERZOEK_ONDERZOEK,
            Element.GEGEVENINONDERZOEK_ONDERZOEK,
            Element.GEGEVENINONDERZOEK_GEGEVENINONDERZOEK,
            Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL,
            Element.PERSOON_AFNEMERINDICATIE_PERSOONAFNEMERINDICATIE,
            Element.PERSOON_BUITENLANDSPERSOONSNUMMER_PERSOONBUITENLANDSPERSOONSNUMMER,
            //INDICATIEACTUEELENGELDIG is niet relevant voor de BLOB (dit is een A-laag gegeven, de BLOB werkt enkel met de HIS-laag)
            Element.ONDERZOEK_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIEACTUEELENGELDIGAFGELEIDADMINISTRATIEF,
            Element.PERSOON_INDICATIEACTUEELENGELDIGIDENTIFICATIENUMMERS,
            Element.PERSOON_INDICATIEACTUEELENGELDIGSAMENGESTELDENAAM,
            Element.PERSOON_INDICATIEACTUEELENGELDIGGEBOORTE,
            Element.PERSOON_INDICATIEACTUEELENGELDIGGESLACHTSAANDUIDING,
            Element.PERSOON_INDICATIEACTUEELENGELDIGINSCHRIJVING,
            Element.PERSOON_INDICATIEACTUEELENGELDIGNUMMERVERWIJZING,
            Element.PERSOON_INDICATIEACTUEELENGELDIGBIJHOUDING,
            Element.PERSOON_INDICATIEACTUEELENGELDIGOVERLIJDEN,
            Element.PERSOON_INDICATIEACTUEELENGELDIGNAAMGEBRUIK,
            Element.PERSOON_INDICATIEACTUEELENGELDIGMIGRATIE,
            Element.PERSOON_INDICATIEACTUEELENGELDIGVERBLIJFSRECHT,
            Element.PERSOON_INDICATIEACTUEELENGELDIGUITSLUITINGKIESRECHT,
            Element.PERSOON_INDICATIEACTUEELENGELDIGDEELNAMEEUVERKIEZINGEN,
            Element.PERSOON_INDICATIEACTUEELENGELDIGPERSOONSKAART,
            Element.PERSOON_ADRES_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_AFNEMERINDICATIE_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_GESLACHTSNAAMCOMPONENT_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_NATIONALITEIT_INDICATIEACTUEELENGELDIG,
            Element.GEGEVENINONDERZOEK_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_REISDOCUMENT_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_VERIFICATIE_PERSOONVERIFICATIE,
            Element.PERSOON_VERIFICATIE_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_VERSTREKKINGSBEPERKING_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_VOORNAAM_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_BUITENLANDSPERSOONSNUMMER_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_ONDERCURATELE_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_STAATLOOS_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_INDICATIEACTUEELENGELDIG,

            Element.PERSOON_KIND_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_KIND_INDICATIEACTUEELENGELDIGOUDERSCHAP,
            Element.PERSOON_KIND_INDICATIEACTUEELENGELDIGOUDERLIJKGEZAG,
            Element.PERSOON_OUDER_INDICATIEACTUEELENGELDIG,
            Element.PERSOON_OUDER_INDICATIEACTUEELENGELDIGOUDERSCHAP,
//            Element.OUDER_OUDERSCHAP_INDICATIEACTUEELENGELDIG,
//            Element.OUDER_OUDERSCHAP_INDICATIEACTUEELENGELDIGOUDERSCHAP,
//            Element.OUDER_OUDERSCHAP_INDICATIEACTUEELENGELDIGOUDERLIJKGEZAG,
            Element.PARTNER_INDICATIEACTUEELENGELDIG,
            Element.PARTNER_INDICATIEACTUEELENGELDIGOUDERLIJKGEZAG,
            Element.PARTNER_INDICATIEACTUEELENGELDIGOUDERSCHAP,
            Element.FAMILIERECHTELIJKEBETREKKING_INDICATIEACTUEELENGELDIG,
            Element.HUWELIJK_INDICATIEACTUEELENGELDIG,
            Element.GEREGISTREERDPARTNERSCHAP_INDICATIEACTUEELENGELDIG
    )
            .stream().map(Element -> getAttribuutElement(Element.getId())).collect
                    (Collectors.toSet());


    private static final Set<GroepElement> NIET_VERWACHTE_GROEPEN = Sets.newHashSet(
            Element.AANGEVER_IDENTITEIT,
            Element.ACTIE_IDENTITEIT,
            Element.BETROKKENHEID_IDENTITEIT,
            Element.BIJHOUDINGSPLAN_IDENTITEIT,
            Element.SOORTADMINISTRATIEVEHANDELING_IDENTITEIT,
            Element.PERSOON_INDICATIE_IDENTITEIT,
            Element.PREDICAAT_IDENTITEIT,
            Element.REDENEINDERELATIE_IDENTITEIT,
            Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT_IDENTITEIT,
            Element.REDENVERKRIJGINGNLNATIONALITEIT_IDENTITEIT,
            Element.REDENVERLIESNLNATIONALITEIT_IDENTITEIT,
            Element.REDENWIJZIGINGVERBLIJF_IDENTITEIT,
            Element.SOORTBETROKKENHEID_IDENTITEIT,
            Element.SOORTDOCUMENT_IDENTITEIT,
            Element.ADELLIJKETITEL_IDENTITEIT,
            Element.ELEMENT_IDENTITEIT,
            Element.SOORTADRES_IDENTITEIT,
            Element.BURGERZAKENMODULE_IDENTITEIT,
            Element.GESLACHTSAANDUIDING_IDENTITEIT,
            Element.LANDGEBIED_IDENTITEIT,
            Element.STELSEL_IDENTITEIT,
            Element.ROL_IDENTITEIT,
            Element.REGEL_IDENTITEIT,
            Element.ACTIEBRON_IDENTITEIT,
            Element.KOPPELVLAK_IDENTITEIT,
            Element.RECHTSGROND_IDENTITEIT,
            Element.SOORTELEMENTAUTORISATIE_IDENTITEIT,
            Element.NATIONALITEIT_IDENTITEIT,
            Element.PLAATS_IDENTITEIT,
            Element.SOORTELEMENT_IDENTITEIT,
            Element.SOORTACTIE_IDENTITEIT,
            Element.SOORTINDICATIE_IDENTITEIT,
            Element.SOORTMELDING_IDENTITEIT,
            Element.SOORTNEDERLANDSREISDOCUMENT_IDENTITEIT,
            Element.SOORTPERSOON_IDENTITEIT,
            Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_IDENTITEIT,
            Element.SOORTRELATIE_IDENTITEIT,
            Element.BIJHOUDINGSAARD_IDENTITEIT,
            Element.AANDUIDINGVERBLIJFSRECHT_IDENTITEIT,
            Element.CATEGORIEADMINISTRATIEVEHANDELING_IDENTITEIT,
            Element.NAAMGEBRUIK_IDENTITEIT,
            Element.GEDEBLOKKEERDEMELDING_IDENTITEIT,
            Element.GEMEENTE_IDENTITEIT,
            Element.STATUSONDERZOEK_IDENTITEIT,
            Element.NADEREBIJHOUDINGSAARD_IDENTITEIT,
            Element.SOORTMIGRATIE_IDENTITEIT,
            Element.SOORTPARTIJ_IDENTITEIT,
            Element.STATUSLEVERINGADMINISTRATIEVEHANDELING_IDENTITEIT,

            Element.PARTIJ_IDENTITEIT,
            Element.PARTIJ_STANDAARD,
            Element.RELATIE_IDENTITEIT,
            Element.RELATIE_STANDAARD,
            Element.DOCUMENT_IDENTITEIT,
            Element.ADMINISTRATIEVEHANDELING_IDENTITEIT,
            Element.ADMINISTRATIEVEHANDELING_STANDAARD,
            Element.PERSOON_INDICATIE_IDENTITEIT,
            Element.PERSOON_INDICATIE_STANDAARD,
            Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_IDENTITEIT,
            Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD,

            Element.GERELATEERDEOUDER_OUDERSCHAP,
            Element.GERELATEERDEOUDER_OUDERLIJKGEZAG,
            Element.ADMINISTRATIEVEHANDELING_LEVERING,
            Element.PARTIJ_BIJHOUDING,
            Element.PARTIJ_VRIJBERICHT,
            Element.SOORTVRIJBERICHT_IDENTITEIT,
            Element.AUTORITEITVANAFGIFTEBUITENLANDSPERSOONSNUMMER_IDENTITEIT,
            Element.AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_IDENTITEIT,
            Element.VERSIESTUFBG_IDENTITEIT,
            Element.VERTALINGBERICHTSOORTBRP_IDENTITEIT,
            Element.KIND_IDENTITEIT,
            Element.OUDER_IDENTITEIT,
            Element.OUDER_OUDERSCHAP,
            Element.OUDER_OUDERLIJKGEZAG,
            Element.PARTNER_IDENTITEIT,
            Element.VERWERKINGSRESULTAAT_IDENTITEIT,
            Element.VERWERKINGSWIJZE_IDENTITEIT,
            Element.VOORVOEGSEL_IDENTITEIT
    ).stream().map(Element -> ElementHelper.getGroepElement(Element.getId())).collect(Collectors.toSet());


    private static final Set<ObjectElement> NIET_VERWACHTE_OBJECTEN = Sets.newHashSet(
            Element.REDENEINDERELATIE,
            Element.SOORTELEMENT,
            Element.SOORTBETROKKENHEID,
            Element.KIND,
            Element.OUDER,
            Element.PARTNER,
            Element.CATEGORIEADMINISTRATIEVEHANDELING,
            Element.REDENVERKRIJGINGNLNATIONALITEIT,
            Element.NATIONALITEIT,
            Element.REDENVERLIESNLNATIONALITEIT,
            Element.BETROKKENHEID,
            Element.STELSEL,
            Element.PREDICAAT,
            Element.ADELLIJKETITEL,
            Element.KOPPELVLAK,
            Element.NAAMGEBRUIK,
            Element.BURGERZAKENMODULE,
            Element.REGEL,
            Element.GESLACHTSAANDUIDING,
            Element.PERSOON_INDICATIE,
            Element.ACTIEBRON,
            Element.SOORTMELDING,
            Element.SOORTADRES,
            Element.ADMINISTRATIEVEHANDELING,
            Element.RECHTSGROND,
            Element.DOCUMENT,
            Element.BIJHOUDINGSAARD,
            Element.SOORTPERSOON,
            Element.STATUSLEVERINGADMINISTRATIEVEHANDELING,
            Element.PARTIJ,
            Element.GEDEBLOKKEERDEMELDING,
            Element.SOORTELEMENTAUTORISATIE,
            Element.SOORTDOCUMENT,
            Element.REDENWIJZIGINGVERBLIJF,
            Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL,
            Element.SOORTNEDERLANDSREISDOCUMENT,
            Element.GEMEENTE,
            Element.HUWELIJKGEREGISTREERDPARTNERSCHAP,
            Element.AANGEVER,
            Element.LANDGEBIED,
            Element.ELEMENT,
            Element.STATUSONDERZOEK,
            Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT,
            Element.AANDUIDINGVERBLIJFSRECHT,
            Element.ROL,
            Element.BIJHOUDINGSPLAN,
            Element.SOORTADMINISTRATIEVEHANDELING,
            Element.RELATIE,
            Element.NADEREBIJHOUDINGSAARD,
            Element.SOORTACTIE,
            Element.SOORTRELATIE,
            Element.SOORTMIGRATIE,
            Element.SOORTPARTIJ,
            Element.SOORTINDICATIE,
            Element.ACTIE,
            Element.VERSIESTUFBG,
            Element.SOORTVRIJBERICHT,
            Element.VERTALINGBERICHTSOORTBRP,
            Element.AUTORITEITVANAFGIFTEBUITENLANDSPERSOONSNUMMER,
            Element.AUTORITEITTYPEVANAFGIFTEREISDOCUMENT,
            Element.VERWERKINGSWIJZE,
            Element.VERWERKINGSRESULTAAT,
            Element.VOORVOEGSEL,
            Element.PLAATS
    )
            .stream().map(Element -> ElementHelper.getObjectElement(Element.getId())).collect(Collectors.toSet());

    private static final Set<ObjectElement> VERWACHTE_OBJECTEN = Sets.difference(Sets.newHashSet(ElementHelper.getObjecten()), NIET_VERWACHTE_OBJECTEN);
    private static final Set<GroepElement> VERWACHTE_GROEPEN = Sets.difference(Sets.newHashSet(ElementHelper.getGroepen()), NIET_VERWACHTE_GROEPEN);

    @InjectMocks
    private PersoonslijstServiceImpl persoonslijstService;

    @Mock
    private PersoonCacheRepository persoonCacheRepository;

    @Before
    public void voorTest() throws BlobException {
        PersoonCache persoonCache = MaakPersoonsBeeldUtil.geefPersoonCache();
        Mockito.when(persoonCacheRepository.haalPersoonCacheOp(Mockito.anyLong())).thenReturn(persoonCache);
    }

    @Test
    public void testMapping() throws BlobException {
        final AfnemerindicatiesBlob afnemerindicatiesBlob = MaakPersoonsBeeldUtil.geefAfnemerindicatiesBlob();
        final PersoonBlob persoonBlob = MaakPersoonsBeeldUtil.geefPersoonBlob();

        List<BlobRecord> recordLijst = afnemerindicatiesBlob.getAfnemerindicaties().get(0).getRecordList();
        recordLijst.addAll(persoonBlob.getPersoonsgegevens().getRecordList());

        final List<MaakPersoonsBeeldUtil.MapInfo> attribuutMappingInfo = MaakPersoonsBeeldUtil.geefAttribuutMappingInfo();
        for (MaakPersoonsBeeldUtil.MapInfo mapInfo : attribuutMappingInfo) {
            final BlobRecord record = Iterables.find(recordLijst,
                    blobRecord -> blobRecord.getVoorkomenSleutel().equals(mapInfo.id.longValue())
                            && blobRecord.getGroepElementId().equals(mapInfo.groep.getId()), null);

            //record bestaat
            Assert.notNull(record, "Record kan niet gevonden worden:" + mapInfo.id + "," + mapInfo.groep);

            //groepelement en sleutels zijn gezet
            Assert.notNull(record.getGroepElementId(), "groep element ontbreekt " + record);
            Assert.notNull(record.getVoorkomenSleutel(), "voorkomensleutel ontbreekt " + record);
            Assert.notNull(record.getObjectElementId(), "objectelement ontbreekt " + record);
            Assert.notNull(record.getObjectSleutel(), "objectsleutel ontbreekt " + record);

            //waarden zijn conform historiepatroon
            switch (mapInfo.recordStatus) {
                case ACTUEEL:
                    if (mapInfo.recordStatus != MaakPersoonsBeeldUtil.RecordStatus.FORMEEL_ACTUEEL) {
                        assertEquals(19671223, record.getDatumAanvangGeldigheid().intValue());
                    }
                    assertNull(record.getDatumEindeGeldigheid());
                    assertEquals(1L, record.getActieInhoud().longValue());
                    assertNull(record.getActieAanpassingGeldigheid());
                    assertTrue(record.isIndicatieTbvLeveringMutaties());
                    assertNull(record.getNadereAanduidingVerval());
                    break;
                case BEEINDIGD:
                    assertEquals(19671223, record.getDatumAanvangGeldigheid().intValue());
                    assertEquals(19671224, record.getDatumEindeGeldigheid().intValue());
                    assertEquals(1L, record.getActieInhoud().longValue());
                    assertEquals(1L, record.getActieAanpassingGeldigheid().longValue());
                    assertNull(record.getActieVervalTbvLeveringMutaties());
                    assertNull(record.isIndicatieTbvLeveringMutaties());
                    assertNull(record.getNadereAanduidingVerval());
                    break;
                case VERVALLEN:
                    if (mapInfo.recordStatus != MaakPersoonsBeeldUtil.RecordStatus.FORMEEL_VERVALLEN) {
                        assertEquals(19671223, record.getDatumAanvangGeldigheid().intValue());
                    }
                    assertNull(record.getDatumEindeGeldigheid());
                    assertEquals(1L, record.getActieInhoud().longValue());
                    assertEquals(1L, record.getActieVervalTbvLeveringMutaties().longValue());
                    assertNull(record.getActieAanpassingGeldigheid());
                    assertNull(record.isIndicatieTbvLeveringMutaties());
                    assertEquals("S", record.getNadereAanduidingVerval());
                    break;
            }
        }
    }

    @Test
    public void testObjectCompleetheidMapping() throws BlobException {
        final Persoonslijst persoonslijst = persoonslijstService.getById(1);
        final Set<MetaObject> objecten = Sets.newHashSet(persoonslijst.getModelIndex().geefObjecten());
        final Set<ObjectElement> actueleObjectElementen = objecten.stream().map(MetaObject::getObjectElement).collect
                (Collectors.toSet());
        final Set<ObjectElement> objectenNietGemapped = Sets.difference(VERWACHTE_OBJECTEN, actueleObjectElementen);
        if (!objectenNietGemapped.isEmpty()) {
            throw new AssertionError(String.format("De volgende %d objecten zijn niet gemapped: %s",
                    objectenNietGemapped.size(), objectenNietGemapped));
        }
        final Set<ObjectElement> objectenWelGemappedNietVerwacht = Sets.difference(actueleObjectElementen, VERWACHTE_OBJECTEN);
        if (!objectenWelGemappedNietVerwacht.isEmpty()) {
            throw new AssertionError("De volgende objecten zijn wel gemapped, maar niet verwacht " + "(test-inconsistentie): " +
                    objectenWelGemappedNietVerwacht);
        }
    }

    @Test
    public void testGroepCompleetheidMapping() {
        final Persoonslijst persoonslijst = persoonslijstService.getById(1);
        final Set<MetaGroep> groepen = Sets.newHashSet(persoonslijst.getModelIndex().geefGroepen());
        final Set<GroepElement> actueleGroepElementen = groepen.stream().map(MetaGroep::getGroepElement).collect
                (Collectors.toSet());
        final Set<GroepElement> groepenNietGemapped = Sets.difference(VERWACHTE_GROEPEN, actueleGroepElementen);

        if (!groepenNietGemapped.isEmpty()) {
            throw new AssertionError(String.format("De volgende %d groepen zijn niet gemapped: %s",
                    groepenNietGemapped.size(), groepenNietGemapped));
        }
        final Set<GroepElement> groepenWelGemappedNietVerwacht = Sets.difference(actueleGroepElementen, VERWACHTE_GROEPEN);
        if (!groepenWelGemappedNietVerwacht.isEmpty()) {
            throw new AssertionError("De volgende groepen zijn wel gemapped, maar niet verwacht (test-inconsistentie)" +
                    ": " +
                    groepenWelGemappedNietVerwacht);
        }
    }


    @Test
    public void testAttribuutCompleetheidMapping() {
        final Persoonslijst persoonslijst = persoonslijstService.getById(1);
        final Set<AttribuutElement> actueleAttributen = persoonslijst.getModelIndex().geefAttribuutElementen();
        final Set<AttribuutElement> expectedAttributen = Sets.newHashSet();
        for (GroepElement groepElement : VERWACHTE_GROEPEN) {
            expectedAttributen.addAll(Sets.newHashSet(groepElement.getAttributenInGroep()));
        }
        final Iterator<AttribuutElement> iterator = expectedAttributen.iterator();
        while (iterator.hasNext()) {
            AttribuutElement next = iterator.next();
            //terug referenties naar persoon negeren
            final Set<AttribuutElement> uitzonderingen = new HashSet<>();
            uitzonderingen.add(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId()));
            uitzonderingen.add(getAttribuutElement(Element.PERSOON_VERIFICATIE_PERSOON.getId()));
            boolean skip = !uitzonderingen.contains(next)
                    && "Persoon".equals(next.getElementNaam());

            //negeer voorkomensleutel en objectsleutel
            skip |= "ID".equals(next.getElementNaam());
            //filter attributen
            skip |= NIET_VERWACHTE_ATTRIBUTEN.contains(next);

            if (skip) {
                iterator.remove();
            }
        }

        final Set<AttribuutElement> attributenNietGemapped = Sets.newHashSet(expectedAttributen);
        attributenNietGemapped.removeAll(actueleAttributen);
        if (!attributenNietGemapped.isEmpty()) {
            throw new AssertionError(String.format("De volgende %d attributen zijn niet gemapped: %s",
                    attributenNietGemapped.size(),
                    attributenNietGemapped));
        }
        final Set<AttribuutElement> attributenWelGemappedNietVerwacht = Sets.newHashSet(actueleAttributen);
        attributenWelGemappedNietVerwacht.removeAll(expectedAttributen);
        if (!attributenWelGemappedNietVerwacht.isEmpty()) {
            throw new AssertionError("De volgende attributen zijn wel gemapped, maar niet verwacht " +
                    "(test-inconsistentie): " +
                    attributenWelGemappedNietVerwacht);
        }
    }

    @Test
    public void testTerugConversieUitBlob() throws IOException {
        final Persoonslijst persoonslijst = persoonslijstService.getById(1);
        final String afdruk = removeLineEndings(ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject()));
        final String bewaardeAfdruk = removeLineEndings(
                IOUtils.toString(BlobTerugConversieTest.class.getResourceAsStream("/data/terugconversie.txt")));
        System.out.println(ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject()));
        org.junit.Assert.assertEquals(bewaardeAfdruk, afdruk);
    }

    private String removeLineEndings(String value) {
        return value.replace("\n", "").replace("\r", "");
    }


}
