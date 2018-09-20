/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;


/**
 * Persoon.Bijhoudingsverantwoordelijkheid

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonBijhoudingsverantwoordelijkheid extends AbstractGroep {

   @Column(name = "Verantwoordelijke")
   protected Verantwoordelijke verantwoordelijke;

   //@AttributeOverride(name = "waarde", column = @Column(name = "DatBijhverantwoordelijkheid"))
   @Transient
    protected Datum datumBijhoudingsverantwoordelijkheid;


   public Verantwoordelijke getVerantwoordelijke() {
      return verantwoordelijke;
   }

   public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
      this.verantwoordelijke = verantwoordelijke;
   }

   public Datum getDatumBijhoudingsverantwoordelijkheid() {
      return datumBijhoudingsverantwoordelijkheid;
   }

   public void setDatumBijhoudingsverantwoordelijkheid(final Datum datumBijhoudingsverantwoordelijkheid) {
      this.datumBijhoudingsverantwoordelijkheid = datumBijhoudingsverantwoordelijkheid;
   }



}
