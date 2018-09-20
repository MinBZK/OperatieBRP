/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.serialize.XmlEncoding;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpSorterenLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse.VerschilBepaler;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.VerschilAnalyseService;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de VerschilAnalayseRegel.
 */
@Service
public final class VerschilAnalyseServiceImpl implements VerschilAnalyseService {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private Lo3SyntaxControle syntaxControle;
    @Inject
    private BrpSorterenLo3 brpSorterenLo3;

    @Inject
    private VerschilBepaler verschilBepaler;

    @Override
    public void bepaalVerschillen(final InitVullingLog vullingLog) {
        final String brpLo3Xml = vullingLog.getBerichtNaTerugConversie();
        final String lo3Bericht = vullingLog.getLo3Bericht();

        if (brpLo3Xml != null && lo3Bericht != null) {
            final Lo3Persoonslijst lo3Pl = createLo3Persoonslijst(lo3Bericht, vullingLog);
            final Lo3Persoonslijst brpLo3Pl = createBrpLo3PersoonsLijst(brpLo3Xml, vullingLog);
            final List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> verschillen = verschilBepaler.bepaalVerschillen(lo3Pl, brpLo3Pl);
            if (verschillen != null) {
                for (final Pair<List<VerschilAnalyseRegel>, FingerPrint> verschil : verschillen) {
                    vullingLog.addVerschilAnalyseRegels(verschil.getLeft());
                    vullingLog.addFingerPrint(verschil.getRight());
                }
                vullingLog.setBerichtNaTerugConversie(converteerNaarLo3Formaat(brpLo3Pl));
            }
        }
    }

    @Override
    public List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> bepaalVerschillen(final Lo3Persoonslijst lo3Pl, final Lo3Persoonslijst brpLo3Pl) {
        return verschilBepaler.bepaalVerschillen(lo3Pl, brpLo3Pl);
    }

    /**
     * Maakt van een XML-structuur een {@link Lo3Persoonslijst}.
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    private Lo3Persoonslijst createBrpLo3PersoonsLijst(final String xmlData, final InitVullingLog vullingLog) {
        Lo3Persoonslijst lo3Pl = null;
        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(xmlData.getBytes(EncodingConstants.CHARSET));
            lo3Pl = XmlEncoding.decode(Lo3Persoonslijst.class, bais);
        } catch (final RuntimeException e /* Moet hier gevangen worden omdat decode een RuntimeException kan gooien. */) {
            final String melding = "Fout bij maken persoonslijst vanuit BRP/LO3 xml-structuur";
            logFoutmelding(vullingLog, melding, e);
        }
        return lo3Pl;
    }

    /**
     * Maakt van een LO3 berichtinhoud formaat een {@link Lo3Persoonslijst}.
     */
    private Lo3Persoonslijst createLo3Persoonslijst(final String lo3Data, final InitVullingLog vullingLog) {
        Lo3Persoonslijst lo3Pl = null;
        try {
            lo3Pl = aanvullenControlerenSorteren(lo3Data);
        } catch (final
            BerichtSyntaxException
            | OngeldigePersoonslijstException
            | NumberFormatException e)
        {
            final String melding = "Fout bij maken persoonslijst vanuit Lo3-bericht";
            logFoutmelding(vullingLog, melding, e);
        }
        return lo3Pl;
    }

    private void logFoutmelding(final InitVullingLog vullingLog, final String melding, final Exception e) {
        LOG.info(melding, e);
        vullingLog.setFoutmelding(melding + " " + e.getMessage());
    }

    private Lo3Persoonslijst aanvullenControlerenSorteren(final String lo3Data) throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final List<Lo3CategorieWaarde> categorieWaarden = Lo3Inhoud.parseInhoud(lo3Data);
        // Controle op syntax
        final List<Lo3CategorieWaarde> syntax = syntaxControle.controleer(categorieWaarden);
        final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
        Lo3Persoonslijst lo3 = parser.parse(syntax);

        // Groep 80 vullen met standaardwaarde als deze niet aanwezig is.
        if (lo3.getInschrijvingStapel() != null && lo3.isGroep80VanInschrijvingStapelLeeg()) {
            lo3 = lo3.maakKopieMetDefaultGroep80VoorInschrijvingStapel();
        }

        // Precondities controleren
        Lo3Persoonslijst lo3Pl = preconditieService.verwerk(lo3);

        // Sorteren zoals bij terug conversie gebeurd
        lo3Pl = brpSorterenLo3.converteer(lo3Pl);
        return lo3Pl;
    }

    private String converteerNaarLo3Formaat(final Lo3Persoonslijst brpLo3Pl) {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(brpLo3Pl);
        return Lo3Inhoud.formatInhoud(categorieen);
    }
}
