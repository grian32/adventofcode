package me.grian.util

class WeightedGraph<T>() {
    private val backing: MutableMap<T, MutableList<Pair<T, Int>>> = mutableMapOf()

    fun add(from: T, to: T, weight: Int) {
        if (backing[from] == null) {
            backing[from] = mutableListOf(to to weight)
        } else {
            backing[from]!!.add(to to weight)
        }
    }

    /**
     *  @return Returns the weight or null if a weight wasn't found between the two nodes.
     */
    fun getWeight(from: T, to: T): Int? {
        return backing[from]?.find { it.first == to }?.second
    }

    /**
     * @return Returns all unique nodes.
     */
    fun getAllNodes(): Set<T> {
        return (backing.keys.toList() + backing.values.map { it.map { it.first } }.flatten()).toSet()
    }
}