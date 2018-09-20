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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonGeboorteID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Land;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Plaats;


/**
 * His Persoon Geboorte

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonGeboorte extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonGeboorteID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatGeboorte"))
   protected Datum datumGeboorte;

   @ManyToOne
   @JoinColumn(name = "GemGeboorte")
   protected Partij gemeenteGeboorte;

   @ManyToOne
   @JoinColumn(name = "WplGeboorte")
   protected Plaats woonplaatsGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLGeboorteplaats"))
   protected BuitenlandsePlaats buitenlandseGeboorteplaats;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioGeboorte"))
   protected BuitenlandseRegio buitenlandseRegioGeboorte;

   @ManyToOne
   @JoinColumn(name = "LandGeboorte")
   protected Land landGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsGeboorteloc"))
   protected LocatieOmschrijving omschrijvingGeboortelocatie;


   @Id
   @SequenceGenerator(name = "seq_His_PersGeboorte", sequenceName = "Kern.seq_His_PersGeboorte")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersGeboorte")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonGeboorteID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Datum getDatumGeboorte() {
      return datumGeboorte;
   }

   public void setDatumGeboorte(final Datum datumGeboorte) {
      this.datumGeboorte = datumGeboorte;
   }

   public Partij getGemeenteGeboorte() {
      return gemeenteGeboorte;
   }

   public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
      this.gemeenteGeboorte = gemeenteGeboorte;
   }

   public Plaats getWoonplaatsGeboorte() {
      return woonplaatsGeboorte;
   }

   public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
      this.woonplaatsGeboorte = woonplaatsGeboorte;
   }

   public BuitenlandsePlaats getBuitenlandseGeboorteplaats() {
      return buitenlandseGeboorteplaats;
   }

   public void setBuitenlandseGeboorteplaats(final BuitenlandsePlaats buitenlandseGeboorteplaats) {
      this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
   }

   public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
      return buitenlandseRegioGeboorte;
   }

   public void setBuitenlandseRegioGeboorte(final BuitenlandseRegio buitenlandseRegioGeboorte) {
      this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
   }

   public Land getLandGeboorte() {
      return landGeboorte;
   }

   public void setLandGeboorte(final Land landGeboorte) {
      this.landGeboorte = landGeboorte;
   }

   public LocatieOmschrijving getOmschrijvingGeboortelocatie() {
      return omschrijvingGeboortelocatie;
   }

   public void setOmschrijvingGeboortelocatie(final LocatieOmschrijving omschrijvingGeboortelocatie) {
      this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
   }



}
