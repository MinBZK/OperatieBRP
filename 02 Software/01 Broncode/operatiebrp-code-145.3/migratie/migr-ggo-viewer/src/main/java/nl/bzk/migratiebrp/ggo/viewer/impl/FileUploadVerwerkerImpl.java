/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.impl;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.ggo.viewer.FileUploadVerwerker;
import nl.bzk.migratiebrp.ggo.viewer.builder.GgoPersoonslijstGroepBuilder;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.service.LeesService;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.ggo.viewer.service.impl.LogRegelConverter;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Verwerkt de fileupload actie van de front end.
 */
@Component
public class FileUploadVerwerkerImpl implements FileUploadVerwerker {
    private final LeesService leesService;
    private final ViewerService viewerService;
    private final BcmService bcmService;
    private final GgoPersoonslijstGroepBuilder ggoPlGroepBuilder;
    private final LogRegelConverter logRegelConverter;

    @Inject
    public FileUploadVerwerkerImpl(final LeesService leesService, final ViewerService viewerService, final BcmService bcmService,
                                   final GgoPersoonslijstGroepBuilder ggoPlGroepBuilder, final LogRegelConverter logRegelConverter) {
        this.leesService = leesService;
        this.viewerService = viewerService;
        this.bcmService = bcmService;
        this.ggoPlGroepBuilder = ggoPlGroepBuilder;
        this.logRegelConverter = logRegelConverter;
    }

    @Override
    public final List<GgoPersoonslijstGroep> verwerkFileUpload(final String filename, final byte[] file, final FoutMelder foutMelder) {
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = new ArrayList<>();

        final List<List<Lo3CategorieWaarde>> plLijst = leesService.leesBestand(filename, file, foutMelder);
        if (plLijst != null) {
            for (final List<Lo3CategorieWaarde> pl : plLijst) {
                final List<GgoFoutRegel> meldingen = new ArrayList<>();
                final FoutMelder foutLog = new FoutMelder();

                Logging.initContext();

                // Verwerk LO3 PL
                final Lo3Persoonslijst lo3PersoonslijstIngelezen = verwerkLo3(pl, meldingen, foutLog);

                // Converteer
                final BrpPersoonslijst brpPersoonslijst = converteer(lo3PersoonslijstIngelezen, foutLog);

                // Naar Entity model
                final Persoon persoon = converteerNaarEntityModel(brpPersoonslijst);

                // Haal meldingen uit log
                registreerConversieMeldingen(meldingen);

                // Converteer terug
                final Lo3Persoonslijst teruggeconverteerd = converteerTerug(brpPersoonslijst, foutLog);

                // Bepaal verschillen
                final List<GgoVoorkomenVergelijking> verschillen = vergelijk(lo3PersoonslijstIngelezen, teruggeconverteerd, foutLog);

                // Voeg herkomst toe
                final Lo3Persoonslijst teruggeconverteerdMetHerkomst = voegLo3HerkomstToe(lo3PersoonslijstIngelezen, teruggeconverteerd, foutLog);

                // Bouw model
                persoonslijstGroepen.add(ggoPlGroepBuilder.build(
                        lo3PersoonslijstIngelezen,
                        brpPersoonslijst,
                        persoon,
                        teruggeconverteerdMetHerkomst,
                        verschillen,
                        meldingen,
                        foutLog.getFoutRegels()));

                Logging.destroyContext();
            }
        }
        return persoonslijstGroepen;
    }

    private Lo3Persoonslijst verwerkLo3(final List<Lo3CategorieWaarde> pl, final List<GgoFoutRegel> meldingen, final FoutMelder foutLog) {
        // Syntax
        final Lo3Persoonslijst lo3PersoonslijstIngelezen = leesService.parsePersoonslijstMetSyntaxControle(pl, foutLog);

        // BCM
        if (lo3PersoonslijstIngelezen != null) {
            final List<GgoFoutRegel> bcm = bcmService.controleerDoorBCM(lo3PersoonslijstIngelezen, foutLog);
            if (bcm != null) {
                meldingen.addAll(bcm);
            }
        }
        return lo3PersoonslijstIngelezen;
    }

    private BrpPersoonslijst converteer(final Lo3Persoonslijst lo3PersoonslijstIngelezen, final FoutMelder foutLog) {
        // Kopieer de persoonslijst naar een nieuw object, inclusief stapels en categorieen. De Conversie
        // sorteert de meegegeven persoonslijst, die willen we niet verder gebruiken in de viewer.
        final Lo3Persoonslijst lo3PersoonslijstVerwerking = VerwerkerUtil.kopieerLo3Persoonslijst(lo3PersoonslijstIngelezen);

        // Precondities
        Lo3Persoonslijst lo3PersoonslijstOpgeschoond = null;
        if (lo3PersoonslijstVerwerking != null) {
            lo3PersoonslijstOpgeschoond = viewerService.verwerkPrecondities(lo3PersoonslijstVerwerking, foutLog);
        }

        // Converteer naar BRP
        BrpPersoonslijst brpPersoonslijst = null;
        if (lo3PersoonslijstOpgeschoond != null) {
            brpPersoonslijst = viewerService.converteerNaarBrp(lo3PersoonslijstOpgeschoond, foutLog);
        } else {
            registreerConversieMislukt(foutLog);
        }
        return brpPersoonslijst;
    }

    private Persoon converteerNaarEntityModel(final BrpPersoonslijst brpPersoonslijst) {
        Persoon persoon = null;

        if (brpPersoonslijst != null) {
            persoon = viewerService.converteerNaarEntityModel(brpPersoonslijst);
        }
        return persoon;
    }

    private Lo3Persoonslijst converteerTerug(final BrpPersoonslijst brpPersoonslijst, final FoutMelder foutLog) {
        // Zoek terugconversie op
        Lo3Persoonslijst teruggeconverteerd = null;
        if (brpPersoonslijst != null) {
            teruggeconverteerd = viewerService.converteerTerug(brpPersoonslijst, foutLog);
        }
        return teruggeconverteerd;
    }

    private List<GgoVoorkomenVergelijking> vergelijk(
            final Lo3Persoonslijst lo3PersoonslijstIngelezen,
            final Lo3Persoonslijst teruggeconverteerd,
            final FoutMelder foutLog) {
        // Bepaal verschillen
        List<GgoVoorkomenVergelijking> verschillen = null;
        if (teruggeconverteerd != null) {
            verschillen = viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3PersoonslijstIngelezen, teruggeconverteerd, foutLog);
        }
        return verschillen;
    }

    private Lo3Persoonslijst voegLo3HerkomstToe(
            final Lo3Persoonslijst lo3PersoonslijstIngelezen,
            final Lo3Persoonslijst teruggeconverteerd,
            final FoutMelder foutLog) {
        // Bepaal lo3Herkomst op basis van de verschil/match analyse
        return viewerService.voegLo3HerkomstToe(lo3PersoonslijstIngelezen, teruggeconverteerd, foutLog);
    }

    /**
     * Registreert de meldingen welke zijn opgekomen voor de conversie (syntax/preconditie controles) en zet deze GGO
     * Foutregel lijst.
     * @param meldingen de meldingen
     * @return of er conversie meldingen zijn
     */
    private boolean registreerConversieMeldingen(final List<GgoFoutRegel> meldingen) {
        final Logging logging = Logging.getLogging();

        if (logging != null) {
            final List<GgoFoutRegel> conversieMeldingen = logRegelConverter.converteerModelNaarGgoFoutRegelList(logging.getRegels());
            if (conversieMeldingen != null && !conversieMeldingen.isEmpty()) {
                meldingen.addAll(conversieMeldingen);
                return true;
            }
        }
        return false;
    }

    /**
     * Registreert dat de conversie naar BRP mislukt is in de FoutMelder.
     * @param foutMelder de foutmelder
     */
    private void registreerConversieMislukt(final FoutMelder foutMelder) {
        // Meld aan de BRP kant dat conversie niet gelukt is.
        foutMelder.setHuidigeStap(GgoStap.BRP);
        foutMelder.log(LogSeverity.ERROR, "Conversie (GBA > BRP)", "niet geslaagd.");
    }
}
