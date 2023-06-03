package graph.util;

import graph.iface.IIterator;
import graph.iface.IList;
import graph.iface.IPosition;

public class ListIterator<T> implements IIterator<T> {
   private IPosition<T> node;
   private IList<T> list;

   public ListIterator( IList<T> list ) {
      this.list = list;
      node = list.first();
   }

   public boolean hasNext() {
      return node != null;
   }

   public T next() {
      T toReturn = node.element();
      node = list.next( node );
      return toReturn;
   }
}
