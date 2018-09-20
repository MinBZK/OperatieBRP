/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.util;

import java.util.List;

import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.lo3.PLData;
import nl.gba.gbav.lo3.util.PLBuilderFactory;
import nl.gba.gbav.spontaan.impl.SpontaanRapportage;
import nl.gba.gbav.spontaan.verschilanalyse.PlDiffResult;
import nl.gba.gbav.spontaan.verschilanalyse.PlVerschilAnalyse;
import nl.gba.gbav.spontaan.verschilanalyse.impl.PlVerschilAnalyseImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.ictu.spg.domain.lo3.util.LO3LelijkParser;
import nl.ictu.spg.domain.pl.util.PLAssembler;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * Util voor het controleren van consistentie van 2 PL'en.
 */
public final class ControleUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private ControleUtil() {
        super();
        // To prevent instantiation
    }

    static {
        // Nodig voor de PLData builder.
        try {
            final String id = System.getProperty("gbav.deployment.id", "vergelijk");
            final String context =
                    System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        } catch (final IllegalStateException e) {
            LOG.debug("De context bestaat al dus niks doen.");
        }
    }

    /**
     * Constructs a Lo3Persoonslijst from InitVullingLog.
     * 
     * @param lo3Lelijk
     *            de lo3 lelijk representatie van de PL.
     * @return Lo3Persoonslijst
     * @throws BerichtSyntaxException
     *             Als het bericht niet geparsed kan worden.
     */
    public static Lo3Persoonslijst createLo3Persoonslijst(final String lo3Lelijk) throws BerichtSyntaxException {
        Lo3Persoonslijst lo3Persoonslijst = null;
        if (lo3Lelijk != null) {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Lelijk);
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            lo3Persoonslijst = parser.parse(categorieen);
        }
        return lo3Persoonslijst;
    }

    /**
     * Vergelijk de versie en tijd stempel van de PL om te bepalen welke nieuwer is.
     * 
     * @param gbavPersoonslijst
     *            GBA-V persoonslijst
     * @param brpPersoonslijst
     *            BRP persoonslijst
     * @return int met waarde 0 als ze gelijk zijn, -1 als de gba-v PL ouder is, 1 als de gba-v PL nieuwer is.
     */
    public static int compareTijdStempelEnVersie(
            final Lo3Persoonslijst gbavPersoonslijst,
            final Lo3Persoonslijst brpPersoonslijst) {
        int result = 0;
        if (gbavPersoonslijst != null && brpPersoonslijst != null) {
            result =
                    gbavPersoonslijst
                            .getInschrijvingStapel()
                            .getMeestRecenteElement()
                            .getInhoud()
                            .getDatumtijdstempel()
                            .compareTo(
                                    brpPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud()
                                            .getDatumtijdstempel());
            if (result == 0) {
                result =
                        gbavPersoonslijst
                                .getInschrijvingStapel()
                                .getMeestRecenteElement()
                                .getInhoud()
                                .getVersienummer()
                                .compareTo(
                                        brpPersoonslijst.getInschrijvingStapel().getMeestRecenteElement().getInhoud()
                                                .getVersienummer());
            }
        }
        return result;

    }

    /**
     * Vergelijk 2 PLen inhoudelijk met elkaar.
     * 
     * @param brpPl
     *            De BRP PL.
     * @param gbavPl
     *            De GBA-V PL.
     * @return PlDiffResult met de inhoudelijke vergelijking.
     */
    public static PlDiffResult vergelijkInhoudPLen(final String brpPl, final String gbavPl) {
        final PlVerschilAnalyse pda = new PlVerschilAnalyseImpl();
        // Gbav pl in LO3 formaat
        final PLData plGbav = ControleUtil.createPLData(gbavPl);
        // Brp pl in LO3 formaat
        final PLData plBrp = ControleUtil.createPLData(brpPl);

        // Bepaal de verschillen tussen beide PL-en
        final SpontaanRapportage rapportage = new SpontaanRapportage();
        return pda.bepaalVerschillen(rapportage, plGbav, plBrp, false);
    }

    /**
     * Maakt een gba-v PLData object.
     * 
     * @param pl
     *            De pl die omgezet moet worden.
     * @return PLData gba-v pl object.
     */
    private static PLData createPLData(final String pl) {
        final PLData plData = PLBuilderFactory.getPLDataBuilder().create();
        if (pl != null) {
            final PLAssembler assembler = new PLAssembler();
            assembler.startOfTraversal(plData);
            final LO3LelijkParser parser = new LO3LelijkParser();
            parser.parse(pl, assembler);
        }
        return plData;
    }
}
