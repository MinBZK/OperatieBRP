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
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden..
 */
@Entity (name = "PartijMdl")
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
public class Partij extends AbstractStatischObjectType {

    @Id
    @Column (name = "id")
    private Long         partijId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private NaamEnumeratieWaarde         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum        datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "gemcode"))
    private GemeenteCode gemeenteCode;

    public Long getPartijId() {
        return partijId;
    }

    public Naam getNaam() {
        return naam;
    }

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public Datum getDatumEinde() {
        return datumEinde;
    }

    public Gemeentecode getGemeenteCode() {
        return gemeenteCode;
    }
}
