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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ANummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;


/**
 * Persoon.Identificatienummers

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonIdentificatienummers extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "BSN"))
   protected Burgerservicenummer burgerservicenummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "ANr"))
   protected ANummer administratienummer;


   public Burgerservicenummer getBurgerservicenummer() {
      return burgerservicenummer;
   }

   public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
      this.burgerservicenummer = burgerservicenummer;
   }

   public ANummer getAdministratienummer() {
      return administratienummer;
   }

   public void setAdministratienummer(final ANummer administratienummer) {
      this.administratienummer = administratienummer;
   }



}
