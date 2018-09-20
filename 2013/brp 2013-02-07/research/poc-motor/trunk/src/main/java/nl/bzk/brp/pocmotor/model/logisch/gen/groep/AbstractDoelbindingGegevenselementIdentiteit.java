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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.GegevenselementDoelbindingID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.DatabaseObject;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Doelbinding;


/**
 * Doelbinding \ Gegevenselement.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbindingGegevenselementIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected GegevenselementDoelbindingID iD;

   @ManyToOne
   @JoinColumn(name = "Doelbinding")
   protected Doelbinding doelbinding;

   @ManyToOne
   @JoinColumn(name = "Gegevenselement")
   protected DatabaseObject gegevenselement;


   public GegevenselementDoelbindingID getID() {
      return iD;
   }

   public void setID(final GegevenselementDoelbindingID iD) {
      this.iD = iD;
   }

   public Doelbinding getDoelbinding() {
      return doelbinding;
   }

   public void setDoelbinding(final Doelbinding doelbinding) {
      this.doelbinding = doelbinding;
   }

   public DatabaseObject getGegevenselement() {
      return gegevenselement;
   }

   public void setGegevenselement(final DatabaseObject gegevenselement) {
      this.gegevenselement = gegevenselement;
   }



}
