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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BetrokkenheidID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidNietIngeschrevene;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidOuder;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidOuderlijkGezag;


/**
 * Betrokkenheid

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBetrokkenheid extends AbstractDynamischObjectType {

   // Groepen
   protected BetrokkenheidIdentiteit identiteit = new BetrokkenheidIdentiteit();

   protected BetrokkenheidOuder ouder;

   protected BetrokkenheidNietIngeschrevene nietIngeschrevene;

   protected BetrokkenheidOuderlijkGezag ouderlijkGezag;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Betr", sequenceName = "Kern.seq_Betr")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Betr")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new BetrokkenheidIdentiteit();
        }
        identiteit.setID(new BetrokkenheidID());
        identiteit.getID().setWaarde(id);
   }

   public BetrokkenheidIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final BetrokkenheidIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public BetrokkenheidOuder getOuder() {
      return ouder;
   }

   public void setOuder(final BetrokkenheidOuder ouder) {
      this.ouder = ouder;
   }

   public BetrokkenheidNietIngeschrevene getNietIngeschrevene() {
      return nietIngeschrevene;
   }

   public void setNietIngeschrevene(final BetrokkenheidNietIngeschrevene nietIngeschrevene) {
      this.nietIngeschrevene = nietIngeschrevene;
   }

   public BetrokkenheidOuderlijkGezag getOuderlijkGezag() {
      return ouderlijkGezag;
   }

   public void setOuderlijkGezag(final BetrokkenheidOuderlijkGezag ouderlijkGezag) {
      this.ouderlijkGezag = ouderlijkGezag;
   }



   // Getters/Setters Sets


}
