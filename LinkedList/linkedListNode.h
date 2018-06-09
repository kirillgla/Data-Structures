#ifndef LINKEDLIST_LINKEDLISTNODE_H
#define LINKEDLIST_LINKEDLISTNODE_H

#ifndef LINKEDLIST_CONTENT
#define LINKEDLIST_CONTENT
#define LIST_CONTENT_NEEDS_NO_FREEING
typedef int linkedListContent_t;
#endif

#ifdef LIST_CONTENT_NEEDS_FREEING
#ifdef LIST_CONTENT_NEEDS_NO_FREEING
#error incompatible macros detected
#endif
#else
#ifndef LIST_CONTENT_NEEDS_NO_FREEING
#warning no freing flag defined
#endif
#endif

typedef struct LinkedListNode {
    linkedListContent_t value;
    struct LinkedListNode *next;
} LinkedListNode;

LinkedListNode *newLinkedListNode(linkedListContent_t value);

/// Note: this function does not free whole list
#ifdef LIST_CONTENT_NEEDS_FREEING
void deleteLinkedListNode(LinkedListNode *this, int preserveContent);
#else
void deleteLinkedListNode(LinkedListNode *this);
#endif

#endif //LINKEDLIST_LINKEDLISTNODE_H
