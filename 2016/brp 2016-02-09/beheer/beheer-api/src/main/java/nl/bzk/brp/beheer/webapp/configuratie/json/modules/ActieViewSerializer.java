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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.beheer.view.ActieType;
import nl.bzk.brp.model.beheer.view.ActieView;
import nl.bzk.brp.model.beheer.view.GegevensView;
import nl.bzk.brp.model.beheer.view.ObjectView;
import nl.bzk.brp.model.beheer.view.OnderzoekView;
import nl.bzk.brp.model.beheer.view.PersoonView;
import nl.bzk.brp.model.beheer.view.RelatieView;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;

/**
 * Actie view serializer.
 */
public class ActieViewSerializer extends JsonSerializer<ActieView> {

    private final SerializerUtils serializerUtils = new SerializerUtils();

    @Override
    public final void serialize(final ActieView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        serializerUtils.writeActie(jgen, value.getActie());

        writePersonen(jgen, value.getPersonen());
        writeRelaties(jgen, value.getRelaties());
        writeOnderzoeken(jgen, value.getOnderzoeken());

        jgen.writeEndObject();
    }

    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */


    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */
    /* **************************************************************** */


    private void writePersonen(final JsonGenerator jgen, final List<PersoonView> personen) throws IOException {
        if (personen != null && !personen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON.getNaam());

            for (final PersoonView persoon : personen) {
                jgen.writeStartObject();

                serializerUtils.writePersoon(jgen, persoon.getBasisObject(), false);
                writeGroepen(jgen, persoon.getGegevensViews());

                writePersoonAdressen(jgen, persoon.getAdressen());
                writePersoonVoornamen(jgen, persoon.getVoornamen());
                writePersoonGeslachtsnaamcomponenten(jgen, persoon.getGeslachtsnaamcomponenten());
                writePersoonNationaliteiten(jgen, persoon.getNationaliteiten());
                writePersoonReisdocumenten(jgen, persoon.getReisdocumenten());
                writePersoonVerificaties(jgen, persoon.getVerificaties());
                writePersoonVerstrekkingsbeperkingen(jgen, persoon.getVerstrekkingsbeperkingen());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonAdressen(final JsonGenerator jgen, final List<ObjectView<PersoonAdresHisVolledig>> adressen) throws IOException {
        if (adressen != null && !adressen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_ADRES.getElementNaam());

            for (final ObjectView<PersoonAdresHisVolledig> relatie : adressen) {
                jgen.writeStartObject();

                serializerUtils.writePersoonAdres(jgen, relatie.getBasisObject());
                writeGroepen(jgen, relatie.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonVoornamen(final JsonGenerator jgen, final List<ObjectView<PersoonVoornaamHisVolledig>> voornamen) throws IOException {
        if (voornamen != null && !voornamen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VOORNAAM.getElementNaam());

            for (final ObjectView<PersoonVoornaamHisVolledig> voornaam : voornamen) {
                jgen.writeStartObject();

                serializerUtils.writePersoonVoornaam(jgen, voornaam.getBasisObject());
                writeGroepen(jgen, voornaam.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonGeslachtsnaamcomponenten(
            final JsonGenerator jgen,
            final List<ObjectView<PersoonGeslachtsnaamcomponentHisVolledig>> geslachtsnaamcomponenten) throws IOException
    {
        if (geslachtsnaamcomponenten != null && !geslachtsnaamcomponenten.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT.getElementNaam());

            for (final ObjectView<PersoonGeslachtsnaamcomponentHisVolledig> geslachtsnaamcomponent : geslachtsnaamcomponenten) {
                jgen.writeStartObject();

                serializerUtils.writePersoonGeslachtsnaamcomponent(jgen, geslachtsnaamcomponent.getBasisObject());
                writeGroepen(jgen, geslachtsnaamcomponent.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonNationaliteiten(final JsonGenerator jgen, final List<ObjectView<PersoonNationaliteitHisVolledig>> nationaliteiten)
            throws IOException
    {
        if (nationaliteiten != null && !nationaliteiten.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_NATIONALITEIT.getElementNaam());

            for (final ObjectView<PersoonNationaliteitHisVolledig> nationaliteit : nationaliteiten) {
                jgen.writeStartObject();

                serializerUtils.writePersoonNationaliteit(jgen, nationaliteit.getBasisObject());
                writeGroepen(jgen, nationaliteit.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonReisdocumenten(final JsonGenerator jgen, final List<ObjectView<PersoonReisdocumentHisVolledig>> reisdocumenten)
            throws IOException
    {
        if (reisdocumenten != null && !reisdocumenten.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_REISDOCUMENT.getElementNaam());

            for (final ObjectView<PersoonReisdocumentHisVolledig> reisdocument : reisdocumenten) {
                jgen.writeStartObject();

                serializerUtils.writePersoonReisdocument(jgen, reisdocument.getBasisObject());
                writeGroepen(jgen, reisdocument.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonVerificaties(final JsonGenerator jgen, final List<ObjectView<PersoonVerificatieHisVolledig>> verificaties) throws IOException {
        if (verificaties != null && !verificaties.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VERIFICATIE.getElementNaam());

            for (final ObjectView<PersoonVerificatieHisVolledig> verificatie : verificaties) {
                jgen.writeStartObject();

                serializerUtils.writePersoonVerificatie(jgen, verificatie.getBasisObject());
                writeGroepen(jgen, verificatie.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writePersoonVerstrekkingsbeperkingen(
            final JsonGenerator jgen,
            final List<ObjectView<PersoonVerstrekkingsbeperkingHisVolledig>> verstrekkingsbeperkingen) throws IOException
    {
        if (verstrekkingsbeperkingen != null && !verstrekkingsbeperkingen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING.getElementNaam());

            for (final ObjectView<PersoonVerstrekkingsbeperkingHisVolledig> verstrekkingsbeperking : verstrekkingsbeperkingen) {
                jgen.writeStartObject();

                serializerUtils.writePersoonVerstrekkingsbeperking(jgen, verstrekkingsbeperking.getBasisObject());
                writeGroepen(jgen, verstrekkingsbeperking.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeRelaties(final JsonGenerator jgen, final List<RelatieView> relaties) throws IOException {
        if (relaties != null && !relaties.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.RELATIE.getElementNaam());

            for (final RelatieView relatie : relaties) {
                jgen.writeStartObject();

                serializerUtils.writeRelatie(jgen, relatie.getBasisObject());
                writeGroepen(jgen, relatie.getGegevensViews());

                writeRelatieBetrokkenheden(jgen, relatie.getBetrokkenheden());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeRelatieBetrokkenheden(final JsonGenerator jgen, final List<ObjectView<BetrokkenheidHisVolledig>> betrokkenheden) throws IOException {
        if (betrokkenheden != null && !betrokkenheden.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.BETROKKENHEID.getElementNaam());

            for (final ObjectView<BetrokkenheidHisVolledig> betrokkenheid : betrokkenheden) {
                jgen.writeStartObject();

                serializerUtils.writeRelatieBetrokkenheid(jgen, betrokkenheid.getBasisObject());
                writeGroepen(jgen, betrokkenheid.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeOnderzoeken(final JsonGenerator jgen, final List<OnderzoekView> onderzoeken) throws IOException {
        if (onderzoeken != null && !onderzoeken.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.ONDERZOEK.getElementNaam());

            for (final OnderzoekView onderzoek : onderzoeken) {
                jgen.writeStartObject();

                serializerUtils.writeOnderzoek(jgen, onderzoek.getBasisObject());
                writeGroepen(jgen, onderzoek.getGegevensViews());

                writeOnderzoekPersonen(jgen, onderzoek.getPersonen());
                writeOnderzoekPartijen(jgen, onderzoek.getPartijen());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeOnderzoekPersonen(final JsonGenerator jgen, final List<ObjectView<PersoonOnderzoekHisVolledig>> personen) throws IOException {
        if (personen != null && !personen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PERSOON_ONDERZOEK.getElementNaam());

            for (final ObjectView<PersoonOnderzoekHisVolledig> betrokkenheid : personen) {
                jgen.writeStartObject();

                serializerUtils.writeOnderzoekPersoon(jgen, betrokkenheid.getBasisObject());
                writeGroepen(jgen, betrokkenheid.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeOnderzoekPartijen(final JsonGenerator jgen, final List<ObjectView<PartijOnderzoekHisVolledig>> partijen) throws IOException {
        if (partijen != null && !partijen.isEmpty()) {
            jgen.writeArrayFieldStart(ElementEnum.PARTIJENINONDERZOEK.getElementNaam());

            for (final ObjectView<PartijOnderzoekHisVolledig> partij : partijen) {
                jgen.writeStartObject();

                serializerUtils.writeOnderzoekPartij(jgen, partij.getBasisObject());
                writeGroepen(jgen, partij.getGegevensViews());

                jgen.writeEndObject();
            }

            jgen.writeEndArray();
        }
    }

    private void writeGroepen(final JsonGenerator jgen, final Collection<GegevensView<?>> gegevensViews) throws IOException {
        if (gegevensViews != null && !gegevensViews.isEmpty()) {
            for (final GegevensView<?> gegevensView : gegevensViews) {
                writeGroep(jgen, gegevensView);
            }
        }
    }

    private <T extends ModelIdentificeerbaar<?>> void writeGroep(final JsonGenerator jgen, final GegevensView<T> gegevensView) throws IOException {
        if (gegevensView.getGegevens() != null && !gegevensView.getGegevens().isEmpty()) {
            jgen.writeArrayFieldStart(gegevensView.getElement().getElementNaam());

            for (final Map.Entry<ActieType, List<T>> gegevens : gegevensView.getGegevens().entrySet()) {
                serializerUtils.writeHisRecords(jgen, gegevens.getKey(), gegevens.getValue());
            }

            jgen.writeEndArray();
        }
    }
}
