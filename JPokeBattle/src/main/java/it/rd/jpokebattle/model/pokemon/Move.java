package it.rd.jpokebattle.model.pokemon;

public class Move {
    private String name;
    private Type type;
    private String description;
    private int power;
    private double precision;
    private int ppDefault;          // numero di utilizzi massimo di una mossa
    private boolean isSpecial;  // la mossa è speciale o fisica


    /**
     * Metodo Costruttore.
     *
     * @param name
     * @param type          il tipo della mossa (es: FIRE)
     * @param description   descrizione della mossa
     * @param power
     * @param precision     precisione della mossa (compreso tra 0.0 e 1.0)
     * @param ppDefault
     * @param isSpecial     la mossa è Speciale o Fisica
     */
    public Move (String name, Type type, String description, int power, double precision, int ppDefault, boolean isSpecial) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.power = power;
        setPrecision(precision);
        this.ppDefault = ppDefault;
        this.isSpecial = isSpecial;
    }


    public String getName() {return name;}

    public Type getType() {return type;}

    public String getDescription() {return description;}

    public int getDamage() {return power;}

    public double getPrecision() {return precision;}

    /**
     *
     * @return numero di utilizzi massimi di una mossa
     */
    public int getPPDefault() {return ppDefault;}


    /**
     *
     * @return valore booleano true se la mossa è speciale, altrimenti è fisica
     */
    public boolean isSpecial() {return isSpecial;}



    public void setName(String name) {this.name = name;}

    public void setType(Type type) {this.type = type;}

    public void setDescription(String description) {this.description = description;}

    public void setPower(int power) {this.power = power;}

    public void setPrecision(double precision) {
        if (precision > 1.0) 
            precision =  1.0;
        this.precision = precision;
    }

    public void setPpMAX(int ppDefault) {this.ppDefault = ppDefault;}

    public void setIsSpecial(boolean special) {isSpecial = special;}

}
