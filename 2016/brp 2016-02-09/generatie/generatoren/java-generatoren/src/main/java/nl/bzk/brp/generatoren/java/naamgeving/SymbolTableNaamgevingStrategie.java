/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.symbols.Symbol;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Klasse voor naamgeving van symbolen in de BRP-expressietaal.
 */
public class SymbolTableNaamgevingStrategie {

    /**
     * Bepaalt de naam van een attribuut op basis van het attribuut zelf en van de groep en objecttype waartoe het
     * attribuut behoort.
     *
     * @param attribuut          Attribuut waarvoor een naam bepaald moet worden.
     * @param groepNaam          Naam van de groep waartoe het attribuut behoort.
     * @param objectTypeNaam     Naam van het objecttype waartoe het attribuut behoort.
     * @param isInverseAttribuut TRUE als het een inverse attribuut betreft, anders FALSE.
     * @return Naam van het attribuut.
     */
    public String bepaalAttribuutNaam(final Attribuut attribuut, final String groepNaam, final String objectTypeNaam,
                                      final boolean isInverseAttribuut)
    {
        final String volledigeAttribuutNaam;
        if (!isInverseAttribuut) {
            volledigeAttribuutNaam = bepaalAttribuutNaam(attribuut.getNaam(), groepNaam, objectTypeNaam);
        } else {
            volledigeAttribuutNaam =
                    bepaalAttribuutNaam(attribuut.getInverseAssociatieIdentCode(), groepNaam, objectTypeNaam);
        }
        return volledigeAttribuutNaam;
    }

    /**
     * Bepaalt de naam van een attribuut op basis van het attribuut zelf en van de groep en objecttype waartoe het
     * attribuut behoort.
     *
     * @param attribuutNaam  Naam van het attribuut waarvoor een volledige naam bepaald moet worden.
     * @param groepNaam      Naam van de groep waartoe het attribuut behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het attribuut behoort.
     * @return Naam van het attribuut.
     */
    public String bepaalAttribuutNaam(final String attribuutNaam, final String groepNaam, final String objectTypeNaam) {
        String volledigeAttribuutNaam = attribuutNaam.toLowerCase().replace("/", " ");
        volledigeAttribuutNaam = JavaGeneratieUtil.cleanUpInvalidJavaCharacters(volledigeAttribuutNaam);

        /*
         *TODO: Dit is een work-around voor een specifiek attribuut, omdat ergens onderweg tekens met diakrieten
         *TODO: niet correct worden vertaald. Mogelijk gebeurt dat in Hibernate.
         */
        if (volledigeAttribuutNaam.equals("datum aanvang materi�le periode")) {
            volledigeAttribuutNaam = "datum aanvang materiele periode";
        }

        volledigeAttribuutNaam = JavaGeneratieUtil.normalise(volledigeAttribuutNaam);
        volledigeAttribuutNaam = vereenvoudigAttribuutNaam(volledigeAttribuutNaam, groepNaam, objectTypeNaam);
        volledigeAttribuutNaam = volledigeAttribuutNaam.replace(' ', '_');

        if (groepNaam.length() > 0 && volledigeAttribuutNaam.length() > 0) {
            volledigeAttribuutNaam = String.format("%s.%s", groepNaam, volledigeAttribuutNaam);
        } else if (volledigeAttribuutNaam.length() == 0) {
            volledigeAttribuutNaam = groepNaam;
        }
        return volledigeAttribuutNaam;
    }

    /**
     * Probeert de attribuutnaam te vereenvoudigen.
     *
     * @param attribuutnaam  Naam van het attribuut.
     * @param groepnaam      Naam van de groep waartoe het attribuut behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het attribuut behoort.
     * @return De vereenvoudigde attribuutnaam.
     */
    private String vereenvoudigAttribuutNaam(final String attribuutnaam, final String groepnaam,
                                             final String objectTypeNaam)
    {
        String result = " " + attribuutnaam + " ";
        result = result.replaceAll(" " + groepnaam + " ", "");

        if (groepnaam.toLowerCase().startsWith(objectTypeNaam.toLowerCase() + ".")) {
            final String substr = groepnaam.substring(objectTypeNaam.length() + 1);
            final String teVervangenResult = " " + substr + " ";

            // Toevallig dezelfde groep/attribuut naam moet niet weggefilterd worden
            if (!teVervangenResult.equals(result)) {
                result = result.replaceAll(teVervangenResult, "");
            }
        }

        return result.trim();
    }

    /**
     * Stel de symbolische naam van de groep samen op basis van de BMR-groep en het objecttype.
     *
     * @param groep      Groep waarvoor de naam gemaakt wordt.
     * @param objectType Objecttype waartoe de groep behoort.
     * @return Symbolische naam van de groep.
     */
    public String maakGroepNaam(final Groep groep, final ObjectType objectType, final boolean isIndicatie) {
        String groepNaam;
        if (groep != null
            && !groep.getIdentCode().equals(AbstractGenerator.STANDAARD)
            && !groep.getIdentCode().equals(AbstractGenerator.IDENTITEIT))
        {
            final String groepDeel = groep.getNaam().replace(' ', '_');
            groepNaam = String.format("%s.%s", objectType.getIdentCode(), groepDeel);
        } else {
            groepNaam = String.format("%s", objectType.getIdentCode());
        }
        groepNaam = JavaGeneratieUtil.normalise(groepNaam).toLowerCase();

        // Sommige namen van groepen worden vereenvoudigd/ingekort of aangepast ten behoeve van leesbaarheid.
        if ("persoon.afgeleid_administratief".equals(groepNaam)) {
            groepNaam = "persoon.administratief";
        } else if ("persoon.curatele".equals(groepNaam)) {
            groepNaam = "persoon.onder.curatele";
            groep.setIdentCode("OnderCuratele");
        } else if ("persoon.gezag_derde".equals(groepNaam)) {
            groepNaam = "persoon.derde.heeft.gezag";
            groep.setIdentCode("DerdeHeeftGezag");
        }

        return groepNaam;
    }

    /**
     * Probeert de syntax van een symbool te vereenvoudigen op basis van de groepnaam en het feit dat het mogelijk een
     * 'subattribuut' is van een inverse attribuut.
     *
     * @param attribuutnaam           Naam van het attribuut.
     * @param groepnaam               Naam van de groep waartoe het attribuut behoort.
     * @param parentSymbol            Symbol van het inverse attribuut waartoe het symbool behoort.
     * @param objectTypeNaam          Naam van het objecttype waartoe het attribuut behoort.
     * @param verantwoordingExpressie De verantwoordingexpressie (alleen voor verantwoording/acties).
     * @return De vereenvoudigde attribuutnaam.
     */
    public String vereenvoudigSyntax(final String attribuutnaam, final String groepnaam, final Symbol parentSymbol,
                                     final String objectTypeNaam, final String verantwoordingExpressie)
    {
        String result = attribuutnaam;
        if (attribuutnaam != null && groepnaam != null && groepnaam.length() > 0) {
            if (parentSymbol != null) {
                /*
                 * Attributen die tot een ander attribuut behoren (zoals huisnummer bij adres), delen in hun naam zowel
                 * de groep als het overkoepelende attribuut. Die verdubbeling is wel nodig om onderscheid te kunnen
                 * maken in attributen (bijvoorbeeld omdat 'code' als attribuut meer dan één keer kan voorkomen).
                 * In de syntax is die verdubbeling echter niet nodig, omdat de naam van het overkoepelende attribuut
                 * verplicht voorafgaand is opgenomen. Daarom kan 'adressen[1].adres.huisnummer' worden afgekort tot
                 * 'adressen[1].huisnummer'.
                 * De hier toegepaste vereenvoudigingen hebben dus uitsluitend betrekking op de syntax van het
                 * attribuut, niet op de naamgeving in de enumeratie.
                 */
                if (attribuutnaam.startsWith(groepnaam + ".")) {
                    result = attribuutnaam.substring(groepnaam.length() + 1);
                }
                if (verantwoordingExpressie != null) {
                    result = verantwoordingExpressie + "." + result;
                }
            } else {
                /*
                 * Attributen die niet tot een ander attribuut behoren, maar waarvan de syntax begint met "persoon.",
                 * kunnen vereenvoudigd worden. De parser gaat namelijk uit van "persoon" als 'standaard object'
                 * waarop de expressies betrekking hebben.
                 */
                if (attribuutnaam.toLowerCase().startsWith(objectTypeNaam.toLowerCase() + ".")) {
                    result = attribuutnaam.substring(objectTypeNaam.length() + 1);
                }
            }
        }

        return result;
    }

    /**
     * Bepaalt de enumwaarde die behoort bij het attribuut.
     *
     * @param attribuut Attribuut waarvoor de enumwaarde bepaald moet worden.
     * @return Enumwaarde van het attribuut.
     */
    public String getEnumWaarde(final String attribuut) {
        String result = attribuut.replace('.', ' ');
        result = result.replace(' ', '_').toUpperCase();
        return result;
    }
}
