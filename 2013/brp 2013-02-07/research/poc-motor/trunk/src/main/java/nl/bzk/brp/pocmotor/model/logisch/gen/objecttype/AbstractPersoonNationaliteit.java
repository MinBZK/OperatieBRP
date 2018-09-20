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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonNationaliteitID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonNationaliteitIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonNationaliteitStandaard;


/**
 * Persoon \ Nationaliteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonNationaliteit extends AbstractDynamischObjectType {

   // Groepen
   protected PersoonNationaliteitIdentiteit identiteit = new PersoonNationaliteitIdentiteit();

   protected PersoonNationaliteitStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_PersNation", sequenceName = "Kern.seq_PersNation")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersNation")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new PersoonNationaliteitIdentiteit();
        }
        identiteit.setID(new PersoonNationaliteitID());
        identiteit.getID().setWaarde(id);
   }

   public PersoonNationaliteitIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final PersoonNationaliteitIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public PersoonNationaliteitStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final PersoonNationaliteitStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
