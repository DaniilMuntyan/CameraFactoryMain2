package entities.machines;

import kpi.trspo.restapp.PackerGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

//@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class Packer extends Machine {
    private UUID id;
    private String name;
    public Packer(String name) {
        this.name = name;
        //super(name);
    }

    public Packer(PackerGrpc packerGrpc) {
        this.id = UUID.fromString(packerGrpc.getPackerId().getValue());
        this.name = packerGrpc.getName();
        /*super.setId(UUID.fromString(packerGrpc.getPackerId().getValue()));
        super.setName(packerGrpc.getName());*/
    }

    @Override
    public String toString() {
        return "Packing machine id: " + getId() + ", name: " + getName();
    }
}
