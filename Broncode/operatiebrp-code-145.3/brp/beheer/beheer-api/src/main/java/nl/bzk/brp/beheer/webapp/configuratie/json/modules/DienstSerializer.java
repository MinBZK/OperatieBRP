/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import org.springframework.stereotype.Component;

/**
 * Dienst serializer.
 */
@Component
public class DienstSerializer extends JsonSerializer<Dienst> {

    private static final List<Function<Dienst, Object>> dienstSelectieGroep = Arrays.asList(
            Dienst::getEersteSelectieDatum,
            Dienst::getSoortSelectie,
            Dienst::getHistorievormSelectie,
            Dienst::getIndicatieSelectieresultaatControleren,
            Dienst::getSelectieInterval,
            Dienst::getEenheidSelectieInterval,
            Dienst::getNadereSelectieCriterium,
            Dienst::getSelectiePeilmomentMaterieelResultaat,
            Dienst::getSelectiePeilmomentFormeelResultaat,
            Dienst::getMaxAantalPersonenPerSelectiebestand,
            Dienst::getMaxGrootteSelectiebestand,
            Dienst::getIndVerzVolBerBijWijzAfniNaSelectie,
            Dienst::getLeverwijzeSelectie
    );

    @Override
    public final void serialize(final Dienst dienst, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstModule.ID, dienst.getId());
        JsonUtils.writeAsInteger(jgen, DienstModule.DIENSTBUNDEL, dienst.getDienstbundel().getId());
        JsonUtils.writeAsInteger(jgen, DienstModule.LEVERINGSAUTORISATIE, dienst.getDienstbundel().getLeveringsautorisatie().getId());
        JsonUtils.writeAsInteger(jgen, DienstModule.SOORT, EnumUtils.getId(dienst.getSoortDienst()));
        JsonUtils.writeAsInteger(jgen, DienstModule.EFFECT_AFNEMERINDICATIES, EnumUtils.getId(dienst.getEffectAfnemerindicaties()));
        JsonUtils.writeAsInteger(jgen, DienstModule.DATUM_INGANG, dienst.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, DienstModule.DATUM_EINDE, dienst.getDatumEinde());
        JsonUtils.writeAsString(
                jgen,
                DienstModule.INDICATIE_GEBLOKKEERD,
                dienst.getIndicatieGeblokkeerd(),
                LeveringsautorisatieModule.WAARDE_JA,
                LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(jgen, DienstModule.ATTENDERINGSCRITERIUM, dienst.getAttenderingscriterium());

        if (anyValueInSelectieGroep(dienst)) {
            JsonUtils.writeAsInteger(jgen, DienstModule.EERSTE_SELECTIEDATUM, dienst.getEersteSelectieDatum());
            JsonUtils.writeAsInteger(jgen, DienstModule.SOORT_SELECTIE, dienst.getSoortSelectie());
            JsonUtils.writeAsInteger(jgen, DienstModule.HISTORIE_VORM, dienst.getHistorievormSelectie());
            JsonUtils.writeAsString(
                    jgen,
                    DienstModule.INDICATIE_RESULTAAT_CONTROLEREN,
                    dienst.getIndicatieSelectieresultaatControleren(),
                    LeveringsautorisatieModule.WAARDE_JA,
                    LeveringsautorisatieModule.WAARDE_NEE);
            JsonUtils.writeAsInteger(jgen, DienstModule.SELECTIE_INTERVAL, dienst.getSelectieInterval());
            JsonUtils.writeAsInteger(jgen, DienstModule.EENHEID_SELECTIE_INTERVAL, dienst.getEenheidSelectieInterval());
            JsonUtils.writeAsString(jgen, DienstModule.NADERE_SELECTIE_CRITERIUM, dienst.getNadereSelectieCriterium());
            JsonUtils.writeAsInteger(jgen, DienstModule.SELECTIE_PEILMOMENT_MATERIEEL_RESULTAAT, dienst.getSelectiePeilmomentMaterieelResultaat());
            if (dienst.getSelectiePeilmomentFormeelResultaat() != null) {
                JsonUtils.writeAsString(jgen, DienstModule.SELECTIE_PEILMOMENT_FORMEEL_RESULTAAT,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dienst.getSelectiePeilmomentFormeelResultaat()));
            }
            JsonUtils.writeAsInteger(jgen, DienstModule.MAX_PERSONEN_PER_SELECTIE, dienst.getMaxAantalPersonenPerSelectiebestand());
            JsonUtils.writeAsInteger(jgen, DienstModule.MAX_GROOTTE_SELECTIE, dienst.getMaxGrootteSelectiebestand());
            JsonUtils.writeAsString(
                    jgen,
                    DienstModule.INDICATIE_VOLLEDIG_BERICHT_BIJ_AFNEMERINDICATIE_NA_SELECTIE,
                    dienst.getIndVerzVolBerBijWijzAfniNaSelectie(),
                    LeveringsautorisatieModule.WAARDE_JA,
                    LeveringsautorisatieModule.WAARDE_NEE);
            JsonUtils.writeAsInteger(jgen, DienstModule.LEVERWIJZE_SELECTIE, dienst.getLeverwijzeSelectie());
        }
        jgen.writeEndObject();
    }

    private boolean anyValueInSelectieGroep(final Dienst dienst) {
        return dienstSelectieGroep.stream().anyMatch(f -> Objects.nonNull(f.apply(dienst)));
    }
}
