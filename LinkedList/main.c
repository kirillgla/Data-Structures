#include <stdio.h>

#include "linkedList.h"

void act(linkedListContent_t value) {
    printf("%d\r\n", value);
}

int main() {
    int result;
    int local;
    LinkedList *list = newLinkedList();

    // Test insertion
    for (int i = 0; i < 100; i++) {
        result = appendToLinkedList(list, i);
        if (result) {
            fprintf(stderr, "Error inserting value\r\n");
            deleteLinkedList(list);
            return result;
        }
    }

    // Test search
    for (int i = 0; i < 100; i++) {
        result = findInLinkedList(list, i, NULL);
        if (!result) {
            fprintf(stderr, "Error: expected value not found\r\n");
            deleteLinkedList(list);
            return result;
        }
    }

    // Test deleting
    result = popFromLinkedList(list, &local);
    if (result) {
        fprintf(stderr, "Error deleting value from list: list error\r\n");
        deleteLinkedList(list);
        return result;
    }
    if (local != 0) {
        fprintf(stderr, "Error deleting value from list: wrong value returned\r\n");
        deleteLinkedList(list);
        return 1;
    }
    if (list->length != 99) {
        fprintf(stderr, "Error deleting value from list: result length is incorrect\r\n");
        deleteLinkedList(list);
        return 1;
    }
    if (list->first->value != 1) {
        fprintf(stderr, "Error deleting value from list: first element is incorrect\r\n");
        deleteLinkedList(list);
        return 1;
    }

    // forEachInLinkedList(list, &act);

    printf("All ok");
    deleteLinkedList(list);
    return 0;
}
