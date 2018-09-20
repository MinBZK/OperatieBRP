/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;
// Generated 12-sep-2012 11:17:43 by Hibernate Tools 3.2.4.GA



import nl.bzk.brp.testdatageneratie.dataaccess.Cacheable;

public class Land implements Cacheable<Short> {

     private int id;
     private short code;
     private String naam;
     private String iso31661alpha2;
     private Integer dataanvgel;
     private Integer dateindegel;

    public Land() {
    }

	
    public Land(int id, short code, String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }
    public Land(int id, short code, String naam, String iso31661alpha2, Integer dataanvgel, Integer dateindegel) {
       this.id = id;
       this.code = code;
       this.naam = naam;
       this.iso31661alpha2 = iso31661alpha2;
       this.dataanvgel = dataanvgel;
       this.dateindegel = dateindegel;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public short getCode() {
        return this.code;
    }
    
    public void setCode(short code) {
        this.code = code;
    }
    public String getNaam() {
        return this.naam;
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }
    public String getIso31661alpha2() {
        return this.iso31661alpha2;
    }
    
    public void setIso31661alpha2(String iso31661alpha2) {
        this.iso31661alpha2 = iso31661alpha2;
    }
    public Integer getDataanvgel() {
        return this.dataanvgel;
    }
    
    public void setDataanvgel(Integer dataanvgel) {
        this.dataanvgel = dataanvgel;
    }
    public Integer getDateindegel() {
        return this.dateindegel;
    }
    
    public void setDateindegel(Integer dateindegel) {
        this.dateindegel = dateindegel;
    }

    @Override
    public Short getKey() {
        return code;
    }

}


