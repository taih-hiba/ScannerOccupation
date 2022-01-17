package com.map.scanner.model;

public class Creneau {


    private String id;
    private String label;
    private String debut;
    private String fin;

    public Creneau(String id, String label, String debut, String fin) {
        this.id = id;
        this.label = label;
        this.debut = debut;
        this.fin = fin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Creneau{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", debut='" + debut + '\'' +
                ", fin='" + fin + '\'' +
                '}';
    }
}
