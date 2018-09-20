/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.springframework.stereotype.Component;

/** Actie uitvoerder voor verhuizing. */
@Component
public class InschrijvingGeboorteUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private GeboorteActieValidator geboorteActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final BRPActie actie) {
        return geboorteActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final BRPActie actie) {
        if (actie == null) {
            throw new IllegalArgumentException("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
        }

        List<Melding> meldingen;
        Persoon nieuwIngeschrevene = haalPersoonUitActie(actie);

        if (nieuwIngeschrevene == null) {
            meldingen = Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                MeldingCode.ALG0002.getOmschrijving() + ": Geen persoon gevonden"));
        } else {
            // Zet soort persoon automatisch op "INGESCHREVENE"
            nieuwIngeschrevene.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
            meldingen = voerInschrijvingGeboorteDoor(nieuwIngeschrevene, actie);
        }

        return meldingen;
    }

    /**
     * Haalt de (eerste) persoon uit de actie en retourneert deze. Indien er geen Persoon als root object in de actie
     * is opgenomen dan zal <code>null</code> worden geretourneerd.
     *
     * @param actie de actie waaruit de persoon zal worden opgehaald.
     * @return de persoon die als root object in de actie is opgenomen of <code>null</code> indien die er niet is.
     */
    private Persoon haalPersoonUitActie(final BRPActie actie) {
        Persoon persoon = null;
        if (actie.getRootObjecten() != null) {
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof Persoon) {
                    persoon = (Persoon) rootObject;
                }
            }
        }
        return persoon;
    }

    /**
     * Voert de werkelijke inschrijving door geboorte door middels het opslaan van de persoon.
     *
     * @param nieuwIngeschrevene de nieuwe ingeschrevene die moet worden ingeschreven.
     * @param actie de actie waarbinnen de inschrijving plaatsvindt.
     * @return eventueel opgetreden meldingen/fouten, welke <code>null</code> kan zijn indien er geen meldingen/fouten
     *         zijn.
     */
    private List<Melding> voerInschrijvingGeboorteDoor(final Persoon nieuwIngeschrevene, final BRPActie actie) {
        List<Melding> meldingen;
        try {
            persoonRepository.opslaanNieuwPersoon(nieuwIngeschrevene, actie.getDatumAanvangGeldigheid());
            meldingen = null;
        } catch (ObjectReedsBestaandExceptie e) {
            meldingen = Arrays.asList(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.REF0010));
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                    String.format("%s. Foutieve code/referentie '%s' voor veld '%s'.",
                            MeldingCode.REF0001.getOmschrijving(), e.getReferentieWaarde(), e.getReferentieVeldNaam()));
            meldingen = Arrays.asList(melding);
        }

        return meldingen;
    }

}
