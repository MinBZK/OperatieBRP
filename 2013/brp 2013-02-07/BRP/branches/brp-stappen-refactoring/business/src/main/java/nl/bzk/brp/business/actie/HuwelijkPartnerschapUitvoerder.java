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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Partner;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
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
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext, final
                               AdministratieveHandelingModel administratieveHandelingModel) {
        List<Melding> meldingen = null;

        final HuwelijkBericht huwelijk = haalRelatieUitActie(actie);

        // Opslag van de actie
        ActieModel persistentActie = persisteerActie(actie, berichtContext, administratieveHandelingModel);

        HuwelijkModel relatie = new HuwelijkModel(huwelijk);

        for (Betrokkenheid betrokkenheid : ((HuwelijkGeregistreerdPartnerschapBericht) huwelijk).getBetrokkenheden()) {
            Burgerservicenummer bsn = betrokkenheid.getPersoon().getIdentificatienummers().getBurgerservicenummer();

            PersoonModel partner = persoonRepository.findByBurgerservicenummer(bsn);

            if (partner == null) {
                meldingen =
                    Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.REF0001, String.format(
                        "Partner (bsn: %s) is niet bekend", bsn), (Identificeerbaar) betrokkenheid,
                        "burgerservicenummer"));
                break;
            }

            relatie.getBetrokkenheden().add(new PartnerModel((Partner) betrokkenheid, relatie, partner));

            // Werk de bericht context bij met de bijgehouden personen.
            berichtContext.voegHoofdPersoonToe(betrokkenheid.getPersoon());
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
                Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002, MeldingCode.ALG0002
                                                                                             .getOmschrijving()
                    + ": Geen relatie gevonden"));
        } else {
            meldingen = new ArrayList<Melding>();
            if (huwelijk.getBetrokkenheden() == null) {
                meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                    "Er zijn geen betrokkenheden (partner) opgenomen terwijl deze wel verwacht worden."));
            } else {
                for (Betrokkenheid betrokkenheid : huwelijk.getBetrokkenheden()) {
                    if (AttribuutTypeUtil.isBlank(betrokkenheid.getPersoon().getIdentificatienummers()
                                                               .getBurgerservicenummer()))
                    {
                        // TODO voor huidige release 0.1.7 is de scope alleen ingescheven personen
                        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
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
    private HuwelijkBericht haalRelatieUitActie(final Actie actie) {
        HuwelijkBericht relatie = null;
        if (actie.getRootObjecten() != null) {
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof HuwelijkBericht) {
                    relatie = (HuwelijkBericht) rootObject;
                }
            }
        }
        return relatie;
    }

}
