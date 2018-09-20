/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.GezagsverhoudingConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.HuwelijkConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.InschrijvingConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.KiesrechtConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.KindConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.NationaliteitConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.OuderConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.OverlijdenConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.PersoonConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.ReisdocumentConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.VerblijfplaatsConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.VerblijfsrechtConverteerder;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Deze service levert de functionaliteit om LO3 inhoud naar BRP inhoud te converteren.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB10)
public class Lo3InhoudNaarBrpConversieStap {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private PersoonConverteerder persoonConverteerder;
    @Inject
    private OuderConverteerder ouderConverteerder;
    @Inject
    private NationaliteitConverteerder nationaliteitConverteerder;
    @Inject
    private HuwelijkConverteerder huwelijkConverteerder;
    @Inject
    private OverlijdenConverteerder overlijdenConverteerder;
    @Inject
    private InschrijvingConverteerder inschrijvingConverteerder;
    @Inject
    private VerblijfplaatsConverteerder verblijfplaatsConverteerder;
    @Inject
    private KindConverteerder kindConverteerder;
    @Inject
    private VerblijfsrechtConverteerder verblijfsrechtConverteerder;
    @Inject
    private GezagsverhoudingConverteerder gezagsverhoudingConverteerder;
    @Inject
    private ReisdocumentConverteerder reisdocumentConverteerder;
    @Inject
    private KiesrechtConverteerder kiesrechtConverteerder;

    /**
     * Converteert de LO3 Persoonslijst in een Migratie persoonslijst.
     * 
     * @param lo3Persoonslijst
     *            de te converteren LO3 persoonslijst
     * @return een nieuw MigratiePersoonslijst object
     */
    public final MigratiePersoonslijst converteer(final Lo3Persoonslijst lo3Persoonslijst) {
        LOG.debug("converteer(lo3Persoonslijst={})", lo3Persoonslijst.getActueelAdministratienummer());
        final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder = new MigratiePersoonslijstBuilder();

        LOG.debug("Converteer categorie 01: persoon");
        persoonConverteerder.converteer(lo3Persoonslijst.getPersoonStapel(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 02/03: ouders (inclusief gedeelte 11: gezagsverhouding)");
        ouderConverteerder.converteer(lo3Persoonslijst.getOuder1Stapels(), lo3Persoonslijst.getOuder2Stapels(),
                lo3Persoonslijst.getGezagsverhoudingStapel(), lo3Persoonslijst.getPersoonStapel()
                        .getMeestRecenteElement(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 04: nationaliteit");
        nationaliteitConverteerder.converteer(lo3Persoonslijst.getNationaliteitStapels(),
                migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 05: huwelijk");
        huwelijkConverteerder.converteer(lo3Persoonslijst.getHuwelijkOfGpStapels(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 06: overlijden");
        overlijdenConverteerder.converteer(lo3Persoonslijst.getOverlijdenStapel(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 07: inschrijving");
        inschrijvingConverteerder.converteer(lo3Persoonslijst.getPersoonStapel(),
                lo3Persoonslijst.getVerblijfplaatsStapel(), lo3Persoonslijst.getInschrijvingStapel(),
                migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 08: verblijfplaats");
        verblijfplaatsConverteerder.converteer(lo3Persoonslijst.getVerblijfplaatsStapel(),
                migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 09: kind");
        kindConverteerder.converteer(lo3Persoonslijst.getKindStapels(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 10: verblijfstitel");
        verblijfsrechtConverteerder.converteer(lo3Persoonslijst.getVerblijfstitelStapel(),
                migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 11: gezagsverhouding");
        gezagsverhoudingConverteerder.converteer(lo3Persoonslijst.getGezagsverhoudingStapel(),
                migratiePersoonslijstBuilder);

        LOG.debug("Converteer categorie 12: reisdocument");
        reisdocumentConverteerder.converteer(lo3Persoonslijst.getReisdocumentStapels(), migratiePersoonslijstBuilder);

        LOG.debug("Converteer cateogrie 13: kiesrecht");
        kiesrechtConverteerder.converteer(lo3Persoonslijst.getKiesrechtStapel(),
                lo3Persoonslijst.getInschrijvingStapel(), migratiePersoonslijstBuilder);

        LOG.debug("Build (migratie)persoonslijst");
        return migratiePersoonslijstBuilder.build();
    }
}
