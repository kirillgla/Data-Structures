#ifndef LINKEDLIST_LINKEDLIST_H
#define LINKEDLIST_LINKEDLIST_H

#include "linkedListNode.h"

/// Returns 1 when items are equal, 0 otherwise
typedef int (*equalityComparator)(linkedListContent_t, linkedListContent_t);

typedef void (*listElementAction)(linkedListContent_t);

typedef struct {
    LinkedListNode *first;
    LinkedListNode *last;
    int length;
} LinkedList;

LinkedList *newLinkedList();

void deleteLinkedList(LinkedList *this);

int appendToLinkedList(LinkedList *this, linkedListContent_t value);

int findInLinkedList(LinkedList *this, linkedListContent_t value, equalityComparator comparator);

void forEachInLinkedList(LinkedList *this, listElementAction action);

int popFromLinkedList(LinkedList *this, linkedListContent_t *result);

// void deleteFromLinkedList(linkedListContent_t value);

#endif //LINKEDLIST_LINKEDLIST_H
