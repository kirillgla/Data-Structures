#include <malloc.h>

#include "linkedListNode.h"

LinkedListNode *newLinkedListNode(linkedListContent_t value) {
    LinkedListNode *result = (LinkedListNode *) malloc(sizeof(LinkedListNode));
    if (!result) {
        return NULL;
    }

    result->value = value;
    result->next = NULL;
    return result;
}

#ifdef LIST_CONTENT_NEEDS_FREEING
void deleteLinkedListNode(LinkedListNode *this, int preserverContent) {
    if (!this) {
        return;
    }
    if (!preserveContent) {
        free(this->value);
    }
    free(this);
}
#else

void deleteLinkedListNode(LinkedListNode *this) {
    if (!this) {
        return;
    }
    free(this);
}

#endif
