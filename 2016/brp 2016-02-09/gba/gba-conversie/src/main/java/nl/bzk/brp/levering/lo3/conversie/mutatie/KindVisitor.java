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
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Kind visitor.
 */
@Component
public final class KindVisitor extends AbstractRelatieVisitor {

    @Autowired
    private KindOuderschapMutatieVerwerker kindOuderschapMutatieVerwerker;
    @Autowired
    private KindGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private KindIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private KindSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;

    /**
     * Verwerk wijzigingen in het kind.
     *
     * @param wijziging
     *            lo3 wijzigingen (output)
     * @param acties
     *            acties
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actie locator
     * @param ouderBetrokkenheid
     *            (mijn) ouder betrokkenheid
     * @param relatie
     *            de familierechtelijke betrekking relatie
     * @param gerelateerdeBetrokkenheid
     *            gerelateerde (kind) betrokkenheid
     */
    public void verwerk(
        final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator,
        final OuderHisVolledig ouderBetrokkenheid,
        final RelatieHisVolledig relatie,
        final KindHisVolledig gerelateerdeBetrokkenheid)
    {
        final FormeelVerantwoordbaar<ActieModel> actueleEntiteit = bepaalActueel(ouderBetrokkenheid.getBetrokkenheidHistorie(), acties);
        final FormeelVerantwoordbaar<ActieModel> historieEntiteit = bepaalVerval(ouderBetrokkenheid.getBetrokkenheidHistorie(), acties);

        if (historieEntiteit != null) {
            // 'Mijn' ouder betrokkenheid is vervallen, alle actuele groepen van relatie en gerelateerde persoon als
            // historisch opnemen
            kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                null,
                HistorieSetUtil.getActueleRecord(ouderBetrokkenheid.getOuderOuderschapHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            geboorteMutatieVerwerker.verwerk(
                wijziging,
                null,
                null,
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie()),
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
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie()),
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
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
        } else if (actueleEntiteit != null) {
            // 'Mijn' ouder betrokkenheid is 'nieuw', alle actuele groepen van relatie en gerelateerde persoon als
            // actueel opnemen
            kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(ouderBetrokkenheid.getOuderOuderschapHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            geboorteMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie()),
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie()),
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
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

        } else if (gerelateerdeBetrokkenheid.getPersoon() != null) {
            // De betrokkenheid is niet geraakt

            // Ouderschap verwerken
            kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                ouderBetrokkenheid.getOuderOuderschapHistorie(),
                acties,
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            // Persoonsgegevens verwerken is afhankelijk van wat er is gebeurd met het ouderschap
            final HisOuderOuderschapModel actueleOuderschapEntiteit = bepaalActueel(ouderBetrokkenheid.getOuderOuderschapHistorie(), acties);
            final HisOuderOuderschapModel beeindigingOuderschapEntiteit = bepaalBeeindiging(ouderBetrokkenheid.getOuderOuderschapHistorie(), acties);
            final HisOuderOuderschapModel historieOuderschapEntiteit = bepaalVerval(ouderBetrokkenheid.getOuderOuderschapHistorie(), acties);

            if (beeindigingOuderschapEntiteit != null || historieOuderschapEntiteit != null) {
                // 'Mijn ouderschap' is beeindigd of vervallen, alle actuele groepen van relatie en gerelateerde
                // persoon als historisch opnemen
                geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    null,
                    null,
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie()),
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
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie()),
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
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie()),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            } else if (actueleOuderschapEntiteit != null) {
                // 'Mijn' ouderschap is nieuw, alle actuele groepen van relatie en gerelateerde persoon als
                // actueel opnemen
                geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie()),
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                identificatienummersMutatieVerwerker.verwerk(
                    wijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie()),
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
                    HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie()),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            } else {
                // Ouderschap is niet geraakt
                final PersoonHisVolledig kind = gerelateerdeBetrokkenheid.getPersoon();
                final List<Long> kindObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(kind);

                geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    kind.getPersoonGeboorteHistorie(),
                    acties,
                    kindObjectSleutels,
                    onderzoekMapper,
                    actieHisVolledigLocator);
                identificatienummersMutatieVerwerker.verwerk(
                    wijziging,
                    kind.getPersoonIdentificatienummersHistorie(),
                    acties,
                    kindObjectSleutels,
                    onderzoekMapper,
                    actieHisVolledigLocator);
                samengesteldeNaamMutatieVerwerker.verwerk(
                    wijziging,
                    kind.getPersoonSamengesteldeNaamHistorie(),
                    acties,
                    kindObjectSleutels,
                    onderzoekMapper,
                    actieHisVolledigLocator);
            }

        }
    }

}
