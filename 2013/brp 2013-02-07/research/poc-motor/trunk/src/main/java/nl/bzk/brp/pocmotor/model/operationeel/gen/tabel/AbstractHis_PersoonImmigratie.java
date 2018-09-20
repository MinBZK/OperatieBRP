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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonImmigratieID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Land;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon Immigratie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonImmigratie extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonImmigratieID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "LandVanwaarGevestigd")
   protected Land landVanwaarGevestigd;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVestigingInNederland"))
   protected Datum datumVestigingInNederland;


   @Id
   @SequenceGenerator(name = "seq_His_PersImmigratie", sequenceName = "Kern.seq_His_PersImmigratie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersImmigratie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonImmigratieID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Land getLandVanwaarGevestigd() {
      return landVanwaarGevestigd;
   }

   public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
      this.landVanwaarGevestigd = landVanwaarGevestigd;
   }

   public Datum getDatumVestigingInNederland() {
      return datumVestigingInNederland;
   }

   public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
      this.datumVestigingInNederland = datumVestigingInNederland;
   }



}
