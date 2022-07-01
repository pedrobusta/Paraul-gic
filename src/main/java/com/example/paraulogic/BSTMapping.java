package com.example.paraulogic;

import java.util.Iterator;
import java.util.Stack;

public class BSTMapping <K extends Comparable,V>{
    private class Pair {

        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Pair() {

        }

        @Override
        public String toString(){
            return key+" ("+value+"), ";
        }
    }

    private class Node {

        Pair pair;
        private Node left, right;

        public Node(Pair pair, Node left, Node right) {
            this.pair = pair;
            this.left = left;
            this.right = right;
        }
    }

    private class IteratorBSTSet implements Iterator {

        private Stack<Node> iterator;

        public IteratorBSTSet() {
            Node p;
            iterator = new Stack();
            if(root!=null){
                p = root;
                while(p.left!=null){
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
        }

        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }

        @Override
        public Object next() {
            Node p= iterator.pop();
            Pair pair = p.pair;
            if(p.right!=null){
                p = p.right;
                while(p.left!=null){
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return pair;
        }

    }

    // ATRIBUTOS
    private Node root;

    // CONSTRUCTOR
    public BSTMapping() {
        root = null;
    }

    // INTERFACE MÉTODOS
    public V get(K key) {
        Node p = get(key, root);
        if (p == null) {
            return null;
        }
        return p.pair.value;
    }

    private Node get(K key, Node current) {
        if (current == null) {
            return null;
        } else {
            if (current.pair.key.equals(key)) {
                return current;
            } else if (current.pair.key.compareTo(key) < 0) {
                return get(key, current.right);
            } else {
                return get(key, current.left);
            }
        }
    }

    public V put(K key, V value) {
        Pair aux = new Pair();
        this.root = put(aux, root, new Pair(key, value));
        return aux.value;
    }

    private Node put(Pair aux, Node current, Pair pair) {

        if (current == null) {

            aux.value = null;
            return new Node(pair, null, null);

        } else {

            if (pair.key.equals(current.pair.key)) {

                aux.value = current.pair.value;
                current.pair.value = pair.value;
                return current;

            } else if (pair.key.compareTo(current.pair.key) > 0) {

                current.right = put(aux, current.right, pair);

            } else {

                current.left = put(aux, current.left, pair);

            }
        }
        return current;
    }

    public V remove(K key) {
        Pair aux = new Pair();
        this.root = remove(key, root, aux);
        return aux.value;
    }

    private Node remove(K key, Node current, Pair aux) {
        if (current == null) {                                                  //L'ARBRE ES BUIT
            aux.value = null;
            return null;
        } else {

            if (current.pair.key.equals(key)) {

                aux.value = current.pair.value;

                if (current.left == null && current.right == null) {              //ES UN NODE FULLA

                    return null;

                } else if (current.left != null && current.right == null) {        //NO TE FILL DRET

                    return current.left;

                } else if (current.left == null && current.right != null) {        //NO TE FILL ESQUERRA

                    return current.right;

                } else {                                                          //TE DOS FILLS

                    Node plowest = current.right;
                    Node parent = current;

                    while (plowest.left != null) {
                        parent = plowest;
                        plowest = plowest.left;
                    }

                    plowest.left = current.left;

                    if (plowest != current.right) {

                        parent.left = plowest.right;
                        plowest.right = current.right;

                    }

                    return plowest;
                }
            } else if (current.pair.key.compareTo(key) > 0) {                   //UNA PROFUNDITAT MES CAP A LA ESQUERRA

                current.left = remove(key, current.left, aux);

            } else {
                current.right = remove(key, current.right, aux);                //UNA PROFUNDITAT MES CAP A LA DRETA
            }
        }
        return current;                                                         //DESFEIM EL CAMI
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Iterator getIterator(){
        return new IteratorBSTSet();
    }

}
