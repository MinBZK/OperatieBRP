/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.ParseException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;

/**
 * Abstract synchronisatie verwerker waarin basis verwerking is geimplementeerd.
 */
public abstract class AbstractSynchronisatieVerwerkerImpl implements SynchronisatieVerwerker {

    @Inject
    private Lo3SyntaxControle syntaxControle;

    @Inject
    private PreconditiesService preconditieService;

    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    /**
     * Parse de LO3 persoonslijst uit het synchronisatie verzoek.
     *
     * @param logging
     *            logging
     * @param verzoek
     *            verzoek
     * @return LO3 persoonslijst
     * @throws SynchronisatieVerwerkerException
     *             bij fouten (parsen of syntax)
     */
    protected final Lo3Persoonslijst parsePersoonslijst(final PlVerwerkerLogging logging, final SynchroniseerNaarBrpVerzoekBericht verzoek)
        throws SynchronisatieVerwerkerException
    {
        Lo3Persoonslijst lo3Persoonslijst = null;
        try {
            // Parse teletex string naar lo3 categorieen
            final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getLo3BerichtAsTeletexString());

            // Controleer syntax obv lo3 categorieen
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);

            // Parse categorieen naar lo3 persoonslijst
            lo3Persoonslijst = new Lo3PersoonslijstParser().parse(lo3InhoudNaSyntaxControle);
        } catch (final BerichtSyntaxException e) {
            logging.addBeslissing(SynchronisatieBeslissing.PARSE_BERICHT_PARSE_FOUT);
            logEnConverteerExceptie(logging, e, "Persoonslijst kan niet geparsed worden: ");
        } catch (final OngeldigePersoonslijstException e) {
            logging.addBeslissing(SynchronisatieBeslissing.PARSE_BERICHT_SYNTAX_FOUT);
            logEnConverteerExceptie(logging, e, "Persoonslijst voldoet niet aan syntax controle: ");
        } catch (final ParseException e) {
            logging.addBeslissing(SynchronisatieBeslissing.PARSE_BERICHT_INHOUD_FOUT);
            logEnConverteerExceptie(logging, e, "Persoonslijst voldoet niet aan inhoudelijk syntax controle: ");
        }

        return lo3Persoonslijst;
    }

    private <T extends Exception> void logEnConverteerExceptie(final PlVerwerkerLogging logging, final T e, final String melding)
        throws SynchronisatieVerwerkerException
    {
        logging.addMelding(melding + e.getMessage());
        throw new SynchronisatieVerwerkerException(StatusType.FOUT, e);
    }

    /**
     * Converteer de LO3 persoonslijst naar een BRP persoonslijst.
     *
     * @param logging
     *            logging
     * @param lo3Persoonslijst
     *            LO3 persoonslijst
     * @return BRP persoonslijst
     * @throws SynchronisatieVerwerkerException
     *             bij fouten (preconditie, conversie probleem)
     */
    protected final BrpPersoonslijst converteerPersoonslijst(final PlVerwerkerLogging logging, final Lo3Persoonslijst lo3Persoonslijst)
        throws SynchronisatieVerwerkerException
    {

        // Controleer precondities
        final Lo3Persoonslijst schoneLo3Persoonslijst;
        try {
            schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
        } catch (final OngeldigePersoonslijstException e) {
            logging.addBeslissing(SynchronisatieBeslissing.CONVERSIE_PRECONDITIE_FOUT);
            throw new SynchronisatieVerwerkerException(StatusType.AFGEKEURD, e);
        }

        // Converteer lo3 persoonslijst naar brp persoonlijst
        return converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
    }
}
