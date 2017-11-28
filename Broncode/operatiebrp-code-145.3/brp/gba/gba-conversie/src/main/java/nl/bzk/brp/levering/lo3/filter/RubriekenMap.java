/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.levering.lo3.mapper.AbstractOuderGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.AdresMapper;
import nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummerMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.GeslachtsnaamcomponentMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.KindGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.KindIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.KindSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapperImpl;
import nl.bzk.brp.levering.lo3.mapper.OuderGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderOuderschapMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonAfgeleidAdministratiefMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonBijhoudingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonDeelnameEuVerkiezingenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieBehandeldAlsNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieDerdeHeeftGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnderCurateleMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnverwerktDocumentAanwezigMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieStaatloosMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVastgesteldNietNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVerstrekkingsbeperkingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonInschrijvingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonMigratieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNaamgebruikMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNummerverwijzingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonOverlijdenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonPersoonskaartMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonUitsluitingKiesrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonVerblijfsrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.mapper.ReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.brp.levering.lo3.mapper.VoornaamMapper;

/**
 * Rubrieken map.
 */
public enum RubriekenMap {

    /**
     * Singleton.
     */
    INSTANCE;
    private static final String GROEP_01_02 = "01.02.";
    private static final String GROEP_01_8X = "01.8";
    private static final String GROEP_04_8X = "04.8";
    private static final String GROEP_07_80 = "07.80.";
    private static final String GROEP_11_8X = "11.8";
    private static final String GROEP_12_8X = "12.8";
    private static final String GROEP_13_8X = "13.8";
    private static final String CATEGORIE_02 = "02.";
    private static final String CATEGORIE_03 = "03.";
    private static final String GROEP_02_8X = "02.8";
    private static final String GROEP_03_8X = "03.8";
    private static final String CATEGORIE_05 = "05.";
    private static final String GROEP_05_01X = "05.01.";
    private static final String GROEP_05_02X = "05.02.";
    private static final String GROEP_05_03X = "05.03.";
    private static final String GROEP_05_04X = "05.04.";
    private static final String GROEP_05_06X = "05.06.";
    private static final String GROEP_05_07X = "05.07.";
    private static final String GROEP_05_15X = "05.15.";
    private static final String GROEP_05_8X = "05.8";
    private static final String CATEGORIE_09 = "09.";
    private static final String GROEP_09_8X = "09.8";

    private final Map<GroepElement, List<String>> rubrieken = new HashMap<>();

    /**
     * Constructor.
     */
    RubriekenMap() {
        voegIndicatiesToe();
        voegFormeelMaterieelHistorischToe();
        voegOuderToe();
        voegHuwelijkOfGeregistreerdPartnerschapToe();
        voegKindToe();
    }

    private void voegIndicatiesToe() {
        // Indicaties
        rubrieken.put(PersoonIndicatieBijzondereVerblijfsrechtelijkePositieMapper.GROEP_ELEMENT, null);
        rubrieken.put(PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.GROEP_ELEMENT, Arrays.asList("12.36.10", GROEP_12_8X));
        rubrieken.put(PersoonIndicatieVerstrekkingsbeperkingMapper.GROEP_ELEMENT, Collections.singletonList("07.70.10"));
        rubrieken.put(PersoonIndicatieBehandeldAlsNederlanderMapper.GROEP_ELEMENT, Arrays.asList("04.65.10", GROEP_04_8X));
        rubrieken.put(PersoonIndicatieDerdeHeeftGezagMapper.GROEP_ELEMENT, Arrays.asList("11.32.10", GROEP_11_8X));
        rubrieken.put(PersoonIndicatieOnderCurateleMapper.GROEP_ELEMENT, Arrays.asList("11.33.10", GROEP_11_8X));
        rubrieken.put(PersoonIndicatieStaatloosMapper.GROEP_ELEMENT, Arrays.asList("04.05.10", GROEP_04_8X));
        rubrieken.put(PersoonIndicatieVastgesteldNietNederlanderMapper.GROEP_ELEMENT, Collections.singletonList("12.65.10"));
        rubrieken.put(PersoonIndicatieOnverwerktDocumentAanwezigMapper.GROEP_ELEMENT, Collections.singletonList("08.75.10"));
    }

    private void voegFormeelMaterieelHistorischToe() {
        // Formeel/Materieel historisch
        rubrieken.put(OnderzoekMapperImpl.GROEP_ELEMENT, null);
        rubrieken.put(PersoonAfgeleidAdministratiefMapper.GROEP_ELEMENT, Arrays.asList(GROEP_07_80, "08.75."));
        rubrieken.put(PersoonDeelnameEuVerkiezingenMapper.GROEP_ELEMENT, Arrays.asList("13.31.", GROEP_13_8X));
        rubrieken.put(PersoonGeboorteMapper.GROEP_ELEMENT, Arrays.asList("01.03.", GROEP_01_8X));
        rubrieken.put(PersoonInschrijvingMapper.GROEP_ELEMENT, Arrays.asList(GROEP_07_80, "07.68."));
        rubrieken.put(PersoonNaamgebruikMapper.GROEP_ELEMENT, Arrays.asList("01.61.", GROEP_01_8X));
        rubrieken.put(PersoonOverlijdenMapper.GROEP_ELEMENT, Arrays.asList("06.08.", "06.8"));
        rubrieken.put(PersoonPersoonskaartMapper.GROEP_ELEMENT, Arrays.asList("07.69.", "07.87."));
        rubrieken.put(ReisdocumentMapper.GROEP_ELEMENT, Arrays.asList("12.35.", GROEP_12_8X));
        rubrieken.put(PersoonUitsluitingKiesrechtMapper.GROEP_ELEMENT, Arrays.asList("13.38.", GROEP_13_8X));
        rubrieken.put(AdresMapper.GROEP_ELEMENT, Collections.singletonList("08."));
        rubrieken.put(PersoonBijhoudingMapper.GROEP_ELEMENT, Collections.singletonList("07.67."));
        rubrieken.put(PersoonGeslachtsaanduidingMapper.GROEP_ELEMENT, Arrays.asList("01.04.", GROEP_01_8X));
        rubrieken.put(GeslachtsnaamcomponentMapper.GROEP_ELEMENT, Arrays.asList(GROEP_01_02, GROEP_01_8X));
        rubrieken.put(PersoonIdentificatienummersMapper.GROEP_ELEMENT, Arrays.asList("01.01.", GROEP_01_8X));
        rubrieken.put(PersoonMigratieMapper.GROEP_ELEMENT, Arrays.asList("08.13.", "08.14.", "08.8"));
        rubrieken.put(NationaliteitMapper.GROEP_ELEMENT, Arrays.asList("04.05.", "04.63.", "04.64.", GROEP_04_8X));
        rubrieken.put(BuitenlandsPersoonsnummerMapper.GROEP_ELEMENT, Collections.singletonList("04.73.10"));
        rubrieken.put(PersoonNummerverwijzingMapper.GROEP_ELEMENT, Arrays.asList("01.20.", GROEP_01_8X));
        rubrieken.put(PersoonSamengesteldeNaamMapper.GROEP_ELEMENT, Arrays.asList(GROEP_01_02, GROEP_01_8X));
        rubrieken.put(PersoonVerblijfsrechtMapper.GROEP_ELEMENT, Collections.singletonList("10."));
        rubrieken.put(VoornaamMapper.GROEP_ELEMENT, Arrays.asList(GROEP_01_02, GROEP_01_8X));
        rubrieken.put(VerificatieMapper.GROEP_ELEMENT, Arrays.asList("07.71.", "07.88."));
    }

    private void voegOuderToe() {
        // Ouder
        rubrieken.put(PersoonslijstMapper.KIND_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
        rubrieken.put(PersoonslijstMapper.GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
        rubrieken.put(OuderOuderschapMapper.GROEP_ELEMENT, Arrays.asList(CATEGORIE_02, CATEGORIE_03));
        rubrieken.put(AbstractOuderGezagMapper.GROEP_ELEMENT, Arrays.asList("11.32", GROEP_11_8X));
        rubrieken.put(OuderIdentificatienummersMapper.GROEP_ELEMENT, Arrays.asList("02.01.", GROEP_02_8X, "03.01.", GROEP_03_8X));
        rubrieken.put(OuderSamengesteldeNaamMapper.GROEP_ELEMENT, Arrays.asList("02.02.", GROEP_02_8X, "03.02.", GROEP_03_8X));
        rubrieken.put(OuderGeboorteMapper.GROEP_ELEMENT, Arrays.asList("02.03.", GROEP_02_8X, "03.03.", GROEP_03_8X));
        rubrieken.put(OuderGeslachtsaanduidingMapper.GROEP_ELEMENT, Arrays.asList("02.04.", GROEP_02_8X, "03.04.", GROEP_03_8X));
    }


    private void voegHuwelijkOfGeregistreerdPartnerschapToe() {
        // Huwelijk/Geregistreerd partnerschap
        rubrieken.put(PersoonslijstMapper.PARTNER_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_05));
        rubrieken.put(PersoonslijstMapper.GERELATEERDE_HUWELIJKSPARTNER_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_05));
        rubrieken.put(PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_05));

        rubrieken.put(HuwelijkRelatieMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_06X, GROEP_05_07X, GROEP_05_15X, GROEP_05_8X));
        rubrieken.put(GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_06X, GROEP_05_07X, GROEP_05_15X, GROEP_05_8X));

        rubrieken.put(HuwelijkIdentificatienummersMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_01X, GROEP_05_8X));
        rubrieken.put(GeregistreerdPartnerschapIdentificatienummersMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_01X, GROEP_05_8X));
        rubrieken.put(HuwelijkSamengesteldeNaamMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_02X, GROEP_05_8X));
        rubrieken.put(GeregistreerdPartnerschapSamengesteldeNaamMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_02X, GROEP_05_8X));
        rubrieken.put(HuwelijkGeboorteMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_03X, GROEP_05_8X));
        rubrieken.put(GeregistreerdPartnerschapGeboorteMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_03X, GROEP_05_8X));
        rubrieken.put(HuwelijkGeslachtsaanduidingMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_04X, GROEP_05_8X));
        rubrieken.put(GeregistreerdPartnerschapGeslachtsaanduidingMapper.GROEP_ELEMENT, Arrays.asList(GROEP_05_04X, GROEP_05_8X));
    }

    private void voegKindToe() {
        // Kind
        rubrieken.put(PersoonslijstMapper.OUDER_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_09));
        rubrieken.put(PersoonslijstMapper.GERELATEERDE_KIND_IDENTITEIT_GROEP_ELEMENT, Arrays.asList(CATEGORIE_09));
        rubrieken.put(KindIdentificatienummersMapper.GROEP_ELEMENT, Arrays.asList("09.01.", GROEP_09_8X));
        rubrieken.put(KindSamengesteldeNaamMapper.GROEP_ELEMENT, Arrays.asList("09.02.", GROEP_09_8X));
        rubrieken.put(KindGeboorteMapper.GROEP_ELEMENT, Arrays.asList("09.03.", GROEP_09_8X));
    }

    /**
     * Geef de rubrieken die geraakt worden door een bepaalde groep.
     * @param groep groep
     * @return lijst met rubrieken (kan leeg zijn)
     */
    public final List<String> getRubrieken(final GroepElement groep) {
        if (rubrieken.containsKey(groep)) {
            return rubrieken.get(groep);
        } else {
            throw new IllegalArgumentException("Onbekende groep voor rubrieken mapping: " + groep);
        }
    }

}
