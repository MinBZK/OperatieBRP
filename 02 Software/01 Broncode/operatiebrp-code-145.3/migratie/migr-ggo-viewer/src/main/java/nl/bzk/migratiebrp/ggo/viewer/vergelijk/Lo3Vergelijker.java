/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.vergelijk;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.gba.gbav.lo3.PLData;
import nl.gba.gbav.lo3.util.PLBuilderFactory;
import nl.gba.gbav.spontaan.impl.SpontaanRapportage;
import nl.gba.gbav.spontaan.verschilanalyse.PlDiffResult;
import nl.gba.gbav.spontaan.verschilanalyse.PlVerschilAnalyse;
import nl.gba.gbav.spontaan.verschilanalyse.StapelMatch;
import nl.gba.gbav.spontaan.verschilanalyse.impl.PlVerschilAnalyseImpl;
import nl.ictu.spg.domain.lo3.util.LO3LelijkParser;
import nl.ictu.spg.domain.pl.util.PLAssembler;

/**
 * Roept GBAV code aan om twee Lo3 Persoonslijsten met elkaar te vergelijken.
 */
public class Lo3Vergelijker {
    /**
     * Vergelijkt de originele Lo3Persoonslijst met de teruggeconverteerde Lo3Persoonslijst. Gebruikt het 'spontaan
     * component' uit GBAV om de vergelijking uit te voeren. Hierna worden de resultaten in een simpel formaat
     * geconverteerd zodat deze door JSON kunnen worden getoond.
     * @param origineel Lo3Persoonslijst
     * @param teruggeconverteerd Lo3Persoonslijst
     * @return Een lijst GgoVoorkomenVergelijking'en
     */
    public final List<GgoVoorkomenVergelijking> vergelijk(final Lo3Persoonslijst origineel, final Lo3Persoonslijst teruggeconverteerd) {
        List<GgoVoorkomenVergelijking> vergelijking;
        try {
            // Bepaal de verschillen tussen beide PL-en
            final PlDiffResult diffResult = bepaalVerschillen(origineel, teruggeconverteerd);
            vergelijking = convertToNormalList(diffResult, origineel.getActueelAdministratienummer());
        } catch (final IllegalStateException ise) {
            vergelijking = new ArrayList<>();
        }
        return vergelijking;
    }

    /**
     * Roept GBA-V verschil/match analyse aan om de verschillen tussen de persoonslijsten te bepalen.
     * @param origineel Lo3Persoonslijst
     * @param teruggeconverteerd Lo3Persoonslijst
     * @return PlDiffResult de verschillen
     */
    protected final PlDiffResult bepaalVerschillen(final Lo3Persoonslijst origineel, final Lo3Persoonslijst teruggeconverteerd) {
        // Gbav pl in LO3 formaat
        final PLData plGbav = createPLData(toLg01String(origineel));
        // Gbav teruggeconverteerde pl in LO3 formaat
        final PLData plGbavTerugconversie = createPLData(toLg01String(teruggeconverteerd));

        // Bepaal de verschillen tussen beide PL-en
        final PlVerschilAnalyse pda = new PlVerschilAnalyseImpl();
        return pda.bepaalVerschillen(new SpontaanRapportage(), plGbav, plGbavTerugconversie, false);
    }

    /**
     * Converteert een Lo3Persoonslijst naar een Lg01 string.
     * @param lo3Persoonslijst Lo3Persoonslijst
     * @return lg01 String
     */
    private static String toLg01String(final Lo3Persoonslijst lo3Persoonslijst) {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        lg01Bericht.setLo3Persoonslijst(lo3Persoonslijst);
        return Lo3Inhoud.formatInhoud(lg01Bericht.formatInhoud());
    }

    /**
     * Maakt een GBAV PLData object van een Lg01.
     * @param lg01 String
     * @return PLData
     */
    private static PLData createPLData(final String lg01) {
        final PLData plData = PLBuilderFactory.getPLDataBuilder().create();
        if (lg01 != null) {
            final PLAssembler assembler = new PLAssembler();
            assembler.startOfTraversal(plData);
            final LO3LelijkParser parser = new LO3LelijkParser();
            parser.parse(lg01, assembler);
        }
        return plData;
    }

    /**
     * Converteer de veredelde lijst PlDiffResult naar een voor JSON bruikbare reguliere lijst.
     *
     * Tegelijkertijd vervangen we de StapelMatch en VoorkomenMatch objecten voor onze eigen implementaties, wederom
     * vooral om de lijsten JSON parseable te maken.
     */
    private static List<GgoVoorkomenVergelijking> convertToNormalList(final PlDiffResult diffResult, final String aNummer) {
        final List<GgoVoorkomenVergelijking> stapelMatches = new ArrayList<>();

        for (int i = 0; i < diffResult.countStapelMatches(); i++) {
            final StapelMatch stapelMatch = diffResult.getStapelMatch(i);
            for (int j = 0; j < stapelMatch.countVoorkomenMatches() + 1; j++) {
                if (stapelMatch.getVoorkomenMatch(j) != null && stapelMatch.getType() != StapelMatch.IDENTICAL) {
                    final GgoVoorkomenVergelijking vv =
                            new GgoVoorkomenVergelijking(stapelMatch.getVoorkomenMatch(j), aNummer, bepaalType(stapelMatch.getType()));
                    if (vv.getInhoud() != null && !vv.getInhoud().isEmpty()) {
                        stapelMatches.add(vv);
                    }
                }
            }
        }

        return stapelMatches;
    }

    private static String bepaalType(final int type) {
        final String typeDescription;
        switch (type) {
            case StapelMatch.IDENTICAL:
                typeDescription = "Zelfde";
                break;
            case StapelMatch.CHANGED:
                typeDescription = "Gewijzigd";
                break;
            case StapelMatch.NEW:
                typeDescription = "Extra";
                break;
            case StapelMatch.DELETED:
                typeDescription = "Ontbreekt";
                break;
            case StapelMatch.NON_UNIQUE_MATCHED:
                typeDescription = "Onbepaald";
                break;
            default:
                typeDescription = "Fout";
        }
        return typeDescription;
    }
}
