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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_BijhoudingssituatieID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Bijhoudingssituatie;


/**
 * His Bijhoudingssituatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Bijhoudingssituatie extends AbstractFormeleHisTabel {

   @Transient
   protected His_BijhoudingssituatieID id;

   @ManyToOne
   @JoinColumn(name = "Bijhsituatie")
   protected Bijhoudingssituatie bijhoudingssituatie;


   @Id
   @SequenceGenerator(name = "seq_His_Bijhsituatie", sequenceName = "AutAut.seq_His_Bijhsituatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Bijhsituatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_BijhoudingssituatieID();
      }
      id.setWaarde(value);
   }

   public Bijhoudingssituatie getBijhoudingssituatie() {
      return bijhoudingssituatie;
   }

   public void setBijhoudingssituatie(final Bijhoudingssituatie bijhoudingssituatie) {
      this.bijhoudingssituatie = bijhoudingssituatie;
   }



}
