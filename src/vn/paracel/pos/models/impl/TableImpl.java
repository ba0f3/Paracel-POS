/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import vn.paracel.pos.models.impl.ModelImpl;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.models.Floor;
import vn.paracel.pos.models.Table;

/**
 *
 * @author rgv151
 */

@DatabaseModel("pos.table")
public class TableImpl extends ModelImpl implements Table {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="floor_id")
    @Many2One(FloorImpl.class)
    private Floor floor;
    
    @DatabaseField(name="state")
    private String state;
    
    @DatabaseField(name="icon", readonly=true)
    private String icon;
    
    @DatabaseField(name="x")
    private Integer x;
    
    @DatabaseField(name="y")
    private Integer y;

    public TableImpl() {
    }
        
    public TableImpl(Integer id) {
        super(id);
    }
    
    public Floor getFloor() {
        return floor;
    }
    
    public void setFloor(Floor floor) {
        this.floor = floor;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
