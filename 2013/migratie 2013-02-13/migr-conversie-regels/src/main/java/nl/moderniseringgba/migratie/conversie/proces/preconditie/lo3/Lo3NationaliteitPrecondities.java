/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 04: Nationaliteit.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3NationaliteitPrecondities extends Lo3Precondities {

    /**
     * Controleer alle stapels.
     * 
     * @param stapels
     *            stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);

        controleerPreconditie051(stapel);
        controleerPreconditie052(stapel);

        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    void controleerCategorie(final Lo3Categorie<Lo3NationaliteitInhoud> categorie) {
        final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep05Aanwezig = isGroepAanwezig(inhoud.getNationaliteitCode());
        final boolean groep63Aanwezig = isGroepAanwezig(inhoud.getRedenVerkrijgingNederlandschapCode());
        final boolean groep64Aanwezig = isGroepAanwezig(inhoud.getRedenVerliesNederlandschapCode());

        if (isGroepAanwezig(inhoud.getAanduidingBijzonderNederlandschap())) {
            controleerGroep65BijzonderNederlanderschap(inhoud.getAanduidingBijzonderNederlandschap(), herkomst);

            if (groep05Aanwezig || groep63Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Als Groep 65: Bijzonder Nederlanderschap voorkomt, "
                                + "mogen de groep 05: Nationaliteit en "
                                + "groep 63: Verkrijging Nederlanderschap niet voorkomen in categorie "
                                + "04: Nationaliteit.");
            }
        }

        if (groep63Aanwezig) {
            controleerGroep63VerkrijgingNederlanderschap(inhoud.getRedenVerkrijgingNederlandschapCode(), herkomst);
        }
        if (groep64Aanwezig) {
            controleerGroep64VerliesNederlanderschap(inhoud.getRedenVerliesNederlandschapCode(), herkomst);

            if (groep05Aanwezig || groep63Aanwezig) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE023,
                        "Als groep 64: Verlies Nederlanderschap is gevuld mogen groep 05: "
                                + "Nationaliteit en groep 63: " + "Verkrijging Nederlanderschap niet voorkomen.");
            }

            if (groep63Aanwezig) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE023,
                        "Groep 63: Verkrijging Nederlanderschap en groep 64: Verlies " + "Nederlandschap mogen niet "
                                + "tegelijk voorkomen in categorie 04: Nationaliteit.");
            }
        }

        if (groep05Aanwezig) {
            controleerGroep05Nationaliteit(inhoud.getNationaliteitCode(), herkomst);

            controleerGroep05NationaliteitAfhankelijkheden(inhoud, groep63Aanwezig, groep64Aanwezig, herkomst);
        }

        controleerAkteEnDocumentatie(categorie, herkomst);
    }

    private void controleerGroep05NationaliteitAfhankelijkheden(
            final Lo3NationaliteitInhoud inhoud,
            final boolean groep63Aanwezig,
            final boolean groep64Aanwezig,
            final Lo3Herkomst herkomst) {
        final boolean isNederlandseNationaliteit =
                Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE.equals(inhoud.getNationaliteitCode().getCode());

        if (isNederlandseNationaliteit) {
            if (!groep63Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Als in groep 05: Nationaliteit de Nederlandse nationaliteit is gevuld moet" + " groep 63: "
                                + "Verkrijging Nederlanderschap verplicht voorkomen in categorie 04: "
                                + "Nationaliteit.");
            }
        } else {
            if (groep63Aanwezig || groep64Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Als in groep 05: Nationaliteit niet de Nederlandse nationaliteit is gevuld" + " mogen "
                                + "groep 63: Verkrijging Nederlanderschap en groep 64: Verlies Nederlanderschap "
                                + "niet voorkomen in categorie 04: Nationaliteit.");
            }
        }
    }

    private void controleerAkteEnDocumentatie(
            final Lo3Categorie<Lo3NationaliteitInhoud> categorie,
            final Lo3Herkomst herkomst) {
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final boolean akteAanwezig = isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte());
        final boolean documentAanwezig =
                isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                        documentatie.getBeschrijvingDocument());

        if (akteAanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 04: Nationaliteit.");

            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (documentAanwezig) {
            final String beschrijvingDocument = documentatie.getBeschrijvingDocument();
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    beschrijvingDocument, herkomst);

            if (beschrijvingDocument != null && beschrijvingDocument.toUpperCase().startsWith("PROBAS")) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB012);
            }
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerPreconditie051(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        // TODO logica omdraaien. Eerst beginnen met de actuele nationaliteit dan de rest.
        Lo3NationaliteitCode nationaliteit = null;
        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitCode categorieNationaliteit = categorie.getInhoud().getNationaliteitCode();
            if (categorieNationaliteit != null) {
                if (nationaliteit == null) {
                    nationaliteit = categorieNationaliteit;
                } else {
                    if (!categorieNationaliteit.equals(nationaliteit)) {
                        Foutmelding.logPreconditieFout(categorie.getLo3Herkomst(), LogSeverity.ERROR,
                                Precondities.PRE051,
                                "Binnen een categorie 04: Nationaliteit stapel mag slechts 1 nationaliteit "
                                        + "voorkomen.");
                    }
                }
            }
        }
    }

    private void controleerPreconditie052(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        boolean verliesAanwezig = false;
        boolean verkrijgingAanwezig = false;
        Lo3Categorie<Lo3NationaliteitInhoud> laatstGecontroleerdeCategorie = null;

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            laatstGecontroleerdeCategorie = categorie;
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            if (isGroepAanwezig(inhoud.getRedenVerkrijgingNederlandschapCode())) {
                verkrijgingAanwezig = true;
            }
            if (isGroepAanwezig(inhoud.getRedenVerliesNederlandschapCode())) {
                verliesAanwezig = true;
            }
        }

        if (verliesAanwezig && !verkrijgingAanwezig) {
            Foutmelding.logPreconditieFout(laatstGecontroleerdeCategorie.getLo3Herkomst(), LogSeverity.ERROR,
                    Precondities.PRE052,
                    "Binnen een categorie 04: Nationaliteit stapel mag niet alleen het verlies Nederlanderschap "
                            + "aanwezig zijn, maar moet ook de reden verkrijging Nederlanderschap voorkomen.")

            ;
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep05Nationaliteit(
            final Lo3NationaliteitCode nationaliteitCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(nationaliteitCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 05.10: Nationaliteit is verplicht in groep 05: Nationaliteit."));
        controleerCode(nationaliteitCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 05.10: Nationaliteit bevat geen geldige waarde."));
    }

    private void controleerGroep63VerkrijgingNederlanderschap(
            final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(redenVerkrijgingNederlandschapCode, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO,
                "Element 63.10: Reden verkrijging is verplicht in groep 63: Verkrijging Nederlanderschap."));
        controleerCode(redenVerkrijgingNederlandschapCode,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 63.10: Reden verkrijging bevat geen geldige waarde."), true);
    }

    private void controleerGroep64VerliesNederlanderschap(
            final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(redenVerliesNederlandschapCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 64.10: Reden verlies is verplicht in groep 64: Verlies Nederlanderschap."));
        controleerCode(redenVerliesNederlandschapCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 64.10: Reden verlies bevat geen geldige waarde."), false);
    }

    private void controleerGroep65BijzonderNederlanderschap(
            final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingBijzonderNederlandschap,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 65.10: Aanduiding bijzonder Nederlanderschap is verplicht in groep 65: Bijzonder "
                                + "Nederlanderschap."));
        Lo3PreconditieEnumCodeChecks.controleerCode(aanduidingBijzonderNederlandschap,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 65.10: Aanduiding bijzonder Nederlanderschap bevat geen geldige " + "waarde."));
    }

}
