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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_AbonnementSoortBerichtID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AbonnementSoortBericht;


/**
 * His Abonnement \ Soort bericht

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_AbonnementSoortBericht extends AbstractFormeleHisTabel {

   @Transient
   protected His_AbonnementSoortBerichtID id;

   @ManyToOne
   @JoinColumn(name = "AbonnementSrtBer")
   protected AbonnementSoortBericht abonnementSoortBericht;


   @Id
   @SequenceGenerator(name = "seq_His_AbonnementSrtBer", sequenceName = "Lev.seq_His_AbonnementSrtBer")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_AbonnementSrtBer")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_AbonnementSoortBerichtID();
      }
      id.setWaarde(value);
   }

   public AbonnementSoortBericht getAbonnementSoortBericht() {
      return abonnementSoortBericht;
   }

   public void setAbonnementSoortBericht(final AbonnementSoortBericht abonnementSoortBericht) {
      this.abonnementSoortBericht = abonnementSoortBericht;
   }



}
