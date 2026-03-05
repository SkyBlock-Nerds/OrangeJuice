package net.hypixel.orangejuice.util.model;

public record Pair<V1, V2>(V1 value1, V2 value2) {

    public V1 getLeft() {
        return value1;
    }

    public V2 getRight() {
        return value2;
    }
}