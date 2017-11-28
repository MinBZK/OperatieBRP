/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import org.springframework.stereotype.Component;

/**
 * Dienst deserializer.
 */
@Component
public class DienstDeserializer extends JsonDeserializer<Dienst> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final Dienst deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Dienstbundel dienstbundel = entityManager.getReference(Dienstbundel.class, JsonUtils.getAsInteger(node, DienstModule.DIENSTBUNDEL));
        final Dienst dienst = new Dienst(dienstbundel, EnumUtils.getAsEnum(SoortDienst.class, JsonUtils.getAsInteger(node, DienstModule.SOORT)));
        dienst.setId(JsonUtils.getAsInteger(node, DienstModule.ID));
        dienst.setEffectAfnemerindicaties(
                EnumUtils.getAsEnum(EffectAfnemerindicaties.class, JsonUtils.getAsInteger(node, DienstModule.EFFECT_AFNEMERINDICATIES)));
        dienst.setDatumIngang(JsonUtils.getAsInteger(node, DienstModule.DATUM_INGANG));
        dienst.setDatumEinde(JsonUtils.getAsInteger(node, DienstModule.DATUM_EINDE));
        dienst.setIndicatieGeblokkeerd(JsonUtils.getAsBoolean(node, DienstModule.INDICATIE_GEBLOKKEERD, DienstModule.WAARDE_JA, Boolean.TRUE, null));
        dienst.setAttenderingscriterium(JsonUtils.getAsString(node, DienstModule.ATTENDERINGSCRITERIUM));

        if (hasSelectieGroepValue(node)) {
            dienst.setEersteSelectieDatum(JsonUtils.getAsInteger(node, DienstModule.EERSTE_SELECTIEDATUM));
            dienst.setSoortSelectie(JsonUtils.getAsInteger(node, DienstModule.SOORT_SELECTIE));
            dienst.setHistorievormSelectie(JsonUtils.getAsInteger(node, DienstModule.HISTORIE_VORM));
            dienst.setIndicatieSelectieresultaatControleren(
                    JsonUtils.getAsBoolean(node, DienstModule.INDICATIE_RESULTAAT_CONTROLEREN, DienstModule.WAARDE_JA, Boolean.TRUE, Boolean.FALSE));
            dienst.setSelectieInterval(JsonUtils.getAsInteger(node, DienstModule.SELECTIE_INTERVAL));
            dienst.setEenheidSelectieInterval(JsonUtils.getAsInteger(node, DienstModule.EENHEID_SELECTIE_INTERVAL));
            dienst.setNadereSelectieCriterium(JsonUtils.getAsString(node, DienstModule.NADERE_SELECTIE_CRITERIUM));
            dienst.setSelectiePeilmomentMaterieelResultaat(JsonUtils.getAsInteger(node, DienstModule.SELECTIE_PEILMOMENT_MATERIEEL_RESULTAAT));
            if (JsonUtils.getAsString(node, DienstModule.SELECTIE_PEILMOMENT_FORMEEL_RESULTAAT) != null) {
                dienst.setSelectiePeilmomentFormeelResultaat(
                        Timestamp.valueOf(JsonUtils.getAsString(node, DienstModule.SELECTIE_PEILMOMENT_FORMEEL_RESULTAAT)));
            }
            dienst.setMaxAantalPersonenPerSelectiebestand(JsonUtils.getAsInteger(node, DienstModule.MAX_PERSONEN_PER_SELECTIE));
            dienst.setMaxGrootteSelectiebestand(JsonUtils.getAsInteger(node, DienstModule.MAX_GROOTTE_SELECTIE));
            dienst.setIndVerzVolBerBijWijzAfniNaSelectie(JsonUtils
                    .getAsBoolean(node, DienstModule.INDICATIE_VOLLEDIG_BERICHT_BIJ_AFNEMERINDICATIE_NA_SELECTIE, DienstModule.WAARDE_JA, Boolean.TRUE,
                            null));
            dienst.setLeverwijzeSelectie(JsonUtils.getAsInteger(node, DienstModule.LEVERWIJZE_SELECTIE));
        }

        return dienst;
    }

    private boolean hasSelectieGroepValue(final JsonNode node) {
        return Stream.of(
                DienstModule.EERSTE_SELECTIEDATUM,
                DienstModule.SOORT_SELECTIE,
                DienstModule.HISTORIE_VORM,
                DienstModule.INDICATIE_RESULTAAT_CONTROLEREN,
                DienstModule.SELECTIE_INTERVAL,
                DienstModule.EENHEID_SELECTIE_INTERVAL,
                DienstModule.NADERE_SELECTIE_CRITERIUM,
                DienstModule.SELECTIE_PEILMOMENT_MATERIEEL_RESULTAAT,
                DienstModule.SELECTIE_PEILMOMENT_FORMEEL_RESULTAAT,
                DienstModule.MAX_PERSONEN_PER_SELECTIE,
                DienstModule.MAX_GROOTTE_SELECTIE,
                DienstModule.INDICATIE_VOLLEDIG_BERICHT_BIJ_AFNEMERINDICATIE_NA_SELECTIE,
                DienstModule.LEVERWIJZE_SELECTIE
        ).anyMatch(node::has);
    }
}
