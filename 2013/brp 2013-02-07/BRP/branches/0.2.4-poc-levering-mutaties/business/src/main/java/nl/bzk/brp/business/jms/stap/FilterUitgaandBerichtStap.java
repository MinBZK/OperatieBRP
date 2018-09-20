/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.jms.service.BerichtFilterService;
import nl.bzk.brp.business.levering.Applicatie;
import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.business.levering.Ontvanger;
import nl.bzk.brp.business.levering.Organisatie;
import nl.bzk.brp.dataaccess.repository.PartijRepository;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.levering.Abonnement;
import nl.bzk.brp.model.levering.AbonnementGegevenselement;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class FilterUitgaandBerichtStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger  LOGGER = LoggerFactory.getLogger(FilterUitgaandBerichtStap.class);

    @Inject
    private PartijRepository     partijRepository;

    @Inject
    private BerichtFilterService berichtFilterService;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {
        List<Short> afnemerIds = context.getPartijIds();
        Map<Short, LEVLeveringBijgehoudenPersoonLv> uitBerichten =
            new HashMap<Short, LEVLeveringBijgehoudenPersoonLv>(afnemerIds.size());

        // pak bericht
        List<LEVLeveringBijgehoudenPersoonLv> maxBerichten = context.getMaxBerichten();

        if (maxBerichten != null) {
            for (LEVLeveringBijgehoudenPersoonLv maxBericht : maxBerichten) {
                if (maxBericht != null) {
                    for (Short id : afnemerIds) {
                        // vind partij
                        Partij afnemer = partijRepository.findById(id);
                        Abonnement abonnement = berichtFilterService.verkrijgRandomAbonnement();

                        LEVLeveringBijgehoudenPersoonLv bericht =
                            (LEVLeveringBijgehoudenPersoonLv) SerializationUtils.clone(maxBericht);

                        // pas bericht aan voor afnemer
                        Ontvanger ontvanger = new Ontvanger();
                        bericht.getStuurgegevens().setOntvanger(ontvanger);

                        Organisatie organisatie = new Organisatie();
                        organisatie.setOrganisatie(afnemer.getNaam().getWaarde());
                        ontvanger.setOrganisatie(organisatie);

                        Applicatie applicatie = new Applicatie();
                        applicatie.setApplicatie("Afnemersysteem X");
                        ontvanger.setApplicatie(applicatie);

                        // filter bericht
                        // TODO: implementeren filtering
                        LEVLeveringBijgehoudenPersoonLv gefilterdBericht =
                            berichtFilterService.filterBericht(bericht, abonnement);

                        // bewaar bericht
                        uitBerichten.put(id, gefilterdBericht);

                        // Log om gefilterd bericht te tonen
                        List<String> gegevenselementen = new ArrayList<String>();
                        for (AbonnementGegevenselement gegevenselement : abonnement.getAbonnementGegevenselementen()) {
                            gegevenselementen.add(gegevenselement.getGegevenselement());
                        }
                        LOGGER.debug("GEFILTERDE XML" + berichtFilterService.verkrijgXmlVanBericht(gefilterdBericht)
                            + "\n Gegevenselementen waartoe partij geen toestemming had: " + gegevenselementen);

                    }
                }
            }
        }

        context.setUitBerichten(uitBerichten);

        // max berichten zijn niet meer nodig
        context.setMaxBerichten(null);

        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }

    private List<Groep> getGroepen(final Partij partij) {
        return Collections.emptyList();
    }
}
