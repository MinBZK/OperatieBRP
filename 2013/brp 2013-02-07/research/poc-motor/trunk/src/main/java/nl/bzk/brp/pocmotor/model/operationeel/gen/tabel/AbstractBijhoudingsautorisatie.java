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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BijhoudingsautorisatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.BeperkingPopulatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBijhouding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortPartij;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Toestand;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Autorisatiebesluit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Bijhoudingsautorisatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBijhoudingsautorisatie extends AbstractTabel {

   @Transient
   protected BijhoudingsautorisatieID id;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatiebesluit")
   protected Autorisatiebesluit bijhoudingsautorisatiebesluit;

   @Column(name = "Verantwoordelijke")
   protected Verantwoordelijke verantwoordelijke;

   @Column(name = "SrtBijhouding")
   protected SoortBijhouding soortBijhouding;

   @Column(name = "GeautoriseerdeSrtPartij")
   protected SoortPartij geautoriseerdeSoortPartij;

   @ManyToOne
   @JoinColumn(name = "GeautoriseerdePartij")
   protected Partij geautoriseerdePartij;

   @Column(name = "Toestand")
   protected Toestand toestand;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;

   @Column(name = "BeperkingPopulatie")
   protected BeperkingPopulatie beperkingPopulatie;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
   protected Datum datumIngang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "BijhautorisatieStatusHis"))
   protected StatusHistorie bijhoudingsautorisatieStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Bijhautorisatie", sequenceName = "AutAut.seq_Bijhautorisatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Bijhautorisatie")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Short value) {
      if (id == null) {
          id = new BijhoudingsautorisatieID();
      }
      id.setWaarde(value);
   }

   public Autorisatiebesluit getBijhoudingsautorisatiebesluit() {
      return bijhoudingsautorisatiebesluit;
   }

   public void setBijhoudingsautorisatiebesluit(final Autorisatiebesluit bijhoudingsautorisatiebesluit) {
      this.bijhoudingsautorisatiebesluit = bijhoudingsautorisatiebesluit;
   }

   public Verantwoordelijke getVerantwoordelijke() {
      return verantwoordelijke;
   }

   public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
      this.verantwoordelijke = verantwoordelijke;
   }

   public SoortBijhouding getSoortBijhouding() {
      return soortBijhouding;
   }

   public void setSoortBijhouding(final SoortBijhouding soortBijhouding) {
      this.soortBijhouding = soortBijhouding;
   }

   public SoortPartij getGeautoriseerdeSoortPartij() {
      return geautoriseerdeSoortPartij;
   }

   public void setGeautoriseerdeSoortPartij(final SoortPartij geautoriseerdeSoortPartij) {
      this.geautoriseerdeSoortPartij = geautoriseerdeSoortPartij;
   }

   public Partij getGeautoriseerdePartij() {
      return geautoriseerdePartij;
   }

   public void setGeautoriseerdePartij(final Partij geautoriseerdePartij) {
      this.geautoriseerdePartij = geautoriseerdePartij;
   }

   public Toestand getToestand() {
      return toestand;
   }

   public void setToestand(final Toestand toestand) {
      this.toestand = toestand;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }

   public BeperkingPopulatie getBeperkingPopulatie() {
      return beperkingPopulatie;
   }

   public void setBeperkingPopulatie(final BeperkingPopulatie beperkingPopulatie) {
      this.beperkingPopulatie = beperkingPopulatie;
   }

   public Datum getDatumIngang() {
      return datumIngang;
   }

   public void setDatumIngang(final Datum datumIngang) {
      this.datumIngang = datumIngang;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public StatusHistorie getBijhoudingsautorisatieStatusHis() {
      return bijhoudingsautorisatieStatusHis;
   }

   public void setBijhoudingsautorisatieStatusHis(final StatusHistorie bijhoudingsautorisatieStatusHis) {
      this.bijhoudingsautorisatieStatusHis = bijhoudingsautorisatieStatusHis;
   }



}
