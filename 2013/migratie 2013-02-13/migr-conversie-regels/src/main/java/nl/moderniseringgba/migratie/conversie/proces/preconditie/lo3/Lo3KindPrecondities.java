/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.List;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 09: Kind.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3KindPrecondities extends Lo3Precondities {

    /**
     * Controleer alle stapels.
     * 
     * @param stapels
     *            stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3KindInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3KindInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3KindInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3KindInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3KindInhoud> categorie) {
        final Lo3KindInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 02: Naam
        if (!isGroepAanwezig(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam())) {

            // Groep 01: Identificatienummers
            if (isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer())) {

                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE048,
                        "Groep 01: Identificatienummer mag niet voorkomen als groep 02: Naam " + "niet voorkomt in "
                                + "categorie 09: Kind.");
            }

            // Groep 03: Geboorte
            if (isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode())) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE048,
                        "Groep 03: Geboorte mag niet voorkomen als groep 02: Naam niet voorkomt "
                                + "in categorie 09: " + "Kind.");
            }

        } else {
            controleerGroep02Naam(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam(), herkomst, false);

            // Groep 01: Identificatienummers
            if (isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer())) {
                controleerGroep01Identificatienummers(inhoud.getaNummer(), inhoud.getBurgerservicenummer(),
                        historie.getIngangsdatumGeldigheid(), herkomst, false);
            }

            // Groep 03: Geboorte
            if (isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode())) {
                controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                        inhoud.getGeboorteLandCode(), herkomst);
            }

        }
    }

}
