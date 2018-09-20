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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ReisdocumentID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.SoortNederlandsReisdocument;


/**
 * Persoon \ Reisdocument.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonReisdocumentIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected ReisdocumentID iD;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "Srt")
   protected SoortNederlandsReisdocument soort;


   public ReisdocumentID getID() {
      return iD;
   }

   public void setID(final ReisdocumentID iD) {
      this.iD = iD;
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public SoortNederlandsReisdocument getSoort() {
      return soort;
   }

   public void setSoort(final SoortNederlandsReisdocument soort) {
      this.soort = soort;
   }



}
