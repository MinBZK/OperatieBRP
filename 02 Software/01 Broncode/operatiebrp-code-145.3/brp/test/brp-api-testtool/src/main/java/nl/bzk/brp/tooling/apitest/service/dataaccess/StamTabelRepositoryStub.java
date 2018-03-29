/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Stub voor StamTabelRepository.
 */
final class StamTabelRepositoryStub implements StamTabelRepository, StamtabelStub {

    private static final String ID = "id";
    private static final String NAAM = "naam";
    private static final String CODE = "code";
    private static final String DATAANVGEL = "dataanvgel";
    private static final String DATEINDEGEL = "dateindegel";

    private static int gemeenteIndex = 1;
    private static int naderBijhoudingaardIndex = 1;
    private static int soortPersoonIndex = 1;

    //@formatter:off
    private static final Map<String, List<Map<String, Object>>> STAMTABELLEN = ImmutableMap.<String, List<Map<String, Object>>>builder()
        .put(
            "Gemeente",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, gemeenteIndex++)
                    .put(NAAM, "Gemeente Voorschoten")
                    .put(CODE, "0626")
                    .put(DATAANVGEL, 20100101)
                    .build()))
            )
        .put(
            "SoortPersoon",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(CODE, "I")
                    .put(NAAM, "Ingeschrevene")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(CODE, "P")
                    .put(NAAM, "PseudoPersoon")
                    .build())
            )
        )
        .put(
            "SoortVrijBericht",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "Beheermelding")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.morgen())
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "Beheerverzoek")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.gisteren())
                    .build())
            )
        )
        .put(
            "VertalingBerichtsoortBRP",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "Mutatiebericht")
                    .put(DATEINDEGEL, 99991231)
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "Volledigbericht")
                    .put(DATEINDEGEL, 99991231)
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "MutatieberichtVerlopen")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.gisteren())
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put(NAAM, "VolledigberichtVerlopen")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.gisteren())
                    .build())
            )
        )
        .put(
            "VersieStufBG",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put("nr", "0310Verlopen")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.gisteren())
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, soortPersoonIndex++)
                    .put("nr", "0310")
                    .put(DATAANVGEL, DatumUtil.gisteren())
                    .put(DATEINDEGEL, DatumUtil.morgen())
                    .build())
            )
        )
        .put(
            "NadereBijhoudingsaard",
            ImmutableList.of(
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "A")
                    .put(NAAM, "Actueel")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "R")
                    .put(NAAM, "Rechtstreeks niet-ingezetene")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "E")
                    .put(NAAM, "Emigratie")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "O")
                    .put(NAAM, "Overleden")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "V")
                    .put(NAAM, "Vertrokken onbekend waarheen")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "M")
                    .put(NAAM, "Bijzondere status")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "F")
                    .put(NAAM, "Fout")
                    .build()),
                Maps.newHashMap(ImmutableMap.<String, Object>builder()
                    .put(ID, naderBijhoudingaardIndex++)
                    .put(CODE, "?")
                    .put(NAAM, "Onbekend")
                    .build())
            )
        ).build();
    //@formatter:on

    private static final Map<String, Integer> DATUM_KEYWORD_MAP = Maps.newHashMap();

    static {
        DATUM_KEYWORD_MAP.put("vandaag", DatumUtil.vandaag());
        DATUM_KEYWORD_MAP.put("morgen", DatumUtil.morgen());
        DATUM_KEYWORD_MAP.put("gisteren", DatumUtil.gisteren());
    }

    @Override
    public List<Map<String, Object>> vindAlleStamgegevensVoorTabel(final StamgegevenTabel stamgegevenTabel) {
        final String naam = stamgegevenTabel.getObjectElement().getNaam();
        if (STAMTABELLEN.containsKey(naam)) {
            return STAMTABELLEN.get(naam);
        }
        return Collections.emptyList();
    }

    @Override
    public void pasStamtabelGegevenAttribuutAanMetWaarde(final String stamtabel, final String gegeven, final String attribuut, final String waarde) {
        Assert.isTrue(STAMTABELLEN.containsKey(stamtabel), String.format("Stamtabel %s niet gevonden.", stamtabel));
        final Object geconverteerdeWaarde = converteerWaarde(waarde);
        final List<Map<String, Object>> stamgegevens = STAMTABELLEN.get(stamtabel);
        boolean stamgegevenGevonden = false;
        for (final Map<String, Object> stamgegeven : stamgegevens) {
            if (stamgegeven.get(NAAM).equals(gegeven)) {
                stamgegevenGevonden = true;
                Assert.isTrue(stamgegeven.containsKey(attribuut),
                        String.format("Attribuut %s niet gevonden in stamgegeven %s van stamtabel %s.", attribuut, stamgegeven, stamtabel));
                stamgegeven.put(attribuut, geconverteerdeWaarde);
            }
        }
        Assert.isTrue(stamgegevenGevonden, String.format("Stamgegeven %s niet gevonden in stamtabel %s.", gegeven, stamtabel));
    }

    private static Object converteerWaarde(final String waarde) {
        Object geconverteerdeWaarde;
        if (StringUtils.isNumeric(waarde)) {
            geconverteerdeWaarde = Integer.parseInt(waarde);
        } else {
            if (DATUM_KEYWORD_MAP.containsKey(waarde)) {
                geconverteerdeWaarde = DATUM_KEYWORD_MAP.get(waarde);
            } else {
                geconverteerdeWaarde = waarde;
            }
        }
        return geconverteerdeWaarde;
    }
}
