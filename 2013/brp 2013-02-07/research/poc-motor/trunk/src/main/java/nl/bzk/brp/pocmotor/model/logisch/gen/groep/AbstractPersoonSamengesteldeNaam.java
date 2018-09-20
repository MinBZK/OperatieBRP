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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Geslachtsnaam;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Scheidingsteken;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornamen;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voorvoegsel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.AdellijkeTitel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Predikaat;


/**
 * Persoon.Samengestelde naam

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonSamengesteldeNaam extends AbstractGroep {

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



}
