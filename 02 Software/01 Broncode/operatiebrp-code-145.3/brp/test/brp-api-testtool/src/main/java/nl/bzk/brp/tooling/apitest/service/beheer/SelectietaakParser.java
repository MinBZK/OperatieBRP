/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import java.sql.Timestamp;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.test.common.dsl.DslSectie;
import org.springframework.util.Assert;

/**
 * DSL parser voor een {@link Selectietaak}.
 * TODO nu gedupliceerd met common, maar dit moet tzt één parser worden.
 */
final class SelectietaakParser {

    public static final String TAAK_ID = "id";
    public static final String SECTIE_TAAK = "Selectietaak";
    public static final String INDICATIE_LIJST_GEBRUIKEN = "indsellijstgebruiken";
    public static final String DIENST_SLEUTEL = "dienstSleutel";
    public static final String DATPLANNING = "datplanning";
    public static final String PEILMOMMATERIEEL = "peilmommaterieel";
    public static final String PEILMOMMATERIEELRESULTAAT = "peilmommaterieelresultaat";
    public static final String PEILMOMFORMEELRESULTAAT = "peilmomformeelresultaat";
    public static final String INDAG = "indag";
    public static final String UITGEVOERDIN = "uitgevoerdin";
    public static final String STATUS = "status";
    public static final String VOLGNUMMER = "volgnummer";

    private SelectietaakParser() {
    }

    /**
     * Parsed de selectietaak.
     * @param sectie Het te parsen deel
     */
    public static Selectietaak parse(DslSectie sectie, Function<DienstSleutel, Dienst> dienstResolver,
                                     Function<DienstSleutel, ToegangLeveringsAutorisatie> tlaResolver) {
        sectie.assertMetNaam(SECTIE_TAAK);

        DienstSleutel dienstSleutel = sectie.geefStringValue(DIENST_SLEUTEL)
                .map(DienstSleutel::new)
                .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + sectie.toString()));
        Dienst dienst = dienstResolver.apply(dienstSleutel);

        Integer volgnummer = sectie.geefInteger(VOLGNUMMER).orElseThrow(() -> new RuntimeException("Volgnummer is niet gezet!"));
        Selectietaak selectietaak = new Selectietaak(dienst, tlaResolver.apply(dienstSleutel), volgnummer);
        selectietaak.setActueelEnGeldig(sectie.geefBooleanValue(INDAG).orElse(true));
        selectietaak.setIndicatieSelectielijstGebruiken(sectie.geefBooleanValue(INDICATIE_LIJST_GEBRUIKEN).orElse(false));
        //optionals
        sectie.geefInteger(TAAK_ID).ifPresent(selectietaak::setId);
        sectie.geefDatumInt(DATPLANNING).ifPresent(selectietaak::setDatumPlanning);
        sectie.geefDatumInt(PEILMOMMATERIEEL).ifPresent(selectietaak::setPeilmomentMaterieel);
        sectie.geefDatumInt(PEILMOMMATERIEELRESULTAAT).ifPresent(selectietaak::setPeilmomentMaterieelResultaat);
        sectie.geefStringValue(PEILMOMFORMEELRESULTAAT).ifPresent(
                s -> selectietaak.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumConstanten.getDateTime(s))));
        sectie.geefStringValue(STATUS).ifPresent(s -> {
            for (SelectietaakStatus selectietaakStatus : SelectietaakStatus.values()) {
                if (s.equals(selectietaakStatus.getNaam())) {
                    selectietaak.setStatus((short) selectietaakStatus.getId());
                }
            }
            Assert.notNull(selectietaak.getStatus(), "Selectietaakstatus onbekend");
        });
        sectie.geefDatumInt(UITGEVOERDIN).map(integer -> {
            Selectierun selectierun = new Selectierun(new Timestamp(System.currentTimeMillis()));
            selectierun.setId(integer);
            return selectierun;
        }).ifPresent(selectietaak::setUitgevoerdIn);

        return selectietaak;
    }
}
