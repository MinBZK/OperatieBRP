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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * Bij de inschrijving geboorte moet een nieuwgeborene worden ingeschreven op hetzelfde adres als de ouder met
 * de adresgevend indicatie, wanneer deze een ingezetene is (de ouder is ingeschreven in de BRP en valt onder de
 * verantwoordelijkheid van een College B&W).
 * <p/>
 * Dit is eigenlijk een afleidingsregel (eventueel kan daar een fout optreden), maar er wordt nooit een foutcode
 * BRBY0118 gegenereerd. (Dus nooit naar buiten gecommuniceerd). In het kader naar structuur aanbrengen wordt deze regel
 * omgezet naar een Verwerkingsregel (VR00104)
 * <p/>
 * Deze bedrijfsregel is afhankelijk van BRBY0168
 *
 * @brp.bedrijfsregel VR00104
 * @brp.bedrijfsregel BRBY0118
 */
public class VR00104 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

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
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        boolean isAfgeleid = false;
        for (OuderBericht betrokkenheid : ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie)
                .getOuderBetrokkenheden())
        {
            if (null != betrokkenheid.getOuderschap()
                && Ja.J == betrokkenheid.getOuderschap().getIndicatieOuderUitWieKindIsVoortgekomen()
                && betrokkenheid.getPersoon().getIdentificatienummers() != null)
            {
                Burgerservicenummer bsn = betrokkenheid.getPersoon().getIdentificatienummers().getBurgerservicenummer();
                try {
                    PersoonAdres adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(bsn);
                    PersoonBericht kind =
                        ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie).getKindBetrokkenheid().getPersoon();
                    kopieerAdresOuderNaarKind(actie, kind, adres);
                    isAfgeleid = true;
                } catch (NoResultException ex) {
                    LOGGER.error(String
                            .format("Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
                                    bsn.getWaarde()));
                } catch (EmptyResultDataAccessException ex) {
                    LOGGER.error(String
                            .format("Geen adres gevonden voor adresgevende ouder (bsn: %s). Mogelijk vanwege data inconsistentie.",
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
        persoonAdres.setSoort(adres.getStandaard().getSoort());
        persoonAdres.setRedenWijziging(referentieDataRepository
                .vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("A")));
        persoonAdres.setAangeverAdreshouding(null);
        persoonAdres.setDatumAanvangAdreshouding(actie.getDatumAanvangGeldigheid());
        persoonAdres.setAdresseerbaarObject(adres.getStandaard().getAdresseerbaarObject());
        persoonAdres.setIdentificatiecodeNummeraanduiding(adres.getStandaard().getIdentificatiecodeNummeraanduiding());
        persoonAdres.setGemeente(adres.getStandaard().getGemeente());
        persoonAdres.setNaamOpenbareRuimte(adres.getStandaard().getNaamOpenbareRuimte());
        persoonAdres.setAfgekorteNaamOpenbareRuimte(adres.getStandaard().getAfgekorteNaamOpenbareRuimte());
        persoonAdres.setLocatieOmschrijving(adres.getStandaard().getLocatieOmschrijving());
        persoonAdres.setLocatietovAdres(adres.getStandaard().getLocatietovAdres());
        persoonAdres.setGemeentedeel(adres.getStandaard().getGemeentedeel());
        persoonAdres.setHuisnummer(adres.getStandaard().getHuisnummer());
        persoonAdres.setHuisletter(adres.getStandaard().getHuisletter());
        persoonAdres.setHuisnummertoevoeging(adres.getStandaard().getHuisnummertoevoeging());
        persoonAdres.setPostcode(adres.getStandaard().getPostcode());
        persoonAdres.setWoonplaats(adres.getStandaard().getWoonplaats());
        persoonAdres.setLand(adres.getStandaard().getLand());
        persoonAdres.setBuitenlandsAdresRegel1(adres.getStandaard().getBuitenlandsAdresRegel1());
        persoonAdres.setBuitenlandsAdresRegel2(adres.getStandaard().getBuitenlandsAdresRegel2());
        persoonAdres.setBuitenlandsAdresRegel3(adres.getStandaard().getBuitenlandsAdresRegel3());
        persoonAdres.setBuitenlandsAdresRegel4(adres.getStandaard().getBuitenlandsAdresRegel4());
        persoonAdres.setBuitenlandsAdresRegel5(adres.getStandaard().getBuitenlandsAdresRegel5());
        persoonAdres.setBuitenlandsAdresRegel6(adres.getStandaard().getBuitenlandsAdresRegel6());
        persoonAdres.setDatumVertrekUitNederland(adres.getStandaard().getDatumVertrekUitNederland());
        persoonAdres.setIndicatiePersoonNietAangetroffenOpAdres(
                adres.getStandaard().getIndicatiePersoonNietAangetroffenOpAdres());
        persoonAdresObj.setStandaard(persoonAdres);
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdresObj);

        kind.setAdressen(adressen);
    }
}
