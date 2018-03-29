/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.expressie.ConverteerNaarExpressieService;
import nl.bzk.migratiebrp.conversie.regels.expressie.Lo3VoorwaardeRegelOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.mapping.RubriekMapping;
import nl.bzk.migratiebrp.conversie.regels.expressie.mapping.RubriekMapping.Expressie;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie, van LO3 rubrieken en voorwaarderegels
 * naar BRP expressies.
 */
@Service
public final class ConverteerNaarExpressieServiceImpl implements ConverteerNaarExpressieService {
    private static final String EXCEPTIE_MESSAGE_START_MARKER = "[[";
    private static final String EXCEPTIE_MESSAGE_END_MARKER = "]]";
    private static final String SLEUTEL_EXPRESSIE = "GEWIJZIGD(oud, nieuw, [%s])";
    private static final String SLEUTEL_EXPRESSIE_FUNCTIE = "GEWIJZIGD(%s, %s)";
    private static final String SLEUTEL_EXPRESSIE_PARENT_LIJST = "GEWIJZIGD(oud, nieuw, [%s.%s])";
    private static final String SLEUTEL_EXPRESSIE_PARENT_FUNCTIE = "GEWIJZIGD(%s, %s, [%s])";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String ENKELE_SPATIE = " ";
    private static final String DUBBELE_SPATIE = "  ";

    private static final List<String> SPATIE_PATTERNS =
            Arrays.asList(
                    "(ENVWD)",
                    "(OFVWD)",
                    "(ENVGL)",
                    "(OFVGL)",
                    "(?<![\\(])(KV)",
                    "(?<![\\(])(KNV)",
                    "(?<!O)(GA1)",
                    "(?<!O)(GAA)",
                    "(OGA1)",
                    "(OGAA)",
                    "(GD1)",
                    "(GDA)",
                    "(KD1)",
                    "(KDA)",
                    "(GDOG1)",
                    "(GDOGA)",
                    "(KDOG1)",
                    "(KDOGA)",
                    "([\\-])",
                    "([\\+])");
    private static final String END_PARENTHESIS = ")";
    private static final String REGEX_ADELLIJKE_TITEL_OF_PREDICAAT = "^(01|02|03|05|09|51|52|53|55|59)\\.02\\.20.*";
    private static final String OF = " OF ";
    private static final int INDEX_ADELLIJKE_TITEL = 0;
    private static final int INDEX_PREDIKAAT = 1;

    private final GbaVoorwaardeRegelFactory gbaVoorwaardeRegelFactory;

    /**
     * Constructor.
     * @param gbaVoorwaardeRegelFactory factory voor voorwaarde regels
     */
    @Inject
    public ConverteerNaarExpressieServiceImpl(final GbaVoorwaardeRegelFactory gbaVoorwaardeRegelFactory) {
        this.gbaVoorwaardeRegelFactory = gbaVoorwaardeRegelFactory;
    }

    @Override
    public String converteerSleutelRubrieken(final String autorisatieRubrieken, final Lo3Herkomst herkomst) {
        final List<String> rubrieken = bepaalRubrieken(autorisatieRubrieken);
        final StringBuilder attenderingsCriterium = new StringBuilder();

        for (final String rubriek : rubrieken) {
            if (!converteerSleutelRubriek(rubriek, attenderingsCriterium)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.AUT007, null);
            }
        }

        return attenderingsCriterium.length() == 0 ? null : attenderingsCriterium.toString();
    }

    private List<String> bepaalRubrieken(final String autorisatieRubrieken) {
        if (autorisatieRubrieken == null || "".equals(autorisatieRubrieken)) {
            return Collections.emptyList();
        }

        final String[] rubrieknummers = autorisatieRubrieken.split("#");
        return Arrays.asList(rubrieknummers);
    }

    private boolean converteerSleutelRubriek(final String lo3Rubriek, final StringBuilder attenderingsCriterium) {

        boolean result = true;

        if (lo3Rubriek != null && !"".equals(lo3Rubriek)) {
            final List<Expressie> brpExpressies = RubriekMapping.getExpressiesVoorRubriek(lo3Rubriek);
            if (brpExpressies != null && !lo3Rubriek.matches(REGEX_ADELLIJKE_TITEL_OF_PREDICAAT)) {
                for (final Expressie brpExpressie : brpExpressies) {
                    wrapAndAppend(attenderingsCriterium, brpExpressie);
                    result = true;
                }
            } else if (brpExpressies != null && lo3Rubriek.matches(REGEX_ADELLIJKE_TITEL_OF_PREDICAAT)) {
                maakAdellijkeTitelOfPredicaatAttenderingsCriterium(attenderingsCriterium, brpExpressies);

            } else {
                LOG.warn("Er kan geen expressie gevonden worden voor sleutelrubriek  {}", lo3Rubriek);
                result = false;
            }
        }

        return result;
    }

    private void maakAdellijkeTitelOfPredicaatAttenderingsCriterium(StringBuilder attenderingsCriterium, List<Expressie> brpExpressies) {
        // gewijzigd adellijke titel OF gewijzigd predicaat OF (gewijzigd geslacht en (kv adellijke titel OF kv predicaat))
        plaatsOfIndienNodig(attenderingsCriterium);
        attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE, brpExpressies.get(INDEX_ADELLIJKE_TITEL).getExpressie()));
        attenderingsCriterium.append(OF);
        attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE, brpExpressies.get(INDEX_PREDIKAAT).getExpressie()));
    }

    private void plaatsOfIndienNodig(final StringBuilder attenderingsCriterium) {
        if (attenderingsCriterium.length() > 0) {
            attenderingsCriterium.append(OF);
        }
    }

    private void wrapAndAppend(final StringBuilder attenderingsCriterium, final Expressie brpExpressie) {
        plaatsOfIndienNodig(attenderingsCriterium);

        if (brpExpressie.getParent() == null) {
            if (brpExpressie.getExpressie().endsWith(END_PARENTHESIS)) {
                final String oud;
                final String nieuw;
                if (!brpExpressie.getExpressie().contains("FILTER") && !brpExpressie.getExpressie().contains("ACTIE")) {
                    // Expressie is functie (bv ALS(x,y,z))
                    final int indexHaakjeOpenen = brpExpressie.getExpressie().indexOf('(') + 1;
                    final String functieStart = brpExpressie.getExpressie().substring(0, indexHaakjeOpenen);
                    final String functieParametersEnd = brpExpressie.getExpressie().substring(indexHaakjeOpenen);

                    final String regex = "(^|, )(?!\\{})";
                    oud = functieStart + functieParametersEnd.replaceAll(regex, "$1oud.");
                    nieuw = functieStart + functieParametersEnd.replaceAll(regex, "$1nieuw.");
                } else if (brpExpressie.getExpressie().contains("ACTIE")) {
                    // Expressie is actie functie (bv ACTIE(veld, object))
                    final int indexHaakjeOpenen = brpExpressie.getExpressie().indexOf('(') + 1;
                    final String functieStart = brpExpressie.getExpressie().substring(0, indexHaakjeOpenen);
                    final String functieParametersEnd = brpExpressie.getExpressie().substring(indexHaakjeOpenen);
                    oud = functieStart + "oud." + functieParametersEnd;
                    nieuw = functieStart + "nieuw." + functieParametersEnd;
                } else {
                    oud = brpExpressie.getExpressie().replace("FILTER(", "FILTER(oud.");
                    nieuw = brpExpressie.getExpressie().replace("FILTER(", "FILTER(nieuw.");
                }
                attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE_FUNCTIE, oud, nieuw));
            } else {
                attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE, brpExpressie.getExpressie()));
            }
        } else if (!brpExpressie.getExpressie().contains("FILTER")) {
            if (brpExpressie.getParent().endsWith(END_PARENTHESIS)) {
                // Parent is functie (bv attribuut uit HUWELIJKEN())
                final int indexHaakjeOpenen = brpExpressie.getParent().indexOf('(') + 1;
                final String functieStart = brpExpressie.getParent().substring(0, indexHaakjeOpenen);
                final String functieEnd = brpExpressie.getParent().substring(indexHaakjeOpenen);

                final String oud;
                final String nieuw;
                if (functieEnd.length() > 1) {
                    oud = functieStart + "oud," + functieEnd;
                    nieuw = functieStart + "nieuw," + functieEnd;
                } else {
                    oud = functieStart + "oud" + functieEnd;
                    nieuw = functieStart + "nieuw" + functieEnd;
                }
                attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE_PARENT_FUNCTIE, oud, nieuw, brpExpressie.getExpressie()));
            } else {
                attenderingsCriterium.append(String.format(SLEUTEL_EXPRESSIE_PARENT_LIJST, brpExpressie.getParent(), brpExpressie.getExpressie()));
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public String converteerVoorwaardeRegel(final String lo3Voorwaarderegel) {
        String brpExpressieResultaat;
        LOG.debug("Converteren Lo3 voorwaarderegel: {}", lo3Voorwaarderegel);

        if (lo3Voorwaarderegel != null && !"".equals(lo3Voorwaarderegel)) {
            final VoorwaardeRegelNaarEnkelvoudigeVoorwaarde naarEnkelvoudig =
                    new VoorwaardeRegelNaarEnkelvoudigeVoorwaarde(normaliseerSpaties(lo3Voorwaarderegel));
            brpExpressieResultaat = naarEnkelvoudig.getVoorwaardeRegel();
            final Map<String, String> gbaVoorwaarden = naarEnkelvoudig.getGbaVoorwaardeMap();
            boolean indicatieMislukt = false;

            GbaVoorwaardeOnvertaalbaarExceptie oorzaak = null;
            for (final Map.Entry<String, String> gbaVoorwaarde : gbaVoorwaarden.entrySet()) {
                try {
                    LOG.debug("Converteren enkelvoudige voorwaarde: {}", gbaVoorwaarde.getValue());
                    final RubriekWaarde rubriekWaarde = new RubriekWaarde(gbaVoorwaarde.getValue());
                    final GbaVoorwaardeRegel regel = gbaVoorwaardeRegelFactory.maakGbaVoorwaardeRegel(rubriekWaarde);
                    LOG.debug("Regel: {}", regel);
                    brpExpressieResultaat =
                            maakBrpExpressie(brpExpressieResultaat, gbaVoorwaarde.getKey(), regel.getBrpExpressie(rubriekWaarde).getBrpExpressie());
                } catch (final GbaVoorwaardeOnvertaalbaarExceptie gvoe) {
                    indicatieMislukt = true;
                    oorzaak = gvoe;
                    brpExpressieResultaat =
                            maakBrpExpressie(
                                    brpExpressieResultaat,
                                    gbaVoorwaarde.getKey(),
                                    EXCEPTIE_MESSAGE_START_MARKER + gbaVoorwaarde.getValue() + EXCEPTIE_MESSAGE_END_MARKER);
                } catch (final IllegalArgumentException e) {
                    indicatieMislukt = true;
                    oorzaak = new GbaVoorwaardeOnvertaalbaarExceptie(e.getMessage(), e);
                    brpExpressieResultaat =
                            maakBrpExpressie(
                                    brpExpressieResultaat,
                                    gbaVoorwaarde.getKey(),
                                    EXCEPTIE_MESSAGE_START_MARKER + gbaVoorwaarde.getValue() + EXCEPTIE_MESSAGE_END_MARKER);
                }
            }

            if (indicatieMislukt) {
                throw new Lo3VoorwaardeRegelOnvertaalbaarExceptie(
                        String.format("Voorwaarderegel: [%s] is onvertaalbaar", lo3Voorwaarderegel),
                        brpExpressieResultaat,
                        oorzaak);

            }
        } else {
            // Een lege voorwaarde regel in LO3 betekend dat er geen beperking is. Dat is in BRP anders; daar moet het
            // dan "WAAR" zijn.
            brpExpressieResultaat = "WAAR";
        }

        return brpExpressieResultaat;
    }

    /**
     * Normaliseer de spaties in een voorwaarde regel.
     * @param lo3VoorwaardeRegel voorwaarde regel
     * @return voorwaarde regel met genormaliseerde spaties
     */
    public static String normaliseerSpaties(final String lo3VoorwaardeRegel) {
        final TekstHelper tekstHelper = new TekstHelper(lo3VoorwaardeRegel);
        if (tekstHelper.getVeiligeRegel().indexOf('\"') > -1) {
            throw new Lo3VoorwaardeRegelOnvertaalbaarExceptie(
                    lo3VoorwaardeRegel,
                    null,
                    new IllegalArgumentException("Onsamenhangend aantal quotes om tekst aan te duiden"));
        }

        String result = tekstHelper.getVeiligeRegel();
        // Omring alle operatoren met spaties (indien die er niet staan)
        for (final String operator : SPATIE_PATTERNS) {
            result = result.replaceAll(operator + "(?!\\s)", "$1 ");
            result = result.replaceAll("(?<!\\s)" + operator, " $1");
        }

        // Verwijder begin en eind spaties
        result = result.trim();

        // Verwijderen dubbele spaties binnen de voorwaarderegel
        while (result.contains(DUBBELE_SPATIE)) {
            result = result.replaceAll(DUBBELE_SPATIE, ENKELE_SPATIE);
        }

        tekstHelper.setVeiligeRegel(result);
        return tekstHelper.getGbaVoorwaardeRegel();
    }

    private String maakBrpExpressie(final String volledigeBrpExpressie, final String sleutel, final String brpExpressie) {
        final int start = volledigeBrpExpressie.indexOf(sleutel);
        if (start > -1) {
            final StringBuilder resultBuilder = new StringBuilder(volledigeBrpExpressie.substring(0, start));
            resultBuilder.append(brpExpressie);
            resultBuilder.append(volledigeBrpExpressie.substring(start + sleutel.length()));
            return maakBrpExpressie(resultBuilder.toString(), sleutel, brpExpressie);
        }
        return volledigeBrpExpressie;
    }
}
