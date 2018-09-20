/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 13: Kiesrecht.
 * 
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3KiesrechtPrecondities extends AbstractLo3Precondities {

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
                Foutmelding.logMeldingFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.STRUC_CATEGORIE_13, null);
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

        final boolean groep31Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP31, inhoud);
        final boolean groep38Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP38, inhoud);
        final boolean documentAanwezig = isDocumentAanwezig(document);

        // Groep 31: Europees kiesrecht
        if (groep31Aanwezig) {
            controleerGroep31EuropeesKiesrecht(
                inhoud.getAanduidingEuropeesKiesrecht(),
                inhoud.getDatumEuropeesKiesrecht(),
                inhoud.getEinddatumUitsluitingEuropeesKiesrecht(),
                herkomst);
        }

        // Groep 38: Uitsluiting kiesrecht
        if (groep38Aanwezig) {
            controleerGroep38UitsluitingKiesrecht(inhoud.getAanduidingUitgeslotenKiesrecht(), inhoud.getEinddatumUitsluitingKiesrecht(), herkomst);
        }

        if (documentAanwezig) {
            controleerGroep82Document(document.getGemeenteDocument(), document.getDatumDocument(), document.getBeschrijvingDocument(), herkomst);
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
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(aanduidingEuropeesKiesrecht, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE090, null));
        Lo3PreconditieEnumCodeChecks.controleerCode(
            aanduidingEuropeesKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3110));

        controleerAanwezig(
            datumEuropeesKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_3120));
        controleerDatum(
            datumEuropeesKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3120));

        controleerDatum(
            einddatumUitsluitingEuropeesKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3130));

    }

    private void controleerGroep38UitsluitingKiesrecht(
        final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht,
        final Lo3Datum einddatumUitsluitingKiesrecht,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(aanduidingUitgeslotenKiesrecht, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE089, null));
        Lo3PreconditieEnumCodeChecks.controleerCode(
            aanduidingUitgeslotenKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3810));

        controleerDatum(
            einddatumUitsluitingKiesrecht,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3820));
    }

}
