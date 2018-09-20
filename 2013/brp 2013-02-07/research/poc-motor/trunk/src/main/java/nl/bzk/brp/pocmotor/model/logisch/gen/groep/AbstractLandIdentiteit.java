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
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ISO31661Alpha2;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LandID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Landcode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;


/**
 * Land.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLandIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected LandID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @AttributeOverride(name = "waarde", column = @Column(name = "ISO31661Alpha2"))
   protected ISO31661Alpha2 iSO31661Alpha2;

   @AttributeOverride(name = "waarde", column = @Column(name = "Landcode"))
   protected Landcode landcode;


   public LandID getID() {
      return iD;
   }

   public void setID(final LandID iD) {
      this.iD = iD;
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public ISO31661Alpha2 getISO31661Alpha2() {
      return iSO31661Alpha2;
   }

   public void setISO31661Alpha2(final ISO31661Alpha2 iSO31661Alpha2) {
      this.iSO31661Alpha2 = iSO31661Alpha2;
   }

   public Landcode getLandcode() {
      return landcode;
   }

   public void setLandcode(final Landcode landcode) {
      this.landcode = landcode;
   }



}
