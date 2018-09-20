/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * 
 * Bepaalt de verschillen in verwijsgegevens tussen twee berichten. Bij de 'verwijsgegevens' gaat het om groep 02 Naam
 * en groep 03 Geboorte
 * 
 */
public final class VerwijsGegevensVerschilAnalyse {

    private static final String PERSOONSGEGEVENS_A = " Persoonsgegevens verstuurd (Tb01): ";
    private static final String VERWIJSGEGEVENS_B = " Verwijsgegevens ontvangen (Tv01): ";

    private VerwijsGegevensVerschilAnalyse() {
        // this helper class contains only static methods and should not be
        // instantiated
    }

    /**
     * Bepaalt de verschillen tussen de verwijsgegevens van bericht A en bericht B.
     * 
     * @param tb01Bericht
     *            Ontvangen Tb01 bericht.
     * @param tv01Bericht
     *            Verwijsgegevens ontvangen Tv01 bericht.
     * @return Verzameling met daarin de gevonden verschillen.
     */
    public static Set<String> bepaalVerschilVerwijsGegevens(
            final Tb01Bericht tb01Bericht,
            final Tv01Bericht tv01Bericht) {

        final Lo3Persoonslijst plTb01Bericht = tb01Bericht.getLo3Persoonslijst();
        Map<Lo3ElementEnum, String> cat21elementen = null;

        final List<Lo3CategorieWaarde> tv01Categorieen = tv01Bericht.getCategorieen();
        for (final Lo3CategorieWaarde lo3CategorieWaarde : tv01Categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_21.equals(lo3CategorieWaarde.getCategorie())) {
                cat21elementen = lo3CategorieWaarde.getElementen();
            }
        }

        final Set<String> verschillen = new HashSet<String>();

        if (plTb01Bericht == null && cat21elementen != null && !cat21elementen.isEmpty()) {
            verschillen.add("Persoonslijst van Tb01 bericht is leeg, "
                    + "terwijl de verwijsgegevens wel in het Tv01 bericht aanwezig zijn.");
        }

        if (plTb01Bericht != null && (cat21elementen == null || cat21elementen.isEmpty())) {
            verschillen.add("Verwijsgegevens van Tv01 bericht zijn leeg, "
                    + "terwijl er wel persoonsgegevens in het Tb01 bericht aanwezig waren.");
        }

        if (plTb01Bericht != null) {
            final Set<String> verschillenNaam =
                    bepaalVerschillenGroepNaam(plTb01Bericht.getPersoonStapel().getMeestRecenteElement().getInhoud(),
                            cat21elementen);
            final Set<String> verschillenGeboorte =
                    bepaalVerschillenGroepGeboorte(plTb01Bericht.getPersoonStapel().getMeestRecenteElement()
                            .getInhoud(), cat21elementen);
            verschillen.addAll(verschillenNaam);
            verschillen.addAll(verschillenGeboorte);
        }

        return verschillen;
    }

    /**
     * Bepaalt de verschillen tussen de verwijsgegevens (naam) van bericht A en bericht B.
     * 
     * @param persoonInhoudTb01Bericht
     * @param e
     *            De map elementen uit categorie 21
     * @return Verzameling met daarin de gevonden verschillen.
     */
    private static Set<String> bepaalVerschillenGroepNaam(
            final Lo3PersoonInhoud persoonInhoudTb01Bericht,
            final Map<Lo3ElementEnum, String> e) {

        final Set<String> verschillenNaam = new HashSet<String>();

        if (!isGelijkeWaarden(persoonInhoudTb01Bericht.getVoornamen(), e.get(Lo3ElementEnum.ELEMENT_0210))) {
            verschillenNaam.add("Verschil 02.10 voornamen -" + PERSOONSGEGEVENS_A
                    + persoonInhoudTb01Bericht.getVoornamen() + VERWIJSGEGEVENS_B
                    + e.get(Lo3ElementEnum.ELEMENT_0210));
        }

        if (!isGelijkeWaarden(
                persoonInhoudTb01Bericht.getAdellijkeTitelPredikaatCode(),
                e.get(Lo3ElementEnum.ELEMENT_0220) != null ? new Lo3AdellijkeTitelPredikaatCode(e
                        .get(Lo3ElementEnum.ELEMENT_0220)) : null)) {
            verschillenNaam.add("Verschil 02.20 adellijke titel predikaat code -"
                    + PERSOONSGEGEVENS_A
                    + alsString(persoonInhoudTb01Bericht.getAdellijkeTitelPredikaatCode())
                    + VERWIJSGEGEVENS_B
                    + alsString(e.get(Lo3ElementEnum.ELEMENT_0220) != null ? new Lo3AdellijkeTitelPredikaatCode(e
                            .get(Lo3ElementEnum.ELEMENT_0220)) : null));
        }

        if (!isGelijkeWaarden(persoonInhoudTb01Bericht.getVoorvoegselGeslachtsnaam(),
                e.get(Lo3ElementEnum.ELEMENT_0230))) {
            verschillenNaam.add("Verschil 02.30 voorvoegsel geslachtsnaam -" + PERSOONSGEGEVENS_A
                    + persoonInhoudTb01Bericht.getVoorvoegselGeslachtsnaam() + VERWIJSGEGEVENS_B
                    + e.get(Lo3ElementEnum.ELEMENT_0230));
        }

        if (!isGelijkeWaarden(persoonInhoudTb01Bericht.getGeslachtsnaam(), e.get(Lo3ElementEnum.ELEMENT_0240))) {
            verschillenNaam.add("Verschil 02.40 geslachtsnaam -" + PERSOONSGEGEVENS_A
                    + persoonInhoudTb01Bericht.getGeslachtsnaam() + VERWIJSGEGEVENS_B
                    + e.get(Lo3ElementEnum.ELEMENT_0240));
        }

        return verschillenNaam;
    }

    /**
     * Bepaalt de verschillen tussen de verwijsgegevens (adres) van bericht A en bericht B.
     * 
     * @param kindInhoudTb01Bericht
     *            Kindinhoud verzonden Tb01 bericht.
     * @param verwijsGegevensOntvangenTv01Bericht
     *            Verwijsgegevens ontvangen Tv01 bericht.
     * @return Verzameling met daarin de gevonden verschillen.
     */
    private static Set<String> bepaalVerschillenGroepGeboorte(
            final Lo3PersoonInhoud persoonInhoudTb01Bericht,
            final Map<Lo3ElementEnum, String> cat21elementen) {

        final Set<String> verschillenGeboorte = new HashSet<String>();

        if (!isGelijkeWaarden(
                persoonInhoudTb01Bericht.getGeboortedatum(),
                cat21elementen.get(Lo3ElementEnum.ELEMENT_0310) != null ? new Lo3Datum(Integer
                        .parseInt(cat21elementen.get(Lo3ElementEnum.ELEMENT_0310))) : null)) {
            verschillenGeboorte.add("Verschil 03.10 Geboortedatum -"
                    + PERSOONSGEGEVENS_A
                    + alsString(persoonInhoudTb01Bericht.getGeboortedatum())
                    + VERWIJSGEGEVENS_B
                    + alsString(cat21elementen.get(Lo3ElementEnum.ELEMENT_0310) != null ? new Lo3Datum(Integer
                            .parseInt(cat21elementen.get(Lo3ElementEnum.ELEMENT_0310))) : null));
        }

        if (!isGelijkeWaarden(
                persoonInhoudTb01Bericht.getGeboorteGemeenteCode(),
                cat21elementen.get(Lo3ElementEnum.ELEMENT_0320) != null ? new Lo3GemeenteCode(cat21elementen
                        .get(Lo3ElementEnum.ELEMENT_0320)) : null)) {
            verschillenGeboorte.add("Verschil 03.20 huisnummerletter -"
                    + PERSOONSGEGEVENS_A
                    + alsString(persoonInhoudTb01Bericht.getGeboorteGemeenteCode())
                    + VERWIJSGEGEVENS_B
                    + alsString(cat21elementen.get(Lo3ElementEnum.ELEMENT_0320) != null ? new Lo3GemeenteCode(
                            cat21elementen.get(Lo3ElementEnum.ELEMENT_0320)) : null));
        }

        if (!isGelijkeWaarden(
                persoonInhoudTb01Bericht.getGeboorteLandCode(),
                cat21elementen.get(Lo3ElementEnum.ELEMENT_0330) != null ? new Lo3LandCode(cat21elementen
                        .get(Lo3ElementEnum.ELEMENT_0330)) : null)) {
            verschillenGeboorte.add("Verschil 03.30 huisnummer -" + PERSOONSGEGEVENS_A
                    + alsString(persoonInhoudTb01Bericht.getGeboorteLandCode()) + VERWIJSGEGEVENS_B
                    + alsString(new Lo3LandCode(cat21elementen.get(Lo3ElementEnum.ELEMENT_0330))));
        }

        return verschillenGeboorte;
    }

    /**
     * Vergelijkt twee objecten met elkaar. Er wordt vanuit gegaan dat de equals-methode juist is geïmplementeerd voor
     * de gebruikte objecten.
     * 
     * @param waardeA
     *            Object A met een waarde.
     * @param waardeB
     *            Object B met een waarde.
     * @return True indien waarden A en B gelijk zijn aan elkaar, false in andere gevallen.
     */
    private static boolean isGelijkeWaarden(final Object waardeA, final Object waardeB) {

        boolean isGelijk = true;

        if (waardeA == null && waardeB != null) {
            isGelijk = false;
        } else if (waardeA != null) {
            isGelijk = waardeA.equals(waardeB);
        }

        return isGelijk;
    }

    /**
     * Geeft de string representatie van een waarde in een object.
     * 
     * @param waarde
     *            Het object dat de waarde bevat; dit object kan ook null zijn.
     * @return De string representatie van de waarde in het object, null indien het object null is.
     */
    private static String alsString(final Object waarde) {

        return waarde == null ? null : waarde.toString();

    }
}
