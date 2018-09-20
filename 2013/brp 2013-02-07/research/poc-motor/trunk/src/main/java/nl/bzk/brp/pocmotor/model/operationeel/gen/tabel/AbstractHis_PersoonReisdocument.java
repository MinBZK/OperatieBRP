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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonReisdocumentID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LengteInCm;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ReisdocumentNummer;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonReisdocument;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVervallenReisdocument;


/**
 * His Persoon \ Reisdocument

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonReisdocument extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonReisdocumentID id;

   @ManyToOne
   @JoinColumn(name = "PersReisdoc")
   protected PersoonReisdocument persoonReisdocument;

   @AttributeOverride(name = "waarde", column = @Column(name = "Nr"))
   protected ReisdocumentNummer nummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatUitgifte"))
   protected Datum datumUitgifte;

   @ManyToOne
   @JoinColumn(name = "AutVanAfgifte")
   protected AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzeEindeGel"))
   protected Datum datumVoorzieneEindeGeldigheid;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInhingVermissing"))
   protected Datum datumInhoudingVermissing;

   @ManyToOne
   @JoinColumn(name = "RdnVervallen")
   protected RedenVervallenReisdocument redenVervallen;

   @AttributeOverride(name = "waarde", column = @Column(name = "LengteHouder"))
   protected LengteInCm lengteHouder;


   @Id
   @SequenceGenerator(name = "seq_His_PersReisdoc", sequenceName = "Kern.seq_His_PersReisdoc")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersReisdoc")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonReisdocumentID();
      }
      id.setWaarde(value);
   }

   public PersoonReisdocument getPersoonReisdocument() {
      return persoonReisdocument;
   }

   public void setPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
      this.persoonReisdocument = persoonReisdocument;
   }

   public ReisdocumentNummer getNummer() {
      return nummer;
   }

   public void setNummer(final ReisdocumentNummer nummer) {
      this.nummer = nummer;
   }

   public Datum getDatumUitgifte() {
      return datumUitgifte;
   }

   public void setDatumUitgifte(final Datum datumUitgifte) {
      this.datumUitgifte = datumUitgifte;
   }

   public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte() {
      return autoriteitVanAfgifte;
   }

   public void setAutoriteitVanAfgifte(final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte) {
      this.autoriteitVanAfgifte = autoriteitVanAfgifte;
   }

   public Datum getDatumVoorzieneEindeGeldigheid() {
      return datumVoorzieneEindeGeldigheid;
   }

   public void setDatumVoorzieneEindeGeldigheid(final Datum datumVoorzieneEindeGeldigheid) {
      this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
   }

   public Datum getDatumInhoudingVermissing() {
      return datumInhoudingVermissing;
   }

   public void setDatumInhoudingVermissing(final Datum datumInhoudingVermissing) {
      this.datumInhoudingVermissing = datumInhoudingVermissing;
   }

   public RedenVervallenReisdocument getRedenVervallen() {
      return redenVervallen;
   }

   public void setRedenVervallen(final RedenVervallenReisdocument redenVervallen) {
      this.redenVervallen = redenVervallen;
   }

   public LengteInCm getLengteHouder() {
      return lengteHouder;
   }

   public void setLengteHouder(final LengteInCm lengteHouder) {
      this.lengteHouder = lengteHouder;
   }



}
