/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/** Actie uitvoerder voor registratie overlijden. */
@Component
public class RegistratieOverlijdenUitvoerder extends AbstractActieUitvoerder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistratieOverlijdenUitvoerder.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

//    @Inject
//    private ReferentieDataRepository referentieDataRepository;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        //TODO implementeer validatie
        return new ArrayList<Melding>();
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext, AdministratieveHandelingModel administratieveHandelingModel) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        // Haal de persoon op
        PersoonModel persoonModel =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        if (persoonModel == null) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001,
                "Persoon voor registratie overlijden niet gevonden. BSN: "
                    + persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                , (Identificeerbaar) persoon.getIdentificatienummers(), "burgerservicenummer"));
        } else {
            try {
                //Persisteer actie
                final ActieModel persistentActie = persisteerActie(actie, berichtContext, administratieveHandelingModel);

                persoonRepository.werkbijOverlijden(persoonModel,
                    persoon.getOverlijden(),
                    persoon.getOpschorting(),
                    persistentActie,
                    persoon.getOverlijden().getDatumOverlijden());

                // We persisteren op het moment alleen veranderingen in relaties bij overlijden.
                // Dit gaat goed omdat het bericht verrijkt is met alleen HGP betrokkenheden, dus worden
                // er alleen HGP
                if (null != persoon.getBetrokkenheden() && persoon.getBetrokkenheden().size() != 0) {
                    for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                        if (betrokkenheid.getRelatie() != null) {
                            HuwelijkGeregistreerdPartnerschapBericht relatieBericht = (HuwelijkGeregistreerdPartnerschapBericht) betrokkenheid.getRelatie();
                            if (relatieBericht.getModelID() != null) {
                                // we behandelen op het moment alleen relaties met bekenden id
                                RelatieModel relatieModel = relatieRepository.vindBijId(relatieBericht.getModelID());
                                HuwelijkGeregistreerdPartnerschapStandaardGroepModel relatieStandaardGroepModel =
                                    new HuwelijkGeregistreerdPartnerschapStandaardGroepModel(relatieBericht.getStandaard());
                                relatieRepository.opslaanVeranderdeRelatieStandaardGegevens(relatieModel,
                                    relatieStandaardGroepModel, persistentActie);
                            } else {
                                LOGGER.warn("Relatie met onbekende id in bericht.");
                            }
                        }
                    }
                }
                // Administratie spul: houdt bij de hoofdpersoon
                berichtContext.voegHoofdPersoonToe(persoon);
            } catch (OnbekendeReferentieExceptie ore) {
                Melding melding =
                    new Melding(SoortMelding.FOUT, MeldingCode.REF0001, String.format(
                        "%s. Foutieve code/referentie '%s' voor veld '%s'.",
                        MeldingCode.REF0001.getOmschrijving(),
                        ore.getReferentieWaarde(), ore.getReferentieVeldNaam()));
                meldingen.add(melding);
            }
        }
        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        if (actie == null) {
            LOGGER.error("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
            throw new IllegalArgumentException("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
        }
        //Geen voorbereidende stappen
        return new ArrayList<Melding>();
    }
}
