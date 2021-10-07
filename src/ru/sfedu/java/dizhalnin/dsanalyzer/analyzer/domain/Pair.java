package ru.sfedu.java.dizhalnin.dsanalyzer.analyzer.domain;

public class Pair<K, V> {
    private K t1;
    private V t2;

    public Pair(K t1, V t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Pair() {
    }

    public K getValue1() {
        return t1;
    }

    public void setValue1(K t1) {
        this.t1 = t1;
    }

    public V getValue2() {
        return t2;
    }

    public void setValue2(V t2) {
        this.t2 = t2;
    }
}
