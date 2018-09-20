/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocator;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.util.HistorieSetUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Huwelijk visitor.
 */
@Component
public final class GeregistreerdPartnerschapVisitor extends AbstractRelatieVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private GeregistreerdPartnerschapRelatieMutatieVerwerker relatieMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapVerbintenisMutatieVerwerker verbintenisMutatieVerwerker;

    @Autowired
    private GeregistreerdPartnerschapGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapGeslachtsaanduidingMutatieVerwerker geslachtsaanduidingMutatieVerwerker;

    @Autowired
    private HuwelijkGeslachtAdellijkeTitelPredikaatNabewerking geslachtAdellijkeTitelPredikaatNabewerking;

    /**
     * Verwerk het geregistreerd partnerschap.
     *
     * @param wijziging
     *            lo3 wijzigingen (output)
     * @param acties
     *            acties
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actie locator
     * @param relatie
     *            relatie van het huwelijk
     * @param partner
     *            partner van het huwelijk
     */
    public void verwerk(
        final Lo3Wijzigingen<Lo3HuwelijkOfGpInhoud> wijziging,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator,
        final GeregistreerdPartnerschapHisVolledig relatie,
        final PersoonHisVolledig partner)
    {
        final HisRelatieModel actueleEntiteit = bepaalActueel(relatie.getRelatieHistorie(), acties);
        final HisRelatieModel historieEntiteit = bepaalVerval(relatie.getRelatieHistorie(), acties);

        if (actueleEntiteit != null && historieEntiteit == null) {
            // Nieuwe relatie, alles van relatie en gerelateerde persoon opnemen als nieuw
            LOGGER.debug("Nieuw partnerschap: id={}; actieInhoud={}", relatie.getID(), actueleEntiteit.getID());

            relatieMutatieVerwerker.verwerk(
                wijziging,
                actueleEntiteit,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            verbintenisMutatieVerwerker.verwerk(
                wijziging,
                actueleEntiteit,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            geboorteMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(partner.getPersoonGeboorteHistorie()),
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(partner.getPersoonIdentificatienummersHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(partner.getPersoonSamengesteldeNaamHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            geslachtsaanduidingMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(partner.getPersoonGeslachtsaanduidingHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
        } else if (actueleEntiteit == null && historieEntiteit != null) {
            // Relatie vervallen, alles van relatie en gerelateerde persoon opnemen als oud
            LOGGER.debug("Vervallen partnerschap: id={}; actieVerval={}", relatie.getID(), historieEntiteit.getID());

            relatieMutatieVerwerker.verwerk(
                wijziging,
                null,
                historieEntiteit,
                historieEntiteit,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            verbintenisMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                historieEntiteit,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            geboorteMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                HistorieSetUtil.getActueleRecord(partner.getPersoonGeboorteHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                null,
                HistorieSetUtil.getActueleRecord(partner.getPersoonIdentificatienummersHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                null,
                HistorieSetUtil.getActueleRecord(partner.getPersoonSamengesteldeNaamHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            geslachtsaanduidingMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                null,
                HistorieSetUtil.getActueleRecord(partner.getPersoonGeslachtsaanduidingHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
        } else {
            LOGGER.debug("Verwerk partnerschap: id={}", relatie.getID());

            final List<Long> relatieObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(relatie);
            relatieMutatieVerwerker.verwerk(
                wijziging,
                relatie.getRelatieHistorie(),
                acties,
                relatieObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);
            verbintenisMutatieVerwerker.verwerk(
                wijziging,
                relatie.getRelatieHistorie(),
                acties,
                relatieObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);

            final List<Long> partnerObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(partner);
            geboorteMutatieVerwerker.verwerk(
                wijziging,
                partner.getPersoonGeboorteHistorie(),
                acties,
                partnerObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);
            identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                partner.getPersoonIdentificatienummersHistorie(),
                acties,
                partnerObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);
            samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                partner.getPersoonSamengesteldeNaamHistorie(),
                acties,
                partnerObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);
            geslachtsaanduidingMutatieVerwerker.verwerk(
                wijziging,
                partner.getPersoonGeslachtsaanduidingHistorie(),
                acties,
                partnerObjectSleutels,
                onderzoekMapper,
                actieHisVolledigLocator);
        }

        geslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(wijziging);
    }
}
