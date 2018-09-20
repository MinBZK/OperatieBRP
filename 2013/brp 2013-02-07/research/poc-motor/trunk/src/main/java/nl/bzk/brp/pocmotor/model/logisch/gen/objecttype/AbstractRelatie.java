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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractDynamischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RelatieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RelatieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RelatieStandaard;


/**
 * Relatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRelatie extends AbstractDynamischObjectType {

   // Groepen
   protected RelatieIdentiteit identiteit = new RelatieIdentiteit();

   protected RelatieStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Relatie", sequenceName = "Kern.seq_Relatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Relatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new RelatieIdentiteit();
        }
        identiteit.setID(new RelatieID());
        identiteit.getID().setWaarde(id);
   }

   public RelatieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final RelatieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public RelatieStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final RelatieStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
