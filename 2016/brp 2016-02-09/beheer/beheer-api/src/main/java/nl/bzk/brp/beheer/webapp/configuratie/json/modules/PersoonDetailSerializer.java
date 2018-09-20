/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.beheer.webapp.view.PersoonDetailView;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.beheer.view.ActieType;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * maakt json object aan voor de persoon detail view.
 *
 * @param <T>
 *            class van de serializer
 */
public final class PersoonDetailSerializer<T extends ModelIdentificeerbaar<?> & FormeleHistorie> extends JsonSerializer<PersoonDetailView> {

    private static final String GEEN_RELATIE = "Geen Relatie";
    private static final String VELDNAAM_LINK = "link";
    private static final String NEE = "Nee";
    private static final String JA = "Ja";
    private static final String VELDNAAM_LINK_TEKST = "linkTekst";
    private static final String CCS_LINK = "cssLink";
    private static final String VELDNAAM_TYPE = "$type";
    private static final String VELDNAAM_CSS = "$css";
    private static final String VELDNAAM_DETAIL = "$detail";
    private static final String INDEX_OBJECT = "$indexObject";
    private static final String BRPMARKEER = "brp-markeer-";
    private static final String TABEL = "tabel";
    private static final String DATA = "data";
    private static final String KOLOMTITELS = "kolomtitels";
    private static final String ZIE_WIJZIGINGEN = "Zie wijzigingen";
    private static final String MUTATIES = "Mutaties";

    private final SerializerUtils serializerUtils = new SerializerUtils();

    @Override
    public void serialize(final PersoonDetailView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        //
        jgen.writeStartObject();
        jgen.writeObjectFieldStart(ElementEnum.PERSOON_IDENTITEIT.getElementNaam());
        serializerUtils.writeTypeGroep(jgen);
        serializerUtils.writeTechnischId(jgen, value.getPersoon());
        serializerUtils.writeValue(jgen, ElementEnum.PERSOON_SOORTCODE, value.getPersoon().getSoort());
        jgen.writeEndObject();

        writeGroepen(jgen, maakGroepenVoorPersoon(value.getPersoon()));

        writePersoonAdressen(jgen, value.getPersoon().getAdressen());
        writePersoonVoornamen(jgen, value.getPersoon().getVoornamen());
        writePersoonGeslachtsnaamcomponenten(jgen, value.getPersoon().getGeslachtsnaamcomponenten());
        writePersoonNationaliteiten(jgen, value.getPersoon().getNationaliteiten());
        writePersoonReisdocumenten(jgen, value.getPersoon().getReisdocumenten());
        writePersoonVerificaties(jgen, value.getPersoon().getVerificaties());
        writePersoonVerstrekkingsbeperkingen(jgen, value.getPersoon().getVerstrekkingsbeperkingen());
        writePersoonBetrokkenheden(jgen, value.getPersoon().getBetrokkenheden());
        writeOnderzoekPersonen(jgen, value.getPersoon().getOnderzoeken());
        writeAdministratieveHandelingen(jgen, value.getPersoon().getAdministratieveHandelingen());
        jgen.writeEndObject();
    }

    private List<Groep> maakGroepenVoorPersoon(final PersoonHisVolledig persHisVolledig) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF, persHisVolledig.getPersoonAfgeleidAdministratiefHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_IDENTIFICATIENUMMERS, persHisVolledig.getPersoonIdentificatienummersHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_SAMENGESTELDENAAM, persHisVolledig.getPersoonSamengesteldeNaamHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_GEBOORTE, persHisVolledig.getPersoonGeboorteHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_GESLACHTSAANDUIDING, persHisVolledig.getPersoonGeslachtsaanduidingHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_INSCHRIJVING, persHisVolledig.getPersoonInschrijvingHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_NUMMERVERWIJZING, persHisVolledig.getPersoonNummerverwijzingHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_BIJHOUDING, persHisVolledig.getPersoonBijhoudingHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_OVERLIJDEN, persHisVolledig.getPersoonOverlijdenHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_NAAMGEBRUIK, persHisVolledig.getPersoonNaamgebruikHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_MIGRATIE, persHisVolledig.getPersoonMigratieHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_VERBLIJFSRECHT, persHisVolledig.getPersoonVerblijfsrechtHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_UITSLUITINGKIESRECHT, persHisVolledig.getPersoonUitsluitingKiesrechtHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN, persHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie());
        maakGroep(groepen, ElementEnum.PERSOON_PERSOONSKAART, persHisVolledig.getPersoonPersoonskaartHistorie());
        return groepen;
    }

    private void writePersoonAdressen(final JsonGenerator jgen, final Set<? extends PersoonAdresHisVolledig> adressen) throws IOException {
        if (adressen != null && !adressen.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_ADRES.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_ADRES.getElementNaam());
            for (final PersoonAdresHisVolledig adres : adressen) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonAdres(jgen, adres);
                writeGroepen(jgen, maakGroepenVoorAdres(adres.getPersoonAdresHistorie()));

                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorAdres(final MaterieleHistorieSet<HisPersoonAdresModel> persoonAdresHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_ADRES, persoonAdresHistorie);
        return groepen;
    }

    private void writePersoonVoornamen(final JsonGenerator jgen, final Set<? extends PersoonVoornaamHisVolledig> voornamen) throws IOException {
        if (voornamen != null && !voornamen.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_VOORNAAM.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VOORNAAM.getElementNaam());
            for (final PersoonVoornaamHisVolledig voornaam : voornamen) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonVoornaam(jgen, voornaam);
                writeGroepen(jgen, maakGroepenVoorVoornaam(voornaam.getPersoonVoornaamHistorie()));

                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorVoornaam(final MaterieleHistorieSet<HisPersoonVoornaamModel> persoonVoornaamHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_VOORNAAM, persoonVoornaamHistorie);
        return groepen;
    }

    private void writePersoonGeslachtsnaamcomponenten(
        final JsonGenerator jgen,
        final Set<? extends PersoonGeslachtsnaamcomponentHisVolledig> geslachtsnaamcomponenten) throws IOException
    {
        if (geslachtsnaamcomponenten != null && !geslachtsnaamcomponenten.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT.getElementNaam());
            for (final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaamcomponent : geslachtsnaamcomponenten) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonGeslachtsnaamcomponent(jgen, geslachtsnaamcomponent);
                writeGroepen(jgen, maakGroepenVoorGeslachtsnaamcomponent(geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorie()));

                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorGeslachtsnaamcomponent(
        final MaterieleHistorieSet<HisPersoonGeslachtsnaamcomponentModel> persoonGeslachtsnaamcomponentHistorie)
    {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT, persoonGeslachtsnaamcomponentHistorie);
        return groepen;
    }

    private void writePersoonNationaliteiten(final JsonGenerator jgen, final Set<? extends PersoonNationaliteitHisVolledig> nationaliteiten)
        throws IOException
    {
        if (nationaliteiten != null && !nationaliteiten.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_NATIONALITEIT.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_NATIONALITEIT.getElementNaam());
            for (final PersoonNationaliteitHisVolledig nationaliteit : nationaliteiten) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonNationaliteit(jgen, nationaliteit);
                writeGroepen(jgen, maakGroepenVoorNationaliteit(nationaliteit.getPersoonNationaliteitHistorie()));
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorNationaliteit(final MaterieleHistorieSet<HisPersoonNationaliteitModel> persoonNationaliteitHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_NATIONALITEIT, persoonNationaliteitHistorie);
        return groepen;
    }

    private void writePersoonReisdocumenten(final JsonGenerator jgen, final Set<? extends PersoonReisdocumentHisVolledig> reisdocumenten)
        throws IOException
    {
        if (reisdocumenten != null && !reisdocumenten.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_REISDOCUMENT.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_REISDOCUMENT.getElementNaam());
            for (final PersoonReisdocumentHisVolledig reisdocument : reisdocumenten) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonReisdocument(jgen, reisdocument);
                writeGroepen(jgen, maakGroepenVoorReisdocumenten(reisdocument.getPersoonReisdocumentHistorie()));
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorReisdocumenten(final FormeleHistorieSet<HisPersoonReisdocumentModel> persoonReisdocumentHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_REISDOCUMENT, persoonReisdocumentHistorie);
        return groepen;
    }

    private void writePersoonVerificaties(final JsonGenerator jgen, final Set<? extends PersoonVerificatieHisVolledig> verificaties) throws IOException {
        if (verificaties != null && !verificaties.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_VERIFICATIE.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VERIFICATIE.getElementNaam());
            for (final PersoonVerificatieHisVolledig verificatie : verificaties) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonVerificatie(jgen, verificatie);
                writeGroepen(jgen, maakGroepenVoorVerificatie(verificatie.getPersoonVerificatieHistorie()));
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorVerificatie(final FormeleHistorieSet<HisPersoonVerificatieModel> persoonVerificatieHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_VERIFICATIE, persoonVerificatieHistorie);
        return groepen;
    }

    private void writePersoonVerstrekkingsbeperkingen(
        final JsonGenerator jgen,
        final Set<? extends PersoonVerstrekkingsbeperkingHisVolledig> verstrekkingsbeperkingen) throws IOException
    {
        if (verstrekkingsbeperkingen != null && !verstrekkingsbeperkingen.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING.getElementNaam());
            for (final PersoonVerstrekkingsbeperkingHisVolledig verstrekkingsbeperking : verstrekkingsbeperkingen) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writePersoonVerstrekkingsbeperking(jgen, verstrekkingsbeperking);
                writeGroepen(jgen, maakGroepenVoorVerstrekkingsbeperking(verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie()));
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorVerstrekkingsbeperking(
        final FormeleHistorieSet<HisPersoonVerstrekkingsbeperkingModel> persoonVerstrekkingsbeperkingHistorie)
    {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_VERBLIJFSRECHT, persoonVerstrekkingsbeperkingHistorie);
        return groepen;
    }

    private void writePersoonBetrokkenheden(final JsonGenerator jgen, final Set<? extends BetrokkenheidHisVolledig> betrokkenheden) throws IOException {
        final Map<String, Set<BetrokkenheidHisVolledig>> gegroepeerdeBetrokkenheden = groepeerPersoonBetrokkenhedenOpRelatieType(betrokkenheden);
        for (final Map.Entry<String, Set<BetrokkenheidHisVolledig>> entry : gegroepeerdeBetrokkenheden.entrySet()) {
            final String relatieSoort = entry.getKey();
            final Set<BetrokkenheidHisVolledig> gegroepeerdeBetrokkenheidSet = entry.getValue();
            if (!gegroepeerdeBetrokkenheidSet.isEmpty()) {
                jgen.writeObjectFieldStart(relatieSoort);
                serializerUtils.writeTypeGroep(jgen);
                jgen.writeArrayFieldStart(ElementEnum.BETROKKENHEID.getElementNaam());
                for (final BetrokkenheidHisVolledig betrokkenheid : gegroepeerdeBetrokkenheidSet) {
                    jgen.writeStartObject();
                    serializerUtils.writeTypeGroep(jgen);
                    serializerUtils.writeBetrokkenheid(jgen, betrokkenheid);
                    writeGroepen(jgen, maakGroepenVoorBetrokkenheid(betrokkenheid.getBetrokkenheidHistorie()));
                    if (betrokkenheid.getRelatie() != null) {
                        writeRelatie(jgen, relatieSoort, betrokkenheid);

                    }
                    jgen.writeEndObject();
                }
                jgen.writeEndArray();
                jgen.writeEndObject();
            }
        }
    }

    private List<Groep> maakGroepenVoorBetrokkenheid(final FormeleHistorieSet<HisBetrokkenheidModel> betrokkenheidHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.BETROKKENHEID, betrokkenheidHistorie);
        return groepen;
    }

    private Map<String, Set<BetrokkenheidHisVolledig>> groepeerPersoonBetrokkenhedenOpRelatieType(
        final Set<? extends BetrokkenheidHisVolledig> betrokkenheden)
    {
        final Map<String, Set<BetrokkenheidHisVolledig>> resultaat = new HashMap<>();
        for (final BetrokkenheidHisVolledig betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getRelatie() != null && betrokkenheid.getRelatie().getSoort() != null && betrokkenheid.getRelatie().getSoort().heeftWaarde()) {
                if (!resultaat.containsKey(betrokkenheid.getRelatie().getSoort().getWaarde().getNaam())) {
                    resultaat.put(betrokkenheid.getRelatie().getSoort().getWaarde().getNaam(), new HashSet<BetrokkenheidHisVolledig>());
                }
                resultaat.get(betrokkenheid.getRelatie().getSoort().getWaarde().getNaam()).add(betrokkenheid);
            } else {
                if (!resultaat.containsKey(GEEN_RELATIE)) {
                    resultaat.put(GEEN_RELATIE, new HashSet<BetrokkenheidHisVolledig>());
                }
                resultaat.get(GEEN_RELATIE).add(betrokkenheid);
            }
        }
        return resultaat;
    }

    private void writeRelatie(final JsonGenerator jgen, final String relatieSoort, final BetrokkenheidHisVolledig betrokkenheid) throws IOException {

        jgen.writeObjectFieldStart(relatieSoort);

        serializerUtils.writeRelatie(jgen, betrokkenheid.getRelatie());
        writeGroepen(jgen, maakgGroepenVoorRelatie(betrokkenheid.getRelatie().getRelatieHistorie()));

        writeRelatieBetrokkenheden(jgen, betrokkenheid);

        jgen.writeEndObject();
    }

    private List<Groep> maakgGroepenVoorRelatie(final FormeleHistorieSet<HisRelatieModel> relatieHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.RELATIE, relatieHistorie);
        return groepen;
    }

    private void writeRelatieBetrokkenheden(final JsonGenerator jgen, final BetrokkenheidHisVolledig betrokkenheid) throws IOException {

        if (betrokkenheid.getRelatie() != null
            && betrokkenheid.getRelatie().getBetrokkenheden() != null
            && !betrokkenheid.getRelatie().getBetrokkenheden().isEmpty())
        {
            jgen.writeObjectFieldStart(ElementEnum.BETROKKENHEID.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.BETROKKENHEID.getElementNaam());
            for (final BetrokkenheidHisVolledig relatieBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                if (!betrokkenheid.getID().equals(relatieBetrokkenheid.getID())
                    && !(betrokkenheid.getRol().getWaarde().equals(SoortBetrokkenheid.OUDER) && relatieBetrokkenheid.getRol()
                                                                                                                    .getWaarde()
                                                                                                                    .equals(SoortBetrokkenheid.OUDER)))
                {
                    jgen.writeStartObject();
                    serializerUtils.writeTypeGroep(jgen);
                    serializerUtils.writeRelatieBetrokkenheid(jgen, relatieBetrokkenheid, true);
                    writeGroepen(jgen, maakGroepenVoorBetrokkenheid(relatieBetrokkenheid.getBetrokkenheidHistorie()));
                    jgen.writeEndObject();
                }
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private void writeOnderzoekPersonen(final JsonGenerator jgen, final Set<? extends PersoonOnderzoekHisVolledig> personen) throws IOException {
        if (personen != null && !personen.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.PERSOON_ONDERZOEK.getElementNaam());
            serializerUtils.writeTypeGroep(jgen);
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_ONDERZOEK.getElementNaam());

            for (final PersoonOnderzoekHisVolledig persoon : personen) {
                jgen.writeStartObject();
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writeLabel(jgen, serializerUtils.getValue(persoon.getID()));
                jgen.writeObjectFieldStart(ElementEnum.PERSOON_ONDERZOEK.getElementNaam());
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writeIndexObject(jgen);
                serializerUtils.writeOnderzoek(jgen, persoon.getOnderzoek());
                writeGroepen(jgen, maakGroepenVoorOnderzoek(persoon.getOnderzoek().getOnderzoekHistorie()));
                jgen.writeEndObject();

                jgen.writeObjectFieldStart(ElementEnum.ONDERZOEK.getElementNaam());
                serializerUtils.writeTypeGroep(jgen);
                serializerUtils.writeIndexObject(jgen);
                serializerUtils.writeOnderzoekPersoon(jgen, persoon);
                writeGroepen(jgen, maakGroepenVoorPersoonOnderzoek(persoon.getPersoonOnderzoekHistorie()));
                jgen.writeEndObject();

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private List<Groep> maakGroepenVoorOnderzoek(final FormeleHistorieSet<HisOnderzoekModel> gegevensInOnderzoek) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_ONDERZOEK, gegevensInOnderzoek);
        return groepen;
    }

    private List<Groep> maakGroepenVoorPersoonOnderzoek(final FormeleHistorieSet<HisPersoonOnderzoekModel> persoonOnderzoekHistorie) {
        final List<Groep> groepen = new ArrayList<>();
        maakGroep(groepen, ElementEnum.PERSOON_ONDERZOEK, persoonOnderzoekHistorie);
        return groepen;
    }

    private void writeAdministratieveHandelingen(
        final JsonGenerator jgen,
        final List<? extends AdministratieveHandelingHisVolledig> administratieveHandelingen) throws IOException
    {
        if (administratieveHandelingen != null && !administratieveHandelingen.isEmpty()) {
            jgen.writeObjectFieldStart(ElementEnum.ADMINISTRATIEVEHANDELING.getElementNaam());
            jgen.writeStringField(VELDNAAM_TYPE, "tabelGroep");
            jgen.writeArrayFieldStart(KOLOMTITELS);
            jgen.writeString("Technisch ID");
            jgen.writeString(ElementEnum.ADMINISTRATIEVEHANDELING_SOORTNAAM.getElementNaam());
            jgen.writeString(ElementEnum.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam());
            jgen.writeString(ElementEnum.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getElementNaam());
            jgen.writeString(MUTATIES);
            jgen.writeString(VELDNAAM_CSS);
            jgen.writeEndArray();
            jgen.writeArrayFieldStart(DATA);
            for (final AdministratieveHandelingHisVolledig administratieveHandeling : administratieveHandelingen) {
                jgen.writeStartObject();

                writeAdministratieveHandeling(jgen, administratieveHandeling);

                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private void writeAdministratieveHandeling(final JsonGenerator jgen, final AdministratieveHandelingHisVolledig administratieveHandeling)
        throws IOException
    {
        serializerUtils.writeTechnischId(jgen, administratieveHandeling);
        jgen.writeStringField(
            ElementEnum.ADMINISTRATIEVEHANDELING_SOORTNAAM.getElementNaam(),
            serializerUtils.getValue(administratieveHandeling.getSoort()));
        jgen.writeStringField(
            ElementEnum.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam(),
            serializerUtils.getValue(administratieveHandeling.getPartij()));
        jgen.writeStringField(
            ElementEnum.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getElementNaam(),
            serializerUtils.getValue(administratieveHandeling.getTijdstipRegistratie()));
        jgen.writeObjectFieldStart(MUTATIES);
        jgen.writeStringField(VELDNAAM_TYPE, CCS_LINK);
        jgen.writeStringField(VELDNAAM_LINK_TEKST, ZIE_WIJZIGINGEN);
        jgen.writeStringField(VELDNAAM_LINK, BRPMARKEER + administratieveHandeling.getID());
        jgen.writeEndObject();
        final StringBuilder cssBuilder = new StringBuilder(BRPMARKEER);
        cssBuilder.append(administratieveHandeling.getID());
        jgen.writeStringField(VELDNAAM_CSS, cssBuilder.toString());
    }

    // private void writeActieHisVolledig(final JsonGenerator jgen, final ActieHisVolledig actie) throws IOException {
    // serializerUtils.writeTechnischId(jgen, actie);
    // serializerUtils.writeLabel(jgen, actie.getSoort().getWaarde().getNaam());
    //
    // serializerUtils.writeHisRecord(jgen, actie);
    // jgen.writeObjectFieldStart(MUTATIES);
    // jgen.writeStringField(VELDNAAM_TYPE, CCS_LINK);
    // jgen.writeStringField(VELDNAAM_LINK_TEKST, ZIE_WIJZIGINGEN);
    // jgen.writeStringField(VELDNAAM_LINK, BRPMARKEER + actie.getAdministratieveHandeling().getID());
    // jgen.writeEndObject();
    // }

    private void maakGroep(final List<Groep> groepen, final ElementEnum element, final HistorieSet historyRecords) {

        if (historyRecords != null && !(historyRecords.isLeeg() && historyRecords.getActueleRecord() == null)) {
            final T actueelRecord = (T) historyRecords.getActueleRecord();
            final Groep groep = new Groep(element, actueelRecord);
            for (final Object record : historyRecords.getHistorie()) {
                groep.add((T) record);
            }
            groepen.add(groep);
        }
    }

    private void writeGroepen(final JsonGenerator jgen, final List<Groep> groepen) throws IOException {
        if (groepen != null && !groepen.isEmpty()) {
            for (final Groep groep : groepen) {
                writeGroep(jgen, groep);
            }
        }
    }

    private void writeGroep(final JsonGenerator jgen, final Groep groep) throws IOException {
        jgen.writeObjectFieldStart(groep.getElement().getElementNaam());
        serializerUtils.writeTypeGroep(jgen);
        serializerUtils.writeTechnischId(jgen, groep.getActueelRecord());
        serializerUtils.writeLabel(jgen, groep.getElement().getElementNaam());
        serializerUtils.writeHisRecord(jgen, groep.getActueelRecord());

        if (groep.getMutaties() != null && !groep.getMutaties().isEmpty()) {
            jgen.writeObjectFieldStart("Mutaties voor " + groep.getElement().getElementNaam());
            serializerUtils.writeIndexObject(jgen);
            jgen.writeStringField(VELDNAAM_TYPE, TABEL);

            writeHistorieKopRecord(jgen, (T) groep.getMutaties().get(0));

            jgen.writeArrayFieldStart(DATA);
            final List<T> mutaties = groep.getMutaties();
            Collections.sort(mutaties, new Comparator<T>() {

                @Override
                public int compare(final T arg0, final T arg1) {
                    return ((FormeleHistorie) arg0).getTijdstipRegistratie().compareTo(((FormeleHistorie) arg1).getTijdstipRegistratie()) * -1;
                }

            });
            writeHistorieRecords(jgen, mutaties);
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
        jgen.writeEndObject();
    }

    private void writeHistorieRecords(final JsonGenerator jgen, final List<T> records) throws IOException {
        if (records != null && !records.isEmpty()) {
            for (final T record : records) {
                if (record != null) {
                    jgen.writeStartObject();
                    serializerUtils.writeIndexObject(jgen);
                    writeHistorieRecord(jgen, record);

                    jgen.writeObjectFieldStart(VELDNAAM_DETAIL);

                    serializerUtils.writeTechnischId(jgen, record);
                    serializerUtils.writeHisRecord(jgen, record);
                    jgen.writeEndObject();
                    jgen.writeEndObject();
                }
            }
        }
    }

    private void writeHistorieKopRecord(final JsonGenerator jgen, final T record) throws IOException {
        jgen.writeArrayFieldStart(KOLOMTITELS);
        if (record instanceof AbstractFormeelHistorischMetActieVerantwoording) {
            jgen.writeString(INDEX_OBJECT);
            jgen.writeString(ElementEnum.PARTIJROL_TIJDSTIPREGISTRATIE.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_TIJDSTIPVERVAL.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_NADEREAANDUIDINGVERVAL.getElementNaam());
            jgen.writeString(ActieType.INHOUD.getOmschrijving());
            jgen.writeString(ActieType.VERVAL.getOmschrijving());
            jgen.writeString(VELDNAAM_CSS);
            jgen.writeString(VELDNAAM_DETAIL);

        } else if (record instanceof AbstractMaterieelHistorischMetActieVerantwoording) {
            jgen.writeString(INDEX_OBJECT);
            jgen.writeString(ElementEnum.PARTIJROL_DATUMINGANG.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_DATUMEINDE.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_TIJDSTIPREGISTRATIE.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_TIJDSTIPVERVAL.getElementNaam());
            jgen.writeString(ElementEnum.PARTIJROL_NADEREAANDUIDINGVERVAL.getElementNaam());
            jgen.writeString(ActieType.INHOUD.getOmschrijving());
            jgen.writeString(ActieType.AANPASSING.getOmschrijving());
            jgen.writeString(ActieType.VERVAL.getOmschrijving());
            jgen.writeString(VELDNAAM_CSS);
            jgen.writeString(VELDNAAM_DETAIL);
        }
        jgen.writeEndArray();
    }

    private void writeHistorieRecord(final JsonGenerator jgen, final T record) throws IOException {
        if (record instanceof AbstractFormeelHistorischMetActieVerantwoording) {
            jgen.writeStringField(
                ElementEnum.PARTIJROL_TIJDSTIPREGISTRATIE.getElementNaam(),
                serializerUtils.getValue(record.getTijdstipRegistratie()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_TIJDSTIPVERVAL.getElementNaam(),
                serializerUtils.getValue(record.getDatumTijdVerval()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_NADEREAANDUIDINGVERVAL.getElementNaam(),
                serializerUtils.getValue(((AbstractFormeelHistorischMetActieVerantwoording) record).getNadereAanduidingVerval()));
            final StringBuilder cssClassBuilder = new StringBuilder();
            maakCssLinkObject(
                ActieType.INHOUD,
                ((AbstractFormeelHistorischMetActieVerantwoording) record).getVerantwoordingInhoud(),
                cssClassBuilder,
                jgen);
            maakCssLinkObject(
                ActieType.VERVAL,
                ((AbstractFormeelHistorischMetActieVerantwoording) record).getVerantwoordingVerval(),
                cssClassBuilder,
                jgen);
            jgen.writeStringField(VELDNAAM_CSS, cssClassBuilder.toString());

        } else if (record instanceof AbstractMaterieelHistorischMetActieVerantwoording) {
            jgen.writeStringField(
                ElementEnum.PARTIJROL_DATUMINGANG.getElementNaam(),
                serializerUtils.getValue(((AbstractMaterieelHistorischMetActieVerantwoording) record).getDatumAanvangGeldigheid()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_DATUMEINDE.getElementNaam(),
                serializerUtils.getValue(((AbstractMaterieelHistorischMetActieVerantwoording) record).getDatumEindeGeldigheid()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_TIJDSTIPREGISTRATIE.getElementNaam(),
                serializerUtils.getValue(record.getTijdstipRegistratie()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_TIJDSTIPVERVAL.getElementNaam(),
                serializerUtils.getValue(record.getDatumTijdVerval()));
            jgen.writeStringField(
                ElementEnum.PARTIJROL_NADEREAANDUIDINGVERVAL.getElementNaam(),
                serializerUtils.getValue(((AbstractMaterieelHistorischMetActieVerantwoording) record).getNadereAanduidingVerval()));
            final StringBuilder cssClassBuilder = new StringBuilder();
            maakCssLinkObject(
                ActieType.INHOUD,
                ((AbstractMaterieelHistorischMetActieVerantwoording) record).getVerantwoordingInhoud(),
                cssClassBuilder,
                jgen);
            maakCssLinkObject(
                ActieType.AANPASSING,
                ((AbstractMaterieelHistorischMetActieVerantwoording) record).getVerantwoordingAanpassingGeldigheid(),
                cssClassBuilder,
                jgen);
            maakCssLinkObject(
                ActieType.VERVAL,
                ((AbstractMaterieelHistorischMetActieVerantwoording) record).getVerantwoordingVerval(),
                cssClassBuilder,
                jgen);
            jgen.writeStringField(VELDNAAM_CSS, cssClassBuilder.toString());
        }
    }

    private void maakCssLinkObject(final ActieType actieType, final ActieModel actie, final StringBuilder ccsClassBuilder, final JsonGenerator jgen)
        throws IOException
    {
        if (actie != null) {
            ccsClassBuilder.append(BRPMARKEER);
            ccsClassBuilder.append(actie.getAdministratieveHandeling().getID());
            ccsClassBuilder.append(" ");
            jgen.writeObjectFieldStart(actieType.getOmschrijving());
            jgen.writeStringField(VELDNAAM_TYPE, CCS_LINK);
            jgen.writeStringField(VELDNAAM_LINK_TEKST, JA);
            jgen.writeStringField(VELDNAAM_LINK, BRPMARKEER + actie.getAdministratieveHandeling().getID());
            jgen.writeEndObject();
        } else {
            jgen.writeObjectFieldStart(actieType.getOmschrijving());
            jgen.writeStringField(VELDNAAM_TYPE, CCS_LINK);
            jgen.writeStringField(VELDNAAM_LINK_TEKST, NEE);
            jgen.writeStringField(VELDNAAM_LINK, "");
            jgen.writeEndObject();
        }
    }

    /**
     * Een groep gegevens.
     *
     * @param <T>
     *            type gegeven
     */
    private static class Groep<T extends ModelIdentificeerbaar<?> & FormeleHistorie> {
        private final ElementEnum element;
        private final T actueelRecord;
        private final List<T> mutaties = new ArrayList<>();

        Groep(final ElementEnum element, final T actueelRecord) {
            this.element = element;
            this.actueelRecord = actueelRecord;
        }

        public ElementEnum getElement() {
            return element;
        }

        public T getActueelRecord() {
            return actueelRecord;
        }

        public void add(final T mutatie) {
            mutaties.add(mutatie);
        }

        public List<T> getMutaties() {
            return mutaties;
        }
    }
}
