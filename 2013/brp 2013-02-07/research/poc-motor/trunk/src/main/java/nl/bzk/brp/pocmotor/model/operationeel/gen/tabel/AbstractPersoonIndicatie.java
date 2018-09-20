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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Ja;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonIndicatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortIndicatie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * Persoon \ Indicatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonIndicatie extends AbstractTabel {

   @Transient
   protected PersoonIndicatieID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @Column(name = "Srt")
   protected SoortIndicatie soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Waarde"))
   protected Ja waarde;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersIndicatieStatusHis"))
   protected StatusHistorie persoonIndicatieStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersIndicatie", sequenceName = "Kern.seq_PersIndicatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersIndicatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonIndicatieID();
      }
      id.setWaarde(value);
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

   public Ja getWaarde() {
      return waarde;
   }

   public void setWaarde(final Ja waarde) {
      this.waarde = waarde;
   }

   public StatusHistorie getPersoonIndicatieStatusHis() {
      return persoonIndicatieStatusHis;
   }

   public void setPersoonIndicatieStatusHis(final StatusHistorie persoonIndicatieStatusHis) {
      this.persoonIndicatieStatusHis = persoonIndicatieStatusHis;
   }



}
