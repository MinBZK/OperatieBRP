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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutoriteitVanAfgifteReisdocumentID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;


/**
 * Autoriteit van afgifte reisdocument.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutoriteitVanAfgifteReisdocumentIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AutoriteitVanAfgifteReisdocumentID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected AutoriteitVanAfgifteReisdocumentCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;


   public AutoriteitVanAfgifteReisdocumentID getID() {
      return iD;
   }

   public void setID(final AutoriteitVanAfgifteReisdocumentID iD) {
      this.iD = iD;
   }

   public AutoriteitVanAfgifteReisdocumentCode getCode() {
      return code;
   }

   public void setCode(final AutoriteitVanAfgifteReisdocumentCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }



}
