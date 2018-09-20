/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import javax.inject.Inject;
import javax.persistence.NoResultException;

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
import org.springframework.dao.EmptyResultDataAccessException;

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
            melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                "De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
        } else {
            if (adresGevendeOuder.getIdentificatienummers() != null) {
                PersistentPersoonAdres adres = null;

                try {
                    adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(
                        adresGevendeOuder.getIdentificatienummers().getBurgerservicenummer()
                    );
                    //De geboortedatum van nieuwgeborene ligt voor de datum aanvang adreshouding van adresGevendeOuder.
                    if (adres.getDatumAanvangAdreshouding() != null && kind.getGeboorte() != null
                        && kind.getGeboorte().getDatumGeboorte() < adres.getDatumAanvangAdreshouding())
                    {
                        melding = new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRPUC00120);
                    }
                } catch (NoResultException ex) {
                    // ouder heeft onbekende adres. geeft ander soort foutmelding ...
                    // TODO: bolie: maak hier een echte waarschuwing / foutmelding.
                    // zie ook BRBY0118
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
                } catch (EmptyResultDataAccessException ex) {
                    // ouder heeft onbekende adres. geeft ander soort foutmelding ...
                    // TODO: bolie: maak hier een echte waarschuwing / foutmelding.
                    // zie ook BRBY0118
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
                }
            } else {
                melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                    "Groep persoonidentificatienummers van adresgevende ouder niet opgegeven in het bericht.");
            }
        }
        return melding;
    }
}
