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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonNationaliteitID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVerliesNLNationaliteit;


/**
 * His Persoon \ Nationaliteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonNationaliteit extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonNationaliteitID id;

   @ManyToOne
   @JoinColumn(name = "PersNation")
   protected PersoonNationaliteit persoonNationaliteit;

   @ManyToOne
   @JoinColumn(name = "RdnVerlies")
   protected RedenVerliesNLNationaliteit redenVerlies;

   @ManyToOne
   @JoinColumn(name = "RdnVerk")
   protected RedenVerkrijgingNLNationaliteit redenVerkrijging;


   @Id
   @SequenceGenerator(name = "seq_His_PersNation", sequenceName = "Kern.seq_His_PersNation")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersNation")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonNationaliteitID();
      }
      id.setWaarde(value);
   }

   public PersoonNationaliteit getPersoonNationaliteit() {
      return persoonNationaliteit;
   }

   public void setPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
      this.persoonNationaliteit = persoonNationaliteit;
   }

   public RedenVerliesNLNationaliteit getRedenVerlies() {
      return redenVerlies;
   }

   public void setRedenVerlies(final RedenVerliesNLNationaliteit redenVerlies) {
      this.redenVerlies = redenVerlies;
   }

   public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
      return redenVerkrijging;
   }

   public void setRedenVerkrijging(final RedenVerkrijgingNLNationaliteit redenVerkrijging) {
      this.redenVerkrijging = redenVerkrijging;
   }



}
