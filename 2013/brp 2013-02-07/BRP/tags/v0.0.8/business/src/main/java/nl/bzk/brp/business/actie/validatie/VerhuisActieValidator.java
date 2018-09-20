/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.springframework.stereotype.Component;

/**
 * Validator die verhuis acties valideert.
 */
@Component
public class VerhuisActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final BRPActie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        meldingen.addAll(valideerPersoon(persoon));
        if (persoon.getAdressen() == null || persoon.getAdressen().isEmpty()) {
            meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002, "adres"));
        } else {
            for (final PersoonAdres persoonAdres : persoon.getAdressen()) {
                meldingen.addAll(valideerVerhuisAdres(persoonAdres));
            }
        }
        return meldingen;
    }

    /**
     * Valideert het adres waarnaar verhuist word.
     *
     * @param persoonAdres Het te valideren adres.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerVerhuisAdres(final PersoonAdres persoonAdres) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getSoort(), "soortAdres");
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getDatumAanvangAdreshouding(),
                "datumAanvangAdresHouding");
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getGemeente(), "gemeenteCode");
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getNaamOpenbareRuimte(), "naamOpenbareRuimte");
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getHuisnummer(), "huisnummer");
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getLand(), "land");
        return meldingen;
    }

    /**
     * Valideert de persoon in de verhuis actie.
     *
     * @param persoon De te valideren persoon.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerPersoon(final Persoon persoon) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        PersoonIdentificatienummers persoonIdentificatienummers = persoon.getIdentificatienummers();
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers, "Identificatienummers");
        if (persoonIdentificatienummers != null) {
            ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers.getBurgerservicenummer(),
                    "Burgerservicenummer");
        }
        return meldingen;
    }
}
