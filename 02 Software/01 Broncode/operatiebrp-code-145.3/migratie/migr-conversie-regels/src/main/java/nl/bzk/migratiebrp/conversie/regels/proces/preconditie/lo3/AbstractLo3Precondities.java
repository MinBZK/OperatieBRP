/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Lo3LoggingUtil;
import nl.bzk.migratiebrp.util.common.AnummerUtil;

/**
 * Basis controles.
 */
public abstract class AbstractLo3Precondities extends AbstractLo3PreconditieCodeChecks {

    /**
     * DATUM stuctuur code.
     */
    private static final Lo3Datum INGANG_BSN = new Lo3Datum(2007_11_26);
    private static final int MAXIMUM_MAAND_WAARDE = 12;
    private static final String DATUM_FORMAT = "%08d";
    private static final String ONBEKEND_JAAR = "0000";
    private static final String ONBEKENDE_MAAND_OF_DAG = "00";
    private static final int A_NUMMER_LENGTE = 10;
    private static final int ELF_PROEF_WAARDE = 11;
    private static final int LENGTE_DATUM = 8;

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public AbstractLo3Precondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer de documentatie.
     * @param <T> categorie type
     * @param stapel stapel
     */
    final <T extends Lo3CategorieInhoud> void controleerDocumentOfAkte(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            controleerDocumentOfAkte(categorie.getDocumentatie(), categorie.getLo3Herkomst());
        }
    }

    /**
     * Geeft aan of akte aanwezig is.
     * @param documentatie de documentatie die mogelijk een akte (groep81) bevat
     * @return true als er akte is opgenomen
     */
    final boolean isAkteAanwezig(final Lo3Documentatie documentatie) {
        return Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP81, documentatie);
    }

    /**
     * Geeft aan of document aanwezig is.
     * @param documentatie de documentatie die mogelijk een document (groep 82) bevat
     * @return true als er document is opgenomen
     */
    final boolean isDocumentAanwezig(final Lo3Documentatie documentatie) {
        return Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP82, documentatie);
    }

    /**
     * Geeft aan of rni-deelnemer aanwezig is.
     * @param documentatie de documentatie die mogelijk een rni-deelnemer (groep 88) bevat
     * @return true als er rni-deelnemer is opgenomen
     */
    final boolean isRNIDeelnemerAanwezig(final Lo3Documentatie documentatie) {
        return Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP88, documentatie);
    }

    private void controleerDocumentOfAkte(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        final boolean akteAanwezig = isAkteAanwezig(documentatie);
        final boolean documentAanwezig = isDocumentAanwezig(documentatie);

        if (akteAanwezig) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (documentAanwezig) {
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(), documentatie.getBeschrijvingDocument(), herkomst);
        }
        // Als beide aanwezig zijn, dan preconditie 020 loggen
        if (akteAanwezig && documentAanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE020, null);
        }
    }

    /**
     * Controleer Groep 83 Procedure.
     * @param <T> categorie type
     * @param stapel stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerOnderzoek(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek onderzoek = categorie.getOnderzoek();

            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP83, onderzoek)) {
                controleerGroep83Procedure(onderzoek, herkomst);
            }
        }
    }

    /**
     * Controleer dat groep 85: Geldigheid aanwezig is in alle categorieen van een stapel.
     * @param <T> categorie type
     * @param stapel stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerGeldigheidAanwezig(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
            final Lo3Historie historie = categorie.getHistorie();

            if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, historie)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE030, null);
            } else {
                final Lo3Datum ingangsDatumGeldigheid = historie.getIngangsdatumGeldigheid();
                controleerDatum(ingangsDatumGeldigheid,
                        Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_8510));
            }
        }
    }

    /**
     * Controleer dat groep 86: Opname aanwezig is in alle categorieen van een stapel.
     * @param <T> categorie type
     * @param stapel stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerOpnemingAanwezig(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
            final Lo3Historie historie = categorie.getHistorie();

            if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP86, historie)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE031, null);
            } else {
                final Lo3Datum datumVanOpneming = historie.getDatumVanOpneming();
                controleerDatum(datumVanOpneming,
                        Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_8610));
                controleerDatumNietOnbekend(datumVanOpneming, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE031, null));
            }
        }
    }

    /**
     * Controleer dat groep 84: Onjuist *niet* aanwezig is in de actuele categorieen van een stapel.
     * @param <T> categorie type
     * @param stapel stapel
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB022)
    @Preconditie(SoortMeldingCode.PRE076)
    protected final <T extends Lo3CategorieInhoud> void controleerOnjuist(final Lo3Stapel<T> stapel) {
        for (int i = 0; i < stapel.size(); i++) {
            final Lo3Categorie<T> categorie = stapel.get(i);

            final Lo3Historie historie = categorie.getHistorie();
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP84, historie)) {
                final Lo3IndicatieOnjuist onjuist = historie.getIndicatieOnjuist();
                final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

                Lo3PreconditieEnumCodeChecks.controleerCode(onjuist, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE076, null));
                if (onjuist != null && "S".equals(onjuist.getWaarde())) {
                    Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB022, null);
                }
            }
        }
    }

    /**
     * Controleer groep 01 identificatienummers.
     * @param aNummer Een A-Nummer.
     * @param burgerservicenummer Een BurgerServiceNummer.
     * @param ingangsDatumGeldigheid Een ingangsdatum geldigheid.
     * @param herkomst Een herkomst.
     * @param isCategorie01 Als Cat01 wordt gecontroleerd, dan moet er een preconditie gecontroleerd worden, anders een structuur melding
     */
    final void controleerGroep01Identificatienummers(
            final Lo3String aNummer,
            final Lo3String burgerservicenummer,
            final Lo3Datum ingangsDatumGeldigheid,
            final Lo3Herkomst herkomst,
            final boolean isCategorie01) {
        controleerAanwezig(aNummer,
                isCategorie01 ? Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE005, Lo3ElementEnum.ELEMENT_0110)
                        : Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_0110));
        controleerAnummer(aNummer,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_0110));

        if (Lo3Validatie.isElementGevuld(ingangsDatumGeldigheid) && INGANG_BSN.compareTo(ingangsDatumGeldigheid) <= 0) {
            controleerAanwezig(burgerservicenummer,
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_0120));
        }

        controleerBsn(burgerservicenummer,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_0120));
    }

    /**
     * Controleer groep 02 Naam.
     * @param voornamen voornamen
     * @param adellijkeTitelPredikaatCode adelijke titel predikaat code
     * @param voorvoegsel voorvoegsel
     * @param geslachtsnaam geslachtsnaam
     * @param herkomst herkomst
     * @param categorie01 Is dit voor categorie 01?
     */
    final void controleerGroep02Naam(
            final Lo3String voornamen,
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3String voorvoegsel,
            final Lo3String geslachtsnaam,
            final Lo3Herkomst herkomst,
            final boolean categorie01) {
        controleerMaximumLengte(voornamen, Lo3ElementEnum.ELEMENT_0210,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_0210));
        controleerCode(adellijkeTitelPredikaatCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0220));
        controleerMaximumLengte(voorvoegsel, Lo3ElementEnum.ELEMENT_0230,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_0230));
        controleerVoorvoegsel(voorvoegsel, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0230));

        controleerAanwezig(geslachtsnaam, Foutmelding
                .maakMeldingFout(herkomst, LogSeverity.ERROR, categorie01 ? SoortMeldingCode.PRE034 : SoortMeldingCode.PRE064, Lo3ElementEnum.ELEMENT_0240));
        controleerMaximumLengte(geslachtsnaam, Lo3ElementEnum.ELEMENT_0240,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_0240));
    }

    /**
     * Controleer groep 03 Geboorte.
     * @param geboortedatum geboortedatum
     * @param geboorteGemeenteCode geboortegemeente code
     * @param geboorteLandCode geboorteland code
     * @param herkomst herkomst
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB001)
    @Preconditie({SoortMeldingCode.PRE007, SoortMeldingCode.PRE008, SoortMeldingCode.PRE025})
    final void controleerGroep03Geboorte(final Lo3Datum geboortedatum, final Lo3GemeenteCode geboorteGemeenteCode, final Lo3LandCode geboorteLandCode,
                                         final Lo3Herkomst herkomst) {
        controleerAanwezig(geboortedatum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE007, Lo3ElementEnum.ELEMENT_0310));
        controleerDatum(geboortedatum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0310));

        controleerAanwezig(geboorteGemeenteCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0320));

        controleerAanwezig(geboorteLandCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE007, Lo3ElementEnum.ELEMENT_0330));
        controleerCode(geboorteLandCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE008, Lo3ElementEnum.ELEMENT_0330));

        if (Lo3Validatie.isElementGevuld(geboorteLandCode) && geboorteLandCode.isOnbekend()) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB001, null);
        }

        controleerNederlandseGemeente(geboorteLandCode, geboorteGemeenteCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE025, Lo3ElementEnum.ELEMENT_0330));
    }

    /**
     * Controleer groep 04 Geslachtsaanduiding.
     * @param geslachtsaanduiding geslachtsaanduiding
     * @param herkomst herkomst
     */
    final void controleerGroep04Geslachtsaanduiding(final Lo3Geslachtsaanduiding geslachtsaanduiding, final Lo3Herkomst herkomst) {
        controleerAanwezig(geslachtsaanduiding,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0410));
        Lo3PreconditieEnumCodeChecks.controleerCode(geslachtsaanduiding,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0410));
    }

    /**
     * Controleer groep 06 Sluiting.
     * @param datumSluitingHuwelijkOfAangaanGp datum
     * @param gemeenteCodeSluitingHuwelijkOfAangaanGp gemeente
     * @param landCodeSluitingHuwelijkOfAangaanGp land
     * @param herkomst herkomst
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB003)
    @Preconditie({SoortMeldingCode.PRE024, SoortMeldingCode.PRE027, SoortMeldingCode.PRE088})
    final void controleerGroep06Huwelijkssluiting(final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
                                                  final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp,
                                                  final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp, final Lo3Herkomst herkomst) {
        controleerAanwezig(datumSluitingHuwelijkOfAangaanGp, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE088, null));
        controleerDatum(datumSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0610));

        controleerAanwezig(gemeenteCodeSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0620));

        controleerAanwezig(landCodeSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE024, Lo3ElementEnum.ELEMENT_0630));
        controleerCode(landCodeSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE024, Lo3ElementEnum.ELEMENT_0630));

        if (landCodeSluitingHuwelijkOfAangaanGp != null && landCodeSluitingHuwelijkOfAangaanGp.isOnbekend()) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB003, null);
        }

        controleerNederlandseGemeente(landCodeSluitingHuwelijkOfAangaanGp, gemeenteCodeSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE027, Lo3ElementEnum.ELEMENT_0630));
    }

    /**
     * Controleer groep 07 Ontbinding.
     * @param datumOntbindingHuwelijkOfGp datum
     * @param gemeenteCodeOntbindingHuwelijkOfGp gemeente
     * @param landCodeOntbindingHuwelijkOfGp land
     * @param redenOntbindingHuwelijkOfGpCode reden
     * @param herkomst herkomst
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB004)
    @Preconditie({SoortMeldingCode.PRE028, SoortMeldingCode.PRE029, SoortMeldingCode.PRE054})
    final void controleerGroep07OnbindingHuwelijk(final Lo3Datum datumOntbindingHuwelijkOfGp, final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
                                                  final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
                                                  final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(datumOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0710));
        controleerDatum(datumOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0710));

        controleerAanwezig(gemeenteCodeOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0720));

        controleerAanwezig(landCodeOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE028, Lo3ElementEnum.ELEMENT_0730));
        controleerCode(landCodeOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE028, Lo3ElementEnum.ELEMENT_0730));

        if (landCodeOntbindingHuwelijkOfGp != null && landCodeOntbindingHuwelijkOfGp.isOnbekend()) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB004, null);
        }

        controleerNederlandseGemeente(landCodeOntbindingHuwelijkOfGp, gemeenteCodeOntbindingHuwelijkOfGp,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE029, Lo3ElementEnum.ELEMENT_0730));

        controleerCode(redenOntbindingHuwelijkOfGpCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0740));
    }

    /**
     * Controleer groep 08 Overlijden.
     * @param datum datum
     * @param gemeenteCode gemeente
     * @param landCode land
     * @param herkomst herkomst
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB002)
    @Preconditie({SoortMeldingCode.PRE009, SoortMeldingCode.PRE010, SoortMeldingCode.PRE026})
    final void controleerGroep08Overlijden(final Lo3Datum datum, final Lo3GemeenteCode gemeenteCode, final Lo3LandCode landCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(datum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE009, Lo3ElementEnum.ELEMENT_0810));
        controleerDatum(datum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0810));

        controleerAanwezig(gemeenteCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_8220));

        controleerAanwezig(landCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE009, Lo3ElementEnum.ELEMENT_0830));
        controleerCode(landCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE010, Lo3ElementEnum.ELEMENT_0830));

        if (landCode != null && landCode.isOnbekend()) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB002, null);
        }

        controleerNederlandseGemeente(landCode, gemeenteCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE026, Lo3ElementEnum.ELEMENT_0830));
    }

    /**
     * Controleer groep 15 Soort verbintenis.
     * @param soortVerbintenis soort verbintenis
     * @param herkomst herkomst
     */
    final void controleerGroep15SoortVerbintenis(final Lo3SoortVerbintenis soortVerbintenis, final Lo3Herkomst herkomst) {
        controleerAanwezig(soortVerbintenis,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1510));

        if (!Lo3SoortVerbintenisEnum.isPartnerschap(soortVerbintenis) && !Lo3SoortVerbintenisEnum.isHuwelijk(soortVerbintenis)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE042, null);
        }
    }

    /**
     * Controleer groep 62 Familierechtelijke betrekking.
     * @param familierechtelijkeBetrekking datum ingang familierechtelijke betrekking
     * @param herkomst herkomst
     */
    final void controleerGroep62FamilierechtelijkeBetrekking(final Lo3Datum familierechtelijkeBetrekking, final Lo3Herkomst herkomst) {
        controleerAanwezig(familierechtelijkeBetrekking,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6210));
        controleerDatum(familierechtelijkeBetrekking,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_6210));
    }

    /**
     * Controleer groep 81 Akte.
     * @param documentatie Bevat de te controleren akte-gegevens
     * @param herkomst herkomst
     */
    protected final void controleerGroep81Akte(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        final Lo3GemeenteCode gemeenteAkte = documentatie.getGemeenteAkte();
        final Lo3String nummerAkte = documentatie.getNummerAkte();

        if (!Lo3Validatie.isElementGevuld(gemeenteAkte) || !Lo3Validatie.isElementGevuld(nummerAkte)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE071, null);
        }

        if (Lo3Validatie.isElementGevuld(nummerAkte)) {
            controleerNummerAkte(nummerAkte, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_8120));
        }
        controleerCode(gemeenteAkte, false, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_8110));
    }

    /**
     * Controleert alle precondities voor Groep 82 Document.
     * @param gemeenteDocument de gemeentecode van/op het document
     * @param datumDocument datum van het document
     * @param beschrijvingDocument beschrijving van het document
     * @param herkomst herkomst
     */
    protected final void controleerGroep82Document(final Lo3GemeenteCode gemeenteDocument, final Lo3Datum datumDocument, final Lo3String beschrijvingDocument,
                                                   final Lo3Herkomst herkomst) {

        if (!Lo3Validatie.isElementGevuld(gemeenteDocument) || !Lo3Validatie.isElementGevuld(datumDocument) || !Lo3Validatie
                .isElementGevuld(beschrijvingDocument)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE070, null);
        }

        controleerCode(gemeenteDocument, false, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_8210));

        controleerDatum(datumDocument, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_8220));
        controleerDatumNietOnbekend(datumDocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE067, Lo3ElementEnum.ELEMENT_8220), true);

        controleerMaximumLengte(beschrijvingDocument, Lo3ElementEnum.ELEMENT_8230,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_8230));
    }

    /**
     * Controleert alle precondities voor Groep 83 Procedure.
     * @param lo3Onderzoek de onderzoek gegevens
     * @param herkomst herkomst
     */
    @Preconditie(SoortMeldingCode.PRE099)
    final void controleerGroep83Procedure(final Lo3Onderzoek lo3Onderzoek, final Lo3Herkomst herkomst) {
        final Lo3Integer aandGegevensInOnderzoek = lo3Onderzoek.getAanduidingGegevensInOnderzoek();
        final Lo3Datum datumIngangOnderzoek = lo3Onderzoek.getDatumIngangOnderzoek();

        if (!Lo3Validatie.isElementGevuld(aandGegevensInOnderzoek)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE099, null);
        }
        if (!Lo3Validatie.isElementGevuld(datumIngangOnderzoek)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE099, null);
        }
    }

    /**
     * Controleert alle precondities voor Groep 88 RNI-deelnemer.
     * @param rniDeelnemerCode de RNI deelnemer code
     * @param herkomst herkomst
     */
    @Preconditie({SoortMeldingCode.PRE054, SoortMeldingCode.PRE098})
    final void controleerGroep88RNIDeelnemer(final Lo3RNIDeelnemerCode rniDeelnemerCode, final Lo3Herkomst herkomst) {

        if (!Lo3Validatie.isElementGevuld(rniDeelnemerCode)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE098, null);
        } else {
            controleerCode(rniDeelnemerCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_8810));
        }
    }

    /**
     * Controleer preconditie 050.
     * @param <T> categorie type
     * @param stapel stapel
     */
    @Preconditie(SoortMeldingCode.PRE050)
    final <T extends Lo3CategorieInhoud> void controleerPreconditie050(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            if (categorie.getInhoud().isLeeg() && controleerDatums(categorie, stapel)) {
                // Controleer of PRE050 al een keer gelogd is voor deze herkomst. Severity maakt niet uit
                final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
                if (!Lo3LoggingUtil.bevatLogPreconditie(SoortMeldingCode.PRE050, herkomst)) {
                    Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE050, null);
                }
            }
        }
    }

    @Preconditie(SoortMeldingCode.PRE050)
    private <T extends Lo3CategorieInhoud> boolean controleerDatums(final Lo3Categorie<T> categorie, final Lo3Stapel<T> stapel) {
        boolean result = true;
        final Lo3Datum legeOpneming = categorie.getHistorie().getDatumVanOpneming();
        final Lo3Datum legeGeldigheid = categorie.getHistorie().getIngangsdatumGeldigheid();

        for (final Lo3Categorie<T> gevuldeCat : stapel) {
            if (!gevuldeCat.getInhoud().isLeeg()) {
                final Lo3Datum gevuldeOpneming = gevuldeCat.getHistorie().getDatumVanOpneming();
                final Lo3Datum gevuldeGeldigheid = gevuldeCat.getHistorie().getIngangsdatumGeldigheid();
                final boolean geldigheidNietAanwezig = !Lo3Validatie.isElementGevuld(legeGeldigheid) || !Lo3Validatie.isElementGevuld(gevuldeGeldigheid);

                if (legeOpneming != null && legeOpneming.compareTo(gevuldeOpneming) >= 0 && (geldigheidNietAanwezig
                        || legeGeldigheid.compareTo(gevuldeGeldigheid) >= 0)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Controleer preconditie 055.
     * @param <T> categorie type
     * @param stapel stapel
     */
    @Preconditie(SoortMeldingCode.PRE055)
    protected final <T extends Lo3CategorieInhoud> void controleerPreconditie055(final Lo3Stapel<T> stapel) {
        Lo3Categorie<T> actueleVoorkomen = null;
        for (final Lo3Categorie<T> voorkomen : stapel) {
            if (voorkomen.getLo3Herkomst() != null && voorkomen.getLo3Herkomst().getVoorkomen() == 0) {
                actueleVoorkomen = voorkomen;
                break;
            }
        }

        if (actueleVoorkomen != null && actueleVoorkomen.getHistorie().isOnjuist()) {
            Foutmelding.logMeldingFout(actueleVoorkomen.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE055, null);
        }
    }

    /**
     * Controleer preconditie 113.
     * @param stapel de stapel die gecontroleerd moet worden
     * @param persoonAnummer het A-nummer van de persoon (01.01.10)
     * @param <T> type van de categorie inhoud
     */
    @Preconditie(SoortMeldingCode.PRE113)
    final <T extends Lo3CategorieInhoud> void controleerPreconditie113(final Lo3Stapel<T> stapel, final Lo3String persoonAnummer) {
        for (final Lo3Categorie<T> voorkomen : stapel) {
            if (!voorkomen.getHistorie().isOnjuist()) {
                final Lo3Herkomst herkomst = voorkomen.getLo3Herkomst();

                final T inhoud = voorkomen.getInhoud();
                final Lo3String relatieAnummer;
                if (inhoud instanceof Lo3HuwelijkOfGpInhoud) {
                    relatieAnummer = ((Lo3HuwelijkOfGpInhoud) inhoud).getaNummer();
                } else if (inhoud instanceof Lo3KindInhoud) {
                    relatieAnummer = ((Lo3KindInhoud) inhoud).getaNummer();
                } else if (inhoud instanceof Lo3OuderInhoud) {
                    relatieAnummer = ((Lo3OuderInhoud) inhoud).getaNummer();
                } else {
                    relatieAnummer = null;
                }

                if (persoonAnummer != null && persoonAnummer.equalsWaarde(relatieAnummer)) {
                    Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE113, null);
                }
            }
        }
    }

    /**
     * Controleer preconditie 056.
     * @param <T> categorie type
     * @param stapel stapel
     */
    final <T extends Lo3CategorieInhoud> void controleerPreconditie056(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> legeRijen = verzamelLegeRijen(stapel);

        for (final Lo3Categorie<T> legeRij : legeRijen) {
            final Lo3Datum legeOpneming = legeRij.getHistorie().getDatumVanOpneming();
            final Lo3Datum legeGeldig = legeRij.getHistorie().getIngangsdatumGeldigheid();

            boolean ok = false;

            for (final Lo3Categorie<T> categorie : stapel) {
                final boolean gevuld = !categorie.getInhoud().isLeeg();
                final boolean onjuist = categorie.getHistorie().isOnjuist();
                final Lo3Datum geldig = categorie.getHistorie().getIngangsdatumGeldigheid();
                final Lo3Datum opneming = categorie.getHistorie().getDatumVanOpneming();

                final boolean isGevuldIsOnjuistEnHeeftDatumGeldigheid = gevuld && onjuist && geldig != null;
                if (isGevuldIsOnjuistEnHeeftDatumGeldigheid && AbstractLo3Element.equalsWaarde(geldig, legeGeldig) && opneming.compareTo(legeOpneming) <= 0) {
                    ok = true;
                    break;
                }
            }

            final Lo3Herkomst herkomst = legeRij.getLo3Herkomst();
            if (!ok && !Lo3LoggingUtil.bevatLogPreconditie(SoortMeldingCode.PRE056, herkomst)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE056, null);
            }
        }
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Categorie<T>> verzamelLegeRijen(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> legeRijen = new ArrayList<>();

        for (final Lo3Categorie<T> categorie : stapel) {
            if (categorie.getInhoud().isLeeg()) {
                legeRijen.add(categorie);
            }
        }
        return legeRijen;
    }

    /**
     * Controleer of het element voorkomt en log een fout indien niet.
     * @param elementWaarde de waarde van het element dat gecontroleerd wordt op aanwezigheid.
     * @param foutmelding de foutmelding om te loggen als het element niet aanwezig is.
     */
    protected final void controleerAanwezig(final Lo3Element elementWaarde, final Foutmelding foutmelding) {
        if (!Lo3Validatie.isElementGevuld(elementWaarde)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of het A-Nummer geldig is en log een fout indien niet. Een afwezig (null) A-Nummer wordt als geldig
     * beschouwd.
     * @param aNummer het A-Nummer om te controleren.
     * @param foutmelding de foutmelding om te loggen als het A-Nummer niet geldig is.
     */
    final void controleerAnummer(final Lo3String aNummer, final Foutmelding foutmelding) {
        if (!Lo3Validatie.isElementGevuld(aNummer)) {
            return;
        }

        if (!isGeldigAnummer(aNummer)) {
            foutmelding.log();
        }
    }

    private boolean isGeldigAnummer(final Lo3String aNummer) {
        final String unwrappedAnummer = Lo3String.unwrap(aNummer);

        boolean heeftInhoudEnJuisteLengte = true;
        if (unwrappedAnummer == null || unwrappedAnummer.length() != A_NUMMER_LENGTE) {
            heeftInhoudEnJuisteLengte = false;
        }

        return heeftInhoudEnJuisteLengte && AnummerUtil.isAnummerValide(unwrappedAnummer);
    }

    /**
     * Controleer of het BurgerServiceNummer geldig is en log een fout indien niet. Een afwezig (null)
     * BurgerServiceNummer wordt als geldig beschouwd.
     * @param burgerservicenummer het BurgerServiceNummer om te controleren.
     * @param foutmelding de foutmelding om te loggen als het BurgerServiceNummer niet geldig is.
     */
    final void controleerBsn(final Lo3String burgerservicenummer, final Foutmelding foutmelding) {
        if (!Lo3Validatie.isElementGevuld(burgerservicenummer)) {
            return;
        }
        if (!isGeldigBurgerservicenummer(burgerservicenummer)) {
            foutmelding.log();
        }

    }

    private boolean isGeldigBurgerservicenummer(final Lo3String burgerservicenummer) {
        final String unwrappedBsn = Lo3String.unwrap(burgerservicenummer);
        boolean heeftInhoudEnJuisteLengte = true;

        final int bsnLengte = Lo3ElementEnum.ELEMENT_0120.getMaximumLengte();
        if (unwrappedBsn == null || unwrappedBsn.length() != bsnLengte) {
            heeftInhoudEnJuisteLengte = false;
        }

        if (!heeftInhoudEnJuisteLengte) {
            return false;
        }

        final byte[] asBytes = new byte[bsnLengte];
        for (int i = 0; i < bsnLengte; i++) {
            asBytes[i] = Byte.valueOf(unwrappedBsn.substring(i, i + 1));
        }

        int som = 0;

        for (int i = 0; i < bsnLengte; i++) {
            // (9*s0)+(8*s1)+(7*s2)+...+(2*s7)-(1*s8) is deelbaar door 11.
            if (i != bsnLengte - 1) {
                som += (bsnLengte - i) * asBytes[i];
            } else {
                som -= (bsnLengte - i) * asBytes[i];
            }
        }

        /* Magic numbers: Vaste waarde in het algoritme */
        return som % ELF_PROEF_WAARDE == 0;
    }

    /**
     * Controleer of de element waarde de minimale lengte voor het element type heeft, en log een foutmelding indien
     * niet. Een afwezige (null) elementwaarde wordt als geldig beschouwd.
     * @param elementWaarde De element waarde om te controleren.
     * @param elementType Het type element.
     * @param foutmelding De foutmeling om te logging als het element te kort is.
     */
    final void controleerMinimumLengte(final Lo3String elementWaarde, final Lo3ElementEnum elementType, final Foutmelding foutmelding) {
        final String waarde = Lo3String.unwrap(elementWaarde);
        if (waarde == null || waarde.isEmpty()) {
            return;
        }

        if (waarde.length() < elementType.getMinimumLengte()) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de element waarde binnen de maximale lengte voor het element type valt, en log een foutmelding
     * indien niet. Een afwezige (null) elementwaarde wordt als geldig beschouwd.
     * @param elementWaarde De element waarde om te controleren.
     * @param elementType Het type element.
     * @param foutmelding De foutmeling om te logging als het element te lang is.
     */
    final void controleerMaximumLengte(final Lo3String elementWaarde, final Lo3ElementEnum elementType, final Foutmelding foutmelding) {
        final String waarde = Lo3String.unwrap(elementWaarde);
        if (waarde == null || waarde.isEmpty()) {
            return;
        }

        if (waarde.length() > elementType.getMaximumLengte()) {
            foutmelding.log();
        }
    }

    private void controleerGeldigheidDag(final Foutmelding foutmelding, final int jaarWaarde, final int maandWaarde, final int dagWaarde) {
        final Calendar cacMaand = new GregorianCalendar();
        cacMaand.setLenient(false);
        cacMaand.set(Calendar.YEAR, jaarWaarde);
        cacMaand.set(Calendar.MONTH, maandWaarde - 1);
        cacMaand.set(Calendar.DAY_OF_MONTH, 1);

        if (dagWaarde > cacMaand.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            foutmelding.log();
        }

    }

    /**
     * Controleer of de datum een geldige waarde heeft en log een fout indien niet. Een afwezige (null) datum wordt als
     * geldig beschouwd.
     * @param datum de datum om te controleren.
     * @param foutmelding de foutmelding om te loggen als de datum niet geldig is.
     */
    final void controleerDatum(final Lo3Datum datum, final Foutmelding foutmelding) {
        /* Cyclomatic Complexity: Code is duidelijker als alle checks in 1 methode staan */
        if (!Lo3Validatie.isElementGevuld(datum)) {
            return;
        }
        final String formatted = String.format(DATUM_FORMAT, datum.getIntegerWaarde());

        if (formatted.length() != LENGTE_DATUM) {
            foutmelding.log();
        } else {
            final String jaar = formatted.substring(0, 4);
            final String maand = formatted.substring(4, 6);
            final String dag = formatted.substring(6, LENGTE_DATUM);

            final boolean onbekendJaar = ONBEKEND_JAAR.equals(jaar);
            final boolean onbekendMaand = ONBEKENDE_MAAND_OF_DAG.equals(maand);
            final boolean onbekendDag = ONBEKENDE_MAAND_OF_DAG.equals(dag);

            if (onbekendJaar) {
                if (!onbekendMaand || !onbekendDag) {
                    foutmelding.log();
                }
            } else {
                controleerGeldigeWaardeMaandEnDag(foutmelding, jaar, maand, dag, onbekendMaand, onbekendDag);
            }
        }
    }

    private void controleerGeldigeWaardeMaandEnDag(Foutmelding foutmelding, String jaar, String maand, String dag, boolean onbekendMaand, boolean onbekendDag) {
        final int jaarWaarde = Integer.parseInt(jaar);
        final int maandWaarde = Integer.parseInt(maand);
        final int dagWaarde = Integer.parseInt(dag);

        if ((onbekendMaand && !onbekendDag) || (!onbekendMaand && maandWaarde > MAXIMUM_MAAND_WAARDE)) {
            foutmelding.log();
        } else {
            controleerGeldigheidDag(foutmelding, jaarWaarde, maandWaarde, dagWaarde);
        }
    }

    /**
     * Controleer of de datum een geldige en volledig bekende waarde heeft en log een fout indien niet. Een afwezige
     * (null) datum wordt als geldig beschouwd.
     * @param datum de datum om te controleren.
     * @param foutmelding de foutmelding om te loggen als de datum niet geldig is.
     */
    final void controleerDatumNietOnbekend(final Lo3Datum datum, final Foutmelding foutmelding) {
        controleerDatumNietOnbekend(datum, foutmelding, false);
    }

    /**
     * Controleer of de datum een geldige en bekende waarde heeft en log een fout indien niet. Een afwezige (null) datum
     * wordt als geldig beschouwd.
     * @param datum de datum om te controleren.
     * @param foutmelding de foutmelding om te loggen als de datum niet geldig is.
     * @param magVolledigOnbekendZijn indien true dan wordt een volledig onbekende datum als geldig beschouwd.
     */
    final void controleerDatumNietOnbekend(final Lo3Datum datum, final Foutmelding foutmelding, final boolean magVolledigOnbekendZijn) {
        if (Lo3Validatie.isElementGevuld(datum)) {
            final String formatted = String.format(DATUM_FORMAT, datum.getIntegerWaarde());

            if (formatted.length() == LENGTE_DATUM) {
                final String jaar = formatted.substring(0, 4);
                final String maand = formatted.substring(4, 6);
                final String dag = formatted.substring(6, LENGTE_DATUM);

                final boolean onbekendJaar = ONBEKEND_JAAR.equals(jaar);
                final boolean onbekendMaand = ONBEKENDE_MAAND_OF_DAG.equals(maand);
                final boolean onbekendDag = ONBEKENDE_MAAND_OF_DAG.equals(dag);

                final boolean isDatumDeelsOnbekend = onbekendJaar || onbekendMaand || onbekendDag;
                final boolean isDatumVolledigOnbekend = onbekendDag && onbekendMaand && onbekendJaar;
                if (isDatumDeelsOnbekend && !(isDatumVolledigOnbekend && magVolledigOnbekendZijn)) {
                    foutmelding.log();
                }
            }
        }
    }

    /**
     * Controleer bijzondere situatie LB024.
     * @param <T> categorie type
     * @param stapel stapel
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    protected final <T extends Lo3CategorieInhoud> void controleerGeldigheidDatumActueel(final Lo3Stapel<T> stapel) {
        Lo3Categorie<T> actueleVoorkomen = null;
        for (final Lo3Categorie<T> voorkomen : stapel) {
            if (actueleVoorkomen == null || voorkomen.getLo3Herkomst().getVoorkomen() < actueleVoorkomen.getLo3Herkomst().getVoorkomen()) {
                actueleVoorkomen = voorkomen;
            }
        }

        if (actueleVoorkomen != null) {
            final int actueleGeldigheid = maximaliseerOnbekendeDatum(actueleVoorkomen.getHistorie().getIngangsdatumGeldigheid());
            for (final Lo3Categorie<T> voorkomen : stapel) {
                if (!voorkomen.getHistorie().isOnjuist() && Lo3Validatie.isElementGevuld(voorkomen.getHistorie().getIngangsdatumGeldigheid())
                        && voorkomen.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde() > actueleGeldigheid) {
                    Foutmelding.logMeldingFoutInfo(voorkomen.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB024, null);
                }
            }
        }
    }

    private int maximaliseerOnbekendeDatum(final Lo3Datum ingangsdatumGeldigheid) {
        if (!Lo3Validatie.isElementGevuld(ingangsdatumGeldigheid)) {
            return 0;
        } else {
            return ingangsdatumGeldigheid.maximaliseerOnbekendeDatum();
        }
    }

    /**
     * Controleert de preconditie 112.
     * Als in een stapel alle gevulde voorkomens onjuist zijn, dan moet er minstens één niet-onjuist leeg voorkomen
     * zijn waarvan 85.10 Ingangsdatum geldigheid overeenkomt met 85.10 Ingangsdatum geldigheid van minstens één gevuld voorkomen.
     * @param stapel een LO3 stapel.
     * @param <T> de categorie inhoud type
     */
    @Preconditie(SoortMeldingCode.PRE112)
    final <T extends Lo3CategorieInhoud> void controleerPreconditie112(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> legeJuisteRijen = new ArrayList<>();
        final List<Lo3Categorie<T>> gevuldeRijen = new ArrayList<>();
        int aantalOnjuisteGevuldeRijen = 0;

        // Eerst bepalen of alle gevulde voorkomens onjuist zijn.
        for (final Lo3Categorie<T> categorie : stapel.getCategorieen()) {
            if (!categorie.getInhoud().isLeeg()) {
                gevuldeRijen.add(categorie);
                if (categorie.getHistorie().isOnjuist()) {
                    aantalOnjuisteGevuldeRijen++;
                }
            } else if (!categorie.getHistorie().isOnjuist()) {
                // Categorie is wel leeg.
                legeJuisteRijen.add(categorie);
            }
        }

        if (aantalOnjuisteGevuldeRijen > 0 && gevuldeRijen.size() == aantalOnjuisteGevuldeRijen) {
            boolean heeftMatchendeLegeRij = false;
            for (final Lo3Categorie<T> legeJuisteRij : legeJuisteRijen) {
                if (heeftLegeRijMatchMetGevuldeRij(gevuldeRijen, legeJuisteRij)) {
                    heeftMatchendeLegeRij = true;
                    break;
                }
            }
            if (!heeftMatchendeLegeRij) {
                Foutmelding.logMeldingFout(stapel.getLo3ActueelVoorkomen().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE112, null);
            }
        }
    }

    private <T extends Lo3CategorieInhoud> boolean heeftLegeRijMatchMetGevuldeRij(final List<Lo3Categorie<T>> lo3Datums, final Lo3Categorie<T> legeCategorie) {
        return lo3Datums.stream().anyMatch(categorie -> {
            final Lo3Historie historie = categorie.getHistorie();
            final Lo3Historie legeHistorie = legeCategorie.getHistorie();
            return historie.getIngangsdatumGeldigheid()
                    .equalsWaarde(legeHistorie.getIngangsdatumGeldigheid())
                    && legeHistorie.getDatumVanOpneming().compareTo(historie.getDatumVanOpneming()) >= 0;
        });
    }
}
