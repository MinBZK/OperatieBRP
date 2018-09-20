/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import org.apache.commons.lang.StringUtils;


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
            final Integer datumGeboorte, final Partij gemeenteGeboorte, final String voornaam,
            final String voorvoegsel, final String geslachtsnaam)
    {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setCommunicatieID("id.pers");

        // Groep identificatie nummers
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        persoon.getIdentificatienummers().setCommunicatieID("id.pers.idnrs");

        // Groep geslachts aanduiding
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.getGeslachtsaanduiding().setGeslachtsaanduiding(geslachtsaanduiding);
        persoon.getGeslachtsaanduiding().setCommunicatieID("id.pers.geslacht");

        // Groep geboorte
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(datumGeboorte));
        persoon.getGeboorte().setLandGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        persoon.getGeboorte().setGemeenteGeboorte(gemeenteGeboorte);
        persoon.getGeboorte().setCommunicatieID("id.pers.geboorte");

        // Objecttype persoonvoornaam
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        persoon.getVoornamen().add(bouwPersoonVoornaam(voornaam));
        persoon.getVoornamen().get(0).setCommunicatieID("id.pers.vnaam1");

        // Objecttype geslachtsnaam component
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(bouwGeslachtsnaamcomponent(voorvoegsel, geslachtsnaam));
        persoon.getGeslachtsnaamcomponenten().get(0).setCommunicatieID("id.pers.naam1");

        // Bijhoudings gemeente groep
        persoon.setBijhoudingsgemeente(new PersoonBijhoudingsgemeenteGroepBericht());
        persoon.getBijhoudingsgemeente().setBijhoudingsgemeente(gemeenteGeboorte);
        persoon.getBijhoudingsgemeente().setDatumInschrijvingInGemeente(new Datum(datumGeboorte));
        persoon.getBijhoudingsgemeente().setCommunicatieID("id.pers.bijhg");

        // Bijhoudings verantwoordelijk groep
        persoon.setBijhoudingsaard(new PersoonBijhoudingsaardGroepBericht());
        persoon.getBijhoudingsaard().setCommunicatieID("id.pers.verantw");

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
        persoonVoornaam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaam.getStandaard().setNaam(new Voornaam(voornaam));
        return persoonVoornaam;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht} met
     * opgegeven voorvoegsel en
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
        persoonGeslachtsnaamcomponent.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        if (voorvoegsel != null) {
            persoonGeslachtsnaamcomponent.getStandaard().setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        }
        persoonGeslachtsnaamcomponent.getStandaard().setNaam(new Geslachtsnaamcomponent(geslachtsnaam));
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     *
     * @param personen De personen waarvoor de NL nationaliteit moet worden toegevoegd.
     */
    public static void voegNederlandseNationaliteitToe(final PersoonModel... personen) {
        for (PersoonModel persoonModel : personen) {
            PersoonNationaliteitModel nation = new PersoonNationaliteitModel(bouwPersoonNationaliteit(BrpConstanten.NL_NATIONALITEIT_CODE.toString()), persoonModel);
            nation.setPersoonNationaliteitStatusHis(StatusHistorie.A);
            persoonModel.getNationaliteiten().add(nation);
        }
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     *
     * @param personen De personen waarvoor de NL nationaliteit moet worden toegevoegd.
     */
    public static void voegNationaliteitenToe(final PersoonModel persoon,
        final PersoonNationaliteit... nationaliteiten)
    {
        for (PersoonNationaliteit nation : nationaliteiten) {
            PersoonNationaliteitModel nationModel = new PersoonNationaliteitModel(nation, persoon);
            nationModel.setPersoonNationaliteitStatusHis(StatusHistorie.A);
            persoon.getNationaliteiten().add(nationModel);

        }
    }

    /**
     * Voeg een indicatie toe aan een persoon.
     *
     * @param srtIndicatie Soort indicatie die toegevoegd moet worden.
     * @param personen Personen die de indicatie moeten krijgen.
     */
    public static void voegPersoonIndicatieToe(final SoortIndicatie srtIndicatie, final PersoonModel... personen) {
        for (PersoonModel persoonModel : personen) {
            final PersoonIndicatieBericht ind = new PersoonIndicatieBericht(srtIndicatie);
            ind.setCommunicatieID("id.pers.ind1");
            ind.setStandaard(new PersoonIndicatieStandaardGroepBericht());
            ind.getStandaard().setWaarde(Ja.J);

            PersoonIndicatieModel persIndicatieModel = new PersoonIndicatieModel(ind, persoonModel);
            persIndicatieModel.setPersoonIndicatieStatusHis(StatusHistorie.A);
            persoonModel.getIndicaties().add(persIndicatieModel);
        }
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final String code) {
        Nationaliteit nation = StatischeObjecttypeBuilder.bouwNationaliteit(code, null);
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID("id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getCode())) {
            RedenVerkrijgingNLNationaliteit rdn = StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit("17");
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final Nationaliteit nation) {
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID("id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getCode())) {
            RedenVerkrijgingNLNationaliteit rdn = StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit("17");
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final Plaats woonplaats, final Partij gemeente, final Land land)
    {
        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID("id.pers.overlijden");
        overlijden.setDatumOverlijden(new Datum(datumOverlijden));
        overlijden.setWoonplaatsOverlijden(woonplaats);
        overlijden.setGemeenteOverlijden(gemeente);
        overlijden.setLandOverlijden(land);
        return overlijden;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final String buitenlandseplaats, final String regio, final Land land, final String locatie)
    {
        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID("id.pers.overlijden");
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
