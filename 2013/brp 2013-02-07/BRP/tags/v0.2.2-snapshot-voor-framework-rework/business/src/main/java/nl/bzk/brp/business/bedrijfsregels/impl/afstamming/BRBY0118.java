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
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * Bij de inschrijving geboorte moet een nieuwgeborene worden ingeschreven op hetzelfde adres als de ouder met
 * de adresgevend indicatie, wanneer deze een ingezetene is (de ouder is ingeschreven in de BRP en valt onder de
 * verantwoordelijkheid van een College B&W).
 *
 * @brp.bedrijfsregel BRBY0118
 */
public class BRBY0118 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0118.class);

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public String getCode() {
        return "BRBY0118";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        PersoonAdres adres = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (Ja.Ja == betrokkenheid.getBetrokkenheidOuderschap().getIndAdresGevend()
                && betrokkenheid.getBetrokkene().getIdentificatienummers() != null)
            {
                Persoon persoon = betrokkenheid.getBetrokkene();
                Burgerservicenummer bsn =
                    betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer();
                try {
                    adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(bsn);
                    Melding melding = checkAdresPersistentIsOk(persoon, adres);
                    if (melding != null) {
                        meldingen.add(melding);
                    }
                } catch (NoResultException ex) {
                    LOGGER.info(String.format(
                        "Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                        bsn));
                } catch (EmptyResultDataAccessException ex) {
                    LOGGER.info(String.format(
                        "Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                        bsn));
                }
                break;
            }
        }
        if (meldingen.size() == 0 && adres == null) {
            meldingen.add(bouwMeldingVoorOnbekendAdres(nieuweSituatie));
        }

        if (meldingen.size() == 0) {
            PersoonBericht kind = (PersoonBericht) nieuweSituatie.getKindBetrokkenheid().getBetrokkene();
            kopieerAdresOuderNaarKind(actie, kind, adres);
        }
        return meldingen;
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
        persoonAdres.setRedenwijziging(referentieDataRepository.vindRedenWijzingAdresOpCode(
            new RedenWijzigingAdresCode("A")
        ));
        persoonAdres.setAangeverAdreshouding(null);
        persoonAdres.setDatumAanvangAdreshouding(actie.getDatumAanvangGeldigheid());
        persoonAdres.setAdresseerbaarObject(adres.getGegevens().getAdresseerbaarObject());
        persoonAdres.setIdentificatiecodeNummeraanduiding(
            adres.getGegevens().getIdentificatiecodeNummeraanduiding());
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

    /**
     * Check of adres van de persoon / relatie bekend is. Deze methode wordt gebruikt voor zowel persoon als relatie
     *
     * @param object de rootobject, dit kan een persoon of relatie zijn.
     * @param adres het te testen adres
     * @return als adres is null return een foutmelding, anders <code>null</code>.
     */
    private Melding checkAdresPersistentIsOk(final RootObject object, final PersoonAdres adres) {
        // TODO: deze check zou uitgevoerd moeten worden in BRBY0131, op dit moment is deze nog niet
        // geimplementeerd. Zou hier eigenlijk een exceptie gegooid moeten worden?
        // zie ook foutmelding in BRPUC00120
        if (null == adres) {
            bouwMeldingVoorOnbekendAdres(object);
        }
        return null;
    }

    /**
     * Bouwt een melding die aangeeft dat het adres niet bepaald kan worden.
     *
     * @param object het object waarvoor het adres niet bepaald kan worden (persoon of relatie)
     * @return een foutmelding
     */
    private Melding bouwMeldingVoorOnbekendAdres(final RootObject object) {
        Identificeerbaar groep = null;
        if (object instanceof Persoon) {
            groep = (Identificeerbaar) object;
        } else if (object instanceof Relatie) {
            groep = (Identificeerbaar) object;
        }
        return new Melding(Soortmelding.FOUT, MeldingCode.ALG0001,
            "Het adres van de adresgevende ouder van de nieuwgeborene is niet te bepalen."
            , groep, "adres");
    }
}
