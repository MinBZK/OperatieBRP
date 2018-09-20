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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonIndicatieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIndicatieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIndicatieStandaard;


/**
 * Persoon \ Indicatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonIndicatie extends AbstractDynamischObjectType {

   // Groepen
   protected PersoonIndicatieIdentiteit identiteit = new PersoonIndicatieIdentiteit();

   protected PersoonIndicatieStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_PersIndicatie", sequenceName = "Kern.seq_PersIndicatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersIndicatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new PersoonIndicatieIdentiteit();
        }
        identiteit.setID(new PersoonIndicatieID());
        identiteit.getID().setWaarde(id);
   }

   public PersoonIndicatieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final PersoonIndicatieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public PersoonIndicatieStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final PersoonIndicatieStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
