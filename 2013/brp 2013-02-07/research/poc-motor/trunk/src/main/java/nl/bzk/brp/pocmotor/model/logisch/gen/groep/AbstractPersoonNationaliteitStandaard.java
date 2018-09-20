/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.RedenVerliesNLNationaliteit;


/**
 * Persoon \ Nationaliteit.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonNationaliteitStandaard extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "RdnVerlies")
   protected RedenVerliesNLNationaliteit redenVerlies;

   @ManyToOne
   @JoinColumn(name = "RdnVerk")
   protected RedenVerkrijgingNLNationaliteit redenVerkrijging;


   public RedenVerliesNLNationaliteit getRedenVerlies() {
      return redenVerlies;
   }

   public void setRedenVerlies(final RedenVerliesNLNationaliteit redenVerlies) {
      this.redenVerlies = redenVerlies;
   }

   public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
      return redenVerkrijging;
   }

   public void setRedenVerkrijging(final RedenVerkrijgingNLNationaliteit redenVerkrijging) {
      this.redenVerkrijging = redenVerkrijging;
   }



}
