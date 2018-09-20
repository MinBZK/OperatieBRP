/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.springframework.stereotype.Component;

/**
 * Actie validator voor correctie Adres binnen NL.
 */
@Component
public class CorrectieAdresNLActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);

        ValidatieUtil.controleerVerplichteGroepInObjectType(
                meldingen, persoon, persoon.getIdentificatienummers(), "identificatienummers"
        );

        if (persoon.getIdentificatienummers() != null) {
            ValidatieUtil.controleerVerplichteVeldInGroep(
                    meldingen, persoon.getIdentificatienummers(),
                    persoon.getIdentificatienummers().getBurgerservicenummer(), "burgerservicenummer");
        }

        if (persoon.getAdressen() == null || persoon.getAdressen().isEmpty()) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "adres")));
        } else {
            for (PersoonAdresBericht adresBericht : persoon.getAdressen()) {
                valideerAdres(meldingen, adresBericht);
            }
        }
        return meldingen;
    }

    /**
     * Valideert het adres.
     * @param meldingen Lijst waaraan meldingen moeten worden toegevoegd.
     * @param adresBericht Het te valideren adres.
     */
    private void valideerAdres(final List<Melding> meldingen, final PersoonAdresBericht adresBericht) {
        PersoonAdresStandaardGroepBericht adresStandaardGroep = adresBericht.getGegevens();
        ValidatieUtil.controleerVerplichteVeldInGroep(
                meldingen, adresStandaardGroep, adresStandaardGroep.getSoort(), "soort adres");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen,
                adresStandaardGroep, adresStandaardGroep.getRedenWijziging(), "Reden wijziging adres");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen,
                adresStandaardGroep, adresStandaardGroep.getDatumAanvangAdreshouding(), "Datum aanvang adreshouding");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen,
                adresStandaardGroep, adresStandaardGroep.getGemeente(), "Gemeente");

        //Naam openbare ruimte en huisnummer zijn verplicht OF locatie omschrijving
        int aantalmeldingen = meldingen.size();
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, adresStandaardGroep,
                adresStandaardGroep.getNaamOpenbareRuimte(), "Naam openbare ruimte");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, adresStandaardGroep,
                adresStandaardGroep.getHuisnummer(), "Huisnummer");

        //Let op de conditie hieronder bij het toevoegen van controles!!!
        if (meldingen.size() - aantalmeldingen == 2) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen,
                    adresStandaardGroep, adresStandaardGroep.getLocatieOmschrijving(), "Locatie omschrijving");
        }

        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen,
                adresStandaardGroep, adresStandaardGroep.getLand(), "Land");

    }
}
