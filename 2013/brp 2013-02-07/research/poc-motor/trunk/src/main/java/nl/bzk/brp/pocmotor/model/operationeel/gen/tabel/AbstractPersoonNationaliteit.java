/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonNationaliteitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Nationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVerliesNLNationaliteit;


/**
 * Persoon \ Nationaliteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonNationaliteit extends AbstractTabel {

   @Transient
   protected PersoonNationaliteitID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "Nation")
   protected Nationaliteit nationaliteit;

   @ManyToOne
   @JoinColumn(name = "RdnVerlies")
   protected RedenVerliesNLNationaliteit redenVerlies;

   @ManyToOne
   @JoinColumn(name = "RdnVerk")
   protected RedenVerkrijgingNLNationaliteit redenVerkrijging;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersNationStatusHis"))
   protected StatusHistorie persoonNationaliteitStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersNation", sequenceName = "Kern.seq_PersNation")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersNation")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonNationaliteitID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Nationaliteit getNationaliteit() {
      return nationaliteit;
   }

   public void setNationaliteit(final Nationaliteit nationaliteit) {
      this.nationaliteit = nationaliteit;
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

   public StatusHistorie getPersoonNationaliteitStatusHis() {
      return persoonNationaliteitStatusHis;
   }

   public void setPersoonNationaliteitStatusHis(final StatusHistorie persoonNationaliteitStatusHis) {
      this.persoonNationaliteitStatusHis = persoonNationaliteitStatusHis;
   }



}
