/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.springframework.stereotype.Component;


/**
 * ActieUitvoerder voor de registratie nationaliteit.
 */
@Component
public class RegistratieNationaliteitUitvoerder extends AbstractActieUitvoerder {

//    private static final Logger            LOGGER = LoggerFactory.getLogger(RegistratieNationaliteitUitvoerder.class);

    @Inject
    private PersoonRepository              persoonRepository;

    @Inject
    private ReferentieDataRepository       referentieDataRepository;

    @Inject
    private PersoonNationaliteitRepository persoonNationaliteitRepository;

    @Override
    List<Melding> valideerActieGegevens(final BRPActie actie) {
        // TODO
        return null;
    }

    @Override
    List<Melding> verwerkActie(final BRPActie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        // Haal de persoon op
        PersistentPersoon ppersoon =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        if (ppersoon == null) {
            meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001,
                    "Persoon voor registratie nationaliteit niet gevonden. BSN: "
                        + persoon.getIdentificatienummers().getBurgerservicenummer()));
        } else {
            if (persoon.getNationaliteiten() != null) {
                for (PersoonNationaliteit persoonNationaliteit : persoon.getNationaliteiten()) {
                    try {
                        // Haal de nationaliteit op
                        String code = persoonNationaliteit.getNationaliteit().getCode();
                        final Nationaliteit nationaliteit = referentieDataRepository.vindNationaliteitOpCode(code);
                        // Knoop de persoon aan de nationaliteit en persisteer
                        final PersistentPersoonNationaliteit ppersoonNationaliteit =
                                new PersistentPersoonNationaliteit();
                        ppersoonNationaliteit.setPers(ppersoon);
                        ppersoonNationaliteit.setNationaliteit(nationaliteit);
                        ppersoonNationaliteit.setNationaliteitStatusHistorie(StatusHistorie.A);
                        // ppersoonNationaliteit.setRedenVerkregenNLNationaliteit(); @TODO
                        // ppersoonNationaliteit.setRedenVerliesNLNationaliteit(); @TODO

                        PersistentPersoonNationaliteit persNation =
                                persoonNationaliteitRepository.save(ppersoonNationaliteit);
                        persoonNationaliteitRepository.persisteerHistorie(persNation,
                                actie.getDatumAanvangGeldigheid(), null, new Date());
                    } catch (OnbekendeReferentieExceptie ore) {
                        Melding melding =
                                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, String.format(
                                        "%s. Foutieve code/referentie '%s' voor veld '%s'.",
                                        MeldingCode.REF0001.getOmschrijving(),
                                        ore.getReferentieWaarde(), ore.getReferentieVeldNaam()));
                        meldingen.add(melding);
                    }
                }
            }
        }
        return meldingen;
    }
}
