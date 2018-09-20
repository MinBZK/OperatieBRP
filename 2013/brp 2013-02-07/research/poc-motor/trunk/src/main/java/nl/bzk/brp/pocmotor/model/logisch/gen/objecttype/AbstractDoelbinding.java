/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.objecttype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractStatischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DoelbindingID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.DoelbindingIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.DoelbindingStandaard;


/**
 * Doelbinding

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbinding extends AbstractStatischObjectType {

   // Groepen
   protected DoelbindingIdentiteit identiteit = new DoelbindingIdentiteit();

   protected DoelbindingStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Doelbinding", sequenceName = "AutAut.seq_Doelbinding")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Doelbinding")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new DoelbindingIdentiteit();
        }
        identiteit.setID(new DoelbindingID());
        identiteit.getID().setWaarde(id);
   }

   public DoelbindingIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final DoelbindingIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public DoelbindingStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final DoelbindingStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
