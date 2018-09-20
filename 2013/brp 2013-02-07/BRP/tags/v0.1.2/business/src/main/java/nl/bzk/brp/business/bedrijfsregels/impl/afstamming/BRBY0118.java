/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.BooleanUtils;


/**
 * Bij de inschrijving geboorte moet een nieuwgeborene worden ingeschreven op hetzelfde adres als de ouder met
 * de adresgevend indicatie, wanneer deze een ingezetene is (de ouder is ingeschreven in de BRP en valt onder de
 * verantwoordelijkheid van een College B&W).
 *
 * @brp.bedrijfsregel BRBY0118
 */
public class BRBY0118 implements BedrijfsRegel<PersistentRelatie, Relatie> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public String getCode() {
        return "BRBY0118";
    }

    @Override
    public Melding executeer(final PersistentRelatie huidigeSituatie, final Relatie nieuweSituatie,
            final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;
        PersistentPersoonAdres adres = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (BooleanUtils.isTrue(betrokkenheid.isIndAdresGevend())) {
                adres =
                    persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(betrokkenheid.getBetrokkene()
                            .getIdentificatienummers().getBurgerservicenummer());
                break;
            }
        }

        if (adres == null) {
            // TODO: deze check zou uitgevoerd moeten worden in BRBY0131, op dit moment is deze nog niet
            // geimplementeerd. Zou hier eigenlijk een exceptie gegooid moeten worden?
            melding =
                new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001,
                        "Kan adres gevende ouder van nieuwgeborene niet bepalen in het bericht.");
        } else {
            Persoon kind = nieuweSituatie.getKindBetrokkenheid().getBetrokkene();

            PersoonAdres persoonAdres = new PersoonAdres();
            persoonAdres.setPersoon(kind);
            persoonAdres.setSoort(adres.getSoort());
            // TODO check wat hier ingevuld moet worden
            persoonAdres.setRedenWijziging(RedenWijzigingAdres.PERSOON);
            // TODO check wat hier ingevuld moet worden
            persoonAdres.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
            // TODO check of dit klopt
            persoonAdres.setDatumAanvangAdreshouding(datumAanvangGeldigheid);
            persoonAdres.setAdresseerbaarObject(adres.getAdresseerbaarObject());
            persoonAdres.setIdentificatiecodeNummeraanduiding(adres.getIdentificatiecodeNummeraanduiding());
            persoonAdres.setGemeente(adres.getGemeente());
            persoonAdres.setNaamOpenbareRuimte(adres.getNaamOpenbareRuimte());
            persoonAdres.setAfgekorteNaamOpenbareRuimte(adres.getAfgekorteNaamOpenbareRuimte());
            persoonAdres.setLocatieOmschrijving(adres.getLocatieOmschrijving());
            persoonAdres.setLocatieTovAdres(adres.getLocatietovAdres());
            persoonAdres.setGemeentedeel(adres.getGemeentedeel());
            persoonAdres.setHuisnummer(adres.getHuisnummer());
            persoonAdres.setHuisletter(adres.getHuisletter());
            persoonAdres.setHuisnummertoevoeging(adres.getHuisnummertoevoeging());
            persoonAdres.setPostcode(adres.getPostcode());
            persoonAdres.setWoonplaats(adres.getWoonplaats());
            persoonAdres.setLand(adres.getLand());
            persoonAdres.setPersoonAdresStatusHis(adres.getPersoonAdresStatusHis());
            persoonAdres.setBuitenlandsAdresRegel1(adres.getBuitenlandsAdresRegel1());
            persoonAdres.setBuitenlandsAdresRegel2(adres.getBuitenlandsAdresRegel2());
            persoonAdres.setBuitenlandsAdresRegel3(adres.getBuitenlandsAdresRegel3());
            persoonAdres.setBuitenlandsAdresRegel4(adres.getBuitenlandsAdresRegel4());
            persoonAdres.setBuitenlandsAdresRegel5(adres.getBuitenlandsAdresRegel5());
            persoonAdres.setBuitenlandsAdresRegel6(adres.getBuitenlandsAdresRegel6());
            persoonAdres.setDatumVertrekUitNederland(adres.getDatumVertrekUitNederland());

            Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
            adressen.add(persoonAdres);

            kind.setAdressen(adressen);
        }

        return melding;
    }
}
