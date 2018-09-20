/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.domein.kern.AdellijkeTitel;
import nl.bzk.brp.domein.kern.Betrokkenheid;
import nl.bzk.brp.domein.kern.Geslachtsaanduiding;
import nl.bzk.brp.domein.kern.Land;
import nl.bzk.brp.domein.kern.MultiRealiteitRegel;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonAdres;
import nl.bzk.brp.domein.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.domein.kern.PersoonIndicatie;
import nl.bzk.brp.domein.kern.PersoonNationaliteit;
import nl.bzk.brp.domein.kern.PersoonReisdocument;
import nl.bzk.brp.domein.kern.PersoonVerificatie;
import nl.bzk.brp.domein.kern.PersoonVoornaam;
import nl.bzk.brp.domein.kern.Plaats;
import nl.bzk.brp.domein.kern.Predikaat;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.SoortPersoon;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import nl.bzk.brp.domein.kern.Verblijfsrecht;
import nl.bzk.brp.domein.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.domein.lev.LeveringPersoon;


/**
 * Proxy die alleen gegevenselementen doorgeeft als deze in het abonnement zitten.
 */
public class PersoonProxy extends AbstractAutorisatieProxy implements Persoon {

    private static final String VELD_PREFIX = Persoon.class.getSimpleName().toLowerCase() + ".";

    private final Persoon       persoon;

    private List<PersoonAdres>  adressenProxies;

    /**
     * Constructor.
     *
     * @param persoon de persoon waarvoor dit object een proxy moet zijn
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    public PersoonProxy(final Persoon persoon, final Set<String> toegestaneVelden) {
        super(toegestaneVelden);
        this.persoon = persoon;
        initialiseerAdressenProxies(toegestaneVelden);
    }

    /**
     * Creeert voor elk adres van de persoon een proxy en voegt die toe aan de verzameling met proxies.
     * Deze proxies zullen worden teruggegeven door de getAdressen methode in plaats van de adresssen zelf.
     *
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    private void initialiseerAdressenProxies(final Set<String> toegestaneVelden) {
        if (persoon.getPersoonAdresen() != null) {
            adressenProxies = new ArrayList<PersoonAdres>();
            for (PersoonAdres adres : persoon.getPersoonAdresen()) {
                adressenProxies.add(new PersoonAdresProxy(adres, toegestaneVelden));
            }
        }
    }

    /**
     * @param persoon de persoon zelf of de proxy ervan
     * @return de persoon zelf
     */
    public static Persoon getPersoon(final Persoon persoon) {
        Persoon resultaat;
        if (persoon instanceof PersoonProxy) {
            resultaat = ((PersoonProxy) persoon).persoon;
        } else {
            resultaat = persoon;
        }
        return resultaat;
    }

    @Override
    public Long getID() {
        return filter(persoon.getID(), VELD_PREFIX + "Id");
    }

    @Override
    public SoortPersoon getSoort() {
        return filter(persoon.getSoort(), VELD_PREFIX + "Soort");
    }

    @Override
    public String getBurgerservicenummer() {
        return filter(persoon.getBurgerservicenummer(), VELD_PREFIX + "Burgerservicenummer");
    }

    @Override
    public String getAdministratienummer() {
        return filter(persoon.getAdministratienummer(), VELD_PREFIX + "Administratienummer");
    }

    @Override
    public String getGeslachtsnaam() {
        return filter(persoon.getGeslachtsnaam(), VELD_PREFIX + "Geslachtsnaam");
    }

    @Override
    public String getVoornamen() {
        return filter(persoon.getVoornamen(), VELD_PREFIX + "Voornamen");
    }

    @Override
    public String getVoorvoegsel() {
        return filter(persoon.getVoorvoegsel(), VELD_PREFIX + "Voorvoegsel");
    }

    @Override
    public String getScheidingsteken() {
        return filter(persoon.getScheidingsteken(), VELD_PREFIX + "ScheidingsTeken");
    }

    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return filter(persoon.getGeslachtsaanduiding(), VELD_PREFIX + "GeslachtsAanduiding");
    }

    @Override
    public Integer getDatumGeboorte() {
        return filter(persoon.getDatumGeboorte(), VELD_PREFIX + "DatumGeboorte");
    }

    @Override
    public Partij getBijhoudingsgemeente() {
        return filter(persoon.getBijhoudingsgemeente(), VELD_PREFIX + "BijhoudingsGemeente");
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return filter(persoon.getBuitenlandseGeboorteplaats(), VELD_PREFIX + "BuitenlandseGeboorteplaats");
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return filter(persoon.getBuitenlandsePlaatsOverlijden(), VELD_PREFIX + "BuitenlandsePlaatsOverlijden");
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return filter(persoon.getBuitenlandseRegioGeboorte(), VELD_PREFIX + "BuitenlandseRegioGeboorte");
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return filter(persoon.getGemeenteGeboorte(), VELD_PREFIX + "GemeenteGeboorte");
    }

    @Override
    public Land getLandGeboorte() {
        return filter(persoon.getLandGeboorte(), VELD_PREFIX + "LandGeboorte");
    }

    @Override
    public List<PersoonAdres> getPersoonAdresen() {
        return adressenProxies;
    }

    @Override
    public String getOmschrijvingGeboortelocatie() {
        return filter(persoon.getOmschrijvingGeboortelocatie(), VELD_PREFIX + "OmschrijvingGeboorteLocatie");
    }

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0047
     */
    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return persoon.getRedenOpschortingBijhouding();
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return filter(persoon.getWoonplaatsGeboorte(), VELD_PREFIX + "WoonplaatsGeboorte");
    }

    @Override
    public Boolean getBehandeldAlsNederlander() {
        return filter(persoon.getBehandeldAlsNederlander(), VELD_PREFIX + "behandeldAlsNederlander");
    }

    @Override
    public Boolean getBelemmeringVerstrekkingReisdocument() {
        return filter(persoon.getBelemmeringVerstrekkingReisdocument(), VELD_PREFIX
            + "belemmeringVerstrekkingReisdocument");
    }

    @Override
    public Boolean getBezitBuitenlandsReisdocument() {
        return filter(persoon.getBezitBuitenlandsReisdocument(), VELD_PREFIX + "BezitBuitenlandsReisdocument");
    }

    @Override
    public Boolean getIndicatieDeelnameEUVerkiezingen() {
        return filter(persoon.getIndicatieDeelnameEUVerkiezingen(), VELD_PREFIX + "IndicatieDeelnameEUVerkiezingen");
    }

    @Override
    public Boolean getDerdeHeeftGezag() {
        return filter(persoon.getDerdeHeeftGezag(), VELD_PREFIX + "DerdeHeeftGezag");
    }

    @Override
    public Boolean getGepriviligeerde() {
        return filter(persoon.getGepriviligeerde(), VELD_PREFIX + "Gepriviligeerde");
    }

    @Override
    public Boolean getOnderCuratele() {
        return filter(persoon.getOnderCuratele(), VELD_PREFIX + "OnderCuratele");
    }

    @Override
    public Boolean getIndicatieUitsluitingNLKiesrecht() {
        return filter(persoon.getIndicatieUitsluitingNLKiesrecht(), VELD_PREFIX + "IndicatieUitsluitingNLKiesrecht");
    }

    @Override
    public Boolean getVastgesteldNietNederlander() {
        return filter(persoon.getVastgesteldNietNederlander(), VELD_PREFIX + "VastgesteldNietNederlander");
    }

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0047, FTPE0003
     */
    @Override
    public Boolean getVerstrekkingsBeperking() {
        return persoon.getVerstrekkingsBeperking();
    }

    @Override
    public final String toString() {
        return persoon.toString();
    }

    @Override
    public List<Betrokkenheid> getBetrokkenheiden() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBetrokkenheid(final Betrokkenheid element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeBetrokkenheid(final Betrokkenheid element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MultiRealiteitRegel> getMultiRealiteitRegelen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addMultiRealiteitRegel(final MultiRealiteitRegel element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMultiRealiteitRegel(final MultiRealiteitRegel element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setID(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSoort(final SoortPersoon soort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBurgerservicenummer(final String burgerservicenummer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAdministratienummer(final String administratienummer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIdentificatienummersStatusHis() {
        return filter(persoon.getIdentificatienummersStatusHis(), VELD_PREFIX + "IdentificatienummersStatusHis");
    }

    @Override
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGeslachtsaanduidingStatusHis() {
        return filter(persoon.getGeslachtsaanduidingStatusHis(), VELD_PREFIX + "GeslachtsaanduidingStatusHis");
    }

    @Override
    public Predikaat getPredikaat() {
        return filter(persoon.getPredikaat(), VELD_PREFIX + "Predikaat");
    }

    @Override
    public void setPredikaat(final Predikaat predikaat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVoornamen(final String voornamen) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVoorvoegsel(final String voorvoegsel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setScheidingsteken(final String scheidingsteken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return filter(persoon.getAdellijkeTitel(), VELD_PREFIX + "AdellijkeTitel");
    }

    @Override
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGeslachtsnaam(final String geslachtsnaam) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieNamenreeksAlsGeslachtsnaam() {
        return filter(persoon.getIndicatieNamenreeksAlsGeslachtsnaam(), VELD_PREFIX
            + "IndicatieNamenreeksAlsGeslachtsnaam");
    }

    @Override
    public void setIndicatieNamenreeksAlsGeslachtsnaam(final Boolean indicatieNamenreeksAlsGeslachtsnaam) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAlgoritmischAfgeleid() {
        return filter(persoon.getIndicatieAlgoritmischAfgeleid(), VELD_PREFIX + "IndicatieAlgoritmischAfgeleid");
    }

    @Override
    public void setIndicatieAlgoritmischAfgeleid(final Boolean indicatieAlgoritmischAfgeleid) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getSamengesteldeNaamStatusHis() {
        return filter(persoon.getSamengesteldeNaamStatusHis(), VELD_PREFIX + "SamengesteldeNaamStatusHis");
    }

    @Override
    public WijzeGebruikGeslachtsnaam getWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner() {
        return filter(persoon.getWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner(), VELD_PREFIX
            + "WijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner");
    }

    @Override
    public void setWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner(
            final WijzeGebruikGeslachtsnaam wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten() {
        return filter(persoon.getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(), VELD_PREFIX
            + "IndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten");
    }

    @Override
    public void setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(
            final Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return filter(persoon.getIndicatieAanschrijvingAlgoritmischAfgeleid(), VELD_PREFIX
            + "IndicatieAanschrijvingAlgoritmischAfgeleid");
    }

    @Override
    public void setIndicatieAanschrijvingAlgoritmischAfgeleid(final Boolean indicatieAanschrijvingAlgoritmischAfgeleid)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predikaat getPredikaatAanschrijving() {
        return filter(persoon.getPredikaatAanschrijving(), VELD_PREFIX + "PredikaatAanschrijving");
    }

    @Override
    public void setPredikaatAanschrijving(final Predikaat predikaatAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVoornamenAanschrijving() {
        return filter(persoon.getVoornamenAanschrijving(), VELD_PREFIX + "VoornamenAanschrijving");
    }

    @Override
    public void setVoornamenAanschrijving(final String voornamenAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVoorvoegselAanschrijving() {
        return filter(persoon.getVoorvoegselAanschrijving(), VELD_PREFIX + "VoorvoegselAanschrijving");
    }

    @Override
    public void setVoorvoegselAanschrijving(final String voorvoegselAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScheidingstekenAanschrijving() {
        return filter(persoon.getScheidingstekenAanschrijving(), VELD_PREFIX + "ScheidingstekenAanschrijving");
    }

    @Override
    public void setScheidingstekenAanschrijving(final String scheidingstekenAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGeslachtsnaamAanschrijving() {
        return filter(persoon.getGeslachtsnaamAanschrijving(), VELD_PREFIX + "GeslachtsnaamAanschrijving");
    }

    @Override
    public void setGeslachtsnaamAanschrijving(final String geslachtsnaamAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAanschrijvingStatusHis() {
        return filter(persoon.getAanschrijvingStatusHis(), VELD_PREFIX + "AanschrijvingStatusHis");
    }

    @Override
    public void setDatumGeboorte(final Integer datumGeboorte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandseGeboorteplaats(final String buitenlandseGeboorteplaats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLandGeboorte(final Land landGeboorte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGeboorteStatusHis() {
        return filter(persoon.getGeboorteStatusHis(), VELD_PREFIX + "GeboorteStatusHis");
    }

    @Override
    public Integer getDatumOverlijden() {
        return filter(persoon.getDatumOverlijden(), VELD_PREFIX + "DatumOverlijden");
    }

    @Override
    public void setDatumOverlijden(final Integer datumOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Partij getGemeenteOverlijden() {
        return filter(persoon.getGemeenteOverlijden(), VELD_PREFIX + "GemeenteOverlijden");
    }

    @Override
    public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Plaats getWoonplaatsOverlijden() {
        return filter(persoon.getWoonplaatsOverlijden(), VELD_PREFIX + "WoonplaatsOverlijden");
    }

    @Override
    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandseRegioOverlijden() {
        return filter(persoon.getBuitenlandseRegioOverlijden(), VELD_PREFIX + "BuitenlandseRegioOverlijden");
    }

    @Override
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Land getLandOverlijden() {
        return filter(persoon.getLandOverlijden(), VELD_PREFIX + "LandOverlijden");
    }

    @Override
    public void setLandOverlijden(final Land landOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOmschrijvingLocatieOverlijden() {
        return filter(persoon.getOmschrijvingLocatieOverlijden(), VELD_PREFIX + "OmschrijvingLocatieOverlijden");
    }

    @Override
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOverlijdenStatusHis() {
        return filter(persoon.getOverlijdenStatusHis(), VELD_PREFIX + "OverlijdenStatusHis");
    }

    @Override
    public Verblijfsrecht getVerblijfsrecht() {
        return filter(persoon.getVerblijfsrecht(), VELD_PREFIX + "Verblijfsrecht");
    }

    @Override
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanvangVerblijfsrecht() {
        return filter(persoon.getDatumAanvangVerblijfsrecht(), VELD_PREFIX + "DatumAanvangVerblijfsrecht");
    }

    @Override
    public void setDatumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return filter(persoon.getDatumVoorzienEindeVerblijfsrecht(), VELD_PREFIX + "DatumVoorzienEindeVerblijfsrecht");
    }

    @Override
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanvangAaneensluitendVerblijfsrecht() {
        return filter(persoon.getDatumAanvangAaneensluitendVerblijfsrecht(), VELD_PREFIX
            + "DatumAanvangAaneensluitendVerblijfsrecht");
    }

    @Override
    public void setDatumAanvangAaneensluitendVerblijfsrecht(final Integer datumAanvangAaneensluitendVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVerblijfsrechtStatusHis() {
        return filter(persoon.getVerblijfsrechtStatusHis(), VELD_PREFIX + "VerblijfsrechtStatusHis");
    }

    @Override
    public void setIndicatieUitsluitingNLKiesrecht(final Boolean indicatieUitsluitingNLKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumEindeUitsluitingNLKiesrecht() {
        return filter(persoon.getDatumEindeUitsluitingNLKiesrecht(), VELD_PREFIX + "DatumEindeUitsluitingNLKiesrecht");
    }

    @Override
    public void setDatumEindeUitsluitingNLKiesrecht(final Integer datumEindeUitsluitingNLKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUitsluitingNLKiesrechtStatusHis() {
        return filter(persoon.getUitsluitingNLKiesrechtStatusHis(), VELD_PREFIX + "UitsluitingNLKiesrechtStatusHis");
    }

    @Override
    public void setIndicatieDeelnameEUVerkiezingen(final Boolean indicatieDeelnameEUVerkiezingen) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return filter(persoon.getDatumAanleidingAanpassingDeelnameEUVerkiezing(), VELD_PREFIX
            + "DatumAanleidingAanpassingDeelnameEUVerkiezing");
    }

    @Override
    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final Integer datumAanleidingAanpassingDeelnameEUVerkiezing)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumEindeUitsluitingEUKiesrecht() {
        return filter(persoon.getDatumEindeUitsluitingEUKiesrecht(), VELD_PREFIX + "DatumEindeUitsluitingEUKiesrecht");
    }

    @Override
    public void setDatumEindeUitsluitingEUKiesrecht(final Integer datumEindeUitsluitingEUKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getEUVerkiezingenStatusHis() {
        return filter(persoon.getEUVerkiezingenStatusHis(), VELD_PREFIX + "EUVerkiezingenStatusHis");
    }

    @Override
    public Verantwoordelijke getVerantwoordelijke() {
        return filter(persoon.getVerantwoordelijke(), VELD_PREFIX + "Verantwoordelijke");
    }

    @Override
    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getBijhoudingsverantwoordelijkheidStatusHis() {
        return filter(persoon.getBijhoudingsverantwoordelijkheidStatusHis(), VELD_PREFIX
            + "BijhoudingsverantwoordelijkheidStatusHis");
    }

    @Override
    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getOpschortingStatusHis() {
        return filter(persoon.getOpschortingStatusHis(), VELD_PREFIX + "OpschortingStatusHis");
    }

    @Override
    public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumInschrijvingInGemeente() {
        return filter(persoon.getDatumInschrijvingInGemeente(), VELD_PREFIX + "DatumInschrijvingInGemeente");
    }

    @Override
    public void setDatumInschrijvingInGemeente(final Integer datumInschrijvingInGemeente) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieOnverwerktDocumentAanwezig() {
        return filter(persoon.getIndicatieOnverwerktDocumentAanwezig(), VELD_PREFIX
            + "IndicatieOnverwerktDocumentAanwezig");
    }

    @Override
    public void setIndicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getBijhoudingsgemeenteStatusHis() {
        return filter(persoon.getBijhoudingsgemeenteStatusHis(), VELD_PREFIX + "BijhoudingsgemeenteStatusHis");
    }

    @Override
    public Partij getGemeentePersoonskaart() {
        return filter(persoon.getGemeentePersoonskaart(), VELD_PREFIX + "GemeentePersoonskaart");
    }

    @Override
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return filter(persoon.getIndicatiePersoonskaartVolledigGeconverteerd(), VELD_PREFIX
            + "IndicatiePersoonskaartVolledigGeconverteerd");
    }

    @Override
    public void
            setIndicatiePersoonskaartVolledigGeconverteerd(final Boolean indicatiePersoonskaartVolledigGeconverteerd)
    {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getPersoonskaartStatusHis() {
        return filter(persoon.getPersoonskaartStatusHis(), VELD_PREFIX + "PersoonskaartStatusHis");
    }

    @Override
    public Land getLandVanwaarGevestigd() {
        return filter(persoon.getLandVanwaarGevestigd(), VELD_PREFIX + "LandVanwaarGevestigd");
    }

    @Override
    public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumVestigingInNederland() {
        return filter(persoon.getDatumVestigingInNederland(), VELD_PREFIX + "DatumVestigingInNederland");
    }

    @Override
    public void setDatumVestigingInNederland(final Integer datumVestigingInNederland) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getImmigratieStatusHis() {
        return filter(persoon.getImmigratieStatusHis(), VELD_PREFIX + "ImmigratieStatusHis");
    }

    @Override
    public Integer getDatumInschrijving() {
        return filter(persoon.getDatumInschrijving(), VELD_PREFIX + "DatumInschrijving");
    }

    @Override
    public void setDatumInschrijving(final Integer datumInschrijving) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Long getVersienummer() {
        return filter(persoon.getVersienummer(), VELD_PREFIX + "Versienummer");
    }

    @Override
    public void setVersienummer(final Long versienummer) {

        throw new UnsupportedOperationException();
    }

    @Override
    public List<Persoon> getPersoonen() {

        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoon(final Persoon element) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoon(final Persoon element) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Persoon getVorigePersoon() {
        return filter(persoon.getVorigePersoon(), VELD_PREFIX + "VorigePersoon");
    }

    @Override
    public void setVorigePersoon(final Persoon vorigePersoon) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Persoon getVolgendePersoon() {
        return filter(persoon.getVolgendePersoon(), VELD_PREFIX + "VolgendePersoon");
    }

    @Override
    public void setVolgendePersoon(final Persoon volgendePersoon) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getInschrijvingStatusHis() {
        return filter(persoon.getInschrijvingStatusHis(), VELD_PREFIX + "InschrijvingStatusHis");
    }

    @Override
    public Calendar getTijdstipLaatsteWijziging() {
        return filter(persoon.getTijdstipLaatsteWijziging(), VELD_PREFIX + "TijdstipLaatsteWijziging");
    }

    @Override
    public void setTijdstipLaatsteWijziging(final Calendar tijdstipLaatsteWijziging) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieGegevensInOnderzoek() {
        return filter(persoon.getIndicatieGegevensInOnderzoek(), VELD_PREFIX + "IndicatieGegevensInOnderzoek");
    }

    @Override
    public void setIndicatieGegevensInOnderzoek(final Boolean indicatieGegevensInOnderzoek) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonAdres(final PersoonAdres element) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonAdres(final PersoonAdres element) {

        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponenten() {

        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonIndicatie> getPersoonIndicatieen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonIndicatie(final PersoonIndicatie element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonIndicatie(final PersoonIndicatie element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonNationaliteit> getPersoonNationaliteiten() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonNationaliteit(final PersoonNationaliteit element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonNationaliteit(final PersoonNationaliteit element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonReisdocument> getPersoonReisdocumenten() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonReisdocument(final PersoonReisdocument element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonReisdocument(final PersoonReisdocument element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonVerificatie> getPersoonVerificatieen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonVerificatie(final PersoonVerificatie element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonVerificatie(final PersoonVerificatie element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersoonVoornaam> getPersoonVoornaamen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersoonVoornaam(final PersoonVoornaam element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersoonVoornaam(final PersoonVoornaam element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LeveringPersoon> getLeveringPersoonen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLeveringPersoon(final LeveringPersoon element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLeveringPersoon(final LeveringPersoon element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBehandeldAlsNederlander(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBelemmeringVerstrekkingReisdocument(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBezitBuitenlandsReisdocument(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDerdeHeeftGezag(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGepriviligeerde(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOnderCuratele(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVastgesteldNietNederlander(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVerstrekkingsBeperking(final Boolean waarde) {
        throw new UnsupportedOperationException();
    }

}
