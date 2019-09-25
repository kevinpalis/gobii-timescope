package org.gobiiproject.datatimescope.entity;

public class MarkerDetailDatasetEntity {

    private Integer dataset_id;
    private String dataset_name;
    private Integer calling_analysis;
    private Integer[] analyses;
    private Integer experiment_id;
    private String experiment_name;
    private Integer project_id;
    private String project_name;
    private Integer vp_id;
    private String vp_name;
    
    
    public MarkerDetailDatasetEntity(Integer dataset_id, String dataset_name, Integer calling_analysis, Integer[] analyses, Integer experiment_id,String experiment_name, Integer project_id, String project_name,Integer vp_id, String vp_name) {
        this.dataset_id = dataset_id;
        this.dataset_name = dataset_name;
        this.calling_analysis = calling_analysis;
        this.analyses = analyses;
        this.experiment_id = experiment_id;
        this.experiment_name = experiment_name;;
        this.project_id = project_id;
        this.project_name = project_name;
        this.vp_id = vp_id;
        this.vp_name = vp_name;
    }
    
    public Integer getDataset_id() {
        return dataset_id;
    }
    public void setDataset_id(Integer dataset_id) {
        this.dataset_id = dataset_id;
    }
    public String getDataset_name() {
        return dataset_name;
    }
    public void setDataset_name(String dataset_name) {
        this.dataset_name = dataset_name;
    }
    public Integer getCalling_analysis() {
        return calling_analysis;
    }
    public void setCalling_analysis(Integer calling_analysis) {
        this.calling_analysis = calling_analysis;
    }
    public Integer[] getAnalyses() {
        return analyses;
    }
    public void setAnalyses(Integer[] analyses) {
        this.analyses = analyses;
    }
    public Integer getExperiment_id() {
        return experiment_id;
    }
    public void setExperiment_id(Integer experiment_id) {
        this.experiment_id = experiment_id;
    }
    public String getExperiment_name() {
        return experiment_name;
    }
    public void setExperiment_name(String experiment_name) {
        this.experiment_name = experiment_name;
    }
    public Integer getProject_id() {
        return project_id;
    }
    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }
    public String getProject_name() {
        return project_name;
    }
    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
    public Integer getVp_id() {
        return vp_id;
    }
    public void setVp_id(Integer vp_id) {
        this.vp_id = vp_id;
    }
    public String getVp_name() {
        return vp_name;
    }
    public void setVp_name(String vp_name) {
        this.vp_name = vp_name;
    }
    
}
