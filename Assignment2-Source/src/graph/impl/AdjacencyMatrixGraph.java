package graph.impl;

import graph.iface.IEdge;
import graph.iface.IGraph;
import graph.iface.IIterator;
import graph.iface.IVertex;

/**
 * @Author liwenyan
 * @Date 2023/6/3 21:20
 * @PackageName:graph.impl
 * @ClassName: AdjacencyMatrixGraph
 * @Description: TODO
 * @Version 1.0
 */
public class AdjacencyMatrixGraph<V,E> implements IGraph<V,E> {
    @Override
    public IVertex<V>[] endVertices(IEdge<E> e) {
        return new IVertex[0];
    }

    @Override
    public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
        return null;
    }

    @Override
    public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
        return false;
    }

    @Override
    public V replace(IVertex<V> v, V o) {
        return null;
    }

    @Override
    public E replace(IEdge<E> e, E o) {
        return null;
    }

    @Override
    public IVertex<V> insertVertex(V o) {
        return null;
    }

    @Override
    public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
        return null;
    }

    @Override
    public V removeVertex(IVertex<V> v) {
        return null;
    }

    @Override
    public E removeEdge(IEdge<E> e) {
        return null;
    }

    @Override
    public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
        return null;
    }

    @Override
    public IIterator<IVertex<V>> vertices() {
        return null;
    }

    @Override
    public IIterator<IEdge<E>> edges() {
        return null;
    }
}
