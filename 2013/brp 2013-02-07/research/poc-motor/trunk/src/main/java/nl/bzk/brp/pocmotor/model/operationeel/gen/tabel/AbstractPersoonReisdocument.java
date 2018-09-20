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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LengteInCm;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ReisdocumentID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ReisdocumentNummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.RedenVervallenReisdocument;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.SoortNederlandsReisdocument;


/**
 * Persoon \ Reisdocument

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonReisdocument extends AbstractTabel {

   @Transient
   protected ReisdocumentID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "Srt")
   protected SoortNederlandsReisdocument soort;

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

   @AttributeOverride(name = "waarde", column = @Column(name = "PersReisdocStatusHis"))
   protected StatusHistorie persoonReisdocumentStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersReisdoc", sequenceName = "Kern.seq_PersReisdoc")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersReisdoc")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new ReisdocumentID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public SoortNederlandsReisdocument getSoort() {
      return soort;
   }

   public void setSoort(final SoortNederlandsReisdocument soort) {
      this.soort = soort;
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

   public StatusHistorie getPersoonReisdocumentStatusHis() {
      return persoonReisdocumentStatusHis;
   }

   public void setPersoonReisdocumentStatusHis(final StatusHistorie persoonReisdocumentStatusHis) {
      this.persoonReisdocumentStatusHis = persoonReisdocumentStatusHis;
   }



}
