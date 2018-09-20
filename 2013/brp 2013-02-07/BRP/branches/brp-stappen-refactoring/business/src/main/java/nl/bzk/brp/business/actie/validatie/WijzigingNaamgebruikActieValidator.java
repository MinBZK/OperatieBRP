/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


/**
 * Validator die wijzigen naamgebuik acties valideert.
 * <p/>
 * Verplichte velden en groepen staan beschreven in BRP_GAG_042_HuwelijkofGeregistreerdPartnerschapRegistreren
 * Wijze van gebruik geslachtsnaam echtg./gereg. partner
 * Aanschrijving algoritmisch afgeleid
 * Geslachtsnaam aanschrijving
 * Datum aanvang geldigheid (afgeleid van Datum aanvang relatie)
 */
@Component
public class WijzigingNaamgebruikActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final Actie actie) {

        final List<Melding> meldingen = new ArrayList<Melding>();
        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        meldingen.addAll(valideerPersoon((PersoonBericht) persoon));
        return meldingen;
    }


    /**
     * Valideert de persoon van wie de aanschrijving wordt aangepast.
     *
     * @param persoon De te valideren persoon.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerPersoon(final PersoonBericht persoon) {
        // eigenlijk zijn ze allemaal al afgedwongen dood de xsd.
        final List<Melding> meldingen = new ArrayList<Melding>();
        PersoonIdentificatienummersGroepBericht persoonIdentificatienummers = persoon.getIdentificatienummers();
        ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, persoon,
            persoonIdentificatienummers, "identificatienummers");
        // TODO: bolie, moet omgezet worden naar technische sleutel
        if (persoonIdentificatienummers != null) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonIdentificatienummers,
                persoonIdentificatienummers.getBurgerservicenummer(), "burgerservicenummer");
        }
        // TODO voorlopig testen we niet de tijdstipLaatstGewijzigd. Het idee is dat de tijdstipLaatsteWijziging
        // overeen moet komen met wat in de database staat (icm. bsn)
        // ValidatieUtil.controleerVerplichtVeld(meldingen, persoon, persoon.getAfgeleidAdministratief(),
        // "afgeleidAdministratief");

        PersoonAanschrijvingGroepBericht persoonAanschrijving = persoon.getAanschrijving();
        ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, persoon, persoonAanschrijving, "aanschrijving");
        if (persoonAanschrijving != null) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonAanschrijving,
                persoonAanschrijving.getNaamgebruik(),
                "wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartnerCode");
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonAanschrijving,
                persoonAanschrijving.getIndicatieAanschrijvingAlgoritmischAfgeleid(), "indicatieAlgoritmischAfgeleid");
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonAanschrijving,
                persoonAanschrijving.getGeslachtsnaamAanschrijving(), "geslachtsnaam");
            if ((persoonAanschrijving.getGeslachtsnaamAanschrijving() != null)
                && StringUtils.isEmpty(persoonAanschrijving.getGeslachtsnaamAanschrijving().getWaarde()))
            {
                meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                    String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "geslachtsnaam"),
                    persoonAanschrijving, null));
            }
        }

        return meldingen;
    }
}
