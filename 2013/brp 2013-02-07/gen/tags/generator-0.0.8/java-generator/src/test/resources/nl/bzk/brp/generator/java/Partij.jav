package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Sector;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPartij;

/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 *
 */
@Entity
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
public class Partij extends AbstractStatischObjectType {

    /** Technische sleutel. */
    @Id
    @Column (name = "id")
    private Integer id;

    /** Naam van de Partij. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private NaamEnumeratiewaarde naam;

    /** Nadere typering van de Partij. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "srt"))
    private SoortPartij soort;

    /** De viercijferige code van oudsher gebruikt in de GBA voor de identificatie van een gemeente. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Gemeentecode code;

    /** De datum waarop de Partij niet meer geacht wordt te bestaan. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum datumEinde;

    /** De datum vanaf wanneer de Partij geacht wordt te bestaan. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum datumAanvang;

    /** Door de beheerder bepaalde typering van de Partij. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "sector"))
    private Sector sector;

    /** De gemeente die de voortzetting is van deze gemeente. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voortzettendegem"))
    private Partij voortzettendeGemeente;

    /** De Partij, waar de onderhavige Gemeente onderdeel van is. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "onderdeelvan"))
    private Partij onderdeelVan;

    /**
     * @return Technische sleutel.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Naam van de Partij.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * @return Nadere typering van de Partij.
     */
    public SoortPartij getSoort() {
        return soort;
    }

    /**
     * @return De viercijferige code van oudsher gebruikt in de GBA voor de identificatie van een gemeente.
     */
    public Gemeentecode getCode() {
        return code;
    }

    /**
     * @return De datum waarop de Partij niet meer geacht wordt te bestaan.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * @return De datum vanaf wanneer de Partij geacht wordt te bestaan.
     */
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * @return Door de beheerder bepaalde typering van de Partij.
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * @return De gemeente die de voortzetting is van deze gemeente.
     */
    public Partij getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * @return De Partij, waar de onderhavige Gemeente onderdeel van is.
     */
    public Partij getOnderdeelVan() {
        return onderdeelVan;
    }

}
