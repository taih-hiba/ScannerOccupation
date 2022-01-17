package com.map.scanner.model;

public class Occupation {
    String id;
    Salle salle;
    Creneau creneau;

    public Occupation(String id, Salle salle, Creneau creneau) {
        this.id = id;
        this.salle = salle;
        this.creneau = creneau;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "id='" + id + '\'' +
                ", salle=" + salle +
                ", creneau=" + creneau +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
}
