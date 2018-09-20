/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import org.apache.commons.lang.StringUtils;


/**
 * Utility class voor het construeren van Persoon instanties.
 */
public final class PersoonBuilder {

    /**
     * Empty en private constructor daar utility classes niet geinstantieerd dienen te worden.
     */
    private PersoonBuilder() {
    }

    public static PersoonBericht bouwRefererendPersoon(final Integer bsn) {
        final PersoonBericht persoon = new PersoonBericht();
        if (bsn != null) {
            persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
            persoon.setObjectSleutel(bsn.toString());
            // Groep identificatie nummers
            persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            persoon.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        }
        persoon.setCommunicatieID(PrefixBuilder.getPrefix() + "id.pers");

        return persoon;
    }

    public static PersoonBericht bouwNietIngeschrevene(final Integer datumGeboorte, final String voornaam,
            final String geslachtsnaam, final Geslachtsaanduiding geslacht)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
        persoon.getGeboorte().setLandGebiedGeboorte(StatischeObjecttypeBuilder.LAND_AFGANISTAN);
        persoon.getGeboorte().setCommunicatieID(prefix + "id.pers.geboorte");

        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.getGeslachtsaanduiding().setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(geslacht));
        persoon.getGeslachtsaanduiding().setCommunicatieID(prefix + "id.pers.geslacht");

        if (null != voornaam) {
            // Objecttype persoonvoornaam
            persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
            persoon.getVoornamen().add(bouwPersoonVoornaam(1, voornaam));
            persoon.getVoornamen().get(0).setCommunicatieID(prefix + "id.pers.vnaam1");

        }
        // Objecttype geslachtsnaam component
        final PersoonGeslachtsnaamcomponentBericht naamComp = bouwGeslachtsnaamcomponent(1, null, geslachtsnaam);
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(naamComp);
        persoon.getGeslachtsnaamcomponenten().get(0).setCommunicatieID(prefix + "id.pers.naam1");

        persoon.setSamengesteldeNaam(bouwPersoonSamengesteldeNaamGroepBericht(null == naamComp.getStandaard().getStam()
                        ? null : naamComp.getStandaard().getStam().getWaarde(), voornaam,
                null == naamComp.getStandaard().getVoorvoegsel() ? null : naamComp.getStandaard().getVoorvoegsel().getWaarde(),
                null == naamComp.getStandaard().getScheidingsteken() ? null : naamComp.getStandaard().getScheidingsteken().getWaarde(),
                naamComp.getStandaard().getAdellijkeTitel(), naamComp.getStandaard().getPredicaat(), false, true));

        // Groep identificatie nummers
        persoon.setCommunicatieID(PrefixBuilder.getPrefix() + "id.pers");

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe Persoon instantie met de opgegeven eigenschappen en enkele standaard
     * waardes voor enkele velden.
     *
     * @param bsn                 het burgerservicenummer
     * @param datumGeboorte       de geboorte datum
     * @param gemeenteGeboorte    de geboorte gemeente
     * @param bijhoudingsGemeente de bijhoudings gemeente als partij
     * @param voornaam            de voornaam
     * @param voorvoegsel         het voorvoegsel
     * @param geslachtsnaam       de geslachtsnaam
     * @return een nieuw persoon
     */
    public static PersoonBericht bouwPersoon(final SoortPersoon soortPersoon, final Integer bsn,
            final Geslachtsaanduiding geslachtsaanduiding, final Integer datumGeboorte,
            final Gemeente gemeenteGeboorte, final Partij bijhoudingsGemeente, final String voornaam,
            final String voorvoegsel, final String geslachtsnaam)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(soortPersoon));
        persoon.setCommunicatieID(prefix + "id.pers");
        if (null != bsn) {
            persoon.setObjectSleutel(bsn.toString());

            // Groep identificatie nummers
            persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            persoon.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
            persoon.getIdentificatienummers().setCommunicatieID(prefix + "id.pers.idnrs");
        }

        if (geslachtsaanduiding != null) {
            // Groep geslachts aanduiding
            persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
            persoon.getGeslachtsaanduiding().setGeslachtsaanduiding(
                    new GeslachtsaanduidingAttribuut(geslachtsaanduiding));
            persoon.getGeslachtsaanduiding().setCommunicatieID(prefix + "id.pers.geslacht");

        }

        if (datumGeboorte != null) {
            // Groep geboorte
            persoon.setGeboorte(new PersoonGeboorteGroepBericht());
            persoon.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
            persoon.getGeboorte().setLandGebiedGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);
            persoon.getGeboorte().setGemeenteGeboorte(new GemeenteAttribuut(gemeenteGeboorte));
            persoon.getGeboorte().setCommunicatieID(prefix + "id.pers.geboorte");
        }

        if (voornaam != null) {
            // Objecttype persoonvoornaam
            persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
            persoon.getVoornamen().add(bouwPersoonVoornaam(1, voornaam));
            persoon.getVoornamen().get(0).setCommunicatieID(prefix + "id.pers.vnaam1");
        }

        if (geslachtsnaam != null) {
            // Objecttype geslachtsnaam component
            final PersoonGeslachtsnaamcomponentBericht naamComp = bouwGeslachtsnaamcomponent(1, voorvoegsel, geslachtsnaam);
            persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
            persoon.getGeslachtsnaamcomponenten().add(naamComp);
            persoon.getGeslachtsnaamcomponenten().get(0).setCommunicatieID(prefix + "id.pers.naam1");

            persoon.setSamengesteldeNaam(bouwPersoonSamengesteldeNaamGroepBericht(null == naamComp.getStandaard()
                            .getStam() ? null : naamComp.getStandaard().getStam().getWaarde(), voornaam,
                    null == naamComp
                            .getStandaard().getVoorvoegsel() ? null : naamComp.getStandaard().getVoorvoegsel().getWaarde(),
                    null == naamComp.getStandaard().getScheidingsteken() ? null : naamComp.getStandaard()
                            .getScheidingsteken().getWaarde(), naamComp.getStandaard().getAdellijkeTitel(), naamComp
                            .getStandaard().getPredicaat(), false, true));
        }

        persoon.setBijhouding(new PersoonBijhoudingGroepBericht());
        persoon.getBijhouding().setCommunicatieID(prefix + "id.pers.bijh");
        if (bijhoudingsGemeente != null) {
            persoon.getBijhouding().setBijhoudingspartij(new PartijAttribuut(bijhoudingsGemeente));
        }

        if (soortPersoon == SoortPersoon.INGESCHREVENE) {
            persoon.getBijhouding().setBijhoudingsaard(new BijhoudingsaardAttribuut(Bijhoudingsaard.INGEZETENE));
        }

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link PersoonVoornaamBericht} met opgegeven voornaam.
     *
     * @param volgNummer de volgNummer
     * @param voornaam   de voornaam
     * @return een nieuwe voornaam
     */
    public static PersoonVoornaamBericht bouwPersoonVoornaam(final Integer volgNummer, final String voornaam) {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        if (volgNummer != null) {
            persoonVoornaam.setVolgnummer(new VolgnummerAttribuut(volgNummer));
        }
        persoonVoornaam.setCommunicatieID(prefix + "id.pers.voorn" + volgNummer);
        persoonVoornaam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaam.getStandaard().setNaam(new VoornaamAttribuut(voornaam));
        return persoonVoornaam;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht} met
     * opgegeven voorvoegsel en
     * geslachtsnaam.
     *
     * @param voorvoegsel   het voorvoegsel
     * @param geslachtsnaam de geslachtsnaam
     * @return een nieuw geslachtsnaamcomponent
     */
    public static PersoonGeslachtsnaamcomponentBericht bouwGeslachtsnaamcomponent(final Integer volgNummer,
            final String voorvoegsel, final String geslachtsnaam)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonGeslachtsnaamcomponentBericht persoonGeslachtsnaamcomponent =
                new PersoonGeslachtsnaamcomponentBericht();
        if (null != volgNummer) {
            persoonGeslachtsnaamcomponent.setVolgnummer(new VolgnummerAttribuut(volgNummer));
        }
        persoonGeslachtsnaamcomponent.setCommunicatieID(prefix + "id.pers.voorn" + volgNummer);
        persoonGeslachtsnaamcomponent.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        if (voorvoegsel != null) {
            persoonGeslachtsnaamcomponent.getStandaard().setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
            persoonGeslachtsnaamcomponent.getStandaard().setScheidingsteken(new ScheidingstekenAttribuut(" "));
        }
        persoonGeslachtsnaamcomponent.getStandaard().setStam(new GeslachtsnaamstamAttribuut(geslachtsnaam));
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     *
     * @param personen De personen waarvoor de NL nationaliteit moet worden toegevoegd.
     */
    public static void voegNederlandseNationaliteitToe(final PersoonModel... personen) {

        for (final PersoonModel persoonModel : personen) {
            final PersoonNationaliteitModel nation =
                    new PersoonNationaliteitModel(
                            bouwPersoonNationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.toString()),
                            persoonModel);
            persoonModel.getNationaliteiten().add(nation);
        }
    }

    /**
     * Voeg de Nederlandse nationaliteit toe aan de personen.
     */
    public static void voegNationaliteitenToe(final PersoonModel persoon, final PersoonNationaliteit... nationaliteiten) {
        for (final PersoonNationaliteit nation : nationaliteiten) {
            final PersoonNationaliteitModel nationModel = new PersoonNationaliteitModel(nation, persoon);
            persoon.getNationaliteiten().add(nationModel);

        }
    }

    /**
     * Voeg een indicatie toe aan een persoon.
     *
     * @param srtIndicatie Soort indicatie die toegevoegd moet worden.
     * @param personen     Personen die de indicatie moeten krijgen.
     */
    public static void voegPersoonIndicatieToe(final SoortIndicatie srtIndicatie, final PersoonModel... personen) {

        final String prefix = PrefixBuilder.getPrefix();
        int volgNummer = 0;
        for (final PersoonModel persoonModel : personen) {
            volgNummer++;
            final PersoonIndicatieBericht ind = new PersoonIndicatieBericht(new SoortIndicatieAttribuut(srtIndicatie));
            ind.setCommunicatieID(prefix + "id.pers.ind" + volgNummer);
            ind.setStandaard(new PersoonIndicatieStandaardGroepBericht());
            ind.getStandaard().setWaarde(new JaAttribuut(Ja.J));

            final PersoonIndicatieModel persIndicatieModel = new PersoonIndicatieModel(ind, persoonModel);
            persoonModel.getIndicaties().add(persIndicatieModel);
        }
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final String code) {
        final String prefix = PrefixBuilder.getPrefix();

        final NationaliteitAttribuut nation = StatischeObjecttypeBuilder.bouwNationaliteit(code, null);
        final PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID(prefix + "id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(nation);
        if (NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.equals(nation.getWaarde().getCode())) {
            final RedenVerkrijgingNLNationaliteitAttribuut rdn =
                    StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit((short) 17);
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonNationaliteitBericht bouwPersoonNationaliteit(final Nationaliteit nation) {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setCommunicatieID(prefix + "id.pers.nat1");
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.setNationaliteit(new NationaliteitAttribuut(nation));
        if (NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.equals(nation.getCode())) {
            final RedenVerkrijgingNLNationaliteitAttribuut rdn =
                    StatischeObjecttypeBuilder.bouwRedenVerkrijgingNLNationaliteit((short) 17);
            persoonNationaliteitBericht.getStandaard().setRedenVerkrijging(rdn);
        }
        return persoonNationaliteitBericht;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final NaamEnumeratiewaardeAttribuut woonplaats, final Gemeente gemeente, final LandGebied landGebied)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID(prefix + "id.pers.overlijden");
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
        overlijden.setWoonplaatsnaamOverlijden(woonplaats);
        overlijden.setGemeenteOverlijden(new GemeenteAttribuut(gemeente));
        overlijden.setLandGebiedOverlijden(new LandGebiedAttribuut(landGebied));
        return overlijden;
    }

    public static PersoonOverlijdenGroepBericht bouwPersoonOverlijdenGroepbericht(final Integer datumOverlijden,
            final String buitenlandseplaats, final String regio, final LandGebied landGebied, final String locatie)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID(prefix + "id.pers.overlijden");
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
        if (StringUtils.isNotBlank(buitenlandseplaats)) {
            overlijden.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaatsAttribuut(buitenlandseplaats));
        }
        if (StringUtils.isNotBlank(regio)) {
            overlijden.setBuitenlandseRegioOverlijden(new BuitenlandseRegioAttribuut(regio));
        }
        overlijden.setLandGebiedOverlijden(new LandGebiedAttribuut(landGebied));
        if (StringUtils.isNotBlank(locatie)) {
            overlijden.setOmschrijvingLocatieOverlijden(new LocatieomschrijvingAttribuut(locatie));
        }
        return overlijden;
    }

    public static PersoonNaamgebruikGroepBericht bouwPersoonNaamgebruikGroepbericht(final AdellijkeTitel adTitel,
            final Predicaat predicaat, final String voornamen, final String voorvoegsel, final String scheidingsTeken,
            final String naam, final JaNeeAttribuut indAlgorthm, final JaNeeAttribuut indPredikaat,
            final Naamgebruik naamgebruikAttribuut)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonNaamgebruikGroepBericht naamgebruik = new PersoonNaamgebruikGroepBericht();
        naamgebruik.setCommunicatieID(prefix + "id.pers.naamgebruik");
        naamgebruik.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(adTitel));
        if (null != naam) {
            naamgebruik.setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut(naam));
        }
        naamgebruik.setIndicatieNaamgebruikAfgeleid(indAlgorthm);
        naamgebruik.setNaamgebruik(new NaamgebruikAttribuut(naamgebruikAttribuut));
        naamgebruik.setPredicaatNaamgebruik(new PredicaatAttribuut(predicaat));
        if (null != scheidingsTeken) {
            naamgebruik.setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut(scheidingsTeken));
        }
        if (null != voornamen) {
            naamgebruik.setVoornamenNaamgebruik(new VoornamenAttribuut(voornamen));
        }
        if (null != voorvoegsel) {
            naamgebruik.setVoorvoegselNaamgebruik(new VoorvoegselAttribuut(voorvoegsel));
        }

        return naamgebruik;
    }

    public static PersoonSamengesteldeNaamGroepBericht bouwPersoonSamengesteldeNaamGroepBericht(final String naam,
            final String voornamen, final String voorvoegsel, final String scheidingsTeken,
            final AdellijkeTitelAttribuut adelijkeTitel, final PredicaatAttribuut predicaat, final boolean namenReeks,
            final boolean algorithmischAfgeleid)
    {
        final String prefix = PrefixBuilder.getPrefix();

        final PersoonSamengesteldeNaamGroepBericht sng = new PersoonSamengesteldeNaamGroepBericht();
        sng.setCommunicatieID(prefix + "id.pers.samgestnaam");
        if (null != adelijkeTitel) {
            sng.setAdellijkeTitel(adelijkeTitel);
        }
        if (null != naam) {
            sng.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut(naam));
        }
        sng.setIndicatieNamenreeks(namenReeks ? JaNeeAttribuut.JA : JaNeeAttribuut.NEE);
        if (null != predicaat) {
            sng.setPredicaat(predicaat);
        }
        sng.setIndicatieAfgeleid(algorithmischAfgeleid ? JaNeeAttribuut.JA : JaNeeAttribuut.NEE);
        if (null != scheidingsTeken) {
            sng.setScheidingsteken(new ScheidingstekenAttribuut(scheidingsTeken));
        }
        if (null != voornamen) {
            sng.setVoornamen(new VoornamenAttribuut(voornamen));
        }
        if (null != voorvoegsel) {
            sng.setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
        }
        return sng;
    }
}
