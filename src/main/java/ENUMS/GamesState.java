package ENUMS;

public enum GamesState
{
    ACTIVE("Active", 1),
    FINISHED("Finished", 2),
    NEXT("Next", 3);

    private String name;
    private int orden;

    private GamesState(String name, int orden)
    {
        this.setName(name);
        this.setOrden(orden);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
