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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.RedenOpschorting;


/**
 * Persoon.Opschorting

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonOpschorting extends AbstractGroep {

   @Column(name = "RdnOpschortingBijhouding")
   protected RedenOpschorting redenOpschortingBijhouding;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatOpschorting"))
   protected Datum datumOpschorting;


   public RedenOpschorting getRedenOpschortingBijhouding() {
      return redenOpschortingBijhouding;
   }

   public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
      this.redenOpschortingBijhouding = redenOpschortingBijhouding;
   }

   public Datum getDatumOpschorting() {
      return datumOpschorting;
   }

   public void setDatumOpschorting(final Datum datumOpschorting) {
      this.datumOpschorting = datumOpschorting;
   }



}
