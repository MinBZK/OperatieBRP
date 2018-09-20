/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.CorrectieAdresNLActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import org.springframework.stereotype.Component;


/** Actie uitvoerdoer voor adres correcties binnen Nederland. */
@Component
public class CorrectieAdresNLUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private CorrectieAdresNLActieValidator correctieAdresNLActieValidator;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        return correctieAdresNLActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext, final
                               AdministratieveHandelingModel administratieveHandelingModel) {
        List<Melding> meldingen = null;
        final Persoon pers = (Persoon) actie.getRootObjecten().get(0);
        final PersoonAdres nieuwAdres = pers.getAdressen().iterator().next();

        // Opslag van de actie
        ActieModel persistentActie = persisteerActie(actie, berichtContext, administratieveHandelingModel);

        // TODO: bolie, moet omgezet worden naar technische sleutel
        // Haal persoon op
        final PersoonModel pPersoon =
            persoonRepository.findByBurgerservicenummer(pers.getIdentificatienummers().getBurgerservicenummer());

        // Voer correctie uit
        persoonAdresRepository.voerCorrectieAdresUit(new PersoonAdresModel(nieuwAdres, pPersoon), persistentActie,
            actie.getDatumAanvangGeldigheid(), actie.getDatumEindeGeldigheid());

        berichtContext.voegHoofdPersoonToe(pers);

        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        return null;
    }
}
