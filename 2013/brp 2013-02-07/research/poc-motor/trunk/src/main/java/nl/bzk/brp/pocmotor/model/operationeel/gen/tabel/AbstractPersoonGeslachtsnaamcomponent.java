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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonGeslachtsnaamID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Scheidingsteken;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Volgnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voorvoegsel;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AdellijkeTitel;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Predikaat;


/**
 * Persoon \ Geslachtsnaamcomponent

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonGeslachtsnaamcomponent extends AbstractTabel {

   @Transient
   protected PersoonGeslachtsnaamID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "Volgnr"))
   protected Volgnummer volgnummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
   protected Voorvoegsel voorvoegsel;

   @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
   protected Scheidingsteken scheidingsteken;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected Geslachtsnaamcomponent naam;

   @ManyToOne
   @JoinColumn(name = "Predikaat")
   protected Predikaat predikaat;

   @ManyToOne
   @JoinColumn(name = "AdellijkeTitel")
   protected AdellijkeTitel adellijkeTitel;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersGeslnaamcompStatusHis"))
   protected StatusHistorie persoonGeslachtsnaamcomponentStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersGeslnaamcomp")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonGeslachtsnaamID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Volgnummer getVolgnummer() {
      return volgnummer;
   }

   public void setVolgnummer(final Volgnummer volgnummer) {
      this.volgnummer = volgnummer;
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

   public Geslachtsnaamcomponent getNaam() {
      return naam;
   }

   public void setNaam(final Geslachtsnaamcomponent naam) {
      this.naam = naam;
   }

   public Predikaat getPredikaat() {
      return predikaat;
   }

   public void setPredikaat(final Predikaat predikaat) {
      this.predikaat = predikaat;
   }

   public AdellijkeTitel getAdellijkeTitel() {
      return adellijkeTitel;
   }

   public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
      this.adellijkeTitel = adellijkeTitel;
   }

   public StatusHistorie getPersoonGeslachtsnaamcomponentStatusHis() {
      return persoonGeslachtsnaamcomponentStatusHis;
   }

   public void setPersoonGeslachtsnaamcomponentStatusHis(final StatusHistorie persoonGeslachtsnaamcomponentStatusHis) {
      this.persoonGeslachtsnaamcomponentStatusHis = persoonGeslachtsnaamcomponentStatusHis;
   }



}
