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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Predikaat;


/**
 * Persoon.Aanschrijving

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonAanschrijving extends AbstractGroep {

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



}
