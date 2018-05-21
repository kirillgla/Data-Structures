#include <stdio.h>
#include <malloc.h>

#include "array.h"

int main(int argc, char **argv) {
    const int ARRAY_SIZE = 1024;
    Array *array = newArray(ARRAY_SIZE);
    if (!array) {
        fprintf(stderr, "Error allocating array\n");
        return 1;
    }

    for (int i = 0; i < ARRAY_SIZE; i++) {
        int result = setElementAt(array, i, i);
        if (result) {
            fprintf(stderr, "Error writing to array\n");
            deleteArray(array);
            return 1;
        }
    }

    for (int i = 0; i < ARRAY_SIZE; i++) {
        int element;
        int result = getElementAt(array, i, &element);
        if (result) {
            fprintf(stderr, "Error reading from array\n");
            deleteArray(array);
            return 1;
        }
        if (element != i) {
            fprintf(stderr, "Array contains invalid data\n");
            deleteArray(array);
            return 1;
        }
    }

    printf("ok\n");
    deleteArray(array);
    return 0;
}
