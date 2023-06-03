package graph.impl;

import graph.iface.*;
import graph.util.DLinkedList;

/**
 * @Author liwenyan
 * @Date 2023/6/2 15:39
 * @PackageName:graph.impl
 * @ClassName: AdjacencyListGraph
 * @Description: TODO
 * @Version 1.0
 */
public class AdjacencyListGraph<V,E> implements IGraph<V,E> {
    /**
     * Inner class to represent a vertex in an edge list graph implementation
     */
    private class AdjacencyListVertex implements IVertex<V> {
        // reference to a node in the vertex list
        IPosition<IVertex<V>> node;

        // element stored in this vertex
        V element;
        //一个顶点相邻的边的列表
        //public DLinkedList<AdjacencyListEdge> adjacencyEdge;
        public IList<IEdge<E>> adjacencyEdge;

        public AdjacencyListVertex(V element) {
            this.element = element;
        }
        @Override
        public V element() {
            return element;
        }

        // It's useful to have a toString() method that can
        // return details about this object, so you can
        // print the object later and get useful information.
        // This one prints the element
        public String toString() {
            return element.toString();
        }
    }

    /**
     * Inner class to represent an edge in an edge list graph implementation.
     *
     */
    private class AdjacencyListEdge implements IEdge<E> {
        // reference to a node in the edge list
        IPosition<IEdge<E>> node;

        // reference to a node in the start point adjacency edge list
        IPosition<IEdge<E>> startNode;

        // reference to a node in the end point adjacency edge list
        IPosition<IEdge<E>> endNode;

        // element stored in this edge
        E element;

        // the start and end vertices that this edge connects
        public AdjacencyListGraph.AdjacencyListVertex start, end;



        // constructor to set the three fields
        public AdjacencyListEdge(AdjacencyListGraph.AdjacencyListVertex start, AdjacencyListGraph.AdjacencyListVertex end, E element) {
            this.start = start;
            this.end = end;
            this.element = element;
        }

        @Override
        public E element() {
            return element;
        }

        public String toString() {
            return element.toString();
        }
    }

    // vertex list
    private IList<IVertex<V>> vertices;

    // edge list
    private IList<IEdge<E>> edges;

    /**
     * Constructor
     */
    public AdjacencyListGraph() {
        // create new (empty) lists of edges and vertices
        vertices = new DLinkedList<IVertex<V>>();
        edges = new DLinkedList<IEdge<E>>();
    }
    @Override
    public IVertex[] endVertices(IEdge e) {
        // need to cast Edge type to EdgeListEdge
        AdjacencyListEdge edge = (AdjacencyListEdge) e;

        // create new array of length 2 that will contain
        // the edge's end vertices
        @SuppressWarnings("unchecked")
        IVertex<V>[] endpoints = new IVertex[2];

        // fill array
        endpoints[0] = edge.start;
        endpoints[1] = edge.end;

        return endpoints;
    }

    @Override
    public IVertex<V> opposite(IVertex v, IEdge e) {
        // find end points of Edge e
        IVertex<V>[] endpoints = endVertices(e);

        // return the end point that is not v
        if (endpoints[0].equals(v)) {
            return endpoints[1];
        } else if (endpoints[1].equals(v)) {
            return endpoints[0];
        }

        // Problem! e is not connected to v.
        throw new RuntimeException("Error: cannot find opposite vertex.");
    }

    @Override
    public boolean areAdjacent(IVertex v, IVertex w) {
        AdjacencyListVertex vertexV = (AdjacencyListVertex) v;
        AdjacencyListVertex vertexW = (AdjacencyListVertex) w;
        if(vertexV.adjacencyEdge.size()<=vertexW.adjacencyEdge.size()){
            IIterator<IEdge<E>> it = vertexV.adjacencyEdge.iterator();
            while (it.hasNext()) {
                // must cast Object type to EdgeListEdge type
                AdjacencyListGraph.AdjacencyListEdge edge = (AdjacencyListGraph.AdjacencyListEdge) it.next();

                // edge connects v -> w (so they are adjacent)
                if ((edge.start.equals(v)) && (edge.end.equals(w)))
                    return true;

                // edge connects w -> v (so they are adjacent)
                if ((edge.end.equals(v)) && (edge.start.equals(w)))
                    return true;
            }
            // no edge was found that connects v to w.
            return false;
        } else if (vertexV.adjacencyEdge.size()>vertexW.adjacencyEdge.size()) {
            IIterator<IEdge<E>> it = vertexW.adjacencyEdge.iterator();
            while (it.hasNext()) {
                // must cast Object type to EdgeListEdge type
                AdjacencyListGraph.AdjacencyListEdge edge = (AdjacencyListGraph.AdjacencyListEdge) it.next();

                // edge connects v -> w (so they are adjacent)
                if ((edge.start.equals(v)) && (edge.end.equals(w)))
                    return true;

                // edge connects w -> v (so they are adjacent)
                if ((edge.end.equals(v)) && (edge.start.equals(w)))
                    return true;
            }
        }

        return false;
    }

    @Override
    public V replace(IVertex<V> v, V x) {
        AdjacencyListVertex vertex = (AdjacencyListVertex) v;
        // store old element that we should return
        V temp = vertex.element;

        // do the replacement
        vertex.element = x;

        // return the old value
        return temp;
    }

    @Override
    public E replace(IEdge<E> e, E x) {
        AdjacencyListEdge edge = (AdjacencyListEdge) e;
        E temp = edge.element;
        edge.element = x;
        return temp;
    }

    @Override
    public IVertex<V> insertVertex(V v) {
        // create new vertex
        AdjacencyListVertex vertex = new AdjacencyListVertex(v);

        // insert the vertex into the vertex list
        // (returns a reference to the new Node that was created)
        IPosition<IVertex<V>> node = vertices.insertLast(vertex);

        // this reference must be stored in the vertex,
        // to make it easier to remove the vertex later.
        vertex.node = node;

        // return the new vertex that was created
        return vertex;
    }

    @Override
    public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
        // create new edge object
        AdjacencyListEdge edge = new AdjacencyListEdge((AdjacencyListVertex) v, (AdjacencyListVertex) w, o);

        // insert into the edge list and store the reference to the node
        // in the edge object
        IPosition<IEdge<E>> n = edges.insertLast(edge);
        edge.node = n;

        IPosition<IEdge<E>> nStart = edge.start.adjacencyEdge.insertLast(edge);
        edge.startNode = nStart;

        IPosition<IEdge<E>> nEnd = edge.end.adjacencyEdge.insertLast(edge);
        edge.endNode = nEnd;

        return edge;
    }

    @Override
    public V removeVertex(IVertex<V> v) {
        IList<IEdge<E>> incidentEdges = new DLinkedList<IEdge<E>>();
        IIterator<IEdge<E>> it = incidentEdges(v);
        while( it.hasNext() )
            incidentEdges.insertLast(it.next());

        while (!incidentEdges.isEmpty())
            removeEdge(incidentEdges.remove(incidentEdges.first()));

        AdjacencyListVertex vertex = (AdjacencyListVertex) v;
        vertices.remove(vertex.node);
        return vertex.element;
    }


    @Override
    public Object removeEdge(IEdge e) {
        AdjacencyListEdge edge = (AdjacencyListEdge) e;
        edges.remove(edge.node);
        edge.start.adjacencyEdge.remove(edge.node);
        edge.end.adjacencyEdge.remove(edge.node);
        return edge.element;
    }

    @Override
    public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
        AdjacencyListVertex vertex = (AdjacencyListVertex) v;
        return vertex.adjacencyEdge.iterator();
    }

    @Override
    public IIterator<IVertex<V>> vertices() {
        return vertices.iterator();
    }

    @Override
    public IIterator<IEdge<E>> edges() {
        return edges.iterator();
    }
}
