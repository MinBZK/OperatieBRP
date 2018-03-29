/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.levering.lo3.builder.LeesbaarBlobRecord;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;

public final class MetaObjectUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final AtomicInteger idTeller = new AtomicInteger(123);

    private static final AdministratieveHandeling handeling;
    public static final Actie actie;

    static {
        final ZonedDateTime tsReg = ZonedDateTime.of(2014, 1, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1L, "000034", tsReg, SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG)
                .metObject(TestVerantwoording.maakActieBuilder(idTeller.getAndIncrement(), SoortActie.CONVERSIE_GBA, tsReg, "000034", 0)
                ).build();
        handeling = AdministratieveHandeling.converter().converteer(ah);
        actie = handeling.getActies().iterator().next();
    }

    public static MetaObject.Builder maakIngeschrevene() {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), "I")
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakIngeschrevene(final Supplier<MetaObject.Builder> childObjectBuilder) {
        return maakIngeschrevene(Collections.singletonList(childObjectBuilder), Collections.emptyList());
    }

    public static MetaObject.Builder maakIngeschrevene(final Consumer<MetaObject.Builder> persoonGroepBuilder) {
        return maakIngeschrevene(Collections.emptyList(), Collections.singletonList(persoonGroepBuilder));
    }

    public static MetaObject.Builder maakIngeschrevene(
            final Supplier<MetaObject.Builder> childObjectBuilder,
            final Consumer<MetaObject.Builder> persoonGroepBuilder) {
        return maakIngeschrevene(Collections.singletonList(childObjectBuilder), Collections.singletonList(persoonGroepBuilder));
    }

    public static MetaObject.Builder maakIngeschrevene(
            final Collection<Supplier<MetaObject.Builder>> childObjectBuilders,
            final Collection<Consumer<MetaObject.Builder>> persoonGroepBuilders) {
        final int persoonId = idTeller.getAndIncrement();
        final MetaObject.Builder persoon =
                MetaObject.maakBuilder().metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(persoonId);

        childObjectBuilders.forEach(childObjectBuilder -> persoon.metObject(childObjectBuilder.get()));

        persoon.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), "I")
                .eindeRecord()
                .eindeGroep();

        persoon.eindeObject();

        persoonGroepBuilders.forEach(groepBuilder -> {
            groepBuilder.accept(persoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(persoonId));
            persoon.eindeObject();
        });

        return persoon;
    }

    public static MetaObject.Builder maakPersoonIndicatieBehandeldAlsNederlander() {
        return maakPersoonIndicatieBehandeldAlsNederlander(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieBehandeldAlsNederlander(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_SOORTNAAM.getId()),
                        SoortIndicatie.BEHANDELD_ALS_NEDERLANDER.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieBijzondereVerblijfsrechtelijkePositie() {
        return maakPersoonIndicatieBijzondereVerblijfsrechtelijkePositie(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieBijzondereVerblijfsrechtelijkePositie(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(
                        ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_SOORTNAAM.getId()),
                        SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE.getId()),
                        waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieDerdeHeeftGezag() {
        return maakPersoonIndicatieDerdeHeeftGezag(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieDerdeHeeftGezag(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM.getId()),
                        SoortIndicatie.DERDE_HEEFT_GEZAG.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieOnderCuratele() {
        return maakPersoonIndicatieOnderCuratele(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieOnderCuratele(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM.getId()),
                        SoortIndicatie.ONDER_CURATELE.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieOnverwerktDocumentAanwezig() {
        return maakPersoonIndicatieOnverwerktDocumentAanwezig(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieOnverwerktDocumentAanwezig(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_SOORTNAAM.getId()),
                        SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() {
        return maakPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(
                        ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId()))
                .metId(idTeller.getAndIncrement())
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
                .metGroep()
                .metGroepElement(
                        ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(
                                Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE.getId()),
                        waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieStaatloos() {
        return maakPersoonIndicatieStaatloos(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieStaatloos(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_STAATLOOS.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_STAATLOOS_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_STAATLOOS_SOORTNAAM.getId()),
                        SoortIndicatie.STAATLOOS.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_STAATLOOS_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_STAATLOOS_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieVastgesteldNietNederlander() {
        return maakPersoonIndicatieVastgesteldNietNederlander(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieVastgesteldNietNederlander(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_SOORTNAAM.getId()),
                        SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonIndicatieVerstrekkingsbeperking() {
        return maakPersoonIndicatieVerstrekkingsbeperking(true);
    }

    public static MetaObject.Builder maakPersoonIndicatieVerstrekkingsbeperking(final Boolean waarde) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_SOORTNAAM.getId()),
                        SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE.getId()), waarde)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObjectAdder maakPersoonOnderzoek(
            final MetaObjectAdder persoonAdder,
            final Element gegevenElementNaam,
            final short gegevenVoorkomenSleutel,
            final String omschrijving) {
        final MetaObject.Builder gegevenInOnderzoekBuilder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.GEGEVENINONDERZOEK.getId()))
                        .metId(idTeller.getAndIncrement())
                        .metGroep()
                        .metGroepElement(
                                ElementHelper.getGroepElement(nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(idTeller.getAndIncrement())
                        .metActieInhoud(actie)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId()), gegevenElementNaam.getNaam())
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId()),
                                gegevenVoorkomenSleutel)
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
                        .metActieInhoud(actie)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG.getId()), 20141004)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING.getId()), omschrijving)
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

        return persoonAdder.voegPersoonMutatieToe(onderzoekIdentiteitRecord)
                .voegPersoonMutatieToe(onderzoekStandaardRecord)
                .voegPersoonMutatieToe(gegevenInOnderzoekRecord);

    }

    public static MetaObject.Builder maakPersoonReisdocument(
            final String soortCode,
            final String aanduidingInhoudingVermissingCode,
            final String autoriteitVanAfgifte,
            final Integer datumEindeDocument,
            final Integer datumIngangDocument,
            final Integer datumInhoudingVermissing,
            final Integer datumUitgifte,
            final String nummer) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_SOORTCODE.getId()), soortCode)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE.getId()),
                        aanduidingInhoudingVermissingCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE.getId()), autoriteitVanAfgifte)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT.getId()), datumEindeDocument)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId()), datumIngangDocument)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING.getId()),
                        datumInhoudingVermissing)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE.getId()), datumUitgifte)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_NUMMER.getId()), nummer)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonVerificatie(final Actie actie, final String partijCode, final String soort, final Integer datum) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_VERIFICATIE.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId()), partijCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_SOORT.getId()), soort)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_DATUM.getId()), datum)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonVoornaam(final Integer volgnummer, final String naam) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_VOORNAAM.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_VOORNAAM_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_VOLGNUMMER.getId()), volgnummer)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metDatumEindeGeldigheid(20131213)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_NAAM.getId()), naam)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonNationaliteit(
            final Actie actie,
            final String nationaliteitCode,
            final String redenVerkrijgingCode,
            final String redenVerliesCode) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId()), nationaliteitCode)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId()), redenVerkrijgingCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId()), redenVerliesCode)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonGeslachtsnaamcomponent(final Integer volgnummer, final String stam) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER.getId()), volgnummer)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metDatumEindeGeldigheid(20131213)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM.getId()), stam)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    private static MetaObject.Builder maakPersoonAdres(
            final Actie actie,
            final String aangeverAdresHoudingCode,
            final String afgekorteNaamOpenbareRuimte,
            final Integer datumAanvangAdresHouding,
            final String gemeenteCode,
            final Integer huisnummer,
            final String landGebied,
            final String naamOpenbareRuimte,
            final String postcode,
            final String redenWijziging,
            final String soort,
            final String woonplaatsnaam) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), aangeverAdresHoudingCode)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId()),
                        afgekorteNaamOpenbareRuimte)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "buitenland1")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId()), "buitenland2")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId()), "buitenland3")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId()), "buitenland4")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId()), "buitenland5")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId()), "buitenland6")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), datumAanvangAdresHouding)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), huisnummer)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "A")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT.getId()),
                        "adresseerbaarObject")
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId()),
                        "nummerAanduiding")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), landGebied)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES.getId()), "to")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getId()), "omschrijving locatie")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), naamOpenbareRuimte)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), postcode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), redenWijziging)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), soort)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), woonplaatsnaam)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonAdres(final Actie actie) {
        return maakPersoonAdres(actie, "I", "afgekorteNaam", 20130101, "0518", 10, "6030", "naam", "2245HJ", "P", "W", "Rotterdam");
    }

    public static MetaObject.Builder maakPersoonAdresLeeg() {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static MetaObject.Builder maakPersoonBuitenlandsPersoonsnummer() {
        return maakPersoonBuitenlandsPersoonsnummer("0054", "DAN-NR-1234567890");
    }

    public static MetaObject.Builder maakPersoonBuitenlandsPersoonsnummer(final String autoriteitVanAfgifteCode, final String nummer) {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId()))
                .metId(idTeller.getAndIncrement())
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId()),
                        autoriteitVanAfgifteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId()), nummer)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static void voegPersoonVerblijfsrechtGroepToe(
            final MetaObject.Builder builder,
            final String aanduidingCode,
            final Integer datumMededeling,
            final Integer datumVoorzienEinde) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_VERBLIJFSRECHT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE.getId()), aanduidingCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING.getId()), datumMededeling)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE.getId()), datumVoorzienEinde)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonUitsluitingKiesrechtGroepToe(
            final MetaObject.Builder builder,
            final Integer datumVoorzienEinde,
            final Boolean indicatie) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_UITSLUITINGKIESRECHT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE.getId()), datumVoorzienEinde)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE.getId()), indicatie)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonPersoonskaartGroepToe(
            final MetaObject.Builder builder,
            final Boolean indicatieVolledigGeconverteerd,
            final String partijCode) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_PERSOONSKAART.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD.getId()),
                        indicatieVolledigGeconverteerd)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getId()), partijCode)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonNummerverwijzingGroepToe(
            final MetaObject.Builder builder,
            final String volgendeAdministratienummer,
            final String vorigeAdministratienummer) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()),
                        volgendeAdministratienummer)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId()),
                        vorigeAdministratienummer)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonOverlijdenGroepToe(
            final MetaObject.Builder builder,
            final Actie actie,
            final String buitenlandsePlaats,
            final String buitenlandseRegio,
            final Integer datum,
            final String gemeenteCode,
            final String landGebiedCode,
            final String omschrijvingLocatie,
            final String woonplaatsnaam) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS.getId()), buitenlandsePlaats)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO.getId()), buitenlandseRegio)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId()), landGebiedCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE.getId()), omschrijvingLocatie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM.getId()), woonplaatsnaam)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonSamengesteldeNaamGroepToe(
            final MetaObject.Builder builder,
            final String adellijkeTitelCode,
            final String geslachtsnaamstam,
            final Boolean indicatieAfgeleid,
            final Boolean indicatieNamenreeks,
            final String predicaatCode,
            final String scheidingsteken,
            final String voornamen,
            final String voorvoegsel) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(456)
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()), adellijkeTitelCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), geslachtsnaamstam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID.getId()), indicatieAfgeleid)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS.getId()), indicatieNamenreeks)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE.getId()), predicaatCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornamen)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegVervallenPersoonSamengesteldeNaamGroepToe(
            final MetaObject.Builder builder,
            final String adellijkeTitelCode,
            final String geslachtsnaamstam,
            final Boolean indicatieAfgeleid,
            final Boolean indicatieNamenreeks,
            final String predicaatCode,
            final String scheidingsteken,
            final String voornamen,
            final String voorvoegsel) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(456)
                .metActieInhoud(actie)
                .metActieVerval(actie)
                .metActieVervalTbvLeveringMutaties(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()), adellijkeTitelCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), geslachtsnaamstam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID.getId()), indicatieAfgeleid)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS.getId()), indicatieNamenreeks)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE.getId()), predicaatCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornamen)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .metRecord()
                .metId(457)
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()), adellijkeTitelCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), geslachtsnaamstam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID.getId()), indicatieAfgeleid)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS.getId()), indicatieNamenreeks)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE.getId()), predicaatCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornamen)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonNaamgebruikGroepToe(
            final MetaObject.Builder builder,
            final String adellijkeTitelCode,
            final String code,
            final String geslachtsnaamstam,
            final Boolean indicatieAfgeleid,
            final String predicaatCode,
            final String scheidingsteken,
            final String voornamen,
            final String voorvoegsel) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE.getId()), adellijkeTitelCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_CODE.getId()), code)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM.getId()), geslachtsnaamstam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID.getId()), indicatieAfgeleid)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE.getId()), predicaatCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN.getId()), scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_VOORNAMEN.getId()), voornamen)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonMigratieGroepToe(
            final MetaObject.Builder builder,
            final String aangeverCode,
            final String buitenlandsAdresRegel1,
            final String buitenlandsAdresRegel2,
            final String buitenlandsAdresRegel3,
            final String buitenlandsAdresRegel4,
            final String buitenlandsAdresRegel5,
            final String buitenlandsAdresRegel6,
            final String landGebiedCode,
            final String redenWijzigingCode,
            final String soortCode) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_AANGEVERCODE.getId()), aangeverCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1.getId()), buitenlandsAdresRegel1)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2.getId()), buitenlandsAdresRegel2)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3.getId()), buitenlandsAdresRegel3)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4.getId()), buitenlandsAdresRegel4)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5.getId()), buitenlandsAdresRegel5)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6.getId()), buitenlandsAdresRegel6)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId()), landGebiedCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE.getId()), redenWijzigingCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE.getId()), soortCode)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonInschrijvingGroepToe(
            final MetaObject.Builder builder,
            final Integer datum,
            final Date datumTijdStempel,
            final Long versieNummer) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INSCHRIJVING.getId()))
                .metRecord()
                .metId(567)
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUM.getId()), datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL.getId()),
                        DatumUtil.vanDateNaarZonedDateTime(datumTijdStempel))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId()), versieNummer)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonIdentificatienummersGroepToe(
            final MetaObject.Builder builder,
            final String administratienummer,
            final String burgerservicenummer) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), administratienummer)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), burgerservicenummer)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonGeboorteGroepToe(
            final MetaObject.Builder builder,
            final Actie actie,
            final Integer datum,
            final String gemeenteCode,
            final String landGebiedCode) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), landGebiedCode)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonBijhoudingGroepToe(final MetaObject.Builder builder) {
        voegPersoonBijhoudingGroepToe(builder, "I");
    }

    public static void voegPersoonBijhoudingGroepToe(final MetaObject.Builder builder, final String waarde) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE.getId()), waarde)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonGeslachtsAanduidingGroepToe(final MetaObject.Builder builder) {
        voegPersoonGeslachtsAanduidingGroepToe(builder, "M");
    }

    public static void voegPersoonGeslachtsAanduidingGroepToe(final MetaObject.Builder builder, final String geslachtsAanduiding) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(20131212)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE.getId()), geslachtsAanduiding)
                .eindeRecord()
                .eindeGroep();
    }

    public static void voegPersoonAfgeleidAdministratiefGroepToe(final MetaObject.Builder persoonBuilder) {
        voegPersoonAfgeleidAdministratiefGroepToe(persoonBuilder, Date.from(actie.getTijdstipRegistratie().toInstant()));
    }

    public static void voegPersoonAfgeleidAdministratiefGroepToe(final MetaObject.Builder builder, final Date waarde) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId()),
                        DatumUtil.vanDateNaarZonedDateTime(waarde))
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId()),
                        DatumUtil.vanDateNaarZonedDateTime(waarde))
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
    }

    public static void voegPersoonDeelnameEUVerkiezingenGroepToe(final MetaObject.Builder builder) {
        voegPersoonDeelnameEUVerkiezingenGroepToe(builder, 20130101, true);
    }

    public static void voegPersoonDeelnameEUVerkiezingenGroepToe(
            final MetaObject.Builder builder,
            final Integer datumAanleidingAanpassing,
            final Boolean indicatieDeelname) {
        builder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING.getId()),
                        datumAanleidingAanpassing)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME.getId()), indicatieDeelname)
                .eindeRecord()
                .eindeGroep();
    }

    public static MetaObject logMetaObject(final MetaObject persoonObject) {
        // Print vanuit meta model
        final ObjectMapper mapper = new ObjectMapper();
        final SlaBijnaPlatVisitor slaPlatVisitor = new SlaBijnaPlatVisitor();
        slaPlatVisitor.visit(persoonObject);
        try {
            LOG.info("----------------BLOB-STRING---------------");
            LOG.info(mapper.writeValueAsString(slaPlatVisitor.recordsJson));
            LOG.info("------------EINDE-BLOB-STRING-------------");
        } catch (final IOException ex) {
            LOG.info("blob ging fout");
        }
        return persoonObject;
    }

    /**
     * Visitor om BlobRecords te maken.
     */
    private static class SlaBijnaPlatVisitor extends ParentFirstModelVisitor {
        private final List<LeesbaarBlobRecord> recordsJson = new LinkedList<>();

        @Override
        public void doVisit(final MetaRecord r) {
            final MetaObject object = r.getParentGroep().getParentObject();
            final LeesbaarBlobRecord blobRecord = new LeesbaarBlobRecord();
            blobRecord.setGroepElement(r.getParentGroep().getGroepElement().getNaam());
            blobRecord.setObjectElement(object.getObjectElement().getNaam());
            blobRecord.setVoorkomenSleutel(r.getVoorkomensleutel());
            blobRecord.setObjectSleutel(object.getObjectsleutel());

            if (object.getParentObject() != null) {
                blobRecord.setParentObjectElement(object.getParentObject().getObjectElement().getNaam());
                blobRecord.setParentObjectSleutel(object.getParentObject().getObjectsleutel());
            }

            if (r.getActieInhoud() != null) {
                blobRecord.setActieInhoud(r.getActieInhoud().getId());
            }
            if (r.getActieVerval() != null) {
                blobRecord.setActieVerval(r.getActieVerval().getId());
            }
            if (r.getActieAanpassingGeldigheid() != null) {
                blobRecord.setActieAanpassingGeldigheid(r.getActieAanpassingGeldigheid().getId());
            }
            if (r.getNadereAanduidingVerval() != null) {
                blobRecord.setNadereAanduidingVerval(r.getNadereAanduidingVerval().toCharArray()[0]);
            }
            if (r.getDatumAanvangGeldigheid() != null) {
                blobRecord.setDatumAanvangGeldigheid(r.getDatumAanvangGeldigheid());
            }
            if (r.getDatumEindeGeldigheid() != null) {
                blobRecord.setDatumEindeGeldigheid(r.getDatumEindeGeldigheid());
            }
            blobRecord.setIndicatieTbvLeveringMutaties(r.isIndicatieTbvLeveringMutaties());
            if (r.getActieVervalTbvLeveringMutaties() != null) {
                blobRecord.setActieVervalTbvLeveringMutaties(r.getActieVervalTbvLeveringMutaties().getId());
            }
            for (final Map.Entry<AttribuutElement, MetaAttribuut> entry : r.getAttributen().entrySet()) {

                final AttribuutElement element = entry.getKey();

                // deze elementen niet dubbel opnement
                // refactor MetaRecord zodat alle attributen in de map zitten
                boolean nietOpnemen = element.isVoorkomenTbvLeveringMutaties();
                nietOpnemen = nietOpnemen || element.isNadereAanduidingVerval();
                nietOpnemen = nietOpnemen || element.isDatumAanvangGeldigheid();
                nietOpnemen = nietOpnemen || element.isDatumEindeGeldigheid();
                nietOpnemen = nietOpnemen || element.isDatumTijdVerval();
                nietOpnemen = nietOpnemen || element.isDatumTijdRegistratie();
                nietOpnemen = nietOpnemen || element.isActieVerval();
                nietOpnemen = nietOpnemen || element.isActieVervalTbvLevermutaties();
                nietOpnemen = nietOpnemen || element.isActieInhoud();
                nietOpnemen = nietOpnemen || element.isActieAanpassingGeldigheid();
                if (nietOpnemen) {
                    continue;
                }

                final Object huidigeWaarde = entry.getValue().getWaarde();
                final Object value;
                switch (element.getDatatype()) {
                    case DATUMTIJD:
                        if (huidigeWaarde != null) {
                            value = DatumUtil.vanDateTimeNaarLong((ZonedDateTime) huidigeWaarde);
                        } else {
                            value = null;
                        }
                        break;
                    default:
                        value = huidigeWaarde;

                }
                blobRecord.addAttribuut(element.getId(), value);
            }
            recordsJson.add(blobRecord);
        }
    }
}
