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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutorisatiebesluitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.TekstUitBesluit;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortAutorisatiebesluit;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Toestand;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Autorisatiebesluit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Autorisatiebesluit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutorisatiebesluit extends AbstractTabel {

   @Transient
   protected AutorisatiebesluitID id;

   @Column(name = "Srt")
   protected SoortAutorisatiebesluit soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Besluittekst"))
   protected TekstUitBesluit besluittekst;

   @ManyToOne
   @JoinColumn(name = "Autoriseerder")
   protected Partij autoriseerder;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndModelBesluit"))
   protected JaNee indicatieModelBesluit;

   @ManyToOne
   @JoinColumn(name = "GebaseerdOp")
   protected Autorisatiebesluit gebaseerdOp;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndIngetrokken"))
   protected JaNee indicatieIngetrokken;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatBesluit"))
   protected Datum datumBesluit;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
   protected Datum datumIngang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "AutorisatiebesluitStatusHis"))
   protected StatusHistorie autorisatiebesluitStatusHis;

   @Column(name = "Toestand")
   protected Toestand toestand;

   @AttributeOverride(name = "waarde", column = @Column(name = "BijhautorisatiebesluitStatus"))
   protected StatusHistorie bijhoudingsautorisatiebesluitStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Autorisatiebesluit", sequenceName = "AutAut.seq_Autorisatiebesluit")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Autorisatiebesluit")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AutorisatiebesluitID();
      }
      id.setWaarde(value);
   }

   public SoortAutorisatiebesluit getSoort() {
      return soort;
   }

   public void setSoort(final SoortAutorisatiebesluit soort) {
      this.soort = soort;
   }

   public TekstUitBesluit getBesluittekst() {
      return besluittekst;
   }

   public void setBesluittekst(final TekstUitBesluit besluittekst) {
      this.besluittekst = besluittekst;
   }

   public Partij getAutoriseerder() {
      return autoriseerder;
   }

   public void setAutoriseerder(final Partij autoriseerder) {
      this.autoriseerder = autoriseerder;
   }

   public JaNee getIndicatieModelBesluit() {
      return indicatieModelBesluit;
   }

   public void setIndicatieModelBesluit(final JaNee indicatieModelBesluit) {
      this.indicatieModelBesluit = indicatieModelBesluit;
   }

   public Autorisatiebesluit getGebaseerdOp() {
      return gebaseerdOp;
   }

   public void setGebaseerdOp(final Autorisatiebesluit gebaseerdOp) {
      this.gebaseerdOp = gebaseerdOp;
   }

   public JaNee getIndicatieIngetrokken() {
      return indicatieIngetrokken;
   }

   public void setIndicatieIngetrokken(final JaNee indicatieIngetrokken) {
      this.indicatieIngetrokken = indicatieIngetrokken;
   }

   public Datum getDatumBesluit() {
      return datumBesluit;
   }

   public void setDatumBesluit(final Datum datumBesluit) {
      this.datumBesluit = datumBesluit;
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

   public StatusHistorie getAutorisatiebesluitStatusHis() {
      return autorisatiebesluitStatusHis;
   }

   public void setAutorisatiebesluitStatusHis(final StatusHistorie autorisatiebesluitStatusHis) {
      this.autorisatiebesluitStatusHis = autorisatiebesluitStatusHis;
   }

   public Toestand getToestand() {
      return toestand;
   }

   public void setToestand(final Toestand toestand) {
      this.toestand = toestand;
   }

   public StatusHistorie getBijhoudingsautorisatiebesluitStatusHis() {
      return bijhoudingsautorisatiebesluitStatusHis;
   }

   public void setBijhoudingsautorisatiebesluitStatusHis(final StatusHistorie bijhoudingsautorisatiebesluitStatusHis) {
      this.bijhoudingsautorisatiebesluitStatusHis = bijhoudingsautorisatiebesluitStatusHis;
   }



}
