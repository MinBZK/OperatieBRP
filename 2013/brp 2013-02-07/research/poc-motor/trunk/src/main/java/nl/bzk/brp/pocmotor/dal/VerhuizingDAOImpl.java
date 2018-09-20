/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.jpa.PersoonAdresLGMRepositoryCustom;
import nl.bzk.brp.pocmotor.dal.logisch.AangeverAdreshoudingLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.GemeenteLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.LandLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.PlaatsLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.RedenWijzigingAdresLGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;
import org.springframework.stereotype.Repository;

@Repository
public class VerhuizingDAOImpl implements VerhuizingDAO {

    @Inject
    private PersoonAdresLGMRepositoryCustom persoonAdresLGMRepository;

    @Inject
    private GemeenteLGMRepository gemeenteLGMRepository;

    @Inject
    private AangeverAdreshoudingLGMRepository aangeverAdreshoudingLGMRepository;

    @Inject
    private RedenWijzigingAdresLGMRepository redenWijzigingAdresLGMRepository;

    @Inject
    private PlaatsLGMRepository plaatsLGMRepository;

    @Inject
    private LandLGMRepository landRepository;

    @Override
    public void persisteerVerhuizing(final Persoon persoon, final PersoonAdres nieuwAdres, final Datum datumAanvang) {
        //PersoonAdres heeft een referentie nodig naar de Persoon waar het om gaat.
        nieuwAdres.getIdentiteit().setPersoon(persoon);

         //Haal de gemeente op:
        nieuwAdres.getStandaard().setGemeente(
                gemeenteLGMRepository.findByGemeenteStandaardGemeentecode(nieuwAdres.getStandaard().getGemeente().getGemeenteStandaard().getGemeentecode())
        );

        //Haal de AangAdresH op
        nieuwAdres.getStandaard().setAangeverAdreshouding(
                aangeverAdreshoudingLGMRepository.findByIdentiteitCode(
                        nieuwAdres.getStandaard().getAangeverAdreshouding().getIdentiteit().getCode()
                )
        );

        //Haal rdnWijzAdresOp
        nieuwAdres.getStandaard().setRedenWijziging(
                redenWijzigingAdresLGMRepository.findByIdentiteitCode(
                        nieuwAdres.getStandaard().getRedenWijziging().getIdentiteit().getCode()
                )
        );

        //Haal Plaats op
        nieuwAdres.getStandaard().setWoonplaats(
                plaatsLGMRepository.findByIdentiteitNaam(
                        nieuwAdres.getStandaard().getWoonplaats().getIdentiteit().getNaam()
                )
        );

        //Haal land op
        nieuwAdres.getStandaard().setLand(
                landRepository.findByIdentiteitLandcode(
                        nieuwAdres.getStandaard().getLand().getIdentiteit().getLandcode()
                )
        );

        //Persisteer
        persoonAdresLGMRepository.opslaanNieuwPersoonAdres(
                nieuwAdres,
                datumAanvang,
                null,
                DatumEnTijdUtil.nu());
    }
}
