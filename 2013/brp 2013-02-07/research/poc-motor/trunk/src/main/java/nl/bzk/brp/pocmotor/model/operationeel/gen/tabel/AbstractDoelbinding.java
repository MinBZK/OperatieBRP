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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DoelbindingID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Populatiecriterium;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.TekstDoelbinding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Protocolleringsniveau;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Autorisatiebesluit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Doelbinding

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbinding extends AbstractTabel {

   @Transient
   protected DoelbindingID id;

   @ManyToOne
   @JoinColumn(name = "Levsautorisatiebesluit")
   protected Autorisatiebesluit leveringsautorisatiebesluit;

   @ManyToOne
   @JoinColumn(name = "Geautoriseerde")
   protected Partij geautoriseerde;

   @Column(name = "Protocolleringsniveau")
   protected Protocolleringsniveau protocolleringsniveau;

   @AttributeOverride(name = "waarde", column = @Column(name = "TekstDoelbinding"))
   protected TekstDoelbinding tekstDoelbinding;

   @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
   protected Populatiecriterium populatiecriterium;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndVerstrbeperkingHonoreren"))
   protected JaNee indicatieVerstrekkingsbeperkingHonoreren;

   @AttributeOverride(name = "waarde", column = @Column(name = "DoelbindingStatusHis"))
   protected StatusHistorie doelbindingStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Doelbinding", sequenceName = "AutAut.seq_Doelbinding")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Doelbinding")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new DoelbindingID();
      }
      id.setWaarde(value);
   }

   public Autorisatiebesluit getLeveringsautorisatiebesluit() {
      return leveringsautorisatiebesluit;
   }

   public void setLeveringsautorisatiebesluit(final Autorisatiebesluit leveringsautorisatiebesluit) {
      this.leveringsautorisatiebesluit = leveringsautorisatiebesluit;
   }

   public Partij getGeautoriseerde() {
      return geautoriseerde;
   }

   public void setGeautoriseerde(final Partij geautoriseerde) {
      this.geautoriseerde = geautoriseerde;
   }

   public Protocolleringsniveau getProtocolleringsniveau() {
      return protocolleringsniveau;
   }

   public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
      this.protocolleringsniveau = protocolleringsniveau;
   }

   public TekstDoelbinding getTekstDoelbinding() {
      return tekstDoelbinding;
   }

   public void setTekstDoelbinding(final TekstDoelbinding tekstDoelbinding) {
      this.tekstDoelbinding = tekstDoelbinding;
   }

   public Populatiecriterium getPopulatiecriterium() {
      return populatiecriterium;
   }

   public void setPopulatiecriterium(final Populatiecriterium populatiecriterium) {
      this.populatiecriterium = populatiecriterium;
   }

   public JaNee getIndicatieVerstrekkingsbeperkingHonoreren() {
      return indicatieVerstrekkingsbeperkingHonoreren;
   }

   public void setIndicatieVerstrekkingsbeperkingHonoreren(final JaNee indicatieVerstrekkingsbeperkingHonoreren) {
      this.indicatieVerstrekkingsbeperkingHonoreren = indicatieVerstrekkingsbeperkingHonoreren;
   }

   public StatusHistorie getDoelbindingStatusHis() {
      return doelbindingStatusHis;
   }

   public void setDoelbindingStatusHis(final StatusHistorie doelbindingStatusHis) {
      this.doelbindingStatusHis = doelbindingStatusHis;
   }



}
