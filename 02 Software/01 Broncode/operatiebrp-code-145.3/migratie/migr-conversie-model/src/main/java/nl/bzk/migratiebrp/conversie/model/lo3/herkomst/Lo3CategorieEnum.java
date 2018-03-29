/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.herkomst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import org.apache.commons.lang3.StringUtils;

/**
 * Enumeratie van alle LO3 categorieen.
 */
public enum Lo3CategorieEnum {

    /**
     * Persoon.
     */
    CATEGORIE_01(true, "Persoon", Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP20,
            Lo3GroepEnum.GROEP61, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86,
            Lo3GroepEnum.GROEP88),
    /**
     * Ouder1.
     */
    CATEGORIE_02("Ouder1", Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP62,
            Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Ouder2.
     */
    CATEGORIE_03("Ouder2", Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP62,
            Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Nationaliteit.
     */
    CATEGORIE_04("Nationaliteit", Lo3GroepEnum.GROEP05, Lo3GroepEnum.GROEP63, Lo3GroepEnum.GROEP64, Lo3GroepEnum.GROEP65, Lo3GroepEnum.GROEP73,
            Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86, Lo3GroepEnum.GROEP88),
    /**
     * Huwelijk/geregistreerd partnerschap.
     */
    CATEGORIE_05("Huwelijk / GP", Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP06,
            Lo3GroepEnum.GROEP07, Lo3GroepEnum.GROEP15, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86),
    /**
     * Overlijden.
     */
    CATEGORIE_06("Overlijden", Lo3GroepEnum.GROEP08, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86, Lo3GroepEnum.GROEP88),
    /**
     * Inschrijving.
     */
    CATEGORIE_07(false, "Inschrijving", Lo3GroepEnum.GROEP66, Lo3GroepEnum.GROEP67, Lo3GroepEnum.GROEP68, Lo3GroepEnum.GROEP69, Lo3GroepEnum.GROEP70,
            Lo3GroepEnum.GROEP71, Lo3GroepEnum.GROEP80, Lo3GroepEnum.GROEP87, Lo3GroepEnum.GROEP88),
    /**
     * Verblijfplaats.
     */
    CATEGORIE_08("Verblijfplaats", Lo3GroepEnum.GROEP09, Lo3GroepEnum.GROEP10, Lo3GroepEnum.GROEP11, Lo3GroepEnum.GROEP12, Lo3GroepEnum.GROEP13,
            Lo3GroepEnum.GROEP14, Lo3GroepEnum.GROEP72, Lo3GroepEnum.GROEP75, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86,
            Lo3GroepEnum.GROEP88),
    /**
     * Kind.
     */
    CATEGORIE_09("Kind", Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82,
            Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Verblijfstitel.
     */
    CATEGORIE_10("Verblijfstitel", Lo3GroepEnum.GROEP39, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Gezagsverhouding.
     */
    CATEGORIE_11("Gezagsverhouding", Lo3GroepEnum.GROEP32, Lo3GroepEnum.GROEP33, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86),
    /**
     * Reisdocument.
     */
    CATEGORIE_12(false, "Reisdocument", Lo3GroepEnum.GROEP35, Lo3GroepEnum.GROEP36, Lo3GroepEnum.GROEP37, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83,
            Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Kiesrecht.
     */
    CATEGORIE_13(false, "Kiesrecht", Lo3GroepEnum.GROEP31, Lo3GroepEnum.GROEP38, Lo3GroepEnum.GROEP82),
    /**
     * Afnemersindicatie persoonslijst.
     */
    CATEGORIE_14(Lo3GroepEnum.GROEP40, Lo3GroepEnum.GROEP85),
    /**
     * Aantekening.
     */
    CATEGORIE_15(false, Lo3GroepEnum.GROEP42),
    /**
     * Verwijzing.
     * In LO 3.8 zijn groepen 11 en 12 niet meer toegestaan in categorie 21/71.
     * echter, we kunnen niet uitsluiten dat deze in oude berichten nog voorkomen bij initiele vulling en worden ze hier nog ondersteund.
     */
    CATEGORIE_21(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP09, Lo3GroepEnum.GROEP11, Lo3GroepEnum.GROEP12,
            Lo3GroepEnum.GROEP70, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Adres.
     */
    CATEGORIE_23(Lo3GroepEnum.GROEP10, Lo3GroepEnum.GROEP11, Lo3GroepEnum.GROEP12),
    /**
     * Afnemersindicatie adreslijst.
     */
    CATEGORIE_24(Lo3GroepEnum.GROEP40, Lo3GroepEnum.GROEP85),
    /**
     * Autorisatietabel.
     */
    CATEGORIE_35(),
    /**
     * Persoon (historisch).
     */
    CATEGORIE_51(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP20, Lo3GroepEnum.GROEP61,
            Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86,
            Lo3GroepEnum.GROEP88),
    /**
     * Ouder1 (historisch).
     */
    CATEGORIE_52(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP62, Lo3GroepEnum.GROEP81,
            Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Ouder2 (historisch).
     */
    CATEGORIE_53(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP62, Lo3GroepEnum.GROEP81,
            Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Nationaliteit (historisch).
     */
    CATEGORIE_54(Lo3GroepEnum.GROEP05, Lo3GroepEnum.GROEP63, Lo3GroepEnum.GROEP64, Lo3GroepEnum.GROEP65, Lo3GroepEnum.GROEP73, Lo3GroepEnum.GROEP82,
            Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86, Lo3GroepEnum.GROEP88),
    /**
     * Huwelijk/geregistreerd partnerschap (historisch).
     */
    CATEGORIE_55(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP04, Lo3GroepEnum.GROEP06, Lo3GroepEnum.GROEP07,
            Lo3GroepEnum.GROEP15, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86),
    /**
     * Overlijden (historisch).
     */
    CATEGORIE_56(Lo3GroepEnum.GROEP08, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86, Lo3GroepEnum.GROEP88),
    /**
     * Inschrijving (historisch). LET OP: Deze categorie is specifiek voor het bericht GV01 !!!!!
     */
    CATEGORIE_57(Lo3GroepEnum.GROEP66, Lo3GroepEnum.GROEP67, Lo3GroepEnum.GROEP68, Lo3GroepEnum.GROEP69, Lo3GroepEnum.GROEP70, Lo3GroepEnum.GROEP71,
            Lo3GroepEnum.GROEP80, Lo3GroepEnum.GROEP87, Lo3GroepEnum.GROEP88),
    /**
     * Verblijfplaats (historisch).
     */
    CATEGORIE_58(Lo3GroepEnum.GROEP09, Lo3GroepEnum.GROEP10, Lo3GroepEnum.GROEP11, Lo3GroepEnum.GROEP12, Lo3GroepEnum.GROEP13, Lo3GroepEnum.GROEP14,
            Lo3GroepEnum.GROEP72, Lo3GroepEnum.GROEP75, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86,
            Lo3GroepEnum.GROEP88),
    /**
     * Kind (historisch).
     */
    CATEGORIE_59(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83,
            Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Verblijfstitel (historisch).
     */
    CATEGORIE_60(Lo3GroepEnum.GROEP39, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Gezagsverhouding (historisch).
     */
    CATEGORIE_61(Lo3GroepEnum.GROEP32, Lo3GroepEnum.GROEP33, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86),
    /**
     * Reisdocument (historisch). LET OP: Deze categorie is specifiek voor het bericht GV01 !!!!!
     */
    CATEGORIE_62(Lo3GroepEnum.GROEP35, Lo3GroepEnum.GROEP36, Lo3GroepEnum.GROEP37, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP85,
            Lo3GroepEnum.GROEP86),

    /**
     * Kiesrecht (historisch). LET OP: Deze categorie is specifiek voor het bericht GV01 !!!!!
     */
    CATEGORIE_63(Lo3GroepEnum.GROEP31, Lo3GroepEnum.GROEP38, Lo3GroepEnum.GROEP82),

    /**
     * Afnemersindicatie persoonslijst (historisch).
     */
    CATEGORIE_64(Lo3GroepEnum.GROEP40, Lo3GroepEnum.GROEP85),
    /**
     * Verwijzing (historisch).
     */
    CATEGORIE_71(Lo3GroepEnum.GROEP01, Lo3GroepEnum.GROEP02, Lo3GroepEnum.GROEP03, Lo3GroepEnum.GROEP09, Lo3GroepEnum.GROEP11, Lo3GroepEnum.GROEP12,
            Lo3GroepEnum.GROEP70, Lo3GroepEnum.GROEP83, Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86),
    /**
     * Afnemersindicatie adreslijst (historisch).
     */
    CATEGORIE_74(Lo3GroepEnum.GROEP40, Lo3GroepEnum.GROEP85),
    /**
     * Autorisatietabel (historisch).
     */
    CATEGORIE_85();

    /**
     * Persoon.
     */
    public static final Lo3CategorieEnum PERSOON = CATEGORIE_01;
    /**
     * Ouder 1.
     */
    public static final Lo3CategorieEnum OUDER_1 = CATEGORIE_02;
    /**
     * Ouder 2.
     */
    public static final Lo3CategorieEnum OUDER_2 = CATEGORIE_03;
    /**
     * Huwelijk.
     */
    public static final Lo3CategorieEnum HUWELIJK = CATEGORIE_05;
    /**
     * Overlijden.
     */
    public static final Lo3CategorieEnum OVERLIJDEN = CATEGORIE_06;
    /**
     * Inschrijving.
     */
    public static final Lo3CategorieEnum INSCHRIJVING = CATEGORIE_07;
    /**
     * Verblijfplaats.
     */
    public static final Lo3CategorieEnum VERBLIJFPLAATS = CATEGORIE_08;
    /**
     * Kind.
     */
    public static final Lo3CategorieEnum KIND = CATEGORIE_09;

    private static final int HISTORIE_OFFSET = 50;
    private static final String CATEGORIE_PREFIX = "CATEGORIE_";
    private static final int AANTAL_KARAKTERS_CATEGORIE = 2;

    private final List<Lo3GroepEnum> groepen;
    private final String categorie;
    private final boolean heeftHistorie;
    private final String label;

    /**
     * Constructor met standaard historie toegestaan.
     * @param groepen Toegestane groepen
     */
    Lo3CategorieEnum(final Lo3GroepEnum... groepen) {
        this(true, groepen);
    }

    /**
     * Constructor met standaard historie toegestaan.
     * @param label Label van de categorie.
     * @param groepen Toegestane groepen
     */
    Lo3CategorieEnum(final String label, final Lo3GroepEnum... groepen) {
        this(true, label, groepen);
    }

    /**
     * Constructor.
     * @param heeftHistorie true als de categorie historie mag hebben
     * @param groepen Toegestane groepen
     */
    Lo3CategorieEnum(final boolean heeftHistorie, final Lo3GroepEnum... groepen) {
        this(heeftHistorie, null, groepen);
    }

    /**
     * Constructor.
     * @param heeftHistorie true als de categorie historie mag hebben
     * @param label Label van de categorie.
     * @param groepen Toegestane groepen
     */
    Lo3CategorieEnum(final boolean heeftHistorie, final String label, final Lo3GroepEnum... groepen) {
        this.heeftHistorie = heeftHistorie;
        final String[] enumName = name().split(CATEGORIE_PREFIX);
        categorie = enumName[1];

        final List<Lo3GroepEnum> groepNamen = new ArrayList<>();
        Collections.addAll(groepNamen, groepen);
        this.groepen = Collections.unmodifiableList(groepNamen);
        this.label = label;
    }

    /**
     * @param categorie de categorie naam
     * @return de coressponderende LO3 categorie
     */
    public static Lo3CategorieEnum getLO3Categorie(final int categorie) {
        return Lo3CategorieEnum.getEnumFromString(Lo3CategorieEnum.categorieAsIntToString(categorie));
    }

    /**
     * @param categorie de categorie naam
     * @return de corresponderende LO3 categorie
     * @throws nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException Wordt gegooid als de gevraagde categorie niet in de enumeratie voor komt.
     */
    public static Lo3CategorieEnum getLO3Categorie(final String categorie) throws Lo3SyntaxException {
        try {
            return Lo3CategorieEnum.getEnumFromString(categorie);
        } catch (final IllegalArgumentException iae) {
            throw new Lo3SyntaxException(iae);
        }
    }

    private static Lo3CategorieEnum getEnumFromString(final String categorie) {
        return Lo3CategorieEnum.valueOf(CATEGORIE_PREFIX + categorie);
    }

    /**
     * Geeft aan of het meegegeven categorienummer een geldige categorie is.
     * @param categorie de categorie code als String
     * @return true als er bij de code een bijbehorende categorie is gedefinieerd
     */
    public static boolean isValidCategorie(final String categorie) {
        try {
            Lo3CategorieEnum.getLO3Categorie(categorie);
            return true;
        } catch (final Lo3SyntaxException lso) {
            LoggerFactory.getLogger().debug("Fout tijdens valideren categorie", lso);
            return false;
        }
    }

    /**
     * Geeft aan of het meegegeven categorienummer een geldige categorie is.
     * @param categorie de categorie code als int
     * @return true als er bij de code een bijbehorende categorie is gedefinieerd
     */
    public static boolean isValidCategorie(final int categorie) {
        return Lo3CategorieEnum.isValidCategorie(Lo3CategorieEnum.categorieAsIntToString(categorie));
    }

    /**
     * Geef op basis van een actuele categorie zijn historische categorie terug.
     * @param categorie de actuele categorie
     * @return de historische categorie of null
     */
    public static Lo3CategorieEnum bepaalHistorischeCategorie(final Lo3CategorieEnum categorie) {
        return bepaalHistorischeCategorie(categorie, false);
    }

    /**
     * Geef op basis van een actuele categorie zijn historische categorie terug.
     * @param categorie de actuele categorie
     * @param negeerHeeftHistorie indien true, dan kunnen ook de 'ongeldige' historische categorieen zoals 57 worden teruggegeven
     * @return de historische categorie of null
     */
    public static Lo3CategorieEnum bepaalHistorischeCategorie(final Lo3CategorieEnum categorie, final boolean negeerHeeftHistorie) {
        Lo3CategorieEnum result = null;
        if (!categorie.isActueel()) {
            result = categorie;
        } else {
            if (categorie.heeftHistorie || negeerHeeftHistorie) {
                result = Lo3CategorieEnum.getEnumFromString(Integer.toString(categorie.getCategorieAsInt() + HISTORIE_OFFSET));
            }
        }
        return result;
    }

    /**
     * Geef op basis van een categorie zijn actuele categorie terug. Als de gegeven categorie al actueel is dan wordt de
     * categorie teruggegeven.
     * @param categorie de categorie
     * @return de meegegeven categorie als deze actueel is, anders de gevonden actuele categorie
     */
    public static Lo3CategorieEnum bepaalActueleCategorie(final Lo3CategorieEnum categorie) {
        if (categorie.isActueel()) {
            return categorie;
        }

        final String catString = categorie.getCategorie();
        final int actueel = Integer.parseInt(catString) - HISTORIE_OFFSET;
        return Lo3CategorieEnum.getEnumFromString(Lo3CategorieEnum.categorieAsIntToString(actueel));
    }

    private static String categorieAsIntToString(final int categorie) {
        return String.valueOf(StringUtils.leftPad(String.valueOf(categorie), AANTAL_KARAKTERS_CATEGORIE, '0'));
    }

    /**
     * Geef de waarde van categorie.
     * @return the categorie
     */
    public final String getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van categorie as int.
     * @return the categorie
     */
    public final int getCategorieAsInt() {
        return Integer.parseInt(categorie);
    }

    /**
     * Geef de waarde van groepen.
     * @return groepen
     */
    public final List<Lo3GroepEnum> getGroepen() {
        return groepen;
    }

    @Override
    public final String toString() {
        return categorie;
    }

    /**
     * Geef de actueel.
     * @return true als de categorie actueel is, false als de categorie historisch is
     */
    public boolean isActueel() {
        return Integer.parseInt(getCategorie()) < HISTORIE_OFFSET;
    }

    /**
     * Geef de waarde van label.
     * @return Label voor de categorie.
     */
    public String getLabel() {
        return label;
    }
}
