/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import nl.gba.gbav.impl.lo3.GenericStapelGS;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.BrpActieJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.BrpGroepInhoudJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.BrpHistorieJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.GenericStapelGSJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.Lo3CategorieInhoudJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.Lo3DocumentatieJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.Lo3HistorieJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.Lo3PersoonslijstJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.StapelJsonMixin;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize.BrpDatumSerializer;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize.BrpDatumTijdSerializer;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize.Lo3DatumSerializer;
import nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize.Lo3DatumtijdstempelSerializer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Deze klasse wordt gebruikt bij het omzetten van het ViewerResponse object naar JSON welke naar de pagina gaat.
 */
public class JsonFormatter {

    /**
     * Gebruik mixins om bepaalde attributen te negeren zodat de BrpPersoonslijst geen loops meer bevat.
     * 
     * (En om redundante attributen te negeren in zowel de Lo3 als Brp Persoonslijst.)
     * 
     * Anders hadden we ook gewoon het UploadResponse object kunnen retourneren, nu zetten we het zelf om naar een
     * string.
     * 
     * @param uploadResponse
     *            ViewerResponse
     * @return ResponseEntity<String> JSON
     */
    public final ResponseEntity<String> format(final ViewerResponse uploadResponse) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.addMixInAnnotations(BrpActie.class, BrpActieJsonMixin.class);
            mapper.addMixInAnnotations(BrpStapel.class, StapelJsonMixin.class);
            mapper.addMixInAnnotations(Lo3Stapel.class, StapelJsonMixin.class);
            mapper.addMixInAnnotations(Lo3CategorieInhoud.class, Lo3CategorieInhoudJsonMixin.class);
            mapper.addMixInAnnotations(Lo3Documentatie.class, Lo3DocumentatieJsonMixin.class);
            mapper.addMixInAnnotations(Lo3Historie.class, Lo3HistorieJsonMixin.class);
            mapper.addMixInAnnotations(BrpHistorie.class, BrpHistorieJsonMixin.class);
            mapper.addMixInAnnotations(GenericStapelGS.class, GenericStapelGSJsonMixin.class);
            mapper.addMixInAnnotations(BrpGroepInhoud.class, BrpGroepInhoudJsonMixin.class);
            mapper.addMixInAnnotations(BrpHistorie.class, BrpHistorieJsonMixin.class);
            mapper.addMixInAnnotations(Lo3Persoonslijst.class, Lo3PersoonslijstJsonMixin.class);

            final SimpleModule module = new SimpleModule();
            module.addSerializer(new Lo3DatumSerializer());
            module.addSerializer(new Lo3DatumtijdstempelSerializer());
            module.addSerializer(new BrpDatumSerializer());
            module.addSerializer(new BrpDatumTijdSerializer());
            mapper.registerModule(module);

            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            final HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=utf-8");
            return new ResponseEntity<String>(mapper.writeValueAsString(uploadResponse), responseHeaders,
                    HttpStatus.OK);
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
