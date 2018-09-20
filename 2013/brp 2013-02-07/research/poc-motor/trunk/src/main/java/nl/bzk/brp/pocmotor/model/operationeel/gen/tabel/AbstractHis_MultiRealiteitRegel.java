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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_MultiRealiteitRegelID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.MultiRealiteitRegel;


/**
 * His Multi-realiteit regel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_MultiRealiteitRegel extends AbstractFormeleHisTabel {

   @Transient
   protected His_MultiRealiteitRegelID id;

   @ManyToOne
   @JoinColumn(name = "MultiRealiteitRegel")
   protected MultiRealiteitRegel multiRealiteitRegel;


   @Id
   @SequenceGenerator(name = "seq_His_MultiRealiteitRegel", sequenceName = "Kern.seq_His_MultiRealiteitRegel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_MultiRealiteitRegel")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_MultiRealiteitRegelID();
      }
      id.setWaarde(value);
   }

   public MultiRealiteitRegel getMultiRealiteitRegel() {
      return multiRealiteitRegel;
   }

   public void setMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
      this.multiRealiteitRegel = multiRealiteitRegel;
   }



}
