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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AuthenticatiemiddelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.IPAdres;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Functie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Rol;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Certificaat;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Authenticatiemiddel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAuthenticatiemiddel extends AbstractTabel {

   @Transient
   protected AuthenticatiemiddelID id;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @Column(name = "Rol")
   protected Rol rol;

   @Column(name = "Functie")
   protected Functie functie;

   @ManyToOne
   @JoinColumn(name = "CertificaatTbvSSL")
   protected Certificaat certificaatTbvSSL;

   @ManyToOne
   @JoinColumn(name = "CertificaatTbvOndertekening")
   protected Certificaat certificaatTbvOndertekening;

   @AttributeOverride(name = "waarde", column = @Column(name = "IPAdres"))
   protected IPAdres iPAdres;

   @AttributeOverride(name = "waarde", column = @Column(name = "AuthenticatiemiddelStatusHis"))
   protected StatusHistorie authenticatiemiddelStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Authenticatiemiddel", sequenceName = "AutAut.seq_Authenticatiemiddel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Authenticatiemiddel")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AuthenticatiemiddelID();
      }
      id.setWaarde(value);
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public Rol getRol() {
      return rol;
   }

   public void setRol(final Rol rol) {
      this.rol = rol;
   }

   public Functie getFunctie() {
      return functie;
   }

   public void setFunctie(final Functie functie) {
      this.functie = functie;
   }

   public Certificaat getCertificaatTbvSSL() {
      return certificaatTbvSSL;
   }

   public void setCertificaatTbvSSL(final Certificaat certificaatTbvSSL) {
      this.certificaatTbvSSL = certificaatTbvSSL;
   }

   public Certificaat getCertificaatTbvOndertekening() {
      return certificaatTbvOndertekening;
   }

   public void setCertificaatTbvOndertekening(final Certificaat certificaatTbvOndertekening) {
      this.certificaatTbvOndertekening = certificaatTbvOndertekening;
   }

   public IPAdres getIPAdres() {
      return iPAdres;
   }

   public void setIPAdres(final IPAdres iPAdres) {
      this.iPAdres = iPAdres;
   }

   public StatusHistorie getAuthenticatiemiddelStatusHis() {
      return authenticatiemiddelStatusHis;
   }

   public void setAuthenticatiemiddelStatusHis(final StatusHistorie authenticatiemiddelStatusHis) {
      this.authenticatiemiddelStatusHis = authenticatiemiddelStatusHis;
   }



}
