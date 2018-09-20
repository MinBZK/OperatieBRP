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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonIndicatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortIndicatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;


/**
 * Persoon \ Indicatie.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonIndicatieIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected PersoonIndicatieID iD;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @Column(name = "Srt")
   protected SoortIndicatie soort;


   public PersoonIndicatieID getID() {
      return iD;
   }

   public void setID(final PersoonIndicatieID iD) {
      this.iD = iD;
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public SoortIndicatie getSoort() {
      return soort;
   }

   public void setSoort(final SoortIndicatie soort) {
      this.soort = soort;
   }



}
