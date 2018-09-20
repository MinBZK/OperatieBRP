/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import org.springframework.stereotype.Component;

/**
 * Actie uitvoerder voor verhuizing.
 *
 */
@Component
public class VerhuizingUitvoerder implements ActieUitvoerder {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public List<Melding> voerUit(final BRPActie actie) {
        //Verhuizing actie heeft maar een persoon in de verhuizing
        final Persoon pers = (Persoon) actie.getRootObjecten().get(0);
        //In de verhuizing actie zit maar een adres voor de persoon
        final PersoonAdres nieuwAdres = pers.getAdressen().iterator().next();
        // Adres dient wel van persoon te zijn voorzien.
        nieuwAdres.setPersoon(pers);

        persoonAdresRepository.opslaanNieuwPersoonAdres(nieuwAdres, actie.getDatumAanvangGeldigheid(), null);

        return null;
    }

}
