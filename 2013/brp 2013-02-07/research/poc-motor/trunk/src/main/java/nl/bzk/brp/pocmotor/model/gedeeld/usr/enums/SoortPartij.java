/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Soort partij
  */
public enum SoortPartij {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null),
   DERDE("Derde"),
   GEMEENTE("Gemeente"),
   VERTEGENWOORDIGER_REGERING("Vertegenwoordiger Regering"),
   OVERHEIDSORGAAN("Overheidsorgaan"),
   SAMENWERKINGSVERBAND("Samenwerkingsverband"),
   WETGEVER("Wetgever");


   private final String naam;



   private SoortPartij(final String naam) {
      this.naam = naam;
   }


   public String getNaam() {
      return naam;
   }



}
