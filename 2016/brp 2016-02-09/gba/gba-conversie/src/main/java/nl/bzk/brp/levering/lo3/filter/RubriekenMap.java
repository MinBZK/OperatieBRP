/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Rubrieken map.
 */
public class RubriekenMap {

    /** Rubrieken map voor persoonsgegevens. */
    public static final RubriekenMap PERSOON = new PersoonRubriekenMap();
    /** Rubrieken map voor kindgegevens. */
    public static final RubriekenMap KIND = new KindRubriekenMap();
    /** Rubrieken map voor oudergegevens. */
    public static final RubriekenMap OUDER = new OuderRubriekenMap();
    /** Rubrieken map voor huwelijksgegevens. */
    public static final RubriekenMap HUWELIJK = new HuwelijkRubriekenMap();

    private final Map<Class<? extends HistorieEntiteit>, List<String>> rubrieken;

    /**
     * Constructor.
     *
     * @param rubrieken
     *            rubrieken map
     */
    protected RubriekenMap(final Map<Class<? extends HistorieEntiteit>, List<String>> rubrieken) {
        this.rubrieken = rubrieken;
    }

    /**
     * Geef de rubrieken die geraakt worden door een bepaalde entiteit.
     *
     * @param entiteit
     *            entiteit
     * @return lijst met rubrieken (kan leeg zijn)
     */
    public final List<String> getRubrieken(final Class<?> entiteit) {
        if (rubrieken.containsKey(entiteit)) {
            return rubrieken.get(entiteit);
        } else {
            throw new IllegalArgumentException("Onbekend entiteit klasse voor rubrieken mapping: " + entiteit);
        }
    }

    /**
     * Persoon rubrieken.
     */
    public static final class PersoonRubriekenMap extends RubriekenMap {

        private static final String GROEP_01_02 = "01.02.";
        private static final String GROEP_01_8X = "01.8";
        private static final String GROEP_04_8X = "04.8";
        private static final String GROEP_07_80 = "07.80.";
        private static final String GROEP_11_8X = "11.8";
        private static final String GROEP_12_8X = "12.8";
        private static final String GROEP_13_8X = "13.8";

        private static final Map<Class<? extends HistorieEntiteit>, List<String>> RUBRIEKEN = new HashMap<>();

        static {
            // Indicaties
            RUBRIEKEN.put(HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel.class, null);
            RUBRIEKEN.put(HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel.class, Arrays.asList("12.36.10", GROEP_12_8X));
            RUBRIEKEN.put(HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel.class, Collections.singletonList("07.70.10"));
            RUBRIEKEN.put(HisPersoonIndicatieBehandeldAlsNederlanderModel.class, Arrays.asList("04.65.10", GROEP_04_8X));
            RUBRIEKEN.put(HisPersoonIndicatieDerdeHeeftGezagModel.class, Arrays.asList("11.32.10", GROEP_11_8X));
            RUBRIEKEN.put(HisPersoonIndicatieOnderCurateleModel.class, Arrays.asList("11.33.10", GROEP_11_8X));
            RUBRIEKEN.put(HisPersoonIndicatieStaatloosModel.class, Arrays.asList("04.05.10", GROEP_04_8X));
            RUBRIEKEN.put(HisPersoonIndicatieVastgesteldNietNederlanderModel.class, Collections.singletonList("12.65.10"));
        }

        static {
            // Formeel/Materieel historisch
            RUBRIEKEN.put(HisDocumentModel.class, null);
            RUBRIEKEN.put(HisOnderzoekAfgeleidAdministratiefModel.class, null);
            RUBRIEKEN.put(HisOnderzoekModel.class, null);
            // RUBRIEKEN.put(HisPartijOnderzoekModel.class, null);
            RUBRIEKEN.put(HisPersoonAfgeleidAdministratiefModel.class, Arrays.asList(GROEP_07_80, "08.75."));
            RUBRIEKEN.put(HisPersoonDeelnameEUVerkiezingenModel.class, Arrays.asList("13.31.", GROEP_13_8X));
            RUBRIEKEN.put(HisPersoonGeboorteModel.class, Arrays.asList("01.03.", GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonInschrijvingModel.class, Arrays.asList(GROEP_07_80, "07.68."));
            RUBRIEKEN.put(HisPersoonNaamgebruikModel.class, Arrays.asList("01.61.", GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonOnderzoekModel.class, null);
            RUBRIEKEN.put(HisPersoonOverlijdenModel.class, Arrays.asList("06.08.", "06.8"));
            RUBRIEKEN.put(HisPersoonPersoonskaartModel.class, Arrays.asList("07.69.", "07.87."));
            RUBRIEKEN.put(HisPersoonReisdocumentModel.class, Arrays.asList("12.35.", GROEP_12_8X));
            RUBRIEKEN.put(HisPersoonUitsluitingKiesrechtModel.class, Arrays.asList("13.38.", GROEP_13_8X));
            RUBRIEKEN.put(HisPersoonAdresModel.class, Collections.singletonList("08."));
            RUBRIEKEN.put(HisPersoonBijhoudingModel.class, Collections.singletonList("07.67."));
            RUBRIEKEN.put(HisPersoonGeslachtsaanduidingModel.class, Arrays.asList("01.04.", GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonGeslachtsnaamcomponentModel.class, Arrays.asList(GROEP_01_02, GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonIdentificatienummersModel.class, Arrays.asList("01.01.", GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonMigratieModel.class, Arrays.asList("08.13.", "08.14.", "08.8"));
            RUBRIEKEN.put(HisPersoonNationaliteitModel.class, Arrays.asList("04.05.", "04.63.", "04.64.", GROEP_04_8X));
            RUBRIEKEN.put(HisPersoonNummerverwijzingModel.class, Arrays.asList("01.20.", GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonSamengesteldeNaamModel.class, Arrays.asList(GROEP_01_02, GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonVerblijfsrechtModel.class, Collections.singletonList("10."));
            RUBRIEKEN.put(HisPersoonVoornaamModel.class, Arrays.asList(GROEP_01_02, GROEP_01_8X));
            RUBRIEKEN.put(HisPersoonVerificatieModel.class, Arrays.asList("07.71.", "07.88."));
        }

        /**
         * Constructor.
         */
        protected PersoonRubriekenMap() {
            super(RUBRIEKEN);
        }
    }

    /**
     * Ouder rubrieken.
     */
    public static final class OuderRubriekenMap extends RubriekenMap {

        private static final String CATEGORIE_02 = "02.";
        private static final String CATEGORIE_03 = "03.";
        private static final String GROEP_02_8X = "02.8";
        private static final String GROEP_03_8X = "03.8";

        private static final Map<Class<? extends HistorieEntiteit>, List<String>> RUBRIEKEN = new HashMap<>();

        static {
            RUBRIEKEN.put(HisBetrokkenheidModel.class, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
            RUBRIEKEN.put(HisRelatieModel.class, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
            RUBRIEKEN.put(HisOuderOuderschapModel.class, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
            RUBRIEKEN.put(HisOuderOuderlijkGezagModel.class, Arrays.asList("11.32", "11.8"));

            RUBRIEKEN.put(HisPersoonIdentificatienummersModel.class, Arrays.asList("02.01.", GROEP_02_8X, "03.01.", GROEP_03_8X));
            RUBRIEKEN.put(HisPersoonSamengesteldeNaamModel.class, Arrays.asList("02.02.", GROEP_02_8X, "03.02.", GROEP_03_8X));
            RUBRIEKEN.put(HisPersoonGeboorteModel.class, Arrays.asList("02.03.", GROEP_02_8X, "03.03.", GROEP_03_8X));
            RUBRIEKEN.put(HisPersoonGeslachtsaanduidingModel.class, Arrays.asList("02.04.", GROEP_02_8X, "03.04.", GROEP_03_8X));
        }

        /**
         * Constructor.
         */
        protected OuderRubriekenMap() {
            super(RUBRIEKEN);
        }
    }

    /**
     * Huwelijk/Geregistreerd partnerschap rubrieken.
     */
    public static final class HuwelijkRubriekenMap extends RubriekenMap {

        private static final String GROEP_05_8X = "05.8";

        private static final Map<Class<? extends HistorieEntiteit>, List<String>> RUBRIEKEN = new HashMap<>();

        static {
            RUBRIEKEN.put(HisBetrokkenheidModel.class, Arrays.asList("05."));
            RUBRIEKEN.put(HisRelatieModel.class, Arrays.asList("05.06.", "05.07.", "05.15.", GROEP_05_8X));

            RUBRIEKEN.put(HisPersoonIdentificatienummersModel.class, Arrays.asList("05.01.", GROEP_05_8X));
            RUBRIEKEN.put(HisPersoonSamengesteldeNaamModel.class, Arrays.asList("05.02.", GROEP_05_8X));
            RUBRIEKEN.put(HisPersoonGeboorteModel.class, Arrays.asList("05.03.", GROEP_05_8X));
            RUBRIEKEN.put(HisPersoonGeslachtsaanduidingModel.class, Arrays.asList("05.04.", GROEP_05_8X));
        }

        /**
         * Constructor.
         */
        protected HuwelijkRubriekenMap() {
            super(RUBRIEKEN);
        }
    }

    /**
     * Kind rubrieken.
     */
    public static final class KindRubriekenMap extends RubriekenMap {

        private static final String CATEGORIE_09 = "09.";
        private static final String GROEP_09_8X = "09.8";

        private static final Map<Class<? extends HistorieEntiteit>, List<String>> RUBRIEKEN = new HashMap<>();

        static {
            RUBRIEKEN.put(HisBetrokkenheidModel.class, Arrays.asList(CATEGORIE_09));
            RUBRIEKEN.put(HisRelatieModel.class, Arrays.asList(CATEGORIE_09));

            RUBRIEKEN.put(HisPersoonIdentificatienummersModel.class, Arrays.asList("09.01.", GROEP_09_8X));
            RUBRIEKEN.put(HisPersoonSamengesteldeNaamModel.class, Arrays.asList("09.02.", GROEP_09_8X));
            RUBRIEKEN.put(HisPersoonGeboorteModel.class, Arrays.asList("09.03.", GROEP_09_8X));
        }

        /**
         * Constructor.
         */
        protected KindRubriekenMap() {
            super(RUBRIEKEN);
        }
    }
}
