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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.MultiRealiteitregelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortMultiRealiteitRegel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;


/**
 * Multi-realiteit regel.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMultiRealiteitRegelIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected MultiRealiteitregelID iD;

   @ManyToOne
   @JoinColumn(name = "GeldigVoor")
   protected Persoon geldigVoor;

   @Column(name = "Srt")
   protected SoortMultiRealiteitRegel soort;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "MultiRealiteitPers")
   protected Persoon multiRealiteitPersoon;

   @ManyToOne
   @JoinColumn(name = "Relatie")
   protected Relatie relatie;

   @ManyToOne
   @JoinColumn(name = "Betr")
   protected Betrokkenheid betrokkenheid;


   public MultiRealiteitregelID getID() {
      return iD;
   }

   public void setID(final MultiRealiteitregelID iD) {
      this.iD = iD;
   }

   public Persoon getGeldigVoor() {
      return geldigVoor;
   }

   public void setGeldigVoor(final Persoon geldigVoor) {
      this.geldigVoor = geldigVoor;
   }

   public SoortMultiRealiteitRegel getSoort() {
      return soort;
   }

   public void setSoort(final SoortMultiRealiteitRegel soort) {
      this.soort = soort;
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Persoon getMultiRealiteitPersoon() {
      return multiRealiteitPersoon;
   }

   public void setMultiRealiteitPersoon(final Persoon multiRealiteitPersoon) {
      this.multiRealiteitPersoon = multiRealiteitPersoon;
   }

   public Relatie getRelatie() {
      return relatie;
   }

   public void setRelatie(final Relatie relatie) {
      this.relatie = relatie;
   }

   public Betrokkenheid getBetrokkenheid() {
      return betrokkenheid;
   }

   public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
      this.betrokkenheid = betrokkenheid;
   }



}
