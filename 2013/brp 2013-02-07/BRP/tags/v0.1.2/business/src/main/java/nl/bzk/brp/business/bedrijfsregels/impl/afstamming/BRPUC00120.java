/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * Implementatie van bedrijfsregel BRPUC00120;
 * De geboortedatum van nieuwgeborene ligt na de datum aanvang adreshouding van de adresgevende ouder.
 *
 * @brp.bedrijfsregel BRPUC00120
 */
public class BRPUC00120 implements BedrijfsRegel<PersistentRelatie, Relatie> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public String getCode() {
        return "BRPUC00120";
    }

    @Override
    public Melding executeer(final PersistentRelatie huidigeSituatie, final Relatie nieuweSituatie,
        final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;
        //Vind huidig adres ouder dat adresgevend is.
        Persoon adresGevendeOuder = null;
        Persoon kind = null;
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()
                && betrokkenheid.isIndAdresGevend() != null && betrokkenheid.isIndAdresGevend())
            {
                adresGevendeOuder = betrokkenheid.getBetrokkene();
            }

            if (SoortBetrokkenheid.KIND == betrokkenheid.getSoortBetrokkenheid()) {
                kind = betrokkenheid.getBetrokkene();
            }
        }

        if (adresGevendeOuder == null) {
            melding = new Melding(
                SoortMelding.FOUT_ONOVERRULEBAAR,
                MeldingCode.ALG0002,
                "Kan adres gevende ouder van nieuwgeborene niet bepalen in het bericht. "
                    + "Is de indicatie adresgevend ingevuld voor een van de ouders?");
        } else {
            PersistentPersoonAdres adres;
            if (adresGevendeOuder.getIdentificatienummers() != null) {
                adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(
                    adresGevendeOuder.getIdentificatienummers().getBurgerservicenummer()
                );

                //De geboortedatum van nieuwgeborene ligt na de datum aanvang adreshouding van adresGevendeOuder.
                if (adres != null && kind.getGeboorte().getDatumGeboorte() <= adres.getDatumAanvangAdreshouding()) {
                    melding = new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRPUC00120);
                }

            } else {
                melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                    "Groep PersoonIdentificatieNummers van adresGevendeOuder niet opgegeven in het bericht.");
            }
        }
        return melding;
    }
}
