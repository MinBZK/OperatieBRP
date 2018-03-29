/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.brp.generated.AdellijkeTitelCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.AdellijkeTitelCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Administratienummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BuitenlandsePlaats;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Burgerservicenummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GeslachtsaanduidingCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GeslachtsaanduidingCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Geslachtsnaamstam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonGeboorte;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonGeslachtsaanduiding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonIdentificatienummers;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonSamengesteldeNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.JaNee;
import nl.bzk.migratiebrp.bericht.model.brp.generated.JaNeeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.LandGebiedCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Locatieomschrijving;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PredicaatCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PredicaatCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Scheidingsteken;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voornamen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voorvoegsel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.springframework.stereotype.Component;

/**
 * Maakt persoon voor bericht aan obv verzoek en de gevonden persoon in brp.
 */
@Component
public class PersoonMaker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private static final String OBJECT_TYPE_PERSOON = "Persoon";
    
    private final AttribuutMaker attribuutMaker;

    /**
     * Constructor.
     * @param attribuutMaker utility voor maken attributen
     */
    @Inject
    public PersoonMaker(final AttribuutMaker attribuutMaker) {
        this.attribuutMaker = attribuutMaker;
    }

    /**
     * Maak op basis van de persoon uit het verzoek een persoon voor de opdracht.
     * @param idMaker identificatie maker
     * @param persoon De in persoon uit het verzoek.
     * @return De persoon voor de opdracht.
     */
    public final ObjecttypePersoon maakPseudoPersoon(final BerichtIdentificatieMaker idMaker, final BrpToevalligeGebeurtenisPersoon persoon) {
        final ObjecttypePersoon persoonObjecttype = new ObjecttypePersoon();
        persoonObjecttype.setObjecttype(OBJECT_TYPE_PERSOON);
        persoonObjecttype.setCommunicatieID(idMaker.volgendIdentificatieId());

        // Groep identificatienummers
        persoonObjecttype.getIdentificatienummers()
                .add(maakPersoonIdentificatienummers(idMaker, persoon.getAdministratienummer(), persoon.getBurgerservicenummer()));

        persoonObjecttype.getSamengesteldeNaam().add(
                maakPersoonSamengesteldeNaam(
                        idMaker,
                        false,
                        persoon.getVoornamen(),
                        persoon.getAdellijkeTitelCode(),
                        persoon.getPredicaatCode(),
                        persoon.getVoorvoegsel(),
                        persoon.getScheidingsteken(),
                        persoon.getGeslachtsnaamstam()));

        // Groep geboorte
        persoonObjecttype.getGeboorte().add(
                maakPersoonGeboorte(
                        idMaker,
                        persoon.getGeboorteDatum(),
                        persoon.getGeboorteGemeenteCode(),
                        persoon.getGeboorteBuitenlandsePlaats(),
                        persoon.getGeboorteOmschrijvingLocatie(),
                        persoon.getGeboorteLandOfGebiedCode()));

        // Groep geslachtsaanduiding
        persoonObjecttype.getGeslachtsaanduiding().add(maakPersoonGeslachtsaanduiding(idMaker, persoon.getGeslachtsaanduidingCode()));

        return persoonObjecttype;
    }

    /**
     * Maakt een persoon samengestelde naam groep voor het BRP bericht.
     * @param idMaker identificatie maker
     * @param naam De naam groep uit het verzoek.
     * @return de samengestelde naam groep voor het BRP bericht.
     */
    public final GroepPersoonSamengesteldeNaam maakPersoonSamengesteldeNaam(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenisPersoon naam) {

        return maakPersoonSamengesteldeNaam(
                idMaker,
                false,
                naam.getVoornamen(),
                naam.getAdellijkeTitelCode(),
                naam.getPredicaatCode(),
                naam.getVoorvoegsel(),
                naam.getScheidingsteken(),
                naam.getGeslachtsnaamstam());
    }

    /**
     * Maakt een persoon samengestelde naam groep voor het BRP bericht.
     * @param idMaker identificatie maker
     * @param naam De naam groep uit het verzoek.
     * @return de samengestelde naam groep voor het BRP bericht.
     */
    public final GroepPersoonSamengesteldeNaam maakPersoonSamengesteldeNaam(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenisNaamGeslacht naam) {

        return maakPersoonSamengesteldeNaam(
                idMaker,
                true,
                naam.getVoornamen(),
                naam.getAdellijkeTitelCode(),
                naam.getPredicaatCode(),
                naam.getVoorvoegsel(),
                naam.getScheidingsteken(),
                naam.getGeslachtsnaamstam());
    }

    /**
     * Maakt een persoon samengestelde naam groep voor het BRP bericht.
     * @param idMaker identificatie maker
     * @param voornamenVerzoek De voornamen uit het verzoek.
     * @param adellijkeTitelVerzoek De adellijke titel uit het verzoek.
     * @param predicaatVerzoek Het predicaat uit het verzoek.
     * @param voorvoegselVerzoek De voorvoegsels uit het verzoek.
     * @param geslachtsnaamstamVerzoek De geslachtsnaamstam uit het verzoek.
     * @return de samengestelde naam groep voor het BRP bericht.
     */
    private GroepPersoonSamengesteldeNaam maakPersoonSamengesteldeNaam(
            final BerichtIdentificatieMaker idMaker,
            final boolean toevoegenIndicatieAfgeleid,
            final BrpString voornamenVerzoek,
            final BrpAdellijkeTitelCode adellijkeTitelVerzoek,
            final BrpPredicaatCode predicaatVerzoek,
            final BrpString voorvoegselVerzoek,
            final BrpCharacter scheidingstekenVerzoek,
            final BrpString geslachtsnaamstamVerzoek) {

        final GroepPersoonSamengesteldeNaam samengesteldeNaam = new GroepPersoonSamengesteldeNaam();
        samengesteldeNaam.setCommunicatieID(idMaker.volgendIdentificatieId());

        if (toevoegenIndicatieAfgeleid) {
            final JaNee indicatieAfgeleid = new JaNee();
            indicatieAfgeleid.setValue(JaNeeS.N);
            samengesteldeNaam.setIndicatieAfgeleid(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamIndicatieAfgeleid(indicatieAfgeleid));
        }

        final JaNee indicatieNamenReeks = new JaNee();
        indicatieNamenReeks.setValue(JaNeeS.N);
        samengesteldeNaam.setIndicatieNamenreeks(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamIndicatieNamenreeks(indicatieNamenReeks));

        if (voornamenVerzoek != null) {
            final Voornamen voornamen = new Voornamen();
            voornamen.setValue(BrpString.unwrap(voornamenVerzoek));
            samengesteldeNaam.setVoornamen(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamVoornamen(voornamen));
        }

        if (adellijkeTitelVerzoek != null) {
            final AdellijkeTitelCode adellijkeTitel = new AdellijkeTitelCode();
            adellijkeTitel.setValue(AdellijkeTitelCodeS.valueOf(adellijkeTitelVerzoek.getWaarde()));
            samengesteldeNaam.setAdellijkeTitelCode(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamAdellijkeTitelCode(adellijkeTitel));
        }

        if (predicaatVerzoek != null) {
            final PredicaatCode predicaat = new PredicaatCode();
            predicaat.setValue(PredicaatCodeS.valueOf(predicaatVerzoek.getWaarde()));
            samengesteldeNaam.setPredicaatCode(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamPredicaatCode(predicaat));
        }

        if (voorvoegselVerzoek != null) {
            final Voorvoegsel voorvoegsel = new Voorvoegsel();
            voorvoegsel.setValue(BrpString.unwrap(voorvoegselVerzoek));
            samengesteldeNaam.setVoorvoegsel(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamVoorvoegsel(voorvoegsel));
        }

        if (scheidingstekenVerzoek != null) {
            final Scheidingsteken scheidingsteken = new Scheidingsteken();
            scheidingsteken.setValue(String.valueOf(scheidingstekenVerzoek.getWaarde()));
            samengesteldeNaam.setScheidingsteken(OBJECT_FACTORY.createGroepPersoonNaamgebruikScheidingsteken(scheidingsteken));
        }

        final Geslachtsnaamstam geslachtsnaamstam = new Geslachtsnaamstam();
        geslachtsnaamstam.setValue(BrpString.unwrap(geslachtsnaamstamVerzoek));
        samengesteldeNaam.setGeslachtsnaamstam(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamGeslachtsnaamstam(geslachtsnaamstam));
        return samengesteldeNaam;
    }

    private GroepPersoonGeboorte maakPersoonGeboorte(
            final BerichtIdentificatieMaker idMaker,
            final BrpDatum geboortedatum,
            final BrpGemeenteCode geboorteplaats,
            final BrpString brpBuitenlandsePlaats,
            final BrpString omschrijvingLocatie,
            final BrpLandOfGebiedCode landOfGebied) {
        final GroepPersoonGeboorte geboorte = new GroepPersoonGeboorte();
        geboorte.setCommunicatieID(idMaker.volgendIdentificatieId());

        final DatumMetOnzekerheid datumGeboorte = attribuutMaker.maakDatumMetOnzekerheid(geboortedatum);
        geboorte.setDatum(OBJECT_FACTORY.createGroepPersoonGeboorteDatum(datumGeboorte));

        if (geboorteplaats != null) {
            final GemeenteCode gemeente = new GemeenteCode();
            gemeente.setValue(geboorteplaats.getWaarde());
            geboorte.setGemeenteCode(OBJECT_FACTORY.createGroepPersoonGeboorteGemeenteCode(gemeente));
        }
        if (brpBuitenlandsePlaats != null) {
            final BuitenlandsePlaats buitenlandsePlaats = new BuitenlandsePlaats();
            buitenlandsePlaats.setValue(brpBuitenlandsePlaats.getWaarde());
            geboorte.setBuitenlandsePlaats(OBJECT_FACTORY.createGroepPersoonGeboorteBuitenlandsePlaats(buitenlandsePlaats));
        }
        if (omschrijvingLocatie != null) {
            final Locatieomschrijving locatieomschrijving = new Locatieomschrijving();
            locatieomschrijving.setValue(omschrijvingLocatie.getWaarde());
            geboorte.setOmschrijvingLocatie(OBJECT_FACTORY.createGroepPersoonGeboorteOmschrijvingLocatie(locatieomschrijving));
        }

        final LandGebiedCode land = new LandGebiedCode();
        land.setValue(String.valueOf(landOfGebied.getWaarde()));
        geboorte.setLandGebiedCode(OBJECT_FACTORY.createGroepPersoonGeboorteLandGebiedCode(land));

        return geboorte;
    }

    private GroepPersoonIdentificatienummers maakPersoonIdentificatienummers(
            final BerichtIdentificatieMaker idMaker,
            final BrpString anr,
            final BrpString bsn) {
        final GroepPersoonIdentificatienummers identificatienummers = new GroepPersoonIdentificatienummers();
        identificatienummers.setCommunicatieID(idMaker.volgendIdentificatieId());
        final Administratienummer aNummer = new Administratienummer();
        aNummer.setValue(String.valueOf(anr.getWaarde()));
        identificatienummers.setAdministratienummer(OBJECT_FACTORY.createGroepPersoonIdentificatienummersAdministratienummer(aNummer));

        if (bsn != null) {
            final Burgerservicenummer burgerServiceNummer = new Burgerservicenummer();
            burgerServiceNummer.setValue(String.valueOf(bsn.getWaarde()));
            identificatienummers.setBurgerservicenummer(OBJECT_FACTORY.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerServiceNummer));
        }

        return identificatienummers;
    }

    /**
     * Maakt een persoon geslachtsaanduiding groep voor het BRP bericht.
     * @param idMaker identificatie maker
     * @param geslachtsaanduidingVerzoek De geslachtsaanduiding uit het verzoek.
     * @return de geslachtsaanduiding groep voor het BRP bericht.
     */
    public final GroepPersoonGeslachtsaanduiding maakPersoonGeslachtsaanduiding(
            final BerichtIdentificatieMaker idMaker,
            final BrpGeslachtsaanduidingCode geslachtsaanduidingVerzoek) {

        final GroepPersoonGeslachtsaanduiding geslachtsaanduiding = new GroepPersoonGeslachtsaanduiding();
        geslachtsaanduiding.setCommunicatieID(idMaker.volgendIdentificatieId());

        final GeslachtsaanduidingCode geslachtsaanduidingCode = new GeslachtsaanduidingCode();
        geslachtsaanduidingCode.setValue(GeslachtsaanduidingCodeS.valueOf(geslachtsaanduidingVerzoek.getWaarde()));
        geslachtsaanduiding.setCode(OBJECT_FACTORY.createGroepPersoonGeslachtsaanduidingCode(geslachtsaanduidingCode));

        return geslachtsaanduiding;
    }
}
