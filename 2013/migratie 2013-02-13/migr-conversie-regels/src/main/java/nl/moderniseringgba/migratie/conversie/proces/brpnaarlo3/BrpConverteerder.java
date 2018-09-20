/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpGezagsverhoudingConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpKiesrechtConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpKindConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpNationaliteitConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpOuderConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpOverlijdenConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpPersoonConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpReisdocumentConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpVerblijfplaatsConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpVerblijfstitelConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder1GezagInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder2GezagInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpVerbintenisInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Converteer een brp persoonlijst naar een lo3 persoonslijst.
 */
// CHECKSTYLE:OFF - Fan-Out complexity - Groot aantal componenten (stappen), niet complex
@Component
public class BrpConverteerder {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpPersoonConverteerder persoonConverteerder;
    @Inject
    private BrpOudersRelateren brpOudersRelateren;
    @Inject
    private BrpOuderConverteerder ouderConverteerder;
    @Inject
    private BrpNationaliteitConverteerder nationaliteitConverteerder;
    @Inject
    private BrpVerbintenissenRelateren brpVerbintenissen;
    @Inject
    private BrpHuwelijkConverteerder huwelijkConverteerder;
    @Inject
    private BrpOverlijdenConverteerder overlijdenConverteerder;
    @Inject
    private BrpInschrijvingConverteerder inschrijvingConverteerder;
    @Inject
    private BrpVerblijfplaatsConverteerder verblijfplaatsConverteerder;
    @Inject
    private BrpKindConverteerder kindConverteerder;
    @Inject
    private BrpVerblijfstitelConverteerder verblijfstitelConverteerder;
    @Inject
    private BrpGezagsverhoudingConverteerder gezagsverhoudingConverteerder;
    @Inject
    private BrpReisdocumentConverteerder reisdocumentConverteerder;
    @Inject
    private BrpKiesrechtConverteerder kiesrechtConverteerder;

    /**
     * Converteer een brp persoonslijst naar een lo3 persoonslijst.
     * 
     * @param brpPersoonslijst
     *            brp persoonslijst
     * @return lo3 persoonsljst
     */
    // CHECKSTYLE:OFF - Executable statement count - veel categorieen, wordt onduidelijk als we dit opsplitsen
    @SuppressWarnings("unchecked")
    public final Lo3Persoonslijst converteer(final BrpPersoonslijst brpPersoonslijst) {
        // CHECKSTYLE:ON
        LOG.debug("converteer(brpPersoonslijst.anummer={})", brpPersoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        LOG.debug("Converteer categorie 01: persoon");
        builder.persoonStapel(persoonConverteerder.converteer(brpPersoonslijst.getGeboorteStapel(),
                brpPersoonslijst.getAanschrijvingStapel(), brpPersoonslijst.getSamengesteldeNaamStapel(),
                brpPersoonslijst.getIdentificatienummerStapel(), brpPersoonslijst.getGeslachtsaanduidingStapel(),
                brpPersoonslijst.getInschrijvingStapel()));

        final List<BrpBetrokkenheid> ouderBetrokkenheden = new ArrayList<BrpBetrokkenheid>();
        for (final BrpRelatie relatieStapel : brpPersoonslijst.getRelaties()) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatieStapel.getSoortRelatieCode())
                    && BrpSoortBetrokkenheidCode.KIND.equals(relatieStapel.getRolCode())) {
                ouderBetrokkenheden.addAll(relatieStapel.getBetrokkenheden());
            }
        }

        final List<List<BrpBetrokkenheid>> ouders = brpOudersRelateren.bepaalOuderEilanden(ouderBetrokkenheden);

        if (ouders.size() > 2) {
            throw new IllegalStateException("Deze BRP persoonslijst heeft meer dan twee (ongerelateerde) ouders."
                    + " Dat kan niet geconverteerd worden naar LO3.");
        }

        LOG.debug("Converteer categorie 02: ouder 1");
        final List<BrpBetrokkenheid> ouder1s;
        if (ouders.size() >= 1) {
            ouder1s = ouders.get(0);
            for (final BrpBetrokkenheid ouder1 : ouder1s) {
                builder.ouder1Stapel(ouderConverteerder.converteer(ouder1.getGeboorteStapel(),
                        ouder1.getGeslachtsaanduidingStapel(), ouder1.getIdentificatienummersStapel(),
                        ouder1.getSamengesteldeNaamStapel(), ouder1.getOuderStapel()));
            }
        } else {
            ouder1s = null;
        }

        LOG.debug("Converteer categorie 03: ouder 2");
        final List<BrpBetrokkenheid> ouder2s;
        if (ouders.size() >= 2) {
            ouder2s = ouders.get(1);
            for (final BrpBetrokkenheid ouder2 : ouder2s) {
                builder.ouder2Stapel(ouderConverteerder.converteer(ouder2.getGeboorteStapel(),
                        ouder2.getGeslachtsaanduidingStapel(), ouder2.getIdentificatienummersStapel(),
                        ouder2.getSamengesteldeNaamStapel(), ouder2.getOuderStapel()));
            }
        } else {
            ouder2s = null;
        }

        LOG.debug("Converteer categorie 04: nationaliteit (inhoud)");
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : brpPersoonslijst.getNationaliteitStapels()) {
            builder.nationaliteitStapel(nationaliteitConverteerder.converteer(stapel));
        }
        LOG.debug("Converteer categorie 04: nationaliteit (statenloos)");
        builder.nationaliteitStapel(nationaliteitConverteerder.converteer(brpPersoonslijst
                .getStatenloosIndicatieStapel()));
        LOG.debug("Converteer categorie 04: nationaliteit (bijzonder nederlanderschap)");
        builder.nationaliteitStapel(nationaliteitConverteerder.converteer(
                brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(),
                brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));

        LOG.debug("Converteer categorie 05: huwelijk en geregistreerd partnerschap");

        final List<List<BrpRelatie>> verbintenissen =
                brpVerbintenissen.bepaalSamenhangendeVerbintenissen(brpPersoonslijst.getRelaties());

        for (final List<BrpRelatie> samenhangendeVerbintenissen : verbintenissen) {
            final List<BrpGroep<BrpRelatieInhoud>> relatieGroepen = new ArrayList<BrpGroep<BrpRelatieInhoud>>();
            final List<BrpGroep<BrpGeboorteInhoud>> geboorteGroepen = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();
            final List<BrpGroep<BrpGeslachtsaanduidingInhoud>> geslachtsGroepen =
                    new ArrayList<BrpGroep<BrpGeslachtsaanduidingInhoud>>();
            final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatieGroepen =
                    new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
            final List<BrpGroep<BrpSamengesteldeNaamInhoud>> naamGroepen =
                    new ArrayList<BrpGroep<BrpSamengesteldeNaamInhoud>>();
            final List<BrpGroep<BrpVerbintenisInhoud>> verbintenisGroepen =
                    new ArrayList<BrpGroep<BrpVerbintenisInhoud>>();

            for (final BrpRelatie relatie : samenhangendeVerbintenissen) {
                for (final BrpGroep<BrpRelatieInhoud> relatieGroep : relatie.getRelatieStapel()) {
                    relatieGroepen.add(relatieGroep);
                    verbintenisGroepen.add(new BrpGroep<BrpVerbintenisInhoud>(new BrpVerbintenisInhoud(relatie
                            .getSoortRelatieCode()), relatieGroep.getHistorie(), relatieGroep.getActieInhoud(),
                            relatieGroep.getActieVerval(), relatieGroep.getActieGeldigheid()));
                }

                for (final BrpBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                    if (betrokkenheid.getGeboorteStapel() != null) {
                        geboorteGroepen.addAll(betrokkenheid.getGeboorteStapel().getGroepen());
                    }
                    if (betrokkenheid.getGeslachtsaanduidingStapel() != null) {
                        geslachtsGroepen.addAll(betrokkenheid.getGeslachtsaanduidingStapel().getGroepen());
                    }
                    if (betrokkenheid.getIdentificatienummersStapel() != null) {
                        identificatieGroepen.addAll(betrokkenheid.getIdentificatienummersStapel().getGroepen());
                    }
                    if (betrokkenheid.getSamengesteldeNaamStapel() != null) {
                        naamGroepen.addAll(betrokkenheid.getSamengesteldeNaamStapel().getGroepen());
                    }
                }
            }

            final BrpStapel<BrpRelatieInhoud> relatieStapel = new BrpStapel<BrpRelatieInhoud>(relatieGroepen);
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                    geboorteGroepen.isEmpty() ? null : new BrpStapel<BrpGeboorteInhoud>(geboorteGroepen);
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsStapel =
                    geslachtsGroepen.isEmpty() ? null : new BrpStapel<BrpGeslachtsaanduidingInhoud>(geslachtsGroepen);
            final BrpStapel<BrpIdentificatienummersInhoud> identificatieStapel =
                    identificatieGroepen.isEmpty() ? null : new BrpStapel<BrpIdentificatienummersInhoud>(
                            identificatieGroepen);
            final BrpStapel<BrpSamengesteldeNaamInhoud> naamStapel =
                    new BrpStapel<BrpSamengesteldeNaamInhoud>(naamGroepen);
            final BrpStapel<BrpVerbintenisInhoud> verbintenisStapel =
                    new BrpStapel<BrpVerbintenisInhoud>(verbintenisGroepen);
            builder.huwelijkOfGpStapel(huwelijkConverteerder.converteer(relatieStapel, geboorteStapel,
                    geslachtsStapel, identificatieStapel, naamStapel, verbintenisStapel));
        }

        LOG.debug("Converteer categorie 06: overlijden");
        builder.overlijdenStapel(overlijdenConverteerder.converteer(brpPersoonslijst.getOverlijdenStapel()));

        LOG.debug("Converteer categorie 07: inschrijving");
        builder.inschrijvingStapel(inschrijvingConverteerder.converteer(brpPersoonslijst.getOpschortingStapel(),
                brpPersoonslijst.getInschrijvingStapel(), brpPersoonslijst.getPersoonskaartStapel(),
                brpPersoonslijst.getVerstrekkingsbeperkingStapel(),
                brpPersoonslijst.getAfgeleidAdministratiefStapel()));

        LOG.debug("Converteer categorie 08: verblijfplaats");
        builder.verblijfplaatsStapel(verblijfplaatsConverteerder.converteer(brpPersoonslijst.getAdresStapel(),
                brpPersoonslijst.getImmigratieStapel(), brpPersoonslijst.getBijhoudingsgemeenteStapel()));

        LOG.debug("Converteer categorie 09: kind");
        for (final BrpRelatie relatie : brpPersoonslijst.getRelaties()) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatieCode())
                    && BrpSoortBetrokkenheidCode.OUDER.equals(relatie.getRolCode())) {
                for (final BrpBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                    builder.kindStapel(kindConverteerder.converteer(betrokkenheid.getGeboorteStapel(),
                            betrokkenheid.getIdentificatienummersStapel(),
                            betrokkenheid.getSamengesteldeNaamStapel(), betrokkenheid.getOuderStapel()));
                }
            }
        }

        LOG.debug("Converteer categorie 10: verblijfstitel");
        builder.verblijfstitelStapel(verblijfstitelConverteerder.converteer(brpPersoonslijst
                .getVerblijfsrechtStapel()));

        // Omdat we werken met de identificatie van data obv klassen moeten we dit omzetten naar eigen klassen.
        final BrpStapel<BrpOuder1GezagInhoud> ouder1Gezag = converteerOuder1GezagStapel(ouder1s);
        final BrpStapel<BrpOuder2GezagInhoud> ouder2Gezag = converteerOuder2GezagStapel(ouder2s);
        LOG.debug("Converteer categorie 11: gezagsverhouding");
        builder.gezagsverhoudingStapel(gezagsverhoudingConverteerder.converteer(
                brpPersoonslijst.getOnderCurateleIndicatieStapel(),
                brpPersoonslijst.getDerdeHeeftGezagIndicatieStapel(), ouder1Gezag, ouder2Gezag));

        LOG.debug("Converteer categorie 12: reisdocument (inhoud)");
        for (final BrpStapel<BrpReisdocumentInhoud> stapel : brpPersoonslijst.getReisdocumentStapels()) {
            builder.reisdocumentStapel(reisdocumentConverteerder.converteer(stapel));
        }
        LOG.debug("Converteer categorie 12: reisdocument (signalering)");
        builder.reisdocumentStapel(reisdocumentConverteerder.converteer(brpPersoonslijst
                .getBelemmeringVerstrekkingReisdocumentIndicatieStapel()));
        LOG.debug("Converteer categorie 12: reisdocument (aanduiding bezit buitenlands reisdocument)");
        builder.reisdocumentStapel(reisdocumentConverteerder.converteer(brpPersoonslijst
                .getBezitBuitenlandsReisdocumentIndicatieStapel()));

        LOG.debug("Converteer categorie 13: kiesrecht");
        builder.kiesrechtStapel(kiesrechtConverteerder.converteer(
                brpPersoonslijst.getUitsluitingNederlandsKiesrechtStapel(),
                brpPersoonslijst.getEuropeseVerkiezingenStapel()));

        LOG.debug("Build (lo3)persoonslijst");
        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        if (brpPersoonslijst.getGeprivilegieerdeIndicatieStapel() != null) {
            LOG.debug("Verwerk geprivilegieerde");
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels =
                    lo3Persoonslijst.getNationaliteitStapels();
            if (!bestaatLo3NationaliteitStapelMetProbas(nationaliteitStapels)) {
                // pas de nationaliteit alleen aan als er nog geen probas vermelding is
                final Lo3Stapel<Lo3NationaliteitInhoud> eersteStapel = nationaliteitStapels.get(0);
                final Lo3Stapel<Lo3NationaliteitInhoud> aangepasteStapel =
                        nationaliteitConverteerder.converteerGeprivilegieerde(
                                brpPersoonslijst.getGeprivilegieerdeIndicatieStapel(), eersteStapel);
                nationaliteitStapels.remove(0);
                nationaliteitStapels.add(0, aangepasteStapel);
            }
        }

        return lo3Persoonslijst;
    }

    private boolean bestaatLo3NationaliteitStapelMetProbas(
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels) {
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : nationaliteitStapels) {
            final String beschrijvingDocument =
                    stapel.getMeestRecenteElement().getDocumentatie().getBeschrijvingDocument();
            if (beschrijvingDocument != null && beschrijvingDocument.startsWith(BrpNationaliteitConverteerder.PROBAS)) {
                return true;
            }
        }
        return false;
    }

    private static BrpStapel<BrpOuder1GezagInhoud> converteerOuder1GezagStapel(final List<BrpBetrokkenheid> ouders) {
        if (ouders == null) {
            return null;
        }

        final List<BrpGroep<BrpOuder1GezagInhoud>> groepen = new ArrayList<BrpGroep<BrpOuder1GezagInhoud>>();
        for (final BrpBetrokkenheid ouder : ouders) {
            if (ouder.getOuderlijkGezagStapel() != null) {
                for (final BrpGroep<BrpOuderlijkGezagInhoud> groep : ouder.getOuderlijkGezagStapel()) {
                    groepen.add(new BrpGroep<BrpOuder1GezagInhoud>(new BrpOuder1GezagInhoud(groep.getInhoud()
                            .getOuderHeeftGezag()), groep.getHistorie(), groep.getActieInhoud(), groep
                            .getActieVerval(), groep.getActieGeldigheid()));
                }
            }
        }

        return groepen.isEmpty() ? null : new BrpStapel<BrpOuder1GezagInhoud>(groepen);
    }

    private static BrpStapel<BrpOuder2GezagInhoud> converteerOuder2GezagStapel(final List<BrpBetrokkenheid> ouders) {
        if (ouders == null) {
            return null;
        }

        final List<BrpGroep<BrpOuder2GezagInhoud>> groepen = new ArrayList<BrpGroep<BrpOuder2GezagInhoud>>();
        for (final BrpBetrokkenheid ouder : ouders) {
            if (ouder.getOuderlijkGezagStapel() != null) {
                for (final BrpGroep<BrpOuderlijkGezagInhoud> groep : ouder.getOuderlijkGezagStapel()) {
                    groepen.add(new BrpGroep<BrpOuder2GezagInhoud>(new BrpOuder2GezagInhoud(groep.getInhoud()
                            .getOuderHeeftGezag()), groep.getHistorie(), groep.getActieInhoud(), groep
                            .getActieVerval(), groep.getActieGeldigheid()));
                }
            }
        }

        return groepen.isEmpty() ? null : new BrpStapel<BrpOuder2GezagInhoud>(groepen);

    }
}
