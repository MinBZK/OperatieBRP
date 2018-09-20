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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AanduidingAdresseerbaarObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AanduidingBijHuisnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Adresregel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Gemeentedeel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisletter;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonAdresID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Postcode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.FunctieAdres;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AangeverAdreshouding;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Land;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Plaats;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenWijzigingAdres;


/**
 * Persoon \ Adres

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonAdres extends AbstractTabel {

   @Transient
   protected PersoonAdresID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @Column(name = "Srt")
   protected FunctieAdres soort;

   @ManyToOne
   @JoinColumn(name = "RdnWijz")
   protected RedenWijzigingAdres redenWijziging;

   @ManyToOne
   @JoinColumn(name = "AangAdresh")
   protected AangeverAdreshouding aangeverAdreshouding;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
   protected Datum datumAanvangAdreshouding;

   @AttributeOverride(name = "waarde", column = @Column(name = "AdresseerbaarObject"))
   protected AanduidingAdresseerbaarObject adresseerbaarObject;

   @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
   protected IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

   @ManyToOne
   @JoinColumn(name = "Gem")
   protected Partij gemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "NOR"))
   protected NaamOpenbareRuimte naamOpenbareRuimte;

   @AttributeOverride(name = "waarde", column = @Column(name = "AfgekorteNOR"))
   protected AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte;

   @AttributeOverride(name = "waarde", column = @Column(name = "Gemdeel"))
   protected Gemeentedeel gemeentedeel;

   @AttributeOverride(name = "waarde", column = @Column(name = "Huisnr"))
   protected Huisnummer huisnummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "Huisletter"))
   protected Huisletter huisletter;

   @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
   protected Huisnummertoevoeging huisnummertoevoeging;

   @AttributeOverride(name = "waarde", column = @Column(name = "Postcode"))
   protected Postcode postcode;

   @ManyToOne
   @JoinColumn(name = "Wpl")
   protected Plaats woonplaats;

   @AttributeOverride(name = "waarde", column = @Column(name = "LoctovAdres"))
   protected AanduidingBijHuisnummer locatietovAdres;

   @AttributeOverride(name = "waarde", column = @Column(name = "LocOms"))
   protected LocatieOmschrijving locatieOmschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel1"))
   protected Adresregel buitenlandsAdresRegel1;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel2"))
   protected Adresregel buitenlandsAdresRegel2;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel3"))
   protected Adresregel buitenlandsAdresRegel3;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel4"))
   protected Adresregel buitenlandsAdresRegel4;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel5"))
   protected Adresregel buitenlandsAdresRegel5;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel6"))
   protected Adresregel buitenlandsAdresRegel6;

   @ManyToOne
   @JoinColumn(name = "Land")
   protected Land land;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
   protected Datum datumVertrekUitNederland;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersAdresStatusHis"))
   protected StatusHistorie persoonAdresStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersAdres", sequenceName = "Kern.seq_PersAdres")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersAdres")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonAdresID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public FunctieAdres getSoort() {
      return soort;
   }

   public void setSoort(final FunctieAdres soort) {
      this.soort = soort;
   }

   public RedenWijzigingAdres getRedenWijziging() {
      return redenWijziging;
   }

   public void setRedenWijziging(final RedenWijzigingAdres redenWijziging) {
      this.redenWijziging = redenWijziging;
   }

   public AangeverAdreshouding getAangeverAdreshouding() {
      return aangeverAdreshouding;
   }

   public void setAangeverAdreshouding(final AangeverAdreshouding aangeverAdreshouding) {
      this.aangeverAdreshouding = aangeverAdreshouding;
   }

   public Datum getDatumAanvangAdreshouding() {
      return datumAanvangAdreshouding;
   }

   public void setDatumAanvangAdreshouding(final Datum datumAanvangAdreshouding) {
      this.datumAanvangAdreshouding = datumAanvangAdreshouding;
   }

   public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
      return adresseerbaarObject;
   }

   public void setAdresseerbaarObject(final AanduidingAdresseerbaarObject adresseerbaarObject) {
      this.adresseerbaarObject = adresseerbaarObject;
   }

   public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
      return identificatiecodeNummeraanduiding;
   }

   public void setIdentificatiecodeNummeraanduiding(final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding) {
      this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
   }

   public Partij getGemeente() {
      return gemeente;
   }

   public void setGemeente(final Partij gemeente) {
      this.gemeente = gemeente;
   }

   public NaamOpenbareRuimte getNaamOpenbareRuimte() {
      return naamOpenbareRuimte;
   }

   public void setNaamOpenbareRuimte(final NaamOpenbareRuimte naamOpenbareRuimte) {
      this.naamOpenbareRuimte = naamOpenbareRuimte;
   }

   public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
      return afgekorteNaamOpenbareRuimte;
   }

   public void setAfgekorteNaamOpenbareRuimte(final AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte) {
      this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
   }

   public Gemeentedeel getGemeentedeel() {
      return gemeentedeel;
   }

   public void setGemeentedeel(final Gemeentedeel gemeentedeel) {
      this.gemeentedeel = gemeentedeel;
   }

   public Huisnummer getHuisnummer() {
      return huisnummer;
   }

   public void setHuisnummer(final Huisnummer huisnummer) {
      this.huisnummer = huisnummer;
   }

   public Huisletter getHuisletter() {
      return huisletter;
   }

   public void setHuisletter(final Huisletter huisletter) {
      this.huisletter = huisletter;
   }

   public Huisnummertoevoeging getHuisnummertoevoeging() {
      return huisnummertoevoeging;
   }

   public void setHuisnummertoevoeging(final Huisnummertoevoeging huisnummertoevoeging) {
      this.huisnummertoevoeging = huisnummertoevoeging;
   }

   public Postcode getPostcode() {
      return postcode;
   }

   public void setPostcode(final Postcode postcode) {
      this.postcode = postcode;
   }

   public Plaats getWoonplaats() {
      return woonplaats;
   }

   public void setWoonplaats(final Plaats woonplaats) {
      this.woonplaats = woonplaats;
   }

   public AanduidingBijHuisnummer getLocatietovAdres() {
      return locatietovAdres;
   }

   public void setLocatietovAdres(final AanduidingBijHuisnummer locatietovAdres) {
      this.locatietovAdres = locatietovAdres;
   }

   public LocatieOmschrijving getLocatieOmschrijving() {
      return locatieOmschrijving;
   }

   public void setLocatieOmschrijving(final LocatieOmschrijving locatieOmschrijving) {
      this.locatieOmschrijving = locatieOmschrijving;
   }

   public Adresregel getBuitenlandsAdresRegel1() {
      return buitenlandsAdresRegel1;
   }

   public void setBuitenlandsAdresRegel1(final Adresregel buitenlandsAdresRegel1) {
      this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
   }

   public Adresregel getBuitenlandsAdresRegel2() {
      return buitenlandsAdresRegel2;
   }

   public void setBuitenlandsAdresRegel2(final Adresregel buitenlandsAdresRegel2) {
      this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
   }

   public Adresregel getBuitenlandsAdresRegel3() {
      return buitenlandsAdresRegel3;
   }

   public void setBuitenlandsAdresRegel3(final Adresregel buitenlandsAdresRegel3) {
      this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
   }

   public Adresregel getBuitenlandsAdresRegel4() {
      return buitenlandsAdresRegel4;
   }

   public void setBuitenlandsAdresRegel4(final Adresregel buitenlandsAdresRegel4) {
      this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
   }

   public Adresregel getBuitenlandsAdresRegel5() {
      return buitenlandsAdresRegel5;
   }

   public void setBuitenlandsAdresRegel5(final Adresregel buitenlandsAdresRegel5) {
      this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
   }

   public Adresregel getBuitenlandsAdresRegel6() {
      return buitenlandsAdresRegel6;
   }

   public void setBuitenlandsAdresRegel6(final Adresregel buitenlandsAdresRegel6) {
      this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
   }

   public Land getLand() {
      return land;
   }

   public void setLand(final Land land) {
      this.land = land;
   }

   public Datum getDatumVertrekUitNederland() {
      return datumVertrekUitNederland;
   }

   public void setDatumVertrekUitNederland(final Datum datumVertrekUitNederland) {
      this.datumVertrekUitNederland = datumVertrekUitNederland;
   }

   public StatusHistorie getPersoonAdresStatusHis() {
      return persoonAdresStatusHis;
   }

   public void setPersoonAdresStatusHis(final StatusHistorie persoonAdresStatusHis) {
      this.persoonAdresStatusHis = persoonAdresStatusHis;
   }



}
