/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.operationeel.LandOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.NationaliteitOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PartijOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PersoonGeslachtsnaamComponentOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PersoonNationaliteitOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PersoonOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PersoonVoornaamOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PlaatsOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.RedenVerkrijgingNLNationaliteitOGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Geslachtsnaam;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornamen;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortPersoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonVoornaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit;
import org.springframework.stereotype.Repository;

@Repository
public class PersoonDAOImpl implements PersoonDAO {

    @Inject
    private PersoonOGMRepository persoonOGMRepository;

    @Inject
    private PartijOGMRepository partijOGMRepository;

    @Inject
    private PlaatsOGMRepository plaatsOGMRepository;

    @Inject
    private LandOGMRepository landOGMRepository;

    @Inject
    private PersoonVoornaamOGMRepository persoonVoornaamOGMRepository;

    @Inject
    private PersoonGeslachtsnaamComponentOGMRepository persoonGeslachtsnaamComponentOGMRepository;

    @Inject
    private RedenVerkrijgingNLNationaliteitOGMRepository redenVerkrijgingNLNationaliteitOGMRepository;

    @Inject
    private PersoonNationaliteitOGMRepository persoonNationaliteitOGMRepository;

    @Inject
    private NationaliteitOGMRepository nationaliteitOGMRepository;

    @Override
    public void persisteerNieuwIngeschreve(Persoon kind) {
         //Verrijk het kind met de nodige informatie
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opPersoon = verrijkKindMetInfoVoorOpslag(kind);

        //Persisteer het kind in ALaag
        opPersoon = persoonOGMRepository.saveAndFlush(opPersoon);

        //Persisteer voornamen
        Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam> voornamen =
                persisteerVoornamen(opPersoon, kind.getVoornamen());

        //Persisteer geslachtsnaam componenten
        Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten =
                persisteerGeslNaamComponenten(opPersoon, kind.getGeslachtsnaamcomponenten());

        //Persisteer nationaliteiten
        Set<PersoonNationaliteit> nationaliteiten = persisteerNationaliteiten(opPersoon, kind);

        //Persisteer het kind in CLaag 
        persoonOGMRepository.persisteerGerelateerdeHisEntiteitenVoorNieuwePersoon(
                opPersoon,
                voornamen,
                nationaliteiten,
                geslachtsnaamcomponenten);
    }

    @Override
    public void wijzigGelsachtsNaam(final Persoon persoon, final BRPActie actie) {
        List<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent> opGeslachtsnaamcomponenten =
                persoonGeslachtsnaamComponentOGMRepository.findByPersoonBurgerservicenummer(
                        persoon.getIdentificatienummers().getBurgerservicenummer()
                );

        for (PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            for (nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent opPersoonGeslachtsnaamcomponent : opGeslachtsnaamcomponenten) {
                if (persoonGeslachtsnaamcomponent.getIdentiteit().getVolgnummer().getWaarde().equals(opPersoonGeslachtsnaamcomponent.getVolgnummer().getWaarde())) {
                    opPersoonGeslachtsnaamcomponent.setNaam(persoonGeslachtsnaamcomponent.getStandaard().getNaam());
                    opPersoonGeslachtsnaamcomponent.setVoorvoegsel(persoonGeslachtsnaamcomponent.getStandaard().getVoorvoegsel());
                    opPersoonGeslachtsnaamcomponent.setScheidingsteken(persoonGeslachtsnaamcomponent.getStandaard().getScheidingsteken());
                    opPersoonGeslachtsnaamcomponent = persoonGeslachtsnaamComponentOGMRepository.save(opPersoonGeslachtsnaamcomponent);
                    persoonGeslachtsnaamComponentOGMRepository.verwerkGeslachtsNaamWijzigingInHistorie(opPersoonGeslachtsnaamcomponent, actie);
                    break;
                }
            }
        }
    }

    private Set<PersoonNationaliteit> persisteerNationaliteiten(
            final nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opPersoon,
            final Persoon kind) {

        final Set<PersoonNationaliteit> persNationaliteiten =
                new HashSet<PersoonNationaliteit>();

        for (nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonNationaliteit persoonNationaliteit : kind.getNationaliteiten()) {
            final nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit persNation =
                    new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit();
            persNation.setNationaliteit(
                    nationaliteitOGMRepository.findByNationaliteitcode(
                            persoonNationaliteit.getIdentiteit().getNationaliteit().getIdentiteit().getNationaliteitcode()
                    )
            );
            
            persNation.setPersoon(opPersoon);
            persNation.setRedenVerkrijging(
                    redenVerkrijgingNLNationaliteitOGMRepository.findOne(
                            persoonNationaliteit.getStandaard().getRedenVerkrijging().getIdentiteit().getID().getWaarde()
                    )
            );
            persNation.setPersoonNationaliteitStatusHis(StatusHistorie.ACTUEEL);
            persNationaliteiten.add(persNation);
        }
        persoonNationaliteitOGMRepository.save(persNationaliteiten);
        return persNationaliteiten;
    }

    private Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent> persisteerGeslNaamComponenten(
            final nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opPersoon,
            final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten) {
        
        final Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent> geslComponenten =
                new HashSet<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent>();

        for (PersoonGeslachtsnaamcomponent persoonGeslNaamcomponent : geslachtsnaamcomponenten) {
            nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent geslachtsnaamcomponent =
                    new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent();
            geslachtsnaamcomponent.setPersoon(opPersoon);
            geslachtsnaamcomponent.setNaam(persoonGeslNaamcomponent.getStandaard().getNaam());
            geslachtsnaamcomponent.setScheidingsteken(persoonGeslNaamcomponent.getStandaard().getScheidingsteken());
            geslachtsnaamcomponent.setVolgnummer(persoonGeslNaamcomponent.getIdentiteit().getVolgnummer());
            geslachtsnaamcomponent.setVoorvoegsel(persoonGeslNaamcomponent.getStandaard().getVoorvoegsel());
            geslachtsnaamcomponent.setPersoonGeslachtsnaamcomponentStatusHis(StatusHistorie.ACTUEEL);
            geslComponenten.add(geslachtsnaamcomponent);
        }
        persoonGeslachtsnaamComponentOGMRepository.save(geslComponenten);
        return geslComponenten;
    }

    private Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam> persisteerVoornamen(
            final nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opPersoon,
            final Set<PersoonVoornaam> voornamen) {

        final Set<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam> opVoornamen
                = new HashSet<nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam>();

        for (PersoonVoornaam persoonVoornaam : voornamen) {
            nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam voornaam
                    = new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam();
            voornaam.setPersoon(opPersoon);
            voornaam.setNaam(persoonVoornaam.getStandaard().getNaam());
            voornaam.setVolgnummer(persoonVoornaam.getIdentiteit().getVolgnummer());
            voornaam.setPersoonVoornaamStatusHis(StatusHistorie.ACTUEEL);
            opVoornamen.add(voornaam);
        }
        persoonVoornaamOGMRepository.save(opVoornamen);
        return opVoornamen;
    }

    private nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon verrijkKindMetInfoVoorOpslag(final Persoon kind) {
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opPersoon
                = new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon();
        opPersoon.setSoort(SoortPersoon.INGESCHREVENE);
        
        if (kind.getIdentificatienummers() != null) {
            opPersoon.setBurgerservicenummer(kind.getIdentificatienummers().getBurgerservicenummer());
            opPersoon.setAdministratienummer(kind.getIdentificatienummers().getAdministratienummer());
        }

        kind.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);

        if (kind.getGeboorte() != null) {
            opPersoon.setGemeenteGeboorte(
                    partijOGMRepository.findByGemeentecode(
                            kind.getGeboorte().getGemeenteGeboorte().getGemeenteStandaard().getGemeentecode()
                    )
            );

            opPersoon.setDatumGeboorte(kind.getGeboorte().getDatumGeboorte());
            opPersoon.setWoonplaatsGeboorte(
                    plaatsOGMRepository.findByNaam(
                            kind.getGeboorte().getWoonplaatsGeboorte().getIdentiteit().getNaam()
                    )
            );

            opPersoon.setLandGeboorte(
                    landOGMRepository.findByLandcode(
                            kind.getGeboorte().getLandGeboorte().getIdentiteit().getLandcode()
                    )
            );
        }

        opPersoon.setGeslachtsaanduiding(kind.getGeslachtsaanduiding().getGeslachtsaanduiding());

        //Geslachtsnaam
        for (PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : kind.getGeslachtsnaamcomponenten()) {
            opPersoon.setVoorvoegsel(persoonGeslachtsnaamcomponent.getStandaard().getVoorvoegsel());
            opPersoon.setScheidingsteken(persoonGeslachtsnaamcomponent.getStandaard().getScheidingsteken());
            Geslachtsnaam geslNaam = new Geslachtsnaam(persoonGeslachtsnaamcomponent.getStandaard().getNaam().getWaarde());
            opPersoon.setGeslachtsnaam(geslNaam);
        }

        //Voornamen
        final StringBuilder voornamenString = new StringBuilder();
        for (PersoonVoornaam persoonVoornaam : kind.getVoornamen()) {
            voornamenString.append(persoonVoornaam.getStandaard().getNaam().getWaarde());
            voornamenString.append(" ");
        }
        Voornamen voornamen = new Voornamen(voornamenString.toString().trim());
        opPersoon.setVoornamen(voornamen);
        return opPersoon;
    }
}
