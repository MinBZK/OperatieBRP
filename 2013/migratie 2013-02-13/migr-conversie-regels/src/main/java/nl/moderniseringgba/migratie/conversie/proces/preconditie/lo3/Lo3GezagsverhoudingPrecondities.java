/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 11: Gezagsverhouding.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3GezagsverhoudingPrecondities extends Lo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie) {
        final Lo3GezagsverhoudingInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep32Aanwezig = isGroepAanwezig(inhoud.getIndicatieGezagMinderjarige());
        final boolean groep33Aanwezig = isGroepAanwezig(inhoud.getIndicatieCurateleregister());

        if (groep32Aanwezig && groep33Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.WARNING,
                    "Groep 32: Gezag minderjarige en Groep 33: Curatele mogen niet tegelijk " + "voorkomen "
                            + "in categorie 11: Gezagsverhouding.");
        }

        if (groep32Aanwezig) {
            controleerGroep32GezagMinderjarige(inhoud.getIndicatieGezagMinderjarige(), herkomst);
        }
        if (groep33Aanwezig) {
            controleerGroep33Curatele(inhoud.getIndicatieCurateleregister(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final boolean akteAanwezig = isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte());
        final boolean documentAanwezig =
                isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                        documentatie.getBeschrijvingDocument());

        if (akteAanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 11: " + "Gezagsverhouding.");

            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (documentAanwezig) {
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }

    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep32GezagMinderjarige(
            final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige,
            final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(indicatieGezagMinderjarige, Foutmelding.maakPreconditieFout(
                herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 32.10 Indicatie gezag minderjarige bevat een ongeldige waarde."));
    }

    private void controleerGroep33Curatele(
            final Lo3IndicatieCurateleregister indicatieCurateleregister,
            final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(indicatieCurateleregister, Foutmelding.maakPreconditieFout(
                herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 33.10 Indicatie curateleregister bevat een ongeldige waarde."));
    }
}
