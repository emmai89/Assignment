/*****************************************************************************
 * FILE: DSALinkedList.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: It's a more flexabile option to store a collection of data than an
 *          array as it has no fixed size and it can store generic data types
 * REFERENCE: none
 * COMMENTS: it contains an inner class, DSAListNode that handles each
 *           individual node in the list
 * REQUIRES: DSAListNode
 * Last Mod: 20/10/2017
 ******************************************************************************/

import java.util.*;
import java.io.*;

public class DSALinkedList<E> implements Iterable<E>, Serializable
{
   // TODO: how do I comment inner classes
   private class DSAListNode<E> implements Serializable
   {
      // Constants
      private static final long serialVersionUID = 42L;

      // private class feilds
      private E value;
      private DSAListNode<E> next;
      private DSAListNode<E> prev;

      /**************************************************************************
      * Alternate Constructor:
      * Import: value
      * Export: none
      * ASSERTION: initilizes the value of a new node to those imported
      * ************************************************************************/
      public DSAListNode(E inValue)
      {
         value = inValue;
         next = null;
         prev = null;
      }

      // Accesors and Mutators
      /************************************************************************
      * SUBMODULE: getValue
      * IMPORTS: none
      * EXPORTS: value
      * ASSERTION: returns the value stored in the node
      * **********************************************************************/
      public E getValue()
      {
         return value;
      }

      /***********************************************************************
       * SUBMODULE: setValue
       * IMPORTS: value
       * EXPORTS: none
       * ASSERTION: sets the current value to the value that's been imported
       * ********************************************************************/
      public void setValue(E value)
      {
         this.value = value;
      }

      /************************************************************************
      * SUBMODULE: getNext
      * IMPORTS: none
      * EXPORTS: next
      * ASSERTION: returns the the node in the linked list just before the
      *            current node
      * **********************************************************************/
      public DSAListNode<E> getNext()
      {
         return next;
      }

      /***********************************************************************
       * SUBMODULE: setNext
       * IMPORTS: next
       * EXPORTS: none
       * ASSERTION: sets what the next node in the current linked list will be
       * ********************************************************************/
      public void setNext(DSAListNode<E> next)
      {
         this.next = next;
      }

      /************************************************************************
      * SUBMODULE: getPrev
      * IMPORTS: none
      * EXPORTS: next
      * ASSERTION: returns the the node in the linked list just after the
      *            current node
      * **********************************************************************/
      public DSAListNode<E> getPrev()
      {
         return prev;
      }

      /***********************************************************************
       * SUBMODULE: setPrev
       * IMPORTS: prev
       * EXPORTS: none
       * ASSERTION: sets what the previous node in the current linked list will be
       * ********************************************************************/
      public void setPrev(DSAListNode<E> prev)
      {
         this.prev = prev;
      }

   }// end of inner class DSAListNode

   // Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   private DSAListNode<E> head;
   private DSAListNode<E> tail;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the values to default valid values
   * ************************************************************************/
   public DSALinkedList()
   {
      head = null; // empty list
      tail = null;
   }

   // Mutators
   /******************************************************************************
    * NAME: insertFirst
    * PURPOSE: Inserts an element at the start of the linked list
    * IMPORTS: newValue, the value of the element to be added
    * EXPORTS: none
    * Assertions:
    * Pre: newValue represent a generic valur to be added to the list
    * Post the newValue gets inserted to the front of the linked list
    * REMARKS: this algorithm was adapted from the DSA lecture notes
    *****************************************************************************/
   public void insertFirst(E newValue)
   {
      DSAListNode<E> newNd = new DSAListNode<E>(newValue);
      if(isEmpty())
      {
         head = newNd; // if the list is empty then the head and tail simply
         tail = newNd; // point to the new node
      }
      else
      {
         newNd.setNext(head); // the node is placed at the start of the list
         head.setPrev(newNd); // with the manipulation of a few next, pre and
         head = newNd;        // head pointers
      }
   }

   /***************************************************************************
   * NAME: insertLast
   * PURPOSE: Inserts an element at the end of the linked list
   * IMPORTS: newValue, the value of the element to be added
   * EXPORTS: none
   * Assertions:
   * Pre: newValue represent a generic valur to be added to the list
   * Post the newValue gets inserted to the end of the linked list
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   ***************************************************************************/
   public void insertLast(E newValue)
   {
      DSAListNode<E> newNd = new DSAListNode<E>(newValue); // the new node is been created
      if(isEmpty())
      {
         head = newNd; // if the list is empty then the head and tail simply
         tail = newNd; // point to the new node
      }
      else
      {
         newNd.setPrev(tail); // the node is placed at the end of the list
         tail.setNext(newNd); // with the manipulation of a few next, pre and
         tail = newNd;        // tail pointers
      }
   }

   /***************************************************************************
   * NAME: insertAt
   * PURPOSE: Inserts an element at given point of the linked list
   * IMPORTS: newValue, the value of the element to be added
   * EXPORTS: none
   * Assertions:
   * Pre: newValue represent a generic valur to be added at some point in the
   *      list
   * Post the newValue gets inserted to the front of the linked list
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   ***************************************************************************/
   public void insertAt(E newValue, int count)
   {
      DSAListNode<E> temp = head, newNd = new DSAListNode<E>(newValue);

      if(isEmpty())// if the list is empty then the new node is placed at the start
      {
         head = newNd;
         tail = newNd;
      }
      else
      {
         for(int ii = 0; ii < count-1; ii++)
         {
            temp = temp.getNext(); // the list is traversed until one node before the
                                   // desired location
         }

         newNd.setPrev(temp);            // the new node is been placed inbetween 2 nodes
         newNd.setNext(temp.getNext());
         temp.getNext().setPrev(newNd);
         temp.setNext(newNd);
      }
   }

/******************************************************************************
 * NAME: removeAt
 * PURPOSE: removes a node of in the linked list that contains the give value TODO: what starts recurssion??
 * IMPORTS: value, the value of the element to be removed
 * EXPORTS: none
 * Assertions:
 * Pre: value represent a generic value to be removed from the list
 * Post: the node storing the valuex
 * REMARKS: this algorithm was adapted from the DSA lecture notes
 *****************************************************************************/
   public void removeAt(E value)
   {
      if(head.getValue() == value)
      {
         removeFirst(); // if the value is the head then the first node is removed
      }
      else if(tail.getValue() == value)
      {
         removeLast(); // if the value is at the tail, the last node is removed
      }
      else
      {
         recRemove(head, value); // a call to a repectivecfunction that find
      }
   }

   // Accesors

   /***************************************************************************
   * SUBMODULE: getCount
   * IMPORTS: none
   * EXPORTS: count
   * ASSERTION: it returns the number of elements in the linked list
   * *************************************************************************/
   public int getCount()
   {
      return recCount(head); // starts a reccursion function to get the length os the list
   }

   /**************************************************************************
   * NAME: removeFirst
   * PURPOSE: to remove the first element of the linked list if there is any
   * IMPORTS: none
   * EXPORTS: node value
   * Assertions: TODO: what?
   * Pre: nothing is imported
   * Post: if there are any nodes in the linked list the first one is removed
   * REMARKS: this algorithm was adapted from the DSA lecture notes.
   *          I'm not sure why I'm getting a null poiter in this function
   **************************************************************************/
   public E removeFirst()
   {
      E nodeValue;

      if(isEmpty())
      {
         throw new IllegalArgumentException("the list is Empty");
      }
      else
      {
         nodeValue = head.getValue();
         head = head.getNext();
         // head.setPrev(null); NOTE: why am I getting a null pointer here??
      }
      return nodeValue;
   }

   /**************************************************************************
   * NAME: removeLast
   * PURPOSE: to remove the last element of the linked list if there is any
   * IMPORTS: none
   * EXPORTS: node value
   * Assertions: TODO: what?
   * Pre: nothing is imported
   * Post: if there are any nodes in the linked list the last one is removed
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public E removeLast()
   {
      E nodeValue;

      if(isEmpty())
      {
         throw new IllegalArgumentException("the list is Empty");
      }
      else if(head.getNext() == null)
      {
         nodeValue = head.getValue(); // this is if there is only one node in the
         head = null;                 // list
         tail = null;
      }
      else
      {
         nodeValue = tail.getValue(); // this methos is made easier by the tail pointer
         tail = tail.getPrev();
         tail.getNext().setPrev(null); // the last node is removed by the
         tail.setNext(null);          // manipulation of pre, next and tail pointers
      }
      return nodeValue;
   }

   /**************************************************************************
   * NAME: isEmpty
   * PURPOSE: to check if the list contains any nodes
   * IMPORTS: none
   * EXPORTS: true or false
   * Assertions: TODO: what?
   * Pre: nothing is imported
   * Post: if it's empty true is returned otherwise it returs false
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public boolean isEmpty()
   {
      boolean empty = false;
      if(head == null)
      {
         empty = true;
      }
      return empty;
   }

   /**************************************************************************
   * NAME: peekFirst
   * PURPOSE: it checks what's at the start of the linked list
   * IMPORTS: none
   * EXPORTS: node value
   * Assertions: TODO: what?
   * Pre: nothing is imported
   * Post: if the list isn't empty it returs the last element of the list
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public E peekFirst()
   {
      E nodeValue;
      if(isEmpty())
      {
         throw new IllegalArgumentException("the list is Empty");
      }
      else
      {
         nodeValue = head.getValue(); // it simply shows teh value of the head
      }
      return nodeValue;
   }

   /**************************************************************************
   * NAME: peekLast
   * PURPOSE: it checks what's at the end of the linked list
   * IMPORTS: none
   * EXPORTS: node value
   * Assertions: TODO: what?
   * Pre: nothing is imported
   * Post: if the list isn't empty it returs the first element of the list
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public E peekLast()
   {
      E nodeValue;
      if(isEmpty())
      {
         throw new IllegalArgumentException("the list is Empty"); // is that all?
      }
      else
      {
         nodeValue = tail.getValue(); // the same as peekFirst but with the tail
      }
      return nodeValue;
   }

   /**************************************************************************
   * NAME: toString
   * PURPOSE: it puts the Contents of the linked list into a String
   * IMPORTS: none
   * EXPORTS: a String of all the linked list's contents
   * Assertions: each node is iterativly called and their values are put into
   *             the String
   * Pre: nothing is imported
   * Post: a String of all the linked list data is returned
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public String toString()
   {
      String temp = "Contents:\t";
      DSAListNode<E> current = head;
      while(current != null)
      {
         temp += current.getValue() +" ";
         current = current.getNext();
      }
      temp += "\n";
      return temp;
   }

   /**************************************************************************
   * NAME: find
   * PURPOSE: a wrapper method to finds a node in the linked list containing the given value
   * IMPORTS: none
   * EXPORTS: the node containing the given value
   * Assertions: TODO: what?
   * Pre: the value to be found is given
   * Post: the node containing the given value is returned, or null if it
   *       can't be founs
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public DSAListNode<E> find(E value)
   {
      DSAListNode<E> node = recFind(head, value);

      return node;
   }

   // private stuff
   /**************************************************************************
   * NAME: recCount
   * PURPOSE: it reccursivly finds the number of nodes in the list from a
   *          given node
   * IMPORTS: the current node
   * EXPORTS: the number of nodes so far in the list
   * Assertions: TODO: what?
   * Pre: the current node is imported
   * Post: if the node is null reccursion is terminated, otherwise the method
   *       calls itself again with the next node in the list
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   private int recCount(DSAListNode<E> node)
   {
      int ii = 1;
      if(node == null)
      {
         ii = 0; // once the reccursion is terminated the count is started at 0
      }
      else if(node.getNext() != null)
      {
         ii = 1 + recCount(node.getNext()); // for each time the function calls
      }                                     // 1 is added, hence the length of
      return ii;                            // the list is found
   }

   /**************************************************************************
   * NAME: recRemove
   * PURPOSE: a recursive method that finds the node containing the specified
   *          value and deletes it from the list
   * IMPORTS: node and value, the value of the element to be removed, and the
   *          current node been checked
   * EXPORTS: none
   * Assertions: TODO: what?
   * Pre: value represent a generic value to be removed from the list and node
   *            is the current node of the linked list
   * Post: if one of the value matches the node value, it's removed and
   *       recurssion is stopped or if the node is null, otherwise the method
   *       is repeated with the next node
   * REMARKS: this algorithm was partially adapted from the DSA lecture notes
   **************************************************************************/
   private void recRemove(DSAListNode<E> node, E value)
   {
      if(node == null)
      {
         throw new IllegalArgumentException("node not foind"); // this is a terminating condition for
      }                                                        // when the value isn't found ina  node
      else if(node.getValue() == value)
      {
         node.getNext().setPrev(node.getPrev()); // if  node is found the node is
         node.getPrev().setNext(node.getNext()); // remove cutting off and recoconnecting
      }                                          // a few prev and next poiters
      else
      {
         recRemove(node.getNext(), value); // if none of the ternimating conditions
      }                                    // are met the the function continues to traverse
   }                                       // the list

   /**************************************************************************
   * NAME: recFind
   * PURPOSE: it finds the node containing the given value reccursivly
   * IMPORTS: node and vale, the current node been examined and the value to be
   *          found
   * EXPORTS: the node containing the given value
   * Assertions: TODO: what?
   * Pre: the value to be found and the current node is given
   * Post: if the node it found or is null, reccursion is ended, otherwise, the
   *       the next node is also searched, and so on
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   private DSAListNode<E> recFind(DSAListNode<E> node, E value)
   {
      DSAListNode<E> found;

      if(node == null) // this is a base condition for when the node is not found
      {
         found = null;
      }
      else if(node.getValue() == value)// this is the base condition for when
      {                                // the node is found
         found = node;
      }
      else
      {
         found = recFind(node.getNext(), value); // if one of the above base classes
      }                                          // are met, the method is called
      return found;                              // again with the next node
   }

   /**************************************************************************
   * NAME: iterator
   * PURPOSE: TODO: what should be done for this
   * IMPORTS: none
   * EXPORTS: the node containing the given value
   * Assertions: TODO: what?
   * Pre: the value to be found is given
   * Post: the node containing the given value is returned
   * REMARKS: this algorithm was adapted from the DSA lecture notes
   **************************************************************************/
   public Iterator<E> iterator()
   {
      return new DSALinkedListIterator<E>(this);
   }

   // end of DSALinkedList

   private class DSALinkedListIterator<E> implements Iterator<E>, Serializable
   {
      // Constants
      private static final long serialVersionUID = 42L;

      private DSALinkedList<E>.DSAListNode<E> iterNext;

      public DSALinkedListIterator(DSALinkedList<E> theList)
      {
         iterNext = theList.head;
      }

      // Iterator interface implementation
      public boolean hasNext()
      {
         return (iterNext != null);
      }

      public E next()
      {
         E value;
         if(iterNext == null)
         {
            value = null;
         }
         else
         {
            value = iterNext.getValue();
            iterNext = iterNext.getNext();
         }
         return value;
      }

      public void remove()
      {
         throw new UnsupportedOperationException("Not supported");
      }

   }
}
