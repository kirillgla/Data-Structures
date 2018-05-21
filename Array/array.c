#include <malloc.h>

#include "array.h"

Array *newArray(int size) {
    if (size < 0) {
        return NULL;
    }

    Array *result = (Array *) malloc(sizeof(Array));
    if (!result) {
        return NULL;
    }

    arrayContent_t *data = (arrayContent_t *) malloc(sizeof(arrayContent_t) * size);
    if (!data) {
        free(result);
        return NULL;
    }

    result->data = data;
    result->size = size;
    return result;
}

void deleteArray(Array *this) {
    if (!this) {
        return;
    }
    free(this->data);
    free(this);
}

int getElementAt(Array *this, int index, arrayContent_t *result) {
    if (!this || !result) {
        return 1;
    }

    if (index < 0 || index >= this->size) {
        return 2;
    }

    *result = this->data[index];
    return 0;
}

int setElementAt(Array *this, int index, arrayContent_t value) {
    if (!this) {
        return 1;
    }

    if (index < 0 || index >= this->size) {
        return 2;
    }

    this->data[index] = value;
    return 0;
}
