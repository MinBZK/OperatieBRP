/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    private static final Lo3Herkomst GEEN_HERKOMST = new Lo3Herkomst(null, -1, -1);

    private final Lo3ToevalligeGebeurtenisPersoonPrecondities persoonPrecondities;
    private final Lo3ToevalligeGebeurtenisOuderPrecondities ouderPrecondities;
    private final Lo3ToevalligeGebeurtenisVerbintenisPrecondities verbintenisPrecondities;
    private final Lo3ToevalligeGebeurtenisOverlijdenPrecondities overlijdenPrecondities;
    private final Lo3ToevalligeGebeurtenisAktesPrecondities aktesPrecondities;

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
        persoonPrecondities = new Lo3ToevalligeGebeurtenisPersoonPrecondities(conversieTabelFactory);
        ouderPrecondities = new Lo3ToevalligeGebeurtenisOuderPrecondities(conversieTabelFactory);
        verbintenisPrecondities = new Lo3ToevalligeGebeurtenisVerbintenisPrecondities(conversieTabelFactory);
        overlijdenPrecondities = new Lo3ToevalligeGebeurtenisOverlijdenPrecondities(conversieTabelFactory);
        aktesPrecondities = new Lo3ToevalligeGebeurtenisAktesPrecondities(conversieTabelFactory);
    }

    /**
     * Controleer de toevallige gebeurtenis.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     */
    public void controleerToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        if (toevalligeGebeurtenis == null) {
            return;
        }

        // Ontvangende gemeente
        controleerAanwezig(
                toevalligeGebeurtenis.getOntvangendeGemeente(),
                Foutmelding.maakMeldingFout(GEEN_HERKOMST, LogSeverity.ERROR, SoortMeldingCode.TG010, null));
        controleerNederlandseGemeente(
                Lo3LandCode.NEDERLAND,
                toevalligeGebeurtenis.getOntvangendeGemeente(),
                Foutmelding.maakMeldingFout(GEEN_HERKOMST, LogSeverity.ERROR, SoortMeldingCode.TG010, null));

        // Verzendende gemeente
        controleerAanwezig(
                toevalligeGebeurtenis.getVerzendendeGemeente(),
                Foutmelding.maakMeldingFout(GEEN_HERKOMST, LogSeverity.ERROR, SoortMeldingCode.TG011, null));
        controleerNederlandseGemeente(
                Lo3LandCode.NEDERLAND,
                toevalligeGebeurtenis.getVerzendendeGemeente(),
                Foutmelding.maakMeldingFout(GEEN_HERKOMST, LogSeverity.ERROR, SoortMeldingCode.TG011, null));

        // Aktenummer
        controleerAanwezig(
                toevalligeGebeurtenis.getNummerAkte(),
                Foutmelding.maakMeldingFout(GEEN_HERKOMST, LogSeverity.ERROR, SoortMeldingCode.TG012, null));

        persoonPrecondities.controleerStapel(toevalligeGebeurtenis.getPersoon());
        if (toevalligeGebeurtenis.getPersoon() == null || toevalligeGebeurtenis.getPersoon().isEmpty()) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG026, null);
        }

        ouderPrecondities.controleerStapel(toevalligeGebeurtenis.getOuder1());
        ouderPrecondities.controleerStapel(toevalligeGebeurtenis.getOuder2());
        verbintenisPrecondities.controleerStapel(toevalligeGebeurtenis.getVerbintenis());
        overlijdenPrecondities.controleerCategorie(toevalligeGebeurtenis.getOverlijden());

        aktesPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);

        controleerAktesGelijk(toevalligeGebeurtenis);
        controleerIngangsdatumGelijk(toevalligeGebeurtenis);

    }

    private void controleerIngangsdatumGelijk(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        Lo3Datum ingangsdatum = controleerIngangsdatumGelijk(null, toevalligeGebeurtenis.getPersoon());
        ingangsdatum = controleerIngangsdatumGelijk(ingangsdatum, toevalligeGebeurtenis.getOuder1());
        ingangsdatum = controleerIngangsdatumGelijk(ingangsdatum, toevalligeGebeurtenis.getOuder2());
        ingangsdatum = controleerIngangsdatumGelijk(ingangsdatum, toevalligeGebeurtenis.getVerbintenis());
        controleerIngangsdatumGelijk(ingangsdatum, toevalligeGebeurtenis.getOverlijden());
    }

    private Lo3Datum controleerIngangsdatumGelijk(final Lo3Datum ingangsdatum, final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        if (stapel != null) {
            return controleerIngangsdatumGelijk(ingangsdatum, stapel.get(0));
        } else {
            return ingangsdatum;
        }
    }

    private Lo3Datum controleerIngangsdatumGelijk(final Lo3Datum ingangsdatum, final Lo3Categorie<? extends Lo3CategorieInhoud> categorie) {
        if (categorie != null) {
            final Lo3Datum huidigeIngangsdatum = categorie.getHistorie().getIngangsdatumGeldigheid();
            if (huidigeIngangsdatum != null) {
                if (ingangsdatum == null) {
                    return huidigeIngangsdatum;
                }
                if (!huidigeIngangsdatum.equalsWaarde(ingangsdatum)) {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG025, null);
                }
            }
        }
        return ingangsdatum;

    }

    /**
     * Alle groepen 81 dienen overeen te komen de verzendende gemeente van het bericht en het aktenummer in de header.
     * @param toevalligeGebeurtenis toevalligeGebeurtenis
     */
    private void controleerAktesGelijk(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        final Lo3GemeenteCode verzendendeGemeente = toevalligeGebeurtenis.getVerzendendeGemeente();
        final Lo3String aktenummer = toevalligeGebeurtenis.getNummerAkte();

        controleerAkteGelijk(verzendendeGemeente, aktenummer, toevalligeGebeurtenis.getPersoon());
        controleerAkteGelijk(verzendendeGemeente, aktenummer, toevalligeGebeurtenis.getOuder1());
        controleerAkteGelijk(verzendendeGemeente, aktenummer, toevalligeGebeurtenis.getOuder2());
        controleerAkteGelijk(verzendendeGemeente, aktenummer, toevalligeGebeurtenis.getVerbintenis());
        controleerAkteGelijk(verzendendeGemeente, aktenummer, toevalligeGebeurtenis.getOverlijden());
    }

    private void controleerAkteGelijk(
            final Lo3GemeenteCode verzendendeGemeente,
            final Lo3String aktenummer,
            final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        if (stapel != null) {
            controleerAkteGelijk(verzendendeGemeente, aktenummer, stapel.get(0));
        }
    }

    private void controleerAkteGelijk(
            final Lo3GemeenteCode verzendendeGemeente,
            final Lo3String aktenummerHeader,
            final Lo3Categorie<? extends Lo3CategorieInhoud> categorie) {
        if (categorie != null) {
            final Lo3GemeenteCode registerGemeente = categorie.getDocumentatie().getGemeenteAkte();
            final Lo3String aktenummer = categorie.getDocumentatie().getNummerAkte();

            if (registerGemeente != null && !registerGemeente.equalsWaarde(verzendendeGemeente)) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG023, null);
            }
            if (aktenummer != null && !aktenummer.equalsWaarde(aktenummerHeader)) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG024, null);
            }
        }
    }

}
