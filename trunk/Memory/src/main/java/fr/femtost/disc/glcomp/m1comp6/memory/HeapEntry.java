package fr.femtost.disc.glcomp.m1comp6.memory;

public class HeapEntry {
    private int address;
    private int size;
    private int nbRef;

    public HeapEntry(int address, int size) {
        this.address = address;
        this.size = size;

        nbRef = 0;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getSize() {
        return size;
    }

    public int getNbRef() {
        return nbRef;
    }

    public void addRef() {
        nbRef++;
    }

    public void removeRef() {
        nbRef--;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(address).append(" ,");
        buffer.append(size).append(" ,");
        buffer.append(nbRef);

        return buffer.toString();
    }
}
