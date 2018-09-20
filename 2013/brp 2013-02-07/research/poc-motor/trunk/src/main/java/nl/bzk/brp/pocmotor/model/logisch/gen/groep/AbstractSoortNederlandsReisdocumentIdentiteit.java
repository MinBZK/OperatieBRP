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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.SoortNederlandsReisdocumentCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.SoortNederlandsReisdocumentID;


/**
 * Soort Nederlands reisdocument.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractSoortNederlandsReisdocumentIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected SoortNederlandsReisdocumentID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected SoortNederlandsReisdocumentCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;


   public SoortNederlandsReisdocumentID getID() {
      return iD;
   }

   public void setID(final SoortNederlandsReisdocumentID iD) {
      this.iD = iD;
   }

   public SoortNederlandsReisdocumentCode getCode() {
      return code;
   }

   public void setCode(final SoortNederlandsReisdocumentCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }



}
