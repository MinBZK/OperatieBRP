/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor categorie 12: Reisdocument.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ReisdocumentPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ReisdocumentPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer alle stapels.
     * @param stapels stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3ReisdocumentInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        controleerPreconditie107(stapels);

        for (final Lo3Stapel<Lo3ReisdocumentInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    private void controleerPreconditie107(final List<Lo3Stapel<Lo3ReisdocumentInhoud>> stapels) {
        int aantalSignaleringen = 0;
        Lo3Herkomst herkomst = null;

        for (final Lo3Stapel<Lo3ReisdocumentInhoud> stapel : stapels) {
            for (final Lo3Categorie<Lo3ReisdocumentInhoud> categorie : stapel.getCategorieen()) {
                if (categorie.getInhoud().getSignalering() != null && categorie.getInhoud().getSignalering().isInhoudelijkGevuld()) {
                    herkomst = categorie.getLo3Herkomst();
                    aantalSignaleringen++;
                }
            }
        }

        if (aantalSignaleringen > 1) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE107, Lo3ElementEnum.ELEMENT_3610);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3ReisdocumentInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        final int minimum = 2;
        if (stapel.size() >= minimum) {
            for (int i = 1; i < stapel.size(); i++) {
                Foutmelding.logMeldingFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.STRUC_CATEGORIE_12, null);
            }
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie050(stapel);

        for (final Lo3Categorie<Lo3ReisdocumentInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3ReisdocumentInhoud> categorie) {
        final Lo3ReisdocumentInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep35aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP35, inhoud);
        final boolean groep36aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP36, inhoud);

        controleerAantalGroepen(groep35aanwezig, groep36aanwezig, herkomst);

        // Groep 35: Nederlands reisdocument
        if (groep35aanwezig) {
            controleerGroep35NederlandsReisdocument(
                    inhoud.getSoortNederlandsReisdocument(),
                    inhoud.getNummerNederlandsReisdocument(),
                    inhoud.getAutoriteitVanAfgifteNederlandsReisdocument(),
                    new ReisdocumentDatumsGeldigheid(inhoud.getDatumUitgifteNederlandsReisdocument(), inhoud.getDatumEindeGeldigheidNederlandsReisdocument()),
                    new InhoudingVermissingReisdocument(
                            inhoud.getDatumInhoudingVermissingNederlandsReisdocument(),
                            inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument()),
                    herkomst);
        }

        // Groep 36: Signalering
        if (groep36aanwezig) {
            controleerGroep36Signalering(inhoud.getSignalering(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        if (isAkteAanwezig(documentatie)) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (isDocumentAanwezig(documentatie)) {
            controleerGroep82Document(
                    documentatie.getGemeenteDocument(),
                    documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(),
                    herkomst);
        }
    }

    private void controleerAantalGroepen(final boolean groep35aanwezig, final boolean groep36aanwezig, final Lo3Herkomst herkomst) {
        final int aantalGevuldeGroepen = (groep35aanwezig ? 1 : 0) + (groep36aanwezig ? 1 : 0);
        if (aantalGevuldeGroepen != 1) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE035, null);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep35NederlandsReisdocument(
            final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument,
            final Lo3String nummerNederlandsReisdocument,
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument,
            final ReisdocumentDatumsGeldigheid reisdocumentDatumsGeldigheid,
            final InhoudingVermissingReisdocument inhoudingVermissingReisdocument,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(
                soortNederlandsReisdocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE011, Lo3ElementEnum.ELEMENT_3510));
        controleerCode(
                soortNederlandsReisdocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3510));

        controleerAanwezig(
                nummerNederlandsReisdocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE011, Lo3ElementEnum.ELEMENT_3520));

        controleerMinimumLengte(
                nummerNederlandsReisdocument,
                Lo3ElementEnum.ELEMENT_3520,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_3520));

        controleerMaximumLengte(
                nummerNederlandsReisdocument,
                Lo3ElementEnum.ELEMENT_3520,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_3520));

        controleerAanwezig(
                reisdocumentDatumsGeldigheid.getDatumUitgifte(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE011, Lo3ElementEnum.ELEMENT_3530));
        controleerDatum(
                reisdocumentDatumsGeldigheid.getDatumUitgifte(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3530));

        controleerAanwezig(
                autoriteitVanAfgifteNederlandsReisdocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE011, Lo3ElementEnum.ELEMENT_3540));
        controleerAanwezig(
                reisdocumentDatumsGeldigheid.getDatumEindeGeldigheid(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE011, Lo3ElementEnum.ELEMENT_3550));
        controleerDatum(
                reisdocumentDatumsGeldigheid.getDatumEindeGeldigheid(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3550));

        if (Lo3Validatie.isElementGevuld(inhoudingVermissingReisdocument.getDatum())) {
            controleerAanwezig(
                    inhoudingVermissingReisdocument.getAanduiding(),
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_3570));
        }
        controleerDatum(
                inhoudingVermissingReisdocument.getDatum(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3560));

        if (Lo3Validatie.isElementGevuld(inhoudingVermissingReisdocument.getAanduiding())) {
            controleerAanwezig(
                    inhoudingVermissingReisdocument.getDatum(),
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_3560));
        }
        Lo3PreconditieEnumCodeChecks.controleerCode(
                inhoudingVermissingReisdocument.getAanduiding(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3570));
    }

    private void controleerGroep36Signalering(final Lo3Signalering signalering, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
                signalering,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3610));
    }

    /**
     * Gegevens over inhouding of vermissing van het reisdocument.
     */
    private static final class InhoudingVermissingReisdocument {
        private final Lo3Datum datum;
        private final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduiding;

        private InhoudingVermissingReisdocument(final Lo3Datum datum, final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduiding) {
            this.datum = datum;
            this.aanduiding = aanduiding;
        }

        /**
         * Geef de waarde van datum.
         * @return datum
         */
        private Lo3Datum getDatum() {
            return datum;
        }

        /**
         * Geef de waarde van aanduiding.
         * @return aanduiding
         */
        private Lo3AanduidingInhoudingVermissingNederlandsReisdocument getAanduiding() {
            return aanduiding;
        }
    }

    /**
     * Geeft aan tussen welke datums het reisdocument geldig is.
     */
    private static final class ReisdocumentDatumsGeldigheid {
        private final Lo3Datum datumUitgifte;
        private final Lo3Datum datumEindeGeldigheid;

        private ReisdocumentDatumsGeldigheid(final Lo3Datum datumUitgifte, final Lo3Datum datumEindeGeldigheid) {
            this.datumUitgifte = datumUitgifte;
            this.datumEindeGeldigheid = datumEindeGeldigheid;
        }

        /**
         * Geef de waarde van datum uitgifte.
         * @return datum uitgifte
         */
        private Lo3Datum getDatumUitgifte() {
            return datumUitgifte;
        }

        /**
         * Geef de waarde van datum einde geldigheid.
         * @return datum einde geldigheid
         */
        private Lo3Datum getDatumEindeGeldigheid() {
            return datumEindeGeldigheid;
        }
    }
}
