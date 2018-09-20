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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenVerliesNLNationaliteitID;


/**
 * Reden verlies NL nationaliteit.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenVerliesNLNationaliteitIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected RedenVerliesNLNationaliteitID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;


   public RedenVerliesNLNationaliteitID getID() {
      return iD;
   }

   public void setID(final RedenVerliesNLNationaliteitID iD) {
      this.iD = iD;
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }



}
