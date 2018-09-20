/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.Collection;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import org.apache.commons.lang.StringUtils;

/**
 * Builder voor een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de
 * expressietaal oplevert. De expressiemap wordt gebruikt voor o.a. migratiedoeleinden en is een tijdelijk product.
 */
public final class ExpressieMapBuilder {

    private static final String VERSIE_TAAL = "1.7.5-SNAPSHOT";
    private static final String VERSIE_GENERATOR = "3.36.0-SNAPSHOT";

    /**
     * Constructor. Private voor utility class.
     */
    private ExpressieMapBuilder() {
    }

    /**
     * Maakt een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de
     * expressietaal oplevert.
     *
     * @param symbols Symbols waarvoor een expressiemap gemaakt moet worden.
     * @return Java-klasse.
     */
    public static JavaKlasse maakExpressieMap(final Collection<Symbol> symbols) {
        final JavaKlasse klasse = new JavaKlasse(SymbolTableConstants.EXPRESSIEMAP_KLASSENAAM,
                "Mapping van id's van gegevenselementen uit BMR (sync-id) op BRP-expressies.",
                SymbolTableConstants.SYMBOLS_PACKAGE, true);
        klasse.voegExtraImportsToe(JavaType.MAP, JavaType.HASH_MAP);

        JavaVeld versienummerVeld = new JavaVeld(JavaType.STRING, "VERSIE_EXPRESSIETAAL", true);
        versienummerVeld.setAccessModifier(JavaAccessModifier.PUBLIC);
        versienummerVeld.setStatic(true);
        versienummerVeld.setGeinstantieerdeWaarde("\"" + VERSIE_TAAL + "\"");
        versienummerVeld.setJavaDoc("Versienummer van de expressietaal.");
        klasse.voegVeldToe(versienummerVeld);

        versienummerVeld = new JavaVeld(JavaType.STRING, "VERSIE_GENERATOR", true);
        versienummerVeld.setAccessModifier(JavaAccessModifier.PUBLIC);
        versienummerVeld.setStatic(true);
        versienummerVeld.setGeinstantieerdeWaarde("\"" + VERSIE_GENERATOR + "\"");
        versienummerVeld.setJavaDoc("Versienummer van de generator.");
        klasse.voegVeldToe(versienummerVeld);

        final Constructor constructor = new Constructor(JavaAccessModifier.PRIVATE, klasse);
        constructor.setJavaDoc("Private constructor voor utility class.");
        klasse.voegConstructorToe(constructor);

        final JavaType keyType = JavaType.INTEGER;
        final JavaType valueType = JavaType.STRING;
        final JavaType returntype = new JavaType(JavaType.MAP.getNaam(), JavaType.MAP.getPackagePad());
        returntype.voegGenericParameterToe(keyType);
        returntype.voegGenericParameterToe(valueType);

        final JavaFunctie functie =
                new JavaFunctie(JavaAccessModifier.PUBLIC, returntype, SymbolTableConstants.EXPRESSIEMAP_METHODENAAM,
                        "Mapping van id's van gegevenselementen op BRP-expressies", true);
        functie.setJavaDoc("Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.");
        klasse.voegFunctieToe(functie);

        final StringBuilder body = new StringBuilder();
        final String localeVariabele = "expressieMap";
        body.append(String.format("final Map<%s, %s> %s = new HashMap<%s, %s>();", keyType.getNaam(), valueType.getNaam(),
                localeVariabele, keyType.getNaam(), valueType.getNaam()));

        int indicatieId = -1;

        for (Symbol symbol : symbols) {
            if (symbol instanceof ActieAttribuutSymbol || symbol instanceof IndicatieActieAttribuutSymbol) {
                //Actie symbols zijn hier niet nodig, omdat ze niet in de expressietabel terechtkomen...
                continue;
            }
            if (symbol.getSyntax().contains("datum_tijd_registratie")
                    || symbol.getSyntax().contains("datum_tijd_verval")
                    || symbol.getSyntax().contains("datum_aanvang_geldigheid")
                    || symbol.getSyntax().contains("datum_einde_geldigheid"))
            {
                continue;
            }

            if (!symbol.isIndexed()) {
                int id;

                if (symbol.getBmrAttribuut() != null) {
                    id = symbol.getBmrAttribuut().getSyncid();
                } else {
                    id = indicatieId;
                    indicatieId--;
                }

                String expressie = null;
                if (symbol.getParentExpressieType().equals(SymbolTableConstants.PERSOON_TYPE)) {
                    expressie = "$" + symbol.getSyntax();
                } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.BETROKKENHEID_TYPE)
                    && !"Rol".equalsIgnoreCase(symbol.getBmrAttribuut().getNaam()))
                {
                    expressie = String.format("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.%s))", symbol.getSyntax());
                } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.HUWELIJK_TYPE)) {
                    expressie = String.format("RMAP(HUWELIJKEN(), h, $h.%s)", symbol.getSyntax());
                } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.GEREGISTREERDPARTNERSCHAP_TYPE)) {
                    expressie = String.format("RMAP(PARTNERSCHAPPEN(), h, $h.%s)", symbol.getSyntax());
                } else if (symbol.getParentExpressieType().equals(SymbolTableConstants.FAMILIERECHTELIJKEBETREKKING_TYPE)) {
                    expressie = String.format("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.%s)", symbol.getSyntax());
                } else if (symbol.getParentSymbol() != null
                        && !symbol.getParentExpressieType().equals(SymbolTableConstants.AFNEMERINDICATIE_TYPE))
                {
                    expressie = String.format("RMAP(%s, v, $v.%s)", symbol.getParentSymbol().getSyntax(),
                            symbol.getSyntax());
                }

                if (expressie != null) {
                    final StringBuilder idNameBuilder = new StringBuilder();
                    idNameBuilder.append("id");
                    if (symbol.getBmrAttribuut() == null) {
                        idNameBuilder.append(symbol.getSyntax());
                    } else if (symbol.getBmrAttribuut().getGroep() != null) {
                        idNameBuilder.append(symbol.getBmrAttribuut().getObjectType().getIdentCode())
                            .append(StringUtils.capitalize(symbol.getObjectTypeNaam().toLowerCase()))
                            .append(symbol.getBmrAttribuut().getGroep().getIdentCode())
                            .append(symbol.getBmrAttribuut().getIdentCode());
                    } else {
                        idNameBuilder.append(symbol.getBmrAttribuut().getObjectType().getIdentCode()).append(symbol.getBmrAttribuut().getIdentCode());
                    }
                    final String idName = idNameBuilder.toString().replace("_", "").replace(".", "");
                    body.append(String.format("final int %s = %d;%n", idName, id));
                    body.append(String.format("%s.put(%s,%n", localeVariabele, idName));
                    body.append(String.format("\"%s\");%n", expressie));
                }
            }
        }

        body.append(String.format("return %s;", localeVariabele));
        functie.setBody(body.toString());

        return klasse;
    }
}
