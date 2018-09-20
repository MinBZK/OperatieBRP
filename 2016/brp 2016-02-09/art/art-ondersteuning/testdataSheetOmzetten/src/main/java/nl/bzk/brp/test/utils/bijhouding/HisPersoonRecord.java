/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding;

public abstract class HisPersoonRecord implements Comparable<HisPersoonRecord> {

    private Integer iD;
    private Integer persoon;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Integer datumTijdRegistratie;
    private Integer datumTijdVerval;
    private Integer actieInhoud;
    private Integer actieVerval;
    private Integer actieAanpassingGeldigheid;
    private String nadereAanduidingVerval;

    @Override
    public int compareTo(final HisPersoonRecord that) {
        int compare = that.datumAanvangGeldigheid - this.datumAanvangGeldigheid;
        if (compare == 0) {
            if (this.datumEindeGeldigheid != null && that.datumEindeGeldigheid != null) {
                compare = that.datumEindeGeldigheid - this.datumEindeGeldigheid;
            } else if (this.datumEindeGeldigheid != null) {
                return -1;
            } else if (that.datumEindeGeldigheid != null) {
                return 1;
            }
            if (compare == 0 && this != that) {
                throw new IllegalStateException("Records zijn hetzelfde!");
            }
        }
        return compare;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(final Integer iD) {
        this.iD = iD;
    }

    public Integer getPersoon() {
        return persoon;
    }

    public void setPersoon(final Integer persoon) {
        this.persoon = persoon;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public Integer getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    public void setDatumTijdRegistratie(final Integer datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    public Integer getDatumTijdVerval() {
        return datumTijdVerval;
    }

    public void setDatumTijdVerval(final Integer datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    public Integer getActieInhoud() {
        return actieInhoud;
    }

    public void setActieInhoud(final Integer actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    public Integer getActieVerval() {
        return actieVerval;
    }

    public void setActieVerval(final Integer actieVerval) {
        this.actieVerval = actieVerval;
    }

    public Integer getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    public void setActieAanpassingGeldigheid(final Integer actieAanpassingGeldigheid) {
        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
    }

    public String getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    public void setNadereAanduidingVerval(final String nadereAanduidingVerval) {
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

}
