/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.brp.beheer.webapp.view.ActieType;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewGroep;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewObject;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewRecord;
import org.springframework.stereotype.Component;

/**
 * Object serializer.
 */
@Component
public final class ObjectSerializer {

    private static final AtomicLong COUNTER = new AtomicLong();
    private static final String SLEUTEL = "$sleutel";
    private static final String TECHNISCH_ID = "Technisch ID";
    private static final String GROEP = "groep";
    private static final String TYPE = "$type";
    private static final String INDEX_OBJECT = "$indexObject";
    private static final String DETAIL = "$detail";
    private static final String DATUM_EINDE_GELDIGHEID = "Datum einde geldigheid";
    private static final String DATUM_INGANG_GELDIGHEID = "Datum ingang geldigheid";
    private static final String NADERE_AANDUIDING_VERVAL = "Nadere aanduiding verval";
    private static final String TIJDSTIP_VERVAL = "Tijdstip verval";
    private static final String TIJDSTIP_REGISTRATIE = "Tijdstip registratie";
    private static final String CSS = "$css";
    private static final String LINK = "link";
    private static final String LINK_TEKST = "linkTekst";
    private static final String CSS_LINK = "cssLink";
    private static final String BRP_MARKEER = "brp-markeer-";

    /**
     * Te negeren elementen bij het serializeren (oa 'terug FKs').
     */
    private static final List<Element> TE_NEGEREN_ELEMENTEN =
            Arrays.asList(
                    Element.PERSOON_VERIFICATIE_PERSOON,
                    Element.PERSOON_AFNEMERINDICATIE_PERSOON,
                    Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE);

    private final AttribuutSerializer attribuutSerializer;

    /**
     * Constructor.
     * @param attribuutSerializer attribuut serializer
     */
    public ObjectSerializer(final AttribuutSerializer attribuutSerializer) {
        this.attribuutSerializer = attribuutSerializer;
    }

    /**
     * Schrijf objecten.
     * @param element element
     * @param objecten objecten
     * @param jgen json generator
     * @throws IOException bij fouten
     */
    public void writeObjecten(final Element element, final Collection<BlobViewObject> objecten, final JsonGenerator jgen) throws IOException {
        if (objecten != null && !objecten.isEmpty()) {
            jgen.writeObjectFieldStart(element.getElementNaam());
            JsonUtils.writeAsString(jgen, TYPE, GROEP);
            jgen.writeArrayFieldStart(element.getElementNaam());
            for (final BlobViewObject object : objecten) {
                jgen.writeStartObject();
                writeObject(object, jgen);
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    /**
     * Schrijf een object.
     * @param object object
     * @param jgen json generator
     * @throws IOException bij fouten
     */
    public void writeObject(final BlobViewObject object, final JsonGenerator jgen) throws IOException {
        JsonUtils.writeAsString(jgen, TYPE, GROEP);
        JsonUtils.writeAsInteger(jgen, TECHNISCH_ID, object.getObjectsleutel());
        JsonUtils.writeAsString(jgen, SLEUTEL, String.valueOf(object.getObjectsleutel()));

        writeGroepen(object, jgen);

        for (final Map.Entry<Element, Collection<BlobViewObject>> child : object.getObjecten().entrySet()) {
            writeObjecten(child.getKey(), child.getValue(), jgen);
        }
    }

    private void writeGroepen(final BlobViewObject object, final JsonGenerator jgen) throws IOException {
        for (final BlobViewGroep groep : object.getGroepen().values()) {
            if (isIdentiteitGroep(groep)) {
                // Output
                writeRecordData(groep.getRecords().iterator().next(), jgen);
            }
        }

        final List<BlobViewGroep> groepen = new ArrayList<>(object.getGroepen().values());
        Collections.sort(groepen, (o1, o2) -> Integer.compare(o1.getElement().getVolgnummer(), o2.getElement().getVolgnummer()));

        for (final BlobViewGroep groep : groepen) {
            if (!isIdentiteitGroep(groep)) {
                writeGroep(groep, jgen);
            }
        }
    }

    private boolean isIdentiteitGroep(final BlobViewGroep groep) {
        return groep.getElement().getNaam().endsWith("Identiteit");
    }

    private void writeGroep(final BlobViewGroep groep, final JsonGenerator jgen) throws IOException {
        jgen.writeObjectFieldStart(groep.getElement().getElementNaam());
        JsonUtils.writeAsString(jgen, TYPE, GROEP);
        JsonUtils.writeAsString(jgen, SLEUTEL, groep.getElement().getElementNaam());

        // Actuele data
        final List<BlobViewRecord> records = new ArrayList<>(groep.getRecords());
        final BlobViewRecord actueelRecord = extractActueelRecord(records);
        if (actueelRecord != null) {
            writeRecordData(actueelRecord, jgen);
        }

        // Mutaties als tabel
        jgen.writeObjectFieldStart("Mutaties voor " + groep.getElement().getElementNaam());
        JsonUtils.writeAsString(jgen, INDEX_OBJECT, String.valueOf(COUNTER.incrementAndGet()));
        jgen.writeStringField(TYPE, "tabel");
        writeHistorieRecordKop(jgen, groep);

        jgen.writeArrayFieldStart("data");
        writeRecords(groep, records, jgen);

        jgen.writeEndArray();
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    private BlobViewRecord extractActueelRecord(final List<BlobViewRecord> records) {
        return records.stream()
                .filter(record -> record.getActieVerval() == null && record.getActieAanpassingGeldigheid() == null)
                .findFirst()
                .orElse(null);
    }

    private void writeRecords(final BlobViewGroep groep, final Collection<BlobViewRecord> records, final JsonGenerator jgen) throws IOException {
        for (final BlobViewRecord record : records) {
            jgen.writeStartObject();
            JsonUtils.writeAsString(jgen, INDEX_OBJECT, String.valueOf(COUNTER.incrementAndGet()));
            writeHistorieRecord(groep, record, jgen);

            jgen.writeObjectFieldStart(DETAIL);

            JsonUtils.writeAsInteger(jgen, TECHNISCH_ID, record.getVoorkomensleutel());
            writeRecordData(record, jgen);
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
    }

    private void writeRecordData(final BlobViewRecord record, final JsonGenerator jgen) throws IOException {
        for (final Map.Entry<Element, Object> attribuut : record.getAttributen().entrySet()) {
            writeAttribuut(attribuut.getKey(), attribuut.getValue(), jgen);
        }
    }

    private void writeAttribuut(final Element element, final Object value, final JsonGenerator jgen) throws IOException {
        if (TE_NEGEREN_ELEMENTEN.contains(element)) {
            return;
        }
        attribuutSerializer.writeAttribuut(element, value, jgen);
    }

    private void writeHistorieRecord(final BlobViewGroep groep, final BlobViewRecord record, final JsonGenerator jgen) throws IOException {
        if (isFormeel(groep.getElement())) {
            jgen.writeStringField(TIJDSTIP_REGISTRATIE, AttribuutSerializer.formatDatum(record.getTijdstipRegistratie()));
            jgen.writeStringField(TIJDSTIP_VERVAL, AttribuutSerializer.formatDatum(record.getTijdstipVerval()));
            jgen.writeStringField(NADERE_AANDUIDING_VERVAL, record.getNadereAanduidingVerval());
            final StringBuilder cssClassBuilder = new StringBuilder();
            maakCssLinkObject(ActieType.INHOUD, record.getActieInhoud(), cssClassBuilder, jgen);
            maakCssLinkObject(ActieType.VERVAL, record.getActieVerval(), cssClassBuilder, jgen);
            jgen.writeStringField(CSS, cssClassBuilder.toString());

        } else if (isMaterieel(groep.getElement())) {
            jgen.writeStringField(DATUM_INGANG_GELDIGHEID, AttribuutSerializer.formatDatum(record.getDatumAanvangGeldigheid()));
            jgen.writeStringField(DATUM_EINDE_GELDIGHEID, AttribuutSerializer.formatDatum(record.getDatumEindeGeldigheid()));
            jgen.writeStringField(TIJDSTIP_REGISTRATIE, AttribuutSerializer.formatDatum(record.getTijdstipRegistratie()));
            jgen.writeStringField(TIJDSTIP_VERVAL, AttribuutSerializer.formatDatum(record.getTijdstipVerval()));
            jgen.writeStringField(NADERE_AANDUIDING_VERVAL, record.getNadereAanduidingVerval());
            final StringBuilder cssClassBuilder = new StringBuilder();
            maakCssLinkObject(ActieType.INHOUD, record.getActieInhoud(), cssClassBuilder, jgen);
            maakCssLinkObject(ActieType.AANPASSING, record.getActieAanpassingGeldigheid(), cssClassBuilder, jgen);
            maakCssLinkObject(ActieType.VERVAL, record.getActieVerval(), cssClassBuilder, jgen);
            jgen.writeStringField(CSS, cssClassBuilder.toString());
        }
    }

    private boolean isFormeel(final Element element) {
        return HistoriePatroon.F.equals(element.getHistoriePatroon()) || HistoriePatroon.F1.equals(element.getHistoriePatroon());
    }

    private boolean isMaterieel(final Element element) {
        return HistoriePatroon.F_M.equals(element.getHistoriePatroon()) || HistoriePatroon.F_M1.equals(element.getHistoriePatroon());
    }

    private void maakCssLinkObject(final ActieType actieType, final BlobViewObject actie, final StringBuilder ccsClassBuilder, final JsonGenerator jgen)
            throws IOException {
        if (actie != null) {
            final BlobViewObject administratieveHandeling = actie.getParent();

            ccsClassBuilder.append(BRP_MARKEER);
            ccsClassBuilder.append(administratieveHandeling.getObjectsleutel());
            ccsClassBuilder.append(" ");
            jgen.writeObjectFieldStart(actieType.getOmschrijving());
            jgen.writeStringField(TYPE, CSS_LINK);
            jgen.writeStringField(LINK_TEKST, "Ja (actie " + actie.getObjectsleutel() + ")");
            jgen.writeStringField(LINK, BRP_MARKEER + administratieveHandeling.getObjectsleutel());
            jgen.writeEndObject();
        } else {
            jgen.writeObjectFieldStart(actieType.getOmschrijving());
            jgen.writeStringField(TYPE, CSS_LINK);
            jgen.writeStringField(LINK_TEKST, "Nee");
            jgen.writeStringField(LINK, "");
            jgen.writeEndObject();
        }
    }

    private void writeHistorieRecordKop(final JsonGenerator jgen, final BlobViewGroep groep) throws IOException {
        jgen.writeArrayFieldStart("kolomtitels");
        jgen.writeString(INDEX_OBJECT);
        if (isFormeel(groep.getElement())) {
            jgen.writeString(TIJDSTIP_REGISTRATIE);
            jgen.writeString(TIJDSTIP_VERVAL);
            jgen.writeString(NADERE_AANDUIDING_VERVAL);
            jgen.writeString(ActieType.INHOUD.getOmschrijving());
            jgen.writeString(ActieType.VERVAL.getOmschrijving());
            jgen.writeString(CSS);

        } else if (isMaterieel(groep.getElement())) {
            jgen.writeString(DATUM_INGANG_GELDIGHEID);
            jgen.writeString(DATUM_EINDE_GELDIGHEID);
            jgen.writeString(TIJDSTIP_REGISTRATIE);
            jgen.writeString(TIJDSTIP_VERVAL);
            jgen.writeString(NADERE_AANDUIDING_VERVAL);
            jgen.writeString(ActieType.INHOUD.getOmschrijving());
            jgen.writeString(ActieType.AANPASSING.getOmschrijving());
            jgen.writeString(ActieType.VERVAL.getOmschrijving());
            jgen.writeString(CSS);
        }
        jgen.writeString(DETAIL);
        jgen.writeEndArray();
    }

}
