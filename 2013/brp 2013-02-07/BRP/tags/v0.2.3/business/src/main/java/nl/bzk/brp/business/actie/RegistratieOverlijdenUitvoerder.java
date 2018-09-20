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
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
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

//    @Inject
//    private ReferentieDataRepository referentieDataRepository;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        //TODO implementeer validatie
        return new ArrayList<Melding>();
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        // Haal de persoon op
        PersoonModel persoonModel =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        if (persoonModel == null) {
            meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.ALG0001,
                "Persoon voor registratie overlijden niet gevonden. BSN: "
                    + persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                    , (Identificeerbaar) persoon.getIdentificatienummers(), "burgerservicenummer"));
        } else {
            try {
                //Persisteer actie
                final ActieModel persistentActie = persisteerActie(actie, berichtContext);

                persoonRepository.werkbijOverlijden(persoonModel,
                        persoon.getOverlijden(),
                        persoon.getOpschorting(),
                        persistentActie,
                        persoon.getOverlijden().getDatumOverlijden());

                // Administratie spul: houdt bij de hoofdpersoon
                berichtContext.voegHoofdPersoonToe(persoon);
            } catch (OnbekendeReferentieExceptie ore) {
                Melding melding =
                    new Melding(Soortmelding.FOUT, MeldingCode.REF0001, String.format(
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
