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
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * Bij de inschrijving geboorte moet een nieuwgeborene worden ingeschreven op hetzelfde adres als de ouder met
 * de adresgevend indicatie, wanneer deze een ingezetene is (de ouder is ingeschreven in de BRP en valt onder de
 * verantwoordelijkheid van een College B&W).
 *
 * Dit is eigenlijk een afleidingsregel (eventueel kan daar een fout optreden), maar er wordt nooit een
 * foutcode BRBY0118 gegenereerd. (Dus nooit naar buiten gecommuniceerd).
 * In het kader naar structuur aanbrengen wordt deze regel omgezet naar een Verwerkingsregel (VR00104)
 *
 * Deze bedrijfsregel is afhankelijk van BRBY0168
 *
 *
 * @brp.bedrijfsregel VR00104
 * @brp.bedrijfsregel BRBY0118
 */
public class VR00104 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger      LOGGER = LoggerFactory.getLogger(VR00104.class);

    @Inject
    private PersoonAdresRepository   persoonAdresRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public String getCode() {
        return "VR00104";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Actie actie)
    {
        boolean isAfgeleid = false;
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (Ja.Ja == betrokkenheid.getBetrokkenheidOuderschap().getIndAdresGevend()
                && betrokkenheid.getBetrokkene().getIdentificatienummers() != null)
            {
                Burgerservicenummer bsn =
                    betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer();
                try {
                    PersoonAdres adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(bsn);
                    PersoonBericht kind = (PersoonBericht) nieuweSituatie.getKindBetrokkenheid().getBetrokkene();
                    kopieerAdresOuderNaarKind(actie, kind, adres);
                    isAfgeleid = true;
                } catch (NoResultException ex) {
                    LOGGER.error(String.format(
                        "Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                        bsn.getWaarde()));
                } catch (EmptyResultDataAccessException ex) {
                    LOGGER.error(String.format(
                        "Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                        bsn.getWaarde()));
                }
                break;
            }
        }

        if (!isAfgeleid) {
            LOGGER.error("Het is niet gelukt om het adres af te leiden.", getCode());
        }

        return new ArrayList<Melding>();
    }

    /**
     * Kopieert het adres van de ouder naar het kind.
     *
     * @param actie de actie die heeft geleidt tot deze kopieer slag.
     * @param kind het kind waarnaar het adres moet worden gekopieerd.
     * @param adres het adres van de ouder dat moet worden gekopieerd.
     */
    private void kopieerAdresOuderNaarKind(final Actie actie, final PersoonBericht kind, final PersoonAdres adres) {
        // eindelijk kunnen we het werk doen.

        PersoonAdresBericht persoonAdresObj = new PersoonAdresBericht();
        persoonAdresObj.setPersoon(kind);
        PersoonAdresStandaardGroepBericht persoonAdres = new PersoonAdresStandaardGroepBericht();
        persoonAdres.setSoort(adres.getGegevens().getSoort());
        persoonAdres.setRedenwijziging(referentieDataRepository
                .vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("A")));
        persoonAdres.setAangeverAdreshouding(null);
        persoonAdres.setDatumAanvangAdreshouding(actie.getDatumAanvangGeldigheid());
        persoonAdres.setAdresseerbaarObject(adres.getGegevens().getAdresseerbaarObject());
        persoonAdres.setIdentificatiecodeNummeraanduiding(adres.getGegevens().getIdentificatiecodeNummeraanduiding());
        persoonAdres.setGemeente(adres.getGegevens().getGemeente());
        persoonAdres.setNaamOpenbareRuimte(adres.getGegevens().getNaamOpenbareRuimte());
        persoonAdres.setAfgekorteNaamOpenbareRuimte(adres.getGegevens().getAfgekorteNaamOpenbareRuimte());
        persoonAdres.setLocatieOmschrijving(adres.getGegevens().getLocatieOmschrijving());
        persoonAdres.setLocatietovAdres(adres.getGegevens().getLocatietovAdres());
        persoonAdres.setGemeentedeel(adres.getGegevens().getGemeentedeel());
        persoonAdres.setHuisnummer(adres.getGegevens().getHuisnummer());
        persoonAdres.setHuisletter(adres.getGegevens().getHuisletter());
        persoonAdres.setHuisnummertoevoeging(adres.getGegevens().getHuisnummertoevoeging());
        persoonAdres.setPostcode(adres.getGegevens().getPostcode());
        persoonAdres.setWoonplaats(adres.getGegevens().getWoonplaats());
        persoonAdres.setLand(adres.getGegevens().getLand());
        persoonAdres.setBuitenlandsAdresRegel1(adres.getGegevens().getBuitenlandsAdresRegel1());
        persoonAdres.setBuitenlandsAdresRegel2(adres.getGegevens().getBuitenlandsAdresRegel2());
        persoonAdres.setBuitenlandsAdresRegel3(adres.getGegevens().getBuitenlandsAdresRegel3());
        persoonAdres.setBuitenlandsAdresRegel4(adres.getGegevens().getBuitenlandsAdresRegel4());
        persoonAdres.setBuitenlandsAdresRegel5(adres.getGegevens().getBuitenlandsAdresRegel5());
        persoonAdres.setBuitenlandsAdresRegel6(adres.getGegevens().getBuitenlandsAdresRegel6());
        persoonAdres.setDatumVertrekUitNederland(adres.getGegevens().getDatumVertrekUitNederland());

        persoonAdresObj.setGegevens(persoonAdres);
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdresObj);

        kind.setAdressen(adressen);
    }
}
