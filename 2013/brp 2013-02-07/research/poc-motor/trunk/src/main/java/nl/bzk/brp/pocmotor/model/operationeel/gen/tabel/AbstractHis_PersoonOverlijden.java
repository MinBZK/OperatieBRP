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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonOverlijdenID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Land;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Plaats;


/**
 * His Persoon Overlijden

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonOverlijden extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonOverlijdenID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatOverlijden"))
   protected Datum datumOverlijden;

   @ManyToOne
   @JoinColumn(name = "GemOverlijden")
   protected Partij gemeenteOverlijden;

   @ManyToOne
   @JoinColumn(name = "WplOverlijden")
   protected Plaats woonplaatsOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsOverlijden"))
   protected BuitenlandsePlaats buitenlandsePlaatsOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioOverlijden"))
   protected BuitenlandseRegio buitenlandseRegioOverlijden;

   @ManyToOne
   @JoinColumn(name = "LandOverlijden")
   protected Land landOverlijden;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocOverlijden"))
   protected LocatieOmschrijving omschrijvingLocatieOverlijden;


   @Id
   @SequenceGenerator(name = "seq_His_PersOverlijden", sequenceName = "Kern.seq_His_PersOverlijden")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersOverlijden")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonOverlijdenID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Datum getDatumOverlijden() {
      return datumOverlijden;
   }

   public void setDatumOverlijden(final Datum datumOverlijden) {
      this.datumOverlijden = datumOverlijden;
   }

   public Partij getGemeenteOverlijden() {
      return gemeenteOverlijden;
   }

   public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
      this.gemeenteOverlijden = gemeenteOverlijden;
   }

   public Plaats getWoonplaatsOverlijden() {
      return woonplaatsOverlijden;
   }

   public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
      this.woonplaatsOverlijden = woonplaatsOverlijden;
   }

   public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
      return buitenlandsePlaatsOverlijden;
   }

   public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaats buitenlandsePlaatsOverlijden) {
      this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
   }

   public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
      return buitenlandseRegioOverlijden;
   }

   public void setBuitenlandseRegioOverlijden(final BuitenlandseRegio buitenlandseRegioOverlijden) {
      this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
   }

   public Land getLandOverlijden() {
      return landOverlijden;
   }

   public void setLandOverlijden(final Land landOverlijden) {
      this.landOverlijden = landOverlijden;
   }

   public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
      return omschrijvingLocatieOverlijden;
   }

   public void setOmschrijvingLocatieOverlijden(final LocatieOmschrijving omschrijvingLocatieOverlijden) {
      this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
   }



}
