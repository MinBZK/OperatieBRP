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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Gemeentecode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PartijID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortPartij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Sector;


/**
 * Partij

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPartij extends AbstractTabel {

   @Transient
   protected PartijID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @Column(name = "Srt")
   protected SoortPartij soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
   protected Datum datumAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @ManyToOne
   @JoinColumn(name = "Sector")
   protected Sector sector;

   @AttributeOverride(name = "waarde", column = @Column(name = "PartijStatusHis"))
   protected StatusHistorie partijStatusHis;

   @ManyToOne
   @JoinColumn(name = "VoortzettendeGem")
   protected Partij voortzettendeGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "Gemcode"))
   protected Gemeentecode gemeentecode;

   @ManyToOne
   @JoinColumn(name = "OnderdeelVan")
   protected Partij onderdeelVan;

   @AttributeOverride(name = "waarde", column = @Column(name = "GemStatusHis"))
   protected StatusHistorie gemeenteStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Partij", sequenceName = "Kern.seq_Partij")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Partij")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new PartijID();
      }
      id.setWaarde(value);
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public SoortPartij getSoort() {
      return soort;
   }

   public void setSoort(final SoortPartij soort) {
      this.soort = soort;
   }

   public Datum getDatumAanvang() {
      return datumAanvang;
   }

   public void setDatumAanvang(final Datum datumAanvang) {
      this.datumAanvang = datumAanvang;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public Sector getSector() {
      return sector;
   }

   public void setSector(final Sector sector) {
      this.sector = sector;
   }

   public StatusHistorie getPartijStatusHis() {
      return partijStatusHis;
   }

   public void setPartijStatusHis(final StatusHistorie partijStatusHis) {
      this.partijStatusHis = partijStatusHis;
   }

   public Partij getVoortzettendeGemeente() {
      return voortzettendeGemeente;
   }

   public void setVoortzettendeGemeente(final Partij voortzettendeGemeente) {
      this.voortzettendeGemeente = voortzettendeGemeente;
   }

   public Gemeentecode getGemeentecode() {
      return gemeentecode;
   }

   public void setGemeentecode(final Gemeentecode gemeentecode) {
      this.gemeentecode = gemeentecode;
   }

   public Partij getOnderdeelVan() {
      return onderdeelVan;
   }

   public void setOnderdeelVan(final Partij onderdeelVan) {
      this.onderdeelVan = onderdeelVan;
   }

   public StatusHistorie getGemeenteStatusHis() {
      return gemeenteStatusHis;
   }

   public void setGemeenteStatusHis(final StatusHistorie gemeenteStatusHis) {
      this.gemeenteStatusHis = gemeenteStatusHis;
   }



}
