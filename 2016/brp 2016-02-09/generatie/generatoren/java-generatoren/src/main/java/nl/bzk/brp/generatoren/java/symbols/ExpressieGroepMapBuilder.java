/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import org.apache.commons.lang.StringUtils;

/**
 * Builder voor een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de expressietaal oplevert. De
 * expressiemap wordt gebruikt voor o.a. migratiedoeleinden en is een tijdelijk product.
 */
public final class ExpressieGroepMapBuilder {

    private static final String AFGELEID_ADMINISTRATIEF = "afgeleid_administratief.";

    /**
     * Constructor. Private voor utility class.
     */
    private ExpressieGroepMapBuilder() {
    }

    /**
     * Maakt een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de expressietaal oplevert.
     *
     * @param symbols Symbols waarvoor een expressiemap gemaakt moet worden.
     * @return Java-klasse.
     */
    public static JavaKlasse maakExpressieGroepMap(final Collection<Symbol> symbols) {
        final JavaKlasse klasse = new JavaKlasse(SymbolTableConstants.EXPRESSIE_GROEP_MAP_KLASSENAAM,
            "Mapping van id's van gegevenselementen uit BMR (sync-id) op BRP-expressies.",
            SymbolTableConstants.SYMBOLS_PACKAGE, true);
        klasse.voegExtraImportsToe(JavaType.MAP, JavaType.HASH_MAP, JavaType.LIST, JavaType.ARRAY_LIST);

        final Constructor constructor = new Constructor(JavaAccessModifier.PRIVATE, klasse);
        constructor.setJavaDoc("Private constructor voor utility class.");
        klasse.voegConstructorToe(constructor);
        klasse.voegExtraImportsToe(JavaType.ELEMENT_ENUM);

        final JavaType keyType = JavaType.ELEMENT_ENUM;
        final JavaType valueType = JavaType.LIST;
        valueType.voegGenericParameterToe(JavaType.STRING);
        final JavaType returnType = new JavaType(JavaType.MAP.getNaam(), JavaType.MAP.getPackagePad());
        returnType.voegGenericParameterToe(keyType);
        returnType.voegGenericParameterToe(valueType);

        maakFunctieVoorVerantwoordingMap(symbols, klasse, keyType, valueType, returnType);
        maakFunctieVoorFormeleHistorieMap(symbols, klasse, keyType, valueType, returnType);
        maakFunctieVoorMaterieleHistorieMap(symbols, klasse, keyType, valueType, returnType);

        return klasse;
    }

    private static void maakFunctieVoorVerantwoordingMap(final Collection<Symbol> symbols, final JavaKlasse klasse,
        final JavaType keyType, final JavaType valueType,
        final JavaType returntype)
    {
        final JavaFunctie functie =
            new JavaFunctie(JavaAccessModifier.PUBLIC, returntype, "getGroepVerantwoordingMap",
                "Mapping van id's van groep-elementen op BRP-expressies", true);
        functie.setJavaDoc("Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.");
        klasse.voegFunctieToe(functie);

        final StringBuilder body = new StringBuilder();
        final String localeVariabele = "expressieGroepVerantwoordingMap";
        body.append(String.format("Map<%s, %s<%s>> %s = new HashMap<>();", keyType.getNaam(), valueType.getNaam(),
            valueType.getGenericParameter(0).getJavaType().getNaam(), localeVariabele));

        Set<String> verwerkteGroepen = new HashSet<>();
        for (Symbol symbol : symbols) {
            if (!(symbol instanceof ActieAttribuutSymbol)) {
                //Alleen actie symbols hier...
                continue;
            }

            final ActieAttribuutSymbol actieSymbol = (ActieAttribuutSymbol) symbol;

            final String elementEnumWaarde = "ElementEnum." + bepaalElementEnumNaamVoorGroep(actieSymbol.getParentGroep());
            if (!verwerkteGroepen.contains(elementEnumWaarde)) {
                voegExpressieLijstToe(body, localeVariabele, actieSymbol);
                verwerkteGroepen.add(elementEnumWaarde);
            }

            String expressie = null;
            if (symbol.getParentExpressieType() == null || symbol.getParentExpressieType().equals(SymbolTableConstants.PERSOON_TYPE)) {
                expressie = "$" + symbol.getSyntax();
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.BETROKKENHEID_TYPE)
                && !symbol.getBmrAttribuut().getNaam().equalsIgnoreCase("Rol")
                && !elementEnumWaarde.equals("ElementEnum.OUDER_OUDERSCHAP"))
            {
                expressie =
                    String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))", symbol.getSyntax());
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.HUWELIJK_TYPE)
                && !elementEnumWaarde.equals("ElementEnum.RELATIE_AFGELEIDADMINISTRATIEF"))
            {
                expressie = String.format("RMAP(HUWELIJKEN(), h, $h.%s)", symbol.getSyntax());
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.GEREGISTREERDPARTNERSCHAP_TYPE)
                && !elementEnumWaarde.equals("ElementEnum.RELATIE_AFGELEIDADMINISTRATIEF"))
            {
                expressie = String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", symbol.getSyntax());
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.FAMILIERECHTELIJKEBETREKKING_TYPE)
                && !elementEnumWaarde.equals("ElementEnum.RELATIE_AFGELEIDADMINISTRATIEF"))
            {
                expressie = String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)", symbol.getSyntax());
            } else if (symbol.getParentSymbol() != null
                && !symbol.getParentExpressieType().equals(SymbolTableConstants.AFNEMERINDICATIE_TYPE))
            {
                expressie = String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                    symbol.getSyntax());
            }

            if (expressie != null) {
                voegExpressieToe(body, localeVariabele, actieSymbol, expressie);
            }
        }

        body.append(String.format("return %s;", localeVariabele));
        functie.setBody(body.toString());
    }

    private static void maakFunctieVoorFormeleHistorieMap(final Collection<Symbol> symbols, final JavaKlasse klasse,
        final JavaType keyType, final JavaType valueType,
        final JavaType returntype)
    {
        final JavaFunctie functie =
            new JavaFunctie(JavaAccessModifier.PUBLIC, returntype, "getGroepFormeleHistorieMap",
                "Mapping van id's van groep-elementen op BRP-expressies", true);
        functie.setJavaDoc("Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.");
        klasse.voegFunctieToe(functie);

        StringBuilder body = new StringBuilder();
        final String localeVariabele = "expressieGroepFormeleHistorieMap";
        body.append(String.format("Map<%s, %s<%s>> %s = new HashMap<>();", keyType.getNaam(), valueType.getNaam(),
            valueType.getGenericParameter(0).getJavaType().getNaam(), localeVariabele));

        Set<String> verwerkteGroepen = new HashSet<>();
        for (Symbol symbol : symbols) {
            if (symbol instanceof ActieAttribuutSymbol) {
                //Alleen actie symbols hier...

                voegNormaleActieSymbolToe(body, localeVariabele, verwerkteGroepen, symbol,
                    (ActieAttribuutSymbol) symbol);

            }
        }

        body.append(String.format("return %s;", localeVariabele));
        functie.setBody(body.toString());
    }

    private static void voegNormaleActieSymbolToe(final StringBuilder body, final String localeVariabele,
        final Set<String> verwerkteGroepen, final Symbol symbol,
        final ActieAttribuutSymbol actieSymbol)
    {
        //Alle groepen hebben een verantwoordingInhoud, misbruik deze om de groepsyntax te bepalen:
        final int eindIndexGroepSyntax = symbol.getSyntax().indexOf("verantwoordingInhoud");

        final String parentSyntax;
        if (symbol.getParentSymbol() != null) {
            parentSyntax = symbol.getParentSymbol().getSyntax();
        } else {
            parentSyntax = "";
        }

        final String groepSyntax;
        if (eindIndexGroepSyntax >= 0) {
            groepSyntax = symbol.getSyntax().substring(0, eindIndexGroepSyntax);
        } else {
            return;
        }
        if (verwerkteGroepen.contains(parentSyntax + groepSyntax)) {
            return;
        }
        verwerkteGroepen.add(parentSyntax + groepSyntax);

        voegExpressieLijstToe(body, localeVariabele, actieSymbol);

        final String tijdstipRegistratieSyntax = groepSyntax + "datum_tijd_registratie";
        final String tijdstipVervalSyntax = groepSyntax + "datum_tijd_verval";

        if (symbol.getParentExpressieType().equals(SymbolTableConstants.PERSOON_TYPE)) {
            voegExpressieToe(body, localeVariabele, actieSymbol, "$" + tijdstipRegistratieSyntax);
            voegExpressieToe(body, localeVariabele, actieSymbol, "$" + tijdstipVervalSyntax);
        } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.BETROKKENHEID_TYPE)
            && !symbol.getBmrAttribuut().getNaam().equalsIgnoreCase("Rol")
            && !groepSyntax.equals("ouderschap."))
        {
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))",
                                                                               tijdstipRegistratieSyntax));
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))",
                                                                               tijdstipVervalSyntax));
        } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.HUWELIJK_TYPE) && !groepSyntax.equals(AFGELEID_ADMINISTRATIEF)) {
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(HUWELIJKEN(), h, $h.%s)", tijdstipRegistratieSyntax));
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(HUWELIJKEN(), h, $h.%s)", tijdstipVervalSyntax));
        } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.GEREGISTREERDPARTNERSCHAP_TYPE) && !groepSyntax.equals(
            AFGELEID_ADMINISTRATIEF)) {
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", tijdstipRegistratieSyntax));
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", tijdstipVervalSyntax));
        } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.FAMILIERECHTELIJKEBETREKKING_TYPE) && !groepSyntax.equals(
            AFGELEID_ADMINISTRATIEF)) {
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)", tijdstipRegistratieSyntax));
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)", tijdstipVervalSyntax));
        } else if (symbol.getParentSymbol() != null && !symbol.getParentExpressieType().equals(SymbolTableConstants.AFNEMERINDICATIE_TYPE)) {
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                                                                               tijdstipRegistratieSyntax));
            voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                                                                               tijdstipVervalSyntax));
        }
    }

    private static void maakFunctieVoorMaterieleHistorieMap(final Collection<Symbol> symbols, final JavaKlasse klasse,
        final JavaType keyType, final JavaType valueType,
        final JavaType returntype)
    {
        final JavaFunctie functie =
            new JavaFunctie(JavaAccessModifier.PUBLIC, returntype, "getGroepMaterieleHistorieMap",
                "Mapping van id's van groep-elementen op BRP-expressies", true);
        functie.setJavaDoc("Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.");
        klasse.voegFunctieToe(functie);

        StringBuilder body = new StringBuilder();
        final String localeVariabele = "expressieGroepMaterieleHistorieMap";
        body.append(String.format("Map<%s, %s<%s>> %s = new HashMap<>();", keyType.getNaam(), valueType.getNaam(),
            valueType.getGenericParameter(0).getJavaType().getNaam(), localeVariabele));

        Set<String> verwerkteGroepen = new HashSet<>();
        for (Symbol symbol : symbols) {
            if (!(symbol instanceof ActieAttribuutSymbol)) {
                //Alleen groep symbols hier...
                continue;
            }

            final ActieAttribuutSymbol actieSymbol = (ActieAttribuutSymbol) symbol;

            //Alle materiele groepen hebben een verantwoordingAanpassingGeldigheid, misbruik deze om de groepsyntax te bepalen:
            final int eindIndexGroepSyntax = symbol.getSyntax().indexOf("verantwoordingAanpassingGeldigheid");

            final String parentSyntax;
            if (symbol.getParentSymbol() != null) {
                parentSyntax = symbol.getParentSymbol().getSyntax();
            } else {
                parentSyntax = "";
            }

            final String groepSyntax;
            if (eindIndexGroepSyntax >= 0) {

                groepSyntax = symbol.getSyntax().substring(0, eindIndexGroepSyntax);
            } else {
                continue;
            }
            if (verwerkteGroepen.contains(parentSyntax + groepSyntax)) {
                continue;
            }
            verwerkteGroepen.add(parentSyntax + groepSyntax);

            voegExpressieLijstToe(body, localeVariabele, actieSymbol);


            final String datumAanvangGeldigheidSyntax = groepSyntax + "datum_aanvang_geldigheid";
            final String datumEindeGeldigheidSyntax = groepSyntax + "datum_einde_geldigheid";

            if (symbol.getParentExpressieType().equals(SymbolTableConstants.PERSOON_TYPE)) {
                voegExpressieToe(body, localeVariabele, actieSymbol, "$" + datumAanvangGeldigheidSyntax);
                voegExpressieToe(body, localeVariabele, actieSymbol, "$" + datumEindeGeldigheidSyntax);
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.BETROKKENHEID_TYPE)
                && !symbol.getBmrAttribuut().getNaam().equalsIgnoreCase("Rol")
                && !groepSyntax.equals("ouderschap."))
            {
                voegExpressieToe(body, localeVariabele, actieSymbol,
                    String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))",
                        datumAanvangGeldigheidSyntax));
                voegExpressieToe(body, localeVariabele, actieSymbol,
                    String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))",
                        datumEindeGeldigheidSyntax));
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.HUWELIJK_TYPE) && !groepSyntax.equals(AFGELEID_ADMINISTRATIEF)) {
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(HUWELIJKEN(), h, $h.%s)", datumAanvangGeldigheidSyntax));
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(HUWELIJKEN(), h, $h.%s)", datumEindeGeldigheidSyntax));
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.GEREGISTREERDPARTNERSCHAP_TYPE)
                && !groepSyntax.equals(AFGELEID_ADMINISTRATIEF))
            {
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", datumAanvangGeldigheidSyntax));
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", datumEindeGeldigheidSyntax));
            } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.FAMILIERECHTELIJKEBETREKKING_TYPE)
                && !groepSyntax.equals(AFGELEID_ADMINISTRATIEF))
            {
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)",
                                                                                   datumAanvangGeldigheidSyntax));
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)",
                                                                                   datumEindeGeldigheidSyntax));
            } else if (symbol.getParentSymbol() != null && !symbol.getParentExpressieType().equals(SymbolTableConstants.AFNEMERINDICATIE_TYPE)) {
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                                                                                   datumAanvangGeldigheidSyntax));
                voegExpressieToe(body, localeVariabele, actieSymbol, String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                                                                                   datumEindeGeldigheidSyntax));
            }

        }

        body.append(String.format("return %s;", localeVariabele));
        functie.setBody(body.toString());
    }

    private static void voegExpressieLijstToe(final StringBuilder body, final String localeVariabeleVerantwoording,
        final ActieAttribuutSymbol actieSymbol)
    {
        final String elementEnumWaarde = "ElementEnum." + bepaalElementEnumNaamVoorGroep(actieSymbol.getParentGroep());
        body.append(String.format("%s.put(%s,%n", localeVariabeleVerantwoording,
            elementEnumWaarde));
        body.append(String.format("%s);%n", "new ArrayList<String>()"));
    }

    private static void voegExpressieToe(final StringBuilder body, final String localeVariabeleVerantwoording,
        final ActieAttribuutSymbol actieSymbol, final String expressie)
    {
        final String elementEnumWaarde = "ElementEnum." + bepaalElementEnumNaamVoorGroep(actieSymbol.getParentGroep());
        body.append(String.format("%s.get(%s).add(\"%s\");%n", localeVariabeleVerantwoording,
            elementEnumWaarde, expressie));
    }

    private static String bepaalElementEnumNaamVoorGroep(final GeneriekElement generiekElementGroep) {
        GeneriekElement generiekElementOuder = generiekElementGroep.getGeneriekElementOuder();

        String identCodeGenElementGroep = null;
        if (generiekElementGroep != null) {
            identCodeGenElementGroep = generiekElementGroep.getIdentCode();
        }
        String identCodeGenElementOuder = null;
        if (generiekElementOuder != null) {
            identCodeGenElementOuder = generiekElementOuder.getIdentCode();
        }

        final StringBuffer resultaat = new StringBuffer();
        if (identCodeGenElementOuder != null) {

            // De enum waarden worden bij persoon gesplitst met underscore
            if (identCodeGenElementOuder.contains("Persoon")) {
                resultaat.append(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(identCodeGenElementOuder), '_'));
            } else {
                resultaat.append(identCodeGenElementOuder);
            }

            resultaat.append("_");
        }
        if (identCodeGenElementGroep != null) {
            resultaat.append(identCodeGenElementGroep);
        }

        return resultaat.toString().toUpperCase();
    }
}
