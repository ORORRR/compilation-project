package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeapTest {
    private Heap heap;

    @Before
    public void initialize() {
        heap = new Heap();
    }

    @Test
    public void getHeapEntryTest() throws HeapException {
        heap.insert(10, Type.INTEGER);
        //System.out.println(heap);
        Assert.assertEquals(0, heap.getHeapEntry(0).getAddress());
        Assert.assertEquals(10, heap.getHeapEntry(0).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        heap.insert(15, Type.BOOLEAN);
        //System.out.println(heap);
        Assert.assertEquals(16, heap.getHeapEntry(1).getAddress());
        Assert.assertEquals(15, heap.getHeapEntry(1).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(1).getNbRef());
    }

    @Test(expected = HeapException.class)
    public void getHeapEntryTest2() throws HeapException {
        HeapEntry entry = heap.getHeapEntry(0);
    }

    @Test
    public void addRefTest() throws HeapException {
        heap.insert(10, Type.INTEGER);
        heap.addRef(0);
        Assert.assertEquals(2, heap.getHeapEntry(0).getNbRef());

        heap.insert(32, Type.INTEGER);
        heap.addRef(1);
        heap.addRef(1);
        Assert.assertEquals(3, heap.getHeapEntry(1).getNbRef());

    }

    @Test
    public void removeRefTest() throws HeapException {
        heap.insert(10, Type.INTEGER);
        heap.addRef(0);
        Assert.assertEquals(2, heap.getHeapEntry(0).getNbRef());
        heap.removeRef(0);
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        heap.insert(32, Type.INTEGER);
        heap.addRef(1);
        heap.addRef(1);
        Assert.assertEquals(3, heap.getHeapEntry(1).getNbRef());
        heap.removeRef(1);
        Assert.assertEquals(2, heap.getHeapEntry(1).getNbRef());
    }

    @Test(expected = HeapException.class)
    public void removeRefTest2() throws HeapException {
        heap.insert(10, Type.INTEGER);
        heap.removeRef(0);

        HeapEntry entry = heap.getHeapEntry(0);
    }

    //insert with negative size for array
    @Test(expected = HeapException.class)
    public void insertTest1() throws HeapException {
        heap.insert(-4, Type.BOOLEAN);
    }

    //insert with null size for array
    @Test(expected = HeapException.class)
    public void insertTest2() throws HeapException {
        heap.insert(0, Type.BOOLEAN);
    }

    //add various size entries
    @Test
    public void insertTest3() throws HeapException {
        //add  a size 63 array
        heap.insert(63, Type.BOOLEAN);
        Assert.assertEquals(0, heap.getHeapEntry(0).getAddress());
        Assert.assertEquals(63, heap.getHeapEntry(0).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        //add a size 64 array
        heap.insert(64, Type.BOOLEAN);
        Assert.assertEquals(64, heap.getHeapEntry(1).getAddress());
        Assert.assertEquals(64, heap.getHeapEntry(1).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(1).getNbRef());

        //add a size 1 array
        heap.insert(1, Type.INTEGER);
        Assert.assertEquals(63, heap.getHeapEntry(2).getAddress());
        Assert.assertEquals(1, heap.getHeapEntry(2).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(2).getNbRef());
    }

    //reassemble and move an entry
    @Test
    public void insertTest4() throws HeapException {
        //add  a size 64 array
        heap.insert(64, Type.BOOLEAN);
        Assert.assertEquals(0, heap.getHeapEntry(0).getAddress());
        Assert.assertEquals(64, heap.getHeapEntry(0).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        //add a size 64 array
        heap.insert(64, Type.BOOLEAN);
        Assert.assertEquals(64, heap.getHeapEntry(1).getAddress());
        Assert.assertEquals(64, heap.getHeapEntry(1).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(1).getNbRef());

        //add a size 64 array
        heap.insert(64, Type.INTEGER);
        Assert.assertEquals(128, heap.getHeapEntry(2).getAddress());
        Assert.assertEquals(64, heap.getHeapEntry(2).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(2).getNbRef());

        //remove the second array
        heap.removeRef(1);

        //add a size 128 array
        heap.insert(128, Type.INTEGER);
        Assert.assertEquals(64, heap.getHeapEntry(2).getAddress());
        Assert.assertEquals(64, heap.getHeapEntry(2).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(2).getNbRef());

        Assert.assertEquals(128, heap.getHeapEntry(3).getAddress());
        Assert.assertEquals(128, heap.getHeapEntry(3).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(3).getNbRef());
    }

    //add an array bigger than the bigger free block size but inferior(or equals) to the free cells
    @Test
    public void insertTest5() throws HeapException {
        //add 129 size array
        heap.insert(129, Type.BOOLEAN);
        Assert.assertEquals(0, heap.getHeapEntry(0).getAddress());
        Assert.assertEquals(129, heap.getHeapEntry(0).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        //add a 127 size array
        heap.insert(127, Type.INTEGER);
        Assert.assertEquals(129, heap.getHeapEntry(1).getAddress());
        Assert.assertEquals(127, heap.getHeapEntry(1).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(1).getNbRef());
    }

    @Test
    public void insertTest6() throws HeapException {
        //first case : size inferior(or equals) to the bigger freeBlock size)
        heap.insert(63, Type.BOOLEAN);
        Assert.assertEquals(0, heap.getHeapEntry(0).getAddress());
        Assert.assertEquals(63, heap.getHeapEntry(0).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(0).getNbRef());

        //second case : size required bigger than the size of current max bloc, the bloc will be reassembled
        heap.insert(140, Type.INTEGER);
        Assert.assertEquals(256 - 140, heap.getHeapEntry(1).getAddress());
        Assert.assertEquals(140, heap.getHeapEntry(1).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(1).getNbRef());

        //third case : size required is bigger than the size of free bloc, the heap will be grown as twice
        heap.insert(100, Type.BOOLEAN);
        Assert.assertEquals(256, heap.getHeapEntry(2).getAddress());
        Assert.assertEquals(100, heap.getHeapEntry(2).getSize());
        Assert.assertEquals(1, heap.getHeapEntry(2).getNbRef());

    }

    @Test(expected = HeapException.class)
    public void getEntryValueAtTest1() throws IndexOutOfBoundsException, HeapException {
        heap.getEntryValueAt(0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEntryValueAtTest2() throws IndexOutOfBoundsException, HeapException {
        heap.insert(31, Type.INTEGER);
        heap.getEntryValueAt(0, 32);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEntryValueAtTest3() throws IndexOutOfBoundsException, HeapException {
        heap.insert(31, Type.INTEGER);
        heap.getEntryValueAt(0, -1);
    }

    @Test
    public void getEntryValueAtTest4() throws IndexOutOfBoundsException, HeapException {
        heap.insert(31, Type.INTEGER);

        int adr = heap.getHeapEntry(0).getAddress();
        int size = heap.getHeapEntry(0).getSize();

        for (int i = adr; i < size; i++) {
            Integer n = (Integer) heap.getEntryValueAt(0, i);
            Assert.assertEquals((Integer) 0, n);
        }
    }

    @Test(expected = HeapException.class)
    public void setEntryValueAtTest1() throws IndexOutOfBoundsException, HeapException {
        heap.setEntryValueAt(0, 0, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setEntryValueAtTest2() throws IndexOutOfBoundsException, HeapException {
        heap.insert(31, Type.INTEGER);

        heap.setEntryValueAt(0, 32, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setEntryValueAtTest3() throws IndexOutOfBoundsException, HeapException {
        heap.insert(31, Type.INTEGER);

        heap.setEntryValueAt(0, -1, 1);
    }

    @Test
    public void setEntryValueAtTest4() throws IndexOutOfBoundsException, HeapException {
        heap.insert(10, Type.INTEGER);

        heap.setEntryValueAt(0, 8, 3);

        Assert.assertEquals(3, heap.getEntryValueAt(0, heap.getHeapEntry(0).getAddress() + 8));
    }
}
