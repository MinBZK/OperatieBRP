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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Regelspecificatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRegel;


/**
 * Regel.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegelIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected RegelID iD;

   @Column(name = "Srt")
   protected SoortRegel soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected RegelCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "Specificatie"))
   protected Regelspecificatie specificatie;


   public RegelID getID() {
      return iD;
   }

   public void setID(final RegelID iD) {
      this.iD = iD;
   }

   public SoortRegel getSoort() {
      return soort;
   }

   public void setSoort(final SoortRegel soort) {
      this.soort = soort;
   }

   public RegelCode getCode() {
      return code;
   }

   public void setCode(final RegelCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }

   public Regelspecificatie getSpecificatie() {
      return specificatie;
   }

   public void setSpecificatie(final Regelspecificatie specificatie) {
      this.specificatie = specificatie;
   }



}
