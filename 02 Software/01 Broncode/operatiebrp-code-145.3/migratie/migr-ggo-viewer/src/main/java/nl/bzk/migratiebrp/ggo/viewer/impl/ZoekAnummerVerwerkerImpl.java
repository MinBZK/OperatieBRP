/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.ZoekAnummerVerwerker;
import nl.bzk.migratiebrp.ggo.viewer.builder.GgoPersoonslijstGroepBuilder;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.ggo.viewer.service.PermissionService;
import nl.bzk.migratiebrp.ggo.viewer.service.ProtocolleringService;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De verwerker voor anummer zoek acties.
 */
@Component
public class ZoekAnummerVerwerkerImpl implements ZoekAnummerVerwerker {
    private static final String ANUMMER_FIELDID = "aNummer";

    @Inject
    private DbService dbService;
    @Inject
    private ViewerService viewerService;
    @Inject
    private BcmService bcmService;
    @Inject
    private PermissionService permissionService;
    @Inject
    private GgoPersoonslijstGroepBuilder ggoPlGroepBuilder;
    @Inject
    private ProtocolleringService protocolleringService;

    /**
     * Constructor voor Spring.
     */
    public ZoekAnummerVerwerkerImpl() {

    }

    /**
     * Constructor zodat je deze class ook gewoon echt kunt gebruiken.
     * @param dbService DbService
     * @param viewerService ViewerService
     * @param bcmService BcmService
     * @param permissionService PermissionService
     * @param ggoPlGroepBuilder PersoonslijstGroepBuilder
     * @param protocolleringService ProtocolleringService
     */
    public ZoekAnummerVerwerkerImpl(
            final DbService dbService,
            final ViewerService viewerService,
            final BcmService bcmService,
            final PermissionService permissionService,
            final GgoPersoonslijstGroepBuilder ggoPlGroepBuilder,
            final ProtocolleringService protocolleringService) {
        this.dbService = dbService;
        this.viewerService = viewerService;
        this.bcmService = bcmService;
        this.permissionService = permissionService;
        this.ggoPlGroepBuilder = ggoPlGroepBuilder;
        this.protocolleringService = protocolleringService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
    public final List<GgoPersoonslijstGroep> zoekOpAnummer(final String aNummer, final FoutMelder foutMelder) {
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = new ArrayList<>();

        // zoek Lo3 BerichtLog op
        final Lo3Bericht lo3Bericht = dbService.zoekLo3Bericht(aNummer);
        // Zoek Lo3Persoonslijst op
        final Lo3Persoonslijst lo3Persoonslijst = dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht);
        if (lo3Persoonslijst != null) {
            // Autorisatie.
            // Bepalen of deze pl gezien mag worden door de huidige gebruiker. Bron is Lo3PL.
            final boolean permission = permissionService.hasPermissionOnPl(lo3Persoonslijst);

            // Protocolleren opvragen PL.
            final Protocollering protocollering =
                    new Protocollering(
                            String.valueOf(SecurityUtils.getSubject().getPrincipal()),
                            new Timestamp(System.currentTimeMillis()),
                            aNummer,
                            permission);
            protocolleringService.persisteerProtocollering(protocollering);

            if (permission) {
                final List<GgoFoutRegel> meldingen = new ArrayList<>();
                final FoutMelder foutLog = new FoutMelder();

                // Zoek logging op (Precondities)
                final List<GgoFoutRegel> logRegels = dbService.haalLogRegelsUitLo3Bericht(lo3Bericht);
                if (logRegels != null) {
                    meldingen.addAll(logRegels);
                }

                // BCM
                final List<GgoFoutRegel> bcm = bcmService.controleerDoorBCM(lo3Persoonslijst, foutLog);
                if (bcm != null) {
                    meldingen.addAll(bcm);
                }

                // Zoek Persoon op
                final Persoon persoon = dbService.zoekPersoon(aNummer);

                // Zoek BrpPersoonslijst op
                final BrpPersoonslijst brpPersoonslijst = dbService.zoekBrpPersoonsLijst(aNummer);

                // Zoek terugconversie op
                Lo3Persoonslijst teruggeconverteerd = null;
                if (brpPersoonslijst != null) {
                    teruggeconverteerd = viewerService.converteerTerug(brpPersoonslijst, foutLog);
                }

                // Bepaal verschillen
                List<GgoVoorkomenVergelijking> matches = null;
                if (teruggeconverteerd != null) {
                    matches = viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3Persoonslijst, teruggeconverteerd, foutLog);
                }

                // Bepaal lo3Herkomst op basis van de verschil/match analyse
                if (matches != null && !matches.isEmpty()) {
                    teruggeconverteerd = viewerService.voegLo3HerkomstToe(lo3Persoonslijst, teruggeconverteerd, foutLog);
                }

                // Bouw model
                persoonslijstGroepen.add(ggoPlGroepBuilder.build(
                        lo3Persoonslijst,
                        brpPersoonslijst,
                        persoon,
                        teruggeconverteerd,
                        matches,
                        meldingen,
                        foutLog.getFoutRegels()));
            } else {
                foutMelder.log(
                        LogSeverity.WARNING,
                        "De PL met A-nummer " + aNummer + " mag niet ingezien worden",
                        "Deze persoon woont in een andere gemeente dan waarvoor u geautoriseerd bent.",
                        ANUMMER_FIELDID);
            }
        } else {
            foutMelder.log(LogSeverity.WARNING, "Geen PL gevonden met A-nummer " + aNummer, "Controleer het nummer en probeer opnieuw.", ANUMMER_FIELDID);
        }

        return persoonslijstGroepen;
    }
}
