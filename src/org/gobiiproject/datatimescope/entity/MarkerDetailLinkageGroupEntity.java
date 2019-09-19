package org.gobiiproject.datatimescope.entity;

public class MarkerDetailLinkageGroupEntity {

    private Integer lg_id;
    private String lg_name;
    private Integer map_id;
    private String map_name;


    public MarkerDetailLinkageGroupEntity(Integer lg_id, String lg_name, Integer map_id,String map_name) {
        this.lg_id = lg_id;
        this.lg_name = lg_name;
        this.map_id = map_id;
        this.map_name = map_name;;
    }


    public Integer getLg_id() {
        return lg_id;
    }


    public void setLg_id(Integer lg_id) {
        this.lg_id = lg_id;
    }


    public String getLg_name() {
        return lg_name;
    }


    public void setLg_name(String lg_name) {
        this.lg_name = lg_name;
    }


    public Integer getMap_id() {
        return map_id;
    }


    public void setMap_id(Integer map_id) {
        this.map_id = map_id;
    }


    public String getMap_name() {
        return map_name;
    }


    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }
}
