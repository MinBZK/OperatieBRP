/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VasteAttribuutWaarde;
import nl.bzk.brp.model.beheer.view.Actie;
import nl.bzk.brp.model.beheer.view.ActieType;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;

/**
 * Hulpmiddelen voor serializen van objecten.
 */
public final class SerializerUtils {

    /** dubbele punt met spatie. */
    public static final String DUBBELE_PUNT_SPATIE = ": ";

    /** map met elementen. */
    public static final Map<Integer, ElementEnum> ELEMENT_BY_HIS_DB_OBJECT = new HashMap<>();
    static {
        for (final ElementEnum element : ElementEnum.values()) {
            ELEMENT_BY_HIS_DB_OBJECT.put(element.getHisDbObjectId(), element);
        }
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    private static final String VELDNAAM_TYPE = "$type";

    private final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);
        }
    };


    /**
     * schrijf iD veld weg voor object.
     * @param jgen de JsonGenerator
     * @param value objet waarvoor id moet worden gemaakt
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public static void writeId(final JsonGenerator jgen, final Number value) throws IOException {
        JsonUtils.writeAsInteger(jgen, "iD", value);
    }

    /**
     * Bepaal de textuele waarde van een object.
     *
     * @param value object
     * @return textuele waarde
     */
    public String getValue(final Object value) {
        final String result;
        if (value == null) {
            result = null;
        } else if (value instanceof Attribuut<?>) {
            result = getValue(((Attribuut<?>) value).getWaarde());

        } else if (value instanceof VasteAttribuutWaarde<?>) {
            result = getValue(((VasteAttribuutWaarde<?>) value).getVasteWaarde());
        } else if (NaamMethoden.heeftNaamMethode(value)) {
            result = getValue(NaamMethoden.invokeNaamMethode(value));
        } else if (CodeMethoden.heeftCodeMethode(value)) {
            result = getValue(CodeMethoden.invokeCodeMethode(value));

        } else if (value instanceof AdministratieveHandelingModel) {
            result = Long.toString(((AdministratieveHandelingModel) value).getID());

        } else if (value instanceof Boolean) {
            result = (Boolean) value ? "Ja" : "Nee";
        } else if (value instanceof Date) {
            result = sdf.get().format(value);
        } else {
            result = String.valueOf(value);
        }
        return result;
    }

    /**
     * Schrijft waarde weg naar json object.
     *
     * @param jgen de JsonGenerator
     * @param element element waarvoor de waarden wordt weggeschreven
     * @param waarde waarde van het element
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public void writeValue(final JsonGenerator jgen, final ElementEnum element, final Object waarde) throws IOException {
        jgen.writeStringField(element == null ? "???" : element.getElementNaam(), getValue(waarde));
    }

    /**
     * schrijft een technisch id weg voor gebruik opklapbare gegevens.
     *
     * @param jgen de JsonGenerator
     * @param value objet waarvoor id moet worden gemaakt
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public void writeTechnischId(final JsonGenerator jgen, final Object value) throws IOException {
        if (value instanceof ModelIdentificeerbaar<?>) {
            JsonUtils.writeAsInteger(jgen, "Technisch ID", ((ModelIdentificeerbaar<?>) value).getID());
        }
        writeIndexObject(jgen);
    }

    /**
     * schrijf een index object weg voor de voorkant.
     *
     * @param jgen de JsonGenerator
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public void writeIndexObject(final JsonGenerator jgen) throws IOException {
        JsonUtils.writeAsString(jgen, "$indexObject", String.valueOf(COUNTER.incrementAndGet()));
    }

    /**
     * schrijf type groep voor de voorkant.
     *
     * @param jgen de JsonGenerator
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public void writeTypeGroep(final JsonGenerator jgen) throws IOException {
        JsonUtils.writeAsString(jgen, VELDNAAM_TYPE, "groep");
    }

    /**
     * Schrijft label weg voor objectgegevens.
     *
     * @param jgen de JsonGenerator
     * @param label het label voor opklapbare gegevens
     * @throws IOException eventuele fout die kan optreden bij schrijven waarde
     */
    public void writeLabel(final JsonGenerator jgen, final String label) throws IOException {
        JsonUtils.writeAsString(jgen, "$sleutel", label);
    }

    /**
     * Schrijf historyrecords weg naar json object.
     *
     * @param jgen json generator
     * @param actieType actieType van de his records
     * @param records lijjst van history objecten
     * @throws IOException fout bij wegschrijven object
     */
    public void writeHisRecords(final JsonGenerator jgen, final ActieType actieType, final List<? extends ModelIdentificeerbaar<?>> records)
            throws IOException
    {
        if (records != null && !records.isEmpty()) {
            for (final ModelIdentificeerbaar<?> record : records) {
                jgen.writeStartObject();

                writeTechnischId(jgen, record);
                writeLabel(jgen, actieType.getOmschrijving() + DUBBELE_PUNT_SPATIE + record.getID());
                writeHisRecord(jgen, record);

                jgen.writeEndObject();
            }
        }
    }

    /**
     * Schrijf 1 HisRecord weg.
     *
     * @param jgen json generator
     * @param record gegevens om weg te schrijven
     * @throws IOException fout bij wegschrijven object
     */
    public void writeHisRecord(final JsonGenerator jgen, final ModelIdentificeerbaar<?> record) throws IOException {
        writeHisRecord(jgen, record, false, null);
    }

    /**
     * Schrijf 1 HisRecord weg.
     *
     * @param jgen json generator
     * @param record gegevens om weg te schrijven
     * @param anrLink administratienummer als link
     * @param linkId id gebruikt voor de link
     * @throws IOException fout bij wegschrijven object
     */
    public void writeHisRecord(final JsonGenerator jgen, final ModelIdentificeerbaar<?> record, final boolean anrLink, final Number linkId)
        throws IOException
    {
        if (record != null) {
            final Map<Integer, Object> values = new TreeMap<>();
            for (final Method method : record.getClass().getMethods()) {
                if (isCompatibleGetter(method)) {
                    final Object value;
                    try {
                        value = method.invoke(record);
                    } catch (final ReflectiveOperationException e) {
                        throw new IllegalArgumentException("Kan his record niet uitlezen", e);
                    }

                    if (value != null) {
                        final AttribuutAccessor attribuutAccessor = method.getAnnotation(AttribuutAccessor.class);
                        values.put(attribuutAccessor.dbObjectId(), value);

                    }
                }
            }

            if (anrLink) {
                // Waarden worden op volgorde van het hisDbObjectId weggeschreven zodat het altijd dezelfde volgorde is.
                for (final Map.Entry<Integer, Object> entry : values.entrySet()) {
                    if (entry.getKey().equals(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getHisDbObjectId())) {
                        jgen.writeObjectFieldStart(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getElementNaam());
                        jgen.writeStringField(VELDNAAM_TYPE, "link");
                        jgen.writeStringField("parameter", "anr");
                        jgen.writeStringField("parameterwaarde", getValue(linkId));
                        jgen.writeStringField("linktekst", getValue(entry.getValue()));
                        jgen.writeEndObject();
                    } else {
                        writeValue(jgen, ELEMENT_BY_HIS_DB_OBJECT.get(entry.getKey()), entry.getValue());
                    }
                }

            } else {
                // Waarden worden op volgorde van het hisDbObjectId weggeschreven zodat het altijd dezelfde volgorde is.
                for (final Map.Entry<Integer, Object> entry : values.entrySet()) {
                    writeValue(jgen, ELEMENT_BY_HIS_DB_OBJECT.get(entry.getKey()), entry.getValue());
                }
            }
        }
    }

    /**
     * Check of methode moet worden gebruikt voor afdruk.
     *
     * @param method de methode
     * @return indicatie of af te drukken
     */
    public boolean isCompatibleGetter(final Method method) {
        return method.getName().startsWith("get") && method.getParameterTypes().length == 0 && method.getAnnotation(AttribuutAccessor.class) != null;
    }

    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */

    /**
     * Schrijf persoon weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @param skipIdAndLabel indien geen label nodig
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoon(final JsonGenerator jgen, final PersoonHisVolledig value, final boolean skipIdAndLabel) throws IOException {
        writePersoon(jgen, value, skipIdAndLabel, false);
    }

    /**
     * Schrijf persoon weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @param skipIdAndLabel indien geen label nodig
     * @param anrLink maak van anummer een linkveld
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoon(final JsonGenerator jgen, final PersoonHisVolledig value, final boolean skipIdAndLabel, final boolean anrLink)
        throws IOException
    {
        if (!skipIdAndLabel) {
            writeTechnischId(jgen, value);
            writeLabel(jgen, labelPersoon(value));
        }

        writeValue(jgen, ElementEnum.PERSOON_SOORTCODE, value.getSoort());
        writeHisRecord(jgen, value.getPersoonIdentificatienummersHistorie().getActueleRecord(), anrLink, value.getID());
        writeHisRecord(jgen, value.getPersoonSamengesteldeNaamHistorie().getActueleRecord());
        writeHisRecord(jgen, value.getPersoonGeboorteHistorie().getActueleRecord());
        writeHisRecord(jgen, value.getPersoonGeslachtsaanduidingHistorie().getActueleRecord());
    }

    private String labelPersoon(final PersoonHisVolledig value) {
        final StringBuilder result = new StringBuilder();

        if (value.getPersoonIdentificatienummersHistorie() != null
            && value.getPersoonIdentificatienummersHistorie().getActueleRecord() != null
            && value.getPersoonIdentificatienummersHistorie().getActueleRecord().getAdministratienummer() != null)
        {
            result.append(value.getPersoonIdentificatienummersHistorie().getActueleRecord().getAdministratienummer().getWaarde()).append(" - ");
        }

        if (value.getPersoonSamengesteldeNaamHistorie() != null && value.getPersoonSamengesteldeNaamHistorie().getActueleRecord() != null) {
            if (value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoorvoegsel() != null) {
                result.append(value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoorvoegsel().getWaarde()).append(' ');
            }
            if (value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getGeslachtsnaamstam() != null) {
                result.append(value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getGeslachtsnaamstam().getWaarde()).append(", ");
            }
            if (value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoornamen() != null) {
                result.append(value.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVoornamen().getWaarde());
            }
        }

        if (result.length() == 0) {
            result.append(value.getID());
        }

        return result.toString();
    }

    /**
     * Schrijf adres weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonAdres(final JsonGenerator jgen, final PersoonAdresHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));
    }

    /**
     * Schrijf voornaam weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonVoornaam(final JsonGenerator jgen, final PersoonVoornaamHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PERSOON_VOORNAAM_VOLGNUMMER, value.getVolgnummer());
    }

    /**
     * Schrijf geslachtsnaam weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonGeslachtsnaamcomponent(final JsonGenerator jgen, final PersoonGeslachtsnaamcomponentHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER, value.getVolgnummer());
    }

    /**
     * Schrijf nationaliteit weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonNationaliteit(final JsonGenerator jgen, final PersoonNationaliteitHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, getValue(value.getNationaliteit().getWaarde().getNaam()));

        writeValue(jgen, ElementEnum.PERSOON_NATIONALITEIT_NATIONALITEITCODE, value.getNationaliteit().getWaarde().getCode());
    }

    /**
     * Schrijf reisdocument weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonReisdocument(final JsonGenerator jgen, final PersoonReisdocumentHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PERSOON_REISDOCUMENT_SOORTCODE, value.getSoort());

    }

    /**
     * Schrijf persoonverificatie weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonVerificatie(final JsonGenerator jgen, final PersoonVerificatieHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PERSOON_VERIFICATIE_PARTIJCODE, value.getPartij().getWaarde().getCode());
        writeValue(jgen, ElementEnum.PERSOON_VERIFICATIE_SOORT, value.getSoort());
    }

    /**
     * Schrijf verstrekkingsbeperking weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writePersoonVerstrekkingsbeperking(final JsonGenerator jgen, final PersoonVerstrekkingsbeperkingHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE, value.getPartij().getWaarde().getCode());
        writeValue(jgen, ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE, value.getOmschrijvingDerde());
        writeValue(jgen, ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE, value.getGemeenteVerordening());
    }

    /**
     * Schrijf relatie weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeRelatie(final JsonGenerator jgen, final RelatieHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, labelRelatie(value));

        writeValue(jgen, ElementEnum.RELATIE_SOORTCODE, value.getSoort());
    }

    private String labelRelatie(final RelatieHisVolledig value) {
        final StringBuilder result = new StringBuilder();

        result.append(value.getID()).append(DUBBELE_PUNT_SPATIE);
        if (value.getSoort() != null) {
            result.append(value.getSoort().getWaarde().getNaam());
        }

        return result.toString();
    }

    /**
     * Schrijf betrokkenheid weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeBetrokkenheid(final JsonGenerator jgen, final BetrokkenheidHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, labelBetrokkenheid(value));

        writeValue(jgen, ElementEnum.BETROKKENHEID_ROLCODE, value.getRol());
    }

    /**
     * Schrijf relatiebetrokkenheid weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeRelatieBetrokkenheid(final JsonGenerator jgen, final BetrokkenheidHisVolledig value) throws IOException {
        writeRelatieBetrokkenheid(jgen, value, false);
    }

    /**
     * Schrijf relatiebetrokkenheid weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @param anrLink maak linkveld van administratienummer
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeRelatieBetrokkenheid(final JsonGenerator jgen, final BetrokkenheidHisVolledig value, final boolean anrLink) throws IOException {
        writeBetrokkenheid(jgen, value);

        if (value.getPersoon() != null) {
            writePersoon(jgen, value.getPersoon(), true, anrLink);
        }
    }

    /**
     * creeer label voor betrokkenheid.
     *
     * @param value betrokkenheid waarvoor label gemaakt moet worden
     * @return label
     */
    public String labelBetrokkenheid(final BetrokkenheidHisVolledig value) {
        final StringBuilder result = new StringBuilder();

        result.append(value.getID()).append(DUBBELE_PUNT_SPATIE);
        if (value.getRol() != null) {
            result.append(value.getRol().getWaarde().getNaam());
        }

        return result.toString();
    }

    /**
     * Schrijf actie weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeActie(final JsonGenerator jgen, final Actie value) throws IOException {
        writeTechnischId(jgen, value);
        writeValue(jgen, ElementEnum.ACTIE_SOORTNAAM, value.getSoort());
        writeValue(jgen, ElementEnum.ACTIE_TIJDSTIPREGISTRATIE, value.getTijdstipRegistratie());
        writeValue(jgen, ElementEnum.ACTIE_DATUMONTLENING, value.getDatumOntlening());
        writeActieBronnen(jgen, value.getBronnen());
    }

    private void writeActieBronnen(final JsonGenerator jgen, final Set<ActieBronModel> actieBronnen) throws IOException {
        if (actieBronnen != null && !actieBronnen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.ACTIEBRON.getElementNaam());

            for (final ActieBronModel actieBron : actieBronnen) {
                jgen.writeStartObject();
                writeTechnischId(jgen, actieBron);
                writeLabel(jgen, String.valueOf(actieBron.getID()));
                writeActieBronDocument(jgen, actieBron.getDocument());
                writeValue(jgen, ElementEnum.RECHTSGROND, actieBron.getRechtsgrond());
                writeValue(jgen, ElementEnum.RECHTSGROND_OMSCHRIJVING, actieBron.getRechtsgrondomschrijving());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }

    private void writeActieBronDocument(final JsonGenerator jgen, final DocumentModel value) throws IOException {
        if (value != null) {
            jgen.writeObjectFieldStart(ElementEnum.DOCUMENT.getElementNaam());
            writeTechnischId(jgen, value);
            writeValue(jgen, ElementEnum.DOCUMENT_SOORTNAAM, value.getSoort());
            jgen.writeObjectFieldStart(ElementEnum.DOCUMENT_STANDAARD.getElementNaam());
            writeTechnischId(jgen, value.getStandaard());
            writeValue(jgen, ElementEnum.DOCUMENT_AKTENUMMER, value.getStandaard().getAktenummer());
            writeValue(jgen, ElementEnum.DOCUMENT_IDENTIFICATIE, value.getStandaard().getIdentificatie());
            writeValue(jgen, ElementEnum.DOCUMENT_OMSCHRIJVING, value.getStandaard().getOmschrijving());
            if (value.getStandaard().getPartij() != null) {
                writeValue(jgen, ElementEnum.PARTIJ_CODE, value.getStandaard().getPartij().getWaarde().getCode());
                writeValue(jgen, ElementEnum.PARTIJ_NAAM, value.getStandaard().getPartij().getWaarde().getNaam());
            }
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
    }

    /**
     * Schrijf onderzoek weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeOnderzoek(final JsonGenerator jgen, final OnderzoekHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        final Set<? extends GegevenInOnderzoekHisVolledig> gegevensInOnderzoek = value.getGegevensInOnderzoek();
        if (gegevensInOnderzoek != null && !gegevensInOnderzoek.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.GEGEVENINONDERZOEK.getElementNaam());

            for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoek : gegevensInOnderzoek) {
                jgen.writeStartObject();
                writeGegevenInOnderzoek(jgen, gegevenInOnderzoek);
                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeGegevenInOnderzoek(final JsonGenerator jgen, final GegevenInOnderzoekHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getElement().getWaarde().getNaam()));

        writeValue(jgen, ElementEnum.GEGEVENINONDERZOEK_ELEMENTNAAM, value.getElement());
        writeValue(jgen, ElementEnum.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN, value.getObjectSleutelGegeven());
        writeValue(jgen, ElementEnum.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN, value.getVoorkomenSleutelGegeven());
    }

    /**
     * Schrijf onderzoek persoon weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeOnderzoekPersoon(final JsonGenerator jgen, final PersoonOnderzoekHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writePersoon(jgen, value.getPersoon(), true);
    }

    /**
     * Schrijf onderzoek partij weg.
     *
     * @param jgen json generator
     * @param value waarde die weggeschreven wordt
     * @throws IOException indien er een fout optreedt bij wegschrijven
     */
    public void writeOnderzoekPartij(final JsonGenerator jgen, final PartijOnderzoekHisVolledig value) throws IOException {
        writeTechnischId(jgen, value);
        writeLabel(jgen, String.valueOf(value.getID()));

        writeValue(jgen, ElementEnum.PARTIJ_CODE, value.getPartij().getWaarde().getCode());
    }

}
