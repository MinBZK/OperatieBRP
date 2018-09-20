/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
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
public class BRPUC00120 implements BedrijfsRegel<Relatie> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public String getCode() {
        return "BRPUC00120";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        List<Melding> melding = new ArrayList<Melding>();
        //Vind huidig adres ouder dat adresgevend is.
        Persoon adresGevendeOuder = null;
        Persoon kind = null;
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()
                && betrokkenheid.getBetrokkenheidOuderschap() != null
                && Ja.Ja == betrokkenheid.getBetrokkenheidOuderschap().getIndAdresGevend())
            {
                adresGevendeOuder = betrokkenheid.getBetrokkene();
            }

            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                kind = betrokkenheid.getBetrokkene();
            }
        }

        if (adresGevendeOuder == null) {
            melding.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                "De adresgevende ouder van de nieuwgeborene is niet te bepalen."
                    , (Identificeerbaar) nieuweSituatie, "ouder"));
        } else {
            if (adresGevendeOuder.getIdentificatienummers() != null) {
                PersoonAdres adres = null;

                try {
                    adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(
                        adresGevendeOuder.getIdentificatienummers().getBurgerservicenummer()
                    );
                    //De geboortedatum van nieuwgeborene ligt voor de datum aanvang adreshouding van adresGevendeOuder.
                    if (adres.getGegevens().getDatumAanvangAdreshouding() != null && kind.getGeboorte() != null
                        && kind.getGeboorte().getDatumGeboorte().getWaarde()
                        < adres.getGegevens().getDatumAanvangAdreshouding().getWaarde())
                    {
                        melding.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRPUC00120
                                , (Identificeerbaar) kind.getGeboorte(), "datumGeboorte"));
                    }
                } catch (NoResultException ex) {
                    // ouder heeft onbekende adres. geeft ander soort foutmelding ...
                    // TODO: bolie: maak hier een echte waarschuwing / foutmelding.
                    // zie ook BRBY0118
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "De adresgevende ouder van de nieuwgeborene is niet te bepalen."
                            , (Identificeerbaar) adresGevendeOuder, "burgerservicenummer");
                } catch (EmptyResultDataAccessException ex) {
                    // ouder heeft onbekende adres. geeft ander soort foutmelding ...
                    // TODO: bolie: maak hier een echte waarschuwing / foutmelding.
                    // zie ook BRBY0118
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "De adresgevende ouder van de nieuwgeborene is niet te bepalen."
                            , (Identificeerbaar) adresGevendeOuder, "burgerservicenummer");
                }
            } else {
                melding.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                    "Groep persoonidentificatienummers van adresgevende ouder niet opgegeven in het bericht."
                        , (Identificeerbaar) adresGevendeOuder, "burgerservicenummer"));
            }
        }
        return melding;
    }
}
