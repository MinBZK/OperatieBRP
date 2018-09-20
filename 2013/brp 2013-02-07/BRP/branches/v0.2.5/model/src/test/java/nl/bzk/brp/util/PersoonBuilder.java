/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsverantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonIndicatieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import org.apache.commons.lang.StringUtils;
import org.springframework.test.util.ReflectionTestUtils;


/** Utility class voor het construeren van Persoon instanties. */
public final class PersoonBuilder {

    /** Empty en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private PersoonBuilder() {
    }

    public static PersoonBericht bouwRefererendPersoon(final String bsn) {
        final PersoonBericht persoon = new PersoonBericht();

        // Groep identificatie nummers
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe Persoon instantie met de opgegeven eigenschappen en enkele standaard
     * waardes voor enkele velden.
     *
     * @param bsn het burgerservicenummer
     * @param datumGeboorte de geboorte datum
     * @param gemeenteGeboorte de geboorte gemeente
     * @param voornaam de voornaam
     * @param voorvoegsel het voorvoegsel
     * @param geslachtsnaam de geslachtsnaam
     * @return een nieuw persoon
     */
    public static PersoonBericht bouwPersoon(final String bsn, final Geslachtsaanduiding geslachtsaanduiding,
        final Integer datumGeboorte, final Partij gemeenteGeboorte, final String voornaam, final String voorvoegsel,
        final String geslachtsnaam)
    {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setVerzendendId("id.pers");

        // Groep identificatie nummers
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        persoon.getIdentificatienummers().setVerzendendId("id.pers.idnrs");

        // Groep geslachts aanduiding
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.getGeslachtsaanduiding().setGeslachtsaanduiding(geslachtsaanduiding);
        persoon.getGeslachtsaanduiding().setVerzendendId("id.pers.geslacht");

        // Groep geboorte
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(datumGeboorte));
        persoon.getGeboorte().setLandGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        persoon.getGeboorte().setGemeenteGeboorte(gemeenteGeboorte);
        persoon.getGeboorte().setVerzendendId("id.pers.geboorte");

        // Objecttype persoonvoornaam
        persoon.setPersoonVoornaam(new ArrayList<PersoonVoornaamBericht>());
        persoon.getPersoonVoornaam().add(bouwPersoonVoornaam(voornaam));
        persoon.getPersoonVoornaam().get(0).setVerzendendId("id.pers.vnaam1");

        // Objecttype geslachtsnaam component
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(bouwGeslachtsnaamcomponent(voorvoegsel, geslachtsnaam));
        persoon.getGeslachtsnaamcomponenten().get(0).setVerzendendId("id.pers.naam1");

        // Bijhoudings gemeente groep
        persoon.setBijhoudingsgemeente(new PersoonBijhoudingsgemeenteGroepBericht());
        persoon.getBijhoudingsgemeente().setBijhoudingsgemeente(gemeenteGeboorte);
        persoon.getBijhoudingsgemeente().setDatumInschrijvingInGemeente(new Datum(datumGeboorte));
        persoon.getBijhoudingsgemeente().setVerzendendId("id.pers.bijhg");

        // Bijhoudings verantwoordelijk groep
        persoon.setBijhoudingsverantwoordelijkheid(new PersoonBijhoudingsverantwoordelijkheidGroepBericht());
        persoon.getBijhoudingsverantwoordelijkheid().setVerzendendId("id.pers.verantw");

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link PersoonVoornaamBericht} met opgegeven voornaam.
     *
     * @param voornaam de voornaam
     * @return een nieuwe voornaam
     */
    public static PersoonVoornaamBericht bouwPersoonVoornaam(final String voornaam) {
        final PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setGegevens(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaam.getGegevens().setVoornaam(new Voornaam(voornaam));
        return persoonVoornaam;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht} met opgegeven voorvoegsel en
     * geslachtsnaam.
     *
     * @param voorvoegsel het voorvoegsel
     * @param geslachtsnaam de geslachtsnaam
     * @return een nieuw geslachtsnaamcomponent
     */
    public static PersoonGeslachtsnaamcomponentBericht bouwGeslachtsnaamcomponent(final String voorvoegsel,
        final String geslachtsnaam)
    {
        final PersoonGeslachtsnaamcomponentBericht persoonGeslachtsnaamcomponent =
            new PersoonGeslachtsnaamcomponentBericht();
        persoonGeslachtsnaamcomponent.setVolgnummer(new Volgnummer(1));
        persoonGeslachtsnaamcomponent.setGegevens(
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        if (voorvoegsel != null) {
            persoonGeslachtsnaamcomponent.getGegevens().setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        }
        persoonGeslachtsnaamcomponent.getGegevens().setGeslachtsnaamcomponent(
            new Geslachtsnaamcomponent(geslachtsnaam));
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     * @param personen De personen waarvoor de NL nationaliteit moet worden toegevoegd.
     */
    public static void voegNederlandseNationaliteitToe(final PersoonModel... personen) {
        for (PersoonModel persoonModel : personen) {
            persoonModel.getNationaliteiten().add(new PersoonNationaliteitModel(bouwPersoonNationaliteit((short) 1),
                        persoonModel));

        }
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     * @param personen De personen waarvoor de NL nationaliteit moet worden toegevoegd.
     */
    public static void voegNationaliteitenToe(final PersoonModel persoon, final PersoonNationaliteit ... nationaliteiten) {
        for (PersoonNationaliteit nation : nationaliteiten) {
            persoon.getNationaliteiten().add(new PersoonNationaliteitModel(nation, persoon));

        }
    }

    /**
     * Voeg een indicatie toe aan een persoon.
     * @param srtIndicatie Soort indicatie die toegevoegd moet worden.
     * @param personen Personen die de indicatie moeten krijgen.
     */
    public static void voegPersoonIndicatieToe(final SoortIndicatie srtIndicatie, final PersoonModel... personen) {
        for (PersoonModel persoonModel : personen) {
            final PersoonIndicatieBericht ind = new PersoonIndicatieBericht();
            ind.setVerzendendId("id.pers.ind1");
            ind.setGegevens(new PersoonIndicatieStandaardGroepBericht());
            ind.getGegevens().setSoort(srtIndicatie);
            ind.getGegevens().setWaarde(Ja.Ja);
            persoonModel.getIndicaties().add(new PersoonIndicatieModel(ind));
        }
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final short code) {
        Nationaliteit nation = new Nationaliteit();
        nation.setNationaliteitcode(new Nationaliteitcode(code));
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setVerzendendId("id.pers.nat1");
        persoonNationaliteitBericht.setGegevens(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getNationaliteitcode())) {
            RedenVerkrijgingNLNationaliteit rdn = new RedenVerkrijgingNLNationaliteit();
            ReflectionTestUtils.setField(rdn, "code", new RedenVerkrijgingCode((short) 17));
            persoonNationaliteitBericht.getGegevens().setRedenVerkregenNlNationaliteit(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final Nationaliteit nation) {
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setVerzendendId("id.pers.nat1");
        persoonNationaliteitBericht.setGegevens(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getNationaliteitcode())) {
            RedenVerkrijgingNLNationaliteit rdn = new RedenVerkrijgingNLNationaliteit();
            ReflectionTestUtils.setField(rdn, "code", new RedenVerkrijgingCode((short) 17));
            persoonNationaliteitBericht.getGegevens().setRedenVerkregenNlNationaliteit(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(
            final Integer datumOverlijden, final Plaats woonplaats, final Partij gemeente, final Land land)
    {
        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setVerzendendId("id.pers.overlijden");
        overlijden.setDatumOverlijden(new Datum(datumOverlijden));
        overlijden.setWoonplaatsOverlijden(woonplaats);
        overlijden.setOverlijdenGemeente(gemeente);
        overlijden.setLandOverlijden(land);
        return overlijden;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(
            final Integer datumOverlijden, final String buitenlandseplaats,
            final String regio, final Land land, final String locatie)
    {
        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setVerzendendId("id.pers.overlijden");
        overlijden.setDatumOverlijden(new Datum(datumOverlijden));
        if (StringUtils.isNotBlank(buitenlandseplaats)) {
            overlijden.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaats(buitenlandseplaats));
        }
        if (StringUtils.isNotBlank(regio)) {
            overlijden.setBuitenlandseRegioOverlijden(new BuitenlandseRegio(regio));
        }
        overlijden.setLandOverlijden(land);
        if (StringUtils.isNotBlank(locatie)) {
            overlijden.setOmschrijvingLocatieOverlijden(new LocatieOmschrijving(locatie));
        }
        return overlijden;
    }
}
