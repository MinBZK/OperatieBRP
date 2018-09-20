/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.gba.gbav.spontaan.GebeurtenisAnalyse;
import nl.gba.gbav.spontaan.impl.DatumKey;
import nl.gba.gbav.spontaan.impl.Gebeurtenis;
import nl.gba.gbav.spontaan.impl.GebeurtenisAnalyseImpl;
import nl.gba.gbav.spontaan.verschilanalyse.PlDiffResult;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.migratie.controle.ControleService;
import nl.moderniseringgba.migratie.controle.rapport.BijzondereSituatie;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.HerstelActie;
import nl.moderniseringgba.migratie.controle.rapport.HerstelActieEnum;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.controle.util.ControleUtil;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.service.LoggingService;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

/**
 * Service voor het controleren van consistentie van 2 PL'en.
 */
@Component
public class ControleServiceImpl implements ControleService {

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private LoggingService loggingService;

    @Inject
    private GemeenteRepository gemeenteRepo;

    /*
     * (non-Javadoc)
     * 
     * @see nl.moderniseringgba.migratie.controle.ControleService#controleerPLen(java.util.List,
     * nl.moderniseringgba.migratie.controle.rapport.Opties,
     * nl.moderniseringgba.migratie.controle.rapport.ControleRapport)
     */
    @Override
    public final void controleerPLen(
            final List<Long> anummers,
            final Opties opties,
            final ControleRapport controleRapport) {
        for (final Long anummer : anummers) {
            // Ophalen meest recente PL uit GBAV gegevensverzameling
            final InitVullingLog gbavLogRegel = loggingService.findLog(anummer);

            // Ophalen meeste recente PL uit Bericht Logging gegevensverzameling
            final BerichtLog berichtLog = brpDalService.zoekBerichtLogOpAnummer(anummer);

            final Lo3Persoonslijst brpPersoonslijst =
                    createBrpPL(berichtLog != null ? berichtLog.getBerichtData() : null, controleRapport);

            final Lo3Persoonslijst gbavPersoonslijst =
                    createLo3PL(gbavLogRegel != null ? gbavLogRegel.getLo3Bericht() : null, controleRapport);

            // Als er aan 1 van 2 kanten geen PL gevonden is, PL vergelijken en herstellen
            // De bericht hash maken en vergelijken.
            if (berichtLog == null
                    || berichtLog.getBerichtHash() == null
                    || gbavPersoonslijst == null
                    || !("" + new HashCodeBuilder().append(gbavLogRegel.getLo3Bericht()).build()).equals(berichtLog
                            .getBerichtHash())) {
                // Als de hash niet gelijk is
                vergelijkPLen(opties, controleRapport, gbavPersoonslijst, brpPersoonslijst, anummer);
            }
        }
    }

    private Lo3Persoonslijst createLo3PL(final String lo3Bericht, final ControleRapport controleRapport) {
        Lo3Persoonslijst resultaat = null;

        try {
            resultaat = ControleUtil.createLo3Persoonslijst(lo3Bericht);
        } catch (final BerichtSyntaxException e) {
            // Onverwachte fout, log dit.
            logSituatie(null, e, lo3Bericht, null, controleRapport);
        }

        return resultaat;
    }

    private Lo3Persoonslijst createBrpPL(final String brpBericht, final ControleRapport controleRapport) {
        Lo3Persoonslijst resultaat = null;
        try {
            resultaat = ControleUtil.createLo3Persoonslijst(brpBericht);
            // CHECKSTYLE:OFF - Alle fouten worden voorlopig hetzelfde afgehandeld.
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            // Onverwachte fout, log dit.
            logSituatie(null, e, null, brpBericht, controleRapport);
        }
        return resultaat;
    }

    private void vergelijkPLen(
            final Opties opties,
            final ControleRapport controleRapport,
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst,
            final Long anummer) {

        if (gbavPersoonslijst == null && brpPersoonslijst == null) {
            logSituatie("Beide PL-en zijn null! Anummer: " + anummer, null, null, null, controleRapport);
        } else if (gbavPersoonslijst == null || brpPersoonslijst == null) {
            final String pl = brpPersoonslijst != null ? brpPersoonslijst.toString() : gbavPersoonslijst.toString();
            herstelPL(controleRapport, anummer, bepaalActie(gbavPersoonslijst, brpPersoonslijst, controleRapport), pl);
        } else {
            // Controleer controle niveau
            // CHECKSTYLE:OFF - Het is een enum, als ik een default toevoeg die nooit geraakt wordt gaat de test
            // coverage ook nog omlaag.
            switch (opties.getControleNiveau()) { // NOSONAR
            // CHECKSTYLE:ON
                case VERSIE_EN_DATUM:
                    controleerSimpel(gbavPersoonslijst, brpPersoonslijst, controleRapport, anummer);
                    break;
                case VOLLEDIGE_PL:
                    controleerVolledig(gbavPersoonslijst, brpPersoonslijst, controleRapport, anummer);
                    break;
            }
        }
    }

    private void controleerSimpel(
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst,
            final ControleRapport controleRapport,
            final Long anummer) {
        // Vergelijken op basis van: 07.80.10 Versienummer en 07.80.20 Datumtijdstempel
        final Integer brpVersieNr =
                brpPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud().getVersienummer();
        final long brpDatumtijdStempel =
                brpPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud().getDatumtijdstempel()
                        .getDatum();

        final Integer gbavVersieNr =
                gbavPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud().getVersienummer();
        final long gbavDatumtijdStempel =
                gbavPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud().getDatumtijdstempel()
                        .getDatum();

        if (brpDatumtijdStempel != gbavDatumtijdStempel || !brpVersieNr.equals(gbavVersieNr)) {
            // Versie en of tijdstempel is anders, herstelactie
            herstelPL(controleRapport, anummer, bepaalActie(gbavPersoonslijst, brpPersoonslijst, controleRapport),
                    "brp versieNr:" + brpVersieNr.toString() + " brp datumTijdStempel: " + brpDatumtijdStempel
                            + " - gbav versieNr:" + gbavVersieNr.toString() + " gbav datumTijdStempel: "
                            + gbavDatumtijdStempel);
        }
    }

    private void controleerVolledig(
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst,
            final ControleRapport controleRapport,
            final Long anummer) {
        // Vergelijken van de hele PL mbv Spontaan
        final List<Lo3CategorieWaarde> brpCategorieen = new Lo3PersoonslijstFormatter().format(brpPersoonslijst);
        final String brpPl = Lo3Inhoud.formatInhoud(brpCategorieen);

        final List<Lo3CategorieWaarde> gbavCategorieen = new Lo3PersoonslijstFormatter().format(gbavPersoonslijst);
        final String gbavPl = Lo3Inhoud.formatInhoud(gbavCategorieen);

        // plData van maken en vergelijken met spontaan
        final PlDiffResult diffResult = ControleUtil.vergelijkInhoudPLen(brpPl, gbavPl);
        final GebeurtenisAnalyse gebAnalyzer = new GebeurtenisAnalyseImpl();
        final Map<DatumKey, List<Gebeurtenis>> gebeurtenissen = gebAnalyzer.determineGebeurtenissen(diffResult);
        if (gebeurtenissen.size() > 0) {
            // Er is een verschil, herstelactie
            herstelPL(controleRapport, anummer, bepaalActie(gbavPersoonslijst, brpPersoonslijst, controleRapport),
                    diffResult.toString());
        }
    }

    private void herstelPL(
            final ControleRapport controleRapport,
            final Long anummer,
            final HerstelActieEnum actie,
            final String verschil) {
        final HerstelActie herstelActie = new HerstelActie();
        herstelActie.setActie(actie);
        herstelActie.setAnummer(anummer);
        herstelActie.setVerschil(verschil);
        controleRapport.getHerstellijst().getHerstelActies().add(herstelActie);
        controleRapport.setAantalVerschillen(controleRapport.getAantalVerschillen() + 1);
    }

    private HerstelActieEnum bepaalActie(
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst,
            final ControleRapport controleRapport) {
        HerstelActieEnum actie = null;
        String gemeenteCode = null;
        if (gemeenteVanInschrijvingGevuld(gbavPersoonslijst)) {
            gemeenteCode =
                    gbavPersoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                            .getGemeenteInschrijving().getCode();
        } else if (gemeenteVanInschrijvingGevuld(brpPersoonslijst)) {
            gemeenteCode =
                    brpPersoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                            .getGemeenteInschrijving().getCode();
        }
        if (gemeenteCode != null) {
            // Check of de PL wordt bijgehouden in een BRP gemeente.
            final Gemeente gemeente = gemeenteRepo.findGemeente(Integer.parseInt(gemeenteCode));
            if (gemeente == null) {
                // De gemeente van bijhouding zit nog in GBA, dus HerstelActieEnum.SYNC_NAAR_BRP
                actie = HerstelActieEnum.SYNC_NAAR_BRP;
            } else {
                // De gemeente van bijhouding zit in BRP, dus HerstelActieEnum.SYNC_NAAR_BRP
                actie = HerstelActieEnum.SYNC_NAAR_GBA;
            }

            checkAnomalies(gbavPersoonslijst, brpPersoonslijst, actie, controleRapport);
        } else {
            logSituatie("Er is geen gemeentecode gevonden op de PL.", null,
                    gbavPersoonslijst != null ? gbavPersoonslijst.toString() : null,
                    brpPersoonslijst != null ? brpPersoonslijst.toString() : null, controleRapport);
        }

        return actie;
    }

    private boolean gemeenteVanInschrijvingGevuld(final Lo3Persoonslijst gbavPersoonslijst) {
        boolean gevuld = true;
        if (gbavPersoonslijst == null) {
            gevuld = false;
        } else if (gbavPersoonslijst.getVerblijfplaatsStapel() == null) {
            gevuld = false;
        } else if (gbavPersoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement() == null) {
            gevuld = false;
        } else if (gbavPersoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud() == null) {
            gevuld = false;
        } else if (gbavPersoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                .getGemeenteInschrijving() == null) {
            gevuld = false;
        }
        return gevuld;
    }

    private void checkAnomalies(
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst,
            final HerstelActieEnum herstelActie,
            final ControleRapport controleRapport) {
        switch (herstelActie) {
            case SYNC_NAAR_BRP:
                // gemeente van bijhouding zit in een stelsel waar de PL niet bestaat, dit is gek, log dit.
                if (gbavPersoonslijst == null) {
                    // Gekke situatie, log dit.
                    logSituatie(
                            "De GBA-V persoonslijst is niet aanwezig maar de sync zou wel van GBA-V naar BRP moeten "
                                    + "gaan. Dit is een gekke situatie!", null, null,
                            brpPersoonslijst != null ? brpPersoonslijst.toString() : null, controleRapport);
                }
                // gemeente van bijhouding heeft niet de meest recente PL, dit is gek, log dit.
                if (ControleUtil.compareTijdStempelEnVersie(gbavPersoonslijst, brpPersoonslijst) == -1) {
                    // Gekke situatie, log dit.
                    logSituatie(
                            "De GBA-V persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een "
                                    + "gekke situatie aangzien de sync naar BRP gaat.", null,
                            gbavPersoonslijst.toString(), brpPersoonslijst.toString(), controleRapport);
                }
                break;
            case SYNC_NAAR_GBA:
                // gemeente van bijhouding zit in een stelsel waar de PL niet bestaat, dit is gek, log dit.
                if (brpPersoonslijst == null) {
                    // Gekke situatie, log dit.
                    logSituatie(
                            "De BRP persoonslijst is niet aanwezig maar de sync zou wel van BRP naar GBA-V moeten"
                                    + " gaan. Dit is een gekke situatie!", null,
                            gbavPersoonslijst != null ? gbavPersoonslijst.toString() : null, null, controleRapport);
                }
                // gemeente van bijhouding heeft niet de meest recente PL, dit is gek, log dit.
                if (ControleUtil.compareTijdStempelEnVersie(gbavPersoonslijst, brpPersoonslijst) == 1) {
                    // Gekke situatie, log dit.
                    logSituatie(
                            "De BRP persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een gekke"
                                    + " situatie aangzien de sync naar GBA-V gaat.", null,
                            gbavPersoonslijst.toString(), brpPersoonslijst.toString(), controleRapport);
                }
                break;

            default:
                break;
        }
    }

    private void logSituatie(
            final String melding,
            final Exception e,
            final String lo3Pl,
            final String brpPl,
            final ControleRapport controleRapport) {
        String foutmelding = melding;
        if (foutmelding == null) {
            foutmelding = "Onverwachte fout opgetreden!";
        }
        controleRapport.getBijzondereSituaties().add(
                new BijzondereSituatie(lo3Pl, brpPl, foutmelding + (e != null ? e.getMessage() : "")));
    }
}
