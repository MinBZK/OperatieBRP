/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.WijzigingNaamgebruikActieValidator;
import nl.bzk.brp.business.bedrijfsregels.impl.aanschrijving.AanschrijvingGroepAfleiding;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;


/** ActieUitvoerder voor wijzigen naam gebruik; dit is ee optionele actie bij registratie Huwelijk/Partnerschap. */
@Component
public class WijzigNaamgebruikUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Inject
    private WijzigingNaamgebruikActieValidator wijzigingNaamgebruikActieValidator;

//    @Inject
//    private AanschrijvingGroepAfleiding             aanschrijvingGroepAfleiding;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        // bolie: kan nu niet de validator aanroepen, de afleiding moet eerst gebeuren.
        // return wijzigingNaamgebruikActieValidator.valideerActie(actie);
        return null;
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext, AdministratieveHandelingModel administratieveHandelingModel) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);

        // Haal de persoon op
        PersoonModel persoonModel =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        if (persoonModel == null) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001,
                "Persoon voor wijzig naam gebruik niet gevonden. BSN: "
                    + persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                , persoon.getIdentificatienummers(), "burgerservicenummer"));
        } else {
            if (persoon.getAanschrijving() != null && meldingen.isEmpty()) {
                // TODO tijdelijke oplossing voor actie en aflopen volgorde.
                // Omdat de afleidingsregel in eerdere stadium wordt aangeroepen (voordat het huwelijk daadwerkelijk
                // wordt opgeslagen), moeten we dit uitstellen tot op dit moment.

                //Persisteer actie
                final ActieModel persistentActie = persisteerActie(actie, berichtContext, administratieveHandelingModel);
                try {
                    persoonRepository.werkbijNaamGebruik(persoonModel, persoon.getAanschrijving(),
                        persistentActie, actie.getDatumAanvangGeldigheid());
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
        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        // TODO: bolie, de afleiding en validatie verplaatst hiernaartoe, omdat als het eerder gebeurt,
        // het huwelijk nog niet is opgeslagen.
        List<Melding> meldingen = new ArrayList<Melding>();
        final PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);
        // Haal de persoon op
        PersoonModel persoonModel =
            persoonRepository.findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

        AanschrijvingGroepAfleiding aanschrijvingGroepAfleiding = new AanschrijvingGroepAfleiding();
        aanschrijvingGroepAfleiding.setPersoonRepository(persoonRepository);
        aanschrijvingGroepAfleiding.setRelatieRepository(relatieRepository);
        List<Melding> foutMeldingen = aanschrijvingGroepAfleiding.executeer(persoonModel, persoon, actie);
        if (CollectionUtils.isNotEmpty(foutMeldingen)) {
            meldingen.addAll(foutMeldingen);
        } else {
            // aangepast: ALS er iets fout met het afleiden, dan moeten we ook niet valideren.
            List<Melding> validatieFouten = wijzigingNaamgebruikActieValidator.valideerActie(actie);
            meldingen.addAll(validatieFouten);
        }

        return meldingen;
    }
}
