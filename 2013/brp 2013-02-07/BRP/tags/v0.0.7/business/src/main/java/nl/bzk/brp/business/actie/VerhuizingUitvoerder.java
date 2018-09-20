/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.springframework.stereotype.Component;

/**
 * Actie uitvoerder voor verhuizing.
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

        List<Melding> meldingen = null;
        try {
            persoonAdresRepository.opslaanNieuwPersoonAdres(nieuwAdres, actie.getDatumAanvangGeldigheid(), null);
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                    String.format("%s. Foutieve code/referentie '%s' voor veld '%s'.",
                            MeldingCode.REF0001.getOmschrijving(), e.getReferentieWaarde(), e.getReferentieVeldNaam()));
            meldingen = Arrays.asList(melding);
        }

        return meldingen;
    }

}
