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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 10: Verblijfstitel.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3VerblijfstitelPrecondities extends Lo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3VerblijfstitelInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3VerblijfstitelInhoud> categorie) {
        final Lo3VerblijfstitelInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        if (isGroepAanwezig(inhoud.getAanduidingVerblijfstitelCode(), inhoud.getDatumEindeVerblijfstitel(),
                inhoud.getIngangsdatumVerblijfstitel())) {
            controleerGroep39Verblijfstitel(inhoud.getAanduidingVerblijfstitelCode(),
                    inhoud.getDatumEindeVerblijfstitel(), inhoud.getIngangsdatumVerblijfstitel(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        if (isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 10: Verblijfstitel.");
            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 82: Document mag niet voorkomen in categorie 10: Verblijfstitel.");
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep39Verblijfstitel(
            final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode,
            final Lo3Datum datumEindeVerblijfstitel,
            final Lo3Datum ingangsdatumVerblijfstitel,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingVerblijfstitelCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE012,
                "Element 39.10: Aanduiding verblijfstitel is verplicht in groep 39: Verblijfstitel."));
        controleerCode(aanduidingVerblijfstitelCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 39.10: Aanduiding verblijfstitel bevat een ongeldige code."));

        controleerDatum(datumEindeVerblijfstitel, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 39.20: Datum einde verblijfstitel bevat een ongeldige datum."));

        controleerAanwezig(ingangsdatumVerblijfstitel, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE012,
                "Element 39.30: Ingangsdatum verblijfstitel is verplicht in groep 39: Verblijfstitel."));
        controleerDatum(ingangsdatumVerblijfstitel, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 39.30: Ingangsdatum verblijfstitel bevat een ongeldige datum."));
    }

}
