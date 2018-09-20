/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.GegevenselementDoelbindingID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.DatabaseObject;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Doelbinding;


/**
 * Doelbinding \ Gegevenselement

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbindingGegevenselement extends AbstractTabel {

   @Transient
   protected GegevenselementDoelbindingID id;

   @ManyToOne
   @JoinColumn(name = "Doelbinding")
   protected Doelbinding doelbinding;

   @ManyToOne
   @JoinColumn(name = "Gegevenselement")
   protected DatabaseObject gegevenselement;


   @Id
   @SequenceGenerator(name = "seq_DoelbindingGegevenselement", sequenceName = "AutAut.seq_DoelbindingGegevenselement")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_DoelbindingGegevenselement")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new GegevenselementDoelbindingID();
      }
      id.setWaarde(value);
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
