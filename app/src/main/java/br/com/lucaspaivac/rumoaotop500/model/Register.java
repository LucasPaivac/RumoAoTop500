package br.com.lucaspaivac.rumoaotop500.model;

public class Register {

    private String role;
    private String resultado;
    private int sr;
    private int calcPoints;
    private String createdDate;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public int getCalcPoints() {
        return calcPoints;
    }

    public void setCalcPoints(int calcPoints) {
        this.calcPoints = calcPoints;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
