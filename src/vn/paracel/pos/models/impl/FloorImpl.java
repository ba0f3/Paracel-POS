/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import vn.paracel.pos.models.impl.ModelImpl;
import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.jcs.JCS;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.One2Many;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.Floor;
import vn.paracel.pos.models.ModelFactory;
import vn.paracel.pos.models.Table;

/**
 *
 * @author rgv151
 */

@DatabaseModel("pos.floor")
public class FloorImpl extends ModelImpl implements Floor {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="description")
    private String description;
        
    @DatabaseField(name="state")
    private String state;
    
    @DatabaseField(name="table_ids")
    @One2Many(model=TableImpl.class, field="floor")
    private List<Table> tables;
    
    @DatabaseField(name="icon")
    private String icon;
    
    @DatabaseField(name="background")
    private String background;

    public FloorImpl() {
        super();
    }
            
    public FloorImpl(Integer id) {
        super(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
    /**
     * Return the list of available floor
     * @return List<Floor>
     */
    public static List<Floor> getAllFloors() {
        OpenERP openerp = AppGlobal.getOpenERPClient();
        Object[] result = openerp.search("pos.floor");
        List<Floor> list = new ArrayList<Floor>();
        for(Object r : result) {
            Integer eid = (Integer)r;
            list.add(ModelFactory.getFloor(eid));
        }
        return list;
    }
}
