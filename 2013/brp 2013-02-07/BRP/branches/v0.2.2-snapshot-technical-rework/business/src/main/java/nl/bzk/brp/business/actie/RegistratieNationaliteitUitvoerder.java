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
import nl.bzk.brp.dataaccess.repository.PersoonNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.springframework.stereotype.Component;


/** ActieUitvoerder voor de registratie nationaliteit. */
@Component
public class RegistratieNationaliteitUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Inject
    private PersoonNationaliteitRepository persoonNatRepository;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        // TODO
        return null;
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        // Haal de persoon op
        PersoonModel persoonModel =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        if (persoonModel == null) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001,
                "Persoon voor registratie nationaliteit niet gevonden. BSN: "
                    + persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                    , (Identificeerbaar) persoon.getIdentificatienummers(), "burgerservicenummer"));
        } else {
            if (persoon.getNationaliteiten() != null) {
                //Persisteer actie
                final ActieModel persistentActie = persisteerActie(actie, berichtContext);
                for (PersoonNationaliteit persoonNationaliteit : persoon.getNationaliteiten()) {
                    try {
                        // Haal de nationaliteit op
                        final Nationaliteit nationaliteit = referentieDataRepository.vindNationaliteitOpCode(
                                persoonNationaliteit.getNationaliteit().getNationaliteitcode()
                        );
                        ((PersoonNationaliteitBericht) persoonNationaliteit).setNationaliteit(nationaliteit);

                        // Knoop de persoon aan de nationaliteit en persisteer
                        final PersoonNationaliteitModel persNation =
                                new PersoonNationaliteitModel(persoonNationaliteit, persoonModel);

                        persoonModel = persoonNatRepository.voegNationaliteit(persoonModel, persNation,
                                persistentActie,
                                actie.getDatumAanvangGeldigheid());

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
            }
        }
        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        //Geen voorbereidende stappen
        return null;
    }
}
