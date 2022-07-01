package com.example.paraulogic;

import java.util.Iterator;

public class UnsortedArraySet <E>{

    private E array[];
    private int n;


    public UnsortedArraySet(int max) {
        array = (E[]) new Object[max];
        n = 0;
    }

    public boolean contains(E elem) {
        for (int i = 0; i < n; i++) {
            // equals mira si un objeto es igual al otro
            if (array[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    public boolean add(E elem) {
        boolean exists = contains(elem);
        if (!exists && n < array.length) {
            array[n++] = elem;
            return true;
        }
        return false;
    }

    public boolean remove(E elem) {
        for (int i = 0; i < array.length; i++) {
            // equals mira si un objeto es igual al otro
            if (array[i].equals(elem)) {
                array[i] = array[n - 1];
                n--;
                array[n] = null;
                return true;
            }
        }
        return false;
    }

    public Iterator getIterator(){
        return new IteratorUnsortedArraySet();
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Iterator iterador(){
        return new IteratorUnsortedArraySet();
    }

    private class IteratorUnsortedArraySet implements Iterator {

        private int idxIterator;

        public IteratorUnsortedArraySet(){
            idxIterator = 0;
        }

        @Override
        public boolean hasNext() {
             return idxIterator<n;
        }

        @Override
        public Object next() {
            return array[idxIterator++];
        }
    }
}

