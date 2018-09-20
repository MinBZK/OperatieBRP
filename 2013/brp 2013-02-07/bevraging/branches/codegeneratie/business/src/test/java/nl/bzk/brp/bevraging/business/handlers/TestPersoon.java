/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
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
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.SoortPersoon;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import nl.bzk.brp.domein.kern.Verblijfsrecht;
import nl.bzk.brp.domein.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.domein.lev.LeveringPersoon;


public class TestPersoon implements Persoon {

    private static DomeinObjectFactory      factory = new PersistentDomeinObjectFactory();

    private static final Integer            DATUM   = 11112011;
    private static final Partij             PARTIJ_1;
    private static final Partij             PARTIJ_2;
    private static final Land               LAND;
    private static final List<PersoonAdres> ADRESSEN;
    private static final Plaats             PLAATS;
    private static final Predikaat          PREDIKAAT;
    private static final Calendar           NU;
    private static final AdellijkeTitel     TITEL;
    private static final Verblijfsrecht     VERBLIJFSRECHT;

    static {
        PARTIJ_1 = factory.createPartij();
        PARTIJ_1.setSoort(SoortPartij.OVERHEIDSORGAAN);
        PARTIJ_2 = factory.createPartij();
        PARTIJ_2.setSoort(SoortPartij.DUMMY);
        LAND = factory.createLand();
        LAND.setNaam("Neverland");
        PLAATS = factory.createPlaats();
        PLAATS.setNaam("Babylon");
        ADRESSEN = new ArrayList<PersoonAdres>();
        ADRESSEN.add(new TestPersoonAdres());
        PREDIKAAT = factory.createPredikaat();
        PREDIKAAT.setCode("Drs");
        NU = Calendar.getInstance();
        TITEL = factory.createAdellijkeTitel();
        TITEL.setCode("Excellentie");
        VERBLIJFSRECHT = factory.createVerblijfsrecht();
        VERBLIJFSRECHT.setOmschrijving("Je mag hier blijven");
    }

    @Override
    public Long getID() {
        return 2L;
    }

    @Override
    public SoortPersoon getSoort() {
        return SoortPersoon.DUMMY;
    }

    @Override
    public String getBurgerservicenummer() {
        return "77";
    }

    @Override
    public String getAdministratienummer() {
        return "44";
    }

    @Override
    public String getGeslachtsnaam() {
        return "Vis";
    }

    @Override
    public String getVoornamen() {
        return "Ab";
    }

    @Override
    public String getVoorvoegsel() {
        return "de";
    }

    @Override
    public String getScheidingsteken() {
        return ",";
    }

    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.DUMMY;
    }

    @Override
    public Integer getDatumGeboorte() {
        return DATUM;
    }

    @Override
    public Partij getBijhoudingsgemeente() {
        return PARTIJ_1;
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return "Bethlehem";
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return "Napels";
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return "Jordaanvalei";
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return PARTIJ_2;
    }

    @Override
    public Land getLandGeboorte() {
        return LAND;
    }

    @Override
    public List<PersoonAdres> getPersoonAdresen() {
        return ADRESSEN;
    }

    @Override
    public String getOmschrijvingGeboortelocatie() {
        return "Stal";
    }

    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return RedenOpschorting.DUMMY;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return PLAATS;
    }

    @Override
    public Boolean getBehandeldAlsNederlander() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getBelemmeringVerstrekkingReisdocument() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getBezitBuitenlandsReisdocument() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getIndicatieDeelnameEUVerkiezingen() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getDerdeHeeftGezag() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getGepriviligeerde() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getOnderCuratele() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getIndicatieUitsluitingNLKiesrecht() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getVastgesteldNietNederlander() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean getVerstrekkingsBeperking() {
        return Boolean.TRUE;
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
        return "A";
    }

    @Override
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGeslachtsaanduidingStatusHis() {
        return "A";
    }

    @Override
    public Predikaat getPredikaat() {
        return PREDIKAAT;
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
        return TITEL;
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
        return Boolean.TRUE;
    }

    @Override
    public void setIndicatieNamenreeksAlsGeslachtsnaam(final Boolean indicatieNamenreeksAlsGeslachtsnaam) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAlgoritmischAfgeleid() {
        return Boolean.TRUE;
    }

    @Override
    public void setIndicatieAlgoritmischAfgeleid(final Boolean indicatieAlgoritmischAfgeleid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSamengesteldeNaamStatusHis() {
        return "A";
    }

    @Override
    public WijzeGebruikGeslachtsnaam getWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner() {
        return WijzeGebruikGeslachtsnaam.PARTNER_EIGEN;
    }

    @Override
    public void setWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner(
            final WijzeGebruikGeslachtsnaam wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten() {
        return Boolean.TRUE;
    }

    @Override
    public void setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(
            final Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return Boolean.TRUE;
    }

    @Override
    public void setIndicatieAanschrijvingAlgoritmischAfgeleid(final Boolean indicatieAanschrijvingAlgoritmischAfgeleid)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predikaat getPredikaatAanschrijving() {
        return PREDIKAAT;
    }

    @Override
    public void setPredikaatAanschrijving(final Predikaat predikaatAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVoornamenAanschrijving() {
        return "Mevrouw";
    }

    @Override
    public void setVoornamenAanschrijving(final String voornamenAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVoorvoegselAanschrijving() {
        return "Mevrouw";
    }

    @Override
    public void setVoorvoegselAanschrijving(final String voorvoegselAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScheidingstekenAanschrijving() {
        return ",";
    }

    @Override
    public void setScheidingstekenAanschrijving(final String scheidingstekenAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGeslachtsnaamAanschrijving() {
        return "";
    }

    @Override
    public void setGeslachtsnaamAanschrijving(final String geslachtsnaamAanschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAanschrijvingStatusHis() {
        return "A";
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
        return "A";
    }

    @Override
    public Integer getDatumOverlijden() {
        return DATUM;
    }

    @Override
    public void setDatumOverlijden(final Integer datumOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Partij getGemeenteOverlijden() {
        return PARTIJ_2;
    }

    @Override
    public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Plaats getWoonplaatsOverlijden() {
        return PLAATS;
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
        return "Jordaanvalei";
    }

    @Override
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Land getLandOverlijden() {
        return LAND;
    }

    @Override
    public void setLandOverlijden(final Land landOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOmschrijvingLocatieOverlijden() {
        return "Kruis";
    }

    @Override
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOverlijdenStatusHis() {
        return "A";
    }

    @Override
    public Verblijfsrecht getVerblijfsrecht() {
        return VERBLIJFSRECHT;
    }

    @Override
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanvangVerblijfsrecht() {
        return DATUM;
    }

    @Override
    public void setDatumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return DATUM;
    }

    @Override
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanvangAaneensluitendVerblijfsrecht() {
        return DATUM;
    }

    @Override
    public void setDatumAanvangAaneensluitendVerblijfsrecht(final Integer datumAanvangAaneensluitendVerblijfsrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVerblijfsrechtStatusHis() {
        return "A";
    }

    @Override
    public void setIndicatieUitsluitingNLKiesrecht(final Boolean indicatieUitsluitingNLKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumEindeUitsluitingNLKiesrecht() {
        return DATUM;
    }

    @Override
    public void setDatumEindeUitsluitingNLKiesrecht(final Integer datumEindeUitsluitingNLKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUitsluitingNLKiesrechtStatusHis() {
        return "A";
    }

    @Override
    public void setIndicatieDeelnameEUVerkiezingen(final Boolean indicatieDeelnameEUVerkiezingen) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return DATUM;
    }

    @Override
    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final Integer datumAanleidingAanpassingDeelnameEUVerkiezing)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumEindeUitsluitingEUKiesrecht() {
        return DATUM;
    }

    @Override
    public void setDatumEindeUitsluitingEUKiesrecht(final Integer datumEindeUitsluitingEUKiesrecht) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getEUVerkiezingenStatusHis() {
        return "A";
    }

    @Override
    public Verantwoordelijke getVerantwoordelijke() {
        return Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS;
    }

    @Override
    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBijhoudingsverantwoordelijkheidStatusHis() {
        return "A";
    }

    @Override
    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOpschortingStatusHis() {
        return "A";
    }

    @Override
    public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumInschrijvingInGemeente() {
        return DATUM;
    }

    @Override
    public void setDatumInschrijvingInGemeente(final Integer datumInschrijvingInGemeente) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieOnverwerktDocumentAanwezig() {
        return Boolean.TRUE;
    }

    @Override
    public void setIndicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBijhoudingsgemeenteStatusHis() {
        return "A";
    }

    @Override
    public Partij getGemeentePersoonskaart() {
        return PARTIJ_1;
    }

    @Override
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return Boolean.TRUE;
    }

    @Override
    public void
            setIndicatiePersoonskaartVolledigGeconverteerd(final Boolean indicatiePersoonskaartVolledigGeconverteerd)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPersoonskaartStatusHis() {
        return "A";
    }

    @Override
    public Land getLandVanwaarGevestigd() {
        return LAND;
    }

    @Override
    public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumVestigingInNederland() {
        return DATUM;
    }

    @Override
    public void setDatumVestigingInNederland(final Integer datumVestigingInNederland) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getImmigratieStatusHis() {
        return "A";
    }

    private Integer datumInschrijving = Integer.valueOf(2012);

    @Override
    public Integer getDatumInschrijving() {
        return datumInschrijving;
    }

    @Override
    public void setDatumInschrijving(final Integer datumInschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getVersienummer() {
        return Long.valueOf(1);
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
        return this;
    }

    @Override
    public void setVorigePersoon(final Persoon vorigePersoon) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Persoon getVolgendePersoon() {
        return this;
    }

    @Override
    public void setVolgendePersoon(final Persoon volgendePersoon) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInschrijvingStatusHis() {
        return "A";
    }

    @Override
    public Calendar getTijdstipLaatsteWijziging() {
        return NU;
    }

    @Override
    public void setTijdstipLaatsteWijziging(final Calendar tijdstipLaatsteWijziging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getIndicatieGegevensInOnderzoek() {
        return Boolean.TRUE;
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
