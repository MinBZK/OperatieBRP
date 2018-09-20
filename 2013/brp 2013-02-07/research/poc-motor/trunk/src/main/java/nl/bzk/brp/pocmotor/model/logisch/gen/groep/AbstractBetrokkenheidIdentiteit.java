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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BetrokkenheidID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;


/**
 * Betrokkenheid.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBetrokkenheidIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected BetrokkenheidID iD;

   @ManyToOne
   @JoinColumn(name = "Relatie")
   protected Relatie relatie;

   @Column(name = "Rol")
   protected SoortBetrokkenheid rol;

   @ManyToOne
   @JoinColumn(name = "Betrokkene")
   protected Persoon betrokkene;


   public BetrokkenheidID getID() {
      return iD;
   }

   public void setID(final BetrokkenheidID iD) {
      this.iD = iD;
   }

   public Relatie getRelatie() {
      return relatie;
   }

   public void setRelatie(final Relatie relatie) {
      this.relatie = relatie;
   }

   public SoortBetrokkenheid getRol() {
      return rol;
   }

   public void setRol(final SoortBetrokkenheid rol) {
      this.rol = rol;
   }

   public Persoon getBetrokkene() {
      return betrokkene;
   }

   public void setBetrokkene(final Persoon betrokkene) {
      this.betrokkene = betrokkene;
   }



}
