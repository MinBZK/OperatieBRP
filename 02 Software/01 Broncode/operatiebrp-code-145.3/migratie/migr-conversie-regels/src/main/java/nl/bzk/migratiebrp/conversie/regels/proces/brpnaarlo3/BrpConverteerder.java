/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpIstGezagsverhoudingConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpIstHuwelijkOfGpConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpIstKindConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpIstOuderConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpKiesrechtConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpNationaliteitConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpOverlijdenConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpPersoonConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpReisdocumentConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpVerblijfplaatsConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpVerblijfstitelConverteerder;
import org.springframework.stereotype.Component;

/**
 * Converteer een brp persoonlijst naar een lo3 persoonslijst.
 */
@Component
public class BrpConverteerder {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpPersoonConverteerder persoonConverteerder;
    private final BrpNationaliteitConverteerder nationaliteitConverteerder;
    private final BrpOverlijdenConverteerder overlijdenConverteerder;
    private final BrpInschrijvingConverteerder inschrijvingConverteerder;
    private final BrpVerblijfplaatsConverteerder verblijfplaatsConverteerder;
    private final BrpVerblijfstitelConverteerder verblijfstitelConverteerder;
    private final BrpReisdocumentConverteerder reisdocumentConverteerder;

    private final BrpKiesrechtConverteerder kiesrechtConverteerder;
    // IST converteerders
    private final BrpIstOuderConverteerder istOuderConverteerder;
    private final BrpIstHuwelijkOfGpConverteerder istHuwelijkConverteerder;
    private final BrpIstKindConverteerder istKindConverteerder;

    private final BrpIstGezagsverhoudingConverteerder istGezagsverhoudingConverteerder;

    /**
     * Constructor.
     * @param attribuutConverteerder
     */
    @Inject
    public BrpConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.persoonConverteerder = new BrpPersoonConverteerder(attribuutConverteerder);
        this.nationaliteitConverteerder = new BrpNationaliteitConverteerder(attribuutConverteerder);
        this.overlijdenConverteerder = new BrpOverlijdenConverteerder(attribuutConverteerder);
        this.inschrijvingConverteerder = new BrpInschrijvingConverteerder(attribuutConverteerder);
        this.verblijfplaatsConverteerder = new BrpVerblijfplaatsConverteerder(attribuutConverteerder);
        this.verblijfstitelConverteerder = new BrpVerblijfstitelConverteerder(attribuutConverteerder);
        this.reisdocumentConverteerder = new BrpReisdocumentConverteerder(attribuutConverteerder);
        this.kiesrechtConverteerder = new BrpKiesrechtConverteerder(attribuutConverteerder);

        //IST
        this.istOuderConverteerder = new BrpIstOuderConverteerder(attribuutConverteerder);
        this.istHuwelijkConverteerder = new BrpIstHuwelijkOfGpConverteerder(attribuutConverteerder);
        this.istKindConverteerder = new BrpIstKindConverteerder(attribuutConverteerder);
        this.istGezagsverhoudingConverteerder = new BrpIstGezagsverhoudingConverteerder(attribuutConverteerder);

    }

    /**
     * Converteer een brp persoonslijst naar een lo3 persoonslijst.
     * @param brpPersoonslijst brp persoonslijst
     * @return lo3 persoonsljst
     */
    public final Lo3Persoonslijst converteer(final BrpPersoonslijst brpPersoonslijst) {
        /* Executable statement count - veel categorieen, wordt onduidelijk als we dit opsplitsen */
        LOG.debug("converteer(brpPersoonslijst.anummer={})", brpPersoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        LOG.debug("Converteer categorie 01: persoon");
        builder.persoonStapel(persoonConverteerder
                .converteer(brpPersoonslijst.getGeboorteStapel(), brpPersoonslijst.getNaamgebruikStapel(), brpPersoonslijst.getSamengesteldeNaamStapel(),
                        brpPersoonslijst.getIdentificatienummerStapel(), brpPersoonslijst.getGeslachtsaanduidingStapel(),
                        brpPersoonslijst.getNummerverwijzingStapel()));

        LOG.debug("Converteer categorie 02: ouder 1");
        builder.ouder1Stapel(istOuderConverteerder.converteerOuder(brpPersoonslijst.getIstOuder1Stapel()));

        LOG.debug("Converteer categorie 03: ouder 2");
        builder.ouder2Stapel(istOuderConverteerder.converteerOuder(brpPersoonslijst.getIstOuder2Stapel()));

        converteerNationatliteit(brpPersoonslijst, builder);

        LOG.debug("Converteer categorie 05: huwelijk en geregistreerd partnerschap");
        builder.huwelijkOfGpStapels(istHuwelijkConverteerder.converteerHuwelijkOfGpStapels(brpPersoonslijst.getIstHuwelijkOfGpStapels()));

        LOG.debug("Converteer categorie 06: overlijden");
        builder.overlijdenStapel(overlijdenConverteerder.converteer(brpPersoonslijst.getOverlijdenStapel()));

        LOG.debug("Converteer categorie 07: inschrijving");
        final BrpStapel<BrpVerificatieInhoud> verificatieStapel = inschrijvingConverteerder.bepaalVerificatieStapel(brpPersoonslijst.getVerificatieStapels());
        Lo3Stapel<Lo3InschrijvingInhoud>
                inschrijvingStapel =
                inschrijvingConverteerder.converteer(brpPersoonslijst.getInschrijvingStapel(), brpPersoonslijst.getPersoonskaartStapel(),
                        brpPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel(), brpPersoonslijst.getPersoonAfgeleidAdministratiefStapel(),
                        verificatieStapel, brpPersoonslijst.getBijhoudingStapel());
        inschrijvingStapel = inschrijvingConverteerder.nabewerking(inschrijvingStapel, brpPersoonslijst.getBijhoudingStapel());
        builder.inschrijvingStapel(inschrijvingStapel);

        LOG.debug("Converteer categorie 08: verblijfplaats");
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingGroepen = verblijfplaatsConverteerder.bepaalBijhoudingGroepen(brpPersoonslijst.getBijhoudingStapel());
        Lo3Stapel<Lo3VerblijfplaatsInhoud>
                verblijfplaatsStapel =
                verblijfplaatsConverteerder.converteer(brpPersoonslijst.getAdresStapel(), brpPersoonslijst.getMigratieStapel(),
                        bijhoudingGroepen, brpPersoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel());
        verblijfplaatsStapel =
                verblijfplaatsConverteerder.nabewerking(verblijfplaatsStapel, brpPersoonslijst.getMigratieStapel(), brpPersoonslijst.getBijhoudingStapel());
        builder.verblijfplaatsStapel(verblijfplaatsStapel);

        LOG.debug("Converteer categorie 09: kind");
        builder.kindStapels(istKindConverteerder.converteerKindStapels(brpPersoonslijst.getIstKindStapels()));

        LOG.debug("Converteer categorie 10: verblijfstitel");
        builder.verblijfstitelStapel(verblijfstitelConverteerder.converteer(brpPersoonslijst.getVerblijfsrechtStapel()));

        LOG.debug("Converteer categorie 11: gezagsverhouding");
        builder.gezagsverhoudingStapel(istGezagsverhoudingConverteerder.converteerGezagsverhouding(brpPersoonslijst.getIstGezagsverhoudingsStapel()));

        converteerReisdocument(brpPersoonslijst, builder);

        LOG.debug("Converteer categorie 13: kiesrecht");
        builder.kiesrechtStapel(
                kiesrechtConverteerder.converteer(brpPersoonslijst.getUitsluitingKiesrechtStapel(), brpPersoonslijst.getDeelnameEuVerkiezingenStapel()));

        LOG.debug("Build (lo3)persoonslijst");
        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bvpIndicatieStapel;
        bvpIndicatieStapel = brpPersoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel();
        if (bvpIndicatieStapel != null) {
            LOG.debug("Verwerk geprivilegieerde");
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels = lo3Persoonslijst.getNationaliteitStapels();
            if (!bestaatLo3NationaliteitStapelMetProbas(nationaliteitStapels)) {
                // pas de nationaliteit alleen aan als er nog geen probas vermelding is
                final Lo3Stapel<Lo3NationaliteitInhoud> eersteStapel = nationaliteitStapels.get(0);
                final Lo3Stapel<Lo3NationaliteitInhoud> aangepasteStapel =
                        nationaliteitConverteerder.converteerGeprivilegieerde(bvpIndicatieStapel, eersteStapel);
                nationaliteitStapels.remove(0);
                nationaliteitStapels.add(0, aangepasteStapel);
            }
        }

        return lo3Persoonslijst;
    }

    private void converteerReisdocument(final BrpPersoonslijst brpPersoonslijst, final Lo3PersoonslijstBuilder builder) {
        LOG.debug("Converteer categorie 12: reisdocument (inhoud)");
        for (final BrpStapel<BrpReisdocumentInhoud> stapel : brpPersoonslijst.getReisdocumentStapels()) {
            builder.reisdocumentStapel(reisdocumentConverteerder.converteer(stapel));
        }
        LOG.debug("Converteer categorie 12: reisdocument (signalering)");
        builder.reisdocumentStapel(reisdocumentConverteerder.converteer(brpPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel()));
    }

    private void converteerNationatliteit(final BrpPersoonslijst brpPersoonslijst, final Lo3PersoonslijstBuilder builder) {
        // Converteer eerst alle nationaliteiten die niet NL zijn.
        LOG.debug("Converteer categorie 04: nationaliteit (inhoud)");
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : brpPersoonslijst.getNationaliteitStapels()) {
            if (!isNederlandseNationaliteit(stapel)) {
                final List<BrpStapel<? extends BrpGroepInhoud>> teConverterenStapels = new ArrayList<>();
                teConverterenStapels.add(stapel);
                bepaalBijbehorendeBuitenlandsPersoonsnummerStapel(teConverterenStapels, brpPersoonslijst.getBuitenlandsPersoonsnummerStapels(), stapel);
                builder.nationaliteitStapel(nationaliteitConverteerder.converteer(teConverterenStapels.toArray(new BrpStapel[0])));
            }
        }

        // Converteer staatloos. Dit levert nieuwe stapels op, maar dat was wss ook zo.
        LOG.debug("Converteer categorie 04: nationaliteit (staatloos)");
        builder.nationaliteitStapel(nationaliteitConverteerder.converteer(brpPersoonslijst.getStaatloosIndicatieStapel()));

        // Converteer BZ NL-schap icm, indien aanwezig, een NL-nationaliteit stapel
        LOG.debug("Converteer categorie 04: nationaliteit (bijzonder nederlanderschap)");
        final BrpStapel<BrpNationaliteitInhoud> nlNationaliteitStapel = bepaalNlNationaliteitStapel(brpPersoonslijst.getNationaliteitStapels());
        if (nlNationaliteitStapel == null) {
            builder.nationaliteitStapel(nationaliteitConverteerder
                    .converteer(brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(), brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        } else {
            builder.nationaliteitStapel(nationaliteitConverteerder
                    .converteer(nlNationaliteitStapel, brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(),
                            brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        }
    }

    private void bepaalBijbehorendeBuitenlandsPersoonsnummerStapel(
            final List<BrpStapel<? extends BrpGroepInhoud>> teConverterenStapels,
            final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerStapels,
            final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel) {
        for (final BrpGroep<BrpNationaliteitInhoud> nationaliteitGroep : nationaliteitStapel) {
            final Long id = nationaliteitGroep.getActieInhoud().getId();
            for (final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> buitenlandsPersoonsnummerStapel : buitenlandsPersoonsnummerStapels) {
                zoekBuitenlandsPersoonsnummerStapel(teConverterenStapels, id, buitenlandsPersoonsnummerStapel);
            }
        }
    }

    private void zoekBuitenlandsPersoonsnummerStapel(final List<BrpStapel<? extends BrpGroepInhoud>> teConverterenStapels, final Long id,
                                                     final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> buitenlandsPersoonsnummerStapel) {
        for (final BrpGroep<BrpBuitenlandsPersoonsnummerInhoud> buitenlandsPersoonsnummerGroep : buitenlandsPersoonsnummerStapel) {
            if (buitenlandsPersoonsnummerGroep.getActieInhoud().getId().equals(id)) {
                teConverterenStapels.add(buitenlandsPersoonsnummerStapel);
                break;
            }
        }
    }

    private BrpStapel<BrpNationaliteitInhoud> bepaalNlNationaliteitStapel(final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (nationaliteitStapels == null || nationaliteitStapels.isEmpty()) {
            return null;
        }

        BrpStapel<BrpNationaliteitInhoud> resultaat = null;

        for (final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
            if (isNederlandseNationaliteit(nationaliteitStapel)) {
                resultaat = nationaliteitStapel;
            }
        }

        return resultaat;
    }

    private boolean isNederlandseNationaliteit(final BrpStapel<BrpNationaliteitInhoud> stapel) {
        return stapel != null
                && !stapel.isEmpty()
                && AbstractBrpAttribuutMetOnderzoek
                .equalsWaarde(BrpNationaliteitCode.NEDERLANDS, stapel.getLaatsteElement().getInhoud().getNationaliteitCode());
    }

    private boolean bestaatLo3NationaliteitStapelMetProbas(final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels) {
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : nationaliteitStapels) {
            final String beschrijvingDocument = Lo3String.unwrap(stapel.getLaatsteElement().getDocumentatie().getBeschrijvingDocument());
            if (beschrijvingDocument != null && beschrijvingDocument.toUpperCase().startsWith(BrpNationaliteitConverteerder.PROBAS)) {
                return true;
            }
        }
        return false;
    }
}
