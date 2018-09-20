/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.IPAdres;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Functie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Certificaat;


/**
 * Authenticatiemiddel.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAuthenticatiemiddelStandaard extends AbstractGroep {

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



}
