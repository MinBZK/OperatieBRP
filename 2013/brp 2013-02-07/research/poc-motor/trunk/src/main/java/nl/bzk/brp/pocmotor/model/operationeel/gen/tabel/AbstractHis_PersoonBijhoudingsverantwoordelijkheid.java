/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonBijhoudingsverantwoordelijkheidID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon Bijhoudingsverantwoordelijkheid

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonBijhoudingsverantwoordelijkheid extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonBijhoudingsverantwoordelijkheidID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @Column(name = "Verantwoordelijke")
   protected Verantwoordelijke verantwoordelijke;


   @Id
   @SequenceGenerator(name = "seq_His_PersBijhverantwoordelijk", sequenceName = "Kern.seq_His_PersBijhverantwoordelijk")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersBijhverantwoordelijk")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonBijhoudingsverantwoordelijkheidID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Verantwoordelijke getVerantwoordelijke() {
      return verantwoordelijke;
   }

   public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
      this.verantwoordelijke = verantwoordelijke;
   }



}
