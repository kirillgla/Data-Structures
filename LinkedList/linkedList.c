#include <malloc.h>

#include "linkedList.h"
#include "linkedListNode.h"

int defaultEqualityComparator(linkedListContent_t first, linkedListContent_t second) {
    return first == second;
}

LinkedList *newLinkedList() {
    LinkedList *result = (LinkedList *) malloc(sizeof(LinkedList));
    if (!result) {
        return NULL;
    }

    result->first = NULL;
    result->last = NULL;
    result->length = 0;
    return result;
}

void deleteLinkedList(LinkedList *this) {
    if (!this) {
        return;
    }

    LinkedListNode *current = this->first;
    while (current) {
        LinkedListNode *next = current->next;
#ifdef LIST_CONTENT_NEEDS_FREEING
        deleteLinkedListNode(current, 0);
#else
        deleteLinkedListNode(current);
#endif
        current = next;
    }

    free(this);
}

int appendToLinkedList(LinkedList *this, linkedListContent_t value) {
    if (!this) {
        return 1;
    }

    LinkedListNode *node = newLinkedListNode(value);
    if (!node) {
        return 2;
    }

    if (!this->first) {
        this->first = node;
    } else {
        this->last->next = node;
    }

    this->last = node;
    this->length++;
    return 0;
}

int findInLinkedList(LinkedList *this, linkedListContent_t value, equalityComparator comparator) {
    if (!this) {
        return 0;
    }

    if (!comparator) {
        comparator = &defaultEqualityComparator;
    }

    LinkedListNode *current = this->first;
    while (current) {
        if (comparator(current->value, value)) {
            return 1;
        }
        current = current->next;
    }

    return 0;
}

void forEachInLinkedList(LinkedList *this, listElementAction action) {
    if (!this || !action) {
        return;
    }

    LinkedListNode *current = this->first;
    while (current) {
        action(current->value);
        current = current->next;
    }
}

int popFromLinkedList(LinkedList *this, linkedListContent_t *result) {
    if (!this) {
        return 1;
    }
    if (!this->first) {
        return 3;
    }

    LinkedListNode *first = this->first;
    this->first = first->next;
    this->length--;
    if (this->length == 0) {
        this->last = NULL;
    }
    if (result) {
        *result = first->value;
    }

#ifdef LIST_CONTENT_NEEDS_FREEING
    deleteLinkedListNode(first, result);
#else
    deleteLinkedListNode(first);
#endif

    return 0;
}
