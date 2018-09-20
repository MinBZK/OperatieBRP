/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 07: Inschrijving.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3InschrijvingPrecondities extends AbstractLo3Precondities {

    private static final int VERSIENUMMER_MIN = 0;
    private static final int VERSIENUMMER_MAX = 9999;

    /**
     * Controleer precondities op stapel niveau.
     *
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3InschrijvingInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        if (stapel.size() >= 2) {
            for (int i = 1; i < stapel.size(); i++) {
                Foutmelding.logMeldingFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.STRUC_CATEGORIE_7, null);
            }
        }

        for (final Lo3Categorie<Lo3InschrijvingInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     *
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3InschrijvingInhoud> categorie) {
        final Lo3InschrijvingInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 66: Blokkering
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP66, inhoud)) {
            controleerDatum(
                inhoud.getDatumIngangBlokkering(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_6620));
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB034, null);
        }

        // Groep 67: Opschorting
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP67, inhoud)) {
            controleerGroep67Opschorting(inhoud.getDatumOpschortingBijhouding(), inhoud.getRedenOpschortingBijhoudingCode(), herkomst);
        }

        // Groep 68: Opname
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP68, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE037, Lo3ElementEnum.ELEMENT_6810);
        } else {
            controleerGroep68Opname(inhoud.getDatumEersteInschrijving(), herkomst);
        }

        // Groep 69: Gemeente PK - geen controles
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP69, inhoud)) {
            controleer69GemeentePK(inhoud.getGemeentePKCode(), herkomst);
        }

        // Groep 70: Geheim - geen controles
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP70, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_7010);
        } else {
            controleerGroep70Geheim(inhoud.getIndicatieGeheimCode(), herkomst);
        }

        // Groep 71: Verificatie
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP71, inhoud)) {
            controleerGroep71Verificatie(inhoud.getDatumVerificatie(), inhoud.getOmschrijvingVerificatie(), herkomst);
        }

        // Groep 80: Synchroniciteit
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP80, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE037, Lo3ElementEnum.ELEMENT_8010);
        } else {
            controleerGroep80Synchroniciteit(inhoud.getVersienummer(), inhoud.getDatumtijdstempel(), herkomst);
        }

        // Groep 87: PK-conversie - geen controles
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP87, inhoud)) {
            controleerGroep87PKConversie(inhoud.getIndicatiePKVolledigGeconverteerdCode(), herkomst);
        }

        // Documentatie Groep 81, Groep 82 en Groep 88
        controleerDocumentatie(categorie.getDocumentatie(), herkomst);

        // Historie Groep 84, Groep 85 en Groep 86
        controleerHistorie(categorie.getHistorie(), herkomst);

        controleerBijzondereSituaties(categorie);
    }

    @BijzondereSituatie({SoortMeldingCode.BIJZ_CONV_LB030, SoortMeldingCode.BIJZ_CONV_LB031, SoortMeldingCode.BIJZ_CONV_LB032 })
    private void controleerBijzondereSituaties(final Lo3Categorie<Lo3InschrijvingInhoud> categorie) {
        final Lo3InschrijvingInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
        final boolean rniDeelnemerAanwezig = isRNIDeelnemerAanwezig(documentatie);
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP71, inhoud)) {
            if (!rniDeelnemerAanwezig || !Validatie.isElementGevuld(documentatie.getRniDeelnemerCode())) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB030, null);
            }
        } else {
            if (rniDeelnemerAanwezig && Validatie.isElementGevuld(documentatie.getRniDeelnemerCode())) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB031, null);
            }
        }

        if (rniDeelnemerAanwezig && Validatie.isElementGevuld(documentatie.getOmschrijvingVerdrag())) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB032, null);
        }
    }

    private void controleerDocumentatie(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        if (isAkteAanwezig(documentatie)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_CATEGORIE_7, null);
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (isDocumentAanwezig(documentatie)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_CATEGORIE_7, null);
            controleerGroep82Document(
                documentatie.getGemeenteDocument(),
                documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument(),
                herkomst);
        }
        if (isRNIDeelnemerAanwezig(documentatie)) {
            controleerGroep88RNIDeelnemer(documentatie.getRniDeelnemerCode(), herkomst);
        }
    }

    private void controleerHistorie(final Lo3Historie historie, final Lo3Herkomst herkomst) {
        if (!historie.isNullHistorie()) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_CATEGORIE_7, null);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep67Opschorting(
        final Lo3Datum datumOpschortingBijhouding,
        final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(datumOpschortingBijhouding, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE087, null));
        controleerDatum(
            datumOpschortingBijhouding,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_6710));

        controleerAanwezig(redenOpschortingBijhoudingCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE087, null));
        Lo3PreconditieEnumCodeChecks.controleerCode(
            redenOpschortingBijhoudingCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6720));
        controleerBijhoudingCode(herkomst, redenOpschortingBijhoudingCode);
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB011)
    private void controleerBijhoudingCode(final Lo3Herkomst herkomst, final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
        if (redenOpschortingBijhoudingCode != null) {
            if (redenOpschortingBijhoudingCode.isOnbekend()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE101, null);
            }

            if (redenOpschortingBijhoudingCode.isFout()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB011, Lo3ElementEnum.ELEMENT_6720);
            }
        }
    }

    private void controleerGroep68Opname(final Lo3Datum datumEersteInschrijving, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            datumEersteInschrijving,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE037, Lo3ElementEnum.ELEMENT_6810));
        controleerDatum(
            datumEersteInschrijving,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_6810));
    }

    private void controleer69GemeentePK(final Lo3GemeenteCode gemeentePKCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            gemeentePKCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6910));
        controleerCode(
            gemeentePKCode,
            false,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6910));
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB009)
    @Preconditie(SoortMeldingCode.PRE054)
    private void controleerGroep70Geheim(final Lo3IndicatieGeheimCode indicatieGeheimCode, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
            indicatieGeheimCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_7010));
        if (indicatieGeheimCode.getIntegerWaarde() >= 1 && indicatieGeheimCode.getIntegerWaarde() <= 6) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB009, Lo3ElementEnum.ELEMENT_7010);
        }
    }

    @Preconditie(SoortMeldingCode.PRE097)
    private void controleerGroep71Verificatie(final Lo3Datum datumVerificatie, final Lo3String omschrijvingVerificatie, final Lo3Herkomst herkomst) {
        if (!Validatie.isElementGevuld(datumVerificatie) || !Validatie.isElementGevuld(omschrijvingVerificatie)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE097, null);
        }
        controleerDatum(
            datumVerificatie,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_7110));
    }

    private void controleerGroep80Synchroniciteit(final Lo3Integer versienummer, final Lo3Datumtijdstempel datumtijdstempel, final Lo3Herkomst herkomst) {
        controleerAanwezig(versienummer, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE037, Lo3ElementEnum.ELEMENT_8010));
        final Integer unwrappedVersienummer = Lo3Integer.unwrap(versienummer);
        controleerVersienummer(unwrappedVersienummer, herkomst);
        controleerAanwezig(
            datumtijdstempel,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE038, Lo3ElementEnum.ELEMENT_8020));
        controleerDatumtijdstempel(
            datumtijdstempel,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE038, Lo3ElementEnum.ELEMENT_8020));
    }

    private void controleerGroep87PKConversie(
        final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(
            indicatiePKVolledigGeconverteerdCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_8710));
        Lo3PreconditieEnumCodeChecks.controleerCode(
            indicatiePKVolledigGeconverteerdCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE077, null));
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerVersienummer(final Integer versienummer, final Lo3Herkomst herkomst) {
        if (versienummer == null) {
            return;
        }

        if (versienummer < VERSIENUMMER_MIN || versienummer > VERSIENUMMER_MAX) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_VERSIENUMMER, null);
        }
    }

    private void controleerDatumtijdstempel(final Lo3Datumtijdstempel datumtijdstempel, final Foutmelding foutmelding) {
        if (datumtijdstempel == null) {
            return;
        }

        final String formatted = String.format("%017d", datumtijdstempel.getLongWaarde());

        /* Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen */
        if (formatted.length() != 17) {
            foutmelding.log();
        } else if ("00000000000000000".equals(formatted)) {
            foutmelding.log();
        } else {
            /* Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen */
            final int jaar = Integer.parseInt(formatted.substring(0, 4));
            final int maand = Integer.parseInt(formatted.substring(4, 6)) - 1;
            final int dag = Integer.parseInt(formatted.substring(6, 8));
            final int uur = Integer.parseInt(formatted.substring(8, 10));
            final int minuut = Integer.parseInt(formatted.substring(10, 12));
            final int seconde = Integer.parseInt(formatted.substring(12, 14));

            final Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            cal.set(Calendar.YEAR, jaar);
            cal.set(Calendar.MONTH, maand);
            cal.set(Calendar.DAY_OF_MONTH, dag);
            cal.set(Calendar.HOUR_OF_DAY, uur);
            cal.set(Calendar.MINUTE, minuut);
            cal.set(Calendar.SECOND, seconde);
            // milliseconden hoeven niet gecheckt te worden omdat alle 1000 mogelijke
            // waarden geldig zijn.

            /* Boolean Expression Complexity: Is niet complex, alleen lang */
            if (cal.get(Calendar.YEAR) != jaar
                || cal.get(Calendar.MONTH) != maand
                || cal.get(Calendar.DAY_OF_MONTH) != dag
                || cal.get(Calendar.HOUR_OF_DAY) != uur
                || cal.get(Calendar.MINUTE) != minuut
                || cal.get(Calendar.SECOND) != seconde)
            {
                foutmelding.log();
            }
        }
    }
}
