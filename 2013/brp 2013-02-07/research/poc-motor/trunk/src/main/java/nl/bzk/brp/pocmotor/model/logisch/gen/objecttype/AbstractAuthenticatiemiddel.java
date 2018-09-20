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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AuthenticatiemiddelID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.AuthenticatiemiddelIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.AuthenticatiemiddelStandaard;


/**
 * Authenticatiemiddel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAuthenticatiemiddel extends AbstractStatischObjectType {

   // Groepen
   protected AuthenticatiemiddelIdentiteit identiteit = new AuthenticatiemiddelIdentiteit();

   protected AuthenticatiemiddelStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Authenticatiemiddel", sequenceName = "AutAut.seq_Authenticatiemiddel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Authenticatiemiddel")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new AuthenticatiemiddelIdentiteit();
        }
        identiteit.setID(new AuthenticatiemiddelID());
        identiteit.getID().setWaarde(id);
   }

   public AuthenticatiemiddelIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final AuthenticatiemiddelIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public AuthenticatiemiddelStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final AuthenticatiemiddelStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
