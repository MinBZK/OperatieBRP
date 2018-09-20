/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ANummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Geslachtsnaam;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Scheidingsteken;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Versienummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornamen;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voorvoegsel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Geslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.RedenOpschorting;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortPersoon;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AdellijkeTitel;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Land;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Plaats;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Predikaat;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Verblijfsrecht;


/**
 * Persoon

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoon extends AbstractTabel {

   @Transient
   protected PersoonID id;

   @Column(name = "Srt")
   protected SoortPersoon soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "BSN"))
   protected Burgerservicenummer burgerservicenummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "ANr"))
   protected ANummer administratienummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "IDsStatusHis"))
   protected StatusHistorie identificatienummersStatusHis;

   @Column(name = "Geslachtsaand")
   protected Geslachtsaanduiding geslachtsaanduiding;

   @AttributeOverride(name = "waarde", column = @Column(name = "GeslachtsaandStatusHis"))
   protected StatusHistorie geslachtsaanduidingStatusHis;

   @ManyToOne
   @JoinColumn(name = "Predikaat")
   protected Predikaat predikaat;

   @AttributeOverride(name = "waarde", column = @Column(name = "Voornamen"))
   protected Voornamen voornamen;

   @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
   protected Voorvoegsel voorvoegsel;

   @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
   protected Scheidingsteken scheidingsteken;

   @ManyToOne
   @JoinColumn(name = "AdellijkeTitel")
   protected AdellijkeTitel adellijkeTitel;

   @AttributeOverride(name = "waarde", column = @Column(name = "Geslnaam"))
   protected Geslachtsnaam geslachtsnaam;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndNreeksAlsGeslnaam"))
   protected JaNee indicatieNamenreeksAlsGeslachtsnaam;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndAlgoritmischAfgeleid"))
   protected JaNee indicatieAlgoritmischAfgeleid;

   @AttributeOverride(name = "waarde", column = @Column(name = "SamengesteldeNaamStatusHis"))
   protected StatusHistorie samengesteldeNaamStatusHis;

   @Column(name = "GebrGeslnaamEGP")
   protected WijzeGebruikGeslachtsnaam wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndAanschrMetAdellijkeTitels"))
   protected JaNee indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndAanschrAlgoritmischAfgele"))
   protected JaNee indicatieAanschrijvingAlgoritmischAfgeleid;

   @ManyToOne
   @JoinColumn(name = "PredikaatAanschr")
   protected Predikaat predikaatAanschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "VoornamenAanschr"))
   protected Voornamen voornamenAanschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "VoorvoegselAanschr"))
   protected Voorvoegsel voorvoegselAanschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "ScheidingstekenAanschr"))
   protected Scheidingsteken scheidingstekenAanschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "GeslnaamAanschr"))
   protected Geslachtsnaam geslachtsnaamAanschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "AanschrStatusHis"))
   protected StatusHistorie aanschrijvingStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatGeboorte"))
   protected Datum datumGeboorte;

   @ManyToOne
   @JoinColumn(name = "GemGeboorte")
   protected Partij gemeenteGeboorte;

   @ManyToOne
   @JoinColumn(name = "WplGeboorte")
   protected Plaats woonplaatsGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLGeboorteplaats"))
   protected BuitenlandsePlaats buitenlandseGeboorteplaats;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioGeboorte"))
   protected BuitenlandseRegio buitenlandseRegioGeboorte;

   @ManyToOne
   @JoinColumn(name = "LandGeboorte")
   protected Land landGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsGeboorteloc"))
   protected LocatieOmschrijving omschrijvingGeboortelocatie;

   @AttributeOverride(name = "waarde", column = @Column(name = "GeboorteStatusHis"))
   protected StatusHistorie geboorteStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatOverlijden"))
   protected Datum datumOverlijden;

   @ManyToOne
   @JoinColumn(name = "GemOverlijden")
   protected Partij gemeenteOverlijden;

   @ManyToOne
   @JoinColumn(name = "WplOverlijden")
   protected Plaats woonplaatsOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsOverlijden"))
   protected BuitenlandsePlaats buitenlandsePlaatsOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioOverlijden"))
   protected BuitenlandseRegio buitenlandseRegioOverlijden;

   @ManyToOne
   @JoinColumn(name = "LandOverlijden")
   protected Land landOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocOverlijden"))
   protected LocatieOmschrijving omschrijvingLocatieOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "OverlijdenStatusHis"))
   protected StatusHistorie overlijdenStatusHis;

   @ManyToOne
   @JoinColumn(name = "Verblijfsr")
   protected Verblijfsrecht verblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfsr"))
   protected Datum datumAanvangVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfsr"))
   protected Datum datumVoorzienEindeVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAaneenslVerblijfsr"))
   protected Datum datumAanvangAaneensluitendVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "VerblijfsrStatusHis"))
   protected StatusHistorie verblijfsrechtStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndUitslNLKiesr"))
   protected JaNee indicatieUitsluitingNLKiesrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslNLKiesr"))
   protected Datum datumEindeUitsluitingNLKiesrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "UitslNLKiesrStatusHis"))
   protected StatusHistorie uitsluitingNLKiesrechtStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndDeelnEUVerkiezingen"))
   protected JaNee indicatieDeelnameEUVerkiezingen;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
   protected Datum datumAanleidingAanpassingDeelnameEUVerkiezing;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslEUKiesr"))
   protected Datum datumEindeUitsluitingEUKiesrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "EUVerkiezingenStatusHis"))
   protected StatusHistorie eUVerkiezingenStatusHis;

   @Column(name = "Verantwoordelijke")
   protected Verantwoordelijke verantwoordelijke;

   @AttributeOverride(name = "waarde", column = @Column(name = "BijhverantwoordelijkheidStat"))
   protected StatusHistorie bijhoudingsverantwoordelijkheidStatusHis;

   @Column(name = "RdnOpschortingBijhouding")
   protected RedenOpschorting redenOpschortingBijhouding;

   @AttributeOverride(name = "waarde", column = @Column(name = "OpschortingStatusHis"))
   protected StatusHistorie opschortingStatusHis;

   @ManyToOne
   @JoinColumn(name = "Bijhgem")
   protected Partij bijhoudingsgemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInschrInGem"))
   protected Datum datumInschrijvingInGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOnverwDocAanw"))
   protected JaNee indicatieOnverwerktDocumentAanwezig;

   @AttributeOverride(name = "waarde", column = @Column(name = "BijhgemStatusHis"))
   protected StatusHistorie bijhoudingsgemeenteStatusHis;

   @ManyToOne
   @JoinColumn(name = "GemPK")
   protected Partij gemeentePersoonskaart;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndPKVolledigGeconv"))
   protected JaNee indicatiePersoonskaartVolledigGeconverteerd;

   @AttributeOverride(name = "waarde", column = @Column(name = "PKStatusHis"))
   protected StatusHistorie persoonskaartStatusHis;

   @ManyToOne
   @JoinColumn(name = "LandVanwaarGevestigd")
   protected Land landVanwaarGevestigd;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVestigingInNederland"))
   protected Datum datumVestigingInNederland;

   @AttributeOverride(name = "waarde", column = @Column(name = "ImmigratieStatusHis"))
   protected StatusHistorie immigratieStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInschr"))
   protected Datum datumInschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "Versienr"))
   protected Versienummer versienummer;

   @ManyToOne
   @JoinColumn(name = "VorigePers")
   protected Persoon vorigePersoon;

   @ManyToOne
   @JoinColumn(name = "VolgendePers")
   protected Persoon volgendePersoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "InschrStatusHis"))
   protected StatusHistorie inschrijvingStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "TijdstipLaatsteWijz"))
   protected DatumTijd tijdstipLaatsteWijziging;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndGegevensInOnderzoek"))
   protected JaNee indicatieGegevensInOnderzoek;


   @Id
   @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Pers")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonID();
      }
      id.setWaarde(value);
   }

   public SoortPersoon getSoort() {
      return soort;
   }

   public void setSoort(final SoortPersoon soort) {
      this.soort = soort;
   }

   public Burgerservicenummer getBurgerservicenummer() {
      return burgerservicenummer;
   }

   public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
      this.burgerservicenummer = burgerservicenummer;
   }

   public ANummer getAdministratienummer() {
      return administratienummer;
   }

   public void setAdministratienummer(final ANummer administratienummer) {
      this.administratienummer = administratienummer;
   }

   public StatusHistorie getIdentificatienummersStatusHis() {
      return identificatienummersStatusHis;
   }

   public void setIdentificatienummersStatusHis(final StatusHistorie identificatienummersStatusHis) {
      this.identificatienummersStatusHis = identificatienummersStatusHis;
   }

   public Geslachtsaanduiding getGeslachtsaanduiding() {
      return geslachtsaanduiding;
   }

   public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
      this.geslachtsaanduiding = geslachtsaanduiding;
   }

   public StatusHistorie getGeslachtsaanduidingStatusHis() {
      return geslachtsaanduidingStatusHis;
   }

   public void setGeslachtsaanduidingStatusHis(final StatusHistorie geslachtsaanduidingStatusHis) {
      this.geslachtsaanduidingStatusHis = geslachtsaanduidingStatusHis;
   }

   public Predikaat getPredikaat() {
      return predikaat;
   }

   public void setPredikaat(final Predikaat predikaat) {
      this.predikaat = predikaat;
   }

   public Voornamen getVoornamen() {
      return voornamen;
   }

   public void setVoornamen(final Voornamen voornamen) {
      this.voornamen = voornamen;
   }

   public Voorvoegsel getVoorvoegsel() {
      return voorvoegsel;
   }

   public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
      this.voorvoegsel = voorvoegsel;
   }

   public Scheidingsteken getScheidingsteken() {
      return scheidingsteken;
   }

   public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
      this.scheidingsteken = scheidingsteken;
   }

   public AdellijkeTitel getAdellijkeTitel() {
      return adellijkeTitel;
   }

   public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
      this.adellijkeTitel = adellijkeTitel;
   }

   public Geslachtsnaam getGeslachtsnaam() {
      return geslachtsnaam;
   }

   public void setGeslachtsnaam(final Geslachtsnaam geslachtsnaam) {
      this.geslachtsnaam = geslachtsnaam;
   }

   public JaNee getIndicatieNamenreeksAlsGeslachtsnaam() {
      return indicatieNamenreeksAlsGeslachtsnaam;
   }

   public void setIndicatieNamenreeksAlsGeslachtsnaam(final JaNee indicatieNamenreeksAlsGeslachtsnaam) {
      this.indicatieNamenreeksAlsGeslachtsnaam = indicatieNamenreeksAlsGeslachtsnaam;
   }

   public JaNee getIndicatieAlgoritmischAfgeleid() {
      return indicatieAlgoritmischAfgeleid;
   }

   public void setIndicatieAlgoritmischAfgeleid(final JaNee indicatieAlgoritmischAfgeleid) {
      this.indicatieAlgoritmischAfgeleid = indicatieAlgoritmischAfgeleid;
   }

   public StatusHistorie getSamengesteldeNaamStatusHis() {
      return samengesteldeNaamStatusHis;
   }

   public void setSamengesteldeNaamStatusHis(final StatusHistorie samengesteldeNaamStatusHis) {
      this.samengesteldeNaamStatusHis = samengesteldeNaamStatusHis;
   }

   public WijzeGebruikGeslachtsnaam getWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner() {
      return wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner;
   }

   public void setWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner(final WijzeGebruikGeslachtsnaam wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner) {
      this.wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner = wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner;
   }

   public JaNee getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten() {
      return indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
   }

   public void setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(final JaNee indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten) {
      this.indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten = indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
   }

   public JaNee getIndicatieAanschrijvingAlgoritmischAfgeleid() {
      return indicatieAanschrijvingAlgoritmischAfgeleid;
   }

   public void setIndicatieAanschrijvingAlgoritmischAfgeleid(final JaNee indicatieAanschrijvingAlgoritmischAfgeleid) {
      this.indicatieAanschrijvingAlgoritmischAfgeleid = indicatieAanschrijvingAlgoritmischAfgeleid;
   }

   public Predikaat getPredikaatAanschrijving() {
      return predikaatAanschrijving;
   }

   public void setPredikaatAanschrijving(final Predikaat predikaatAanschrijving) {
      this.predikaatAanschrijving = predikaatAanschrijving;
   }

   public Voornamen getVoornamenAanschrijving() {
      return voornamenAanschrijving;
   }

   public void setVoornamenAanschrijving(final Voornamen voornamenAanschrijving) {
      this.voornamenAanschrijving = voornamenAanschrijving;
   }

   public Voorvoegsel getVoorvoegselAanschrijving() {
      return voorvoegselAanschrijving;
   }

   public void setVoorvoegselAanschrijving(final Voorvoegsel voorvoegselAanschrijving) {
      this.voorvoegselAanschrijving = voorvoegselAanschrijving;
   }

   public Scheidingsteken getScheidingstekenAanschrijving() {
      return scheidingstekenAanschrijving;
   }

   public void setScheidingstekenAanschrijving(final Scheidingsteken scheidingstekenAanschrijving) {
      this.scheidingstekenAanschrijving = scheidingstekenAanschrijving;
   }

   public Geslachtsnaam getGeslachtsnaamAanschrijving() {
      return geslachtsnaamAanschrijving;
   }

   public void setGeslachtsnaamAanschrijving(final Geslachtsnaam geslachtsnaamAanschrijving) {
      this.geslachtsnaamAanschrijving = geslachtsnaamAanschrijving;
   }

   public StatusHistorie getAanschrijvingStatusHis() {
      return aanschrijvingStatusHis;
   }

   public void setAanschrijvingStatusHis(final StatusHistorie aanschrijvingStatusHis) {
      this.aanschrijvingStatusHis = aanschrijvingStatusHis;
   }

   public Datum getDatumGeboorte() {
      return datumGeboorte;
   }

   public void setDatumGeboorte(final Datum datumGeboorte) {
      this.datumGeboorte = datumGeboorte;
   }

   public Partij getGemeenteGeboorte() {
      return gemeenteGeboorte;
   }

   public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
      this.gemeenteGeboorte = gemeenteGeboorte;
   }

   public Plaats getWoonplaatsGeboorte() {
      return woonplaatsGeboorte;
   }

   public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
      this.woonplaatsGeboorte = woonplaatsGeboorte;
   }

   public BuitenlandsePlaats getBuitenlandseGeboorteplaats() {
      return buitenlandseGeboorteplaats;
   }

   public void setBuitenlandseGeboorteplaats(final BuitenlandsePlaats buitenlandseGeboorteplaats) {
      this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
   }

   public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
      return buitenlandseRegioGeboorte;
   }

   public void setBuitenlandseRegioGeboorte(final BuitenlandseRegio buitenlandseRegioGeboorte) {
      this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
   }

   public Land getLandGeboorte() {
      return landGeboorte;
   }

   public void setLandGeboorte(final Land landGeboorte) {
      this.landGeboorte = landGeboorte;
   }

   public LocatieOmschrijving getOmschrijvingGeboortelocatie() {
      return omschrijvingGeboortelocatie;
   }

   public void setOmschrijvingGeboortelocatie(final LocatieOmschrijving omschrijvingGeboortelocatie) {
      this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
   }

   public StatusHistorie getGeboorteStatusHis() {
      return geboorteStatusHis;
   }

   public void setGeboorteStatusHis(final StatusHistorie geboorteStatusHis) {
      this.geboorteStatusHis = geboorteStatusHis;
   }

   public Datum getDatumOverlijden() {
      return datumOverlijden;
   }

   public void setDatumOverlijden(final Datum datumOverlijden) {
      this.datumOverlijden = datumOverlijden;
   }

   public Partij getGemeenteOverlijden() {
      return gemeenteOverlijden;
   }

   public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
      this.gemeenteOverlijden = gemeenteOverlijden;
   }

   public Plaats getWoonplaatsOverlijden() {
      return woonplaatsOverlijden;
   }

   public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
      this.woonplaatsOverlijden = woonplaatsOverlijden;
   }

   public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
      return buitenlandsePlaatsOverlijden;
   }

   public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaats buitenlandsePlaatsOverlijden) {
      this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
   }

   public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
      return buitenlandseRegioOverlijden;
   }

   public void setBuitenlandseRegioOverlijden(final BuitenlandseRegio buitenlandseRegioOverlijden) {
      this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
   }

   public Land getLandOverlijden() {
      return landOverlijden;
   }

   public void setLandOverlijden(final Land landOverlijden) {
      this.landOverlijden = landOverlijden;
   }

   public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
      return omschrijvingLocatieOverlijden;
   }

   public void setOmschrijvingLocatieOverlijden(final LocatieOmschrijving omschrijvingLocatieOverlijden) {
      this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
   }

   public StatusHistorie getOverlijdenStatusHis() {
      return overlijdenStatusHis;
   }

   public void setOverlijdenStatusHis(final StatusHistorie overlijdenStatusHis) {
      this.overlijdenStatusHis = overlijdenStatusHis;
   }

   public Verblijfsrecht getVerblijfsrecht() {
      return verblijfsrecht;
   }

   public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
      this.verblijfsrecht = verblijfsrecht;
   }

   public Datum getDatumAanvangVerblijfsrecht() {
      return datumAanvangVerblijfsrecht;
   }

   public void setDatumAanvangVerblijfsrecht(final Datum datumAanvangVerblijfsrecht) {
      this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
   }

   public Datum getDatumVoorzienEindeVerblijfsrecht() {
      return datumVoorzienEindeVerblijfsrecht;
   }

   public void setDatumVoorzienEindeVerblijfsrecht(final Datum datumVoorzienEindeVerblijfsrecht) {
      this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
   }

   public Datum getDatumAanvangAaneensluitendVerblijfsrecht() {
      return datumAanvangAaneensluitendVerblijfsrecht;
   }

   public void setDatumAanvangAaneensluitendVerblijfsrecht(final Datum datumAanvangAaneensluitendVerblijfsrecht) {
      this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
   }

   public StatusHistorie getVerblijfsrechtStatusHis() {
      return verblijfsrechtStatusHis;
   }

   public void setVerblijfsrechtStatusHis(final StatusHistorie verblijfsrechtStatusHis) {
      this.verblijfsrechtStatusHis = verblijfsrechtStatusHis;
   }

   public JaNee getIndicatieUitsluitingNLKiesrecht() {
      return indicatieUitsluitingNLKiesrecht;
   }

   public void setIndicatieUitsluitingNLKiesrecht(final JaNee indicatieUitsluitingNLKiesrecht) {
      this.indicatieUitsluitingNLKiesrecht = indicatieUitsluitingNLKiesrecht;
   }

   public Datum getDatumEindeUitsluitingNLKiesrecht() {
      return datumEindeUitsluitingNLKiesrecht;
   }

   public void setDatumEindeUitsluitingNLKiesrecht(final Datum datumEindeUitsluitingNLKiesrecht) {
      this.datumEindeUitsluitingNLKiesrecht = datumEindeUitsluitingNLKiesrecht;
   }

   public StatusHistorie getUitsluitingNLKiesrechtStatusHis() {
      return uitsluitingNLKiesrechtStatusHis;
   }

   public void setUitsluitingNLKiesrechtStatusHis(final StatusHistorie uitsluitingNLKiesrechtStatusHis) {
      this.uitsluitingNLKiesrechtStatusHis = uitsluitingNLKiesrechtStatusHis;
   }

   public JaNee getIndicatieDeelnameEUVerkiezingen() {
      return indicatieDeelnameEUVerkiezingen;
   }

   public void setIndicatieDeelnameEUVerkiezingen(final JaNee indicatieDeelnameEUVerkiezingen) {
      this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
   }

   public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
      return datumAanleidingAanpassingDeelnameEUVerkiezing;
   }

   public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(final Datum datumAanleidingAanpassingDeelnameEUVerkiezing) {
      this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
   }

   public Datum getDatumEindeUitsluitingEUKiesrecht() {
      return datumEindeUitsluitingEUKiesrecht;
   }

   public void setDatumEindeUitsluitingEUKiesrecht(final Datum datumEindeUitsluitingEUKiesrecht) {
      this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
   }

   public StatusHistorie getEUVerkiezingenStatusHis() {
      return eUVerkiezingenStatusHis;
   }

   public void setEUVerkiezingenStatusHis(final StatusHistorie eUVerkiezingenStatusHis) {
      this.eUVerkiezingenStatusHis = eUVerkiezingenStatusHis;
   }

   public Verantwoordelijke getVerantwoordelijke() {
      return verantwoordelijke;
   }

   public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
      this.verantwoordelijke = verantwoordelijke;
   }

   public StatusHistorie getBijhoudingsverantwoordelijkheidStatusHis() {
      return bijhoudingsverantwoordelijkheidStatusHis;
   }

   public void setBijhoudingsverantwoordelijkheidStatusHis(final StatusHistorie bijhoudingsverantwoordelijkheidStatusHis) {
      this.bijhoudingsverantwoordelijkheidStatusHis = bijhoudingsverantwoordelijkheidStatusHis;
   }

   public RedenOpschorting getRedenOpschortingBijhouding() {
      return redenOpschortingBijhouding;
   }

   public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
      this.redenOpschortingBijhouding = redenOpschortingBijhouding;
   }

   public StatusHistorie getOpschortingStatusHis() {
      return opschortingStatusHis;
   }

   public void setOpschortingStatusHis(final StatusHistorie opschortingStatusHis) {
      this.opschortingStatusHis = opschortingStatusHis;
   }

   public Partij getBijhoudingsgemeente() {
      return bijhoudingsgemeente;
   }

   public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
      this.bijhoudingsgemeente = bijhoudingsgemeente;
   }

   public Datum getDatumInschrijvingInGemeente() {
      return datumInschrijvingInGemeente;
   }

   public void setDatumInschrijvingInGemeente(final Datum datumInschrijvingInGemeente) {
      this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
   }

   public JaNee getIndicatieOnverwerktDocumentAanwezig() {
      return indicatieOnverwerktDocumentAanwezig;
   }

   public void setIndicatieOnverwerktDocumentAanwezig(final JaNee indicatieOnverwerktDocumentAanwezig) {
      this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
   }

   public StatusHistorie getBijhoudingsgemeenteStatusHis() {
      return bijhoudingsgemeenteStatusHis;
   }

   public void setBijhoudingsgemeenteStatusHis(final StatusHistorie bijhoudingsgemeenteStatusHis) {
      this.bijhoudingsgemeenteStatusHis = bijhoudingsgemeenteStatusHis;
   }

   public Partij getGemeentePersoonskaart() {
      return gemeentePersoonskaart;
   }

   public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
      this.gemeentePersoonskaart = gemeentePersoonskaart;
   }

   public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
      return indicatiePersoonskaartVolledigGeconverteerd;
   }

   public void setIndicatiePersoonskaartVolledigGeconverteerd(final JaNee indicatiePersoonskaartVolledigGeconverteerd) {
      this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
   }

   public StatusHistorie getPersoonskaartStatusHis() {
      return persoonskaartStatusHis;
   }

   public void setPersoonskaartStatusHis(final StatusHistorie persoonskaartStatusHis) {
      this.persoonskaartStatusHis = persoonskaartStatusHis;
   }

   public Land getLandVanwaarGevestigd() {
      return landVanwaarGevestigd;
   }

   public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
      this.landVanwaarGevestigd = landVanwaarGevestigd;
   }

   public Datum getDatumVestigingInNederland() {
      return datumVestigingInNederland;
   }

   public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
      this.datumVestigingInNederland = datumVestigingInNederland;
   }

   public StatusHistorie getImmigratieStatusHis() {
      return immigratieStatusHis;
   }

   public void setImmigratieStatusHis(final StatusHistorie immigratieStatusHis) {
      this.immigratieStatusHis = immigratieStatusHis;
   }

   public Datum getDatumInschrijving() {
      return datumInschrijving;
   }

   public void setDatumInschrijving(final Datum datumInschrijving) {
      this.datumInschrijving = datumInschrijving;
   }

   public Versienummer getVersienummer() {
      return versienummer;
   }

   public void setVersienummer(final Versienummer versienummer) {
      this.versienummer = versienummer;
   }

   public Persoon getVorigePersoon() {
      return vorigePersoon;
   }

   public void setVorigePersoon(final Persoon vorigePersoon) {
      this.vorigePersoon = vorigePersoon;
   }

   public Persoon getVolgendePersoon() {
      return volgendePersoon;
   }

   public void setVolgendePersoon(final Persoon volgendePersoon) {
      this.volgendePersoon = volgendePersoon;
   }

   public StatusHistorie getInschrijvingStatusHis() {
      return inschrijvingStatusHis;
   }

   public void setInschrijvingStatusHis(final StatusHistorie inschrijvingStatusHis) {
      this.inschrijvingStatusHis = inschrijvingStatusHis;
   }

   public DatumTijd getTijdstipLaatsteWijziging() {
      return tijdstipLaatsteWijziging;
   }

   public void setTijdstipLaatsteWijziging(final DatumTijd tijdstipLaatsteWijziging) {
      this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
   }

   public JaNee getIndicatieGegevensInOnderzoek() {
      return indicatieGegevensInOnderzoek;
   }

   public void setIndicatieGegevensInOnderzoek(final JaNee indicatieGegevensInOnderzoek) {
      this.indicatieGegevensInOnderzoek = indicatieGegevensInOnderzoek;
   }



}
