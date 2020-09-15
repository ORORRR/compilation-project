package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap {
    // Should be a power of 2
    private int HEAP_SIZE = 256;

    private int entryCount = 0;

    private Object[] values;
    private HashMap<Integer, HeapEntry> symbolTable;
    private HashMap<Integer, List<Integer>> freeBlocks;

    public Heap() {
        values = new Object[HEAP_SIZE];
        symbolTable = new HashMap<>();
        freeBlocks = new HashMap<>();

        // Initialize values with null values
        for (int i = 0; i < HEAP_SIZE; ++i) {
            values[i] = null;
        }

        // Initialize the lists of free blocks
        for (int i = HEAP_SIZE; i > 0; i = i / 2) {
            freeBlocks.put(i, new ArrayList<>());
        }

        // Add one free block of size HEAP_SIZE at 0
        freeBlocks.get(HEAP_SIZE).add(0);
    }

    public Object[] getValues() {
        return values;
    }

    public HashMap<Integer, HeapEntry> getSymbolTable() {
        return symbolTable;
    }

    /**
     * Get the entry matching the given id.
     *
     * @param id The id of the entry.
     * @return The entry matching the id.
     * @throws HeapException No entry matching the id.
     */
    public HeapEntry getHeapEntry(int id) throws HeapException {
        HeapEntry entry = symbolTable.get(id);

        if (entry == null) {
            throw new HeapException("Tried to access a non-existing entry: no entry with id " + id + " found in the heap");
        }

        return entry;
    }

    /**
     * Grow the heap by doubling its size.
     */
    private void grow() {
        int size = HEAP_SIZE;

        // Double the heap size
        HEAP_SIZE *= 2;

        // Add a new free block list
        freeBlocks.put(HEAP_SIZE, new ArrayList<>());

        // Add a free block at the former HEAP_SIZE
        freeBlocks.get(size).add(size);

        // Grow values
        Object[] nextValues = new Object[HEAP_SIZE];

        for (int i = 0; i < HEAP_SIZE; ++i) {
            if (i < size) {
                nextValues[i] = values[i];
            } else {
                nextValues[i] = null;
            }
        }

        values = nextValues;
    }

    /**
     * Reassemble the heap:
     * - move all entries at the front of values;
     * - regenerate free blocks at the end of values so that free blocks are as big as possible.
     */
    private void reassemble() throws HeapException {
        // Move all entries at the front of values
        Integer from = null;

        for (int i = 0; i < HEAP_SIZE; ++i) {
            // See if there is an entry starting at i
            Integer id = findEntryAtAddress(i);

            if (id == null) {
                // No entry
                if (from == null) {
                    from = i;
                }

                // Go to the end of the free block starting from i
                Integer size = findBlockSizeAtAddress(i);

                if (size != null) {
                    i += size - 1;
                }
            } else {
                HeapEntry entry = getHeapEntry(id);

                // If there is a free block in front of the entry then move the entry
                if (from != null) {
                    moveEntry(entry, from);

                    from += entry.getSize();
                }

                // Go to the end of the block
                i += entry.getSize() - 1;
            }
        }

        // Delete all free blocks
        deleteAllFreeBlocks();

        // Regenerate free blocks at the end of values so that free blocks are as big as possible
        if (from != null) {
            generateFreeBlocks(from, HEAP_SIZE - from);
        }
    }

    /**
     * Delete all free blocks.
     */
    private void deleteAllFreeBlocks() {
        for (Map.Entry<Integer, List<Integer>> entry : freeBlocks.entrySet()) {
            entry.getValue().clear();
        }
    }

    /**
     * Generate free blocks for a part of the heap.
     *
     * @param address The address to start from.
     * @param size    The size of the block.
     */
    private void generateFreeBlocks(int address, int size) {
        int from = address;
        int remaining = size;
        int blockSize = findClosestPowerOfTwo(remaining);

        while (remaining != blockSize && remaining > 0) {
            blockSize /= 2;

            freeBlocks.get(blockSize).add(from);

            from += blockSize;
            remaining -= blockSize;
        }

        if (remaining > 0) {
            freeBlocks.get(blockSize).add(from);
        }
    }

    /**
     * Count the number of free cells in the heap.
     *
     * @return The number of free cells in the heap.
     */
    private int countFreeCells() {
        int n = 0;

        for (Map.Entry<Integer, List<Integer>> entry : freeBlocks.entrySet()) {
            for (Integer i : entry.getValue()) {
                n += entry.getKey();
            }
        }

        return n;
    }

    /**
     * Get the size of the bigger free block.
     *
     * @return The size of the bigger free block.
     */
    private int getFreeBlockMaxSize() {
        int size = 0;

        for (Map.Entry<Integer, List<Integer>> entry : freeBlocks.entrySet()) {
            if (!entry.getValue().isEmpty() && entry.getKey() > size) {
                size = entry.getKey();
            }
        }

        return size;
    }

    /**
     * Move an entry in the heap.
     *
     * @param entry The entry to move.
     * @param to    The address to move the entry to.
     */
    private void moveEntry(HeapEntry entry, int to) {
        int cellFrom = entry.getAddress();
        int cellTo = to;

        for (int i = 0; i < entry.getSize(); ++i) {
            values[cellTo] = values[cellFrom];
            values[cellFrom] = null;

            cellFrom++;
            cellTo++;
        }

        entry.setAddress(to);
    }

    /**
     * Disassemble a block in order to create a block of a wanted size.
     *
     * @param address The address of the block to disassemble.
     * @param size    The size of the block to disassemble (must be a power of two).
     * @param wanted  The size of the block we want to create (must be a power of two).
     */
    private void disassembleBlock(int address, int size, int wanted) {
        while (size > wanted) {
            // Remove the block from the free blocks
            freeBlocks.get(size).remove(Integer.valueOf(address));

            // Divide the block in two free blocks
            size /= 2;

            List<Integer> blocks = freeBlocks.get(size);

            blocks.add(address);
            blocks.add(address + size);
        }
    }

    /**
     * Return the id of the entry matching the address.
     *
     * @param address The address of the entry to fetch.
     * @return The id of the entry, null if not found.
     */
    private Integer findEntryAtAddress(int address) {
        for (Map.Entry<Integer, HeapEntry> entry : symbolTable.entrySet()) {
            if (entry.getValue().getAddress() == address) {
                return entry.getKey();
            }
        }

        // No entry starting at given address
        return null;
    }

    /**
     * Return the size of the free block matching the address.
     *
     * @param address The address of the free block.
     * @return The size of the free block, null if not found.
     */
    private Integer findBlockSizeAtAddress(int address) {
        for (Map.Entry<Integer, List<Integer>> entry : freeBlocks.entrySet()) {
            if (entry.getValue().contains(address)) {
                return entry.getKey();
            }
        }

        // No free block at given address
        return null;
    }

    /**
     * Find or create a free block to store a given number of elements with minimal waste.
     * Warning: only call this function when we know for sure that there is a free block big enough to store the given number of elements.
     *
     * @param size The number of elements we want to put in the block.
     * @return The address of the found free block.
     */
    private int findFreeBlock(int size) {
        int actualSize = findClosestPowerOfTwo(size);

        // Look for an existing free block of the given size
        List<Integer> blocks = freeBlocks.get(actualSize);

        if (!blocks.isEmpty()) {
            return blocks.remove(0);
        }

        // Look for an existing free block of a bigger size
        int biggerSize = actualSize * 2;

        blocks = freeBlocks.get(biggerSize);

        while (biggerSize <= HEAP_SIZE && blocks.isEmpty()) {
            biggerSize *= 2;
            blocks = freeBlocks.get(biggerSize);
        }

        // A free block of a bigger size has been found
        int address = blocks.get(0);

        // Now disassemble the block to ensure a minimal waste of space
        disassembleBlock(address, biggerSize, actualSize);

        return address;
    }

    /**
     * Get the closest (equal or superior) power of 2 of the given number.
     *
     * @param number The number for which we want the closest power of 2.
     * @return The closest (equal or superior) power of 2.
     */
    private int findClosestPowerOfTwo(int number) {
        int p = 1;

        while (number > p) {
            p *= 2;
        }

        return p;
    }

    /**
     * Create a new entry in the heap.
     *
     * @param size The size of the new entry.
     * @param type The type of the new entry.
     * @return The id of the new entry.
     * @throws HeapException Tried to declare an array with a negative or null size.
     */
    public int insert(int size, Type type) throws HeapException {
        if (size <= 0) {
            throw new HeapException("Cannot declare an array with a negative or null size");
        }

        int id = entryCount;
        int actualSize = findClosestPowerOfTwo(size);
        int freeCells = countFreeCells();

        if (size <= getFreeBlockMaxSize()) {
            // The size is lesser than the bigger free block size
            int adr = findFreeBlock(size);

            // Add the entry in the symbol table
            symbolTable.put(id, new HeapEntry(adr, size));

            // Remove the block from free blocks
            freeBlocks.get(actualSize).remove(Integer.valueOf(adr));

            // Generate free blocks with the remaining space
            generateFreeBlocks(adr + size, actualSize - size);
        } else if (size <= freeCells) {
            // The size is lesser than the number of free cells
            reassemble();

            // Check if there is now a free block big enough
            if (size <= getFreeBlockMaxSize()) {
                return insert(size, type);
            }

            // Remove all free blocks
            deleteAllFreeBlocks();

            // Create an entry
            symbolTable.put(id, new HeapEntry(HEAP_SIZE - size, size));

            // Generate free blocks with the remaining space
            generateFreeBlocks(HEAP_SIZE - freeCells, freeCells - size);
        } else {
            // The size is superior to the number of free cells
            while (size > countFreeCells()) {
                grow();
            }

            return insert(size, type);
        }

        // Add a reference to the new entry
        getHeapEntry(id).addRef();

        // Initialize the array
        initEntryValues(id, type);

        // Update entry count
        entryCount += 1;

        return id;
    }

    /**
     * Get the value of entry at the given index.
     *
     * @param id    The id of the entry.
     * @param index The index of the value to fetch.
     * @return The value at index in the entry.
     * @throws IndexOutOfBoundsException Tried to access value that is not in the bounds of the entry.
     * @throws HeapException             No entry matching the given id.
     */
    public Object getEntryValueAt(int id, int index) throws IndexOutOfBoundsException, HeapException {
        HeapEntry entry = getHeapEntry(id);

        if (index < 0 || index >= entry.getSize()) {
            throw new IndexOutOfBoundsException("Tried to access index " + index + " but the size of the array is " + entry.getSize());
        }

        return values[entry.getAddress() + index];
    }

    /**
     * Set the value of entry at the given index.
     *
     * @param id    The id of the entry.
     * @param index The index of the value to fetch.
     * @param value The value to set at the index.
     * @throws IndexOutOfBoundsException Tried to access value that is not in the bounds of the entry.
     * @throws HeapException             No entry matching the given id.
     */
    public void setEntryValueAt(int id, int index, Object value) throws HeapException, IndexOutOfBoundsException {
        HeapEntry entry = getHeapEntry(id);

        if (index < 0 || index >= entry.getSize()) {
            throw new IndexOutOfBoundsException("Tried to access index " + index + " but the size of the array is " + entry.getSize());
        }

        values[entry.getAddress() + index] = value;
    }

    /**
     * Initialize an entry considering its type.
     *
     * @param id   The id of the entry.
     * @param type The type of the entry.
     */
    private void initEntryValues(int id, Type type) throws HeapException {
        HeapEntry entry = getHeapEntry(id);
        Object value = type == Type.INTEGER ? 0 : false;

        for (int i = entry.getAddress(); i < entry.getAddress() + entry.getSize(); ++i) {
            values[i] = value;
        }
    }

    /**
     * Add a reference to an entry.
     *
     * @param id The id of the entry.
     * @throws HeapException No entry matching the given id.
     */
    public void addRef(int id) throws HeapException {
        HeapEntry entry = getHeapEntry(id);

        entry.addRef();
    }

    /**
     * Remove a reference of an entry (an entry with no reference is deleted).
     *
     * @param id The id of the entry.
     * @throws HeapException No entry matching the given id.
     */
    public void removeRef(int id) throws HeapException {
        HeapEntry entry = getHeapEntry(id);

        entry.removeRef();

        if (entry.getNbRef() == 0) {
            // Remove the entry from values
            for (int i = entry.getAddress(); i < entry.getAddress() + entry.getSize(); ++i) {
                values[i] = null;
            }

            // Generate the free blocks that will replace the entry
            generateFreeBlocks(entry.getAddress(), entry.getSize());

            // Remove the entry from the symbol table
            symbolTable.remove(id);
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Heap:\n");

        buffer.append("\tsymbol table :\n");

        for (Map.Entry<Integer, HeapEntry> entry : symbolTable.entrySet()) {
            buffer.append(entry.getKey()).append(" : ");
            buffer.append(entry.getValue()).append("\n");
        }

        buffer.append("\tfree blocks :\n");

        for (Map.Entry<Integer, List<Integer>> entry : freeBlocks.entrySet()) {
            buffer.append(entry.getKey()).append(" : ");
            for (Integer i : entry.getValue()) {
                buffer.append(i).append(" ,");
            }
            buffer.append("\n");
        }

        buffer.append("\tvalues :\n");

        for (int i = 0; i < HEAP_SIZE; i++) {
            if (values[i] == null) {
                buffer.append("-");
            } else {
                buffer.append(values[i]);
            }

            //add separator or line return
            if ((i + 1) % 10 == 0) {
                buffer.append("\n");
            } else {
                buffer.append(" | ");
            }
        }

        return buffer.toString();
    }
}
