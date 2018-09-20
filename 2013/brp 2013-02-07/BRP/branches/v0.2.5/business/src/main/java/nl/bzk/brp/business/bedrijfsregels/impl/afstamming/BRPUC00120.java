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

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * Implementatie van bedrijfsregel BRPUC00120;
 * De geboortedatum van nieuwgeborene ligt na de datum aanvang adreshouding van de adresgevende ouder.
 *
 * Bedrijfsregel is afhankelijk van BRBY0168
 *
 * @brp.bedrijfsregel BRPUC00120
 */
public class BRPUC00120 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger    LOGGER = LoggerFactory.getLogger(BRPUC00120.class);

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public String getCode() {
        return "BRPUC00120";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> melding = new ArrayList<Melding>();
        // Vind huidig adres ouder dat adresgevend is.
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

        if (adresGevendeOuder != null) {
            if (adresGevendeOuder.getIdentificatienummers() != null) {
                Burgerservicenummer bsn = adresGevendeOuder.getIdentificatienummers().getBurgerservicenummer();
                try {
                    PersoonAdres adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(bsn);
                    // De geboortedatum van nieuwgeborene ligt voor de datum aanvang adreshouding van adresGevendeOuder.
                    if (adres.getGegevens().getDatumAanvangAdreshouding() != null
                        && kind.getGeboorte() != null
                        && kind.getGeboorte().getDatumGeboorte().getWaarde() < adres.getGegevens()
                                .getDatumAanvangAdreshouding().getWaarde())
                    {
                        melding.add(new Melding(Soortmelding.WAARSCHUWING, MeldingCode.BRPUC00120,
                                (Identificeerbaar) kind.getGeboorte(), "datumGeboorte"));
                    }
                } catch (NoResultException ex) {
                    LOGGER.error(String
                            .format("Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                                    bsn));
                } catch (EmptyResultDataAccessException ex) {
                    LOGGER.error(String
                            .format("Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                                    bsn));
                }
            } else {
                LOGGER.error("Groep persoonidentificatienummers van adresgevende ouder niet opgegeven in het bericht.");
            }
        } else {
            LOGGER.error("De adresgevende ouder van de nieuwgeborene is niet te bepalen.");
        }
        return melding;
    }
}
