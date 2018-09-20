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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenOntbrekenReisdocumentCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenOntbrekenReisdocumentID;


/**
 * Reden vervallen reisdocument.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenVervallenReisdocumentIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected RedenOntbrekenReisdocumentID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected RedenOntbrekenReisdocumentCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;


   public RedenOntbrekenReisdocumentID getID() {
      return iD;
   }

   public void setID(final RedenOntbrekenReisdocumentID iD) {
      this.iD = iD;
   }

   public RedenOntbrekenReisdocumentCode getCode() {
      return code;
   }

   public void setCode(final RedenOntbrekenReisdocumentCode code) {
      this.code = code;
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }



}
