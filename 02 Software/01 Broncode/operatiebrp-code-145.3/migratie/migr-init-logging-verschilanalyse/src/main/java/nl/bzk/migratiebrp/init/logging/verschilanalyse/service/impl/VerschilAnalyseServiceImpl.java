/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpSorterenLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse.VergelijkResultaat;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse.VerschilBepaler;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.VerschilAnalyseService;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de VerschilAnalayseRegel.
 */
@Service
public final class VerschilAnalyseServiceImpl implements VerschilAnalyseService {
    private static final Logger LOG = LoggerFactory.getLogger();

    private final PreconditiesService preconditieService;
    private final Lo3SyntaxControle syntaxControle;
    private final BrpSorterenLo3 brpSorterenLo3;
    private final VerschilBepaler verschilBepaler;

    /**
     * Constructor voor de {@link VerschilAnalyseServiceImpl}.
     * @param preconditieService preconditie service
     * @param syntaxControle syntax controle service
     * @param brpSorterenLo3 brp sorteren op LO# service
     */
    @Inject
    public VerschilAnalyseServiceImpl(final PreconditiesService preconditieService, final Lo3SyntaxControle syntaxControle,
                                      final BrpSorterenLo3 brpSorterenLo3) {
        this.preconditieService = preconditieService;
        this.syntaxControle = syntaxControle;
        this.brpSorterenLo3 = brpSorterenLo3;
        verschilBepaler = new VerschilBepaler();
    }

    @Override
    public void bepaalVerschillen(final InitVullingLog vullingLog) {
        final String brpLo3Xml = vullingLog.getBerichtNaTerugConversie();
        final String lo3Bericht = vullingLog.getLo3Bericht();

        if (brpLo3Xml != null && lo3Bericht != null) {
            final Lo3Persoonslijst lo3Pl = createLo3Persoonslijst(lo3Bericht, vullingLog);
            final Lo3Persoonslijst brpLo3Pl = createBrpLo3PersoonsLijst(brpLo3Xml, vullingLog);
            final List<VergelijkResultaat> verschillen = verschilBepaler.bepaalVerschillen(lo3Pl, brpLo3Pl);

            final String brpLo3Bericht = converteerNaarLo3Formaat(brpLo3Pl);
            vullingLog.setBerichtNaTerugConversie(brpLo3Bericht);

            // Herkomst opnieuw genereren door te parsen vanuit lo3
            verschillen.addAll(verschilBepaler.controleerActueelJuist(maakLo3PersoonslijstVoorActueelOnjuistBepaling(brpLo3Bericht, vullingLog)));


            for (final VergelijkResultaat verschil : verschillen) {
                vullingLog.addVerschilAnalyseRegels(verschil.getRegels());
                vullingLog.addFingerPrint(verschil.getVingerafdruk());
            }
        }
    }

    @Override
    public List<VergelijkResultaat> bepaalVerschillen(final Lo3Persoonslijst lo3Pl, final Lo3Persoonslijst brpLo3Pl) {
        final List<VergelijkResultaat> verschillen = verschilBepaler.bepaalVerschillen(lo3Pl, brpLo3Pl);
        final String brpLo3Bericht = converteerNaarLo3Formaat(brpLo3Pl);
        verschillen.addAll(verschilBepaler.controleerActueelJuist(maakLo3PersoonslijstVoorActueelOnjuistBepaling(brpLo3Bericht, new InitVullingLog())));
        return verschillen;
    }

    /**
     * Maakt van een XML-structuur een {@link Lo3Persoonslijst}.
     */
    private Lo3Persoonslijst createBrpLo3PersoonsLijst(final String xmlData, final InitVullingLog vullingLog) {
        Lo3Persoonslijst lo3Pl = null;
        try (final Reader reader = new StringReader(xmlData)) {
            lo3Pl = MigratieXml.decode(Lo3Persoonslijst.class, reader);
        } catch (final IOException | XmlException e) {
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
        } catch (final BerichtSyntaxException | OngeldigePersoonslijstException | NumberFormatException e) {
            final String melding = "Fout bij maken persoonslijst vanuit Lo3-bericht";
            logFoutmelding(vullingLog, melding, e);
        }
        return lo3Pl;
    }

    private Lo3Persoonslijst maakLo3PersoonslijstVoorActueelOnjuistBepaling(final String brpLo3Data, final InitVullingLog vullingLog) {
        Lo3Persoonslijst lo3Pl = null;
        try {
            final List<Lo3CategorieWaarde> categorieWaarden = Lo3Inhoud.parseInhoud(brpLo3Data);
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            lo3Pl = parser.parse(categorieWaarden);
        } catch (final BerichtSyntaxException | NumberFormatException e) {
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
