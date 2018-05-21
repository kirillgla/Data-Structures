#include <stdio.h>
#include <malloc.h>
#include "dynamicArray.h"

int main() {
    const int MAX_SIZE = 16;
    DynamicArray *array = newDynamicArray(MAX_SIZE);
    if (!array) {
        fprintf(stderr, "Error allocating memory");
        deleteDynamicArray(array);
        return 1;
    }

    for (int i = 0; i < 100; i++) {
        int result = addToEndOfDynamicArray(array, i);
        if (result) {
            fprintf(stderr, "Error writing to dynamic array");
            deleteDynamicArray(array);
            return 1;
        }
    }

    for (int i = 0; i < 100; i++) {
        int element;
        int result = getElementAt(array, i, &element);
        if (result) {
            fprintf(stderr, "Error reading from dynamic array");
            deleteDynamicArray(array);
            return 1;
        }
        if (element != i) {
            fprintf(stderr, "Error: added value is incorrect");
        }
    }

    int result = setElementAt(array, 42, 66);
    if (result) {
        fprintf(stderr, "Error modifying array contents");
        deleteDynamicArray(array);
        return 1;
    }
    int element;
    result = getElementAt(array, 42, &element);
    if (result) {
        fprintf(stderr, "Error reading modified element");
        deleteDynamicArray(array);
        return 1;
    }
    if (element != 66) {
        fprintf(stderr, "Error: saved values is incorrect");
        deleteDynamicArray(array);
        return 1;
    }

    printf("ok: size is %d", getNumberOfElements(array));
    deleteDynamicArray(array);
    return 0;
}
