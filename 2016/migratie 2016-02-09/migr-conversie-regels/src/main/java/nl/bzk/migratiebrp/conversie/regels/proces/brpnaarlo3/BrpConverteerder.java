/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
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
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteer een brp persoonlijst naar een lo3 persoonslijst.
 */
@Component
public class BrpConverteerder {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpPersoonConverteerder persoonConverteerder;
    @Inject
    private BrpNationaliteitConverteerder nationaliteitConverteerder;
    @Inject
    private BrpOverlijdenConverteerder overlijdenConverteerder;
    @Inject
    private BrpInschrijvingConverteerder inschrijvingConverteerder;
    @Inject
    private BrpVerblijfplaatsConverteerder verblijfplaatsConverteerder;
    @Inject
    private BrpVerblijfstitelConverteerder verblijfstitelConverteerder;
    @Inject
    private BrpReisdocumentConverteerder reisdocumentConverteerder;
    @Inject
    private BrpKiesrechtConverteerder kiesrechtConverteerder;

    // IST converteerders
    @Inject
    private BrpIstOuderConverteerder istOuderConverteerder;
    @Inject
    private BrpIstHuwelijkOfGpConverteerder istHuwelijkConverteerder;
    @Inject
    private BrpIstKindConverteerder istKindConverteerder;
    @Inject
    private BrpIstGezagsverhoudingConverteerder istGezagsverhoudingConverteerder;

    /**
     * Converteer een brp persoonslijst naar een lo3 persoonslijst.
     *
     * @param brpPersoonslijst
     *            brp persoonslijst
     * @return lo3 persoonsljst
     */
    public final Lo3Persoonslijst converteer(final BrpPersoonslijst brpPersoonslijst) {
        /* Executable statement count - veel categorieen, wordt onduidelijk als we dit opsplitsen */
        LOG.debug("converteer(brpPersoonslijst.anummer={})", brpPersoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        LOG.debug("Converteer categorie 01: persoon");
        builder.persoonStapel(persoonConverteerder.converteer(
            brpPersoonslijst.getGeboorteStapel(),
            brpPersoonslijst.getNaamgebruikStapel(),
            brpPersoonslijst.getSamengesteldeNaamStapel(),
            brpPersoonslijst.getIdentificatienummerStapel(),
            brpPersoonslijst.getGeslachtsaanduidingStapel(),
            brpPersoonslijst.getNummerverwijzingStapel()));

        LOG.debug("Converteer categorie 02: ouder 1");
        builder.ouder1Stapel(istOuderConverteerder.converteer(brpPersoonslijst.getIstOuder1Stapel()));

        LOG.debug("Converteer categorie 03: ouder 2");
        builder.ouder2Stapel(istOuderConverteerder.converteer(brpPersoonslijst.getIstOuder2Stapel()));

        LOG.debug("Converteer categorie 04: nationaliteit (inhoud)");
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : brpPersoonslijst.getNationaliteitStapels()) {
            if (!isNederlandseNationaliteit(stapel)) {
                builder.nationaliteitStapel(nationaliteitConverteerder.converteer(stapel));
            }
        }
        LOG.debug("Converteer categorie 04: nationaliteit (staatloos)");
        builder.nationaliteitStapel(nationaliteitConverteerder.converteer(brpPersoonslijst.getStaatloosIndicatieStapel()));
        LOG.debug("Converteer categorie 04: nationaliteit (bijzonder nederlanderschap)");
        final BrpStapel<BrpNationaliteitInhoud> nlNationaliteitStapel = bepaalNlNationaliteitStapel(brpPersoonslijst.getNationaliteitStapels());
        if (nlNationaliteitStapel == null) {
            builder.nationaliteitStapel(nationaliteitConverteerder.converteer(
                brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(),
                brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        } else {
            builder.nationaliteitStapel(nationaliteitConverteerder.converteer(
                nlNationaliteitStapel,
                brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(),
                brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        }

        LOG.debug("Converteer categorie 05: huwelijk en geregistreerd partnerschap");
        builder.huwelijkOfGpStapels(istHuwelijkConverteerder.converteer(brpPersoonslijst.getIstHuwelijkOfGpStapels()));

        LOG.debug("Converteer categorie 06: overlijden");
        builder.overlijdenStapel(overlijdenConverteerder.converteer(brpPersoonslijst.getOverlijdenStapel()));

        LOG.debug("Converteer categorie 07: inschrijving");
        final BrpStapel<BrpVerificatieInhoud> verificatieStapel =
                inschrijvingConverteerder.bepaalVerificatieStapel(brpPersoonslijst.getVerificatieStapels());
        Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                inschrijvingConverteerder.converteer(
                    brpPersoonslijst.getInschrijvingStapel(),
                    brpPersoonslijst.getPersoonskaartStapel(),
                    brpPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel(),
                    brpPersoonslijst.getPersoonAfgeleidAdministratiefStapel(),
                    verificatieStapel,
                    brpPersoonslijst.getBijhoudingStapel());
        inschrijvingStapel = inschrijvingConverteerder.nabewerking(inschrijvingStapel, brpPersoonslijst.getBijhoudingStapel());
        builder.inschrijvingStapel(inschrijvingStapel);

        LOG.debug("Converteer categorie 08: verblijfplaats");
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingGroepen =
                verblijfplaatsConverteerder.bepaalBijhoudingGroepen(brpPersoonslijst.getBijhoudingStapel());
        Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                verblijfplaatsConverteerder.converteer(brpPersoonslijst.getAdresStapel(), brpPersoonslijst.getMigratieStapel(), bijhoudingGroepen);
        verblijfplaatsStapel =
                verblijfplaatsConverteerder.nabewerking(verblijfplaatsStapel, brpPersoonslijst.getMigratieStapel(), brpPersoonslijst.getBijhoudingStapel());
        builder.verblijfplaatsStapel(verblijfplaatsStapel);

        LOG.debug("Converteer categorie 09: kind");
        builder.kindStapels(istKindConverteerder.converteer(brpPersoonslijst.getIstKindStapels()));

        LOG.debug("Converteer categorie 10: verblijfstitel");
        builder.verblijfstitelStapel(verblijfstitelConverteerder.converteer(brpPersoonslijst.getVerblijfsrechtStapel()));

        LOG.debug("Converteer categorie 11: gezagsverhouding");
        builder.gezagsverhoudingStapel(istGezagsverhoudingConverteerder.converteer(brpPersoonslijst.getIstGezagsverhoudingsStapel()));

        LOG.debug("Converteer categorie 12: reisdocument (inhoud)");
        for (final BrpStapel<BrpReisdocumentInhoud> stapel : brpPersoonslijst.getReisdocumentStapels()) {
            builder.reisdocumentStapel(reisdocumentConverteerder.converteer(stapel));
        }
        LOG.debug("Converteer categorie 12: reisdocument (signalering)");
        builder.reisdocumentStapel(reisdocumentConverteerder.converteer(brpPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel()));

        LOG.debug("Converteer categorie 13: kiesrecht");
        builder.kiesrechtStapel(kiesrechtConverteerder.converteer(
            brpPersoonslijst.getUitsluitingKiesrechtStapel(),
            brpPersoonslijst.getDeelnameEuVerkiezingenStapel()));

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
               && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(BrpNationaliteitCode.NEDERLANDS, stapel.getLaatsteElement()
                                                                                                       .getInhoud()
                                                                                                       .getNationaliteitCode());
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
