package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.ISO31661Alpha2;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Onafhankelijke staat, zoals door Nederland erkend.De erkenning van de ene staat door de andere, is een eenzijdige rechtshandeling van die andere staat, die daarmee te kennen geeft dat hij de nieuw erkende staat als lid van het internationale statensysteem aanvaardt en alle gevolgen van die erkenning wil accepteren. De voorkomens van "Land" zijn hier beperkt tot die staten die door de Staat der Nederlanden erkend zijn, dan wel (in geval van historische gegevens) erkend zijn geweest.
 *
 */
@Entity
@Table(schema = "Kern", name = "Land")
@Access(AccessType.FIELD)
public class Land extends AbstractStatischObjectType {

    /** Techniche sleutel. */
    @Id
    @Column (name = "id")
    private Integer id;

    /** De landencode, zoals die binnen het GBA stelsel wordt gebruikt. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Landcode code;

    /** De officiële naam van het land. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private NaamEnumeratiewaarde naam;

    /** De tweeletterige landcode gebaseerd op de ISO 3166-1-norm. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "iso31661alpha2"))
    private ISO31661Alpha2 iso31661Alpha2;

    /**
     * @return Techniche sleutel.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return De landencode, zoals die binnen het GBA stelsel wordt gebruikt.
     */
    public Landcode getCode() {
        return code;
    }

    /**
     * @return De officiële naam van het land.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * @return De tweeletterige landcode gebaseerd op de ISO 3166-1-norm.
     */
    public ISO31661Alpha2 getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    /**
     * Constructor.
     *
     * @param code de code.
     * @param naam de naam.
     * @param iso31661Alpha2 de iso31661Alpha2.
     *
     */
     Land(final Code code, final Naam naam, final ISO31661Alpha2 iso31661Alpha2) {
        this.code =  code;
        this.naam =  naam;
        this.iso31661Alpha2 =  iso31661Alpha2;
    }

}
