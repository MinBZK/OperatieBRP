/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 06: Overlijden.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3OverlijdenPrecondities extends Lo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3OverlijdenInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie055(stapel);
        controleerPreconditie056(stapel);
        controleerPreconditie068(stapel);

        for (final Lo3Categorie<Lo3OverlijdenInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        final Lo3OverlijdenInhoud inhoud = categorie.getInhoud();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 08: Overlijden
        if (isGroepAanwezig(inhoud.getDatum(), inhoud.getGemeenteCode(), inhoud.getLandCode())) {
            controleerGroep08Overlijden(inhoud.getDatum(), inhoud.getGemeenteCode(), inhoud.getLandCode(), herkomst);
        }
    }

    private void controleerGroep08Overlijden(
            final Lo3Datum datum,
            final Lo3GemeenteCode gemeenteCode,
            final Lo3LandCode landCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(datum, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE009,
                "Element 08.10: Datum overlijden moet verplicht voorkomen in groep 08: Overlijden."));
        controleerDatum(datum, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 08.10: Datum overlijden bevat geen geldige datum."));

        controleerAanwezig(gemeenteCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 08.20: Plaats overlijden moet verplicht voorkomen in groep 08: Overlijden."));

        controleerAanwezig(landCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE009,
                "Element 08.30: Land overlijden moet verplicht voorkomen in groep 08: Overlijden."));
        controleerCode(landCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE010,
                "Element 08.30: Land overlijden bevat geen geldige waarde."));

        if (landCode != null && landCode.isOnbekend()) {
            Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB002);
        }

        controleerNederlandseGemeente(landCode, gemeenteCode,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE026,
                        "Als element 08.30: Land overlijden Nederland bevat, moet element 08.20: Plaats overlijden een "
                                + "Nederlandse gemeente bevatten."));
    }
}
