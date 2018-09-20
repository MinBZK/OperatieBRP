/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonGeboorteRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonGeslachtsaanduidingRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonGeslachtsnaamcomponentRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonIdentificatienummersRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonNationaliteitRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonSamengesteldeNaamRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonVoornaamRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonGeboorte;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonGeslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonIdentificatienummers;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonSamengesteldeNaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonVoornaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;

public class PersoonOGMRepositoryImpl implements PersoonOGMRepositoryCustom {

    @Inject
    private His_PersoonIdentificatienummersRepository his_persoonIdentificatienummersRepository;

    @Inject
    private His_PersoonGeboorteRepository his_persoonGeboorteRepository;

    @Inject
    private His_PersoonGeslachtsaanduidingRepository his_persoonGeslachtsaanduidingRepository;

    @Inject
    private His_PersoonVoornaamRepository his_persoonVoornaamRepository;

    @Inject
    private His_PersoonGeslachtsnaamcomponentRepository his_persoonGeslachtsnaamcomponentRepository;

    @Inject
    private His_PersoonNationaliteitRepository his_persoonNationaliteitRepository;

    @Inject
    private His_PersoonSamengesteldeNaamRepository his_persoonSamengesteldeNaamRepository;

    @Override
    public void persisteerGerelateerdeHisEntiteitenVoorNieuwePersoon(final Persoon persoon,
                                                                     final Set<PersoonVoornaam> voornamen,
                                                                     final Set<PersoonNationaliteit> nationaliteiten,
                                                                     final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten) {
        final DatumTijd registratieTijd = DatumEnTijdUtil.nu();
        final Datum vandaag = DatumEnTijdUtil.vandaag();

        //HisPersoonIdentificatieNummers
        final His_PersoonIdentificatienummers hisPids = new His_PersoonIdentificatienummers();
        hisPids.setPersoon(persoon);
        hisPids.setBurgerservicenummer(persoon.getBurgerservicenummer());
        hisPids.setAdministratienummer(persoon.getAdministratienummer());
        hisPids.setDatumTijdRegistratie(registratieTijd);
        hisPids.setDatumAanvangGeldigheid(vandaag);
        his_persoonIdentificatienummersRepository.save(hisPids);

        //HisPersoonGeboorte
        final His_PersoonGeboorte his_persoonGeboorte = new His_PersoonGeboorte();
        his_persoonGeboorte.setPersoon(persoon);
        his_persoonGeboorte.setDatumTijdRegistratie(registratieTijd);
        his_persoonGeboorte.setDatumGeboorte(persoon.getDatumGeboorte());
        his_persoonGeboorte.setGemeenteGeboorte(persoon.getGemeenteGeboorte());
        his_persoonGeboorte.setWoonplaatsGeboorte(persoon.getWoonplaatsGeboorte());
        his_persoonGeboorte.setBuitenlandseGeboorteplaats(persoon.getBuitenlandseGeboorteplaats());
        his_persoonGeboorte.setBuitenlandseRegioGeboorte(persoon.getBuitenlandseRegioGeboorte());
        his_persoonGeboorte.setLandGeboorte(persoon.getLandGeboorte());
        his_persoonGeboorteRepository.save(his_persoonGeboorte);

        //HisPersoonGeslachtsAanduiding
        final His_PersoonGeslachtsaanduiding hisGeslAand = new His_PersoonGeslachtsaanduiding();
        hisGeslAand.setPersoon(persoon);
        hisGeslAand.setDatumAanvangGeldigheid(vandaag);
        hisGeslAand.setGeslachtsaanduiding(persoon.getGeslachtsaanduiding());
        hisGeslAand.setDatumTijdRegistratie(registratieTijd);
        his_persoonGeslachtsaanduidingRepository.save(hisGeslAand);

        //HisPersoonVoornamen
        for (PersoonVoornaam persoonVoornaam : voornamen) {
            final His_PersoonVoornaam hisVoorNaam = new His_PersoonVoornaam();
            hisVoorNaam.setPersoonVoornaam(persoonVoornaam);
            hisVoorNaam.setDatumAanvangGeldigheid(vandaag);
            hisVoorNaam.setDatumTijdRegistratie(registratieTijd);
            hisVoorNaam.setNaam(persoonVoornaam.getNaam());
            his_persoonVoornaamRepository.save(hisVoorNaam);
        }

        //HisPersoonGeslachtsnaamcomponenten
        for (PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : geslachtsnaamcomponenten) {
            final His_PersoonGeslachtsnaamcomponent hisGeslComp = new His_PersoonGeslachtsnaamcomponent();
            hisGeslComp.setPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
            hisGeslComp.setDatumAanvangGeldigheid(vandaag);
            hisGeslComp.setDatumTijdRegistratie(registratieTijd);
            hisGeslComp.setVoorvoegsel(persoonGeslachtsnaamcomponent.getVoorvoegsel());
            hisGeslComp.setScheidingsteken(persoonGeslachtsnaamcomponent.getScheidingsteken());
            hisGeslComp.setNaam(persoonGeslachtsnaamcomponent.getNaam());
            hisGeslComp.setPredikaat(persoonGeslachtsnaamcomponent.getPredikaat());
            hisGeslComp.setAdellijkeTitel(persoonGeslachtsnaamcomponent.getAdellijkeTitel());
            his_persoonGeslachtsnaamcomponentRepository.save(hisGeslComp);
        }

        //HisPersoonNationaliteiten
        for (PersoonNationaliteit persoonNationaliteit : nationaliteiten) {
            final His_PersoonNationaliteit hisPersNation = new His_PersoonNationaliteit();
            hisPersNation.setPersoonNationaliteit(persoonNationaliteit);
            hisPersNation.setDatumAanvangGeldigheid(vandaag);
            hisPersNation.setDatumTijdRegistratie(registratieTijd);
            hisPersNation.setRedenVerkrijging(persoonNationaliteit.getRedenVerkrijging());
            hisPersNation.setRedenVerlies(persoonNationaliteit.getRedenVerlies());
            his_persoonNationaliteitRepository.save(hisPersNation);
        }

        //HisPersoonSamenGesteldeNaam
        final His_PersoonSamengesteldeNaam hisSamenGesteldeNaam = new His_PersoonSamengesteldeNaam();
        hisSamenGesteldeNaam.setPersoon(persoon);
        hisSamenGesteldeNaam.setDatumAanvangGeldigheid(vandaag);
        hisSamenGesteldeNaam.setDatumTijdRegistratie(registratieTijd);
        hisSamenGesteldeNaam.setPredikaat(persoon.getPredikaat());
        hisSamenGesteldeNaam.setVoornamen(persoon.getVoornamen());
        hisSamenGesteldeNaam.setVoorvoegsel(persoon.getVoorvoegsel());
        hisSamenGesteldeNaam.setScheidingsteken(persoon.getScheidingsteken());
        hisSamenGesteldeNaam.setAdellijkeTitel(persoon.getAdellijkeTitel());
        hisSamenGesteldeNaam.setGeslachtsnaam(persoon.getGeslachtsnaam());
        hisSamenGesteldeNaam.setIndicatieAlgoritmischAfgeleid(new JaNee(false));
        hisSamenGesteldeNaam.setIndicatieNamenreeksAlsGeslachtsnaam(new JaNee(false));
        his_persoonSamengesteldeNaamRepository.save(hisSamenGesteldeNaam);
    }
}
