/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Nationaliteit converteerder.
 */
@Component
@Requirement(Requirements.CCA04)
public final class BrpNationaliteitConverteerder extends BrpCategorieConverteerder<Lo3NationaliteitInhoud> {

    /**
     * De string die geprivilegieerde indicatie in LO3 aangeeft.
     */
    public static final String PROBAS = "PROBAS";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private NationaliteitInhoudConverteerder nationaliteitInhoudConverteerder;
    @Inject
    private NationaliteitBehandeldConverteerder nationaliteitBehandeldConverteerder;
    @Inject
    private NationaliteitVastgesteldConverteerder nationaliteitVastgesteldConverteerder;
    @Inject
    private NationaliteitStatenloosConverteerder nationaliteitStatenloosConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3NationaliteitInhoud> bepaalConverteerder(
            final T inhoud) {
        final BrpGroepConverteerder<T, Lo3NationaliteitInhoud> result;

        if (inhoud instanceof BrpNationaliteitInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitInhoudConverteerder;
        } else if (inhoud instanceof BrpBehandeldAlsNederlanderIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitBehandeldConverteerder;
        } else if (inhoud instanceof BrpVastgesteldNietNederlanderIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitVastgesteldConverteerder;
        } else if (inhoud instanceof BrpStatenloosIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitStatenloosConverteerder;
        } else {

            throw new IllegalArgumentException("BrpGroepConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /**
     * Converteer de geprivilegieerde-indicatie en pas indien nodig de meegegeven lo3Nationaliteit aan.
     * 
     * @param stapel
     *            de geprivilegieerde-indicatie
     * @param lo3Nationaliteit
     *            de aan te passen nationaliteit
     * @return de lo3Nationaliteit waaraan eventueel de vermelding PROBAS is toegevoegd
     */
    @Requirement(Requirements.CCA04_BL02)
    public Lo3Stapel<Lo3NationaliteitInhoud> converteerGeprivilegieerde(
            final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> stapel,
            final Lo3Stapel<Lo3NationaliteitInhoud> lo3Nationaliteit) {
        if (isGeprivilegieerde(stapel)) {
            // DEF061
            // De tekst ‘PROBAS’wordt opgenomen. Als dit element al aanwezig was, wordt de bestaande vulling
            // aangevuld, door de tekst ‘PROBAS’ voor de bestaande vulling te plaatsen.
            final Lo3Categorie<Lo3NationaliteitInhoud> actueleNationaliteit =
                    lo3Nationaliteit.getMeestRecenteElement();
            String beschrijvingDocument = actueleNationaliteit.getDocumentatie().getBeschrijvingDocument();
            if (beschrijvingDocument == null) {
                beschrijvingDocument = PROBAS;
            } else {
                if (!beschrijvingDocument.startsWith(PROBAS)) {
                    beschrijvingDocument = PROBAS + " " + beschrijvingDocument;
                }
            }
            final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = lo3Nationaliteit.getCategorieen();
            // verwijder aan te passen nationaliteit
            final int index = categorieen.indexOf(actueleNationaliteit);
            categorieen.remove(actueleNationaliteit);
            // voeg nieuwe nationaliteit toe
            categorieen.add(index, pasDocumentatieAan(actueleNationaliteit, beschrijvingDocument));

            return new Lo3Stapel<Lo3NationaliteitInhoud>(categorieen);

        } else {
            // DEF062
            // geen conversie nodig
            return lo3Nationaliteit;
        }
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> pasDocumentatieAan(
            final Lo3Categorie<Lo3NationaliteitInhoud> cat,
            final String beschrijvingDocument) {
        final Lo3Documentatie doc = cat.getDocumentatie();
        final Lo3Documentatie nieuweDocumentatie =
                new Lo3Documentatie(doc.getId(), doc.getGemeenteAkte(), doc.getNummerAkte(),
                        doc.getGemeenteDocument(), doc.getDatumDocument(), beschrijvingDocument, null, null);
        return new Lo3Categorie<Lo3NationaliteitInhoud>(cat.getInhoud(), nieuweDocumentatie, cat.getHistorie(),
                cat.getLo3Herkomst());
    }

    @Definitie({ Definities.DEF061, Definities.DEF062 })
    private boolean isGeprivilegieerde(final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> stapel) {
        boolean isGeprivilegieerde = false;
        if (stapel != null) {
            final BrpGroep<BrpGeprivilegieerdeIndicatieInhoud> recent = stapel.getMeestRecenteElement();
            if (recent != null && recent.getHistorie().getDatumEindeGeldigheid() == null
                    && recent.getHistorie().getDatumTijdVerval() == null) {
                // meest recente is ook nog actueel
                final BrpGeprivilegieerdeIndicatieInhoud inhoud = recent.getInhoud();
                isGeprivilegieerde = inhoud != null && inhoud.getHeeftIndicatie();
            }
        }
        return isGeprivilegieerde;
    }

    /**
     * Converteerder die weet hoe Lo3NationaliteitInhoud rijen aangemaakt moeten worden.
     */
    public abstract static class NationaliteitConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3NationaliteitInhoud> {

        @Override
        protected final Lo3NationaliteitInhoud maakNieuweInhoud() {
            return new Lo3NationaliteitInhoud(null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpNationaliteitInhoud omgezet moet worden naar Lo3NationaliteitInhoud.
     */
    @Component
    @Requirement(Requirements.CCA04_BL01)
    private static final class NationaliteitInhoudConverteerder extends
            NationaliteitConverteerder<BrpNationaliteitInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3NationaliteitInhoud vulInhoud(
                final Lo3NationaliteitInhoud lo3Inhoud,
                final BrpNationaliteitInhoud brpInhoud,
                final BrpNationaliteitInhoud brpVorigeInhoud) {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null || (brpInhoud.isLeeg() && brpInhoud.getRedenVerliesNederlandschapCode() == null)) {
                builder.setNationaliteitCode(null);
                builder.setRedenVerkrijgingNederlandschapCode(null);
                builder.setRedenVerliesNederlandschapCode(brpVorigeInhoud != null ? converteerder
                        .converteerRedenNederlanderschap(brpVorigeInhoud.getRedenVerliesNederlandschapCode()) : null);
            } else {
                builder.setNationaliteitCode(converteerder.converteerNationaliteit(brpInhoud.getNationaliteitCode()));
                builder.setRedenVerkrijgingNederlandschapCode(converteerder.converteerRedenNederlanderschap(brpInhoud
                        .getRedenVerkrijgingNederlandschapCode()));
                builder.setRedenVerliesNederlandschapCode(null);

            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpBehandeldAlsNederlanderIndicatieInhoud omgezet moet worden naar
     * Lo3NationaliteitInhoud.
     */
    @Component
    @Requirement(Requirements.CCA04_BL01)
    private static final class NationaliteitBehandeldConverteerder extends
            BrpNationaliteitConverteerder.NationaliteitConverteerder<BrpBehandeldAlsNederlanderIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({ Definities.DEF011, Definities.DEF012 })
        protected Lo3NationaliteitInhoud vulInhoud(
                final Lo3NationaliteitInhoud lo3Inhoud,
                final BrpBehandeldAlsNederlanderIndicatieInhoud brpInhoud,
                final BrpBehandeldAlsNederlanderIndicatieInhoud brpVorigeInhoud) {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                if (Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.equalsElement(lo3Inhoud
                        .getAanduidingBijzonderNederlandschap())) {
                    builder.setAanduidingBijzonderNederlandschap(null);
                }
            } else {
                // DEF011
                builder.setAanduidingBijzonderNederlandschap(
                        Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.asElement());
            }
            // Doe niets als DEF012: Geen bijzonder Nederlandershap

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpNationaliteitInhoud omgezet moet worden naar Lo3NationaliteitInhoud.
     */
    @Component
    @Requirement(Requirements.CCA04_BL01)
    private static final class NationaliteitVastgesteldConverteerder extends
            BrpNationaliteitConverteerder.NationaliteitConverteerder<BrpVastgesteldNietNederlanderIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({ Definities.DEF010, Definities.DEF012 })
        protected Lo3NationaliteitInhoud vulInhoud(
                final Lo3NationaliteitInhoud lo3Inhoud,
                final BrpVastgesteldNietNederlanderIndicatieInhoud brpInhoud,
                final BrpVastgesteldNietNederlanderIndicatieInhoud brpVorigeInhoud) {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                if (Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDERS.equalsElement(lo3Inhoud
                        .getAanduidingBijzonderNederlandschap())) {
                    builder.setAanduidingBijzonderNederlandschap(null);
                }
            } else {
                // DEF010
                builder.setAanduidingBijzonderNederlandschap(
                        Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDERS.asElement());
            }
            // Doe niets als DEF012: Geen bijzonder Nederlandershap

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpNationaliteitInhoud omgezet moet worden naar Lo3NationaliteitInhoud.
     */
    @Component
    @Requirement(Requirements.CCA04_BL03)
    private static final class NationaliteitStatenloosConverteerder extends
            BrpNationaliteitConverteerder.NationaliteitConverteerder<BrpStatenloosIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({ Definities.DEF056, Definities.DEF057 })
        protected Lo3NationaliteitInhoud vulInhoud(
                final Lo3NationaliteitInhoud lo3Inhoud,
                final BrpStatenloosIndicatieInhoud brpInhoud,
                final BrpStatenloosIndicatieInhoud brpVorigeInhoud) {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                // DEF057
                builder.setNationaliteitCode(null);
            } else {
                // DEF056
                builder.setNationaliteitCode(new Lo3NationaliteitCode(
                        Lo3NationaliteitCode.NATIONALITEIT_CODE_STATENLOOS));
            }

            return builder.build();
        }
    }

}
