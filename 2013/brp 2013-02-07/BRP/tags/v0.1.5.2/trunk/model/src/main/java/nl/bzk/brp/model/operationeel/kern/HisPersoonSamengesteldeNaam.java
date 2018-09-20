/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.Predikaat;

/**
 * Database entiteit voor Kern.His_PersSamengesteldeNaam.
 */
@Entity
@Table(schema = "kern", name = "His_PersSamengesteldeNaam")
@Access(AccessType.FIELD)
public class HisPersoonSamengesteldeNaam extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HISPERSSAMENGESTELDENAAM", sequenceName = "Kern.seq_His_PersSamengesteldeNaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISPERSSAMENGESTELDENAAM")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pers")
    private PersistentPersoon persoon;
    @Column(name = "predikaat")
    private Predikaat predikaat;
    @Column(name = "voornamen")
    private String voornamen;
    @Column(name = "voorvoegsel")
    private String voorvoegsel;
    @Column(name = "scheidingsteken")
    private String scheidingsTeken;
    @Column(name = "adellijketitel")
    private AdellijkeTitel adellijkeTitel;
    @Column(name = "geslnaam")
    private String geslachtsNaam;
    @Column(name = "indnreeksalsgeslnaam")
    private Boolean indNreeksAlsGeslnaam;
    @Column(name = "indalgoritmischafgeleid")
    private Boolean indAlgoritmischAfgeleid;

    public long getId() {
        return id;
    }



    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Predikaat getPredikaat() {
        return predikaat;
    }

    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public String getScheidingsTeken() {
        return scheidingsTeken;
    }

    public void setScheidingsTeken(final String scheidingsTeken) {
        this.scheidingsTeken = scheidingsTeken;
    }

    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    public String getGeslachtsNaam() {
        return geslachtsNaam;
    }

    public void setGeslachtsNaam(final String geslachtsNaam) {
        this.geslachtsNaam = geslachtsNaam;
    }

    public Boolean isIndNreeksAlsGeslnaam() {
        return indNreeksAlsGeslnaam;
    }

    public void setIndNreeksAlsGeslnaam(final Boolean indNreeksAlsGeslnaam) {
        this.indNreeksAlsGeslnaam = indNreeksAlsGeslnaam;
    }

    public Boolean isIndAlgoritmischAfgeleid() {
        return indAlgoritmischAfgeleid;
    }

    public void setIndAlgoritmischAfgeleid(final Boolean indAlgoritmischAfgeleid) {
        this.indAlgoritmischAfgeleid = indAlgoritmischAfgeleid;
    }

    @Override
    public AbstractMaterieleEnFormeleHistorieEntiteit clone() throws CloneNotSupportedException {
        HisPersoonSamengesteldeNaam kopie = (HisPersoonSamengesteldeNaam) super.clone();
        kopie.id = null;
        kopie.setAdellijkeTitel(getAdellijkeTitel());
        kopie.setGeslachtsNaam(getGeslachtsNaam());
        kopie.setIndAlgoritmischAfgeleid(isIndAlgoritmischAfgeleid());
        kopie.setIndNreeksAlsGeslnaam(isIndNreeksAlsGeslnaam());
        kopie.setPersoon(getPersoon());
        kopie.setPredikaat(getPredikaat());
        kopie.setScheidingsTeken(getScheidingsTeken());
        kopie.setVoornamen(getVoornamen());
        kopie.setVoorvoegsel(getVoorvoegsel());
        return kopie;
    }
}
