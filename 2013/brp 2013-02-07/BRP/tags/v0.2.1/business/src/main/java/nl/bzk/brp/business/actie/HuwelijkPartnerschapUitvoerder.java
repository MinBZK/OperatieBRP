/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.HuwelijkPartnerschapActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.springframework.stereotype.Component;


/** Actie uitvoerder voor huwelijk en partnerschap. */
@Component
public class HuwelijkPartnerschapUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Inject
    private HuwelijkPartnerschapActieValidator huwelijkPartnerschapActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        return huwelijkPartnerschapActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext) {
        List<Melding> meldingen = null;

        final Relatie huwelijk = haalRelatieUitActie(actie);

        // Opslag van de actie
        ActieModel persistentActie = persisteerActie(actie, berichtContext);

        RelatieModel relatie = new RelatieModel(huwelijk);

        for (Betrokkenheid betrokkenheid : ((RelatieBericht) huwelijk).getPartnerBetrokkenheden()) {
            Burgerservicenummer bsn = betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer();

            PersoonModel partner = persoonRepository.findByBurgerservicenummer(bsn);

            if (partner == null) {
                meldingen =
                    Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, String.format(
                            "Partner (bsn: %s) is niet bekend", bsn), (Identificeerbaar) betrokkenheid,
                            "burgerservicenummer"));
                break;
            }

            relatie.getBetrokkenheden().add(new BetrokkenheidModel(betrokkenheid, partner, relatie));

            // Werk de bericht context bij met de bijgehouden personen.
            berichtContext.voegHoofdPersoonToe(betrokkenheid.getBetrokkene());
        }

        if (relatie.getBetrokkenheden().size() > 0) {
            relatieRepository.opslaanNieuweRelatie(relatie, persistentActie, actie.getDatumAanvangGeldigheid());
        }

        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        if (actie == null) {
            throw new IllegalArgumentException("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
        }

        List<Melding> meldingen;
        final Relatie huwelijk = haalRelatieUitActie(actie);

        if (huwelijk == null) {
            meldingen =
                Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002, MeldingCode.ALG0002
                        .getOmschrijving() + ": Geen relatie gevonden"));
        } else {
            meldingen = new ArrayList<Melding>();
            if (huwelijk.getBetrokkenheden() == null) {
                meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                        "Er zijn geen betrokkenheden (partner) opgenomen terwijl deze wel verwacht worden."));
            } else {
                for (Betrokkenheid betrokkenheid : huwelijk.getBetrokkenheden()) {
                    if (AttribuutTypeUtil.isBlank(betrokkenheid.getBetrokkene().getIdentificatienummers()
                            .getBurgerservicenummer()))
                    {
                        // TODO voor huidige release 0.1.7 is de scope alleen ingescheven personen
                        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                                "Personen zonder BSN is op het moment nog niet ondersteund"));
                    }
                }
            }
        }

        return meldingen;
    }

    /**
     * Haalt de (eerste) relatie uit de actie en retourneert deze. Indien er geen Relatie als root object in de actie
     * is opgenomen dan zal <code>null</code> worden geretourneerd.
     *
     * @param actie de actie waaruit de relatie zal worden opgehaald.
     * @return de relatie die als root object in de actie is opgenomen of <code>null</code> indien die er niet is.
     */
    private Relatie haalRelatieUitActie(final Actie actie) {
        Relatie relatie = null;
        if (actie.getRootObjecten() != null) {
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof Relatie) {
                    relatie = (Relatie) rootObject;
                }
            }
        }
        return relatie;
    }

}
