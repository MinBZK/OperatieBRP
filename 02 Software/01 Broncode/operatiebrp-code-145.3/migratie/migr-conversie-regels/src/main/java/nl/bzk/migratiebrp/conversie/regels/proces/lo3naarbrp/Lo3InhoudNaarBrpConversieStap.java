/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAutorisatie;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.AfnemersindicatieConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.GezagsverhoudingConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.HuwelijkOfGpConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.InschrijvingConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.KiesrechtConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.KindConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.NationaliteitConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.OuderConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.OverlijdenConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.PersoonConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.ReisdocumentConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.VerblijfplaatsConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.VerblijfsrechtConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie.AutorisatieConverteerder;
import org.springframework.stereotype.Component;

/**
 * Deze service levert de functionaliteit om LO3 inhoud naar BRP inhoud te converteren.
 */
@Component
@Requirement(Requirements.CHP001_LB10)
public class Lo3InhoudNaarBrpConversieStap {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final PersoonConverteerder persoonConverteerder;
    private final OuderConverteerder ouderConverteerder;
    private final NationaliteitConverteerder nationaliteitConverteerder;
    private final HuwelijkOfGpConverteerder huwelijkOfGpConverteerder;
    private final OverlijdenConverteerder overlijdenConverteerder;
    private final InschrijvingConverteerder inschrijvingConverteerder;
    private final VerblijfplaatsConverteerder verblijfplaatsConverteerder;
    private final KindConverteerder kindConverteerder;
    private final VerblijfsrechtConverteerder verblijfsrechtConverteerder;
    private final GezagsverhoudingConverteerder gezagsverhoudingConverteerder;
    private final ReisdocumentConverteerder reisdocumentConverteerder;
    private final KiesrechtConverteerder kiesrechtConverteerder;
    private final AutorisatieConverteerder autorisatieConverteerder;
    private final AfnemersindicatieConverteerder afnemersindicatieConverteerder;

    /**
     * constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     * @param autorisatieConverteerder AutorisatieConverteerder
     */
    @Inject
    public Lo3InhoudNaarBrpConversieStap(final Lo3AttribuutConverteerder lo3AttribuutConverteerder, final AutorisatieConverteerder autorisatieConverteerder) {
        persoonConverteerder = new PersoonConverteerder(lo3AttribuutConverteerder);
        ouderConverteerder = new OuderConverteerder(lo3AttribuutConverteerder);
        nationaliteitConverteerder = new NationaliteitConverteerder(lo3AttribuutConverteerder);
        huwelijkOfGpConverteerder = new HuwelijkOfGpConverteerder(lo3AttribuutConverteerder);
        overlijdenConverteerder = new OverlijdenConverteerder(lo3AttribuutConverteerder);
        inschrijvingConverteerder = new InschrijvingConverteerder(lo3AttribuutConverteerder);
        verblijfplaatsConverteerder = new VerblijfplaatsConverteerder(lo3AttribuutConverteerder);
        kindConverteerder = new KindConverteerder(lo3AttribuutConverteerder);
        verblijfsrechtConverteerder = new VerblijfsrechtConverteerder(lo3AttribuutConverteerder);
        gezagsverhoudingConverteerder = new GezagsverhoudingConverteerder(lo3AttribuutConverteerder);
        reisdocumentConverteerder = new ReisdocumentConverteerder(lo3AttribuutConverteerder);
        kiesrechtConverteerder = new KiesrechtConverteerder(lo3AttribuutConverteerder);
        this.autorisatieConverteerder = autorisatieConverteerder;
        afnemersindicatieConverteerder = new AfnemersindicatieConverteerder();
    }

    /**
     * Converteert de LO3 Persoonslijst in een Migratie persoonslijst.
     * @param lo3Persoonslijst de te converteren LO3 persoonslijst
     * @return een nieuw TussenPersoonslijst object
     */
    public final TussenPersoonslijst converteer(final Lo3Persoonslijst lo3Persoonslijst) {
        LOG.debug("converteer(lo3Persoonslijst={})", lo3Persoonslijst.getActueelAdministratienummer());
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();

        // Eerst huwelijk converteren, deze gegevens zijn namelijk nodig bij naamgebruik afleiding vij conversie cat01.
        LOG.debug("Converteer categorie 05: huwelijk");
        huwelijkOfGpConverteerder.converteer(
                lo3Persoonslijst.getHuwelijkOfGpStapels(),
                tussenPersoonslijstBuilder,
                lo3Persoonslijst.getOverlijdenStapel());

        LOG.debug("Converteer categorie 01: persoon");
        persoonConverteerder.converteer(lo3Persoonslijst, lo3Persoonslijst.isDummyPl(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 02/03: ouders (inclusief gedeelte 11: gezagsverhouding)");
        ouderConverteerder.converteer(
                lo3Persoonslijst.getOuder1Stapel(),
                lo3Persoonslijst.getOuder2Stapel(),
                lo3Persoonslijst.getGezagsverhoudingStapel(),
                lo3Persoonslijst.isDummyPl(),
                tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 04: nationaliteit");
        nationaliteitConverteerder.converteer(
                lo3Persoonslijst.getNationaliteitStapels(),
                lo3Persoonslijst.getInschrijvingStapel(),
                tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 06: overlijden");
        overlijdenConverteerder.converteer(lo3Persoonslijst.getOverlijdenStapel(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 07: inschrijving");
        if (lo3Persoonslijst.getPersoonStapel() != null && lo3Persoonslijst.getInschrijvingStapel() != null) {
            inschrijvingConverteerder.converteer(
                    lo3Persoonslijst.getVerblijfplaatsStapel(),
                    lo3Persoonslijst.getInschrijvingStapel(),
                    lo3Persoonslijst.isDummyPl(),
                    tussenPersoonslijstBuilder);
        }

        LOG.debug("Converteer categorie 08: verblijfplaats");
        if (lo3Persoonslijst.getVerblijfplaatsStapel() != null) {
            verblijfplaatsConverteerder.converteer(lo3Persoonslijst.getVerblijfplaatsStapel(), tussenPersoonslijstBuilder);
        }

        LOG.debug("Converteer categorie 09: kind");
        kindConverteerder.converteer(lo3Persoonslijst.getKindStapels(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 10: verblijfstitel");
        verblijfsrechtConverteerder.converteer(lo3Persoonslijst.getVerblijfstitelStapel(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 11: gezagsverhouding");
        gezagsverhoudingConverteerder.converteer(lo3Persoonslijst.getGezagsverhoudingStapel(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer categorie 12: reisdocument");
        reisdocumentConverteerder.converteer(lo3Persoonslijst.getReisdocumentStapels(), tussenPersoonslijstBuilder);

        LOG.debug("Converteer cateogrie 13: kiesrecht");
        kiesrechtConverteerder.converteer(lo3Persoonslijst.getKiesrechtStapel(), lo3Persoonslijst.getInschrijvingStapel(), tussenPersoonslijstBuilder);

        LOG.debug("Build (tussen)persoonslijst");
        return tussenPersoonslijstBuilder.build();
    }

    /**
     * Converteert de LO3 Autorisatie in een Migratie Autorisatie.
     * @param lo3Autorisatie de te converteren LO3 autorisatie
     * @return een nieuw TussenAutorisatie object
     */
    public final TussenAutorisatie converteer(final Lo3Autorisatie lo3Autorisatie) {
        LOG.debug("converteerLo3Autorisatie(lo3Autorisatie.afnemerindicatie={})", lo3Autorisatie.getAfnemersindicatie());
        final BrpPartijCode partij = autorisatieConverteerder.converteerPartij(lo3Autorisatie);
        final BrpBoolean indicatieVerstrekkingsbeperkingMogelijk =
                autorisatieConverteerder.converteerIndicatieVerstrekkingsbeperkingMogelijk(lo3Autorisatie);
        final List<TussenLeveringsautorisatie> leveringsautorisaties = autorisatieConverteerder.converteerAutorisatie(lo3Autorisatie);
        LOG.debug("BrpAutorisatie opbouwen compleet");
        return new TussenAutorisatie(partij, indicatieVerstrekkingsbeperkingMogelijk, leveringsautorisaties);
    }

    /**
     * Converteert de LO3 afnemersindicaties naar een migratie afnemersindicaties.
     * @param lo3Afnemersindicaties de te converteren afnemersindicaties
     * @return een nieuw TussenAfnemersindicaties object
     */
    public final TussenAfnemersindicaties converteer(final Lo3Afnemersindicatie lo3Afnemersindicaties) {
        LOG.debug("converteer(lo3Afnemersindicaties={})", lo3Afnemersindicaties);
        final String administratienummer = lo3Afnemersindicaties.getANummer();
        final List<TussenAfnemersindicatie> afnemersindicaties =
                afnemersindicatieConverteerder.converteer(lo3Afnemersindicaties.getAfnemersindicatieStapels());
        LOG.debug("TussenAfnemersindicaties converteren compleet");
        return new TussenAfnemersindicaties(administratienummer, afnemersindicaties);

    }
}
