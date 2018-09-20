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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 13: Kiesrecht.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3KiesrechtPrecondities extends Lo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3KiesrechtInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        if (stapel.size() >= 2) {
            for (int i = 1; i < stapel.size(); i++) {
                Foutmelding.logStructuurFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR,
                        "Categorie 13: Kiesrecht mag alleen actuele categorie voorkomens hebben.");
            }
        }

        for (final Lo3Categorie<Lo3KiesrechtInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3KiesrechtInhoud> categorie) {
        final Lo3KiesrechtInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie document = categorie.getDocumentatie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep31Aanwezig =
                isGroepAanwezig(inhoud.getAanduidingEuropeesKiesrecht(), inhoud.getDatumEuropeesKiesrecht(),
                        inhoud.getEinddatumUitsluitingEuropeesKiesrecht());
        final boolean groep38Aanwezig =
                isGroepAanwezig(inhoud.getAanduidingUitgeslotenKiesrecht(), inhoud.getEinddatumUitsluitingKiesrecht());
        final boolean documentAanwezig =
                isGroepAanwezig(document.getGemeenteDocument(), document.getDatumDocument(),
                        document.getBeschrijvingDocument());

        if (!groep31Aanwezig && !groep38Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Tenminste een van groep 31: Europees kiesrecht of groep 38: Uitsluiting " + "kiesrecht moet "
                            + "voorkomen in categorie 13: Kiesrecht.");
        }

        // Groep 31: Europees kiesrecht
        if (groep31Aanwezig) {
            controleerGroep31EuropeesKiesrecht(inhoud.getAanduidingEuropeesKiesrecht(),
                    inhoud.getDatumEuropeesKiesrecht(), inhoud.getEinddatumUitsluitingEuropeesKiesrecht(), herkomst);
        }

        // Groep 38: Uitsluiting kiesrecht
        if (groep38Aanwezig) {
            controleerGroep38UitsluitingKiesrecht(inhoud.getAanduidingUitgeslotenKiesrecht(),
                    inhoud.getEinddatumUitsluitingKiesrecht(), herkomst);
        }

        if (documentAanwezig) {
            controleerGroep82Document(document.getGemeenteDocument(), document.getDatumDocument(),
                    document.getBeschrijvingDocument(), herkomst);
        }

        if (groep38Aanwezig != documentAanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als groep 32: Uitsluiting kiesrecht of groep 82: Document voorkomt, dan moeten ze "
                            + "beide verplicht voorkomen.");
        }

        final Lo3Historie historie = categorie.getHistorie();
        if (!historie.isNullHistorie()) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 84: Onjuist en Groep 85: Geldigheid en Groep 86: Opneming mogen niet voorkomen "
                            + "in categorie 13: Kiesrecht.");
        }

    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep31EuropeesKiesrecht(
            final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht,
            final Lo3Datum datumEuropeesKiesrecht,
            final Lo3Datum einddatumUitsluitingEuropeesKiesrecht,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingEuropeesKiesrecht, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 31.10: Aanduiding europees kiesrecht moet verplicht voorkomen in groep 31: "
                        + "Europees kiesrecht."));
        Lo3PreconditieEnumCodeChecks.controleerCode(aanduidingEuropeesKiesrecht, Foutmelding.maakPreconditieFout(
                herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 31.10: Aanduiding europees kiesrecht bevat een ongeldige waarde."));

        controleerAanwezig(datumEuropeesKiesrecht, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 31.20: Datum mededeling " + "europees kiesrecht moet verplicht voorkomen in groep "
                        + "31: Europees kiesrecht."));
        controleerDatum(datumEuropeesKiesrecht, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 31.20: Datum mededeling europees kiesrecht bevat een ongeldige datum."));

        controleerDatum(einddatumUitsluitingEuropeesKiesrecht, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR,
                "Element 31.30: Einddatum uitsluiting europees kiesrecht bevat een ongeldige datum."));

    }

    private void controleerGroep38UitsluitingKiesrecht(
            final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht,
            final Lo3Datum einddatumUitsluitingKiesrecht,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingUitgeslotenKiesrecht, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 38.10: Aanduiding uitgesloten kiesrecht moet verplicht voorkomen in groep "

                + "38: Uitsluiting kiesrecht."));
        Lo3PreconditieEnumCodeChecks.controleerCode(aanduidingUitgeslotenKiesrecht,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 38.10: Aanduiding uitgesloten kiesrecht bevat een ongeldige " + "waarde."));

        controleerDatum(einddatumUitsluitingKiesrecht, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 38.20: Einddatum uitsluiting kiesrecht bevat een ongeldige datum."));
    }

}
