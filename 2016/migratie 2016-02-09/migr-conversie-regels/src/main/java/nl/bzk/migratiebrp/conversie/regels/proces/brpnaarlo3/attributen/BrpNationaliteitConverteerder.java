/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import java.util.regex.Pattern;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Nationaliteit converteerder.
 */
@Component
@Requirement(Requirements.CCA04)
public final class BrpNationaliteitConverteerder extends AbstractBrpCategorieConverteerder<Lo3NationaliteitInhoud> {

    /**
     * De string die geprivilegieerde indicatie in LO3 aangeeft.
     */
    public static final String PROBAS = "PROBAS";

    /**
     * Geprivilegieerden worden aangeduid door rubriek 04.82.30 beschrjjving document met deze waarde te laten beginnen.
     * Deze tekst is case insensitive en wordt voorafgegaan door 0 of meerdere spaties.
     */
    private static final Pattern GEPRIVILEGIEERDE_PATROON = Pattern.compile("(?i)^[ ]*PROBAS.*$");

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private NationaliteitInhoudConverteerder nationaliteitInhoudConverteerder;
    @Inject
    private NationaliteitBehandeldConverteerder nationaliteitBehandeldConverteerder;
    @Inject
    private NationaliteitVastgesteldConverteerder nationaliteitVastgesteldConverteerder;
    @Inject
    private NationaliteitStaatloosConverteerder nationaliteitStaatloosConverteerder;

    private static boolean isNationaliteitInhoudLeeg(final Lo3NationaliteitInhoud nationaliteitInhoud) {
        return nationaliteitInhoud.isLeeg() && nationaliteitInhoud.getRedenVerliesNederlandschapCode() == null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpCategorieConverteerder#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3NationaliteitInhoud> bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3NationaliteitInhoud> result;

        if (inhoud instanceof BrpNationaliteitInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitInhoudConverteerder;
        } else if (inhoud instanceof BrpBehandeldAlsNederlanderIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitBehandeldConverteerder;
        } else if (inhoud instanceof BrpVastgesteldNietNederlanderIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitVastgesteldConverteerder;
        } else if (inhoud instanceof BrpStaatloosIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3NationaliteitInhoud>) nationaliteitStaatloosConverteerder;
        } else {

            throw new IllegalArgumentException("BrpGroepConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /**
     * Converteer de geprivilegieerde-indicatie en pas indien nodig de meegegeven lo3Nationaliteit aan.
     *
     * @param stapel
     *            De stapel die geconverteerd moet worden.
     * @param lo3Nationaliteit
     *            de aan te passen nationaliteit
     * @return de lo3Nationaliteit waaraan eventueel de vermelding PROBAS is toegevoegd
     */
    @Requirement(Requirements.CCA04_BL02)
    public Lo3Stapel<Lo3NationaliteitInhoud> converteerGeprivilegieerde(
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel,
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3Nationaliteit)
    {
        if (isGeprivilegieerde(stapel)) {
            // DEF061
            // De tekst ‘PROBAS’wordt opgenomen. Als dit element al aanwezig was, wordt de bestaande vulling
            // aangevuld, door de tekst ‘PROBAS’ voor de bestaande vulling te plaatsen.
            final Lo3Categorie<Lo3NationaliteitInhoud> actueleNationaliteit = lo3Nationaliteit.getLaatsteElement();

            final Lo3String beschrijvingDocument = actueleNationaliteit.getDocumentatie().getBeschrijvingDocument();
            final Lo3Onderzoek beschrijvingDocumentOnderzoek = beschrijvingDocument == null ? null : beschrijvingDocument.getOnderzoek();
            String beschrijvingDocumentAsString = Lo3String.unwrap(beschrijvingDocument);
            if (beschrijvingDocumentAsString == null) {
                beschrijvingDocumentAsString = PROBAS;
            } else {
                if (!GEPRIVILEGIEERDE_PATROON.matcher(beschrijvingDocumentAsString).matches()) {
                    beschrijvingDocumentAsString = PROBAS + " " + beschrijvingDocumentAsString;
                }
            }
            final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = lo3Nationaliteit.getCategorieen();
            // verwijder aan te passen nationaliteit
            final int index = categorieen.indexOf(actueleNationaliteit);
            categorieen.remove(actueleNationaliteit);
            // voeg nieuwe nationaliteit toe
            categorieen.add(index, pasDocumentatieAan(actueleNationaliteit, new Lo3String(beschrijvingDocumentAsString, beschrijvingDocumentOnderzoek)));

            return new Lo3Stapel<>(categorieen);

        } else {
            // DEF062
            // geen conversie nodig
            return lo3Nationaliteit;
        }
    }

    @Definitie({Definities.DEF061, Definities.DEF062 })
    private boolean isGeprivilegieerde(final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel) {
        boolean isGeprivilegieerde = false;
        if (stapel != null) {
            final BrpGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> actueel = stapel.getActueel();
            if (actueel != null) {
                // actuele BVP indicatie groep gevonden
                final BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud inhoud = actueel.getInhoud();
                isGeprivilegieerde = inhoud != null && inhoud.heeftIndicatie();
            }
        }
        return isGeprivilegieerde;
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> pasDocumentatieAan(final Lo3Categorie<Lo3NationaliteitInhoud> cat, final Lo3String beschrijvingDocument) {
        final Lo3Documentatie doc = cat.getDocumentatie();
        final Lo3Documentatie nieuweDocumentatie =
                new Lo3Documentatie(
                    doc.getId(),
                    doc.getGemeenteAkte(),
                    doc.getNummerAkte(),
                    doc.getGemeenteDocument(),
                    doc.getDatumDocument(),
                    beschrijvingDocument,
                    null,
                    null);
        return new Lo3Categorie<>(cat.getInhoud(), nieuweDocumentatie, cat.getHistorie(), cat.getLo3Herkomst());
    }

    /**
     * Converteerder die weet hoe Lo3NationaliteitInhoud rijen aangemaakt moeten worden.
     * 
     * @param <T>
     *            groep inhoud type
     * 
     */
    public abstract static class AbstractNationaliteitConverteerder<T extends BrpGroepInhoud> extends BrpGroepConverteerder<T, Lo3NationaliteitInhoud> {

        @Override
        public final Lo3NationaliteitInhoud maakNieuweInhoud() {
            return new Lo3NationaliteitInhoud(null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpNationaliteitInhoud omgezet moet worden naar Lo3NationaliteitInhoud.
     */
    @Component
    @Requirement(Requirements.CCA04_BL01)
    public static final class NationaliteitInhoudConverteerder extends AbstractNationaliteitConverteerder<BrpNationaliteitInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3NationaliteitInhoud vulInhoud(
            final Lo3NationaliteitInhoud lo3Inhoud,
            final BrpNationaliteitInhoud brpInhoud,
            final BrpNationaliteitInhoud brpVorigeInhoud)
        {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null || brpInhoud.isLeeg() && brpInhoud.getRedenVerliesNederlandschapCode() == null) {
                builder.resetElkaarUitsluitendeVelden();

                final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode;
                if (brpVorigeInhoud != null) {
                    if (brpVorigeInhoud.isEindeBijhouding()) {
                        redenVerliesNederlandschapCode =
                                new Lo3RedenNederlandschapCode(
                                    Lo3RedenNederlandschapCode.EINDE_BIJHOUDING_CODE,
                                    brpVorigeInhoud.getEindeBijhouding().getOnderzoek());
                    } else {
                        if (brpVorigeInhoud.getRedenVerliesNederlandschapCode() != null
                            && brpVorigeInhoud.getRedenVerliesNederlandschapCode().getWaarde() != null)
                        {
                            redenVerliesNederlandschapCode =
                                    converteerder.converteerRedenNederlanderschap(brpVorigeInhoud.getRedenVerliesNederlandschapCode());
                        } else {
                            // De reden verlies is niet ingevuld, mogelijk wel in de migratie velden
                            redenVerliesNederlandschapCode =
                                    converteerder.converteerRedenNederlanderschap(brpVorigeInhoud.getMigratieRedenBeeindigingNationaliteit());
                        }
                    }
                } else {
                    redenVerliesNederlandschapCode = null;
                }
                builder.redenVerliesNederlandschapCode(redenVerliesNederlandschapCode);
            } else {
                builder.resetElkaarUitsluitendeVelden();

                builder.nationaliteitCode(converteerder.converteerNationaliteit(brpInhoud.getNationaliteitCode()));
                final Lo3RedenNederlandschapCode redenOpnameNationaliteit;
                if (brpInhoud.getRedenVerkrijgingNederlandschapCode() != null) {
                    redenOpnameNationaliteit = converteerder.converteerRedenNederlanderschap(brpInhoud.getRedenVerkrijgingNederlandschapCode());
                } else {
                    redenOpnameNationaliteit = converteerder.converteerRedenNederlanderschap(brpInhoud.getMigratieRedenOpnameNationaliteit());
                }

                builder.redenVerkrijgingNederlandschapCode(redenOpnameNationaliteit);
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
    public static final class NationaliteitBehandeldConverteerder extends AbstractNationaliteitConverteerder<BrpBehandeldAlsNederlanderIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        @Inject
        private BrpAttribuutConverteerder converteerder;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({Definities.DEF011, Definities.DEF012 })
        public Lo3NationaliteitInhoud vulInhoud(
            final Lo3NationaliteitInhoud lo3Inhoud,
            final BrpBehandeldAlsNederlanderIndicatieInhoud brpInhoud,
            final BrpBehandeldAlsNederlanderIndicatieInhoud brpVorigeInhoud)
        {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                if (isNationaliteitInhoudLeeg(lo3Inhoud)
                    || Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.equalsElement(
                        lo3Inhoud.getAanduidingBijzonderNederlandschap()))
                {
                    builder.aanduidingBijzonderNederlandschap(null);
                    builder.redenVerkrijgingNederlandschapCode(null);
                    builder.redenVerliesNederlandschapCode(
                        converteerder.converteerRedenNederlanderschap(brpVorigeInhoud.getMigratieRedenBeeindigingNationaliteit()));
                }
            } else {
                // DEF011
                builder.resetElkaarUitsluitendeVelden();
                final Lo3Onderzoek lo3Onderzoek = brpInhoud.getIndicatie() == null ? null : brpInhoud.getIndicatie().getOnderzoek();
                final Lo3AanduidingBijzonderNederlandschap aandBijzNL;
                aandBijzNL =
                        new Lo3AanduidingBijzonderNederlandschap(
                            Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.getCode(),
                            lo3Onderzoek);
                builder.aanduidingBijzonderNederlandschap(aandBijzNL);
                builder.redenVerkrijgingNederlandschapCode(converteerder.converteerRedenNederlanderschap(brpInhoud.getMigratieRedenOpnameNationaliteit()));
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
    public static final class NationaliteitVastgesteldConverteerder
            extends AbstractNationaliteitConverteerder<BrpVastgesteldNietNederlanderIndicatieInhoud>
    {
        private static final Logger LOG = LoggerFactory.getLogger();
        @Inject
        private BrpAttribuutConverteerder converteerder;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({Definities.DEF010, Definities.DEF012 })
        public Lo3NationaliteitInhoud vulInhoud(
            final Lo3NationaliteitInhoud lo3Inhoud,
            final BrpVastgesteldNietNederlanderIndicatieInhoud brpInhoud,
            final BrpVastgesteldNietNederlanderIndicatieInhoud brpVorigeInhoud)
        {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                if (isNationaliteitInhoudLeeg(lo3Inhoud)
                    || Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDER.equalsElement(
                        lo3Inhoud.getAanduidingBijzonderNederlandschap()))
                {
                    builder.aanduidingBijzonderNederlandschap(null);
                    builder.redenVerkrijgingNederlandschapCode(null);
                    builder.redenVerliesNederlandschapCode(
                        converteerder.converteerRedenNederlanderschap(brpVorigeInhoud.getMigratieRedenBeeindigingNationaliteit()));
                }
            } else {
                // DEF010
                builder.resetElkaarUitsluitendeVelden();
                final Lo3Onderzoek lo3Onderzoek = brpInhoud.getIndicatie() == null ? null : brpInhoud.getIndicatie().getOnderzoek();
                final Lo3AanduidingBijzonderNederlandschap aandBijzNL;
                aandBijzNL =
                        new Lo3AanduidingBijzonderNederlandschap(
                            Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDER.getCode(),
                            lo3Onderzoek);
                builder.aanduidingBijzonderNederlandschap(aandBijzNL);
                builder.redenVerkrijgingNederlandschapCode(converteerder.converteerRedenNederlanderschap(brpInhoud.getMigratieRedenOpnameNationaliteit()));
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
    public static final class NationaliteitStaatloosConverteerder extends AbstractNationaliteitConverteerder<BrpStaatloosIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({Definities.DEF056, Definities.DEF057 })
        public Lo3NationaliteitInhoud vulInhoud(
            final Lo3NationaliteitInhoud lo3Inhoud,
            final BrpStaatloosIndicatieInhoud brpInhoud,
            final BrpStaatloosIndicatieInhoud brpVorigeInhoud)
        {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                // DEF057
                builder.nationaliteitCode(null);
                builder.redenVerkrijgingNederlandschapCode(null);
                builder.redenVerliesNederlandschapCode(
                    converteerder.converteerRedenNederlanderschap(brpVorigeInhoud.getMigratieRedenBeeindigingNationaliteit()));
            } else {
                // DEF056
                final Lo3Onderzoek lo3Onderzoek = brpInhoud.getIndicatie() == null ? null : brpInhoud.getIndicatie().getOnderzoek();
                builder.nationaliteitCode(new Lo3NationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS, lo3Onderzoek));
                builder.redenVerkrijgingNederlandschapCode(converteerder.converteerRedenNederlanderschap(brpInhoud.getMigratieRedenOpnameNationaliteit()));
            }

            return builder.build();
        }
    }
}
