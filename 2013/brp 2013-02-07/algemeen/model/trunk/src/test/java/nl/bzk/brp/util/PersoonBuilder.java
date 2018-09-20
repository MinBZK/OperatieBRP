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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsgemeenteGroepBericht;
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

    public static PersoonBericht bouwRefererendPersoon(final Integer bsn) {
        final PersoonBericht persoon = new PersoonBericht();

        // Groep identificatie nummers
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        persoon.setCommunicatieID(PrefixBuilder.getPrefix() + "id.pers");

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
    public static PersoonBericht bouwPersoon(final Integer bsn, final Geslachtsaanduiding geslachtsaanduiding,
            final Integer datumGeboorte, final Partij gemeenteGeboorte, final String voornaam,
            final String voorvoegsel, final String geslachtsnaam)
    {
        String prefix = PrefixBuilder.getPrefix();

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setCommunicatieID(prefix + "id.pers");

        // Groep identificatie nummers
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        persoon.getIdentificatienummers().setCommunicatieID(prefix + "id.pers.idnrs");

        // Groep geslachts aanduiding
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.getGeslachtsaanduiding().setGeslachtsaanduiding(geslachtsaanduiding);
        persoon.getGeslachtsaanduiding().setCommunicatieID(prefix + "id.pers.geslacht");

        // Groep geboorte
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(datumGeboorte));
        persoon.getGeboorte().setLandGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        persoon.getGeboorte().setGemeenteGeboorte(gemeenteGeboorte);
        persoon.getGeboorte().setCommunicatieID(prefix + "id.pers.geboorte");

        // Objecttype persoonvoornaam
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        persoon.getVoornamen().add(bouwPersoonVoornaam(1, voornaam));
        persoon.getVoornamen().get(0).setCommunicatieID(prefix + "id.pers.vnaam1");

        // Objecttype geslachtsnaam component
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(bouwGeslachtsnaamcomponent(1, voorvoegsel, geslachtsnaam));
        persoon.getGeslachtsnaamcomponenten().get(0).setCommunicatieID(prefix + "id.pers.naam1");

        // Bijhoudings gemeente groep
        persoon.setBijhoudingsgemeente(new PersoonBijhoudingsgemeenteGroepBericht());
        persoon.getBijhoudingsgemeente().setBijhoudingsgemeente(gemeenteGeboorte);
        persoon.getBijhoudingsgemeente().setDatumInschrijvingInGemeente(new Datum(datumGeboorte));
        persoon.getBijhoudingsgemeente().setCommunicatieID(prefix + "id.pers.bijhg");

        // Bijhoudings verantwoordelijk groep
        persoon.setBijhoudingsaard(new PersoonBijhoudingsaardGroepBericht());
        persoon.getBijhoudingsaard().setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
        persoon.getBijhoudingsaard().setCommunicatieID(prefix + "id.pers.verantw");

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link PersoonVoornaamBericht} met opgegeven voornaam.
     *
     * @param volgNummer de volgNummer
     * @param voornaam de voornaam
     * @return een nieuwe voornaam
     */
    public static PersoonVoornaamBericht bouwPersoonVoornaam(final Integer volgNummer, final String voornaam) {
        String prefix = PrefixBuilder.getPrefix();

        final PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        if (volgNummer != null) {
            persoonVoornaam.setVolgnummer(new Volgnummer(volgNummer));
        }
        persoonVoornaam.setCommunicatieID(prefix + "id.pers.voorn" + volgNummer);
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
    public static PersoonGeslachtsnaamcomponentBericht bouwGeslachtsnaamcomponent(
            final Integer volgNummer,
            final String voorvoegsel,
            final String geslachtsnaam)
    {
        String prefix = PrefixBuilder.getPrefix();

        final PersoonGeslachtsnaamcomponentBericht persoonGeslachtsnaamcomponent =
                new PersoonGeslachtsnaamcomponentBericht();
        if (null != volgNummer) {
            persoonGeslachtsnaamcomponent.setVolgnummer(new Volgnummer(volgNummer));
        }
        persoonGeslachtsnaamcomponent.setCommunicatieID(prefix + "id.pers.voorn" + volgNummer);
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
            PersoonNationaliteitModel nation =
                    new PersoonNationaliteitModel(bouwPersoonNationaliteit(BrpConstanten.NL_NATIONALITEIT_CODE.toString()),
                            persoonModel);
            nation.setPersoonNationaliteitStatusHis(StatusHistorie.A);
            persoonModel.getNationaliteiten().add(nation);
        }
    }

    /** Voeg de Nederlandse nationaliteit toe aan de personen. */
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

        String prefix = PrefixBuilder.getPrefix();
        int volgNummer = 0;
        for (PersoonModel persoonModel : personen) {
            volgNummer++;
            final PersoonIndicatieBericht ind = new PersoonIndicatieBericht(srtIndicatie);
            ind.setCommunicatieID(prefix + "id.pers.ind" + volgNummer);
            ind.setStandaard(new PersoonIndicatieStandaardGroepBericht());
            ind.getStandaard().setWaarde(Ja.J);

            PersoonIndicatieModel persIndicatieModel = new PersoonIndicatieModel(ind, persoonModel);
            persIndicatieModel.setPersoonIndicatieStatusHis(StatusHistorie.A);
            persoonModel.getIndicaties().add(persIndicatieModel);
        }
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final String code) {
        String prefix = PrefixBuilder.getPrefix();

        Nationaliteit nation = StatischeObjecttypeBuilder.bouwNationaliteit(code, null);
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID(prefix + "id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getCode())) {
            RedenVerkrijgingNLNationaliteit rdn =
                    StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit((short) 17);
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final Nationaliteit nation) {
        String prefix = PrefixBuilder.getPrefix();

        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID(prefix + "id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nation.getCode())) {
            RedenVerkrijgingNLNationaliteit rdn =
                    StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit((short) 17);
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final Plaats woonplaats, final Partij gemeente, final Land land)
    {
        String prefix = PrefixBuilder.getPrefix();

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID(prefix + "id.pers.overlijden");
        overlijden.setDatumOverlijden(new Datum(datumOverlijden));
        overlijden.setWoonplaatsOverlijden(woonplaats);
        overlijden.setGemeenteOverlijden(gemeente);
        overlijden.setLandOverlijden(land);
        return overlijden;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final String buitenlandseplaats, final String regio, final Land land, final String locatie)
    {
        String prefix = PrefixBuilder.getPrefix();

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID(prefix + "id.pers.overlijden");
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
