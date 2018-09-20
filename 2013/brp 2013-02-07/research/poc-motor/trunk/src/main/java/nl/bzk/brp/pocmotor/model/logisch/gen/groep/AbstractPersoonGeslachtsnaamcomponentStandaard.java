/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Scheidingsteken;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voorvoegsel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.AdellijkeTitel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Predikaat;


/**
 * Persoon \ Geslachtsnaamcomponent.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaard extends AbstractGroep {

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



}
