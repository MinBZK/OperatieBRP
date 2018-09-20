/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;

/**
 * Constanten voor de generatie van de symbol table (BRP-expressietaal).
 */
public final class SymbolTableConstants {

    /**
     * Private constructor voor utility class.
     */
    private SymbolTableConstants() {
    }

    /**
     * Packages en javaklassen.
     */
    private static final String BRP_PACKAGE           = GeneratiePackage.BRP_BASEPACKAGE.getPackage();
    private static final String EXPRESSIETAAL_PACKAGE = BRP_PACKAGE + ".expressietaal";
    private static final String EXPRESSIES_PACKAGE    = EXPRESSIETAAL_PACKAGE + ".expressies";
    /**
     * Java-packagenaam voor literals.
     */
    public static final  String LITERALS_PACKAGE      = EXPRESSIES_PACKAGE + ".literals";
    /**
     * Java-packagenaam voor symbols.
     */
    public static final  String SYMBOLS_PACKAGE       = EXPRESSIETAAL_PACKAGE + ".symbols";
    /**
     * Naam van attributenenumeratie voor BRP-expressietaal.
     */
    public static final  String ATTRIBUTES_ENUM_NAAM  = "ExpressieAttribuut";
    public static final  String GROEPEN_ENUM_NAAM     = "ExpressieGroep";
    /**
     * Java-packagenaam voor attribute getter-classes.
     */
    public static final  String SOLVERS_PACKAGE       = SYMBOLS_PACKAGE + ".solvers";

    /**
     * Klassenaam voor expressietypes.
     */
    public static final String EXPRESSIETYPE_KLASSENAAM  = "ExpressieType";
    /**
     * Klassenaam voor lijst-expressies.
     */
    public static final String LIJSTEXPRESSIE_KLASSENAAM = "LijstExpressie";
    /**
     * Interfacenaam voor expressies.
     */
    public static final String EXPRESSIE_KLASSENAAM      = "Expressie";

    private static final String NULLVALUE_KLASSENAAM            = "NullValue";
    private static final String ATTRIBUTEGETTER_KLASSENAAM      = "AttributeGetter";
    private static final String GROEPGETTER_KLASSENAAM          = "GroepGetter";
    private static final String ROOTOBJECT_KLASSENAAM           = "BrpObject";
    private static final String ATTRIBUUT_KLASSENAAM            = "Attribuut";
    private static final String GROEP_KLASSENAAM                = "Groep";
    /**
     * Klassenaam voor expressies die verwijzen naar een BRP-object.
     */
    public static final  String ROOTOBJECT_EXPRESSIE_KLASSENAAM = "BrpObjectExpressie";

    private static final String MODELBASIS_PACKAGE = GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage();

    /**
     * Javatype voor attribute getters.
     */
    public static final JavaType ATTRIBUTEGETTER_JAVATYPE = new JavaType(ATTRIBUTEGETTER_KLASSENAAM, SOLVERS_PACKAGE);
    public static final JavaType GROEPGETTER_JAVATYPE     = new JavaType(GROEPGETTER_KLASSENAAM, SOLVERS_PACKAGE);
    /**
     * Javatype ExpressieType.
     */
    public static final JavaType EXPRESSIETYPE_JAVATYPE   = new JavaType(EXPRESSIETYPE_KLASSENAAM, EXPRESSIETAAL_PACKAGE);
    /**
     * Javatype Expressie.
     */
    public static final JavaType EXPRESSIE_JAVATYPE       = new JavaType(EXPRESSIE_KLASSENAAM, EXPRESSIETAAL_PACKAGE);
    /**
     * Javatype voor BMR-rootobjecten.
     */
    public static final JavaType ROOTOBJECT_JAVATYPE      = new JavaType(ROOTOBJECT_KLASSENAAM, MODELBASIS_PACKAGE);
    /**
     * Javatype voor BMR-attributen.
     */
    public static final JavaType ATTRIBUUT_JAVATYPE       = new JavaType(ATTRIBUUT_KLASSENAAM, MODELBASIS_PACKAGE);
    public static final JavaType GROEP_JAVATYPE           = new JavaType(GROEP_KLASSENAAM, MODELBASIS_PACKAGE);
    /**
     * Javatype ListExpressie.
     */
    public static final JavaType LIJSTEXPRESSIE_JAVATYPE  = new JavaType(LIJSTEXPRESSIE_KLASSENAAM, EXPRESSIES_PACKAGE);
    /**
     * Javatype NullValue.
     */
    public static final JavaType NULLVALUE_JAVATYPE       = new JavaType(NULLVALUE_KLASSENAAM, LITERALS_PACKAGE);

    /**
     * Expressie om een NULL-waarde in een BRP-expressie te gebruiken.
     */
    public static final String NULLVALUE_EXPRESSIE = String.format("%s.getInstance()", NULLVALUE_KLASSENAAM);

    /**
     * Overige JavaTypes
     */
    public static final JavaType PERSOON_HIS_MOMENT_JAVATYPE                    = new JavaType("PersoonHisMoment", "nl.bzk.brp.model.logisch.kern");
    public static final JavaType PERSOON_INDICATIE_JAVATYPE                     = new JavaType("PersoonIndicatie", "nl.bzk.brp.model.logisch.kern");
    public static final JavaType PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE = new JavaType("HisPersoonIndicatieStandaardGroep", "nl.bzk"
        + ".brp.model.logisch.kern");

    /**
     * Basistypes uit expressietaal.
     */
    /**
     * ExpressieType STRING.
     */
    public static final String STRING_TYPE                                = "STRING";
    /**
     * ExpressieType NUMBER.
     */
    public static final String NUMBER_TYPE                                = "GETAL";
    /**
     * ExpressieType BIG_NUMBER_TYPE.
     */
    public static final String BIG_NUMBER_TYPE                            = "GROOT_GETAL";
    /**
     * ExpressieType BOOLEAN.
     */
    public static final String BOOLEAN_TYPE                               = "BOOLEAN";
    /**
     * ExpressieType DATE.
     */
    public static final String DATE_TYPE                                  = "DATUM";
    /**
     * ExpressieType DATETIME.
     */
    public static final String DATETIME_TYPE                              = "DATUMTIJD";
    /**
     * ExpressieType PERSOON.
     */
    public static final String PERSOON_TYPE                               = "PERSOON";
    /**
     * ExpressieType ADRES.
     */
    public static final String ADRES_TYPE                                 = "ADRES";
    /**
     * ExpressieType VOORNAAM.
     */
    public static final String VOORNAAM_TYPE                              = "VOORNAAM";
    /**
     * ExpressieType GESLACHTSNAAMCOMPONENT.
     */
    public static final String GESLACHTSNAAMCOMPONENT_TYPE                = "GESLACHTSNAAMCOMPONENT";
    /**
     * ExpressieType NATIONALITEIT.
     */
    public static final String NATIONALITEIT_TYPE                         = "NATIONALITEIT";
    /**
     * ExpressieType INDICATIE.
     */
    public static final String INDICATIE_TYPE                             = "INDICATIE";
    /**
     * ExpressieType VERIFICATIE.
     */
    public static final String VERIFICATIE_TYPE                           = "VERIFICATIE";
    /**
     * ExpressieType REISDOCUMENT.
     */
    public static final String REISDOCUMENT_TYPE                          = "REISDOCUMENT";
    /**
     * ExpressieType PERSOON_ONDERZOEK.
     */
    public static final String PERSOON_ONDERZOEK_TYPE                            = "PERSOON_ONDERZOEK";
    /**
     * ExpressieType ONDERZOEK.
     */
    public static final String ONDERZOEK_TYPE                             = "ONDERZOEK";
    /**
     * ExpressieType GEGEVEN_IN_ONDERZOEK
     */
    public static final String GEGEVEN_IN_ONDERZOEK_TYPE                  = "GEGEVEN_IN_ONDERZOEK";
    /**
     * ExpressieType PARTIJ_ONDERZOEK
     */
    public static final String PARTIJ_ONDERZOEK_TYPE                      = "PARTIJ_ONDERZOEK";
    /**
     * ExpressieType MULTIREALITEIT.
     */
    public static final String MULTIREALITEITREGEL_TYPE                   = "MULTIREALITEITREGEL";
    /**
     * ExpressieType MULTIREALITEIT.
     */
    public static final String MULTIREALITEITREGEL_VERBORGENPERSONEN_TYPE = "MULTIREALITEITREGEL_VERBORGENPERSONEN";
    /**
     * ExpressieType BETROKKENHEID.
     */
    public static final String BETROKKENHEID_TYPE                         = "BETROKKENHEID";
    /**
     * ExpressieType AFNEMERINDICATIE.
     */
    public static final String AFNEMERINDICATIE_TYPE                      = "AFNEMERINDICATIE";
    /**
     * ExpressieType PERSOONADMINISTRATIEVEHANDELING.
     */
    public static final String PERSOONADMINISTRATIEVEHANDELING_TYPE       = "PERSOONADMINISTRATIEVEHANDELING";
    /**
     * ExpressieType VERSTREKKINGSBEPERKING.
     */
    public static final String VERSTREKKINGSBEPERKING_TYPE                = "VERSTREKKINGSBEPERKING";
    /**
     * ExpressieType HUWELIJK.
     */
    public static final String HUWELIJK_TYPE                              = "HUWELIJK";
    /**
     * ExpressieType PARTNERSCHAP.
     */
    public static final String GEREGISTREERDPARTNERSCHAP_TYPE             = "GEREGISTREERDPARTNERSCHAP";
    /**
     * ExpressieType FAMILIERECHTELIJKEBETREKKING.
     */
    public static final String FAMILIERECHTELIJKEBETREKKING_TYPE          = "FAMILIERECHTELIJKEBETREKKING";
    /**
     * Getter class (command class) identifiers.
     */
    /**
     * Naam van de methode om de waarde van een attribuut op te vragen.
     */
    public static final String GETATTRIBUUTWAARDE_METHODENAAM             = "getAttribuutWaarde";
    /**
     * Naam van de methode om een attribuut op te vragen.
     */
    public static final String GETATTRIBUUT_METHODENAAM                   = "getAttribuut";
    /**
     * Naam van de methode om historische attributen op te vragen.
     */
    public static final String GETHISTORISCHEATTRIBUTEN_METHODENAAM       = "getHistorischeAttributen";

    /**
     * Naam van de methode om een attribuut op te vragen.
     */
    public static final String GETGROEP_METHODENAAM              = "getGroep";
    /**
     * Naam van de methode om historische attributen op te vragen.
     */
    public static final String GETHISTORISCHEGROEPEN_METHODENAAM = "getHistorischeGroepen";

    /**
     * Naam van de parameter van de get-functie die verwijst naar het object waarvan een attribuut of waarde bepaald
     * moet worden.
     */
    public static final String OBJECTPARAMETERNAAM                  = "brpObject";
    /**
     * Naam van de methode in de (LGM) klasse Attribuut die de waarde van een attribuut oplevert.
     */
    public static final String BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM = "getWaarde";

    /**
     * Naam van de Javaklasse die de mapping van element-id's naar expressietaalattributen bevat.
     */
    public static final String EXPRESSIEMAP_KLASSENAAM = "ExpressieMap";

    public static final String EXPRESSIE_GROEP_MAP_KLASSENAAM = "ExpressieGroepMap";

    /**
     * Naam van de methode in de utility class (ExpressieMap) die de mapping van element-id's op attributen geeft.
     */
    public static final String EXPRESSIEMAP_METHODENAAM = "getMap";


    /**
     * Afbeelding van types uit de expressietaal naar de bijbehorende Expressie-klassen.
     */
    public static final Map<String, String> EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS;
    /**
     * Afbeelding van types uit de expressietaal naar Java basic types.
     */
    public static final Map<String, String> EXPRESSIETYPE_NAAR_JAVA_TYPE;
    /**
     * Afbeelding van BMR objecttypes naar types uit de expressietaal.
     */
    public static final Map<String, String> BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE;

    static {
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS = new HashMap<>();
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.BIG_NUMBER_TYPE, "GrootGetalLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.STRING_TYPE, "StringLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.NUMBER_TYPE, "GetalLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.DATE_TYPE, "DatumLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.DATETIME_TYPE, "DateTimeLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.BOOLEAN_TYPE, "BooleanLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.PERSOON_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.ADRES_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.VOORNAAM_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.GESLACHTSNAAMCOMPONENT_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.NATIONALITEIT_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.INDICATIE_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.VERIFICATIE_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.ONDERZOEK_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.MULTIREALITEITREGEL_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.REISDOCUMENT_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.AFNEMERINDICATIE_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.VERSTREKKINGSBEPERKING_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);
        EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.put(SymbolTableConstants.BETROKKENHEID_TYPE, ROOTOBJECT_EXPRESSIE_KLASSENAAM);

        EXPRESSIETYPE_NAAR_JAVA_TYPE = new HashMap<>();
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.STRING_TYPE, "String");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.NUMBER_TYPE, "Integer");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.DATE_TYPE, "Integer");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.DATETIME_TYPE, "Integer");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.BOOLEAN_TYPE, "Boolean");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.PERSOON_TYPE, "PersoonHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.ADRES_TYPE, "PersoonAdresHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.VOORNAAM_TYPE, "PersoonVoornaamHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.GESLACHTSNAAMCOMPONENT_TYPE, "PersoonGeslachtsnaamcomponentHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.NATIONALITEIT_TYPE, "PersoonNationaliteitHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.INDICATIE_TYPE, "PersoonIndicatieHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.VERIFICATIE_TYPE, "PersoonVerificatieHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.REISDOCUMENT_TYPE, "PersoonReisdocumentHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.ONDERZOEK_TYPE, "OnderzoekHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.PERSOON_ONDERZOEK_TYPE, "PersoonOnderzoekHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.MULTIREALITEITREGEL_TYPE, "MultirealiteitRegelHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.AFNEMERINDICATIE_TYPE, "PersoonAfnemerindicatieHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.VERSTREKKINGSBEPERKING_TYPE, "PersoonVerstrekkingsbeperkingHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put(SymbolTableConstants.BETROKKENHEID_TYPE, "BetrokkenheidHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put("Ouder", "OuderHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put("HUWELIJK", "HuwelijkHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put("GEREGISTREERDPARTNERSCHAP", "GeregistreerdPartnerschapHisVolledig");
        EXPRESSIETYPE_NAAR_JAVA_TYPE.put("FAMILIERECHTELIJKEBETREKKING", "FamilierechtelijkeBetrekkingHisVolledig");

        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE = new HashMap<>();
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Adres", SymbolTableConstants.ADRES_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Voornaam", SymbolTableConstants.VOORNAAM_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Geslachtsnaamcomponent", SymbolTableConstants.GESLACHTSNAAMCOMPONENT_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Nationaliteit", SymbolTableConstants.NATIONALITEIT_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Indicatie", SymbolTableConstants.INDICATIE_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Verificatie", SymbolTableConstants.VERIFICATIE_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Verstrekkingsbeperking", SymbolTableConstants.VERSTREKKINGSBEPERKING_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Reisdocument", SymbolTableConstants.REISDOCUMENT_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Onderzoek", SymbolTableConstants.PERSOON_ONDERZOEK_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Gegeven in onderzoek", SymbolTableConstants.GEGEVEN_IN_ONDERZOEK_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Partij \\ Onderzoek", SymbolTableConstants.PARTIJ_ONDERZOEK_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Multirealiteit regel", SymbolTableConstants.MULTIREALITEITREGEL_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Multirealiteit regel \\ Verborgen persoon", SymbolTableConstants.MULTIREALITEITREGEL_VERBORGENPERSONEN_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Betrokkenheid", SymbolTableConstants.BETROKKENHEID_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Afnemerindicatie", SymbolTableConstants.AFNEMERINDICATIE_TYPE);
        BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.put("Persoon \\ Administratieve handeling", SymbolTableConstants.PERSOONADMINISTRATIEVEHANDELING_TYPE);

    }
}
